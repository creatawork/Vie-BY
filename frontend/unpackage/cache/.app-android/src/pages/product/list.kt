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
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesProductList : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onPageShow(fun() {
            uni__emit("bottomNavRefresh", null)
        }
        , __ins)
        onLoad(fun(options: OnLoadOptions) {
            val opts = options as UTSJSONObject
            val keyword = opts.get("keyword")
            if (keyword != null) {
                this.searchKeyword = keyword as String
            }
            val type = opts.get("type")
            if (type != null) {
                this.listType = type as String
            }
            val categoryId = opts.get("categoryId")
            if (categoryId != null) {
                this.categoryId = parseInt(categoryId as String)
            }
            val category = opts.get("category")
            if (category != null) {
                this.activeCategory = category as String
            }
            this.loadData()
            this.updateCartCount()
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        val _component_BottomNav = resolveComponent("BottomNav")
        return createElementVNode("view", utsMapOf("class" to "page"), utsArrayOf(
            createElementVNode("view", utsMapOf("class" to "header"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "search-bar", "onClick" to _ctx.handleSearch), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "search-icon"), "🔍"),
                    createElementVNode("input", utsMapOf("class" to "search-input", "modelValue" to _ctx.searchKeyword, "onInput" to fun(`$event`: InputEvent){
                        _ctx.searchKeyword = `$event`.detail.value
                    }
                    , "placeholder" to "搜索商品", "onConfirm" to _ctx.handleSearch), null, 40, utsArrayOf(
                        "modelValue",
                        "onInput",
                        "onConfirm"
                    ))
                ), 8, utsArrayOf(
                    "onClick"
                ))
            )),
            createElementVNode("view", utsMapOf("class" to "filter-bar"), utsArrayOf(
                createElementVNode("scroll-view", utsMapOf("class" to "category-scroll", "scroll-x" to "true", "show-scrollbar" to "false"), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "category-tabs"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "tab-item", "onClick" to fun(){
                            _ctx.selectCategory("all")
                        }
                        ), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                                "tab-text",
                                utsMapOf("tab-text-active" to (_ctx.activeCategory === "all"))
                            ))), "全部", 2),
                            if (_ctx.activeCategory === "all") {
                                createElementVNode("view", utsMapOf("key" to 0, "class" to "active-line"))
                            } else {
                                createCommentVNode("v-if", true)
                            }
                        ), 8, utsArrayOf(
                            "onClick"
                        )),
                        createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.categories, fun(cat, __key, __index, _cached): Any {
                            return createElementVNode("view", utsMapOf("class" to "tab-item", "key" to cat.id, "onClick" to fun(){
                                _ctx.selectCategory(cat.id)
                            }
                            ), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                                    "tab-text",
                                    utsMapOf("tab-text-active" to (_ctx.activeCategory === cat.id))
                                ))), toDisplayString(cat.name), 3),
                                if (_ctx.activeCategory === cat.id) {
                                    createElementVNode("view", utsMapOf("key" to 0, "class" to "active-line"))
                                } else {
                                    createCommentVNode("v-if", true)
                                }
                            ), 8, utsArrayOf(
                                "onClick"
                            ))
                        }
                        ), 128)
                    ))
                )),
                createElementVNode("view", utsMapOf("class" to "sort-tabs"), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                        "sort-item",
                        utsMapOf("sort-item-active" to (_ctx.activeSort === "default"))
                    )), "onClick" to fun(){
                        _ctx.selectSort("default")
                    }
                    ), utsArrayOf(
                        createElementVNode("text", null, "综合")
                    ), 10, utsArrayOf(
                        "onClick"
                    )),
                    createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                        "sort-item",
                        utsMapOf("sort-item-active" to (_ctx.activeSort === "sales"))
                    )), "onClick" to fun(){
                        _ctx.selectSort("sales")
                    }
                    ), utsArrayOf(
                        createElementVNode("text", null, "销量")
                    ), 10, utsArrayOf(
                        "onClick"
                    )),
                    createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                        "sort-item",
                        utsMapOf("sort-item-active" to _ctx.activeSort.includes("price"))
                    )), "onClick" to _ctx.togglePriceSort), utsArrayOf(
                        createElementVNode("text", null, "价格"),
                        createElementVNode("view", utsMapOf("class" to "sort-arrows"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                                "arrow arrow-up",
                                utsMapOf("arrow-on" to (_ctx.activeSort === "price_asc"))
                            ))), "▲", 2),
                            createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                                "arrow",
                                utsMapOf("arrow-on" to (_ctx.activeSort === "price_desc"))
                            ))), "▼", 2)
                        ))
                    ), 10, utsArrayOf(
                        "onClick"
                    ))
                ))
            )),
            createElementVNode("scroll-view", utsMapOf("class" to "product-scroll", "scroll-y" to "true", "onScrolltolower" to _ctx.loadMore), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "product-list"), utsArrayOf(
                    createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.filteredProducts, fun(product, __key, __index, _cached): Any {
                        return createElementVNode("view", utsMapOf("class" to "product-item", "key" to product.id, "onClick" to fun(){
                            _ctx.goToDetail(product.id)
                        }
                        ), utsArrayOf(
                            createElementVNode("image", utsMapOf("class" to "product-img", "src" to product.image, "mode" to "aspectFill"), null, 8, utsArrayOf(
                                "src"
                            )),
                            createElementVNode("view", utsMapOf("class" to "product-content"), utsArrayOf(
                                createElementVNode("view", utsMapOf("class" to "info-top"), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "product-name"), toDisplayString(product.name), 1),
                                    createElementVNode("text", utsMapOf("class" to "product-desc"), toDisplayString(product.description), 1),
                                    if (isTrue(_ctx.hasTags(product))) {
                                        createElementVNode("view", utsMapOf("key" to 0, "class" to "product-tags"), utsArrayOf(
                                            createElementVNode(Fragment, null, RenderHelpers.renderList(product.tags, fun(tag, __key, __index, _cached): Any {
                                                return createElementVNode("text", utsMapOf("class" to "tag", "key" to tag), toDisplayString(tag), 1)
                                            }), 128)
                                        ))
                                    } else {
                                        createCommentVNode("v-if", true)
                                    }
                                )),
                                createElementVNode("view", utsMapOf("class" to "info-bottom"), utsArrayOf(
                                    createElementVNode("view", utsMapOf("class" to "price-box"), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to "currency"), "¥"),
                                        createElementVNode("text", utsMapOf("class" to "price"), toDisplayString(product.price), 1),
                                        if (isTrue(product.originalPrice)) {
                                            createElementVNode("text", utsMapOf("key" to 0, "class" to "origin-price"), "¥" + toDisplayString(product.originalPrice), 1)
                                        } else {
                                            createCommentVNode("v-if", true)
                                        }
                                    )),
                                    createElementVNode("view", utsMapOf("class" to "add-btn", "onClick" to withModifiers(fun(){
                                        _ctx.addToCart(product)
                                    }
                                    , utsArrayOf(
                                        "stop"
                                    ))), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to "add-icon"), "+")
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
                if (isTrue(_ctx.filteredProducts.length === 0 && !_ctx.isLoading)) {
                    createElementVNode("view", utsMapOf("key" to 0, "class" to "empty-state"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "empty-icon"), "📦"),
                        createElementVNode("text", utsMapOf("class" to "empty-text"), "暂无相关商品")
                    ))
                } else {
                    createCommentVNode("v-if", true)
                }
                ,
                if (isTrue(_ctx.isLoading)) {
                    createElementVNode("view", utsMapOf("key" to 1, "class" to "loading-more"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "loading-text"), "加载中...")
                    ))
                } else {
                    createCommentVNode("v-if", true)
                }
                ,
                if (isTrue(!_ctx.isLoading && !_ctx.hasMore && _ctx.filteredProducts.length > 0)) {
                    createElementVNode("view", utsMapOf("key" to 2, "class" to "no-more"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "no-more-text"), "没有更多了")
                    ))
                } else {
                    createCommentVNode("v-if", true)
                }
            ), 40, utsArrayOf(
                "onScrolltolower"
            )),
            createElementVNode("view", utsMapOf("class" to "cart-float-btn", "onClick" to _ctx.goToCart), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "cart-icon-wrapper"), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "cart-icon"), "🛒"),
                    if (_ctx.cartCount > 0) {
                        createElementVNode("view", utsMapOf("key" to 0, "class" to "cart-badge"), toDisplayString(_ctx.cartCount), 1)
                    } else {
                        createCommentVNode("v-if", true)
                    }
                ))
            ), 8, utsArrayOf(
                "onClick"
            )),
            createVNode(_component_BottomNav)
        ))
    }
    open var searchKeyword: String by `$data`
    open var activeCategory: String by `$data`
    open var activeSort: String by `$data`
    open var cartCount: Number by `$data`
    open var isLoading: Boolean by `$data`
    open var hasMore: Boolean by `$data`
    open var pageNum: Number by `$data`
    open var pageSize: Number by `$data`
    open var listType: String by `$data`
    open var categoryId: Number by `$data`
    open var categories: UTSArray<CategoryType1> by `$data`
    open var products: UTSArray<ProductType> by `$data`
    open var displayProducts: UTSArray<ProductType> by `$data`
    open var filteredProducts: UTSArray<ProductType> by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return utsMapOf("searchKeyword" to "", "activeCategory" to "all", "activeSort" to "default", "cartCount" to 0, "isLoading" to false, "hasMore" to true, "pageNum" to 1, "pageSize" to 20, "listType" to "", "categoryId" to 0, "categories" to utsArrayOf<CategoryType1>(CategoryType1(id = "vegetable", name = "蔬菜"), CategoryType1(id = "fruit", name = "水果"), CategoryType1(id = "meat", name = "肉禽"), CategoryType1(id = "seafood", name = "水产"), CategoryType1(id = "dairy", name = "乳品"), CategoryType1(id = "snack", name = "零食")), "products" to utsArrayOf<ProductType>(), "displayProducts" to utsArrayOf<ProductType>(), "filteredProducts" to computed<UTSArray<ProductType>>(fun(): UTSArray<ProductType> {
            return this.products
        }
        ))
    }
    open var hasTags = ::gen_hasTags_fn
    open fun gen_hasTags_fn(product: ProductType): Boolean {
        val tags = product.tags
        return tags != null && tags.length > 0
    }
    open var loadData = ::gen_loadData_fn
    open fun gen_loadData_fn() {
        this.isLoading = true
        val params: UTSJSONObject = let {
            object : UTSJSONObject(UTSSourceMapPosition("params", "pages/product/list.uvue", 203, 13)) {
                var pageNum = it.pageNum
                var pageSize = it.pageSize
                var status: Number = 1
            }
        }
        if (this.searchKeyword != "") {
            params.set("keyword", this.searchKeyword)
        }
        if (this.categoryId > 0) {
            params.set("categoryId", this.categoryId)
        }
        if (this.listType === "special") {
        } else if (this.listType === "discount") {
        } else if (this.listType === "new") {
            params.set("isNew", 1)
        } else if (this.listType === "recommend") {
            params.set("isRecommended", 1)
        }
        if (this.activeSort === "sales") {
            params.set("sortBy", "sales")
            params.set("sortOrder", "desc")
        } else if (this.activeSort === "price_asc") {
            params.set("sortBy", "price")
            params.set("sortOrder", "asc")
        } else if (this.activeSort === "price_desc") {
            params.set("sortBy", "price")
            params.set("sortOrder", "desc")
        }
        post("/products/search", params).then(fun(res){
            val data = res.data as UTSJSONObject
            val records = data.getArray("records")
            if (records != null && records.length > 0) {
                val products: UTSArray<ProductType> = utsArrayOf()
                run {
                    var i: Number = 0
                    while(i < records.length){
                        val item = records[i] as UTSJSONObject
                        val tags: UTSArray<String> = utsArrayOf()
                        val isNew = item.getBoolean("isNew") ?: false
                        val isHot = item.getBoolean("isHot") ?: false
                        val isRecommended = item.getBoolean("isRecommended") ?: false
                        if (isNew) {
                            tags.push("新品")
                        }
                        if (isHot) {
                            tags.push("热销")
                        }
                        if (isRecommended) {
                            tags.push("推荐")
                        }
                        products.push(ProductType(id = (item.getNumber("id") ?: 0).toInt(), name = item.getString("productName") ?: "", description = item.getString("description") ?: "", image = item.getString("mainImage") ?: "", price = item.getNumber("currentPrice") ?: 0, originalPrice = item.getNumber("originalPrice"), sales = (item.getNumber("salesVolume") ?: 0).toInt(), category = "all", tags = tags))
                        i++
                    }
                }
                this.products = products
            }
            this.isLoading = false
        }
        ).`catch`(fun(err){
            console.error("获取商品列表失败:", err, " at pages/product/list.uvue:275")
            this.isLoading = false
        }
        )
    }
    open var handleSearch = ::gen_handleSearch_fn
    open fun gen_handleSearch_fn() {
        this.pageNum = 1
        this.products = utsArrayOf()
        this.loadData()
    }
    open var selectCategory = ::gen_selectCategory_fn
    open fun gen_selectCategory_fn(id: String) {
        this.activeCategory = id
    }
    open var selectSort = ::gen_selectSort_fn
    open fun gen_selectSort_fn(type: String) {
        this.activeSort = type
        this.pageNum = 1
        this.products = utsArrayOf()
        this.loadData()
    }
    open var togglePriceSort = ::gen_togglePriceSort_fn
    open fun gen_togglePriceSort_fn() {
        if (this.activeSort === "price_asc") {
            this.activeSort = "price_desc"
        } else {
            this.activeSort = "price_asc"
        }
        this.pageNum = 1
        this.products = utsArrayOf()
        this.loadData()
    }
    open var loadMore = ::gen_loadMore_fn
    open fun gen_loadMore_fn() {
        if (!this.isLoading) {
            console.log("Load More...", " at pages/product/list.uvue:309")
        }
    }
    open var goToDetail = ::gen_goToDetail_fn
    open fun gen_goToDetail_fn(id: Number) {
        uni_navigateTo(NavigateToOptions(url = "/pages/product/detail?id=" + id))
    }
    open var addToCart = ::gen_addToCart_fn
    open fun gen_addToCart_fn(product: ProductType) {
        getProductDetail(product.id).then(fun(res){
            val data = res.data as UTSJSONObject
            var skuId = product.id
            val skuArr = data.getArray("skuList")
            if (skuArr != null && skuArr.length > 0) {
                val firstSku = skuArr[0] as UTSJSONObject
                skuId = (firstSku.getNumber("id") ?: product.id).toInt()
            }
            val params = CartAddParams(productId = product.id, skuId = skuId, quantity = 1)
            console.log("添加购物车参数:", JSON.stringify(params), " at pages/product/list.uvue:333")
            uni.UNIBY001.addToCart(params).then(fun(_addRes){
                uni_showToast(ShowToastOptions(title = "已加入购物车", icon = "success"))
                this.cartCount++
                uni__emit("bottomNavRefresh", null)
            }
            ).`catch`(fun(addErr){
                console.error("添加购物车失败:", addErr, " at pages/product/list.uvue:341")
                uni_showToast(ShowToastOptions(title = "添加失败", icon = "none"))
            }
            )
        }
        ).`catch`(fun(err){
            console.error("获取商品详情失败:", err, " at pages/product/list.uvue:345")
            uni_showToast(ShowToastOptions(title = "添加失败", icon = "none"))
        }
        )
    }
    open var goToCart = ::gen_goToCart_fn
    open fun gen_goToCart_fn() {
        uni_navigateTo(NavigateToOptions(url = "/pages/cart/index"))
    }
    open var updateCartCount = ::gen_updateCartCount_fn
    open fun gen_updateCartCount_fn() {
        getCartCount().then(fun(res){
            val count = (res.data as Number?) ?: 0
            this.cartCount = count as Number
        }
        ).`catch`(fun(err){
            console.error("获取购物车数量失败:", err, " at pages/product/list.uvue:358")
            this.cartCount = 0
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
                return utsMapOf("page" to padStyleMapOf(utsMapOf("backgroundColor" to "#f7f8fa", "flex" to 1, "display" to "flex", "flexDirection" to "column")), "header" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "paddingTop" to CSS_VAR_STATUS_BAR_HEIGHT, "paddingRight" to 16, "paddingBottom" to 8, "paddingLeft" to 16)), "search-bar" to padStyleMapOf(utsMapOf("backgroundColor" to "#f2f2f2", "borderTopLeftRadius" to 20, "borderTopRightRadius" to 20, "borderBottomRightRadius" to 20, "borderBottomLeftRadius" to 20, "height" to 36, "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "paddingTop" to 0, "paddingRight" to 12, "paddingBottom" to 0, "paddingLeft" to 12)), "search-icon" to utsMapOf(".search-bar " to utsMapOf("fontSize" to 16, "color" to "#999999", "marginRight" to 8)), "search-input" to utsMapOf(".search-bar " to utsMapOf("flex" to 1, "fontSize" to 14, "color" to "#333333")), "filter-bar" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "#eeeeee", "zIndex" to 10)), "category-scroll" to padStyleMapOf(utsMapOf("whiteSpace" to "nowrap", "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "#f9f9f9")), "category-tabs" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "paddingTop" to 0, "paddingRight" to 8, "paddingBottom" to 0, "paddingLeft" to 8)), "tab-item" to padStyleMapOf(utsMapOf("position" to "relative", "paddingTop" to 12, "paddingRight" to 16, "paddingBottom" to 12, "paddingLeft" to 16, "display" to "flex", "flexDirection" to "column", "alignItems" to "center")), "tab-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#666666")), "tab-text-active" to padStyleMapOf(utsMapOf("color" to "#0066CC", "fontWeight" to "700", "fontSize" to 15)), "active-line" to padStyleMapOf(utsMapOf("position" to "absolute", "bottom" to 6, "width" to 20, "height" to 3, "backgroundColor" to "#0066CC", "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "sort-tabs" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "height" to 40, "alignItems" to "center")), "sort-item" to padStyleMapOf(utsMapOf("flex" to 1, "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center", "fontSize" to 13, "color" to "#666666")), "sort-item-active" to padStyleMapOf(utsMapOf("color" to "#0066CC", "fontWeight" to "400")), "sort-arrows" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "column", "marginLeft" to 4)), "arrow" to padStyleMapOf(utsMapOf("fontSize" to 8, "lineHeight" to "8px", "color" to "#cccccc")), "arrow-on" to padStyleMapOf(utsMapOf("color" to "#0066CC")), "arrow-up" to padStyleMapOf(utsMapOf("marginBottom" to -1)), "product-scroll" to padStyleMapOf(utsMapOf("flex" to 1, "backgroundColor" to "#f7f8fa")), "product-list" to padStyleMapOf(utsMapOf("paddingTop" to 10, "paddingRight" to 12, "paddingBottom" to 10, "paddingLeft" to 12)), "product-item" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "backgroundColor" to "#ffffff", "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "paddingTop" to 10, "paddingRight" to 10, "paddingBottom" to 10, "paddingLeft" to 10, "marginBottom" to 10, "boxShadow" to "0 1px 4px rgba(0, 0, 0, 0.02)")), "product-img" to utsMapOf(".product-item " to utsMapOf("width" to 100, "height" to 100, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "flexShrink" to 0, "backgroundColor" to "#f9f9f9")), "product-content" to utsMapOf(".product-item " to utsMapOf("flex" to 1, "marginLeft" to 12, "display" to "flex", "flexDirection" to "column", "justifyContent" to "space-between")), "product-name" to utsMapOf(".product-item .product-content .info-top " to utsMapOf("fontSize" to 15, "fontWeight" to "700", "color" to "#333333", "marginBottom" to 4, "overflow" to "hidden", "whiteSpace" to "normal", "lineHeight" to 1.4, "maxHeight" to 40)), "product-desc" to utsMapOf(".product-item .product-content .info-top " to utsMapOf("fontSize" to 12, "color" to "#999999", "marginBottom" to 6, "whiteSpace" to "nowrap", "overflow" to "hidden", "textOverflow" to "ellipsis")), "product-tags" to utsMapOf(".product-item .product-content .info-top " to utsMapOf("display" to "flex", "flexDirection" to "row", "flexWrap" to "wrap")), "tag" to utsMapOf(".product-item .product-content .info-top .product-tags " to utsMapOf("fontSize" to 10, "color" to "#ff4d4f", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "rgba(255,77,79,0.3)", "borderRightColor" to "rgba(255,77,79,0.3)", "borderBottomColor" to "rgba(255,77,79,0.3)", "borderLeftColor" to "rgba(255,77,79,0.3)", "paddingTop" to 1, "paddingRight" to 4, "paddingBottom" to 1, "paddingLeft" to 4, "borderTopLeftRadius" to 4, "borderTopRightRadius" to 4, "borderBottomRightRadius" to 4, "borderBottomLeftRadius" to 4, "backgroundColor" to "rgba(255,77,79,0.05)")), "info-bottom" to utsMapOf(".product-item .product-content " to utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "flex-end", "justifyContent" to "space-between")), "price-box" to utsMapOf(".product-item .product-content .info-bottom " to utsMapOf("color" to "#ff4d4f")), "currency" to utsMapOf(".product-item .product-content .info-bottom .price-box " to utsMapOf("fontSize" to 12, "fontWeight" to "bold")), "price" to utsMapOf(".product-item .product-content .info-bottom .price-box " to utsMapOf("fontSize" to 18, "fontWeight" to "bold")), "origin-price" to utsMapOf(".product-item .product-content .info-bottom .price-box " to utsMapOf("fontSize" to 11, "color" to "#cccccc", "marginLeft" to 4, "opacity" to 0.8)), "add-btn" to utsMapOf(".product-item .product-content .info-bottom " to utsMapOf("width" to 28, "height" to 28, "backgroundColor" to "#0066CC", "borderTopLeftRadius" to 999, "borderTopRightRadius" to 999, "borderBottomRightRadius" to 999, "borderBottomLeftRadius" to 999, "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "add-icon" to utsMapOf(".product-item .product-content .info-bottom .add-btn " to utsMapOf("color" to "#ffffff", "fontSize" to 20, "fontWeight" to "400", "marginTop" to -2)), "empty-state" to padStyleMapOf(utsMapOf("paddingTop" to 20, "paddingRight" to 20, "paddingBottom" to 20, "paddingLeft" to 20, "display" to "flex", "flexDirection" to "column", "alignItems" to "center", "justifyContent" to "center")), "loading-more" to padStyleMapOf(utsMapOf("paddingTop" to 20, "paddingRight" to 20, "paddingBottom" to 20, "paddingLeft" to 20, "display" to "flex", "flexDirection" to "column", "alignItems" to "center", "justifyContent" to "center")), "no-more" to padStyleMapOf(utsMapOf("paddingTop" to 20, "paddingRight" to 20, "paddingBottom" to 20, "paddingLeft" to 20, "display" to "flex", "flexDirection" to "column", "alignItems" to "center", "justifyContent" to "center")), "empty-icon" to utsMapOf(".empty-state " to utsMapOf("fontSize" to 40, "marginBottom" to 10), ".loading-more " to utsMapOf("fontSize" to 40, "marginBottom" to 10), ".no-more " to utsMapOf("fontSize" to 40, "marginBottom" to 10)), "empty-text" to padStyleMapOf(utsMapOf("fontSize" to 13, "color" to "#999999")), "loading-text" to padStyleMapOf(utsMapOf("fontSize" to 13, "color" to "#999999")), "no-more-text" to padStyleMapOf(utsMapOf("fontSize" to 13, "color" to "#999999")), "cart-float-btn" to padStyleMapOf(utsMapOf("position" to "fixed", "right" to 20, "bottom" to 100, "width" to 50, "height" to 50, "backgroundColor" to "#ffffff", "borderTopLeftRadius" to 999, "borderTopRightRadius" to 999, "borderBottomRightRadius" to 999, "borderBottomLeftRadius" to 999, "boxShadow" to "0 4px 12px rgba(0, 0, 0, 0.15)", "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center", "zIndex" to 100)), "cart-icon" to utsMapOf(".cart-float-btn " to utsMapOf("fontSize" to 24)), "cart-badge" to utsMapOf(".cart-float-btn " to utsMapOf("position" to "absolute", "top" to -2, "right" to -2, "backgroundColor" to "#ff4d4f", "color" to "#ffffff", "fontSize" to 10, "paddingTop" to 2, "paddingRight" to 5, "paddingBottom" to 2, "paddingLeft" to 5, "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10, "minWidth" to 16, "textAlign" to "center")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = utsMapOf()
        var emits: Map<String, Any?> = utsMapOf()
        var props = normalizePropsOptions(utsMapOf())
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf()
        var components: Map<String, CreateVueComponent> = utsMapOf("BottomNav" to GenComponentsBottomNavClass)
    }
}
