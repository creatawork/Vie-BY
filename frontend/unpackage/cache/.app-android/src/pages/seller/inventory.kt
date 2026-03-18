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
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesSellerInventory : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onLoad(fun(_: OnLoadOptions) {
            this.loadInventory()
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return createElementVNode("view", utsMapOf("class" to "page"), utsArrayOf(
            createElementVNode("view", utsMapOf("class" to "filter-header"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "filter-row"), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "filter-left"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "filter-title"), "库存管理")
                    )),
                    createElementVNode("view", utsMapOf("class" to "filter-right"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "low-stock-switch", "onClick" to _ctx.toggleLowStock), utsArrayOf(
                            createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                                "switch-box",
                                utsMapOf("switch-box-active" to _ctx.showLowStock)
                            ))), utsArrayOf(
                                createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                                    "switch-dot",
                                    utsMapOf("switch-dot-active" to _ctx.showLowStock)
                                ))), null, 2)
                            ), 2),
                            createElementVNode("text", utsMapOf("class" to "switch-label"), "仅显示低库存")
                        ), 8, utsArrayOf(
                            "onClick"
                        ))
                    ))
                ))
            )),
            createElementVNode("scroll-view", utsMapOf("class" to "inventory-scroll", "scroll-y" to "true", "onScrolltolower" to _ctx.loadMore, "onRefresherrefresh" to _ctx.onRefresh, "refresher-enabled" to true, "refresher-triggered" to _ctx.isRefreshing, "show-scrollbar" to false), utsArrayOf(
                if (isTrue(_ctx.isLoading && _ctx.inventoryList.length === 0)) {
                    createElementVNode("view", utsMapOf("key" to 0, "class" to "loading-state"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "loading-text"), "加载中...")
                    ))
                } else {
                    createElementVNode("view", utsMapOf("key" to 1, "class" to "inventory-list"), utsArrayOf(
                        createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.inventoryList, fun(item, __key, __index, _cached): Any {
                            return createElementVNode("view", utsMapOf("class" to "inventory-card", "key" to item.skuId), utsArrayOf(
                                createElementVNode("view", utsMapOf("class" to "card-header"), utsArrayOf(
                                    createElementVNode("image", utsMapOf("class" to "product-img", "src" to item.mainImage, "mode" to "aspectFill"), null, 8, utsArrayOf(
                                        "src"
                                    )),
                                    createElementVNode("view", utsMapOf("class" to "product-info"), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to "product-name"), toDisplayString(item.productName), 1),
                                        createElementVNode("text", utsMapOf("class" to "sku-name"), "规格: " + toDisplayString(item.skuName), 1)
                                    ))
                                )),
                                createElementVNode("view", utsMapOf("class" to "card-body"), utsArrayOf(
                                    createElementVNode("view", utsMapOf("class" to "stock-info"), utsArrayOf(
                                        createElementVNode("view", utsMapOf("class" to "info-item"), utsArrayOf(
                                            createElementVNode("text", utsMapOf("class" to "info-label"), "当前库存"),
                                            createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                                                "info-value",
                                                utsMapOf("info-value-low-stock" to (item.stock < 10))
                                            ))), toDisplayString(item.stock), 3)
                                        )),
                                        createElementVNode("view", utsMapOf("class" to "info-item"), utsArrayOf(
                                            createElementVNode("text", utsMapOf("class" to "info-label"), "销量"),
                                            createElementVNode("text", utsMapOf("class" to "info-value"), toDisplayString(item.sales), 1)
                                        )),
                                        createElementVNode("view", utsMapOf("class" to "info-item"), utsArrayOf(
                                            createElementVNode("text", utsMapOf("class" to "info-label"), "价格"),
                                            createElementVNode("text", utsMapOf("class" to "info-value info-value-price"), "¥" + toDisplayString(item.price.toFixed(2)), 1)
                                        ))
                                    )),
                                    createElementVNode("view", utsMapOf("class" to "action-area"), utsArrayOf(
                                        createElementVNode("view", utsMapOf("class" to "adjust-btn", "onClick" to fun(){
                                            _ctx.openAdjustModal(item)
                                        }
                                        ), utsArrayOf(
                                            createElementVNode("text", utsMapOf("class" to "adjust-btn-text"), "调整库存")
                                        ), 8, utsArrayOf(
                                            "onClick"
                                        ))
                                    ))
                                ))
                            ))
                        }
                        ), 128)
                    ))
                }
                ,
                if (isTrue(!_ctx.isLoading && _ctx.inventoryList.length === 0)) {
                    createElementVNode("view", utsMapOf("key" to 2, "class" to "empty-state"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "empty-icon"), "📦"),
                        createElementVNode("text", utsMapOf("class" to "empty-text"), toDisplayString(if (_ctx.showLowStock) {
                            "暂无低库存商品"
                        } else {
                            "暂无库存数据"
                        }), 1)
                    ))
                } else {
                    createCommentVNode("v-if", true)
                }
                ,
                if (_ctx.inventoryList.length > 0) {
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
            )),
            if (isTrue(_ctx.showAdjustModal)) {
                createElementVNode("view", utsMapOf("key" to 0, "class" to "modal-mask", "onClick" to _ctx.closeAdjustModal), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "modal-content", "onClick" to withModifiers(fun(){}, utsArrayOf(
                        "stop"
                    ))), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "modal-header"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "modal-title"), "调整库存"),
                            createElementVNode("view", utsMapOf("class" to "modal-close", "onClick" to _ctx.closeAdjustModal), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "close-icon"), "×")
                            ), 8, utsArrayOf(
                                "onClick"
                            ))
                        )),
                        createElementVNode("view", utsMapOf("class" to "modal-body"), utsArrayOf(
                            if (_ctx.currentItem != null) {
                                createElementVNode("view", utsMapOf("key" to 0, "class" to "adjust-product-info"), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "adjust-product-name"), toDisplayString(_ctx.currentItem!!.productName), 1),
                                    createElementVNode("text", utsMapOf("class" to "adjust-sku-name"), "规格: " + toDisplayString(_ctx.currentItem!!.skuName), 1),
                                    createElementVNode("view", utsMapOf("class" to "current-stock-row"), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to "current-stock-label"), "当前库存:"),
                                        createElementVNode("text", utsMapOf("class" to "current-stock-value"), toDisplayString(_ctx.currentItem!!.stock), 1)
                                    ))
                                ))
                            } else {
                                createCommentVNode("v-if", true)
                            },
                            createElementVNode("view", utsMapOf("class" to "adjust-type-section"), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "section-label"), "调整类型"),
                                createElementVNode("view", utsMapOf("class" to "type-options"), utsArrayOf(
                                    createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                                        "type-option type-option-first",
                                        utsMapOf("type-option-active" to (_ctx.adjustType === "ADD"))
                                    )), "onClick" to fun(){
                                        _ctx.selectAdjustType("ADD")
                                    }), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                                            "type-text",
                                            utsMapOf("type-text-active" to (_ctx.adjustType === "ADD"))
                                        ))), "增加", 2)
                                    ), 10, utsArrayOf(
                                        "onClick"
                                    )),
                                    createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                                        "type-option",
                                        utsMapOf("type-option-active" to (_ctx.adjustType === "REDUCE"))
                                    )), "onClick" to fun(){
                                        _ctx.selectAdjustType("REDUCE")
                                    }), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                                            "type-text",
                                            utsMapOf("type-text-active" to (_ctx.adjustType === "REDUCE"))
                                        ))), "减少", 2)
                                    ), 10, utsArrayOf(
                                        "onClick"
                                    )),
                                    createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                                        "type-option type-option-last",
                                        utsMapOf("type-option-active" to (_ctx.adjustType === "SET"))
                                    )), "onClick" to fun(){
                                        _ctx.selectAdjustType("SET")
                                    }), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                                            "type-text",
                                            utsMapOf("type-text-active" to (_ctx.adjustType === "SET"))
                                        ))), "设置", 2)
                                    ), 10, utsArrayOf(
                                        "onClick"
                                    ))
                                ))
                            )),
                            createElementVNode("view", utsMapOf("class" to "adjust-quantity-section"), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "section-label"), toDisplayString(if (_ctx.adjustType === "SET") {
                                    "设置为"
                                } else {
                                    "调整数量"
                                }), 1),
                                createElementVNode("view", utsMapOf("class" to "quantity-input-row"), utsArrayOf(
                                    createElementVNode("input", utsMapOf("class" to "quantity-input", "type" to "number", "modelValue" to _ctx.adjustQuantity, "onInput" to fun(`$event`: InputEvent){
                                        _ctx.adjustQuantity = `$event`.detail.value
                                    }, "placeholder" to "请输入数量"), null, 40, utsArrayOf(
                                        "modelValue",
                                        "onInput"
                                    ))
                                ))
                            )),
                            if (isTrue(_ctx.currentItem != null && _ctx.adjustQuantity !== "")) {
                                createElementVNode("view", utsMapOf("key" to 1, "class" to "preview-section"), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "preview-label"), "调整后库存:"),
                                    createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                                        "preview-value",
                                        utsMapOf("preview-value-low-stock" to (_ctx.getPreviewStock() < 10), "preview-value-error" to (_ctx.getPreviewStock() < 0))
                                    ))), toDisplayString(_ctx.getPreviewStock()), 3),
                                    if (_ctx.getPreviewStock() < 0) {
                                        createElementVNode("text", utsMapOf("key" to 0, "class" to "preview-error"), "库存不能为负数")
                                    } else {
                                        createCommentVNode("v-if", true)
                                    }
                                ))
                            } else {
                                createCommentVNode("v-if", true)
                            },
                            createElementVNode("view", utsMapOf("class" to "remark-section"), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "section-label"), "备注（可选）"),
                                createElementVNode("input", utsMapOf("class" to "remark-input", "modelValue" to _ctx.adjustRemark, "onInput" to fun(`$event`: InputEvent){
                                    _ctx.adjustRemark = `$event`.detail.value
                                }, "placeholder" to "请输入调整原因"), null, 40, utsArrayOf(
                                    "modelValue",
                                    "onInput"
                                ))
                            ))
                        )),
                        createElementVNode("view", utsMapOf("class" to "modal-footer"), utsArrayOf(
                            createElementVNode("view", utsMapOf("class" to "cancel-btn", "onClick" to _ctx.closeAdjustModal), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "cancel-btn-text"), "取消")
                            ), 8, utsArrayOf(
                                "onClick"
                            )),
                            createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                                "confirm-btn",
                                utsMapOf("confirm-btn-disabled" to !_ctx.canSubmit)
                            )), "onClick" to _ctx.submitAdjust), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "confirm-btn-text"), "确认调整")
                            ), 10, utsArrayOf(
                                "onClick"
                            ))
                        ))
                    ), 8, utsArrayOf(
                        "onClick"
                    ))
                ), 8, utsArrayOf(
                    "onClick"
                ))
            } else {
                createCommentVNode("v-if", true)
            }
        ))
    }
    open var showLowStock: Boolean by `$data`
    open var isLoading: Boolean by `$data`
    open var isRefreshing: Boolean by `$data`
    open var hasMore: Boolean by `$data`
    open var pageNum: Number by `$data`
    open var pageSize: Number by `$data`
    open var inventoryList: UTSArray<InventoryItemType> by `$data`
    open var showAdjustModal: Boolean by `$data`
    open var currentItem: InventoryItemType? by `$data`
    open var adjustType: String by `$data`
    open var adjustQuantity: String by `$data`
    open var adjustRemark: String by `$data`
    open var canSubmit: Boolean by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return utsMapOf("showLowStock" to false, "isLoading" to false, "isRefreshing" to false, "hasMore" to true, "pageNum" to 1, "pageSize" to 10, "inventoryList" to utsArrayOf<InventoryItemType>(), "showAdjustModal" to false, "currentItem" to null as InventoryItemType?, "adjustType" to "ADD" as String, "adjustQuantity" to "", "adjustRemark" to "", "canSubmit" to computed<Boolean>(fun(): Boolean {
            if (this.adjustQuantity === "") {
                return false
            }
            val qty = parseInt(this.adjustQuantity)
            if (isNaN(qty) || qty < 0) {
                return false
            }
            if (this.getPreviewStock() < 0) {
                return false
            }
            return true
        }
        ))
    }
    open var loadInventory = ::gen_loadInventory_fn
    open fun gen_loadInventory_fn() {
        this.isLoading = true
        val params = InventoryQueryParams(pageNum = this.pageNum, pageSize = this.pageSize, productId = null, lowStock = if (this.showLowStock) {
            true
        } else {
            null
        }
        )
        getInventoryList(params).then(fun(res){
            val data = res.data as UTSJSONObject
            val total = (data.getNumber("total") ?: 0).toInt()
            val itemList = this.parseInventoryFromData(data)
            if (this.pageNum === 1) {
                this.inventoryList = itemList
            } else {
                this.inventoryList = this.inventoryList.concat(itemList)
            }
            this.hasMore = this.inventoryList.length < total
            this.isLoading = false
            this.isRefreshing = false
        }
        ).`catch`(fun(err){
            console.error("获取库存列表失败:", err, " at pages/seller/inventory.uvue:236")
            this.isLoading = false
            this.isRefreshing = false
            uni_showToast(ShowToastOptions(title = "获取库存失败", icon = "none"))
        }
        )
    }
    open var parseInventoryFromData = ::gen_parseInventoryFromData_fn
    open fun gen_parseInventoryFromData_fn(data: UTSJSONObject): UTSArray<InventoryItemType> {
        val records = data.getArray("records")
        if (records == null) {
            return utsArrayOf<InventoryItemType>()
        }
        val itemList: UTSArray<InventoryItemType> = utsArrayOf()
        run {
            var i: Number = 0
            while(i < records.length){
                val product = records[i] as UTSJSONObject
                val productId = (product.getNumber("productId") ?: 0).toInt()
                val productName = product.getString("productName") ?: ""
                val mainImage = product.getString("mainImage") ?: ""
                val skuList = product.getArray("skuList")
                if (skuList != null) {
                    run {
                        var j: Number = 0
                        while(j < skuList.length){
                            val sku = skuList[j] as UTSJSONObject
                            itemList.push(InventoryItemType(skuId = (sku.getNumber("skuId") ?: 0).toInt(), productId = productId, productName = productName, mainImage = mainImage, skuName = sku.getString("skuName") ?: "默认规格", price = sku.getNumber("price") ?: 0, stock = (sku.getNumber("stock") ?: 0).toInt(), sales = (sku.getNumber("salesVolume") ?: 0).toInt()))
                            j++
                        }
                    }
                }
                i++
            }
        }
        return itemList
    }
    open var toggleLowStock = ::gen_toggleLowStock_fn
    open fun gen_toggleLowStock_fn() {
        this.showLowStock = !this.showLowStock
        this.pageNum = 1
        this.inventoryList = utsArrayOf()
        this.loadInventory()
    }
    open var onRefresh = ::gen_onRefresh_fn
    open fun gen_onRefresh_fn() {
        this.isRefreshing = true
        this.pageNum = 1
        this.loadInventory()
    }
    open var loadMore = ::gen_loadMore_fn
    open fun gen_loadMore_fn() {
        if (!this.hasMore || this.isLoading) {
            return
        }
        this.pageNum++
        this.loadInventory()
    }
    open var openAdjustModal = ::gen_openAdjustModal_fn
    open fun gen_openAdjustModal_fn(item: InventoryItemType) {
        this.currentItem = item
        this.adjustType = "ADD"
        this.adjustQuantity = ""
        this.adjustRemark = ""
        this.showAdjustModal = true
    }
    open var closeAdjustModal = ::gen_closeAdjustModal_fn
    open fun gen_closeAdjustModal_fn() {
        this.showAdjustModal = false
        this.currentItem = null
    }
    open var selectAdjustType = ::gen_selectAdjustType_fn
    open fun gen_selectAdjustType_fn(type: String) {
        this.adjustType = type
    }
    open var getPreviewStock = ::gen_getPreviewStock_fn
    open fun gen_getPreviewStock_fn(): Number {
        if (this.currentItem == null || this.adjustQuantity === "") {
            return 0
        }
        val qty = parseInt(this.adjustQuantity)
        if (isNaN(qty)) {
            return this.currentItem!!.stock
        }
        if (this.adjustType === "ADD") {
            return this.currentItem!!.stock + qty
        } else if (this.adjustType === "REDUCE") {
            return this.currentItem!!.stock - qty
        } else {
            return qty
        }
    }
    open var submitAdjust = ::gen_submitAdjust_fn
    open fun gen_submitAdjust_fn() {
        if (!this.canSubmit || this.currentItem == null) {
            return
        }
        val qty = parseInt(this.adjustQuantity)
        val data = AdjustData(adjustType = this.adjustType, quantity = qty, remark = if (this.adjustRemark !== "") {
            this.adjustRemark
        } else {
            null
        }
        )
        uni_showLoading(ShowLoadingOptions(title = "调整中..."))
        adjustInventory(this.currentItem!!.skuId, data).then(fun(res){
            uni_hideLoading()
            val resData = res.data as UTSJSONObject
            val newStock = (resData.getNumber("afterStock") ?: this.getPreviewStock()).toInt()
            val index = this.inventoryList.findIndex(fun(item: InventoryItemType): Boolean {
                return item.skuId === this.currentItem!!.skuId
            }
            )
            if (index !== -1) {
                this.inventoryList[index].stock = newStock
            }
            this.closeAdjustModal()
            uni_showToast(ShowToastOptions(title = "调整成功", icon = "success"))
        }
        ).`catch`(fun(err){
            uni_hideLoading()
            console.error("库存调整失败:", err, " at pages/seller/inventory.uvue:363")
            uni_showToast(ShowToastOptions(title = "调整失败", icon = "none"))
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
                return utsMapOf("page" to padStyleMapOf(utsMapOf("backgroundColor" to "#f7f8fa", "display" to "flex", "flexDirection" to "column", "flex" to 1)), "filter-header" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "paddingTop" to CSS_VAR_STATUS_BAR_HEIGHT, "boxShadow" to "0 1px 4px rgba(0, 0, 0, 0.05)", "zIndex" to 100)), "filter-row" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "paddingTop" to 12, "paddingRight" to 16, "paddingBottom" to 12, "paddingLeft" to 16)), "filter-left" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center")), "filter-title" to padStyleMapOf(utsMapOf("fontSize" to 16, "fontWeight" to "700", "color" to "#333333")), "filter-right" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center")), "low-stock-switch" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center")), "switch-box" to padStyleMapOf(utsMapOf("width" to 40, "height" to 22, "backgroundColor" to "#dddddd", "borderTopLeftRadius" to 11, "borderTopRightRadius" to 11, "borderBottomRightRadius" to 11, "borderBottomLeftRadius" to 11, "position" to "relative")), "switch-box-active" to padStyleMapOf(utsMapOf("backgroundColor" to "#0066CC")), "switch-dot" to padStyleMapOf(utsMapOf("width" to 18, "height" to 18, "backgroundColor" to "#ffffff", "borderTopLeftRadius" to 9, "borderTopRightRadius" to 9, "borderBottomRightRadius" to 9, "borderBottomLeftRadius" to 9, "position" to "absolute", "top" to 2, "left" to 2)), "switch-dot-active" to padStyleMapOf(utsMapOf("left" to 20)), "switch-label" to padStyleMapOf(utsMapOf("fontSize" to 13, "color" to "#666666", "marginLeft" to 8)), "inventory-scroll" to padStyleMapOf(utsMapOf("flex" to 1, "width" to "100%")), "loading-state" to padStyleMapOf(utsMapOf("paddingTop" to 60, "paddingRight" to 0, "paddingBottom" to 60, "paddingLeft" to 0, "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "loading-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#999999")), "inventory-list" to padStyleMapOf(utsMapOf("paddingTop" to 12, "paddingRight" to 16, "paddingBottom" to 12, "paddingLeft" to 16, "display" to "flex", "flexDirection" to "column")), "inventory-card" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "marginBottom" to 12, "boxShadow" to "0 2px 8px rgba(0, 0, 0, 0.02)")), "card-header" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "paddingBottom" to 12, "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "#f5f5f5")), "product-img" to padStyleMapOf(utsMapOf("width" to 60, "height" to 60, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#f5f5f5", "flexShrink" to 0)), "product-info" to padStyleMapOf(utsMapOf("flex" to 1, "marginLeft" to 12, "display" to "flex", "flexDirection" to "column", "justifyContent" to "center")), "product-name" to padStyleMapOf(utsMapOf("fontSize" to 14, "fontWeight" to "700", "color" to "#333333", "lineHeight" to 1.4, "overflow" to "hidden", "textOverflow" to "ellipsis")), "sku-name" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999", "marginTop" to 4)), "card-body" to padStyleMapOf(utsMapOf("paddingTop" to 12)), "stock-info" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between")), "info-item" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "column", "alignItems" to "center", "flex" to 1)), "info-label" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999")), "info-value" to padStyleMapOf(utsMapOf("fontSize" to 16, "fontWeight" to "700", "color" to "#333333", "marginTop" to 4)), "info-value-low-stock" to padStyleMapOf(utsMapOf("color" to "#ff4d4f")), "info-value-price" to padStyleMapOf(utsMapOf("color" to "#ff4d4f", "fontSize" to 14)), "action-area" to padStyleMapOf(utsMapOf("display" to "flex", "justifyContent" to "flex-end", "marginTop" to 12)), "adjust-btn" to padStyleMapOf(utsMapOf("paddingTop" to 6, "paddingRight" to 16, "paddingBottom" to 6, "paddingLeft" to 16, "backgroundColor" to "#0066CC", "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16)), "adjust-btn-text" to padStyleMapOf(utsMapOf("fontSize" to 13, "color" to "#ffffff")), "empty-state" to padStyleMapOf(utsMapOf("paddingTop" to 80, "paddingRight" to 0, "paddingBottom" to 80, "paddingLeft" to 0, "display" to "flex", "flexDirection" to "column", "alignItems" to "center", "justifyContent" to "center")), "empty-icon" to padStyleMapOf(utsMapOf("fontSize" to 48, "marginBottom" to 16, "color" to "#cccccc")), "empty-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#999999")), "load-more" to padStyleMapOf(utsMapOf("paddingTop" to 20, "paddingRight" to 0, "paddingBottom" to 20, "paddingLeft" to 0, "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "load-more-text" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999")), "modal-mask" to padStyleMapOf(utsMapOf("position" to "fixed", "top" to 0, "left" to 0, "right" to 0, "bottom" to 0, "backgroundColor" to "rgba(0,0,0,0.5)", "display" to "flex", "alignItems" to "center", "justifyContent" to "center", "zIndex" to 1000)), "modal-content" to padStyleMapOf(utsMapOf("width" to "85%", "maxWidth" to 340, "backgroundColor" to "#ffffff", "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "overflow" to "hidden")), "modal-header" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "#f5f5f5")), "modal-title" to padStyleMapOf(utsMapOf("fontSize" to 16, "fontWeight" to "700", "color" to "#333333")), "modal-close" to padStyleMapOf(utsMapOf("width" to 24, "height" to 24, "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "close-icon" to padStyleMapOf(utsMapOf("fontSize" to 20, "color" to "#999999")), "modal-body" to padStyleMapOf(utsMapOf("paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16)), "adjust-product-info" to padStyleMapOf(utsMapOf("backgroundColor" to "#f7f8fa", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "marginBottom" to 16)), "adjust-product-name" to padStyleMapOf(utsMapOf("fontSize" to 14, "fontWeight" to "700", "color" to "#333333")), "adjust-sku-name" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999", "marginTop" to 4)), "current-stock-row" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "marginTop" to 8)), "current-stock-label" to padStyleMapOf(utsMapOf("fontSize" to 13, "color" to "#666666")), "current-stock-value" to padStyleMapOf(utsMapOf("fontSize" to 16, "fontWeight" to "700", "color" to "#0066CC", "marginLeft" to 8)), "section-label" to padStyleMapOf(utsMapOf("fontSize" to 13, "color" to "#666666", "marginBottom" to 8)), "adjust-type-section" to padStyleMapOf(utsMapOf("marginBottom" to 16)), "type-options" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row")), "type-option" to padStyleMapOf(utsMapOf("flex" to 1, "paddingTop" to 10, "paddingRight" to 0, "paddingBottom" to 10, "paddingLeft" to 0, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#dddddd", "borderRightColor" to "#dddddd", "borderBottomColor" to "#dddddd", "borderLeftColor" to "#dddddd", "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "type-option-first" to padStyleMapOf(utsMapOf("borderTopLeftRadius" to 8, "borderTopRightRadius" to 0, "borderBottomRightRadius" to 0, "borderBottomLeftRadius" to 8)), "type-option-last" to padStyleMapOf(utsMapOf("borderTopLeftRadius" to 0, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 0)), "type-option-active" to padStyleMapOf(utsMapOf("backgroundColor" to "#0066CC", "borderTopColor" to "#0066CC", "borderRightColor" to "#0066CC", "borderBottomColor" to "#0066CC", "borderLeftColor" to "#0066CC")), "type-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#333333")), "type-text-active" to padStyleMapOf(utsMapOf("color" to "#ffffff")), "adjust-quantity-section" to padStyleMapOf(utsMapOf("marginBottom" to 16)), "quantity-input-row" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center")), "quantity-input" to padStyleMapOf(utsMapOf("flex" to 1, "height" to 44, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#dddddd", "borderRightColor" to "#dddddd", "borderBottomColor" to "#dddddd", "borderLeftColor" to "#dddddd", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingTop" to 0, "paddingRight" to 12, "paddingBottom" to 0, "paddingLeft" to 12, "fontSize" to 16, "color" to "#333333")), "preview-section" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "flexWrap" to "wrap", "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "backgroundColor" to "#f7f8fa", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "marginBottom" to 16)), "preview-label" to padStyleMapOf(utsMapOf("fontSize" to 13, "color" to "#666666")), "preview-value" to padStyleMapOf(utsMapOf("fontSize" to 18, "fontWeight" to "700", "color" to "#52c41a", "marginLeft" to 8)), "preview-value-low-stock" to padStyleMapOf(utsMapOf("color" to "#faad14")), "preview-value-error" to padStyleMapOf(utsMapOf("color" to "#ff4d4f")), "preview-error" to padStyleMapOf(utsMapOf("width" to "100%", "fontSize" to 12, "color" to "#ff4d4f", "marginTop" to 4)), "remark-section" to padStyleMapOf(utsMapOf("marginBottom" to 8)), "remark-input" to padStyleMapOf(utsMapOf("height" to 44, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#dddddd", "borderRightColor" to "#dddddd", "borderBottomColor" to "#dddddd", "borderLeftColor" to "#dddddd", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingTop" to 0, "paddingRight" to 12, "paddingBottom" to 0, "paddingLeft" to 12, "fontSize" to 14, "color" to "#333333")), "modal-footer" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "paddingTop" to 12, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "borderTopWidth" to 1, "borderTopStyle" to "solid", "borderTopColor" to "#f5f5f5")), "cancel-btn" to padStyleMapOf(utsMapOf("flex" to 1, "height" to 44, "display" to "flex", "alignItems" to "center", "justifyContent" to "center", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#dddddd", "borderRightColor" to "#dddddd", "borderBottomColor" to "#dddddd", "borderLeftColor" to "#dddddd", "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "marginRight" to 12)), "cancel-btn-text" to padStyleMapOf(utsMapOf("fontSize" to 15, "color" to "#666666")), "confirm-btn" to padStyleMapOf(utsMapOf("flex" to 1, "height" to 44, "display" to "flex", "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "#0066CC", "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22)), "confirm-btn-disabled" to padStyleMapOf(utsMapOf("backgroundColor" to "#cccccc")), "confirm-btn-text" to padStyleMapOf(utsMapOf("fontSize" to 15, "color" to "#ffffff")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = utsMapOf()
        var emits: Map<String, Any?> = utsMapOf()
        var props = normalizePropsOptions(utsMapOf())
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf()
        var components: Map<String, CreateVueComponent> = utsMapOf()
    }
}
