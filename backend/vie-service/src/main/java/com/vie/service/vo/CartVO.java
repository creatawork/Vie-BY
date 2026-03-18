package com.vie.service.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 购物车汇总VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartVO {

    /**
     * 购物车商品列表
     */
    private List<CartItemVO> items;

    /**
     * 商品总数量
     */
    private Integer totalQuantity;

    /**
     * 选中商品数量
     */
    private Integer selectedQuantity;

    /**
     * 选中商品总金额
     */
    private BigDecimal selectedAmount;

    /**
     * 是否全选
     */
    private Boolean allSelected;

    /**
     * 有效商品数量
     */
    private Integer validCount;

    /**
     * 无效商品数量
     */
    private Integer invalidCount;
}
