package com.vie.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vie.db.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 商品分类Mapper接口
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * 根据父分类ID查询子分类列表
     *
     * @param parentId 父分类ID
     * @return 子分类列表
     */
    @Select("SELECT * FROM category WHERE parent_id = #{parentId} ORDER BY sort_order ASC")
    List<Category> selectByParentId(@Param("parentId") Long parentId);

    /**
     * 查询所有分类（树形结构）
     *
     * @return 分类列表
     */
    @Select("SELECT * FROM category ORDER BY level ASC, sort_order ASC")
    List<Category> selectAllCategories();

    /**
     * 统计分类下的商品数量
     *
     * @param categoryId 分类ID
     * @return 商品数量
     */
    @Select("SELECT COUNT(*) FROM product WHERE category_id = #{categoryId}")
    Integer countProductsByCategoryId(@Param("categoryId") Long categoryId);
}
