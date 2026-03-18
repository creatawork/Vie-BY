package com.vie.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vie.db.entity.CartItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 购物车Mapper接口
 */
@Mapper
public interface CartItemMapper extends BaseMapper<CartItem> {

    /**
     * 查询用户购物车列表（关联商品和SKU信息）
     *
     * @param userId 用户ID
     * @return 购物车列表
     */
    List<Map<String, Object>> selectCartListWithProduct(@Param("userId") Long userId);

    /**
     * 根据用户ID和SKU ID列表查询购物车
     *
     * @param userId 用户ID
     * @param skuIds SKU ID列表
     * @return 购物车列表
     */
    List<CartItem> selectByUserIdAndSkuIds(@Param("userId") Long userId,
                                            @Param("skuIds") List<Long> skuIds);

    /**
     * 批量删除购物车商品（逻辑删除）
     *
     * @param userId 用户ID
     * @param ids 购物车ID列表
     * @return 影响行数
     */
    int batchDelete(@Param("userId") Long userId, @Param("ids") List<Long> ids);

    /**
     * 清空用户购物车
     *
     * @param userId 用户ID
     * @return 影响行数
     */
    int clearByUserId(@Param("userId") Long userId);

    /**
     * 更新选中状态
     *
     * @param userId 用户ID
     * @param selected 选中状态
     * @return 影响行数
     */
    int updateSelectedByUserId(@Param("userId") Long userId, @Param("selected") Integer selected);

    /**
     * 根据用户ID和SKU ID查询购物车（包括已删除的记录，绕过逻辑删除）
     * 使用@Select注解直接执行原生SQL，避免被MyBatis-Plus逻辑删除拦截
     *
     * @param userId 用户ID
     * @param skuId SKU ID
     * @return 购物车记录
     */
    @Select("SELECT id, user_id, product_id, sku_id, quantity, selected, " +
            "create_time, update_time, is_deleted " +
            "FROM cart_item " +
            "WHERE user_id = #{userId} AND sku_id = #{skuId} " +
            "LIMIT 1")
    CartItem selectByUserIdAndSkuIdIncludeDeleted(@Param("userId") Long userId, @Param("skuId") Long skuId);

    /**
     * 恢复逻辑删除的购物车项
     */
    @org.apache.ibatis.annotations.Update("UPDATE cart_item SET is_deleted = 0, quantity = #{quantity}, selected = 1, update_time = NOW() WHERE id = #{id}")
    int restoreItem(@Param("id") Long id, @Param("quantity") Integer quantity);
}
