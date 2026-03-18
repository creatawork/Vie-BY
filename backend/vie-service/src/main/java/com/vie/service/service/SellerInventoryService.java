package com.vie.service.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vie.service.dto.InventoryQueryDTO;
import com.vie.service.dto.StockAdjustDTO;
import com.vie.service.vo.InventoryVO;
import com.vie.service.vo.StockAdjustResultVO;

/**
 * 卖家库存管理服务接口
 */
public interface SellerInventoryService {

    /**
     * 分页查询库存列表
     *
     * @param queryDTO 查询条件
     * @param sellerId 卖家ID
     * @return 库存分页列表
     */
    IPage<InventoryVO> getInventoryPage(InventoryQueryDTO queryDTO, Long sellerId);

    /**
     * 调整库存
     *
     * @param skuId    SKU ID
     * @param dto      调整参数
     * @param sellerId 卖家ID
     * @return 调整结果
     */
    StockAdjustResultVO adjustStock(Long skuId, StockAdjustDTO dto, Long sellerId);
}
