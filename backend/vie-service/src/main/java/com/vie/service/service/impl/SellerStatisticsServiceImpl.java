package com.vie.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.vie.db.entity.Order;
import com.vie.db.entity.OrderItem;
import com.vie.db.entity.Product;
import com.vie.db.entity.SellerAccount;
import com.vie.db.mapper.OrderItemMapper;
import com.vie.db.mapper.OrderMapper;
import com.vie.db.mapper.ProductMapper;
import com.vie.db.mapper.SellerAccountMapper;
import com.vie.service.dto.SalesStatisticsQueryDTO;
import com.vie.service.service.SellerStatisticsService;
import com.vie.service.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 卖家销售统计服务实现类
 */
@Slf4j
@Service
public class SellerStatisticsServiceImpl implements SellerStatisticsService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private SellerAccountMapper sellerAccountMapper;

    @Override
    public SalesStatisticsVO getSalesStatistics(SalesStatisticsQueryDTO queryDTO, Long sellerId) {
        // 默认查询近7天
        LocalDate endDate = queryDTO.getEndDate() != null ? queryDTO.getEndDate() : LocalDate.now();
        LocalDate startDate = queryDTO.getStartDate() != null ? queryDTO.getStartDate() : endDate.minusDays(6);
        String timeUnit = queryDTO.getTimeUnit() != null ? queryDTO.getTimeUnit().toUpperCase() : "DAY";

        // 获取卖家的商品ID列表
        List<Long> productIds = getSellerProductIds(sellerId);
        if (productIds.isEmpty()) {
            return buildEmptySalesStatistics();
        }

        // 查询订单明细
        List<OrderItem> orderItems = getOrderItemsInDateRange(productIds, startDate, endDate);

        // 计算汇总数据
        SalesStatisticsVO.SalesSummary summary = calculateSummary(orderItems);

        // 计算趋势数据
        List<SalesStatisticsVO.SalesTrend> trend = calculateTrend(orderItems, startDate, endDate, timeUnit);

        return SalesStatisticsVO.builder()
                .summary(summary)
                .trend(trend)
                .build();
    }

    @Override
    public List<ProductRankVO> getProductRank(Integer limit, String orderBy, LocalDate startDate, LocalDate endDate, Long sellerId) {
        int rankLimit = limit != null ? limit : 10;
        String sortBy = orderBy != null ? orderBy.toLowerCase() : "sales";

        // 默认查询近30天
        LocalDate end = endDate != null ? endDate : LocalDate.now();
        LocalDate start = startDate != null ? startDate : end.minusDays(29);

        // 获取卖家的商品ID列表
        List<Long> productIds = getSellerProductIds(sellerId);
        if (productIds.isEmpty()) {
            return new ArrayList<>();
        }

        // 查询订单明细
        List<OrderItem> orderItems = getOrderItemsInDateRange(productIds, start, end);

        // 按商品分组统计
        Map<Long, List<OrderItem>> groupedItems = orderItems.stream()
                .collect(Collectors.groupingBy(OrderItem::getProductId));

        // 获取商品信息
        Map<Long, Product> productMap = new HashMap<>();
        if (!groupedItems.isEmpty()) {
            List<Product> products = productMapper.selectBatchIds(groupedItems.keySet());
            productMap = products.stream().collect(Collectors.toMap(Product::getId, p -> p));
        }

        // 计算排行
        List<ProductRankVO> rankList = new ArrayList<>();
        Map<Long, Product> finalProductMap = productMap;
        groupedItems.forEach((productId, items) -> {
            int salesVolume = items.stream().mapToInt(OrderItem::getQuantity).sum();
            BigDecimal salesAmount = items.stream()
                    .map(OrderItem::getTotalPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            Product product = finalProductMap.get(productId);
            if (product != null) {
                rankList.add(ProductRankVO.builder()
                        .productId(productId)
                        .productName(product.getProductName())
                        .mainImage(product.getMainImage())
                        .salesVolume(salesVolume)
                        .salesAmount(salesAmount)
                        .build());
            }
        });

        // 排序
        if ("amount".equals(sortBy)) {
            rankList.sort((a, b) -> b.getSalesAmount().compareTo(a.getSalesAmount()));
        } else {
            rankList.sort((a, b) -> b.getSalesVolume().compareTo(a.getSalesVolume()));
        }

        // 设置排名并限制数量
        List<ProductRankVO> result = new ArrayList<>();
        for (int i = 0; i < Math.min(rankLimit, rankList.size()); i++) {
            ProductRankVO vo = rankList.get(i);
            vo.setRank(i + 1);
            result.add(vo);
        }

        return result;
    }

    @Override
    public RevenueStatisticsVO getRevenueStatistics(Long sellerId) {
        // 查询商家账户
        LambdaQueryWrapper<SellerAccount> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SellerAccount::getSellerId, sellerId);
        SellerAccount account = sellerAccountMapper.selectOne(wrapper);

        if (account == null) {
            return RevenueStatisticsVO.builder()
                    .totalRevenue(BigDecimal.ZERO)
                    .availableBalance(BigDecimal.ZERO)
                    .frozenAmount(BigDecimal.ZERO)
                    .withdrawnAmount(BigDecimal.ZERO)
                    .pendingSettlement(BigDecimal.ZERO)
                    .build();
        }

        // 计算总收益 = 可用余额 + 冻结金额 + 已提现金额
        BigDecimal totalRevenue = account.getBalance()
                .add(account.getFrozenAmount())
                .add(account.getTotalWithdraw() != null ? account.getTotalWithdraw() : BigDecimal.ZERO);

        return RevenueStatisticsVO.builder()
                .totalRevenue(totalRevenue)
                .availableBalance(account.getBalance())
                .frozenAmount(account.getFrozenAmount())
                .withdrawnAmount(account.getTotalWithdraw() != null ? account.getTotalWithdraw() : BigDecimal.ZERO)
                .pendingSettlement(account.getFrozenAmount())
                .build();
    }

    @Override
    public SellerOrderOverviewVO getOrderOverview(Long sellerId) {
        log.info("获取商家订单概览统计，sellerId: {}", sellerId);
        Map<String, Object> resultMap = orderMapper.selectSellerOrderOverview(sellerId);

        return SellerOrderOverviewVO.builder()
                .totalCount(getIntValue(resultMap, "totalCount"))
                .waitShipCount(getIntValue(resultMap, "waitShipCount"))
                .finishedCount(getIntValue(resultMap, "finishedCount"))
                .build();
    }

    @Override
    public SellerOrderDistributionVO getOrderStatusDistribution(Long sellerId, LocalDate startDate, LocalDate endDate) {
        LocalDateTime startTime = startDate != null ? startDate.atStartOfDay() : null;
        LocalDateTime endTime = endDate != null ? endDate.plusDays(1).atStartOfDay() : null;

        List<Map<String, Object>> rawDistribution = orderMapper.selectSellerOrderStatusDistribution(sellerId, startTime, endTime);

        long total = rawDistribution.stream()
                .mapToLong(m -> ((Number) m.get("cnt")).longValue())
                .sum();

        List<SellerOrderDistributionVO.StatusCount> distribution = rawDistribution.stream()
                .map(m -> {
                    Integer status = ((Number) m.get("status")).intValue();
                    Integer count = ((Number) m.get("cnt")).intValue();
                    return SellerOrderDistributionVO.StatusCount.builder()
                            .status(status)
                            .statusDesc(getStatusDesc(status))
                            .count(count)
                            .percentage(total > 0 ? (count * 100.0 / total) : 0.0)
                            .build();
                })
                .collect(Collectors.toList());

        return SellerOrderDistributionVO.builder()
                .distribution(distribution)
                .build();
    }

    @Override
    public List<SkuRankVO> getSkuRank(Integer limit, String orderBy, LocalDate startDate, LocalDate endDate, Long sellerId) {
        LocalDateTime startTime = startDate != null ? startDate.atStartOfDay() : null;
        LocalDateTime endTime = endDate != null ? endDate.plusDays(1).atStartOfDay() : null;

        List<Map<String, Object>> rawRank = orderMapper.selectSellerSkuRank(sellerId, limit, orderBy, startTime, endTime);

        List<SkuRankVO> result = new ArrayList<>();
        for (int i = 0; i < rawRank.size(); i++) {
            Map<String, Object> m = rawRank.get(i);
            result.add(SkuRankVO.builder()
                    .skuId(((Number) m.get("skuId")).longValue())
                    .skuName((String) m.get("skuName"))
                    .productName((String) m.get("productName"))
                    .salesVolume(((Number) m.get("salesVolume")).intValue())
                    .salesAmount(new BigDecimal(m.get("salesAmount").toString()))
                    .rank(i + 1)
                    .build());
        }
        return result;
    }

    @Override
    public InventoryHealthVO getInventoryHealth(Long sellerId) {
        Map<String, Object> metrics = orderMapper.selectSellerInventoryHealth(sellerId, 10); // 默认低库存阈值10

        Integer totalSkuCount = getIntValue(metrics, "totalSkuCount");
        Integer outOfStockCount = getIntValue(metrics, "outOfStockCount");
        Integer lowStockCount = getIntValue(metrics, "lowStockCount");

        return InventoryHealthVO.builder()
                .totalSkuCount(totalSkuCount)
                .outOfStockCount(outOfStockCount)
                .lowStockCount(lowStockCount)
                .healthyRate(totalSkuCount > 0 ? ((totalSkuCount - outOfStockCount) * 100.0 / totalSkuCount) : 100.0)
                .build();
    }

    private String getStatusDesc(Integer status) {
        switch (status) {
            case 0: return "待支付";
            case 1: return "待发货";
            case 2: return "待收货";
            case 3: return "已完成";
            case 4: return "已取消";
            case 5: return "已关闭";
            default: return "未知";
        }
    }

    private Integer getIntValue(Map<String, Object> map, String key) {
        if (map == null || map.get(key) == null) {
            return 0;
        }
        Object value = map.get(key);
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        return Integer.parseInt(value.toString());
    }

    // ========== 私有方法 ==========

    private List<Long> getSellerProductIds(Long sellerId) {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Product::getSellerId, sellerId);
        wrapper.eq(Product::getIsDeleted, 0);
        wrapper.select(Product::getId);
        List<Product> products = productMapper.selectList(wrapper);
        return products.stream().map(Product::getId).collect(Collectors.toList());
    }

    private List<OrderItem> getOrderItemsInDateRange(List<Long> productIds, LocalDate startDate, LocalDate endDate) {
        // 查询已完成的订单
        LambdaQueryWrapper<Order> orderWrapper = new LambdaQueryWrapper<>();
        orderWrapper.in(Order::getStatus, 1, 2, 3); // 待发货、待收货、已完成
        orderWrapper.eq(Order::getIsDeleted, 0);
        orderWrapper.ge(Order::getCreateTime, startDate.atStartOfDay());
        orderWrapper.lt(Order::getCreateTime, endDate.plusDays(1).atStartOfDay());
        List<Order> orders = orderMapper.selectList(orderWrapper);

        if (orders.isEmpty()) {
            return new ArrayList<>();
        }

        List<Long> orderIds = orders.stream().map(Order::getId).collect(Collectors.toList());

        // 查询订单明细
        LambdaQueryWrapper<OrderItem> itemWrapper = new LambdaQueryWrapper<>();
        itemWrapper.in(OrderItem::getOrderId, orderIds);
        itemWrapper.in(OrderItem::getProductId, productIds);
        itemWrapper.eq(OrderItem::getIsDeleted, 0);

        return orderItemMapper.selectList(itemWrapper);
    }

    private SalesStatisticsVO.SalesSummary calculateSummary(List<OrderItem> orderItems) {
        if (orderItems.isEmpty()) {
            return SalesStatisticsVO.SalesSummary.builder()
                    .totalSales(BigDecimal.ZERO)
                    .totalOrders(0)
                    .totalQuantity(0)
                    .avgOrderAmount(BigDecimal.ZERO)
                    .build();
        }

        BigDecimal totalSales = orderItems.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int totalQuantity = orderItems.stream()
                .mapToInt(OrderItem::getQuantity)
                .sum();

        // 统计不同订单数
        long totalOrders = orderItems.stream()
                .map(OrderItem::getOrderId)
                .distinct()
                .count();

        BigDecimal avgOrderAmount = totalOrders > 0
                ? totalSales.divide(BigDecimal.valueOf(totalOrders), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        return SalesStatisticsVO.SalesSummary.builder()
                .totalSales(totalSales)
                .totalOrders((int) totalOrders)
                .totalQuantity(totalQuantity)
                .avgOrderAmount(avgOrderAmount)
                .build();
    }

    private List<SalesStatisticsVO.SalesTrend> calculateTrend(List<OrderItem> orderItems,
                                                               LocalDate startDate, LocalDate endDate, String timeUnit) {
        // 获取订单创建时间映射
        Set<Long> orderIds = orderItems.stream().map(OrderItem::getOrderId).collect(Collectors.toSet());
        Map<Long, LocalDateTime> orderTimeMap = new HashMap<>();
        if (!orderIds.isEmpty()) {
            List<Order> orders = orderMapper.selectBatchIds(orderIds);
            orderTimeMap = orders.stream().collect(Collectors.toMap(Order::getId, Order::getCreateTime));
        }

        // 按时间分组
        Map<String, List<OrderItem>> groupedItems = new LinkedHashMap<>();
        Map<Long, LocalDateTime> finalOrderTimeMap = orderTimeMap;

        for (OrderItem item : orderItems) {
            LocalDateTime orderTime = finalOrderTimeMap.get(item.getOrderId());
            if (orderTime == null) continue;

            String key = getTimeKey(orderTime.toLocalDate(), timeUnit);
            groupedItems.computeIfAbsent(key, k -> new ArrayList<>()).add(item);
        }

        // 生成完整的时间序列
        List<String> timeKeys = generateTimeKeys(startDate, endDate, timeUnit);

        List<SalesStatisticsVO.SalesTrend> trend = new ArrayList<>();
        for (String key : timeKeys) {
            List<OrderItem> items = groupedItems.getOrDefault(key, new ArrayList<>());

            BigDecimal sales = items.stream()
                    .map(OrderItem::getTotalPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            int orders = (int) items.stream()
                    .map(OrderItem::getOrderId)
                    .distinct()
                    .count();

            trend.add(SalesStatisticsVO.SalesTrend.builder()
                    .date(key)
                    .sales(sales)
                    .orders(orders)
                    .build());
        }

        return trend;
    }

    private String getTimeKey(LocalDate date, String timeUnit) {
        switch (timeUnit) {
            case "WEEK":
                int weekOfYear = date.get(WeekFields.ISO.weekOfWeekBasedYear());
                return date.getYear() + "-W" + String.format("%02d", weekOfYear);
            case "MONTH":
                return date.format(DateTimeFormatter.ofPattern("yyyy-MM"));
            case "YEAR":
                return String.valueOf(date.getYear());
            default: // DAY
                return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
    }

    private List<String> generateTimeKeys(LocalDate startDate, LocalDate endDate, String timeUnit) {
        List<String> keys = new ArrayList<>();
        LocalDate current = startDate;

        while (!current.isAfter(endDate)) {
            keys.add(getTimeKey(current, timeUnit));
            switch (timeUnit) {
                case "WEEK":
                    current = current.plusWeeks(1);
                    break;
                case "MONTH":
                    current = current.plusMonths(1);
                    break;
                case "YEAR":
                    current = current.plusYears(1);
                    break;
                default: // DAY
                    current = current.plusDays(1);
            }
        }

        return keys.stream().distinct().collect(Collectors.toList());
    }

    private SalesStatisticsVO buildEmptySalesStatistics() {
        return SalesStatisticsVO.builder()
                .summary(SalesStatisticsVO.SalesSummary.builder()
                        .totalSales(BigDecimal.ZERO)
                        .totalOrders(0)
                        .totalQuantity(0)
                        .avgOrderAmount(BigDecimal.ZERO)
                        .build())
                .trend(new ArrayList<>())
                .build();
    }
}
