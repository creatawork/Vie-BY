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
import io.dcloud.uniapp.extapi.showModal as uni_showModal
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesSellerProductAudit : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onLoad(fun(_: OnLoadOptions) {
            this.loadProducts()
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return createElementVNode("view", utsMapOf("class" to "page"), utsArrayOf(
            createElementVNode("view", utsMapOf("class" to "filter-header"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "status-tabs"), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                        "tab-item",
                        utsMapOf("active" to (_ctx.currentStatus === 0))
                    )), "onClick" to fun(){
                        _ctx.switchStatus(0)
                    }
                    ), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                            "tab-text",
                            utsMapOf("tab-text--active" to (_ctx.currentStatus === 0))
                        ))), "待审核", 2),
                        if (_ctx.currentStatus === 0) {
                            createElementVNode("view", utsMapOf("key" to 0, "class" to "active-line"))
                        } else {
                            createCommentVNode("v-if", true)
                        }
                    ), 10, utsArrayOf(
                        "onClick"
                    )),
                    createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                        "tab-item",
                        utsMapOf("active" to (_ctx.currentStatus === 1))
                    )), "onClick" to fun(){
                        _ctx.switchStatus(1)
                    }
                    ), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                            "tab-text",
                            utsMapOf("tab-text--active" to (_ctx.currentStatus === 1))
                        ))), "已通过", 2),
                        if (_ctx.currentStatus === 1) {
                            createElementVNode("view", utsMapOf("key" to 0, "class" to "active-line"))
                        } else {
                            createCommentVNode("v-if", true)
                        }
                    ), 10, utsArrayOf(
                        "onClick"
                    )),
                    createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                        "tab-item",
                        utsMapOf("active" to (_ctx.currentStatus === 3))
                    )), "onClick" to fun(){
                        _ctx.switchStatus(3)
                    }
                    ), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                            "tab-text",
                            utsMapOf("tab-text--active" to (_ctx.currentStatus === 3))
                        ))), "已驳回", 2),
                        if (_ctx.currentStatus === 3) {
                            createElementVNode("view", utsMapOf("key" to 0, "class" to "active-line"))
                        } else {
                            createCommentVNode("v-if", true)
                        }
                    ), 10, utsArrayOf(
                        "onClick"
                    ))
                ))
            )),
            createElementVNode("scroll-view", utsMapOf("class" to "product-scroll", "scroll-y" to "true", "onScrolltolower" to _ctx.loadMore, "onRefresherrefresh" to _ctx.onRefresh, "refresher-enabled" to true, "refresher-triggered" to _ctx.isRefreshing, "show-scrollbar" to false), utsArrayOf(
                if (isTrue(_ctx.isLoading && _ctx.products.length === 0)) {
                    createElementVNode("view", utsMapOf("key" to 0, "class" to "loading-state"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "loading-text"), "加载中...")
                    ))
                } else {
                    createElementVNode("view", utsMapOf("key" to 1, "class" to "product-list"), utsArrayOf(
                        createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.products, fun(product, __key, __index, _cached): Any {
                            return createElementVNode("view", utsMapOf("class" to "product-card", "key" to product.id, "onClick" to fun(){
                                _ctx.goToDetail(product.id)
                            }
                            ), utsArrayOf(
                                createElementVNode("image", utsMapOf("class" to "product-img", "src" to product.mainImage, "mode" to "aspectFill"), null, 8, utsArrayOf(
                                    "src"
                                )),
                                createElementVNode("view", utsMapOf("class" to "product-info"), utsArrayOf(
                                    createElementVNode("view", utsMapOf("class" to "info-top"), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to "product-name"), toDisplayString(product.productName), 1),
                                        createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                                            "status-tag",
                                            _ctx.getStatusClass(product.status)
                                        ))), utsArrayOf(
                                            createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                                                "status-text",
                                                _ctx.getStatusTextClass(product.status)
                                            ))), toDisplayString(_ctx.getStatusText(product.status)), 3)
                                        ), 2)
                                    )),
                                    createElementVNode("view", utsMapOf("class" to "info-middle"), utsArrayOf(
                                        createElementVNode("view", utsMapOf("class" to "price-row"), utsArrayOf(
                                            createElementVNode("text", utsMapOf("class" to "currency"), "¥"),
                                            createElementVNode("text", utsMapOf("class" to "price"), toDisplayString(product.currentPrice.toFixed(2)), 1)
                                        )),
                                        createElementVNode("view", utsMapOf("class" to "stock-row"), utsArrayOf(
                                            createElementVNode("text", utsMapOf("class" to "stock-label"), "库存:"),
                                            createElementVNode("text", utsMapOf("class" to "stock-value"), toDisplayString(product.stock), 1)
                                        ))
                                    )),
                                    createElementVNode("view", utsMapOf("class" to "info-bottom"), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to "time-text"), toDisplayString(product.createTime), 1),
                                        if (product.status === 0) {
                                            createElementVNode("view", utsMapOf("key" to 0, "class" to "action-btns"), utsArrayOf(
                                                createElementVNode("view", utsMapOf("class" to "action-btn", "onClick" to withModifiers(fun(){
                                                    _ctx.handleAudit(product, 1)
                                                }, utsArrayOf(
                                                    "stop"
                                                ))), utsArrayOf(
                                                    createElementVNode("text", utsMapOf("class" to "action-btn-text"), "通过")
                                                ), 8, utsArrayOf(
                                                    "onClick"
                                                )),
                                                createElementVNode("view", utsMapOf("class" to "action-btn action-btn-reject", "onClick" to withModifiers(fun(){
                                                    _ctx.handleAudit(product, 3)
                                                }, utsArrayOf(
                                                    "stop"
                                                ))), utsArrayOf(
                                                    createElementVNode("text", utsMapOf("class" to "action-btn-text action-btn-text-reject"), "驳回")
                                                ), 8, utsArrayOf(
                                                    "onClick"
                                                ))
                                            ))
                                        } else {
                                            createCommentVNode("v-if", true)
                                        }
                                    ))
                                ))
                            ), 8, utsArrayOf(
                                "onClick"
                            ))
                        }
                        ), 128)
                    ))
                }
                ,
                if (isTrue(!_ctx.isLoading && _ctx.products.length === 0)) {
                    createElementVNode("view", utsMapOf("key" to 2, "class" to "empty-state"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "empty-icon"), "📦"),
                        createElementVNode("text", utsMapOf("class" to "empty-text"), "暂无相关商品")
                    ))
                } else {
                    createCommentVNode("v-if", true)
                }
                ,
                if (_ctx.products.length > 0) {
                    createElementVNode("view", utsMapOf("key" to 3, "class" to "load-more"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "load-more-text"), toDisplayString(if (_ctx.hasMore) {
                            "上拉加载更多"
                        } else {
                            "没有更多了"
                        }), 1)
                    ))
                } else {
                    createCommentVNode("v-if", true)
                }
                ,
                createElementVNode("view", utsMapOf("style" to normalizeStyle(utsMapOf("height" to "20px"))), null, 4)
            ), 40, utsArrayOf(
                "onScrolltolower",
                "onRefresherrefresh",
                "refresher-triggered"
            ))
        ))
    }
    open var currentStatus: Number by `$data`
    open var isLoading: Boolean by `$data`
    open var isRefreshing: Boolean by `$data`
    open var hasMore: Boolean by `$data`
    open var pageNum: Number by `$data`
    open var pageSize: Number by `$data`
    open var products: UTSArray<AdminProductType> by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return utsMapOf("currentStatus" to 0 as Number, "isLoading" to false, "isRefreshing" to false, "hasMore" to true, "pageNum" to 1, "pageSize" to 10, "products" to utsArrayOf<AdminProductType>())
    }
    open var loadProducts = ::gen_loadProducts_fn
    open fun gen_loadProducts_fn() {
        this.isLoading = true
        getAdminProducts(this.pageNum, this.pageSize, this.currentStatus).then(fun(res){
            val data = res.data as UTSJSONObject
            val total = (data.getNumber("total") ?: 0).toInt()
            val records = data.getArray("records")
            val productList: UTSArray<AdminProductType> = utsArrayOf()
            if (records != null) {
                run {
                    var i: Number = 0
                    while(i < records.length){
                        val item = records[i] as UTSJSONObject
                        productList.push(AdminProductType(id = (item.getNumber("id") ?: 0).toInt(), productName = item.getString("productName") ?: "", productCode = item.getString("productCode") ?: "", mainImage = item.getString("mainImage") ?: "", currentPrice = item.getNumber("currentPrice") ?: 0, stock = (item.getNumber("stock") ?: 0).toInt(), salesVolume = (item.getNumber("salesVolume") ?: 0).toInt(), status = (item.getNumber("status") ?: 0).toInt(), createTime = item.getString("createTime") ?: ""))
                        i++
                    }
                }
            }
            if (this.pageNum === 1) {
                this.products = productList
            } else {
                this.products = this.products.concat(productList)
            }
            this.hasMore = this.products.length < total
            this.isLoading = false
            this.isRefreshing = false
        }
        ).`catch`(fun(err){
            console.error("获取商品列表失败:", err, " at pages/seller/product-audit.uvue:148")
            this.isLoading = false
            this.isRefreshing = false
            uni_showToast(ShowToastOptions(title = "加载失败", icon = "none"))
        }
        )
    }
    open var switchStatus = ::gen_switchStatus_fn
    open fun gen_switchStatus_fn(status: Number) {
        if (this.currentStatus === status) {
            return
        }
        this.currentStatus = status
        this.pageNum = 1
        this.products = utsArrayOf()
        this.loadProducts()
    }
    open var onRefresh = ::gen_onRefresh_fn
    open fun gen_onRefresh_fn() {
        this.isRefreshing = true
        this.pageNum = 1
        this.loadProducts()
    }
    open var loadMore = ::gen_loadMore_fn
    open fun gen_loadMore_fn() {
        if (!this.hasMore || this.isLoading) {
            return
        }
        this.pageNum++
        this.loadProducts()
    }
    open var getStatusClass = ::gen_getStatusClass_fn
    open fun gen_getStatusClass_fn(status: Number): String {
        if (status === 0) {
            return "status-tag-pending"
        }
        if (status === 1) {
            return "status-tag-passed"
        }
        if (status === 3) {
            return "status-tag-rejected"
        }
        return ""
    }
    open var getStatusTextClass = ::gen_getStatusTextClass_fn
    open fun gen_getStatusTextClass_fn(status: Number): String {
        if (status === 0) {
            return "status-text-pending"
        }
        if (status === 1) {
            return "status-text-passed"
        }
        if (status === 3) {
            return "status-text-rejected"
        }
        return ""
    }
    open var getStatusText = ::gen_getStatusText_fn
    open fun gen_getStatusText_fn(status: Number): String {
        if (status === 0) {
            return "待审核"
        }
        if (status === 1) {
            return "已通过"
        }
        if (status === 3) {
            return "已驳回"
        }
        return "未知"
    }
    open var goToDetail = ::gen_goToDetail_fn
    open fun gen_goToDetail_fn(productId: Number) {
        uni_navigateTo(NavigateToOptions(url = "/pages/product/detail?id=" + productId))
    }
    open var handleAudit = ::gen_handleAudit_fn
    open fun gen_handleAudit_fn(product: AdminProductType, status: Number) {
        if (status === 3) {
            uni_showModal(ShowModalOptions(title = "驳回审核", editable = true, placeholderText = "请输入驳回原因", success = fun(res){
                if (res.confirm) {
                    this.submitAudit(product.id, status, res.content ?: "不符合上架标准")
                }
            }))
        } else {
            uni_showModal(ShowModalOptions(title = "提示", content = "\u786E\u5B9A\u8981\u901A\u8FC7\u8BE5\u5546\u54C1\u7684\u4E0A\u67B6\u7533\u8BF7\u5417\uFF1F", success = fun(res){
                if (res.confirm) {
                    this.submitAudit(product.id, status, "符合上架标准")
                }
            }
            ))
        }
    }
    open var submitAudit = ::gen_submitAudit_fn
    open fun gen_submitAudit_fn(productId: Number, status: Number, remark: String) {
        auditProduct(productId, status, remark).then(fun(){
            uni_showToast(ShowToastOptions(title = "操作成功", icon = "success"))
            this.pageNum = 1
            this.loadProducts()
        }
        ).`catch`(fun(err){
            console.error("审核失败:", err, " at pages/seller/product-audit.uvue:224")
            uni_showToast(ShowToastOptions(title = "操作失败", icon = "none"))
        }
        )
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
                return utsMapOf("page" to padStyleMapOf(utsMapOf("backgroundColor" to "#f7f8fa", "display" to "flex", "flexDirection" to "column", "flex" to 1)), "filter-header" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "boxShadow" to "0 1px 4px rgba(0, 0, 0, 0.05)", "zIndex" to 100)), "status-tabs" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "paddingTop" to 0, "paddingRight" to 8, "paddingBottom" to 0, "paddingLeft" to 8, "justifyContent" to "space-around")), "tab-item" to padStyleMapOf(utsMapOf("position" to "relative", "paddingTop" to 12, "paddingRight" to 16, "paddingBottom" to 12, "paddingLeft" to 16, "display" to "flex", "flexDirection" to "column", "alignItems" to "center")), "tab-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#666666")), "tab-text--active" to padStyleMapOf(utsMapOf("color" to "#0066CC", "fontWeight" to "700")), "active-line" to padStyleMapOf(utsMapOf("position" to "absolute", "bottom" to 4, "width" to 20, "height" to 3, "backgroundColor" to "#0066CC", "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "product-scroll" to padStyleMapOf(utsMapOf("flex" to 1, "width" to "100%")), "loading-state" to padStyleMapOf(utsMapOf("paddingTop" to 60, "paddingRight" to 0, "paddingBottom" to 60, "paddingLeft" to 0, "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "loading-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#999999")), "product-list" to padStyleMapOf(utsMapOf("paddingTop" to 12, "paddingRight" to 16, "paddingBottom" to 12, "paddingLeft" to 16)), "product-card" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "backgroundColor" to "#ffffff", "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "marginBottom" to 12, "boxShadow" to "0 2px 8px rgba(0, 0, 0, 0.02)")), "product-img" to padStyleMapOf(utsMapOf("width" to 90, "height" to 90, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#f5f5f5", "flexShrink" to 0)), "product-info" to padStyleMapOf(utsMapOf("flex" to 1, "marginLeft" to 12, "display" to "flex", "flexDirection" to "column", "justifyContent" to "space-between")), "info-top" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between")), "product-name" to padStyleMapOf(utsMapOf("flex" to 1, "fontSize" to 14, "fontWeight" to "700", "color" to "#333333", "lineHeight" to 1.4, "marginRight" to 8)), "status-tag" to padStyleMapOf(utsMapOf("paddingTop" to 2, "paddingRight" to 8, "paddingBottom" to 2, "paddingLeft" to 8, "borderTopLeftRadius" to 4, "borderTopRightRadius" to 4, "borderBottomRightRadius" to 4, "borderBottomLeftRadius" to 4)), "status-tag-pending" to padStyleMapOf(utsMapOf("backgroundColor" to "#fff7e6")), "status-tag-passed" to padStyleMapOf(utsMapOf("backgroundColor" to "#e6f7ff")), "status-tag-rejected" to padStyleMapOf(utsMapOf("backgroundColor" to "#fff1f0")), "status-text" to padStyleMapOf(utsMapOf("fontSize" to 11)), "status-text-pending" to padStyleMapOf(utsMapOf("color" to "#fa8c16")), "status-text-passed" to padStyleMapOf(utsMapOf("color" to "#1890ff")), "status-text-rejected" to padStyleMapOf(utsMapOf("color" to "#ff4d4f")), "info-middle" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "marginTop" to 8, "marginRight" to 0, "marginBottom" to 8, "marginLeft" to 0)), "price-row" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "flex-end")), "currency" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#ff4d4f", "fontWeight" to "700")), "price" to padStyleMapOf(utsMapOf("fontSize" to 16, "color" to "#ff4d4f", "fontWeight" to "700")), "stock-row" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center")), "stock-label" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999")), "stock-value" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#333333", "marginLeft" to 4)), "info-bottom" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center")), "time-text" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999")), "action-btns" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row")), "action-btn" to padStyleMapOf(utsMapOf("paddingTop" to 4, "paddingRight" to 12, "paddingBottom" to 4, "paddingLeft" to 12, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#0066CC", "borderRightColor" to "#0066CC", "borderBottomColor" to "#0066CC", "borderLeftColor" to "#0066CC", "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "marginLeft" to 8)), "action-btn-reject" to padStyleMapOf(utsMapOf("borderTopColor" to "#ff4d4f", "borderRightColor" to "#ff4d4f", "borderBottomColor" to "#ff4d4f", "borderLeftColor" to "#ff4d4f")), "action-btn-text" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#0066CC")), "action-btn-text-reject" to padStyleMapOf(utsMapOf("color" to "#ff4d4f")), "empty-state" to padStyleMapOf(utsMapOf("paddingTop" to 80, "paddingRight" to 0, "paddingBottom" to 80, "paddingLeft" to 0, "display" to "flex", "flexDirection" to "column", "alignItems" to "center")), "empty-icon" to padStyleMapOf(utsMapOf("fontSize" to 48, "marginBottom" to 16)), "empty-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#999999")), "load-more" to padStyleMapOf(utsMapOf("paddingTop" to 20, "paddingRight" to 0, "paddingBottom" to 20, "paddingLeft" to 0, "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "load-more-text" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = utsMapOf()
        var emits: Map<String, Any?> = utsMapOf()
        var props = normalizePropsOptions(utsMapOf())
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf()
        var components: Map<String, CreateVueComponent> = utsMapOf()
    }
}
