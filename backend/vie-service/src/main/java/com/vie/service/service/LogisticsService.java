package com.vie.service.service;

import com.vie.service.vo.LogisticsVO;

/**
 * 物流服务接口
 */
public interface LogisticsService {

    /**
     * 获取订单物流信息
     *
     * @param orderId 订单ID
     * @param userId 用户ID
     * @return 物流信息
     */
    LogisticsVO getLogisticsInfo(Long orderId, Long userId);

    /**
     * 自动发货（支付成功后调用）
     *
     * @param orderId 订单ID
     */
    void autoShip(Long orderId);

    /**
     * 手动发货（管理员）
     *
     * @param orderId 订单ID
     */
    void manualShip(Long orderId);

    /**
     * 手动标记送达（管理员）
     *
     * @param orderId 订单ID
     */
    void manualDeliver(Long orderId);

    /**
     * 处理待发货订单（定时任务调用）
     */
    void processPendingShipOrders();

    /**
     * 处理待送达订单（定时任务调用）
     */
    void processUndeliveredOrders();

    /**
     * 处理自动确认收货订单（定时任务调用）
     */
    void processAutoReceiveOrders();
}
