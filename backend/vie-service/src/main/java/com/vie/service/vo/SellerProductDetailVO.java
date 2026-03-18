package com.vie.service.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 卖家商品详情VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerProductDetailVO {

    /**
     * 商品ID
     */
    private Long id;

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
     * 分类ID
     */
    private Long categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

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
     * 状态：0-待审核,1-上架,2-下架,3-审核不通过
     */
    private Integer status;

    /**
     * 状态描述
     */
    private String statusDesc;

    /**
     * 是否推荐
     */
    private Integer isRecommended;

    /**
     * 是否新品
     */
    private Integer isNew;

    /**
     * 是否热卖
     */
    private Integer isHot;

    /**
     * 审核备注
     */
    private String auditRemark;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 商品图片列表
     */
    private List<ProductImageVO> images;

    /**
     * SKU列表
     */
    private List<ProductSkuVO> skuList;

    /**
     * 商品图片VO
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductImageVO {
        private Long id;
        private String imageUrl;
        private Integer sortOrder;
        private Integer imageType;
    }

    /**
     * 商品SKU VO
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductSkuVO {
        private Long id;
        private String skuName;
        private String skuCode;
        private String skuImage;
        private BigDecimal price;
        private Integer stock;
        private Integer salesVolume;
        private String specInfo;
        private Integer status;
    }
}
