package com.vie.service.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vie.service.dto.CollectionQueryDTO;
import com.vie.service.vo.ProductCollectionVO;

/**
 * 商品收藏服务接口
 */
public interface ProductCollectionService {

    /**
     * 收藏商品
     *
     * @param productId 商品ID
     * @param userId 用户ID
     */
    void collectProduct(Long productId, Long userId);

    /**
     * 取消收藏
     *
     * @param productId 商品ID
     * @param userId 用户ID
     */
    void uncollectProduct(Long productId, Long userId);

    /**
     * 检查是否已收藏
     *
     * @param productId 商品ID
     * @param userId 用户ID
     * @return true-已收藏，false-未收藏
     */
    Boolean isCollected(Long productId, Long userId);

    /**
     * 分页查询用户收藏列表
     *
     * @param queryDTO 查询条件
     * @param userId 用户ID
     * @return 收藏商品列表
     */
    IPage<ProductCollectionVO> getCollectionPage(CollectionQueryDTO queryDTO, Long userId);

    /**
     * 获取用户收藏商品总数
     *
     * @param userId 用户ID
     * @return 收藏商品总数
     */
    Long getCollectionCount(Long userId);

    /**
     * 清空用户收藏夹
     *
     * @param userId 用户ID
     * @return 清空的收藏数量
     */
    Integer clearCollections(Long userId);
}
