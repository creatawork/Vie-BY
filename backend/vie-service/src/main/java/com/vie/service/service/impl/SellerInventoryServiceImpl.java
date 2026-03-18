package com.vie.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vie.db.entity.Product;
import com.vie.db.entity.ProductSku;
import com.vie.db.entity.StockAdjustment;
import com.vie.db.mapper.ProductMapper;
import com.vie.db.mapper.ProductSkuMapper;
import com.vie.db.mapper.StockAdjustmentMapper;
import com.vie.service.dto.InventoryQueryDTO;
import com.vie.service.dto.StockAdjustDTO;
import com.vie.service.exception.BusinessException;
import com.vie.service.service.SellerInventoryService;
import com.vie.service.vo.InventoryVO;
import com.vie.service.vo.StockAdjustResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 卖家库存管理服务实现类
 */
@Slf4j
@Service
public class SellerInventoryServiceImpl implements SellerInventoryService {

    /**
     * 低库存阈值
     */
    private static final int LOW_STOCK_THRESHOLD = 10;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    private StockAdjustmentMapper stockAdjustmentMapper;

    @Override
    public IPage<InventoryVO> getInventoryPage(InventoryQueryDTO queryDTO, Long sellerId) {
        // 先查询商品
        Page<Product> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());

        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Product::getSellerId, sellerId);
        wrapper.eq(Product::getIsDeleted, 0);

        if (queryDTO.getProductId() != null) {
            wrapper.eq(Product::getId, queryDTO.getProductId());
        }

        wrapper.orderByDesc(Product::getCreateTime);

        IPage<Product> productPage = productMapper.selectPage(page, wrapper);

        if (productPage.getRecords().isEmpty()) {
            return productPage.convert(p -> null);
        }

        // 获取所有商品的SKU
        List<Long> productIds = productPage.getRecords().stream()
                .map(Product::getId)
                .collect(Collectors.toList());

        LambdaQueryWrapper<ProductSku> skuWrapper = new LambdaQueryWrapper<>();
        skuWrapper.in(ProductSku::getProductId, productIds);
        skuWrapper.eq(ProductSku::getIsDeleted, 0);
        List<ProductSku> allSkus = productSkuMapper.selectList(skuWrapper);

        // 按商品ID分组
        Map<Long, List<ProductSku>> skuMap = allSkus.stream()
                .collect(Collectors.groupingBy(ProductSku::getProductId));

        // 如果只显示低库存，过滤商品
        Boolean lowStockFilter = queryDTO.getLowStock();

        return productPage.convert(product -> {
            List<ProductSku> skus = skuMap.getOrDefault(product.getId(), new ArrayList<>());

            // 检查是否有低库存SKU
            boolean hasLowStock = skus.stream().anyMatch(sku -> sku.getStock() < LOW_STOCK_THRESHOLD);

            // 如果筛选低库存但该商品没有低库存SKU，返回null（后续过滤）
            if (Boolean.TRUE.equals(lowStockFilter) && !hasLowStock) {
                return null;
            }

            return buildInventoryVO(product, skus);
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StockAdjustResultVO adjustStock(Long skuId, StockAdjustDTO dto, Long sellerId) {
        // 查询SKU
        ProductSku sku = productSkuMapper.selectById(skuId);
        if (sku == null || sku.getIsDeleted() == 1) {
            throw new BusinessException(404, "SKU不存在");
        }

        // 验证商品归属
        Product product = productMapper.selectById(sku.getProductId());
        if (product == null || !product.getSellerId().equals(sellerId)) {
            throw new BusinessException(403, "无权操作此SKU");
        }

        int beforeStock = sku.getStock();
        int afterStock;

        String adjustType = dto.getAdjustType().toUpperCase();
        switch (adjustType) {
            case "ADD":
                afterStock = beforeStock + dto.getQuantity();
                break;
            case "REDUCE":
                afterStock = beforeStock - dto.getQuantity();
                if (afterStock < 0) {
                    throw new BusinessException(400, "库存不足，当前库存: " + beforeStock);
                }
                break;
            case "SET":
                afterStock = dto.getQuantity();
                break;
            default:
                throw new BusinessException(400, "无效的调整类型");
        }

        // 更新SKU库存
        sku.setStock(afterStock);
        productSkuMapper.updateById(sku);

        // 更新商品总库存
        updateProductTotalStock(product.getId());

        // 记录库存调整日志
        StockAdjustment adjustment = StockAdjustment.builder()
                .skuId(skuId)
                .productId(product.getId())
                .sellerId(sellerId)
                .adjustType(adjustType)
                .beforeStock(beforeStock)
                .adjustQuantity(dto.getQuantity())
                .afterStock(afterStock)
                .remark(dto.getRemark())
                .build();
        stockAdjustmentMapper.insert(adjustment);

        log.info("卖家 {} 调整库存，SKU: {}, 类型: {}, 调整前: {}, 调整后: {}",
                sellerId, skuId, adjustType, beforeStock, afterStock);

        return StockAdjustResultVO.builder()
                .skuId(skuId)
                .beforeStock(beforeStock)
                .afterStock(afterStock)
                .build();
    }

    // ========== 私有方法 ==========

    private void updateProductTotalStock(Long productId) {
        LambdaQueryWrapper<ProductSku> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductSku::getProductId, productId);
        wrapper.eq(ProductSku::getIsDeleted, 0);
        List<ProductSku> skus = productSkuMapper.selectList(wrapper);

        int totalStock = skus.stream().mapToInt(ProductSku::getStock).sum();

        Product product = new Product();
        product.setId(productId);
        product.setStock(totalStock);
        productMapper.updateById(product);
    }

    private InventoryVO buildInventoryVO(Product product, List<ProductSku> skus) {
        List<InventoryVO.SkuInventoryVO> skuList = skus.stream()
                .map(sku -> InventoryVO.SkuInventoryVO.builder()
                        .skuId(sku.getId())
                        .skuName(sku.getSkuName())
                        .stock(sku.getStock())
                        .salesVolume(sku.getSalesVolume())
                        .lowStock(sku.getStock() < LOW_STOCK_THRESHOLD)
                        .price(sku.getPrice())
                        .build())
                .collect(Collectors.toList());

        int totalStock = skus.stream().mapToInt(ProductSku::getStock).sum();

        return InventoryVO.builder()
                .productId(product.getId())
                .productName(product.getProductName())
                .mainImage(product.getMainImage())
                .totalStock(totalStock)
                .skuList(skuList)
                .build();
    }
}
