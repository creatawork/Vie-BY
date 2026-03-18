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
 * 商家账户实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("seller_account")
public class SellerAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
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

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
