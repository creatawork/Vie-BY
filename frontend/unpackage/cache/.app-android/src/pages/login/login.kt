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
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.reLaunch as uni_reLaunch
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesLoginLogin : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return createElementVNode("view", utsMapOf("class" to "page-container"), utsArrayOf(
            createElementVNode("view", utsMapOf("class" to "main-content"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "header"), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "title"), "欢迎回来"),
                    createElementVNode("text", utsMapOf("class" to "subtitle"), "登录您的账号以继续")
                )),
                createElementVNode("view", utsMapOf("class" to "form-area"), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                        "input-wrap",
                        utsMapOf("input-wrap-error" to (_ctx.errors.username !== ""))
                    ))), utsArrayOf(
                        createElementVNode("input", utsMapOf("modelValue" to _ctx.form.username, "onInput" to fun(`$event`: InputEvent){
                            _ctx.form.username = `$event`.detail.value
                        }
                        , "type" to "text", "placeholder" to "用户名或手机号", "placeholder-class" to "placeholder", "class" to "input-field", "onBlur" to _ctx.validateUsername), null, 40, utsArrayOf(
                            "modelValue",
                            "onInput",
                            "onBlur"
                        ))
                    ), 2),
                    if (_ctx.errors.username !== "") {
                        createElementVNode("text", utsMapOf("key" to 0, "class" to "error-msg"), toDisplayString(_ctx.errors.username), 1)
                    } else {
                        createCommentVNode("v-if", true)
                    }
                    ,
                    createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                        "input-wrap",
                        utsMapOf("input-wrap-error" to (_ctx.errors.password !== ""))
                    ))), utsArrayOf(
                        createElementVNode("input", utsMapOf("modelValue" to _ctx.form.password, "onInput" to fun(`$event`: InputEvent){
                            _ctx.form.password = `$event`.detail.value
                        }
                        , "type" to if (_ctx.showPassword) {
                            "text"
                        } else {
                            "password"
                        }
                        , "placeholder" to "密码", "placeholder-class" to "placeholder", "class" to "input-field", "onBlur" to _ctx.validatePassword), null, 40, utsArrayOf(
                            "modelValue",
                            "onInput",
                            "type",
                            "onBlur"
                        )),
                        createElementVNode("view", utsMapOf("class" to "action-btn", "onClick" to fun(){
                            _ctx.showPassword = !_ctx.showPassword
                        }
                        ), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "action-text"), toDisplayString(if (_ctx.showPassword) {
                                "隐藏"
                            } else {
                                "显示"
                            }
                            ), 1)
                        ), 8, utsArrayOf(
                            "onClick"
                        ))
                    ), 2),
                    if (_ctx.errors.password !== "") {
                        createElementVNode("text", utsMapOf("key" to 1, "class" to "error-msg"), toDisplayString(_ctx.errors.password), 1)
                    } else {
                        createCommentVNode("v-if", true)
                    }
                    ,
                    createElementVNode("view", utsMapOf("class" to "options-row"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "link-left", "onClick" to withModifiers(_ctx.handleRegister, utsArrayOf(
                            "stop"
                        ))), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "text-link"), "新用户注册")
                        ), 8, utsArrayOf(
                            "onClick"
                        )),
                        createElementVNode("view", utsMapOf("class" to "link-right", "onClick" to withModifiers(_ctx.handleForgotPassword, utsArrayOf(
                            "stop"
                        ))), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "text-gray"), "忘记密码？")
                        ), 8, utsArrayOf(
                            "onClick"
                        ))
                    )),
                    createElementVNode("button", utsMapOf("class" to normalizeClass(utsArrayOf(
                        "primary-btn",
                        utsMapOf("primary-btn-disabled" to _ctx.isLoading)
                    )), "loading" to _ctx.isLoading, "onClick" to withModifiers(_ctx.handleLogin, utsArrayOf(
                        "stop"
                    )), "disabled" to _ctx.isLoading), toDisplayString(if (_ctx.isLoading) {
                        "登录中..."
                    } else {
                        "登 录"
                    }
                    ), 11, utsArrayOf(
                        "loading",
                        "onClick",
                        "disabled"
                    ))
                ))
            )),
            createElementVNode("view", utsMapOf("class" to "bottom-brand"), utsArrayOf(
                createElementVNode("image", utsMapOf("src" to "/static/logo.png", "mode" to "aspectFit", "class" to "brand-logo")),
                createElementVNode("text", utsMapOf("class" to "brand-text"), "鲜农优选")
            ))
        ))
    }
    open var form: LoginForm by `$data`
    open var errors: LoginErrors by `$data`
    open var showPassword: Boolean by `$data`
    open var isLoading: Boolean by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return utsMapOf("form" to LoginForm(username = "", password = ""), "errors" to LoginErrors(username = "", password = ""), "showPassword" to false, "isLoading" to false)
    }
    open var validateUsername = ::gen_validateUsername_fn
    open fun gen_validateUsername_fn() {
        val username = this.form.username.trim()
        if (username == "") {
            this.errors.username = "请输入用户名或手机号"
        } else {
            this.errors.username = ""
        }
    }
    open var validatePassword = ::gen_validatePassword_fn
    open fun gen_validatePassword_fn() {
        val password = this.form.password
        if (password == "") {
            this.errors.password = "请输入密码"
        } else if (password.length < 6) {
            this.errors.password = "密码至少6个字符"
        } else {
            this.errors.password = ""
        }
    }
    open var handleLogin = ::gen_handleLogin_fn
    open fun gen_handleLogin_fn() {
        if (this.isLoading) {
            return
        }
        this.validateUsername()
        this.validatePassword()
        val hasError = this.errors.username != "" || this.errors.password != ""
        if (hasError) {
            return
        }
        this.isLoading = true
        login(this.form.username, this.form.password).then(fun(response){
            if (response.data != null) {
                val resData = response.data as UTSJSONObject
                val token = resData.getString("token")
                if (token != null && token != "") {
                    uni_setStorageSync("auth_token", token)
                    val userId = resData.getNumber("userId")
                    val username = resData.getString("username")
                    val nickname = resData.getString("nickname")
                    val avatar = resData.getString("avatar")
                    val roleCodes = resData.getAny("roleCodes")
                    val userInfo: UTSJSONObject = UTSJSONObject(Map<String, Any?>(utsArrayOf(
                        utsArrayOf(
                            "__\$originalPosition",
                            UTSSourceMapPosition("userInfo", "pages/login/login.uvue", 148, 13)
                        ),
                        utsArrayOf(
                            "id",
                            if (userId != null) {
                                userId.toInt()
                            } else {
                                0
                            }
                        ),
                        utsArrayOf(
                            "username",
                            if (username != null) {
                                username
                            } else {
                                ""
                            }
                        ),
                        utsArrayOf(
                            "nickname",
                            if (nickname != null) {
                                nickname
                            } else {
                                ""
                            }
                        ),
                        utsArrayOf(
                            "avatar",
                            if (avatar != null) {
                                avatar
                            } else {
                                ""
                            }
                        ),
                        utsArrayOf(
                            "roleCodes",
                            roleCodes
                        )
                    )))
                    uni_setStorageSync("user_info", JSON.stringify(userInfo))
                    this.isLoading = false
                    uni_showToast(ShowToastOptions(title = "登录成功", icon = "success"))
                    setTimeout(fun(){
                        var targetUrl = "/pages/index/index"
                        var isAdmin = false
                        if (roleCodes != null && UTSArray.isArray(roleCodes)) {
                            val roles = roleCodes as UTSArray<String>
                            if (roles.includes("ROLE_ADMIN") || roles.includes("ADMIN")) {
                                isAdmin = true
                            }
                        }
                        if (isAdmin) {
                            targetUrl = "/pages/seller/admin-index"
                        }
                        uni_reLaunch(ReLaunchOptions(url = targetUrl, fail = fun(err){
                            console.error("跳转失败:", err, " at pages/login/login.uvue:185")
                            uni_navigateTo(NavigateToOptions(url = targetUrl, fail = fun(err2){
                                console.error("navigateTo 也失败:", err2, " at pages/login/login.uvue:190")
                            }))
                        }))
                    }, 500)
                } else {
                    this.isLoading = false
                    uni_showToast(ShowToastOptions(title = "登录失败", icon = "none"))
                }
            } else {
                this.isLoading = false
                uni_showToast(ShowToastOptions(title = "登录失败", icon = "none"))
            }
        }
        ).`catch`(fun(error){
            this.isLoading = false
            val errMsg = if (error != null && (error as UTSError).message != null && (error as UTSError).message != "") {
                (error as UTSError).message
            } else {
                "登录失败，请稍后重试"
            }
            uni_showToast(ShowToastOptions(title = errMsg, icon = "none"))
        }
        )
    }
    open var handleForgotPassword = ::gen_handleForgotPassword_fn
    open fun gen_handleForgotPassword_fn() {
        uni_showToast(ShowToastOptions(title = "功能开发中", icon = "none"))
    }
    open var handleRegister = ::gen_handleRegister_fn
    open fun gen_handleRegister_fn() {
        uni_navigateTo(NavigateToOptions(url = "/pages/register/register"))
    }
    open var handleQuickLogin = ::gen_handleQuickLogin_fn
    open fun gen_handleQuickLogin_fn(type: String) {
        uni_showToast(ShowToastOptions(title = "功能开发中", icon = "none"))
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
                return utsMapOf("page-container" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "column", "flex" to 1, "backgroundColor" to "#ffffff", "paddingTop" to 0, "paddingRight" to "60rpx", "paddingBottom" to 0, "paddingLeft" to "60rpx", "position" to "relative")), "main-content" to padStyleMapOf(utsMapOf("flex" to 1, "display" to "flex", "flexDirection" to "column", "justifyContent" to "center", "paddingBottom" to "200rpx")), "header" to padStyleMapOf(utsMapOf("marginBottom" to "80rpx")), "title" to padStyleMapOf(utsMapOf("fontSize" to "56rpx", "fontWeight" to "700", "color" to "#111111", "marginBottom" to "16rpx", "letterSpacing" to "2rpx")), "subtitle" to padStyleMapOf(utsMapOf("fontSize" to "28rpx", "color" to "#888888")), "form-area" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "column")), "input-wrap" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "backgroundColor" to "#f7f8fa", "height" to "110rpx", "borderTopLeftRadius" to "24rpx", "borderTopRightRadius" to "24rpx", "borderBottomRightRadius" to "24rpx", "borderBottomLeftRadius" to "24rpx", "paddingTop" to 0, "paddingRight" to "32rpx", "paddingBottom" to 0, "paddingLeft" to "32rpx", "marginTop" to "32rpx", "borderTopWidth" to "2rpx", "borderRightWidth" to "2rpx", "borderBottomWidth" to "2rpx", "borderLeftWidth" to "2rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "rgba(0,0,0,0)", "borderRightColor" to "rgba(0,0,0,0)", "borderBottomColor" to "rgba(0,0,0,0)", "borderLeftColor" to "rgba(0,0,0,0)")), "input-wrap-error" to padStyleMapOf(utsMapOf("borderTopColor" to "#ff4d4f", "borderRightColor" to "#ff4d4f", "borderBottomColor" to "#ff4d4f", "borderLeftColor" to "#ff4d4f", "backgroundColor" to "#fff0f0")), "input-field" to padStyleMapOf(utsMapOf("flex" to 1, "height" to "100%", "fontSize" to "32rpx", "color" to "#333333", "backgroundImage" to "none", "backgroundColor" to "rgba(0,0,0,0)")), "placeholder" to padStyleMapOf(utsMapOf("color" to "#bbbbbb", "fontSize" to "32rpx")), "action-btn" to padStyleMapOf(utsMapOf("paddingTop" to 0, "paddingRight" to "10rpx", "paddingBottom" to 0, "paddingLeft" to "10rpx", "height" to "100%", "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "action-text" to padStyleMapOf(utsMapOf("fontSize" to "28rpx", "color" to "#0066CC", "fontWeight" to "bold")), "error-msg" to padStyleMapOf(utsMapOf("fontSize" to "24rpx", "color" to "#ff4d4f", "marginTop" to "12rpx", "paddingLeft" to "12rpx")), "options-row" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "marginTop" to "16rpx", "marginBottom" to "24rpx")), "link-left" to padStyleMapOf(utsMapOf("paddingTop" to "30rpx", "paddingRight" to "40rpx", "paddingBottom" to "30rpx", "paddingLeft" to 0, "zIndex" to 10)), "link-right" to padStyleMapOf(utsMapOf("paddingTop" to "30rpx", "paddingRight" to 0, "paddingBottom" to "30rpx", "paddingLeft" to "40rpx", "zIndex" to 10)), "text-link" to padStyleMapOf(utsMapOf("fontSize" to "28rpx", "color" to "#0066CC", "fontWeight" to "bold")), "text-gray" to padStyleMapOf(utsMapOf("fontSize" to "28rpx", "color" to "#999999")), "primary-btn" to padStyleMapOf(utsMapOf("width" to "100%", "height" to "110rpx", "backgroundColor" to "#0066CC", "color" to "#ffffff", "borderTopLeftRadius" to "24rpx", "borderTopRightRadius" to "24rpx", "borderBottomRightRadius" to "24rpx", "borderBottomLeftRadius" to "24rpx", "fontSize" to "34rpx", "fontWeight" to "bold", "display" to "flex", "alignItems" to "center", "justifyContent" to "center", "borderTopWidth" to "medium", "borderRightWidth" to "medium", "borderBottomWidth" to "medium", "borderLeftWidth" to "medium", "borderTopStyle" to "none", "borderRightStyle" to "none", "borderBottomStyle" to "none", "borderLeftStyle" to "none", "borderTopColor" to "#000000", "borderRightColor" to "#000000", "borderBottomColor" to "#000000", "borderLeftColor" to "#000000", "marginTop" to "16rpx", "position" to "relative", "zIndex" to 1)), "primary-btn-disabled" to padStyleMapOf(utsMapOf("backgroundColor" to "#a0c6ed", "boxShadow" to "none")), "bottom-brand" to padStyleMapOf(utsMapOf("position" to "absolute", "bottom" to "80rpx", "left" to 0, "right" to 0, "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center", "opacity" to 0.6)), "brand-logo" to padStyleMapOf(utsMapOf("width" to "40rpx", "height" to "40rpx", "marginRight" to "12rpx")), "brand-text" to padStyleMapOf(utsMapOf("fontSize" to "26rpx", "color" to "#888888", "fontWeight" to "bold", "letterSpacing" to "2rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = utsMapOf()
        var emits: Map<String, Any?> = utsMapOf()
        var props = normalizePropsOptions(utsMapOf())
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf()
        var components: Map<String, CreateVueComponent> = utsMapOf()
    }
}
