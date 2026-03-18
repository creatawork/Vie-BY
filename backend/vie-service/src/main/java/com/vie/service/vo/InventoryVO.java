package com.vie.service.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 库存VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryVO {

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 主图URL
     */
    private String mainImage;

    /**
     * 总库存
     */
    private Integer totalStock;

    /**
     * SKU库存列表
     */
    private List<SkuInventoryVO> skuList;

    /**
     * SKU库存VO
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SkuInventoryVO {
        private Long skuId;
        private String skuName;
        private Integer stock;
        private Integer salesVolume;
        private Boolean lowStock; // 是否低库存（库存<10）
        private java.math.BigDecimal price; // SKU价格
    }
}
