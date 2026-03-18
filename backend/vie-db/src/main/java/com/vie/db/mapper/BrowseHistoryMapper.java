package com.vie.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vie.db.entity.BrowseHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 浏览历史 Mapper
 */
@Mapper
public interface BrowseHistoryMapper extends BaseMapper<BrowseHistory> {

    /**
     * 分页查询用户浏览历史（关联商品信息）
     *
     * @param page 分页对象
     * @param userId 用户ID
     * @return 浏览历史列表（包含商品详细信息）
     */
    IPage<Map<String, Object>> selectBrowseHistoryPageWithProduct(
            Page<?> page,
            @Param("userId") Long userId
    );
}
