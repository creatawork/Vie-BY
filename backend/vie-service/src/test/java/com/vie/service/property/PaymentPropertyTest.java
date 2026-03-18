package com.vie.service.property;

import com.vie.db.entity.Order;
import com.vie.db.entity.OrderItem;
import com.vie.db.entity.Product;
import com.vie.db.mapper.OrderItemMapper;
import com.vie.db.mapper.OrderMapper;
import com.vie.db.mapper.ProductMapper;
import com.vie.service.exception.BusinessException;
import com.vie.service.service.SellerAccountService;
import com.vie.service.service.WalletService;
import com.vie.service.service.impl.PaymentServiceImpl;
import net.jqwik.api.*;
import net.jqwik.api.lifecycle.BeforeProperty;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import org.mockito.Mockito;

/**
 * 支付服务属性测试
 * 验证支付余额验证和资金流转一致性
 */
public class PaymentPropertyTest {

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private OrderItemMapper orderItemMapper;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private WalletService walletService;

    @Mock
    private SellerAccountService sellerAccountService;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @BeforeProperty
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Mockito.reset(orderMapper, orderItemMapper, productMapper, walletService, sellerAccountService);
    }

    /**
     * Property 6: 支付余额验证
     * 当订单金额超过钱包余额时，系统应拒绝支付并返回"余额不足"错误
     */
    @Property
    void payOrderWithInsufficientBalanceShouldBeRejected(
            @ForAll("insufficientBalanceScenarios") PaymentScenario scenario) {
        // Given
        Long orderId = 1L;
        Long userId = 1L;
        
        Order order = createOrder(orderId, userId, scenario.orderAmount, 0);
        
        when(orderMapper.selectById(orderId)).thenReturn(order);
        when(walletService.checkBalance(userId, scenario.orderAmount)).thenReturn(false);

        // When & Then
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            paymentService.payOrder(orderId, userId);
        });
        
        assertEquals(2002, exception.getCode());
        assertEquals("余额不足", exception.getMessage());
        
        // 验证扣款方法未被调用
        verify(walletService, never()).deduct(any(), any(), any());
        // 验证商家冻结方法未被调用
        verify(sellerAccountService, never()).freeze(any(), any(), any());
    }

    @Provide
    Arbitrary<PaymentScenario> insufficientBalanceScenarios() {
        return Arbitraries.of(
                new PaymentScenario(new BigDecimal("100.00")),
                new PaymentScenario(new BigDecimal("500.00")),
                new PaymentScenario(new BigDecimal("1000.00")),
                new PaymentScenario(new BigDecimal("0.01")),
                new PaymentScenario(new BigDecimal("9999.99"))
        );
    }

    /**
     * Property 7: 支付资金流转一致性
     * 对于任何成功的支付金额X，用户钱包余额应减少X，商家冻结金额应增加X
     */
    @Property
    void successfulPaymentShouldMaintainFundConsistency(
            @ForAll("validPaymentScenarios") PaymentScenario scenario) {
        // Given
        Long orderId = 1L;
        Long userId = 1L;
        Long sellerId = 2L;
        String orderNo = "TEST" + System.currentTimeMillis();
        
        Order order = createOrder(orderId, userId, scenario.orderAmount, 0);
        order.setOrderNo(orderNo);
        
        OrderItem orderItem = createOrderItem(1L, orderId, 1L, scenario.orderAmount);
        Product product = createProduct(1L, sellerId);
        
        when(orderMapper.selectById(orderId)).thenReturn(order);
        when(walletService.checkBalance(userId, scenario.orderAmount)).thenReturn(true);
        when(orderItemMapper.selectByOrderId(orderId)).thenReturn(List.of(orderItem));
        when(productMapper.selectById(1L)).thenReturn(product);
        when(orderMapper.updateById(any())).thenReturn(1);

        // When
        paymentService.payOrder(orderId, userId);

        // Then
        // 验证用户钱包扣款金额正确
        verify(walletService, atLeastOnce()).deduct(eq(userId), eq(scenario.orderAmount), eq(orderNo));
        // 验证商家账户冻结金额正确
        verify(sellerAccountService, atLeastOnce()).freeze(eq(sellerId), eq(scenario.orderAmount), eq(orderNo));
        // 验证订单状态更新
        verify(orderMapper, atLeastOnce()).updateById(any());
    }

    @Provide
    Arbitrary<PaymentScenario> validPaymentScenarios() {
        return Arbitraries.of(
                new PaymentScenario(new BigDecimal("10.00")),
                new PaymentScenario(new BigDecimal("100.00")),
                new PaymentScenario(new BigDecimal("500.50")),
                new PaymentScenario(new BigDecimal("0.01")),
                new PaymentScenario(new BigDecimal("9999.99"))
        );
    }

    /**
     * Property 8: 结算资金流转一致性
     * 对于任何订单结算金额X，商家冻结金额应减少X，可用余额应增加X
     */
    @Property
    void settlementShouldTransferFrozenToAvailable(
            @ForAll("settlementScenarios") PaymentScenario scenario) {
        // Given
        Long orderId = 1L;
        Long sellerId = 2L;
        String orderNo = "TEST" + System.currentTimeMillis();
        
        Order order = createOrder(orderId, 1L, scenario.orderAmount, 2);
        order.setOrderNo(orderNo);
        
        OrderItem orderItem = createOrderItem(1L, orderId, 1L, scenario.orderAmount);
        Product product = createProduct(1L, sellerId);
        
        when(orderMapper.selectById(orderId)).thenReturn(order);
        when(orderItemMapper.selectByOrderId(orderId)).thenReturn(List.of(orderItem));
        when(productMapper.selectById(1L)).thenReturn(product);

        // When
        paymentService.settleOrder(orderId);

        // Then
        // 验证商家账户结算方法被调用，金额正确
        verify(sellerAccountService, atLeastOnce()).settle(eq(sellerId), eq(scenario.orderAmount), eq(orderNo));
    }

    @Provide
    Arbitrary<PaymentScenario> settlementScenarios() {
        return Arbitraries.of(
                new PaymentScenario(new BigDecimal("10.00")),
                new PaymentScenario(new BigDecimal("100.00")),
                new PaymentScenario(new BigDecimal("500.50")),
                new PaymentScenario(new BigDecimal("0.01"))
        );
    }

    /**
     * Property 9: 退款资金流转一致性
     * 对于任何退款金额X，用户钱包余额应增加X，商家冻结金额应减少X
     */
    @Property
    void refundShouldMaintainFundConsistency(
            @ForAll("refundScenarios") PaymentScenario scenario) {
        // Given
        Long orderId = 1L;
        Long userId = 1L;
        Long sellerId = 2L;
        String orderNo = "TEST" + System.currentTimeMillis();
        
        Order order = createOrder(orderId, userId, scenario.orderAmount, 1);
        order.setOrderNo(orderNo);
        
        OrderItem orderItem = createOrderItem(1L, orderId, 1L, scenario.orderAmount);
        Product product = createProduct(1L, sellerId);
        
        when(orderMapper.selectById(orderId)).thenReturn(order);
        when(orderItemMapper.selectByOrderId(orderId)).thenReturn(List.of(orderItem));
        when(productMapper.selectById(1L)).thenReturn(product);

        // When
        paymentService.refundOrder(orderId);

        // Then
        // 验证用户钱包退款金额正确
        verify(walletService, atLeastOnce()).refund(eq(userId), eq(scenario.orderAmount), eq(orderNo));
        // 验证商家账户解冻金额正确
        verify(sellerAccountService, atLeastOnce()).unfreeze(eq(sellerId), eq(scenario.orderAmount), eq(orderNo));
    }

    @Provide
    Arbitrary<PaymentScenario> refundScenarios() {
        return Arbitraries.of(
                new PaymentScenario(new BigDecimal("10.00")),
                new PaymentScenario(new BigDecimal("100.00")),
                new PaymentScenario(new BigDecimal("500.50")),
                new PaymentScenario(new BigDecimal("0.01"))
        );
    }

    // ========== 辅助方法 ==========

    private Order createOrder(Long id, Long userId, BigDecimal payAmount, Integer status) {
        Order order = new Order();
        order.setId(id);
        order.setUserId(userId);
        order.setOrderNo("TEST" + System.currentTimeMillis());
        order.setPayAmount(payAmount);
        order.setStatus(status);
        order.setIsDeleted(0);
        return order;
    }

    private OrderItem createOrderItem(Long id, Long orderId, Long productId, BigDecimal totalPrice) {
        OrderItem item = new OrderItem();
        item.setId(id);
        item.setOrderId(orderId);
        item.setProductId(productId);
        item.setTotalPrice(totalPrice);
        return item;
    }

    private Product createProduct(Long id, Long sellerId) {
        Product product = new Product();
        product.setId(id);
        product.setSellerId(sellerId);
        return product;
    }

    record PaymentScenario(BigDecimal orderAmount) {}
}
