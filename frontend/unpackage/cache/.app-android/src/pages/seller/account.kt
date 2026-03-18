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
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesSellerAccount : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onLoad(fun(_: OnLoadOptions) {
            this.loadAccountInfo()
            this.loadTransactions()
        }
        , __ins)
        onPageShow(fun() {
            this.loadAccountInfo()
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return createElementVNode("view", utsMapOf("class" to "page"), utsArrayOf(
            createElementVNode("view", utsMapOf("class" to "header-bg"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "status-bar")),
                createElementVNode("view", utsMapOf("class" to "nav-bar"), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "nav-back", "onClick" to _ctx.goBack), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "back-arrow"), "‹")
                    ), 8, utsArrayOf(
                        "onClick"
                    )),
                    createElementVNode("text", utsMapOf("class" to "nav-title"), "商家账户"),
                    createElementVNode("view", utsMapOf("class" to "nav-placeholder"))
                )),
                createElementVNode("view", utsMapOf("class" to "balance-box"), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "balance-header"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "balance-label"), "可用余额(元)"),
                        createElementVNode("view", utsMapOf("class" to "eye-btn", "onClick" to _ctx.toggleEye), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "eye-text"), toDisplayString(if (_ctx.showBalance) {
                                "隐藏"
                            } else {
                                "显示"
                            }
                            ), 1)
                        ), 8, utsArrayOf(
                            "onClick"
                        ))
                    )),
                    createElementVNode("view", utsMapOf("class" to "balance-amount"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "amount-symbol"), "¥"),
                        createElementVNode("text", utsMapOf("class" to "amount-num"), toDisplayString(if (_ctx.showBalance) {
                            _ctx.formatMoney(_ctx.accountInfo.availableBalance)
                        } else {
                            "****"
                        }
                        ), 1)
                    )),
                    createElementVNode("view", utsMapOf("class" to "balance-actions"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "action-btn-primary", "onClick" to _ctx.handleWithdraw), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "action-btn-primary-text"), "提现")
                        ), 8, utsArrayOf(
                            "onClick"
                        ))
                    ))
                ))
            )),
            createElementVNode("scroll-view", utsMapOf("class" to "main-scroll", "scroll-y" to "true", "show-scrollbar" to false, "onScrolltolower" to _ctx.loadMore), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "stats-card"), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "stats-item"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "stats-num"), toDisplayString(if (_ctx.showBalance) {
                            _ctx.formatMoney(_ctx.accountInfo.frozenAmount)
                        } else {
                            "****"
                        }
                        ), 1),
                        createElementVNode("text", utsMapOf("class" to "stats-name"), "冻结金额")
                    )),
                    createElementVNode("view", utsMapOf("class" to "stats-divider")),
                    createElementVNode("view", utsMapOf("class" to "stats-item"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "stats-num stats-num-green"), toDisplayString(if (_ctx.showBalance) {
                            _ctx.formatMoney(_ctx.accountInfo.totalIncome)
                        } else {
                            "****"
                        }
                        ), 1),
                        createElementVNode("text", utsMapOf("class" to "stats-name"), "累计收入")
                    )),
                    createElementVNode("view", utsMapOf("class" to "stats-divider")),
                    createElementVNode("view", utsMapOf("class" to "stats-item"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "stats-num stats-num-orange"), toDisplayString(if (_ctx.showBalance) {
                            _ctx.formatMoney(_ctx.accountInfo.totalWithdraw)
                        } else {
                            "****"
                        }
                        ), 1),
                        createElementVNode("text", utsMapOf("class" to "stats-name"), "累计提现")
                    ))
                )),
                createElementVNode("view", utsMapOf("class" to "record-card"), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "record-header"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "record-title"), "交易记录"),
                        createElementVNode("view", utsMapOf("class" to "filter-tabs"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                                "filter-tab",
                                utsMapOf("filter-tab-active" to (_ctx.currentFilter == "all"))
                            )), "onClick" to fun(){
                                _ctx.changeFilter("all")
                            }
                            ), "全部", 10, utsArrayOf(
                                "onClick"
                            )),
                            createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                                "filter-tab",
                                utsMapOf("filter-tab-active" to (_ctx.currentFilter == "income"))
                            )), "onClick" to fun(){
                                _ctx.changeFilter("income")
                            }
                            ), "收入", 10, utsArrayOf(
                                "onClick"
                            )),
                            createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                                "filter-tab",
                                utsMapOf("filter-tab-active" to (_ctx.currentFilter == "withdraw"))
                            )), "onClick" to fun(){
                                _ctx.changeFilter("withdraw")
                            }
                            ), "提现", 10, utsArrayOf(
                                "onClick"
                            ))
                        ))
                    )),
                    if (isTrue(_ctx.isLoading && _ctx.transactions.length == 0)) {
                        createElementVNode("view", utsMapOf("key" to 0, "class" to "loading-box"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "loading-text"), "加载中...")
                        ))
                    } else {
                        createElementVNode("view", utsMapOf("key" to 1, "class" to "record-list"), utsArrayOf(
                            createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.transactions, fun(item, index, __index, _cached): Any {
                                return createElementVNode("view", utsMapOf("class" to "record-item", "key" to index), utsArrayOf(
                                    createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                                        "item-icon",
                                        _ctx.getIconClass(item.type)
                                    ))), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to "icon-text"), toDisplayString(_ctx.getIcon(item.type)), 1)
                                    ), 2),
                                    createElementVNode("view", utsMapOf("class" to "item-info"), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to "item-title"), toDisplayString(item.typeDesc), 1),
                                        if (item.description != "") {
                                            createElementVNode("text", utsMapOf("key" to 0, "class" to "item-desc"), toDisplayString(item.description), 1)
                                        } else {
                                            createCommentVNode("v-if", true)
                                        }
                                        ,
                                        createElementVNode("text", utsMapOf("class" to "item-time"), toDisplayString(_ctx.formatTime(item.createTime)), 1)
                                    )),
                                    createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                                        "item-amount",
                                        utsMapOf("item-amount-income" to (item.amount > 0))
                                    ))), toDisplayString(if (item.amount > 0) {
                                        "+"
                                    } else {
                                        ""
                                    }
                                    ) + toDisplayString(_ctx.formatMoney(item.amount)), 3)
                                ))
                            }
                            ), 128)
                        ))
                    }
                    ,
                    if (_ctx.transactions.length > 0) {
                        createElementVNode("view", utsMapOf("key" to 2, "class" to "list-footer"), utsArrayOf(
                            if (isTrue(_ctx.isLoading)) {
                                createElementVNode("text", utsMapOf("key" to 0, "class" to "footer-text"), "加载中...")
                            } else {
                                if (isTrue(!_ctx.hasMore)) {
                                    createElementVNode("text", utsMapOf("key" to 1, "class" to "footer-text"), "— 没有更多了 —")
                                } else {
                                    createCommentVNode("v-if", true)
                                }
                            }
                        ))
                    } else {
                        createCommentVNode("v-if", true)
                    }
                    ,
                    if (isTrue(!_ctx.isLoading && _ctx.transactions.length == 0)) {
                        createElementVNode("view", utsMapOf("key" to 3, "class" to "empty-box"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "empty-icon"), "📝"),
                            createElementVNode("text", utsMapOf("class" to "empty-text"), "暂无交易记录")
                        ))
                    } else {
                        createCommentVNode("v-if", true)
                    }
                ))
            ), 40, utsArrayOf(
                "onScrolltolower"
            )),
            if (isTrue(_ctx.showWithdrawModal)) {
                createElementVNode("view", utsMapOf("key" to 0, "class" to "modal-mask", "onClick" to _ctx.closeWithdrawModal), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "modal-content", "onClick" to withModifiers(fun(){}, utsArrayOf(
                        "stop"
                    ))), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "modal-title"), "提现"),
                        createElementVNode("view", utsMapOf("class" to "withdraw-info"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "withdraw-label"), "可提现金额"),
                            createElementVNode("text", utsMapOf("class" to "withdraw-available"), "¥" + toDisplayString(_ctx.formatMoney(_ctx.accountInfo.availableBalance)), 1)
                        )),
                        createElementVNode("view", utsMapOf("class" to "withdraw-input-box"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "input-prefix"), "¥"),
                            createElementVNode("input", utsMapOf("class" to "withdraw-input", "type" to "digit", "placeholder" to "请输入提现金额", "modelValue" to _ctx.withdrawAmount, "onInput" to utsArrayOf(
                                fun(`$event`: InputEvent){
                                    _ctx.withdrawAmount = `$event`.detail.value
                                },
                                _ctx.onWithdrawInput
                            )), null, 40, utsArrayOf(
                                "modelValue",
                                "onInput"
                            )),
                            createElementVNode("view", utsMapOf("class" to "withdraw-all-btn", "onClick" to _ctx.withdrawAll), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "withdraw-all-text"), "全部提现")
                            ), 8, utsArrayOf(
                                "onClick"
                            ))
                        )),
                        if (_ctx.withdrawError != "") {
                            createElementVNode("text", utsMapOf("key" to 0, "class" to "withdraw-tip"), toDisplayString(_ctx.withdrawError), 1)
                        } else {
                            createCommentVNode("v-if", true)
                        },
                        createElementVNode("text", utsMapOf("class" to "withdraw-hint"), "提现将在1-3个工作日内到账"),
                        createElementVNode("view", utsMapOf("class" to "modal-actions"), utsArrayOf(
                            createElementVNode("view", utsMapOf("class" to "modal-btn modal-btn-cancel", "onClick" to _ctx.closeWithdrawModal), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "btn-text"), "取消")
                            ), 8, utsArrayOf(
                                "onClick"
                            )),
                            createElementVNode("view", utsMapOf("class" to "modal-btn modal-btn-confirm", "onClick" to _ctx.confirmWithdraw), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "btn-text-white"), "确认提现")
                            ), 8, utsArrayOf(
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
    open var isLoading: Boolean by `$data`
    open var showBalance: Boolean by `$data`
    open var currentFilter: String by `$data`
    open var accountInfo: AccountInfoType by `$data`
    open var transactions: UTSArray<TransactionItemType> by `$data`
    open var pageNum: Number by `$data`
    open var pageSize: Number by `$data`
    open var hasMore: Boolean by `$data`
    open var showWithdrawModal: Boolean by `$data`
    open var withdrawAmount: String by `$data`
    open var withdrawError: String by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return utsMapOf("isLoading" to false, "showBalance" to true, "currentFilter" to "all", "accountInfo" to AccountInfoType(availableBalance = 0, frozenAmount = 0, totalIncome = 0, totalWithdraw = 0), "transactions" to utsArrayOf<TransactionItemType>(), "pageNum" to 1, "pageSize" to 10, "hasMore" to true, "showWithdrawModal" to false, "withdrawAmount" to "", "withdrawError" to "")
    }
    open var loadAccountInfo = ::gen_loadAccountInfo_fn
    open fun gen_loadAccountInfo_fn() {
        getSellerAccount().then(fun(res){
            val data = res.data as UTSJSONObject
            this.accountInfo = AccountInfoType(availableBalance = data.getNumber("availableBalance") ?: data.getNumber("balance") ?: 0, frozenAmount = data.getNumber("frozenAmount") ?: 0, totalIncome = data.getNumber("totalIncome") ?: 0, totalWithdraw = data.getNumber("totalWithdraw") ?: 0)
        }
        ).`catch`(fun(err){
            console.error("获取账户信息失败:", err, " at pages/seller/account.uvue:205")
            uni_showToast(ShowToastOptions(title = "获取账户信息失败", icon = "none"))
        }
        )
    }
    open var loadTransactions = ::gen_loadTransactions_fn
    open fun gen_loadTransactions_fn() {
        if (this.isLoading) {
            return
        }
        this.isLoading = true
        var typeParam: String? = null
        if (this.currentFilter == "income") {
            typeParam = "INCOME"
        } else if (this.currentFilter == "withdraw") {
            typeParam = "WITHDRAW"
        }
        val params = SellerTransactionParams(type = typeParam, startDate = null, endDate = null, pageNum = this.pageNum, pageSize = this.pageSize)
        getSellerTransactions(params).then(fun(res){
            val data = res.data as UTSJSONObject
            val recordsArr = data.getArray("records")
            val total = (data.getNumber("total") ?: 0).toInt()
            if (recordsArr != null) {
                val newRecords: UTSArray<TransactionItemType> = utsArrayOf()
                run {
                    var i: Number = 0
                    while(i < recordsArr.length){
                        val item = recordsArr[i] as UTSJSONObject
                        newRecords.push(TransactionItemType(id = (item.getNumber("id") ?: 0).toInt(), transactionNo = item.getString("transactionNo") ?: "", type = item.getString("type") ?: "", typeDesc = item.getString("typeDesc") ?: this.getTypeDesc(item.getString("type") ?: ""), amount = item.getNumber("amount") ?: 0, balanceAfter = item.getNumber("balanceAfter") ?: 0, relatedOrderNo = item.getString("relatedOrderNo") ?: "", description = item.getString("description") ?: "", createTime = item.getString("createTime") ?: ""))
                        i++
                    }
                }
                if (this.pageNum == 1) {
                    this.transactions = newRecords
                } else {
                    this.transactions = this.transactions.concat(newRecords)
                }
                this.hasMore = this.transactions.length < total
            }
            this.isLoading = false
        }
        ).`catch`(fun(err){
            console.error("获取交易记录失败:", err, " at pages/seller/account.uvue:261")
            this.isLoading = false
        }
        )
    }
    open var getTypeDesc = ::gen_getTypeDesc_fn
    open fun gen_getTypeDesc_fn(type: String): String {
        if (type == "INCOME" || type == "ORDER_INCOME") {
            return "订单收入"
        }
        if (type == "WITHDRAW") {
            return "提现"
        }
        if (type == "REFUND") {
            return "退款"
        }
        if (type == "SETTLE") {
            return "结算"
        }
        return type
    }
    open var loadMore = ::gen_loadMore_fn
    open fun gen_loadMore_fn() {
        if (!this.hasMore || this.isLoading) {
            return
        }
        this.pageNum++
        this.loadTransactions()
    }
    open var changeFilter = ::gen_changeFilter_fn
    open fun gen_changeFilter_fn(filter: String) {
        if (this.currentFilter == filter) {
            return
        }
        this.currentFilter = filter
        this.pageNum = 1
        this.transactions = utsArrayOf<TransactionItemType>()
        this.hasMore = true
        this.loadTransactions()
    }
    open var formatMoney = ::gen_formatMoney_fn
    open fun gen_formatMoney_fn(num: Number): String {
        return Math.abs(num).toFixed(2)
    }
    open var formatTime = ::gen_formatTime_fn
    open fun gen_formatTime_fn(timeStr: String): String {
        if (timeStr.length >= 16) {
            return timeStr.substring(5, 16)
        }
        return timeStr
    }
    open var goBack = ::gen_goBack_fn
    open fun gen_goBack_fn() {
        uni_navigateBack(null)
    }
    open var toggleEye = ::gen_toggleEye_fn
    open fun gen_toggleEye_fn() {
        this.showBalance = !this.showBalance
    }
    open var handleWithdraw = ::gen_handleWithdraw_fn
    open fun gen_handleWithdraw_fn() {
        this.showWithdrawModal = true
        this.withdrawAmount = ""
        this.withdrawError = ""
    }
    open var closeWithdrawModal = ::gen_closeWithdrawModal_fn
    open fun gen_closeWithdrawModal_fn() {
        this.showWithdrawModal = false
        this.withdrawAmount = ""
        this.withdrawError = ""
    }
    open var onWithdrawInput = ::gen_onWithdrawInput_fn
    open fun gen_onWithdrawInput_fn(_e: InputEvent) {
        this.withdrawError = ""
        val amount = parseFloat(this.withdrawAmount)
        if (!isNaN(amount) && amount > this.accountInfo.availableBalance) {
            this.withdrawError = "提现金额不能超过可用余额"
        }
    }
    open var withdrawAll = ::gen_withdrawAll_fn
    open fun gen_withdrawAll_fn() {
        this.withdrawAmount = this.accountInfo.availableBalance.toFixed(2)
        this.withdrawError = ""
    }
    open var confirmWithdraw = ::gen_confirmWithdraw_fn
    open fun gen_confirmWithdraw_fn() {
        if (this.withdrawAmount == "") {
            this.withdrawError = "请输入提现金额"
            return
        }
        val amount = parseFloat(this.withdrawAmount)
        if (isNaN(amount) || amount <= 0) {
            this.withdrawError = "请输入有效的提现金额"
            return
        }
        if (amount > this.accountInfo.availableBalance) {
            this.withdrawError = "提现金额不能超过可用余额"
            return
        }
        uni_showLoading(ShowLoadingOptions(title = "提现中..."))
        withdrawBalance(amount).then(fun(_res){
            uni_hideLoading()
            uni_showToast(ShowToastOptions(title = "提现申请已提交", icon = "success"))
            this.closeWithdrawModal()
            this.loadAccountInfo()
            this.pageNum = 1
            this.transactions = utsArrayOf<TransactionItemType>()
            this.loadTransactions()
        }
        ).`catch`(fun(err){
            uni_hideLoading()
            console.error("提现失败:", err, " at pages/seller/account.uvue:374")
            val errMsg = (err as UTSError).message
            if (errMsg.includes("余额不足") || errMsg.includes("2002")) {
                this.withdrawError = "可用余额不足"
            } else {
                uni_showToast(ShowToastOptions(title = if (errMsg != "") {
                    errMsg
                } else {
                    "提现失败"
                }
                , icon = "none"))
            }
        }
        )
    }
    open var getIcon = ::gen_getIcon_fn
    open fun gen_getIcon_fn(type: String): String {
        if (type == "INCOME" || type == "ORDER_INCOME") {
            return "💰"
        }
        if (type == "WITHDRAW") {
            return "💳"
        }
        if (type == "REFUND") {
            return "↩️"
        }
        if (type == "SETTLE") {
            return "✅"
        }
        return "📝"
    }
    open var getIconClass = ::gen_getIconClass_fn
    open fun gen_getIconClass_fn(type: String): String {
        if (type == "INCOME" || type == "ORDER_INCOME") {
            return "item-icon-income"
        }
        if (type == "WITHDRAW") {
            return "item-icon-withdraw"
        }
        if (type == "REFUND") {
            return "item-icon-refund"
        }
        return ""
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
                return utsMapOf("page" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "column", "backgroundColor" to "#F5F7FA", "height" to "100%")), "header-bg" to padStyleMapOf(utsMapOf("backgroundColor" to "#0066CC", "paddingBottom" to "80rpx")), "status-bar" to padStyleMapOf(utsMapOf("height" to CSS_VAR_STATUS_BAR_HEIGHT)), "nav-bar" to padStyleMapOf(utsMapOf("height" to "88rpx", "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "paddingTop" to 0, "paddingRight" to "24rpx", "paddingBottom" to 0, "paddingLeft" to "24rpx")), "nav-back" to padStyleMapOf(utsMapOf("width" to "80rpx", "height" to "80rpx", "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "back-arrow" to padStyleMapOf(utsMapOf("fontSize" to "56rpx", "color" to "#FFFFFF", "fontWeight" to "400")), "nav-title" to padStyleMapOf(utsMapOf("fontSize" to "34rpx", "fontWeight" to "700", "color" to "#FFFFFF")), "nav-placeholder" to padStyleMapOf(utsMapOf("width" to "80rpx")), "balance-box" to padStyleMapOf(utsMapOf("paddingTop" to "32rpx", "paddingRight" to "32rpx", "paddingBottom" to "32rpx", "paddingLeft" to "32rpx")), "balance-header" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "marginBottom" to "16rpx")), "balance-label" to padStyleMapOf(utsMapOf("fontSize" to "26rpx", "color" to "rgba(255,255,255,0.8)")), "eye-btn" to padStyleMapOf(utsMapOf("paddingTop" to "8rpx", "paddingRight" to "16rpx", "paddingBottom" to "8rpx", "paddingLeft" to "16rpx")), "eye-text" to padStyleMapOf(utsMapOf("fontSize" to "24rpx", "color" to "rgba(255,255,255,0.8)")), "balance-amount" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "flex-end", "marginBottom" to "40rpx")), "amount-symbol" to padStyleMapOf(utsMapOf("fontSize" to "36rpx", "color" to "#FFFFFF", "fontWeight" to "700", "marginRight" to "8rpx", "marginBottom" to "12rpx")), "amount-num" to padStyleMapOf(utsMapOf("fontSize" to "72rpx", "color" to "#FFFFFF", "fontWeight" to "700")), "balance-actions" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row")), "action-btn-primary" to padStyleMapOf(utsMapOf("flex" to 1, "height" to "80rpx", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "40rpx", "borderTopRightRadius" to "40rpx", "borderBottomRightRadius" to "40rpx", "borderBottomLeftRadius" to "40rpx", "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "action-btn-primary-text" to padStyleMapOf(utsMapOf("fontSize" to "28rpx", "color" to "#0066CC", "fontWeight" to "700")), "main-scroll" to padStyleMapOf(utsMapOf("flex" to 1, "marginTop" to "-48rpx")), "stats-card" to padStyleMapOf(utsMapOf("marginTop" to 0, "marginRight" to "32rpx", "marginBottom" to "24rpx", "marginLeft" to "32rpx", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "24rpx", "borderTopRightRadius" to "24rpx", "borderBottomRightRadius" to "24rpx", "borderBottomLeftRadius" to "24rpx", "paddingTop" to "32rpx", "paddingRight" to "32rpx", "paddingBottom" to "32rpx", "paddingLeft" to "32rpx", "display" to "flex", "flexDirection" to "row", "justifyContent" to "space-around")), "stats-item" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "column", "alignItems" to "center")), "stats-num" to padStyleMapOf(utsMapOf("fontSize" to "36rpx", "fontWeight" to "700", "color" to "#1A1A1A", "marginBottom" to "8rpx")), "stats-num-green" to padStyleMapOf(utsMapOf("color" to "#52C41A")), "stats-num-orange" to padStyleMapOf(utsMapOf("color" to "#FF7A45")), "stats-name" to padStyleMapOf(utsMapOf("fontSize" to "24rpx", "color" to "#999999")), "stats-divider" to padStyleMapOf(utsMapOf("width" to "2rpx", "height" to "60rpx", "backgroundColor" to "#F0F0F0")), "record-card" to padStyleMapOf(utsMapOf("marginTop" to 0, "marginRight" to "32rpx", "marginBottom" to "32rpx", "marginLeft" to "32rpx", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "24rpx", "borderTopRightRadius" to "24rpx", "borderBottomRightRadius" to "24rpx", "borderBottomLeftRadius" to "24rpx", "paddingTop" to "32rpx", "paddingRight" to "32rpx", "paddingBottom" to "32rpx", "paddingLeft" to "32rpx")), "record-header" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "marginBottom" to "24rpx")), "record-title" to padStyleMapOf(utsMapOf("fontSize" to "32rpx", "fontWeight" to "700", "color" to "#1A1A1A")), "filter-tabs" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "backgroundColor" to "#F5F7FA", "borderTopLeftRadius" to "8rpx", "borderTopRightRadius" to "8rpx", "borderBottomRightRadius" to "8rpx", "borderBottomLeftRadius" to "8rpx", "paddingTop" to "4rpx", "paddingRight" to "4rpx", "paddingBottom" to "4rpx", "paddingLeft" to "4rpx")), "filter-tab" to padStyleMapOf(utsMapOf("fontSize" to "24rpx", "color" to "#666666", "paddingTop" to "8rpx", "paddingRight" to "20rpx", "paddingBottom" to "8rpx", "paddingLeft" to "20rpx", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx")), "filter-tab-active" to padStyleMapOf(utsMapOf("backgroundColor" to "#0066CC", "color" to "#FFFFFF")), "loading-box" to padStyleMapOf(utsMapOf("paddingTop" to "60rpx", "paddingRight" to 0, "paddingBottom" to "60rpx", "paddingLeft" to 0, "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "loading-text" to padStyleMapOf(utsMapOf("fontSize" to "28rpx", "color" to "#999999")), "record-list" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "column")), "record-item" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "paddingTop" to "24rpx", "paddingRight" to 0, "paddingBottom" to "24rpx", "paddingLeft" to 0, "borderBottomWidth" to "1rpx", "borderBottomStyle" to "solid", "borderBottomColor" to "#F5F5F5")), "item-icon" to padStyleMapOf(utsMapOf("width" to "80rpx", "height" to "80rpx", "borderTopLeftRadius" to "20rpx", "borderTopRightRadius" to "20rpx", "borderBottomRightRadius" to "20rpx", "borderBottomLeftRadius" to "20rpx", "display" to "flex", "alignItems" to "center", "justifyContent" to "center", "marginRight" to "24rpx", "backgroundColor" to "#F5F7FA")), "item-icon-income" to padStyleMapOf(utsMapOf("backgroundColor" to "#E6F7E6")), "item-icon-withdraw" to padStyleMapOf(utsMapOf("backgroundColor" to "#FFF2E8")), "item-icon-refund" to padStyleMapOf(utsMapOf("backgroundColor" to "#E8F4FF")), "icon-text" to padStyleMapOf(utsMapOf("fontSize" to "36rpx")), "item-info" to padStyleMapOf(utsMapOf("flex" to 1, "display" to "flex", "flexDirection" to "column")), "item-title" to padStyleMapOf(utsMapOf("fontSize" to "28rpx", "color" to "#1A1A1A", "marginBottom" to "4rpx")), "item-desc" to padStyleMapOf(utsMapOf("fontSize" to "24rpx", "color" to "#666666", "marginBottom" to "4rpx")), "item-time" to padStyleMapOf(utsMapOf("fontSize" to "24rpx", "color" to "#999999")), "item-amount" to padStyleMapOf(utsMapOf("fontSize" to "32rpx", "fontWeight" to "700", "color" to "#1A1A1A")), "item-amount-income" to padStyleMapOf(utsMapOf("color" to "#52C41A")), "list-footer" to padStyleMapOf(utsMapOf("paddingTop" to "32rpx", "paddingRight" to 0, "paddingBottom" to "32rpx", "paddingLeft" to 0, "display" to "flex", "justifyContent" to "center")), "footer-text" to padStyleMapOf(utsMapOf("fontSize" to "24rpx", "color" to "#CCCCCC")), "empty-box" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "column", "alignItems" to "center", "paddingTop" to "80rpx", "paddingRight" to 0, "paddingBottom" to "80rpx", "paddingLeft" to 0)), "empty-icon" to padStyleMapOf(utsMapOf("fontSize" to "80rpx", "marginBottom" to "16rpx")), "empty-text" to padStyleMapOf(utsMapOf("fontSize" to "28rpx", "color" to "#999999")), "modal-mask" to padStyleMapOf(utsMapOf("position" to "fixed", "top" to 0, "left" to 0, "right" to 0, "bottom" to 0, "backgroundColor" to "rgba(0,0,0,0.5)", "display" to "flex", "alignItems" to "center", "justifyContent" to "center", "zIndex" to 999)), "modal-content" to padStyleMapOf(utsMapOf("width" to "600rpx", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "24rpx", "borderTopRightRadius" to "24rpx", "borderBottomRightRadius" to "24rpx", "borderBottomLeftRadius" to "24rpx", "paddingTop" to "40rpx", "paddingRight" to "40rpx", "paddingBottom" to "40rpx", "paddingLeft" to "40rpx")), "modal-title" to padStyleMapOf(utsMapOf("fontSize" to "36rpx", "fontWeight" to "700", "color" to "#1A1A1A", "textAlign" to "center", "marginBottom" to "32rpx")), "withdraw-info" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "marginBottom" to "24rpx", "paddingTop" to "16rpx", "paddingRight" to 0, "paddingBottom" to "16rpx", "paddingLeft" to 0, "borderBottomWidth" to "1rpx", "borderBottomStyle" to "solid", "borderBottomColor" to "#F5F5F5")), "withdraw-label" to padStyleMapOf(utsMapOf("fontSize" to "28rpx", "color" to "#666666")), "withdraw-available" to padStyleMapOf(utsMapOf("fontSize" to "32rpx", "fontWeight" to "700", "color" to "#52C41A")), "withdraw-input-box" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "backgroundColor" to "#F5F7FA", "borderTopLeftRadius" to "12rpx", "borderTopRightRadius" to "12rpx", "borderBottomRightRadius" to "12rpx", "borderBottomLeftRadius" to "12rpx", "paddingTop" to 0, "paddingRight" to "24rpx", "paddingBottom" to 0, "paddingLeft" to "24rpx", "marginBottom" to "16rpx")), "input-prefix" to padStyleMapOf(utsMapOf("fontSize" to "36rpx", "fontWeight" to "700", "color" to "#1A1A1A", "marginRight" to "8rpx")), "withdraw-input" to padStyleMapOf(utsMapOf("flex" to 1, "height" to "88rpx", "fontSize" to "32rpx", "color" to "#1A1A1A")), "withdraw-all-btn" to padStyleMapOf(utsMapOf("paddingTop" to "12rpx", "paddingRight" to "20rpx", "paddingBottom" to "12rpx", "paddingLeft" to "20rpx", "backgroundColor" to "#E6F0FF", "borderTopLeftRadius" to "8rpx", "borderTopRightRadius" to "8rpx", "borderBottomRightRadius" to "8rpx", "borderBottomLeftRadius" to "8rpx")), "withdraw-all-text" to padStyleMapOf(utsMapOf("fontSize" to "24rpx", "color" to "#0066CC")), "withdraw-tip" to padStyleMapOf(utsMapOf("fontSize" to "24rpx", "color" to "#FF4D4F", "marginBottom" to "8rpx")), "withdraw-hint" to padStyleMapOf(utsMapOf("fontSize" to "24rpx", "color" to "#999999", "marginBottom" to "32rpx")), "modal-actions" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between")), "modal-btn" to padStyleMapOf(utsMapOf("flex" to 1, "height" to "88rpx", "borderTopLeftRadius" to "44rpx", "borderTopRightRadius" to "44rpx", "borderBottomRightRadius" to "44rpx", "borderBottomLeftRadius" to "44rpx", "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "modal-btn-cancel" to padStyleMapOf(utsMapOf("backgroundColor" to "#F5F7FA", "marginRight" to "24rpx")), "modal-btn-confirm" to padStyleMapOf(utsMapOf("backgroundColor" to "#0066CC")), "btn-text" to padStyleMapOf(utsMapOf("fontSize" to "30rpx", "color" to "#666666")), "btn-text-white" to padStyleMapOf(utsMapOf("fontSize" to "30rpx", "color" to "#FFFFFF", "fontWeight" to "700")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = utsMapOf()
        var emits: Map<String, Any?> = utsMapOf()
        var props = normalizePropsOptions(utsMapOf())
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf()
        var components: Map<String, CreateVueComponent> = utsMapOf()
    }
}
