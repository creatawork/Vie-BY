package com.vie.service.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderVO {

    /**
     * 订单ID
     */
    private Long id;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;

    /**
     * 实付金额
     */
    private BigDecimal payAmount;

    /**
     * 运费
     */
    private BigDecimal freightAmount;

    /**
     * 订单状态：0-待支付，1-待发货，2-待收货，3-已完成，4-已取消，5-已关闭
     */
    private Integer status;

    /**
     * 订单状态描述
     */
    private String statusDesc;

    /**
     * 收货人姓名
     */
    private String receiverName;

    /**
     * 收货人电话
     */
    private String receiverPhone;

    /**
     * 收货省份
     */
    private String receiverProvince;

    /**
     * 收货城市
     */
    private String receiverCity;

    /**
     * 收货区/县
     */
    private String receiverDistrict;

    /**
     * 收货地址
     */
    private String receiverAddress;

    /**
     * 订单备注
     */
    private String remark;

    /**
     * 支付时间
     */
    private String payTime;

    /**
     * 发货时间
     */
    private String deliverTime;

    /**
     * 收货时间
     */
    private String receiveTime;

    /**
     * 取消时间
     */
    private String cancelTime;

    /**
     * 取消原因
     */
    private String cancelReason;

    /**
     * 创建时间
     */
    private String createTime;

    // ========== 物流相关字段 ==========

    /**
     * 物流单号
     */
    private String logisticsNo;

    /**
     * 物流公司
     */
    private String logisticsCompany;

    /**
     * 是否已送达
     */
    private Boolean delivered;

    /**
     * 自动确认收货时间
     */
    private String autoReceiveTime;

    /**
     * 订单明细列表
     */
    private List<OrderItemVO> orderItems;

    /**
     * 商品总数量
     */
    private Integer totalQuantity;

    /**
     * 是否已全部评价
     */
    private Boolean reviewed;

    /**
     * 评价数量（已评价的商品数）
     */
    private Integer reviewCount;
}
