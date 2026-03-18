package com.vie.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vie.db.entity.StockAdjustment;
import org.apache.ibatis.annotations.Mapper;

/**
 * 库存调整记录Mapper接口
 */
@Mapper
public interface StockAdjustmentMapper extends BaseMapper<StockAdjustment> {
}
