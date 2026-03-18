package com.vie.service.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 购物车商品VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemVO {

    /**
     * 购物车ID
     */
    private Long cartId;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * SKU ID
     */
    private Long skuId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品图片
     */
    private String productImage;

    /**
     * SKU名称
     */
    private String skuName;

    /**
     * SKU图片
     */
    private String skuImage;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 小计金额
     */
    private BigDecimal totalPrice;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 是否选中
     */
    private Boolean selected;

    /**
     * 商品状态：1-上架，其他-不可购买
     */
    private Integer productStatus;

    /**
     * SKU状态：1-正常，其他-不可购买
     */
    private Integer skuStatus;

    /**
     * 是否有效（商品上架且SKU正常且有库存）
     */
    private Boolean valid;
}
