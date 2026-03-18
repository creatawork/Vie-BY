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
import io.dcloud.uniapp.extapi.getStorageSync as uni_getStorageSync
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.removeStorageSync as uni_removeStorageSync
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showModal as uni_showModal
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesUserSettings : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onLoad(fun(_: OnLoadOptions) {
            this.loadUserInfo()
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return createElementVNode("view", utsMapOf("class" to "settings-container"), utsArrayOf(
            createElementVNode("view", utsMapOf("class" to "header"), utsArrayOf(
                createElementVNode("text", utsMapOf("class" to "back-icon", "onClick" to _ctx.handleBack), "‹", 8, utsArrayOf(
                    "onClick"
                )),
                createElementVNode("text", utsMapOf("class" to "header-title"), "设置")
            )),
            createElementVNode("view", utsMapOf("class" to "content"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "section"), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "section-title"), "基本信息"),
                    createElementVNode("view", utsMapOf("class" to "setting-item"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "setting-label"), "用户名"),
                        createElementVNode("text", utsMapOf("class" to "setting-value"), toDisplayString(_ctx.userInfo.username), 1)
                    )),
                    createElementVNode("view", utsMapOf("class" to "setting-item", "onClick" to _ctx.handleEditEmail), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "setting-label"), "邮箱"),
                        createElementVNode("text", utsMapOf("class" to "setting-value"), toDisplayString(_ctx.getEmail()), 1),
                        createElementVNode("text", utsMapOf("class" to "setting-arrow"), "›")
                    ), 8, utsArrayOf(
                        "onClick"
                    )),
                    createElementVNode("view", utsMapOf("class" to "setting-item"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "setting-label"), "手机号"),
                        createElementVNode("text", utsMapOf("class" to "setting-value"), toDisplayString(_ctx.getPhone()), 1)
                    )),
                    createElementVNode("view", utsMapOf("class" to "setting-item", "onClick" to _ctx.handleEditNickname), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "setting-label"), "昵称"),
                        createElementVNode("text", utsMapOf("class" to "setting-value"), toDisplayString(_ctx.getNickname()), 1),
                        createElementVNode("text", utsMapOf("class" to "setting-arrow"), "›")
                    ), 8, utsArrayOf(
                        "onClick"
                    ))
                )),
                createElementVNode("view", utsMapOf("class" to "section"), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "section-title"), "安全设置"),
                    createElementVNode("view", utsMapOf("class" to "setting-item", "onClick" to _ctx.handleChangePassword), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "setting-label"), "修改密码"),
                        createElementVNode("text", utsMapOf("class" to "setting-arrow"), "›")
                    ), 8, utsArrayOf(
                        "onClick"
                    ))
                )),
                createElementVNode("view", utsMapOf("class" to "logout-section"), utsArrayOf(
                    createElementVNode("button", utsMapOf("class" to "logout-btn", "onClick" to _ctx.handleLogout), "退出登录", 8, utsArrayOf(
                        "onClick"
                    ))
                ))
            )),
            if (isTrue(_ctx.showEditModal)) {
                createElementVNode("view", utsMapOf("key" to 0, "class" to "modal-overlay", "onClick" to _ctx.closeEditModal), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "modal-content", "onClick" to withModifiers(fun(){}, utsArrayOf(
                        "stop"
                    ))), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "modal-title"), toDisplayString(_ctx.editTitle), 1),
                        createElementVNode("input", utsMapOf("modelValue" to _ctx.editValue, "onInput" to fun(`$event`: InputEvent){
                            _ctx.editValue = `$event`.detail.value
                        }, "class" to "modal-input", "placeholder" to _ctx.editPlaceholder, "onConfirm" to _ctx.handleConfirmEdit), null, 40, utsArrayOf(
                            "modelValue",
                            "onInput",
                            "placeholder",
                            "onConfirm"
                        )),
                        createElementVNode("view", utsMapOf("class" to "modal-actions"), utsArrayOf(
                            createElementVNode("button", utsMapOf("class" to "modal-btn cancel-btn", "onClick" to _ctx.closeEditModal), "取消", 8, utsArrayOf(
                                "onClick"
                            )),
                            createElementVNode("button", utsMapOf("class" to "modal-btn confirm-btn", "onClick" to _ctx.handleConfirmEdit), "确定", 8, utsArrayOf(
                                "onClick"
                            ))
                        ))
                    ))
                ), 8, utsArrayOf(
                    "onClick"
                ))
            } else {
                createCommentVNode("v-if", true)
            }
        ))
    }
    open var userInfo: UserInfoType1 by `$data`
    open var showEditModal: Boolean by `$data`
    open var editType: String by `$data`
    open var editTitle: String by `$data`
    open var editValue: String by `$data`
    open var editPlaceholder: String by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return utsMapOf("userInfo" to UserInfoType1(username = "testuser", email = "user@example.com", phone = "13800138000", nickname = "测试用户"), "showEditModal" to false, "editType" to "", "editTitle" to "", "editValue" to "", "editPlaceholder" to "")
    }
    open var getEmail = ::gen_getEmail_fn
    open fun gen_getEmail_fn(): String {
        return if (this.userInfo.email != "") {
            this.userInfo.email
        } else {
            "未设置"
        }
    }
    open var getPhone = ::gen_getPhone_fn
    open fun gen_getPhone_fn(): String {
        return if (this.userInfo.phone != "") {
            this.userInfo.phone
        } else {
            "未设置"
        }
    }
    open var getNickname = ::gen_getNickname_fn
    open fun gen_getNickname_fn(): String {
        return if (this.userInfo.nickname != "") {
            this.userInfo.nickname
        } else {
            "未设置"
        }
    }
    open var loadUserInfo = ::gen_loadUserInfo_fn
    open fun gen_loadUserInfo_fn() {
        getUserProfile().then(fun(result){
            if (result.code === 200) {
                val data = result.data as UTSJSONObject
                if (data != null) {
                    val username = data.getString("username") ?: ""
                    val email = data.getString("email") ?: ""
                    val phone = data.getString("phone") ?: ""
                    val nickname = data.getString("nickname") ?: ""
                    this.userInfo = UserInfoType1(username = username, email = email, phone = phone, nickname = nickname)
                    uni_setStorageSync("user_info", JSON.stringify(this.userInfo))
                }
            } else {
                this.loadFromStorage()
            }
        }
        ).`catch`(fun(error){
            console.error("获取用户信息失败:", error, " at pages/user/settings.uvue:135")
            this.loadFromStorage()
        }
        )
    }
    open var loadFromStorage = ::gen_loadFromStorage_fn
    open fun gen_loadFromStorage_fn() {
        val userInfoStr = uni_getStorageSync("user_info")
        if (userInfoStr != null && userInfoStr != "") {
            try {
                val infoStr = userInfoStr as String
                val parsedInfo = UTSAndroid.consoleDebugError(JSON.parse(infoStr), " at pages/user/settings.uvue:145") as UTSJSONObject
                if (parsedInfo != null) {
                    val username = parsedInfo.getString("username") ?: ""
                    val email = parsedInfo.getString("email") ?: ""
                    val phone = parsedInfo.getString("phone") ?: ""
                    val nickname = parsedInfo.getString("nickname") ?: ""
                    this.userInfo = UserInfoType1(username = username, email = email, phone = phone, nickname = nickname)
                }
            }
             catch (e: Throwable) {
                console.error("Failed to parse user info:", e, " at pages/user/settings.uvue:160")
            }
        }
    }
    open var handleBack = ::gen_handleBack_fn
    open fun gen_handleBack_fn() {
        uni_navigateBack(null)
    }
    open var handleEditEmail = ::gen_handleEditEmail_fn
    open fun gen_handleEditEmail_fn() {
        this.editType = "email"
        this.editTitle = "编辑邮箱"
        this.editValue = if (this.userInfo.email != "") {
            this.userInfo.email
        } else {
            ""
        }
        this.editPlaceholder = "请输入邮箱地址"
        this.showEditModal = true
    }
    open var handleEditNickname = ::gen_handleEditNickname_fn
    open fun gen_handleEditNickname_fn() {
        this.editType = "nickname"
        this.editTitle = "编辑昵称"
        this.editValue = if (this.userInfo.nickname != "") {
            this.userInfo.nickname
        } else {
            ""
        }
        this.editPlaceholder = "请输入昵称"
        this.showEditModal = true
    }
    open var handleChangePassword = ::gen_handleChangePassword_fn
    open fun gen_handleChangePassword_fn() {
        uni_navigateTo(NavigateToOptions(url = "/pages/user/change-password"))
    }
    open var closeEditModal = ::gen_closeEditModal_fn
    open fun gen_closeEditModal_fn() {
        this.showEditModal = false
    }
    open var handleConfirmEdit = ::gen_handleConfirmEdit_fn
    open fun gen_handleConfirmEdit_fn() {
        val trimmedValue = this.editValue.trim()
        if (trimmedValue == "") {
            uni_showToast(ShowToastOptions(title = "内容不能为空", icon = "none"))
            return
        }
        if (this.editType === "email") {
            val emailRegex = UTSRegExp("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+\$", "")
            val isValid = emailRegex.test(this.editValue)
            if (isValid == false) {
                uni_showToast(ShowToastOptions(title = "邮箱格式不正确", icon = "none"))
                return
            }
        }
        var updateData: UTSJSONObject
        if (this.editType === "email") {
            updateData = let {
                object : UTSJSONObject() {
                    var email = it.editValue
                }
            }
        } else if (this.editType === "nickname") {
            updateData = let {
                object : UTSJSONObject() {
                    var nickname = it.editValue
                }
            }
        } else {
            updateData = UTSJSONObject()
        }
        updateUserProfile(updateData).then(fun(result){
            if (result.code === 200) {
                if (this.editType === "email") {
                    this.userInfo.email = this.editValue
                } else if (this.editType === "nickname") {
                    this.userInfo.nickname = this.editValue
                }
                uni_setStorageSync("user_info", JSON.stringify(this.userInfo))
                this.closeEditModal()
                uni_showToast(ShowToastOptions(title = "更新成功", icon = "success"))
            } else {
                val msg = if (result.message != "") {
                    result.message
                } else {
                    "更新失败"
                }
                uni_showToast(ShowToastOptions(title = msg, icon = "error"))
            }
        }
        ).`catch`(fun(error){
            console.error("更新用户信息失败:", error, " at pages/user/settings.uvue:249")
            uni_showToast(ShowToastOptions(title = "更新失败，请检查网络连接", icon = "error"))
        }
        )
    }
    open var handleLogout = ::gen_handleLogout_fn
    open fun gen_handleLogout_fn() {
        uni_showModal(ShowModalOptions(title = "退出登录", content = "确定要退出登录吗？", confirmText = "退出", cancelText = "取消", success = fun(res){
            if (res.confirm) {
                logout().then(fun(){}).`catch`(fun(error){
                    console.error("登出API调用失败:", error, " at pages/user/settings.uvue:265")
                }
                )
                uni_removeStorageSync("auth_token")
                uni_removeStorageSync("user_info")
                uni_navigateTo(NavigateToOptions(url = "/pages/login/login"))
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
                return utsMapOf("settings-container" to padStyleMapOf(utsMapOf("flex" to 1, "backgroundColor" to "#f8f8f8", "display" to "flex", "flexDirection" to "column")), "header" to padStyleMapOf(utsMapOf("display" to "flex", "justifyContent" to "center", "alignItems" to "center", "paddingTop" to 10, "paddingRight" to 16, "paddingBottom" to 10, "paddingLeft" to 16, "backgroundImage" to "linear-gradient(135deg, #0066CC 0%, #1890FF 100%)", "backgroundColor" to "rgba(0,0,0,0)", "color" to "#ffffff", "flexShrink" to 0, "position" to "relative")), "back-icon" to padStyleMapOf(utsMapOf("fontSize" to 28, "color" to "#ffffff", "position" to "absolute", "left" to 16, "top" to "50%", "width" to 44, "height" to 44, "display" to "flex", "alignItems" to "center", "justifyContent" to "center", "zIndex" to 10)), "header-title" to padStyleMapOf(utsMapOf("fontSize" to 16, "fontWeight" to "700", "color" to "#ffffff")), "content" to padStyleMapOf(utsMapOf("flex" to 1, "paddingTop" to 8, "paddingRight" to 0, "paddingBottom" to 8, "paddingLeft" to 0)), "section" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "marginBottom" to 8, "overflow" to "hidden")), "section-title" to padStyleMapOf(utsMapOf("fontSize" to 12, "fontWeight" to "700", "color" to "#999999", "paddingTop" to 10, "paddingRight" to 16, "paddingBottom" to 10, "paddingLeft" to 16, "backgroundColor" to "#f8f8f8")), "setting-item" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "paddingTop" to 12, "paddingRight" to 16, "paddingBottom" to 12, "paddingLeft" to 16, "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "#f0f0f0")), "setting-label" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#333333", "fontWeight" to "400", "flexShrink" to 0, "width" to 60)), "setting-value" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999", "flex" to 1, "textAlign" to "right", "marginRight" to 8)), "setting-arrow" to padStyleMapOf(utsMapOf("fontSize" to 18, "color" to "#cccccc", "flexShrink" to 0)), "logout-section" to padStyleMapOf(utsMapOf("paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "flexShrink" to 0)), "logout-btn" to padStyleMapOf(utsMapOf("width" to "100%", "height" to 44, "backgroundColor" to "#ffffff", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#e74c3c", "borderRightColor" to "#e74c3c", "borderBottomColor" to "#e74c3c", "borderLeftColor" to "#e74c3c", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "color" to "#e74c3c", "fontSize" to 15, "fontWeight" to "bold", "display" to "flex", "justifyContent" to "center", "alignItems" to "center")), "modal-overlay" to padStyleMapOf(utsMapOf("position" to "fixed", "top" to 0, "left" to 0, "right" to 0, "bottom" to 0, "backgroundColor" to "rgba(0,0,0,0.5)", "display" to "flex", "alignItems" to "center", "justifyContent" to "center", "zIndex" to 1000)), "modal-content" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "paddingTop" to 20, "paddingRight" to 20, "paddingBottom" to 20, "paddingLeft" to 20, "width" to "80%", "maxWidth" to 300)), "modal-title" to padStyleMapOf(utsMapOf("fontSize" to 16, "fontWeight" to "700", "color" to "#333333", "marginBottom" to 12, "display" to "flex")), "modal-input" to padStyleMapOf(utsMapOf("width" to "100%", "height" to 40, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#e0e0e0", "borderRightColor" to "#e0e0e0", "borderBottomColor" to "#e0e0e0", "borderLeftColor" to "#e0e0e0", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingTop" to 0, "paddingRight" to 12, "paddingBottom" to 0, "paddingLeft" to 12, "fontSize" to 14, "color" to "#333333", "marginBottom" to 16, "boxSizing" to "border-box")), "modal-actions" to padStyleMapOf(utsMapOf("display" to "flex", "marginRight" to 12)), "modal-btn" to padStyleMapOf(utsMapOf("flex" to 1, "height" to 40, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "fontSize" to 14, "fontWeight" to "400", "borderTopWidth" to "medium", "borderRightWidth" to "medium", "borderBottomWidth" to "medium", "borderLeftWidth" to "medium", "borderTopStyle" to "none", "borderRightStyle" to "none", "borderBottomStyle" to "none", "borderLeftStyle" to "none", "borderTopColor" to "#000000", "borderRightColor" to "#000000", "borderBottomColor" to "#000000", "borderLeftColor" to "#000000")), "cancel-btn" to padStyleMapOf(utsMapOf("backgroundColor" to "#f0f0f0", "color" to "#333333")), "confirm-btn" to padStyleMapOf(utsMapOf("backgroundImage" to "linear-gradient(135deg, #0066CC 0%, #1890FF 100%)", "backgroundColor" to "rgba(0,0,0,0)", "color" to "#ffffff")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = utsMapOf()
        var emits: Map<String, Any?> = utsMapOf()
        var props = normalizePropsOptions(utsMapOf())
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf()
        var components: Map<String, CreateVueComponent> = utsMapOf()
    }
}
