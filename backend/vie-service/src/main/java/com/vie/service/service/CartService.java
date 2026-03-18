package com.vie.service.service;

import com.vie.service.dto.CartAddDTO;
import com.vie.service.dto.CartUpdateDTO;
import com.vie.service.vo.CartVO;

/**
 * 购物车服务接口
 */
public interface CartService {

    /**
     * 添加商品到购物车
     *
     * @param dto 添加购物车DTO
     * @param userId 用户ID
     */
    void addToCart(CartAddDTO dto, Long userId);

    /**
     * 获取购物车列表
     *
     * @param userId 用户ID
     * @return 购物车信息
     */
    CartVO getCart(Long userId);

    /**
     * 更新购物车商品数量
     *
     * @param cartItemId 购物车ID
     * @param dto 更新DTO
     * @param userId 用户ID
     */
    void updateQuantity(Long cartItemId, CartUpdateDTO dto, Long userId);

    /**
     * 删除购物车商品
     *
     * @param cartItemId 购物车ID
     * @param userId 用户ID
     */
    void deleteCartItem(Long cartItemId, Long userId);

    /**
     * 清空购物车
     *
     * @param userId 用户ID
     */
    void clearCart(Long userId);

    /**
     * 更新商品选中状态
     *
     * @param cartItemId 购物车ID
     * @param selected 是否选中
     * @param userId 用户ID
     */
    void updateSelected(Long cartItemId, Boolean selected, Long userId);

    /**
     * 全选/取消全选
     *
     * @param selected 是否选中
     * @param userId 用户ID
     */
    void selectAll(Boolean selected, Long userId);

    /**
     * 获取购物车商品数量
     *
     * @param userId 用户ID
     * @return 商品数量
     */
    Integer getCartCount(Long userId);
}
