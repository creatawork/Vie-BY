package com.vie.service.service;

import java.math.BigDecimal;

/**
 * 支付服务接口
 */
public interface PaymentService {

    /**
     * 订单支付
     * 1. 验证用户余额
     * 2. 扣减用户钱包余额
     * 3. 冻结商家账户金额
     * 4. 更新订单状态
     *
     * @param orderId 订单ID
     * @param userId 用户ID
     */
    void payOrder(Long orderId, Long userId);

    /**
     * 订单退款（取消订单时调用）
     * 1. 退款到用户钱包
     * 2. 解冻商家账户金额
     *
     * @param orderId 订单ID
     */
    void refundOrder(Long orderId);

    /**
     * 订单结算（确认收货时调用）
     * 1. 将商家冻结金额转为可用余额
     *
     * @param orderId 订单ID
     */
    void settleOrder(Long orderId);

    /**
     * 管理员充值
     *
     * @param userId 用户ID
     * @param amount 充值金额
     * @param adminId 管理员ID
     */
    void adminRecharge(Long userId, BigDecimal amount, Long adminId);
}
