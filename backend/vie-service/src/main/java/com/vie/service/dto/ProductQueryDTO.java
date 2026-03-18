package com.vie.service.dto;

import lombok.Data;

/**
 * 商品查询DTO
 */
@Data
public class ProductQueryDTO {

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 搜索关键词
     */
    private String keyword;

    /**
     * 商品状态：0-待审核，1-上架，2-下架，3-审核不通过
     */
    private Integer status;

    /**
     * 最低价格
     */
    private String minPrice;

    /**
     * 最高价格
     */
    private String maxPrice;

    /**
     * 排序字段：price-价格，sales-销量，time-时间
     */
    private String sortBy;

    /**
     * 排序方式：asc-升序，desc-降序
     */
    private String sortOrder;

    /**
     * 是否推荐：0-否，1-是
     */
    private Integer isRecommended;

    /**
     * 是否新品：0-否，1-是
     */
    private Integer isNew;

    /**
     * 是否热卖：0-否，1-是
     */
    private Integer isHot;

    /**
     * 页码
     */
    private Integer pageNum = 1;

    /**
     * 每页数量
     */
    private Integer pageSize = 10;
}
