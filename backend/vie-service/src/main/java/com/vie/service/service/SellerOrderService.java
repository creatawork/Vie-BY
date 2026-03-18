package com.vie.service.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vie.service.dto.SellerOrderQueryDTO;
import com.vie.service.vo.SellerOrderCountVO;
import com.vie.service.vo.SellerOrderVO;

/**
 * 商家订单服务接口
 */
public interface SellerOrderService {

    /**
     * 分页查询商家订单列表
     *
     * @param queryDTO 查询条件
     * @param sellerId 商家ID
     * @return 订单分页列表
     */
    IPage<SellerOrderVO> getSellerOrderPage(SellerOrderQueryDTO queryDTO, Long sellerId);

    /**
     * 获取商家订单详情
     *
     * @param orderId  订单ID
     * @param sellerId 商家ID
     * @return 订单详情
     */
    SellerOrderVO getSellerOrderDetail(Long orderId, Long sellerId);

    /**
     * 获取商家各状态订单数量统计
     *
     * @param sellerId 商家ID
     * @return 订单数量统计
     */
    SellerOrderCountVO getSellerOrderCount(Long sellerId);
}
