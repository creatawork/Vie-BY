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
import io.dcloud.uniapp.extapi.`$emit` as uni__emit
import io.dcloud.uniapp.extapi.hideLoading as uni_hideLoading
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showModal as uni_showModal
import io.dcloud.uniapp.extapi.showToast as uni_showToast
import io.dcloud.uniapp.extapi.switchTab as uni_switchTab
open class GenPagesProductFavorites : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onLoad(fun(_: OnLoadOptions) {
            this.loadFavorites()
        }
        , __ins)
        onPageShow(fun() {
            this.pageNum = 1
            this.hasMore = true
            this.loadFavorites()
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return createElementVNode("view", utsMapOf("class" to "favorites-page"), utsArrayOf(
            createElementVNode("scroll-view", utsMapOf("class" to "favorites-scroll", "scroll-y" to "true", "show-scrollbar" to false), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "favorites-list"), utsArrayOf(
                    createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.favorites, fun(item, __key, __index, _cached): Any {
                        return createElementVNode("view", utsMapOf("class" to "favorite-card", "key" to item.collectionId, "onClick" to fun(){
                            _ctx.goToDetail(item.productId)
                        }
                        ), utsArrayOf(
                            createElementVNode("image", utsMapOf("class" to "goods-img", "src" to item.mainImage, "mode" to "aspectFill"), null, 8, utsArrayOf(
                                "src"
                            )),
                            createElementVNode("view", utsMapOf("class" to "goods-info"), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "goods-name"), toDisplayString(item.productName), 1),
                                createElementVNode("text", utsMapOf("class" to "goods-desc"), toDisplayString(if (item.inStock) {
                                    "有货"
                                } else {
                                    "暂时缺货"
                                }
                                ) + " · 已售" + toDisplayString(item.salesVolume) + "件", 1),
                                createElementVNode("view", utsMapOf("class" to "goods-bottom"), utsArrayOf(
                                    createElementVNode("view", utsMapOf("class" to "price-box"), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to "currency"), "¥"),
                                        createElementVNode("text", utsMapOf("class" to "price"), toDisplayString(item.currentPrice.toFixed(2)), 1),
                                        if (item.originalPrice > item.currentPrice) {
                                            createElementVNode("text", utsMapOf("key" to 0, "class" to "original-price"), "¥" + toDisplayString(item.originalPrice.toFixed(2)), 1)
                                        } else {
                                            createCommentVNode("v-if", true)
                                        }
                                    )),
                                    createElementVNode("view", utsMapOf("class" to "action-btn", "onClick" to withModifiers(fun(){
                                        _ctx.removeFavorite(item.productId)
                                    }
                                    , utsArrayOf(
                                        "stop"
                                    ))), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to "btn-text"), "取消收藏")
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
                if (_ctx.favorites.length === 0) {
                    createElementVNode("view", utsMapOf("key" to 0, "class" to "empty-state"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "empty-icon"), "❤️"),
                        createElementVNode("text", utsMapOf("class" to "empty-text"), "暂无收藏商品"),
                        createElementVNode("button", utsMapOf("class" to "go-shopping-btn", "onClick" to _ctx.goShopping), "去逛逛", 8, utsArrayOf(
                            "onClick"
                        ))
                    ))
                } else {
                    createCommentVNode("v-if", true)
                }
            ))
        ))
    }
    open var favorites: UTSArray<CollectionItem> by `$data`
    open var loading: Boolean by `$data`
    open var pageNum: Number by `$data`
    open var pageSize: Number by `$data`
    open var hasMore: Boolean by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return utsMapOf("favorites" to utsArrayOf<CollectionItem>(), "loading" to false, "pageNum" to 1, "pageSize" to 20, "hasMore" to true)
    }
    open var loadFavorites = ::gen_loadFavorites_fn
    open fun gen_loadFavorites_fn() {
        if (this.loading) {
            return
        }
        this.loading = true
        uni_showLoading(ShowLoadingOptions(title = "加载中..."))
        getCollectionList(this.pageNum, this.pageSize, "time", "desc").then(fun(result){
            uni_hideLoading()
            this.loading = false
            if (result.code === 200) {
                val data = result.data as UTSJSONObject
                if (data != null) {
                    val records = data.getArray("records")
                    if (records != null && records.length > 0) {
                        val items: UTSArray<CollectionItem> = utsArrayOf()
                        run {
                            var i: Number = 0
                            while(i < records.length){
                                val item = records[i] as UTSJSONObject
                                if (item != null) {
                                    items.push(CollectionItem(collectionId = (item.getNumber("collectionId") ?: 0).toInt(), productId = (item.getNumber("productId") ?: 0).toInt(), productName = item.getString("productName") ?: "", mainImage = item.getString("mainImage") ?: "", currentPrice = (item.getNumber("currentPrice") ?: 0), originalPrice = (item.getNumber("originalPrice") ?: 0), stock = (item.getNumber("stock") ?: 0).toInt(), salesVolume = (item.getNumber("salesVolume") ?: 0).toInt(), status = (item.getNumber("status") ?: 1).toInt(), collectTime = item.getString("collectTime") ?: "", inStock = item.getBoolean("inStock") ?: true))
                                }
                                i++
                            }
                        }
                        if (this.pageNum === 1) {
                            this.favorites = items
                        } else {
                            this.favorites = this.favorites.concat(items)
                        }
                        val total = (data.getNumber("total") ?: 0).toInt()
                        this.hasMore = this.favorites.length < total
                    } else {
                        if (this.pageNum === 1) {
                            this.favorites = utsArrayOf()
                        }
                        this.hasMore = false
                    }
                }
            }
        }
        ).`catch`(fun(error){
            uni_hideLoading()
            this.loading = false
            console.error("获取收藏列表失败:", error, " at pages/product/favorites.uvue:132")
            uni_showToast(ShowToastOptions(title = "加载失败", icon = "none"))
        }
        )
    }
    open var goToDetail = ::gen_goToDetail_fn
    open fun gen_goToDetail_fn(productId: Number) {
        uni_navigateTo(NavigateToOptions(url = "/pages/product/detail?id=" + productId))
    }
    open var removeFavorite = ::gen_removeFavorite_fn
    open fun gen_removeFavorite_fn(productId: Number) {
        uni_showModal(ShowModalOptions(title = "提示", content = "确定取消收藏吗？", success = fun(res){
            if (res.confirm) {
                uncollectProduct(productId).then(fun(result){
                    if (result.code === 200) {
                        this.favorites = this.favorites.filter(fun(item: CollectionItem): Boolean {
                            return item.productId !== productId
                        })
                        uni_showToast(ShowToastOptions(title = "已取消收藏", icon = "none"))
                        uni__emit("refreshFavorites", null)
                    } else {
                        uni_showToast(ShowToastOptions(title = result.message ?: "操作失败", icon = "none"))
                    }
                }
                ).`catch`(fun(error){
                    console.error("取消收藏失败:", error, " at pages/product/favorites.uvue:156")
                    uni_showToast(ShowToastOptions(title = "操作失败", icon = "none"))
                }
                )
            }
        }
        ))
    }
    open var goShopping = ::gen_goShopping_fn
    open fun gen_goShopping_fn() {
        uni_switchTab(SwitchTabOptions(url = "/pages/index/index"))
    }
    open var clearAll = ::gen_clearAll_fn
    open fun gen_clearAll_fn() {
        uni_showModal(ShowModalOptions(title = "提示", content = "确定清空所有收藏吗？此操作不可撤销。", success = fun(res){
            if (res.confirm) {
                clearCollections().then(fun(result){
                    if (result.code === 200) {
                        this.favorites = utsArrayOf()
                        uni_showToast(ShowToastOptions(title = "已清空收藏夹", icon = "success"))
                        uni__emit("refreshFavorites", null)
                    } else {
                        uni_showToast(ShowToastOptions(title = result.message ?: "操作失败", icon = "none"))
                    }
                }
                ).`catch`(fun(error){
                    console.error("清空收藏夹失败:", error, " at pages/product/favorites.uvue:182")
                    uni_showToast(ShowToastOptions(title = "操作失败", icon = "none"))
                }
                )
            }
        }
        ))
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
                return utsMapOf("favorites-page" to padStyleMapOf(utsMapOf("backgroundColor" to "#F5F7FA", "display" to "flex", "flexDirection" to "column")), "favorites-scroll" to padStyleMapOf(utsMapOf("flex" to 1, "width" to "100%")), "favorites-list" to padStyleMapOf(utsMapOf("paddingTop" to 12, "paddingRight" to 16, "paddingBottom" to 12, "paddingLeft" to 16, "display" to "flex", "flexDirection" to "column")), "favorite-card" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "marginBottom" to 12, "display" to "flex", "flexDirection" to "row", "boxShadow" to "0 2px 8px rgba(0, 0, 0, 0.02)")), "goods-img" to padStyleMapOf(utsMapOf("width" to "200rpx", "height" to "200rpx", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#F5F7FA", "marginRight" to "20rpx")), "goods-info" to padStyleMapOf(utsMapOf("flex" to 1, "display" to "flex", "flexDirection" to "column", "justifyContent" to "space-between", "paddingTop" to 4, "paddingRight" to 0, "paddingBottom" to 4, "paddingLeft" to 0)), "goods-name" to padStyleMapOf(utsMapOf("fontSize" to 15, "fontWeight" to "700", "color" to "#333333", "marginBottom" to 6, "lineHeight" to 1.4, "height" to 42, "overflow" to "hidden")), "goods-desc" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999", "marginBottom" to "auto", "lineHeight" to 1.4, "height" to 34, "overflow" to "hidden")), "goods-bottom" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center")), "price-box" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "color" to "#FF4D4F")), "currency" to padStyleMapOf(utsMapOf("fontSize" to 12, "fontWeight" to "bold")), "price" to padStyleMapOf(utsMapOf("fontSize" to 18, "fontWeight" to "bold")), "original-price" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999", "marginLeft" to 8)), "action-btn" to padStyleMapOf(utsMapOf("paddingTop" to 4, "paddingRight" to 12, "paddingBottom" to 4, "paddingLeft" to 12, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#EEEEEE", "borderRightColor" to "#EEEEEE", "borderBottomColor" to "#EEEEEE", "borderLeftColor" to "#EEEEEE", "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "btn-text" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999")), "empty-state" to padStyleMapOf(utsMapOf("paddingTop" to 80, "paddingRight" to 0, "paddingBottom" to 80, "paddingLeft" to 0, "display" to "flex", "flexDirection" to "column", "alignItems" to "center", "justifyContent" to "center")), "empty-icon" to padStyleMapOf(utsMapOf("fontSize" to 48, "marginBottom" to 16, "color" to "#C0C0C0")), "empty-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#999999", "marginBottom" to 24)), "go-shopping-btn" to padStyleMapOf(utsMapOf("backgroundColor" to "#0066CC", "color" to "#ffffff", "fontSize" to 14, "paddingTop" to 0, "paddingRight" to 32, "paddingBottom" to 0, "paddingLeft" to 32, "height" to 40, "lineHeight" to "40px", "borderTopLeftRadius" to 20, "borderTopRightRadius" to 20, "borderBottomRightRadius" to 20, "borderBottomLeftRadius" to 20)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = utsMapOf()
        var emits: Map<String, Any?> = utsMapOf()
        var props = normalizePropsOptions(utsMapOf())
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf()
        var components: Map<String, CreateVueComponent> = utsMapOf()
    }
}
