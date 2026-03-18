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
import io.dcloud.uniapp.extapi.showModal as uni_showModal
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesOrderDetail : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onLoad(fun(options: Any) {
            val opts = options as UTSJSONObject
            val id = opts.get("id")
            if (id != null) {
                this.orderId = parseInt(id as String)
                this.loadOrderDetail()
            }
        }
        , __ins)
        onPageShow(fun() {
            if (this.orderId > 0) {
                this.loadOrderDetail()
            }
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return createElementVNode("view", utsMapOf("class" to "order-detail-page"), utsArrayOf(
            if (isTrue(_ctx.isLoading)) {
                createElementVNode("view", utsMapOf("key" to 0, "class" to "loading-state"), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "loading-text"), "加载中...")
                ))
            } else {
                createElementVNode(Fragment, utsMapOf("key" to 1), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "status-section"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "status-icon"), toDisplayString(_ctx.getStatusIcon(_ctx.orderDetail.status)), 1),
                        createElementVNode("text", utsMapOf("class" to "status-text"), toDisplayString(_ctx.orderDetail.statusDesc), 1),
                        if (isTrue(_ctx.orderDetail.status === 2 && _ctx.logistics.autoReceiveTime != null)) {
                            createElementVNode("text", utsMapOf("key" to 0, "class" to "status-tip"), toDisplayString(if (_ctx.logistics.delivered) {
                                "已送达，等待签收"
                            } else {
                                "运输中"
                            }) + " · 将于 " + toDisplayString(_ctx.formatAutoTime(_ctx.logistics.autoReceiveTime)) + " 自动确认 ", 1)
                        } else {
                            createCommentVNode("v-if", true)
                        }
                    )),
                    if (isTrue((_ctx.orderDetail.status === 2 || _ctx.orderDetail.status === 3) && _ctx.logistics.logisticsNo != "")) {
                        createElementVNode("view", utsMapOf("key" to 0, "class" to "logistics-section", "onClick" to _ctx.showLogisticsDetail), utsArrayOf(
                            createElementVNode("view", utsMapOf("class" to "section-header"), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "section-icon"), "🚚"),
                                createElementVNode("text", utsMapOf("class" to "section-title"), "物流信息"),
                                createElementVNode("text", utsMapOf("class" to "view-more"), "查看详情 ›")
                            )),
                            createElementVNode("view", utsMapOf("class" to "logistics-brief"), utsArrayOf(
                                createElementVNode("view", utsMapOf("class" to "logistics-company"), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "company-name"), toDisplayString(_ctx.logistics.logisticsCompany), 1),
                                    createElementVNode("text", utsMapOf("class" to "logistics-no"), toDisplayString(_ctx.logistics.logisticsNo), 1)
                                )),
                                if (_ctx.logistics.tracks.length > 0) {
                                    createElementVNode("view", utsMapOf("key" to 0, "class" to "latest-track"), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to "track-status"), toDisplayString(_ctx.logistics.tracks[0].status), 1),
                                        createElementVNode("text", utsMapOf("class" to "track-desc"), toDisplayString(_ctx.logistics.tracks[0].desc), 1),
                                        createElementVNode("text", utsMapOf("class" to "track-time"), toDisplayString(_ctx.logistics.tracks[0].time), 1)
                                    ))
                                } else {
                                    createCommentVNode("v-if", true)
                                }
                            ))
                        ), 8, utsArrayOf(
                            "onClick"
                        ))
                    } else {
                        createCommentVNode("v-if", true)
                    }
                    ,
                    createElementVNode("view", utsMapOf("class" to "address-section"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "section-header"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "section-icon"), "📍"),
                            createElementVNode("text", utsMapOf("class" to "section-title"), "收货信息")
                        )),
                        createElementVNode("view", utsMapOf("class" to "address-info"), utsArrayOf(
                            createElementVNode("view", utsMapOf("class" to "address-row"), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "receiver-name"), toDisplayString(_ctx.orderDetail.receiverName), 1),
                                createElementVNode("text", utsMapOf("class" to "receiver-phone"), toDisplayString(_ctx.orderDetail.receiverPhone), 1)
                            )),
                            createElementVNode("text", utsMapOf("class" to "receiver-address"), toDisplayString(_ctx.orderDetail.receiverAddress), 1)
                        ))
                    )),
                    createElementVNode("view", utsMapOf("class" to "goods-section"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "section-header"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "section-icon"), "📦"),
                            createElementVNode("text", utsMapOf("class" to "section-title"), "商品信息")
                        )),
                        createElementVNode("view", utsMapOf("class" to "goods-list"), utsArrayOf(
                            createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.orderDetail.orderItems, fun(goods, index, __index, _cached): Any {
                                return createElementVNode("view", utsMapOf("class" to "goods-item", "key" to index, "onClick" to fun(){
                                    _ctx.goToProduct(goods.productId)
                                }
                                ), utsArrayOf(
                                    createElementVNode("image", utsMapOf("class" to "goods-image", "src" to goods.productImage, "mode" to "aspectFill"), null, 8, utsArrayOf(
                                        "src"
                                    )),
                                    createElementVNode("view", utsMapOf("class" to "goods-info"), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to "goods-name"), toDisplayString(goods.productName), 1),
                                        if (isTrue(goods.skuName)) {
                                            createElementVNode("text", utsMapOf("key" to 0, "class" to "goods-spec"), toDisplayString(goods.skuName), 1)
                                        } else {
                                            createCommentVNode("v-if", true)
                                        }
                                        ,
                                        createElementVNode("view", utsMapOf("class" to "goods-price-row"), utsArrayOf(
                                            createElementVNode("text", utsMapOf("class" to "goods-price"), "¥" + toDisplayString(goods.price.toFixed(2)), 1),
                                            createElementVNode("text", utsMapOf("class" to "goods-quantity"), "×" + toDisplayString(goods.quantity), 1)
                                        ))
                                    ))
                                ), 8, utsArrayOf(
                                    "onClick"
                                ))
                            }
                            ), 128)
                        ))
                    )),
                    createElementVNode("view", utsMapOf("class" to "price-section"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "section-header"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "section-icon"), "💰"),
                            createElementVNode("text", utsMapOf("class" to "section-title"), "费用明细")
                        )),
                        createElementVNode("view", utsMapOf("class" to "price-list"), utsArrayOf(
                            createElementVNode("view", utsMapOf("class" to "price-row"), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "price-label"), "商品总价"),
                                createElementVNode("text", utsMapOf("class" to "price-value"), "¥" + toDisplayString(_ctx.orderDetail.totalAmount.toFixed(2)), 1)
                            )),
                            createElementVNode("view", utsMapOf("class" to "price-row"), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "price-label"), "运费"),
                                createElementVNode("text", utsMapOf("class" to "price-value"), "¥" + toDisplayString(_ctx.orderDetail.freightAmount.toFixed(2)), 1)
                            )),
                            createElementVNode("view", utsMapOf("class" to "price-row price-row-total"), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "price-label price-label-total"), "实付金额"),
                                createElementVNode("text", utsMapOf("class" to "price-value price-value-total"), "¥" + toDisplayString(_ctx.orderDetail.payAmount.toFixed(2)), 1)
                            ))
                        ))
                    )),
                    createElementVNode("view", utsMapOf("class" to "order-section"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "section-header"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "section-icon"), "📄"),
                            createElementVNode("text", utsMapOf("class" to "section-title"), "订单信息")
                        )),
                        createElementVNode("view", utsMapOf("class" to "order-info"), utsArrayOf(
                            createElementVNode("view", utsMapOf("class" to "info-row"), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "info-label"), "订单编号"),
                                createElementVNode("text", utsMapOf("class" to "info-value"), toDisplayString(_ctx.orderDetail.orderNo), 1)
                            )),
                            createElementVNode("view", utsMapOf("class" to "info-row"), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "info-label"), "下单时间"),
                                createElementVNode("text", utsMapOf("class" to "info-value"), toDisplayString(_ctx.orderDetail.createTime), 1)
                            )),
                            if (isTrue(_ctx.orderDetail.payTime)) {
                                createElementVNode("view", utsMapOf("key" to 0, "class" to "info-row"), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "info-label"), "支付时间"),
                                    createElementVNode("text", utsMapOf("class" to "info-value"), toDisplayString(_ctx.orderDetail.payTime), 1)
                                ))
                            } else {
                                createCommentVNode("v-if", true)
                            }
                            ,
                            if (isTrue(_ctx.orderDetail.deliverTime)) {
                                createElementVNode("view", utsMapOf("key" to 1, "class" to "info-row"), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "info-label"), "发货时间"),
                                    createElementVNode("text", utsMapOf("class" to "info-value"), toDisplayString(_ctx.orderDetail.deliverTime), 1)
                                ))
                            } else {
                                createCommentVNode("v-if", true)
                            }
                            ,
                            if (isTrue(_ctx.orderDetail.receiveTime)) {
                                createElementVNode("view", utsMapOf("key" to 2, "class" to "info-row"), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "info-label"), "收货时间"),
                                    createElementVNode("text", utsMapOf("class" to "info-value"), toDisplayString(_ctx.orderDetail.receiveTime), 1)
                                ))
                            } else {
                                createCommentVNode("v-if", true)
                            }
                            ,
                            if (isTrue(_ctx.orderDetail.remark)) {
                                createElementVNode("view", utsMapOf("key" to 3, "class" to "info-row"), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "info-label"), "订单备注"),
                                    createElementVNode("text", utsMapOf("class" to "info-value"), toDisplayString(_ctx.orderDetail.remark), 1)
                                ))
                            } else {
                                createCommentVNode("v-if", true)
                            }
                        ))
                    )),
                    if (isTrue(_ctx.hasActions)) {
                        createElementVNode("view", utsMapOf("key" to 1, "class" to "bottom-actions"), utsArrayOf(
                            if (_ctx.orderDetail.status === 0) {
                                createElementVNode("view", utsMapOf("key" to 0, "class" to "action-btn action-btn-secondary", "onClick" to _ctx.handleCancelOrder), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "action-btn-text action-btn-text-secondary"), "取消订单")
                                ), 8, utsArrayOf(
                                    "onClick"
                                ))
                            } else {
                                createCommentVNode("v-if", true)
                            },
                            if (_ctx.orderDetail.status === 0) {
                                createElementVNode("view", utsMapOf("key" to 1, "class" to "action-btn action-btn-primary", "onClick" to _ctx.handlePayOrder), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "action-btn-text action-btn-text-primary"), "立即支付")
                                ), 8, utsArrayOf(
                                    "onClick"
                                ))
                            } else {
                                createCommentVNode("v-if", true)
                            },
                            if (_ctx.orderDetail.status === 2) {
                                createElementVNode("view", utsMapOf("key" to 2, "class" to "action-btn action-btn-primary", "onClick" to _ctx.handleConfirmReceipt), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "action-btn-text action-btn-text-primary"), "确认收货")
                                ), 8, utsArrayOf(
                                    "onClick"
                                ))
                            } else {
                                createCommentVNode("v-if", true)
                            },
                            if (isTrue(_ctx.orderDetail.status === 3 && _ctx.orderDetail.reviewed != true)) {
                                createElementVNode("view", utsMapOf("key" to 3, "class" to "action-btn action-btn-secondary", "onClick" to _ctx.handleReview), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "action-btn-text action-btn-text-secondary"), "评价")
                                ), 8, utsArrayOf(
                                    "onClick"
                                ))
                            } else {
                                createCommentVNode("v-if", true)
                            },
                            if (isTrue(_ctx.orderDetail.status === 3 || _ctx.orderDetail.status === 4 || _ctx.orderDetail.status === 5)) {
                                createElementVNode("view", utsMapOf("key" to 4, "class" to "action-btn action-btn-secondary", "onClick" to _ctx.handleDeleteOrder), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "action-btn-text action-btn-text-secondary"), "删除订单")
                                ), 8, utsArrayOf(
                                    "onClick"
                                ))
                            } else {
                                createCommentVNode("v-if", true)
                            }
                        ))
                    } else {
                        createCommentVNode("v-if", true)
                    }
                ), 64)
            }
            ,
            if (isTrue(_ctx.showLogisticsModal)) {
                createElementVNode("view", utsMapOf("key" to 2, "class" to "logistics-modal", "onClick" to fun(){
                    _ctx.showLogisticsModal = false
                }), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "modal-content", "onClick" to withModifiers(fun(){}, utsArrayOf(
                        "stop"
                    ))), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "modal-header"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "modal-title"), "物流详情"),
                            createElementVNode("text", utsMapOf("class" to "modal-close", "onClick" to fun(){
                                _ctx.showLogisticsModal = false
                            }), "×", 8, utsArrayOf(
                                "onClick"
                            ))
                        )),
                        createElementVNode("view", utsMapOf("class" to "modal-company"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "company-label"), toDisplayString(_ctx.logistics.logisticsCompany), 1),
                            createElementVNode("text", utsMapOf("class" to "company-no"), toDisplayString(_ctx.logistics.logisticsNo), 1)
                        )),
                        createElementVNode("scroll-view", utsMapOf("class" to "tracks-scroll", "scroll-y" to "true"), utsArrayOf(
                            createElementVNode("view", utsMapOf("class" to "tracks-list"), utsArrayOf(
                                createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.logistics.tracks, fun(track, index, __index, _cached): Any {
                                    return createElementVNode("view", utsMapOf("class" to "track-item", "key" to index), utsArrayOf(
                                        createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                                            "track-dot",
                                            utsMapOf("track-dot-active" to (index === 0))
                                        ))), null, 2),
                                        if (index < _ctx.logistics.tracks.length - 1) {
                                            createElementVNode("view", utsMapOf("key" to 0, "class" to "track-line"))
                                        } else {
                                            createCommentVNode("v-if", true)
                                        },
                                        createElementVNode("view", utsMapOf("class" to "track-content"), utsArrayOf(
                                            createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                                                "track-status",
                                                utsMapOf("track-status-active" to (index === 0))
                                            ))), toDisplayString(track.status), 3),
                                            createElementVNode("text", utsMapOf("class" to "track-desc"), toDisplayString(track.desc), 1),
                                            createElementVNode("text", utsMapOf("class" to "track-time"), toDisplayString(track.time), 1)
                                        ))
                                    ))
                                }), 128)
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
            ,
            createElementVNode("view", utsMapOf("class" to "bottom-placeholder"))
        ))
    }
    open var orderId: Number by `$data`
    open var isLoading: Boolean by `$data`
    open var showLogisticsModal: Boolean by `$data`
    open var orderDetail: OrderVO by `$data`
    open var logistics: LogisticsVO by `$data`
    open var hasActions: Boolean by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return utsMapOf("orderId" to 0, "isLoading" to true, "showLogisticsModal" to false, "orderDetail" to OrderVO(id = 0, orderNo = "", totalAmount = 0, payAmount = 0, freightAmount = 0, status = 0, statusDesc = "", receiverName = "", receiverPhone = "", receiverAddress = "", remark = null, payTime = null, deliverTime = null, receiveTime = null, cancelTime = null, cancelReason = null, createTime = "", orderItems = utsArrayOf<OrderItemVO>(), totalQuantity = 0), "logistics" to LogisticsVO(orderId = 0, orderNo = "", logisticsNo = "", logisticsCompany = "", status = 0, statusDesc = "", delivered = false, deliveredDesc = "", shipTime = null, receiveTime = null, autoReceiveTime = null, tracks = utsArrayOf<LogisticsTrack>()), "hasActions" to computed<Boolean>(fun(): Boolean {
            val status = this.orderDetail.status
            return status === 0 || status === 2 || status === 3 || status === 4 || status === 5
        }
        ))
    }
    open var loadOrderDetail = ::gen_loadOrderDetail_fn
    open fun gen_loadOrderDetail_fn() {
        this.isLoading = true
        getOrderDetail(this.orderId).then(fun(res){
            val data = res.data as UTSJSONObject
            this.parseOrderDetail(data)
            this.isLoading = false
            if (this.orderDetail.status === 2 || this.orderDetail.status === 3) {
                this.loadLogistics()
            }
        }
        ).`catch`(fun(err){
            console.error("获取订单详情失败:", err, " at pages/order/detail.uvue:249")
            this.isLoading = false
            uni_showToast(ShowToastOptions(title = "获取订单详情失败", icon = "none"))
        }
        )
    }
    open var loadLogistics = ::gen_loadLogistics_fn
    open fun gen_loadLogistics_fn() {
        getOrderLogistics(this.orderId).then(fun(res){
            val data = res.data as UTSJSONObject
            this.parseLogistics(data)
        }
        ).`catch`(fun(err){
            console.error("获取物流信息失败:", err, " at pages/order/detail.uvue:259")
        }
        )
    }
    open var parseLogistics = ::gen_parseLogistics_fn
    open fun gen_parseLogistics_fn(data: UTSJSONObject) {
        val tracksArr = data.getArray("tracks")
        val tracks: UTSArray<LogisticsTrack> = utsArrayOf()
        if (tracksArr != null) {
            run {
                var i: Number = 0
                while(i < tracksArr.length){
                    val t = tracksArr[i] as UTSJSONObject
                    tracks.push(LogisticsTrack(time = t.getString("time") ?: "", status = t.getString("status") ?: "", desc = t.getString("desc") ?: ""))
                    i++
                }
            }
        }
        this.logistics = LogisticsVO(orderId = (data.getNumber("orderId") ?: 0).toInt(), orderNo = data.getString("orderNo") ?: "", logisticsNo = data.getString("logisticsNo") ?: "", logisticsCompany = data.getString("logisticsCompany") ?: "", status = (data.getNumber("status") ?: 0).toInt(), statusDesc = data.getString("statusDesc") ?: "", delivered = data.getBoolean("delivered") ?: false, deliveredDesc = data.getString("deliveredDesc") ?: "", shipTime = data.getString("shipTime"), receiveTime = data.getString("receiveTime"), autoReceiveTime = data.getString("autoReceiveTime"), tracks = tracks)
    }
    open var parseOrderDetail = ::gen_parseOrderDetail_fn
    open fun gen_parseOrderDetail_fn(data: UTSJSONObject) {
        val itemsArr = data.getArray("orderItems")
        val orderItems: UTSArray<OrderItemVO> = utsArrayOf()
        if (itemsArr != null) {
            run {
                var i: Number = 0
                while(i < itemsArr.length){
                    val item = itemsArr[i] as UTSJSONObject
                    orderItems.push(OrderItemVO(id = (item.getNumber("id") ?: 0).toInt(), productId = (item.getNumber("productId") ?: 0).toInt(), skuId = (item.getNumber("skuId") ?: 0).toInt(), productName = item.getString("productName") ?: "", skuName = item.getString("skuName") ?: "", productImage = item.getString("productImage") ?: "", price = item.getNumber("price") ?: 0, quantity = (item.getNumber("quantity") ?: 0).toInt(), totalPrice = item.getNumber("totalPrice") ?: 0, reviewed = item.getBoolean("reviewed") ?: false))
                    i++
                }
            }
        }
        this.orderDetail = OrderVO(id = (data.getNumber("id") ?: 0).toInt(), orderNo = data.getString("orderNo") ?: "", totalAmount = data.getNumber("totalAmount") ?: 0, payAmount = data.getNumber("payAmount") ?: 0, freightAmount = data.getNumber("freightAmount") ?: 0, status = (data.getNumber("status") ?: 0).toInt(), statusDesc = data.getString("statusDesc") ?: "", receiverName = data.getString("receiverName") ?: "", receiverPhone = data.getString("receiverPhone") ?: "", receiverAddress = data.getString("receiverAddress") ?: "", remark = data.getString("remark"), payTime = data.getString("payTime"), deliverTime = data.getString("deliverTime"), receiveTime = data.getString("receiveTime"), cancelTime = data.getString("cancelTime"), cancelReason = data.getString("cancelReason"), createTime = data.getString("createTime") ?: "", orderItems = orderItems, totalQuantity = (data.getNumber("totalQuantity") ?: 0).toInt(), reviewed = data.getBoolean("reviewed") ?: false, reviewCount = (data.getNumber("reviewCount") ?: 0).toInt())
    }
    open var formatAutoTime = ::gen_formatAutoTime_fn
    open fun gen_formatAutoTime_fn(timeStr: String?): String {
        if (timeStr == null) {
            return ""
        }
        if (timeStr.length >= 16) {
            return timeStr.substring(5, 16)
        }
        return timeStr
    }
    open var getStatusIcon = ::gen_getStatusIcon_fn
    open fun gen_getStatusIcon_fn(status: Number): String {
        val iconMap = Map<Number, String>(utsArrayOf(
            utsArrayOf(
                0,
                "⏳"
            ),
            utsArrayOf(
                1,
                "✅"
            ),
            utsArrayOf(
                2,
                "🚚"
            ),
            utsArrayOf(
                3,
                "🎉"
            ),
            utsArrayOf(
                4,
                "❌"
            ),
            utsArrayOf(
                5,
                "⏰"
            )
        ))
        return iconMap.get(status) ?: "❓"
    }
    open var showLogisticsDetail = ::gen_showLogisticsDetail_fn
    open fun gen_showLogisticsDetail_fn() {
        this.showLogisticsModal = true
    }
    open var goToProduct = ::gen_goToProduct_fn
    open fun gen_goToProduct_fn(productId: Number) {
        uni_navigateTo(NavigateToOptions(url = "/pages/product/detail?id=" + productId))
    }
    open var handleCancelOrder = ::gen_handleCancelOrder_fn
    open fun gen_handleCancelOrder_fn() {
        uni_showModal(ShowModalOptions(title = "确认取消", content = "确定要取消这个订单吗？", success = fun(res){
            if (res.confirm) {
                cancelOrder(this.orderId, "用户取消").then(fun(){
                    this.orderDetail.status = 4
                    this.orderDetail.statusDesc = "已取消"
                    uni_showToast(ShowToastOptions(title = "订单已取消", icon = "success"))
                }
                ).`catch`(fun(err){
                    console.error("取消订单失败:", err, " at pages/order/detail.uvue:358")
                    uni_showToast(ShowToastOptions(title = "取消失败", icon = "none"))
                }
                )
            }
        }
        ))
    }
    open var handlePayOrder = ::gen_handlePayOrder_fn
    open fun gen_handlePayOrder_fn() {
        uni_navigateTo(NavigateToOptions(url = "/pages/order/pay?orderId=" + this.orderId + "&orderNo=" + this.orderDetail.orderNo + "&amount=" + this.orderDetail.payAmount))
    }
    open var handleConfirmReceipt = ::gen_handleConfirmReceipt_fn
    open fun gen_handleConfirmReceipt_fn() {
        uni_showModal(ShowModalOptions(title = "确认收货", content = "确认已收到商品吗？", success = fun(res){
            if (res.confirm) {
                confirmReceive(this.orderId).then(fun(){
                    this.orderDetail.status = 3
                    this.orderDetail.statusDesc = "已完成"
                    uni_showToast(ShowToastOptions(title = "确认收货成功", icon = "success"))
                }
                ).`catch`(fun(err){
                    console.error("确认收货失败:", err, " at pages/order/detail.uvue:374")
                    uni_showToast(ShowToastOptions(title = "操作失败", icon = "none"))
                }
                )
            }
        }
        ))
    }
    open var handleReview = ::gen_handleReview_fn
    open fun gen_handleReview_fn() {
        val items = this.orderDetail.orderItems
        if (items == null || items.length === 0) {
            uni_showToast(ShowToastOptions(title = "订单商品为空", icon = "none"))
            return
        }
        val pending: UTSArray<OrderItemVO> = utsArrayOf()
        run {
            var i: Number = 0
            while(i < items.length){
                val it = items[i]
                if (it.reviewed != true) {
                    pending.push(it)
                }
                i++
            }
        }
        if (pending.length === 0) {
            uni_showToast(ShowToastOptions(title = "该订单已全部评价", icon = "none"))
            return
        }
        val first = pending[0]
        uni_navigateTo(NavigateToOptions(url = "/pages/order/review?orderId=" + this.orderId + "&productId=" + first.productId + "&productName=" + UTSAndroid.consoleDebugError(encodeURIComponent(first.productName), " at pages/order/detail.uvue:395") + "&productImage=" + UTSAndroid.consoleDebugError(encodeURIComponent(first.productImage), " at pages/order/detail.uvue:395") + "&reviewIndex=0&reviewTotal=" + pending.length))
    }
    open var handleDeleteOrder = ::gen_handleDeleteOrder_fn
    open fun gen_handleDeleteOrder_fn() {
        uni_showModal(ShowModalOptions(title = "提示", content = "确定删除订单记录吗？", success = fun(res){
            if (res.confirm) {
                deleteOrder(this.orderId).then(fun(){
                    uni_showToast(ShowToastOptions(title = "删除成功", icon = "success"))
                    setTimeout(fun(){
                        uni_navigateBack(null)
                    }
                    , 1500)
                }
                ).`catch`(fun(err){
                    console.error("删除订单失败:", err, " at pages/order/detail.uvue:404")
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
                return utsMapOf("order-detail-page" to padStyleMapOf(utsMapOf("backgroundColor" to "#f5f5f5", "paddingBottom" to "140rpx", "minHeight" to "1500rpx")), "loading-state" to padStyleMapOf(utsMapOf("paddingTop" to 100, "paddingRight" to 0, "paddingBottom" to 100, "paddingLeft" to 0, "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "loading-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#999999")), "status-section" to padStyleMapOf(utsMapOf("backgroundColor" to "#0066CC", "paddingTop" to "60rpx", "paddingRight" to "30rpx", "paddingBottom" to "60rpx", "paddingLeft" to "30rpx", "textAlign" to "center", "color" to "#ffffff")), "status-icon" to padStyleMapOf(utsMapOf("fontSize" to "80rpx", "marginBottom" to "20rpx")), "status-text" to padStyleMapOf(utsMapOf("fontSize" to "36rpx", "fontWeight" to "700")), "status-tip" to padStyleMapOf(utsMapOf("fontSize" to "24rpx", "color" to "rgba(255,255,255,0.8)", "marginTop" to "16rpx")), "logistics-section" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "marginTop" to "20rpx", "marginRight" to "30rpx", "marginBottom" to "20rpx", "marginLeft" to "30rpx", "borderTopLeftRadius" to "16rpx", "borderTopRightRadius" to "16rpx", "borderBottomRightRadius" to "16rpx", "borderBottomLeftRadius" to "16rpx", "paddingTop" to "30rpx", "paddingRight" to "30rpx", "paddingBottom" to "30rpx", "paddingLeft" to "30rpx", "boxShadow" to "0 4rpx 12rpx rgba(0, 0, 0, 0.05)")), "address-section" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "marginTop" to "20rpx", "marginRight" to "30rpx", "marginBottom" to "20rpx", "marginLeft" to "30rpx", "borderTopLeftRadius" to "16rpx", "borderTopRightRadius" to "16rpx", "borderBottomRightRadius" to "16rpx", "borderBottomLeftRadius" to "16rpx", "paddingTop" to "30rpx", "paddingRight" to "30rpx", "paddingBottom" to "30rpx", "paddingLeft" to "30rpx", "boxShadow" to "0 4rpx 12rpx rgba(0, 0, 0, 0.05)")), "goods-section" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "marginTop" to "20rpx", "marginRight" to "30rpx", "marginBottom" to "20rpx", "marginLeft" to "30rpx", "borderTopLeftRadius" to "16rpx", "borderTopRightRadius" to "16rpx", "borderBottomRightRadius" to "16rpx", "borderBottomLeftRadius" to "16rpx", "paddingTop" to "30rpx", "paddingRight" to "30rpx", "paddingBottom" to "30rpx", "paddingLeft" to "30rpx", "boxShadow" to "0 4rpx 12rpx rgba(0, 0, 0, 0.05)")), "price-section" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "marginTop" to "20rpx", "marginRight" to "30rpx", "marginBottom" to "20rpx", "marginLeft" to "30rpx", "borderTopLeftRadius" to "16rpx", "borderTopRightRadius" to "16rpx", "borderBottomRightRadius" to "16rpx", "borderBottomLeftRadius" to "16rpx", "paddingTop" to "30rpx", "paddingRight" to "30rpx", "paddingBottom" to "30rpx", "paddingLeft" to "30rpx", "boxShadow" to "0 4rpx 12rpx rgba(0, 0, 0, 0.05)")), "order-section" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "marginTop" to "20rpx", "marginRight" to "30rpx", "marginBottom" to "20rpx", "marginLeft" to "30rpx", "borderTopLeftRadius" to "16rpx", "borderTopRightRadius" to "16rpx", "borderBottomRightRadius" to "16rpx", "borderBottomLeftRadius" to "16rpx", "paddingTop" to "30rpx", "paddingRight" to "30rpx", "paddingBottom" to "30rpx", "paddingLeft" to "30rpx", "boxShadow" to "0 4rpx 12rpx rgba(0, 0, 0, 0.05)")), "section-header" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "marginBottom" to "24rpx")), "section-icon" to padStyleMapOf(utsMapOf("fontSize" to "32rpx", "marginRight" to "12rpx")), "section-title" to padStyleMapOf(utsMapOf("fontSize" to "32rpx", "fontWeight" to "700", "color" to "#333333", "flex" to 1)), "view-more" to padStyleMapOf(utsMapOf("fontSize" to "24rpx", "color" to "#0066CC")), "logistics-company" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "marginBottom" to "16rpx")), "company-name" to padStyleMapOf(utsMapOf("fontSize" to "28rpx", "color" to "#333333", "fontWeight" to "bold", "marginRight" to "16rpx")), "logistics-no" to padStyleMapOf(utsMapOf("fontSize" to "24rpx", "color" to "#999999")), "latest-track" to padStyleMapOf(utsMapOf("backgroundColor" to "#f8f8f8", "paddingTop" to "20rpx", "paddingRight" to "20rpx", "paddingBottom" to "20rpx", "paddingLeft" to "20rpx", "borderTopLeftRadius" to "12rpx", "borderTopRightRadius" to "12rpx", "borderBottomRightRadius" to "12rpx", "borderBottomLeftRadius" to "12rpx")), "track-status" to padStyleMapOf(utsMapOf("fontSize" to "28rpx", "color" to "#0066CC", "fontWeight" to "bold", "marginBottom" to "8rpx")), "track-desc" to padStyleMapOf(utsMapOf("fontSize" to "26rpx", "color" to "#666666", "marginBottom" to "8rpx")), "track-time" to padStyleMapOf(utsMapOf("fontSize" to "24rpx", "color" to "#999999")), "address-row" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "marginBottom" to "12rpx")), "receiver-name" to padStyleMapOf(utsMapOf("fontSize" to "28rpx", "color" to "#333333", "fontWeight" to "700")), "receiver-phone" to padStyleMapOf(utsMapOf("fontSize" to "28rpx", "color" to "#666666")), "receiver-address" to padStyleMapOf(utsMapOf("fontSize" to "26rpx", "color" to "#666666", "lineHeight" to 1.4)), "goods-item" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "paddingTop" to "20rpx", "paddingRight" to 0, "paddingBottom" to "20rpx", "paddingLeft" to 0, "borderBottomWidth" to "1rpx", "borderBottomStyle" to "solid", "borderBottomColor" to "#f0f0f0")), "goods-image" to padStyleMapOf(utsMapOf("width" to "120rpx", "height" to "120rpx", "borderTopLeftRadius" to "12rpx", "borderTopRightRadius" to "12rpx", "borderBottomRightRadius" to "12rpx", "borderBottomLeftRadius" to "12rpx", "marginRight" to "20rpx")), "goods-info" to padStyleMapOf(utsMapOf("flex" to 1)), "goods-name" to padStyleMapOf(utsMapOf("fontSize" to "28rpx", "color" to "#333333", "marginBottom" to "8rpx")), "goods-spec" to padStyleMapOf(utsMapOf("fontSize" to "24rpx", "color" to "#999999", "marginBottom" to "12rpx")), "goods-price-row" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center")), "goods-price" to padStyleMapOf(utsMapOf("fontSize" to "28rpx", "color" to "#FF4D4F", "fontWeight" to "400")), "goods-quantity" to padStyleMapOf(utsMapOf("fontSize" to "24rpx", "color" to "#666666")), "price-row" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "marginBottom" to "20rpx")), "price-row-total" to padStyleMapOf(utsMapOf("paddingTop" to "20rpx", "borderTopWidth" to "1rpx", "borderTopStyle" to "solid", "borderTopColor" to "#f0f0f0")), "price-label-total" to padStyleMapOf(utsMapOf("fontSize" to "32rpx", "fontWeight" to "700", "color" to "#333333")), "price-value-total" to padStyleMapOf(utsMapOf("fontSize" to "32rpx", "fontWeight" to "700", "color" to "#333333")), "price-label" to padStyleMapOf(utsMapOf("fontSize" to "28rpx", "color" to "#666666")), "price-value" to padStyleMapOf(utsMapOf("fontSize" to "28rpx", "color" to "#333333")), "info-row" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "marginBottom" to "20rpx")), "info-label" to padStyleMapOf(utsMapOf("fontSize" to "28rpx", "color" to "#666666")), "info-value" to padStyleMapOf(utsMapOf("fontSize" to "28rpx", "color" to "#333333", "textAlign" to "right", "maxWidth" to "400rpx", "overflow" to "hidden", "textOverflow" to "ellipsis")), "bottom-actions" to padStyleMapOf(utsMapOf("position" to "fixed", "bottom" to 0, "left" to 0, "right" to 0, "backgroundColor" to "#ffffff", "paddingTop" to "30rpx", "paddingRight" to "30rpx", "paddingBottom" to "30rpx", "paddingLeft" to "30rpx", "boxShadow" to "0 -2rpx 8rpx rgba(0, 0, 0, 0.1)", "display" to "flex", "flexDirection" to "row", "justifyContent" to "flex-end")), "action-btn" to padStyleMapOf(utsMapOf("paddingTop" to "20rpx", "paddingRight" to "40rpx", "paddingBottom" to "20rpx", "paddingLeft" to "40rpx", "borderTopLeftRadius" to "8rpx", "borderTopRightRadius" to "8rpx", "borderBottomRightRadius" to "8rpx", "borderBottomLeftRadius" to "8rpx", "borderTopWidth" to "1rpx", "borderRightWidth" to "1rpx", "borderBottomWidth" to "1rpx", "borderLeftWidth" to "1rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "rgba(0,0,0,0)", "borderRightColor" to "rgba(0,0,0,0)", "borderBottomColor" to "rgba(0,0,0,0)", "borderLeftColor" to "rgba(0,0,0,0)", "minWidth" to "160rpx", "marginLeft" to "20rpx", "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "action-btn-primary" to padStyleMapOf(utsMapOf("backgroundColor" to "#0066CC")), "action-btn-secondary" to padStyleMapOf(utsMapOf("backgroundColor" to "rgba(0,0,0,0)", "borderTopColor" to "#e5e5e5", "borderRightColor" to "#e5e5e5", "borderBottomColor" to "#e5e5e5", "borderLeftColor" to "#e5e5e5")), "action-btn-text" to padStyleMapOf(utsMapOf("fontSize" to "28rpx")), "action-btn-text-primary" to padStyleMapOf(utsMapOf("color" to "#ffffff")), "action-btn-text-secondary" to padStyleMapOf(utsMapOf("color" to "#666666")), "bottom-placeholder" to padStyleMapOf(utsMapOf("height" to "40rpx")), "logistics-modal" to padStyleMapOf(utsMapOf("position" to "fixed", "top" to 0, "left" to 0, "right" to 0, "bottom" to 0, "backgroundColor" to "rgba(0,0,0,0.5)", "display" to "flex", "alignItems" to "flex-end", "zIndex" to 999)), "modal-content" to padStyleMapOf(utsMapOf("width" to "100%", "backgroundColor" to "#ffffff", "borderTopLeftRadius" to "24rpx", "borderTopRightRadius" to "24rpx", "borderBottomRightRadius" to 0, "borderBottomLeftRadius" to 0, "maxHeight" to "1000rpx", "display" to "flex", "flexDirection" to "column")), "modal-header" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "paddingTop" to "30rpx", "paddingRight" to "30rpx", "paddingBottom" to "30rpx", "paddingLeft" to "30rpx", "borderBottomWidth" to "1rpx", "borderBottomStyle" to "solid", "borderBottomColor" to "#f0f0f0")), "modal-title" to padStyleMapOf(utsMapOf("fontSize" to "32rpx", "fontWeight" to "700", "color" to "#333333")), "modal-close" to padStyleMapOf(utsMapOf("fontSize" to "48rpx", "color" to "#999999", "paddingTop" to 0, "paddingRight" to "10rpx", "paddingBottom" to 0, "paddingLeft" to "10rpx")), "modal-company" to padStyleMapOf(utsMapOf("paddingTop" to "20rpx", "paddingRight" to "30rpx", "paddingBottom" to "20rpx", "paddingLeft" to "30rpx", "backgroundColor" to "#f8f8f8")), "company-label" to padStyleMapOf(utsMapOf("fontSize" to "28rpx", "color" to "#333333", "fontWeight" to "bold")), "company-no" to padStyleMapOf(utsMapOf("fontSize" to "24rpx", "color" to "#999999", "marginLeft" to "16rpx")), "tracks-scroll" to padStyleMapOf(utsMapOf("flex" to 1, "paddingTop" to "30rpx", "paddingRight" to "30rpx", "paddingBottom" to "30rpx", "paddingLeft" to "30rpx")), "track-item" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "position" to "relative", "paddingLeft" to "40rpx", "paddingBottom" to "40rpx")), "track-dot" to padStyleMapOf(utsMapOf("position" to "absolute", "left" to 0, "top" to "8rpx", "width" to "16rpx", "height" to "16rpx", "borderTopLeftRadius" to "8rpx", "borderTopRightRadius" to "8rpx", "borderBottomRightRadius" to "8rpx", "borderBottomLeftRadius" to "8rpx", "backgroundColor" to "#cccccc")), "track-dot-active" to padStyleMapOf(utsMapOf("backgroundColor" to "#0066CC", "width" to "20rpx", "height" to "20rpx", "left" to "-2rpx")), "track-line" to padStyleMapOf(utsMapOf("position" to "absolute", "left" to "7rpx", "top" to "28rpx", "width" to "2rpx", "height" to "80rpx", "backgroundColor" to "#e0e0e0")), "track-content" to padStyleMapOf(utsMapOf("flex" to 1)), "track-status-active" to padStyleMapOf(utsMapOf("color" to "#0066CC", "fontWeight" to "bold")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = utsMapOf()
        var emits: Map<String, Any?> = utsMapOf()
        var props = normalizePropsOptions(utsMapOf())
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf()
        var components: Map<String, CreateVueComponent> = utsMapOf()
    }
}
