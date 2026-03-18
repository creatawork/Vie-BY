package com.vie.service.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 商品审核DTO
 */
@Data
public class ProductAuditDTO {

    @NotNull(message = "状态不能为空")
    @Min(value = 1, message = "无效的状态")
    @Max(value = 3, message = "无效的状态")
    private Integer status; // 1-通过(上架), 3-驳回

    private String auditRemark; // 审核备注
}

