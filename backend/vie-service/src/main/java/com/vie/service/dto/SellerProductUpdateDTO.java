package com.vie.service.dto;

import lombok.Data;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

/**
 * 卖家更新商品DTO
 */
@Data
public class SellerProductUpdateDTO {

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 商品名称
     */
    @Size(max = 200, message = "商品名称不能超过200字符")
    private String productName;

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
     * 商品图片列表（全量更新）
     */
    private List<SellerProductCreateDTO.ProductImageDTO> images;

    /**
     * SKU列表（全量更新）
     */
    @Valid
    private List<SellerProductCreateDTO.ProductSkuDTO> skuList;
}
