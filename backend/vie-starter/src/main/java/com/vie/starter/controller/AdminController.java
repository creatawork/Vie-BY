package com.vie.starter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vie.service.common.Result;
import com.vie.service.constant.RoleConstant;
import com.vie.service.dto.AdminRechargeDTO;
import com.vie.service.dto.ProductQueryDTO;
import com.vie.service.service.PaymentService;
import com.vie.service.service.ProductService;
import com.vie.service.vo.ProductVO;
import com.vie.starter.annotation.RequireLogin;
import com.vie.starter.annotation.RequireRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 管理员控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ProductService productService;

    /**
     * 获取商品列表（管理员）
     * 默认查询待审核商品
     */
    @RequireLogin
    @RequireRole(RoleConstant.ADMIN_ROLE_CODE)
    @GetMapping("/products")
    public Result<IPage<ProductVO>> getProductList(
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {

        ProductQueryDTO queryDTO = new ProductQueryDTO();
        queryDTO.setStatus(status != null ? status : 0);
        queryDTO.setCategoryId(categoryId);
        queryDTO.setKeyword(keyword);
        queryDTO.setPageNum(pageNum);
        queryDTO.setPageSize(pageSize);

        IPage<ProductVO> page = productService.getProductPage(queryDTO);
        return Result.success(page);
    }

    /**
     * 管理员为用户充值
     */
    @RequireLogin
    @PostMapping("/wallet/recharge")
    public Result<Void> adminRecharge(@Valid @RequestBody AdminRechargeDTO dto,
                                       HttpServletRequest request) {
        Long adminId = (Long) request.getAttribute("userId");
        paymentService.adminRecharge(dto.getUserId(), dto.getAmount(), adminId);
        return Result.success("充值成功", null);
    }

    /**
     * 手动触发商品向量全量同步
     */
    @RequireLogin
    @RequireRole(RoleConstant.ADMIN_ROLE_CODE)
    @PostMapping("/products/sync-vectors")
    public Result<Integer> syncProductVectors() {
        int count = productService.syncProductVectors();
        return Result.success("商品向量同步完成", count);
    }
}
