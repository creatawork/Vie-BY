package com.vie.starter.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vie.service.common.Result;
import com.vie.service.constant.RoleConstant;
import com.vie.service.service.ProductReviewService;
import com.vie.service.vo.ProductReviewVO;
import com.vie.starter.annotation.RequireRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 管理员-评价管理
 */
@Slf4j
@RestController
@RequestMapping("/api/admin/reviews")
@RequireRole(RoleConstant.ADMIN_ROLE_CODE)
public class AdminReviewController {

    @Autowired
    private ProductReviewService productReviewService;

    /**
     * 评价列表（分页）
     */
    @GetMapping
    public Result<IPage<ProductReviewVO>> getReviewList(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Integer rating,
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {

        IPage<ProductReviewVO> page = productReviewService.getAdminReviewPage(
                keyword, status, rating, productId, userId, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 评价详情
     */
    @GetMapping("/{reviewId}")
    public Result<ProductReviewVO> getReviewDetail(@PathVariable Long reviewId) {
        ProductReviewVO detail = productReviewService.getAdminReviewDetail(reviewId);
        return Result.success(detail);
    }

    /**
     * 修改评价状态（隐藏/显示）
     */
    @PutMapping("/{reviewId}/status")
    public Result<Void> updateReviewStatus(@PathVariable Long reviewId,
                                           @RequestParam Integer status) {
        if (status == null || (status != 0 && status != 1)) {
            return Result.error(400, "status参数错误，仅支持0或1");
        }
        productReviewService.updateReviewStatus(reviewId, status);
        return Result.success("状态更新成功", null);
    }

    /**
     * 删除评价
     */
    @DeleteMapping("/{reviewId}")
    public Result<Void> deleteReview(@PathVariable Long reviewId) {
        productReviewService.deleteReview(reviewId);
        return Result.success("评价删除成功", null);
    }

    /**
     * 评价统计概览
     */
    @GetMapping("/statistics")
    public Result<Map<String, Object>> getReviewStatistics() {
        Map<String, Object> stats = productReviewService.getAdminReviewStatistics();
        return Result.success(stats);
    }
}

