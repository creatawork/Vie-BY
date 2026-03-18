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
import io.dcloud.uniapp.extapi.showModal as uni_showModal
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesSellerAdminUsers : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onLoad(fun(_: OnLoadOptions) {
            this.loadUserList()
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return createElementVNode("view", utsMapOf("class" to "page"), utsArrayOf(
            createElementVNode("view", utsMapOf("class" to "search-bar"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "search-wrapper"), utsArrayOf(
                    createElementVNode("input", utsMapOf("modelValue" to _ctx.searchKeyword, "onInput" to fun(`$event`: InputEvent){
                        _ctx.searchKeyword = `$event`.detail.value
                    }
                    , "class" to "search-input", "placeholder" to "搜索用户名/手机号", "onConfirm" to _ctx.handleSearch), null, 40, utsArrayOf(
                        "modelValue",
                        "onInput",
                        "onConfirm"
                    ))
                ))
            )),
            createElementVNode("scroll-view", utsMapOf("class" to "user-list", "scroll-y" to "", "onScrolltolower" to _ctx.loadMore), utsArrayOf(
                createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.userList, fun(user, __key, __index, _cached): Any {
                    return createElementVNode("view", utsMapOf("key" to user.id, "class" to "user-card"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "card-left"), utsArrayOf(
                            createElementVNode("image", utsMapOf("src" to if (user.avatar !== "") {
                                user.avatar
                            } else {
                                "/static/default-avatar.png"
                            }
                            , "class" to "user-avatar", "mode" to "aspectFill"), null, 8, utsArrayOf(
                                "src"
                            )),
                            createElementVNode("view", utsMapOf("class" to "user-info"), utsArrayOf(
                                createElementVNode("view", utsMapOf("class" to "info-top"), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "user-name"), toDisplayString(user.username), 1),
                                    createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                                        "status-badge",
                                        if (user.status === 1) {
                                            "badge-active"
                                        } else {
                                            "badge-disabled"
                                        }
                                    ))), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                                            "badge-text",
                                            if (user.status === 1) {
                                                "badge-active-text"
                                            } else {
                                                "badge-disabled-text"
                                            }
                                        ))), toDisplayString(if (user.status === 1) {
                                            "正常"
                                        } else {
                                            "禁用"
                                        }
                                        ), 3)
                                    ), 2)
                                )),
                                createElementVNode("text", utsMapOf("class" to "user-phone"), toDisplayString(if (user.phone !== "") {
                                    user.phone
                                } else {
                                    "未绑定手机"
                                }
                                ), 1)
                            ))
                        )),
                        createElementVNode("view", utsMapOf("class" to "card-right"), utsArrayOf(
                            createElementVNode("view", utsMapOf("class" to "btn btn-detail", "onClick" to fun(){
                                _ctx.viewUserDetail(user.id)
                            }
                            ), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "btn-text btn-detail-text"), "详情")
                            ), 8, utsArrayOf(
                                "onClick"
                            )),
                            createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                                "btn",
                                if (user.status === 1) {
                                    "btn-danger"
                                } else {
                                    "btn-success"
                                }
                            )), "onClick" to fun(){
                                _ctx.toggleUserStatus(user)
                            }
                            ), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                                    "btn-text",
                                    if (user.status === 1) {
                                        "btn-danger-text"
                                    } else {
                                        "btn-success-text"
                                    }
                                ))), toDisplayString(if (user.status === 1) {
                                    "禁用"
                                } else {
                                    "启用"
                                }
                                ), 3)
                            ), 10, utsArrayOf(
                                "onClick"
                            ))
                        ))
                    ))
                }
                ), 128),
                createElementVNode("view", utsMapOf("class" to "load-tip"), utsArrayOf(
                    if (isTrue(_ctx.loading)) {
                        createElementVNode("text", utsMapOf("key" to 0, "class" to "tip-text"), "加载中...")
                    } else {
                        if (isTrue(_ctx.noMore)) {
                            createElementVNode("text", utsMapOf("key" to 1, "class" to "tip-text"), "没有更多了")
                        } else {
                            createCommentVNode("v-if", true)
                        }
                    }
                ))
            ), 40, utsArrayOf(
                "onScrolltolower"
            ))
        ))
    }
    open var searchKeyword: String by `$data`
    open var userList: UTSArray<UserType> by `$data`
    open var pageNum: Number by `$data`
    open var pageSize: Number by `$data`
    open var loading: Boolean by `$data`
    open var noMore: Boolean by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return utsMapOf("searchKeyword" to "", "userList" to utsArrayOf<UserType>(), "pageNum" to 1, "pageSize" to 10, "loading" to false, "noMore" to false)
    }
    open var loadUserList = ::gen_loadUserList_fn
    open fun gen_loadUserList_fn() {
        if (this.loading || this.noMore) {
            return
        }
        this.loading = true
        getAdminUsers(this.pageNum, this.pageSize, this.searchKeyword).then(fun(res: ResponseDataType){
            this.loading = false
            if (res.code === 200 && res.data != null) {
                val data = res.data as UTSJSONObject
                val records = data.getArray("records")
                if (records != null && records.length > 0) {
                    val newUsers = utsArrayOf<UserType>()
                    run {
                        var i: Number = 0
                        while(i < records.length){
                            val item = records[i] as UTSJSONObject
                            newUsers.push(UserType(id = (item.getNumber("id") ?: 0).toInt(), username = item.getString("username") ?: "", nickname = item.getString("nickname") ?: "", phone = item.getString("phone") ?: "", email = item.getString("email") ?: "", avatar = item.getString("avatar") ?: "", gender = (item.getNumber("gender") ?: 0).toInt(), status = (item.getNumber("status") ?: 1).toInt(), createTime = item.getString("createTime") ?: "", updateTime = item.getString("updateTime") ?: ""))
                            i++
                        }
                    }
                    if (this.pageNum === 1) {
                        this.userList = newUsers
                    } else {
                        this.userList = this.userList.concat(newUsers)
                    }
                    if (newUsers.length < this.pageSize) {
                        this.noMore = true
                    }
                } else {
                    if (this.pageNum === 1) {
                        this.userList = utsArrayOf()
                    }
                    this.noMore = true
                }
            }
        }
        ).`catch`(fun(err: Any?){
            this.loading = false
            val error = err as UTSError
            uni_showToast(ShowToastOptions(title = error.message, icon = "none"))
        }
        )
    }
    open var handleSearch = ::gen_handleSearch_fn
    open fun gen_handleSearch_fn() {
        this.pageNum = 1
        this.noMore = false
        this.userList = utsArrayOf()
        this.loadUserList()
    }
    open var loadMore = ::gen_loadMore_fn
    open fun gen_loadMore_fn() {
        if (!this.loading && !this.noMore) {
            this.pageNum++
            this.loadUserList()
        }
    }
    open var toggleUserStatus = ::gen_toggleUserStatus_fn
    open fun gen_toggleUserStatus_fn(user: UserType) {
        val newStatus = if (user.status === 1) {
            0
        } else {
            1
        }
        val statusText = if (newStatus === 1) {
            "启用"
        } else {
            "禁用"
        }
        uni_showModal(ShowModalOptions(title = "确认操作", content = "\u786E\u5B9A\u8981" + statusText + "\u7528\u6237\"" + user.username + "\"\u5417\uFF1F", success = fun(res){
            if (res.confirm) {
                updateUserStatus(user.id, newStatus).then(fun(response: ResponseDataType){
                    if (response.code === 200) {
                        uni_showToast(ShowToastOptions(title = "" + statusText + "\u6210\u529F", icon = "success"))
                        user.status = newStatus
                    } else {
                        uni_showToast(ShowToastOptions(title = response.message, icon = "none"))
                    }
                }
                ).`catch`(fun(err: Any?){
                    val error = err as UTSError
                    uni_showToast(ShowToastOptions(title = error.message, icon = "none"))
                }
                )
            }
        }
        ))
    }
    open var viewUserDetail = ::gen_viewUserDetail_fn
    open fun gen_viewUserDetail_fn(userId: Number) {
        getAdminUserDetail(userId).then(fun(res: ResponseDataType){
            if (res.code === 200 && res.data != null) {
                val user = res.data as UTSJSONObject
                val genderMap = utsArrayOf(
                    "未知",
                    "男",
                    "女"
                )
                val gender = (user.getNumber("gender") ?: 0).toInt()
                val genderText = genderMap[gender]
                val content = "\u7528\u6237\u540D\uFF1A" + (user.getString("username") ?: "") + "\n\u6635\u79F0\uFF1A" + (user.getString("nickname") ?: "未设置") + "\n\u624B\u673A\uFF1A" + (user.getString("phone") ?: "未绑定") + "\n\u90AE\u7BB1\uFF1A" + (user.getString("email") ?: "未绑定") + "\n\u6027\u522B\uFF1A" + genderText + "\n\u6CE8\u518C\u65F6\u95F4\uFF1A" + (user.getString("createTime") ?: "")
                uni_showModal(ShowModalOptions(title = "用户详情", content = content, showCancel = false))
            }
        }
        ).`catch`(fun(err: Any?){
            val error = err as UTSError
            uni_showToast(ShowToastOptions(title = error.message, icon = "none"))
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
                return utsMapOf("page" to padStyleMapOf(utsMapOf("backgroundColor" to "#f5f5f5", "display" to "flex", "flexDirection" to "column")), "search-bar" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "paddingTop" to 12, "paddingRight" to 15, "paddingBottom" to 12, "paddingLeft" to 15, "boxShadow" to "0 2px 8px rgba(0, 0, 0, 0.06)")), "search-wrapper" to padStyleMapOf(utsMapOf("backgroundColor" to "#f7f7f7", "borderTopLeftRadius" to 20, "borderTopRightRadius" to 20, "borderBottomRightRadius" to 20, "borderBottomLeftRadius" to 20, "paddingTop" to 0, "paddingRight" to 15, "paddingBottom" to 0, "paddingLeft" to 15, "height" to 40, "display" to "flex", "alignItems" to "center")), "search-input" to padStyleMapOf(utsMapOf("flex" to 1, "fontSize" to 14, "color" to "#333333")), "user-list" to padStyleMapOf(utsMapOf("flex" to 1, "paddingTop" to 12, "paddingRight" to 15, "paddingBottom" to 12, "paddingLeft" to 15)), "user-card" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10, "marginBottom" to 10, "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "boxShadow" to "0 2px 6px rgba(0, 0, 0, 0.06)", "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between")), "card-left" to padStyleMapOf(utsMapOf("flex" to 1, "display" to "flex", "flexDirection" to "row", "alignItems" to "center")), "user-avatar" to padStyleMapOf(utsMapOf("width" to 45, "height" to 45, "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "marginRight" to 12, "backgroundColor" to "#e0e0e0")), "user-info" to padStyleMapOf(utsMapOf("flex" to 1, "display" to "flex", "flexDirection" to "column")), "info-top" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "marginBottom" to 4)), "user-name" to padStyleMapOf(utsMapOf("fontSize" to 16, "fontWeight" to "bold", "color" to "#333333", "marginRight" to 8)), "status-badge" to padStyleMapOf(utsMapOf("paddingTop" to 2, "paddingRight" to 8, "paddingBottom" to 2, "paddingLeft" to 8, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8)), "badge-active" to padStyleMapOf(utsMapOf("backgroundColor" to "#e6f7ff")), "badge-disabled" to padStyleMapOf(utsMapOf("backgroundColor" to "#fff1f0")), "badge-text" to padStyleMapOf(utsMapOf("fontSize" to 11)), "badge-active-text" to padStyleMapOf(utsMapOf("color" to "#1890ff")), "badge-disabled-text" to padStyleMapOf(utsMapOf("color" to "#ff4d4f")), "user-phone" to padStyleMapOf(utsMapOf("fontSize" to 13, "color" to "#999999")), "card-right" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "marginLeft" to 10)), "btn" to padStyleMapOf(utsMapOf("width" to 55, "height" to 32, "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "display" to "flex", "alignItems" to "center", "justifyContent" to "center", "marginLeft" to 8)), "btn-detail" to padStyleMapOf(utsMapOf("backgroundColor" to "#f5f5f5")), "btn-detail-text" to padStyleMapOf(utsMapOf("color" to "#666666")), "btn-success" to padStyleMapOf(utsMapOf("backgroundColor" to "#52c41a")), "btn-success-text" to padStyleMapOf(utsMapOf("color" to "#ffffff")), "btn-danger" to padStyleMapOf(utsMapOf("backgroundColor" to "#ff4d4f")), "btn-danger-text" to padStyleMapOf(utsMapOf("color" to "#ffffff")), "btn-text" to padStyleMapOf(utsMapOf("fontSize" to 13, "fontWeight" to "bold")), "load-tip" to padStyleMapOf(utsMapOf("paddingTop" to 20, "paddingRight" to 0, "paddingBottom" to 20, "paddingLeft" to 0, "textAlign" to "center")), "tip-text" to padStyleMapOf(utsMapOf("fontSize" to 13, "color" to "#999999")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = utsMapOf()
        var emits: Map<String, Any?> = utsMapOf()
        var props = normalizePropsOptions(utsMapOf())
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf()
        var components: Map<String, CreateVueComponent> = utsMapOf()
    }
}
