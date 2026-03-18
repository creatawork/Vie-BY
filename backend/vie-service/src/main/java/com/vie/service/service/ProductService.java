package com.vie.service.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vie.service.dto.ProductCreateDTO;
import com.vie.service.dto.ProductQueryDTO;
import com.vie.service.dto.ProductUpdateDTO;
import com.vie.service.vo.ProductDetailVO;
import com.vie.service.vo.ProductVO;

import java.util.List;

/**
 * 商品服务接口
 */
public interface ProductService {

    /**
     * 创建商品
     *
     * @param dto 商品创建DTO
     * @param sellerId 商家ID
     * @return 商品ID
     */
    Long createProduct(ProductCreateDTO dto, Long sellerId);

    /**
     * 更新商品信息
     *
     * @param productId 商品ID
     * @param dto 商品更新DTO
     * @param sellerId 商家ID（用于权限校验）
     */
    void updateProduct(Long productId, ProductUpdateDTO dto, Long sellerId);

    /**
     * 删除商品（逻辑删除）
     *
     * @param productId 商品ID
     * @param sellerId 商家ID（用于权限校验）
     */
    void deleteProduct(Long productId, Long sellerId);

    /**
     * 分页查询商品列表
     *
     * @param queryDTO 查询条件
     * @return 商品分页列表
     */
    IPage<ProductVO> getProductPage(ProductQueryDTO queryDTO);

    /**
     * 获取商品详情
     *
     * @param productId 商品ID
     * @return 商品详情
     */
    ProductDetailVO getProductDetail(Long productId);

    /**
     * 获取推荐商品
     *
     * @param limit 数量限制
     * @return 推荐商品列表
     */
    List<ProductVO> getRecommendedProducts(Integer limit);

    /**
     * 获取新品列表
     *
     * @param limit 数量限制
     * @return 新品列表
     */
    List<ProductVO> getNewProducts(Integer limit);

    /**
     * 获取热销商品
     *
     * @param limit 数量限制
     * @return 热销商品列表
     */
    List<ProductVO> getHotProducts(Integer limit);

    /**
     * 上架商品
     *
     * @param productId 商品ID
     * @param sellerId 商家ID（用于权限校验）
     */
    void publishProduct(Long productId, Long sellerId);

    /**
     * 下架商品
     *
     * @param productId 商品ID
     * @param sellerId 商家ID（用于权限校验）
     */
    void unpublishProduct(Long productId, Long sellerId);

    /**
     * 商品审核（管理员）
     *
     * @param productId 商品ID
     * @param status 审核状态：1-通过，3-不通过
     * @param remark 审核备注
     */
    void auditProduct(Long productId, Integer status, String remark);

    /**
     * 全量同步商品向量（仅同步上架且未删除的商品）
     *
     * @return 同步数量
     */
    int syncProductVectors();
}
