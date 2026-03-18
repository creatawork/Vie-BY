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
import io.dcloud.uniapp.extapi.`$off` as uni__off
import io.dcloud.uniapp.extapi.`$on` as uni__on
import io.dcloud.uniapp.extapi.chooseImage as uni_chooseImage
import io.dcloud.uniapp.extapi.getStorageSync as uni_getStorageSync
import io.dcloud.uniapp.extapi.hideLoading as uni_hideLoading
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.reLaunch as uni_reLaunch
import io.dcloud.uniapp.extapi.removeStorageSync as uni_removeStorageSync
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showActionSheet as uni_showActionSheet
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showModal as uni_showModal
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesUserProfile : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onLoad(fun(_: OnLoadOptions) {
            this.checkLoginStatus()
            uni__on("refreshFavorites", this.loadCollectionCount)
        }
        , __ins)
        onUnload(fun() {
            uni__off("refreshFavorites", this.loadCollectionCount)
        }
        , __ins)
        onPageShow(fun() {
            uni__emit("bottomNavRefresh", null)
            val userInfo = uni_getStorageSync("user_info")
            if (userInfo != null && userInfo != "") {
                try {
                    val infoStr = userInfo as String
                    val parsed = UTSAndroid.consoleDebugError(JSON.parse(infoStr), " at pages/user/profile.uvue:395") as UTSJSONObject
                    val id = parsed.getNumber("id")
                    val avatar = parsed.getString("avatar")
                    val username = parsed.getString("username")
                    val email = parsed.getString("email")
                    val phone = parsed.getString("phone")
                    val nickname = parsed.getString("nickname")
                    this.userInfo = UserInfoType(id = if (id != null) {
                        id.toInt()
                    } else {
                        this.userInfo.id
                    }, avatar = if (avatar != null) {
                        avatar
                    } else {
                        this.userInfo.avatar
                    }, username = if (username != null) {
                        username
                    } else {
                        this.userInfo.username
                    }, email = if (email != null) {
                        email
                    } else {
                        this.userInfo.email
                    }, phone = if (phone != null) {
                        phone
                    } else {
                        this.userInfo.phone
                    }, nickname = if (nickname != null) {
                        nickname
                    } else {
                        this.userInfo.nickname
                    })
                    this.checkSellerStatus()
                    this.loadOrderStats()
                } catch (e: Throwable) {
                    console.error(e, " at pages/user/profile.uvue:419")
                }
            } else {
                this.checkLoginStatus()
            }
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        val _component_BottomNav = resolveComponent("BottomNav")
        return createElementVNode("view", utsMapOf("class" to "profile-page profile-container"), utsArrayOf(
            createElementVNode("view", utsMapOf("class" to "bg-shape bg-shape-1")),
            createElementVNode("view", utsMapOf("class" to "bg-shape bg-shape-2")),
            createElementVNode("view", utsMapOf("class" to "header"), utsArrayOf(
                if (isTrue(_ctx.isSeller)) {
                    createElementVNode("view", utsMapOf("key" to 0, "class" to "switch-role-btn", "onClick" to _ctx.handleSwitchRole), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "switch-icon"), "🔄"),
                        createElementVNode("text", utsMapOf("class" to "switch-text"), toDisplayString(if (_ctx.currentRole == "seller") {
                            "切换买家"
                        } else {
                            "切换卖家"
                        }), 1)
                    ), 8, utsArrayOf(
                        "onClick"
                    ))
                } else {
                    createElementVNode("view", utsMapOf("key" to 1, "class" to "header-spacer"))
                }
                ,
                createElementVNode("view", utsMapOf("class" to "settings-btn", "onClick" to _ctx.handleSettings), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "settings-icon"), "⚙️")
                ), 8, utsArrayOf(
                    "onClick"
                ))
            )),
            createElementVNode("scroll-view", utsMapOf("class" to "main-content", "scroll-y" to "true", "show-scrollbar" to false), utsArrayOf(
                if (_ctx.currentRole == "buyer") {
                    createElementVNode("view", utsMapOf("key" to 0), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "role-status-bar buyer-mode"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "role-icon"), "🛒"),
                            createElementVNode("text", utsMapOf("class" to "role-text"), "买家模式")
                        )),
                        createElementVNode("view", utsMapOf("class" to "profile-card"), utsArrayOf(
                            createElementVNode("view", utsMapOf("class" to "profile-top"), utsArrayOf(
                                createElementVNode("view", utsMapOf("class" to "avatar-wrapper", "onClick" to _ctx.handleUploadAvatar), utsArrayOf(
                                    createElementVNode("image", utsMapOf("key" to _ctx.imageKey, "src" to _ctx.getAvatarUrl(), "class" to "avatar", "mode" to "aspectFill", "onError" to _ctx.handleImageError), null, 40, utsArrayOf(
                                        "src",
                                        "onError"
                                    )),
                                    createElementVNode("view", utsMapOf("class" to "avatar-edit-badge"), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to "edit-icon"), "📷")
                                    ))
                                ), 8, utsArrayOf(
                                    "onClick"
                                )),
                                createElementVNode("view", utsMapOf("class" to "profile-info"), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "nickname"), toDisplayString(_ctx.getNickname()), 1),
                                    createElementVNode("text", utsMapOf("class" to "username"), "用户名: " + toDisplayString(_ctx.userInfo.username), 1),
                                    createElementVNode("view", utsMapOf("class" to "user-id-tag"), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to "user-id"), "ID: " + toDisplayString(_ctx.getUserId()), 1)
                                    ))
                                ))
                            )),
                            createElementVNode("view", utsMapOf("class" to "stats-row"), utsArrayOf(
                                createElementVNode("view", utsMapOf("class" to "stat-item", "onClick" to fun(){
                                    _ctx.handleNavigate("orders")
                                }), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "stat-num"), toDisplayString(_ctx.stats.orders), 1),
                                    createElementVNode("text", utsMapOf("class" to "stat-name"), "全部订单")
                                ), 8, utsArrayOf(
                                    "onClick"
                                )),
                                createElementVNode("view", utsMapOf("class" to "stat-item", "onClick" to fun(){
                                    _ctx.handleNavigate("pending")
                                }), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "stat-num"), toDisplayString(_ctx.stats.pending), 1),
                                    createElementVNode("text", utsMapOf("class" to "stat-name"), "待付款")
                                ), 8, utsArrayOf(
                                    "onClick"
                                )),
                                createElementVNode("view", utsMapOf("class" to "stat-item", "onClick" to fun(){
                                    _ctx.handleNavigate("shipped")
                                }), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "stat-num"), toDisplayString(_ctx.stats.shipped), 1),
                                    createElementVNode("text", utsMapOf("class" to "stat-name"), "待收货")
                                ), 8, utsArrayOf(
                                    "onClick"
                                )),
                                createElementVNode("view", utsMapOf("class" to "stat-item", "onClick" to fun(){
                                    _ctx.handleNavigate("favorites")
                                }), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "stat-num"), toDisplayString(_ctx.stats.favorites), 1),
                                    createElementVNode("text", utsMapOf("class" to "stat-name"), "收藏夹")
                                ), 8, utsArrayOf(
                                    "onClick"
                                ))
                            ))
                        )),
                        createElementVNode("view", utsMapOf("class" to "section-title"), "我的服务"),
                        createElementVNode("view", utsMapOf("class" to "menu-grid"), utsArrayOf(
                            createElementVNode("view", utsMapOf("class" to "menu-item", "onClick" to fun(){
                                _ctx.handleNavigate("addresses")
                            }), utsArrayOf(
                                createElementVNode("view", utsMapOf("class" to "menu-icon-box menu-icon-box-green"), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "menu-icon"), "📍")
                                )),
                                createElementVNode("text", utsMapOf("class" to "menu-label"), "地址管理")
                            ), 8, utsArrayOf(
                                "onClick"
                            )),
                            createElementVNode("view", utsMapOf("class" to "menu-item", "onClick" to fun(){
                                _ctx.handleNavigate("coupons")
                            }), utsArrayOf(
                                createElementVNode("view", utsMapOf("class" to "menu-icon-box menu-icon-box-orange"), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "menu-icon"), "🎟️")
                                )),
                                createElementVNode("text", utsMapOf("class" to "menu-label"), "优惠券")
                            ), 8, utsArrayOf(
                                "onClick"
                            )),
                            createElementVNode("view", utsMapOf("class" to "menu-item", "onClick" to fun(){
                                _ctx.handleNavigate("history")
                            }), utsArrayOf(
                                createElementVNode("view", utsMapOf("class" to "menu-icon-box menu-icon-box-purple"), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "menu-icon"), "📚")
                                )),
                                createElementVNode("text", utsMapOf("class" to "menu-label"), "浏览历史")
                            ), 8, utsArrayOf(
                                "onClick"
                            )),
                            createElementVNode("view", utsMapOf("class" to "menu-item", "onClick" to fun(){
                                _ctx.handleNavigate("reviews")
                            }), utsArrayOf(
                                createElementVNode("view", utsMapOf("class" to "menu-icon-box menu-icon-box-cyan"), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "menu-icon"), "⭐")
                                )),
                                createElementVNode("text", utsMapOf("class" to "menu-label"), "我的评价")
                            ), 8, utsArrayOf(
                                "onClick"
                            )),
                            createElementVNode("view", utsMapOf("class" to "menu-item", "onClick" to fun(){
                                _ctx.handleNavigate("wallet")
                            }), utsArrayOf(
                                createElementVNode("view", utsMapOf("class" to "menu-icon-box menu-icon-box-red"), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "menu-icon"), "💰")
                                )),
                                createElementVNode("text", utsMapOf("class" to "menu-label"), "我的钱包")
                            ), 8, utsArrayOf(
                                "onClick"
                            )),
                            createElementVNode("view", utsMapOf("class" to "menu-item", "onClick" to _ctx.handleChangePassword), utsArrayOf(
                                createElementVNode("view", utsMapOf("class" to "menu-icon-box menu-icon-box-indigo"), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "menu-icon"), "🔒")
                                )),
                                createElementVNode("text", utsMapOf("class" to "menu-label"), "修改密码")
                            ), 8, utsArrayOf(
                                "onClick"
                            )),
                            createElementVNode("view", utsMapOf("class" to "menu-item", "onClick" to fun(){
                                _ctx.handleNavigate("service")
                            }), utsArrayOf(
                                createElementVNode("view", utsMapOf("class" to "menu-icon-box menu-icon-box-teal"), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "menu-icon"), "💬")
                                )),
                                createElementVNode("text", utsMapOf("class" to "menu-label"), "客服")
                            ), 8, utsArrayOf(
                                "onClick"
                            )),
                            if (isTrue(!_ctx.isSeller)) {
                                createElementVNode("view", utsMapOf("key" to 0, "class" to "menu-item", "onClick" to _ctx.handleBecomeSeller), utsArrayOf(
                                    createElementVNode("view", utsMapOf("class" to "menu-icon-box menu-icon-box-blue"), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to "menu-icon"), "🏪")
                                    )),
                                    createElementVNode("text", utsMapOf("class" to "menu-label"), "成为商家")
                                ), 8, utsArrayOf(
                                    "onClick"
                                ))
                            } else {
                                createCommentVNode("v-if", true)
                            }
                        )),
                        createElementVNode("view", utsMapOf("class" to "buyer-tips"), utsArrayOf(
                            createElementVNode("view", utsMapOf("class" to "tips-content"), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "tips-title"), "💡 购物小贴士"),
                                createElementVNode("text", utsMapOf("class" to "tips-desc"), "收藏喜欢的商品，及时关注优惠活动，享受更多实惠~")
                            ))
                        )),
                        createElementVNode("view", utsMapOf("class" to "logout-btn", "onClick" to _ctx.handleLogout), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "logout-icon"), "🚪"),
                            createElementVNode("text", utsMapOf("class" to "logout-text"), "退出登录")
                        ), 8, utsArrayOf(
                            "onClick"
                        ))
                    ))
                } else {
                    createCommentVNode("v-if", true)
                }
                ,
                if (_ctx.currentRole == "seller") {
                    createElementVNode("view", utsMapOf("key" to 1), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "role-status-bar seller-mode"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "role-icon"), "🏪"),
                            createElementVNode("text", utsMapOf("class" to "role-text role-text-seller"), "商家模式")
                        )),
                        createElementVNode("view", utsMapOf("class" to "seller-profile-card"), utsArrayOf(
                            createElementVNode("view", utsMapOf("class" to "seller-card-bg")),
                            createElementVNode("view", utsMapOf("class" to "seller-main-content"), utsArrayOf(
                                createElementVNode("view", utsMapOf("class" to "seller-profile-header"), utsArrayOf(
                                    createElementVNode("view", utsMapOf("class" to "seller-avatar-box"), utsArrayOf(
                                        createElementVNode("image", utsMapOf("key" to _ctx.imageKey, "src" to _ctx.getAvatarUrl(), "class" to "seller-avatar-img", "mode" to "aspectFill", "onError" to _ctx.handleImageError), null, 40, utsArrayOf(
                                            "src",
                                            "onError"
                                        )),
                                        createElementVNode("view", utsMapOf("class" to "seller-verify-badge"), utsArrayOf(
                                            createElementVNode("text", utsMapOf("class" to "verify-icon"), "✓")
                                        ))
                                    )),
                                    createElementVNode("view", utsMapOf("class" to "seller-basic-info"), utsArrayOf(
                                        createElementVNode("view", utsMapOf("class" to "shop-name-row"), utsArrayOf(
                                            createElementVNode("text", utsMapOf("class" to "shop-name-text"), toDisplayString(if (_ctx.shopName != "") {
                                                _ctx.shopName
                                            } else {
                                                "我的店铺"
                                            }), 1),
                                            createElementVNode("view", utsMapOf("class" to "shop-level-tag"), utsArrayOf(
                                                createElementVNode("text", utsMapOf("class" to "level-text"), "Lv5")
                                            ))
                                        )),
                                        createElementVNode("text", utsMapOf("class" to "seller-id-text"), "ID: " + toDisplayString(_ctx.userInfo.username), 1),
                                        createElementVNode("view", utsMapOf("class" to "seller-rating-row"), utsArrayOf(
                                            createElementVNode("text", utsMapOf("class" to "rating-stars"), "⭐⭐⭐⭐⭐"),
                                            createElementVNode("text", utsMapOf("class" to "rating-score"), "4.8"),
                                            createElementVNode("text", utsMapOf("class" to "rating-count"), "(128评价)")
                                        ))
                                    ))
                                )),
                                createElementVNode("view", utsMapOf("class" to "seller-stats-grid"), utsArrayOf(
                                    createElementVNode("view", utsMapOf("class" to "stat-card stat-card-1"), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to "stat-icon"), "📦"),
                                        createElementVNode("text", utsMapOf("class" to "stat-number"), toDisplayString(_ctx.sellerOrderStats["totalCount"] ?: 0), 1),
                                        createElementVNode("text", utsMapOf("class" to "stat-label"), "订单总数")
                                    )),
                                    createElementVNode("view", utsMapOf("class" to "stat-card stat-card-2"), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to "stat-icon"), "🚚"),
                                        createElementVNode("text", utsMapOf("class" to "stat-number"), toDisplayString(_ctx.sellerOrderStats["waitShipCount"] ?: 0), 1),
                                        createElementVNode("text", utsMapOf("class" to "stat-label"), "待发货")
                                    )),
                                    createElementVNode("view", utsMapOf("class" to "stat-card stat-card-3"), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to "stat-icon"), "✅"),
                                        createElementVNode("text", utsMapOf("class" to "stat-number"), toDisplayString(_ctx.sellerOrderStats["finishedCount"] ?: 0), 1),
                                        createElementVNode("text", utsMapOf("class" to "stat-label"), "已完成")
                                    ))
                                ))
                            ))
                        )),
                        if (isTrue(_ctx.isAdmin)) {
                            createElementVNode("view", utsMapOf("key" to 0, "class" to "admin-entry-section"), utsArrayOf(
                                createElementVNode("view", utsMapOf("class" to "admin-entry-card", "onClick" to _ctx.handleAdminNavigate), utsArrayOf(
                                    createElementVNode("view", utsMapOf("class" to "admin-entry-left"), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to "admin-entry-icon"), "👑"),
                                        createElementVNode("view", utsMapOf("class" to "admin-entry-info"), utsArrayOf(
                                            createElementVNode("text", utsMapOf("class" to "admin-entry-title"), "管理员后台"),
                                            createElementVNode("text", utsMapOf("class" to "admin-entry-desc"), "平台管理与数据监控")
                                        ))
                                    )),
                                    createElementVNode("text", utsMapOf("class" to "admin-entry-arrow"), "→")
                                ), 8, utsArrayOf(
                                    "onClick"
                                ))
                            ))
                        } else {
                            createCommentVNode("v-if", true)
                        },
                        createElementVNode("view", utsMapOf("class" to "section-title"), "商家服务"),
                        createElementVNode("view", utsMapOf("class" to "menu-grid"), utsArrayOf(
                            createElementVNode("view", utsMapOf("class" to "menu-item", "onClick" to fun(){
                                _ctx.handleSellerNavigate("products")
                            }), utsArrayOf(
                                createElementVNode("view", utsMapOf("class" to "menu-icon-box menu-icon-box-blue"), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "menu-icon"), "📦")
                                )),
                                createElementVNode("text", utsMapOf("class" to "menu-label"), "商品管理")
                            ), 8, utsArrayOf(
                                "onClick"
                            )),
                            createElementVNode("view", utsMapOf("class" to "menu-item", "onClick" to fun(){
                                _ctx.handleSellerNavigate("inventory")
                            }), utsArrayOf(
                                createElementVNode("view", utsMapOf("class" to "menu-icon-box menu-icon-box-green"), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "menu-icon"), "📊")
                                )),
                                createElementVNode("text", utsMapOf("class" to "menu-label"), "库存管理")
                            ), 8, utsArrayOf(
                                "onClick"
                            )),
                            createElementVNode("view", utsMapOf("class" to "menu-item", "onClick" to fun(){
                                _ctx.handleSellerNavigate("statistics")
                            }), utsArrayOf(
                                createElementVNode("view", utsMapOf("class" to "menu-icon-box menu-icon-box-purple"), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "menu-icon"), "📈")
                                )),
                                createElementVNode("text", utsMapOf("class" to "menu-label"), "销售统计")
                            ), 8, utsArrayOf(
                                "onClick"
                            )),
                            createElementVNode("view", utsMapOf("class" to "menu-item", "onClick" to fun(){
                                _ctx.handleSellerNavigate("account")
                            }), utsArrayOf(
                                createElementVNode("view", utsMapOf("class" to "menu-icon-box menu-icon-box-red"), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "menu-icon"), "💰")
                                )),
                                createElementVNode("text", utsMapOf("class" to "menu-label"), "商家账户")
                            ), 8, utsArrayOf(
                                "onClick"
                            )),
                            createElementVNode("view", utsMapOf("class" to "menu-item", "onClick" to fun(){
                                _ctx.handleSellerNavigate("reviews")
                            }), utsArrayOf(
                                createElementVNode("view", utsMapOf("class" to "menu-icon-box menu-icon-box-orange"), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "menu-icon"), "⭐")
                                )),
                                createElementVNode("text", utsMapOf("class" to "menu-label"), "评价管理")
                            ), 8, utsArrayOf(
                                "onClick"
                            ))
                        )),
                        createElementVNode("view", utsMapOf("class" to "seller-tips"), utsArrayOf(
                            createElementVNode("view", utsMapOf("class" to "tips-content"), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "tips-title"), "💡 经营小贴士"),
                                createElementVNode("text", utsMapOf("class" to "tips-desc"), "及时处理订单，保持良好的发货速度可以提升店铺评分哦~")
                            ))
                        )),
                        createElementVNode("view", utsMapOf("class" to "logout-btn", "onClick" to _ctx.handleLogout), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "logout-icon"), "🚪"),
                            createElementVNode("text", utsMapOf("class" to "logout-text"), "退出登录")
                        ), 8, utsArrayOf(
                            "onClick"
                        ))
                    ))
                } else {
                    createCommentVNode("v-if", true)
                }
            )),
            createVNode(_component_BottomNav)
        ))
    }
    open var userInfo: UserInfoType by `$data`
    open var stats: StatsType by `$data`
    open var defaultAvatar: String by `$data`
    open var isLoading: Boolean by `$data`
    open var imageKey: Number by `$data`
    open var isSeller: Boolean by `$data`
    open var isAdmin: Boolean by `$data`
    open var currentRole: String by `$data`
    open var shopName: String by `$data`
    open var sellerStats: SellerStatsType by `$data`
    open var sellerOrderStats: UTSJSONObject by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return utsMapOf("userInfo" to UserInfoType(id = "12345678", avatar = "", username = "testuser", email = "user@example.com", phone = "13800138000", nickname = "测试用户"), "stats" to StatsType(orders = 5, pending = 1, shipped = 2, favorites = 12), "defaultAvatar" to "/static/logo.png", "isLoading" to false, "imageKey" to 0, "isSeller" to false, "isAdmin" to false, "currentRole" to "buyer" as String, "shopName" to "" as String, "sellerStats" to SellerStatsType(todaySales = 0, pendingShip = 0, pendingSettle = 0, productCount = 0), "sellerOrderStats" to object : UTSJSONObject() {
            var waitShipCount: Number = 0
            var finishedCount: Number = 0
            var totalCount: Number = 0
        })
    }
    open var getAvatarUrl = ::gen_getAvatarUrl_fn
    open fun gen_getAvatarUrl_fn(): String {
        return if (this.userInfo.avatar != "") {
            this.userInfo.avatar
        } else {
            this.defaultAvatar
        }
    }
    open var getNickname = ::gen_getNickname_fn
    open fun gen_getNickname_fn(): String {
        return if (this.userInfo.nickname != "") {
            this.userInfo.nickname
        } else {
            "未设置昵称"
        }
    }
    open var getUserId = ::gen_getUserId_fn
    open fun gen_getUserId_fn(): String {
        val id = this.userInfo.id
        return if (id != null && id != "") {
            id.toString()
        } else {
            "12345678"
        }
    }
    open var checkSellerStatus = ::gen_checkSellerStatus_fn
    open fun gen_checkSellerStatus_fn() {
        val userInfoStr = uni_getStorageSync("user_info")
        if (userInfoStr != null && userInfoStr != "") {
            try {
                val parsed = UTSAndroid.consoleDebugError(JSON.parse(userInfoStr as String), " at pages/user/profile.uvue:442") as UTSJSONObject
                val sellerStatus = parsed.getBoolean("isSeller")
                val storedShopName = parsed.getString("shopName")
                val roleCodes = parsed.getArray("roleCodes")
                this.isSeller = sellerStatus == true
                this.shopName = if (storedShopName != null) {
                    storedShopName
                } else {
                    ""
                }
                val username = parsed.getString("username")
                if (username == "admin") {
                    this.isAdmin = true
                } else if (roleCodes != null) {
                    run {
                        var i: Number = 0
                        while(i < roleCodes.length){
                            val role = roleCodes[i] as String
                            if (role == "ADMIN" || role == "admin") {
                                this.isAdmin = true
                                break
                            }
                            i++
                        }
                    }
                }
            }
             catch (e: Throwable) {
                console.error("解析卖家状态失败:", e, " at pages/user/profile.uvue:465")
            }
        }
        val storedRole = uni_getStorageSync("current_role")
        if (storedRole != null && storedRole != "") {
            val roleStr = storedRole as String
            if (this.isSeller && roleStr == "seller") {
                this.currentRole = "seller"
                this.loadSellerStats()
            } else {
                this.currentRole = "buyer"
            }
        } else {
            this.currentRole = "buyer"
        }
        getSellerStatus().then(fun(result: ResponseDataType){
            if (result.code === 200) {
                val data = result.data as UTSJSONObject
                if (data != null) {
                    val serverIsSeller = data.getBoolean("isSeller")
                    val serverShopName = data.getString("shopName")
                    this.isSeller = serverIsSeller == true
                    this.shopName = if (serverShopName != null) {
                        serverShopName
                    } else {
                        ""
                    }
                    val userInfoStr2 = uni_getStorageSync("user_info")
                    if (userInfoStr2 != null && userInfoStr2 != "") {
                        try {
                            val info = UTSAndroid.consoleDebugError(JSON.parse(userInfoStr2 as String), " at pages/user/profile.uvue:500") as UTSJSONObject
                            val updatedInfo: UTSJSONObject = let {
                                object : UTSJSONObject(UTSSourceMapPosition("updatedInfo", "pages/user/profile.uvue", 501, 15)) {
                                    var id = info.getNumber("id")
                                    var username = info.getString("username")
                                    var nickname = info.getString("nickname")
                                    var avatar = info.getString("avatar")
                                    var email = info.getString("email")
                                    var phone = info.getString("phone")
                                    var roleCodes = info.getAny("roleCodes")
                                    var isSeller = it.isSeller
                                    var shopName = it.shopName
                                }
                            }
                            uni_setStorageSync("user_info", JSON.stringify(updatedInfo))
                        }
                         catch (e: Throwable) {
                            console.error("更新本地卖家状态失败:", e, " at pages/user/profile.uvue:514")
                        }
                    }
                    if (this.isSeller && this.currentRole == "seller") {
                        this.loadSellerStats()
                    }
                }
            }
        }
        ).`catch`(fun(error: Any?){
            console.error("获取卖家状态失败:", error, " at pages/user/profile.uvue:525")
        }
        )
    }
    open var handleSwitchRole = ::gen_handleSwitchRole_fn
    open fun gen_handleSwitchRole_fn() {
        if (!this.isSeller) {
            uni_showToast(ShowToastOptions(title = "请先开通卖家功能", icon = "none"))
            return
        }
        if (this.currentRole == "buyer") {
            this.currentRole = "seller"
            this.loadSellerStats()
        } else {
            this.currentRole = "buyer"
        }
        uni_setStorageSync("current_role", this.currentRole)
        uni__emit("bottomNavRefresh", null)
    }
    open var loadSellerStats = ::gen_loadSellerStats_fn
    open fun gen_loadSellerStats_fn() {
        getRevenueStatistics().then(fun(result){
            if (result.code === 200) {
                val data = result.data as UTSJSONObject
                if (data != null) {
                    val todaySales = data.getNumber("todaySales")
                    val pendingShip = data.getNumber("pendingShipCount")
                    val pendingSettle = data.getNumber("pendingSettleAmount")
                    val productCount = data.getNumber("productCount")
                    this.sellerStats = SellerStatsType(todaySales = if (todaySales != null) {
                        todaySales
                    } else {
                        0
                    }
                    , pendingShip = if (pendingShip != null) {
                        pendingShip.toInt()
                    } else {
                        0
                    }
                    , pendingSettle = if (pendingSettle != null) {
                        pendingSettle
                    } else {
                        0
                    }
                    , productCount = if (productCount != null) {
                        productCount.toInt()
                    } else {
                        0
                    }
                    )
                }
            }
        }
        ).`catch`(fun(error){
            console.error("获取卖家经营数据失败:", error, " at pages/user/profile.uvue:571")
        }
        )
        this.loadSellerOrderStats()
    }
    open var loadSellerOrderStats = ::gen_loadSellerOrderStats_fn
    open fun gen_loadSellerOrderStats_fn() {
        getOrderOverview().then(fun(result){
            if (result.code === 200) {
                val data = result.data as UTSJSONObject
                if (data != null) {
                    this.sellerOrderStats = data
                }
            }
        }
        ).`catch`(fun(error){
            console.error("获取商家订单统计失败:", error, " at pages/user/profile.uvue:587")
        }
        )
    }
    open var handleSellerNavigate = ::gen_handleSellerNavigate_fn
    open fun gen_handleSellerNavigate_fn(type: String) {
        val routes = Map<String, String>(utsArrayOf(
            utsArrayOf(
                "products",
                "/pages/seller/products"
            ),
            utsArrayOf(
                "inventory",
                "/pages/seller/inventory"
            ),
            utsArrayOf(
                "statistics",
                "/pages/seller/statistics"
            ),
            utsArrayOf(
                "account",
                "/pages/seller/account"
            ),
            utsArrayOf(
                "reviews",
                "/pages/seller/reviews"
            )
        ))
        val route = routes.get(type)
        if (route != null && route != "") {
            uni_navigateTo(NavigateToOptions(url = route as String))
        } else {
            uni_showToast(ShowToastOptions(title = "功能开发中", icon = "none"))
        }
    }
    open var handleAdminNavigate = ::gen_handleAdminNavigate_fn
    open fun gen_handleAdminNavigate_fn() {
        uni_navigateTo(NavigateToOptions(url = "/pages/admin/index"))
    }
    open var handleBecomeSeller = ::gen_handleBecomeSeller_fn
    open fun gen_handleBecomeSeller_fn() {
        uni_showModal(ShowModalOptions(title = "开通卖家功能", content = "确定要开通卖家功能吗？开通后您可以在平台上销售商品。", editable = true, placeholderText = "请输入店铺名称（可选）", confirmText = "确认开通", cancelText = "取消", success = fun(res){
            if (res.confirm) {
                val inputShopName = if (res.content != null && res.content != "") {
                    res.content
                } else {
                    null
                }
                uni_showLoading(ShowLoadingOptions(title = "开通中..."))
                becomeSeller(inputShopName).then(fun(result: ResponseDataType){
                    uni_hideLoading()
                    if (result.code === 200) {
                        this.isSeller = true
                        if (inputShopName != null && inputShopName != "") {
                            this.shopName = inputShopName!!
                        }
                        val userInfoStr = uni_getStorageSync("user_info")
                        if (userInfoStr != null && userInfoStr != "") {
                            try {
                                val info = UTSAndroid.consoleDebugError(JSON.parse(userInfoStr as String), " at pages/user/profile.uvue:640") as UTSJSONObject
                                val updatedInfo: UTSJSONObject = let {
                                    object : UTSJSONObject(UTSSourceMapPosition("updatedInfo", "pages/user/profile.uvue", 641, 17)) {
                                        var id = info.getNumber("id")
                                        var username = info.getString("username")
                                        var nickname = info.getString("nickname")
                                        var avatar = info.getString("avatar")
                                        var email = info.getString("email")
                                        var phone = info.getString("phone")
                                        var roleCodes = info.getAny("roleCodes")
                                        var isSeller = true
                                        var shopName = it.shopName
                                    }
                                }
                                uni_setStorageSync("user_info", JSON.stringify(updatedInfo))
                            }
                             catch (e: Throwable) {
                                console.error("更新本地卖家状态失败:", e, " at pages/user/profile.uvue:654")
                            }
                        }
                        uni_showToast(ShowToastOptions(title = "开通成功！", icon = "success", duration = 2000))
                        setTimeout(fun(){
                            this.checkSellerStatus()
                        }, 500)
                    } else {
                        val msg = if (result.message != null) {
                            result.message
                        } else {
                            "开通失败"
                        }
                        uni_showToast(ShowToastOptions(title = msg, icon = "error"))
                    }
                }
                ).`catch`(fun(error: Any?){
                    uni_hideLoading()
                    console.error("开通卖家失败:", error, " at pages/user/profile.uvue:674")
                    uni_showToast(ShowToastOptions(title = "开通失败，请稍后重试", icon = "error"))
                }
                )
            }
        }
        ))
    }
    open var checkLoginStatus = ::gen_checkLoginStatus_fn
    open fun gen_checkLoginStatus_fn() {
        val token = uni_getStorageSync("auth_token")
        if (token == null || token == "") {
            uni_showModal(ShowModalOptions(title = "提示", content = "请先登录后再访问个人中心", showCancel = false, success = fun(_){
                uni_reLaunch(ReLaunchOptions(url = "/pages/login/login"))
            }
            ))
            return
        }
        this.loadUserInfo()
    }
    open var loadUserInfo = ::gen_loadUserInfo_fn
    open fun gen_loadUserInfo_fn() {
        getUserProfile().then(fun(result){
            if (result.code === 200) {
                val data = result.data as UTSJSONObject
                if (data != null) {
                    val id = data.getNumber("id")
                    val avatar = data.getString("avatar")
                    val username = data.getString("username")
                    val email = data.getString("email")
                    val phone = data.getString("phone")
                    val nickname = data.getString("nickname")
                    this.userInfo = UserInfoType(id = if (id != null) {
                        id.toInt()
                    } else {
                        "12345678"
                    }
                    , avatar = if (avatar != null) {
                        avatar
                    } else {
                        ""
                    }
                    , username = if (username != null) {
                        username
                    } else {
                        ""
                    }
                    , email = if (email != null) {
                        email
                    } else {
                        ""
                    }
                    , phone = if (phone != null) {
                        phone
                    } else {
                        ""
                    }
                    , nickname = if (nickname != null) {
                        nickname
                    } else {
                        ""
                    }
                    )
                    val storedInfo = uni_getStorageSync("user_info")
                    var roleCodes: Any? = null
                    var isSeller: Boolean = false
                    var shopName: String = ""
                    if (storedInfo != null && storedInfo != "") {
                        try {
                            val oldInfo = UTSAndroid.consoleDebugError(JSON.parse(storedInfo as String), " at pages/user/profile.uvue:724") as UTSJSONObject
                            roleCodes = oldInfo.getAny("roleCodes")
                            isSeller = oldInfo.getBoolean("isSeller") == true
                            shopName = oldInfo.getString("shopName") ?: ""
                        }
                         catch (e: Throwable) {
                            console.error("解析旧用户信息失败:", e, " at pages/user/profile.uvue:729")
                        }
                    }
                    val mergedUserInfo: UTSJSONObject = let {
                        object : UTSJSONObject(UTSSourceMapPosition("mergedUserInfo", "pages/user/profile.uvue", 733, 13)) {
                            var id = it.userInfo.id
                            var username = it.userInfo.username
                            var nickname = it.userInfo.nickname
                            var avatar = it.userInfo.avatar
                            var email = it.userInfo.email
                            var phone = it.userInfo.phone
                            var roleCodes = roleCodes
                            var isSeller = isSeller
                            var shopName = shopName
                        }
                    }
                    uni_setStorageSync("user_info", JSON.stringify(mergedUserInfo))
                }
            }
        }
        ).`catch`(fun(error){
            console.error("获取用户信息失败:", error, " at pages/user/profile.uvue:748")
            val errStr = if (error != null) {
                error.toString()
            } else {
                ""
            }
            val errMsg = if (errStr != "") {
                errStr
            } else {
                ""
            }
            if (errMsg != "" && (errMsg.includes("401") || errMsg.includes("Token") || errMsg.includes("过期") || errMsg.includes("无效"))) {
                uni_removeStorageSync("auth_token")
                uni_removeStorageSync("user_info")
                uni_reLaunch(ReLaunchOptions(url = "/pages/login/login"))
            }
        }
        )
        this.loadOrderStats()
    }
    open var loadOrderStats = ::gen_loadOrderStats_fn
    open fun gen_loadOrderStats_fn() {
        getOrderCount().then(fun(result){
            if (result.code === 200) {
                val data = result.data as UTSJSONObject
                if (data != null) {
                    val unpaid = (data.getNumber("unpaid") ?: 0).toInt()
                    val unshipped = (data.getNumber("unshipped") ?: 0).toInt()
                    val shipped = (data.getNumber("shipped") ?: 0).toInt()
                    val completed = (data.getNumber("completed") ?: 0).toInt()
                    val total = (data.getNumber("total") ?: 0).toInt()
                    this.stats = StatsType(orders = total, pending = unpaid, shipped = shipped, favorites = this.stats.favorites)
                }
            }
        }
        ).`catch`(fun(error){
            console.error("获取订单统计失败:", error, " at pages/user/profile.uvue:782")
        }
        )
        this.loadCollectionCount()
    }
    open var loadCollectionCount = ::gen_loadCollectionCount_fn
    open fun gen_loadCollectionCount_fn() {
        getCollectionCount().then(fun(result){
            if (result.code === 200) {
                val count = result.data as Number
                this.stats.favorites = if (count != null) {
                    count
                } else {
                    0
                }
            }
        }
        ).`catch`(fun(error){
            console.error("获取收藏总数失败:", error, " at pages/user/profile.uvue:796")
        }
        )
    }
    open var handleImageError = ::gen_handleImageError_fn
    open fun gen_handleImageError_fn() {
        this.userInfo.avatar = this.defaultAvatar
    }
    open var handleUploadAvatar = ::gen_handleUploadAvatar_fn
    open fun gen_handleUploadAvatar_fn() {
        uni_showActionSheet(ShowActionSheetOptions(itemList = utsArrayOf(
            "拍照",
            "从相册选择"
        ), success = fun(res){
            val type = if (res.tapIndex === 0) {
                "camera"
            } else {
                "album"
            }
            uni_chooseImage(ChooseImageOptions(sourceType = utsArrayOf(
                type
            ), count = 1, success = fun(imgRes){
                if (imgRes.tempFilePaths.length > 0) {
                    this.uploadAvatarFile(imgRes.tempFilePaths[0])
                }
            }
            ))
        }
        ))
    }
    open var uploadAvatarFile = ::gen_uploadAvatarFile_fn
    open fun gen_uploadAvatarFile_fn(filePath: String) {
        if (this.isLoading) {
            return
        }
        this.isLoading = true
        uni_showLoading(ShowLoadingOptions(title = "上传中..."))
        uploadAvatar(filePath).then(fun(result){
            if (result != null) {
                val code = (result.getNumber("code") ?: 0).toInt()
                if (code === 200) {
                    val data = result.getString("data") ?: ""
                    this.userInfo.avatar = data
                    this.imageKey++
                    val storedInfo = uni_getStorageSync("user_info")
                    if (storedInfo != null && storedInfo != "") {
                        val infoStr = storedInfo as String
                        val info = UTSAndroid.consoleDebugError(JSON.parse(infoStr), " at pages/user/profile.uvue:836") as UTSJSONObject
                        val updatedInfo: UTSJSONObject = object : UTSJSONObject(UTSSourceMapPosition("updatedInfo", "pages/user/profile.uvue", 837, 14)) {
                            var id = if (info.getNumber("id") != null) {
                                info.getNumber("id")!!.toInt()
                            } else {
                                0
                            }
                            var username = info.getString("username") ?: ""
                            var nickname = info.getString("nickname") ?: ""
                            var avatar = data
                            var email = info.getString("email") ?: ""
                            var phone = info.getString("phone") ?: ""
                            var roleCodes = info.getAny("roleCodes")
                            var isSeller = info.getBoolean("isSeller") == true
                            var shopName = info.getString("shopName") ?: ""
                        }
                        uni_setStorageSync("user_info", JSON.stringify(updatedInfo))
                    }
                    uni_showToast(ShowToastOptions(title = "头像上传成功", icon = "success"))
                } else {
                    val msg = result.getString("message") ?: "上传失败"
                    uni_showToast(ShowToastOptions(title = msg, icon = "error"))
                }
            } else {
                uni_showToast(ShowToastOptions(title = "上传失败", icon = "error"))
            }
            this.isLoading = false
            uni_hideLoading()
        }
        ).`catch`(fun(error){
            uni_showToast(ShowToastOptions(title = "上传失败", icon = "error"))
            this.isLoading = false
            uni_hideLoading()
        }
        )
    }
    open var handleSettings = ::gen_handleSettings_fn
    open fun gen_handleSettings_fn() {
        uni_navigateTo(NavigateToOptions(url = "/pages/user/settings"))
    }
    open var handleNavigate = ::gen_handleNavigate_fn
    open fun gen_handleNavigate_fn(type: String) {
        val routes = Map<String, String>(utsArrayOf(
            utsArrayOf(
                "products",
                "/pages/product/list"
            ),
            utsArrayOf(
                "orders",
                "/pages/order/list"
            ),
            utsArrayOf(
                "favorites",
                "/pages/product/favorites"
            ),
            utsArrayOf(
                "history",
                "/pages/product/history"
            ),
            utsArrayOf(
                "reviews",
                "/pages/user/reviews"
            ),
            utsArrayOf(
                "addresses",
                "/pages/user/address-list"
            ),
            utsArrayOf(
                "coupons",
                "/pages/user/coupons"
            ),
            utsArrayOf(
                "wallet",
                "/pages/user/wallet"
            ),
            utsArrayOf(
                "pending",
                "/pages/order/list?status=pending"
            ),
            utsArrayOf(
                "shipped",
                "/pages/order/list?status=shipped"
            ),
            utsArrayOf(
                "service",
                "/pages/service/chat"
            )
        ))
        val route = routes.get(type)
        if (route != null && route != "") {
            uni_navigateTo(NavigateToOptions(url = route as String))
        } else {
            uni_showToast(ShowToastOptions(title = "功能开发中", icon = "none"))
        }
    }
    open var handleEditProfile = ::gen_handleEditProfile_fn
    open fun gen_handleEditProfile_fn() {
        uni_navigateTo(NavigateToOptions(url = "/pages/user/edit-profile"))
    }
    open var handleChangePassword = ::gen_handleChangePassword_fn
    open fun gen_handleChangePassword_fn() {
        uni_navigateTo(NavigateToOptions(url = "/pages/user/change-password"))
    }
    open var handleLogout = ::gen_handleLogout_fn
    open fun gen_handleLogout_fn() {
        uni_showModal(ShowModalOptions(title = "退出登录", content = "确定要退出登录吗？", confirmText = "退出", confirmColor = "#FF4D4F", success = fun(res){
            if (res.confirm) {
                logout().then(fun(){}).`catch`(fun(){})
                uni_removeStorageSync("auth_token")
                uni_removeStorageSync("user_info")
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
                return utsMapOf("profile-page" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "column", "backgroundColor" to "#F5F7FA")), "profile-container" to padStyleMapOf(utsMapOf("height" to "100%", "backgroundColor" to "#F5F7FA", "position" to "relative", "overflow" to "hidden", "display" to "flex", "flexDirection" to "column")), "bg-shape" to padStyleMapOf(utsMapOf("position" to "absolute", "borderTopLeftRadius" to 999, "borderTopRightRadius" to 999, "borderBottomRightRadius" to 999, "borderBottomLeftRadius" to 999, "opacity" to 0.1, "zIndex" to 0)), "bg-shape-1" to padStyleMapOf(utsMapOf("width" to 300, "height" to 300, "backgroundColor" to "#0066CC", "top" to -100, "left" to -50)), "bg-shape-2" to padStyleMapOf(utsMapOf("width" to 200, "height" to 200, "backgroundColor" to "#52C41A", "top" to 50, "right" to -50)), "header" to padStyleMapOf(utsMapOf("height" to 44, "display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "paddingTop" to 0, "paddingRight" to 24, "paddingBottom" to 0, "paddingLeft" to 24, "marginTop" to CSS_VAR_STATUS_BAR_HEIGHT, "position" to "relative", "zIndex" to 10, "flexShrink" to 0)), "header-spacer" to padStyleMapOf(utsMapOf("flex" to 1)), "switch-role-btn" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "backgroundColor" to "rgba(255,255,255,0.9)", "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "paddingTop" to 6, "paddingRight" to 12, "paddingBottom" to 6, "paddingLeft" to 12, "boxShadow" to "0 2px 6px rgba(0, 0, 0, 0.1)")), "switch-icon" to padStyleMapOf(utsMapOf("fontSize" to 14, "marginRight" to 4)), "switch-text" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#0066CC", "fontWeight" to "bold")), "settings-btn" to padStyleMapOf(utsMapOf("width" to 40, "height" to 40, "display" to "flex", "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "rgba(255,255,255,0.8)", "borderTopLeftRadius" to 20, "borderTopRightRadius" to 20, "borderBottomRightRadius" to 20, "borderBottomLeftRadius" to 20, "boxShadow" to "0 2px 6px rgba(0, 0, 0, 0.1)")), "settings-icon" to padStyleMapOf(utsMapOf("fontSize" to 20)), "main-content" to padStyleMapOf(utsMapOf("flex" to 1, "paddingTop" to 16, "paddingRight" to 24, "paddingBottom" to "160rpx", "paddingLeft" to 24, "position" to "relative", "zIndex" to 1)), "role-status-bar" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center", "paddingTop" to 10, "paddingRight" to 16, "paddingBottom" to 10, "paddingLeft" to 16, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "marginBottom" to 16, "boxShadow" to "0 2px 8px rgba(0, 0, 0, 0.08)")), "buyer-mode" to padStyleMapOf(utsMapOf("backgroundImage" to "linear-gradient(135deg, #E6F4FF 0%, #BAE0FF 100%)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#91CAFF", "borderRightColor" to "#91CAFF", "borderBottomColor" to "#91CAFF", "borderLeftColor" to "#91CAFF")), "seller-mode" to padStyleMapOf(utsMapOf("backgroundImage" to "linear-gradient(135deg, #FFF7E6 0%, #FFE7BA 100%)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FFD591", "borderRightColor" to "#FFD591", "borderBottomColor" to "#FFD591", "borderLeftColor" to "#FFD591")), "role-icon" to padStyleMapOf(utsMapOf("fontSize" to 18, "marginRight" to 8)), "role-text" to padStyleMapOf(utsMapOf("fontSize" to 15, "fontWeight" to "700", "color" to "#0066CC")), "role-text-seller" to padStyleMapOf(utsMapOf("color" to "#FF8C00")), "profile-card" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "paddingTop" to 24, "paddingRight" to 24, "paddingBottom" to 24, "paddingLeft" to 24, "boxShadow" to "0 4px 16px rgba(0, 0, 0, 0.04)", "marginBottom" to 24)), "profile-top" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "marginBottom" to 24)), "avatar-wrapper" to padStyleMapOf(utsMapOf("position" to "relative", "marginRight" to 16)), "avatar" to padStyleMapOf(utsMapOf("width" to 72, "height" to 72, "borderTopLeftRadius" to 36, "borderTopRightRadius" to 36, "borderBottomRightRadius" to 36, "borderBottomLeftRadius" to 36, "borderTopWidth" to 3, "borderRightWidth" to 3, "borderBottomWidth" to 3, "borderLeftWidth" to 3, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#ffffff", "borderRightColor" to "#ffffff", "borderBottomColor" to "#ffffff", "borderLeftColor" to "#ffffff", "boxShadow" to "0 4px 12px rgba(0, 0, 0, 0.08)", "backgroundColor" to "#f0f0f0")), "avatar-edit-badge" to padStyleMapOf(utsMapOf("position" to "absolute", "bottom" to 0, "right" to 0, "width" to 24, "height" to 24, "backgroundColor" to "#0066CC", "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "display" to "flex", "alignItems" to "center", "justifyContent" to "center", "borderTopWidth" to 2, "borderRightWidth" to 2, "borderBottomWidth" to 2, "borderLeftWidth" to 2, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#ffffff", "borderRightColor" to "#ffffff", "borderBottomColor" to "#ffffff", "borderLeftColor" to "#ffffff")), "edit-icon" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#ffffff")), "profile-info" to padStyleMapOf(utsMapOf("flex" to 1, "display" to "flex", "flexDirection" to "column", "justifyContent" to "center")), "nickname" to padStyleMapOf(utsMapOf("fontSize" to 20, "fontWeight" to "700", "color" to "#333333", "marginBottom" to 4)), "username" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999", "marginBottom" to 4)), "user-id-tag" to padStyleMapOf(utsMapOf("backgroundColor" to "#F5F7FA", "paddingTop" to 2, "paddingRight" to 8, "paddingBottom" to 2, "paddingLeft" to 8, "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10, "alignSelf" to "flex-start")), "user-id" to padStyleMapOf(utsMapOf("fontSize" to 10, "color" to "#999999")), "stats-row" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "paddingTop" to 16, "borderTopWidth" to 1, "borderTopStyle" to "solid", "borderTopColor" to "#F5F5F5")), "stat-item" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "column", "alignItems" to "center", "flex" to 1)), "stat-num" to padStyleMapOf(utsMapOf("fontSize" to 20, "fontWeight" to "700", "color" to "#333333", "marginBottom" to 6)), "stat-name" to padStyleMapOf(utsMapOf("fontSize" to 10, "color" to "#999999")), "section-title" to padStyleMapOf(utsMapOf("fontSize" to 16, "fontWeight" to "700", "color" to "#333333", "marginBottom" to 16, "marginLeft" to 4)), "menu-grid" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "flexWrap" to "wrap", "backgroundColor" to "#ffffff", "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "boxShadow" to "0 4px 16px rgba(0, 0, 0, 0.04)", "marginBottom" to 24)), "menu-item" to padStyleMapOf(utsMapOf("width" to "25%", "display" to "flex", "flexDirection" to "column", "alignItems" to "center", "marginBottom" to 24)), "menu-icon-box" to padStyleMapOf(utsMapOf("width" to 44, "height" to 44, "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "display" to "flex", "alignItems" to "center", "justifyContent" to "center", "marginBottom" to 8)), "menu-icon-box-blue" to padStyleMapOf(utsMapOf("backgroundColor" to "rgba(0,102,204,0.1)")), "menu-icon-box-green" to padStyleMapOf(utsMapOf("backgroundColor" to "rgba(82,196,26,0.1)")), "menu-icon-box-orange" to padStyleMapOf(utsMapOf("backgroundColor" to "rgba(255,122,69,0.1)")), "menu-icon-box-purple" to padStyleMapOf(utsMapOf("backgroundColor" to "rgba(114,46,209,0.1)")), "menu-icon-box-red" to padStyleMapOf(utsMapOf("backgroundColor" to "rgba(255,77,79,0.1)")), "menu-icon-box-cyan" to padStyleMapOf(utsMapOf("backgroundColor" to "rgba(19,194,194,0.1)")), "menu-icon-box-teal" to padStyleMapOf(utsMapOf("backgroundColor" to "rgba(0,150,136,0.1)")), "menu-icon-box-indigo" to padStyleMapOf(utsMapOf("backgroundColor" to "rgba(63,81,181,0.1)")), "menu-icon" to padStyleMapOf(utsMapOf("fontSize" to 20)), "menu-label" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#333333")), "promo-banner" to padStyleMapOf(utsMapOf("backgroundImage" to "linear-gradient(135deg, #0066CC 0%, #1890FF 100%)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "paddingTop" to 16, "paddingRight" to 24, "paddingBottom" to 16, "paddingLeft" to 24, "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "boxShadow" to "0 4px 12px rgba(0, 102, 204, 0.2)", "marginBottom" to 16)), "promo-content" to padStyleMapOf(utsMapOf("flex" to 1, "display" to "flex", "flexDirection" to "column")), "promo-title" to padStyleMapOf(utsMapOf("fontSize" to 16, "fontWeight" to "700", "color" to "#ffffff", "marginBottom" to 4)), "promo-desc" to padStyleMapOf(utsMapOf("fontSize" to 10, "color" to "rgba(255,255,255,0.8)")), "promo-btn" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "color" to "#0066CC", "fontSize" to 10, "fontWeight" to "700", "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "paddingTop" to 0, "paddingRight" to 12, "paddingBottom" to 0, "paddingLeft" to 12, "height" to 28, "lineHeight" to "28px", "marginLeft" to 16)), "buyer-tips" to padStyleMapOf(utsMapOf("backgroundColor" to "#E6F7FF", "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "paddingTop" to 16, "paddingRight" to 24, "paddingBottom" to 16, "paddingLeft" to 24, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#BAE7FF", "borderRightColor" to "#BAE7FF", "borderBottomColor" to "#BAE7FF", "borderLeftColor" to "#BAE7FF", "marginTop" to 16, "marginBottom" to 16)), "logout-btn" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "color" to "#666666", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E5E5E5", "borderRightColor" to "#E5E5E5", "borderBottomColor" to "#E5E5E5", "borderLeftColor" to "#E5E5E5", "fontSize" to 14, "fontWeight" to "400", "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "height" to 48, "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center", "boxShadow" to "0 2px 8px rgba(0, 0, 0, 0.05)", "marginTop" to 16, "marginBottom" to 16)), "logout-icon" to padStyleMapOf(utsMapOf("fontSize" to 18, "marginRight" to 6)), "logout-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#666666")), "seller-profile-card" to padStyleMapOf(utsMapOf("backgroundImage" to "none", "backgroundColor" to "#ffffff", "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "overflow" to "hidden", "boxShadow" to "0 4px 16px rgba(0, 0, 0, 0.08)", "marginBottom" to 24, "position" to "relative")), "seller-card-bg" to padStyleMapOf(utsMapOf("height" to 80, "backgroundImage" to "linear-gradient(135deg, #0066CC 0%, #1890FF 100%)", "backgroundColor" to "rgba(0,0,0,0)", "position" to "relative")), "seller-main-content" to padStyleMapOf(utsMapOf("paddingTop" to 0, "paddingRight" to 24, "paddingBottom" to 24, "paddingLeft" to 24, "marginTop" to -40, "position" to "relative", "zIndex" to 2)), "seller-profile-header" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "flex-start", "marginBottom" to 16)), "seller-avatar-box" to padStyleMapOf(utsMapOf("position" to "relative", "marginRight" to 12)), "seller-avatar-img" to padStyleMapOf(utsMapOf("width" to 88, "height" to 88, "borderTopLeftRadius" to 44, "borderTopRightRadius" to 44, "borderBottomRightRadius" to 44, "borderBottomLeftRadius" to 44, "borderTopWidth" to 4, "borderRightWidth" to 4, "borderBottomWidth" to 4, "borderLeftWidth" to 4, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#ffffff", "borderRightColor" to "#ffffff", "borderBottomColor" to "#ffffff", "borderLeftColor" to "#ffffff", "backgroundColor" to "#f5f5f5", "boxShadow" to "0 2px 8px rgba(0, 0, 0, 0.1)")), "seller-verify-badge" to padStyleMapOf(utsMapOf("position" to "absolute", "bottom" to 2, "right" to 2, "width" to 24, "height" to 24, "backgroundImage" to "linear-gradient(135deg, #52C41A 0%, #73D13D 100%)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "display" to "flex", "alignItems" to "center", "justifyContent" to "center", "borderTopWidth" to 2, "borderRightWidth" to 2, "borderBottomWidth" to 2, "borderLeftWidth" to 2, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#ffffff", "borderRightColor" to "#ffffff", "borderBottomColor" to "#ffffff", "borderLeftColor" to "#ffffff", "boxShadow" to "0 2px 4px rgba(82, 196, 26, 0.3)")), "verify-icon" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#ffffff", "fontWeight" to "700")), "seller-basic-info" to padStyleMapOf(utsMapOf("flex" to 1, "paddingTop" to 48)), "shop-name-row" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "marginBottom" to 4)), "shop-name-text" to padStyleMapOf(utsMapOf("fontSize" to 20, "fontWeight" to "700", "color" to "#333333", "marginRight" to 8)), "shop-level-tag" to padStyleMapOf(utsMapOf("backgroundImage" to "linear-gradient(135deg, #FFD700 0%, #FFA500 100%)", "backgroundColor" to "rgba(0,0,0,0)", "paddingTop" to 2, "paddingRight" to 8, "paddingBottom" to 2, "paddingLeft" to 8, "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10)), "level-text" to padStyleMapOf(utsMapOf("fontSize" to 10, "color" to "#ffffff", "fontWeight" to "700")), "seller-id-text" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999", "marginBottom" to 6)), "seller-rating-row" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center")), "rating-stars" to padStyleMapOf(utsMapOf("fontSize" to 11, "marginRight" to 4)), "rating-score" to padStyleMapOf(utsMapOf("fontSize" to 14, "fontWeight" to "700", "color" to "#FF8C00", "marginRight" to 4)), "rating-count" to padStyleMapOf(utsMapOf("fontSize" to 11, "color" to "#999999")), "seller-stats-grid" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "marginTop" to 16)), "stat-card" to padStyleMapOf(utsMapOf("flex" to 1, "backgroundImage" to "none", "backgroundColor" to "#F8F9FA", "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "paddingTop" to 12, "paddingRight" to 8, "paddingBottom" to 12, "paddingLeft" to 8, "display" to "flex", "flexDirection" to "column", "alignItems" to "center", "position" to "relative", "overflow" to "hidden", "marginTop" to 0, "marginRight" to 5, "marginBottom" to 0, "marginLeft" to 5)), "stat-card-1" to padStyleMapOf(utsMapOf("backgroundImage" to "linear-gradient(135deg, #E6F7FF 0%, #BAE7FF 100%)", "backgroundColor" to "rgba(0,0,0,0)")), "stat-card-2" to padStyleMapOf(utsMapOf("backgroundImage" to "linear-gradient(135deg, #FFF7E6 0%, #FFE7BA 100%)", "backgroundColor" to "rgba(0,0,0,0)")), "stat-card-3" to padStyleMapOf(utsMapOf("backgroundImage" to "linear-gradient(135deg, #F6FFED 0%, #D9F7BE 100%)", "backgroundColor" to "rgba(0,0,0,0)")), "stat-icon" to padStyleMapOf(utsMapOf("fontSize" to 24, "marginBottom" to 4)), "stat-number" to padStyleMapOf(utsMapOf("fontSize" to 18, "fontWeight" to "700", "color" to "#333333", "marginBottom" to 2)), "stat-label" to padStyleMapOf(utsMapOf("fontSize" to 11, "color" to "#666666")), "seller-tips" to padStyleMapOf(utsMapOf("backgroundColor" to "#FFF7E6", "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "paddingTop" to 16, "paddingRight" to 24, "paddingBottom" to 16, "paddingLeft" to 24, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FFE4B5", "borderRightColor" to "#FFE4B5", "borderBottomColor" to "#FFE4B5", "borderLeftColor" to "#FFE4B5", "marginTop" to 16, "marginBottom" to 16)), "tips-content" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "column")), "tips-title" to padStyleMapOf(utsMapOf("fontSize" to 14, "fontWeight" to "bold", "color" to "#FF8C00", "marginBottom" to 4)), "tips-desc" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#996600", "lineHeight" to 1.5)), "admin-entry-section" to padStyleMapOf(utsMapOf("marginBottom" to 24)), "admin-entry-card" to padStyleMapOf(utsMapOf("backgroundImage" to "linear-gradient(135deg, #722ED1 0%, #9254DE 100%)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "boxShadow" to "0 4px 12px rgba(114, 46, 209, 0.3)", "position" to "relative", "overflow" to "hidden")), "admin-entry-left" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "flex" to 1, "zIndex" to 1)), "admin-entry-icon" to padStyleMapOf(utsMapOf("fontSize" to 36, "marginRight" to 12)), "admin-entry-info" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "column")), "admin-entry-title" to padStyleMapOf(utsMapOf("fontSize" to 17, "fontWeight" to "700", "color" to "#FFFFFF", "marginBottom" to 4)), "admin-entry-desc" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "rgba(255,255,255,0.8)")), "admin-entry-arrow" to padStyleMapOf(utsMapOf("fontSize" to 24, "color" to "#FFFFFF", "zIndex" to 1)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = utsMapOf()
        var emits: Map<String, Any?> = utsMapOf()
        var props = normalizePropsOptions(utsMapOf())
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf()
        var components: Map<String, CreateVueComponent> = utsMapOf("BottomNav" to GenComponentsBottomNavClass)
    }
}
