package com.vie.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * 创建订单DTO
 */
@Data
public class OrderCreateDTO {

    /**
     * 购物车ID列表（从购物车下单时使用）
     */
    private List<Long> cartItemIds;

    /**
     * 直接购买信息（直接购买时使用）
     */
    private DirectBuyItem directBuyItem;

    /**
     * 收货人姓名
     */
    @NotBlank(message = "收货人姓名不能为空")
    private String receiverName;

    /**
     * 收货人电话
     */
    @NotBlank(message = "收货人电话不能为空")
    private String receiverPhone;

    /**
     * 收货地址
     */
    /**
     * 收货地址
     */
    @NotBlank(message = "收货地址不能为空")
    private String receiverAddress;

    /**
     * 收货省份
     */
    private String receiverProvince;

    /**
     * 收货城市
     */
    private String receiverCity;

    /**
     * 收货区/县
     */
    private String receiverDistrict;

    /**
     * 订单备注
     */
    private String remark;

    /**
     * 直接购买商品信息
     */
    @Data
    public static class DirectBuyItem {
        /**
         * 商品ID
         */
        private Long productId;

        /**
         * SKU ID
         */
        private Long skuId;

        /**
         * 数量
         */
        private Integer quantity;
    }
}
