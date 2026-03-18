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
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesOrderPay : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onLoad(fun(options: Any) {
            val opts = options as UTSJSONObject
            this.orderId = parseInt(opts.getString("orderId") ?: "0")
            this.orderNo = opts.getString("orderNo") ?: ""
            this.payAmount = parseFloat(opts.getString("amount") ?: "0")
        }
        , __ins)
        onPageShow(fun() {
            this.loadWalletBalance()
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return createElementVNode("view", utsMapOf("class" to "pay-page"), utsArrayOf(
            createElementVNode("view", utsMapOf("class" to "nav-bar"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "nav-back", "onClick" to _ctx.goBack), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "back-icon"), "‹")
                ), 8, utsArrayOf(
                    "onClick"
                )),
                createElementVNode("text", utsMapOf("class" to "nav-title"), "订单支付"),
                createElementVNode("view", utsMapOf("class" to "nav-placeholder"))
            )),
            createElementVNode("view", utsMapOf("class" to "amount-section"), utsArrayOf(
                createElementVNode("text", utsMapOf("class" to "amount-label"), "支付金额"),
                createElementVNode("view", utsMapOf("class" to "amount-row"), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "amount-symbol"), "¥"),
                    createElementVNode("text", utsMapOf("class" to "amount-value"), toDisplayString(_ctx.payAmount.toFixed(2)), 1)
                )),
                createElementVNode("text", utsMapOf("class" to "order-no"), "订单号：" + toDisplayString(_ctx.orderNo), 1)
            )),
            createElementVNode("view", utsMapOf("class" to "wallet-section"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "wallet-card"), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "wallet-icon"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "icon-text"), "💰")
                    )),
                    createElementVNode("view", utsMapOf("class" to "wallet-info"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "wallet-title"), "账户余额支付"),
                        createElementVNode("text", utsMapOf("class" to "wallet-balance"), "可用余额：¥" + toDisplayString(_ctx.walletBalance.toFixed(2)), 1)
                    ))
                )),
                if (_ctx.walletBalance < _ctx.payAmount) {
                    createElementVNode("view", utsMapOf("key" to 0, "class" to "balance-tip"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "tip-icon"), "⚠️"),
                        createElementVNode("text", utsMapOf("class" to "tip-text"), "余额不足，请先充值"),
                        createElementVNode("view", utsMapOf("class" to "recharge-btn", "onClick" to _ctx.goRecharge), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "recharge-text"), "去充值")
                        ), 8, utsArrayOf(
                            "onClick"
                        ))
                    ))
                } else {
                    createCommentVNode("v-if", true)
                }
            )),
            createElementVNode("view", utsMapOf("class" to "bottom-bar"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                    "pay-btn",
                    utsMapOf("pay-btn-disabled" to (_ctx.isPaying || _ctx.walletBalance < _ctx.payAmount))
                )), "onClick" to _ctx.handlePay), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "pay-btn-text"), toDisplayString(if (_ctx.isPaying) {
                        "支付中..."
                    } else {
                        "确认支付 ¥" + _ctx.payAmount.toFixed(2)
                    }
                    ), 1)
                ), 10, utsArrayOf(
                    "onClick"
                ))
            ))
        ))
    }
    open var orderId: Number by `$data`
    open var orderNo: String by `$data`
    open var payAmount: Number by `$data`
    open var walletBalance: Number by `$data`
    open var isPaying: Boolean by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return utsMapOf("orderId" to 0, "orderNo" to "", "payAmount" to 0, "walletBalance" to 0, "isPaying" to false)
    }
    open var goBack = ::gen_goBack_fn
    open fun gen_goBack_fn() {
        uni_navigateBack(null)
    }
    open var loadWalletBalance = ::gen_loadWalletBalance_fn
    open fun gen_loadWalletBalance_fn() {
        getWallet().then(fun(res){
            val data = res.data as UTSJSONObject
            this.walletBalance = data.getNumber("balance") ?: 0
        }
        ).`catch`(fun(_err){
            this.walletBalance = 0
        }
        )
    }
    open var goRecharge = ::gen_goRecharge_fn
    open fun gen_goRecharge_fn() {
        uni_navigateTo(NavigateToOptions(url = "/pages/user/wallet"))
    }
    open var handlePay = ::gen_handlePay_fn
    open fun gen_handlePay_fn() {
        if (this.isPaying) {
            return
        }
        if (this.walletBalance < this.payAmount) {
            uni_showToast(ShowToastOptions(title = "余额不足，请先充值", icon = "none"))
            return
        }
        this.isPaying = true
        uni_showLoading(ShowLoadingOptions(title = "支付中..."))
        payOrder(this.orderId).then(fun(_res){
            uni_hideLoading()
            this.isPaying = false
            uni_showToast(ShowToastOptions(title = "支付成功", icon = "success"))
            setTimeout(fun(){
                uni_navigateBack(NavigateBackOptions(delta = 1))
            }
            , 1500)
        }
        ).`catch`(fun(err){
            uni_hideLoading()
            this.isPaying = false
            console.error("支付失败:", err, " at pages/order/pay.uvue:110")
            uni_showToast(ShowToastOptions(title = "支付失败，请重试", icon = "none"))
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
                return utsMapOf("pay-page" to padStyleMapOf(utsMapOf("backgroundColor" to "#f5f5f5", "minHeight" to "1500rpx", "display" to "flex", "flexDirection" to "column")), "nav-bar" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "height" to "88rpx", "backgroundColor" to "#ffffff", "paddingTop" to CSS_VAR_STATUS_BAR_HEIGHT, "paddingRight" to "20rpx", "paddingBottom" to 0, "paddingLeft" to "20rpx")), "nav-back" to padStyleMapOf(utsMapOf("width" to "80rpx", "height" to "80rpx", "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "back-icon" to padStyleMapOf(utsMapOf("fontSize" to "48rpx", "color" to "#333333")), "nav-title" to padStyleMapOf(utsMapOf("fontSize" to "32rpx", "fontWeight" to "700", "color" to "#333333")), "nav-placeholder" to padStyleMapOf(utsMapOf("width" to "80rpx")), "amount-section" to padStyleMapOf(utsMapOf("backgroundColor" to "#0066CC", "paddingTop" to "60rpx", "paddingRight" to "40rpx", "paddingBottom" to "60rpx", "paddingLeft" to "40rpx", "display" to "flex", "flexDirection" to "column", "alignItems" to "center")), "amount-label" to padStyleMapOf(utsMapOf("fontSize" to "28rpx", "color" to "rgba(255,255,255,0.8)", "marginBottom" to "20rpx")), "amount-row" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "flex-end", "marginBottom" to "20rpx")), "amount-symbol" to padStyleMapOf(utsMapOf("fontSize" to "36rpx", "color" to "#ffffff", "fontWeight" to "700", "marginRight" to "8rpx", "marginBottom" to "8rpx")), "amount-value" to padStyleMapOf(utsMapOf("fontSize" to "72rpx", "color" to "#ffffff", "fontWeight" to "700")), "order-no" to padStyleMapOf(utsMapOf("fontSize" to "24rpx", "color" to "rgba(255,255,255,0.7)")), "wallet-section" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "marginTop" to "20rpx", "marginRight" to "20rpx", "marginBottom" to "20rpx", "marginLeft" to "20rpx", "borderTopLeftRadius" to "16rpx", "borderTopRightRadius" to "16rpx", "borderBottomRightRadius" to "16rpx", "borderBottomLeftRadius" to "16rpx", "paddingTop" to "30rpx", "paddingRight" to "30rpx", "paddingBottom" to "30rpx", "paddingLeft" to "30rpx")), "wallet-card" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center")), "wallet-icon" to padStyleMapOf(utsMapOf("width" to "80rpx", "height" to "80rpx", "borderTopLeftRadius" to "16rpx", "borderTopRightRadius" to "16rpx", "borderBottomRightRadius" to "16rpx", "borderBottomLeftRadius" to "16rpx", "backgroundColor" to "#FFF3E0", "display" to "flex", "alignItems" to "center", "justifyContent" to "center", "marginRight" to "24rpx")), "icon-text" to padStyleMapOf(utsMapOf("fontSize" to "40rpx")), "wallet-info" to padStyleMapOf(utsMapOf("flex" to 1, "display" to "flex", "flexDirection" to "column")), "wallet-title" to padStyleMapOf(utsMapOf("fontSize" to "30rpx", "color" to "#333333", "fontWeight" to "bold", "marginBottom" to "8rpx")), "wallet-balance" to padStyleMapOf(utsMapOf("fontSize" to "26rpx", "color" to "#666666")), "balance-tip" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "marginTop" to "24rpx", "paddingTop" to "20rpx", "paddingRight" to "20rpx", "paddingBottom" to "20rpx", "paddingLeft" to "20rpx", "backgroundColor" to "#FFF8E1", "borderTopLeftRadius" to "12rpx", "borderTopRightRadius" to "12rpx", "borderBottomRightRadius" to "12rpx", "borderBottomLeftRadius" to "12rpx")), "tip-icon" to padStyleMapOf(utsMapOf("fontSize" to "28rpx", "marginRight" to "12rpx")), "tip-text" to padStyleMapOf(utsMapOf("flex" to 1, "fontSize" to "26rpx", "color" to "#F57C00")), "recharge-btn" to padStyleMapOf(utsMapOf("paddingTop" to "12rpx", "paddingRight" to "24rpx", "paddingBottom" to "12rpx", "paddingLeft" to "24rpx", "backgroundColor" to "#0066CC", "borderTopLeftRadius" to "24rpx", "borderTopRightRadius" to "24rpx", "borderBottomRightRadius" to "24rpx", "borderBottomLeftRadius" to "24rpx")), "recharge-text" to padStyleMapOf(utsMapOf("fontSize" to "24rpx", "color" to "#ffffff")), "bottom-bar" to padStyleMapOf(utsMapOf("position" to "fixed", "bottom" to 0, "left" to 0, "right" to 0, "paddingTop" to "20rpx", "paddingRight" to "30rpx", "paddingBottom" to "40rpx", "paddingLeft" to "30rpx", "backgroundColor" to "#ffffff", "boxShadow" to "0 -2rpx 10rpx rgba(0, 0, 0, 0.05)")), "pay-btn" to padStyleMapOf(utsMapOf("height" to "88rpx", "backgroundColor" to "#0066CC", "borderTopLeftRadius" to "44rpx", "borderTopRightRadius" to "44rpx", "borderBottomRightRadius" to "44rpx", "borderBottomLeftRadius" to "44rpx", "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "pay-btn-disabled" to padStyleMapOf(utsMapOf("backgroundColor" to "#cccccc")), "pay-btn-text" to padStyleMapOf(utsMapOf("fontSize" to "32rpx", "color" to "#ffffff", "fontWeight" to "bold")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = utsMapOf()
        var emits: Map<String, Any?> = utsMapOf()
        var props = normalizePropsOptions(utsMapOf())
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf()
        var components: Map<String, CreateVueComponent> = utsMapOf()
    }
}
