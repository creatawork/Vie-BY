package com.vie.service.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 商品详情VO
 */
@Data
public class ProductDetailVO {

    /**
     * 商品ID
     */
    private Long id;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 商家ID
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
     * 商品图片列表
     */
    private List<ProductImageVO> images;

    /**
     * 商品SKU列表
     */
    private List<ProductSkuVO> skuList;

    /**
     * 平均评分
     */
    private Double avgRating;

    /**
     * 评价数量
     */
    private Integer reviewCount;

    /**
     * 各评分数量统计
     */
    private RatingStatistics ratingStatistics;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 评分统计内部类
     */
    @Data
    public static class RatingStatistics {
        private Integer fiveStar;   // 5星数量
        private Integer fourStar;   // 4星数量
        private Integer threeStar;  // 3星数量
        private Integer twoStar;    // 2星数量
        private Integer oneStar;    // 1星数量
    }
}
