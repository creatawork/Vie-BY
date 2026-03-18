package com.vie.starter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vie.service.common.Result;
import com.vie.service.constant.RoleConstant;
import com.vie.service.dto.ProductAuditDTO;
import com.vie.service.dto.ProductCreateDTO;
import com.vie.service.dto.ProductQueryDTO;
import com.vie.service.dto.ProductUpdateDTO;
import com.vie.service.service.BrowseHistoryService;
import com.vie.service.service.ProductCollectionService;
import com.vie.service.service.ProductService;
import com.vie.service.vo.ProductDetailVO;
import com.vie.service.vo.ProductVO;
import com.vie.starter.annotation.RequireLogin;
import com.vie.starter.annotation.RequireRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCollectionService collectionService;

    @Autowired
    private BrowseHistoryService browseHistoryService;

    /**
     * 创建商品（商家）
     */
    @RequireLogin
    @PostMapping
    public Result<Long> createProduct(@Valid @RequestBody ProductCreateDTO dto,
                                      HttpServletRequest request) {
        Long sellerId = (Long) request.getAttribute("userId");
        Long productId = productService.createProduct(dto, sellerId);
        return Result.success("商品创建成功", productId);
    }

    /**
     * 更新商品信息（商家）
     */
    @RequireLogin
    @PutMapping("/{productId}")
    public Result<Void> updateProduct(@PathVariable("productId") Long productId,
                                      @Valid @RequestBody ProductUpdateDTO dto,
                                      HttpServletRequest request) {
        Long sellerId = (Long) request.getAttribute("userId");
        productService.updateProduct(productId, dto, sellerId);
        return Result.success("商品更新成功", null);
    }

    /**
     * 删除商品（商家）
     */
    @RequireLogin
    @DeleteMapping("/{productId}")
    public Result<Void> deleteProduct(@PathVariable("productId") Long productId,
                                      HttpServletRequest request) {
        Long sellerId = (Long) request.getAttribute("userId");
        productService.deleteProduct(productId, sellerId);
        return Result.success("商品删除成功", null);
    }

    /**
     * 分页查询商品列表
     */
    @PostMapping("/search")
    public Result<IPage<ProductVO>> getProductPage(@RequestBody ProductQueryDTO queryDTO) {
        IPage<ProductVO> productPage = productService.getProductPage(queryDTO);
        return Result.success(productPage);
    }

    /**
     * 获取商品详情
     */
    @GetMapping("/{productId}")
    public Result<ProductDetailVO> getProductDetail(@PathVariable("productId") Long productId,
                                                     HttpServletRequest request) {
        // 获取商品详情
        ProductDetailVO productDetail = productService.getProductDetail(productId);

        // 记录浏览历史（仅登录用户）
        Long userId = (Long) request.getAttribute("userId");
        if (userId != null) {
            try {
                browseHistoryService.recordBrowse(userId, productId);
            } catch (Exception e) {
                // 记录失败不影响主流程
                log.error("记录浏览历史失败: userId={}, productId={}", userId, productId, e);
            }
        }

        return Result.success(productDetail);
    }

    /**
     * 获取推荐商品
     */
    @GetMapping("/recommended")
    public Result<List<ProductVO>> getRecommendedProducts(
            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        List<ProductVO> products = productService.getRecommendedProducts(limit);
        return Result.success(products);
    }

    /**
     * 获取新品列表
     */
    @GetMapping("/new")
    public Result<List<ProductVO>> getNewProducts(
            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        List<ProductVO> products = productService.getNewProducts(limit);
        return Result.success(products);
    }

    /**
     * 获取热销商品
     */
    @GetMapping("/hot")
    public Result<List<ProductVO>> getHotProducts(
            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        List<ProductVO> products = productService.getHotProducts(limit);
        return Result.success(products);
    }

    /**
     * 上架商品（商家）
     */
    @RequireLogin
    @PutMapping("/{productId}/publish")
    public Result<Void> publishProduct(@PathVariable("productId") Long productId,
                                       HttpServletRequest request) {
        Long sellerId = (Long) request.getAttribute("userId");
        productService.publishProduct(productId, sellerId);
        return Result.success("商品上架成功", null);
    }

    /**
     * 下架商品（商家）
     */
    @RequireLogin
    @PutMapping("/{productId}/unpublish")
    public Result<Void> unpublishProduct(@PathVariable("productId") Long productId,
                                         HttpServletRequest request) {
        Long sellerId = (Long) request.getAttribute("userId");
        productService.unpublishProduct(productId, sellerId);
        return Result.success("商品下架成功", null);
    }

    /**
     * 商品审核（管理员）
     */
    @RequireRole(RoleConstant.ADMIN_ROLE_CODE)
    @PutMapping("/{productId}/audit")
    public Result<Void> auditProduct(@PathVariable("productId") Long productId,
                                     @Valid @RequestBody ProductAuditDTO dto) {
        productService.auditProduct(productId, dto.getStatus(), dto.getAuditRemark());
        return Result.success("审核成功", null);
    }

    /**
     * 收藏商品
     */
    @RequireLogin
    @PostMapping("/{productId}/collect")
    public Result<Void> collectProduct(@PathVariable("productId") Long productId,
                                       HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        collectionService.collectProduct(productId, userId);
        return Result.success("收藏成功", null);
    }

    /**
     * 取消收藏
     */
    @RequireLogin
    @DeleteMapping("/{productId}/uncollect")
    public Result<Void> uncollectProduct(@PathVariable("productId") Long productId,
                                         HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        collectionService.uncollectProduct(productId, userId);
        return Result.success("取消收藏成功", null);
    }

    /**
     * 检查收藏状态
     */
    @RequireLogin
    @GetMapping("/{productId}/is-collected")
    public Result<Boolean> isCollected(@PathVariable("productId") Long productId,
                                       HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Boolean collected = collectionService.isCollected(productId, userId);
        return Result.success(collected);
    }
}
