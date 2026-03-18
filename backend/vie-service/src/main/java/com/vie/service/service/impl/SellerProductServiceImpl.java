package com.vie.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vie.db.entity.Category;
import com.vie.db.entity.Product;
import com.vie.db.entity.ProductImage;
import com.vie.db.entity.ProductSku;
import com.vie.db.mapper.CategoryMapper;
import com.vie.db.mapper.ProductImageMapper;
import com.vie.db.mapper.ProductMapper;
import com.vie.db.mapper.ProductSkuMapper;
import com.vie.service.dto.SellerProductCreateDTO;
import com.vie.service.dto.SellerProductQueryDTO;
import com.vie.service.dto.SellerProductUpdateDTO;
import com.vie.service.exception.BusinessException;
import com.vie.service.service.SellerProductService;
import com.vie.service.vo.SellerProductDetailVO;
import com.vie.service.vo.SellerProductVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 卖家商品服务实现类
 */
@Slf4j
@Service
public class SellerProductServiceImpl implements SellerProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    private ProductImageMapper productImageMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private VectorStore vectorStore;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final Map<Integer, String> STATUS_DESC_MAP = Map.of(
            0, "待审核",
            1, "上架中",
            2, "已下架",
            3, "审核不通过"
    );

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createProduct(SellerProductCreateDTO dto, Long sellerId) {
        // 校验分类
        Category category = categoryMapper.selectById(dto.getCategoryId());
        if (category == null || category.getIsDeleted() == 1) {
            throw new BusinessException(400, "分类不存在");
        }

        // 生成商品编码
        String productCode = "P-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        // 计算总库存
        int totalStock = dto.getSkuList().stream()
                .mapToInt(SellerProductCreateDTO.ProductSkuDTO::getStock)
                .sum();

        // 创建商品
        Product product = new Product();
        product.setCategoryId(dto.getCategoryId());
        product.setSellerId(sellerId);
        product.setProductName(dto.getProductName());
        product.setProductCode(productCode);
        product.setMainImage(dto.getMainImage());
        product.setDescription(dto.getDescription());
        product.setDetail(dto.getDetail());
        product.setOriginalPrice(dto.getOriginalPrice());
        product.setCurrentPrice(dto.getCurrentPrice());
        product.setStock(totalStock);
        product.setSalesVolume(0);
        product.setViewCount(0);
        product.setStatus(0); // 待审核
        product.setIsRecommended(dto.getIsRecommended() != null ? dto.getIsRecommended() : 0);
        product.setIsNew(dto.getIsNew() != null ? dto.getIsNew() : 0);
        product.setIsHot(dto.getIsHot() != null ? dto.getIsHot() : 0);
        productMapper.insert(product);

        Long productId = product.getId();

        // 创建SKU
        for (int i = 0; i < dto.getSkuList().size(); i++) {
            SellerProductCreateDTO.ProductSkuDTO skuDTO = dto.getSkuList().get(i);
            ProductSku sku = new ProductSku();
            sku.setProductId(productId);
            sku.setSkuName(skuDTO.getSkuName());
            sku.setSkuCode(productCode + "-S" + (i + 1));
            sku.setSkuImage(skuDTO.getSkuImage());
            sku.setPrice(skuDTO.getPrice());
            sku.setStock(skuDTO.getStock());
            sku.setSalesVolume(0);
            sku.setSpecInfo(skuDTO.getSpecInfo());
            sku.setStatus(1);
            productSkuMapper.insert(sku);
        }

        // 创建商品图片
        if (dto.getImages() != null && !dto.getImages().isEmpty()) {
            for (SellerProductCreateDTO.ProductImageDTO imageDTO : dto.getImages()) {
                ProductImage image = new ProductImage();
                image.setProductId(productId);
                image.setImageUrl(imageDTO.getImageUrl());
                image.setSortOrder(imageDTO.getSortOrder() != null ? imageDTO.getSortOrder() : 0);
                image.setImageType(imageDTO.getImageType() != null ? imageDTO.getImageType() : 1);
                productImageMapper.insert(image);
            }
        }

        log.info("卖家 {} 创建商品成功，商品ID: {}, 商品编码: {}", sellerId, productId, productCode);
        return productId;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProduct(Long productId, SellerProductUpdateDTO dto, Long sellerId) {
        Product product = getProductBySeller(productId, sellerId);

        // 更新商品基本信息
        if (dto.getCategoryId() != null) {
            Category category = categoryMapper.selectById(dto.getCategoryId());
            if (category == null || category.getIsDeleted() == 1) {
                throw new BusinessException(400, "分类不存在");
            }
            product.setCategoryId(dto.getCategoryId());
        }
        if (StringUtils.hasText(dto.getProductName())) {
            product.setProductName(dto.getProductName());
        }
        if (dto.getMainImage() != null) {
            product.setMainImage(dto.getMainImage());
        }
        if (dto.getDescription() != null) {
            product.setDescription(dto.getDescription());
        }
        if (dto.getDetail() != null) {
            product.setDetail(dto.getDetail());
        }
        if (dto.getOriginalPrice() != null) {
            product.setOriginalPrice(dto.getOriginalPrice());
        }
        if (dto.getCurrentPrice() != null) {
            product.setCurrentPrice(dto.getCurrentPrice());
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

        // 如果商品已上架，编辑后变为待审核
        if (product.getStatus() == 1) {
            product.setStatus(0);
            product.setAuditRemark(null);
        }

        productMapper.updateById(product);

        if (product.getStatus() == 1) {
            upsertProductVector(product);
        } else {
            removeProductVector(product.getId());
        }

        // 更新SKU（全量更新）
        // 注意：product_sku 表使用了逻辑删除（@TableLogic），且 sku_code 有唯一索引。
        // 若直接 delete(逻辑删除) 后再 insert 相同 sku_code，会触发 DuplicateKeyException。
        // 因此这里采用“先让出旧 sku_code，再插入新 sku”的方式实现全量更新。
        if (dto.getSkuList() != null && !dto.getSkuList().isEmpty()) {
            // 1) 查询旧SKU（包含未删除的即可；此处只处理当前有效SKU）
            LambdaQueryWrapper<ProductSku> oldSkuQuery = new LambdaQueryWrapper<>();
            oldSkuQuery.eq(ProductSku::getProductId, productId);
            oldSkuQuery.eq(ProductSku::getIsDeleted, 0);
            List<ProductSku> oldSkus = productSkuMapper.selectList(oldSkuQuery);

            // 2) 让出旧 sku_code（避免唯一索引冲突）
            //    做法：把 sku_code 改为 sku_code + "_DEL_" + id，然后再逻辑删除
            for (ProductSku oldSku : oldSkus) {
                ProductSku update = new ProductSku();
                update.setId(oldSku.getId());
                update.setSkuCode(oldSku.getSkuCode() + "_DEL_" + oldSku.getId());
                productSkuMapper.updateById(update);
            }

            // 3) 逻辑删除旧SKU
            if (!oldSkus.isEmpty()) {
                LambdaQueryWrapper<ProductSku> skuDelWrapper = new LambdaQueryWrapper<>();
                skuDelWrapper.eq(ProductSku::getProductId, productId);
                productSkuMapper.delete(skuDelWrapper);
            }

            // 4) 插入新SKU
            int totalStock = 0;
            for (int i = 0; i < dto.getSkuList().size(); i++) {
                SellerProductCreateDTO.ProductSkuDTO skuDTO = dto.getSkuList().get(i);
                ProductSku sku = new ProductSku();
                sku.setProductId(productId);
                sku.setSkuName(skuDTO.getSkuName());
                sku.setSkuCode(product.getProductCode() + "-S" + (i + 1));
                sku.setSkuImage(skuDTO.getSkuImage());
                sku.setPrice(skuDTO.getPrice());
                sku.setStock(skuDTO.getStock());
                sku.setSalesVolume(0);
                sku.setSpecInfo(skuDTO.getSpecInfo());
                sku.setStatus(1);
                productSkuMapper.insert(sku);
                totalStock += skuDTO.getStock();
            }
            product.setStock(totalStock);
            productMapper.updateById(product);
        }

        // 更新图片（全量更新）
        if (dto.getImages() != null) {
            // 删除旧图片
            LambdaQueryWrapper<ProductImage> imageWrapper = new LambdaQueryWrapper<>();
            imageWrapper.eq(ProductImage::getProductId, productId);
            productImageMapper.delete(imageWrapper);

            // 创建新图片
            for (SellerProductCreateDTO.ProductImageDTO imageDTO : dto.getImages()) {
                ProductImage image = new ProductImage();
                image.setProductId(productId);
                image.setImageUrl(imageDTO.getImageUrl());
                image.setSortOrder(imageDTO.getSortOrder() != null ? imageDTO.getSortOrder() : 0);
                image.setImageType(imageDTO.getImageType() != null ? imageDTO.getImageType() : 1);
                productImageMapper.insert(image);
            }
        }

        log.info("卖家 {} 更新商品成功，商品ID: {}", sellerId, productId);
    }

    @Override
    public void updateProductStatus(Long productId, Integer status, Long sellerId) {
        Product product = getProductBySeller(productId, sellerId);

        // 商家侧上下架规则：
        // - status=2：直接下架
        // - status=1：申请上架（进入待审核 status=0，由管理员审核通过后才会变为上架中 status=1）
        if (status == null) {
            throw new BusinessException(400, "状态值不能为空");
        }

        if (status == 2) {
            product.setStatus(2);
            productMapper.updateById(product);
            removeProductVector(productId);
            log.info("卖家 {} 下架商品，商品ID: {}", sellerId, productId);
            return;
        }

        if (status == 1) {
            // 下架/审核不通过/待审核 的商品，允许再次发起申请上架，统一进入待审核
            product.setStatus(0);
            product.setAuditRemark(null);
            productMapper.updateById(product);
            log.info("卖家 {} 申请上架商品，商品ID: {}，状态置为待审核", sellerId, productId);
            return;
        }

        throw new BusinessException(400, "无效的状态值");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteProduct(Long productId, Long sellerId) {
        Product product = getProductBySeller(productId, sellerId);

        // TODO: 检查是否有未完成订单

        // 软删除商品
        productMapper.deleteById(productId);

        // 软删除SKU
        LambdaQueryWrapper<ProductSku> skuWrapper = new LambdaQueryWrapper<>();
        skuWrapper.eq(ProductSku::getProductId, productId);
        productSkuMapper.delete(skuWrapper);

        // 软删除图片
        LambdaQueryWrapper<ProductImage> imageWrapper = new LambdaQueryWrapper<>();
        imageWrapper.eq(ProductImage::getProductId, productId);
        productImageMapper.delete(imageWrapper);

        removeProductVector(productId);

        log.info("卖家 {} 删除商品，商品ID: {}", sellerId, productId);
    }

    @Override
    public IPage<SellerProductVO> getProductPage(SellerProductQueryDTO queryDTO, Long sellerId) {
        Page<Product> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());

        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Product::getSellerId, sellerId);
        wrapper.eq(Product::getIsDeleted, 0);

        if (queryDTO.getStatus() != null) {
            wrapper.eq(Product::getStatus, queryDTO.getStatus());
        }
        if (StringUtils.hasText(queryDTO.getKeyword())) {
            wrapper.like(Product::getProductName, queryDTO.getKeyword());
        }
        if (queryDTO.getCategoryId() != null) {
            wrapper.eq(Product::getCategoryId, queryDTO.getCategoryId());
        }

        wrapper.orderByDesc(Product::getCreateTime);

        IPage<Product> productPage = productMapper.selectPage(page, wrapper);

        // 获取分类信息
        List<Long> categoryIds = productPage.getRecords().stream()
                .map(Product::getCategoryId)
                .distinct()
                .collect(Collectors.toList());

        Map<Long, String> categoryNameMap = Map.of();
        if (!categoryIds.isEmpty()) {
            List<Category> categories = categoryMapper.selectBatchIds(categoryIds);
            categoryNameMap = categories.stream()
                    .collect(Collectors.toMap(Category::getId, Category::getCategoryName));
        }

        Map<Long, String> finalCategoryNameMap = categoryNameMap;
        return productPage.convert(product -> buildSellerProductVO(product, finalCategoryNameMap));
    }

    @Override
    public SellerProductDetailVO getProductDetail(Long productId, Long sellerId) {
        Product product = getProductBySeller(productId, sellerId);

        // 获取分类名称
        Category category = categoryMapper.selectById(product.getCategoryId());
        String categoryName = category != null ? category.getCategoryName() : "";

        // 获取SKU列表
        LambdaQueryWrapper<ProductSku> skuWrapper = new LambdaQueryWrapper<>();
        skuWrapper.eq(ProductSku::getProductId, productId);
        skuWrapper.eq(ProductSku::getIsDeleted, 0);
        List<ProductSku> skuList = productSkuMapper.selectList(skuWrapper);

        // 获取图片列表
        LambdaQueryWrapper<ProductImage> imageWrapper = new LambdaQueryWrapper<>();
        imageWrapper.eq(ProductImage::getProductId, productId);
        imageWrapper.eq(ProductImage::getIsDeleted, 0);
        imageWrapper.orderByAsc(ProductImage::getSortOrder);
        List<ProductImage> imageList = productImageMapper.selectList(imageWrapper);

        return buildSellerProductDetailVO(product, categoryName, skuList, imageList);
    }

    // ========== 私有方法 ==========

    private Product getProductBySeller(Long productId, Long sellerId) {
        Product product = productMapper.selectById(productId);
        if (product == null || product.getIsDeleted() == 1) {
            throw new BusinessException(404, "商品不存在");
        }
        if (!product.getSellerId().equals(sellerId)) {
            throw new BusinessException(403, "无权操作此商品");
        }
        return product;
    }

    private SellerProductVO buildSellerProductVO(Product product, Map<Long, String> categoryNameMap) {
        return SellerProductVO.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .productCode(product.getProductCode())
                .mainImage(product.getMainImage())
                .categoryId(product.getCategoryId())
                .categoryName(categoryNameMap.getOrDefault(product.getCategoryId(), ""))
                .currentPrice(product.getCurrentPrice())
                .stock(product.getStock())
                .salesVolume(product.getSalesVolume())
                .status(product.getStatus())
                .statusDesc(STATUS_DESC_MAP.getOrDefault(product.getStatus(), "未知"))
                .auditRemark(product.getAuditRemark())
                .createTime(product.getCreateTime() != null ? product.getCreateTime().format(FORMATTER) : null)
                .build();
    }

    private SellerProductDetailVO buildSellerProductDetailVO(Product product, String categoryName,
                                                              List<ProductSku> skuList, List<ProductImage> imageList) {
        List<SellerProductDetailVO.ProductSkuVO> skuVOList = skuList.stream()
                .map(sku -> SellerProductDetailVO.ProductSkuVO.builder()
                        .id(sku.getId())
                        .skuName(sku.getSkuName())

                        .skuCode(sku.getSkuCode())
                        .skuImage(sku.getSkuImage())
                        .price(sku.getPrice())
                        .stock(sku.getStock())
                        .salesVolume(sku.getSalesVolume())
                        .specInfo(sku.getSpecInfo())
                        .status(sku.getStatus())
                        .build())
                .collect(Collectors.toList());

        List<SellerProductDetailVO.ProductImageVO> imageVOList = imageList.stream()
                .map(image -> SellerProductDetailVO.ProductImageVO.builder()
                        .id(image.getId())
                        .imageUrl(image.getImageUrl())
                        .sortOrder(image.getSortOrder())
                        .imageType(image.getImageType())
                        .build())
                .collect(Collectors.toList());

        return SellerProductDetailVO.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .productCode(product.getProductCode())
                .mainImage(product.getMainImage())
                .categoryId(product.getCategoryId())
                .categoryName(categoryName)
                .description(product.getDescription())
                .detail(product.getDetail())
                .originalPrice(product.getOriginalPrice())
                .currentPrice(product.getCurrentPrice())
                .stock(product.getStock())
                .salesVolume(product.getSalesVolume())
                .status(product.getStatus())
                .statusDesc(STATUS_DESC_MAP.getOrDefault(product.getStatus(), "未知"))
                .isRecommended(product.getIsRecommended())
                .isNew(product.getIsNew())
                .isHot(product.getIsHot())
                .auditRemark(product.getAuditRemark())
                .createTime(product.getCreateTime() != null ? product.getCreateTime().format(FORMATTER) : null)
                .images(imageVOList)
                .skuList(skuVOList)
                .build();
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
        Document document = new Document(content, metadata);

        vectorStore.delete(List.of(product.getId().toString()));
        vectorStore.add(List.of(document));
    }

    private void removeProductVector(Long productId) {
        if (productId == null) {
            return;
        }
        vectorStore.delete(List.of(productId.toString()));
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

}
