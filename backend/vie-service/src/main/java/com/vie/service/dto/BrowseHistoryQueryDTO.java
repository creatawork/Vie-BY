package com.vie.service.dto;

import lombok.Data;

/**
 * 浏览历史查询 DTO
 */
@Data
public class BrowseHistoryQueryDTO {

    /**
     * 页码，默认1
     */
    private Integer pageNum = 1;

    /**
     * 每页数量，默认10
     */
    private Integer pageSize = 10;
}
