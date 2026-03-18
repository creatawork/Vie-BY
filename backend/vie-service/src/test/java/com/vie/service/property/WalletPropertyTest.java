package com.vie.service.property;

import com.vie.db.entity.UserWallet;
import com.vie.db.mapper.TransactionRecordMapper;
import com.vie.db.mapper.UserWalletMapper;
import com.vie.service.exception.BusinessException;
import com.vie.service.service.impl.WalletServiceImpl;
import net.jqwik.api.*;
import net.jqwik.api.lifecycle.BeforeProperty;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import org.mockito.Mockito;

/**
 * 钱包服务属性测试
 * 验证充值金额验证和余额一致性
 */
public class WalletPropertyTest {

    @Mock
    private UserWalletMapper userWalletMapper;

    @Mock
    private TransactionRecordMapper transactionRecordMapper;

    @InjectMocks
    private WalletServiceImpl walletService;

    @BeforeProperty
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Mockito.reset(userWalletMapper, transactionRecordMapper);
    }

    /**
     * Property 3: 充值金额验证
     * 对于任何非正数金额，系统应拒绝充值请求，钱包余额保持不变
     */
    @Property
    void rechargeWithNonPositiveAmountShouldBeRejected(
            @ForAll("nonPositiveAmounts") BigDecimal amount) {
        // Given
        Long userId = 1L;
        BigDecimal initialBalance = new BigDecimal("100.00");
        
        UserWallet wallet = createWallet(1L, userId, initialBalance);
        
        when(userWalletMapper.selectByUserId(userId)).thenReturn(wallet);

        // When & Then
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            walletService.recharge(userId, amount);
        });
        
        assertEquals(2003, exception.getCode());
        assertEquals("充值金额无效", exception.getMessage());
        
        // 验证余额更新方法未被调用
        verify(userWalletMapper, never()).increaseBalance(any(), any());
    }

    @Provide
    Arbitrary<BigDecimal> nonPositiveAmounts() {
        return Arbitraries.oneOf(
                // 零
                Arbitraries.just(BigDecimal.ZERO),
                // 负数
                Arbitraries.bigDecimals()
                        .lessThan(BigDecimal.ZERO)
                        .ofScale(2),
                // null 会在服务层被处理
                Arbitraries.just(new BigDecimal("-0.01")),
                Arbitraries.just(new BigDecimal("-100.00")),
                Arbitraries.just(new BigDecimal("-999999.99"))
        );
    }

    /**
     * Property 4: 充值余额一致性
     * 对于任何成功的充值金额X，钱包余额应恰好增加X，并创建交易记录
     */
    @Property
    void rechargeWithPositiveAmountShouldIncreaseBalanceExactly(
            @ForAll("positiveAmounts") BigDecimal amount) {
        // Given
        Long userId = 1L;
        BigDecimal initialBalance = new BigDecimal("100.00");
        
        UserWallet wallet = createWallet(1L, userId, initialBalance);
        UserWallet updatedWallet = createWallet(1L, userId, initialBalance.add(amount));
        updatedWallet.setTotalRecharge(amount);
        
        when(userWalletMapper.selectByUserId(userId))
                .thenReturn(wallet)
                .thenReturn(updatedWallet);
        when(userWalletMapper.increaseBalance(eq(userId), eq(amount))).thenReturn(1);
        when(userWalletMapper.increaseTotalRecharge(eq(userId), eq(amount))).thenReturn(1);
        when(transactionRecordMapper.insert(any())).thenReturn(1);

        // When
        walletService.recharge(userId, amount);

        // Then
        // 验证余额增加方法被调用，且金额正确
        verify(userWalletMapper, atLeastOnce()).increaseBalance(userId, amount);
        // 验证累计充值增加
        verify(userWalletMapper, atLeastOnce()).increaseTotalRecharge(userId, amount);
        // 验证交易记录被创建
        verify(transactionRecordMapper, atLeastOnce()).insert(any());
    }

    @Provide
    Arbitrary<BigDecimal> positiveAmounts() {
        return Arbitraries.bigDecimals()
                .between(new BigDecimal("0.01"), new BigDecimal("100000.00"))
                .ofScale(2);
    }

    /**
     * Property: 余额检查一致性
     * 检查余额方法应正确判断余额是否充足
     */
    @Property
    void checkBalanceShouldReturnCorrectResult(
            @ForAll("balanceScenarios") BalanceScenario scenario) {
        // Given
        Long userId = 1L;
        
        UserWallet wallet = createWallet(1L, userId, scenario.balance);
        
        when(userWalletMapper.selectByUserId(userId)).thenReturn(wallet);

        // When
        boolean result = walletService.checkBalance(userId, scenario.requiredAmount);

        // Then
        assertEquals(scenario.expectedResult, result);
    }

    @Provide
    Arbitrary<BalanceScenario> balanceScenarios() {
        return Arbitraries.of(
                // 余额充足
                new BalanceScenario(new BigDecimal("100.00"), new BigDecimal("50.00"), true),
                new BalanceScenario(new BigDecimal("100.00"), new BigDecimal("100.00"), true),
                // 余额不足
                new BalanceScenario(new BigDecimal("100.00"), new BigDecimal("100.01"), false),
                new BalanceScenario(new BigDecimal("0.00"), new BigDecimal("0.01"), false),
                // 边界情况
                new BalanceScenario(new BigDecimal("0.01"), new BigDecimal("0.01"), true)
        );
    }

    // ========== 辅助方法 ==========

    private UserWallet createWallet(Long id, Long userId, BigDecimal balance) {
        UserWallet wallet = new UserWallet();
        wallet.setId(id);
        wallet.setUserId(userId);
        wallet.setBalance(balance);
        wallet.setFrozenAmount(BigDecimal.ZERO);
        wallet.setTotalRecharge(BigDecimal.ZERO);
        wallet.setTotalConsume(BigDecimal.ZERO);
        return wallet;
    }

    record BalanceScenario(BigDecimal balance, BigDecimal requiredAmount, boolean expectedResult) {}
}
