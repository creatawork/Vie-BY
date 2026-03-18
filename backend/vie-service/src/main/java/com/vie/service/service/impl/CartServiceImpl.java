package com.vie.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.vie.db.entity.CartItem;
import com.vie.db.entity.Product;
import com.vie.db.entity.ProductSku;
import com.vie.db.mapper.CartItemMapper;
import com.vie.db.mapper.ProductMapper;
import com.vie.db.mapper.ProductSkuMapper;
import com.vie.service.dto.CartAddDTO;
import com.vie.service.dto.CartUpdateDTO;
import com.vie.service.exception.BusinessException;
import com.vie.service.service.CartService;
import com.vie.service.vo.CartItemVO;
import com.vie.service.vo.CartVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 购物车服务实现类
 */
@Slf4j
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartItemMapper cartItemMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addToCart(CartAddDTO dto, Long userId) {
        // 1. 校验商品是否存在且上架
        Product product = productMapper.selectById(dto.getProductId());
        if (product == null || product.getIsDeleted() == 1) {
            throw new BusinessException(404, "商品不存在");
        }
        if (product.getStatus() != 1) {
            throw new BusinessException(400, "商品已下架");
        }

        // 2. 校验SKU是否存在且有效
        ProductSku sku = productSkuMapper.selectById(dto.getSkuId());
        if (sku == null || sku.getIsDeleted() == 1) {
            throw new BusinessException(404, "商品规格不存在");
        }
        if (!sku.getProductId().equals(dto.getProductId())) {
            throw new BusinessException(400, "商品规格不匹配");
        }
        if (sku.getStatus() != 1) {
            throw new BusinessException(400, "商品规格已下架");
        }

        // 3. 校验库存
        if (sku.getStock() < dto.getQuantity()) {
            throw new BusinessException(400, "库存不足");
        }

        // 4. 检查购物车是否已存在该SKU（包括已删除的记录，因为有唯一索引）
        CartItem existingItem = cartItemMapper.selectByUserIdAndSkuIdIncludeDeleted(userId, dto.getSkuId());

        if (existingItem != null) {
            if (existingItem.getIsDeleted() == 1) {
                // 已删除的记录，使用自定义 SQL 恢复并设置新数量，绕过 MyBatis-Plus 的逻辑删除保护
                if (dto.getQuantity() > sku.getStock()) {
                    throw new BusinessException(400, "库存不足，当前库存：" + sku.getStock());
                }
                cartItemMapper.restoreItem(existingItem.getId(), dto.getQuantity());
                log.info("用户 {} 恢复购物车商品，SKU: {}, 数量: {}", userId, dto.getSkuId(), dto.getQuantity());
            } else {
                // 已存在，累加数量
                int newQuantity = existingItem.getQuantity() + dto.getQuantity();
                if (newQuantity > sku.getStock()) {
                    throw new BusinessException(400, "库存不足，当前库存：" + sku.getStock());
                }
                existingItem.setQuantity(newQuantity);
                cartItemMapper.updateById(existingItem);
                log.info("用户 {} 更新购物车商品数量，SKU: {}, 数量: {}", userId, dto.getSkuId(), newQuantity);
            }
        } else {
            // 不存在，新增
            CartItem cartItem = CartItem.builder()
                    .userId(userId)
                    .productId(dto.getProductId())
                    .skuId(dto.getSkuId())
                    .quantity(dto.getQuantity())
                    .selected(1)
                    .build();
            cartItem.setIsDeleted(0);
            cartItemMapper.insert(cartItem);
            log.info("用户 {} 添加商品到购物车，SKU: {}, 数量: {}", userId, dto.getSkuId(), dto.getQuantity());
        }
    }

    @Override
    public CartVO getCart(Long userId) {
        // 查询购物车列表
        List<Map<String, Object>> cartList = cartItemMapper.selectCartListWithProduct(userId);

        List<CartItemVO> items = new ArrayList<>();
        int totalQuantity = 0;
        int selectedQuantity = 0;
        BigDecimal selectedAmount = BigDecimal.ZERO;
        int validCount = 0;
        int invalidCount = 0;
        boolean allSelected = true;

        for (Map<String, Object> map : cartList) {
            CartItemVO item = buildCartItemVO(map);
            items.add(item);

            totalQuantity += item.getQuantity();

            if (Boolean.TRUE.equals(item.getValid())) {
                validCount++;
                if (Boolean.TRUE.equals(item.getSelected())) {
                    selectedQuantity += item.getQuantity();
                    selectedAmount = selectedAmount.add(item.getTotalPrice());
                } else {
                    allSelected = false;
                }
            } else {
                invalidCount++;
                allSelected = false;
            }
        }

        if (items.isEmpty()) {
            allSelected = false;
        }

        return CartVO.builder()
                .items(items)
                .totalQuantity(totalQuantity)
                .selectedQuantity(selectedQuantity)
                .selectedAmount(selectedAmount)
                .allSelected(allSelected)
                .validCount(validCount)
                .invalidCount(invalidCount)
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateQuantity(Long cartItemId, CartUpdateDTO dto, Long userId) {
        // 查询购物车项
        CartItem cartItem = getCartItemByIdAndUserId(cartItemId, userId);

        // 校验库存
        ProductSku sku = productSkuMapper.selectById(cartItem.getSkuId());
        if (sku == null || sku.getStock() < dto.getQuantity()) {
            throw new BusinessException(400, "库存不足");
        }

        // 更新数量
        cartItem.setQuantity(dto.getQuantity());
        cartItemMapper.updateById(cartItem);
        log.info("用户 {} 更新购物车商品数量，cartItemId: {}, 数量: {}", userId, cartItemId, dto.getQuantity());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCartItem(Long cartItemId, Long userId) {
        // 先验证购物车项存在且属于当前用户
        getCartItemByIdAndUserId(cartItemId, userId);
        // 使用MyBatis-Plus的deleteById，会自动执行逻辑删除
        cartItemMapper.deleteById(cartItemId);
        log.info("用户 {} 删除购物车商品，cartItemId: {}", userId, cartItemId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void clearCart(Long userId) {
        cartItemMapper.clearByUserId(userId);
        log.info("用户 {} 清空购物车", userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSelected(Long cartItemId, Boolean selected, Long userId) {
        CartItem cartItem = getCartItemByIdAndUserId(cartItemId, userId);
        cartItem.setSelected(selected ? 1 : 0);
        cartItemMapper.updateById(cartItem);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void selectAll(Boolean selected, Long userId) {
        cartItemMapper.updateSelectedByUserId(userId, selected ? 1 : 0);
    }

    @Override
    public Integer getCartCount(Long userId) {
        LambdaQueryWrapper<CartItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CartItem::getUserId, userId)
                    .eq(CartItem::getIsDeleted, 0);
        return Math.toIntExact(cartItemMapper.selectCount(queryWrapper));
    }

    /**
     * 根据ID和用户ID获取购物车项
     */
    private CartItem getCartItemByIdAndUserId(Long cartItemId, Long userId) {
        CartItem cartItem = cartItemMapper.selectById(cartItemId);
        if (cartItem == null || cartItem.getIsDeleted() == 1) {
            throw new BusinessException(404, "购物车商品不存在");
        }
        if (!cartItem.getUserId().equals(userId)) {
            throw new BusinessException(403, "无权操作");
        }
        return cartItem;
    }

    /**
     * 构建购物车项VO
     */
    private CartItemVO buildCartItemVO(Map<String, Object> map) {
        Long cartId = getLongValue(map, "cart_id");
        Long productId = getLongValue(map, "product_id");
        Long skuId = getLongValue(map, "sku_id");
        Integer quantity = getIntegerValue(map, "quantity");
        Integer selected = getIntegerValue(map, "selected");
        String productName = (String) map.get("product_name");
        String productImage = (String) map.get("product_image");
        String skuName = (String) map.get("sku_name");
        String skuImage = (String) map.get("sku_image");
        BigDecimal price = getBigDecimalValue(map, "price");
        Integer stock = getIntegerValue(map, "stock");
        Integer productStatus = getIntegerValue(map, "product_status");
        Integer skuStatus = getIntegerValue(map, "sku_status");

        // 计算小计
        BigDecimal totalPrice = price != null && quantity != null
                ? price.multiply(BigDecimal.valueOf(quantity))
                : BigDecimal.ZERO;

        // 判断是否有效
        boolean valid = productStatus != null && productStatus == 1
                && skuStatus != null && skuStatus == 1
                && stock != null && stock > 0;

        return CartItemVO.builder()
                .cartId(cartId)
                .productId(productId)
                .skuId(skuId)
                .productName(productName)
                .productImage(productImage)
                .skuName(skuName)
                .skuImage(skuImage)
                .price(price)
                .quantity(quantity)
                .totalPrice(totalPrice)
                .stock(stock)
                .selected(selected != null && selected == 1)
                .productStatus(productStatus)
                .skuStatus(skuStatus)
                .valid(valid)
                .build();
    }

    private Long getLongValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) return null;
        if (value instanceof Long) return (Long) value;
        if (value instanceof Integer) return ((Integer) value).longValue();
        return Long.parseLong(value.toString());
    }

    private Integer getIntegerValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) return null;
        if (value instanceof Integer) return (Integer) value;
        if (value instanceof Long) return ((Long) value).intValue();
        return Integer.parseInt(value.toString());
    }

    private BigDecimal getBigDecimalValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) return null;
        if (value instanceof BigDecimal) return (BigDecimal) value;
        return new BigDecimal(value.toString());
    }
}
