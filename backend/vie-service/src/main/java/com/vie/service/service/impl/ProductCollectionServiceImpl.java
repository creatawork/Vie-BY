package com.vie.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vie.db.entity.Product;
import com.vie.db.entity.ProductCollection;
import com.vie.db.mapper.ProductCollectionMapper;
import com.vie.db.mapper.ProductMapper;
import com.vie.service.common.ResultCode;
import com.vie.service.dto.CollectionQueryDTO;
import com.vie.service.exception.BusinessException;
import com.vie.service.service.ProductCollectionService;
import com.vie.service.vo.ProductCollectionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * 商品收藏服务实现类
 */
@Slf4j
@Service
public class ProductCollectionServiceImpl implements ProductCollectionService {

    @Autowired
    private ProductCollectionMapper collectionMapper;

    @Autowired
    private ProductMapper productMapper;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void collectProduct(Long productId, Long userId) {
        // 1. 校验商品是否存在且可收藏
        Product product = productMapper.selectById(productId);
        if (product == null || product.getIsDeleted() == 1) {
            throw new BusinessException(404, "商品不存在");
        }

        // 2. 检查是否已收藏
        LambdaQueryWrapper<ProductCollection> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductCollection::getUserId, userId)
                    .eq(ProductCollection::getProductId, productId)
                    .eq(ProductCollection::getIsDeleted, 0);

        ProductCollection existing = collectionMapper.selectOne(queryWrapper);
        if (existing != null) {
            throw new BusinessException(400, "商品已在收藏夹中");
        }

        // 3. 创建收藏记录
        ProductCollection collection = ProductCollection.builder()
                .userId(userId)
                .productId(productId)
                .build();

        int rows = collectionMapper.insert(collection);
        if (rows == 0) {
            throw new BusinessException(500, "收藏失败");
        }

        log.info("用户 {} 收藏商品 {} 成功", userId, productId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void uncollectProduct(Long productId, Long userId) {
        // 查找收藏记录
        LambdaQueryWrapper<ProductCollection> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductCollection::getUserId, userId)
                    .eq(ProductCollection::getProductId, productId)
                    .eq(ProductCollection::getIsDeleted, 0);

        ProductCollection collection = collectionMapper.selectOne(queryWrapper);
        if (collection == null) {
            throw new BusinessException(404, "收藏记录不存在");
        }

        // 使用UpdateWrapper进行逻辑删除，确保更新成功
        LambdaUpdateWrapper<ProductCollection> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(ProductCollection::getId, collection.getId())
                     .eq(ProductCollection::getUserId, userId)
                     .eq(ProductCollection::getIsDeleted, 0)
                     .set(ProductCollection::getIsDeleted, 1);
        
        int rows = collectionMapper.update(null, updateWrapper);
        if (rows == 0) {
            throw new BusinessException(500, "取消收藏失败");
        }

        log.info("用户 {} 取消收藏商品 {} 成功", userId, productId);
    }

    @Override
    public Boolean isCollected(Long productId, Long userId) {
        LambdaQueryWrapper<ProductCollection> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductCollection::getUserId, userId)
                    .eq(ProductCollection::getProductId, productId)
                    .eq(ProductCollection::getIsDeleted, 0);

        Long count = collectionMapper.selectCount(queryWrapper);
        return count != null && count > 0;
    }

    @Override
    public IPage<ProductCollectionVO> getCollectionPage(
            CollectionQueryDTO queryDTO, Long userId) {

        // 构建分页对象
        Page<Map<String, Object>> page = new Page<>(
                queryDTO.getPageNum(),
                queryDTO.getPageSize()
        );

        // 查询收藏列表（关联商品信息）
        IPage<Map<String, Object>> collectionPage =
                collectionMapper.selectCollectionPageWithProduct(page, userId);

        // 转换为VO
        IPage<ProductCollectionVO> voPage = collectionPage.convert(map -> {
            // 从Map中提取数据并构建VO
            ProductCollectionVO vo = ProductCollectionVO.builder()
                    .collectionId(getLongValue(map, "collection_id"))
                    .productId(getLongValue(map, "product_id"))
                    .productName((String) map.get("product_name"))
                    .mainImage((String) map.get("main_image"))
                    .currentPrice(getBigDecimalValue(map, "current_price"))
                    .originalPrice(getBigDecimalValue(map, "original_price"))
                    .stock(getIntegerValue(map, "stock"))
                    .salesVolume(getIntegerValue(map, "sales_volume"))
                    .status(getIntegerValue(map, "status"))
                    .build();

            // 处理收藏时间
            Object collectTime = map.get("collect_time");
            if (collectTime instanceof LocalDateTime) {
                vo.setCollectTime(((LocalDateTime) collectTime).format(FORMATTER));
            } else if (collectTime != null) {
                vo.setCollectTime(collectTime.toString());
            }

            // 设置是否有货
            Integer stock = vo.getStock();
            vo.setInStock(stock != null && stock > 0);

            return vo;
        });

        return voPage;
    }

    @Override
    public Long getCollectionCount(Long userId) {
        LambdaQueryWrapper<ProductCollection> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductCollection::getUserId, userId)
                    .eq(ProductCollection::getIsDeleted, 0);

        Long count = collectionMapper.selectCount(queryWrapper);
        return count != null ? count : 0L;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer clearCollections(Long userId) {
        // 使用UpdateWrapper批量逻辑删除
        LambdaUpdateWrapper<ProductCollection> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(ProductCollection::getUserId, userId)
                     .eq(ProductCollection::getIsDeleted, 0)
                     .set(ProductCollection::getIsDeleted, 1);

        int rows = collectionMapper.update(null, updateWrapper);
        
        log.info("用户 {} 清空收藏夹，共清空 {} 条记录", userId, rows);
        return rows;
    }

    /**
     * 从Map中获取Long值
     */
    private Long getLongValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) {
            return null;
        }
        if (value instanceof Long) {
            return (Long) value;
        }
        if (value instanceof Integer) {
            return ((Integer) value).longValue();
        }
        return Long.parseLong(value.toString());
    }

    /**
     * 从Map中获取BigDecimal值
     */
    private BigDecimal getBigDecimalValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) {
            return null;
        }
        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }
        return new BigDecimal(value.toString());
    }

    /**
     * 从Map中获取Integer值
     */
    private Integer getIntegerValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) {
            return null;
        }
        if (value instanceof Integer) {
            return (Integer) value;
        }
        if (value instanceof Long) {
            return ((Long) value).intValue();
        }
        return Integer.parseInt(value.toString());
    }
}
