package com.vie.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vie.db.entity.TransactionRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 交易记录Mapper接口
 */
@Mapper
public interface TransactionRecordMapper extends BaseMapper<TransactionRecord> {

    /**
     * 分页查询用户交易记录
     *
     * @param page 分页参数
     * @param userId 用户ID
     * @param accountType 账户类型：1-用户钱包，2-商家账户
     * @param type 交易类型（可选）
     * @param startTime 开始时间（可选）
     * @param endTime 结束时间（可选）
     * @return 交易记录分页列表
     */
    IPage<Map<String, Object>> selectPageByUserId(
            Page<Map<String, Object>> page,
            @Param("userId") Long userId,
            @Param("accountType") Integer accountType,
            @Param("type") String type,
            @Param("startTime") String startTime,
            @Param("endTime") String endTime
    );
}
