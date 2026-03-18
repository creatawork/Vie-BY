package com.vie.service.dto;

import lombok.Data;

/**
 * 收藏查询 DTO
 */
@Data
public class CollectionQueryDTO {

    /**
     * 页码，默认1
     */
    private Integer pageNum = 1;

    /**
     * 每页数量，默认10
     */
    private Integer pageSize = 10;

    /**
     * 排序字段：time-收藏时间（默认），price-价格
     */
    private String sortBy = "time";

    /**
     * 排序方式：desc-降序（默认），asc-升序
     */
    private String sortOrder = "desc";
}
