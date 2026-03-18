package com.vie.starter.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.vie.db.entity.Order;
import com.vie.db.entity.Product;
import com.vie.db.entity.User;
import com.vie.db.mapper.OrderMapper;
import com.vie.db.mapper.ProductMapper;
import com.vie.db.mapper.UserMapper;
import com.vie.service.common.Result;
import com.vie.service.constant.RoleConstant;
import com.vie.starter.annotation.RequireRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理员-数据统计
 */
@Slf4j
@RestController
@RequestMapping("/api/admin/statistics")
@RequireRole(RoleConstant.ADMIN_ROLE_CODE)
public class AdminStatisticsController {

    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private ProductMapper productMapper;
    
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 平台概览
     */
    @GetMapping("/overview")
    public Result<Map<String, Object>> getOverview() {
        Map<String, Object> data = new HashMap<>();
        
        // 用户统计
        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(User::getIsDeleted, 0);
        Long totalUsers = userMapper.selectCount(userWrapper);
        data.put("totalUsers", totalUsers);
        
        // 正常用户数
        LambdaQueryWrapper<User> activeUserWrapper = new LambdaQueryWrapper<>();
        activeUserWrapper.eq(User::getIsDeleted, 0);
        activeUserWrapper.eq(User::getStatus, 1);
        Long activeUsers = userMapper.selectCount(activeUserWrapper);
        data.put("activeUsers", activeUsers);
        
        // 商品统计
        LambdaQueryWrapper<Product> productWrapper = new LambdaQueryWrapper<>();
        productWrapper.eq(Product::getIsDeleted, 0);
        Long totalProducts = productMapper.selectCount(productWrapper);
        data.put("totalProducts", totalProducts);
        
        // 待审核商品
        LambdaQueryWrapper<Product> pendingWrapper = new LambdaQueryWrapper<>();
        pendingWrapper.eq(Product::getIsDeleted, 0);
        pendingWrapper.eq(Product::getStatus, 0);
        Long pendingProducts = productMapper.selectCount(pendingWrapper);
        data.put("pendingProducts", pendingProducts);
        
        // 上架商品
        LambdaQueryWrapper<Product> onlineWrapper = new LambdaQueryWrapper<>();
        onlineWrapper.eq(Product::getIsDeleted, 0);
        onlineWrapper.eq(Product::getStatus, 1);
        Long onlineProducts = productMapper.selectCount(onlineWrapper);
        data.put("onlineProducts", onlineProducts);
        
        // 订单统计
        LambdaQueryWrapper<Order> orderWrapper = new LambdaQueryWrapper<>();
        orderWrapper.eq(Order::getIsDeleted, 0);
        Long totalOrders = orderMapper.selectCount(orderWrapper);
        data.put("totalOrders", totalOrders);
        
        // 已完成订单
        LambdaQueryWrapper<Order> completedWrapper = new LambdaQueryWrapper<>();
        completedWrapper.eq(Order::getIsDeleted, 0);
        completedWrapper.eq(Order::getStatus, 3);
        Long completedOrders = orderMapper.selectCount(completedWrapper);
        data.put("completedOrders", completedOrders);
        
        // 待处理订单（待支付+待发货）
        LambdaQueryWrapper<Order> pendingOrderWrapper = new LambdaQueryWrapper<>();
        pendingOrderWrapper.eq(Order::getIsDeleted, 0);
        pendingOrderWrapper.in(Order::getStatus, 0, 1);
        Long pendingOrders = orderMapper.selectCount(pendingOrderWrapper);
        data.put("pendingOrders", pendingOrders);
        
        return Result.success(data);
    }
    
    /**
     * 商品状态分布
     */
    @GetMapping("/product-distribution")
    public Result<Map<String, Long>> getProductDistribution() {
        Map<String, Long> data = new HashMap<>();
        
        // 待审核
        LambdaQueryWrapper<Product> pendingWrapper = new LambdaQueryWrapper<>();
        pendingWrapper.eq(Product::getIsDeleted, 0);
        pendingWrapper.eq(Product::getStatus, 0);
        data.put("pending", productMapper.selectCount(pendingWrapper));
        
        // 已上架
        LambdaQueryWrapper<Product> onlineWrapper = new LambdaQueryWrapper<>();
        onlineWrapper.eq(Product::getIsDeleted, 0);
        onlineWrapper.eq(Product::getStatus, 1);
        data.put("online", productMapper.selectCount(onlineWrapper));
        
        // 已下架
        LambdaQueryWrapper<Product> offlineWrapper = new LambdaQueryWrapper<>();
        offlineWrapper.eq(Product::getIsDeleted, 0);
        offlineWrapper.eq(Product::getStatus, 2);
        data.put("offline", productMapper.selectCount(offlineWrapper));
        
        // 审核不通过
        LambdaQueryWrapper<Product> rejectedWrapper = new LambdaQueryWrapper<>();
        rejectedWrapper.eq(Product::getIsDeleted, 0);
        rejectedWrapper.eq(Product::getStatus, 3);
        data.put("rejected", productMapper.selectCount(rejectedWrapper));
        
        return Result.success(data);
    }
    
    /**
     * 订单状态分布
     */
    @GetMapping("/order-distribution")
    public Result<Map<String, Long>> getOrderDistribution() {
        Map<String, Long> data = new HashMap<>();
        
        // 待支付
        LambdaQueryWrapper<Order> unpaidWrapper = new LambdaQueryWrapper<>();
        unpaidWrapper.eq(Order::getIsDeleted, 0);
        unpaidWrapper.eq(Order::getStatus, 0);
        data.put("unpaid", orderMapper.selectCount(unpaidWrapper));
        
        // 待发货
        LambdaQueryWrapper<Order> unshippedWrapper = new LambdaQueryWrapper<>();
        unshippedWrapper.eq(Order::getIsDeleted, 0);
        unshippedWrapper.eq(Order::getStatus, 1);
        data.put("unshipped", orderMapper.selectCount(unshippedWrapper));
        
        // 待收货
        LambdaQueryWrapper<Order> shippedWrapper = new LambdaQueryWrapper<>();
        shippedWrapper.eq(Order::getIsDeleted, 0);
        shippedWrapper.eq(Order::getStatus, 2);
        data.put("shipped", orderMapper.selectCount(shippedWrapper));
        
        // 已完成
        LambdaQueryWrapper<Order> completedWrapper = new LambdaQueryWrapper<>();
        completedWrapper.eq(Order::getIsDeleted, 0);
        completedWrapper.eq(Order::getStatus, 3);
        data.put("completed", orderMapper.selectCount(completedWrapper));
        
        // 已取消
        LambdaQueryWrapper<Order> cancelledWrapper = new LambdaQueryWrapper<>();
        cancelledWrapper.eq(Order::getIsDeleted, 0);
        cancelledWrapper.eq(Order::getStatus, 4);
        data.put("cancelled", orderMapper.selectCount(cancelledWrapper));
        
        return Result.success(data);
    }
}

