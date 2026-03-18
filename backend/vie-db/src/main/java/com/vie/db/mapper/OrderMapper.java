package com.vie.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vie.db.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 订单Mapper接口
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 分页查询用户订单列表
     *
     * @param page 分页参数
     * @param userId 用户ID
     * @param status 订单状态（可选）
     * @return 订单分页列表
     */
    IPage<Map<String, Object>> selectOrderPageByUserId(Page<Map<String, Object>> page,
                                                        @Param("userId") Long userId,
                                                        @Param("status") Integer status);

    /**
     * 根据订单编号查询订单
     *
     * @param orderNo 订单编号
     * @return 订单信息
     */
    Order selectByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 查询待发货的订单
     *
     * @return 待发货订单列表
     */
    List<Order> selectPendingShipOrders();

    /**
     * 查询待标记送达的订单（发货超过指定分钟数且未送达）
     *
     * @param minutes 发货后分钟数
     * @return 订单列表
     */
    List<Order> selectUndeliveredOrders(@Param("minutes") int minutes);

    /**
     * 查询需要自动确认收货的订单
     *
     * @return 订单列表
     */
    List<Order> selectAutoReceiveOrders();

    /**
     * 分页查询商家订单列表
     *
     * @param page      分页参数
     * @param sellerId  商家ID
     * @param status    订单状态（可选）
     * @param orderNo   订单号（可选）
     * @param startDate 开始日期（可选）
     * @param endDate   结束日期（可选）
     * @param keyword   关键词（买家昵称/手机号）
     * @return 订单分页列表
     */
    IPage<Map<String, Object>> selectSellerOrderPage(Page<Map<String, Object>> page,
                                                      @Param("sellerId") Long sellerId,
                                                      @Param("status") Integer status,
                                                      @Param("orderNo") String orderNo,
                                                      @Param("startDate") String startDate,
                                                      @Param("endDate") String endDate,
                                                      @Param("keyword") String keyword);

    /**
     * 查询商家订单详情
     *
     * @param orderId  订单ID
     * @param sellerId 商家ID
     * @return 订单详情
     */
    Map<String, Object> selectSellerOrderDetail(@Param("orderId") Long orderId,
                                                 @Param("sellerId") Long sellerId);

    /**
     * 统计商家各状态订单数量
     *
     * @param sellerId 商家ID
     * @return 各状态订单数量
     */
    /**
     * 统计商家各状态订单数量
     *
     * @param sellerId 商家ID
     * @return 各状态订单数量
     */
    Map<String, Integer> countSellerOrdersByStatus(@Param("sellerId") Long sellerId);

    /**
     * 获取商家订单概览统计（订单总数、待发货、已完成）
     * 累计口径，排除已取消订单
     *
     * @param sellerId 商家ID
     * @return 概览统计数据
     */
    Map<String, Object> selectSellerOrderOverview(@Param("sellerId") Long sellerId);

    /**
     * 获取商家订单状态分布
     */
    List<Map<String, Object>> selectSellerOrderStatusDistribution(@Param("sellerId") Long sellerId,
                                                                  @Param("startTime") LocalDateTime startTime,
                                                                  @Param("endTime") LocalDateTime endTime);

    /**
     * 获取商家SKU销量排行
     */
    List<Map<String, Object>> selectSellerSkuRank(@Param("sellerId") Long sellerId,
                                                 @Param("limit") Integer limit,
                                                 @Param("orderBy") String orderBy,
                                                 @Param("startTime") LocalDateTime startTime,
                                                 @Param("endTime") LocalDateTime endTime);
/**
     * 获取商家库存健康概览（SKU维度）
     */
    Map<String, Object> selectSellerInventoryHealth(@Param("sellerId") Long sellerId,
                                                    @Param("lowStockThreshold") Integer lowStockThreshold);
}
