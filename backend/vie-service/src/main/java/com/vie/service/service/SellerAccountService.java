package com.vie.service.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vie.service.dto.TransactionQueryDTO;
import com.vie.service.vo.SellerAccountVO;
import com.vie.service.vo.TransactionRecordVO;

import java.math.BigDecimal;

/**
 * 商家账户服务接口
 */
public interface SellerAccountService {

    /**
     * 获取商家账户信息
     *
     * @param sellerId 商家用户ID
     * @return 账户信息
     */
    SellerAccountVO getAccount(Long sellerId);

    /**
     * 创建商家账户
     *
     * @param sellerId 商家用户ID
     */
    void createAccount(Long sellerId);

    /**
     * 冻结金额（订单支付时调用）
     *
     * @param sellerId 商家ID
     * @param amount 金额
     * @param orderNo 订单号
     */
    void freeze(Long sellerId, BigDecimal amount, String orderNo);

    /**
     * 结算（确认收货时调用）- 冻结金额转为可用余额
     *
     * @param sellerId 商家ID
     * @param amount 金额
     * @param orderNo 订单号
     */
    void settle(Long sellerId, BigDecimal amount, String orderNo);

    /**
     * 解冻退回（退款时调用）
     *
     * @param sellerId 商家ID
     * @param amount 金额
     * @param orderNo 订单号
     */
    void unfreeze(Long sellerId, BigDecimal amount, String orderNo);

    /**
     * 提现
     *
     * @param sellerId 商家ID
     * @param amount 金额
     */
    void withdraw(Long sellerId, BigDecimal amount);

    /**
     * 查询交易记录
     *
     * @param sellerId 商家ID
     * @param queryDTO 查询条件
     * @return 交易记录分页列表
     */
    IPage<TransactionRecordVO> getTransactionRecords(Long sellerId, TransactionQueryDTO queryDTO);
}
