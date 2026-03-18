package com.vie.db.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 商品分类实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("category")
public class Category extends BaseEntity {

    /**
     * 父分类ID，0表示顶级分类
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
     * 分类层级：1-一级，2-二级，3-三级
     */
    private Integer level;

    /**
     * 状态：0-禁用，1-正常
     */
    private Integer status;

    /**
     * 子分类列表（非数据库字段）
     */
    @TableField(exist = false)
    private List<Category> children;

    /**
     * 商品数量（非数据库字段）
     */
    @TableField(exist = false)
    private Integer productCount;
}
