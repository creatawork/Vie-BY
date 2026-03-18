package com.vie.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vie.db.entity.ProductReview;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 商品评价Mapper接口
 */
@Mapper
public interface ProductReviewMapper extends BaseMapper<ProductReview> {

    /**
     * 分页查询商品评价（带用户信息）
     *
     * @param page 分页对象
     * @param productId 商品ID
     * @param rating 评分（可选）
     * @return 评价分页列表
     */
    IPage<ProductReview> selectReviewPage(Page<ProductReview> page,
                                          @Param("productId") Long productId,
                                          @Param("rating") Integer rating);

    /**
     * 计算商品平均评分
     *
     * @param productId 商品ID
     * @return 平均评分
     */
    @Select("SELECT IFNULL(AVG(rating), 0) FROM product_review " +
            "WHERE product_id = #{productId} AND status = 1 AND is_deleted = 0")
    Double calculateAvgRating(@Param("productId") Long productId);

    /**
     * 统计商品评价数量
     *
     * @param productId 商品ID
     * @return 评价数量
     */
    @Select("SELECT COUNT(*) FROM product_review " +
            "WHERE product_id = #{productId} AND status = 1 AND is_deleted = 0")
    Integer countByProductId(@Param("productId") Long productId);

    /**
     * 根据评分统计评价数量
     *
     * @param productId 商品ID
     * @param rating 评分
     * @return 评价数量
     */
    @Select("SELECT COUNT(*) FROM product_review " +
            "WHERE product_id = #{productId} AND rating = #{rating} AND status = 1 AND is_deleted = 0")
    Integer countByProductIdAndRating(@Param("productId") Long productId, @Param("rating") Integer rating);

    /**
     * 检查用户是否已对某订单的某商品评价
     *
     * @param userId 用户ID
     * @param orderId 订单ID
     * @param productId 商品ID
     * @return 评价数量
     */
    @Select("SELECT COUNT(*) FROM product_review " +
            "WHERE user_id = #{userId} AND order_id = #{orderId} AND product_id = #{productId} AND is_deleted = 0")
    Integer countByUserAndOrderAndProduct(@Param("userId") Long userId,
                                          @Param("orderId") Long orderId,
                                          @Param("productId") Long productId);

    /**
     * 分页查询用户的评价列表（带商品信息）
     *
     * @param page 分页对象
     * @param userId 用户ID
     * @return 评价分页列表
     */
    IPage<ProductReview> selectUserReviewPage(Page<ProductReview> page,
                                              @Param("userId") Long userId);

    /**
     * 管理员分页查询评价列表
     */
    IPage<ProductReview> selectAdminReviewPage(Page<ProductReview> page,
                                               @Param("keyword") String keyword,
                                               @Param("status") Integer status,
                                               @Param("rating") Integer rating,
                                               @Param("productId") Long productId,
                                               @Param("userId") Long userId);

    /**
     * 管理员查询评价详情
     */
    ProductReview selectAdminReviewById(@Param("reviewId") Long reviewId);

    /**
     * 查询某订单下已评价的商品ID列表
     *
     * @param orderId 订单ID
     * @param userId 用户ID
     * @return 已评价的商品ID列表
     */
    @Select("SELECT product_id FROM product_review " +
            "WHERE order_id = #{orderId} AND user_id = #{userId} AND is_deleted = 0")
    List<Long> selectReviewedProductIdsByOrderId(@Param("orderId") Long orderId,
                                                 @Param("userId") Long userId);

    /**
     * 统计某订单的评价数量
     *
     * @param orderId 订单ID
     * @return 评价数量
     */
    @Select("SELECT COUNT(*) FROM product_review " +
            "WHERE order_id = #{orderId} AND is_deleted = 0")
    Integer countByOrderId(@Param("orderId") Long orderId);
}
