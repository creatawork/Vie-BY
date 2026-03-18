package com.vie.service.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 商品排行VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRankVO {

    /**
     * 排名
     */
    private Integer rank;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 主图URL
     */
    private String mainImage;

    /**
     * 销量
     */
    private Integer salesVolume;

    /**
     * 销售额
     */
    private BigDecimal salesAmount;
}
