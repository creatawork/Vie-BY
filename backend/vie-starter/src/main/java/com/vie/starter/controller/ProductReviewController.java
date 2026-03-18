package com.vie.starter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vie.service.common.Result;
import com.vie.service.constant.RoleConstant;
import com.vie.service.dto.ProductReviewDTO;
import com.vie.service.service.ProductReviewService;
import com.vie.service.vo.ProductReviewVO;
import com.vie.starter.annotation.RequireLogin;
import com.vie.starter.annotation.RequireRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 商品评价控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/reviews")
public class ProductReviewController {

    @Autowired
    private ProductReviewService productReviewService;

    /**
     * 发布商品评价
     */
    @RequireLogin
    @PostMapping
    public Result<Void> createReview(@Valid @RequestBody ProductReviewDTO dto,
                                     HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        productReviewService.createReview(dto, userId);
        return Result.success("评价发布成功", null);
    }

    /**
     * 分页查询商品评价
     */
    @GetMapping("/product/{productId}")
    public Result<IPage<ProductReviewVO>> getReviewPage(
            @PathVariable Long productId,
            @RequestParam(required = false) Integer rating,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        IPage<ProductReviewVO> reviewPage = productReviewService.getReviewPage(
                productId, rating, pageNum, pageSize);
        return Result.success(reviewPage);
    }

    /**
     * 分页查询用户的评价列表（我的评价）
     */
    @RequireLogin
    @GetMapping("/mine")
    public Result<IPage<ProductReviewVO>> getUserReviewPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        IPage<ProductReviewVO> reviewPage = productReviewService.getUserReviewPage(
                userId, pageNum, pageSize);
        return Result.success(reviewPage);
    }

    /**
     * 查询订单的评价状态（哪些商品已评价）
     */
    @RequireLogin
    @GetMapping("/order/{orderId}")
    public Result<Map<String, Object>> getOrderReviewStatus(
            @PathVariable Long orderId,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        List<Long> reviewedProductIds = productReviewService.getReviewedProductIds(orderId, userId);
        return Result.success(Map.of("reviewedProductIds", reviewedProductIds));
    }

    /**
     * 商家回复评价
     */
    @RequireLogin
    @PutMapping("/{reviewId}/reply")
    public Result<Void> replyReview(@PathVariable Long reviewId,
                                    @RequestParam String replyContent,
                                    HttpServletRequest request) {
        Long sellerId = (Long) request.getAttribute("userId");
        productReviewService.replyReview(reviewId, replyContent, sellerId);
        return Result.success("回复成功", null);
    }

    /**
     * 删除评价（管理员）
     */
    @RequireRole(RoleConstant.ADMIN_ROLE_CODE)
    @DeleteMapping("/{reviewId}")
    public Result<Void> deleteReview(@PathVariable Long reviewId) {
        productReviewService.deleteReview(reviewId);
        return Result.success("评价删除成功", null);
    }

    /**
     * 隐藏/显示评价（管理员）
     */
    @RequireRole(RoleConstant.ADMIN_ROLE_CODE)
    @PutMapping("/{reviewId}/status")
    public Result<Void> updateReviewStatus(@PathVariable Long reviewId,
                                           @RequestParam Integer status) {
        productReviewService.updateReviewStatus(reviewId, status);
        return Result.success("状态更新成功", null);
    }
}
