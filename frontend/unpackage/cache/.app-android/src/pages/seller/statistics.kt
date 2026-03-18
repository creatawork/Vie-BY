@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME", "UNUSED_ANONYMOUS_PARAMETER", "NAME_SHADOWING", "UNNECESSARY_NOT_NULL_ASSERTION")
package uni.UNIBY001
import io.dcloud.uniapp.*
import io.dcloud.uniapp.extapi.*
import io.dcloud.uniapp.framework.*
import io.dcloud.uniapp.runtime.*
import io.dcloud.uniapp.vue.*
import io.dcloud.uniapp.vue.shared.*
import io.dcloud.unicloud.*
import io.dcloud.uts.*
import io.dcloud.uts.Map
import io.dcloud.uts.Set
import io.dcloud.uts.UTSAndroid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesSellerStatistics : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onLoad(fun(_: OnLoadOptions) {
            this.loadAllData()
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return createElementVNode("view", utsMapOf("class" to "page"), utsArrayOf(
            createElementVNode("view", utsMapOf("class" to "summary-section"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "summary-header"), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "summary-title"), "数据概览"),
                    createElementVNode("view", utsMapOf("class" to "time-selector"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                            "time-option",
                            utsMapOf("time-option-active" to (_ctx.timeUnit === "DAY"))
                        )), "onClick" to fun(){
                            _ctx.switchTimeUnit("DAY")
                        }
                        ), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                                "time-text",
                                utsMapOf("time-text-active" to (_ctx.timeUnit === "DAY"))
                            ))), "日", 2)
                        ), 10, utsArrayOf(
                            "onClick"
                        )),
                        createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                            "time-option",
                            utsMapOf("time-option-active" to (_ctx.timeUnit === "WEEK"))
                        )), "onClick" to fun(){
                            _ctx.switchTimeUnit("WEEK")
                        }
                        ), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                                "time-text",
                                utsMapOf("time-text-active" to (_ctx.timeUnit === "WEEK"))
                            ))), "周", 2)
                        ), 10, utsArrayOf(
                            "onClick"
                        )),
                        createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                            "time-option",
                            utsMapOf("time-option-active" to (_ctx.timeUnit === "MONTH"))
                        )), "onClick" to fun(){
                            _ctx.switchTimeUnit("MONTH")
                        }
                        ), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                                "time-text",
                                utsMapOf("time-text-active" to (_ctx.timeUnit === "MONTH"))
                            ))), "月", 2)
                        ), 10, utsArrayOf(
                            "onClick"
                        ))
                    ))
                )),
                createElementVNode("view", utsMapOf("class" to "summary-cards"), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "summary-card"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "card-value"), "¥" + toDisplayString(_ctx.formatMoney(_ctx.salesData.totalSales)), 1),
                        createElementVNode("text", utsMapOf("class" to "card-label"), "总销售额")
                    )),
                    createElementVNode("view", utsMapOf("class" to "summary-card"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "card-value"), toDisplayString(_ctx.salesData.totalOrders), 1),
                        createElementVNode("text", utsMapOf("class" to "card-label"), "订单数")
                    )),
                    createElementVNode("view", utsMapOf("class" to "summary-card"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "card-value"), toDisplayString(_ctx.salesData.totalQuantity), 1),
                        createElementVNode("text", utsMapOf("class" to "card-label"), "销量")
                    )),
                    createElementVNode("view", utsMapOf("class" to "summary-card"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "card-value"), "¥" + toDisplayString(_ctx.formatMoney(_ctx.salesData.avgOrderAmount)), 1),
                        createElementVNode("text", utsMapOf("class" to "card-label"), "客单价")
                    ))
                ))
            )),
            createElementVNode("view", utsMapOf("class" to "chart-section"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "section-header"), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "section-title"), "销售趋势")
                )),
                createElementVNode("view", utsMapOf("class" to "chart-container"), utsArrayOf(
                    if (_ctx.trendData.length > 0) {
                        createElementVNode("view", utsMapOf("key" to 0, "class" to "chart-wrapper"), utsArrayOf(
                            createElementVNode("view", utsMapOf("class" to "chart-bars"), utsArrayOf(
                                createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.trendData, fun(item, index, __index, _cached): Any {
                                    return createElementVNode("view", utsMapOf("class" to "chart-bar-item", "key" to index), utsArrayOf(
                                        createElementVNode("view", utsMapOf("class" to "bar-wrapper"), utsArrayOf(
                                            createElementVNode("view", utsMapOf("class" to "bar", "style" to normalizeStyle(utsMapOf("height" to (_ctx.getBarHeight(item.amount) + "px")))), null, 4)
                                        )),
                                        createElementVNode("text", utsMapOf("class" to "bar-label"), toDisplayString(item.label), 1)
                                    ))
                                }), 128)
                            )),
                            createElementVNode("view", utsMapOf("class" to "chart-legend"), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "legend-text"), "单位: 元")
                            ))
                        ))
                    } else {
                        createElementVNode("view", utsMapOf("key" to 1, "class" to "chart-empty"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "empty-text"), "暂无趋势数据")
                        ))
                    }
                ))
            )),
            createElementVNode("view", utsMapOf("class" to "ranking-section"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "section-header"), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "section-title"), "商品销量排行 TOP10")
                )),
                if (_ctx.rankingList.length > 0) {
                    createElementVNode("view", utsMapOf("key" to 0, "class" to "ranking-list"), utsArrayOf(
                        createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.rankingList, fun(item, index, __index, _cached): Any {
                            return createElementVNode("view", utsMapOf("class" to "ranking-item", "key" to item.productId), utsArrayOf(
                                createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                                    "rank-badge",
                                    _ctx.getRankClass(index)
                                ))), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                                        "rank-num",
                                        utsMapOf("rank-num-top" to (index < 3))
                                    ))), toDisplayString(index + 1), 3)
                                ), 2),
                                createElementVNode("image", utsMapOf("class" to "product-img", "src" to item.mainImage, "mode" to "aspectFill"), null, 8, utsArrayOf(
                                    "src"
                                )),
                                createElementVNode("view", utsMapOf("class" to "product-info"), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "product-name"), toDisplayString(item.productName), 1),
                                    createElementVNode("view", utsMapOf("class" to "product-stats"), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to "stat-item"), "销量: " + toDisplayString(item.sales), 1),
                                        createElementVNode("text", utsMapOf("class" to "stat-item"), "销售额: ¥" + toDisplayString(_ctx.formatMoney(item.salesAmount)), 1)
                                    ))
                                ))
                            ))
                        }), 128)
                    ))
                } else {
                    createElementVNode("view", utsMapOf("key" to 1, "class" to "ranking-empty"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "empty-icon"), "📊"),
                        createElementVNode("text", utsMapOf("class" to "empty-text"), "暂无排行数据")
                    ))
                }
            )),
            createElementVNode("view", utsMapOf("class" to "insight-grid"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "insight-card", "onClick" to _ctx.goInventoryLowStock), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "insight-value"), toDisplayString(_ctx.inventoryHealth.lowStockCount), 1),
                    createElementVNode("text", utsMapOf("class" to "insight-label"), "低库存 SKU")
                ), 8, utsArrayOf(
                    "onClick"
                )),
                createElementVNode("view", utsMapOf("class" to "insight-card", "onClick" to _ctx.goInventoryOutOfStock), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "insight-value insight-value-warn"), toDisplayString(_ctx.inventoryHealth.outOfStockCount), 1),
                    createElementVNode("text", utsMapOf("class" to "insight-label"), "缺货 SKU")
                ), 8, utsArrayOf(
                    "onClick"
                )),
                createElementVNode("view", utsMapOf("class" to "insight-card"), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "insight-value"), toDisplayString(_ctx.inventoryHealth.healthyRate.toFixed(1)) + "%", 1),
                    createElementVNode("text", utsMapOf("class" to "insight-label"), "库存健康率")
                ))
            )),
            createElementVNode("view", utsMapOf("class" to "dist-section"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "section-header"), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "section-title"), "订单状态分布")
                )),
                if (_ctx.orderDistribution.length > 0) {
                    createElementVNode("view", utsMapOf("key" to 0, "class" to "dist-list"), utsArrayOf(
                        createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.orderDistribution, fun(item, index, __index, _cached): Any {
                            return createElementVNode("view", utsMapOf("class" to "dist-item", "key" to index), utsArrayOf(
                                createElementVNode("view", utsMapOf("class" to "dist-row"), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "dist-name"), toDisplayString(item.statusDesc), 1),
                                    createElementVNode("text", utsMapOf("class" to "dist-meta"), toDisplayString(item.count) + " · " + toDisplayString(item.percentage.toFixed(1)) + "%", 1)
                                )),
                                createElementVNode("view", utsMapOf("class" to "dist-bar"), utsArrayOf(
                                    createElementVNode("view", utsMapOf("class" to "dist-bar-fill", "style" to normalizeStyle(utsMapOf("width" to (item.percentage + "%")))), null, 4)
                                ))
                            ))
                        }), 128)
                    ))
                } else {
                    createElementVNode("view", utsMapOf("key" to 1, "class" to "chart-empty"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "empty-text"), "暂无订单分布数据")
                    ))
                }
            )),
            createElementVNode("view", utsMapOf("class" to "ranking-section"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "section-header"), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "section-title"), "SKU 销量排行 TOP5")
                )),
                if (_ctx.skuRankingList.length > 0) {
                    createElementVNode("view", utsMapOf("key" to 0, "class" to "sku-list"), utsArrayOf(
                        createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.skuRankingList, fun(item, index, __index, _cached): Any {
                            return createElementVNode("view", utsMapOf("class" to "sku-item", "key" to item.skuId), utsArrayOf(
                                createElementVNode("view", utsMapOf("class" to "sku-rank"), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "sku-rank-num"), toDisplayString(index + 1), 1)
                                )),
                                createElementVNode("view", utsMapOf("class" to "sku-info"), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "sku-name"), toDisplayString(item.productName) + " · " + toDisplayString(item.skuName), 1),
                                    createElementVNode("text", utsMapOf("class" to "sku-sub"), "销量 " + toDisplayString(item.salesVolume) + " · ¥" + toDisplayString(_ctx.formatMoney(item.salesAmount)), 1)
                                ))
                            ))
                        }), 128)
                    ))
                } else {
                    createElementVNode("view", utsMapOf("key" to 1, "class" to "ranking-empty"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "empty-text"), "暂无 SKU 排行数据")
                    ))
                }
            )),
            if (isTrue(_ctx.isLoading)) {
                createElementVNode("view", utsMapOf("key" to 0, "class" to "loading-mask"), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "loading-text"), "加载中...")
                ))
            } else {
                createCommentVNode("v-if", true)
            }
        ))
    }
    open var isLoading: Boolean by `$data`
    open var timeUnit: String by `$data`
    open var salesData: SalesDataType by `$data`
    open var trendData: UTSArray<TrendItemType> by `$data`
    open var maxTrendAmount: Number by `$data`
    open var rankingList: UTSArray<RankingItemType> by `$data`
    open var orderDistribution: UTSArray<OrderStatusType> by `$data`
    open var inventoryHealth: InventoryHealthType by `$data`
    open var skuRankingList: UTSArray<SkuRankingType> by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return utsMapOf("isLoading" to false, "timeUnit" to "DAY" as String, "salesData" to SalesDataType(totalSales = 0, totalOrders = 0, totalQuantity = 0, avgOrderAmount = 0), "trendData" to utsArrayOf<TrendItemType>(), "maxTrendAmount" to 0, "rankingList" to utsArrayOf<RankingItemType>(), "orderDistribution" to utsArrayOf<OrderStatusType>(), "inventoryHealth" to InventoryHealthType(totalSkuCount = 0, lowStockCount = 0, outOfStockCount = 0, healthyRate = 0), "skuRankingList" to utsArrayOf<SkuRankingType>())
    }
    open var loadAllData = ::gen_loadAllData_fn
    open fun gen_loadAllData_fn() {
        this.isLoading = true
        UTSPromise.all(utsArrayOf(
            this.loadSalesStatistics(),
            this.loadProductRanking(),
            this.loadOrderStatusDistribution(),
            this.loadSkuRanking(),
            this.loadInventoryHealth()
        )).then(fun(){
            this.isLoading = false
        }
        ).`catch`(fun(err){
            console.error("加载数据失败:", err, " at pages/seller/statistics.uvue:275")
            this.isLoading = false
            uni_showToast(ShowToastOptions(title = "加载数据失败", icon = "none"))
        }
        )
    }
    open var loadSalesStatistics = ::gen_loadSalesStatistics_fn
    open fun gen_loadSalesStatistics_fn(): UTSPromise<Unit> {
        return UTSPromise(fun(resolve, reject){
            val params = StatisticsParams(startDate = null, endDate = null, timeUnit = this.timeUnit)
            getSalesStatistics(params).then(fun(res){
                val root = res.data as UTSJSONObject?
                if (root == null) {
                    resolve(Unit)
                    return
                }
                val summary = root.get("summary") as UTSJSONObject?
                if (summary != null) {
                    this.salesData = SalesDataType(totalSales = summary.getNumber("totalSales") ?: 0, totalOrders = (summary.getNumber("totalOrders") ?: 0).toInt(), totalQuantity = (summary.getNumber("totalQuantity") ?: 0).toInt(), avgOrderAmount = summary.getNumber("avgOrderAmount") ?: 0)
                }
                val trend = root.getArray("trend")
                this.parseTrendData(trend as UTSArray<Any>?)
                resolve(Unit)
            }
            ).`catch`(fun(err){
                console.error("获取销售统计失败:", err, " at pages/seller/statistics.uvue:312")
                reject(err)
            }
            )
        }
        )
    }
    open var parseTrendData = ::gen_parseTrendData_fn
    open fun gen_parseTrendData_fn(trend: UTSArray<Any>?) {
        if (trend == null) {
            this.trendData = utsArrayOf()
            this.maxTrendAmount = 0
            return
        }
        val items: UTSArray<TrendItemType> = utsArrayOf()
        var maxAmount: Number = 0
        run {
            var i: Number = 0
            while(i < trend.length){
                val item = trend[i] as UTSJSONObject
                val amount = item.getNumber("sales") ?: 0
                if (amount > maxAmount) {
                    maxAmount = amount
                }
                val dateStr = item.getString("date") ?: ""
                items.push(TrendItemType(date = dateStr, label = this.formatDateLabel(dateStr), amount = amount, orders = (item.getNumber("orders") ?: 0).toInt()))
                i++
            }
        }
        this.trendData = items
        this.maxTrendAmount = maxAmount
    }
    open var formatDateLabel = ::gen_formatDateLabel_fn
    open fun gen_formatDateLabel_fn(dateStr: String): String {
        if (dateStr === "" || dateStr.length < 5) {
            return dateStr
        }
        if (this.timeUnit === "DAY") {
            if (dateStr.length >= 10) {
                return dateStr.substring(5, 10)
            }
            return dateStr
        } else if (this.timeUnit === "WEEK") {
            return dateStr
        } else {
            if (dateStr.length >= 7) {
                return dateStr.substring(5, 7) + "月"
            }
            return dateStr
        }
    }
    open var loadProductRanking = ::gen_loadProductRanking_fn
    open fun gen_loadProductRanking_fn(): UTSPromise<Unit> {
        return UTSPromise(fun(resolve, reject){
            val params = RankingParams(limit = 10, orderBy = "sales")
            getProductRanking(params).then(fun(res){
                this.parseRankingData(res.data)
                resolve(Unit)
            }
            ).`catch`(fun(err){
                console.error("获取商品排行失败:", err, " at pages/seller/statistics.uvue:385")
                reject(err)
            }
            )
        }
        )
    }
    open var parseRankingData = ::gen_parseRankingData_fn
    open fun gen_parseRankingData_fn(data: Any?) {
        val list = data as UTSArray<Any>?
        if (list == null) {
            this.rankingList = utsArrayOf()
            return
        }
        val items: UTSArray<RankingItemType> = utsArrayOf()
        run {
            var i: Number = 0
            while(i < list.length){
                val item = list[i] as UTSJSONObject
                items.push(RankingItemType(productId = (item.getNumber("productId") ?: 0).toInt(), productName = item.getString("productName") ?: "", mainImage = item.getString("mainImage") ?: "", sales = (item.getNumber("salesVolume") ?: 0).toInt(), salesAmount = item.getNumber("salesAmount") ?: 0))
                i++
            }
        }
        this.rankingList = items
    }
    open var loadOrderStatusDistribution = ::gen_loadOrderStatusDistribution_fn
    open fun gen_loadOrderStatusDistribution_fn(): UTSPromise<Unit> {
        return UTSPromise(fun(resolve, reject){
            getOrderStatusDistribution(StatisticsParams(startDate = null, endDate = null, timeUnit = null)).then(fun(res: ResponseDataType){
                val root = res.data as UTSJSONObject?
                if (root != null) {
                    val list = root.getArray("distribution")
                    if (list != null) {
                        val items: UTSArray<OrderStatusType> = utsArrayOf()
                        run {
                            var i: Number = 0
                            while(i < list.length){
                                val item = list[i] as UTSJSONObject
                                items.push(OrderStatusType(status = (item.getNumber("status") ?: 0).toInt(), statusDesc = item.getString("statusDesc") ?: "", count = (item.getNumber("count") ?: 0).toInt(), percentage = item.getNumber("percentage") ?: 0))
                                i++
                            }
                        }
                        this.orderDistribution = items
                    }
                }
                resolve(Unit)
            }
            ).`catch`(fun(err: Any?){
                console.error("获取订单分布失败:", err, " at pages/seller/statistics.uvue:436")
                reject(err)
            }
            )
        }
        )
    }
    open var goInventoryLowStock = ::gen_goInventoryLowStock_fn
    open fun gen_goInventoryLowStock_fn() {
        uni_navigateTo(NavigateToOptions(url = "/pages/seller/inventory?lowStock=true"))
    }
    open var goInventoryOutOfStock = ::gen_goInventoryOutOfStock_fn
    open fun gen_goInventoryOutOfStock_fn() {
        uni_navigateTo(NavigateToOptions(url = "/pages/seller/inventory?outOfStock=true"))
    }
    open var loadSkuRanking = ::gen_loadSkuRanking_fn
    open fun gen_loadSkuRanking_fn(): UTSPromise<Unit> {
        return UTSPromise(fun(resolve, reject){
            getSkuRanking(RankingParams(limit = 5, orderBy = "sales")).then(fun(res: ResponseDataType){
                val list = res.data as UTSArray<Any>?
                if (list != null) {
                    val items: UTSArray<SkuRankingType> = utsArrayOf()
                    run {
                        var i: Number = 0
                        while(i < list.length){
                            val item = list[i] as UTSJSONObject
                            items.push(SkuRankingType(skuId = (item.getNumber("skuId") ?: 0).toInt(), skuName = item.getString("skuName") ?: "", productName = item.getString("productName") ?: "", salesVolume = (item.getNumber("salesVolume") ?: 0).toInt(), salesAmount = item.getNumber("salesAmount") ?: 0))
                            i++
                        }
                    }
                    this.skuRankingList = items
                }
                resolve(Unit)
            }
            ).`catch`(fun(err){
                console.error("获取SKU排行失败:", err, " at pages/seller/statistics.uvue:475")
                reject(err)
            }
            )
        }
        )
    }
    open var loadInventoryHealth = ::gen_loadInventoryHealth_fn
    open fun gen_loadInventoryHealth_fn(): UTSPromise<Unit> {
        return UTSPromise(fun(resolve, reject){
            getInventoryHealth().then(fun(res){
                val data = res.data as UTSJSONObject?
                if (data != null) {
                    this.inventoryHealth = InventoryHealthType(totalSkuCount = (data.getNumber("totalSkuCount") ?: 0).toInt(), lowStockCount = (data.getNumber("lowStockCount") ?: 0).toInt(), outOfStockCount = (data.getNumber("outOfStockCount") ?: 0).toInt(), healthyRate = data.getNumber("healthyRate") ?: 0)
                }
                resolve(Unit)
            }
            ).`catch`(fun(err){
                console.error("获取库存健康失败:", err, " at pages/seller/statistics.uvue:496")
                reject(err)
            }
            )
        }
        )
    }
    open var switchTimeUnit = ::gen_switchTimeUnit_fn
    open fun gen_switchTimeUnit_fn(unit: String) {
        if (this.timeUnit === unit) {
            return
        }
        this.timeUnit = unit
        this.loadSalesStatistics()
    }
    open var formatMoney = ::gen_formatMoney_fn
    open fun gen_formatMoney_fn(amount: Number): String {
        if (amount >= 10000) {
            return (amount / 10000).toFixed(2) + "万"
        }
        return amount.toFixed(2)
    }
    open var getBarHeight = ::gen_getBarHeight_fn
    open fun gen_getBarHeight_fn(amount: Number): Number {
        if (this.maxTrendAmount <= 0) {
            return 10
        }
        val maxHeight: Number = 100
        val minHeight: Number = 10
        val ratio = amount / this.maxTrendAmount
        return Math.max(minHeight, ratio * maxHeight)
    }
    open var getRankClass = ::gen_getRankClass_fn
    open fun gen_getRankClass_fn(index: Number): String {
        if (index === 0) {
            return "rank-badge-first"
        }
        if (index === 1) {
            return "rank-badge-second"
        }
        if (index === 2) {
            return "rank-badge-third"
        }
        return ""
    }
    companion object {
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            normalizeCssStyles(utsArrayOf(
                styles0
            ), utsArrayOf(
                GenApp.styles
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return utsMapOf("page" to padStyleMapOf(utsMapOf("backgroundColor" to "#f7f8fa", "display" to "flex", "flexDirection" to "column", "flex" to 1, "paddingBottom" to 20)), "summary-section" to padStyleMapOf(utsMapOf("backgroundColor" to "#0066CC", "paddingTop" to 66, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16)), "summary-header" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "marginBottom" to 16)), "summary-title" to padStyleMapOf(utsMapOf("fontSize" to 16, "fontWeight" to "700", "color" to "#ffffff")), "time-selector" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "backgroundColor" to "rgba(255,255,255,0.2)", "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "paddingTop" to 2, "paddingRight" to 2, "paddingBottom" to 2, "paddingLeft" to 2)), "time-option" to padStyleMapOf(utsMapOf("paddingTop" to 6, "paddingRight" to 14, "paddingBottom" to 6, "paddingLeft" to 14, "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14)), "time-option-active" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff")), "time-text" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "rgba(255,255,255,0.8)")), "time-text-active" to padStyleMapOf(utsMapOf("color" to "#0066CC", "fontWeight" to "700")), "summary-cards" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "flexWrap" to "wrap", "marginTop" to -6, "marginRight" to -6, "marginBottom" to -6, "marginLeft" to -6)), "summary-card" to padStyleMapOf(utsMapOf("width" to "46%", "paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "boxSizing" to "border-box", "backgroundColor" to "rgba(255,255,255,0.15)", "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "display" to "flex", "flexDirection" to "column", "alignItems" to "center", "marginTop" to 6, "marginRight" to 6, "marginBottom" to 6, "marginLeft" to 6)), "card-value" to padStyleMapOf(utsMapOf("fontSize" to 20, "fontWeight" to "700", "color" to "#ffffff")), "card-label" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "rgba(255,255,255,0.8)", "marginTop" to 6)), "chart-section" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "marginTop" to 12, "marginRight" to 16, "marginBottom" to 12, "marginLeft" to 16, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "boxShadow" to "0 2px 8px rgba(0, 0, 0, 0.02)")), "section-header" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "marginBottom" to 16)), "section-title" to padStyleMapOf(utsMapOf("fontSize" to 15, "fontWeight" to "700", "color" to "#333333")), "chart-container" to padStyleMapOf(utsMapOf("minHeight" to 160)), "chart-wrapper" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "column")), "chart-bars" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-around", "alignItems" to "flex-end", "height" to 120, "paddingTop" to 0, "paddingRight" to 8, "paddingBottom" to 0, "paddingLeft" to 8)), "chart-bar-item" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "column", "alignItems" to "center", "flex" to 1, "maxWidth" to 40)), "bar-wrapper" to padStyleMapOf(utsMapOf("height" to 100, "display" to "flex", "flexDirection" to "column", "justifyContent" to "flex-end", "alignItems" to "center")), "bar" to padStyleMapOf(utsMapOf("width" to 20, "backgroundImage" to "linear-gradient(180deg, #0066CC 0%, #4d94db 100%)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopLeftRadius" to 4, "borderTopRightRadius" to 4, "borderBottomRightRadius" to 0, "borderBottomLeftRadius" to 0, "minHeight" to 4)), "bar-label" to padStyleMapOf(utsMapOf("fontSize" to 10, "color" to "#999999", "marginTop" to 6, "textAlign" to "center")), "chart-legend" to padStyleMapOf(utsMapOf("display" to "flex", "justifyContent" to "flex-end", "marginTop" to 12)), "legend-text" to padStyleMapOf(utsMapOf("fontSize" to 11, "color" to "#999999")), "chart-empty" to padStyleMapOf(utsMapOf("display" to "flex", "alignItems" to "center", "justifyContent" to "center", "height" to 120)), "ranking-section" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "marginTop" to 0, "marginRight" to 16, "marginBottom" to 12, "marginLeft" to 16, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "boxShadow" to "0 2px 8px rgba(0, 0, 0, 0.02)")), "ranking-list" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "column")), "ranking-item" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "paddingTop" to 12, "paddingRight" to 0, "paddingBottom" to 12, "paddingLeft" to 0, "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "#f5f5f5")), "rank-badge" to padStyleMapOf(utsMapOf("width" to 24, "height" to 24, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#f5f5f5", "display" to "flex", "alignItems" to "center", "justifyContent" to "center", "flexShrink" to 0)), "rank-badge-first" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffd700")), "rank-badge-second" to padStyleMapOf(utsMapOf("backgroundColor" to "#c0c0c0")), "rank-badge-third" to padStyleMapOf(utsMapOf("backgroundColor" to "#cd7f32")), "rank-num" to padStyleMapOf(utsMapOf("fontSize" to 12, "fontWeight" to "700", "color" to "#666666")), "rank-num-top" to padStyleMapOf(utsMapOf("color" to "#ffffff")), "product-img" to padStyleMapOf(utsMapOf("width" to 50, "height" to 50, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#f5f5f5", "marginLeft" to 12, "flexShrink" to 0)), "product-info" to padStyleMapOf(utsMapOf("flex" to 1, "marginLeft" to 12, "display" to "flex", "flexDirection" to "column")), "product-name" to padStyleMapOf(utsMapOf("fontSize" to 14, "fontWeight" to "bold", "color" to "#333333", "lineHeight" to 1.4, "overflow" to "hidden", "textOverflow" to "ellipsis")), "product-stats" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "marginTop" to 6)), "stat-item" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999", "marginRight" to 16)), "ranking-empty" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "column", "alignItems" to "center", "justifyContent" to "center", "paddingTop" to 40, "paddingRight" to 0, "paddingBottom" to 40, "paddingLeft" to 0)), "empty-icon" to padStyleMapOf(utsMapOf("fontSize" to 40, "marginBottom" to 12)), "empty-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#999999")), "loading-mask" to padStyleMapOf(utsMapOf("position" to "fixed", "top" to 0, "left" to 0, "right" to 0, "bottom" to 0, "backgroundColor" to "rgba(255,255,255,0.8)", "display" to "flex", "alignItems" to "center", "justifyContent" to "center", "zIndex" to 1000)), "loading-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#666666")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = utsMapOf()
        var emits: Map<String, Any?> = utsMapOf()
        var props = normalizePropsOptions(utsMapOf())
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf()
        var components: Map<String, CreateVueComponent> = utsMapOf()
    }
}
