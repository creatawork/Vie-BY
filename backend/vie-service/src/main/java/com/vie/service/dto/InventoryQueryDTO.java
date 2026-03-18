package com.vie.service.dto;

import lombok.Data;

/**
 * 库存查询DTO
 */
@Data
public class InventoryQueryDTO {

    /**
     * 页码
     */
    private Integer pageNum = 1;

    /**
     * 每页数量
     */
    private Integer pageSize = 10;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 是否只显示低库存（库存<10）
     */
    private Boolean lowStock;
}
