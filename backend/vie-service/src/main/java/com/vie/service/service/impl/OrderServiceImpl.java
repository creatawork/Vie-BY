package com.vie.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vie.db.entity.*;
import com.vie.db.mapper.*;
import com.vie.service.dto.OrderCreateDTO;
import com.vie.service.dto.OrderQueryDTO;
import com.vie.service.exception.BusinessException;
import com.vie.service.service.OrderService;
import com.vie.service.service.PaymentService;
import com.vie.service.vo.OrderItemVO;
import com.vie.service.vo.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 订单服务实现类
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private CartItemMapper cartItemMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Lazy
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ProductReviewMapper productReviewMapper;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // 订单状态描述
    private static final Map<Integer, String> STATUS_DESC_MAP = Map.of(
            0, "待支付",
            1, "待发货",
            2, "待收货",
            3, "已完成",
            4, "已取消",
            5, "已关闭"
    );

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createOrder(OrderCreateDTO dto, Long userId) {
        List<OrderItemInfo> orderItemInfos;

        // 判断是从购物车下单还是直接购买
        if (dto.getCartItemIds() != null && !dto.getCartItemIds().isEmpty()) {
            // 从购物车下单
            orderItemInfos = buildOrderItemsFromCart(dto.getCartItemIds(), userId);
        } else if (dto.getDirectBuyItem() != null) {
            // 直接购买
            orderItemInfos = buildOrderItemsFromDirectBuy(dto.getDirectBuyItem());
        } else {
            throw new BusinessException(400, "请选择要购买的商品");
        }

        if (orderItemInfos.isEmpty()) {
            throw new BusinessException(400, "没有可购买的商品");
        }

        // 生成订单编号
        String orderNo = generateOrderNo();

        // 计算订单金额
        BigDecimal totalAmount = orderItemInfos.stream()
                .map(OrderItemInfo::totalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal freightAmount = BigDecimal.ZERO; // 暂不计算运费
        BigDecimal payAmount = totalAmount.add(freightAmount);

        // 创建订单
        Order order = Order.builder()
                .orderNo(orderNo)
                .userId(userId)
                .totalAmount(totalAmount)
                .payAmount(payAmount)
                .freightAmount(freightAmount)
                .status(0) // 待支付
                .receiverName(dto.getReceiverName())
                .receiverPhone(dto.getReceiverPhone())
                .receiverProvince(dto.getReceiverProvince())
                .receiverCity(dto.getReceiverCity())
                .receiverDistrict(dto.getReceiverDistrict())
                .receiverAddress(dto.getReceiverAddress())
                .remark(dto.getRemark())
                .build();
        orderMapper.insert(order);

        // 创建订单明细
        List<OrderItem> orderItems = orderItemInfos.stream()
                .map(info -> OrderItem.builder()
                        .orderId(order.getId())
                        .orderNo(orderNo)
                        .productId(info.productId())
                        .skuId(info.skuId())
                        .productName(info.productName())
                        .skuName(info.skuName())
                        .productImage(info.productImage())
                        .price(info.price())
                        .quantity(info.quantity())
                        .totalPrice(info.totalPrice())
                        .build())
                .collect(Collectors.toList());
        orderItemMapper.batchInsert(orderItems);

        // 扣减库存
        for (OrderItemInfo info : orderItemInfos) {
            int rows = productSkuMapper.decreaseStock(info.skuId(), info.quantity());
            if (rows == 0) {
                throw new BusinessException(400, "商品 " + info.productName() + " 库存不足");
            }
        }

        // 如果是从购物车下单，删除购物车商品
        if (dto.getCartItemIds() != null && !dto.getCartItemIds().isEmpty()) {
            cartItemMapper.batchDelete(userId, dto.getCartItemIds());
        }

        log.info("用户 {} 创建订单成功，订单号: {}", userId, orderNo);
        return orderNo;
    }

    @Override
    public IPage<OrderVO> getOrderPage(OrderQueryDTO queryDTO, Long userId) {
        Page<Map<String, Object>> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        IPage<Map<String, Object>> orderPage = orderMapper.selectOrderPageByUserId(page, userId, queryDTO.getStatus());

        return orderPage.convert(map -> {
            OrderVO vo = buildOrderVO(map);
            Long odId = vo.getId();
            Long uid = userId;
            // 查询订单明细
            List<OrderItem> items = orderItemMapper.selectByOrderId(odId);
            // 查询已评价的商品ID列表（仅已完成订单需要）
            List<Long> reviewedProductIds = (vo.getStatus() != null && vo.getStatus() == 3)
                    ? productReviewMapper.selectReviewedProductIdsByOrderId(odId, uid)
                    : Collections.emptyList();
            vo.setOrderItems(items.stream().map(item -> buildOrderItemVO(item, reviewedProductIds)).collect(Collectors.toList()));
            vo.setTotalQuantity(items.stream().mapToInt(OrderItem::getQuantity).sum());
            // 设置订单评价状态
            if (vo.getStatus() != null && vo.getStatus() == 3) {
                // 按商品种类数判断是否全部评价（去重productId）
                long distinctProductCount = items.stream().map(OrderItem::getProductId).distinct().count();
                vo.setReviewCount(reviewedProductIds.size());
                vo.setReviewed(reviewedProductIds.size() >= distinctProductCount);
            } else {
                vo.setReviewed(false);
                vo.setReviewCount(0);
            }
            return vo;
        });
    }

    @Override
    public OrderVO getOrderDetail(Long orderId, Long userId) {
        Order order = getOrderByIdAndUserId(orderId, userId);

        OrderVO vo = new OrderVO();
        vo.setId(order.getId());
        vo.setOrderNo(order.getOrderNo());
        vo.setTotalAmount(order.getTotalAmount());
        vo.setPayAmount(order.getPayAmount());
        vo.setFreightAmount(order.getFreightAmount());
        vo.setStatus(order.getStatus());
        vo.setStatusDesc(STATUS_DESC_MAP.getOrDefault(order.getStatus(), "未知"));
        vo.setReceiverName(order.getReceiverName());
        vo.setReceiverPhone(order.getReceiverPhone());
        vo.setReceiverProvince(order.getReceiverProvince());
        vo.setReceiverCity(order.getReceiverCity());
        vo.setReceiverDistrict(order.getReceiverDistrict());
        vo.setReceiverAddress(order.getReceiverAddress());
        vo.setRemark(order.getRemark());
        vo.setCancelReason(order.getCancelReason());
        vo.setCreateTime(formatDateTime(order.getCreateTime()));
        vo.setPayTime(formatDateTime(order.getPayTime()));
        vo.setDeliverTime(formatDateTime(order.getDeliverTime()));
        vo.setReceiveTime(formatDateTime(order.getReceiveTime()));
        vo.setCancelTime(formatDateTime(order.getCancelTime()));
        // 物流相关字段
        vo.setLogisticsNo(order.getLogisticsNo());
        vo.setLogisticsCompany(order.getLogisticsCompany());
        vo.setDelivered(order.getDelivered() != null && order.getDelivered() == 1);
        vo.setAutoReceiveTime(formatDateTime(order.getAutoReceiveTime()));

        // 查询订单明细
        List<OrderItem> items = orderItemMapper.selectByOrderId(orderId);
        // 查询已评价的商品ID列表（仅已完成订单需要）
        List<Long> reviewedProductIds = (order.getStatus() == 3)
                ? productReviewMapper.selectReviewedProductIdsByOrderId(orderId, userId)
                : Collections.emptyList();
        vo.setOrderItems(items.stream().map(item -> buildOrderItemVO(item, reviewedProductIds)).collect(Collectors.toList()));
        vo.setTotalQuantity(items.stream().mapToInt(OrderItem::getQuantity).sum());
        // 设置订单评价状态
        if (order.getStatus() == 3) {
            long distinctProductCount = items.stream().map(OrderItem::getProductId).distinct().count();
            vo.setReviewCount(reviewedProductIds.size());
            vo.setReviewed(reviewedProductIds.size() >= distinctProductCount);
        } else {
            vo.setReviewed(false);
            vo.setReviewCount(0);
        }

        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelOrder(Long orderId, String reason, Long userId) {
        Order order = getOrderByIdAndUserId(orderId, userId);

        // 只有待支付(0)和待发货(1)的订单可以取消
        if (order.getStatus() != 0 && order.getStatus() != 1) {
            throw new BusinessException(400, "当前订单状态不可取消");
        }

        // 如果是已支付订单（待发货状态），需要退款
        if (order.getStatus() == 1) {
            paymentService.refundOrder(orderId);
        }

        // 更新订单状态
        order.setStatus(4); // 已取消
        order.setCancelTime(LocalDateTime.now());
        order.setCancelReason(reason);
        orderMapper.updateById(order);

        // 恢复库存
        List<OrderItem> items = orderItemMapper.selectByOrderId(orderId);
        for (OrderItem item : items) {
            productSkuMapper.increaseStock(item.getSkuId(), item.getQuantity());
        }

        log.info("用户 {} 取消订单，订单ID: {}", userId, orderId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmReceive(Long orderId, Long userId) {
        Order order = getOrderByIdAndUserId(orderId, userId);

        // 只有待收货的订单可以确认收货
        if (order.getStatus() != 2) {
            throw new BusinessException(400, "当前订单状态不可确认收货");
        }

        // 调用支付服务完成结算（冻结金额转为商家可用余额）
        paymentService.settleOrder(orderId);

        order.setStatus(3); // 已完成
        order.setReceiveTime(LocalDateTime.now());
        orderMapper.updateById(order);

        log.info("用户 {} 确认收货，订单ID: {}", userId, orderId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOrder(Long orderId, Long userId) {
        Order order = getOrderByIdAndUserId(orderId, userId);

        // 只有已完成、已取消、已关闭的订单可以删除
        if (order.getStatus() != 3 && order.getStatus() != 4 && order.getStatus() != 5) {
            throw new BusinessException(400, "当前订单状态不可删除");
        }

        // 使用 MyBatis-Plus 的 deleteById 进行逻辑删除
        // 由于 BaseEntity 中 isDeleted 字段配置了 @TableLogic 注解
        // updateById 不会更新 isDeleted 字段，需要使用 deleteById 触发逻辑删除
        orderMapper.deleteById(orderId);

        log.info("用户 {} 删除订单，订单ID: {}", userId, orderId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void mockPay(Long orderId, Long userId) {
        // 调用支付服务完成支付
        paymentService.payOrder(orderId, userId);
        log.info("用户 {} 支付成功，订单ID: {}", userId, orderId);
    }

    @Override
    public OrderCountVO getOrderCount(Long userId) {
        LambdaQueryWrapper<Order> baseWrapper = new LambdaQueryWrapper<Order>()
                .eq(Order::getUserId, userId)
                .eq(Order::getIsDeleted, 0);

        int unpaid = Math.toIntExact(orderMapper.selectCount(
                new LambdaQueryWrapper<Order>().eq(Order::getUserId, userId)
                        .eq(Order::getIsDeleted, 0).eq(Order::getStatus, 0)));
        int unshipped = Math.toIntExact(orderMapper.selectCount(
                new LambdaQueryWrapper<Order>().eq(Order::getUserId, userId)
                        .eq(Order::getIsDeleted, 0).eq(Order::getStatus, 1)));
        int shipped = Math.toIntExact(orderMapper.selectCount(
                new LambdaQueryWrapper<Order>().eq(Order::getUserId, userId)
                        .eq(Order::getIsDeleted, 0).eq(Order::getStatus, 2)));
        int completed = Math.toIntExact(orderMapper.selectCount(
                new LambdaQueryWrapper<Order>().eq(Order::getUserId, userId)
                        .eq(Order::getIsDeleted, 0).eq(Order::getStatus, 3)));
        int total = Math.toIntExact(orderMapper.selectCount(baseWrapper));

        return new OrderCountVO(unpaid, unshipped, shipped, completed, total);
    }

    // ========== 私有方法 ==========

    /**
     * 从购物车构建订单项信息
     */
    private List<OrderItemInfo> buildOrderItemsFromCart(List<Long> cartItemIds, Long userId) {
        List<OrderItemInfo> result = new ArrayList<>();

        for (Long cartItemId : cartItemIds) {
            CartItem cartItem = cartItemMapper.selectById(cartItemId);
            if (cartItem == null || cartItem.getIsDeleted() == 1) {
                continue;
            }
            if (!cartItem.getUserId().equals(userId)) {
                throw new BusinessException(403, "无权操作");
            }

            OrderItemInfo info = validateAndBuildOrderItemInfo(
                    cartItem.getProductId(), cartItem.getSkuId(), cartItem.getQuantity());
            result.add(info);
        }

        return result;
    }

    /**
     * 从直接购买构建订单项信息
     */
    private List<OrderItemInfo> buildOrderItemsFromDirectBuy(OrderCreateDTO.DirectBuyItem directBuyItem) {
        OrderItemInfo info = validateAndBuildOrderItemInfo(
                directBuyItem.getProductId(), directBuyItem.getSkuId(), directBuyItem.getQuantity());
        return List.of(info);
    }

    /**
     * 校验并构建订单项信息
     */
    private OrderItemInfo validateAndBuildOrderItemInfo(Long productId, Long skuId, Integer quantity) {
        // 校验商品
        Product product = productMapper.selectById(productId);
        if (product == null || product.getIsDeleted() == 1) {
            throw new BusinessException(404, "商品不存在");
        }
        if (product.getStatus() != 1) {
            throw new BusinessException(400, "商品 " + product.getProductName() + " 已下架");
        }

        // 校验SKU
        ProductSku sku = productSkuMapper.selectById(skuId);
        if (sku == null || sku.getIsDeleted() == 1) {
            throw new BusinessException(404, "商品规格不存在");
        }
        if (sku.getStatus() != 1) {
            throw new BusinessException(400, "商品规格已下架");
        }
        if (sku.getStock() < quantity) {
            throw new BusinessException(400, "商品 " + product.getProductName() + " 库存不足");
        }

        BigDecimal totalPrice = sku.getPrice().multiply(BigDecimal.valueOf(quantity));

        return new OrderItemInfo(
                productId, skuId, product.getProductName(), sku.getSkuName(),
                product.getMainImage(), sku.getPrice(), quantity, totalPrice
        );
    }

    /**
     * 生成订单编号
     */
    private String generateOrderNo() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String random = String.format("%04d", new Random().nextInt(10000));
        return timestamp + random;
    }

    /**
     * 根据ID和用户ID获取订单
     */
    private Order getOrderByIdAndUserId(Long orderId, Long userId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null || order.getIsDeleted() == 1) {
            throw new BusinessException(404, "订单不存在");
        }
        if (!order.getUserId().equals(userId)) {
            throw new BusinessException(403, "无权操作");
        }
        return order;
    }

    /**
     * 构建订单VO
     */
    private OrderVO buildOrderVO(Map<String, Object> map) {
        Integer deliveredValue = getIntegerValue(map, "delivered");
        return OrderVO.builder()
                .id(getLongValue(map, "id"))
                .orderNo((String) map.get("order_no"))
                .totalAmount(getBigDecimalValue(map, "total_amount"))
                .payAmount(getBigDecimalValue(map, "pay_amount"))
                .freightAmount(getBigDecimalValue(map, "freight_amount"))
                .status(getIntegerValue(map, "status"))
                .statusDesc(STATUS_DESC_MAP.getOrDefault(getIntegerValue(map, "status"), "未知"))
                .receiverName((String) map.get("receiver_name"))
                .receiverPhone((String) map.get("receiver_phone"))
                .receiverAddress((String) map.get("receiver_address"))
                .receiverProvince((String) map.get("receiver_province"))
                .receiverCity((String) map.get("receiver_city"))
                .receiverDistrict((String) map.get("receiver_district"))
                .remark((String) map.get("remark"))
                .cancelReason((String) map.get("cancel_reason"))
                .createTime(formatDateTime(map.get("create_time")))
                .payTime(formatDateTime(map.get("pay_time")))
                .deliverTime(formatDateTime(map.get("deliver_time")))
                .receiveTime(formatDateTime(map.get("receive_time")))
                .cancelTime(formatDateTime(map.get("cancel_time")))
                // 物流相关字段
                .logisticsNo((String) map.get("logistics_no"))
                .logisticsCompany((String) map.get("logistics_company"))
                .delivered(deliveredValue != null && deliveredValue == 1)
                .autoReceiveTime(formatDateTime(map.get("auto_receive_time")))
                .build();
    }

    /**
     * 构建订单明细VO（含评价状态）
     */
    private OrderItemVO buildOrderItemVO(OrderItem item, List<Long> reviewedProductIds) {
        return OrderItemVO.builder()
                .id(item.getId())
                .productId(item.getProductId())
                .skuId(item.getSkuId())
                .productName(item.getProductName())
                .skuName(item.getSkuName())
                .productImage(item.getProductImage())
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .totalPrice(item.getTotalPrice())
                .reviewed(reviewedProductIds.contains(item.getProductId()))
                .build();
    }

    private String formatDateTime(Object dateTime) {
        if (dateTime == null) return null;
        if (dateTime instanceof LocalDateTime) {
            return ((LocalDateTime) dateTime).format(FORMATTER);
        }
        return dateTime.toString();
    }

    private Long getLongValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) return null;
        if (value instanceof Long) return (Long) value;
        if (value instanceof Integer) return ((Integer) value).longValue();
        return Long.parseLong(value.toString());
    }

    private Integer getIntegerValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) return null;
        if (value instanceof Integer) return (Integer) value;
        if (value instanceof Long) return ((Long) value).intValue();
        return Integer.parseInt(value.toString());
    }

    private BigDecimal getBigDecimalValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) return null;
        if (value instanceof BigDecimal) return (BigDecimal) value;
        return new BigDecimal(value.toString());
    }

    /**
     * 订单项信息记录
     */
    private record OrderItemInfo(
            Long productId,
            Long skuId,
            String productName,
            String skuName,
            String productImage,
            BigDecimal price,
            Integer quantity,
            BigDecimal totalPrice
    ) {}
}
