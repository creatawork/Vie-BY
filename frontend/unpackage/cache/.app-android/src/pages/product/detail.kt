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
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.previewImage as uni_previewImage
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesProductDetail : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onLoad(fun(options: Any) {
            val opts = options as UTSJSONObject
            val queryId = opts.get("id")
            if (queryId != null) {
                val idStr = queryId as String
                val idNum = parseInt(idStr)
                this.productId = if (idNum > 0) {
                    idNum
                } else {
                    1
                }
            }
            this.loadProductDetail()
            this.loadProductReviews()
            this.recordBrowseHistory()
            this.checkCollectionStatus()
            this.initDefaultSpecs()
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return createElementVNode("view", utsMapOf("class" to "product-detail-page"), utsArrayOf(
            createElementVNode("scroll-view", utsMapOf("scroll-y" to "true", "class" to "product-detail__scroll"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "product-detail__carousel"), utsArrayOf(
                    if (_ctx.productImages.length === 0) {
                        createElementVNode("view", utsMapOf("key" to 0, "class" to "product-detail__loading"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "loading-text"), "加载中...")
                        ))
                    } else {
                        createElementVNode("swiper", utsMapOf("key" to 1, "current" to _ctx.currentImageIndex, "class" to "product-detail__swiper", "circular" to false, "autoplay" to false, "interval" to 4000, "duration" to 500, "onChange" to _ctx.handleImageChange), utsArrayOf(
                            createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.productImages, fun(image, index, __index, _cached): Any {
                                return createElementVNode("swiper-item", utsMapOf("key" to image, "class" to "product-detail__swiper-item", "onClick" to fun(){
                                    _ctx.previewImage(index)
                                }
                                ), utsArrayOf(
                                    createElementVNode("image", utsMapOf("src" to image, "class" to "product-detail__image", "mode" to "aspectFill", "show-menu-by-longpress" to true), null, 8, utsArrayOf(
                                        "src"
                                    ))
                                ), 8, utsArrayOf(
                                    "onClick"
                                ))
                            }
                            ), 128)
                        ), 40, utsArrayOf(
                            "current",
                            "onChange"
                        ))
                    }
                    ,
                    if (_ctx.productImages.length > 0) {
                        createElementVNode("view", utsMapOf("key" to 2, "class" to "product-detail__image-indicator"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "product-detail__indicator-text"), toDisplayString(_ctx.currentImageIndex + 1) + " / " + toDisplayString(_ctx.productImages.length), 1)
                        ))
                    } else {
                        createCommentVNode("v-if", true)
                    }
                )),
                createElementVNode("view", utsMapOf("class" to "product-detail__info-section"), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "product-detail__price-row"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "product-detail__price-group"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "product-detail__currency"), "¥"),
                            createElementVNode("text", utsMapOf("class" to "product-detail__current-price"), toDisplayString(_ctx.product.price), 1),
                            if (isTrue(_ctx.product.originalPrice)) {
                                createElementVNode("text", utsMapOf("key" to 0, "class" to "product-detail__original-price"), " ¥" + toDisplayString(_ctx.product.originalPrice), 1)
                            } else {
                                createCommentVNode("v-if", true)
                            }
                        )),
                        createElementVNode("text", utsMapOf("class" to "product-detail__sales"), " 已售 " + toDisplayString(_ctx.product.sales) + " 件 ", 1)
                    )),
                    if (isTrue(_ctx.product.isNew || _ctx.product.discount || _ctx.product.isFresh)) {
                        createElementVNode("view", utsMapOf("key" to 0, "class" to "product-detail__tags-row"), utsArrayOf(
                            if (isTrue(_ctx.product.discount)) {
                                createElementVNode("view", utsMapOf("key" to 0, "class" to "product-detail__tag product-detail__tag--discount"), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "product-detail__tag-text product-detail__tag-text--discount"), "省 ¥" + toDisplayString(_ctx.getDiscountAmount()), 1)
                                ))
                            } else {
                                createCommentVNode("v-if", true)
                            },
                            if (isTrue(_ctx.product.isNew)) {
                                createElementVNode("view", utsMapOf("key" to 1, "class" to "product-detail__tag product-detail__tag--new"), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "product-detail__tag-text product-detail__tag-text--new"), "新品")
                                ))
                            } else {
                                createCommentVNode("v-if", true)
                            },
                            if (isTrue(_ctx.product.isFresh)) {
                                createElementVNode("view", utsMapOf("key" to 2, "class" to "product-detail__tag product-detail__tag--fresh"), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "product-detail__tag-text product-detail__tag-text--fresh"), "新鲜")
                                ))
                            } else {
                                createCommentVNode("v-if", true)
                            }
                        ))
                    } else {
                        createCommentVNode("v-if", true)
                    }
                    ,
                    createElementVNode("text", utsMapOf("class" to "product-detail__name"), toDisplayString(_ctx.product.name), 1),
                    if (isTrue(_ctx.product.description)) {
                        createElementVNode("text", utsMapOf("key" to 1, "class" to "product-detail__brief"), toDisplayString(_ctx.product.description), 1)
                    } else {
                        createCommentVNode("v-if", true)
                    }
                    ,
                    createElementVNode("view", utsMapOf("class" to "product-detail__meta-row"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "product-detail__rating"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "rating-star"), "⭐"),
                            createElementVNode("text", utsMapOf("class" to "rating-score"), toDisplayString(_ctx.product.rating), 1),
                            createElementVNode("text", utsMapOf("class" to "rating-count"), "(" + toDisplayString(_ctx.product.reviewCount) + "条评价)", 1)
                        )),
                        createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                            "product-detail__stock-status",
                            utsMapOf("product-detail__stock-status--low" to (_ctx.product.stock < 10))
                        ))), toDisplayString(if (_ctx.product.stock > 0) {
                            "\u5E93\u5B58: " + _ctx.product.stock
                        } else {
                            "暂时缺货"
                        }
                        ), 3)
                    ))
                )),
                if (isTrue(_ctx.hasSpecs())) {
                    createElementVNode("view", utsMapOf("key" to 0, "class" to "product-detail__section"), utsArrayOf(
                        createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.product.specs, fun(spec, __key, __index, _cached): Any {
                            return createElementVNode("view", utsMapOf("key" to spec.id, "class" to "product-detail__spec-item"), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "product-detail__spec-label"), toDisplayString(spec.name), 1),
                                createElementVNode("view", utsMapOf("class" to "product-detail__spec-options"), utsArrayOf(
                                    createElementVNode(Fragment, null, RenderHelpers.renderList(spec.options, fun(option, __key, __index, _cached): Any {
                                        return createElementVNode("view", utsMapOf("key" to option, "class" to normalizeClass(utsArrayOf(
                                            "product-detail__spec-option",
                                            utsMapOf("product-detail__spec-option--selected" to _ctx.isSpecSelected(spec.id, option))
                                        )), "onClick" to fun(){
                                            _ctx.selectSpec(spec.id, option)
                                        }), utsArrayOf(
                                            createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                                                "product-detail__spec-text",
                                                utsMapOf("product-detail__spec-text--selected" to _ctx.isSpecSelected(spec.id, option))
                                            ))), toDisplayString(option), 3)
                                        ), 10, utsArrayOf(
                                            "onClick"
                                        ))
                                    }), 128)
                                ))
                            ))
                        }), 128),
                        createElementVNode("view", utsMapOf("class" to "product-detail__quantity-row"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "product-detail__spec-label"), "数量"),
                            createElementVNode("view", utsMapOf("class" to "product-detail__quantity-control"), utsArrayOf(
                                createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                                    "product-detail__quantity-btn",
                                    utsMapOf("product-detail__quantity-btn--disabled" to (_ctx.quantity <= 1))
                                )), "onClick" to _ctx.decreaseQuantity), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "product-detail__quantity-btn-text"), "－")
                                ), 10, utsArrayOf(
                                    "onClick"
                                )),
                                createElementVNode("view", utsMapOf("class" to "product-detail__quantity-input"), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "product-detail__quantity-input-text"), toDisplayString(_ctx.quantity), 1)
                                )),
                                createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                                    "product-detail__quantity-btn",
                                    utsMapOf("product-detail__quantity-btn--disabled" to (_ctx.quantity >= _ctx.product.stock))
                                )), "onClick" to _ctx.increaseQuantity), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "product-detail__quantity-btn-text"), "＋")
                                ), 10, utsArrayOf(
                                    "onClick"
                                ))
                            ))
                        ))
                    ))
                } else {
                    createCommentVNode("v-if", true)
                }
                ,
                createElementVNode("view", utsMapOf("class" to "product-detail__section product-detail__reviews-section"), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "product-detail__section-header", "onClick" to _ctx.viewAllReviews), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "product-detail__section-title-box"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "product-detail__section-title"), "用户评价"),
                            createElementVNode("text", utsMapOf("class" to "title-count"), "(" + toDisplayString(_ctx.product.reviewCount) + ")", 1)
                        )),
                        createElementVNode("view", utsMapOf("class" to "product-detail__section-more"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "product-detail__section-more-text"), "好评度 " + toDisplayString((_ctx.product.rating / 5 * 100).toFixed(0)) + "%", 1),
                            createElementVNode("text", utsMapOf("class" to "arrow-right"), ">")
                        ))
                    ), 8, utsArrayOf(
                        "onClick"
                    )),
                    if (_ctx.reviews.length > 0) {
                        createElementVNode("view", utsMapOf("key" to 0, "class" to "product-detail__review-list"), utsArrayOf(
                            createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.reviews.slice(0, 2), fun(review, __key, __index, _cached): Any {
                                return createElementVNode("view", utsMapOf("key" to review.id, "class" to "product-detail__review-item"), utsArrayOf(
                                    createElementVNode("view", utsMapOf("class" to "product-detail__review-header"), utsArrayOf(
                                        createElementVNode("view", utsMapOf("class" to "product-detail__review-user"), utsArrayOf(
                                            createElementVNode("image", utsMapOf("src" to review.userAvatar, "class" to "product-detail__review-avatar"), null, 8, utsArrayOf(
                                                "src"
                                            )),
                                            createElementVNode("text", utsMapOf("class" to "product-detail__review-username"), toDisplayString(review.userNickname), 1),
                                            createElementVNode("view", utsMapOf("class" to "product-detail__review-rating-stars"), utsArrayOf(
                                                createElementVNode(Fragment, null, RenderHelpers.renderList(5, fun(i, __key, __index, _cached): Any {
                                                    return createElementVNode("text", utsMapOf("key" to i, "class" to normalizeClass(utsArrayOf(
                                                        "star",
                                                        utsMapOf("star-active" to (i <= review.rating))
                                                    ))), "★", 2)
                                                }), 64)
                                            ))
                                        )),
                                        createElementVNode("text", utsMapOf("class" to "product-detail__review-date"), toDisplayString(review.createTime), 1)
                                    )),
                                    createElementVNode("text", utsMapOf("class" to "product-detail__review-content"), toDisplayString(review.content), 1),
                                    if (isTrue(review.replyContent)) {
                                        createElementVNode("view", utsMapOf("key" to 0, "class" to "product-detail__review-reply"), utsArrayOf(
                                            createElementVNode("text", utsMapOf("class" to "reply-label"), "商家回复："),
                                            createElementVNode("text", utsMapOf("class" to "reply-content"), toDisplayString(review.replyContent), 1)
                                        ))
                                    } else {
                                        createCommentVNode("v-if", true)
                                    }
                                ))
                            }), 128)
                        ))
                    } else {
                        createElementVNode("view", utsMapOf("key" to 1, "class" to "product-detail__empty-review"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "product-detail__empty-review-text"), "暂无评价")
                        ))
                    }
                )),
                createElementVNode("view", utsMapOf("class" to "product-detail__section product-detail__detail-section"), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "product-detail__section-title-simple"), "商品详情"),
                    createElementVNode("text", utsMapOf("class" to "product-detail__description"), toDisplayString(_ctx.product.description), 1)
                )),
                createElementVNode("view", utsMapOf("class" to "product-detail__safe-area-placeholder"))
            )),
            createElementVNode("view", utsMapOf("class" to "product-detail__footer"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "product-detail__footer-icons"), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "product-detail__icon-btn", "onClick" to _ctx.toggleFavorite), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "icon-text"), toDisplayString(if (_ctx.isFavorite) {
                            "❤️"
                        } else {
                            "🤍"
                        }
                        ), 1),
                        createElementVNode("text", utsMapOf("class" to "icon-label"), "收藏")
                    ), 8, utsArrayOf(
                        "onClick"
                    ))
                )),
                createElementVNode("view", utsMapOf("class" to "product-detail__footer-actions"), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "product-detail__action-btn product-detail__action-btn--cart", "onClick" to _ctx.handleAddToCart), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "product-detail__action-text"), "加入购物车")
                    ), 8, utsArrayOf(
                        "onClick"
                    )),
                    createElementVNode("view", utsMapOf("class" to "product-detail__action-btn product-detail__action-btn--buy", "onClick" to _ctx.buyNow), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "product-detail__action-text"), "立即购买")
                    ), 8, utsArrayOf(
                        "onClick"
                    ))
                ))
            ))
        ))
    }
    open var productId: Number by `$data`
    open var product: Product by `$data`
    open var productImages: UTSArray<String> by `$data`
    open var currentImageIndex: Number by `$data`
    open var selectedSpecs: Map<String, String> by `$data`
    open var quantity: Number by `$data`
    open var isFavorite: Boolean by `$data`
    open var skuList: UTSArray<ProductSku> by `$data`
    open var selectedSkuId: Number by `$data`
    open var reviews: UTSArray<ReviewVO> by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return utsMapOf("productId" to 0, "product" to Product(id = 0, name = "", description = "", price = 0, originalPrice = null, image = "", rating = 5, sales = 0, stock = 0, reviewCount = 0, isNew = false, discount = false, isFresh = false, specs = utsArrayOf<ProductSpec>(), detailImages = utsArrayOf<String>()), "productImages" to utsArrayOf<String>(), "currentImageIndex" to 0, "selectedSpecs" to Map<String, String>(), "quantity" to 1, "isFavorite" to false, "skuList" to utsArrayOf<ProductSku>(), "selectedSkuId" to 0, "reviews" to utsArrayOf<ReviewVO>())
    }
    open var getDiscountAmount = ::gen_getDiscountAmount_fn
    open fun gen_getDiscountAmount_fn(): String {
        val originalPrice = this.product.originalPrice
        if (originalPrice != null) {
            val discount = originalPrice - this.product.price
            return discount.toFixed(2)
        }
        return "0.00"
    }
    open var hasSpecs = ::gen_hasSpecs_fn
    open fun gen_hasSpecs_fn(): Boolean {
        val specs = this.product.specs
        return specs != null && specs.length > 0
    }
    open var recordBrowseHistory = ::gen_recordBrowseHistory_fn
    open fun gen_recordBrowseHistory_fn() {
        recordBrowse(this.productId).then(fun(){
            console.log("浏览历史记录成功", " at pages/product/detail.uvue:343")
        }
        ).`catch`(fun(err){
            console.error("记录浏览历史失败:", err, " at pages/product/detail.uvue:345")
        }
        )
    }
    open var loadProductReviews = ::gen_loadProductReviews_fn
    open fun gen_loadProductReviews_fn() {
        getProductReviews(this.productId, 1, 2).then(fun(res){
            val data = res.data as UTSJSONObject
            val records = data.getArray("records")
            if (records != null && records.length > 0) {
                val reviews: UTSArray<ReviewVO> = utsArrayOf()
                run {
                    var i: Number = 0
                    while(i < records.length){
                        val item = records[i] as UTSJSONObject
                        reviews.push(ReviewVO(id = (item.getNumber("id") ?: 0).toInt(), productId = (item.getNumber("productId") ?: 0).toInt(), productName = item.getString("productName") ?: "", productMainImage = item.getString("productMainImage") ?: "", userId = (item.getNumber("userId") ?: 0).toInt(), userNickname = item.getString("userNickname") ?: "", userAvatar = item.getString("userAvatar") ?: "", orderId = (item.getNumber("orderId") ?: 0).toInt(), rating = (item.getNumber("rating") ?: 5).toInt(), content = item.getString("content") ?: "", images = item.getString("images") ?: "", replyContent = item.getString("replyContent"), replyTime = item.getString("replyTime"), status = (item.getNumber("status") ?: 1).toInt(), createTime = item.getString("createTime") ?: ""))
                        i++
                    }
                }
                this.reviews = reviews
            }
        }
        ).`catch`(fun(err){
            console.error("获取商品评价失败:", err, " at pages/product/detail.uvue:381")
        }
        )
    }
    open var loadProductDetail = ::gen_loadProductDetail_fn
    open fun gen_loadProductDetail_fn() {
        getProductDetail(this.productId).then(fun(res){
            val data = res.data as UTSJSONObject
            this.product.id = (data.getNumber("id") ?: 0).toInt()
            this.product.name = data.getString("productName") ?: ""
            this.product.description = data.getString("description") ?: ""
            this.product.price = data.getNumber("currentPrice") ?: 0
            this.product.originalPrice = data.getNumber("originalPrice")
            this.product.image = data.getString("mainImage") ?: ""
            this.product.rating = data.getNumber("avgRating") ?: 5
            this.product.sales = (data.getNumber("salesVolume") ?: 0).toInt()
            this.product.stock = (data.getNumber("stock") ?: 0).toInt()
            this.product.reviewCount = (data.getNumber("reviewCount") ?: 0).toInt()
            val imagesArr = data.getArray("images")
            val imgs: UTSArray<String> = utsArrayOf()
            if (imagesArr != null && imagesArr.length > 0) {
                run {
                    var i: Number = 0
                    while(i < imagesArr.length){
                        val imgObj = imagesArr[i] as UTSJSONObject
                        val imgUrl = imgObj.getString("imageUrl")
                        if (imgUrl != null && imgUrl !== "") {
                            imgs.push(imgUrl)
                        }
                        i++
                    }
                }
            }
            if (imgs.length === 0) {
                val mainImage = data.getString("mainImage")
                if (mainImage != null && mainImage !== "") {
                    imgs.push(mainImage)
                }
            }
            if (imgs.length > 0) {
                this.productImages = imgs
                this.currentImageIndex = 0
                console.log("商品图片加载成功，共", imgs.length, "张", " at pages/product/detail.uvue:425")
                console.log("图片URL列表:", imgs, " at pages/product/detail.uvue:426")
            } else {
                console.log("警告：商品没有图片", " at pages/product/detail.uvue:428")
            }
            val skuArr = data.getArray("skuList")
            if (skuArr != null && skuArr.length > 0) {
                val skus: UTSArray<ProductSku> = utsArrayOf()
                val specOptions: UTSArray<String> = utsArrayOf()
                run {
                    var i: Number = 0
                    while(i < skuArr.length){
                        val skuObj = skuArr[i] as UTSJSONObject
                        val skuName = skuObj.getString("skuName") ?: ""
                        skus.push(ProductSku(id = (skuObj.getNumber("id") ?: 0).toInt(), productId = (skuObj.getNumber("productId") ?: 0).toInt(), skuName = skuName, skuCode = skuObj.getString("skuCode") ?: "", skuImage = skuObj.getString("skuImage"), price = skuObj.getNumber("price") ?: 0, stock = (skuObj.getNumber("stock") ?: 0).toInt(), salesVolume = (skuObj.getNumber("salesVolume") ?: 0).toInt(), specInfo = skuObj.getString("specInfo"), status = (skuObj.getNumber("status") ?: 1).toInt()))
                        if (skuName !== "") {
                            specOptions.push(skuName)
                        }
                        i++
                    }
                }
                this.skuList = skus
                if (specOptions.length > 0) {
                    this.product.specs = utsArrayOf(
                        ProductSpec(id = "sku", name = "规格", options = specOptions)
                    )
                    this.selectedSpecs.set("sku", specOptions[0])
                    this.selectedSkuId = skus[0].id
                    this.product.price = skus[0].price
                    this.product.stock = skus[0].stock
                }
            }
            console.log("商品详情加载成功:", this.product.name, "SKU数量:", this.skuList.length, " at pages/product/detail.uvue:475")
        }
        ).`catch`(fun(err){
            console.error("获取商品详情失败:", err, " at pages/product/detail.uvue:477")
        }
        )
    }
    open var initDefaultSpecs = ::gen_initDefaultSpecs_fn
    open fun gen_initDefaultSpecs_fn() {
        val specs = this.product.specs
        if (specs != null) {
            specs.forEach(fun(spec: ProductSpec){
                if (spec.options.length > 0) {
                    this.selectedSpecs.set(spec.id, spec.options[0])
                }
            }
            )
        }
    }
    open var isSpecSelected = ::gen_isSpecSelected_fn
    open fun gen_isSpecSelected_fn(specId: String, option: String): Boolean {
        return this.selectedSpecs.get(specId) === option
    }
    open var handleImageChange = ::gen_handleImageChange_fn
    open fun gen_handleImageChange_fn(e: UniSwiperChangeEvent) {
        this.currentImageIndex = e.detail.current
    }
    open var previewImage = ::gen_previewImage_fn
    open fun gen_previewImage_fn(index: Number) {
        uni_previewImage(PreviewImageOptions(urls = this.productImages, current = index))
    }
    open var previewDetailImage = ::gen_previewDetailImage_fn
    open fun gen_previewDetailImage_fn(index: Number) {
        val detailImages = this.product.detailImages
        if (detailImages != null) {
            uni_previewImage(PreviewImageOptions(urls = detailImages, current = index))
        }
    }
    open var selectSpec = ::gen_selectSpec_fn
    open fun gen_selectSpec_fn(specId: String, option: String) {
        this.selectedSpecs.set(specId, option)
        if (specId === "sku") {
            val selectedSku = this.skuList.find(fun(item: ProductSku): Boolean {
                return item.skuName === option
            }
            )
            if (selectedSku != null) {
                this.selectedSkuId = selectedSku.id
                this.product.price = selectedSku.price
                this.product.stock = selectedSku.stock
                if (this.quantity > selectedSku.stock) {
                    this.quantity = Math.max(1, selectedSku.stock)
                }
            }
        }
    }
    open var increaseQuantity = ::gen_increaseQuantity_fn
    open fun gen_increaseQuantity_fn() {
        if (this.quantity < this.product.stock) {
            this.quantity++
        }
    }
    open var decreaseQuantity = ::gen_decreaseQuantity_fn
    open fun gen_decreaseQuantity_fn() {
        if (this.quantity > 1) {
            this.quantity--
        }
    }
    open var checkCollectionStatus = ::gen_checkCollectionStatus_fn
    open fun gen_checkCollectionStatus_fn() {
        isProductCollected(this.productId).then(fun(result){
            if (result.code === 200) {
                this.isFavorite = result.data as Boolean
            }
        }
        ).`catch`(fun(error){
            console.error("检查收藏状态失败:", error, " at pages/product/detail.uvue:567")
        }
        )
    }
    open var toggleFavorite = ::gen_toggleFavorite_fn
    open fun gen_toggleFavorite_fn() {
        val targetStatus = !this.isFavorite
        val apiCall = if (targetStatus) {
            collectProduct(this.productId)
        } else {
            uncollectProduct(this.productId)
        }
        apiCall.then(fun(result){
            if (result.code === 200) {
                this.isFavorite = targetStatus
                uni_showToast(ShowToastOptions(title = if (targetStatus) {
                    "已收藏"
                } else {
                    "已取消收藏"
                }, icon = "success"))
                uni__emit("refreshFavorites", null)
            } else {
                uni_showToast(ShowToastOptions(title = result.message ?: "操作失败", icon = "none"))
            }
        }
        ).`catch`(fun(error){
            console.error("收藏操作失败:", error, " at pages/product/detail.uvue:594")
            uni_showToast(ShowToastOptions(title = "操作失败，请重试", icon = "none"))
        }
        )
    }
    open var getSelectedSpecName = ::gen_getSelectedSpecName_fn
    open fun gen_getSelectedSpecName_fn(): String {
        val specs = this.product.specs
        if (specs == null || specs.length === 0) {
            return ""
        }
        val specNames: UTSArray<String> = utsArrayOf()
        specs.forEach(fun(spec: ProductSpec){
            val selected = this.selectedSpecs.get(spec.id)
            if (selected != null) {
                specNames.push(selected)
            }
        }
        )
        return specNames.join(" ")
    }
    open var handleAddToCart = ::gen_handleAddToCart_fn
    open fun gen_handleAddToCart_fn() {
        val skuId = if (this.selectedSkuId > 0) {
            this.selectedSkuId
        } else {
            this.productId
        }
        val params = CartAddParams(productId = this.productId, skuId = skuId, quantity = this.quantity)
        console.log("添加购物车参数:", JSON.stringify(params), " at pages/product/detail.uvue:627")
        addToCart(params).then(fun(){
            uni_showToast(ShowToastOptions(title = "已添加到购物车", icon = "success", duration = 1500))
            uni__emit("bottomNavRefresh", null)
        }
        ).`catch`(fun(err){
            console.error("添加购物车失败:", err, " at pages/product/detail.uvue:637")
            uni_showToast(ShowToastOptions(title = "添加失败", icon = "none"))
        }
        )
    }
    open var buyNow = ::gen_buyNow_fn
    open fun gen_buyNow_fn() {
        val skuId = if (this.selectedSkuId > 0) {
            this.selectedSkuId
        } else {
            this.productId
        }
        val skuName = this.getSelectedSpecName()
        val params = "type=direct&productId=" + this.productId + "&skuId=" + skuId + "&quantity=" + this.quantity + "&productName=" + UTSAndroid.consoleDebugError(encodeURIComponent(this.product.name), " at pages/product/detail.uvue:650") + "&skuName=" + UTSAndroid.consoleDebugError(encodeURIComponent(skuName), " at pages/product/detail.uvue:650") + "&price=" + this.product.price + "&productImage=" + UTSAndroid.consoleDebugError(encodeURIComponent(this.product.image), " at pages/product/detail.uvue:650")
        uni_navigateTo(NavigateToOptions(url = "/pages/order/checkout?" + params))
    }
    open var viewAllReviews = ::gen_viewAllReviews_fn
    open fun gen_viewAllReviews_fn() {
        uni_navigateTo(NavigateToOptions(url = "/pages/product/reviews?productId=" + this.productId + "&productName=" + UTSAndroid.consoleDebugError(encodeURIComponent(this.product.name), " at pages/product/detail.uvue:658")))
    }
    companion object {
        var name = "ProductDetail"
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            normalizeCssStyles(utsArrayOf(
                styles0
            ), utsArrayOf(
                GenApp.styles
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return utsMapOf("product-detail-page" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "column", "height" to "100%", "width" to "100%", "backgroundColor" to "#F5F7FA")), "product-detail__scroll" to padStyleMapOf(utsMapOf("flex" to 1, "width" to "100%", "height" to 0)), "product-detail__carousel" to padStyleMapOf(utsMapOf("position" to "relative", "width" to "100%", "backgroundImage" to "none", "backgroundColor" to "#ffffff")), "product-detail__loading" to padStyleMapOf(utsMapOf("width" to "100%", "height" to "750rpx", "display" to "flex", "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "#f5f5f5")), "loading-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#999999")), "product-detail__swiper" to padStyleMapOf(utsMapOf("width" to "100%", "height" to "750rpx")), "product-detail__swiper-item" to padStyleMapOf(utsMapOf("width" to "100%", "height" to "100%", "backgroundColor" to "#f5f5f5", "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "product-detail__image" to padStyleMapOf(utsMapOf("width" to "100%", "height" to "100%", "backgroundColor" to "#ffffff")), "product-detail__image-indicator" to padStyleMapOf(utsMapOf("position" to "absolute", "bottom" to "20rpx", "right" to "20rpx", "backgroundImage" to "none", "backgroundColor" to "rgba(0,0,0,0.6)", "paddingTop" to "6rpx", "paddingRight" to "16rpx", "paddingBottom" to "6rpx", "paddingLeft" to "16rpx", "borderTopLeftRadius" to "20rpx", "borderTopRightRadius" to "20rpx", "borderBottomRightRadius" to "20rpx", "borderBottomLeftRadius" to "20rpx", "zIndex" to 5)), "product-detail__indicator-text" to padStyleMapOf(utsMapOf("color" to "#ffffff", "fontSize" to "22rpx")), "product-detail__info-section" to padStyleMapOf(utsMapOf("backgroundImage" to "none", "backgroundColor" to "#ffffff", "paddingTop" to "24rpx", "paddingRight" to "28rpx", "paddingBottom" to "24rpx", "paddingLeft" to "28rpx", "marginTop" to 0, "marginRight" to 0, "marginBottom" to "12rpx", "marginLeft" to 0, "display" to "flex", "flexDirection" to "column")), "product-detail__price-row" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "flex-end", "marginBottom" to "20rpx", "paddingBottom" to "20rpx", "borderBottomWidth" to "1rpx", "borderBottomStyle" to "solid", "borderBottomColor" to "#f0f0f0")), "product-detail__price-group" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "color" to "#FF4D4F")), "product-detail__currency" to padStyleMapOf(utsMapOf("fontSize" to "28rpx", "fontWeight" to "bold", "color" to "#FF4D4F")), "product-detail__current-price" to padStyleMapOf(utsMapOf("fontSize" to "48rpx", "fontWeight" to "bold", "marginRight" to "16rpx", "color" to "#ff4d4f")), "product-detail__original-price" to padStyleMapOf(utsMapOf("fontSize" to "26rpx", "color" to "#999999", "opacity" to 0.7)), "product-detail__sales" to padStyleMapOf(utsMapOf("fontSize" to "24rpx", "color" to "#999999")), "product-detail__tags-row" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "flexWrap" to "wrap", "marginBottom" to "16rpx")), "product-detail__tag" to padStyleMapOf(utsMapOf("paddingTop" to "6rpx", "paddingRight" to "16rpx", "paddingBottom" to "6rpx", "paddingLeft" to "16rpx", "borderTopLeftRadius" to "20rpx", "borderTopRightRadius" to "20rpx", "borderBottomRightRadius" to "20rpx", "borderBottomLeftRadius" to "20rpx", "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center", "marginRight" to "8rpx", "marginBottom" to "8rpx")), "product-detail__tag--discount" to padStyleMapOf(utsMapOf("backgroundImage" to "linear-gradient(135deg, #fff0f0 0%, #ffe0e0 100%)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopWidth" to "2rpx", "borderRightWidth" to "2rpx", "borderBottomWidth" to "2rpx", "borderLeftWidth" to "2rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#ffcccc", "borderRightColor" to "#ffcccc", "borderBottomColor" to "#ffcccc", "borderLeftColor" to "#ffcccc")), "product-detail__tag--new" to padStyleMapOf(utsMapOf("backgroundImage" to "linear-gradient(135deg, #e6f7ff 0%, #bae0ff 100%)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopWidth" to "2rpx", "borderRightWidth" to "2rpx", "borderBottomWidth" to "2rpx", "borderLeftWidth" to "2rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#91caff", "borderRightColor" to "#91caff", "borderBottomColor" to "#91caff", "borderLeftColor" to "#91caff")), "product-detail__tag--fresh" to padStyleMapOf(utsMapOf("backgroundImage" to "linear-gradient(135deg, #f6ffed 0%, #d9f7be 100%)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopWidth" to "2rpx", "borderRightWidth" to "2rpx", "borderBottomWidth" to "2rpx", "borderLeftWidth" to "2rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#b7eb8f", "borderRightColor" to "#b7eb8f", "borderBottomColor" to "#b7eb8f", "borderLeftColor" to "#b7eb8f")), "product-detail__tag-text" to padStyleMapOf(utsMapOf("fontSize" to "20rpx", "fontWeight" to "normal")), "product-detail__tag-text--discount" to padStyleMapOf(utsMapOf("color" to "#ff4d4f")), "product-detail__tag-text--new" to padStyleMapOf(utsMapOf("color" to "#0066CC")), "product-detail__tag-text--fresh" to padStyleMapOf(utsMapOf("color" to "#52c41a")), "product-detail__name" to padStyleMapOf(utsMapOf("fontSize" to "32rpx", "fontWeight" to "bold", "color" to "#1a1a1a", "marginBottom" to "12rpx", "lineHeight" to 1.4)), "product-detail__brief" to padStyleMapOf(utsMapOf("fontSize" to "24rpx", "color" to "#999999", "lineHeight" to 1.4, "marginBottom" to "16rpx")), "product-detail__meta-row" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "paddingTop" to "16rpx")), "product-detail__rating" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center")), "rating-star" to padStyleMapOf(utsMapOf("marginRight" to "8rpx", "fontSize" to "24rpx")), "rating-score" to padStyleMapOf(utsMapOf("fontWeight" to "bold", "color" to "#333333", "marginRight" to "8rpx", "fontSize" to "24rpx")), "rating-count" to padStyleMapOf(utsMapOf("color" to "#999999", "fontSize" to "24rpx")), "product-detail__stock-status" to padStyleMapOf(utsMapOf("fontSize" to "24rpx", "color" to "#52C41A")), "product-detail__stock-status--low" to padStyleMapOf(utsMapOf("color" to "#FF7A45")), "product-detail__section" to padStyleMapOf(utsMapOf("backgroundImage" to "none", "backgroundColor" to "#ffffff", "paddingTop" to "24rpx", "paddingRight" to "28rpx", "paddingBottom" to "24rpx", "paddingLeft" to "28rpx", "marginBottom" to "12rpx", "display" to "flex", "flexDirection" to "column")), "product-detail__section-title-box" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center")), "product-detail__section-title" to padStyleMapOf(utsMapOf("fontSize" to "30rpx", "fontWeight" to "bold", "color" to "#333333")), "title-count" to padStyleMapOf(utsMapOf("fontSize" to "24rpx", "color" to "#999999", "fontWeight" to "normal", "marginLeft" to "8rpx")), "product-detail__section-title-simple" to padStyleMapOf(utsMapOf("fontSize" to "30rpx", "fontWeight" to "bold", "color" to "#333333", "marginBottom" to "24rpx", "position" to "relative", "paddingLeft" to "20rpx", "borderLeftWidth" to "6rpx", "borderLeftStyle" to "solid", "borderLeftColor" to "#0066CC")), "product-detail__spec-item" to padStyleMapOf(utsMapOf("marginBottom" to "24rpx", "display" to "flex", "flexDirection" to "column")), "product-detail__spec-label" to padStyleMapOf(utsMapOf("fontSize" to "28rpx", "color" to "#333333", "fontWeight" to "bold", "marginBottom" to "12rpx")), "product-detail__spec-options" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "flexWrap" to "wrap")), "product-detail__spec-option" to padStyleMapOf(utsMapOf("paddingTop" to "12rpx", "paddingRight" to "32rpx", "paddingBottom" to "12rpx", "paddingLeft" to "32rpx", "borderTopWidth" to "2rpx", "borderRightWidth" to "2rpx", "borderBottomWidth" to "2rpx", "borderLeftWidth" to "2rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#e8e8e8", "borderRightColor" to "#e8e8e8", "borderBottomColor" to "#e8e8e8", "borderLeftColor" to "#e8e8e8", "borderTopLeftRadius" to "8rpx", "borderTopRightRadius" to "8rpx", "borderBottomRightRadius" to "8rpx", "borderBottomLeftRadius" to "8rpx", "backgroundColor" to "#fafafa", "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center", "marginRight" to "16rpx", "marginBottom" to "16rpx")), "product-detail__spec-option--selected" to padStyleMapOf(utsMapOf("backgroundImage" to "linear-gradient(135deg, #e6f7ff 0%, #bae0ff 100%)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopColor" to "#0066CC", "borderRightColor" to "#0066CC", "borderBottomColor" to "#0066CC", "borderLeftColor" to "#0066CC", "borderTopWidth" to "2rpx", "borderRightWidth" to "2rpx", "borderBottomWidth" to "2rpx", "borderLeftWidth" to "2rpx", "boxShadow" to "0 4rpx 12rpx rgba(0, 102, 204, 0.2)")), "product-detail__spec-text" to padStyleMapOf(utsMapOf("fontSize" to "24rpx", "color" to "#333333")), "product-detail__spec-text--selected" to padStyleMapOf(utsMapOf("color" to "#0066CC", "fontWeight" to "400")), "product-detail__quantity-row" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "marginTop" to "8rpx")), "product-detail__quantity-control" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "backgroundImage" to "none", "backgroundColor" to "#f5f5f5", "borderTopLeftRadius" to "8rpx", "borderTopRightRadius" to "8rpx", "borderBottomRightRadius" to "8rpx", "borderBottomLeftRadius" to "8rpx", "paddingTop" to "4rpx", "paddingRight" to "4rpx", "paddingBottom" to "4rpx", "paddingLeft" to "4rpx", "borderTopWidth" to "1rpx", "borderRightWidth" to "1rpx", "borderBottomWidth" to "1rpx", "borderLeftWidth" to "1rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#e0e0e0", "borderRightColor" to "#e0e0e0", "borderBottomColor" to "#e0e0e0", "borderLeftColor" to "#e0e0e0")), "product-detail__quantity-btn" to padStyleMapOf(utsMapOf("width" to "56rpx", "height" to "56rpx", "display" to "flex", "alignItems" to "center", "justifyContent" to "center", "backgroundImage" to "none", "backgroundColor" to "#ffffff", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx", "marginTop" to 0, "marginRight" to "2rpx", "marginBottom" to 0, "marginLeft" to "2rpx")), "product-detail__quantity-btn--disabled" to padStyleMapOf(utsMapOf("backgroundImage" to "none", "backgroundColor" to "rgba(0,0,0,0)", "boxShadow" to "none", "opacity" to 0.5)), "product-detail__quantity-btn-text" to padStyleMapOf(utsMapOf("fontSize" to "32rpx", "color" to "#333333", "fontWeight" to "bold")), "product-detail__quantity-input" to padStyleMapOf(utsMapOf("width" to "80rpx", "height" to "56rpx", "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "product-detail__quantity-input-text" to padStyleMapOf(utsMapOf("fontSize" to "30rpx", "color" to "#1a1a1a", "fontWeight" to "bold")), "product-detail__section-header" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "marginBottom" to "20rpx")), "product-detail__section-more" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center")), "product-detail__section-more-text" to padStyleMapOf(utsMapOf("fontSize" to "24rpx", "color" to "#999999")), "arrow-right" to padStyleMapOf(utsMapOf("marginLeft" to "4rpx", "fontSize" to "24rpx", "color" to "#999999")), "product-detail__review-list" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "column")), "product-detail__review-item" to padStyleMapOf(utsMapOf("borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "#F5F5F5", "paddingBottom" to "24rpx", "marginBottom" to "24rpx", "display" to "flex", "flexDirection" to "column")), "product-detail__review-header" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "marginBottom" to "12rpx")), "product-detail__review-user" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center")), "product-detail__review-avatar" to padStyleMapOf(utsMapOf("width" to "48rpx", "height" to "48rpx", "borderTopLeftRadius" to 999, "borderTopRightRadius" to 999, "borderBottomRightRadius" to 999, "borderBottomLeftRadius" to 999, "marginRight" to "12rpx", "backgroundColor" to "#eeeeee")), "product-detail__review-username" to padStyleMapOf(utsMapOf("fontSize" to "24rpx", "color" to "#999999", "marginRight" to "12rpx")), "product-detail__review-rating-stars" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row")), "star" to padStyleMapOf(utsMapOf("fontSize" to "20rpx", "color" to "#C0C0C0", "marginRight" to "2rpx")), "star-active" to padStyleMapOf(utsMapOf("color" to "#FF7A45")), "product-detail__review-date" to padStyleMapOf(utsMapOf("fontSize" to "22rpx", "color" to "#C0C0C0")), "product-detail__review-content" to padStyleMapOf(utsMapOf("fontSize" to "26rpx", "color" to "#333333", "lineHeight" to 1.5)), "product-detail__review-reply" to padStyleMapOf(utsMapOf("marginTop" to "16rpx", "paddingTop" to "16rpx", "paddingRight" to "16rpx", "paddingBottom" to "16rpx", "paddingLeft" to "16rpx", "backgroundColor" to "#f8f8f8", "borderTopLeftRadius" to "8rpx", "borderTopRightRadius" to "8rpx", "borderBottomRightRadius" to "8rpx", "borderBottomLeftRadius" to "8rpx", "display" to "flex", "flexDirection" to "column")), "reply-label" to utsMapOf(".product-detail__review-reply " to utsMapOf("fontSize" to "24rpx", "color" to "#0066CC", "fontWeight" to "bold", "marginBottom" to "8rpx")), "reply-content" to utsMapOf(".product-detail__review-reply " to utsMapOf("fontSize" to "24rpx", "color" to "#666666", "lineHeight" to 1.4)), "product-detail__empty-review" to padStyleMapOf(utsMapOf("display" to "flex", "alignItems" to "center", "justifyContent" to "center", "paddingTop" to "40rpx", "paddingRight" to 0, "paddingBottom" to "40rpx", "paddingLeft" to 0, "backgroundImage" to "none", "backgroundColor" to "#fafafa", "borderTopLeftRadius" to "8rpx", "borderTopRightRadius" to "8rpx", "borderBottomRightRadius" to "8rpx", "borderBottomLeftRadius" to "8rpx")), "product-detail__empty-review-text" to padStyleMapOf(utsMapOf("color" to "#999999", "fontSize" to "24rpx")), "product-detail__detail-section" to padStyleMapOf(utsMapOf("paddingBottom" to "24rpx")), "product-detail__description" to padStyleMapOf(utsMapOf("fontSize" to "26rpx", "color" to "#333333", "lineHeight" to 1.8, "paddingTop" to "20rpx", "paddingRight" to 0, "paddingBottom" to "20rpx", "paddingLeft" to 0, "textAlign" to "left")), "product-detail__safe-area-placeholder" to padStyleMapOf(utsMapOf("height" to "140rpx", "width" to "100%")), "product-detail__footer" to padStyleMapOf(utsMapOf("position" to "fixed", "bottom" to 0, "left" to 0, "right" to 0, "backgroundImage" to "none", "backgroundColor" to "#ffffff", "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "paddingTop" to "16rpx", "paddingRight" to "20rpx", "paddingBottom" to "24rpx", "paddingLeft" to "20rpx", "boxShadow" to "0 -2rpx 16rpx rgba(0, 0, 0, 0.06)", "zIndex" to 1000, "borderTopWidth" to "1rpx", "borderTopStyle" to "solid", "borderTopColor" to "#f0f0f0")), "product-detail__footer-icons" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "marginRight" to "20rpx")), "product-detail__icon-btn" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "column", "alignItems" to "center", "justifyContent" to "center", "width" to "90rpx")), "icon-text" to utsMapOf(".product-detail__icon-btn " to utsMapOf("fontSize" to "36rpx", "marginBottom" to "4rpx")), "icon-label" to utsMapOf(".product-detail__icon-btn " to utsMapOf("fontSize" to "20rpx", "color" to "#999999")), "product-detail__footer-actions" to padStyleMapOf(utsMapOf("flex" to 1, "display" to "flex", "flexDirection" to "row", "height" to "80rpx")), "product-detail__action-btn" to padStyleMapOf(utsMapOf("flex" to 1, "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center", "height" to "76rpx")), "product-detail__action-btn--cart" to padStyleMapOf(utsMapOf("backgroundColor" to "#FF9500", "borderTopLeftRadius" to "8rpx", "borderTopRightRadius" to "8rpx", "borderBottomRightRadius" to "8rpx", "borderBottomLeftRadius" to "8rpx", "marginRight" to "12rpx")), "product-detail__action-btn--buy" to padStyleMapOf(utsMapOf("backgroundColor" to "#FF3D00", "borderTopLeftRadius" to "8rpx", "borderTopRightRadius" to "8rpx", "borderBottomRightRadius" to "8rpx", "borderBottomLeftRadius" to "8rpx")), "product-detail__action-text" to padStyleMapOf(utsMapOf("fontSize" to "30rpx", "fontWeight" to "bold", "letterSpacing" to 1, "color" to "#ffffff")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = utsMapOf()
        var emits: Map<String, Any?> = utsMapOf()
        var props = normalizePropsOptions(utsMapOf())
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf()
        var components: Map<String, CreateVueComponent> = utsMapOf()
    }
}
