package com.vie.service.property;

import com.vie.db.entity.SellerAccount;
import com.vie.db.mapper.SellerAccountMapper;
import com.vie.db.mapper.TransactionRecordMapper;
import com.vie.service.exception.BusinessException;
import com.vie.service.service.impl.SellerAccountServiceImpl;
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
 * 商家账户服务属性测试
 * 验证提现余额验证
 */
public class SellerAccountPropertyTest {

    @Mock
    private SellerAccountMapper sellerAccountMapper;

    @Mock
    private TransactionRecordMapper transactionRecordMapper;

    @InjectMocks
    private SellerAccountServiceImpl sellerAccountService;

    @BeforeProperty
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Mockito.reset(sellerAccountMapper, transactionRecordMapper);
    }

    /**
     * Property 5: 提现余额验证
     * 对于任何提现金额超过可用余额的请求，系统应拒绝并保持余额不变
     */
    @Property
    void withdrawExceedingBalanceShouldBeRejected(
            @ForAll("withdrawExceedScenarios") WithdrawScenario scenario) {
        // Given
        Long sellerId = 1L;
        
        SellerAccount account = createSellerAccount(1L, sellerId, scenario.balance);
        
        when(sellerAccountMapper.selectBySellerId(sellerId)).thenReturn(account);

        // When & Then
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            sellerAccountService.withdraw(sellerId, scenario.withdrawAmount);
        });
        
        assertEquals(2002, exception.getCode());
        assertEquals("余额不足", exception.getMessage());
        
        // 验证余额扣减方法未被调用
        verify(sellerAccountMapper, never()).decreaseBalance(any(), any());
    }

    @Provide
    Arbitrary<WithdrawScenario> withdrawExceedScenarios() {
        return Arbitraries.of(
                // 提现金额超过余额
                new WithdrawScenario(new BigDecimal("100.00"), new BigDecimal("100.01")),
                new WithdrawScenario(new BigDecimal("100.00"), new BigDecimal("200.00")),
                new WithdrawScenario(new BigDecimal("0.00"), new BigDecimal("0.01")),
                new WithdrawScenario(new BigDecimal("50.50"), new BigDecimal("50.51")),
                new WithdrawScenario(new BigDecimal("999.99"), new BigDecimal("1000.00"))
        );
    }

    /**
     * Property: 提现金额无效验证
     * 对于任何非正数提现金额，系统应拒绝请求
     */
    @Property
    void withdrawWithNonPositiveAmountShouldBeRejected(
            @ForAll("nonPositiveAmounts") BigDecimal amount) {
        // Given
        Long sellerId = 1L;
        
        SellerAccount account = createSellerAccount(1L, sellerId, new BigDecimal("1000.00"));
        
        when(sellerAccountMapper.selectBySellerId(sellerId)).thenReturn(account);

        // When & Then
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            sellerAccountService.withdraw(sellerId, amount);
        });
        
        assertEquals(2004, exception.getCode());
        assertEquals("提现金额无效", exception.getMessage());
    }

    @Provide
    Arbitrary<BigDecimal> nonPositiveAmounts() {
        return Arbitraries.oneOf(
                Arbitraries.just(BigDecimal.ZERO),
                Arbitraries.just(new BigDecimal("-0.01")),
                Arbitraries.just(new BigDecimal("-100.00")),
                Arbitraries.bigDecimals()
                        .lessThan(BigDecimal.ZERO)
                        .ofScale(2)
        );
    }

    /**
     * Property: 有效提现应正确扣减余额
     * 对于任何有效的提现金额（正数且不超过余额），余额应正确扣减
     */
    @Property
    void validWithdrawShouldDecreaseBalanceCorrectly(
            @ForAll("validWithdrawScenarios") WithdrawScenario scenario) {
        // Given
        Long sellerId = 1L;
        
        SellerAccount account = createSellerAccount(1L, sellerId, scenario.balance);
        SellerAccount updatedAccount = createSellerAccount(1L, sellerId, 
                scenario.balance.subtract(scenario.withdrawAmount));
        updatedAccount.setTotalWithdraw(scenario.withdrawAmount);
        
        when(sellerAccountMapper.selectBySellerId(sellerId))
                .thenReturn(account)
                .thenReturn(updatedAccount);
        when(sellerAccountMapper.decreaseBalance(eq(sellerId), eq(scenario.withdrawAmount))).thenReturn(1);
        when(sellerAccountMapper.increaseTotalWithdraw(eq(sellerId), eq(scenario.withdrawAmount))).thenReturn(1);
        when(transactionRecordMapper.insert(any())).thenReturn(1);

        // When
        sellerAccountService.withdraw(sellerId, scenario.withdrawAmount);

        // Then
        verify(sellerAccountMapper, atLeastOnce()).decreaseBalance(sellerId, scenario.withdrawAmount);
        verify(sellerAccountMapper, atLeastOnce()).increaseTotalWithdraw(sellerId, scenario.withdrawAmount);
        verify(transactionRecordMapper, atLeastOnce()).insert(any());
    }

    @Provide
    Arbitrary<WithdrawScenario> validWithdrawScenarios() {
        return Arbitraries.of(
                // 有效提现场景
                new WithdrawScenario(new BigDecimal("100.00"), new BigDecimal("50.00")),
                new WithdrawScenario(new BigDecimal("100.00"), new BigDecimal("100.00")),
                new WithdrawScenario(new BigDecimal("100.00"), new BigDecimal("0.01")),
                new WithdrawScenario(new BigDecimal("1000.00"), new BigDecimal("999.99")),
                new WithdrawScenario(new BigDecimal("0.01"), new BigDecimal("0.01"))
        );
    }

    // ========== 辅助方法 ==========

    private SellerAccount createSellerAccount(Long id, Long sellerId, BigDecimal balance) {
        SellerAccount account = new SellerAccount();
        account.setId(id);
        account.setSellerId(sellerId);
        account.setBalance(balance);
        account.setFrozenAmount(BigDecimal.ZERO);
        account.setTotalIncome(new BigDecimal("1000.00"));
        account.setTotalWithdraw(BigDecimal.ZERO);
        return account;
    }

    record WithdrawScenario(BigDecimal balance, BigDecimal withdrawAmount) {}
}
