package com.vie.starter.controller;

import com.vie.service.common.Result;
import com.vie.service.service.CategoryService;
import com.vie.service.vo.CategoryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品分类控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取分类树（所有分类）
     */
    @GetMapping("/tree")
    public Result<List<CategoryVO>> getCategoryTree() {
        List<CategoryVO> categoryTree = categoryService.getCategoryTree();
        return Result.success(categoryTree);
    }

    /**
     * 获取一级分类列表
     */
    @GetMapping("/top")
    public Result<List<CategoryVO>> getTopCategories() {
        List<CategoryVO> categories = categoryService.getTopCategories();
        return Result.success(categories);
    }

    /**
     * 根据父分类ID获取子分类列表
     */
    @GetMapping("/children/{parentId}")
    public Result<List<CategoryVO>> getChildCategories(@PathVariable Long parentId) {
        List<CategoryVO> categories = categoryService.getChildCategories(parentId);
        return Result.success(categories);
    }

    /**
     * 获取分类详情
     */
    @GetMapping("/{categoryId}")
    public Result<CategoryVO> getCategoryById(@PathVariable Long categoryId) {
        CategoryVO category = categoryService.getCategoryById(categoryId);
        return Result.success(category);
    }
}
