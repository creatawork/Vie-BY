package com.vie.service.dto;

import lombok.Data;

/**
 * 交易记录查询DTO
 */
@Data
public class TransactionQueryDTO {

    /**
     * 交易类型筛选
     */
    private String type;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 页码，默认1
     */
    private Integer pageNum = 1;

    /**
     * 每页数量，默认10
     */
    private Integer pageSize = 10;
}
