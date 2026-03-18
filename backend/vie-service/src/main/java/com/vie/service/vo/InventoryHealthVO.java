package com.vie.service.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryHealthVO {
    private Integer lowStockCount;      // 低库存规格数
    private Integer outOfStockCount;   // 缺货规格数
    private Integer totalSkuCount;     // 总规格数
    private Double healthyRate;        // 健康率（非缺货/总数）
}

