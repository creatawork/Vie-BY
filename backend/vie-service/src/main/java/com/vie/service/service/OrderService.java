package com.vie.service.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vie.service.dto.OrderCreateDTO;
import com.vie.service.dto.OrderQueryDTO;
import com.vie.service.vo.OrderVO;

/**
 * 订单服务接口
 */
public interface OrderService {

    /**
     * 创建订单
     *
     * @param dto 创建订单DTO
     * @param userId 用户ID
     * @return 订单编号
     */
    String createOrder(OrderCreateDTO dto, Long userId);

    /**
     * 分页查询订单列表
     *
     * @param queryDTO 查询条件
     * @param userId 用户ID
     * @return 订单分页列表
     */
    IPage<OrderVO> getOrderPage(OrderQueryDTO queryDTO, Long userId);

    /**
     * 获取订单详情
     *
     * @param orderId 订单ID
     * @param userId 用户ID
     * @return 订单详情
     */
    OrderVO getOrderDetail(Long orderId, Long userId);

    /**
     * 取消订单
     *
     * @param orderId 订单ID
     * @param reason 取消原因
     * @param userId 用户ID
     */
    void cancelOrder(Long orderId, String reason, Long userId);

    /**
     * 确认收货
     *
     * @param orderId 订单ID
     * @param userId 用户ID
     */
    void confirmReceive(Long orderId, Long userId);

    /**
     * 删除订单
     *
     * @param orderId 订单ID
     * @param userId 用户ID
     */
    void deleteOrder(Long orderId, Long userId);

    /**
     * 模拟支付（测试用）
     *
     * @param orderId 订单ID
     * @param userId 用户ID
     */
    void mockPay(Long orderId, Long userId);

    /**
     * 获取各状态订单数量
     *
     * @param userId 用户ID
     * @return 各状态数量
     */
    OrderCountVO getOrderCount(Long userId);

    /**
     * 订单数量VO
     */
    record OrderCountVO(
            Integer unpaid,      // 待支付
            Integer unshipped,   // 待发货
            Integer shipped,     // 待收货
            Integer completed,   // 已完成
            Integer total        // 全部
    ) {}
}
