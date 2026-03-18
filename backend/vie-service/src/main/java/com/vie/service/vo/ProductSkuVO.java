package com.vie.service.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品SKU VO
 */
@Data
public class ProductSkuVO {

    /**
     * SKU ID
     */
    private Long id;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * SKU名称
     */
    private String skuName;

    /**
     * SKU编码
     */
    private String skuCode;

    /**
     * SKU图片
     */
    private String skuImage;

    /**
     * SKU价格
     */
    private BigDecimal price;

    /**
     * SKU库存
     */
    private Integer stock;

    /**
     * SKU销量
     */
    private Integer salesVolume;

    /**
     * 规格信息（JSON格式）
     */
    private String specInfo;

    /**
     * 状态：0-禁用，1-正常
     */
    private Integer status;
}
