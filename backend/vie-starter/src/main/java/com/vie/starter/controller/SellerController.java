package com.vie.starter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vie.service.common.Result;
import com.vie.service.dto.*;
import com.vie.service.service.ProductReviewService;
import com.vie.service.service.SellerInventoryService;
import com.vie.service.service.SellerOrderService;
import com.vie.service.service.SellerProductService;
import com.vie.service.service.SellerStatisticsService;
import com.vie.service.util.OssUtil;
import com.vie.service.vo.*;
import com.vie.starter.annotation.RequireLogin;
import com.vie.starter.annotation.RequireSeller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 卖家模块控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/seller")
@RequireLogin
@RequireSeller
public class SellerController {

    @Autowired
    private SellerProductService productService;

    @Autowired
    private SellerInventoryService inventoryService;

    @Autowired
    private SellerStatisticsService statisticsService;

    @Autowired
    private SellerOrderService orderService;

    @Autowired
    private ProductReviewService productReviewService;

    @Autowired
    private OssUtil ossUtil;

    // ==================== 图片上传 ====================

    /**
     * 上传商品图片
     */
    @PostMapping("/upload/image")
    public Result<UploadResultVO> uploadImage(@RequestParam("file") MultipartFile file) {
        String imageUrl = ossUtil.uploadFile(file, "products");
        return Result.success("上传成功", UploadResultVO.builder().url(imageUrl).build());
    }

    // ==================== 商品管理 ====================

    /**
     * 创建商品
     */
    @PostMapping("/products")
    public Result<Map<String, Long>> createProduct(@Valid @RequestBody SellerProductCreateDTO dto,
                                                    HttpServletRequest request) {
        Long sellerId = (Long) request.getAttribute("userId");
        Long productId = productService.createProduct(dto, sellerId);
        return Result.success("商品创建成功，等待审核", Map.of("productId", productId));
    }

    /**
     * 更新商品
     */
    @PutMapping("/products/{productId}")
    public Result<Void> updateProduct(@PathVariable Long productId,
                                      @Valid @RequestBody SellerProductUpdateDTO dto,
                                      HttpServletRequest request) {
        Long sellerId = (Long) request.getAttribute("userId");
        productService.updateProduct(productId, dto, sellerId);
        return Result.success("商品更新成功", null);
    }

    /**
     * 商品上下架
     */
    @PutMapping("/products/{productId}/status")
    public Result<Void> updateProductStatus(@PathVariable Long productId,
                                            @RequestBody Map<String, Integer> body,
                                            HttpServletRequest request) {
        Long sellerId = (Long) request.getAttribute("userId");
        Integer status = body.get("status");
        productService.updateProductStatus(productId, status, sellerId);
        return Result.success("操作成功", null);
    }

    /**
     * 删除商品
     */
    @DeleteMapping("/products/{productId}")
    public Result<Void> deleteProduct(@PathVariable Long productId,
                                      HttpServletRequest request) {
        Long sellerId = (Long) request.getAttribute("userId");
        productService.deleteProduct(productId, sellerId);
        return Result.success("商品删除成功", null);
    }

    /**
     * 获取商品列表
     */
    @GetMapping("/products")
    public Result<IPage<SellerProductVO>> getProductList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId,
            HttpServletRequest request) {

        Long sellerId = (Long) request.getAttribute("userId");

        SellerProductQueryDTO queryDTO = new SellerProductQueryDTO();
        queryDTO.setPageNum(pageNum);
        queryDTO.setPageSize(pageSize);
        queryDTO.setStatus(status);
        queryDTO.setKeyword(keyword);
        queryDTO.setCategoryId(categoryId);

        IPage<SellerProductVO> page = productService.getProductPage(queryDTO, sellerId);
        return Result.success(page);
    }

    /**
     * 获取商品详情
     */
    @GetMapping("/products/{productId}")
    public Result<SellerProductDetailVO> getProductDetail(@PathVariable Long productId,
                                                          HttpServletRequest request) {
        Long sellerId = (Long) request.getAttribute("userId");
        SellerProductDetailVO detail = productService.getProductDetail(productId, sellerId);
        return Result.success(detail);
    }

    // ==================== 库存管理 ====================

    /**
     * 获取库存列表
     */
    @GetMapping("/inventory")
    public Result<IPage<InventoryVO>> getInventoryList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) Boolean lowStock,
            HttpServletRequest request) {

        Long sellerId = (Long) request.getAttribute("userId");

        InventoryQueryDTO queryDTO = new InventoryQueryDTO();
        queryDTO.setPageNum(pageNum);
        queryDTO.setPageSize(pageSize);
        queryDTO.setProductId(productId);
        queryDTO.setLowStock(lowStock);

        IPage<InventoryVO> page = inventoryService.getInventoryPage(queryDTO, sellerId);
        return Result.success(page);
    }

    /**
     * 调整库存
     */
    @PutMapping("/inventory/{skuId}")
    public Result<StockAdjustResultVO> adjustStock(@PathVariable Long skuId,
                                                   @Valid @RequestBody StockAdjustDTO dto,
                                                   HttpServletRequest request) {
        Long sellerId = (Long) request.getAttribute("userId");
        StockAdjustResultVO result = inventoryService.adjustStock(skuId, dto, sellerId);
        return Result.success("库存调整成功", result);
    }

    // ==================== 销售统计 ====================

    /**
     * 获取销售统计
     */
    @GetMapping("/statistics/sales")
    public Result<SalesStatisticsVO> getSalesStatistics(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "DAY") String timeUnit,
            HttpServletRequest request) {

        Long sellerId = (Long) request.getAttribute("userId");

        SalesStatisticsQueryDTO queryDTO = new SalesStatisticsQueryDTO();
        queryDTO.setStartDate(startDate);
        queryDTO.setEndDate(endDate);
        queryDTO.setTimeUnit(timeUnit);

        SalesStatisticsVO statistics = statisticsService.getSalesStatistics(queryDTO, sellerId);
        return Result.success(statistics);
    }

    /**
     * 获取商品销量排行
     */
    @GetMapping("/statistics/products")
    public Result<List<ProductRankVO>> getProductRank(
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "sales") String orderBy,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            HttpServletRequest request) {

        Long sellerId = (Long) request.getAttribute("userId");
        List<ProductRankVO> rankList = statisticsService.getProductRank(limit, orderBy, startDate, endDate, sellerId);
        return Result.success(rankList);
    }

    /**
     * 获取收益统计
     */
    @GetMapping("/statistics/revenue")
    public Result<RevenueStatisticsVO> getRevenueStatistics(HttpServletRequest request) {
        Long sellerId = (Long) request.getAttribute("userId");
        RevenueStatisticsVO statistics = statisticsService.getRevenueStatistics(sellerId);
        return Result.success(statistics);
    }

