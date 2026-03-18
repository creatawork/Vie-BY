package com.vie.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vie.db.entity.ProductSku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 商品SKUMapper接口
 */
@Mapper
public interface ProductSkuMapper extends BaseMapper<ProductSku> {

    /**
     * 根据商品ID查询SKU列表
     *
     * @param productId 商品ID
     * @return SKU列表
     */
    @Select("SELECT * FROM product_sku WHERE product_id = #{productId} ORDER BY id ASC")
    List<ProductSku> selectByProductId(@Param("productId") Long productId);

    /**
     * 根据SKU编码查询SKU
     *
     * @param skuCode SKU编码
     * @return SKU信息
     */
    @Select("SELECT * FROM product_sku WHERE sku_code = #{skuCode}")
    ProductSku selectBySkuCode(@Param("skuCode") String skuCode);

    /**
     * 扣减库存（乐观锁）
     *
     * @param skuId SKU ID
     * @param quantity 扣减数量
     * @return 影响行数
     */
    @Update("UPDATE product_sku SET stock = stock - #{quantity}, sales_volume = sales_volume + #{quantity} " +
            "WHERE id = #{skuId} AND stock >= #{quantity} AND is_deleted = 0")
    int decreaseStock(@Param("skuId") Long skuId, @Param("quantity") Integer quantity);

    /**
     * 增加库存（用于订单取消、退货）
     *
     * @param skuId SKU ID
     * @param quantity 增加数量
     * @return 影响行数
     */
    @Update("UPDATE product_sku SET stock = stock + #{quantity}, sales_volume = sales_volume - #{quantity} " +
            "WHERE id = #{skuId} AND is_deleted = 0")
    int increaseStock(@Param("skuId") Long skuId, @Param("quantity") Integer quantity);
}
