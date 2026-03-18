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
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showModal as uni_showModal
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesSellerProducts : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onLoad(fun(_: OnLoadOptions) {
            this.loadProducts()
        }
        , __ins)
        onPageShow(fun() {
            if (this.products.length > 0) {
                this.pageNum = 1
                this.loadProducts()
            }
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return createElementVNode("view", utsMapOf("class" to "page"), utsArrayOf(
            createElementVNode("view", utsMapOf("class" to "filter-header"), utsArrayOf(
                createElementVNode("scroll-view", utsMapOf("class" to "status-scroll", "scroll-x" to "true", "show-scrollbar" to false), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "status-tabs"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                            "tab-item",
                            utsMapOf("active" to (_ctx.currentStatus == null))
                        )), "onClick" to fun(){
                            _ctx.switchStatus(null)
                        }
                        ), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                                "tab-text",
                                utsMapOf("tab-text--active" to (_ctx.currentStatus == null))
                            ))), "全部", 2),
                            if (_ctx.currentStatus == null) {
                                createElementVNode("view", utsMapOf("key" to 0, "class" to "active-line"))
                            } else {
                                createCommentVNode("v-if", true)
                            }
                        ), 10, utsArrayOf(
                            "onClick"
                        )),
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
                            ))), "已上架", 2),
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
                            utsMapOf("active" to (_ctx.currentStatus === 2))
                        )), "onClick" to fun(){
                            _ctx.switchStatus(2)
                        }
                        ), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                                "tab-text",
                                utsMapOf("tab-text--active" to (_ctx.currentStatus === 2))
                            ))), "已下架", 2),
                            if (_ctx.currentStatus === 2) {
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
                            ))), "审核不通过", 2),
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
                createElementVNode("view", utsMapOf("class" to "search-bar"), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "search-icon"), "🔍"),
                    createElementVNode("input", utsMapOf("class" to "search-input", "modelValue" to _ctx.searchKeyword, "onInput" to fun(`$event`: InputEvent){
                        _ctx.searchKeyword = `$event`.detail.value
                    }
                    , "placeholder" to "搜索商品名称", "onConfirm" to _ctx.handleSearch), null, 40, utsArrayOf(
                        "modelValue",
                        "onInput",
                        "onConfirm"
                    )),
                    if (_ctx.searchKeyword !== "") {
                        createElementVNode("view", utsMapOf("key" to 0, "class" to "search-btn", "onClick" to _ctx.handleSearch), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "search-btn-text"), "搜索")
                        ), 8, utsArrayOf(
                            "onClick"
                        ))
                    } else {
                        createCommentVNode("v-if", true)
                    }
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
                                _ctx.goToEdit(product.id)
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
                                            createElementVNode("text", utsMapOf("class" to "price"), toDisplayString(product.currentPrice.toFixed(2)), 1),
                                            if (isTrue(product.originalPrice != null && (product.originalPrice as Number) > 0)) {
                                                createElementVNode("text", utsMapOf("key" to 0, "class" to "original-price"), "¥" + toDisplayString((product.originalPrice as Number).toFixed(2)), 1)
                                            } else {
                                                createCommentVNode("v-if", true)
                                            }
                                        )),
                                        createElementVNode("view", utsMapOf("class" to "stock-row"), utsArrayOf(
                                            createElementVNode("text", utsMapOf("class" to "stock-label"), "库存:"),
                                            createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                                                "stock-value",
                                                utsMapOf("stock-value-low" to (product.totalStock < 10))
                                            ))), toDisplayString(product.totalStock), 3)
                                        ))
                                    )),
                                    createElementVNode("view", utsMapOf("class" to "info-bottom"), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to "sales-text"), "销量: " + toDisplayString(product.sales), 1),
                                        createElementVNode("view", utsMapOf("class" to "action-btns"), utsArrayOf(
                                            if (product.status === 1) {
                                                createElementVNode("view", utsMapOf("key" to 0, "class" to "action-btn", "onClick" to withModifiers(fun(){
                                                    _ctx.handleOffShelf(product)
                                                }, utsArrayOf(
                                                    "stop"
                                                ))), utsArrayOf(
                                                    createElementVNode("text", utsMapOf("class" to "action-btn-text"), "下架")
                                                ), 8, utsArrayOf(
                                                    "onClick"
                                                ))
                                            } else {
                                                createCommentVNode("v-if", true)
                                            }
                                            ,
                                            if (isTrue(product.status === 2 || product.status === 3)) {
                                                createElementVNode("view", utsMapOf("key" to 1, "class" to "action-btn", "onClick" to withModifiers(fun(){
                                                    _ctx.handleOnShelf(product)
                                                }, utsArrayOf(
                                                    "stop"
                                                ))), utsArrayOf(
                                                    createElementVNode("text", utsMapOf("class" to "action-btn-text"), "申请上架")
                                                ), 8, utsArrayOf(
                                                    "onClick"
                                                ))
                                            } else {
                                                createCommentVNode("v-if", true)
                                            }
                                            ,
                                            createElementVNode("view", utsMapOf("class" to "action-btn", "onClick" to withModifiers(fun(){
                                                _ctx.goToReviews(product)
                                            }
                                            , utsArrayOf(
                                                "stop"
                                            ))), utsArrayOf(
                                                createElementVNode("text", utsMapOf("class" to "action-btn-text"), "评价")
                                            ), 8, utsArrayOf(
                                                "onClick"
                                            )),
                                            createElementVNode("view", utsMapOf("class" to "action-btn action-btn-delete", "onClick" to withModifiers(fun(){
                                                _ctx.handleDelete(product)
                                            }
                                            , utsArrayOf(
                                                "stop"
                                            ))), utsArrayOf(
                                                createElementVNode("text", utsMapOf("class" to "action-btn-text action-btn-text-delete"), "删除")
                                            ), 8, utsArrayOf(
                                                "onClick"
                                            ))
                                        ))
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
                        createElementVNode("text", utsMapOf("class" to "empty-text"), "暂无商品"),
                        createElementVNode("view", utsMapOf("class" to "empty-btn", "onClick" to _ctx.goToPublish), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "empty-btn-text"), "去发布商品")
                        ), 8, utsArrayOf(
                            "onClick"
                        ))
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
                createElementVNode("view", utsMapOf("style" to normalizeStyle(utsMapOf("height" to "80px"))), null, 4)
            ), 40, utsArrayOf(
                "onScrolltolower",
                "onRefresherrefresh",
                "refresher-triggered"
            )),
            createElementVNode("view", utsMapOf("class" to "publish-btn-wrapper"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "publish-btn", "onClick" to _ctx.goToPublish), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "publish-icon"), "+"),
                    createElementVNode("text", utsMapOf("class" to "publish-text"), "发布商品")
                ), 8, utsArrayOf(
                    "onClick"
                ))
            ))
        ))
    }
    open var currentStatus: Number? by `$data`
    open var searchKeyword: String by `$data`
    open var isLoading: Boolean by `$data`
    open var isRefreshing: Boolean by `$data`
    open var hasMore: Boolean by `$data`
    open var pageNum: Number by `$data`
    open var pageSize: Number by `$data`
    open var products: UTSArray<SellerProductType> by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return utsMapOf("currentStatus" to null as Number?, "searchKeyword" to "", "isLoading" to false, "isRefreshing" to false, "hasMore" to true, "pageNum" to 1, "pageSize" to 10, "products" to utsArrayOf<SellerProductType>())
    }
    open var loadProducts = ::gen_loadProducts_fn
    open fun gen_loadProducts_fn() {
        this.isLoading = true
        val params = ProductQueryParams(pageNum = this.pageNum, pageSize = this.pageSize, status = this.currentStatus, keyword = if (this.searchKeyword !== "") {
            this.searchKeyword
        } else {
            null
        }
        , categoryId = null)
        getSellerProducts(params).then(fun(res){
            val data = res.data as UTSJSONObject
            val total = (data.getNumber("total") ?: 0).toInt()
            val productList = this.parseProductsFromData(data)
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
            console.error("获取商品列表失败:", err, " at pages/seller/products.uvue:194")
            this.isLoading = false
            this.isRefreshing = false
            uni_showToast(ShowToastOptions(title = "获取商品失败", icon = "none"))
        }
        )
    }
    open var parseProductsFromData = ::gen_parseProductsFromData_fn
    open fun gen_parseProductsFromData_fn(data: UTSJSONObject): UTSArray<SellerProductType> {
        val records = data.getArray("records")
        if (records == null) {
            return utsArrayOf<SellerProductType>()
        }
        val productList: UTSArray<SellerProductType> = utsArrayOf()
        run {
            var i: Number = 0
            while(i < records.length){
                val item = records[i] as UTSJSONObject
                productList.push(SellerProductType(id = (item.getNumber("id") ?: 0).toInt(), productName = item.getString("productName") ?: "", mainImage = item.getString("mainImage") ?: "", currentPrice = item.getNumber("currentPrice") ?: 0, originalPrice = item.getNumber("originalPrice"), totalStock = (item.getNumber("stock") ?: item.getNumber("totalStock") ?: 0).toInt(), sales = (item.getNumber("sales") ?: item.getNumber("salesVolume") ?: 0).toInt(), status = (item.getNumber("status") ?: 0).toInt(), categoryId = (item.getNumber("categoryId") ?: 0).toInt(), categoryName = item.getString("categoryName")))
                i++
            }
        }
        return productList
    }
    open var switchStatus = ::gen_switchStatus_fn
    open fun gen_switchStatus_fn(status: Number?) {
        if (this.currentStatus === status) {
            return
        }
        this.currentStatus = status
        this.pageNum = 1
        this.products = utsArrayOf()
        this.loadProducts()
    }
    open var handleSearch = ::gen_handleSearch_fn
    open fun gen_handleSearch_fn() {
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
        val classMap = Map<Number, String>(utsArrayOf(
            utsArrayOf(
                0,
                "status-tag-pending"
            ),
            utsArrayOf(
                1,
                "status-tag-online"
            ),
            utsArrayOf(
                2,
                "status-tag-offline"
            ),
            utsArrayOf(
                3,
                "status-tag-rejected"
            )
        ))
        return classMap.get(status) ?: ""
    }
    open var getStatusTextClass = ::gen_getStatusTextClass_fn
    open fun gen_getStatusTextClass_fn(status: Number): String {
        val classMap = Map<Number, String>(utsArrayOf(
            utsArrayOf(
                0,
                "status-text-pending"
            ),
            utsArrayOf(
                1,
                "status-text-online"
            ),
            utsArrayOf(
                2,
                "status-text-offline"
            ),
            utsArrayOf(
                3,
                "status-text-rejected"
            )
        ))
        return classMap.get(status) ?: ""
    }
    open var getStatusText = ::gen_getStatusText_fn
    open fun gen_getStatusText_fn(status: Number): String {
        val textMap = Map<Number, String>(utsArrayOf(
            utsArrayOf(
                0,
                "待审核"
            ),
            utsArrayOf(
                1,
                "已上架"
            ),
            utsArrayOf(
                2,
                "已下架"
            ),
            utsArrayOf(
                3,
                "审核不通过"
            )
        ))
        return textMap.get(status) ?: "未知"
    }
    open var goToEdit = ::gen_goToEdit_fn
    open fun gen_goToEdit_fn(productId: Number) {
        uni_navigateTo(NavigateToOptions(url = "/pages/seller/product-edit?id=" + productId))
    }
    open var goToPublish = ::gen_goToPublish_fn
    open fun gen_goToPublish_fn() {
        uni_navigateTo(NavigateToOptions(url = "/pages/seller/product-edit"))
    }
    open var goToReviews = ::gen_goToReviews_fn
    open fun gen_goToReviews_fn(product: SellerProductType) {
        uni_navigateTo(NavigateToOptions(url = "/pages/seller/reviews?productId=" + product.id + "&productName=" + product.productName))
    }
    open var handleOffShelf = ::gen_handleOffShelf_fn
    open fun gen_handleOffShelf_fn(product: SellerProductType) {
        uni_showModal(ShowModalOptions(title = "提示", content = "确定要下架该商品吗？", success = fun(res){
            if (res.confirm) {
                updateProductStatus(product.id, 2).then(fun(){
                    product.status = 2
                    uni_showToast(ShowToastOptions(title = "下架成功", icon = "success"))
                }
                ).`catch`(fun(err){
                    console.error("下架失败:", err, " at pages/seller/products.uvue:315")
                    uni_showToast(ShowToastOptions(title = "下架失败", icon = "none"))
                }
                )
            }
        }
        ))
    }
    open var handleOnShelf = ::gen_handleOnShelf_fn
    open fun gen_handleOnShelf_fn(product: SellerProductType) {
        uni_showModal(ShowModalOptions(title = "提示", content = "确定要重新申请上架该商品吗？提交后需经管理员审核。", success = fun(res){
            if (res.confirm) {
                uni_showLoading(ShowLoadingOptions(title = "提交中..."))
                updateProductStatus(product.id, 1).then(fun(){
                    uni_hideLoading()
                    product.status = 0
                    uni_showToast(ShowToastOptions(title = "申请已提交", icon = "success"))
                }
                ).`catch`(fun(err){
                    uni_hideLoading()
                    console.error("申请上架失败:", err, " at pages/seller/products.uvue:337")
                    uni_showToast(ShowToastOptions(title = "提交失败", icon = "none"))
                }
                )
            }
        }
        ))
    }
    open var handleDelete = ::gen_handleDelete_fn
    open fun gen_handleDelete_fn(product: SellerProductType) {
        uni_showModal(ShowModalOptions(title = "提示", content = "确定要删除该商品吗？删除后不可恢复。", success = fun(res){
            if (res.confirm) {
                deleteProduct(product.id).then(fun(){
                    val index = this.products.findIndex(fun(p: SellerProductType): Boolean {
                        return p.id === product.id
                    }
                    )
                    if (index !== -1) {
                        this.products.splice(index, 1)
                    }
                    uni_showToast(ShowToastOptions(title = "删除成功", icon = "success"))
                }
                ).`catch`(fun(err){
                    console.error("删除失败:", err, " at pages/seller/products.uvue:359")
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
                return utsMapOf("page" to padStyleMapOf(utsMapOf("backgroundColor" to "#f7f8fa", "display" to "flex", "flexDirection" to "column", "flex" to 1)), "filter-header" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "paddingTop" to CSS_VAR_STATUS_BAR_HEIGHT, "boxShadow" to "0 1px 4px rgba(0, 0, 0, 0.05)", "zIndex" to 100)), "status-scroll" to padStyleMapOf(utsMapOf("whiteSpace" to "nowrap", "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "#f5f5f5")), "status-tabs" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "paddingTop" to 0, "paddingRight" to 8, "paddingBottom" to 0, "paddingLeft" to 8)), "tab-item" to padStyleMapOf(utsMapOf("position" to "relative", "paddingTop" to 12, "paddingRight" to 16, "paddingBottom" to 12, "paddingLeft" to 16, "display" to "flex", "flexDirection" to "column", "alignItems" to "center")), "tab-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#666666")), "tab-text--active" to padStyleMapOf(utsMapOf("color" to "#0066CC", "fontWeight" to "700")), "active-line" to padStyleMapOf(utsMapOf("position" to "absolute", "bottom" to 4, "width" to 20, "height" to 3, "backgroundColor" to "#0066CC", "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "search-bar" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "marginTop" to 10, "marginRight" to 16, "marginBottom" to 10, "marginLeft" to 16, "paddingTop" to 0, "paddingRight" to 12, "paddingBottom" to 0, "paddingLeft" to 12, "height" to 36, "backgroundColor" to "#f5f5f5", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18)), "search-icon" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#999999", "marginRight" to 8)), "search-input" to padStyleMapOf(utsMapOf("flex" to 1, "fontSize" to 14, "color" to "#333333")), "search-btn" to padStyleMapOf(utsMapOf("paddingTop" to 4, "paddingRight" to 12, "paddingBottom" to 4, "paddingLeft" to 12, "backgroundColor" to "#0066CC", "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "marginLeft" to 8)), "search-btn-text" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#ffffff")), "product-scroll" to padStyleMapOf(utsMapOf("flex" to 1, "width" to "100%")), "loading-state" to padStyleMapOf(utsMapOf("paddingTop" to 60, "paddingRight" to 0, "paddingBottom" to 60, "paddingLeft" to 0, "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "loading-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#999999")), "product-list" to padStyleMapOf(utsMapOf("paddingTop" to 12, "paddingRight" to 16, "paddingBottom" to 12, "paddingLeft" to 16, "display" to "flex", "flexDirection" to "column")), "product-card" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "backgroundColor" to "#ffffff", "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "marginBottom" to 12, "boxShadow" to "0 2px 8px rgba(0, 0, 0, 0.02)")), "product-img" to padStyleMapOf(utsMapOf("width" to 90, "height" to 90, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#f5f5f5", "flexShrink" to 0)), "product-info" to padStyleMapOf(utsMapOf("flex" to 1, "marginLeft" to 12, "display" to "flex", "flexDirection" to "column", "justifyContent" to "space-between")), "info-top" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "flex-start")), "product-name" to padStyleMapOf(utsMapOf("flex" to 1, "fontSize" to 14, "fontWeight" to "700", "color" to "#333333", "lineHeight" to 1.4, "marginRight" to 8, "overflow" to "hidden", "textOverflow" to "ellipsis")), "status-tag" to padStyleMapOf(utsMapOf("paddingTop" to 2, "paddingRight" to 8, "paddingBottom" to 2, "paddingLeft" to 8, "borderTopLeftRadius" to 4, "borderTopRightRadius" to 4, "borderBottomRightRadius" to 4, "borderBottomLeftRadius" to 4, "flexShrink" to 0)), "status-tag-pending" to padStyleMapOf(utsMapOf("backgroundColor" to "#fff7e6")), "status-tag-online" to padStyleMapOf(utsMapOf("backgroundColor" to "#e6f7ff")), "status-tag-offline" to padStyleMapOf(utsMapOf("backgroundColor" to "#f5f5f5")), "status-tag-rejected" to padStyleMapOf(utsMapOf("backgroundColor" to "#fff1f0")), "status-text" to padStyleMapOf(utsMapOf("fontSize" to 11)), "status-text-pending" to padStyleMapOf(utsMapOf("color" to "#fa8c16")), "status-text-online" to padStyleMapOf(utsMapOf("color" to "#1890ff")), "status-text-offline" to padStyleMapOf(utsMapOf("color" to "#999999")), "status-text-rejected" to padStyleMapOf(utsMapOf("color" to "#ff4d4f")), "info-middle" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "marginTop" to 8, "marginRight" to 0, "marginBottom" to 8, "marginLeft" to 0)), "price-row" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "flex-end")), "currency" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#ff4d4f", "fontWeight" to "700")), "price" to padStyleMapOf(utsMapOf("fontSize" to 16, "color" to "#ff4d4f", "fontWeight" to "700")), "original-price" to padStyleMapOf(utsMapOf("fontSize" to 11, "color" to "#999999", "marginLeft" to 6)), "stock-row" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center")), "stock-label" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999")), "stock-value" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#333333", "marginLeft" to 4)), "stock-value-low" to padStyleMapOf(utsMapOf("color" to "#ff4d4f")), "info-bottom" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center")), "sales-text" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999")), "action-btns" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row")), "action-btn" to padStyleMapOf(utsMapOf("paddingTop" to 4, "paddingRight" to 12, "paddingBottom" to 4, "paddingLeft" to 12, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#0066CC", "borderRightColor" to "#0066CC", "borderBottomColor" to "#0066CC", "borderLeftColor" to "#0066CC", "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "marginLeft" to 8)), "action-btn-delete" to padStyleMapOf(utsMapOf("borderTopColor" to "#ff4d4f", "borderRightColor" to "#ff4d4f", "borderBottomColor" to "#ff4d4f", "borderLeftColor" to "#ff4d4f")), "action-btn-text" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#0066CC")), "action-btn-text-delete" to padStyleMapOf(utsMapOf("color" to "#ff4d4f")), "empty-state" to padStyleMapOf(utsMapOf("paddingTop" to 80, "paddingRight" to 0, "paddingBottom" to 80, "paddingLeft" to 0, "display" to "flex", "flexDirection" to "column", "alignItems" to "center", "justifyContent" to "center")), "empty-icon" to padStyleMapOf(utsMapOf("fontSize" to 48, "marginBottom" to 16, "color" to "#cccccc")), "empty-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#999999", "marginBottom" to 20)), "empty-btn" to padStyleMapOf(utsMapOf("paddingTop" to 10, "paddingRight" to 24, "paddingBottom" to 10, "paddingLeft" to 24, "backgroundColor" to "#0066CC", "borderTopLeftRadius" to 20, "borderTopRightRadius" to 20, "borderBottomRightRadius" to 20, "borderBottomLeftRadius" to 20)), "empty-btn-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#ffffff")), "load-more" to padStyleMapOf(utsMapOf("paddingTop" to 20, "paddingRight" to 0, "paddingBottom" to 20, "paddingLeft" to 0, "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "load-more-text" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999")), "publish-btn-wrapper" to padStyleMapOf(utsMapOf("position" to "fixed", "bottom" to 20, "left" to 0, "right" to 0, "display" to "flex", "justifyContent" to "center", "zIndex" to 100)), "publish-btn" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center", "paddingTop" to 12, "paddingRight" to 32, "paddingBottom" to 12, "paddingLeft" to 32, "backgroundColor" to "#0066CC", "borderTopLeftRadius" to 24, "borderTopRightRadius" to 24, "borderBottomRightRadius" to 24, "borderBottomLeftRadius" to 24, "boxShadow" to "0 4px 12px rgba(0, 102, 204, 0.3)")), "publish-icon" to padStyleMapOf(utsMapOf("fontSize" to 18, "color" to "#ffffff", "marginRight" to 6, "fontWeight" to "700")), "publish-text" to padStyleMapOf(utsMapOf("fontSize" to 15, "color" to "#ffffff", "fontWeight" to "700")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = utsMapOf()
        var emits: Map<String, Any?> = utsMapOf()
        var props = normalizePropsOptions(utsMapOf())
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf()
        var components: Map<String, CreateVueComponent> = utsMapOf()
    }
}
