package com.vie.service.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vie.service.dto.TransactionQueryDTO;
import com.vie.service.vo.TransactionRecordVO;
import com.vie.service.vo.WalletVO;

import java.math.BigDecimal;

/**
 * 用户钱包服务接口
 */
public interface WalletService {

    /**
     * 获取用户钱包信息
     *
     * @param userId 用户ID
     * @return 钱包信息
     */
    WalletVO getWallet(Long userId);

    /**
     * 创建用户钱包
     *
     * @param userId 用户ID
     */
    void createWallet(Long userId);

    /**
     * 用户充值
     *
     * @param userId 用户ID
     * @param amount 充值金额
     */
    void recharge(Long userId, BigDecimal amount);

    /**
     * 支付扣款（内部调用）
     *
     * @param userId 用户ID
     * @param amount 扣款金额
     * @param orderNo 订单号
     */
    void deduct(Long userId, BigDecimal amount, String orderNo);

    /**
     * 退款（内部调用）
     *
     * @param userId 用户ID
     * @param amount 退款金额
     * @param orderNo 订单号
     */
    void refund(Long userId, BigDecimal amount, String orderNo);

    /**
     * 管理员充值
     *
     * @param userId 用户ID
     * @param amount 充值金额
     * @param adminId 管理员ID
     */
    void adminRecharge(Long userId, BigDecimal amount, Long adminId);

    /**
     * 查询交易记录
     *
     * @param userId 用户ID
     * @param queryDTO 查询条件
     * @return 交易记录分页列表
     */
    IPage<TransactionRecordVO> getTransactionRecords(Long userId, TransactionQueryDTO queryDTO);

    /**
     * 检查余额是否充足
     *
     * @param userId 用户ID
     * @param amount 金额
     * @return 是否充足
     */
    boolean checkBalance(Long userId, BigDecimal amount);
}
