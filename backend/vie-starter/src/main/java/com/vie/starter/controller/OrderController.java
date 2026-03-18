package com.vie.starter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vie.service.common.Result;
import com.vie.service.dto.OrderCreateDTO;
import com.vie.service.dto.OrderQueryDTO;
import com.vie.service.service.LogisticsService;
import com.vie.service.service.OrderService;
import com.vie.service.vo.LogisticsVO;
import com.vie.service.vo.OrderVO;
import com.vie.starter.annotation.RequireLogin;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 订单控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private LogisticsService logisticsService;

    /**
     * 创建订单
     */
    @RequireLogin
    @PostMapping
    public Result<String> createOrder(@Valid @RequestBody OrderCreateDTO dto,
                                       HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String orderNo = orderService.createOrder(dto, userId);
        return Result.success("订单创建成功", orderNo);
    }

    /**
     * 分页查询订单列表
     */
    @RequireLogin
    @GetMapping
    public Result<IPage<OrderVO>> getOrderPage(OrderQueryDTO queryDTO,
                                                HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        IPage<OrderVO> orderPage = orderService.getOrderPage(queryDTO, userId);
        return Result.success(orderPage);
    }

    /**
     * 获取订单详情
     */
    @RequireLogin
    @GetMapping("/{orderId}")
    public Result<OrderVO> getOrderDetail(@PathVariable("orderId") Long orderId,
                                           HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        OrderVO orderDetail = orderService.getOrderDetail(orderId, userId);
        return Result.success(orderDetail);
    }

    /**
     * 取消订单
     */
    @RequireLogin
    @PutMapping("/{orderId}/cancel")
    public Result<Void> cancelOrder(@PathVariable("orderId") Long orderId,
                                     @RequestParam(value = "reason", required = false) String reason,
                                     HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        orderService.cancelOrder(orderId, reason, userId);
        return Result.success("订单取消成功", null);
    }

    /**
     * 确认收货
     */
    @RequireLogin
    @PutMapping("/{orderId}/receive")
    public Result<Void> confirmReceive(@PathVariable("orderId") Long orderId,
                                        HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        orderService.confirmReceive(orderId, userId);
        return Result.success("确认收货成功", null);
    }

    /**
     * 删除订单
     */
    @RequireLogin
    @DeleteMapping("/{orderId}")
    public Result<Void> deleteOrder(@PathVariable("orderId") Long orderId,
                                     HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        orderService.deleteOrder(orderId, userId);
        return Result.success("订单删除成功", null);
    }

    /**
     * 模拟支付（测试用）
     */
    @RequireLogin
    @PutMapping("/{orderId}/pay")
    public Result<Void> mockPay(@PathVariable("orderId") Long orderId,
                                 HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        orderService.mockPay(orderId, userId);
        return Result.success("支付成功", null);
    }

    /**
     * 获取各状态订单数量
     */
    @RequireLogin
    @GetMapping("/count")
    public Result<OrderService.OrderCountVO> getOrderCount(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        OrderService.OrderCountVO count = orderService.getOrderCount(userId);
        return Result.success(count);
    }

    // ========== 物流相关接口 ==========

    /**
     * 获取订单物流信息
     */
    @RequireLogin
    @GetMapping("/{orderId}/logistics")
    public Result<LogisticsVO> getLogisticsInfo(@PathVariable("orderId") Long orderId,
                                                 HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        LogisticsVO logistics = logisticsService.getLogisticsInfo(orderId, userId);
        return Result.success(logistics);
    }

    /**
     * 手动发货（管理员/演示用）
     */
    @RequireLogin
    @PutMapping("/{orderId}/ship")
    public Result<Void> manualShip(@PathVariable("orderId") Long orderId) {
        logisticsService.manualShip(orderId);
        return Result.success("发货成功", null);
    }

    /**
     * 手动标记送达（管理员/演示用）
     */
    @RequireLogin
    @PutMapping("/{orderId}/deliver")
    public Result<Void> manualDeliver(@PathVariable("orderId") Long orderId) {
        logisticsService.manualDeliver(orderId);
        return Result.success("标记送达成功", null);
    }
}
