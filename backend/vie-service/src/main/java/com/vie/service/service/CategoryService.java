package com.vie.service.service;

import com.vie.service.vo.CategoryVO;

import java.util.List;

/**
 * 商品分类服务接口
 */
public interface CategoryService {

    /**
     * 获取分类树（所有分类）
     *
     * @return 分类树
     */
    List<CategoryVO> getCategoryTree();

    /**
     * 根据父分类ID获取子分类列表
     *
     * @param parentId 父分类ID
     * @return 子分类列表
     */
    List<CategoryVO> getChildCategories(Long parentId);

    /**
     * 根据ID获取分类详情
     *
     * @param categoryId 分类ID
     * @return 分类详情
     */
    CategoryVO getCategoryById(Long categoryId);

    /**
     * 获取一级分类列表
     *
     * @return 一级分类列表
     */
    List<CategoryVO> getTopCategories();
}
