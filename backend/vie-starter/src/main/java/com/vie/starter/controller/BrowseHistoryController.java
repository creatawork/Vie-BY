package com.vie.starter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vie.service.common.Result;
import com.vie.service.dto.BrowseHistoryQueryDTO;
import com.vie.service.service.BrowseHistoryService;
import com.vie.service.vo.BrowseHistoryVO;
import com.vie.starter.annotation.RequireLogin;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 浏览历史控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/browse-history")
public class BrowseHistoryController {

    @Autowired
    private BrowseHistoryService browseHistoryService;

    /**
     * 手动记录浏览历史（通常由商品详情自动记录）
     */
    @RequireLogin
    @PostMapping
    public Result<Void> recordBrowse(@RequestParam("productId") Long productId,
                                     HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        browseHistoryService.recordBrowse(userId, productId);
        return Result.success("记录成功", null);
    }

    /**
     * 获取浏览历史列表
     */
    @RequireLogin
    @GetMapping
    public Result<IPage<BrowseHistoryVO>> getBrowseHistory(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            HttpServletRequest request) {

        Long userId = (Long) request.getAttribute("userId");

        BrowseHistoryQueryDTO queryDTO = new BrowseHistoryQueryDTO();
        queryDTO.setPageNum(pageNum);
        queryDTO.setPageSize(pageSize);

        IPage<BrowseHistoryVO> historyPage =
                browseHistoryService.getBrowseHistoryPage(queryDTO, userId);

        return Result.success(historyPage);
    }

    /**
     * 删除浏览记录
     */
    @RequireLogin
    @DeleteMapping("/{historyId}")
    public Result<Void> deleteBrowseHistory(@PathVariable("historyId") Long historyId,
                                            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        browseHistoryService.deleteBrowseHistory(historyId, userId);
        return Result.success("删除成功", null);
    }

    /**
     * 清空浏览历史
     */
    @RequireLogin
    @DeleteMapping("/clear")
    public Result<Void> clearBrowseHistory(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        browseHistoryService.clearBrowseHistory(userId);
        return Result.success("清空成功", null);
    }
}
