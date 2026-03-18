package com.vie.service.dto;

import lombok.Data;

/**
 * 商家订单查询DTO
 */
@Data
public class SellerOrderQueryDTO {

    /**
     * 页码
     */
    private Integer pageNum = 1;

    /**
     * 每页数量
     */
    private Integer pageSize = 10;

    /**
     * 订单状态：0-待支付，1-待发货，2-待收货，3-已完成，4-已取消
     */
    private Integer status;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 开始日期 (格式: yyyy-MM-dd)
     */
    private String startDate;

    /**
     * 结束日期 (格式: yyyy-MM-dd)
     */
    private String endDate;

    /**
     * 关键词搜索（买家昵称/手机号）
     */
    private String keyword;
}
