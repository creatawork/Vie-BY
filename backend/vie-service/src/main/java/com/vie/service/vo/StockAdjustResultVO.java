package com.vie.service.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 库存调整结果VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockAdjustResultVO {

    /**
     * SKU ID
     */
    private Long skuId;

    /**
     * 调整前库存
     */
    private Integer beforeStock;

    /**
     * 调整后库存
     */
    private Integer afterStock;
}
