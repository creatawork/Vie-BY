package com.vie.service.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vie.db.entity.OrderItem;
import com.vie.db.mapper.OrderItemMapper;
import com.vie.db.mapper.OrderMapper;
import com.vie.service.dto.SellerOrderQueryDTO;
import com.vie.service.exception.BusinessException;
import com.vie.service.service.SellerOrderService;
import com.vie.service.vo.OrderItemVO;
import com.vie.service.vo.SellerOrderCountVO;
import com.vie.service.vo.SellerOrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商家订单服务实现类
 */
@Slf4j
@Service
public class SellerOrderServiceImpl implements SellerOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public IPage<SellerOrderVO> getSellerOrderPage(SellerOrderQueryDTO queryDTO, Long sellerId) {
        log.info("查询商家订单列表，sellerId: {}, queryDTO: {}", sellerId, queryDTO);

        // 创建分页对象
        Page<Map<String, Object>> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());

        // 查询订单列表
        IPage<Map<String, Object>> orderPage = orderMapper.selectSellerOrderPage(
                page,
                sellerId,
                queryDTO.getStatus(),
                queryDTO.getOrderNo(),
                queryDTO.getStartDate(),
                queryDTO.getEndDate(),
                queryDTO.getKeyword()
        );

        // 转换为VO
        IPage<SellerOrderVO> result = orderPage.convert(this::convertToSellerOrderVO);

        // 查询订单明细
        result.getRecords().forEach(orderVO -> {
            List<OrderItem> orderItems = orderItemMapper.selectByOrderId(orderVO.getId());
            List<OrderItemVO> itemVOs = orderItems.stream()
                    .map(this::convertToOrderItemVO)
                    .collect(Collectors.toList());
            orderVO.setItems(itemVOs);
        });

        return result;
    }

    @Override
    public SellerOrderVO getSellerOrderDetail(Long orderId, Long sellerId) {
        log.info("查询商家订单详情，orderId: {}, sellerId: {}", orderId, sellerId);

        // 查询订单基本信息
        Map<String, Object> orderMap = orderMapper.selectSellerOrderDetail(orderId, sellerId);
        if (orderMap == null || orderMap.isEmpty()) {
            throw new BusinessException("订单不存在或无权查看");
        }

        // 转换为VO
        SellerOrderVO orderVO = convertToSellerOrderVO(orderMap);

        // 查询订单明细
        List<OrderItem> orderItems = orderItemMapper.selectByOrderId(orderId);
        List<OrderItemVO> itemVOs = orderItems.stream()
                .map(this::convertToOrderItemVO)
                .collect(Collectors.toList());
        orderVO.setItems(itemVOs);

        // 计算商品数量
        int itemCount = orderItems.size();
        int totalQuantity = orderItems.stream().mapToInt(OrderItem::getQuantity).sum();
        orderVO.setItemCount(itemCount);
        orderVO.setTotalQuantity(totalQuantity);

        return orderVO;
    }

    @Override
    public SellerOrderCountVO getSellerOrderCount(Long sellerId) {
        log.info("统计商家订单数量，sellerId: {}", sellerId);

        Map<String, Integer> countMap = orderMapper.countSellerOrdersByStatus(sellerId);

        return SellerOrderCountVO.builder()
                .unpaid(countMap.getOrDefault("unpaid", 0))
                .unshipped(countMap.getOrDefault("unshipped", 0))
                .shipped(countMap.getOrDefault("shipped", 0))
                .completed(countMap.getOrDefault("completed", 0))
                .cancelled(countMap.getOrDefault("cancelled", 0))
                .total(countMap.getOrDefault("total", 0))
                .build();
    }

    /**
     * 转换为商家订单VO
     */
    private SellerOrderVO convertToSellerOrderVO(Map<String, Object> map) {
        return SellerOrderVO.builder()
                .id(getLongValue(map, "id"))
                .orderNo(getStringValue(map, "order_no"))
                .buyerId(getLongValue(map, "buyer_id"))
                .buyerNickname(getStringValue(map, "buyer_nickname"))
                .buyerPhone(getStringValue(map, "buyer_phone"))
                .totalAmount(getBigDecimalValue(map, "total_amount"))
                .payAmount(getBigDecimalValue(map, "pay_amount"))
                .status(getIntValue(map, "status"))
                .statusText(getStatusText(getIntValue(map, "status")))
                .receiverName(getStringValue(map, "receiver_name"))
                .receiverPhone(getStringValue(map, "receiver_phone"))
                .receiverAddress(getStringValue(map, "receiver_address"))
                .remark(getStringValue(map, "remark"))
                .payTime(formatDateTime(map.get("pay_time")))
                .deliverTime(formatDateTime(map.get("deliver_time")))
                .createTime(formatDateTime(map.get("create_time")))
                .itemCount(getIntValue(map, "item_count"))
                .totalQuantity(getIntValue(map, "total_quantity"))
                .logisticsNo(getStringValue(map, "logistics_no"))
                .logisticsCompany(getStringValue(map, "logistics_company"))
                .build();
    }

    /**
     * 转换为订单明细VO
     */
    private OrderItemVO convertToOrderItemVO(OrderItem item) {
        return OrderItemVO.builder()
                .id(item.getId())
                .orderId(item.getOrderId())
                .orderNo(item.getOrderNo())
                .productId(item.getProductId())
                .skuId(item.getSkuId())
                .productName(item.getProductName())
                .skuName(item.getSkuName())
                .productImage(item.getProductImage())
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .totalPrice(item.getTotalPrice())
                .build();
    }

    /**
     * 获取订单状态文本
     */
    private String getStatusText(Integer status) {
        if (status == null) {
            return "未知";
        }
        return switch (status) {
            case 0 -> "待支付";
            case 1 -> "待发货";
            case 2 -> "待收货";
            case 3 -> "已完成";
            case 4 -> "已取消";
            case 5 -> "已关闭";
            default -> "未知";
        };
    }

    /**
     * 格式化日期时间
     */
    private String formatDateTime(Object dateTime) {
        if (dateTime == null) {
            return null;
        }
        if (dateTime instanceof LocalDateTime) {
            return ((LocalDateTime) dateTime).format(DATE_TIME_FORMATTER);
        }
        return dateTime.toString();
    }

    /**
     * 从Map中获取Long值
     */
    private Long getLongValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        return Long.parseLong(value.toString());
    }

    /**
     * 从Map中获取Integer值
     */
    private Integer getIntValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) {
            return 0;
        }
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        return Integer.parseInt(value.toString());
    }

    /**
     * 从Map中获取String值
     */
    private String getStringValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        return value == null ? null : value.toString();
    }

    /**
     * 从Map中获取BigDecimal值
     */
    private BigDecimal getBigDecimalValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) {
            return BigDecimal.ZERO;
        }
        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }
        return new BigDecimal(value.toString());
    }
}
