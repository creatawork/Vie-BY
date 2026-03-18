package com.vie.service.dto;

import lombok.Data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 库存调整DTO
 */
@Data
public class StockAdjustDTO {

    /**
     * 调整类型：ADD-增加, REDUCE-减少, SET-设置
     */
    @NotBlank(message = "调整类型不能为空")
    private String adjustType;

    /**
     * 调整数量
     */
    @NotNull(message = "调整数量不能为空")
    @Min(value = 0, message = "调整数量不能为负数")
    private Integer quantity;

    /**
     * 备注
     */
    private String remark;
}
