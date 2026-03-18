package com.vie.service.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商家订单统计VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerOrderCountVO {

    /**
     * 待支付订单数
     */
    private Integer unpaid;

    /**
     * 待发货订单数
     */
    private Integer unshipped;

    /**
     * 待收货订单数（已发货）
     */
    private Integer shipped;

    /**
     * 已完成订单数
     */
    private Integer completed;

    /**
     * 已取消订单数
     */
    private Integer cancelled;

    /**
     * 订单总数
     */
    private Integer total;
}
