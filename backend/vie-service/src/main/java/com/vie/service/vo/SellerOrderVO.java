package com.vie.service.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商家订单VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerOrderVO {

    /**
     * 订单ID
     */
    private Long id;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 买家ID
     */
    private Long buyerId;

    /**
     * 买家昵称
     */
    private String buyerNickname;

    /**
     * 买家手机号
     */
    private String buyerPhone;

    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;

    /**
     * 实付金额
     */
    private BigDecimal payAmount;

    /**
     * 订单状态：0-待支付，1-待发货，2-待收货，3-已完成，4-已取消
     */
    private Integer status;

    /**
     * 订单状态描述
     */
    private String statusText;

    /**
     * 收货人姓名
     */
    private String receiverName;

    /**
     * 收货人电话
     */
    private String receiverPhone;

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
     * 创建时间
     */
    private String createTime;

    /**
     * 订单商品数量（商品种类数）
     */
    private Integer itemCount;

    /**
     * 订单商品总数量
     */
    private Integer totalQuantity;

    /**
     * 物流单号
     */
    private String logisticsNo;

    /**
     * 物流公司
     */
    private String logisticsCompany;

    /**
     * 订单明细列表
     */
    private List<OrderItemVO> items;
}
