package com.vie.db.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品基础信息实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("product")
public class Product extends BaseEntity {

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 商家ID（关联user表）
     */
    private Long sellerId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品编码
     */
    private String productCode;

    /**
     * 主图URL
     */
    private String mainImage;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 商品详情（富文本）
     */
    private String detail;

    /**
     * 原价
     */
    private BigDecimal originalPrice;

    /**
     * 现价
     */
    private BigDecimal currentPrice;

    /**
     * 总库存
     */
    private Integer stock;

    /**
     * 销量
     */
    private Integer salesVolume;

    /**
     * 浏览量
     */
    private Integer viewCount;

    /**
     * 状态：0-待审核，1-上架，2-下架，3-审核不通过
     */
    private Integer status;

    /**
     * 是否推荐：0-否，1-是
     */
    private Integer isRecommended;

    /**
     * 是否新品：0-否，1-是
     */
    private Integer isNew;

    /**
     * 是否热卖：0-否，1-是
     */
    private Integer isHot;

    /**
     * 审核备注
     */
    private String auditRemark;

    /**
     * 分类信息（非数据库字段）
     */
    @TableField(exist = false)
    private Category category;

    /**
     * 商品图片列表（非数据库字段）
     */
    @TableField(exist = false)
    private List<ProductImage> images;

    /**
     * 商品SKU列表（非数据库字段）
     */
    @TableField(exist = false)
    private List<ProductSku> skuList;

    /**
     * 平均评分（非数据库字段）
     */
    @TableField(exist = false)
    private Double avgRating;

    /**
     * 评价数量（非数据库字段）
     */
    @TableField(exist = false)
    private Integer reviewCount;
}
