package com.vie.service.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkuRankVO {
    private Long skuId;
    private String skuName;
    private String productName;
    private Integer salesVolume;
    private BigDecimal salesAmount;
    private Integer rank;
}

