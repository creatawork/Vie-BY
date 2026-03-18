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
import io.dcloud.uniapp.extapi.switchTab as uni_switchTab
open class GenPagesProductHistory : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onLoad(fun(_: OnLoadOptions) {
            this.loadHistoryList()
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return createElementVNode("view", utsMapOf("class" to "history-page"), utsArrayOf(
            if (_ctx.historyList.length > 0) {
                createElementVNode("view", utsMapOf("key" to 0, "class" to "action-bar"), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "count-text"), "共 " + toDisplayString(_ctx.total) + " 条记录", 1),
                    createElementVNode("text", utsMapOf("class" to "clear-btn", "onClick" to _ctx.clearHistory), "清空", 8, utsArrayOf(
                        "onClick"
                    ))
                ))
            } else {
                createCommentVNode("v-if", true)
            }
            ,
            createElementVNode("scroll-view", utsMapOf("class" to "history-scroll", "scroll-y" to "true", "show-scrollbar" to false, "onScrolltolower" to _ctx.loadMore), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "history-list"), utsArrayOf(
                    createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.historyList, fun(item, __key, __index, _cached): Any {
                        return createElementVNode("view", utsMapOf("class" to "history-item", "key" to item.id, "onClick" to fun(){
                            _ctx.goToDetail(item.productId)
                        }
                        ), utsArrayOf(
                            createElementVNode("image", utsMapOf("class" to "goods-img", "src" to item.mainImage, "mode" to "aspectFill"), null, 8, utsArrayOf(
                                "src"
                            )),
                            createElementVNode("view", utsMapOf("class" to "goods-info"), utsArrayOf(
                                createElementVNode("view", utsMapOf("class" to "info-top"), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "goods-name"), toDisplayString(item.productName), 1),
                                    createElementVNode("text", utsMapOf("class" to "view-time"), toDisplayString(_ctx.formatTime(item.browseTime)), 1)
                                )),
                                createElementVNode("text", utsMapOf("class" to "goods-desc"), toDisplayString(_ctx.getStockText(item)), 1),
                                createElementVNode("view", utsMapOf("class" to "goods-bottom"), utsArrayOf(
                                    createElementVNode("view", utsMapOf("class" to "price-box"), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to "currency"), "¥"),
                                        createElementVNode("text", utsMapOf("class" to "price"), toDisplayString(item.currentPrice), 1),
                                        if (isTrue(_ctx.shouldShowOriginalPrice(item))) {
                                            createElementVNode("text", utsMapOf("key" to 0, "class" to "original-price"), "¥" + toDisplayString(item.originalPrice), 1)
                                        } else {
                                            createCommentVNode("v-if", true)
                                        }
                                    )),
                                    createElementVNode("view", utsMapOf("class" to "action-icon", "onClick" to withModifiers(fun(){
                                        _ctx.deleteItem(item.id)
                                    }
                                    , utsArrayOf(
                                        "stop"
                                    ))), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to "delete-text"), "✕")
                                    ), 8, utsArrayOf(
                                        "onClick"
                                    ))
                                ))
                            ))
                        ), 8, utsArrayOf(
                            "onClick"
                        ))
                    }
                    ), 128)
                )),
                if (isTrue(_ctx.loading)) {
                    createElementVNode("view", utsMapOf("key" to 0, "class" to "loading-more"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "loading-text"), "加载中...")
                    ))
                } else {
                    createCommentVNode("v-if", true)
                }
                ,
                if (isTrue(!_ctx.loading && _ctx.hasMore === false && _ctx.historyList.length > 0)) {
                    createElementVNode("view", utsMapOf("key" to 1, "class" to "no-more"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "no-more-text"), "没有更多了")
                    ))
                } else {
                    createCommentVNode("v-if", true)
                }
                ,
                if (isTrue(!_ctx.loading && _ctx.historyList.length === 0)) {
                    createElementVNode("view", utsMapOf("key" to 2, "class" to "empty-state"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "empty-icon"), "🕒"),
                        createElementVNode("text", utsMapOf("class" to "empty-text"), "暂无浏览记录"),
                        createElementVNode("button", utsMapOf("class" to "go-shopping-btn", "onClick" to _ctx.goShopping), "去逛逛", 8, utsArrayOf(
                            "onClick"
                        ))
                    ))
                } else {
                    createCommentVNode("v-if", true)
                }
                ,
                createElementVNode("view", utsMapOf("style" to normalizeStyle(utsMapOf("height" to "20px"))), null, 4)
            ), 40, utsArrayOf(
                "onScrolltolower"
            ))
        ))
    }
    open var historyList: UTSArray<HistoryItem> by `$data`
    open var loading: Boolean by `$data`
    open var pageNum: Number by `$data`
    open var pageSize: Number by `$data`
    open var total: Number by `$data`
    open var hasMore: Boolean by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return utsMapOf("historyList" to utsArrayOf<HistoryItem>(), "loading" to false, "pageNum" to 1, "pageSize" to 10, "total" to 0, "hasMore" to true)
    }
    open var shouldShowOriginalPrice = ::gen_shouldShowOriginalPrice_fn
    open fun gen_shouldShowOriginalPrice_fn(item: HistoryItem): Boolean {
        return item.originalPrice > 0 && item.originalPrice > item.currentPrice
    }
    open var loadHistoryList = ::gen_loadHistoryList_fn
    open fun gen_loadHistoryList_fn() {
        if (this.loading || !this.hasMore) {
            return
        }
        this.loading = true
        getBrowseHistory(this.pageNum, this.pageSize).then(fun(res){
            if (res.code == 200 && res.data != null) {
                val resData = res.data as UTSJSONObject
                val recordsArr = resData.getArray("records")
                val total = (resData.getNumber("total") ?: 0).toInt()
                val current = (resData.getNumber("current") ?: 1).toInt()
                val pages = (resData.getNumber("pages") ?: 1).toInt()
                val records: UTSArray<HistoryItem> = utsArrayOf()
                if (recordsArr != null) {
                    run {
                        var i: Number = 0
                        while(i < recordsArr.length){
                            val item = recordsArr[i] as UTSJSONObject
                            val historyId = (item.getNumber("historyId") ?: 0).toInt()
                            records.push(HistoryItem(id = historyId, productId = (item.getNumber("productId") ?: 0).toInt(), productName = item.getString("productName") ?: "", mainImage = item.getString("productImage") ?: "", currentPrice = item.getNumber("currentPrice") ?: 0, originalPrice = item.getNumber("originalPrice") ?: 0, stock = (item.getNumber("stock") ?: 0).toInt(), salesVolume = (item.getNumber("salesVolume") ?: 0).toInt(), status = (item.getNumber("productStatus") ?: 1).toInt(), browseTime = item.getString("browseTime") ?: "", inStock = (item.getBoolean("inStock") ?: true)))
                            i++
                        }
                    }
                }
                this.historyList = this.historyList.concat(records)
                this.total = total
                this.pageNum = current
                this.hasMore = this.pageNum < pages
            } else {
                val msg = if (res.message != "") {
                    res.message
                } else {
                    "加载失败"
                }
                uni_showToast(ShowToastOptions(title = msg, icon = "none"))
            }
            this.loading = false
        }
        ).`catch`(fun(error){
            console.error("加载浏览历史失败:", error, " at pages/product/history.uvue:153")
            uni_showToast(ShowToastOptions(title = "加载失败，请重试", icon = "none"))
            this.loading = false
        }
        )
    }
    open var loadMore = ::gen_loadMore_fn
    open fun gen_loadMore_fn() {
        if (!this.hasMore || this.loading) {
            return
        }
        this.pageNum += 1
        this.loadHistoryList()
    }
    open var goToDetail = ::gen_goToDetail_fn
    open fun gen_goToDetail_fn(productId: Number) {
        uni_navigateTo(NavigateToOptions(url = "/pages/product/detail?id=" + productId))
    }
    open var deleteItem = ::gen_deleteItem_fn
    open fun gen_deleteItem_fn(historyId: Number) {
        console.log("准备删除浏览记录，ID:", historyId, " at pages/product/history.uvue:177")
        deleteBrowseHistory(historyId).then(fun(res){
            console.log("删除API响应:", JSON.stringify(res), " at pages/product/history.uvue:180")
            if (res.code == 200) {
                this.historyList = this.historyList.filter(fun(item: HistoryItem): Boolean {
                    return item.id !== historyId
                })
                this.total -= 1
                uni_showToast(ShowToastOptions(title = "已删除", icon = "success"))
            } else {
                val msg = if (res.message != "") {
                    res.message
                } else {
                    "删除失败"
                }
                console.error("删除失败，响应:", msg, " at pages/product/history.uvue:190")
                uni_showToast(ShowToastOptions(title = msg, icon = "none"))
            }
        }
        ).`catch`(fun(error){
            console.error("删除浏览记录异常:", error, " at pages/product/history.uvue:197")
            uni_showToast(ShowToastOptions(title = "删除失败，请重试", icon = "none"))
        }
        )
    }
    open var clearHistory = ::gen_clearHistory_fn
    open fun gen_clearHistory_fn() {
        uni_showModal(ShowModalOptions(title = "提示", content = "确定清空所有浏览记录吗？", success = fun(modalRes){
            if (modalRes.confirm) {
                clearBrowseHistory().then(fun(res){
                    if (res.code == 200) {
                        this.historyList = utsArrayOf()
                        this.total = 0
                        this.pageNum = 1
                        this.hasMore = false
                        uni_showToast(ShowToastOptions(title = "已清空", icon = "none"))
                    } else {
                        val msg = if (res.message != "") {
                            res.message
                        } else {
                            "清空失败"
                        }
                        uni_showToast(ShowToastOptions(title = msg, icon = "none"))
                    }
                }
                ).`catch`(fun(error){
                    console.error("清空浏览历史失败:", error, " at pages/product/history.uvue:228")
                    uni_showToast(ShowToastOptions(title = "清空失败，请重试", icon = "none"))
                }
                )
            }
        }
        ))
    }
    open var goShopping = ::gen_goShopping_fn
    open fun gen_goShopping_fn() {
        uni_switchTab(SwitchTabOptions(url = "/pages/product/list"))
    }
    open var formatTime = ::gen_formatTime_fn
    open fun gen_formatTime_fn(timeStr: String): String {
        if (timeStr == "") {
            return ""
        }
        try {
            val time = Date(timeStr.replace(UTSRegExp("-", "g"), "/"))
            val now = Date()
            val diff = now.getTime() - time.getTime()
            val minutes = Math.floor(diff / 60000)
            val hours = Math.floor(diff / 3600000)
            val days = Math.floor(diff / 86400000)
            if (minutes < 1) {
                return "刚刚"
            } else if (minutes < 60) {
                return "" + minutes + "\u5206\u949F\u524D"
            } else if (hours < 24) {
                return "" + hours + "\u5C0F\u65F6\u524D"
            } else if (days === 1) {
                return "昨天"
            } else if (days === 2) {
                return "前天"
            } else if (days < 7) {
                return "" + days + "\u5929\u524D"
            } else {
                val month = time.getMonth() + 1
                val date = time.getDate()
                return "" + month + "\u6708" + date + "\u65E5"
            }
        }
         catch (error: Throwable) {
            return timeStr
        }
    }
    open var getStockText = ::gen_getStockText_fn
    open fun gen_getStockText_fn(item: HistoryItem): String {
        if (item.status !== 1) {
            return "商品已下架"
        }
        if (!item.inStock) {
            return "暂时缺货"
        }
        return "\u9500\u91CF " + item.salesVolume
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
                return utsMapOf("history-page" to padStyleMapOf(utsMapOf("backgroundColor" to "#F5F7FA", "display" to "flex", "flexDirection" to "column")), "action-bar" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "paddingTop" to 12, "paddingRight" to 16, "paddingBottom" to 12, "paddingLeft" to 16, "backgroundColor" to "#ffffff", "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "#F5F5F5")), "count-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#999999")), "clear-btn" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#333333", "paddingTop" to 4, "paddingRight" to 8, "paddingBottom" to 4, "paddingLeft" to 8)), "history-scroll" to padStyleMapOf(utsMapOf("flex" to 1, "width" to "100%")), "history-list" to padStyleMapOf(utsMapOf("paddingTop" to 12, "paddingRight" to 16, "paddingBottom" to 12, "paddingLeft" to 16, "display" to "flex", "flexDirection" to "column")), "history-item" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "marginBottom" to 12, "display" to "flex", "flexDirection" to "row", "boxShadow" to "0 1px 4px rgba(0, 0, 0, 0.02)")), "goods-img" to padStyleMapOf(utsMapOf("width" to "180rpx", "height" to "180rpx", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#F5F7FA", "marginRight" to "20rpx")), "goods-info" to padStyleMapOf(utsMapOf("flex" to 1, "display" to "flex", "flexDirection" to "column", "justifyContent" to "space-between", "paddingTop" to 2, "paddingRight" to 0, "paddingBottom" to 2, "paddingLeft" to 0)), "info-top" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "flex-start")), "goods-name" to padStyleMapOf(utsMapOf("flex" to 1, "fontSize" to 15, "fontWeight" to "700", "color" to "#333333", "marginBottom" to 4, "lineHeight" to 1.4, "height" to 42, "overflow" to "hidden", "marginRight" to 8)), "view-time" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999", "whiteSpace" to "nowrap")), "goods-desc" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999", "marginBottom" to "auto", "lineHeight" to 1.4, "height" to 17, "overflow" to "hidden")), "goods-bottom" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center")), "price-box" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "color" to "#FF4D4F")), "currency" to padStyleMapOf(utsMapOf("fontSize" to 12, "fontWeight" to "bold")), "price" to padStyleMapOf(utsMapOf("fontSize" to 16, "fontWeight" to "bold", "marginRight" to 4)), "original-price" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999", "opacity" to 0.8)), "action-icon" to padStyleMapOf(utsMapOf("width" to 24, "height" to 24, "display" to "flex", "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "#F5F7FA", "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12)), "delete-text" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999")), "loading-more" to padStyleMapOf(utsMapOf("paddingTop" to 20, "paddingRight" to 0, "paddingBottom" to 20, "paddingLeft" to 0, "display" to "flex", "justifyContent" to "center", "alignItems" to "center")), "loading-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#999999")), "no-more" to padStyleMapOf(utsMapOf("paddingTop" to 20, "paddingRight" to 0, "paddingBottom" to 20, "paddingLeft" to 0, "display" to "flex", "justifyContent" to "center", "alignItems" to "center")), "no-more-text" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#C0C0C0")), "empty-state" to padStyleMapOf(utsMapOf("paddingTop" to 80, "paddingRight" to 0, "paddingBottom" to 80, "paddingLeft" to 0, "display" to "flex", "flexDirection" to "column", "alignItems" to "center", "justifyContent" to "center")), "empty-icon" to padStyleMapOf(utsMapOf("fontSize" to 48, "marginBottom" to 16, "color" to "#C0C0C0")), "empty-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#999999", "marginBottom" to 24)), "go-shopping-btn" to padStyleMapOf(utsMapOf("backgroundColor" to "#0066CC", "color" to "#ffffff", "fontSize" to 14, "paddingTop" to 0, "paddingRight" to 32, "paddingBottom" to 0, "paddingLeft" to 32, "height" to 40, "lineHeight" to "40px", "borderTopLeftRadius" to 20, "borderTopRightRadius" to 20, "borderBottomRightRadius" to 20, "borderBottomLeftRadius" to 20)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = utsMapOf()
        var emits: Map<String, Any?> = utsMapOf()
        var props = normalizePropsOptions(utsMapOf())
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf()
        var components: Map<String, CreateVueComponent> = utsMapOf()
    }
}
