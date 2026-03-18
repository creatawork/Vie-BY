package com.vie.service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品创建DTO
 */
@Data
public class ProductCreateDTO {

    /**
     * 分类ID
     */
    @NotNull(message = "分类ID不能为空")
    private Long categoryId;

    /**
     * 商品名称
     */
    @NotBlank(message = "商品名称不能为空")
    private String productName;

    /**
     * 商品编码
     */
    private String productCode;

    /**
     * 主图URL
     */
    @NotBlank(message = "商品主图不能为空")
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
    @DecimalMin(value = "0.01", message = "原价必须大于0")
    private BigDecimal originalPrice;

    /**
     * 现价
     */
    @NotNull(message = "现价不能为空")
    @DecimalMin(value = "0.01", message = "现价必须大于0")
    private BigDecimal currentPrice;

    /**
     * 总库存
     */
    @NotNull(message = "库存不能为空")
    private Integer stock;

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
}
