package com.vie.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vie.db.entity.BrowseHistory;
import com.vie.db.entity.Product;
import com.vie.db.mapper.BrowseHistoryMapper;
import com.vie.db.mapper.ProductMapper;
import com.vie.service.dto.BrowseHistoryQueryDTO;
import com.vie.service.exception.BusinessException;
import com.vie.service.service.BrowseHistoryService;
import com.vie.service.vo.BrowseHistoryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * 浏览历史服务实现类
 */
@Slf4j
@Service
public class BrowseHistoryServiceImpl implements BrowseHistoryService {

    @Autowired
    private BrowseHistoryMapper browseHistoryMapper;

    @Autowired
    private ProductMapper productMapper;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recordBrowse(Long userId, Long productId) {
        // 1. 校验商品是否存在
        Product product = productMapper.selectById(productId);
        if (product == null || product.getIsDeleted() == 1) {
            log.warn("商品不存在或已删除: {}", productId);
            return; // 商品不存在时静默失败，不影响主流程
        }

        // 2. 查询是否已有浏览记录
        LambdaQueryWrapper<BrowseHistory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BrowseHistory::getUserId, userId)
                    .eq(BrowseHistory::getProductId, productId)
                    .eq(BrowseHistory::getIsDeleted, 0);

        BrowseHistory existing = browseHistoryMapper.selectOne(queryWrapper);

        LocalDateTime now = LocalDateTime.now();

        if (existing != null) {
            // 已存在，更新浏览时间
            existing.setBrowseTime(now);
            browseHistoryMapper.updateById(existing);
            log.debug("更新浏览历史: userId={}, productId={}", userId, productId);
        } else {
            // 不存在，新增记录
            BrowseHistory history = BrowseHistory.builder()
                    .userId(userId)
                    .productId(productId)
                    .browseTime(now)
                    .build();

            browseHistoryMapper.insert(history);
            log.debug("新增浏览历史: userId={}, productId={}", userId, productId);
        }
    }

    @Override
    public IPage<BrowseHistoryVO> getBrowseHistoryPage(
            BrowseHistoryQueryDTO queryDTO, Long userId) {

        // 构建分页对象
        Page<Map<String, Object>> page = new Page<>(
                queryDTO.getPageNum(),
                queryDTO.getPageSize()
        );

        // 查询浏览历史（关联商品信息）
        IPage<Map<String, Object>> historyPage =
                browseHistoryMapper.selectBrowseHistoryPageWithProduct(page, userId);

        // 转换为VO
        IPage<BrowseHistoryVO> voPage = historyPage.convert(map -> {
            BrowseHistoryVO vo = BrowseHistoryVO.builder()
                    .historyId(getLongValue(map, "history_id"))
                    .productId(getLongValue(map, "product_id"))
                    .productName((String) map.get("product_name"))
                    .mainImage((String) map.get("main_image"))
                    .currentPrice(getBigDecimalValue(map, "current_price"))
                    .originalPrice(getBigDecimalValue(map, "original_price"))
                    .stock(getIntegerValue(map, "stock"))
                    .salesVolume(getIntegerValue(map, "sales_volume"))
                    .status(getIntegerValue(map, "status"))
                    .build();

            // 处理浏览时间
            Object browseTime = map.get("browse_time");
            if (browseTime instanceof LocalDateTime) {
                vo.setBrowseTime(((LocalDateTime) browseTime).format(FORMATTER));
            } else if (browseTime != null) {
                vo.setBrowseTime(browseTime.toString());
            }

            // 设置是否有货
            Integer stock = vo.getStock();
            vo.setInStock(stock != null && stock > 0);

            return vo;
        });

        return voPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBrowseHistory(Long historyId, Long userId) {
        // 查找浏览记录（使用QueryWrapper明确查询条件，避免@TableLogic影响）
        LambdaQueryWrapper<BrowseHistory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BrowseHistory::getId, historyId)
                    .eq(BrowseHistory::getIsDeleted, 0);
        
        BrowseHistory history = browseHistoryMapper.selectOne(queryWrapper);
        if (history == null) {
            throw new BusinessException(404, "浏览记录不存在");
        }

        // 校验权限（只能删除自己的记录）
        if (!history.getUserId().equals(userId)) {
            throw new BusinessException(403, "无权删除此记录");
        }

        // 使用UpdateWrapper进行逻辑删除，确保更新成功
        LambdaUpdateWrapper<BrowseHistory> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(BrowseHistory::getId, historyId)
                     .eq(BrowseHistory::getUserId, userId)
                     .eq(BrowseHistory::getIsDeleted, 0)
                     .set(BrowseHistory::getIsDeleted, 1);
        
        int updated = browseHistoryMapper.update(null, updateWrapper);
        if (updated == 0) {
            throw new BusinessException(500, "删除失败");
        }

        log.info("用户 {} 删除浏览记录 {}", userId, historyId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void clearBrowseHistory(Long userId) {
        // 批量逻辑删除该用户的所有浏览记录
        LambdaUpdateWrapper<BrowseHistory> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(BrowseHistory::getUserId, userId)
                     .eq(BrowseHistory::getIsDeleted, 0)
                     .set(BrowseHistory::getIsDeleted, 1);

        browseHistoryMapper.update(null, updateWrapper);

        log.info("用户 {} 清空浏览历史", userId);
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
