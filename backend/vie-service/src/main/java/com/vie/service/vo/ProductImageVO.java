package com.vie.service.vo;

import lombok.Data;

/**
 * 商品图片VO
 */
@Data
public class ProductImageVO {

    /**
     * 图片ID
     */
    private Long id;

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
