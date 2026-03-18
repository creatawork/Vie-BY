package com.vie.service.service;

import com.vie.service.dto.SalesStatisticsQueryDTO;
import com.vie.service.vo.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 卖家销售统计服务接口
 */
public interface SellerStatisticsService {

    /**
     * 获取销售统计
     *
     * @param queryDTO 查询条件
     * @param sellerId 卖家ID
     * @return 销售统计数据
     */
    SalesStatisticsVO getSalesStatistics(SalesStatisticsQueryDTO queryDTO, Long sellerId);

    /**
     * 获取商品销量排行
     *
     * @param limit     返回数量
     * @param orderBy   排序方式：sales-销量, amount-销售额
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @param sellerId  卖家ID
     * @return 商品排行列表
     */
    List<ProductRankVO> getProductRank(Integer limit, String orderBy, LocalDate startDate, LocalDate endDate, Long sellerId);

    /**
     * 获取收益统计
     *
     * @param sellerId 卖家ID
     * @return 收益统计数据
     */
    RevenueStatisticsVO getRevenueStatistics(Long sellerId);

    /**
     * 获取商家订单概览统计（订单总数/待发货/已完成）
     *
     * @param sellerId 商家ID
     * @return 概览统计
     */
    SellerOrderOverviewVO getOrderOverview(Long sellerId);

    /**
     * 获取订单状态分布统计
     *
     * @param sellerId 商家ID
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 状态分布数据
     */
    SellerOrderDistributionVO getOrderStatusDistribution(Long sellerId, LocalDate startDate, LocalDate endDate);

    /**
     * 获取SKU销量排行
     *
     * @param limit     返回数量
     * @param orderBy   排序方式：sales-销量, amount-销售额
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @param sellerId  卖家ID
     * @return SKU排行列表
     */
    List<SkuRankVO> getSkuRank(Integer limit, String orderBy, LocalDate startDate, LocalDate endDate, Long sellerId);

    /**
     * 获取库存健康概览
     *
     * @param sellerId 商家ID
     * @return 库存健康数据
     */
    InventoryHealthVO getInventoryHealth(Long sellerId);
}
