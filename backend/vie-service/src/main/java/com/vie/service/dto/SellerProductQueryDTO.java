package com.vie.service.dto;

import lombok.Data;

/**
 * 卖家商品查询DTO
 */
@Data
public class SellerProductQueryDTO {

    /**
     * 页码
     */
    private Integer pageNum = 1;

    /**
     * 每页数量
     */
    private Integer pageSize = 10;

    /**
     * 商品状态：0-待审核,1-上架,2-下架,3-审核不通过
     */
    private Integer status;

    /**
     * 商品名称关键词
     */
    private String keyword;

    /**
     * 分类ID
     */
    private Long categoryId;
}
