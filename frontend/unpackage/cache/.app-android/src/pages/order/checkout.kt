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
import io.dcloud.uniapp.extapi.`$off` as uni__off
import io.dcloud.uniapp.extapi.`$on` as uni__on
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.redirectTo as uni_redirectTo
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesOrderCheckout : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onPageShow(fun() {
            uni__on("addressSelected", fun(addr: Any){
                val data = addr as UTSJSONObject
                this.selectedAddress = AddressType(id = (data.getNumber("id") ?: 0).toInt(), receiverName = data.getString("receiverName") ?: "", receiverPhone = data.getString("receiverPhone") ?: "", province = data.getString("province") ?: "", city = data.getString("city") ?: "", district = data.getString("district") ?: "", detailAddress = data.getString("detailAddress") ?: "", fullAddress = data.getString("fullAddress") ?: "", isDefault = (data.getNumber("isDefault") ?: 0).toInt())
            }
            )
        }
        , __ins)
        onUnload(fun() {
            uni__off("addressSelected", null)
        }
        , __ins)
        onLoad(fun(options: Any) {
            val opts = options as UTSJSONObject
            val type = opts.get("type") as String?
            if (type === "direct") {
                this.orderType = "direct"
                val productIdStr = opts.get("productId")
                val skuIdStr = opts.get("skuId")
                val quantityStr = opts.get("quantity")
                val productNameStr = opts.get("productName")
                val skuNameStr = opts.get("skuName")
                val priceStr = opts.get("price")
                val productImageStr = opts.get("productImage")
                val productId = parseInt(if (productIdStr != null) {
                    productIdStr as String
                } else {
                    "0"
                })
                val skuId = parseInt(if (skuIdStr != null) {
                    skuIdStr as String
                } else {
                    "0"
                })
                val quantity = parseInt(if (quantityStr != null) {
                    quantityStr as String
                } else {
                    "1"
                })
                val price = parseFloat(if (priceStr != null) {
                    priceStr as String
                } else {
                    "0"
                })
                var productName = ""
                var skuName = ""
                var productImage = ""
                if (productNameStr != null) {
                    val decoded = UTSAndroid.consoleDebugError(decodeURIComponent(productNameStr as String), " at pages/order/checkout.uvue:191")
                    productName = if (decoded != null) {
                        decoded
                    } else {
                        ""
                    }
                }
                if (skuNameStr != null) {
                    val decoded = UTSAndroid.consoleDebugError(decodeURIComponent(skuNameStr as String), " at pages/order/checkout.uvue:195")
                    skuName = if (decoded != null) {
                        decoded
                    } else {
                        ""
                    }
                }
                if (productImageStr != null) {
                    val decoded = UTSAndroid.consoleDebugError(decodeURIComponent(productImageStr as String), " at pages/order/checkout.uvue:199")
                    productImage = if (decoded != null) {
                        decoded
                    } else {
                        ""
                    }
                }
                this.directBuyItem = DirectBuyItem(productId = productId, skuId = skuId, quantity = quantity)
                this.orderGoods = utsArrayOf(
                    OrderGoodsType(cartId = 0, productId = productId, skuId = skuId, productName = productName, productImage = productImage, skuName = skuName, price = price, quantity = quantity)
                )
                this.isLoading = false
            } else {
                this.orderType = "cart"
                val cartIds = opts.get("cartIds") as String?
                if (cartIds != null && cartIds !== "") {
                    this.cartItemIds = cartIds.split(",").map(fun(id: String): Number {
                        return parseInt(id)
                    }
                    )
                }
                this.loadCartGoods()
            }
            this.loadDefaultAddress()
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return createElementVNode("view", utsMapOf("class" to "checkout-page"), utsArrayOf(
            createElementVNode("view", utsMapOf("class" to "header"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "back-btn", "onClick" to _ctx.goBack), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "back-icon"), "←")
                ), 8, utsArrayOf(
                    "onClick"
                )),
                createElementVNode("text", utsMapOf("class" to "page-title"), "确认订单"),
                createElementVNode("view", utsMapOf("class" to "placeholder"))
            )),
            if (isTrue(_ctx.isLoading)) {
                createElementVNode("view", utsMapOf("key" to 0, "class" to "loading-state"), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "loading-text"), "加载中...")
                ))
            } else {
                createElementVNode("scroll-view", utsMapOf("key" to 1, "class" to "content-scroll", "scroll-y" to "true"), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "address-section", "onClick" to _ctx.selectAddress), utsArrayOf(
                        if (_ctx.selectedAddress != null) {
                            createElementVNode("view", utsMapOf("key" to 0, "class" to "address-card"), utsArrayOf(
                                createElementVNode("view", utsMapOf("class" to "address-icon"), "📍"),
                                createElementVNode("view", utsMapOf("class" to "address-info"), utsArrayOf(
                                    createElementVNode("view", utsMapOf("class" to "address-top"), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to "receiver-name"), toDisplayString(_ctx.selectedAddress!!.receiverName), 1),
                                        createElementVNode("text", utsMapOf("class" to "receiver-phone"), toDisplayString(_ctx.selectedAddress!!.receiverPhone), 1),
                                        if (_ctx.selectedAddress!!.isDefault === 1) {
                                            createElementVNode("view", utsMapOf("key" to 0, "class" to "default-tag"), utsArrayOf(
                                                createElementVNode("text", utsMapOf("class" to "default-tag-text"), "默认")
                                            ))
                                        } else {
                                            createCommentVNode("v-if", true)
                                        }
                                    )),
                                    createElementVNode("text", utsMapOf("class" to "address-detail"), toDisplayString(_ctx.selectedAddress!!.fullAddress), 1)
                                )),
                                createElementVNode("text", utsMapOf("class" to "arrow-icon"), ">")
                            ))
                        } else {
                            createElementVNode("view", utsMapOf("key" to 1, "class" to "no-address"), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "no-address-icon"), "+"),
                                createElementVNode("text", utsMapOf("class" to "no-address-text"), "请添加收货地址")
                            ))
                        }
                    ), 8, utsArrayOf(
                        "onClick"
                    )),
                    createElementVNode("view", utsMapOf("class" to "goods-section"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "section-header"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "shop-icon"), "🏪"),
                            createElementVNode("text", utsMapOf("class" to "shop-name"), "鲜农优选直营店")
                        )),
                        createElementVNode("view", utsMapOf("class" to "goods-list"), utsArrayOf(
                            createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.orderGoods, fun(item, index, __index, _cached): Any {
                                return createElementVNode("view", utsMapOf("class" to "goods-item", "key" to index), utsArrayOf(
                                    createElementVNode("image", utsMapOf("class" to "goods-image", "src" to item.productImage, "mode" to "aspectFill"), null, 8, utsArrayOf(
                                        "src"
                                    )),
                                    createElementVNode("view", utsMapOf("class" to "goods-info"), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to "goods-name"), toDisplayString(item.productName), 1),
                                        createElementVNode("text", utsMapOf("class" to "goods-spec"), toDisplayString(item.skuName), 1),
                                        createElementVNode("view", utsMapOf("class" to "goods-bottom"), utsArrayOf(
                                            createElementVNode("text", utsMapOf("class" to "goods-price"), "¥" + toDisplayString(item.price.toFixed(2)), 1),
                                            createElementVNode("text", utsMapOf("class" to "goods-qty"), "x" + toDisplayString(item.quantity), 1)
                                        ))
                                    ))
                                ))
                            }
                            ), 128)
                        ))
                    )),
                    createElementVNode("view", utsMapOf("class" to "remark-section"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "remark-label"), "订单备注"),
                        createElementVNode("input", utsMapOf("class" to "remark-input", "type" to "text", "modelValue" to _ctx.remark, "onInput" to fun(`$event`: InputEvent){
                            _ctx.remark = `$event`.detail.value
                        }
                        , "placeholder" to "选填，请输入备注信息", "placeholder-class" to "remark-placeholder"), null, 40, utsArrayOf(
                            "modelValue",
                            "onInput"
                        ))
                    )),
                    createElementVNode("view", utsMapOf("class" to "price-section"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "price-row"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "price-label"), "商品金额"),
                            createElementVNode("text", utsMapOf("class" to "price-value"), "¥" + toDisplayString(_ctx.totalGoodsAmount.toFixed(2)), 1)
                        )),
                        createElementVNode("view", utsMapOf("class" to "price-row"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "price-label"), "运费"),
                            createElementVNode("text", utsMapOf("class" to "price-value"), "¥" + toDisplayString(_ctx.freightAmount.toFixed(2)), 1)
                        ))
                    )),
                    createElementVNode("view", utsMapOf("style" to normalizeStyle(utsMapOf("height" to "140rpx"))), null, 4)
                ))
            }
            ,
            createElementVNode("view", utsMapOf("class" to "bottom-bar"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "total-info"), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "total-label"), "合计："),
                    createElementVNode("text", utsMapOf("class" to "total-price"), "¥" + toDisplayString(_ctx.totalPayAmount.toFixed(2)), 1)
                )),
                createElementVNode("button", utsMapOf("class" to "submit-btn", "disabled" to (!_ctx.canSubmit || _ctx.isSubmitting), "onClick" to _ctx.submitOrder), toDisplayString(if (_ctx.isSubmitting) {
                    "提交中..."
                } else {
                    "提交订单"
                }
                ), 9, utsArrayOf(
                    "disabled",
                    "onClick"
                ))
            ))
        ))
    }
    open var isLoading: Boolean by `$data`
    open var isSubmitting: Boolean by `$data`
    open var orderType: String by `$data`
    open var cartItemIds: UTSArray<Number> by `$data`
    open var directBuyItem: DirectBuyItem? by `$data`
    open var orderGoods: UTSArray<OrderGoodsType> by `$data`
    open var selectedAddress: AddressType? by `$data`
    open var remark: String by `$data`
    open var freightAmount: Number by `$data`
    open var totalGoodsAmount: Number by `$data`
    open var totalPayAmount: Number by `$data`
    open var canSubmit: Boolean by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return utsMapOf("isLoading" to true, "isSubmitting" to false, "orderType" to "cart", "cartItemIds" to utsArrayOf<Number>(), "directBuyItem" to null as DirectBuyItem?, "orderGoods" to utsArrayOf<OrderGoodsType>(), "selectedAddress" to null as AddressType?, "remark" to "", "freightAmount" to 0, "totalGoodsAmount" to computed<Number>(fun(): Number {
            return this.orderGoods.reduce(fun(sum: Number, item: OrderGoodsType): Number {
                return sum + item.price * item.quantity
            }
            , 0)
        }
        ), "totalPayAmount" to computed<Number>(fun(): Number {
            return this.totalGoodsAmount + this.freightAmount
        }
        ), "canSubmit" to computed<Boolean>(fun(): Boolean {
            return this.selectedAddress != null && this.orderGoods.length > 0
        }
        ))
    }
    open var goBack = ::gen_goBack_fn
    open fun gen_goBack_fn() {
        uni_navigateBack(null)
    }
    open var loadCartGoods = ::gen_loadCartGoods_fn
    open fun gen_loadCartGoods_fn() {
        this.isLoading = true
        getCart().then(fun(res){
            val data = res.data as UTSJSONObject
            val itemsArr = data.getArray("items")
            val goods: UTSArray<OrderGoodsType> = utsArrayOf()
            if (itemsArr != null) {
                run {
                    var i: Number = 0
                    while(i < itemsArr.length){
                        val item = itemsArr[i] as UTSJSONObject
                        val cartId = (item.getNumber("cartId") ?: 0).toInt()
                        if (this.cartItemIds.includes(cartId)) {
                            goods.push(OrderGoodsType(cartId = cartId, productId = (item.getNumber("productId") ?: 0).toInt(), skuId = (item.getNumber("skuId") ?: 0).toInt(), productName = item.getString("productName") ?: "", productImage = item.getString("productImage") ?: "", skuName = item.getString("skuName") ?: "", price = item.getNumber("price") ?: 0, quantity = (item.getNumber("quantity") ?: 0).toInt()))
                        }
                        i++
                    }
                }
            }
            this.orderGoods = goods
            this.isLoading = false
        }
        ).`catch`(fun(err){
            console.error("获取购物车失败:", err, " at pages/order/checkout.uvue:248")
            this.isLoading = false
            uni_showToast(ShowToastOptions(title = "加载失败", icon = "none"))
        }
        )
    }
    open var loadDefaultAddress = ::gen_loadDefaultAddress_fn
    open fun gen_loadDefaultAddress_fn() {
        getDefaultAddress().then(fun(res){
            if (res.code === 200) {
                val data = res.data as UTSJSONObject?
                if (data != null) {
                    this.selectedAddress = AddressType(id = (data.getNumber("id") ?: 0).toInt(), receiverName = data.getString("receiverName") ?: "", receiverPhone = data.getString("receiverPhone") ?: "", province = data.getString("province") ?: "", city = data.getString("city") ?: "", district = data.getString("district") ?: "", detailAddress = data.getString("detailAddress") ?: "", fullAddress = data.getString("fullAddress") ?: "", isDefault = (data.getNumber("isDefault") ?: 0).toInt())
                } else {
                    this.selectedAddress = null
                }
            }
        }
        ).`catch`(fun(err){
            console.error("获取默认地址失败:", err, " at pages/order/checkout.uvue:275")
        }
        )
    }
    open var selectAddress = ::gen_selectAddress_fn
    open fun gen_selectAddress_fn() {
        uni_navigateTo(NavigateToOptions(url = "/pages/user/address-list?select=1"))
    }
    open var submitOrder = ::gen_submitOrder_fn
    open fun gen_submitOrder_fn() {
        if (!this.canSubmit) {
            if (this.selectedAddress == null) {
                uni_showToast(ShowToastOptions(title = "请选择收货地址", icon = "none"))
            }
            return
        }
        this.isSubmitting = true
        val address = this.selectedAddress as AddressType
        val params = OrderCreateParams(cartItemIds = if (this.orderType === "cart") {
            this.cartItemIds
        } else {
            null
        }
        , directBuyItem = if (this.orderType === "direct") {
            this.directBuyItem
        } else {
            null
        }
        , receiverName = address.receiverName, receiverPhone = address.receiverPhone, receiverAddress = address.fullAddress, receiverProvince = address.province, receiverCity = address.city, receiverDistrict = address.district, remark = if (this.remark !== "") {
            this.remark
        } else {
            null
        }
        )
        createOrder(params).then(fun(res){
            this.isSubmitting = false
            val orderNo = res.data as String
            uni_showToast(ShowToastOptions(title = "订单创建成功", icon = "success"))
            setTimeout(fun(){
                uni_redirectTo(RedirectToOptions(url = "/pages/order/list?status=pending"))
            }
            , 1500)
        }
        ).`catch`(fun(err){
            this.isSubmitting = false
            console.error("创建订单失败:", err, " at pages/order/checkout.uvue:310")
            uni_showToast(ShowToastOptions(title = "创建订单失败", icon = "none"))
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
                return utsMapOf("checkout-page" to padStyleMapOf(utsMapOf("backgroundColor" to "#f5f5f5", "display" to "flex", "flexDirection" to "column", "flex" to 1)), "header" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "paddingTop" to CSS_VAR_STATUS_BAR_HEIGHT, "paddingRight" to 16, "paddingBottom" to 12, "paddingLeft" to 16, "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between")), "back-btn" to padStyleMapOf(utsMapOf("width" to 40)), "back-icon" to padStyleMapOf(utsMapOf("fontSize" to 20, "color" to "#333333")), "page-title" to padStyleMapOf(utsMapOf("fontSize" to 17, "fontWeight" to "700", "color" to "#333333")), "placeholder" to padStyleMapOf(utsMapOf("width" to 40)), "loading-state" to padStyleMapOf(utsMapOf("flex" to 1, "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "loading-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#999999")), "content-scroll" to padStyleMapOf(utsMapOf("flex" to 1)), "address-section" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "marginTop" to 12, "marginRight" to 12, "marginBottom" to 12, "marginLeft" to 12, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16)), "address-card" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center")), "address-icon" to padStyleMapOf(utsMapOf("fontSize" to 24, "marginRight" to 12)), "address-info" to padStyleMapOf(utsMapOf("flex" to 1)), "address-top" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "marginBottom" to 8)), "receiver-name" to padStyleMapOf(utsMapOf("fontSize" to 16, "fontWeight" to "700", "color" to "#333333", "marginRight" to 12)), "receiver-phone" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#666666", "marginRight" to 8)), "default-tag" to padStyleMapOf(utsMapOf("backgroundColor" to "#0066CC", "paddingTop" to 2, "paddingRight" to 6, "paddingBottom" to 2, "paddingLeft" to 6, "borderTopLeftRadius" to 4, "borderTopRightRadius" to 4, "borderBottomRightRadius" to 4, "borderBottomLeftRadius" to 4)), "default-tag-text" to padStyleMapOf(utsMapOf("fontSize" to 10, "color" to "#ffffff")), "address-detail" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#666666", "lineHeight" to 1.4)), "arrow-icon" to padStyleMapOf(utsMapOf("fontSize" to 16, "color" to "#cccccc")), "no-address" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center", "paddingTop" to 20, "paddingRight" to 0, "paddingBottom" to 20, "paddingLeft" to 0)), "no-address-icon" to padStyleMapOf(utsMapOf("fontSize" to 24, "color" to "#0066CC", "marginRight" to 8)), "no-address-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#0066CC")), "goods-section" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "marginTop" to 0, "marginRight" to 12, "marginBottom" to 12, "marginLeft" to 12, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16)), "section-header" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "marginBottom" to 16, "paddingBottom" to 12, "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "#f5f5f5")), "shop-icon" to padStyleMapOf(utsMapOf("fontSize" to 16, "marginRight" to 8)), "shop-name" to padStyleMapOf(utsMapOf("fontSize" to 14, "fontWeight" to "700", "color" to "#333333")), "goods-item" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "paddingTop" to 12, "paddingRight" to 0, "paddingBottom" to 12, "paddingLeft" to 0, "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "#f5f5f5")), "goods-image" to padStyleMapOf(utsMapOf("width" to 80, "height" to 80, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "marginRight" to 12, "backgroundColor" to "#f5f5f5")), "goods-info" to padStyleMapOf(utsMapOf("flex" to 1, "display" to "flex", "flexDirection" to "column", "justifyContent" to "space-between")), "goods-name" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#333333", "lineHeight" to 1.4, "marginBottom" to 4)), "goods-spec" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999", "marginBottom" to 8)), "goods-bottom" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center")), "goods-price" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#ff4d4f", "fontWeight" to "700")), "goods-qty" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999")), "remark-section" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "marginTop" to 0, "marginRight" to 12, "marginBottom" to 12, "marginLeft" to 12, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "display" to "flex", "flexDirection" to "row", "alignItems" to "center")), "remark-label" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#333333", "marginRight" to 12, "whiteSpace" to "nowrap")), "remark-input" to padStyleMapOf(utsMapOf("flex" to 1, "fontSize" to 14, "color" to "#333333")), "price-section" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "marginTop" to 0, "marginRight" to 12, "marginBottom" to 12, "marginLeft" to 12, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16)), "price-row" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "marginBottom" to 12)), "price-label" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#666666")), "price-value" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#333333")), "bottom-bar" to padStyleMapOf(utsMapOf("position" to "fixed", "bottom" to 0, "left" to 0, "right" to 0, "backgroundColor" to "#ffffff", "paddingTop" to 12, "paddingRight" to 16, "paddingBottom" to 30, "paddingLeft" to 16, "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "boxShadow" to "0 -2px 8px rgba(0, 0, 0, 0.05)")), "total-info" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center")), "total-label" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#333333")), "total-price" to padStyleMapOf(utsMapOf("fontSize" to 20, "fontWeight" to "700", "color" to "#ff4d4f")), "submit-btn" to padStyleMapOf(utsMapOf("backgroundColor" to "#0066CC", "color" to "#ffffff", "fontSize" to 16, "fontWeight" to "700", "paddingTop" to 0, "paddingRight" to 40, "paddingBottom" to 0, "paddingLeft" to 40, "height" to 44, "lineHeight" to "44px", "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "borderTopWidth" to "medium", "borderRightWidth" to "medium", "borderBottomWidth" to "medium", "borderLeftWidth" to "medium", "borderTopStyle" to "none", "borderRightStyle" to "none", "borderBottomStyle" to "none", "borderLeftStyle" to "none", "borderTopColor" to "#000000", "borderRightColor" to "#000000", "borderBottomColor" to "#000000", "borderLeftColor" to "#000000")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = utsMapOf()
        var emits: Map<String, Any?> = utsMapOf()
        var props = normalizePropsOptions(utsMapOf())
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf()
        var components: Map<String, CreateVueComponent> = utsMapOf()
    }
}
