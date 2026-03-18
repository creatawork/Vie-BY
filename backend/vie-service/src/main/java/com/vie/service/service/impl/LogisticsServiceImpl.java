package com.vie.service.service.impl;

import com.vie.db.entity.Order;
import com.vie.db.mapper.OrderMapper;
import com.vie.service.exception.BusinessException;
import com.vie.service.service.LogisticsService;
import com.vie.service.service.PaymentService;
import com.vie.service.vo.LogisticsTrackVO;
import com.vie.service.vo.LogisticsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 物流服务实现类
 */
@Slf4j
@Service
public class LogisticsServiceImpl implements LogisticsService {

    @Autowired
    private OrderMapper orderMapper;

    @Lazy
    @Autowired
    private PaymentService paymentService;

    @Value("${logistics.auto-deliver-minutes:2}")
    private int autoDeliverMinutes;

    @Value("${logistics.auto-receive-minutes:5}")
    private int autoReceiveMinutes;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final Map<Integer, String> STATUS_DESC_MAP = Map.of(
            0, "待支付",
            1, "待发货",
            2, "待收货",
            3, "已完成",
            4, "已取消",
            5, "已关闭"
    );

    private static final String[] LOGISTICS_COMPANIES = {
            "VIE速递", "顺丰速运", "圆通速递", "中通快递", "韵达快递"
    };

    @Override
    public LogisticsVO getLogisticsInfo(Long orderId, Long userId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null || order.getIsDeleted() == 1) {
            throw new BusinessException(404, "订单不存在");
        }
        if (!order.getUserId().equals(userId)) {
            throw new BusinessException(403, "无权查看");
        }

        // 构建物流信息
        LogisticsVO vo = LogisticsVO.builder()
                .orderId(order.getId())
                .orderNo(order.getOrderNo())
                .logisticsNo(order.getLogisticsNo())
                .logisticsCompany(order.getLogisticsCompany())
                .status(order.getStatus())
                .statusDesc(STATUS_DESC_MAP.getOrDefault(order.getStatus(), "未知"))
                .delivered(order.getDelivered() != null && order.getDelivered() == 1)
                .shipTime(formatDateTime(order.getDeliverTime()))
                .receiveTime(formatDateTime(order.getReceiveTime()))
                .autoReceiveTime(formatDateTime(order.getAutoReceiveTime()))
                .build();

        // 设置送达描述
        if (order.getStatus() == 3) {
            vo.setDeliveredDesc("已签收");
        } else if (order.getDelivered() != null && order.getDelivered() == 1) {
            vo.setDeliveredDesc("已送达，等待签收");
        } else if (order.getStatus() == 2) {
            vo.setDeliveredDesc("运输中");
        } else {
            vo.setDeliveredDesc(null);
        }

        // 生成物流轨迹
        vo.setTracks(generateTracks(order));

        return vo;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void autoShip(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null || order.getIsDeleted() == 1) {
            log.warn("自动发货失败，订单不存在: {}", orderId);
            return;
        }

        // 只处理待发货状态的订单
        if (order.getStatus() != 1) {
            log.warn("自动发货跳过，订单状态不是待发货: orderId={}, status={}", orderId, order.getStatus());
            return;
        }

        doShip(order);
        log.info("订单 {} 自动发货成功", order.getOrderNo());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void manualShip(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null || order.getIsDeleted() == 1) {
            throw new BusinessException(404, "订单不存在");
        }

        if (order.getStatus() != 1) {
            throw new BusinessException(400, "订单状态不是待发货");
        }

        doShip(order);
        log.info("订单 {} 手动发货成功", order.getOrderNo());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void manualDeliver(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null || order.getIsDeleted() == 1) {
            throw new BusinessException(404, "订单不存在");
        }

        if (order.getStatus() != 2) {
            throw new BusinessException(400, "订单状态不是待收货");
        }

        if (order.getDelivered() != null && order.getDelivered() == 1) {
            throw new BusinessException(400, "订单已送达");
        }

