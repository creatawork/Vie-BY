package com.vie.service.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vie.service.dto.ProductReviewDTO;
import com.vie.service.vo.ProductReviewVO;

import java.util.List;
import java.util.Map;

/**
 * 商品评价服务接口
 */
public interface ProductReviewService {

    /**
     * 发布商品评价
     *
     * @param dto 评价DTO
     * @param userId 用户ID
     */
    void createReview(ProductReviewDTO dto, Long userId);

    /**
     * 分页查询商品评价
     *
     * @param productId 商品ID
     * @param rating 评分（可选）
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return 评价分页列表
     */
    IPage<ProductReviewVO> getReviewPage(Long productId, Integer rating, Integer pageNum, Integer pageSize);

    /**
     * 分页查询用户的评价列表
     *
     * @param userId 用户ID
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return 评价分页列表
     */
    IPage<ProductReviewVO> getUserReviewPage(Long userId, Integer pageNum, Integer pageSize);

    /**
     * 获取订单的评价状态（哪些商品已评价、哪些未评价）
     *
     * @param orderId 订单ID
     * @param userId 用户ID
     * @return 已评价的商品ID列表
     */
    List<Long> getReviewedProductIds(Long orderId, Long userId);

    /**
     * 检查订单是否已全部评价
     *
     * @param orderId 订单ID
     * @param userId 用户ID
     * @param orderItemCount 订单商品种类数
     * @return 是否全部评价
     */
    boolean isOrderFullyReviewed(Long orderId, Long userId, int orderItemCount);

    /**
     * 商家回复评价
     *
     * @param reviewId 评价ID
     * @param replyContent 回复内容
     * @param sellerId 商家ID（用于权限校验）
     */
    void replyReview(Long reviewId, String replyContent, Long sellerId);

    /**
     * 删除评价（管理员）
     *
     * @param reviewId 评价ID
     */
    void deleteReview(Long reviewId);

    /**
     * 隐藏/显示评价（管理员）
     *
     * @param reviewId 评价ID
     * @param status 状态：0-隐藏，1-显示
     */
    void updateReviewStatus(Long reviewId, Integer status);

    /**
     * 管理员分页查询评价列表
     *
     * @param keyword 关键词（评价内容/用户名/昵称）
     * @param status 状态：0-隐藏，1-显示
     * @param rating 评分
     * @param productId 商品ID
     * @param userId 用户ID
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return 评价分页列表
     */
    IPage<ProductReviewVO> getAdminReviewPage(String keyword,
                                              Integer status,
                                              Integer rating,
                                              Long productId,
                                              Long userId,
                                              Integer pageNum,
                                              Integer pageSize);

    /**
     * 管理员查询评价详情
     *
     * @param reviewId 评价ID
     * @return 评价详情
     */
    ProductReviewVO getAdminReviewDetail(Long reviewId);

    /**
     * 管理员获取评价概览统计
     *
     * @return 统计数据
     */
    Map<String, Object> getAdminReviewStatistics();
}
