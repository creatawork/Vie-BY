package com.vie.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.vie.db.entity.Category;
import com.vie.db.mapper.CategoryMapper;
import com.vie.service.common.ResultCode;
import com.vie.service.exception.BusinessException;
import com.vie.service.service.CategoryService;
import com.vie.service.vo.CategoryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商品分类服务实现类
 */
@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryVO> getCategoryTree() {
        // 查询所有分类
        List<Category> allCategories = categoryMapper.selectAllCategories();

        // 转换为VO并构建树形结构
        return buildCategoryTree(allCategories, 0L);
    }

    @Override
    public List<CategoryVO> getChildCategories(Long parentId) {
        List<Category> categories = categoryMapper.selectByParentId(parentId);

        return categories.stream().map(category -> {
            CategoryVO vo = new CategoryVO();
            BeanUtils.copyProperties(category, vo);

            // 统计商品数量
            Integer productCount = categoryMapper.countProductsByCategoryId(category.getId());
            vo.setProductCount(productCount);

            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public CategoryVO getCategoryById(Long categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        if (category == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "分类不存在");
        }

        CategoryVO vo = new CategoryVO();
        BeanUtils.copyProperties(category, vo);

        // 统计商品数量
        Integer productCount = categoryMapper.countProductsByCategoryId(categoryId);
        vo.setProductCount(productCount);

        return vo;
    }

    @Override
    public List<CategoryVO> getTopCategories() {
        LambdaQueryWrapper<Category> query = new LambdaQueryWrapper<>();
        query.eq(Category::getParentId, 0L)
             .eq(Category::getStatus, 1)
             .orderByAsc(Category::getSortOrder);

        List<Category> categories = categoryMapper.selectList(query);

        return categories.stream().map(category -> {
            CategoryVO vo = new CategoryVO();
            BeanUtils.copyProperties(category, vo);

            // 统计商品数量
            Integer productCount = categoryMapper.countProductsByCategoryId(category.getId());
            vo.setProductCount(productCount);

            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 构建分类树
     *
     * @param allCategories 所有分类
     * @param parentId 父分类ID
     * @return 分类树
     */
    private List<CategoryVO> buildCategoryTree(List<Category> allCategories, Long parentId) {
        // 按父ID分组
        Map<Long, List<Category>> categoryMap = allCategories.stream()
                .collect(Collectors.groupingBy(Category::getParentId));

        return buildTree(categoryMap, parentId);
    }

    /**
     * 递归构建树形结构
     *
     * @param categoryMap 分类映射
     * @param parentId 父分类ID
     * @return 树形分类列表
     */
    private List<CategoryVO> buildTree(Map<Long, List<Category>> categoryMap, Long parentId) {
        List<Category> categories = categoryMap.get(parentId);
        if (categories == null || categories.isEmpty()) {
            return new ArrayList<>();
        }

        return categories.stream().map(category -> {
            CategoryVO vo = new CategoryVO();
            BeanUtils.copyProperties(category, vo);

            // 统计商品数量
            Integer productCount = categoryMapper.countProductsByCategoryId(category.getId());
            vo.setProductCount(productCount);

            // 递归查询子分类
            List<CategoryVO> children = buildTree(categoryMap, category.getId());
            if (!children.isEmpty()) {
                vo.setChildren(children);
            }

            return vo;
        }).collect(Collectors.toList());
    }
}
