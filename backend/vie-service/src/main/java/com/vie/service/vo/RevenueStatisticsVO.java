package com.vie.service.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 收益统计VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RevenueStatisticsVO {

    /**
     * 总收益
     */
    private BigDecimal totalRevenue;

    /**
     * 可用余额
     */
    private BigDecimal availableBalance;

    /**
     * 冻结金额（待结算）
     */
    private BigDecimal frozenAmount;

    /**
     * 已提现金额
     */
    private BigDecimal withdrawnAmount;

    /**
     * 待结算金额
     */
    private BigDecimal pendingSettlement;
}
