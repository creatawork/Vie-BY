package com.vie.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vie.db.entity.ProductCollection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 商品收藏 Mapper
 */
@Mapper
public interface ProductCollectionMapper extends BaseMapper<ProductCollection> {

    /**
     * 分页查询用户收藏列表（关联商品信息）
     *
     * @param page 分页对象
     * @param userId 用户ID
     * @return 收藏商品列表（包含商品详细信息）
     */
    IPage<Map<String, Object>> selectCollectionPageWithProduct(
            Page<?> page,
            @Param("userId") Long userId
    );
}
