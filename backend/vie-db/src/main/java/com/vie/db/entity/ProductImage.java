package com.vie.db.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品图片实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("product_image")
public class ProductImage extends BaseEntity {

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 图片URL
     */
    private String imageUrl;

    /**
     * 排序号
     */
    private Integer sortOrder;

    /**
     * 图片类型：1-商品图，2-详情图
     */
    private Integer imageType;
}
