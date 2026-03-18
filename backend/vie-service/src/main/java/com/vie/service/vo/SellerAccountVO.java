package com.vie.service.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 商家账户VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerAccountVO {

    /**
     * 账户ID
     */
    private Long id;

    /**
     * 商家用户ID
     */
    private Long sellerId;

    /**
     * 可用余额
     */
    private BigDecimal balance;

    /**
     * 冻结金额（待结算）
     */
    private BigDecimal frozenAmount;

    /**
     * 累计收入
     */
    private BigDecimal totalIncome;

    /**
     * 累计提现
     */
    private BigDecimal totalWithdraw;
}
