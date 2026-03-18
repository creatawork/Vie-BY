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
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.showModal as uni_showModal
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesUserAddressList : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onLoad(fun(options: Any) {
            val opts = options as UTSJSONObject
            this.isSelectMode = opts.getString("select") == "1"
        }
        , __ins)
        onPageShow(fun() {
            this.loadData()
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return createElementVNode("view", utsMapOf("class" to "page"), utsArrayOf(
            createElementVNode("view", utsMapOf("class" to "nav-header"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "status-bar")),
                createElementVNode("view", utsMapOf("class" to "nav-bar"), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "nav-back", "onClick" to _ctx.goBack), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "back-arrow"), "‹")
                    ), 8, utsArrayOf(
                        "onClick"
                    )),
                    createElementVNode("text", utsMapOf("class" to "nav-title"), "收货地址"),
                    createElementVNode("view", utsMapOf("class" to "nav-right"))
                ))
            )),
            createElementVNode("scroll-view", utsMapOf("class" to "main-content", "scroll-y" to "true", "show-scrollbar" to false), utsArrayOf(
                if (_ctx.addresses.length > 0) {
                    createElementVNode("view", utsMapOf("key" to 0, "class" to "address-cards"), utsArrayOf(
                        createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.addresses, fun(item, index, __index, _cached): Any {
                            return createElementVNode("view", utsMapOf("class" to "address-card", "key" to item.id, "onClick" to fun(){
                                _ctx.onAddressClick(item)
                            }), utsArrayOf(
                                if (isTrue(item.isDefault)) {
                                    createElementVNode("view", utsMapOf("key" to 0, "class" to "default-tag"), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to "default-tag-text"), "默认")
                                    ))
                                } else {
                                    createCommentVNode("v-if", true)
                                },
                                createElementVNode("view", utsMapOf("class" to "card-main"), utsArrayOf(
                                    createElementVNode("view", utsMapOf("class" to "contact-row"), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to "contact-name"), toDisplayString(item.receiverName), 1),
                                        createElementVNode("text", utsMapOf("class" to "contact-phone"), toDisplayString(_ctx.formatPhone(item.receiverPhone)), 1)
                                    )),
                                    createElementVNode("view", utsMapOf("class" to "address-row"), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to "address-text"), toDisplayString(item.fullAddress), 1)
                                    ))
                                )),
                                createElementVNode("view", utsMapOf("class" to "card-actions"), utsArrayOf(
                                    createElementVNode("view", utsMapOf("class" to "action-left", "onClick" to fun(){
                                        _ctx.setDefault(index)
                                    }), utsArrayOf(
                                        createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                                            "radio-box",
                                            utsMapOf("radio-box-active" to item.isDefault)
                                        ))), utsArrayOf(
                                            if (isTrue(item.isDefault)) {
                                                createElementVNode("text", utsMapOf("key" to 0, "class" to "radio-dot"), "✓")
                                            } else {
                                                createCommentVNode("v-if", true)
                                            }
                                        ), 2),
                                        createElementVNode("text", utsMapOf("class" to "action-text"), "默认地址")
                                    ), 8, utsArrayOf(
                                        "onClick"
                                    )),
                                    createElementVNode("view", utsMapOf("class" to "action-right"), utsArrayOf(
                                        createElementVNode("view", utsMapOf("class" to "action-btn", "onClick" to fun(){
                                            _ctx.editAddress(item)
                                        }), utsArrayOf(
                                            createElementVNode("text", utsMapOf("class" to "action-btn-text"), "编辑")
                                        ), 8, utsArrayOf(
                                            "onClick"
                                        )),
                                        createElementVNode("view", utsMapOf("class" to "action-btn", "onClick" to fun(){
                                            _ctx.deleteAddress(index)
                                        }), utsArrayOf(
                                            createElementVNode("text", utsMapOf("class" to "action-btn-text action-btn-text-delete"), "删除")
                                        ), 8, utsArrayOf(
                                            "onClick"
                                        ))
                                    ))
                                ))
                            ), 8, utsArrayOf(
                                "onClick"
                            ))
                        }), 128)
                    ))
                } else {
                    createElementVNode("view", utsMapOf("key" to 1, "class" to "empty-box"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "empty-icon"), "📭"),
                        createElementVNode("text", utsMapOf("class" to "empty-title"), "暂无收货地址"),
                        createElementVNode("text", utsMapOf("class" to "empty-desc"), "添加地址后可快速下单")
                    ))
                }
                ,
                createElementVNode("view", utsMapOf("class" to "footer-space"))
            )),
            createElementVNode("view", utsMapOf("class" to "footer-btn-box"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "add-btn", "onClick" to _ctx.addAddress), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "add-btn-icon"), "+"),
                    createElementVNode("text", utsMapOf("class" to "add-btn-text"), "新增收货地址")
                ), 8, utsArrayOf(
                    "onClick"
                ))
            ))
        ))
    }
    open var addresses: UTSArray<AddressItem> by `$data`
    open var isLoading: Boolean by `$data`
    open var isSelectMode: Boolean by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return utsMapOf("addresses" to utsArrayOf<AddressItem>(), "isLoading" to false, "isSelectMode" to false)
    }
    open var onAddressClick = ::gen_onAddressClick_fn
    open fun gen_onAddressClick_fn(item: AddressItem) {
        if (this.isSelectMode) {
            uni__emit("addressSelected", item)
            uni_navigateBack(null)
        }
    }
    open var loadData = ::gen_loadData_fn
    open fun gen_loadData_fn() {
        if (this.isLoading) {
            return
        }
        this.isLoading = true
        getAddressList().then(fun(result){
            if (result.code === 200) {
                val data = result.data as UTSArray<UTSJSONObject>
                if (data != null) {
                    this.addresses = data.map(fun(item: UTSJSONObject): AddressItem {
                        return AddressItem(id = (item.getNumber("id") ?: 0).toInt(), receiverName = item.getString("receiverName") ?: "", receiverPhone = item.getString("receiverPhone") ?: "", province = item.getString("province") ?: "", city = item.getString("city") ?: "", district = item.getString("district") ?: "", detailAddress = item.getString("detailAddress") ?: "", fullAddress = item.getString("fullAddress") ?: "", isDefault = (item.getNumber("isDefault") ?: 0).toInt(), createTime = item.getString("createTime") ?: "", updateTime = item.getString("updateTime") ?: "")
                    }
                    )
                }
            } else {
                uni_showToast(ShowToastOptions(title = result.message, icon = "none"))
            }
        }
        ).`catch`(fun(error){
            console.error("获取地址列表失败:", error, " at pages/user/address-list.uvue:150")
            uni_showToast(ShowToastOptions(title = "加载失败，请重试", icon = "none"))
        }
        ).`finally`(fun(){
            this.isLoading = false
        }
        )
    }
    open var formatPhone = ::gen_formatPhone_fn
    open fun gen_formatPhone_fn(phone: String): String {
        if (phone.length == 11) {
            return phone.substring(0, 3) + "****" + phone.substring(7)
        }
        return phone
    }
    open var goBack = ::gen_goBack_fn
    open fun gen_goBack_fn() {
        uni_navigateBack(null)
    }
    open var addAddress = ::gen_addAddress_fn
    open fun gen_addAddress_fn() {
        uni_navigateTo(NavigateToOptions(url = "/pages/user/address-edit?mode=add"))
    }
    open var editAddress = ::gen_editAddress_fn
    open fun gen_editAddress_fn(item: AddressItem) {
        uni_navigateTo(NavigateToOptions(url = "/pages/user/address-edit?mode=edit&id=" + item.id))
    }
    open var deleteAddress = ::gen_deleteAddress_fn
    open fun gen_deleteAddress_fn(index: Number) {
        val address = this.addresses[index]
        uni_showModal(ShowModalOptions(title = "提示", content = "确定删除该地址吗？", success = fun(res){
            if (res.confirm) {
                uni.UNIBY001.deleteAddress(address.id).then(fun(result){
                    if (result.code === 200) {
                        this.addresses.splice(index, 1)
                        uni_showToast(ShowToastOptions(title = "已删除", icon = "success"))
                    } else {
                        uni_showToast(ShowToastOptions(title = result.message, icon = "none"))
                    }
                }
                ).`catch`(fun(error){
                    console.error("删除地址失败:", error, " at pages/user/address-list.uvue:186")
                    uni_showToast(ShowToastOptions(title = "删除失败，请重试", icon = "none"))
                }
                )
            }
        }
        ))
    }
    open var setDefault = ::gen_setDefault_fn
    open fun gen_setDefault_fn(index: Number) {
        val address = this.addresses[index]
        if (address.isDefault === 1) {
            return
        }
        setDefaultAddress(address.id).then(fun(result){
            if (result.code === 200) {
                this.addresses.forEach(fun(item: AddressItem, i: Number){
                    item.isDefault = if (i == index) {
                        1
                    } else {
                        0
                    }
                })
                uni_showToast(ShowToastOptions(title = "设置成功", icon = "success"))
            } else {
                uni_showToast(ShowToastOptions(title = result.message, icon = "none"))
            }
        }
        ).`catch`(fun(error){
            console.error("设置默认地址失败:", error, " at pages/user/address-list.uvue:211")
            uni_showToast(ShowToastOptions(title = "设置失败，请重试", icon = "none"))
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
                return utsMapOf("page" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "column", "backgroundColor" to "#F5F7FA", "height" to "100%")), "nav-header" to padStyleMapOf(utsMapOf("backgroundColor" to "#FFFFFF")), "status-bar" to padStyleMapOf(utsMapOf("height" to CSS_VAR_STATUS_BAR_HEIGHT)), "nav-bar" to padStyleMapOf(utsMapOf("height" to "88rpx", "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "paddingTop" to 0, "paddingRight" to "24rpx", "paddingBottom" to 0, "paddingLeft" to "24rpx")), "nav-back" to padStyleMapOf(utsMapOf("width" to "80rpx", "height" to "80rpx", "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "back-arrow" to padStyleMapOf(utsMapOf("fontSize" to "56rpx", "color" to "#333333", "fontWeight" to "400")), "nav-title" to padStyleMapOf(utsMapOf("fontSize" to "34rpx", "fontWeight" to "700", "color" to "#1A1A1A")), "nav-right" to padStyleMapOf(utsMapOf("width" to "80rpx")), "main-content" to padStyleMapOf(utsMapOf("flex" to 1, "paddingTop" to "24rpx", "paddingRight" to "24rpx", "paddingBottom" to "24rpx", "paddingLeft" to "24rpx")), "address-cards" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "column")), "address-card" to padStyleMapOf(utsMapOf("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "24rpx", "borderTopRightRadius" to "24rpx", "borderBottomRightRadius" to "24rpx", "borderBottomLeftRadius" to "24rpx", "marginBottom" to "24rpx", "paddingTop" to "32rpx", "paddingRight" to "32rpx", "paddingBottom" to "32rpx", "paddingLeft" to "32rpx", "position" to "relative", "overflow" to "hidden")), "default-tag" to padStyleMapOf(utsMapOf("position" to "absolute", "top" to 0, "right" to 0, "backgroundColor" to "#0066CC", "paddingTop" to "8rpx", "paddingRight" to "24rpx", "paddingBottom" to "8rpx", "paddingLeft" to "24rpx", "borderBottomLeftRadius" to "16rpx")), "default-tag-text" to padStyleMapOf(utsMapOf("fontSize" to "22rpx", "color" to "#FFFFFF")), "card-main" to padStyleMapOf(utsMapOf("paddingBottom" to "24rpx", "borderBottomWidth" to "1rpx", "borderBottomStyle" to "solid", "borderBottomColor" to "#F0F0F0")), "contact-row" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "marginBottom" to "16rpx")), "contact-name" to padStyleMapOf(utsMapOf("fontSize" to "32rpx", "fontWeight" to "700", "color" to "#1A1A1A", "marginRight" to "20rpx")), "contact-phone" to padStyleMapOf(utsMapOf("fontSize" to "28rpx", "color" to "#666666", "marginRight" to "16rpx")), "tag-badge" to padStyleMapOf(utsMapOf("backgroundColor" to "#E8F4FF", "paddingTop" to "4rpx", "paddingRight" to "16rpx", "paddingBottom" to "4rpx", "paddingLeft" to "16rpx", "borderTopLeftRadius" to "8rpx", "borderTopRightRadius" to "8rpx", "borderBottomRightRadius" to "8rpx", "borderBottomLeftRadius" to "8rpx")), "tag-badge-text" to padStyleMapOf(utsMapOf("fontSize" to "22rpx", "color" to "#0066CC")), "address-row" to padStyleMapOf(utsMapOf("display" to "flex")), "address-text" to padStyleMapOf(utsMapOf("fontSize" to "28rpx", "color" to "#666666", "lineHeight" to 1.5)), "card-actions" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "paddingTop" to "24rpx")), "action-left" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center")), "radio-box" to padStyleMapOf(utsMapOf("width" to "36rpx", "height" to "36rpx", "borderTopWidth" to "2rpx", "borderRightWidth" to "2rpx", "borderBottomWidth" to "2rpx", "borderLeftWidth" to "2rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#CCCCCC", "borderRightColor" to "#CCCCCC", "borderBottomColor" to "#CCCCCC", "borderLeftColor" to "#CCCCCC", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "marginRight" to "12rpx", "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "radio-box-active" to padStyleMapOf(utsMapOf("backgroundColor" to "#0066CC", "borderTopColor" to "#0066CC", "borderRightColor" to "#0066CC", "borderBottomColor" to "#0066CC", "borderLeftColor" to "#0066CC")), "radio-dot" to padStyleMapOf(utsMapOf("fontSize" to "24rpx", "color" to "#FFFFFF")), "action-text" to padStyleMapOf(utsMapOf("fontSize" to "26rpx", "color" to "#666666")), "action-right" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row")), "action-btn" to padStyleMapOf(utsMapOf("paddingTop" to "12rpx", "paddingRight" to "28rpx", "paddingBottom" to "12rpx", "paddingLeft" to "28rpx", "marginLeft" to "16rpx")), "action-btn-text" to padStyleMapOf(utsMapOf("fontSize" to "26rpx", "color" to "#0066CC")), "action-btn-text-delete" to padStyleMapOf(utsMapOf("color" to "#999999")), "empty-box" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "column", "alignItems" to "center", "paddingTop" to "160rpx", "paddingRight" to 0, "paddingBottom" to "160rpx", "paddingLeft" to 0)), "empty-icon" to padStyleMapOf(utsMapOf("fontSize" to "120rpx", "marginBottom" to "32rpx")), "empty-title" to padStyleMapOf(utsMapOf("fontSize" to "32rpx", "color" to "#333333", "fontWeight" to "700", "marginBottom" to "16rpx")), "empty-desc" to padStyleMapOf(utsMapOf("fontSize" to "26rpx", "color" to "#999999")), "footer-space" to padStyleMapOf(utsMapOf("height" to "180rpx")), "footer-btn-box" to padStyleMapOf(utsMapOf("position" to "fixed", "bottom" to 0, "left" to 0, "right" to 0, "paddingTop" to "24rpx", "paddingRight" to "32rpx", "paddingBottom" to "48rpx", "paddingLeft" to "32rpx", "backgroundColor" to "#FFFFFF")), "add-btn" to padStyleMapOf(utsMapOf("height" to "96rpx", "backgroundColor" to "#0066CC", "borderTopLeftRadius" to "48rpx", "borderTopRightRadius" to "48rpx", "borderBottomRightRadius" to "48rpx", "borderBottomLeftRadius" to "48rpx", "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center")), "add-btn-icon" to padStyleMapOf(utsMapOf("fontSize" to "40rpx", "color" to "#FFFFFF", "marginRight" to "12rpx")), "add-btn-text" to padStyleMapOf(utsMapOf("fontSize" to "32rpx", "color" to "#FFFFFF", "fontWeight" to "700")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = utsMapOf()
        var emits: Map<String, Any?> = utsMapOf()
        var props = normalizePropsOptions(utsMapOf())
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf()
        var components: Map<String, CreateVueComponent> = utsMapOf()
    }
}
