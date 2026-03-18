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
import io.dcloud.uniapp.extapi.clearStorageSync as uni_clearStorageSync
import io.dcloud.uniapp.extapi.hideLoading as uni_hideLoading
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.reLaunch as uni_reLaunch
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showModal as uni_showModal
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesSellerAdminIndex : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return createElementVNode("view", utsMapOf("class" to "page"), utsArrayOf(
            createElementVNode("view", utsMapOf("class" to "header"), utsArrayOf(
                createElementVNode("text", utsMapOf("class" to "title"), "管理工作台"),
                createElementVNode("text", utsMapOf("class" to "subtitle"), "欢迎回来，管理员")
            )),
            createElementVNode("view", utsMapOf("class" to "grid-container"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "grid-item", "onClick" to fun(){
                    _ctx.navigateTo("/pages/seller/product-audit")
                }
                ), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "icon-box icon-box-audit"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "icon"), "📋")
                    )),
                    createElementVNode("text", utsMapOf("class" to "item-name"), "商品审核"),
                    createElementVNode("text", utsMapOf("class" to "item-desc"), "处理商家申请")
                ), 8, utsArrayOf(
                    "onClick"
                )),
                createElementVNode("view", utsMapOf("class" to "grid-item", "onClick" to fun(){
                    _ctx.navigateTo("/pages/seller/admin-users")
                }
                ), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "icon-box icon-box-users"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "icon"), "👥")
                    )),
                    createElementVNode("text", utsMapOf("class" to "item-name"), "用户管理"),
                    createElementVNode("text", utsMapOf("class" to "item-desc"), "管理平台用户")
                ), 8, utsArrayOf(
                    "onClick"
                )),
                createElementVNode("view", utsMapOf("class" to "grid-item", "onClick" to fun(){
                    _ctx.navigateTo("/pages/seller/admin-statistics")
                }
                ), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "icon-box icon-box-stats"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "icon"), "📊")
                    )),
                    createElementVNode("text", utsMapOf("class" to "item-name"), "数据统计"),
                    createElementVNode("text", utsMapOf("class" to "item-desc"), "查看运营报表")
                ), 8, utsArrayOf(
                    "onClick"
                )),
                createElementVNode("view", utsMapOf("class" to "grid-item", "onClick" to fun(){
                    _ctx.navigateTo("/pages/admin/reviews")
                }
                ), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "icon-box icon-box-reviews"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "icon"), "💬")
                    )),
                    createElementVNode("text", utsMapOf("class" to "item-name"), "评价管理"),
                    createElementVNode("text", utsMapOf("class" to "item-desc"), "全平台评价管理")
                ), 8, utsArrayOf(
                    "onClick"
                )),
                createElementVNode("view", utsMapOf("class" to "grid-item grid-item-full", "onClick" to _ctx.handleSyncVectors), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "icon-box icon-box-sync"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "icon"), "🧠")
                    )),
                    createElementVNode("text", utsMapOf("class" to "item-name"), "同步商品向量"),
                    createElementVNode("text", utsMapOf("class" to "item-desc"), "手动触发向量数据库同步")
                ), 8, utsArrayOf(
                    "onClick"
                ))
            )),
            createElementVNode("view", utsMapOf("class" to "logout-section"), utsArrayOf(
                createElementVNode("button", utsMapOf("class" to "logout-btn", "onClick" to _ctx.handleLogout), "退出登录", 8, utsArrayOf(
                    "onClick"
                ))
            ))
        ))
    }
    open var navigateTo = ::gen_navigateTo_fn
    open fun gen_navigateTo_fn(url: String) {
        uni_navigateTo(NavigateToOptions(url = url, fail = fun(err){
            console.error("跳转失败:", err, " at pages/seller/admin-index.uvue:65")
        }
        ))
    }
    open var handleSyncVectors = ::gen_handleSyncVectors_fn
    open fun gen_handleSyncVectors_fn() {
        uni_showModal(ShowModalOptions(title = "同步商品向量", content = "确认要立即同步商品向量到向量数据库吗？", success = fun(res){
            if (!res.confirm) {
                return
            }
            uni_showLoading(ShowLoadingOptions(title = "同步中..."))
            syncProductVectors().then(fun(res){
                val rawData = res.data as Any
                var count: Number? = null
                if (UTSAndroid.`typeof`(rawData) == "number") {
                    count = rawData as Number
                } else if (rawData != null && UTSAndroid.`typeof`(rawData) == "object") {
                    val dataObj = rawData as UTSJSONObject
                    count = dataObj.getNumber("data") ?: dataObj.getNumber("count")
                }
                val countText = if (count != null) {
                    "\uFF0C\u5171\u540C\u6B65 " + count + " \u6761"
                } else {
                    ""
                }
                uni_showToast(ShowToastOptions(title = "\u540C\u6B65\u5B8C\u6210" + countText, icon = "success"))
            }
            ).`catch`(fun(err){
                console.error("同步商品向量失败:", err, " at pages/seller/admin-index.uvue:90")
                uni_showToast(ShowToastOptions(title = "同步失败，请稍后重试", icon = "none"))
            }
            ).`finally`(fun(){
                uni_hideLoading()
            }
            )
        }
        ))
    }
    open var handleLogout = ::gen_handleLogout_fn
    open fun gen_handleLogout_fn() {
        uni_showModal(ShowModalOptions(title = "提示", content = "确定要退出登录吗？", success = fun(res){
            if (res.confirm) {
                uni_clearStorageSync()
                uni_reLaunch(ReLaunchOptions(url = "/pages/login/login"))
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
                return utsMapOf("page" to padStyleMapOf(utsMapOf("backgroundColor" to "#f7f8fa", "flex" to 1, "paddingTop" to 20, "paddingRight" to 20, "paddingBottom" to 20, "paddingLeft" to 20)), "header" to padStyleMapOf(utsMapOf("marginTop" to 40, "marginBottom" to 30, "paddingTop" to 0, "paddingRight" to 10, "paddingBottom" to 0, "paddingLeft" to 10)), "title" to padStyleMapOf(utsMapOf("fontSize" to 24, "fontWeight" to "700", "color" to "#333333")), "subtitle" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#999999", "marginTop" to 8)), "grid-container" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "flexWrap" to "wrap", "justifyContent" to "space-between")), "grid-item" to padStyleMapOf(utsMapOf("width" to "46%", "backgroundColor" to "#ffffff", "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "paddingTop" to 20, "paddingRight" to 20, "paddingBottom" to 20, "paddingLeft" to 20, "marginBottom" to 20, "display" to "flex", "flexDirection" to "column", "alignItems" to "center", "boxShadow" to "0 4px 12px rgba(0, 0, 0, 0.05)")), "icon-box" to padStyleMapOf(utsMapOf("width" to 50, "height" to 50, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "display" to "flex", "alignItems" to "center", "justifyContent" to "center", "marginBottom" to 12)), "icon-box-audit" to padStyleMapOf(utsMapOf("backgroundColor" to "#e6f7ff")), "icon-box-users" to padStyleMapOf(utsMapOf("backgroundColor" to "#f6ffed")), "icon-box-stats" to padStyleMapOf(utsMapOf("backgroundColor" to "#fff7e6")), "icon-box-reviews" to padStyleMapOf(utsMapOf("backgroundColor" to "#fff0f6")), "icon-box-sync" to padStyleMapOf(utsMapOf("backgroundColor" to "#f0f5ff")), "grid-item-full" to padStyleMapOf(utsMapOf("width" to "100%")), "icon" to padStyleMapOf(utsMapOf("fontSize" to 24)), "item-name" to padStyleMapOf(utsMapOf("fontSize" to 16, "fontWeight" to "700", "color" to "#333333")), "item-desc" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999", "marginTop" to 4)), "logout-section" to padStyleMapOf(utsMapOf("marginTop" to 40, "paddingTop" to 0, "paddingRight" to 10, "paddingBottom" to 0, "paddingLeft" to 10)), "logout-btn" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "color" to "#ff4d4f", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#ff4d4f", "borderRightColor" to "#ff4d4f", "borderBottomColor" to "#ff4d4f", "borderLeftColor" to "#ff4d4f", "borderTopLeftRadius" to 24, "borderTopRightRadius" to 24, "borderBottomRightRadius" to 24, "borderBottomLeftRadius" to 24, "fontSize" to 16)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = utsMapOf()
        var emits: Map<String, Any?> = utsMapOf()
        var props = normalizePropsOptions(utsMapOf())
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf()
        var components: Map<String, CreateVueComponent> = utsMapOf()
    }
}
