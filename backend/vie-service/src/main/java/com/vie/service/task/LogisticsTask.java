package com.vie.service.task;

import com.vie.service.service.LogisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 物流定时任务
 */
@Slf4j
@Component
public class LogisticsTask {

    @Autowired
    private LogisticsService logisticsService;

    /**
     * 自动发货任务
     * 每30秒执行一次，处理待发货的订单
     */
    @Scheduled(fixedRate = 30000)
    public void autoShipTask() {
        log.debug("执行自动发货任务...");
        try {
            logisticsService.processPendingShipOrders();
        } catch (Exception e) {
            log.error("自动发货任务执行失败", e);
        }
    }

    /**
     * 自动送达任务
     * 每分钟执行一次，标记已送达的订单
     */
    @Scheduled(fixedRate = 60000)
    public void autoDeliverTask() {
        log.debug("执行自动送达任务...");
        try {
            logisticsService.processUndeliveredOrders();
        } catch (Exception e) {
            log.error("自动送达任务执行失败", e);
        }
    }

    /**
     * 自动确认收货任务
     * 每分钟执行一次，处理超时未确认的订单
     */
    @Scheduled(fixedRate = 60000)
    public void autoReceiveTask() {
        log.debug("执行自动确认收货任务...");
        try {
            logisticsService.processAutoReceiveOrders();
        } catch (Exception e) {
            log.error("自动确认收货任务执行失败", e);
        }
    }
}
