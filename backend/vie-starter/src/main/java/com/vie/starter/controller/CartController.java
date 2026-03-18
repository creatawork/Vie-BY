package com.vie.starter.controller;

import com.vie.service.common.Result;
import com.vie.service.dto.CartAddDTO;
import com.vie.service.dto.CartUpdateDTO;
import com.vie.service.service.CartService;
import com.vie.service.vo.CartVO;
import com.vie.starter.annotation.RequireLogin;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 购物车控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 添加商品到购物车
     */
    @RequireLogin
    @PostMapping("/items")
    public Result<Void> addToCart(@Valid @RequestBody CartAddDTO dto,
                                   HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        cartService.addToCart(dto, userId);
        return Result.success("添加成功", null);
    }

    /**
     * 获取购物车列表
     */
    @RequireLogin
    @GetMapping
    public Result<CartVO> getCart(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        CartVO cart = cartService.getCart(userId);
        return Result.success(cart);
    }

    /**
     * 更新购物车商品数量
     */
    @RequireLogin
    @PutMapping("/items/{cartItemId}")
    public Result<Void> updateQuantity(@PathVariable("cartItemId") Long cartItemId,
                                        @Valid @RequestBody CartUpdateDTO dto,
                                        HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        cartService.updateQuantity(cartItemId, dto, userId);
        return Result.success("更新成功", null);
    }

    /**
     * 删除购物车商品
     */
    @RequireLogin
    @DeleteMapping("/items/{cartItemId}")
    public Result<Void> deleteCartItem(@PathVariable("cartItemId") Long cartItemId,
                                        HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        cartService.deleteCartItem(cartItemId, userId);
        return Result.success("删除成功", null);
    }

    /**
     * 清空购物车
     */
    @RequireLogin
    @DeleteMapping("/clear")
    public Result<Void> clearCart(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        cartService.clearCart(userId);
        return Result.success("清空成功", null);
    }

    /**
     * 更新商品选中状态
     */
    @RequireLogin
    @PutMapping("/items/{cartItemId}/select")
    public Result<Void> updateSelected(@PathVariable("cartItemId") Long cartItemId,
                                        @RequestParam("selected") Boolean selected,
                                        HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        cartService.updateSelected(cartItemId, selected, userId);
        return Result.success("更新成功", null);
    }

    /**
     * 全选/取消全选
     */
    @RequireLogin
    @PutMapping("/select-all")
    public Result<Void> selectAll(@RequestParam("selected") Boolean selected,
                                   HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        cartService.selectAll(selected, userId);
        return Result.success("更新成功", null);
    }

    /**
     * 获取购物车商品数量
     */
    @RequireLogin
    @GetMapping("/count")
    public Result<Integer> getCartCount(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Integer count = cartService.getCartCount(userId);
        return Result.success(count);
    }
}
