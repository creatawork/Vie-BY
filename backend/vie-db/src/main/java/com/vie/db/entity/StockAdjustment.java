package com.vie.db.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 库存调整记录实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("stock_adjustment")
public class StockAdjustment extends BaseEntity {

    /**
     * SKU ID
     */
    private Long skuId;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 商家ID
     */
    private Long sellerId;

    /**
     * 调整类型：ADD-增加,REDUCE-减少,SET-设置
     */
    private String adjustType;

    /**
     * 调整前库存
     */
    private Integer beforeStock;

    /**
     * 调整数量
     */
    private Integer adjustQuantity;

    /**
     * 调整后库存
     */
    private Integer afterStock;

    /**
     * 备注
     */
    private String remark;
}
