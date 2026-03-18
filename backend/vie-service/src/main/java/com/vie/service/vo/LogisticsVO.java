package com.vie.service.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 物流信息VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogisticsVO {

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 物流单号
     */
    private String logisticsNo;

    /**
     * 物流公司
     */
    private String logisticsCompany;

    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 订单状态描述
     */
    private String statusDesc;

    /**
     * 是否已送达
     */
    private Boolean delivered;

    /**
     * 送达状态描述
     */
    private String deliveredDesc;

    /**
     * 发货时间
     */
    private String shipTime;

    /**
     * 收货时间
     */
    private String receiveTime;

    /**
     * 自动确认收货时间
     */
    private String autoReceiveTime;

    /**
     * 物流轨迹列表
     */
    private List<LogisticsTrackVO> tracks;
}
