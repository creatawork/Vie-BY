package com.vie.service.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 浏览历史 VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrowseHistoryVO {

    /**
     * 浏览记录ID
     */
    private Long historyId;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品主图
     */
    private String mainImage;

    /**
     * 现价
     */
    private BigDecimal currentPrice;

    /**
     * 原价
     */
    private BigDecimal originalPrice;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 销量
     */
    private Integer salesVolume;

    /**
     * 商品状态：0-待审核，1-上架，2-下架，3-审核不通过
     */
    private Integer status;

    /**
     * 浏览时间
     */
    private String browseTime;

    /**
     * 是否有货
     */
    private Boolean inStock;
}
