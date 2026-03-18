package com.vie.service.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 用户钱包VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletVO {

    /**
     * 钱包ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 可用余额
     */
    private BigDecimal balance;

    /**
     * 冻结金额
     */
    private BigDecimal frozenAmount;

    /**
     * 累计充值
     */
    private BigDecimal totalRecharge;

    /**
     * 累计消费
     */
    private BigDecimal totalConsume;
}
