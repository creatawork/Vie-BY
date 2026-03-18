package com.vie.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vie.db.entity.ProductImage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 商品图片Mapper接口
 */
@Mapper
public interface ProductImageMapper extends BaseMapper<ProductImage> {

    /**
     * 根据商品ID查询图片列表
     *
     * @param productId 商品ID
     * @return 图片列表
     */
    @Select("SELECT * FROM product_image WHERE product_id = #{productId} AND is_deleted = 0 ORDER BY sort_order ASC")
    List<ProductImage> selectByProductId(@Param("productId") Long productId);

    /**
     * 根据商品ID和图片类型查询图片列表
     *
     * @param productId 商品ID
     * @param imageType 图片类型：1-商品图，2-详情图
     * @return 图片列表
     */
    @Select("SELECT * FROM product_image WHERE product_id = #{productId} AND image_type = #{imageType} " +
            "AND is_deleted = 0 ORDER BY sort_order ASC")
    List<ProductImage> selectByProductIdAndType(@Param("productId") Long productId,
                                                 @Param("imageType") Integer imageType);
}
