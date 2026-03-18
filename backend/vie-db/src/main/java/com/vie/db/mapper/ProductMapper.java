package com.vie.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vie.db.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 商品Mapper接口
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {

    /**
     * 分页查询商品（带分类信息）
     *
     * @param page 分页对象
     * @param categoryId 分类ID（可选）
     * @param keyword 搜索关键词（可选）
     * @param status 商品状态（可选）
     * @return 商品分页列表
     */
    IPage<Product> selectProductPage(Page<Product> page,
                                     @Param("categoryId") Long categoryId,
                                     @Param("keyword") String keyword,
                                     @Param("status") Integer status);

    /**
     * 根据商品ID查询商品详情（包含分类、图片、SKU）
     *
     * @param productId 商品ID
     * @return 商品详情
     */
    Product selectProductDetailById(@Param("productId") Long productId);

    /**
     * 增加商品浏览量
     *
     * @param productId 商品ID
     */
    @Update("UPDATE product SET view_count = view_count + 1 WHERE id = #{productId}")
    void incrementViewCount(@Param("productId") Long productId);

    /**
     * 查询推荐商品
     *
     * @param limit 数量限制
     * @return 推荐商品列表
     */
    @Select("SELECT * FROM product WHERE is_recommended = 1 AND status = 1 " +
            "ORDER BY sales_volume DESC LIMIT #{limit}")
    List<Product> selectRecommendedProducts(@Param("limit") Integer limit);

    /**
     * 查询新品
     *
     * @param limit 数量限制
     * @return 新品列表
     */
    @Select("SELECT * FROM product WHERE is_new = 1 AND status = 1 " +
            "ORDER BY create_time DESC LIMIT #{limit}")
    List<Product> selectNewProducts(@Param("limit") Integer limit);

    /**
     * 查询热销商品
     *
     * @param limit 数量限制
     * @return 热销商品列表
     */
    @Select("SELECT * FROM product WHERE is_hot = 1 AND status = 1 " +
            "ORDER BY sales_volume DESC LIMIT #{limit}")
    List<Product> selectHotProducts(@Param("limit") Integer limit);
}