/**
     * 获取商家订单概览统计（订单总数/待发货/已完成）
     */
    @GetMapping("/statistics/order-overview")
    public Result<SellerOrderOverviewVO> getOrderOverview(HttpServletRequest request) {
        Long sellerId = (Long) request.getAttribute("userId");
        SellerOrderOverviewVO overview = statisticsService.getOrderOverview(sellerId);
        return Result.success(overview);
    }

    /**
     * 获取订单状态分布
     */
    @GetMapping("/statistics/orders/status-distribution")
    @RequireLogin
    public Result<SellerOrderDistributionVO> getOrderStatusDistribution(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            HttpServletRequest request) {
        Long sellerId = (Long) request.getAttribute("userId");
        SellerOrderDistributionVO distribution = statisticsService.getOrderStatusDistribution(sellerId, startDate, endDate);
        return Result.success(distribution);
    }

    /**
     * 获取SKU销量排行
     */
    @GetMapping("/statistics/skus/rank")
    @RequireLogin
    public Result<List<SkuRankVO>> getSkuRank(
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "sales") String orderBy,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            HttpServletRequest request) {
        Long sellerId = (Long) request.getAttribute("userId");
        List<SkuRankVO> rankList = statisticsService.getSkuRank(limit, orderBy, startDate, endDate, sellerId);
        return Result.success(rankList);
    }

    /**
     * 获取库存健康概览
     */
    @GetMapping("/statistics/inventory/health")
    @RequireLogin
    public Result<InventoryHealthVO> getInventoryHealth(HttpServletRequest request) {
        Long sellerId = (Long) request.getAttribute("userId");
        InventoryHealthVO health = statisticsService.getInventoryHealth(sellerId);
        return Result.success(health);
    }

    // ==================== 订单管理 ====================

    /**
     * 获取商家订单列表
     */
    @GetMapping("/orders")
    public Result<IPage<SellerOrderVO>> getOrderList(
            SellerOrderQueryDTO queryDTO,
            HttpServletRequest request) {

        Long sellerId = (Long) request.getAttribute("userId");
        IPage<SellerOrderVO> page = orderService.getSellerOrderPage(queryDTO, sellerId);
        return Result.success(page);
    }

    /**
     * 获取商家订单详情
     */
    @GetMapping("/orders/{orderId}")
    public Result<SellerOrderVO> getOrderDetail(@PathVariable Long orderId,
                                                 HttpServletRequest request) {
        Long sellerId = (Long) request.getAttribute("userId");
        SellerOrderVO orderDetail = orderService.getSellerOrderDetail(orderId, sellerId);
        return Result.success(orderDetail);
    }

    /**
     * 获取商家订单统计
     */
    @GetMapping("/orders/count")
    public Result<SellerOrderCountVO> getOrderCount(HttpServletRequest request) {
        Long sellerId = (Long) request.getAttribute("userId");
        SellerOrderCountVO count = orderService.getSellerOrderCount(sellerId);
        return Result.success(count);
    }

    // ==================== 评价管理 ====================

    /**
     * 商家回复评价
     */
    @PutMapping("/reviews/{reviewId}/reply")
    public Result<Void> replyReview(@PathVariable Long reviewId,
                                    @RequestParam String replyContent,
                                    HttpServletRequest request) {
        Long sellerId = (Long) request.getAttribute("userId");
        productReviewService.replyReview(reviewId, replyContent, sellerId);
        return Result.success("回复成功", null);
    }

    /**
     * 查询商家商品的评价列表
     */
    @GetMapping("/reviews")
    public Result<IPage<ProductReviewVO>> getProductReviews(
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) Integer rating,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        if (productId == null) {
            return Result.error(400, "缺少必要参数：productId");
        }

        IPage<ProductReviewVO> reviewPage = productReviewService.getReviewPage(
                productId, rating, pageNum, pageSize);
        return Result.success(reviewPage);
    }
}
