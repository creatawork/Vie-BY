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
import io.dcloud.uniapp.extapi.chooseImage as uni_chooseImage
import io.dcloud.uniapp.extapi.getStorageSync as uni_getStorageSync
import io.dcloud.uniapp.extapi.hideLoading as uni_hideLoading
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesUserEditProfile : BasePage {
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
        return createElementVNode("view", utsMapOf("class" to "edit-profile-container"), utsArrayOf(
            createElementVNode("view", utsMapOf("class" to "header"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "header-inner"), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "back-btn", "onClick" to _ctx.handleBack), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "back-icon"), "←")
                    ), 8, utsArrayOf(
                        "onClick"
                    )),
                    createElementVNode("view", utsMapOf("class" to "header-texts"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "header-title"), "编辑个人信息"),
                        createElementVNode("text", utsMapOf("class" to "header-subtitle"), "完善资料，让服务更贴近你")
                    ))
                ))
            )),
            if (isTrue(_ctx.isLoading && !_ctx.formLoaded)) {
                createElementVNode("view", utsMapOf("key" to 0, "class" to "loading-box"), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "loading-text"), "加载中...")
                ))
            } else {
                createElementVNode("view", utsMapOf("key" to 1, "class" to "content-sheet"), utsArrayOf(
                    createElementVNode("scroll-view", utsMapOf("class" to "content", "scroll-y" to "true", "show-scrollbar" to false), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "form-card"), utsArrayOf(
                            createElementVNode("view", utsMapOf("class" to "avatar-section"), utsArrayOf(
                                createElementVNode("view", utsMapOf("class" to "avatar-wrapper", "onClick" to _ctx.handleUploadAvatar), utsArrayOf(
                                    createElementVNode("image", utsMapOf("src" to _ctx.getAvatarUrl(), "class" to "avatar-preview", "mode" to "aspectFill"), null, 8, utsArrayOf(
                                        "src"
                                    )),
                                    createElementVNode("view", utsMapOf("class" to "avatar-overlay"), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to "camera-icon"), "📷")
                                    ))
                                ), 8, utsArrayOf(
                                    "onClick"
                                )),
                                createElementVNode("text", utsMapOf("class" to "avatar-hint"), "点击更换头像")
                            )),
                            createElementVNode("view", utsMapOf("class" to "input-group"), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "input-label"), "昵称"),
                                createElementVNode("view", utsMapOf("class" to "flex-column flex-1"), utsArrayOf(
                                    createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                                        "input-wrapper",
                                        utsMapOf("input-wrapper-focused" to (_ctx.focusedField === "nickname"), "input-wrapper-error" to (_ctx.errors.nickname !== ""))
                                    ))), utsArrayOf(
                                        createElementVNode("input", utsMapOf("modelValue" to _ctx.form.nickname, "onInput" to fun(`$event`: InputEvent){
                                            _ctx.form.nickname = `$event`.detail.value
                                        }
                                        , "type" to "text", "placeholder" to "请输入昵称", "placeholder-class" to "placeholder-text", "class" to "input-field", "onFocus" to fun(){
                                            _ctx.handleFocus("nickname")
                                        }
                                        , "onBlur" to _ctx.handleBlur), null, 40, utsArrayOf(
                                            "modelValue",
                                            "onInput",
                                            "onFocus",
                                            "onBlur"
                                        )),
                                        if (isTrue(_ctx.form.nickname)) {
                                            createElementVNode("text", utsMapOf("key" to 0, "class" to "clear-btn", "onClick" to fun(){
                                                _ctx.form.nickname = ""
                                            }), "✕", 8, utsArrayOf(
                                                "onClick"
                                            ))
                                        } else {
                                            createCommentVNode("v-if", true)
                                        }
                                    ), 2),
                                    if (isTrue(_ctx.errors.nickname)) {
                                        createElementVNode("text", utsMapOf("key" to 0, "class" to "error-text"), toDisplayString(_ctx.errors.nickname), 1)
                                    } else {
                                        createCommentVNode("v-if", true)
                                    }
                                ))
                            )),
                            createElementVNode("view", utsMapOf("class" to "input-group"), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "input-label"), "性别"),
                                createElementVNode("view", utsMapOf("class" to "gender-options"), utsArrayOf(
                                    createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                                        "gender-btn",
                                        utsMapOf("gender-btn-active" to (_ctx.form.gender === 1))
                                    )), "onClick" to fun(){
                                        _ctx.form.gender = 1
                                    }
                                    ), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to "gender-icon"), "♂"),
                                        createElementVNode("text", utsMapOf("class" to "gender-text"), "男")
                                    ), 10, utsArrayOf(
                                        "onClick"
                                    )),
                                    createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                                        "gender-btn",
                                        utsMapOf("gender-btn-active" to (_ctx.form.gender === 2))
                                    )), "onClick" to fun(){
                                        _ctx.form.gender = 2
                                    }
                                    ), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to "gender-icon"), "♀"),
                                        createElementVNode("text", utsMapOf("class" to "gender-text"), "女")
                                    ), 10, utsArrayOf(
                                        "onClick"
                                    )),
                                    createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                                        "gender-btn",
                                        utsMapOf("gender-btn-active" to (_ctx.form.gender === 0))
                                    )), "onClick" to fun(){
                                        _ctx.form.gender = 0
                                    }
                                    ), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to "gender-icon"), "?"),
                                        createElementVNode("text", utsMapOf("class" to "gender-text"), "保密")
                                    ), 10, utsArrayOf(
                                        "onClick"
                                    ))
                                ))
                            )),
                            createElementVNode("view", utsMapOf("class" to "input-group"), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "input-label"), "手机号"),
                                createElementVNode("view", utsMapOf("class" to "input-wrapper input-wrapper-disabled"), utsArrayOf(
                                    createElementVNode("input", utsMapOf("value" to _ctx.form.phone, "type" to "text", "class" to "input-field", "disabled" to ""), null, 8, utsArrayOf(
                                        "value"
                                    )),
                                    createElementVNode("text", utsMapOf("class" to "lock-icon"), "🔒")
                                ))
                            )),
                            createElementVNode("view", utsMapOf("class" to "input-group"), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "input-label"), "邮箱"),
                                createElementVNode("view", utsMapOf("class" to "input-wrapper input-wrapper-disabled"), utsArrayOf(
                                    createElementVNode("input", utsMapOf("value" to _ctx.form.email, "type" to "text", "class" to "input-field", "disabled" to ""), null, 8, utsArrayOf(
                                        "value"
                                    )),
                                    createElementVNode("text", utsMapOf("class" to "lock-icon"), "🔒")
                                ))
                            ))
                        )),
                        createElementVNode("view", utsMapOf("class" to "action-section"), utsArrayOf(
                            createElementVNode("button", utsMapOf("class" to "btn btn-primary submit-btn", "hover-class" to "btn-hover", "loading" to _ctx.isLoading, "onClick" to _ctx.handleSubmit), toDisplayString(if (_ctx.isLoading) {
                                "保存中..."
                            } else {
                                "保存修改"
                            }
                            ), 9, utsArrayOf(
                                "loading",
                                "onClick"
                            ))
                        ))
                    ))
                ))
            }
        ))
    }
    open var form: ProfileForm by `$data`
    open var errors: ProfileErrors by `$data`
    open var isLoading: Boolean by `$data`
    open var isUploadingAvatar: Boolean by `$data`
    open var formLoaded: Boolean by `$data`
    open var defaultAvatar: String by `$data`
    open var focusedField: String by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return utsMapOf("form" to ProfileForm(nickname = "", avatar = "", gender = 0, phone = "", email = ""), "errors" to ProfileErrors(nickname = ""), "isLoading" to false, "isUploadingAvatar" to false, "formLoaded" to false, "defaultAvatar" to "/static/logo.png", "focusedField" to "")
    }
    open var getAvatarUrl = ::gen_getAvatarUrl_fn
    open fun gen_getAvatarUrl_fn(): String {
        return if (this.form.avatar != "") {
            this.form.avatar
        } else {
            this.defaultAvatar
        }
    }
    open var handleFocus = ::gen_handleFocus_fn
    open fun gen_handleFocus_fn(field: String) {
        this.focusedField = field
    }
    open var handleBlur = ::gen_handleBlur_fn
    open fun gen_handleBlur_fn() {
        this.focusedField = ""
        this.validateNickname()
    }
    open var loadUserInfo = ::gen_loadUserInfo_fn
    open fun gen_loadUserInfo_fn() {
        getUserProfile().then(fun(result){
            if (result.code === 200) {
                val userInfo = result.data as UTSJSONObject
                this.form.nickname = (userInfo.getString("nickname") ?: "") as String
                this.form.avatar = (userInfo.getString("avatar") ?: "") as String
                this.form.gender = (userInfo.getNumber("gender") ?: 0).toInt()
                this.form.phone = (userInfo.getString("phone") ?: "") as String
                this.form.email = (userInfo.getString("email") ?: "") as String
                this.formLoaded = true
            } else {
                val msg = if (result.message != "") {
                    result.message
                } else {
                    "获取用户信息失败"
                }
                uni_showToast(ShowToastOptions(title = msg, icon = "error"))
                setTimeout(fun(){
                    uni_navigateBack(null)
                }
                , 1000)
            }
        }
        ).`catch`(fun(error){
            console.error("获取用户信息失败:", error, " at pages/user/edit-profile.uvue:202")
            uni_showToast(ShowToastOptions(title = "获取用户信息失败", icon = "error"))
            setTimeout(fun(){
                uni_navigateBack(null)
            }
            , 1000)
        }
        )
    }
    open var validateNickname = ::gen_validateNickname_fn
    open fun gen_validateNickname_fn() {
        val nickname = this.form.nickname.trim()
        if (nickname == "") {
            this.errors.nickname = "昵称不能为空"
        } else if (nickname.length > 20) {
            this.errors.nickname = "昵称长度不能超过20个字符"
        } else {
            this.errors.nickname = ""
        }
    }
    open var handleUploadAvatar = ::gen_handleUploadAvatar_fn
    open fun gen_handleUploadAvatar_fn() {
        uni_chooseImage(ChooseImageOptions(count = 1, sizeType = utsArrayOf(
            "original",
            "compressed"
        ), sourceType = utsArrayOf(
            "album",
            "camera"
        ), success = fun(res){
            if (res.tempFilePaths != null && res.tempFilePaths.length > 0) {
                this.uploadAvatarFile(res.tempFilePaths[0])
            }
        }
        , fail = fun(err: Any){
            console.error("选择图片失败:", err, " at pages/user/edit-profile.uvue:233")
            uni_showToast(ShowToastOptions(title = "选择图片失败", icon = "error"))
        }
        ))
    }
    open var uploadAvatarFile = ::gen_uploadAvatarFile_fn
    open fun gen_uploadAvatarFile_fn(filePath: String) {
        this.isUploadingAvatar = true
        uni_showLoading(ShowLoadingOptions(title = "上传中..."))
        uploadAvatar(filePath).then(fun(result){
            if (result != null) {
                val code = (result.getNumber("code") ?: 0).toInt()
                if (code === 200) {
                    val data = result.getString("data") ?: ""
                    this.form.avatar = data
                    uni_showToast(ShowToastOptions(title = "头像上传成功", icon = "success"))
                } else {
                    val msg = result.getString("message") ?: "上传失败"
                    uni_showToast(ShowToastOptions(title = msg, icon = "error"))
                }
            } else {
                uni_showToast(ShowToastOptions(title = "上传失败", icon = "error"))
            }
            this.isUploadingAvatar = false
            uni_hideLoading()
        }
        ).`catch`(fun(error){
            console.error("上传头像失败:", error, " at pages/user/edit-profile.uvue:270")
            uni_showToast(ShowToastOptions(title = "上传失败，请检查网络连接", icon = "error"))
            this.isUploadingAvatar = false
            uni_hideLoading()
        }
        )
    }
    open var handleSubmit = ::gen_handleSubmit_fn
    open fun gen_handleSubmit_fn() {
        this.validateNickname()
        if (this.errors.nickname != "") {
            return
        }
        this.isLoading = true
        updateUserProfile(let {
            object : UTSJSONObject() {
                var nickname = it.form.nickname
                var avatar = it.form.avatar
                var gender = it.form.gender
            }
        }).then(fun(result){
            if (result.code === 200) {
                val storedInfo = uni_getStorageSync("user_info")
                if (storedInfo != null && storedInfo != "") {
                    val infoStr = storedInfo as String
                    val info = UTSAndroid.consoleDebugError(JSON.parse(infoStr), " at pages/user/edit-profile.uvue:297") as UTSJSONObject
                    val updatedInfo: UTSJSONObject = let {
                        object : UTSJSONObject(UTSSourceMapPosition("updatedInfo", "pages/user/edit-profile.uvue", 298, 13)) {
                            var id = if (info.getNumber("id") != null) {
                                info.getNumber("id")!!.toInt()
                            } else {
                                0
                            }
                            var username = info.getString("username") ?: ""
                            var nickname = it.form.nickname
                            var avatar = it.form.avatar
                            var gender = it.form.gender
                            var roleCodes = info.getAny("roleCodes")
                        }
                    }
                    uni_setStorageSync("user_info", JSON.stringify(updatedInfo))
                }
                uni_showToast(ShowToastOptions(title = "保存成功", icon = "success"))
                setTimeout(fun(){
                    uni_navigateBack(null)
                }, 1000)
            } else {
                val msg = if (result.message != "") {
                    result.message
                } else {
                    "更新失败"
                }
                uni_showToast(ShowToastOptions(title = msg, icon = "error"))
            }
            this.isLoading = false
        }
        ).`catch`(fun(error){
            console.error("更新个人信息失败:", error, " at pages/user/edit-profile.uvue:325")
            uni_showToast(ShowToastOptions(title = "更新失败，请检查网络连接", icon = "error"))
            this.isLoading = false
        }
        )
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
                return utsMapOf("edit-profile-container" to padStyleMapOf(utsMapOf("backgroundImage" to "linear-gradient(135deg, #0066CC 0%, #1890FF 100%)", "backgroundColor" to "rgba(0,0,0,0)", "position" to "relative", "overflow" to "hidden", "display" to "flex", "flexDirection" to "column")), "header" to padStyleMapOf(utsMapOf("paddingTop" to CSS_VAR_STATUS_BAR_HEIGHT, "paddingLeft" to 24, "paddingRight" to 24, "paddingBottom" to 12, "marginTop" to 8, "position" to "relative", "zIndex" to 10)), "header-inner" to padStyleMapOf(utsMapOf("height" to 44, "display" to "flex", "alignItems" to "center")), "back-btn" to padStyleMapOf(utsMapOf("width" to 40, "height" to 40, "display" to "flex", "alignItems" to "center", "justifyContent" to "center", "marginLeft" to -12)), "back-icon" to padStyleMapOf(utsMapOf("fontSize" to 24, "color" to "#ffffff", "lineHeight" to 1)), "header-title" to padStyleMapOf(utsMapOf("fontSize" to 18, "fontWeight" to "700", "color" to "#ffffff")), "header-texts" to padStyleMapOf(utsMapOf("flex" to 1, "display" to "flex", "flexDirection" to "column", "paddingLeft" to 4)), "header-subtitle" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "rgba(255,255,255,0.82)", "marginTop" to 2)), "loading-box" to padStyleMapOf(utsMapOf("flex" to 1, "display" to "flex", "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "#F5F7FA")), "loading-text" to padStyleMapOf(utsMapOf("color" to "#999999", "fontSize" to 14)), "content-sheet" to padStyleMapOf(utsMapOf("flex" to 1, "backgroundColor" to "#F5F7FA", "borderTopLeftRadius" to 20, "borderTopRightRadius" to 20, "paddingTop" to 16, "boxShadow" to "0 -4px 16px rgba(0, 0, 0, 0.06)")), "content" to padStyleMapOf(utsMapOf("flex" to 1, "paddingTop" to 0, "paddingRight" to 24, "paddingBottom" to 0, "paddingLeft" to 24, "position" to "relative", "zIndex" to 1)), "form-card" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "paddingTop" to 24, "paddingRight" to 24, "paddingBottom" to 24, "paddingLeft" to 24, "boxShadow" to "0 4px 12px rgba(0, 0, 0, 0.08)", "marginBottom" to 24)), "avatar-section" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "column", "alignItems" to "center", "marginBottom" to 32)), "avatar-wrapper" to padStyleMapOf(utsMapOf("position" to "relative", "width" to 110, "height" to 110, "borderTopLeftRadius" to 55, "borderTopRightRadius" to 55, "borderBottomRightRadius" to 55, "borderBottomLeftRadius" to 55, "overflow" to "hidden", "boxShadow" to "0 8px 20px rgba(0, 0, 0, 0.1)", "borderTopWidth" to 4, "borderRightWidth" to 4, "borderBottomWidth" to 4, "borderLeftWidth" to 4, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#ffffff", "borderRightColor" to "#ffffff", "borderBottomColor" to "#ffffff", "borderLeftColor" to "#ffffff")), "avatar-preview" to padStyleMapOf(utsMapOf("width" to "100%", "height" to "100%", "backgroundColor" to "#f0f0f0")), "avatar-overlay" to padStyleMapOf(utsMapOf("position" to "absolute", "bottom" to 0, "left" to 0, "right" to 0, "height" to 35, "backgroundColor" to "rgba(0,0,0,0.6)", "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "camera-icon" to padStyleMapOf(utsMapOf("fontSize" to 18, "color" to "#ffffff", "marginBottom" to 4)), "avatar-hint" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999", "marginTop" to 12, "backgroundColor" to "rgba(255,255,255,0.6)", "paddingTop" to 4, "paddingRight" to 12, "paddingBottom" to 4, "paddingLeft" to 12, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12)), "input-group" to padStyleMapOf(utsMapOf("marginBottom" to 24, "display" to "flex", "alignItems" to "center")), "input-label" to padStyleMapOf(utsMapOf("fontSize" to 15, "color" to "#333333", "fontWeight" to "400", "width" to 70, "marginRight" to 12, "flexShrink" to 0)), "input-wrapper" to padStyleMapOf(utsMapOf("flex" to 1, "backgroundColor" to "#f8f9fa", "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "paddingTop" to 0, "paddingRight" to 16, "paddingBottom" to 0, "paddingLeft" to 16, "height" to 56, "display" to "flex", "alignItems" to "center", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "rgba(0,0,0,0)", "borderRightColor" to "rgba(0,0,0,0)", "borderBottomColor" to "rgba(0,0,0,0)", "borderLeftColor" to "rgba(0,0,0,0)")), "input-wrapper-focused" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "borderTopColor" to "#0066CC", "borderRightColor" to "#0066CC", "borderBottomColor" to "#0066CC", "borderLeftColor" to "#0066CC", "boxShadow" to "0 4px 12px rgba(0, 102, 204, 0.15)")), "input-wrapper-error" to padStyleMapOf(utsMapOf("borderTopColor" to "#ff4d4f", "borderRightColor" to "#ff4d4f", "borderBottomColor" to "#ff4d4f", "borderLeftColor" to "#ff4d4f", "backgroundColor" to "#fff0f0")), "input-wrapper-disabled" to padStyleMapOf(utsMapOf("backgroundColor" to "#f5f5f5", "opacity" to 0.7, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "dashed", "borderRightStyle" to "dashed", "borderBottomStyle" to "dashed", "borderLeftStyle" to "dashed", "borderTopColor" to "#e0e0e0", "borderRightColor" to "#e0e0e0", "borderBottomColor" to "#e0e0e0", "borderLeftColor" to "#e0e0e0")), "input-field" to padStyleMapOf(utsMapOf("flex" to 1, "height" to 48, "fontSize" to 14, "color" to "#333333")), "placeholder-text" to padStyleMapOf(utsMapOf("color" to "#CCCCCC")), "lock-icon" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999")), "error-text" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#FF4D4F", "marginTop" to 4, "marginLeft" to 4)), "flex-column" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "column")), "flex-1" to padStyleMapOf(utsMapOf("flex" to 1)), "clear-btn" to padStyleMapOf(utsMapOf("paddingTop" to 8, "paddingRight" to 8, "paddingBottom" to 8, "paddingLeft" to 8, "color" to "#999999", "fontSize" to 14)), "gender-options" to padStyleMapOf(utsMapOf("flex" to 1, "display" to "flex", "backgroundColor" to "#f8f9fa", "paddingTop" to 6, "paddingRight" to 6, "paddingBottom" to 6, "paddingLeft" to 6, "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16)), "gender-btn" to padStyleMapOf(utsMapOf("flex" to 1, "height" to 44, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "display" to "flex", "alignItems" to "center", "justifyContent" to "center", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "rgba(0,0,0,0)", "borderRightColor" to "rgba(0,0,0,0)", "borderBottomColor" to "rgba(0,0,0,0)", "borderLeftColor" to "rgba(0,0,0,0)", "color" to "#999999", "marginLeft" to 12)), "gender-btn-active" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "color" to "#0066CC", "boxShadow" to "0 2px 8px rgba(0, 0, 0, 0.08)", "fontWeight" to "700")), "gender-icon" to padStyleMapOf(utsMapOf("marginRight" to 6, "fontSize" to 18)), "gender-text" to padStyleMapOf(utsMapOf("fontSize" to 14)), "action-section" to padStyleMapOf(utsMapOf("marginTop" to 32, "paddingBottom" to 24)), "submit-btn" to padStyleMapOf(utsMapOf("height" to 56, "borderTopLeftRadius" to 28, "borderTopRightRadius" to 28, "borderBottomRightRadius" to 28, "borderBottomLeftRadius" to 28, "fontSize" to 18, "fontWeight" to "700", "boxShadow" to "0 8px 24px rgba(0, 102, 204, 0.3)", "backgroundImage" to "linear-gradient(135deg, #0066CC 0%, #1890FF 100%)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopWidth" to "medium", "borderRightWidth" to "medium", "borderBottomWidth" to "medium", "borderLeftWidth" to "medium", "borderTopStyle" to "none", "borderRightStyle" to "none", "borderBottomStyle" to "none", "borderLeftStyle" to "none", "borderTopColor" to "#000000", "borderRightColor" to "#000000", "borderBottomColor" to "#000000", "borderLeftColor" to "#000000", "display" to "flex", "alignItems" to "center", "justifyContent" to "center", "letterSpacing" to 1)), "btn-hover" to padStyleMapOf(utsMapOf("opacity" to 0.9)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = utsMapOf()
        var emits: Map<String, Any?> = utsMapOf()
        var props = normalizePropsOptions(utsMapOf())
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf()
        var components: Map<String, CreateVueComponent> = utsMapOf()
    }
}