        order.setDelivered(1);
        orderMapper.updateById(order);
        log.info("订单 {} 手动标记送达成功", order.getOrderNo());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processPendingShipOrders() {
        List<Order> orders = orderMapper.selectPendingShipOrders();
        for (Order order : orders) {
            try {
                doShip(order);
                log.info("定时任务：订单 {} 自动发货成功", order.getOrderNo());
            } catch (Exception e) {
                log.error("定时任务：订单 {} 自动发货失败", order.getOrderNo(), e);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processUndeliveredOrders() {
        List<Order> orders = orderMapper.selectUndeliveredOrders(autoDeliverMinutes);
        for (Order order : orders) {
            try {
                order.setDelivered(1);
                orderMapper.updateById(order);
                log.info("定时任务：订单 {} 标记送达成功", order.getOrderNo());
            } catch (Exception e) {
                log.error("定时任务：订单 {} 标记送达失败", order.getOrderNo(), e);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processAutoReceiveOrders() {
        List<Order> orders = orderMapper.selectAutoReceiveOrders();
        for (Order order : orders) {
            try {
                // 调用结算逻辑
                paymentService.settleOrder(order.getId());

                // 更新订单状态
                order.setStatus(3); // 已完成
                order.setReceiveTime(LocalDateTime.now());
                orderMapper.updateById(order);

                log.info("定时任务：订单 {} 自动确认收货成功", order.getOrderNo());
            } catch (Exception e) {
                log.error("定时任务：订单 {} 自动确认收货失败", order.getOrderNo(), e);
            }
        }
    }

    /**
     * 执行发货逻辑
     */
    private void doShip(Order order) {
        // 生成物流单号
        String logisticsNo = generateLogisticsNo();
        // 随机选择物流公司
        String logisticsCompany = LOGISTICS_COMPANIES[new Random().nextInt(LOGISTICS_COMPANIES.length)];

        order.setLogisticsNo(logisticsNo);
        order.setLogisticsCompany(logisticsCompany);
        order.setStatus(2); // 待收货
        order.setDeliverTime(LocalDateTime.now());
        order.setDelivered(0);
        // 设置自动确认收货时间
        order.setAutoReceiveTime(LocalDateTime.now().plusMinutes(autoReceiveMinutes));

        orderMapper.updateById(order);
    }

    /**
     * 生成物流单号
     */
    private String generateLogisticsNo() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String random = String.format("%06d", new Random().nextInt(1000000));
        return "VIE" + timestamp + random;
    }

    /**
     * 动态生成物流轨迹
     */
    private List<LogisticsTrackVO> generateTracks(Order order) {
        List<LogisticsTrackVO> tracks = new ArrayList<>();

        // 未发货的订单没有轨迹
        if (order.getDeliverTime() == null) {
            return tracks;
        }

        LocalDateTime shipTime = order.getDeliverTime();
        LocalDateTime now = LocalDateTime.now();

        // 已发货
        tracks.add(LogisticsTrackVO.builder()
                .time(formatDateTime(shipTime))
                .status("已发货")
                .desc("商家已发货，包裹正在等待揽收")
                .build());

        // 运输中（发货后30秒）
        LocalDateTime transitTime = shipTime.plusSeconds(30);
        if (now.isAfter(transitTime)) {
            tracks.add(LogisticsTrackVO.builder()
                    .time(formatDateTime(transitTime))
                    .status("运输中")
                    .desc("包裹已被揽收，正在运输中")
                    .build());
        }

        // 派送中（发货后60秒）
        LocalDateTime deliveryTime = shipTime.plusSeconds(60);
        if (now.isAfter(deliveryTime)) {
            tracks.add(LogisticsTrackVO.builder()
                    .time(formatDateTime(deliveryTime))
                    .status("派送中")
                    .desc("快递员正在派送中，请保持电话畅通")
                    .build());
        }

        // 已送达
        if (order.getDelivered() != null && order.getDelivered() == 1) {
            LocalDateTime deliveredTime = shipTime.plusMinutes(autoDeliverMinutes);
            tracks.add(LogisticsTrackVO.builder()
                    .time(formatDateTime(deliveredTime))
                    .status("已送达")
                    .desc("您的包裹已送达，请尽快签收")
                    .build());
        }

        // 已签收
        if (order.getStatus() == 3 && order.getReceiveTime() != null) {
            tracks.add(LogisticsTrackVO.builder()
                    .time(formatDateTime(order.getReceiveTime()))
                    .status("已签收")
                    .desc("您已确认收货，感谢您的购买")
                    .build());
        }

        // 按时间倒序排列
        tracks.sort((a, b) -> b.getTime().compareTo(a.getTime()));
        return tracks;
    }

    private String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.format(FORMATTER);
    }
}
