package com.vie.service.dto;

import lombok.Data;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

/**
 * 卖家创建商品DTO
 */
@Data
public class SellerProductCreateDTO {

    /**
     * 分类ID
     */
    @NotNull(message = "分类ID不能为空")
    private Long categoryId;

    /**
     * 商品名称
     */
    @NotBlank(message = "商品名称不能为空")
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
    @NotNull(message = "现价不能为空")
    private BigDecimal currentPrice;

    /**
     * 是否推荐：0-否，1-是
     */
    private Integer isRecommended = 0;

    /**
     * 是否新品：0-否，1-是
     */
    private Integer isNew = 0;

    /**
     * 是否热卖：0-否，1-是
     */
    private Integer isHot = 0;

    /**
     * 商品图片列表
     */
    private List<ProductImageDTO> images;

    /**
     * SKU列表
     */
    @Valid
    @NotNull(message = "SKU列表不能为空")
    @Size(min = 1, message = "至少需要一个SKU")
    private List<ProductSkuDTO> skuList;

    /**
     * 商品图片DTO
     */
    @Data
    public static class ProductImageDTO {
        private String imageUrl;
        private Integer sortOrder = 0;
        private Integer imageType = 1; // 1-商品图，2-详情图
    }

    /**
     * 商品SKU DTO
     */
    @Data
    public static class ProductSkuDTO {
        @NotBlank(message = "SKU名称不能为空")
        private String skuName;
        private String skuImage;
        @NotNull(message = "SKU价格不能为空")
        private BigDecimal price;
        @NotNull(message = "SKU库存不能为空")
        private Integer stock;
        private String specInfo; // JSON格式规格信息
    }
}
