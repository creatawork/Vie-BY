package com.vie.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vie.db.entity.UserWallet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * 用户钱包Mapper接口
 */
@Mapper
public interface UserWalletMapper extends BaseMapper<UserWallet> {

    /**
     * 根据用户ID查询钱包
     *
     * @param userId 用户ID
     * @return 用户钱包
     */
    UserWallet selectByUserId(@Param("userId") Long userId);

    /**
     * 增加余额（充值）
     *
     * @param userId 用户ID
     * @param amount 金额
     * @return 影响行数
     */
    int increaseBalance(@Param("userId") Long userId, @Param("amount") BigDecimal amount);

    /**
     * 扣减余额（支付）
     *
     * @param userId 用户ID
     * @param amount 金额
     * @return 影响行数（0表示余额不足）
     */
    int decreaseBalance(@Param("userId") Long userId, @Param("amount") BigDecimal amount);

    /**
     * 增加累计充值
     *
     * @param userId 用户ID
     * @param amount 金额
     * @return 影响行数
     */
    int increaseTotalRecharge(@Param("userId") Long userId, @Param("amount") BigDecimal amount);

    /**
     * 增加累计消费
     *
     * @param userId 用户ID
     * @param amount 金额
     * @return 影响行数
     */
    int increaseTotalConsume(@Param("userId") Long userId, @Param("amount") BigDecimal amount);
}
