package com.vie.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vie.db.entity.SellerAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * 商家账户Mapper接口
 */
@Mapper
public interface SellerAccountMapper extends BaseMapper<SellerAccount> {

    /**
     * 根据商家ID查询账户
     *
     * @param sellerId 商家用户ID
     * @return 商家账户
     */
    SellerAccount selectBySellerId(@Param("sellerId") Long sellerId);

    /**
     * 增加冻结金额（订单支付时）
     *
     * @param sellerId 商家ID
     * @param amount 金额
     * @return 影响行数
     */
    int increaseFrozenAmount(@Param("sellerId") Long sellerId, @Param("amount") BigDecimal amount);

    /**
     * 减少冻结金额（退款时）
     *
     * @param sellerId 商家ID
     * @param amount 金额
     * @return 影响行数（0表示冻结金额不足）
     */
    int decreaseFrozenAmount(@Param("sellerId") Long sellerId, @Param("amount") BigDecimal amount);

    /**
     * 结算：冻结金额转为可用余额
     *
     * @param sellerId 商家ID
     * @param amount 金额
     * @return 影响行数（0表示冻结金额不足）
     */
    int settle(@Param("sellerId") Long sellerId, @Param("amount") BigDecimal amount);

    /**
     * 增加可用余额
     *
     * @param sellerId 商家ID
     * @param amount 金额
     * @return 影响行数
     */
    int increaseBalance(@Param("sellerId") Long sellerId, @Param("amount") BigDecimal amount);

    /**
     * 扣减可用余额（提现）
     *
     * @param sellerId 商家ID
     * @param amount 金额
     * @return 影响行数（0表示余额不足）
     */
    int decreaseBalance(@Param("sellerId") Long sellerId, @Param("amount") BigDecimal amount);

    /**
     * 增加累计收入
     *
     * @param sellerId 商家ID
     * @param amount 金额
     * @return 影响行数
     */
    int increaseTotalIncome(@Param("sellerId") Long sellerId, @Param("amount") BigDecimal amount);

    /**
     * 增加累计提现
     *
     * @param sellerId 商家ID
     * @param amount 金额
     * @return 影响行数
     */
    int increaseTotalWithdraw(@Param("sellerId") Long sellerId, @Param("amount") BigDecimal amount);
}
