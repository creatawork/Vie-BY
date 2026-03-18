package com.vie.service.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vie.service.dto.SellerProductCreateDTO;
import com.vie.service.dto.SellerProductQueryDTO;
import com.vie.service.dto.SellerProductUpdateDTO;
import com.vie.service.vo.SellerProductDetailVO;
import com.vie.service.vo.SellerProductVO;

/**
 * 卖家商品服务接口
 */
public interface SellerProductService {

    /**
     * 创建商品
     *
     * @param dto      商品创建DTO
     * @param sellerId 卖家ID
     * @return 商品ID
     */
    Long createProduct(SellerProductCreateDTO dto, Long sellerId);

    /**
     * 更新商品
     *
     * @param productId 商品ID
     * @param dto       商品更新DTO
     * @param sellerId  卖家ID
     */
    void updateProduct(Long productId, SellerProductUpdateDTO dto, Long sellerId);

    /**
     * 更新商品状态（上下架）
     *
     * @param productId 商品ID
     * @param status    状态：1-上架, 2-下架
     * @param sellerId  卖家ID
     */
    void updateProductStatus(Long productId, Integer status, Long sellerId);

    /**
     * 删除商品
     *
     * @param productId 商品ID
     * @param sellerId  卖家ID
     */
    void deleteProduct(Long productId, Long sellerId);

    /**
     * 分页查询卖家商品列表
     *
     * @param queryDTO 查询条件
     * @param sellerId 卖家ID
     * @return 商品分页列表
     */
    IPage<SellerProductVO> getProductPage(SellerProductQueryDTO queryDTO, Long sellerId);

    /**
     * 获取商品详情
     *
     * @param productId 商品ID
     * @param sellerId  卖家ID
     * @return 商品详情
     */
    SellerProductDetailVO getProductDetail(Long productId, Long sellerId);
}
