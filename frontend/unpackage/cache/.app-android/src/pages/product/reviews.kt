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
import io.dcloud.uniapp.extapi.setNavigationBarTitle as uni_setNavigationBarTitle
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesProductReviews : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onLoad(fun(options: Any) {
            val opts = options as UTSJSONObject
            this.productId = parseInt(opts.getString("productId") ?: "0")
            val productName = UTSAndroid.consoleDebugError(decodeURIComponent(opts.getString("productName") ?: ""), " at pages/product/reviews.uvue:46")
            if (productName != "") {
                uni_setNavigationBarTitle(SetNavigationBarTitleOptions(title = "" + productName + "\u8BC4\u4EF7"))
            }
            this.loadReviews()
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return createElementVNode("view", utsMapOf("class" to "reviews-page"), utsArrayOf(
            createElementVNode("view", utsMapOf("class" to "filter-row"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                    "filter-item",
                    utsMapOf("active" to (_ctx.currentRating == null))
                )), "onClick" to fun(){
                    _ctx.changeRating(null)
                }
                ), "全部", 10, utsArrayOf(
                    "onClick"
                )),
                createElementVNode(Fragment, null, RenderHelpers.renderList(utsArrayOf(
                    5,
                    4,
                    3,
                    2,
                    1
                ), fun(r, __key, __index, _cached): Any {
                    return createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                        "filter-item",
                        utsMapOf("active" to (_ctx.currentRating === r))
                    )), "key" to r, "onClick" to fun(){
                        _ctx.changeRating(r)
                    }
                    ), toDisplayString(r) + "星", 11, utsArrayOf(
                        "onClick"
                    ))
                }
                ), 64)
            )),
            createElementVNode("scroll-view", utsMapOf("class" to "list", "scroll-y" to "true", "onScrolltolower" to _ctx.loadMore), utsArrayOf(
                if (isTrue(_ctx.reviews.length === 0 && !_ctx.isLoading)) {
                    createElementVNode("view", utsMapOf("key" to 0, "class" to "empty"), "暂无评价")
                } else {
                    createCommentVNode("v-if", true)
                }
                ,
                createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.reviews, fun(item, __key, __index, _cached): Any {
                    return createElementVNode("view", utsMapOf("class" to "item", "key" to item.id), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "head"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "name"), toDisplayString(item.userNickname), 1),
                            createElementVNode("text", utsMapOf("class" to "time"), toDisplayString(item.createTime), 1)
                        )),
                        createElementVNode("view", utsMapOf("class" to "stars"), utsArrayOf(
                            createElementVNode(Fragment, null, RenderHelpers.renderList(5, fun(i, __key, __index, _cached): Any {
                                return createElementVNode("text", utsMapOf("key" to i, "class" to normalizeClass(utsArrayOf(
                                    "star",
                                    utsMapOf("on" to (i <= item.rating))
                                ))), "★", 2)
                            }
                            ), 64)
                        )),
                        createElementVNode("text", utsMapOf("class" to "content"), toDisplayString(_ctx.getContentText(item.content)), 1),
                        if (isTrue(item.replyContent)) {
                            createElementVNode("view", utsMapOf("key" to 0, "class" to "reply"), "商家回复：" + toDisplayString(item.replyContent), 1)
                        } else {
                            createCommentVNode("v-if", true)
                        }
                    ))
                }
                ), 128),
                if (isTrue(_ctx.isLoading)) {
                    createElementVNode("view", utsMapOf("key" to 1, "class" to "tip"), "加载中...")
                } else {
                    if (isTrue(!_ctx.hasMore && _ctx.reviews.length > 0)) {
                        createElementVNode("view", utsMapOf("key" to 2, "class" to "tip"), "没有更多了")
                    } else {
                        createCommentVNode("v-if", true)
                    }
                }
                ,
                createElementVNode("view", utsMapOf("style" to normalizeStyle(utsMapOf("height" to "24rpx"))), null, 4)
            ), 40, utsArrayOf(
                "onScrolltolower"
            ))
        ))
    }
    open var productId: Number by `$data`
    open var pageNum: Number by `$data`
    open var pageSize: Number by `$data`
    open var currentRating: Number? by `$data`
    open var reviews: UTSArray<ReviewVO> by `$data`
    open var isLoading: Boolean by `$data`
    open var hasMore: Boolean by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return utsMapOf("productId" to 0, "pageNum" to 1, "pageSize" to 10, "currentRating" to null as Number?, "reviews" to utsArrayOf<ReviewVO>(), "isLoading" to false, "hasMore" to true)
    }
    open var changeRating = ::gen_changeRating_fn
    open fun gen_changeRating_fn(rating: Number?) {
        if (this.currentRating === rating) {
            return
        }
        this.currentRating = rating
        this.pageNum = 1
        this.reviews = utsArrayOf()
        this.hasMore = true
        this.loadReviews()
    }
    open var loadReviews = ::gen_loadReviews_fn
    open fun gen_loadReviews_fn() {
        if (this.isLoading || !this.hasMore) {
            return
        }
        this.isLoading = true
        getProductReviews(this.productId, this.pageNum, this.pageSize, this.currentRating).then(fun(res){
            val data = res.data as UTSJSONObject
            val total = (data.getNumber("total") ?: 0).toInt()
            val records = data.getArray("records")
            val list: UTSArray<ReviewVO> = utsArrayOf()
            if (records != null) {
                run {
                    var i: Number = 0
                    while(i < records.length){
                        val it = records[i] as UTSJSONObject
                        list.push(ReviewVO(id = (it.getNumber("id") ?: 0).toInt(), productId = (it.getNumber("productId") ?: 0).toInt(), productName = it.getString("productName") ?: "", productMainImage = it.getString("productMainImage") ?: "", userId = (it.getNumber("userId") ?: 0).toInt(), userNickname = it.getString("userNickname") ?: "匿名用户", userAvatar = it.getString("userAvatar") ?: "", orderId = (it.getNumber("orderId") ?: 0).toInt(), rating = (it.getNumber("rating") ?: 5).toInt(), content = it.getString("content") ?: "", images = it.getString("images") ?: "", replyContent = it.getString("replyContent"), replyTime = it.getString("replyTime"), status = (it.getNumber("status") ?: 1).toInt(), createTime = it.getString("createTime") ?: ""))
                        i++
                    }
                }
            }
            this.reviews = if (this.pageNum === 1) {
                list
            } else {
                this.reviews.concat(list)
            }
            this.hasMore = this.reviews.length < total
            this.pageNum++
            this.isLoading = false
        }
        ).`catch`(fun(err){
            console.error("获取评价列表失败:", err, " at pages/product/reviews.uvue:96")
            this.isLoading = false
            uni_showToast(ShowToastOptions(title = "加载失败", icon = "none"))
        }
        )
    }
    open var getContentText = ::gen_getContentText_fn
    open fun gen_getContentText_fn(content: String): String {
        return if (content == "") {
            "用户未填写评价内容"
        } else {
            content
        }
    }
    open var loadMore = ::gen_loadMore_fn
    open fun gen_loadMore_fn() {
        this.loadReviews()
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
                return utsMapOf("reviews-page" to padStyleMapOf(utsMapOf("backgroundImage" to "none", "backgroundColor" to "#f7f8fa", "height" to "100%", "display" to "flex", "flexDirection" to "column")), "filter-row" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "paddingTop" to "20rpx", "paddingRight" to "20rpx", "paddingBottom" to "20rpx", "paddingLeft" to "20rpx", "backgroundImage" to "none", "backgroundColor" to "#ffffff")), "filter-item" to utsMapOf("" to utsMapOf("marginRight" to "16rpx", "paddingTop" to "8rpx", "paddingRight" to "16rpx", "paddingBottom" to "8rpx", "paddingLeft" to "16rpx", "borderTopLeftRadius" to "20rpx", "borderTopRightRadius" to "20rpx", "borderBottomRightRadius" to "20rpx", "borderBottomLeftRadius" to "20rpx", "backgroundImage" to "none", "backgroundColor" to "#f2f3f5", "color" to "#666666", "fontSize" to "24rpx"), ".active" to utsMapOf("backgroundImage" to "none", "backgroundColor" to "#0066CC", "color" to "#ffffff")), "list" to padStyleMapOf(utsMapOf("flex" to 1, "paddingTop" to "20rpx", "paddingRight" to "20rpx", "paddingBottom" to "20rpx", "paddingLeft" to "20rpx", "boxSizing" to "border-box")), "item" to padStyleMapOf(utsMapOf("backgroundImage" to "none", "backgroundColor" to "#ffffff", "borderTopLeftRadius" to "12rpx", "borderTopRightRadius" to "12rpx", "borderBottomRightRadius" to "12rpx", "borderBottomLeftRadius" to "12rpx", "paddingTop" to "20rpx", "paddingRight" to "20rpx", "paddingBottom" to "20rpx", "paddingLeft" to "20rpx", "marginBottom" to "16rpx")), "head" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "marginBottom" to "8rpx")), "name" to padStyleMapOf(utsMapOf("fontSize" to "26rpx", "color" to "#333333")), "time" to padStyleMapOf(utsMapOf("fontSize" to "22rpx", "color" to "#999999")), "stars" to padStyleMapOf(utsMapOf("marginBottom" to "10rpx")), "star" to utsMapOf("" to utsMapOf("color" to "#dddddd", "fontSize" to "24rpx"), ".on" to utsMapOf("color" to "#ff9900")), "content" to padStyleMapOf(utsMapOf("fontSize" to "26rpx", "color" to "#333333", "lineHeight" to 1.5)), "reply" to padStyleMapOf(utsMapOf("marginTop" to "12rpx", "backgroundImage" to "none", "backgroundColor" to "#f8f8f8", "paddingTop" to "10rpx", "paddingRight" to "10rpx", "paddingBottom" to "10rpx", "paddingLeft" to "10rpx", "borderTopLeftRadius" to "8rpx", "borderTopRightRadius" to "8rpx", "borderBottomRightRadius" to "8rpx", "borderBottomLeftRadius" to "8rpx", "fontSize" to "24rpx", "color" to "#666666")), "empty" to padStyleMapOf(utsMapOf("textAlign" to "center", "color" to "#999999", "fontSize" to "24rpx", "paddingTop" to "30rpx", "paddingRight" to 0, "paddingBottom" to "30rpx", "paddingLeft" to 0)), "tip" to padStyleMapOf(utsMapOf("textAlign" to "center", "color" to "#999999", "fontSize" to "24rpx", "paddingTop" to "30rpx", "paddingRight" to 0, "paddingBottom" to "30rpx", "paddingLeft" to 0)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = utsMapOf()
        var emits: Map<String, Any?> = utsMapOf()
        var props = normalizePropsOptions(utsMapOf())
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf()
        var components: Map<String, CreateVueComponent> = utsMapOf()
    }
}
