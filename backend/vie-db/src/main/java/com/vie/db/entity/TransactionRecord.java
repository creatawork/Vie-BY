package com.vie.db.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 交易记录实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("transaction_record")
public class TransactionRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 交易流水号
     */
    private String transactionNo;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 账户类型：1-用户钱包，2-商家账户
     */
    private Integer accountType;

    /**
     * 交易类型：RECHARGE-充值，ADMIN_RECHARGE-管理员充值，PAYMENT-支付，
     * REFUND-退款，SETTLEMENT-结算，WITHDRAW-提现，FREEZE-冻结
     */
    private String type;

    /**
     * 交易金额
     */
    private BigDecimal amount;

    /**
     * 交易后余额
     */
    private BigDecimal balanceAfter;

    /**
     * 关联订单号
     */
    private String relatedOrderNo;

    /**
     * 交易描述
     */
    private String description;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 账户类型常量
     */
    public static final int ACCOUNT_TYPE_USER_WALLET = 1;
    public static final int ACCOUNT_TYPE_SELLER_ACCOUNT = 2;

    /**
     * 交易类型常量
     */
    public static final String TYPE_RECHARGE = "RECHARGE";
    public static final String TYPE_ADMIN_RECHARGE = "ADMIN_RECHARGE";
    public static final String TYPE_PAYMENT = "PAYMENT";
    public static final String TYPE_REFUND = "REFUND";
    public static final String TYPE_SETTLEMENT = "SETTLEMENT";
    public static final String TYPE_WITHDRAW = "WITHDRAW";
    public static final String TYPE_FREEZE = "FREEZE";
}
