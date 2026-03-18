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
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.showModal as uni_showModal
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesCartIndex : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onLoad(fun(_: OnLoadOptions) {
            this.loadCart()
        }
        , __ins)
        onPageShow(fun() {
            uni__emit("bottomNavRefresh", null)
            this.loadCart()
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        val _component_checkbox = resolveComponent("checkbox")
        val _component_BottomNav = resolveComponent("BottomNav")
        return createElementVNode("view", utsMapOf("class" to "cart-page"), utsArrayOf(
            createElementVNode("view", utsMapOf("class" to "header"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "header-left"), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "page-title"), "购物车"),
                    createElementVNode("text", utsMapOf("class" to "cart-count"), "(" + toDisplayString(_ctx.cartData.totalQuantity) + ")", 1)
                )),
                createElementVNode("text", utsMapOf("class" to "edit-btn", "onClick" to _ctx.toggleEditMode), toDisplayString(if (_ctx.isEditing) {
                    "完成"
                } else {
                    "编辑"
                }
                ), 9, utsArrayOf(
                    "onClick"
                ))
            )),
            if (isTrue(_ctx.isLoading)) {
                createElementVNode("view", utsMapOf("key" to 0, "class" to "loading-state"), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "loading-text"), "加载中...")
                ))
            } else {
                if (_ctx.cartData.items.length === 0) {
                    createElementVNode("view", utsMapOf("key" to 1, "class" to "empty-cart"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "empty-icon"), "🛒"),
                        createElementVNode("text", utsMapOf("class" to "empty-title"), "购物车空空如也"),
                        createElementVNode("text", utsMapOf("class" to "empty-desc"), "赶紧去挑选心仪的商品吧"),
                        createElementVNode("button", utsMapOf("class" to "go-shopping-btn", "onClick" to _ctx.goShopping), "去逛逛", 8, utsArrayOf(
                            "onClick"
                        ))
                    ))
                } else {
                    createElementVNode("scroll-view", utsMapOf("key" to 2, "class" to "cart-scroll", "scroll-y" to "true", "onRefresherrefresh" to _ctx.onRefresh, "refresher-enabled" to true, "refresher-triggered" to _ctx.isRefreshing), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "cart-list"), utsArrayOf(
                            createElementVNode("view", utsMapOf("class" to "shop-group"), utsArrayOf(
                                createElementVNode("view", utsMapOf("class" to "shop-header"), utsArrayOf(
                                    createVNode(_component_checkbox, utsMapOf("class" to "shop-checkbox", "checked" to _ctx.cartData.allSelected, "onClick" to _ctx.toggleSelectAll, "color" to "#0066CC"), null, 8, utsArrayOf(
                                        "checked",
                                        "onClick"
                                    )),
                                    createElementVNode("text", utsMapOf("class" to "shop-icon"), "🏪"),
                                    createElementVNode("text", utsMapOf("class" to "shop-name"), "鲜农优选直营店")
                                )),
                                createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.cartData.items, fun(item, index, __index, _cached): Any {
                                    return createElementVNode("view", utsMapOf("class" to "cart-item", "key" to item.cartId), utsArrayOf(
                                        createElementVNode("view", utsMapOf("class" to "item-checkbox-wrapper", "onClick" to fun(){
                                            _ctx.toggleSelect(item)
                                        }
                                        ), utsArrayOf(
                                            createVNode(_component_checkbox, utsMapOf("checked" to item.selected, "color" to "#0066CC", "onClick" to withModifiers(fun(){
                                                _ctx.toggleSelect(item)
                                            }
                                            , utsArrayOf(
                                                "stop"
                                            ))), null, 8, utsArrayOf(
                                                "checked",
                                                "onClick"
                                            ))
                                        ), 8, utsArrayOf(
                                            "onClick"
                                        )),
                                        createElementVNode("view", utsMapOf("class" to "item-content", "onClick" to fun(){
                                            _ctx.goToDetail(item.productId)
                                        }
                                        ), utsArrayOf(
                                            createElementVNode("image", utsMapOf("class" to "item-image", "src" to item.productImage, "mode" to "aspectFill"), null, 8, utsArrayOf(
                                                "src"
                                            )),
                                            createElementVNode("view", utsMapOf("class" to "item-info"), utsArrayOf(
                                                createElementVNode("view", utsMapOf("class" to "info-top"), utsArrayOf(
                                                    createElementVNode("text", utsMapOf("class" to "item-name"), toDisplayString(item.productName), 1),
                                                    if (isTrue(item.skuName)) {
                                                        createElementVNode("view", utsMapOf("key" to 0, "class" to "item-spec-box"), utsArrayOf(
                                                            createElementVNode("text", utsMapOf("class" to "item-spec"), toDisplayString(item.skuName), 1)
                                                        ))
                                                    } else {
                                                        createCommentVNode("v-if", true)
                                                    }
                                                    ,
                                                    if (isTrue(!item.valid)) {
                                                        createElementVNode("view", utsMapOf("key" to 1, "class" to "invalid-tag"), utsArrayOf(
                                                            createElementVNode("text", utsMapOf("class" to "invalid-text"), toDisplayString(if (item.productStatus !== 1) {
                                                                "已下架"
                                                            } else {
                                                                "库存不足"
                                                            }), 1)
                                                        ))
                                                    } else {
                                                        createCommentVNode("v-if", true)
                                                    }
                                                )),
                                                createElementVNode("view", utsMapOf("class" to "info-bottom"), utsArrayOf(
                                                    createElementVNode("view", utsMapOf("class" to "price-box"), utsArrayOf(
                                                        createElementVNode("text", utsMapOf("class" to "currency"), "¥"),
                                                        createElementVNode("text", utsMapOf("class" to "price"), toDisplayString(item.price.toFixed(2)), 1)
                                                    )),
                                                    createElementVNode("view", utsMapOf("class" to "quantity-control"), utsArrayOf(
                                                        createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                                                            "qty-btn qty-btn-minus",
                                                            utsMapOf("qty-btn-disabled" to (item.quantity <= 1))
                                                        )), "onClick" to withModifiers(fun(){
                                                            _ctx.decreaseQuantity(item)
                                                        }
                                                        , utsArrayOf(
                                                            "stop"
                                                        ))), utsArrayOf(
                                                            createElementVNode("text", utsMapOf("class" to "qty-btn-text"), "-")
                                                        ), 10, utsArrayOf(
                                                            "onClick"
                                                        )),
                                                        createElementVNode("view", utsMapOf("class" to "qty-input"), utsArrayOf(
                                                            createElementVNode("text", utsMapOf("class" to "qty-text"), toDisplayString(item.quantity), 1)
                                                        )),
                                                        createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                                                            "qty-btn qty-btn-plus",
                                                            utsMapOf("qty-btn-disabled" to (item.quantity >= item.stock))
                                                        )), "onClick" to withModifiers(fun(){
                                                            _ctx.increaseQuantity(item)
                                                        }
                                                        , utsArrayOf(
                                                            "stop"
                                                        ))), utsArrayOf(
                                                            createElementVNode("text", utsMapOf("class" to "qty-btn-text"), "+")
                                                        ), 10, utsArrayOf(
                                                            "onClick"
                                                        ))
                                                    ))
                                                ))
                                            ))
                                        ), 8, utsArrayOf(
                                            "onClick"
                                        ))
                                    ))
                                }
                                ), 128)
                            ))
                        )),
                        createElementVNode("view", utsMapOf("style" to normalizeStyle(utsMapOf("height" to "140rpx"))), null, 4)
                    ), 40, utsArrayOf(
                        "onRefresherrefresh",
                        "refresher-triggered"
                    ))
                }
            }
            ,
            if (_ctx.cartData.items.length > 0) {
                createElementVNode("view", utsMapOf("key" to 3, "class" to "bottom-bar"), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "select-all", "onClick" to _ctx.toggleSelectAll), utsArrayOf(
                        createVNode(_component_checkbox, utsMapOf("checked" to _ctx.cartData.allSelected, "color" to "#0066CC", "onClick" to withModifiers(_ctx.toggleSelectAll, utsArrayOf(
                            "stop"
                        ))), null, 8, utsArrayOf(
                            "checked",
                            "onClick"
                        )),
                        createElementVNode("text", utsMapOf("class" to "select-all-text"), "全选")
                    ), 8, utsArrayOf(
                        "onClick"
                    )),
                    createElementVNode("view", utsMapOf("class" to "action-area"), utsArrayOf(
                        if (isTrue(!_ctx.isEditing)) {
                            createElementVNode(Fragment, utsMapOf("key" to 0), utsArrayOf(
                                createElementVNode("view", utsMapOf("class" to "price-info"), utsArrayOf(
                                    createElementVNode("view", utsMapOf("class" to "total-row"), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to "total-label"), "合计:"),
                                        createElementVNode("text", utsMapOf("class" to "total-price"), "¥" + toDisplayString(_ctx.cartData.selectedAmount.toFixed(2)), 1)
                                    ))
                                )),
                                createElementVNode("button", utsMapOf("class" to "submit-btn submit-btn-checkout", "disabled" to (_ctx.cartData.selectedQuantity === 0), "onClick" to _ctx.goCheckout), " 结算(" + toDisplayString(_ctx.cartData.selectedQuantity) + ") ", 9, utsArrayOf(
                                    "disabled",
                                    "onClick"
                                ))
                            ), 64)
                        } else {
                            createElementVNode("button", utsMapOf("key" to 1, "class" to "submit-btn submit-btn-delete", "disabled" to (_ctx.cartData.selectedQuantity === 0), "onClick" to _ctx.removeSelectedItems), " 删除(" + toDisplayString(_ctx.cartData.selectedQuantity) + ") ", 9, utsArrayOf(
                                "disabled",
                                "onClick"
                            ))
                        }
                    ))
                ))
            } else {
                createCommentVNode("v-if", true)
            }
            ,
            createVNode(_component_BottomNav)
        ))
    }
    open var isEditing: Boolean by `$data`
    open var isLoading: Boolean by `$data`
    open var isRefreshing: Boolean by `$data`
    open var cartData: CartVO by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return utsMapOf("isEditing" to false, "isLoading" to true, "isRefreshing" to false, "cartData" to CartVO(items = utsArrayOf<CartItemVO>(), totalQuantity = 0, selectedQuantity = 0, selectedAmount = 0, allSelected = false, validCount = 0, invalidCount = 0))
    }
    open var loadCart = ::gen_loadCart_fn
    open fun gen_loadCart_fn() {
        this.isLoading = true
        getCart().then(fun(res){
            console.log("=== 购物车响应 ===", " at pages/cart/index.uvue:155")
            console.log("响应code:", res.code, " at pages/cart/index.uvue:156")
            console.log("响应message:", res.message, " at pages/cart/index.uvue:157")
            console.log("响应data类型:", UTSAndroid.`typeof`(res.data), " at pages/cart/index.uvue:158")
            console.log("购物车原始数据:", JSON.stringify(res.data), " at pages/cart/index.uvue:159")
            val data = res.data as UTSJSONObject
            this.parseCartData(data)
            console.log("解析后购物车数据:", this.cartData.items.length, "件商品", " at pages/cart/index.uvue:162")
            console.log("=================", " at pages/cart/index.uvue:163")
            this.isLoading = false
            this.isRefreshing = false
        }
        ).`catch`(fun(err){
            console.error("获取购物车失败:", err, " at pages/cart/index.uvue:167")
            this.isLoading = false
            this.isRefreshing = false
            uni_showToast(ShowToastOptions(title = "获取购物车失败", icon = "none"))
        }
        )
    }
    open var parseCartData = ::gen_parseCartData_fn
    open fun gen_parseCartData_fn(data: UTSJSONObject) {
        val itemsArr = data.getArray("items")
        val items: UTSArray<CartItemVO> = utsArrayOf()
        console.log("购物车items数组:", if (itemsArr != null) {
            itemsArr.length
        } else {
            "null"
        }
        , " at pages/cart/index.uvue:181")
        if (itemsArr != null) {
            run {
                var i: Number = 0
                while(i < itemsArr.length){
                    val item = itemsArr[i] as UTSJSONObject
                    val cartId = (item.getNumber("cartId") ?: item.getNumber("id") ?: 0).toInt()
                    console.log("解析商品:", item.getString("productName"), "cartId:", cartId, " at pages/cart/index.uvue:188")
                    items.push(CartItemVO(cartId = cartId, productId = (item.getNumber("productId") ?: 0).toInt(), skuId = (item.getNumber("skuId") ?: 0).toInt(), productName = item.getString("productName") ?: "", productImage = item.getString("productImage") ?: "", skuName = item.getString("skuName") ?: "", skuImage = item.getString("skuImage"), price = item.getNumber("price") ?: 0, quantity = (item.getNumber("quantity") ?: 0).toInt(), totalPrice = item.getNumber("totalPrice") ?: 0, stock = (item.getNumber("stock") ?: 0).toInt(), selected = item.getBoolean("selected") ?: false, productStatus = (item.getNumber("productStatus") ?: 0).toInt(), skuStatus = (item.getNumber("skuStatus") ?: 0).toInt(), valid = item.getBoolean("valid") ?: true))
                    i++
                }
            }
        }
        this.cartData = CartVO(items = items, totalQuantity = (data.getNumber("totalQuantity") ?: 0).toInt(), selectedQuantity = (data.getNumber("selectedQuantity") ?: 0).toInt(), selectedAmount = data.getNumber("selectedAmount") ?: 0, allSelected = data.getBoolean("allSelected") ?: false, validCount = (data.getNumber("validCount") ?: 0).toInt(), invalidCount = (data.getNumber("invalidCount") ?: 0).toInt())
    }
    open var onRefresh = ::gen_onRefresh_fn
    open fun gen_onRefresh_fn() {
        this.isRefreshing = true
        this.loadCart()
    }
    open var toggleEditMode = ::gen_toggleEditMode_fn
    open fun gen_toggleEditMode_fn() {
        this.isEditing = !this.isEditing
    }
    open var toggleSelect = ::gen_toggleSelect_fn
    open fun gen_toggleSelect_fn(item: CartItemVO) {
        val newSelected = !item.selected
        updateCartSelected(item.cartId, newSelected).then(fun(){
            item.selected = newSelected
            this.recalculateCart()
        }
        ).`catch`(fun(err){
            console.error("更新选中状态失败:", err, " at pages/cart/index.uvue:241")
            uni_showToast(ShowToastOptions(title = "操作失败", icon = "none"))
        }
        )
    }
    open var toggleSelectAll = ::gen_toggleSelectAll_fn
    open fun gen_toggleSelectAll_fn() {
        val newSelected = !this.cartData.allSelected
        selectAllCart(newSelected).then(fun(){
            this.cartData.items.forEach(fun(item: CartItemVO){
                if (item.valid) {
                    item.selected = newSelected
                }
            }
            )
            this.recalculateCart()
        }
        ).`catch`(fun(err){
            console.error("全选操作失败:", err, " at pages/cart/index.uvue:259")
            uni_showToast(ShowToastOptions(title = "操作失败", icon = "none"))
        }
        )
    }
    open var recalculateCart = ::gen_recalculateCart_fn
    open fun gen_recalculateCart_fn() {
        var selectedQuantity: Number = 0
        var selectedAmount: Number = 0
        var allSelected = true
        var validCount: Number = 0
        this.cartData.items.forEach(fun(item: CartItemVO){
            if (item.valid) {
                validCount++
                if (item.selected) {
                    selectedQuantity += item.quantity
                    selectedAmount += item.totalPrice
                } else {
                    allSelected = false
                }
            }
        }
        )
        if (validCount === 0) {
            allSelected = false
        }
        this.cartData.selectedQuantity = selectedQuantity
        this.cartData.selectedAmount = selectedAmount
        this.cartData.allSelected = allSelected
        this.cartData.validCount = validCount
    }
    open var increaseQuantity = ::gen_increaseQuantity_fn
    open fun gen_increaseQuantity_fn(item: CartItemVO) {
        if (item.quantity >= item.stock) {
            uni_showToast(ShowToastOptions(title = "库存不足", icon = "none"))
            return
        }
        val newQuantity = item.quantity + 1
        updateCartQuantity(item.cartId, newQuantity).then(fun(){
            item.quantity = newQuantity
            item.totalPrice = item.price * newQuantity
            this.recalculateCart()
        }
        ).`catch`(fun(err){
            console.error("更新数量失败:", err, " at pages/cart/index.uvue:310")
            uni_showToast(ShowToastOptions(title = "操作失败", icon = "none"))
        }
        )
    }
    open var decreaseQuantity = ::gen_decreaseQuantity_fn
    open fun gen_decreaseQuantity_fn(item: CartItemVO) {
        if (item.quantity <= 1) {
            uni_showModal(ShowModalOptions(title = "提示", content = "确认删除该商品吗？", success = fun(res){
                if (res.confirm) {
                    this.deleteItem(item)
                }
            }
            ))
            return
        }
        val newQuantity = item.quantity - 1
        updateCartQuantity(item.cartId, newQuantity).then(fun(){
            item.quantity = newQuantity
            item.totalPrice = item.price * newQuantity
            this.recalculateCart()
        }
        ).`catch`(fun(err){
            console.error("更新数量失败:", err, " at pages/cart/index.uvue:339")
            uni_showToast(ShowToastOptions(title = "操作失败", icon = "none"))
        }
        )
    }
    open var deleteItem = ::gen_deleteItem_fn
    open fun gen_deleteItem_fn(item: CartItemVO) {
        deleteCartItem(item.cartId).then(fun(){
            val index = this.cartData.items.findIndex(fun(i: CartItemVO): Boolean {
                return i.cartId === item.cartId
            }
            )
            if (index !== -1) {
                this.cartData.items.splice(index, 1)
                this.cartData.totalQuantity -= item.quantity
                this.recalculateCart()
            }
            uni_showToast(ShowToastOptions(title = "删除成功", icon = "success"))
        }
        ).`catch`(fun(err){
            console.error("删除失败:", err, " at pages/cart/index.uvue:357")
            uni_showToast(ShowToastOptions(title = "删除失败", icon = "none"))
        }
        )
    }
    open var removeSelectedItems = ::gen_removeSelectedItems_fn
    open fun gen_removeSelectedItems_fn() {
        val selectedItems = this.cartData.items.filter(fun(item: CartItemVO): Boolean {
            return item.selected
        }
        )
        if (selectedItems.length === 0) {
            return
        }
        uni_showModal(ShowModalOptions(title = "提示", content = "\u786E\u5B9A\u5220\u9664\u9009\u4E2D\u7684 " + selectedItems.length + " \u4EF6\u5546\u54C1\u5417\uFF1F", success = fun(res){
            if (res.confirm) {
                var deleteCount: Number = 0
                selectedItems.forEach(fun(item: CartItemVO){
                    deleteCartItem(item.cartId).then(fun(){
                        deleteCount++
                        if (deleteCount === selectedItems.length) {
                            this.loadCart()
                            this.isEditing = false
                            uni_showToast(ShowToastOptions(title = "删除成功", icon = "success"))
                        }
                    }
                    ).`catch`(fun(err){
                        console.error("删除失败:", err, " at pages/cart/index.uvue:388")
                    }
                    )
                }
                )
            }
        }
        ))
    }
    open var goShopping = ::gen_goShopping_fn
    open fun gen_goShopping_fn() {
        uni_navigateBack(NavigateBackOptions(delta = 99))
    }
    open var goToDetail = ::gen_goToDetail_fn
    open fun gen_goToDetail_fn(productId: Number) {
        uni_navigateTo(NavigateToOptions(url = "/pages/product/detail?id=" + productId))
    }
    open var goCheckout = ::gen_goCheckout_fn
    open fun gen_goCheckout_fn() {
        val selectedCartIds = this.cartData.items.filter(fun(item: CartItemVO): Boolean {
            return item.selected && item.valid
        }
        ).map(fun(item: CartItemVO): Number {
            return item.cartId
        }
        )
        if (selectedCartIds.length === 0) {
            uni_showToast(ShowToastOptions(title = "请选择商品", icon = "none"))
            return
        }
        uni_navigateTo(NavigateToOptions(url = "/pages/order/checkout?type=cart&cartIds=" + selectedCartIds.join(",")))
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
                return utsMapOf("cart-page" to padStyleMapOf(utsMapOf("backgroundColor" to "#f7f8fa", "flex" to 1, "display" to "flex", "flexDirection" to "column")), "header" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "paddingTop" to CSS_VAR_STATUS_BAR_HEIGHT, "paddingRight" to 16, "paddingBottom" to 12, "paddingLeft" to 16, "display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "zIndex" to 100)), "header-left" to utsMapOf(".header " to utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center")), "page-title" to utsMapOf(".header .header-left " to utsMapOf("fontSize" to 18, "fontWeight" to "700", "color" to "#333333")), "cart-count" to utsMapOf(".header .header-left " to utsMapOf("fontSize" to 14, "color" to "#999999", "marginLeft" to 4)), "edit-btn" to utsMapOf(".header " to utsMapOf("fontSize" to 14, "color" to "#0066CC")), "loading-state" to padStyleMapOf(utsMapOf("flex" to 1, "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "loading-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#999999")), "empty-cart" to padStyleMapOf(utsMapOf("flex" to 1, "display" to "flex", "flexDirection" to "column", "alignItems" to "center", "justifyContent" to "center", "paddingBottom" to 100)), "empty-icon" to padStyleMapOf(utsMapOf("fontSize" to 60, "marginBottom" to 20)), "empty-title" to padStyleMapOf(utsMapOf("fontSize" to 16, "color" to "#333333", "marginBottom" to 8)), "empty-desc" to padStyleMapOf(utsMapOf("fontSize" to 13, "color" to "#999999", "marginBottom" to 24)), "go-shopping-btn" to padStyleMapOf(utsMapOf("borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#0066CC", "borderRightColor" to "#0066CC", "borderBottomColor" to "#0066CC", "borderLeftColor" to "#0066CC", "color" to "#0066CC", "backgroundColor" to "rgba(0,0,0,0)", "borderTopLeftRadius" to 20, "borderTopRightRadius" to 20, "borderBottomRightRadius" to 20, "borderBottomLeftRadius" to 20, "paddingTop" to 0, "paddingRight" to 30, "paddingBottom" to 0, "paddingLeft" to 30, "fontSize" to 14, "height" to 36, "lineHeight" to "36px")), "cart-scroll" to padStyleMapOf(utsMapOf("flex" to 1)), "cart-list" to padStyleMapOf(utsMapOf("paddingTop" to 12, "paddingRight" to 16, "paddingBottom" to 12, "paddingLeft" to 16)), "shop-group" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "paddingTop" to 0, "paddingRight" to 16, "paddingBottom" to 0, "paddingLeft" to 16, "marginBottom" to 12, "boxShadow" to "0 4px 16px rgba(0, 0, 0, 0.04)")), "shop-header" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "paddingTop" to 12, "paddingRight" to 0, "paddingBottom" to 12, "paddingLeft" to 0, "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "#f5f5f5")), "shop-checkbox" to padStyleMapOf(utsMapOf("marginRight" to 8)), "shop-icon" to padStyleMapOf(utsMapOf("fontSize" to 16, "marginRight" to 6)), "shop-name" to padStyleMapOf(utsMapOf("fontSize" to 14, "fontWeight" to "700", "color" to "#333333")), "cart-item" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "paddingTop" to 16, "paddingRight" to 0, "paddingBottom" to 16, "paddingLeft" to 0, "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "#f5f5f5")), "item-checkbox-wrapper" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "marginRight" to 8)), "item-content" to padStyleMapOf(utsMapOf("flex" to 1, "display" to "flex", "flexDirection" to "row")), "item-image" to padStyleMapOf(utsMapOf("width" to 90, "height" to 90, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#f5f5f5", "marginRight" to 12)), "item-info" to padStyleMapOf(utsMapOf("flex" to 1, "display" to "flex", "flexDirection" to "column", "justifyContent" to "space-between")), "info-top" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "column")), "item-name" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#333333", "lineHeight" to 1.4, "marginBottom" to 6)), "item-spec-box" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "backgroundColor" to "#f5f5f5", "paddingTop" to 2, "paddingRight" to 6, "paddingBottom" to 2, "paddingLeft" to 6, "borderTopLeftRadius" to 4, "borderTopRightRadius" to 4, "borderBottomRightRadius" to 4, "borderBottomLeftRadius" to 4, "alignSelf" to "flex-start", "marginBottom" to 6)), "item-spec" to padStyleMapOf(utsMapOf("fontSize" to 11, "color" to "#666666")), "invalid-tag" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffebee", "paddingTop" to 2, "paddingRight" to 6, "paddingBottom" to 2, "paddingLeft" to 6, "borderTopLeftRadius" to 4, "borderTopRightRadius" to 4, "borderBottomRightRadius" to 4, "borderBottomLeftRadius" to 4, "alignSelf" to "flex-start")), "invalid-text" to padStyleMapOf(utsMapOf("fontSize" to 11, "color" to "#ff4d4f")), "info-bottom" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "flex-end")), "price-box" to padStyleMapOf(utsMapOf("color" to "#ff4d4f", "display" to "flex", "flexDirection" to "row", "alignItems" to "center")), "currency" to padStyleMapOf(utsMapOf("fontSize" to 12, "fontWeight" to "700")), "price" to padStyleMapOf(utsMapOf("fontSize" to 16, "fontWeight" to "700")), "quantity-control" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#eeeeee", "borderRightColor" to "#eeeeee", "borderBottomColor" to "#eeeeee", "borderLeftColor" to "#eeeeee", "borderTopLeftRadius" to 4, "borderTopRightRadius" to 4, "borderBottomRightRadius" to 4, "borderBottomLeftRadius" to 4)), "qty-btn" to padStyleMapOf(utsMapOf("width" to 28, "height" to 28, "display" to "flex", "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "#ffffff")), "qty-btn-minus" to padStyleMapOf(utsMapOf("borderRightWidth" to 1, "borderRightStyle" to "solid", "borderRightColor" to "#eeeeee")), "qty-btn-plus" to padStyleMapOf(utsMapOf("borderLeftWidth" to 1, "borderLeftStyle" to "solid", "borderLeftColor" to "#eeeeee")), "qty-btn-disabled" to padStyleMapOf(utsMapOf("backgroundColor" to "#f5f5f5")), "qty-btn-text" to padStyleMapOf(utsMapOf("fontSize" to 16, "color" to "#333333")), "qty-input" to padStyleMapOf(utsMapOf("width" to 36, "height" to 28, "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "qty-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#333333")), "bottom-bar" to padStyleMapOf(utsMapOf("position" to "fixed", "bottom" to "120rpx", "left" to 0, "right" to 0, "backgroundColor" to "#ffffff", "height" to 50, "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "paddingTop" to 0, "paddingRight" to 16, "paddingBottom" to 0, "paddingLeft" to 16, "boxShadow" to "0 -1px 8px rgba(0, 0, 0, 0.05)", "zIndex" to 99)), "select-all" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center")), "select-all-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#666666", "marginLeft" to 8)), "action-area" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center")), "price-info" to padStyleMapOf(utsMapOf("textAlign" to "right", "marginRight" to 12)), "total-row" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "flex-end")), "total-label" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#333333")), "total-price" to padStyleMapOf(utsMapOf("fontSize" to 18, "fontWeight" to "700", "color" to "#ff4d4f", "marginLeft" to 4)), "submit-btn" to padStyleMapOf(utsMapOf("borderTopLeftRadius" to 20, "borderTopRightRadius" to 20, "borderBottomRightRadius" to 20, "borderBottomLeftRadius" to 20, "fontSize" to 14, "color" to "#ffffff", "paddingTop" to 0, "paddingRight" to 24, "paddingBottom" to 0, "paddingLeft" to 24, "height" to 36, "lineHeight" to "36px", "borderTopWidth" to "medium", "borderRightWidth" to "medium", "borderBottomWidth" to "medium", "borderLeftWidth" to "medium", "borderTopStyle" to "none", "borderRightStyle" to "none", "borderBottomStyle" to "none", "borderLeftStyle" to "none", "borderTopColor" to "#000000", "borderRightColor" to "#000000", "borderBottomColor" to "#000000", "borderLeftColor" to "#000000")), "submit-btn-checkout" to padStyleMapOf(utsMapOf("backgroundColor" to "#0066CC")), "submit-btn-delete" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "color" to "#ff4d4f", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#ff4d4f", "borderRightColor" to "#ff4d4f", "borderBottomColor" to "#ff4d4f", "borderLeftColor" to "#ff4d4f")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = utsMapOf()
        var emits: Map<String, Any?> = utsMapOf()
        var props = normalizePropsOptions(utsMapOf())
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf()
        var components: Map<String, CreateVueComponent> = utsMapOf("BottomNav" to GenComponentsBottomNavClass)
    }
}
