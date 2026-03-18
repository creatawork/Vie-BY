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
import io.dcloud.uniapp.extapi.showModal as uni_showModal
import io.dcloud.uniapp.extapi.showToast as uni_showToast
import io.dcloud.uniapp.extapi.switchTab as uni_switchTab
open class GenPagesOrderList : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onLoad(fun(options: OnLoadOptions) {
            val opts = options as UTSJSONObject
            val status = opts.get("status")
            if (status != null) {
                this.currentTab = status as String
            }
            this.loadOrders()
        }
        , __ins)
        onPageShow(fun() {
            uni__emit("bottomNavRefresh", null)
            this.pageNum = 1
            this.loadOrders()
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        val _component_BottomNav = resolveComponent("BottomNav")
        return createElementVNode("view", utsMapOf("class" to "order-page"), utsArrayOf(
            createElementVNode("view", utsMapOf("class" to "tab-header"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                    "tab-item",
                    utsMapOf("active" to (_ctx.currentTab === "all"))
                )), "onClick" to fun(){
                    _ctx.switchTab("all")
                }
                ), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                        "tab-text",
                        utsMapOf("tab-text--active" to (_ctx.currentTab === "all"))
                    ))), "全部", 2),
                    if (_ctx.currentTab === "all") {
                        createElementVNode("view", utsMapOf("key" to 0, "class" to "active-line"))
                    } else {
                        createCommentVNode("v-if", true)
                    }
                ), 10, utsArrayOf(
                    "onClick"
                )),
                createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                    "tab-item",
                    utsMapOf("active" to (_ctx.currentTab === "pending"))
                )), "onClick" to fun(){
                    _ctx.switchTab("pending")
                }
                ), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                        "tab-text",
                        utsMapOf("tab-text--active" to (_ctx.currentTab === "pending"))
                    ))), "待付款", 2),
                    if (_ctx.currentTab === "pending") {
                        createElementVNode("view", utsMapOf("key" to 0, "class" to "active-line"))
                    } else {
                        createCommentVNode("v-if", true)
                    }
                ), 10, utsArrayOf(
                    "onClick"
                )),
                createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                    "tab-item",
                    utsMapOf("active" to (_ctx.currentTab === "unshipped"))
                )), "onClick" to fun(){
                    _ctx.switchTab("unshipped")
                }
                ), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                        "tab-text",
                        utsMapOf("tab-text--active" to (_ctx.currentTab === "unshipped"))
                    ))), "待发货", 2),
                    if (_ctx.currentTab === "unshipped") {
                        createElementVNode("view", utsMapOf("key" to 0, "class" to "active-line"))
                    } else {
                        createCommentVNode("v-if", true)
                    }
                ), 10, utsArrayOf(
                    "onClick"
                )),
                createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                    "tab-item",
                    utsMapOf("active" to (_ctx.currentTab === "shipped"))
                )), "onClick" to fun(){
                    _ctx.switchTab("shipped")
                }
                ), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                        "tab-text",
                        utsMapOf("tab-text--active" to (_ctx.currentTab === "shipped"))
                    ))), "待收货", 2),
                    if (_ctx.currentTab === "shipped") {
                        createElementVNode("view", utsMapOf("key" to 0, "class" to "active-line"))
                    } else {
                        createCommentVNode("v-if", true)
                    }
                ), 10, utsArrayOf(
                    "onClick"
                )),
                createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                    "tab-item",
                    utsMapOf("active" to (_ctx.currentTab === "completed"))
                )), "onClick" to fun(){
                    _ctx.switchTab("completed")
                }
                ), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                        "tab-text",
                        utsMapOf("tab-text--active" to (_ctx.currentTab === "completed"))
                    ))), "已完成", 2),
                    if (_ctx.currentTab === "completed") {
                        createElementVNode("view", utsMapOf("key" to 0, "class" to "active-line"))
                    } else {
                        createCommentVNode("v-if", true)
                    }
                ), 10, utsArrayOf(
                    "onClick"
                ))
            )),
            createElementVNode("scroll-view", utsMapOf("class" to "order-scroll", "scroll-y" to "true", "onScrolltolower" to _ctx.loadMore, "onRefresherrefresh" to _ctx.onRefresh, "refresher-enabled" to true, "refresher-triggered" to _ctx.isRefreshing, "show-scrollbar" to false), utsArrayOf(
                if (isTrue(_ctx.isLoading && _ctx.orders.length === 0)) {
                    createElementVNode("view", utsMapOf("key" to 0, "class" to "loading-state"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "loading-text"), "加载中...")
                    ))
                } else {
                    createElementVNode("view", utsMapOf("key" to 1, "class" to "order-list"), utsArrayOf(
                        createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.orders, fun(order, __key, __index, _cached): Any {
                            return createElementVNode("view", utsMapOf("class" to "order-card", "key" to order.id, "onClick" to fun(){
                                _ctx.goToDetail(order.id)
                            }
                            ), utsArrayOf(
                                createElementVNode("view", utsMapOf("class" to "card-header"), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "order-number"), "订单号：" + toDisplayString(order.orderNo), 1),
                                    createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                                        "order-status",
                                        _ctx.getStatusClass(order.status)
                                    ))), toDisplayString(order.statusDesc), 3)
                                )),
                                createElementVNode("view", utsMapOf("class" to "goods-preview"), utsArrayOf(
                                    if (order.orderItems.length === 1) {
                                        createElementVNode("view", utsMapOf("key" to 0, "class" to "single-goods"), utsArrayOf(
                                            createElementVNode("image", utsMapOf("class" to "goods-img", "src" to order.orderItems[0].productImage, "mode" to "aspectFill"), null, 8, utsArrayOf(
                                                "src"
                                            )),
                                            createElementVNode("view", utsMapOf("class" to "goods-info"), utsArrayOf(
                                                createElementVNode("text", utsMapOf("class" to "goods-name"), toDisplayString(order.orderItems[0].productName), 1),
                                                createElementVNode("text", utsMapOf("class" to "goods-spec"), toDisplayString(order.orderItems[0].skuName), 1)
                                            )),
                                            createElementVNode("view", utsMapOf("class" to "goods-price-qty"), utsArrayOf(
                                                createElementVNode("text", utsMapOf("class" to "price"), "¥" + toDisplayString(order.orderItems[0].price.toFixed(2)), 1),
                                                createElementVNode("text", utsMapOf("class" to "qty"), "x" + toDisplayString(order.orderItems[0].quantity), 1)
                                            ))
                                        ))
                                    } else {
                                        createElementVNode("view", utsMapOf("key" to 1, "class" to "multi-goods"), utsArrayOf(
                                            createElementVNode("scroll-view", utsMapOf("scroll-x" to "true", "class" to "goods-img-scroll", "show-scrollbar" to false), utsArrayOf(
                                                createElementVNode("view", utsMapOf("class" to "goods-img-list"), utsArrayOf(
                                                    createElementVNode(Fragment, null, RenderHelpers.renderList(order.orderItems, fun(item, idx, __index, _cached): Any {
                                                        return createElementVNode("image", utsMapOf("key" to idx, "class" to "goods-img-small", "src" to item.productImage, "mode" to "aspectFill"), null, 8, utsArrayOf(
                                                            "src"
                                                        ))
                                                    }
                                                    ), 128)
                                                ))
                                            )),
                                            createElementVNode("view", utsMapOf("class" to "total-qty-info"), utsArrayOf(
                                                createElementVNode("text", utsMapOf("class" to "price"), "¥" + toDisplayString(order.payAmount.toFixed(2)), 1),
                                                createElementVNode("text", utsMapOf("class" to "qty"), "共" + toDisplayString(order.totalQuantity) + "件", 1)
                                            ))
                                        ))
                                    }
                                )),
                                createElementVNode("view", utsMapOf("class" to "card-footer"), utsArrayOf(
                                    if (order.orderItems.length === 1) {
                                        createElementVNode("view", utsMapOf("key" to 0, "class" to "total-info"), utsArrayOf(
                                            createElementVNode("text", utsMapOf("class" to "label"), "实付"),
                                            createElementVNode("text", utsMapOf("class" to "amount"), "¥" + toDisplayString(order.payAmount.toFixed(2)), 1)
                                        ))
                                    } else {
                                        createCommentVNode("v-if", true)
                                    }
                                    ,
                                    createElementVNode("view", utsMapOf("class" to "btn-group"), utsArrayOf(
                                        if (order.status === 0) {
                                            createElementVNode("view", utsMapOf("key" to 0, "class" to "action-btn secondary", "onClick" to withModifiers(fun(){
                                                _ctx.handleCancelOrder(order)
                                            }, utsArrayOf(
                                                "stop"
                                            ))), utsArrayOf(
                                                createElementVNode("text", utsMapOf("class" to "btn-text secondary-text"), "取消订单")
                                            ), 8, utsArrayOf(
                                                "onClick"
                                            ))
                                        } else {
                                            createCommentVNode("v-if", true)
                                        }
                                        ,
                                        if (order.status === 0) {
                                            createElementVNode("view", utsMapOf("key" to 1, "class" to "action-btn primary", "onClick" to withModifiers(fun(){
                                                _ctx.handlePayOrder(order)
                                            }, utsArrayOf(
                                                "stop"
                                            ))), utsArrayOf(
                                                createElementVNode("text", utsMapOf("class" to "btn-text primary-text"), "去支付")
                                            ), 8, utsArrayOf(
                                                "onClick"
                                            ))
                                        } else {
                                            createCommentVNode("v-if", true)
                                        }
                                        ,
                                        if (order.status === 2) {
                                            createElementVNode("view", utsMapOf("key" to 2, "class" to "action-btn primary", "onClick" to withModifiers(fun(){
                                                _ctx.handleConfirmReceipt(order)
                                            }, utsArrayOf(
                                                "stop"
                                            ))), utsArrayOf(
                                                createElementVNode("text", utsMapOf("class" to "btn-text primary-text"), "确认收货")
                                            ), 8, utsArrayOf(
                                                "onClick"
                                            ))
                                        } else {
                                            createCommentVNode("v-if", true)
                                        }
                                        ,
                                        if (isTrue(order.status === 3 || order.status === 4 || order.status === 5)) {
                                            createElementVNode("view", utsMapOf("key" to 3, "class" to "action-btn secondary", "onClick" to withModifiers(fun(){
                                                _ctx.handleDeleteOrder(order)
                                            }, utsArrayOf(
                                                "stop"
                                            ))), utsArrayOf(
                                                createElementVNode("text", utsMapOf("class" to "btn-text secondary-text"), "删除订单")
                                            ), 8, utsArrayOf(
                                                "onClick"
                                            ))
                                        } else {
                                            createCommentVNode("v-if", true)
                                        }
                                        ,
                                        if (isTrue(order.status === 3 && order.reviewed != true)) {
                                            createElementVNode("view", utsMapOf("key" to 4, "class" to "action-btn primary", "onClick" to withModifiers(fun(){
                                                _ctx.goToReview(order)
                                            }, utsArrayOf(
                                                "stop"
                                            ))), utsArrayOf(
                                                createElementVNode("text", utsMapOf("class" to "btn-text primary-text"), "去评价")
                                            ), 8, utsArrayOf(
                                                "onClick"
                                            ))
                                        } else {
                                            createCommentVNode("v-if", true)
                                        }
                                        ,
                                        if (order.status === 3) {
                                            createElementVNode("view", utsMapOf("key" to 5, "class" to "action-btn primary", "onClick" to withModifiers(fun(){
                                                _ctx.buyAgain(order)
                                            }, utsArrayOf(
                                                "stop"
                                            ))), utsArrayOf(
                                                createElementVNode("text", utsMapOf("class" to "btn-text primary-text"), "再次购买")
                                            ), 8, utsArrayOf(
                                                "onClick"
                                            ))
                                        } else {
                                            createCommentVNode("v-if", true)
                                        }
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
                if (isTrue(!_ctx.isLoading && _ctx.orders.length === 0)) {
                    createElementVNode("view", utsMapOf("key" to 2, "class" to "empty-state"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "empty-icon"), "📃"),
                        createElementVNode("text", utsMapOf("class" to "empty-text"), "暂无相关订单")
                    ))
                } else {
                    createCommentVNode("v-if", true)
                }
                ,
                if (_ctx.orders.length > 0) {
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
                createElementVNode("view", utsMapOf("style" to normalizeStyle(utsMapOf("height" to "140rpx"))), null, 4)
            ), 40, utsArrayOf(
                "onScrolltolower",
                "onRefresherrefresh",
                "refresher-triggered"
            )),
            createVNode(_component_BottomNav)
        ))
    }
    open var currentTab: String by `$data`
    open var isLoading: Boolean by `$data`
    open var isRefreshing: Boolean by `$data`
    open var hasMore: Boolean by `$data`
    open var pageNum: Number by `$data`
    open var pageSize: Number by `$data`
    open var orders: UTSArray<OrderVO> by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return utsMapOf("currentTab" to "all", "isLoading" to false, "isRefreshing" to false, "hasMore" to true, "pageNum" to 1, "pageSize" to 10, "orders" to utsArrayOf<OrderVO>())
    }
    open var getStatusValue = ::gen_getStatusValue_fn
    open fun gen_getStatusValue_fn(): Number? {
        val statusMap = Map<String, Number>(utsArrayOf(
            utsArrayOf(
                "pending",
                0
            ),
            utsArrayOf(
                "unshipped",
                1
            ),
            utsArrayOf(
                "shipped",
                2
            ),
            utsArrayOf(
                "completed",
                3
            )
        ))
        if (this.currentTab === "all") {
            return null
        }
        return statusMap.get(this.currentTab) ?: null
    }
    open var loadOrders = ::gen_loadOrders_fn
    open fun gen_loadOrders_fn() {
        this.isLoading = true
        val params = OrderQueryParams(status = this.getStatusValue(), pageNum = this.pageNum, pageSize = this.pageSize)
        getOrderList(params).then(fun(res){
            val data = res.data as UTSJSONObject
            val total = (data.getNumber("total") ?: 0).toInt()
            val orderList = this.parseOrdersFromData(data)
            if (this.pageNum === 1) {
                this.orders = orderList
            } else {
                this.orders = this.orders.concat(orderList)
            }
            this.hasMore = this.orders.length < total
            this.isLoading = false
            this.isRefreshing = false
        }
        ).`catch`(fun(err){
            console.error("获取订单列表失败:", err, " at pages/order/list.uvue:157")
            this.isLoading = false
            this.isRefreshing = false
            uni_showToast(ShowToastOptions(title = "获取订单失败", icon = "none"))
        }
        )
    }
    open var parseOrdersFromData = ::gen_parseOrdersFromData_fn
    open fun gen_parseOrdersFromData_fn(data: UTSJSONObject): UTSArray<OrderVO> {
        val records = data.getArray("records")
        if (records == null) {
            return utsArrayOf<OrderVO>()
        }
        val orderList: UTSArray<OrderVO> = utsArrayOf()
        run {
            var i: Number = 0
            while(i < records.length){
                val orderObj = records[i] as UTSJSONObject
                val itemsArr = orderObj.getArray("orderItems")
                val orderItems: UTSArray<OrderItemVO> = utsArrayOf()
                if (itemsArr != null) {
                    run {
                        var j: Number = 0
                        while(j < itemsArr.length){
                            val item = itemsArr[j] as UTSJSONObject
                            orderItems.push(OrderItemVO(id = (item.getNumber("id") ?: 0).toInt(), productId = (item.getNumber("productId") ?: 0).toInt(), skuId = (item.getNumber("skuId") ?: 0).toInt(), productName = item.getString("productName") ?: "", skuName = item.getString("skuName") ?: "", productImage = item.getString("productImage") ?: "", price = item.getNumber("price") ?: 0, quantity = (item.getNumber("quantity") ?: 0).toInt(), totalPrice = item.getNumber("totalPrice") ?: 0, reviewed = item.getBoolean("reviewed") ?: false))
                            j++
                        }
                    }
                }
                orderList.push(OrderVO(id = (orderObj.getNumber("id") ?: 0).toInt(), orderNo = orderObj.getString("orderNo") ?: "", totalAmount = orderObj.getNumber("totalAmount") ?: 0, payAmount = orderObj.getNumber("payAmount") ?: 0, freightAmount = orderObj.getNumber("freightAmount") ?: 0, status = (orderObj.getNumber("status") ?: 0).toInt(), statusDesc = orderObj.getString("statusDesc") ?: "", receiverName = orderObj.getString("receiverName") ?: "", receiverPhone = orderObj.getString("receiverPhone") ?: "", receiverAddress = orderObj.getString("receiverAddress") ?: "", remark = orderObj.getString("remark"), payTime = orderObj.getString("payTime"), deliverTime = orderObj.getString("deliverTime"), receiveTime = orderObj.getString("receiveTime"), cancelTime = orderObj.getString("cancelTime"), cancelReason = orderObj.getString("cancelReason"), createTime = orderObj.getString("createTime") ?: "", orderItems = orderItems, totalQuantity = (orderObj.getNumber("totalQuantity") ?: 0).toInt(), reviewed = orderObj.getBoolean("reviewed") ?: false, reviewCount = (orderObj.getNumber("reviewCount") ?: 0).toInt()))
                i++
            }
        }
        return orderList
    }
    open var switchTab = ::gen_switchTab_fn
    open fun gen_switchTab_fn(tab: String) {
        if (this.currentTab === tab) {
            return
        }
        this.currentTab = tab
        this.pageNum = 1
        this.orders = utsArrayOf()
        this.loadOrders()
    }
    open var onRefresh = ::gen_onRefresh_fn
    open fun gen_onRefresh_fn() {
        this.isRefreshing = true
        this.pageNum = 1
        this.loadOrders()
    }
    open var loadMore = ::gen_loadMore_fn
    open fun gen_loadMore_fn() {
        if (!this.hasMore || this.isLoading) {
            return
        }
        this.pageNum++
        this.loadOrders()
    }
    open var getStatusClass = ::gen_getStatusClass_fn
    open fun gen_getStatusClass_fn(status: Number): String {
        val classMap = Map<Number, String>(utsArrayOf(
            utsArrayOf(
                0,
                "status-pending"
            ),
            utsArrayOf(
                1,
                "status-unshipped"
            ),
            utsArrayOf(
                2,
                "status-shipped"
            ),
            utsArrayOf(
                3,
                "status-completed"
            ),
            utsArrayOf(
                4,
                "status-cancelled"
            ),
            utsArrayOf(
                5,
                "status-closed"
            )
        ))
        return classMap.get(status) ?: ""
    }
    open var goToDetail = ::gen_goToDetail_fn
    open fun gen_goToDetail_fn(id: Number) {
        uni_navigateTo(NavigateToOptions(url = "/pages/order/detail?id=" + id))
    }
    open var handleCancelOrder = ::gen_handleCancelOrder_fn
    open fun gen_handleCancelOrder_fn(order: OrderVO) {
        uni_showModal(ShowModalOptions(title = "提示", content = "确定取消订单吗？", success = fun(res){
            if (res.confirm) {
                cancelOrder(order.id, "用户取消").then(fun(){
                    order.status = 4
                    order.statusDesc = "已取消"
                    uni_showToast(ShowToastOptions(title = "订单已取消", icon = "success"))
                }
                ).`catch`(fun(err){
                    console.error("取消订单失败:", err, " at pages/order/list.uvue:231")
                    uni_showToast(ShowToastOptions(title = "取消失败", icon = "none"))
                }
                )
            }
        }
        ))
    }
    open var handlePayOrder = ::gen_handlePayOrder_fn
    open fun gen_handlePayOrder_fn(order: OrderVO) {
        uni_navigateTo(NavigateToOptions(url = "/pages/order/pay?orderId=" + order.id + "&orderNo=" + order.orderNo + "&amount=" + order.payAmount))
    }
    open var handleConfirmReceipt = ::gen_handleConfirmReceipt_fn
    open fun gen_handleConfirmReceipt_fn(order: OrderVO) {
        uni_showModal(ShowModalOptions(title = "提示", content = "确认已收到货吗？", success = fun(res){
            if (res.confirm) {
                confirmReceive(order.id).then(fun(){
                    order.status = 3
                    order.statusDesc = "已完成"
                    uni_showToast(ShowToastOptions(title = "确认收货成功", icon = "success"))
                }
                ).`catch`(fun(err){
                    console.error("确认收货失败:", err, " at pages/order/list.uvue:243")
                    uni_showToast(ShowToastOptions(title = "操作失败", icon = "none"))
                }
                )
            }
        }
        ))
    }
    open var handleDeleteOrder = ::gen_handleDeleteOrder_fn
    open fun gen_handleDeleteOrder_fn(order: OrderVO) {
        uni_showModal(ShowModalOptions(title = "提示", content = "确定要删除该订单吗？", success = fun(res){
            if (res.confirm) {
                deleteOrder(order.id).then(fun(){
                    uni_showToast(ShowToastOptions(title = "删除成功", icon = "success"))
                    this.pageNum = 1
                    this.loadOrders()
                }
                ).`catch`(fun(){
                    uni_showToast(ShowToastOptions(title = "删除失败", icon = "none"))
                }
                )
            }
        }
        ))
    }
    open var goToReview = ::gen_goToReview_fn
    open fun gen_goToReview_fn(order: OrderVO) {
        val items = order.orderItems
        if (items == null || items.length === 0) {
            uni_showToast(ShowToastOptions(title = "订单商品为空", icon = "none"))
            return
        }
        var target = items[0]
        run {
            var i: Number = 0
            while(i < items.length){
                val it = items[i]
                if (it.reviewed != true) {
                    target = it
                    break
                }
                i++
            }
        }
        uni_navigateTo(NavigateToOptions(url = "/pages/order/review?orderId=" + order.id + "&productId=" + target.productId + "&productName=" + UTSAndroid.consoleDebugError(encodeURIComponent(target.productName), " at pages/order/list.uvue:279") + "&productImage=" + UTSAndroid.consoleDebugError(encodeURIComponent(target.productImage), " at pages/order/list.uvue:279")))
    }
    open var buyAgain = ::gen_buyAgain_fn
    open fun gen_buyAgain_fn(order: OrderVO) {
        uni_showToast(ShowToastOptions(title = "商品已加入购物车", icon = "success"))
        uni_switchTab(SwitchTabOptions(url = "/pages/cart/index"))
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
                return utsMapOf("order-page" to padStyleMapOf(utsMapOf("backgroundColor" to "#f7f8fa", "display" to "flex", "flexDirection" to "column", "width" to "100%", "height" to "100%")), "tab-header" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "display" to "flex", "flexDirection" to "row", "paddingTop" to CSS_VAR_STATUS_BAR_HEIGHT, "zIndex" to 100, "boxShadow" to "0 1px 2px rgba(0, 0, 0, 0.05)")), "tab-item" to padStyleMapOf(utsMapOf("flex" to 1, "height" to 44, "display" to "flex", "flexDirection" to "column", "alignItems" to "center", "justifyContent" to "center", "position" to "relative")), "tab-text" to padStyleMapOf(utsMapOf("fontSize" to 13, "color" to "#666666")), "tab-text--active" to padStyleMapOf(utsMapOf("color" to "#0066CC", "fontWeight" to "700")), "active-line" to padStyleMapOf(utsMapOf("position" to "absolute", "bottom" to 0, "width" to 24, "height" to 3, "backgroundColor" to "#0066CC", "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "order-scroll" to padStyleMapOf(utsMapOf("flex" to 1, "width" to "100%", "height" to 0)), "loading-state" to padStyleMapOf(utsMapOf("paddingTop" to 60, "paddingRight" to 0, "paddingBottom" to 60, "paddingLeft" to 0, "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "loading-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#999999")), "order-list" to padStyleMapOf(utsMapOf("paddingTop" to 12, "paddingRight" to 16, "paddingBottom" to 12, "paddingLeft" to 16, "display" to "flex", "flexDirection" to "column")), "order-card" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "marginBottom" to 12, "boxShadow" to "0 2px 8px rgba(0, 0, 0, 0.02)", "display" to "flex", "flexDirection" to "column")), "card-header" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "marginBottom" to 16)), "order-number" to padStyleMapOf(utsMapOf("fontSize" to 13, "color" to "#666666")), "order-status" to padStyleMapOf(utsMapOf("fontSize" to 13, "fontWeight" to "400")), "status-pending" to padStyleMapOf(utsMapOf("color" to "#ff9800")), "status-unshipped" to padStyleMapOf(utsMapOf("color" to "#2196f3")), "status-shipped" to padStyleMapOf(utsMapOf("color" to "#0066CC")), "status-completed" to padStyleMapOf(utsMapOf("color" to "#999999")), "status-cancelled" to padStyleMapOf(utsMapOf("color" to "#cccccc")), "status-closed" to padStyleMapOf(utsMapOf("color" to "#cccccc")), "goods-preview" to padStyleMapOf(utsMapOf("marginBottom" to 16, "display" to "flex", "flexDirection" to "column")), "single-goods" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row")), "goods-img" to padStyleMapOf(utsMapOf("width" to 70, "height" to 70, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#f5f5f5", "marginRight" to 12)), "goods-info" to padStyleMapOf(utsMapOf("flex" to 1, "display" to "flex", "flexDirection" to "column", "justifyContent" to "flex-start")), "goods-name" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#333333", "marginBottom" to 8, "lineHeight" to 1.4)), "goods-spec" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999", "backgroundColor" to "#f5f5f5", "paddingTop" to 2, "paddingRight" to 6, "paddingBottom" to 2, "paddingLeft" to 6, "borderTopLeftRadius" to 4, "borderTopRightRadius" to 4, "borderBottomRightRadius" to 4, "borderBottomLeftRadius" to 4, "alignSelf" to "flex-start")), "goods-price-qty" to padStyleMapOf(utsMapOf("textAlign" to "right", "display" to "flex", "flexDirection" to "column", "alignItems" to "flex-end", "minWidth" to 60)), "price" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#333333", "fontWeight" to "700", "marginBottom" to 4)), "qty" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999")), "multi-goods" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center")), "goods-img-scroll" to padStyleMapOf(utsMapOf("flex" to 1, "whiteSpace" to "nowrap", "marginRight" to 12)), "goods-img-list" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row")), "goods-img-small" to padStyleMapOf(utsMapOf("width" to 60, "height" to 60, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#f5f5f5", "marginRight" to 8)), "total-qty-info" to padStyleMapOf(utsMapOf("textAlign" to "right", "minWidth" to 80, "display" to "flex", "flexDirection" to "column", "alignItems" to "flex-end")), "card-footer" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "flex-end", "alignItems" to "center", "borderTopWidth" to 1, "borderTopStyle" to "solid", "borderTopColor" to "#f5f5f5", "paddingTop" to 12)), "total-info" to padStyleMapOf(utsMapOf("marginRight" to "auto", "display" to "flex", "flexDirection" to "row", "alignItems" to "center")), "label" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999", "marginRight" to 4)), "amount" to padStyleMapOf(utsMapOf("fontSize" to 16, "fontWeight" to "700", "color" to "#333333")), "btn-group" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row")), "action-btn" to padStyleMapOf(utsMapOf("paddingTop" to 0, "paddingRight" to 16, "paddingBottom" to 0, "paddingLeft" to 16, "height" to 32, "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "display" to "flex", "alignItems" to "center", "justifyContent" to "center", "marginTop" to 0, "marginRight" to 0, "marginBottom" to 0, "marginLeft" to 8)), "secondary" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#dddddd", "borderRightColor" to "#dddddd", "borderBottomColor" to "#dddddd", "borderLeftColor" to "#dddddd")), "primary" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#0066CC", "borderRightColor" to "#0066CC", "borderBottomColor" to "#0066CC", "borderLeftColor" to "#0066CC")), "btn-text" to padStyleMapOf(utsMapOf("fontSize" to 13)), "secondary-text" to padStyleMapOf(utsMapOf("color" to "#666666")), "primary-text" to padStyleMapOf(utsMapOf("color" to "#0066CC")), "empty-state" to padStyleMapOf(utsMapOf("paddingTop" to 60, "paddingRight" to 0, "paddingBottom" to 60, "paddingLeft" to 0, "display" to "flex", "flexDirection" to "column", "alignItems" to "center", "justifyContent" to "center")), "empty-icon" to padStyleMapOf(utsMapOf("fontSize" to 48, "marginBottom" to 16, "color" to "#cccccc")), "empty-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#999999")), "load-more" to padStyleMapOf(utsMapOf("paddingTop" to 20, "paddingRight" to 0, "paddingBottom" to 20, "paddingLeft" to 0, "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "load-more-text" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = utsMapOf()
        var emits: Map<String, Any?> = utsMapOf()
        var props = normalizePropsOptions(utsMapOf())
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf()
        var components: Map<String, CreateVueComponent> = utsMapOf("BottomNav" to GenComponentsBottomNavClass)
    }
}
