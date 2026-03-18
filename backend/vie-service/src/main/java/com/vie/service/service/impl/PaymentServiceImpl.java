package com.vie.service.service.impl;

import com.vie.db.entity.Order;
import com.vie.db.entity.OrderItem;
import com.vie.db.entity.Product;
import com.vie.db.mapper.OrderItemMapper;
import com.vie.db.mapper.OrderMapper;
import com.vie.db.mapper.ProductMapper;
import com.vie.service.exception.BusinessException;
import com.vie.service.service.PaymentService;
import com.vie.service.service.SellerAccountService;
import com.vie.service.service.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 支付服务实现类
 */
@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private WalletService walletService;

    @Autowired
    private SellerAccountService sellerAccountService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void payOrder(Long orderId, Long userId) {
        // 1. 查询订单
        Order order = orderMapper.selectById(orderId);
        if (order == null || order.getIsDeleted() == 1) {
            throw new BusinessException(404, "订单不存在");
        }
        if (!order.getUserId().equals(userId)) {
            throw new BusinessException(403, "无权操作");
        }
        if (order.getStatus() != 0) {
            throw new BusinessException(2006, "订单已支付或状态不可支付");
        }

        BigDecimal payAmount = order.getPayAmount();
        String orderNo = order.getOrderNo();

        // 2. 检查用户余额
        if (!walletService.checkBalance(userId, payAmount)) {
            throw new BusinessException(2002, "余额不足");
        }

        // 3. 扣减用户钱包余额
        walletService.deduct(userId, payAmount, orderNo);

        // 4. 获取订单商品对应的商家，并按商家分组金额
        Map<Long, BigDecimal> sellerAmountMap = getSellerAmountMap(orderId);

        // 5. 为每个商家冻结对应金额
        for (Map.Entry<Long, BigDecimal> entry : sellerAmountMap.entrySet()) {
            Long sellerId = entry.getKey();
            BigDecimal amount = entry.getValue();
            sellerAccountService.freeze(sellerId, amount, orderNo);
        }

        // 6. 更新订单状态为待发货
        order.setStatus(1);
        order.setPayTime(LocalDateTime.now());
        orderMapper.updateById(order);

        log.info("订单 {} 支付成功，金额: {}", orderNo, payAmount);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refundOrder(Long orderId) {
        // 1. 查询订单
        Order order = orderMapper.selectById(orderId);
        if (order == null || order.getIsDeleted() == 1) {
            throw new BusinessException(404, "订单不存在");
        }

        // 只有待发货状态（已支付）的订单可以退款
        if (order.getStatus() != 1) {
            throw new BusinessException(2007, "订单状态不可退款");
        }

        BigDecimal payAmount = order.getPayAmount();
        String orderNo = order.getOrderNo();
        Long userId = order.getUserId();

        // 2. 退款到用户钱包
        walletService.refund(userId, payAmount, orderNo);

        // 3. 获取订单商品对应的商家，并按商家分组金额
        Map<Long, BigDecimal> sellerAmountMap = getSellerAmountMap(orderId);

        // 4. 为每个商家解冻对应金额
        for (Map.Entry<Long, BigDecimal> entry : sellerAmountMap.entrySet()) {
            Long sellerId = entry.getKey();
            BigDecimal amount = entry.getValue();
            sellerAccountService.unfreeze(sellerId, amount, orderNo);
        }

        log.info("订单 {} 退款成功，金额: {}", orderNo, payAmount);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void settleOrder(Long orderId) {
        // 1. 查询订单
        Order order = orderMapper.selectById(orderId);
        if (order == null || order.getIsDeleted() == 1) {
            throw new BusinessException(404, "订单不存在");
        }

        String orderNo = order.getOrderNo();

        // 2. 获取订单商品对应的商家，并按商家分组金额
        Map<Long, BigDecimal> sellerAmountMap = getSellerAmountMap(orderId);

        // 3. 为每个商家结算对应金额（冻结转可用）
        for (Map.Entry<Long, BigDecimal> entry : sellerAmountMap.entrySet()) {
            Long sellerId = entry.getKey();
            BigDecimal amount = entry.getValue();
            sellerAccountService.settle(sellerId, amount, orderNo);
        }

        log.info("订单 {} 结算成功", orderNo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void adminRecharge(Long userId, BigDecimal amount, Long adminId) {
        walletService.adminRecharge(userId, amount, adminId);
    }

    /**
     * 获取订单商品对应的商家金额映射
     * 按商家分组统计每个商家应得的金额
     */
    private Map<Long, BigDecimal> getSellerAmountMap(Long orderId) {
        Map<Long, BigDecimal> sellerAmountMap = new HashMap<>();

        // 查询订单明细
        List<OrderItem> orderItems = orderItemMapper.selectByOrderId(orderId);

        for (OrderItem item : orderItems) {
            // 通过商品ID获取商家ID
            Product product = productMapper.selectById(item.getProductId());
            if (product != null) {
                Long sellerId = product.getSellerId();
                BigDecimal itemAmount = item.getTotalPrice();

                // 累加该商家的金额
                sellerAmountMap.merge(sellerId, itemAmount, BigDecimal::add);
            }
        }

        return sellerAmountMap;
    }
}
