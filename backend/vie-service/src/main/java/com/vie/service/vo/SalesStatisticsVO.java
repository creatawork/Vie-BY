package com.vie.service.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 销售统计VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesStatisticsVO {

    /**
     * 汇总数据
     */
    private SalesSummary summary;

    /**
     * 趋势数据
     */
    private List<SalesTrend> trend;

    /**
     * 销售汇总
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SalesSummary {
        /**
         * 总销售额
         */
        private BigDecimal totalSales;

        /**
         * 总订单数
         */
        private Integer totalOrders;

        /**
         * 总销量
         */
        private Integer totalQuantity;

        /**
         * 平均订单金额
         */
        private BigDecimal avgOrderAmount;
    }

    /**
     * 销售趋势
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SalesTrend {
        /**
         * 日期/时间段
         */
        private String date;

        /**
         * 销售额
         */
        private BigDecimal sales;

        /**
         * 订单数
         */
        private Integer orders;
    }
}
