package com.vie.db.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 商品SKU实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("product_sku")
public class ProductSku extends BaseEntity {

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * SKU名称（如：500g、1kg）
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
     * 规格信息（JSON格式，如：{"重量":"500g","产地":"山东"}）
     */
    private String specInfo;

    /**
     * 状态：0-禁用，1-正常
     */
    private Integer status;
}
