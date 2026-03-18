package com.vie.service.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * 销售统计查询DTO
 */
@Data
public class SalesStatisticsQueryDTO {

    /**
     * 开始日期
     */
    private LocalDate startDate;

    /**
     * 结束日期
     */
    private LocalDate endDate;

    /**
     * 时间粒度：DAY-日, WEEK-周, MONTH-月, YEAR-年
     */
    private String timeUnit = "DAY";
}
