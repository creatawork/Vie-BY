package com.vie.service.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 商品分类VO
 */
@Data
public class CategoryVO {

    /**
     * 分类ID
     */
    private Long id;

    /**
     * 父分类ID
     */
    private Long parentId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 分类图标
     */
    private String categoryIcon;

    /**
     * 排序号
     */
    private Integer sortOrder;

    /**
     * 分类层级
     */
    private Integer level;

    /**
     * 状态：0-禁用，1-正常
     */
    private Integer status;

    /**
     * 子分类列表
     */
    private List<CategoryVO> children;

    /**
     * 商品数量
     */
    private Integer productCount;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
