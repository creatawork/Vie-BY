package com.vie.service.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商家订单概览统计VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerOrderOverviewVO {

    /**
     * 订单总数（排除已取消）
     */
    private Integer totalCount;

    /**
     * 待发货订单数
     */
    private Integer waitShipCount;

    /**
     * 已完成订单数
     */
    private Integer finishedCount;
}

