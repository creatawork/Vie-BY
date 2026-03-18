package com.vie.service.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vie.db.entity.Order;
import com.vie.db.entity.OrderItem;
import com.vie.db.entity.Product;
import com.vie.db.entity.ProductReview;
import com.vie.db.mapper.OrderItemMapper;
import com.vie.db.mapper.OrderMapper;
import com.vie.db.mapper.ProductMapper;
import com.vie.db.mapper.ProductReviewMapper;
import com.vie.service.common.ResultCode;
import com.vie.service.dto.ProductReviewDTO;
import com.vie.service.exception.BusinessException;
import com.vie.service.service.ProductReviewService;
import com.vie.service.vo.ProductReviewVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商品评价服务实现类
 */
@Slf4j
@Service
public class ProductReviewServiceImpl implements ProductReviewService {

    @Autowired
    private ProductReviewMapper productReviewMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createReview(ProductReviewDTO dto, Long userId) {
        // 1. 验证商品是否存在
        Product product = productMapper.selectById(dto.getProductId());
        if (product == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "商品不存在");
        }

        // 2. 验证订单是否存在且属于当前用户
        Order order = orderMapper.selectById(dto.getOrderId());
        if (order == null || order.getIsDeleted() == 1) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "订单不存在");
        }
        if (!order.getUserId().equals(userId)) {
            throw new BusinessException(ResultCode.FORBIDDEN.getCode(), "无权评价此订单");
        }

        // 3. 验证订单状态为已完成
        if (order.getStatus() != 3) {
            throw new BusinessException(400, "只有已完成的订单才能评价");
        }

        // 4. 验证订单中包含该商品
        List<OrderItem> orderItems = orderItemMapper.selectByOrderId(dto.getOrderId());
        boolean containsProduct = orderItems.stream()
                .anyMatch(item -> item.getProductId().equals(dto.getProductId()));
        if (!containsProduct) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "该订单中不包含此商品");
        }

        // 5. 防止重复评价
        Integer existCount = productReviewMapper.countByUserAndOrderAndProduct(
                userId, dto.getOrderId(), dto.getProductId());
        if (existCount > 0) {
            throw new BusinessException(400, "您已对该订单中的此商品进行过评价");
        }

        // 6. 创建评价
        ProductReview review = new ProductReview();
        BeanUtils.copyProperties(dto, review);
        review.setUserId(userId);
        review.setStatus(1); // 显示

        // images 字段是 MySQL JSON 类型，先做格式校验与标准化，避免数据库层抛异常
        String images = dto.getImages();
        validateImagesFormat(images);
        if (images == null || images.trim().isEmpty()) {
            review.setImages(null);
        } else {
            String normalizedImages = normalizeImagesToJsonArray(images);
            review.setImages(normalizedImages);
        }

        productReviewMapper.insert(review);

        log.info("商品评价发布成功：reviewId={}, productId={}, orderId={}, userId={}, rating={}",
                 review.getId(), dto.getProductId(), dto.getOrderId(), userId, dto.getRating());
    }

    @Override
    public IPage<ProductReviewVO> getReviewPage(Long productId, Integer rating,
                                                 Integer pageNum, Integer pageSize) {
        Page<ProductReview> page = new Page<>(pageNum, pageSize);

        // 查询评价分页（带用户信息）
        IPage<ProductReview> reviewPage = productReviewMapper.selectReviewPage(page, productId, rating);

        // 转换为VO
        return reviewPage.convert(review -> convertToVO(review));
    }

    @Override
    public IPage<ProductReviewVO> getUserReviewPage(Long userId, Integer pageNum, Integer pageSize) {
        Page<ProductReview> page = new Page<>(pageNum, pageSize);

        // 查询用户评价分页
        IPage<ProductReview> reviewPage = productReviewMapper.selectUserReviewPage(page, userId);

        // 转换为VO
        return reviewPage.convert(review -> convertToVO(review));
    }

    @Override
    public List<Long> getReviewedProductIds(Long orderId, Long userId) {
        return productReviewMapper.selectReviewedProductIdsByOrderId(orderId, userId);
    }

    @Override
    public boolean isOrderFullyReviewed(Long orderId, Long userId, int orderItemCount) {
        Integer reviewCount = productReviewMapper.countByOrderId(orderId);
        return reviewCount >= orderItemCount;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void replyReview(Long reviewId, String replyContent, Long sellerId) {
        // 1. 查询评价
        ProductReview review = productReviewMapper.selectById(reviewId);
        if (review == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "评价不存在");
        }

        // 2. 验证权限：商家只能回复自己商品的评价
        Product product = productMapper.selectById(review.getProductId());
        if (product == null || !product.getSellerId().equals(sellerId)) {
            throw new BusinessException(ResultCode.FORBIDDEN.getCode(), "无权回复此评价");
        }

        // 3. 检查是否已回复
        if (review.getReplyContent() != null && !review.getReplyContent().isEmpty()) {
            throw new BusinessException(400, "该评价已回复，不可重复回复");
        }

        // 4. 更新回复内容
        review.setReplyContent(replyContent);
        review.setReplyTime(LocalDateTime.now());
        productReviewMapper.updateById(review);

        log.info("评价回复成功：reviewId={}, sellerId={}", reviewId, sellerId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteReview(Long reviewId) {
        ProductReview review = productReviewMapper.selectById(reviewId);
        if (review == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "评价不存在");
        }

        productReviewMapper.deleteById(reviewId);

        log.info("评价删除成功：reviewId={}", reviewId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateReviewStatus(Long reviewId, Integer status) {
        ProductReview review = productReviewMapper.selectById(reviewId);
        if (review == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "评价不存在");
        }

        review.setStatus(status);
        productReviewMapper.updateById(review);

        log.info("评价状态更新成功：reviewId={}, status={}", reviewId, status);
    }

    @Override
    public IPage<ProductReviewVO> getAdminReviewPage(String keyword,
                                                     Integer status,
                                                     Integer rating,
                                                     Long productId,
                                                     Long userId,
                                                     Integer pageNum,
                                                     Integer pageSize) {
        Page<ProductReview> page = new Page<>(pageNum, pageSize);
        IPage<ProductReview> reviewPage = productReviewMapper.selectAdminReviewPage(
                page, keyword, status, rating, productId, userId);
        return reviewPage.convert(this::convertToVO);
    }

    @Override
    public ProductReviewVO getAdminReviewDetail(Long reviewId) {
        ProductReview review = productReviewMapper.selectAdminReviewById(reviewId);
        if (review == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "评价不存在");
        }
        return convertToVO(review);
    }

    @Override
    public Map<String, Object> getAdminReviewStatistics() {
        Map<String, Object> stats = new HashMap<>();

        IPage<ProductReview> allPage = productReviewMapper.selectAdminReviewPage(
                new Page<>(1, 1), null, null, null, null, null);
        IPage<ProductReview> visiblePage = productReviewMapper.selectAdminReviewPage(
                new Page<>(1, 1), null, 1, null, null, null);
        IPage<ProductReview> hiddenPage = productReviewMapper.selectAdminReviewPage(
                new Page<>(1, 1), null, 0, null, null, null);

        stats.put("total", allPage.getTotal());
        stats.put("visible", visiblePage.getTotal());
        stats.put("hidden", hiddenPage.getTotal());
        return stats;
    }


    /**
     * 校验 images 字段格式：
     * - 允许 null/空串（表示无图片）
     * - 允许 JSON 数组字符串（如 ["a","b"]）
     * - 允许逗号分隔字符串（如 a,b）
     */
    private void validateImagesFormat(String images) {
        if (images == null || images.trim().isEmpty()) {
            return;
        }

        String trimmed = images.trim();
        if (trimmed.startsWith("[") && trimmed.endsWith("]")) {
            try {
                JsonNode node = OBJECT_MAPPER.readTree(trimmed);
                if (!node.isArray()) {
                    throw new BusinessException(ResultCode.PARAM_ERROR.getCode(),
                            "评价图片格式错误：images 必须是 JSON 数组");
                }
            } catch (BusinessException e) {
                throw e;
            } catch (Exception e) {
                throw new BusinessException(ResultCode.PARAM_ERROR.getCode(),
                        "评价图片格式错误：images 不是合法 JSON 数组");
            }
        }
    }

    /**
     * 将前端传入的图片字符串标准化为 JSON 数组字符串。
     * 支持两种输入：
     * 1) 已经是 JSON 数组： ["url1","url2"]
     * 2) 逗号分隔字符串： url1,url2
     */
    private String normalizeImagesToJsonArray(String images) {
        String trimmed = images == null ? null : images.trim();
        if (trimmed == null || trimmed.isEmpty()) {
            return null;
        }

        // 已是 JSON 数组，直接返回
        if (trimmed.startsWith("[") && trimmed.endsWith("]")) {
            return trimmed;
        }

        // 兼容旧格式：逗号分隔 URL，转换为 JSON 数组
        List<String> urls = java.util.Arrays.stream(trimmed.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        if (urls.isEmpty()) {
            return null;
        }

        String jsonArray = urls.stream()
                .map(url -> "\"" + url.replace("\\", "\\\\").replace("\"", "\\\"") + "\"")
                .collect(Collectors.joining(",", "[", "]"));

        return jsonArray;
    }


    /**
     * 将评价实体转换为VO
     */
    private ProductReviewVO convertToVO(ProductReview review) {
        ProductReviewVO vo = new ProductReviewVO();
        BeanUtils.copyProperties(review, vo);

        // 设置用户信息
        if (review.getUser() != null) {
            vo.setUserNickname(review.getUser().getNickname());
            vo.setUserAvatar(review.getUser().getAvatar());
        }

        // 查询商品信息
        Product product = productMapper.selectById(review.getProductId());
        if (product != null) {
            vo.setProductName(product.getProductName());
            vo.setProductMainImage(product.getMainImage());
        }

        return vo;
    }
}
