package com.vie.service.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 交易记录VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRecordVO {

    /**
     * 记录ID
     */
    private Long id;

    /**
     * 交易流水号
     */
    private String transactionNo;

    /**
     * 交易类型
     */
    private String type;

    /**
     * 交易类型描述
     */
    private String typeDesc;

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
    private String createTime;

    /**
     * 交易类型描述映射
     */
    private static final Map<String, String> TYPE_DESC_MAP = Map.of(
            "RECHARGE", "充值",
            "ADMIN_RECHARGE", "管理员充值",
            "PAYMENT", "支付",
            "REFUND", "退款",
            "SETTLEMENT", "结算",
            "WITHDRAW", "提现",
            "FREEZE", "冻结"
    );

    /**
     * 获取交易类型描述
     */
    public static String getTypeDesc(String type) {
        return TYPE_DESC_MAP.getOrDefault(type, "未知");
    }
}
