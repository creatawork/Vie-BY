package com.vie.service.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vie.service.dto.BrowseHistoryQueryDTO;
import com.vie.service.vo.BrowseHistoryVO;

/**
 * 浏览历史服务接口
 */
public interface BrowseHistoryService {

    /**
     * 记录浏览历史
     *
     * @param userId 用户ID
     * @param productId 商品ID
     */
    void recordBrowse(Long userId, Long productId);

    /**
     * 分页查询用户浏览历史
     *
     * @param queryDTO 查询条件
     * @param userId 用户ID
     * @return 浏览历史列表
     */
    IPage<BrowseHistoryVO> getBrowseHistoryPage(BrowseHistoryQueryDTO queryDTO, Long userId);

    /**
     * 删除浏览记录
     *
     * @param historyId 浏览记录ID
     * @param userId 用户ID
     */
    void deleteBrowseHistory(Long historyId, Long userId);

    /**
     * 清空浏览历史
     *
     * @param userId 用户ID
     */
    void clearBrowseHistory(Long userId);
}
