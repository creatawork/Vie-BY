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
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesSellerAdminStatistics : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onLoad(fun(_: OnLoadOptions) {
            this.loadData()
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return createElementVNode("scroll-view", utsMapOf("class" to "page", "scroll-y" to ""), utsArrayOf(
            createElementVNode("view", utsMapOf("class" to "top-cards"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "data-card card-1"), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "card-header"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "card-title"), "用户"),
                        createElementVNode("text", utsMapOf("class" to "card-icon"), "👥")
                    )),
                    createElementVNode("text", utsMapOf("class" to "card-number"), toDisplayString(_ctx.overview.totalUsers), 1),
                    createElementVNode("view", utsMapOf("class" to "card-footer"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "footer-label"), "活跃用户"),
                        createElementVNode("text", utsMapOf("class" to "footer-value"), toDisplayString(_ctx.overview.activeUsers), 1)
                    ))
                )),
                createElementVNode("view", utsMapOf("class" to "data-card card-2"), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "card-header"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "card-title"), "商品"),
                        createElementVNode("text", utsMapOf("class" to "card-icon"), "🛍️")
                    )),
                    createElementVNode("text", utsMapOf("class" to "card-number"), toDisplayString(_ctx.overview.totalProducts), 1),
                    createElementVNode("view", utsMapOf("class" to "card-footer"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "footer-label"), "在售"),
                        createElementVNode("text", utsMapOf("class" to "footer-value"), toDisplayString(_ctx.overview.onlineProducts), 1)
                    ))
                )),
                createElementVNode("view", utsMapOf("class" to "data-card card-3"), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "card-header"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "card-title"), "订单"),
                        createElementVNode("text", utsMapOf("class" to "card-icon"), "📦")
                    )),
                    createElementVNode("text", utsMapOf("class" to "card-number"), toDisplayString(_ctx.overview.totalOrders), 1),
                    createElementVNode("view", utsMapOf("class" to "card-footer"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "footer-label"), "已完成"),
                        createElementVNode("text", utsMapOf("class" to "footer-value"), toDisplayString(_ctx.overview.completedOrders), 1)
                    ))
                )),
                createElementVNode("view", utsMapOf("class" to "data-card card-4"), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "card-header"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "card-title"), "待审核"),
                        createElementVNode("text", utsMapOf("class" to "card-icon"), "⚠️")
                    )),
                    createElementVNode("text", utsMapOf("class" to "card-number"), toDisplayString(_ctx.overview.pendingProducts), 1),
                    createElementVNode("view", utsMapOf("class" to "card-footer"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "footer-label"), "需处理"),
                        createElementVNode("text", utsMapOf("class" to "footer-value"), toDisplayString(_ctx.overview.pendingProducts), 1)
                    ))
                ))
            )),
            createElementVNode("view", utsMapOf("class" to "analysis-section"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "section-title-bar"), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "section-title"), "商品分析"),
                    createElementVNode("text", utsMapOf("class" to "section-total"), "总计 " + toDisplayString(_ctx.getTotalProducts()), 1)
                )),
                createElementVNode("view", utsMapOf("class" to "analysis-card"), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "analysis-item"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "item-left"), utsArrayOf(
                            createElementVNode("view", utsMapOf("class" to "item-indicator ind-orange")),
                            createElementVNode("text", utsMapOf("class" to "item-name"), "待审核")
                        )),
                        createElementVNode("view", utsMapOf("class" to "item-right"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "item-number"), toDisplayString(_ctx.productDist.pending), 1),
                            createElementVNode("text", utsMapOf("class" to "item-percent"), toDisplayString(_ctx.getPercentage(_ctx.productDist.pending, _ctx.getTotalProducts())), 1)
                        ))
                    )),
                    createElementVNode("view", utsMapOf("class" to "progress-track"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "progress-bar bar-orange", "style" to normalizeStyle(utsMapOf("width" to _ctx.getPercentage(_ctx.productDist.pending, _ctx.getTotalProducts())))), null, 4)
                    )),
                    createElementVNode("view", utsMapOf("class" to "analysis-item"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "item-left"), utsArrayOf(
                            createElementVNode("view", utsMapOf("class" to "item-indicator ind-green")),
                            createElementVNode("text", utsMapOf("class" to "item-name"), "已上架")
                        )),
                        createElementVNode("view", utsMapOf("class" to "item-right"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "item-number"), toDisplayString(_ctx.productDist.online), 1),
                            createElementVNode("text", utsMapOf("class" to "item-percent"), toDisplayString(_ctx.getPercentage(_ctx.productDist.online, _ctx.getTotalProducts())), 1)
                        ))
                    )),
                    createElementVNode("view", utsMapOf("class" to "progress-track"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "progress-bar bar-green", "style" to normalizeStyle(utsMapOf("width" to _ctx.getPercentage(_ctx.productDist.online, _ctx.getTotalProducts())))), null, 4)
                    )),
                    createElementVNode("view", utsMapOf("class" to "analysis-item"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "item-left"), utsArrayOf(
                            createElementVNode("view", utsMapOf("class" to "item-indicator ind-gray")),
                            createElementVNode("text", utsMapOf("class" to "item-name"), "已下架")
                        )),
                        createElementVNode("view", utsMapOf("class" to "item-right"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "item-number"), toDisplayString(_ctx.productDist.offline), 1),
                            createElementVNode("text", utsMapOf("class" to "item-percent"), toDisplayString(_ctx.getPercentage(_ctx.productDist.offline, _ctx.getTotalProducts())), 1)
                        ))
                    )),
                    createElementVNode("view", utsMapOf("class" to "progress-track"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "progress-bar bar-gray", "style" to normalizeStyle(utsMapOf("width" to _ctx.getPercentage(_ctx.productDist.offline, _ctx.getTotalProducts())))), null, 4)
                    )),
                    createElementVNode("view", utsMapOf("class" to "analysis-item"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "item-left"), utsArrayOf(
                            createElementVNode("view", utsMapOf("class" to "item-indicator ind-red")),
                            createElementVNode("text", utsMapOf("class" to "item-name"), "审核不通过")
                        )),
                        createElementVNode("view", utsMapOf("class" to "item-right"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "item-number"), toDisplayString(_ctx.productDist.rejected), 1),
                            createElementVNode("text", utsMapOf("class" to "item-percent"), toDisplayString(_ctx.getPercentage(_ctx.productDist.rejected, _ctx.getTotalProducts())), 1)
                        ))
                    )),
                    createElementVNode("view", utsMapOf("class" to "progress-track"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "progress-bar bar-red", "style" to normalizeStyle(utsMapOf("width" to _ctx.getPercentage(_ctx.productDist.rejected, _ctx.getTotalProducts())))), null, 4)
                    ))
                ))
            )),
            createElementVNode("view", utsMapOf("class" to "analysis-section"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "section-title-bar"), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "section-title"), "订单分析"),
                    createElementVNode("text", utsMapOf("class" to "section-total"), "总计 " + toDisplayString(_ctx.getTotalOrders()), 1)
                )),
                createElementVNode("view", utsMapOf("class" to "analysis-card"), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "analysis-item"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "item-left"), utsArrayOf(
                            createElementVNode("view", utsMapOf("class" to "item-indicator ind-orange")),
                            createElementVNode("text", utsMapOf("class" to "item-name"), "待支付")
                        )),
                        createElementVNode("view", utsMapOf("class" to "item-right"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "item-number"), toDisplayString(_ctx.orderDist.unpaid), 1),
                            createElementVNode("text", utsMapOf("class" to "item-percent"), toDisplayString(_ctx.getPercentage(_ctx.orderDist.unpaid, _ctx.getTotalOrders())), 1)
                        ))
                    )),
                    createElementVNode("view", utsMapOf("class" to "progress-track"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "progress-bar bar-orange", "style" to normalizeStyle(utsMapOf("width" to _ctx.getPercentage(_ctx.orderDist.unpaid, _ctx.getTotalOrders())))), null, 4)
                    )),
                    createElementVNode("view", utsMapOf("class" to "analysis-item"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "item-left"), utsArrayOf(
                            createElementVNode("view", utsMapOf("class" to "item-indicator ind-blue")),
                            createElementVNode("text", utsMapOf("class" to "item-name"), "待发货")
                        )),
                        createElementVNode("view", utsMapOf("class" to "item-right"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "item-number"), toDisplayString(_ctx.orderDist.unshipped), 1),
                            createElementVNode("text", utsMapOf("class" to "item-percent"), toDisplayString(_ctx.getPercentage(_ctx.orderDist.unshipped, _ctx.getTotalOrders())), 1)
                        ))
                    )),
                    createElementVNode("view", utsMapOf("class" to "progress-track"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "progress-bar bar-blue", "style" to normalizeStyle(utsMapOf("width" to _ctx.getPercentage(_ctx.orderDist.unshipped, _ctx.getTotalOrders())))), null, 4)
                    )),
                    createElementVNode("view", utsMapOf("class" to "analysis-item"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "item-left"), utsArrayOf(
                            createElementVNode("view", utsMapOf("class" to "item-indicator ind-purple")),
                            createElementVNode("text", utsMapOf("class" to "item-name"), "待收货")
                        )),
                        createElementVNode("view", utsMapOf("class" to "item-right"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "item-number"), toDisplayString(_ctx.orderDist.shipped), 1),
                            createElementVNode("text", utsMapOf("class" to "item-percent"), toDisplayString(_ctx.getPercentage(_ctx.orderDist.shipped, _ctx.getTotalOrders())), 1)
                        ))
                    )),
                    createElementVNode("view", utsMapOf("class" to "progress-track"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "progress-bar bar-purple", "style" to normalizeStyle(utsMapOf("width" to _ctx.getPercentage(_ctx.orderDist.shipped, _ctx.getTotalOrders())))), null, 4)
                    )),
                    createElementVNode("view", utsMapOf("class" to "analysis-item"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "item-left"), utsArrayOf(
                            createElementVNode("view", utsMapOf("class" to "item-indicator ind-green")),
                            createElementVNode("text", utsMapOf("class" to "item-name"), "已完成")
                        )),
                        createElementVNode("view", utsMapOf("class" to "item-right"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "item-number"), toDisplayString(_ctx.orderDist.completed), 1),
                            createElementVNode("text", utsMapOf("class" to "item-percent"), toDisplayString(_ctx.getPercentage(_ctx.orderDist.completed, _ctx.getTotalOrders())), 1)
                        ))
                    )),
                    createElementVNode("view", utsMapOf("class" to "progress-track"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "progress-bar bar-green", "style" to normalizeStyle(utsMapOf("width" to _ctx.getPercentage(_ctx.orderDist.completed, _ctx.getTotalOrders())))), null, 4)
                    )),
                    createElementVNode("view", utsMapOf("class" to "analysis-item"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "item-left"), utsArrayOf(
                            createElementVNode("view", utsMapOf("class" to "item-indicator ind-gray")),
                            createElementVNode("text", utsMapOf("class" to "item-name"), "已取消")
                        )),
                        createElementVNode("view", utsMapOf("class" to "item-right"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "item-number"), toDisplayString(_ctx.orderDist.cancelled), 1),
                            createElementVNode("text", utsMapOf("class" to "item-percent"), toDisplayString(_ctx.getPercentage(_ctx.orderDist.cancelled, _ctx.getTotalOrders())), 1)
                        ))
                    )),
                    createElementVNode("view", utsMapOf("class" to "progress-track"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "progress-bar bar-gray", "style" to normalizeStyle(utsMapOf("width" to _ctx.getPercentage(_ctx.orderDist.cancelled, _ctx.getTotalOrders())))), null, 4)
                    ))
                ))
            ))
        ))
    }
    open var overview: OverviewType by `$data`
    open var productDist: ProductDistType by `$data`
    open var orderDist: OrderDistType by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return utsMapOf("overview" to OverviewType(totalUsers = 0, activeUsers = 0, totalProducts = 0, pendingProducts = 0, onlineProducts = 0, totalOrders = 0, completedOrders = 0, pendingOrders = 0), "productDist" to ProductDistType(pending = 0, online = 0, offline = 0, rejected = 0), "orderDist" to OrderDistType(unpaid = 0, unshipped = 0, shipped = 0, completed = 0, cancelled = 0))
    }
    open var loadData = ::gen_loadData_fn
    open fun gen_loadData_fn() {
        this.loadOverview()
        this.loadProductDist()
        this.loadOrderDist()
    }
    open var loadOverview = ::gen_loadOverview_fn
    open fun gen_loadOverview_fn() {
        getAdminOverview().then(fun(res: ResponseDataType){
            if (res.code === 200 && res.data != null) {
                val data = res.data as UTSJSONObject
                this.overview = OverviewType(totalUsers = (data.getNumber("totalUsers") ?: 0).toInt(), activeUsers = (data.getNumber("activeUsers") ?: 0).toInt(), totalProducts = (data.getNumber("totalProducts") ?: 0).toInt(), pendingProducts = (data.getNumber("pendingProducts") ?: 0).toInt(), onlineProducts = (data.getNumber("onlineProducts") ?: 0).toInt(), totalOrders = (data.getNumber("totalOrders") ?: 0).toInt(), completedOrders = (data.getNumber("completedOrders") ?: 0).toInt(), pendingOrders = (data.getNumber("pendingOrders") ?: 0).toInt())
            }
        }
        ).`catch`(fun(err: Any?){
            val error = err as UTSError
            uni_showToast(ShowToastOptions(title = error.message, icon = "none"))
        }
        )
    }
    open var loadProductDist = ::gen_loadProductDist_fn
    open fun gen_loadProductDist_fn() {
        getProductDistribution().then(fun(res: ResponseDataType){
            if (res.code === 200 && res.data != null) {
                val data = res.data as UTSJSONObject
                this.productDist = ProductDistType(pending = (data.getNumber("pending") ?: 0).toInt(), online = (data.getNumber("online") ?: 0).toInt(), offline = (data.getNumber("offline") ?: 0).toInt(), rejected = (data.getNumber("rejected") ?: 0).toInt())
            }
        }
        ).`catch`(fun(err: Any?){
            console.error("加载商品分布失败:", err, " at pages/seller/admin-statistics.uvue:302")
        }
        )
    }
    open var loadOrderDist = ::gen_loadOrderDist_fn
    open fun gen_loadOrderDist_fn() {
        getOrderDistribution().then(fun(res: ResponseDataType){
            if (res.code === 200 && res.data != null) {
                val data = res.data as UTSJSONObject
                this.orderDist = OrderDistType(unpaid = (data.getNumber("unpaid") ?: 0).toInt(), unshipped = (data.getNumber("unshipped") ?: 0).toInt(), shipped = (data.getNumber("shipped") ?: 0).toInt(), completed = (data.getNumber("completed") ?: 0).toInt(), cancelled = (data.getNumber("cancelled") ?: 0).toInt())
            }
        }
        ).`catch`(fun(err: Any?){
            console.error("加载订单分布失败:", err, " at pages/seller/admin-statistics.uvue:318")
        }
        )
    }
    open var getTotalProducts = ::gen_getTotalProducts_fn
    open fun gen_getTotalProducts_fn(): Number {
        return this.productDist.pending + this.productDist.online + this.productDist.offline + this.productDist.rejected
    }
    open var getTotalOrders = ::gen_getTotalOrders_fn
    open fun gen_getTotalOrders_fn(): Number {
        return this.orderDist.unpaid + this.orderDist.unshipped + this.orderDist.shipped + this.orderDist.completed + this.orderDist.cancelled
    }
    open var getPercentage = ::gen_getPercentage_fn
    open fun gen_getPercentage_fn(value: Number, total: Number): String {
        if (total === 0) {
            return "0%"
        }
        val percent = (value / total * 100).toFixed(0)
        return "" + percent + "%"
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
                return utsMapOf("page" to padStyleMapOf(utsMapOf("backgroundColor" to "#f7f8fa", "paddingTop" to 15, "paddingRight" to 15, "paddingBottom" to 15, "paddingLeft" to 15)), "top-cards" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "marginBottom" to 20)), "data-card" to padStyleMapOf(utsMapOf("width" to "23%", "backgroundColor" to "#ffffff", "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "boxShadow" to "0 2px 8px rgba(0, 0, 0, 0.06)")), "card-1" to padStyleMapOf(utsMapOf("borderTopWidth" to 3, "borderTopStyle" to "solid", "borderTopColor" to "#3b82f6")), "card-2" to padStyleMapOf(utsMapOf("borderTopWidth" to 3, "borderTopStyle" to "solid", "borderTopColor" to "#10b981")), "card-3" to padStyleMapOf(utsMapOf("borderTopWidth" to 3, "borderTopStyle" to "solid", "borderTopColor" to "#f59e0b")), "card-4" to padStyleMapOf(utsMapOf("borderTopWidth" to 3, "borderTopStyle" to "solid", "borderTopColor" to "#ef4444")), "card-header" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "marginBottom" to 8)), "card-title" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#6b7280")), "card-icon" to padStyleMapOf(utsMapOf("fontSize" to 18)), "card-number" to padStyleMapOf(utsMapOf("fontSize" to 24, "fontWeight" to "bold", "color" to "#111827", "display" to "flex", "marginBottom" to 6)), "card-footer" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "column", "paddingTop" to 6, "borderTopWidth" to 1, "borderTopStyle" to "solid", "borderTopColor" to "#f3f4f6")), "footer-label" to padStyleMapOf(utsMapOf("fontSize" to 11, "color" to "#9ca3af", "marginBottom" to 2)), "footer-value" to padStyleMapOf(utsMapOf("fontSize" to 13, "fontWeight" to "bold", "color" to "#6b7280")), "analysis-section" to padStyleMapOf(utsMapOf("marginBottom" to 20)), "section-title-bar" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "marginBottom" to 12)), "section-title" to padStyleMapOf(utsMapOf("fontSize" to 18, "fontWeight" to "bold", "color" to "#111827")), "section-total" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#6b7280")), "analysis-card" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "boxShadow" to "0 2px 12px rgba(0, 0, 0, 0.06)")), "analysis-item" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "marginBottom" to 8)), "item-left" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center")), "item-indicator" to padStyleMapOf(utsMapOf("width" to 12, "height" to 12, "borderTopLeftRadius" to 6, "borderTopRightRadius" to 6, "borderBottomRightRadius" to 6, "borderBottomLeftRadius" to 6, "marginRight" to 10)), "ind-blue" to padStyleMapOf(utsMapOf("backgroundColor" to "#3b82f6")), "ind-green" to padStyleMapOf(utsMapOf("backgroundColor" to "#10b981")), "ind-orange" to padStyleMapOf(utsMapOf("backgroundColor" to "#f59e0b")), "ind-red" to padStyleMapOf(utsMapOf("backgroundColor" to "#ef4444")), "ind-gray" to padStyleMapOf(utsMapOf("backgroundColor" to "#9ca3af")), "ind-purple" to padStyleMapOf(utsMapOf("backgroundColor" to "#8b5cf6")), "item-name" to padStyleMapOf(utsMapOf("fontSize" to 15, "color" to "#374151")), "item-right" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center")), "item-number" to padStyleMapOf(utsMapOf("fontSize" to 16, "fontWeight" to "bold", "color" to "#111827", "marginRight" to 8)), "item-percent" to padStyleMapOf(utsMapOf("fontSize" to 13, "color" to "#9ca3af", "minWidth" to 40, "textAlign" to "right")), "progress-track" to padStyleMapOf(utsMapOf("height" to 6, "backgroundColor" to "#f3f4f6", "borderTopLeftRadius" to 3, "borderTopRightRadius" to 3, "borderBottomRightRadius" to 3, "borderBottomLeftRadius" to 3, "marginBottom" to 16, "overflow" to "hidden")), "progress-bar" to padStyleMapOf(utsMapOf("height" to "100%", "borderTopLeftRadius" to 3, "borderTopRightRadius" to 3, "borderBottomRightRadius" to 3, "borderBottomLeftRadius" to 3)), "bar-blue" to padStyleMapOf(utsMapOf("backgroundColor" to "#3b82f6")), "bar-green" to padStyleMapOf(utsMapOf("backgroundColor" to "#10b981")), "bar-orange" to padStyleMapOf(utsMapOf("backgroundColor" to "#f59e0b")), "bar-red" to padStyleMapOf(utsMapOf("backgroundColor" to "#ef4444")), "bar-gray" to padStyleMapOf(utsMapOf("backgroundColor" to "#9ca3af")), "bar-purple" to padStyleMapOf(utsMapOf("backgroundColor" to "#8b5cf6")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = utsMapOf()
        var emits: Map<String, Any?> = utsMapOf()
        var props = normalizePropsOptions(utsMapOf())
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf()
        var components: Map<String, CreateVueComponent> = utsMapOf()
    }
}
