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
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.previewImage as uni_previewImage
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesUserReviews : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onLoad(fun(_: OnLoadOptions) {
            this.loadReviews()
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return createElementVNode("view", utsMapOf("class" to "page-container"), utsArrayOf(
            createElementVNode("view", utsMapOf("class" to "nav-bar"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "nav-back", "onClick" to _ctx.goBack), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "back-icon"), "‹")
                ), 8, utsArrayOf(
                    "onClick"
                )),
                createElementVNode("text", utsMapOf("class" to "nav-title"), "我的评价"),
                createElementVNode("view", utsMapOf("class" to "nav-placeholder"))
            )),
            createElementVNode("scroll-view", utsMapOf("class" to "review-list", "scroll-y" to "true", "onScrolltolower" to _ctx.loadMore), utsArrayOf(
                if (isTrue(_ctx.reviews.length === 0 && !_ctx.isLoading)) {
                    createElementVNode("view", utsMapOf("key" to 0, "class" to "empty-state"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "empty-icon"), "📝"),
                        createElementVNode("text", utsMapOf("class" to "empty-text"), "暂无评价记录")
                    ))
                } else {
                    createCommentVNode("v-if", true)
                }
                ,
                createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.reviews, fun(item, index, __index, _cached): Any {
                    return createElementVNode("view", utsMapOf("class" to "review-card", "key" to index), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "product-info", "onClick" to fun(){
                            _ctx.goToProduct(item.productId)
                        }
                        ), utsArrayOf(
                            createElementVNode("image", utsMapOf("class" to "product-img", "src" to item.productMainImage, "mode" to "aspectFill"), null, 8, utsArrayOf(
                                "src"
                            )),
                            createElementVNode("view", utsMapOf("class" to "product-detail"), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "product-name"), toDisplayString(item.productName), 1),
                                createElementVNode("view", utsMapOf("class" to "rating-stars"), utsArrayOf(
                                    createElementVNode(Fragment, null, RenderHelpers.renderList(5, fun(i, __key, __index, _cached): Any {
                                        return createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                                            "star-icon",
                                            utsMapOf("star-icon-active" to (i <= item.rating))
                                        )), "key" to i), "★", 2)
                                    }
                                    ), 64)
                                ))
                            ))
                        ), 8, utsArrayOf(
                            "onClick"
                        )),
                        createElementVNode("view", utsMapOf("class" to "review-content"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "content-text"), toDisplayString(item.content), 1),
                            if (isTrue(item.images)) {
                                createElementVNode("view", utsMapOf("key" to 0, "class" to "image-list"), utsArrayOf(
                                    createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.getImages(item.images), fun(img, imgIdx, __index, _cached): Any {
                                        return createElementVNode("image", utsMapOf("class" to "review-img", "key" to imgIdx, "src" to img, "mode" to "aspectFill", "onClick" to fun(){
                                            _ctx.previewImage(_ctx.getImages(item.images), imgIdx)
                                        }), null, 8, utsArrayOf(
                                            "src",
                                            "onClick"
                                        ))
                                    }), 128)
                                ))
                            } else {
                                createCommentVNode("v-if", true)
                            }
                        )),
                        if (isTrue(item.replyContent)) {
                            createElementVNode("view", utsMapOf("key" to 0, "class" to "reply-box"), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "reply-label"), "商家回复："),
                                createElementVNode("text", utsMapOf("class" to "reply-content"), toDisplayString(item.replyContent), 1)
                            ))
                        } else {
                            createCommentVNode("v-if", true)
                        }
                        ,
                        createElementVNode("view", utsMapOf("class" to "review-footer"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "review-time"), toDisplayString(item.createTime), 1),
                            if (item.status === 0) {
                                createElementVNode("text", utsMapOf("key" to 0, "class" to "review-status"), "已隐藏")
                            } else {
                                createCommentVNode("v-if", true)
                            }
                        ))
                    ))
                }
                ), 128),
                if (isTrue(_ctx.isLoading)) {
                    createElementVNode("view", utsMapOf("key" to 1, "class" to "loading-more"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "loading-text"), "加载中...")
                    ))
                } else {
                    createCommentVNode("v-if", true)
                }
                ,
                if (isTrue(!_ctx.hasMore && _ctx.reviews.length > 0)) {
                    createElementVNode("view", utsMapOf("key" to 2, "class" to "no-more"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "no-more-text"), "没有更多了")
                    ))
                } else {
                    createCommentVNode("v-if", true)
                }
                ,
                createElementVNode("view", utsMapOf("style" to normalizeStyle(utsMapOf("height" to "40rpx"))), null, 4)
            ), 40, utsArrayOf(
                "onScrolltolower"
            ))
        ))
    }
    open var reviews: UTSArray<ReviewVO> by `$data`
    open var pageNum: Number by `$data`
    open var pageSize: Number by `$data`
    open var isLoading: Boolean by `$data`
    open var hasMore: Boolean by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return utsMapOf("reviews" to utsArrayOf<ReviewVO>(), "pageNum" to 1, "pageSize" to 10, "isLoading" to false, "hasMore" to true)
    }
    open var goBack = ::gen_goBack_fn
    open fun gen_goBack_fn() {
        uni_navigateBack(null)
    }
    open var goToProduct = ::gen_goToProduct_fn
    open fun gen_goToProduct_fn(productId: Number) {
        uni_navigateTo(NavigateToOptions(url = "/pages/product/detail?id=" + productId))
    }
    open var loadReviews = ::gen_loadReviews_fn
    open fun gen_loadReviews_fn() {
        if (this.isLoading || !this.hasMore) {
            return
        }
        this.isLoading = true
        getMyReviews(this.pageNum, this.pageSize).then(fun(res){
            val data = res.data as UTSJSONObject
            val records = data.getArray("records")
            val total = (data.getNumber("total") ?: 0).toInt()
            val newReviews: UTSArray<ReviewVO> = utsArrayOf()
            if (records != null) {
                run {
                    var i: Number = 0
                    while(i < records.length){
                        val item = records[i] as UTSJSONObject
                        newReviews.push(ReviewVO(id = (item.getNumber("id") ?: 0).toInt(), productId = (item.getNumber("productId") ?: 0).toInt(), productName = item.getString("productName") ?: "", productMainImage = item.getString("productMainImage") ?: "", userId = (item.getNumber("userId") ?: 0).toInt(), userNickname = item.getString("userNickname") ?: "", userAvatar = item.getString("userAvatar") ?: "", orderId = (item.getNumber("orderId") ?: 0).toInt(), rating = (item.getNumber("rating") ?: 5).toInt(), content = item.getString("content") ?: "", images = item.getString("images") ?: "", replyContent = item.getString("replyContent"), replyTime = item.getString("replyTime"), status = (item.getNumber("status") ?: 1).toInt(), createTime = item.getString("createTime") ?: ""))
                        i++
                    }
                }
            }
            if (this.pageNum === 1) {
                this.reviews = newReviews
            } else {
                this.reviews = this.reviews.concat(newReviews)
            }
            this.hasMore = this.reviews.length < total
            this.pageNum++
            this.isLoading = false
        }
        ).`catch`(fun(err){
            console.error("获取我的评价失败:", err, " at pages/user/reviews.uvue:133")
            this.isLoading = false
            if (this.pageNum === 1) {
                uni_showToast(ShowToastOptions(title = "加载失败", icon = "none"))
            }
        }
        )
    }
    open var loadMore = ::gen_loadMore_fn
    open fun gen_loadMore_fn() {
        this.loadReviews()
    }
    open var getImages = ::gen_getImages_fn
    open fun gen_getImages_fn(imagesStr: String): UTSArray<String> {
        if (imagesStr == null || imagesStr == "") {
            return utsArrayOf()
        }
        try {
            val arr = UTSAndroid.consoleDebugError(JSON.parse(imagesStr), " at pages/user/reviews.uvue:146")
            if (UTSArray.isArray(arr)) {
                return arr as UTSArray<String>
            }
        }
         catch (e: Throwable) {
            if (imagesStr.includes(",")) {
                return imagesStr.split(",")
            }
        }
        return utsArrayOf(
            imagesStr
        )
    }
    open var previewImage = ::gen_previewImage_fn
    open fun gen_previewImage_fn(urls: UTSArray<String>, current: Number) {
        uni_previewImage(PreviewImageOptions(urls = urls, current = current))
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
                return utsMapOf("page-container" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "column", "flex" to 1, "backgroundColor" to "#f7f8fa")), "nav-bar" to padStyleMapOf(utsMapOf("height" to 44, "backgroundColor" to "#ffffff", "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "paddingTop" to CSS_VAR_STATUS_BAR_HEIGHT, "paddingRight" to 16, "paddingBottom" to 0, "paddingLeft" to 16, "boxShadow" to "0 1px 4px rgba(0, 0, 0, 0.05)", "zIndex" to 100)), "nav-back" to padStyleMapOf(utsMapOf("width" to 40, "height" to 44, "display" to "flex", "alignItems" to "center")), "back-icon" to padStyleMapOf(utsMapOf("fontSize" to 28, "color" to "#333333")), "nav-title" to padStyleMapOf(utsMapOf("fontSize" to 16, "fontWeight" to "bold", "color" to "#333333")), "nav-placeholder" to padStyleMapOf(utsMapOf("width" to 40)), "review-list" to padStyleMapOf(utsMapOf("flex" to 1, "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12)), "review-card" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "marginBottom" to 12, "boxShadow" to "0 2px 8px rgba(0, 0, 0, 0.02)")), "product-info" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "marginBottom" to 12, "paddingBottom" to 12, "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "#f5f5f5")), "product-img" to padStyleMapOf(utsMapOf("width" to 48, "height" to 48, "borderTopLeftRadius" to 6, "borderTopRightRadius" to 6, "borderBottomRightRadius" to 6, "borderBottomLeftRadius" to 6, "backgroundColor" to "#f5f5f5", "marginRight" to 12)), "product-detail" to padStyleMapOf(utsMapOf("flex" to 1, "display" to "flex", "flexDirection" to "column", "justifyContent" to "center")), "product-name" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#333333", "marginBottom" to 6)), "rating-stars" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row")), "star-icon" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#eeeeee", "marginRight" to 2)), "star-icon-active" to padStyleMapOf(utsMapOf("color" to "#ff9900")), "review-content" to padStyleMapOf(utsMapOf("marginBottom" to 12)), "content-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#333333", "lineHeight" to 1.5, "marginBottom" to 10)), "image-list" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "flexWrap" to "wrap", "marginTop" to 8)), "review-img" to padStyleMapOf(utsMapOf("width" to 80, "height" to 80, "borderTopLeftRadius" to 6, "borderTopRightRadius" to 6, "borderBottomRightRadius" to 6, "borderBottomLeftRadius" to 6, "marginRight" to 8, "marginBottom" to 8, "backgroundColor" to "#f5f5f5")), "reply-box" to padStyleMapOf(utsMapOf("backgroundColor" to "#f8f8f8", "paddingTop" to 10, "paddingRight" to 12, "paddingBottom" to 10, "paddingLeft" to 12, "borderTopLeftRadius" to 6, "borderTopRightRadius" to 6, "borderBottomRightRadius" to 6, "borderBottomLeftRadius" to 6, "marginBottom" to 12, "display" to "flex", "flexDirection" to "column")), "reply-label" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#666666", "fontWeight" to "bold", "marginBottom" to 4)), "reply-content" to padStyleMapOf(utsMapOf("fontSize" to 13, "color" to "#333333", "lineHeight" to 1.4)), "review-footer" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center")), "review-time" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999")), "review-status" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#ff4d4f", "backgroundColor" to "#ffebee", "paddingTop" to 2, "paddingRight" to 6, "paddingBottom" to 2, "paddingLeft" to 6, "borderTopLeftRadius" to 4, "borderTopRightRadius" to 4, "borderBottomRightRadius" to 4, "borderBottomLeftRadius" to 4)), "empty-state" to padStyleMapOf(utsMapOf("paddingTop" to 30, "paddingRight" to 30, "paddingBottom" to 30, "paddingLeft" to 30, "display" to "flex", "flexDirection" to "column", "alignItems" to "center", "justifyContent" to "center")), "loading-more" to padStyleMapOf(utsMapOf("paddingTop" to 30, "paddingRight" to 30, "paddingBottom" to 30, "paddingLeft" to 30, "display" to "flex", "flexDirection" to "column", "alignItems" to "center", "justifyContent" to "center")), "no-more" to padStyleMapOf(utsMapOf("paddingTop" to 30, "paddingRight" to 30, "paddingBottom" to 30, "paddingLeft" to 30, "display" to "flex", "flexDirection" to "column", "alignItems" to "center", "justifyContent" to "center")), "empty-icon" to padStyleMapOf(utsMapOf("fontSize" to 48, "marginBottom" to 16)), "empty-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#999999")), "loading-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#999999")), "no-more-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#999999")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = utsMapOf()
        var emits: Map<String, Any?> = utsMapOf()
        var props = normalizePropsOptions(utsMapOf())
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf()
        var components: Map<String, CreateVueComponent> = utsMapOf()
    }
}
