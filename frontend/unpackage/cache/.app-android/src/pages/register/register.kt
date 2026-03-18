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
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesRegisterRegister : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return createElementVNode("view", utsMapOf("class" to "page-container"), utsArrayOf(
            createElementVNode("view", utsMapOf("class" to "header"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "back-btn", "onClick" to _ctx.handleBack), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "back-icon"), "←")
                ), 8, utsArrayOf(
                    "onClick"
                )),
                createElementVNode("text", utsMapOf("class" to "title"), "创建账号"),
                createElementVNode("text", utsMapOf("class" to "subtitle"), "填写以下信息完成注册")
            )),
            createElementVNode("scroll-view", utsMapOf("class" to "main-scroll", "scroll-y" to "true", "show-scrollbar" to false), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "form-area"), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                        "input-wrap",
                        utsMapOf("input-wrap-error" to (_ctx.errors.username != ""))
                    ))), utsArrayOf(
                        createElementVNode("input", utsMapOf("modelValue" to _ctx.form.username, "onInput" to fun(`$event`: InputEvent){
                            _ctx.form.username = `$event`.detail.value
                        }
                        , "type" to "text", "placeholder" to "用户名", "placeholder-class" to "placeholder", "class" to "input-field", "onBlur" to _ctx.validateUsername), null, 40, utsArrayOf(
                            "modelValue",
                            "onInput",
                            "onBlur"
                        ))
                    ), 2),
                    if (_ctx.errors.username != "") {
                        createElementVNode("text", utsMapOf("key" to 0, "class" to "error-msg"), toDisplayString(_ctx.errors.username), 1)
                    } else {
                        createCommentVNode("v-if", true)
                    }
                    ,
                    createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                        "input-wrap",
                        utsMapOf("input-wrap-error" to (_ctx.errors.email != ""))
                    ))), utsArrayOf(
                        createElementVNode("input", utsMapOf("modelValue" to _ctx.form.email, "onInput" to fun(`$event`: InputEvent){
                            _ctx.form.email = `$event`.detail.value
                        }
                        , "type" to "text", "placeholder" to "邮箱地址", "placeholder-class" to "placeholder", "class" to "input-field", "onBlur" to _ctx.validateEmail), null, 40, utsArrayOf(
                            "modelValue",
                            "onInput",
                            "onBlur"
                        ))
                    ), 2),
                    if (_ctx.errors.email != "") {
                        createElementVNode("text", utsMapOf("key" to 1, "class" to "error-msg"), toDisplayString(_ctx.errors.email), 1)
                    } else {
                        createCommentVNode("v-if", true)
                    }
                    ,
                    createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                        "input-wrap",
                        utsMapOf("input-wrap-error" to (_ctx.errors.phone != ""))
                    ))), utsArrayOf(
                        createElementVNode("input", utsMapOf("modelValue" to _ctx.form.phone, "onInput" to fun(`$event`: InputEvent){
                            _ctx.form.phone = `$event`.detail.value
                        }
                        , "type" to "text", "maxlength" to "11", "placeholder" to "手机号码", "placeholder-class" to "placeholder", "class" to "input-field", "onBlur" to _ctx.validatePhone), null, 40, utsArrayOf(
                            "modelValue",
                            "onInput",
                            "onBlur"
                        ))
                    ), 2),
                    if (_ctx.errors.phone != "") {
                        createElementVNode("text", utsMapOf("key" to 2, "class" to "error-msg"), toDisplayString(_ctx.errors.phone), 1)
                    } else {
                        createCommentVNode("v-if", true)
                    }
                    ,
                    createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                        "input-wrap",
                        utsMapOf("input-wrap-error" to (_ctx.errors.password != ""))
                    ))), utsArrayOf(
                        createElementVNode("input", utsMapOf("modelValue" to _ctx.form.password, "onInput" to fun(`$event`: InputEvent){
                            _ctx.form.password = `$event`.detail.value
                        }
                        , "type" to if (_ctx.showPassword) {
                            "text"
                        } else {
                            "password"
                        }
                        , "placeholder" to "密码 (至少6位)", "placeholder-class" to "placeholder", "class" to "input-field", "onBlur" to _ctx.validatePassword), null, 40, utsArrayOf(
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
                    if (_ctx.errors.password != "") {
                        createElementVNode("text", utsMapOf("key" to 3, "class" to "error-msg"), toDisplayString(_ctx.errors.password), 1)
                    } else {
                        createCommentVNode("v-if", true)
                    }
                    ,
                    if (_ctx.form.password != "") {
                        createElementVNode("view", utsMapOf("key" to 4, "class" to "strength-row"), utsArrayOf(
                            createElementVNode("view", utsMapOf("class" to "strength-bars"), utsArrayOf(
                                createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                                    "bar",
                                    utsMapOf("bar-active-weak" to (_ctx.strengthLevel >= 1))
                                ))), null, 2),
                                createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                                    "bar",
                                    utsMapOf("bar-active-medium" to (_ctx.strengthLevel >= 2))
                                ))), null, 2),
                                createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                                    "bar",
                                    utsMapOf("bar-active-strong" to (_ctx.strengthLevel >= 3))
                                ))), null, 2)
                            )),
                            createElementVNode("text", utsMapOf("class" to "strength-label"), toDisplayString(_ctx.strengthText), 1)
                        ))
                    } else {
                        createCommentVNode("v-if", true)
                    }
                    ,
                    createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                        "input-wrap",
                        utsMapOf("input-wrap-error" to (_ctx.errors.confirmPassword != ""))
                    ))), utsArrayOf(
                        createElementVNode("input", utsMapOf("modelValue" to _ctx.form.confirmPassword, "onInput" to fun(`$event`: InputEvent){
                            _ctx.form.confirmPassword = `$event`.detail.value
                        }
                        , "type" to if (_ctx.showConfirmPassword) {
                            "text"
                        } else {
                            "password"
                        }
                        , "placeholder" to "确认密码", "placeholder-class" to "placeholder", "class" to "input-field", "onBlur" to _ctx.validateConfirmPassword), null, 40, utsArrayOf(
                            "modelValue",
                            "onInput",
                            "type",
                            "onBlur"
                        )),
                        createElementVNode("view", utsMapOf("class" to "action-btn", "onClick" to fun(){
                            _ctx.showConfirmPassword = !_ctx.showConfirmPassword
                        }
                        ), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "action-text"), toDisplayString(if (_ctx.showConfirmPassword) {
                                "隐藏"
                            } else {
                                "显示"
                            }
                            ), 1)
                        ), 8, utsArrayOf(
                            "onClick"
                        ))
                    ), 2),
                    if (_ctx.errors.confirmPassword != "") {
                        createElementVNode("text", utsMapOf("key" to 5, "class" to "error-msg"), toDisplayString(_ctx.errors.confirmPassword), 1)
                    } else {
                        createCommentVNode("v-if", true)
                    }
                    ,
                    createElementVNode("view", utsMapOf("class" to "agreement-row"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                            "checkbox",
                            utsMapOf("checkbox-checked" to _ctx.form.agreeTerms)
                        )), "onClick" to fun(){
                            _ctx.form.agreeTerms = !_ctx.form.agreeTerms
                        }
                        ), utsArrayOf(
                            if (isTrue(_ctx.form.agreeTerms)) {
                                createElementVNode("text", utsMapOf("key" to 0, "class" to "check-mark"), "✓")
                            } else {
                                createCommentVNode("v-if", true)
                            }
                        ), 10, utsArrayOf(
                            "onClick"
                        )),
                        createElementVNode("view", utsMapOf("class" to "agreement-text"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "text-gray"), "我已阅读并同意"),
                            createElementVNode("text", utsMapOf("class" to "text-link", "onClick" to _ctx.handleViewTerms), "《用户协议》", 8, utsArrayOf(
                                "onClick"
                            )),
                            createElementVNode("text", utsMapOf("class" to "text-gray"), "和"),
                            createElementVNode("text", utsMapOf("class" to "text-link", "onClick" to _ctx.handleViewPrivacy), "《隐私政策》", 8, utsArrayOf(
                                "onClick"
                            ))
                        ))
                    )),
                    if (_ctx.errors.agreement != "") {
                        createElementVNode("text", utsMapOf("key" to 6, "class" to "error-msg error-center"), toDisplayString(_ctx.errors.agreement), 1)
                    } else {
                        createCommentVNode("v-if", true)
                    }
                    ,
                    createElementVNode("button", utsMapOf("class" to normalizeClass(utsArrayOf(
                        "primary-btn",
                        utsMapOf("primary-btn-disabled" to _ctx.isLoading)
                    )), "loading" to _ctx.isLoading, "onClick" to _ctx.handleRegister, "disabled" to _ctx.isLoading), toDisplayString(if (_ctx.isLoading) {
                        "注册中..."
                    } else {
                        "注 册"
                    }
                    ), 11, utsArrayOf(
                        "loading",
                        "onClick",
                        "disabled"
                    )),
                    createElementVNode("view", utsMapOf("class" to "footer-link"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "text-gray"), "已有账号？"),
                        createElementVNode("view", utsMapOf("class" to "link-wrap", "onClick" to withModifiers(_ctx.handleLogin, utsArrayOf(
                            "stop"
                        ))), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "text-link bold"), "直接登录")
                        ), 8, utsArrayOf(
                            "onClick"
                        ))
                    ))
                ))
            ))
        ))
    }
    open var form: RegisterForm by `$data`
    open var errors: RegisterErrors by `$data`
    open var showPassword: Boolean by `$data`
    open var showConfirmPassword: Boolean by `$data`
    open var isLoading: Boolean by `$data`
    open var strengthLevel: Number by `$data`
    open var strengthText: String by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return utsMapOf("form" to RegisterForm(username = "", email = "", phone = "", password = "", confirmPassword = "", agreeTerms = false), "errors" to RegisterErrors(username = "", email = "", phone = "", password = "", confirmPassword = "", agreement = ""), "showPassword" to false, "showConfirmPassword" to false, "isLoading" to false, "strengthLevel" to computed<Number>(fun(): Number {
            val pwd = this.form.password
            if (pwd == "") {
                return 0
            }
            var score: Number = 0
            if (pwd.length >= 6) {
                score++
            }
            if (pwd.length >= 8) {
                score++
            }
            if (UTSRegExp("[a-z]", "").test(pwd) && UTSRegExp("[A-Z]", "").test(pwd)) {
                score++
            }
            if (UTSRegExp("[0-9]", "").test(pwd)) {
                score++
            }
            if (UTSRegExp("[^a-zA-Z0-9]", "").test(pwd)) {
                score++
            }
            if (score <= 2) {
                return 1
            }
            if (score <= 4) {
                return 2
            }
            return 3
        }
        ), "strengthText" to computed<String>(fun(): String {
            val levels = utsArrayOf(
                "",
                "弱",
                "中",
                "强"
            )
            return levels[this.strengthLevel]
        }
        ))
    }
    open var validateUsername = ::gen_validateUsername_fn
    open fun gen_validateUsername_fn() {
        val username = this.form.username.trim()
        if (username == "") {
            this.errors.username = "请输入用户名"
        } else if (username.length < 3) {
            this.errors.username = "用户名至少3个字符"
        } else if (username.length > 20) {
            this.errors.username = "用户名最多20个字符"
        } else if (!UTSRegExp("^[a-zA-Z0-9_]+\$", "").test(username)) {
            this.errors.username = "用户名只能包含字母、数字和下划线"
        } else {
            this.errors.username = ""
        }
    }
    open var validateEmail = ::gen_validateEmail_fn
    open fun gen_validateEmail_fn() {
        val email = this.form.email.trim()
        if (email == "") {
            this.errors.email = "请输入邮箱"
        } else if (!UTSRegExp("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$", "").test(email)) {
            this.errors.email = "请输入有效的邮箱地址"
        } else {
            this.errors.email = ""
        }
    }
    open var validatePhone = ::gen_validatePhone_fn
    open fun gen_validatePhone_fn() {
        this.form.phone = this.form.phone.replace(UTSRegExp("[^0-9]", "g"), "").slice(0, 11)
        val phone = this.form.phone.trim()
        if (phone == "") {
            this.errors.phone = "请输入手机号"
        } else if (!UTSRegExp("^1[3-9][0-9]{9}\$", "").test(phone)) {
            this.errors.phone = "请输入有效的手机号"
        } else {
            this.errors.phone = ""
        }
    }
    open var validatePassword = ::gen_validatePassword_fn
    open fun gen_validatePassword_fn() {
        val password = this.form.password.trim()
        if (password == "") {
            this.errors.password = "请输入密码"
        } else if (password.length < 6) {
            this.errors.password = "密码至少6个字符"
        } else {
            this.errors.password = ""
        }
        this.form.password = password
        if (this.form.confirmPassword != "") {
            this.validateConfirmPassword()
        }
    }
    open var validateConfirmPassword = ::gen_validateConfirmPassword_fn
    open fun gen_validateConfirmPassword_fn() {
        val confirmPassword = this.form.confirmPassword.trim()
        val password = this.form.password.trim()
        if (confirmPassword == "") {
            this.errors.confirmPassword = "请确认密码"
        } else if (confirmPassword != password) {
            this.errors.confirmPassword = "两次输入的密码不一致"
        } else {
            this.errors.confirmPassword = ""
        }
        this.form.confirmPassword = confirmPassword
        this.form.password = password
    }
    open var validateAgreement = ::gen_validateAgreement_fn
    open fun gen_validateAgreement_fn() {
        if (this.form.agreeTerms == false) {
            this.errors.agreement = "请同意用户协议和隐私政策"
        } else {
            this.errors.agreement = ""
        }
    }
    open var handleRegister = ::gen_handleRegister_fn
    open fun gen_handleRegister_fn() {
        if (this.isLoading) {
            return
        }
        this.form.username = this.form.username.trim()
        this.form.email = this.form.email.trim()
        this.form.phone = this.form.phone.trim()
        this.form.password = this.form.password.trim()
        this.form.confirmPassword = this.form.confirmPassword.trim()
        this.validateUsername()
        this.validateEmail()
        this.validatePhone()
        this.validatePassword()
        this.validateConfirmPassword()
        this.validateAgreement()
        val hasError = this.errors.username != "" || this.errors.email != "" || this.errors.phone != "" || this.errors.password != "" || this.errors.confirmPassword != "" || this.errors.agreement != ""
        if (hasError) {
            return
        }
        this.isLoading = true
        register(this.form.username, this.form.email, this.form.phone, this.form.password, this.form.username, this.form.confirmPassword).then(fun(response){
            this.isLoading = false
            uni_showToast(ShowToastOptions(title = "注册成功", icon = "success"))
            setTimeout(fun(){
                uni_navigateTo(NavigateToOptions(url = "/pages/login/login"))
            }
            , 500)
        }
        ).`catch`(fun(error){
            this.isLoading = false
            val errMsg = if (error != null && (error as UTSError).message != null && (error as UTSError).message != "") {
                (error as UTSError).message
            } else {
                "注册失败，请稍后重试"
            }
            uni_showToast(ShowToastOptions(title = errMsg, icon = "none"))
        }
        )
    }
    open var handleViewTerms = ::gen_handleViewTerms_fn
    open fun gen_handleViewTerms_fn() {
        uni_showToast(ShowToastOptions(title = "用户协议功能开发中", icon = "none"))
    }
    open var handleViewPrivacy = ::gen_handleViewPrivacy_fn
    open fun gen_handleViewPrivacy_fn() {
        uni_showToast(ShowToastOptions(title = "隐私政策功能开发中", icon = "none"))
    }
    open var handleLogin = ::gen_handleLogin_fn
    open fun gen_handleLogin_fn() {
        uni_navigateTo(NavigateToOptions(url = "/pages/login/login"))
    }
    open var handleBack = ::gen_handleBack_fn
    open fun gen_handleBack_fn() {
        uni_navigateBack(null)
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
                return utsMapOf("page-container" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "column", "flex" to 1, "backgroundColor" to "#ffffff")), "header" to padStyleMapOf(utsMapOf("paddingTop" to "80rpx", "paddingRight" to "60rpx", "paddingBottom" to "40rpx", "paddingLeft" to "60rpx", "backgroundColor" to "#ffffff", "zIndex" to 10)), "back-btn" to padStyleMapOf(utsMapOf("width" to "80rpx", "height" to "80rpx", "display" to "flex", "alignItems" to "center", "marginLeft" to "-20rpx", "marginBottom" to "20rpx")), "back-icon" to padStyleMapOf(utsMapOf("fontSize" to "48rpx", "color" to "#333333", "fontWeight" to "normal")), "title" to padStyleMapOf(utsMapOf("fontSize" to "56rpx", "fontWeight" to "700", "color" to "#111111", "marginBottom" to "16rpx", "letterSpacing" to "2rpx")), "subtitle" to padStyleMapOf(utsMapOf("fontSize" to "28rpx", "color" to "#888888")), "main-scroll" to padStyleMapOf(utsMapOf("flex" to 1, "height" to 0)), "form-area" to padStyleMapOf(utsMapOf("paddingTop" to 0, "paddingRight" to "60rpx", "paddingBottom" to "80rpx", "paddingLeft" to "60rpx", "display" to "flex", "flexDirection" to "column")), "input-wrap" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "backgroundColor" to "#f7f8fa", "height" to "110rpx", "borderTopLeftRadius" to "24rpx", "borderTopRightRadius" to "24rpx", "borderBottomRightRadius" to "24rpx", "borderBottomLeftRadius" to "24rpx", "paddingTop" to 0, "paddingRight" to "32rpx", "paddingBottom" to 0, "paddingLeft" to "32rpx", "marginTop" to "32rpx", "borderTopWidth" to "2rpx", "borderRightWidth" to "2rpx", "borderBottomWidth" to "2rpx", "borderLeftWidth" to "2rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "rgba(0,0,0,0)", "borderRightColor" to "rgba(0,0,0,0)", "borderBottomColor" to "rgba(0,0,0,0)", "borderLeftColor" to "rgba(0,0,0,0)")), "input-wrap-error" to padStyleMapOf(utsMapOf("borderTopColor" to "#ff4d4f", "borderRightColor" to "#ff4d4f", "borderBottomColor" to "#ff4d4f", "borderLeftColor" to "#ff4d4f", "backgroundColor" to "#fff0f0")), "input-field" to padStyleMapOf(utsMapOf("flex" to 1, "height" to "100%", "fontSize" to "32rpx", "color" to "#333333", "backgroundImage" to "none", "backgroundColor" to "rgba(0,0,0,0)")), "placeholder" to padStyleMapOf(utsMapOf("color" to "#bbbbbb", "fontSize" to "32rpx")), "action-btn" to padStyleMapOf(utsMapOf("paddingTop" to 0, "paddingRight" to "10rpx", "paddingBottom" to 0, "paddingLeft" to "10rpx", "height" to "100%", "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "action-text" to padStyleMapOf(utsMapOf("fontSize" to "28rpx", "color" to "#0066CC", "fontWeight" to "bold")), "error-msg" to padStyleMapOf(utsMapOf("fontSize" to "24rpx", "color" to "#ff4d4f", "marginTop" to "12rpx", "paddingLeft" to "12rpx")), "error-center" to padStyleMapOf(utsMapOf("textAlign" to "center", "paddingLeft" to 0)), "strength-row" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "marginTop" to "16rpx", "paddingTop" to 0, "paddingRight" to "12rpx", "paddingBottom" to 0, "paddingLeft" to "12rpx")), "strength-bars" to padStyleMapOf(utsMapOf("flex" to 1, "display" to "flex", "flexDirection" to "row", "height" to "8rpx", "marginRight" to "24rpx")), "bar" to padStyleMapOf(utsMapOf("flex" to 1, "backgroundColor" to "#f0f0f0", "borderTopLeftRadius" to "4rpx", "borderTopRightRadius" to "4rpx", "borderBottomRightRadius" to "4rpx", "borderBottomLeftRadius" to "4rpx", "marginRight" to "8rpx")), "bar-active-weak" to padStyleMapOf(utsMapOf("backgroundColor" to "#ff4d4f")), "bar-active-medium" to padStyleMapOf(utsMapOf("backgroundColor" to "#faad14")), "bar-active-strong" to padStyleMapOf(utsMapOf("backgroundColor" to "#52c41a")), "strength-label" to padStyleMapOf(utsMapOf("fontSize" to "24rpx", "color" to "#888888", "width" to "50rpx", "textAlign" to "right")), "agreement-row" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "flex-start", "marginTop" to "40rpx", "marginRight" to 0, "marginBottom" to "24rpx", "marginLeft" to 0)), "checkbox" to padStyleMapOf(utsMapOf("width" to "36rpx", "height" to "36rpx", "borderTopWidth" to "3rpx", "borderRightWidth" to "3rpx", "borderBottomWidth" to "3rpx", "borderLeftWidth" to "3rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#d9d9d9", "borderRightColor" to "#d9d9d9", "borderBottomColor" to "#d9d9d9", "borderLeftColor" to "#d9d9d9", "borderTopLeftRadius" to "8rpx", "borderTopRightRadius" to "8rpx", "borderBottomRightRadius" to "8rpx", "borderBottomLeftRadius" to "8rpx", "marginRight" to "16rpx", "display" to "flex", "alignItems" to "center", "justifyContent" to "center", "marginTop" to "4rpx")), "checkbox-checked" to padStyleMapOf(utsMapOf("backgroundColor" to "#0066CC", "borderTopColor" to "#0066CC", "borderRightColor" to "#0066CC", "borderBottomColor" to "#0066CC", "borderLeftColor" to "#0066CC")), "check-mark" to padStyleMapOf(utsMapOf("color" to "#ffffff", "fontSize" to "24rpx", "fontWeight" to "bold")), "agreement-text" to padStyleMapOf(utsMapOf("flex" to 1, "lineHeight" to 1.6)), "text-gray" to padStyleMapOf(utsMapOf("fontSize" to "26rpx", "color" to "#888888")), "text-link" to padStyleMapOf(utsMapOf("fontSize" to "26rpx", "color" to "#0066CC")), "bold" to padStyleMapOf(utsMapOf("fontWeight" to "bold")), "primary-btn" to padStyleMapOf(utsMapOf("width" to "100%", "height" to "110rpx", "backgroundColor" to "#0066CC", "color" to "#ffffff", "borderTopLeftRadius" to "24rpx", "borderTopRightRadius" to "24rpx", "borderBottomRightRadius" to "24rpx", "borderBottomLeftRadius" to "24rpx", "fontSize" to "34rpx", "fontWeight" to "bold", "display" to "flex", "alignItems" to "center", "justifyContent" to "center", "borderTopWidth" to "medium", "borderRightWidth" to "medium", "borderBottomWidth" to "medium", "borderLeftWidth" to "medium", "borderTopStyle" to "none", "borderRightStyle" to "none", "borderBottomStyle" to "none", "borderLeftStyle" to "none", "borderTopColor" to "#000000", "borderRightColor" to "#000000", "borderBottomColor" to "#000000", "borderLeftColor" to "#000000", "boxShadow" to "0 16rpx 32rpx rgba(0, 102, 204, 0.2)", "marginTop" to "16rpx")), "primary-btn-disabled" to padStyleMapOf(utsMapOf("backgroundColor" to "#a0c6ed", "boxShadow" to "none")), "footer-link" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "center", "alignItems" to "center", "marginTop" to "48rpx")), "link-wrap" to padStyleMapOf(utsMapOf("paddingTop" to "40rpx", "paddingRight" to "40rpx", "paddingBottom" to "40rpx", "paddingLeft" to "40rpx", "marginLeft" to "-20rpx", "zIndex" to 10)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = utsMapOf()
        var emits: Map<String, Any?> = utsMapOf()
        var props = normalizePropsOptions(utsMapOf())
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf()
        var components: Map<String, CreateVueComponent> = utsMapOf()
    }
}
