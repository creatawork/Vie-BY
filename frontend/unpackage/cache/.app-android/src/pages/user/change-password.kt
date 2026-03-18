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
import io.dcloud.uniapp.extapi.removeStorageSync as uni_removeStorageSync
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesUserChangePassword : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return createElementVNode("view", utsMapOf("class" to "password-container"), utsArrayOf(
            createElementVNode("view", utsMapOf("class" to "header"), utsArrayOf(
                createElementVNode("text", utsMapOf("class" to "back-icon", "onClick" to _ctx.handleBack), "‹", 8, utsArrayOf(
                    "onClick"
                )),
                createElementVNode("text", utsMapOf("class" to "header-title"), "修改密码")
            )),
            createElementVNode("scroll-view", utsMapOf("class" to "content", "scroll-y" to "true"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "form-section"), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "form-group"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "form-label"), "当前密码"),
                        createElementVNode("view", utsMapOf("class" to "password-input-wrapper"), utsArrayOf(
                            createElementVNode("input", utsMapOf("modelValue" to _ctx.form.oldPassword, "onInput" to fun(`$event`: InputEvent){
                                _ctx.form.oldPassword = `$event`.detail.value
                            }
                            , "type" to if (_ctx.showOldPassword) {
                                "text"
                            } else {
                                "password"
                            }
                            , "placeholder" to "请输入当前密码", "class" to "form-input", "onBlur" to _ctx.validateOldPassword), null, 40, utsArrayOf(
                                "modelValue",
                                "onInput",
                                "type",
                                "onBlur"
                            )),
                            createElementVNode("text", utsMapOf("class" to "toggle-password", "onClick" to fun(){
                                _ctx.showOldPassword = !_ctx.showOldPassword
                            }
                            ), toDisplayString(if (_ctx.showOldPassword) {
                                "隐藏"
                            } else {
                                "显示"
                            }
                            ), 9, utsArrayOf(
                                "onClick"
                            ))
                        )),
                        if (isTrue(_ctx.errors.oldPassword)) {
                            createElementVNode("text", utsMapOf("key" to 0, "class" to "error-text"), toDisplayString(_ctx.errors.oldPassword), 1)
                        } else {
                            createCommentVNode("v-if", true)
                        }
                    )),
                    createElementVNode("view", utsMapOf("class" to "form-group"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "form-label"), "新密码"),
                        createElementVNode("view", utsMapOf("class" to "password-input-wrapper"), utsArrayOf(
                            createElementVNode("input", utsMapOf("modelValue" to _ctx.form.newPassword, "onInput" to fun(`$event`: InputEvent){
                                _ctx.form.newPassword = `$event`.detail.value
                            }
                            , "type" to if (_ctx.showNewPassword) {
                                "text"
                            } else {
                                "password"
                            }
                            , "placeholder" to "请输入新密码", "class" to "form-input", "onBlur" to _ctx.validateNewPassword), null, 40, utsArrayOf(
                                "modelValue",
                                "onInput",
                                "type",
                                "onBlur"
                            )),
                            createElementVNode("text", utsMapOf("class" to "toggle-password", "onClick" to fun(){
                                _ctx.showNewPassword = !_ctx.showNewPassword
                            }
                            ), toDisplayString(if (_ctx.showNewPassword) {
                                "隐藏"
                            } else {
                                "显示"
                            }
                            ), 9, utsArrayOf(
                                "onClick"
                            ))
                        )),
                        createElementVNode("text", utsMapOf("class" to "form-hint"), "密码需包含大小写字母、数字，长度6-20位"),
                        createElementVNode("view", utsMapOf("class" to "password-strength"), utsArrayOf(
                            createElementVNode("view", utsMapOf("class" to "strength-bar"), utsArrayOf(
                                createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                                    "strength-fill",
                                    _ctx.passwordStrength
                                )), "style" to normalizeStyle(utsMapOf("width" to _ctx.strengthWidth))), null, 6)
                            )),
                            createElementVNode("text", utsMapOf("class" to "strength-text"), toDisplayString(_ctx.strengthText), 1)
                        )),
                        if (isTrue(_ctx.errors.newPassword)) {
                            createElementVNode("text", utsMapOf("key" to 0, "class" to "error-text"), toDisplayString(_ctx.errors.newPassword), 1)
                        } else {
                            createCommentVNode("v-if", true)
                        }
                    )),
                    createElementVNode("view", utsMapOf("class" to "form-group"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "form-label"), "确认新密码"),
                        createElementVNode("view", utsMapOf("class" to "password-input-wrapper"), utsArrayOf(
                            createElementVNode("input", utsMapOf("modelValue" to _ctx.form.confirmPassword, "onInput" to fun(`$event`: InputEvent){
                                _ctx.form.confirmPassword = `$event`.detail.value
                            }
                            , "type" to if (_ctx.showConfirmPassword) {
                                "text"
                            } else {
                                "password"
                            }
                            , "placeholder" to "请再次输入新密码", "class" to "form-input", "onBlur" to _ctx.validateConfirmPassword), null, 40, utsArrayOf(
                                "modelValue",
                                "onInput",
                                "type",
                                "onBlur"
                            )),
                            createElementVNode("text", utsMapOf("class" to "toggle-password", "onClick" to fun(){
                                _ctx.showConfirmPassword = !_ctx.showConfirmPassword
                            }
                            ), toDisplayString(if (_ctx.showConfirmPassword) {
                                "隐藏"
                            } else {
                                "显示"
                            }
                            ), 9, utsArrayOf(
                                "onClick"
                            ))
                        )),
                        if (isTrue(_ctx.errors.confirmPassword)) {
                            createElementVNode("text", utsMapOf("key" to 0, "class" to "error-text"), toDisplayString(_ctx.errors.confirmPassword), 1)
                        } else {
                            createCommentVNode("v-if", true)
                        }
                    )),
                    createElementVNode("view", utsMapOf("class" to "tips-section"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "tips-title"), "密码修改提示："),
                        createElementVNode("text", utsMapOf("class" to "tips-item"), "• 修改密码后需要重新登录"),
                        createElementVNode("text", utsMapOf("class" to "tips-item"), "• 请勿使用过于简单的密码"),
                        createElementVNode("text", utsMapOf("class" to "tips-item"), "• 定期修改密码可以提高账户安全")
                    ))
                )),
                createElementVNode("view", utsMapOf("class" to "action-section"), utsArrayOf(
                    createElementVNode("button", utsMapOf("class" to "confirm-btn", "disabled" to _ctx.isLoading, "onClick" to _ctx.handleConfirm), toDisplayString(if (_ctx.isLoading) {
                        "修改中..."
                    } else {
                        "确认修改"
                    }
                    ), 9, utsArrayOf(
                        "disabled",
                        "onClick"
                    )),
                    createElementVNode("button", utsMapOf("class" to "cancel-btn", "onClick" to _ctx.handleCancel), "取消", 8, utsArrayOf(
                        "onClick"
                    ))
                ))
            ))
        ))
    }
    open var form: PasswordForm by `$data`
    open var errors: PasswordErrors by `$data`
    open var showOldPassword: Boolean by `$data`
    open var showNewPassword: Boolean by `$data`
    open var showConfirmPassword: Boolean by `$data`
    open var isLoading: Boolean by `$data`
    open var passwordStrength: String by `$data`
    open var strengthWidth: String by `$data`
    open var strengthText: String by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return utsMapOf("form" to PasswordForm(oldPassword = "", newPassword = "", confirmPassword = ""), "errors" to PasswordErrors(oldPassword = "", newPassword = "", confirmPassword = ""), "showOldPassword" to false, "showNewPassword" to false, "showConfirmPassword" to false, "isLoading" to false, "passwordStrength" to computed<String>(fun(): String {
            val pwd = this.form.newPassword
            if (pwd == "") {
                return ""
            }
            var strength: Number = 0
            if (pwd.length >= 6) {
                strength++
            }
            if (pwd.length >= 8) {
                strength++
            }
            if (UTSRegExp("[a-z]", "").test(pwd) && UTSRegExp("[A-Z]", "").test(pwd)) {
                strength++
            }
            if (UTSRegExp("\\d", "").test(pwd)) {
                strength++
            }
            if (UTSRegExp("[^a-zA-Z\\d]", "").test(pwd)) {
                strength++
            }
            if (strength <= 2) {
                return "strength-fill-weak"
            }
            if (strength <= 3) {
                return "strength-fill-medium"
            }
            return "strength-fill-strong"
        }
        ), "strengthWidth" to computed<String>(fun(): String {
            val strength = this.passwordStrength
            if (strength === "strength-fill-weak") {
                return "33%"
            }
            if (strength === "strength-fill-medium") {
                return "66%"
            }
            if (strength === "strength-fill-strong") {
                return "100%"
            }
            return "0%"
        }
        ), "strengthText" to computed<String>(fun(): String {
            val strength = this.passwordStrength
            if (strength === "strength-fill-weak") {
                return "弱"
            }
            if (strength === "strength-fill-medium") {
                return "中"
            }
            if (strength === "strength-fill-strong") {
                return "强"
            }
            return ""
        }
        ))
    }
    open var validateOldPassword = ::gen_validateOldPassword_fn
    open fun gen_validateOldPassword_fn() {
        val password = this.form.oldPassword
        if (password == "") {
            this.errors.oldPassword = "请输入当前密码"
        } else if (password.length < 6) {
            this.errors.oldPassword = "密码长度不正确"
        } else {
            this.errors.oldPassword = ""
        }
    }
    open var validateNewPassword = ::gen_validateNewPassword_fn
    open fun gen_validateNewPassword_fn() {
        val password = this.form.newPassword
        if (password == "") {
            this.errors.newPassword = "请输入新密码"
        } else if (password.length < 6) {
            this.errors.newPassword = "密码至少6个字符"
        } else if (password.length > 20) {
            this.errors.newPassword = "密码最多20个字符"
        } else if (!UTSRegExp("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)", "").test(password)) {
            this.errors.newPassword = "密码需包含大小写字母和数字"
        } else {
            this.errors.newPassword = ""
        }
    }
    open var validateConfirmPassword = ::gen_validateConfirmPassword_fn
    open fun gen_validateConfirmPassword_fn() {
        val confirmPassword = this.form.confirmPassword
        if (confirmPassword == "") {
            this.errors.confirmPassword = "请确认新密码"
        } else if (confirmPassword !== this.form.newPassword) {
            this.errors.confirmPassword = "两次输入的密码不一致"
        } else {
            this.errors.confirmPassword = ""
        }
    }
    open var handleConfirm = ::gen_handleConfirm_fn
    open fun gen_handleConfirm_fn() {
        this.validateOldPassword()
        this.validateNewPassword()
        this.validateConfirmPassword()
        val hasErrors = this.errors.oldPassword != "" || this.errors.newPassword != "" || this.errors.confirmPassword != ""
        if (hasErrors) {
            return
        }
        this.isLoading = true
        changePassword(this.form.oldPassword, this.form.newPassword, this.form.confirmPassword).then(fun(result){
            if (result.code === 200) {
                uni_showToast(ShowToastOptions(title = "密码修改成功，请重新登录", icon = "success"))
                setTimeout(fun(){
                    uni_removeStorageSync("auth_token")
                    uni_removeStorageSync("user_info")
                    uni_navigateTo(NavigateToOptions(url = "/pages/login/login"))
                }, 1000)
            } else {
                val msg = if (result.message != "") {
                    result.message
                } else {
                    "密码修改失败"
                }
                uni_showToast(ShowToastOptions(title = msg, icon = "error"))
            }
            this.isLoading = false
        }
        ).`catch`(fun(error){
            console.error("修改密码失败:", error, " at pages/user/change-password.uvue:240")
            uni_showToast(ShowToastOptions(title = "密码修改失败，请检查网络连接", icon = "error"))
            this.isLoading = false
        }
        )
    }
    open var handleCancel = ::gen_handleCancel_fn
    open fun gen_handleCancel_fn() {
        uni_navigateBack(null)
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
                return utsMapOf("password-container" to padStyleMapOf(utsMapOf("flex" to 1, "backgroundColor" to "#f8f8f8", "display" to "flex", "flexDirection" to "column")), "header" to padStyleMapOf(utsMapOf("display" to "flex", "alignItems" to "center", "paddingTop" to 12, "paddingRight" to 16, "paddingBottom" to 12, "paddingLeft" to 16, "backgroundImage" to "linear-gradient(135deg, #0066CC 0%, #1890FF 100%)", "backgroundColor" to "rgba(0,0,0,0)", "color" to "#ffffff", "flexShrink" to 0, "position" to "relative")), "back-icon" to padStyleMapOf(utsMapOf("fontSize" to 28, "color" to "#ffffff", "fontWeight" to "400", "position" to "absolute", "left" to 16, "top" to "50%", "width" to 44, "height" to 44, "display" to "flex", "alignItems" to "center", "justifyContent" to "center", "zIndex" to 10)), "header-title" to padStyleMapOf(utsMapOf("fontSize" to 18, "fontWeight" to "700", "color" to "#ffffff", "flex" to 1, "textAlign" to "center")), "content" to padStyleMapOf(utsMapOf("flex" to 1, "paddingTop" to 12, "paddingRight" to 0, "paddingBottom" to 12, "paddingLeft" to 0)), "form-section" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "paddingTop" to 24, "paddingRight" to 24, "paddingBottom" to 24, "paddingLeft" to 24, "marginBottom" to 8, "marginLeft" to 12, "marginRight" to 12, "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10, "boxShadow" to "0 2px 8px rgba(0, 0, 0, 0.06)")), "form-group" to padStyleMapOf(utsMapOf("marginBottom" to 24)), "form-label" to padStyleMapOf(utsMapOf("fontSize" to 14, "fontWeight" to "400", "color" to "#333333", "display" to "flex", "marginBottom" to 8)), "password-input-wrapper" to padStyleMapOf(utsMapOf("position" to "relative", "display" to "flex", "alignItems" to "center")), "form-input" to padStyleMapOf(utsMapOf("width" to "100%", "height" to 44, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#e0e0e0", "borderRightColor" to "#e0e0e0", "borderBottomColor" to "#e0e0e0", "borderLeftColor" to "#e0e0e0", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingTop" to 0, "paddingRight" to 12, "paddingBottom" to 0, "paddingLeft" to 12, "fontSize" to 14, "color" to "#333333", "backgroundColor" to "#ffffff", "boxSizing" to "border-box")), "form-input-focused" to padStyleMapOf(utsMapOf("borderTopColor" to "#0066CC", "borderRightColor" to "#0066CC", "borderBottomColor" to "#0066CC", "borderLeftColor" to "#0066CC", "backgroundColor" to "#ffffff")), "toggle-password" to padStyleMapOf(utsMapOf("position" to "absolute", "right" to 12, "fontSize" to 12, "color" to "#0066CC", "fontWeight" to "400")), "form-hint" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999", "marginTop" to 4, "display" to "flex")), "error-text" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#e74c3c", "marginTop" to 4, "display" to "flex")), "password-strength" to padStyleMapOf(utsMapOf("marginTop" to 8, "display" to "flex", "alignItems" to "center", "marginRight" to 8)), "strength-bar" to padStyleMapOf(utsMapOf("flex" to 1, "height" to 4, "backgroundColor" to "#e0e0e0", "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2, "overflow" to "hidden")), "strength-fill" to padStyleMapOf(utsMapOf("height" to "100%")), "strength-fill-weak" to padStyleMapOf(utsMapOf("backgroundColor" to "#FF7A45")), "strength-fill-medium" to padStyleMapOf(utsMapOf("backgroundColor" to "#FF7A45")), "strength-fill-strong" to padStyleMapOf(utsMapOf("backgroundColor" to "#52C41A")), "strength-text" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#666666", "minWidth" to 20)), "tips-section" to padStyleMapOf(utsMapOf("backgroundColor" to "#f0f7ff", "borderLeftWidth" to 3, "borderLeftStyle" to "solid", "borderLeftColor" to "#0066CC", "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "borderTopLeftRadius" to 6, "borderTopRightRadius" to 6, "borderBottomRightRadius" to 6, "borderBottomLeftRadius" to 6, "marginTop" to 24)), "tips-title" to padStyleMapOf(utsMapOf("fontSize" to 13, "fontWeight" to "700", "color" to "#0066CC", "display" to "flex", "marginBottom" to 8)), "tips-item" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#666666", "display" to "flex", "marginBottom" to 4, "lineHeight" to 1.5)), "action-section" to padStyleMapOf(utsMapOf("paddingTop" to 24, "paddingRight" to 12, "paddingBottom" to 24, "paddingLeft" to 12, "display" to "flex", "flexDirection" to "column", "marginBottom" to 12, "flexShrink" to 0)), "confirm-btn" to padStyleMapOf(utsMapOf("width" to "100%", "height" to 48, "backgroundImage" to "linear-gradient(135deg, #0066CC 0%, #1890FF 100%)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "color" to "#ffffff", "fontSize" to 16, "fontWeight" to "700", "borderTopWidth" to "medium", "borderRightWidth" to "medium", "borderBottomWidth" to "medium", "borderLeftWidth" to "medium", "borderTopStyle" to "none", "borderRightStyle" to "none", "borderBottomStyle" to "none", "borderLeftStyle" to "none", "borderTopColor" to "#000000", "borderRightColor" to "#000000", "borderBottomColor" to "#000000", "borderLeftColor" to "#000000", "opacity:disabled" to 0.6)), "cancel-btn" to padStyleMapOf(utsMapOf("width" to "100%", "height" to 48, "backgroundColor" to "#ffffff", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#e0e0e0", "borderRightColor" to "#e0e0e0", "borderBottomColor" to "#e0e0e0", "borderLeftColor" to "#e0e0e0", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "color" to "#666666", "fontSize" to 16, "fontWeight" to "700")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = utsMapOf()
        var emits: Map<String, Any?> = utsMapOf()
        var props = normalizePropsOptions(utsMapOf())
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf()
        var components: Map<String, CreateVueComponent> = utsMapOf()
    }
}
