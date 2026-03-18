package com.vie.service.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 卖家商品列表VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerProductVO {

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
     * 审核备注
     */
    private String auditRemark;

    /**
     * 创建时间
     */
    private String createTime;
}
