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
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.reLaunch as uni_reLaunch
open class GenComponentsBottomNav : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {
        onMounted(fun() {
            this.updateCurrentNav()
            this.loadBadgeCounts()
            uni__on("bottomNavRefresh", fun(){
                this.updateCurrentNav()
                this.loadBadgeCounts()
                this.refreshKey++
            }
            )
        }
        , __ins)
        onUnmounted(fun() {
            uni__off("bottomNavRefresh", null)
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return createElementVNode("view", utsMapOf("class" to "bottom-nav"), utsArrayOf(
            createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.navList, fun(item, index, __index, _cached): Any {
                return createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                    "nav-item",
                    utsMapOf("nav-item--active" to (_ctx.currentNav === item.path))
                )), "key" to index, "onClick" to fun(){
                    _ctx.switchNav(item)
                }
                ), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "nav-icon"), toDisplayString(if (_ctx.currentNav === item.path) {
                        item.activeIcon
                    } else {
                        item.icon
                    }
                    ), 1),
                    createElementVNode("text", utsMapOf("class" to "nav-text"), toDisplayString(item.name), 1),
                    if (isTrue(item.badge && item.badgeCount > 0)) {
                        createElementVNode("view", utsMapOf("key" to 0, "class" to "nav-badge"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "badge-text"), toDisplayString(if (item.badgeCount > 99) {
                                "99+"
                            } else {
                                item.badgeCount
                            }), 1)
                        ))
                    } else {
                        createCommentVNode("v-if", true)
                    }
                ), 10, utsArrayOf(
                    "onClick"
                ))
            }
            ), 128)
        ))
    }
    open var currentNav: String by `$data`
    open var refreshKey: Number by `$data`
    open var navList: UTSArray<NavItemType> by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return utsMapOf("currentNav" to "/pages/index/index", "refreshKey" to 0, "navList" to utsArrayOf<NavItemType>(NavItemType(name = "首页", path = "/pages/index/index", icon = "🏠", activeIcon = "🏡", badge = false, badgeCount = 0), NavItemType(name = "分类", path = "/pages/product/list", icon = "📂", activeIcon = "📁", badge = false, badgeCount = 0), NavItemType(name = "购物车", path = "/pages/cart/index", icon = "🛒", activeIcon = "🛍️", badge = true, badgeCount = 0), NavItemType(name = "订单", path = "/pages/order/list", icon = "📋", activeIcon = "📄", badge = true, badgeCount = 0), NavItemType(name = "我的", path = "/pages/user/profile", icon = "👤", activeIcon = "👨", badge = false, badgeCount = 0)))
    }
    open var loadBadgeCounts = ::gen_loadBadgeCounts_fn
    open fun gen_loadBadgeCounts_fn() {
        getCartCount().then(fun(res){
            val count = (res.data as Number?) ?: 0
            this.navList[2].badgeCount = count as Number
        }
        ).`catch`(fun(err){
            console.error("获取购物车数量失败:", err, " at components/BottomNav.uvue:105")
        }
        )
        getOrderCount().then(fun(res){
            val data = res.data as UTSJSONObject
            val unpaid = (data.getNumber("unpaid") ?: 0).toInt()
            val shipped = (data.getNumber("shipped") ?: 0).toInt()
            this.navList[3].badgeCount = unpaid + shipped
        }
        ).`catch`(fun(err){
            console.error("获取订单数量失败:", err, " at components/BottomNav.uvue:115")
        }
        )
    }
    open var updateCurrentNav = ::gen_updateCurrentNav_fn
    open fun gen_updateCurrentNav_fn() {
        val pages = getCurrentPages()
        if (pages.length > 0) {
            val currentPage = pages[pages.length - 1]
            this.currentNav = "/" + currentPage.route
        }
    }
    open var switchNav = ::gen_switchNav_fn
    open fun gen_switchNav_fn(item: NavItemType) {
        if (item.path === this.currentNav) {
            return
        }
        this.currentNav = item.path
        uni_reLaunch(ReLaunchOptions(url = item.path, fail = fun(err){
            console.error("切换页面失败:", err, " at components/BottomNav.uvue:138")
            uni_navigateTo(NavigateToOptions(url = item.path, fail = fun(err2){
                console.error("navigateTo 也失败:", err2, " at components/BottomNav.uvue:142")
            }
            ))
        }
        ))
    }
    companion object {
        var name = "BottomNav"
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            normalizeCssStyles(utsArrayOf(
                styles0
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return utsMapOf("bottom-nav" to padStyleMapOf(utsMapOf("position" to "fixed", "bottom" to 0, "left" to 0, "right" to 0, "display" to "flex", "flexDirection" to "row", "height" to "120rpx", "backgroundColor" to "#FFFFFF", "borderTopWidth" to "1rpx", "borderTopStyle" to "solid", "borderTopColor" to "#E5E5E5", "zIndex" to 9999, "paddingBottom" to "20rpx")), "nav-item" to padStyleMapOf(utsMapOf("flex" to 1, "display" to "flex", "flexDirection" to "column", "alignItems" to "center", "justifyContent" to "center", "position" to "relative", "paddingTop" to "8rpx", "paddingBottom" to "8rpx", "backgroundColor" to "#FFFFFF")), "nav-icon" to padStyleMapOf(utsMapOf("fontSize" to "44rpx", "marginBottom" to "4rpx")), "nav-text" to utsMapOf("" to utsMapOf("fontSize" to "22rpx", "color" to "#999999"), ".nav-item--active " to utsMapOf("color" to "#0066CC", "fontWeight" to "700")), "nav-badge" to padStyleMapOf(utsMapOf("position" to "absolute", "top" to "4rpx", "right" to "20rpx", "minWidth" to "32rpx", "height" to "32rpx", "backgroundColor" to "#FF4D4F", "borderTopLeftRadius" to "16rpx", "borderTopRightRadius" to "16rpx", "borderBottomRightRadius" to "16rpx", "borderBottomLeftRadius" to "16rpx", "display" to "flex", "alignItems" to "center", "justifyContent" to "center", "paddingLeft" to "8rpx", "paddingRight" to "8rpx")), "badge-text" to padStyleMapOf(utsMapOf("color" to "#FFFFFF", "fontSize" to "18rpx", "fontWeight" to "700")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = utsMapOf()
        var emits: Map<String, Any?> = utsMapOf()
        var props = normalizePropsOptions(utsMapOf())
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf()
        var components: Map<String, CreateVueComponent> = utsMapOf()
    }
}
