package com.vie.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vie.db.entity.*;
import com.vie.db.mapper.*;
import com.vie.service.common.ResultCode;
import com.vie.service.dto.ProductCreateDTO;
import com.vie.service.dto.ProductQueryDTO;
import com.vie.service.dto.ProductUpdateDTO;
import com.vie.service.exception.BusinessException;
import com.vie.service.service.ProductService;
import com.vie.service.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 商品服务实现类
 */
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    private ProductImageMapper productImageMapper;

    @Autowired
    private ProductReviewMapper productReviewMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private VectorStore vectorStore;

    @Value("${spring.ai.vectorstore.pgvector.table-name:vector_store}")
    private String vectorStoreTableName;

    @Autowired
    @Qualifier("pgvectorDataSource")
    private DataSource pgvectorDataSource;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createProduct(ProductCreateDTO dto, Long sellerId) {
        // 1. 验证分类是否存在
        Category category = categoryMapper.selectById(dto.getCategoryId());
        if (category == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "商品分类不存在");
        }

        // 2. 创建商品
        Product product = new Product();
        BeanUtils.copyProperties(dto, product);
        product.setSellerId(sellerId);
        product.setStatus(0); // 待审核
        product.setSalesVolume(0);
        product.setViewCount(0);

        // 设置默认值
        if (product.getIsRecommended() == null) {
            product.setIsRecommended(0);
        }
        if (product.getIsNew() == null) {
            product.setIsNew(0);
        }
        if (product.getIsHot() == null) {
            product.setIsHot(0);
        }

        productMapper.insert(product);

        log.info("商品创建成功：productId={}, sellerId={}, productName={}",
                 product.getId(), sellerId, product.getProductName());

        return product.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProduct(Long productId, ProductUpdateDTO dto, Long sellerId) {
        // 1. 查询商品并验证权限
        Product product = productMapper.selectById(productId);
        if (product == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "商品不存在");
        }
        if (!product.getSellerId().equals(sellerId)) {
            throw new BusinessException(ResultCode.FORBIDDEN.getCode(), "无权修改此商品");
        }

        // 2. 更新商品信息
        if (dto.getCategoryId() != null) {
            Category category = categoryMapper.selectById(dto.getCategoryId());
            if (category == null) {
                throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "商品分类不存在");
            }
            product.setCategoryId(dto.getCategoryId());
        }
        if (StringUtils.hasText(dto.getProductName())) {
            product.setProductName(dto.getProductName());
        }
        if (StringUtils.hasText(dto.getMainImage())) {
            product.setMainImage(dto.getMainImage());
        }
        if (StringUtils.hasText(dto.getDescription())) {
            product.setDescription(dto.getDescription());
        }
        if (StringUtils.hasText(dto.getDetail())) {
            product.setDetail(dto.getDetail());
        }
        if (dto.getOriginalPrice() != null) {
            product.setOriginalPrice(dto.getOriginalPrice());
        }
        if (dto.getCurrentPrice() != null) {
            product.setCurrentPrice(dto.getCurrentPrice());
        }
        if (dto.getStock() != null) {
            product.setStock(dto.getStock());
        }
        if (dto.getStatus() != null) {
            product.setStatus(dto.getStatus());
        }
        if (dto.getIsRecommended() != null) {
            product.setIsRecommended(dto.getIsRecommended());
        }
        if (dto.getIsNew() != null) {
            product.setIsNew(dto.getIsNew());
        }
        if (dto.getIsHot() != null) {
            product.setIsHot(dto.getIsHot());
        }

        productMapper.updateById(product);

        if (product.getStatus() != null && product.getStatus() == 1) {
            upsertProductVector(product);
        }

        log.info("商品更新成功：productId={}, sellerId={}", productId, sellerId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteProduct(Long productId, Long sellerId) {
        // 查询商品并验证权限
        Product product = productMapper.selectById(productId);
        if (product == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "商品不存在");
        }
        if (!product.getSellerId().equals(sellerId)) {
            throw new BusinessException(ResultCode.FORBIDDEN.getCode(), "无权删除此商品");
        }

        // 逻辑删除
        productMapper.deleteById(productId);

        log.info("商品删除成功：productId={}, sellerId={}", productId, sellerId);
    }

    @Override
    public IPage<ProductVO> getProductPage(ProductQueryDTO queryDTO) {
        Page<Product> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());

        // 查询商品分页
        IPage<Product> productPage = productMapper.selectProductPage(
                page,
                queryDTO.getCategoryId(),
                queryDTO.getKeyword(),
                queryDTO.getStatus()
        );

        // 转换为VO
        IPage<ProductVO> result = productPage.convert(product -> {
            ProductVO vo = new ProductVO();
            BeanUtils.copyProperties(product, vo);

            // 设置分类名称
            if (product.getCategory() != null) {
                vo.setCategoryName(product.getCategory().getCategoryName());
            }

            // 查询评价统计
            Double avgRating = productReviewMapper.calculateAvgRating(product.getId());
            Integer reviewCount = productReviewMapper.countByProductId(product.getId());
            vo.setAvgRating(avgRating);
            vo.setReviewCount(reviewCount);

            return vo;
        });

        return result;
    }

    @Override
    public ProductDetailVO getProductDetail(Long productId) {
        // 1. 查询商品详情（包含分类、图片、SKU）
        Product product = productMapper.selectProductDetailById(productId);
        if (product == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "商品不存在");
        }

        // 2. 增加浏览量
        productMapper.incrementViewCount(productId);

        // 3. 转换为VO
        ProductDetailVO vo = new ProductDetailVO();
        BeanUtils.copyProperties(product, vo);

        // 设置分类名称
        if (product.getCategory() != null) {
            vo.setCategoryName(product.getCategory().getCategoryName());
        }

        // 转换图片列表
        if (product.getImages() != null && !product.getImages().isEmpty()) {
            List<ProductImageVO> imageVOList = product.getImages().stream()
                    .map(image -> {
                        ProductImageVO imageVO = new ProductImageVO();
                        BeanUtils.copyProperties(image, imageVO);
                        return imageVO;
                    })
                    .collect(Collectors.toList());
            vo.setImages(imageVOList);
        }

        // 转换SKU列表
        if (product.getSkuList() != null && !product.getSkuList().isEmpty()) {
            List<ProductSkuVO> skuVOList = product.getSkuList().stream()
                    .map(sku -> {
                        ProductSkuVO skuVO = new ProductSkuVO();
                        BeanUtils.copyProperties(sku, skuVO);
                        return skuVO;
                    })
                    .collect(Collectors.toList());
            vo.setSkuList(skuVOList);
        }

        // 查询评价统计
        Double avgRating = productReviewMapper.calculateAvgRating(productId);
        Integer reviewCount = productReviewMapper.countByProductId(productId);
        vo.setAvgRating(avgRating);
        vo.setReviewCount(reviewCount);

        // 查询各评分数量
        ProductDetailVO.RatingStatistics ratingStatistics = new ProductDetailVO.RatingStatistics();
        ratingStatistics.setFiveStar(productReviewMapper.countByProductIdAndRating(productId, 5));
        ratingStatistics.setFourStar(productReviewMapper.countByProductIdAndRating(productId, 4));
        ratingStatistics.setThreeStar(productReviewMapper.countByProductIdAndRating(productId, 3));
        ratingStatistics.setTwoStar(productReviewMapper.countByProductIdAndRating(productId, 2));
        ratingStatistics.setOneStar(productReviewMapper.countByProductIdAndRating(productId, 1));
        vo.setRatingStatistics(ratingStatistics);

        return vo;
    }

    @Override
    public List<ProductVO> getRecommendedProducts(Integer limit) {
        List<Product> products = productMapper.selectRecommendedProducts(limit);
        return convertToVOList(products);
    }

    @Override
    public List<ProductVO> getNewProducts(Integer limit) {
        List<Product> products = productMapper.selectNewProducts(limit);
        return convertToVOList(products);
    }

    @Override
    public List<ProductVO> getHotProducts(Integer limit) {
        List<Product> products = productMapper.selectHotProducts(limit);
        return convertToVOList(products);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void publishProduct(Long productId, Long sellerId) {
        Product product = productMapper.selectById(productId);
        if (product == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "商品不存在");
        }
        if (!product.getSellerId().equals(sellerId)) {
            throw new BusinessException(ResultCode.FORBIDDEN.getCode(), "无权操作此商品");
        }
        if (product.getStatus() == 3) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "审核不通过的商品不能上架");
        }

        product.setStatus(1); // 上架
        productMapper.updateById(product);
        upsertProductVector(product);

        log.info("商品上架成功：productId={}, sellerId={}", productId, sellerId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unpublishProduct(Long productId, Long sellerId) {
        Product product = productMapper.selectById(productId);
        if (product == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "商品不存在");
        }
        if (!product.getSellerId().equals(sellerId)) {
            throw new BusinessException(ResultCode.FORBIDDEN.getCode(), "无权操作此商品");
        }

        product.setStatus(2); // 下架
        productMapper.updateById(product);
        removeProductVector(product.getId());

        log.info("商品下架成功：productId={}, sellerId={}", productId, sellerId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditProduct(Long productId, Integer status, String remark) {
        Product product = productMapper.selectById(productId);
        if (product == null || product.getIsDeleted() == 1) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "商品不存在");
        }
        if (status == null || (status != 1 && status != 3)) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "审核状态错误");
        }
        if (product.getStatus() != 0) {
            throw new BusinessException(400, "只有待审核的商品可以进行审核操作");
        }

        product.setStatus(status);
        product.setAuditRemark(remark);
        productMapper.updateById(product);

        // 审核通过则允许SKU正常售卖
        if (status == 1) {
            LambdaQueryWrapper<ProductSku> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ProductSku::getProductId, productId);
            ProductSku updateSku = new ProductSku();
            updateSku.setStatus(1);
            productSkuMapper.update(updateSku, wrapper);
            upsertProductVector(product);
        } else {
            removeProductVector(productId);
        }

        log.info("商品审核成功：productId={}, status={}, remark={}", productId, status, remark);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int syncProductVectors() {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Product::getStatus, 1);
        wrapper.eq(Product::getIsDeleted, 0);
        List<Product> products = productMapper.selectList(wrapper);
        if (products == null || products.isEmpty()) {
            return 0;
        }
        clearVectorStore();
        products.forEach(this::upsertProductVector);
        log.info("商品向量全量同步完成，共同步 {} 条", products.size());
        return products.size();
    }

    private void clearVectorStore() {
        String tableName = StringUtils.hasText(vectorStoreTableName)
                ? vectorStoreTableName
                : "vector_store";
        String sql = "TRUNCATE TABLE " + tableName;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(pgvectorDataSource);
        jdbcTemplate.execute(sql);
        log.info("向量库已清空，table={}", tableName);
    }



    private void upsertProductVector(Product product) {
        if (product == null || product.getId() == null) {
            return;
        }
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("productId", product.getId());
        metadata.put("productName", product.getProductName());
        metadata.put("mainImage", product.getMainImage());
        metadata.put("currentPrice", product.getCurrentPrice());
        metadata.put("description", product.getDescription());
        metadata.put("detailUrl", "/product/" + product.getId());

        String content = buildProductContent(product);
        String vectorId = buildVectorId(product.getId());
        Document document = new Document(vectorId, content, metadata);

        vectorStore.delete(List.of(vectorId));
        vectorStore.add(List.of(document));
    }

    private void removeProductVector(Long productId) {
        if (productId == null) {
            return;
        }
        String vectorId = buildVectorId(productId);
        vectorStore.delete(List.of(vectorId));
    }

    private String buildVectorId(Long productId) {
        return UUID.nameUUIDFromBytes(("product:" + productId).getBytes(StandardCharsets.UTF_8)).toString();
    }

    private String buildProductContent(Product product) {
        StringBuilder builder = new StringBuilder();
        builder.append("商品名称：").append(product.getProductName());
        if (StringUtils.hasText(product.getDescription())) {
            builder.append("\n商品描述：").append(product.getDescription());
        }
        if (product.getCurrentPrice() != null) {
            builder.append("\n当前价格：").append(product.getCurrentPrice());
        }
        if (StringUtils.hasText(product.getDetail())) {
            builder.append("\n商品详情：").append(product.getDetail());
        }
        return builder.toString();
    }

    /**
     * 转换商品列表为VO列表
     *
     * @param products 商品列表
     * @return VO列表
     */
    private List<ProductVO> convertToVOList(List<Product> products) {
        return products.stream().map(product -> {
            ProductVO vo = new ProductVO();
            BeanUtils.copyProperties(product, vo);

            // 查询评价统计
            Double avgRating = productReviewMapper.calculateAvgRating(product.getId());
            Integer reviewCount = productReviewMapper.countByProductId(product.getId());
            vo.setAvgRating(avgRating);
            vo.setReviewCount(reviewCount);

            return vo;
        }).collect(Collectors.toList());
    }
}
