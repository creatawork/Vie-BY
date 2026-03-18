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
import io.dcloud.uniapp.extapi.getFileSystemManager as uni_getFileSystemManager
import io.dcloud.uniapp.extapi.hideLoading as uni_hideLoading
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
import io.dcloud.uniapp.extapi.setNavigationBarTitle as uni_setNavigationBarTitle
import io.dcloud.uniapp.extapi.showActionSheet as uni_showActionSheet
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesUserAddressEdit : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onReady(fun() {}, __ins)
        onLoad(fun(options: Any) {
            val opts = options as UTSJSONObject
            val mode = opts.getString("mode")
            this.loadPcaData()
            if (mode == "edit") {
                this.isEdit = true
                val id = opts.getNumber("id")
                if (id != null) {
                    this.addressId = id.toInt()
                    this.loadAddress(this.addressId)
                }
            }
            uni_setNavigationBarTitle(SetNavigationBarTitleOptions(title = if (this.isEdit) {
                "编辑地址"
            } else {
                "新增地址"
            }
            ))
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return createElementVNode("view", utsMapOf("class" to "page"), utsArrayOf(
            createElementVNode("scroll-view", utsMapOf("class" to "form-scroll", "scroll-y" to "true", "show-scrollbar" to false), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "form-content"), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "form-section"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "section-label"), utsArrayOf(
                            "收货人 ",
                            createElementVNode("text", utsMapOf("class" to "required"), "*")
                        )),
                        createElementVNode("input", utsMapOf("class" to "form-input", "modelValue" to _ctx.form.receiverName, "onInput" to fun(`$event`: InputEvent){
                            _ctx.form.receiverName = `$event`.detail.value
                        }
                        , "placeholder" to "请输入收货人姓名", "placeholder-class" to "placeholder"), null, 40, utsArrayOf(
                            "modelValue",
                            "onInput"
                        ))
                    )),
                    createElementVNode("view", utsMapOf("class" to "form-section"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "section-label"), utsArrayOf(
                            "手机号 ",
                            createElementVNode("text", utsMapOf("class" to "required"), "*")
                        )),
                        createElementVNode("input", utsMapOf("class" to "form-input", "modelValue" to _ctx.form.receiverPhone, "onInput" to fun(`$event`: InputEvent){
                            _ctx.form.receiverPhone = `$event`.detail.value
                        }
                        , "type" to "number", "placeholder" to "请输入11位手机号", "placeholder-class" to "placeholder", "maxlength" to "11"), null, 40, utsArrayOf(
                            "modelValue",
                            "onInput"
                        ))
                    )),
                    createElementVNode("view", utsMapOf("class" to "form-section"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "section-label"), utsArrayOf(
                            "所在地区 ",
                            createElementVNode("text", utsMapOf("class" to "required"), "*")
                        )),
                        createElementVNode("view", utsMapOf("class" to "region-picker", "onClick" to _ctx.showRegionPicker), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                                "region-text",
                                utsMapOf("region-text-placeholder" to !_ctx.hasRegion())
                            ))), toDisplayString(_ctx.getRegionText()), 3),
                            createElementVNode("text", utsMapOf("class" to "region-arrow"), "›")
                        ), 8, utsArrayOf(
                            "onClick"
                        ))
                    )),
                    createElementVNode("view", utsMapOf("class" to "form-section"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "section-label"), utsArrayOf(
                            "详细地址 ",
                            createElementVNode("text", utsMapOf("class" to "required"), "*")
                        )),
                        createElementVNode("textarea", utsMapOf("class" to "form-textarea", "modelValue" to _ctx.form.detailAddress, "onInput" to fun(`$event`: InputEvent){
                            _ctx.form.detailAddress = `$event`.detail.value
                        }
                        , "placeholder" to "请输入街道、楼栋、门牌号等", "placeholder-class" to "placeholder", "maxlength" to "200"), null, 40, utsArrayOf(
                            "modelValue",
                            "onInput"
                        )),
                        createElementVNode("text", utsMapOf("class" to "char-count"), toDisplayString(_ctx.form.detailAddress.length) + "/200", 1)
                    )),
                    createElementVNode("view", utsMapOf("class" to "default-row", "onClick" to fun(){
                        _ctx.form.isDefault = if (_ctx.form.isDefault === 1) {
                            0
                        } else {
                            1
                        }
                    }
                    ), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "default-label"), "设为默认地址"),
                        createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                            "switch-box",
                            utsMapOf("switch-box-active" to (_ctx.form.isDefault === 1))
                        ))), utsArrayOf(
                            createElementVNode("view", utsMapOf("class" to "switch-dot"))
                        ), 2)
                    ), 8, utsArrayOf(
                        "onClick"
                    ))
                )),
                createElementVNode("view", utsMapOf("class" to "form-space"))
            )),
            createElementVNode("view", utsMapOf("class" to "footer-box"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "save-btn", "onClick" to _ctx.saveAddress), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "save-btn-text"), "保存地址")
                ), 8, utsArrayOf(
                    "onClick"
                ))
            ))
        ))
    }
    open var isEdit: Boolean by `$data`
    open var addressId: Number by `$data`
    open var isLoading: Boolean by `$data`
    open var fullPcaData: UTSArray<PCAItem> by `$data`
    open var form: AddressForm by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return utsMapOf("isEdit" to false, "addressId" to 0, "isLoading" to false, "fullPcaData" to utsArrayOf<PCAItem>(), "form" to AddressForm(receiverName = "", receiverPhone = "", province = "", city = "", district = "", detailAddress = "", isDefault = 0))
    }
    open var loadPcaData = ::gen_loadPcaData_fn
    open fun gen_loadPcaData_fn(): UTSPromise<Unit> {
        return UTSPromise(fun(resolve, reject){
            if (this.fullPcaData.length > 0) {
                resolve(Unit)
                return
            }
            uni_showLoading(ShowLoadingOptions(title = "加载城市数据...", mask = true))
            uni_getFileSystemManager().readFile(ReadFileOptions(filePath = "/static/pca-code.json", encoding = "utf-8", success = fun(res){
                try {
                    val text = res.data as String
                    val arr = UTSAndroid.consoleDebugError(JSON.parse(text), " at pages/user/address-edit.uvue:146") as UTSArray<UTSJSONObject>
                    this.fullPcaData = arr.map(fun(p): PCAItem {
                        val pName = p.getString("name") ?: ""
                        val pCode = p.getString("code") ?: ""
                        val pChildrenAny = p.getAny("children")
                        val pChildren = if ((pChildrenAny != null)) {
                            (pChildrenAny as UTSArray<UTSJSONObject>)
                        } else {
                            utsArrayOf()
                        }
                        val cities = pChildren.map(fun(c): PCAItem {
                            val cName = c.getString("name") ?: ""
                            val cCode = c.getString("code") ?: ""
                            val cChildrenAny = c.getAny("children")
                            val cChildren = if ((cChildrenAny != null)) {
                                (cChildrenAny as UTSArray<UTSJSONObject>)
                            } else {
                                utsArrayOf()
                            }
                            val districts = cChildren.map(fun(d): PCAItem {
                                return PCAItem(name = d.getString("name") ?: "", code = d.getString("code") ?: "", children = utsArrayOf<PCAItem>())
                            }
                            )
                            return PCAItem(name = cName, code = cCode, children = districts)
                        }
                        )
                        return PCAItem(name = pName, code = pCode, children = cities)
                    }
                    )
                    uni_hideLoading()
                    resolve(Unit)
                }
                 catch (e: Throwable) {
                    uni_hideLoading()
                    reject(e)
                }
            }
            , fail = fun(err){
                uni_hideLoading()
                reject(err)
            }
            ))
        }
        )
    }
    open var loadAddress = ::gen_loadAddress_fn
    open fun gen_loadAddress_fn(id: Number) {
        getAddressDetail(id).then(fun(result){
            if (result.code === 200) {
                val data = result.data as UTSJSONObject
                if (data != null) {
                    this.form = AddressForm(receiverName = data.getString("receiverName") ?: "", receiverPhone = data.getString("receiverPhone") ?: "", province = data.getString("province") ?: "", city = data.getString("city") ?: "", district = data.getString("district") ?: "", detailAddress = data.getString("detailAddress") ?: "", isDefault = data.getNumber("isDefault")?.toInt() ?: 0)
                }
            } else {
                uni_showToast(ShowToastOptions(title = result.message, icon = "none"))
            }
        }
        ).`catch`(fun(error){
            console.error("加载地址详情失败:", error, " at pages/user/address-edit.uvue:197")
            uni_showToast(ShowToastOptions(title = "加载失败", icon = "none"))
        }
        )
    }
    open var hasRegion = ::gen_hasRegion_fn
    open fun gen_hasRegion_fn(): Boolean {
        return this.form.province != "" && this.form.city != "" && this.form.district != ""
    }
    open var getRegionText = ::gen_getRegionText_fn
    open fun gen_getRegionText_fn(): String {
        if (this.hasRegion()) {
            return this.form.province + " " + this.form.city + " " + this.form.district
        }
        return "请选择省/市/区"
    }
    open var showRegionPicker = ::gen_showRegionPicker_fn
    open fun gen_showRegionPicker_fn() {
        if (this.fullPcaData.length == 0) {
            this.loadPcaData().then(fun(){
                this.startPickRegion()
            }).`catch`(fun(err){
                uni_showToast(ShowToastOptions(title = "加载城市数据失败", icon = "none"))
            })
        } else {
            this.startPickRegion()
        }
    }
    open var startPickRegion = ::gen_startPickRegion_fn
    open fun gen_startPickRegion_fn() {
        val provinces = this.fullPcaData.map(fun(item: PCAItem): String {
            return item.name
        }
        )
        uni_showActionSheet(ShowActionSheetOptions(title = "选择省份", itemList = provinces, success = fun(res){
            val pIndex = res.tapIndex
            val province = this.fullPcaData[pIndex]
            val cities = province.children.map(fun(item: PCAItem): String {
                return item.name
            }
            )
            uni_showActionSheet(ShowActionSheetOptions(title = "选择城市", itemList = cities, success = fun(res2){
                val cIndex = res2.tapIndex
                val city = province.children[cIndex]
                val districts = city.children.map(fun(item: PCAItem): String {
                    return item.name
                }
                )
                uni_showActionSheet(ShowActionSheetOptions(title = "选择区县", itemList = districts, success = fun(res3){
                    val dIndex = res3.tapIndex
                    val district = city.children[dIndex]
                    this.form.province = province.name
                    this.form.city = city.name
                    this.form.district = district.name
                }
                ))
            }
            ))
        }
        ))
    }
    open var goBack = ::gen_goBack_fn
    open fun gen_goBack_fn() {
        uni_navigateBack(null)
    }
    open var saveAddress = ::gen_saveAddress_fn
    open fun gen_saveAddress_fn() {
        if (this.form.receiverName.trim() == "") {
            uni_showToast(ShowToastOptions(title = "请输入收货人", icon = "none"))
            return
        }
        if (this.form.receiverPhone.trim() == "" || this.form.receiverPhone.length != 11) {
            uni_showToast(ShowToastOptions(title = "请输入正确手机号", icon = "none"))
            return
        }
        if (!this.hasRegion()) {
            uni_showToast(ShowToastOptions(title = "请选择所在地区", icon = "none"))
            return
        }
        if (this.form.detailAddress.trim() == "") {
            uni_showToast(ShowToastOptions(title = "请输入详细地址", icon = "none"))
            return
        }
        if (this.isLoading) {
            return
        }
        this.isLoading = true
        val data: UTSJSONObject = let {
            object : UTSJSONObject(UTSSourceMapPosition("data", "pages/user/address-edit.uvue", 282, 10)) {
                var receiverName = it.form.receiverName
                var receiverPhone = it.form.receiverPhone
                var province = it.form.province
                var city = it.form.city
                var district = it.form.district
                var detailAddress = it.form.detailAddress
                var isDefault = it.form.isDefault
            }
        }
        val apiCall = if (this.isEdit) {
            updateAddress(this.addressId, data)
        } else {
            addAddress(data)
        }
        apiCall.then(fun(result){
            if (result.code === 200) {
                uni_showToast(ShowToastOptions(title = "保存成功", icon = "success"))
                setTimeout(fun(){
                    uni_navigateBack(null)
                }, 1000)
            } else {
                uni_showToast(ShowToastOptions(title = result.message, icon = "none"))
            }
        }
        ).`catch`(fun(error){
            console.error("保存地址失败:", error, " at pages/user/address-edit.uvue:305")
            uni_showToast(ShowToastOptions(title = "保存失败，请重试", icon = "none"))
        }
        ).`finally`(fun(){
            this.isLoading = false
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
                return utsMapOf("page" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "column", "backgroundColor" to "#F5F7FA", "height" to "100%")), "form-scroll" to padStyleMapOf(utsMapOf("flex" to 1)), "form-content" to padStyleMapOf(utsMapOf("paddingTop" to "24rpx", "paddingRight" to "24rpx", "paddingBottom" to "24rpx", "paddingLeft" to "24rpx")), "form-section" to padStyleMapOf(utsMapOf("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "16rpx", "borderTopRightRadius" to "16rpx", "borderBottomRightRadius" to "16rpx", "borderBottomLeftRadius" to "16rpx", "paddingTop" to "28rpx", "paddingRight" to "28rpx", "paddingBottom" to "28rpx", "paddingLeft" to "28rpx", "marginBottom" to "24rpx")), "section-label" to padStyleMapOf(utsMapOf("fontSize" to "28rpx", "color" to "#1A1A1A", "fontWeight" to "700", "marginBottom" to "20rpx")), "required" to padStyleMapOf(utsMapOf("color" to "#FF4D4F")), "tag-list" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "flexWrap" to "wrap")), "tag-item" to padStyleMapOf(utsMapOf("paddingTop" to "16rpx", "paddingRight" to "32rpx", "paddingBottom" to "16rpx", "paddingLeft" to "32rpx", "backgroundColor" to "#F5F7FA", "borderTopLeftRadius" to "8rpx", "borderTopRightRadius" to "8rpx", "borderBottomRightRadius" to "8rpx", "borderBottomLeftRadius" to "8rpx", "marginRight" to "20rpx", "marginBottom" to "16rpx", "borderTopWidth" to "2rpx", "borderRightWidth" to "2rpx", "borderBottomWidth" to "2rpx", "borderLeftWidth" to "2rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "rgba(0,0,0,0)", "borderRightColor" to "rgba(0,0,0,0)", "borderBottomColor" to "rgba(0,0,0,0)", "borderLeftColor" to "rgba(0,0,0,0)")), "tag-item-active" to padStyleMapOf(utsMapOf("backgroundColor" to "#E8F4FF", "borderTopColor" to "#0066CC", "borderRightColor" to "#0066CC", "borderBottomColor" to "#0066CC", "borderLeftColor" to "#0066CC")), "tag-text" to padStyleMapOf(utsMapOf("fontSize" to "26rpx", "color" to "#666666")), "tag-text-active" to padStyleMapOf(utsMapOf("color" to "#0066CC")), "form-input" to padStyleMapOf(utsMapOf("height" to "88rpx", "backgroundColor" to "#F5F7FA", "borderTopLeftRadius" to "12rpx", "borderTopRightRadius" to "12rpx", "borderBottomRightRadius" to "12rpx", "borderBottomLeftRadius" to "12rpx", "paddingTop" to 0, "paddingRight" to "24rpx", "paddingBottom" to 0, "paddingLeft" to "24rpx", "fontSize" to "28rpx", "color" to "#1A1A1A")), "placeholder" to padStyleMapOf(utsMapOf("color" to "#CCCCCC")), "region-picker" to padStyleMapOf(utsMapOf("height" to "88rpx", "backgroundColor" to "#F5F7FA", "borderTopLeftRadius" to "12rpx", "borderTopRightRadius" to "12rpx", "borderBottomRightRadius" to "12rpx", "borderBottomLeftRadius" to "12rpx", "paddingTop" to 0, "paddingRight" to "24rpx", "paddingBottom" to 0, "paddingLeft" to "24rpx", "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between")), "region-text" to padStyleMapOf(utsMapOf("fontSize" to "28rpx", "color" to "#1A1A1A")), "region-text-placeholder" to padStyleMapOf(utsMapOf("color" to "#CCCCCC")), "region-arrow" to padStyleMapOf(utsMapOf("fontSize" to "32rpx", "color" to "#CCCCCC")), "form-textarea" to padStyleMapOf(utsMapOf("width" to "100%", "height" to "180rpx", "backgroundColor" to "#F5F7FA", "borderTopLeftRadius" to "12rpx", "borderTopRightRadius" to "12rpx", "borderBottomRightRadius" to "12rpx", "borderBottomLeftRadius" to "12rpx", "paddingTop" to "24rpx", "paddingRight" to "24rpx", "paddingBottom" to "24rpx", "paddingLeft" to "24rpx", "fontSize" to "28rpx", "color" to "#1A1A1A")), "char-count" to padStyleMapOf(utsMapOf("fontSize" to "24rpx", "color" to "#CCCCCC", "textAlign" to "right", "marginTop" to "12rpx")), "default-row" to padStyleMapOf(utsMapOf("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "16rpx", "borderTopRightRadius" to "16rpx", "borderBottomRightRadius" to "16rpx", "borderBottomLeftRadius" to "16rpx", "paddingTop" to "28rpx", "paddingRight" to "28rpx", "paddingBottom" to "28rpx", "paddingLeft" to "28rpx", "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between")), "default-label" to padStyleMapOf(utsMapOf("fontSize" to "28rpx", "color" to "#1A1A1A")), "switch-box" to padStyleMapOf(utsMapOf("width" to "96rpx", "height" to "52rpx", "backgroundColor" to "#E0E0E0", "borderTopLeftRadius" to "26rpx", "borderTopRightRadius" to "26rpx", "borderBottomRightRadius" to "26rpx", "borderBottomLeftRadius" to "26rpx", "paddingTop" to "4rpx", "paddingRight" to "4rpx", "paddingBottom" to "4rpx", "paddingLeft" to "4rpx", "display" to "flex", "alignItems" to "center")), "switch-box-active" to padStyleMapOf(utsMapOf("backgroundColor" to "#0066CC", "justifyContent" to "flex-end")), "switch-dot" to padStyleMapOf(utsMapOf("width" to "44rpx", "height" to "44rpx", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "22rpx", "borderTopRightRadius" to "22rpx", "borderBottomRightRadius" to "22rpx", "borderBottomLeftRadius" to "22rpx")), "form-space" to padStyleMapOf(utsMapOf("height" to "180rpx")), "footer-box" to padStyleMapOf(utsMapOf("position" to "fixed", "bottom" to 0, "left" to 0, "right" to 0, "paddingTop" to "24rpx", "paddingRight" to "32rpx", "paddingBottom" to "48rpx", "paddingLeft" to "32rpx", "backgroundColor" to "#FFFFFF")), "save-btn" to padStyleMapOf(utsMapOf("height" to "96rpx", "backgroundColor" to "#0066CC", "borderTopLeftRadius" to "48rpx", "borderTopRightRadius" to "48rpx", "borderBottomRightRadius" to "48rpx", "borderBottomLeftRadius" to "48rpx", "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "save-btn-text" to padStyleMapOf(utsMapOf("fontSize" to "32rpx", "color" to "#FFFFFF", "fontWeight" to "700")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = utsMapOf()
        var emits: Map<String, Any?> = utsMapOf()
        var props = normalizePropsOptions(utsMapOf())
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf()
        var components: Map<String, CreateVueComponent> = utsMapOf()
    }
}
