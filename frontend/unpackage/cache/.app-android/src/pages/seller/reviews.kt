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
import io.dcloud.uniapp.extapi.hideLoading as uni_hideLoading
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.previewImage as uni_previewImage
import io.dcloud.uniapp.extapi.setNavigationBarTitle as uni_setNavigationBarTitle
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesSellerReviews : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onLoad(fun(options: Any) {
            val opts = options as UTSJSONObject
            this.productId = parseInt(opts.getString("productId") ?: "0")
            this.productName = opts.getString("productName") ?: ""
            if (this.productName != "") {
                uni_setNavigationBarTitle(SetNavigationBarTitleOptions(title = "" + this.productName + "\u8BC4\u4EF7\u7BA1\u7406"))
            }
            this.loadSellerProducts(fun(){
                this.loadReviews()
            }
            )
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return createElementVNode("view", utsMapOf("class" to "page-container"), utsArrayOf(
            createElementVNode("view", utsMapOf("class" to "product-tip"), utsArrayOf(
                createElementVNode("text", utsMapOf("class" to "product-tip-text"), "当前范围：" + toDisplayString(if (_ctx.productId == 0) {
                    "全部商品评价"
                } else {
                    if (_ctx.productName == "") {
                        ("ID:" + _ctx.productId)
                    } else {
                        _ctx.productName
                    }
                }
                ), 1)
            )),
            if (_ctx.sellerProducts.length > 0) {
                createElementVNode("scroll-view", utsMapOf("key" to 0, "class" to "product-tabs", "scroll-x" to "true", "show-scrollbar" to false), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "product-tabs-row"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                            "product-tab-item",
                            utsMapOf("product-tab-item-active" to (_ctx.productId === 0))
                        )), "onClick" to fun(){
                            _ctx.selectProduct(0, "全部商品")
                        }), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                                "product-tab-text",
                                utsMapOf("product-tab-text-active" to (_ctx.productId === 0))
                            ))), "全部商品", 2)
                        ), 10, utsArrayOf(
                            "onClick"
                        )),
                        createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.sellerProducts, fun(p, idx, __index, _cached): Any {
                            return createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                                "product-tab-item",
                                utsMapOf("product-tab-item-active" to (_ctx.productId === (p.getNumber("id") ?: 0).toInt()))
                            )), "key" to idx, "onClick" to fun(){
                                _ctx.selectProduct((p.getNumber("id") ?: 0).toInt(), p.getString("productName") ?: "")
                            }), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                                    "product-tab-text",
                                    utsMapOf("product-tab-text-active" to (_ctx.productId === (p.getNumber("id") ?: 0).toInt()))
                                ))), toDisplayString(p.getString("productName") ?: ("商品" + (idx + 1))), 3)
                            ), 10, utsArrayOf(
                                "onClick"
                            ))
                        }), 128)
                    ))
                ))
            } else {
                createCommentVNode("v-if", true)
            }
            ,
            createElementVNode("view", utsMapOf("class" to "search-bar"), utsArrayOf(
                createElementVNode("text", utsMapOf("class" to "search-icon"), "🔍"),
                createElementVNode("input", utsMapOf("class" to "search-input", "modelValue" to _ctx.searchKeyword, "onInput" to fun(`$event`: InputEvent){
                    _ctx.searchKeyword = `$event`.detail.value
                }
                , "placeholder" to "搜索评价内容 / 商品名", "onConfirm" to _ctx.handleSearch), null, 40, utsArrayOf(
                    "modelValue",
                    "onInput",
                    "onConfirm"
                ))
            )),
            createElementVNode("view", utsMapOf("class" to "filter-bar"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                    "filter-item",
                    utsMapOf("filter-item-active" to (_ctx.ratingFilter === 0))
                )), "onClick" to fun(){
                    _ctx.setFilter(0)
                }
                ), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                        "filter-item-text",
                        utsMapOf("filter-item-active-text" to (_ctx.ratingFilter === 0))
                    ))), "全部", 2)
                ), 10, utsArrayOf(
                    "onClick"
                )),
                createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                    "filter-item",
                    utsMapOf("filter-item-active" to (_ctx.ratingFilter === 5))
                )), "onClick" to fun(){
                    _ctx.setFilter(5)
                }
                ), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                        "filter-item-text",
                        utsMapOf("filter-item-active-text" to (_ctx.ratingFilter === 5))
                    ))), "好评", 2)
                ), 10, utsArrayOf(
                    "onClick"
                )),
                createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                    "filter-item",
                    utsMapOf("filter-item-active" to (_ctx.ratingFilter === 3))
                )), "onClick" to fun(){
                    _ctx.setFilter(3)
                }
                ), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                        "filter-item-text",
                        utsMapOf("filter-item-active-text" to (_ctx.ratingFilter === 3))
                    ))), "中评", 2)
                ), 10, utsArrayOf(
                    "onClick"
                )),
                createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                    "filter-item",
                    utsMapOf("filter-item-active" to (_ctx.ratingFilter === 1))
                )), "onClick" to fun(){
                    _ctx.setFilter(1)
                }
                ), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                        "filter-item-text",
                        utsMapOf("filter-item-active-text" to (_ctx.ratingFilter === 1))
                    ))), "差评", 2)
                ), 10, utsArrayOf(
                    "onClick"
                ))
            )),
            if (_ctx.reviews.length > 0) {
                createElementVNode("view", utsMapOf("key" to 1, "class" to "summary-row"), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "summary-text"), "当前已加载 " + toDisplayString(_ctx.reviews.length) + " 条评价", 1)
                ))
            } else {
                createCommentVNode("v-if", true)
            }
            ,
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
                        )),
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
                            createElementVNode("view", utsMapOf("class" to "rating-stars"), utsArrayOf(
                                createElementVNode(Fragment, null, RenderHelpers.renderList(5, fun(i, __key, __index, _cached): Any {
                                    return createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                                        "star-icon",
                                        utsMapOf("star-icon-active" to (i <= item.rating))
                                    )), "key" to i), "★", 2)
                                }
                                ), 64)
                            ))
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
                                createElementVNode("text", utsMapOf("class" to "reply-label"), "我的回复："),
                                createElementVNode("text", utsMapOf("class" to "reply-text"), toDisplayString(item.replyContent), 1),
                                createElementVNode("text", utsMapOf("class" to "reply-time"), toDisplayString(item.replyTime), 1)
                            ))
                        } else {
                            createCommentVNode("v-if", true)
                        }
                        ,
                        if (isTrue(item.replyContent == null || item.replyContent == "")) {
                            createElementVNode("view", utsMapOf("key" to 1, "class" to "action-bar"), utsArrayOf(
                                createElementVNode("button", utsMapOf("class" to "reply-btn", "onClick" to fun(){
                                    _ctx.openReplyModal(item)
                                }), "回复评价", 8, utsArrayOf(
                                    "onClick"
                                ))
                            ))
                        } else {
                            createCommentVNode("v-if", true)
                        }
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
            )),
            if (isTrue(_ctx.showReplyModal)) {
                createElementVNode("view", utsMapOf("key" to 2, "class" to "modal-mask", "onClick" to _ctx.closeReplyModal), null, 8, utsArrayOf(
                    "onClick"
                ))
            } else {
                createCommentVNode("v-if", true)
            }
            ,
            if (isTrue(_ctx.showReplyModal)) {
                createElementVNode("view", utsMapOf("key" to 3, "class" to "reply-modal"), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "modal-header"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "modal-title"), "回复评价"),
                        createElementVNode("text", utsMapOf("class" to "close-icon", "onClick" to _ctx.closeReplyModal), "×", 8, utsArrayOf(
                            "onClick"
                        ))
                    )),
                    createElementVNode("view", utsMapOf("class" to "modal-content"), utsArrayOf(
                        createElementVNode("textarea", utsMapOf("class" to "reply-input", "modelValue" to _ctx.replyContent, "onInput" to fun(`$event`: InputEvent){
                            _ctx.replyContent = `$event`.detail.value
                        }, "placeholder" to "请输入回复内容(最多200字)", "maxlength" to "200", "adjust-position" to true, "cursor-spacing" to 120), null, 40, utsArrayOf(
                            "modelValue",
                            "onInput"
                        ))
                    )),
                    createElementVNode("view", utsMapOf("class" to "modal-footer"), utsArrayOf(
                        createElementVNode("button", utsMapOf("class" to "btn btn-cancel", "onClick" to _ctx.closeReplyModal), "取消", 8, utsArrayOf(
                            "onClick"
                        )),
                        createElementVNode("button", utsMapOf("class" to "btn btn-confirm", "onClick" to _ctx.submitReply), "确定", 8, utsArrayOf(
                            "onClick"
                        ))
                    ))
                ))
            } else {
                createCommentVNode("v-if", true)
            }
        ))
    }
    open var productId: Number by `$data`
    open var productName: String by `$data`
    open var sellerProducts: UTSArray<UTSJSONObject> by `$data`
    open var productNameOptions: UTSArray<String> by `$data`
    open var reviews: UTSArray<ReviewVO> by `$data`
    open var allReviewsPool: UTSArray<ReviewVO> by `$data`
    open var allModeLoaded: Boolean by `$data`
    open var pageNum: Number by `$data`
    open var pageSize: Number by `$data`
    open var searchKeyword: String by `$data`
    open var ratingFilter: Number by `$data`
    open var isLoading: Boolean by `$data`
    open var hasMore: Boolean by `$data`
    open var showReplyModal: Boolean by `$data`
    open var currentReviewId: Number by `$data`
    open var replyContent: String by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return utsMapOf("productId" to 0, "productName" to "", "sellerProducts" to utsArrayOf<UTSJSONObject>(), "productNameOptions" to utsArrayOf<String>(), "reviews" to utsArrayOf<ReviewVO>(), "allReviewsPool" to utsArrayOf<ReviewVO>(), "allModeLoaded" to false, "pageNum" to 1, "pageSize" to 10, "searchKeyword" to "", "ratingFilter" to 0, "isLoading" to false, "hasMore" to true, "showReplyModal" to false, "currentReviewId" to 0, "replyContent" to "")
    }
    open var goBack = ::gen_goBack_fn
    open fun gen_goBack_fn() {
        uni_navigateBack(null)
    }
    open var goToProduct = ::gen_goToProduct_fn
    open fun gen_goToProduct_fn(productId: Number) {
        uni_navigateTo(NavigateToOptions(url = "/pages/product/detail?id=" + productId))
    }
    open fun loadSellerProducts(done: (() -> Unit)? = null) {
        getSellerProducts(ProductQueryParams(pageNum = 1, pageSize = 200, status = null, keyword = null, categoryId = null)).then(fun(res){
            val data = res.data as UTSJSONObject
            val records = data.getArray("records")
            val productList: UTSArray<UTSJSONObject> = utsArrayOf()
            val nameOptions: UTSArray<String> = utsArrayOf()
            if (records != null) {
                run {
                    var i: Number = 0
                    while(i < records.length){
                        val item = records[i] as UTSJSONObject
                        productList.push(item)
                        nameOptions.push(item.getString("productName") ?: "\u5546\u54C1" + (i + 1))
                        i++
                    }
                }
            }
            this.sellerProducts = productList
            this.productNameOptions = nameOptions
            if (this.productId < 0) {
                this.productId = 0
            }
            if (this.productId === 0) {
                this.productName = "全部商品"
            }
            if (done != null) {
                done()
            }
        }
        ).`catch`(fun(err){
            console.error("获取商家商品失败:", err, " at pages/seller/reviews.uvue:191")
            if (done != null) {
                done()
            }
        }
        )
    }
    open var getSelectedProductLabel = ::gen_getSelectedProductLabel_fn
    open fun gen_getSelectedProductLabel_fn(): String {
        if (this.productId <= 0) {
            return "请选择商品"
        }
        if (this.productName != "") {
            return this.productName
        }
        return "\u5546\u54C1ID: " + this.productId
    }
    open var selectProduct = ::gen_selectProduct_fn
    open fun gen_selectProduct_fn(id: Number, name: String) {
        if (id < 0 || this.productId === id) {
            return
        }
        this.productId = id
        this.productName = name
        this.handleSearch()
    }
    open var setFilter = ::gen_setFilter_fn
    open fun gen_setFilter_fn(rating: Number) {
        if (this.ratingFilter !== rating) {
            this.ratingFilter = rating
            this.handleSearch()
        }
    }
    open var handleSearch = ::gen_handleSearch_fn
    open fun gen_handleSearch_fn() {
        this.pageNum = 1
        this.reviews = utsArrayOf()
        this.hasMore = true
        if (this.productId === 0) {
            this.allReviewsPool = utsArrayOf<ReviewVO>()
            this.allModeLoaded = false
        }
        this.loadReviews()
    }
    open var toReviewVO = ::gen_toReviewVO_fn
    open fun gen_toReviewVO_fn(item: UTSJSONObject): ReviewVO {
        return ReviewVO(id = (item.getNumber("id") ?: 0).toInt(), productId = (item.getNumber("productId") ?: 0).toInt(), productName = item.getString("productName") ?: "", productMainImage = item.getString("productMainImage") ?: "", userId = (item.getNumber("userId") ?: 0).toInt(), userNickname = item.getString("userNickname") ?: "", userAvatar = item.getString("userAvatar") ?: "", orderId = (item.getNumber("orderId") ?: 0).toInt(), rating = (item.getNumber("rating") ?: 5).toInt(), content = item.getString("content") ?: "", images = item.getString("images") ?: "", replyContent = item.getString("replyContent"), replyTime = item.getString("replyTime"), status = (item.getNumber("status") ?: 1).toInt(), createTime = item.getString("createTime") ?: "")
    }
    open var applyClientFilters = ::gen_applyClientFilters_fn
    open fun gen_applyClientFilters_fn(source: UTSArray<ReviewVO>): UTSArray<ReviewVO> {
        var list = source
        if (this.ratingFilter !== 0) {
            list = list.filter(fun(item): Boolean {
                return item.rating === this.ratingFilter
            }
            )
        }
        if (this.searchKeyword != "") {
            val keyword = this.searchKeyword.toLowerCase()
            list = list.filter(fun(item): Boolean {
                return item.productName.toLowerCase().includes(keyword) || item.content.toLowerCase().includes(keyword)
            }
            )
        }
        return list
    }
    open var loadReviews = ::gen_loadReviews_fn
    open fun gen_loadReviews_fn() {
        if (this.isLoading || !this.hasMore) {
            return
        }
        if (this.productId === 0) {
            this.loadAllProductReviews()
            return
        }
        this.loadSingleProductReviews()
    }
    open var loadSingleProductReviews = ::gen_loadSingleProductReviews_fn
    open fun gen_loadSingleProductReviews_fn() {
        if (this.productId < 0) {
            return
        }
        this.isLoading = true
        getSellerReviews(this.productId, this.pageNum, this.pageSize, if (this.ratingFilter === 0) {
            null
        } else {
            this.ratingFilter
        }
        ).then(fun(res){
            val data = res.data as UTSJSONObject
            val records = data.getArray("records")
            val total = (data.getNumber("total") ?: 0).toInt()
            val newReviews: UTSArray<ReviewVO> = utsArrayOf()
            if (records != null) {
                run {
                    var i: Number = 0
                    while(i < records.length){
                        newReviews.push(this.toReviewVO(records[i] as UTSJSONObject))
                        i++
                    }
                }
            }
            val filteredReviews = this.applyClientFilters(newReviews)
            this.reviews = if (this.pageNum === 1) {
                filteredReviews
            } else {
                this.reviews.concat(filteredReviews)
            }
            this.hasMore = this.reviews.length < total && newReviews.length > 0
            this.pageNum++
            this.isLoading = false
        }
        ).`catch`(fun(err){
            console.error("获取评价列表失败:", err, " at pages/seller/reviews.uvue:281")
            this.isLoading = false
            if (this.pageNum === 1) {
                uni_showToast(ShowToastOptions(title = "加载失败", icon = "none"))
            }
        }
        )
    }
    open var loadAllProductReviews = ::gen_loadAllProductReviews_fn
    open fun gen_loadAllProductReviews_fn() {
        if (this.sellerProducts.length === 0) {
            this.reviews = utsArrayOf<ReviewVO>()
            this.hasMore = false
            return
        }
        if (this.allModeLoaded) {
            val start = (this.pageNum - 1) * this.pageSize
            val end = start + this.pageSize
            val filtered = this.applyClientFilters(this.allReviewsPool)
            this.reviews = filtered.slice(0, end)
            this.hasMore = end < filtered.length
            this.pageNum++
            return
        }
        this.isLoading = true
        val merged: UTSArray<ReviewVO> = utsArrayOf()
        val productIds: UTSArray<Number> = utsArrayOf()
        run {
            var i: Number = 0
            while(i < this.sellerProducts.length){
                val p = this.sellerProducts[i]
                val id = (p.getNumber("id") ?: 0).toInt()
                if (id > 0) {
                    productIds.push(id)
                }
                i++
            }
        }
        val finishAll = fun(){
            merged.sort(fun(a, b): Number {
                if (a.createTime == b.createTime) {
                    return 0
                }
                return if (a.createTime > b.createTime) {
                    -1
                } else {
                    1
                }
            }
            )
            this.allReviewsPool = merged
            this.allModeLoaded = true
            val filtered = this.applyClientFilters(this.allReviewsPool)
            this.reviews = filtered.slice(0, this.pageSize)
            this.hasMore = this.pageSize < filtered.length
            this.pageNum = 2
            this.isLoading = false
        }
        val self = this
        fun fetchByIndex(idx: Number) {
            if (idx >= productIds.length) {
                finishAll()
                return
            }
            val id = productIds[idx]
            getSellerReviews(id, 1, 50, null).then(fun(res){
                val data = res.data as UTSJSONObject
                val records = data.getArray("records")
                if (records != null) {
                    run {
                        var j: Number = 0
                        while(j < records.length){
                            merged.push(self.toReviewVO(records[j] as UTSJSONObject))
                            j++
                        }
                    }
                }
                fetchByIndex(idx + 1)
            }
            ).`catch`(fun(err){
                console.error("获取商品评价失败:", err, " at pages/seller/reviews.uvue:343")
                fetchByIndex(idx + 1)
            }
            )
        }
        fetchByIndex(0)
    }
    open var loadMore = ::gen_loadMore_fn
    open fun gen_loadMore_fn() {
        this.loadReviews()
    }
    open var getImages = ::gen_getImages_fn
    open fun gen_getImages_fn(imagesStr: String): UTSArray<String> {
        if (imagesStr == null || imagesStr == "") {
            return utsArrayOf<String>()
        }
        try {
            val arr = UTSAndroid.consoleDebugError(JSON.parse(imagesStr), " at pages/seller/reviews.uvue:356")
            if (UTSArray.isArray(arr)) {
                return arr as UTSArray<String>
            }
        }
         catch (e: Throwable) {
            if (imagesStr.includes(",")) {
                return imagesStr.split(",")
            }
        }
        return utsArrayOf<String>(imagesStr)
    }
    open var previewImage = ::gen_previewImage_fn
    open fun gen_previewImage_fn(urls: UTSArray<String>, current: Number) {
        uni_previewImage(PreviewImageOptions(urls = urls, current = current))
    }
    open var openReplyModal = ::gen_openReplyModal_fn
    open fun gen_openReplyModal_fn(item: ReviewVO) {
        this.currentReviewId = item.id
        this.replyContent = ""
        this.showReplyModal = true
    }
    open var closeReplyModal = ::gen_closeReplyModal_fn
    open fun gen_closeReplyModal_fn() {
        this.showReplyModal = false
    }
    open var submitReply = ::gen_submitReply_fn
    open fun gen_submitReply_fn() {
        if (this.replyContent.trim() == "") {
            uni_showToast(ShowToastOptions(title = "请输入回复内容", icon = "none"))
            return
        }
        uni_showLoading(ShowLoadingOptions(title = "提交中"))
        replyReview(this.currentReviewId, this.replyContent).then(fun(_){
            uni_hideLoading()
            uni_showToast(ShowToastOptions(title = "回复成功", icon = "success"))
            val review = this.reviews.find(fun(item): Boolean {
                return item.id === this.currentReviewId
            }
            )
            if (review != null) {
                review.replyContent = this.replyContent
                val now = Date()
                review.replyTime = "" + now.getFullYear() + "-" + (now.getMonth() + 1).toString(10).padStart(2, "0") + "-" + now.getDate().toString(10).padStart(2, "0") + " " + now.getHours().toString(10).padStart(2, "0") + ":" + now.getMinutes().toString(10).padStart(2, "0")
            }
            this.closeReplyModal()
        }
        ).`catch`(fun(err){
            uni_hideLoading()
            console.error("回复失败:", err, " at pages/seller/reviews.uvue:404")
            uni_showToast(ShowToastOptions(title = "回复失败", icon = "none"))
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
                return utsMapOf("page-container" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "column", "flex" to 1, "backgroundColor" to "#f7f8fa")), "product-tip" to padStyleMapOf(utsMapOf("marginTop" to 12, "marginRight" to 12, "marginBottom" to 8, "marginLeft" to 12, "paddingTop" to 12, "paddingRight" to 14, "paddingBottom" to 12, "paddingLeft" to 14, "backgroundImage" to "linear-gradient(135deg, #eef5ff 0%, #f7faff 100%)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#dbe9ff", "borderRightColor" to "#dbe9ff", "borderBottomColor" to "#dbe9ff", "borderLeftColor" to "#dbe9ff", "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10)), "product-tip-text" to padStyleMapOf(utsMapOf("fontSize" to 13, "color" to "#2d5fa8")), "product-tabs" to padStyleMapOf(utsMapOf("height" to 44, "paddingTop" to 0, "paddingRight" to 12, "paddingBottom" to 10, "paddingLeft" to 12)), "product-tabs-row" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "height" to 34, "width" to "auto")), "product-tab-item" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center", "height" to 34, "paddingTop" to 0, "paddingRight" to 14, "paddingBottom" to 0, "paddingLeft" to 14, "marginRight" to 8, "backgroundColor" to "#ffffff", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#e8ecf3", "borderRightColor" to "#e8ecf3", "borderBottomColor" to "#e8ecf3", "borderLeftColor" to "#e8ecf3", "borderTopLeftRadius" to 17, "borderTopRightRadius" to 17, "borderBottomRightRadius" to 17, "borderBottomLeftRadius" to 17, "flexShrink" to 0)), "product-tab-item-active" to padStyleMapOf(utsMapOf("backgroundImage" to "linear-gradient(135deg, #2f80ff 0%, #1d6df2 100%)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopColor" to "#2f80ff", "borderRightColor" to "#2f80ff", "borderBottomColor" to "#2f80ff", "borderLeftColor" to "#2f80ff", "boxShadow" to "0 4px 10px rgba(47, 128, 255, 0.2)")), "product-tab-text" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#4f5d75")), "product-tab-text-active" to padStyleMapOf(utsMapOf("color" to "#ffffff", "fontWeight" to "bold")), "summary-row" to padStyleMapOf(utsMapOf("paddingTop" to 6, "paddingRight" to 16, "paddingBottom" to 8, "paddingLeft" to 16)), "summary-text" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#8a94a6")), "nav-bar" to padStyleMapOf(utsMapOf("height" to 44, "backgroundColor" to "#ffffff", "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "paddingTop" to CSS_VAR_STATUS_BAR_HEIGHT, "paddingRight" to 16, "paddingBottom" to 0, "paddingLeft" to 16, "boxShadow" to "0 1px 4px rgba(0, 0, 0, 0.05)", "zIndex" to 100)), "nav-back" to padStyleMapOf(utsMapOf("width" to 40, "height" to 44, "display" to "flex", "alignItems" to "center")), "back-icon" to padStyleMapOf(utsMapOf("fontSize" to 28, "color" to "#333333")), "nav-title" to padStyleMapOf(utsMapOf("fontSize" to 16, "fontWeight" to "bold", "color" to "#333333")), "nav-placeholder" to padStyleMapOf(utsMapOf("width" to 40)), "search-bar" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "marginTop" to 0, "marginRight" to 12, "marginBottom" to 0, "marginLeft" to 12, "paddingTop" to 10, "paddingRight" to 12, "paddingBottom" to 10, "paddingLeft" to 12, "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "boxShadow" to "0 4px 12px rgba(0, 0, 0, 0.03)")), "search-icon" to padStyleMapOf(utsMapOf("fontSize" to 16, "color" to "#8b95a7", "marginRight" to 8)), "search-input" to padStyleMapOf(utsMapOf("flex" to 1, "height" to 36, "backgroundColor" to "#f4f7fc", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "paddingTop" to 0, "paddingRight" to 16, "paddingBottom" to 0, "paddingLeft" to 16, "fontSize" to 14)), "filter-bar" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "backgroundColor" to "#ffffff", "marginTop" to 10, "marginRight" to 12, "marginBottom" to 0, "marginLeft" to 12, "paddingTop" to 10, "paddingRight" to 12, "paddingBottom" to 10, "paddingLeft" to 12, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "boxShadow" to "0 4px 12px rgba(0, 0, 0, 0.03)")), "filter-item" to padStyleMapOf(utsMapOf("paddingTop" to 5, "paddingRight" to 12, "paddingBottom" to 5, "paddingLeft" to 12, "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "backgroundColor" to "#f2f4f8", "marginRight" to 10)), "filter-item-text" to padStyleMapOf(utsMapOf("fontSize" to 13, "color" to "#667085")), "filter-item-active" to padStyleMapOf(utsMapOf("backgroundColor" to "#e8f1ff")), "filter-item-active-text" to padStyleMapOf(utsMapOf("color" to "#1d6df2", "fontWeight" to "bold")), "review-list" to padStyleMapOf(utsMapOf("flex" to 1, "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12)), "review-card" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "paddingTop" to 14, "paddingRight" to 14, "paddingBottom" to 14, "paddingLeft" to 14, "marginBottom" to 12, "boxShadow" to "0 6px 16px rgba(17, 24, 39, 0.05)", "display" to "flex", "flexDirection" to "column", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#edf1f7", "borderRightColor" to "#edf1f7", "borderBottomColor" to "#edf1f7", "borderLeftColor" to "#edf1f7")), "product-info" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "backgroundColor" to "#f8fafc", "paddingTop" to 8, "paddingRight" to 8, "paddingBottom" to 8, "paddingLeft" to 8, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "marginBottom" to 14)), "product-img" to padStyleMapOf(utsMapOf("width" to 40, "height" to 40, "borderTopLeftRadius" to 6, "borderTopRightRadius" to 6, "borderBottomRightRadius" to 6, "borderBottomLeftRadius" to 6, "marginRight" to 8, "backgroundColor" to "#eeeeee")), "product-name" to padStyleMapOf(utsMapOf("flex" to 1, "fontSize" to 13, "color" to "#4b5565", "whiteSpace" to "nowrap", "overflow" to "hidden", "textOverflow" to "ellipsis")), "review-header" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "marginBottom" to 12)), "user-info" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center")), "avatar" to padStyleMapOf(utsMapOf("width" to 36, "height" to 36, "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "backgroundColor" to "#eeeeee", "marginRight" to 10)), "user-meta" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "column")), "nickname" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#333333", "fontWeight" to "bold")), "review-time" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999", "marginTop" to 2)), "rating-stars" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row")), "star-icon" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#eeeeee", "marginRight" to 2)), "star-icon-active" to padStyleMapOf(utsMapOf("color" to "#ff9900")), "review-content" to padStyleMapOf(utsMapOf("marginBottom" to 12, "display" to "flex", "flexDirection" to "column")), "content-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#333333", "lineHeight" to 1.5, "marginBottom" to 10)), "image-list" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "flexWrap" to "wrap")), "review-img" to padStyleMapOf(utsMapOf("width" to 80, "height" to 80, "borderTopLeftRadius" to 6, "borderTopRightRadius" to 6, "borderBottomRightRadius" to 6, "borderBottomLeftRadius" to 6, "marginRight" to 8, "marginBottom" to 8, "backgroundColor" to "#f5f5f5")), "reply-box" to padStyleMapOf(utsMapOf("backgroundColor" to "#f8f8f8", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "marginTop" to 12, "display" to "flex", "flexDirection" to "column")), "reply-label" to padStyleMapOf(utsMapOf("fontSize" to 13, "color" to "#0066CC", "fontWeight" to "bold", "marginBottom" to 6)), "reply-text" to padStyleMapOf(utsMapOf("fontSize" to 13, "color" to "#666666", "lineHeight" to 1.4, "marginBottom" to 6)), "reply-time" to padStyleMapOf(utsMapOf("fontSize" to 11, "color" to "#999999")), "action-bar" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "flex-end", "marginTop" to 12, "paddingTop" to 12, "borderTopWidth" to 1, "borderTopStyle" to "solid", "borderTopColor" to "#f5f5f5")), "reply-btn" to padStyleMapOf(utsMapOf("marginTop" to 0, "marginRight" to 0, "marginBottom" to 0, "marginLeft" to 0, "paddingTop" to 0, "paddingRight" to 16, "paddingBottom" to 0, "paddingLeft" to 16, "height" to 30, "lineHeight" to "30px", "fontSize" to 13, "backgroundColor" to "#ffffff", "color" to "#0066CC", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#0066CC", "borderRightColor" to "#0066CC", "borderBottomColor" to "#0066CC", "borderLeftColor" to "#0066CC", "borderTopLeftRadius" to 15, "borderTopRightRadius" to 15, "borderBottomRightRadius" to 15, "borderBottomLeftRadius" to 15)), "empty-state" to padStyleMapOf(utsMapOf("paddingTop" to 30, "paddingRight" to 30, "paddingBottom" to 30, "paddingLeft" to 30, "display" to "flex", "flexDirection" to "column", "alignItems" to "center", "justifyContent" to "center")), "loading-more" to padStyleMapOf(utsMapOf("paddingTop" to 30, "paddingRight" to 30, "paddingBottom" to 30, "paddingLeft" to 30, "display" to "flex", "flexDirection" to "column", "alignItems" to "center", "justifyContent" to "center")), "no-more" to padStyleMapOf(utsMapOf("paddingTop" to 30, "paddingRight" to 30, "paddingBottom" to 30, "paddingLeft" to 30, "display" to "flex", "flexDirection" to "column", "alignItems" to "center", "justifyContent" to "center")), "empty-icon" to padStyleMapOf(utsMapOf("fontSize" to 48, "marginBottom" to 16)), "empty-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#999999")), "loading-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#999999")), "no-more-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#999999")), "modal-mask" to padStyleMapOf(utsMapOf("position" to "fixed", "top" to 0, "right" to 0, "bottom" to 0, "left" to 0, "backgroundColor" to "rgba(0,0,0,0.5)", "zIndex" to 1000)), "reply-modal" to padStyleMapOf(utsMapOf("position" to "fixed", "left" to "50%", "top" to "50%", "transform" to "translate(-50%, -50%)", "width" to 300, "backgroundColor" to "#ffffff", "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "zIndex" to 1001, "display" to "flex", "flexDirection" to "column")), "modal-header" to padStyleMapOf(utsMapOf("paddingTop" to 16, "paddingRight" to 20, "paddingBottom" to 16, "paddingLeft" to 20, "display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "#f5f5f5")), "modal-title" to padStyleMapOf(utsMapOf("fontSize" to 16, "fontWeight" to "bold", "color" to "#333333")), "close-icon" to padStyleMapOf(utsMapOf("fontSize" to 24, "color" to "#999999", "paddingTop" to 0, "paddingRight" to 5, "paddingBottom" to 0, "paddingLeft" to 5)), "modal-content" to padStyleMapOf(utsMapOf("paddingTop" to 20, "paddingRight" to 20, "paddingBottom" to 20, "paddingLeft" to 20, "display" to "flex", "flexDirection" to "column")), "reply-input" to padStyleMapOf(utsMapOf("width" to "100%", "height" to 120, "backgroundColor" to "#f5f5f5", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "fontSize" to 14, "boxSizing" to "border-box")), "modal-footer" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "paddingTop" to 0, "paddingRight" to 0, "paddingBottom" to 0, "paddingLeft" to 0, "borderTopWidth" to 1, "borderTopStyle" to "solid", "borderTopColor" to "#eeeeee")), "btn" to padStyleMapOf(utsMapOf("flex" to 1, "height" to 50, "lineHeight" to "50px", "textAlign" to "center", "fontSize" to 16, "backgroundColor" to "rgba(0,0,0,0)", "borderTopLeftRadius" to 0, "borderTopRightRadius" to 0, "borderBottomRightRadius" to 0, "borderBottomLeftRadius" to 0, "marginTop" to 0, "marginRight" to 0, "marginBottom" to 0, "marginLeft" to 0)), "btn-cancel" to padStyleMapOf(utsMapOf("color" to "#666666", "borderRightWidth" to 1, "borderRightStyle" to "solid", "borderRightColor" to "#eeeeee")), "btn-confirm" to padStyleMapOf(utsMapOf("color" to "#0066CC", "fontWeight" to "bold")), "btn-confirm-disabled" to padStyleMapOf(utsMapOf("color" to "#cccccc")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = utsMapOf()
        var emits: Map<String, Any?> = utsMapOf()
        var props = normalizePropsOptions(utsMapOf())
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf()
        var components: Map<String, CreateVueComponent> = utsMapOf()
    }
}
