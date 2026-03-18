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
import io.dcloud.uniapp.extapi.showModal as uni_showModal
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesAdminReviews : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onLoad(fun(_: OnLoadOptions) {
            this.loadStatistics()
            this.loadReviews()
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return createElementVNode("view", utsMapOf("class" to "page-container"), utsArrayOf(
            createElementVNode("view", utsMapOf("class" to "top-panel"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "stats-bar"), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "stats-item"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "stats-label"), "总评价"),
                        createElementVNode("text", utsMapOf("class" to "stats-value"), toDisplayString(_ctx.statsTotal), 1)
                    )),
                    createElementVNode("view", utsMapOf("class" to "stats-item"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "stats-label"), "显示中"),
                        createElementVNode("text", utsMapOf("class" to "stats-value stats-value-success"), toDisplayString(_ctx.statsVisible), 1)
                    )),
                    createElementVNode("view", utsMapOf("class" to "stats-item"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "stats-label"), "已隐藏"),
                        createElementVNode("text", utsMapOf("class" to "stats-value stats-value-danger"), toDisplayString(_ctx.statsHidden), 1)
                    ))
                )),
                createElementVNode("view", utsMapOf("class" to "search-bar"), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "search-icon"), "🔍"),
                    createElementVNode("input", utsMapOf("class" to "search-input", "modelValue" to _ctx.searchKeyword, "onInput" to fun(`$event`: InputEvent){
                        _ctx.searchKeyword = `$event`.detail.value
                    }
                    , "placeholder" to "搜索评价内容 / 用户昵称", "onConfirm" to _ctx.handleSearch), null, 40, utsArrayOf(
                        "modelValue",
                        "onInput",
                        "onConfirm"
                    ))
                )),
                createElementVNode("view", utsMapOf("class" to "filter-bar"), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "filter-group"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "filter-title"), "状态"),
                        createElementVNode("view", utsMapOf("class" to "filter-options"), utsArrayOf(
                            createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.statusOptions, fun(label, index, __index, _cached): Any {
                                return createElementVNode("text", utsMapOf("key" to ("status-" + index), "class" to normalizeClass(utsArrayOf(
                                    "filter-chip",
                                    utsMapOf("filter-chip-active" to (_ctx.selectedStatusIndex === index))
                                )), "onClick" to fun(){
                                    _ctx.selectStatus(index)
                                }
                                ), toDisplayString(label), 11, utsArrayOf(
                                    "onClick"
                                ))
                            }
                            ), 128)
                        ))
                    )),
                    createElementVNode("view", utsMapOf("class" to "filter-group"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "filter-title"), "评分"),
                        createElementVNode("view", utsMapOf("class" to "filter-options"), utsArrayOf(
                            createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.ratingOptions, fun(label, index, __index, _cached): Any {
                                return createElementVNode("text", utsMapOf("key" to ("rating-" + index), "class" to normalizeClass(utsArrayOf(
                                    "filter-chip",
                                    utsMapOf("filter-chip-active" to (_ctx.selectedRatingIndex === index))
                                )), "onClick" to fun(){
                                    _ctx.selectRating(index)
                                }
                                ), toDisplayString(label), 11, utsArrayOf(
                                    "onClick"
                                ))
                            }
                            ), 128)
                        ))
                    ))
                ))
            )),
            createElementVNode("scroll-view", utsMapOf("class" to "review-list", "scroll-y" to "true", "onScrolltolower" to _ctx.loadMore), utsArrayOf(
                if (isTrue(_ctx.reviews.length === 0 && !_ctx.isLoading)) {
                    createElementVNode("view", utsMapOf("key" to 0, "class" to "empty-state"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "empty-icon"), "📝"),
                        createElementVNode("text", utsMapOf("class" to "empty-text"), "暂无评价")
                    ))
                } else {
                    createCommentVNode("v-if", true)
                }
                ,
                createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.reviews, fun(item, index, __index, _cached): Any {
                    return createElementVNode("view", utsMapOf("class" to "review-card", "key" to index), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "review-header"), utsArrayOf(
                            createElementVNode("view", utsMapOf("class" to "user-info"), utsArrayOf(
                                createElementVNode("image", utsMapOf("class" to "avatar", "src" to item.userAvatar, "mode" to "aspectFill"), null, 8, utsArrayOf(
                                    "src"
                                )),
                                createElementVNode("view", utsMapOf("class" to "user-meta"), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "nickname"), toDisplayString(item.userNickname), 1),
                                    createElementVNode("text", utsMapOf("class" to "review-time"), toDisplayString(item.createTime), 1)
                                ))
                            )),
                            createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                                "status-tag",
                                if (item.status === 1) {
                                    "status-tag-visible"
                                } else {
                                    "status-tag-hidden"
                                }
                            ))), toDisplayString(if (item.status === 1) {
                                "显示中"
                            } else {
                                "已隐藏"
                            }
                            ), 3)
                        )),
                        createElementVNode("view", utsMapOf("class" to "action-box"), utsArrayOf(
                            createElementVNode("button", utsMapOf("class" to "action-btn", "onClick" to fun(){
                                _ctx.toggleStatus(item)
                            }
                            ), toDisplayString(if (item.status === 1) {
                                "隐藏"
                            } else {
                                "显示"
                            }
                            ), 9, utsArrayOf(
                                "onClick"
                            )),
                            createElementVNode("button", utsMapOf("class" to "action-btn action-btn-danger", "onClick" to fun(){
                                _ctx.deleteReviewItem(item.id)
                            }
                            ), "删除", 8, utsArrayOf(
                                "onClick"
                            ))
                        )),
                        createElementVNode("view", utsMapOf("class" to "rating-stars"), utsArrayOf(
                            createElementVNode(Fragment, null, RenderHelpers.renderList(5, fun(i, __key, __index, _cached): Any {
                                return createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                                    "star-icon",
                                    utsMapOf("star-icon-active" to (i <= item.rating))
                                )), "key" to i), "★", 2)
                            }
                            ), 64)
                        )),
                        createElementVNode("view", utsMapOf("class" to "review-content"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                                "content-text",
                                utsMapOf("content-text-hidden" to (item.status === 0))
                            ))), toDisplayString(item.content), 3),
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
                        createElementVNode("view", utsMapOf("class" to "product-info", "onClick" to fun(){
                            _ctx.goToProduct(item.productId)
                        }
                        ), utsArrayOf(
                            createElementVNode("image", utsMapOf("class" to "product-img", "src" to item.productMainImage, "mode" to "aspectFill"), null, 8, utsArrayOf(
                                "src"
                            )),
                            createElementVNode("text", utsMapOf("class" to "product-name"), toDisplayString(item.productName), 1)
                        ), 8, utsArrayOf(
                            "onClick"
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
    open var searchKeyword: String by `$data`
    open var selectedStatusIndex: Number by `$data`
    open var selectedRatingIndex: Number by `$data`
    open var statusOptions: UTSArray<String> by `$data`
    open var ratingOptions: UTSArray<String> by `$data`
    open var statsTotal: Number by `$data`
    open var statsVisible: Number by `$data`
    open var statsHidden: Number by `$data`
    open var isLoading: Boolean by `$data`
    open var hasMore: Boolean by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return utsMapOf("reviews" to utsArrayOf<ReviewVO>(), "pageNum" to 1, "pageSize" to 10, "searchKeyword" to "", "selectedStatusIndex" to 0, "selectedRatingIndex" to 0, "statusOptions" to utsArrayOf(
            "全部",
            "显示",
            "隐藏"
        ), "ratingOptions" to utsArrayOf(
            "全部",
            "1星",
            "2星",
            "3星",
            "4星",
            "5星"
        ), "statsTotal" to 0, "statsVisible" to 0, "statsHidden" to 0, "isLoading" to false, "hasMore" to true)
    }
    open var goBack = ::gen_goBack_fn
    open fun gen_goBack_fn() {
        uni_navigateBack(null)
    }
    open var goToProduct = ::gen_goToProduct_fn
    open fun gen_goToProduct_fn(productId: Number) {
        uni_navigateTo(NavigateToOptions(url = "/pages/product/detail?id=" + productId))
    }
    open var resetAndLoad = ::gen_resetAndLoad_fn
    open fun gen_resetAndLoad_fn() {
        this.pageNum = 1
        this.reviews = utsArrayOf()
        this.hasMore = true
        this.loadReviews()
    }
    open var handleSearch = ::gen_handleSearch_fn
    open fun gen_handleSearch_fn() {
        this.resetAndLoad()
    }
    open var selectStatus = ::gen_selectStatus_fn
    open fun gen_selectStatus_fn(index: Number) {
        if (this.selectedStatusIndex == index) {
            return
        }
        this.selectedStatusIndex = index
        this.resetAndLoad()
    }
    open var selectRating = ::gen_selectRating_fn
    open fun gen_selectRating_fn(index: Number) {
        if (this.selectedRatingIndex == index) {
            return
        }
        this.selectedRatingIndex = index
        this.resetAndLoad()
    }
    open var loadStatistics = ::gen_loadStatistics_fn
    open fun gen_loadStatistics_fn() {
        getAdminReviewStatistics().then(fun(res){
            val data = res.data as UTSJSONObject
            this.statsTotal = (data.getNumber("total") ?: 0).toInt()
            this.statsVisible = (data.getNumber("visible") ?: 0).toInt()
            this.statsHidden = (data.getNumber("hidden") ?: 0).toInt()
        }
        ).`catch`(fun(err){
            console.error("获取评价统计失败:", err, " at pages/admin/reviews.uvue:169")
        }
        )
    }
    open var loadReviews = ::gen_loadReviews_fn
    open fun gen_loadReviews_fn() {
        if (this.isLoading || !this.hasMore) {
            return
        }
        this.isLoading = true
        val statusValues: UTSArray<Number> = utsArrayOf(
            -1,
            1,
            0
        )
        val ratingValues: UTSArray<Number> = utsArrayOf(
            0,
            1,
            2,
            3,
            4,
            5
        )
        val status = statusValues[this.selectedStatusIndex]
        val rating = ratingValues[this.selectedRatingIndex]
        getAdminReviews(this.pageNum, this.pageSize, this.searchKeyword, status, rating).then(fun(res){
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
            console.error("获取评价列表失败:", err, " at pages/admin/reviews.uvue:219")
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
            val arr = UTSAndroid.consoleDebugError(JSON.parse(imagesStr), " at pages/admin/reviews.uvue:232")
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
    open var toggleStatus = ::gen_toggleStatus_fn
    open fun gen_toggleStatus_fn(item: ReviewVO) {
        val newStatus = if (item.status === 1) {
            0
        } else {
            1
        }
        val actionName = if (newStatus === 1) {
            "显示"
        } else {
            "隐藏"
        }
        uni_showModal(ShowModalOptions(title = "提示", content = "\u786E\u5B9A\u8981" + actionName + "\u8BE5\u8BC4\u4EF7\u5417\uFF1F", success = fun(res){
            if (res.confirm) {
                updateReviewStatus(item.id, newStatus).then(fun(){
                    uni_showToast(ShowToastOptions(title = "操作成功", icon = "success"))
                    item.status = newStatus
                    this.loadStatistics()
                }
                ).`catch`(fun(err){
                    console.error("更新状态失败:", err, " at pages/admin/reviews.uvue:263")
                    uni_showToast(ShowToastOptions(title = "操作失败", icon = "none"))
                }
                )
            }
        }
        ))
    }
    open var deleteReviewItem = ::gen_deleteReviewItem_fn
    open fun gen_deleteReviewItem_fn(id: Number) {
        uni_showModal(ShowModalOptions(title = "警告", content = "确定要永久删除该评价吗？此操作不可恢复。", confirmColor = "#ff4d4f", success = fun(res){
            if (res.confirm) {
                deleteReview(id).then(fun(){
                    uni_showToast(ShowToastOptions(title = "删除成功", icon = "success"))
                    this.loadStatistics()
                    this.resetAndLoad()
                }
                ).`catch`(fun(err){
                    console.error("删除评价失败:", err, " at pages/admin/reviews.uvue:282")
                    uni_showToast(ShowToastOptions(title = "删除失败", icon = "none"))
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
                return utsMapOf("page-container" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "column", "flex" to 1, "backgroundColor" to "#f7f8fa")), "top-panel" to padStyleMapOf(utsMapOf("backgroundImage" to "linear-gradient(180deg, #f4f8ff 0%, #f7f8fa 100%)", "backgroundColor" to "rgba(0,0,0,0)", "paddingTop" to 10, "paddingRight" to 10, "paddingBottom" to 6, "paddingLeft" to 10)), "stats-bar" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "marginBottom" to 8)), "stats-item" to padStyleMapOf(utsMapOf("flex" to 1, "backgroundColor" to "#ffffff", "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10, "paddingTop" to 10, "paddingRight" to 8, "paddingBottom" to 10, "paddingLeft" to 8, "marginRight" to 6, "boxShadow" to "0 1px 6px rgba(0, 0, 0, 0.03)", "marginRight:last-child" to 0)), "stats-label" to padStyleMapOf(utsMapOf("fontSize" to 11, "color" to "#8c8c8c", "display" to "flex", "marginBottom" to 2)), "stats-value" to padStyleMapOf(utsMapOf("fontSize" to 18, "fontWeight" to "700", "color" to "#1f2329")), "stats-value-success" to padStyleMapOf(utsMapOf("color" to "#00b578")), "stats-value-danger" to padStyleMapOf(utsMapOf("color" to "#ff4d4f")), "nav-bar" to padStyleMapOf(utsMapOf("height" to 44, "backgroundColor" to "#ffffff", "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "paddingTop" to CSS_VAR_STATUS_BAR_HEIGHT, "paddingRight" to 16, "paddingBottom" to 0, "paddingLeft" to 16, "boxShadow" to "0 1px 4px rgba(0, 0, 0, 0.05)", "zIndex" to 100)), "nav-back" to padStyleMapOf(utsMapOf("width" to 40, "height" to 44, "display" to "flex", "alignItems" to "center")), "back-icon" to padStyleMapOf(utsMapOf("fontSize" to 28, "color" to "#333333")), "nav-title" to padStyleMapOf(utsMapOf("fontSize" to 16, "fontWeight" to "bold", "color" to "#333333")), "nav-placeholder" to padStyleMapOf(utsMapOf("width" to 40)), "search-bar" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "paddingTop" to 8, "paddingRight" to 10, "paddingBottom" to 8, "paddingLeft" to 10, "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10, "boxShadow" to "0 1px 6px rgba(0, 0, 0, 0.03)")), "filter-bar" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "paddingTop" to 10, "paddingRight" to 10, "paddingBottom" to 10, "paddingLeft" to 10, "marginTop" to 8, "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10, "boxShadow" to "0 1px 6px rgba(0, 0, 0, 0.03)")), "filter-group" to padStyleMapOf(utsMapOf("marginBottom" to 8, "marginBottom:last-child" to 0)), "filter-title" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999", "marginBottom" to 6, "display" to "flex")), "filter-options" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "flexWrap" to "wrap")), "filter-chip" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#666666", "backgroundColor" to "#f5f6f8", "paddingTop" to 6, "paddingRight" to 12, "paddingBottom" to 6, "paddingLeft" to 12, "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "marginRight" to 8, "marginBottom" to 6, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "rgba(0,0,0,0)", "borderRightColor" to "rgba(0,0,0,0)", "borderBottomColor" to "rgba(0,0,0,0)", "borderLeftColor" to "rgba(0,0,0,0)")), "filter-chip-active" to padStyleMapOf(utsMapOf("color" to "#1677ff", "backgroundColor" to "#eaf3ff", "borderTopColor" to "#bcd8ff", "borderRightColor" to "#bcd8ff", "borderBottomColor" to "#bcd8ff", "borderLeftColor" to "#bcd8ff", "fontWeight" to "bold")), "search-icon" to padStyleMapOf(utsMapOf("fontSize" to 16, "color" to "#999999", "marginRight" to 8)), "search-input" to padStyleMapOf(utsMapOf("flex" to 1, "height" to 34, "backgroundColor" to "#f5f6f8", "borderTopLeftRadius" to 17, "borderTopRightRadius" to 17, "borderBottomRightRadius" to 17, "borderBottomLeftRadius" to 17, "paddingTop" to 0, "paddingRight" to 12, "paddingBottom" to 0, "paddingLeft" to 12, "fontSize" to 14)), "review-list" to padStyleMapOf(utsMapOf("flex" to 1, "paddingTop" to 0, "paddingRight" to 10, "paddingBottom" to 10, "paddingLeft" to 10)), "review-card" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "marginBottom" to 10, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#f0f2f5", "borderRightColor" to "#f0f2f5", "borderBottomColor" to "#f0f2f5", "borderLeftColor" to "#f0f2f5", "boxShadow" to "0 2px 8px rgba(22, 119, 255, 0.04)")), "review-header" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "marginBottom" to 12)), "user-info" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center")), "avatar" to padStyleMapOf(utsMapOf("width" to 40, "height" to 40, "borderTopLeftRadius" to 20, "borderTopRightRadius" to 20, "borderBottomRightRadius" to 20, "borderBottomLeftRadius" to 20, "backgroundColor" to "#eeeeee", "marginRight" to 10)), "user-meta" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "column")), "nickname" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#1f2329", "fontWeight" to "700")), "review-time" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999", "marginTop" to 2)), "action-box" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center")), "status-tag" to padStyleMapOf(utsMapOf("fontSize" to 11, "paddingTop" to 4, "paddingRight" to 8, "paddingBottom" to 4, "paddingLeft" to 8, "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10, "marginRight" to 6)), "status-tag-visible" to padStyleMapOf(utsMapOf("color" to "#00a870", "backgroundColor" to "#e8fff5")), "status-tag-hidden" to padStyleMapOf(utsMapOf("color" to "#ff4d4f", "backgroundColor" to "#fff1f0")), "action-btn" to padStyleMapOf(utsMapOf("marginTop" to 0, "marginRight" to 0, "marginBottom" to 0, "marginLeft" to 6, "paddingTop" to 0, "paddingRight" to 12, "paddingBottom" to 0, "paddingLeft" to 12, "height" to 30, "lineHeight" to "30px", "fontSize" to 12, "backgroundColor" to "#f5f6f8", "color" to "#333333", "borderTopLeftRadius" to 15, "borderTopRightRadius" to 15, "borderBottomRightRadius" to 15, "borderBottomLeftRadius" to 15)), "action-btn-danger" to padStyleMapOf(utsMapOf("color" to "#ff4d4f", "backgroundColor" to "#ffebee")), "rating-stars" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "marginBottom" to 8)), "star-icon" to padStyleMapOf(utsMapOf("fontSize" to 15, "color" to "#e8e8e8", "marginRight" to 2)), "star-icon-active" to padStyleMapOf(utsMapOf("color" to "#ffb400")), "review-content" to padStyleMapOf(utsMapOf("marginBottom" to 12)), "content-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#333333", "lineHeight" to 1.6, "marginBottom" to 10)), "content-text-hidden" to padStyleMapOf(utsMapOf("color" to "#999999", "fontStyle" to "italic")), "image-list" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "flexWrap" to "wrap")), "review-img" to padStyleMapOf(utsMapOf("width" to 84, "height" to 84, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "marginRight" to 8, "marginBottom" to 8, "backgroundColor" to "#f5f5f5", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#f0f0f0", "borderRightColor" to "#f0f0f0", "borderBottomColor" to "#f0f0f0", "borderLeftColor" to "#f0f0f0")), "product-info" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "backgroundColor" to "#f7f9fc", "paddingTop" to 8, "paddingRight" to 8, "paddingBottom" to 8, "paddingLeft" to 8, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8)), "product-img" to padStyleMapOf(utsMapOf("width" to 42, "height" to 42, "borderTopLeftRadius" to 6, "borderTopRightRadius" to 6, "borderBottomRightRadius" to 6, "borderBottomLeftRadius" to 6, "marginRight" to 8, "backgroundColor" to "#eeeeee")), "product-name" to padStyleMapOf(utsMapOf("flex" to 1, "fontSize" to 13, "color" to "#5f6773", "whiteSpace" to "nowrap", "overflow" to "hidden", "textOverflow" to "ellipsis")), "empty-state" to padStyleMapOf(utsMapOf("paddingTop" to 30, "paddingRight" to 30, "paddingBottom" to 30, "paddingLeft" to 30, "display" to "flex", "flexDirection" to "column", "alignItems" to "center", "justifyContent" to "center")), "loading-more" to padStyleMapOf(utsMapOf("paddingTop" to 30, "paddingRight" to 30, "paddingBottom" to 30, "paddingLeft" to 30, "display" to "flex", "flexDirection" to "column", "alignItems" to "center", "justifyContent" to "center")), "no-more" to padStyleMapOf(utsMapOf("paddingTop" to 30, "paddingRight" to 30, "paddingBottom" to 30, "paddingLeft" to 30, "display" to "flex", "flexDirection" to "column", "alignItems" to "center", "justifyContent" to "center")), "empty-icon" to padStyleMapOf(utsMapOf("fontSize" to 48, "marginBottom" to 16)), "empty-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#999999")), "loading-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#999999")), "no-more-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#999999")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = utsMapOf()
        var emits: Map<String, Any?> = utsMapOf()
        var props = normalizePropsOptions(utsMapOf())
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf()
        var components: Map<String, CreateVueComponent> = utsMapOf()
    }
}
