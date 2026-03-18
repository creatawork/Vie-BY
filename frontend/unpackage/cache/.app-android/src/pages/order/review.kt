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
import io.dcloud.uniapp.extapi.chooseImage as uni_chooseImage
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
import io.dcloud.uniapp.extapi.previewImage as uni_previewImage
import io.dcloud.uniapp.extapi.redirectTo as uni_redirectTo
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesOrderReview : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onLoad(fun(options: Any) {
            val opts = options as UTSJSONObject
            this.orderId = parseInt(opts.getString("orderId") ?: "0")
            this.productId = parseInt(opts.getString("productId") ?: "0")
            this.reviewIndex = parseInt(opts.getString("reviewIndex") ?: "0")
            this.reviewTotal = parseInt(opts.getString("reviewTotal") ?: "1")
            val nameParam: String = opts.getString("productName") ?: ""
            val imageParam: String = opts.getString("productImage") ?: ""
            this.productName = UTSAndroid.consoleDebugError(decodeURIComponent(nameParam), " at pages/order/review.uvue:93") ?: ""
            this.productImage = UTSAndroid.consoleDebugError(decodeURIComponent(imageParam), " at pages/order/review.uvue:94") ?: ""
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return createElementVNode("view", utsMapOf("class" to "review-page"), utsArrayOf(
            createElementVNode("view", utsMapOf("class" to "nav-bar"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "nav-back", "onClick" to _ctx.goBack), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "back-icon"), "‹")
                ), 8, utsArrayOf(
                    "onClick"
                )),
                createElementVNode("text", utsMapOf("class" to "nav-title"), "发表评价"),
                createElementVNode("view", utsMapOf("class" to "nav-placeholder"))
            )),
            createElementVNode("view", utsMapOf("class" to "product-card"), utsArrayOf(
                createElementVNode("image", utsMapOf("class" to "product-image", "src" to _ctx.productImage, "mode" to "aspectFill"), null, 8, utsArrayOf(
                    "src"
                )),
                createElementVNode("view", utsMapOf("class" to "product-info"), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "product-name"), toDisplayString(_ctx.productName), 1)
                ))
            )),
            createElementVNode("view", utsMapOf("class" to "rating-section"), utsArrayOf(
                createElementVNode("text", utsMapOf("class" to "section-label"), "商品评分"),
                createElementVNode("view", utsMapOf("class" to "stars-row"), utsArrayOf(
                    createElementVNode(Fragment, null, RenderHelpers.renderList(5, fun(i, __key, __index, _cached): Any {
                        return createElementVNode("view", utsMapOf("class" to "star", "key" to i, "onClick" to fun(){
                            _ctx.setRating(i)
                        }
                        ), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                                "star-icon",
                                utsMapOf("star-icon-active" to (i <= _ctx.rating))
                            ))), "★", 2)
                        ), 8, utsArrayOf(
                            "onClick"
                        ))
                    }
                    ), 64),
                    createElementVNode("text", utsMapOf("class" to "rating-text"), toDisplayString(_ctx.getRatingText()), 1)
                ))
            )),
            createElementVNode("view", utsMapOf("class" to "content-section"), utsArrayOf(
                createElementVNode("text", utsMapOf("class" to "section-label"), "评价内容"),
                createElementVNode("textarea", utsMapOf("class" to "content-input", "modelValue" to _ctx.content, "onInput" to fun(`$event`: InputEvent){
                    _ctx.content = `$event`.detail.value
                }
                , "placeholder" to "请分享您对商品的使用感受，帮助其他买家做出选择~", "maxlength" to 500), null, 40, utsArrayOf(
                    "modelValue",
                    "onInput"
                )),
                createElementVNode("text", utsMapOf("class" to "word-count"), toDisplayString(_ctx.content.length) + "/500", 1)
            )),
            createElementVNode("view", utsMapOf("class" to "content-section"), utsArrayOf(
                createElementVNode("text", utsMapOf("class" to "section-label"), "评价图片（选填）"),
                createElementVNode("view", utsMapOf("class" to "image-list"), utsArrayOf(
                    createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.selectedImages, fun(img, idx, __index, _cached): Any {
                        return createElementVNode("view", utsMapOf("class" to "picked-image-wrap", "key" to idx), utsArrayOf(
                            createElementVNode("image", utsMapOf("class" to "picked-image", "src" to img, "mode" to "aspectFill", "onClick" to fun(){
                                _ctx.previewPickedImage(idx)
                            }
                            ), null, 8, utsArrayOf(
                                "src",
                                "onClick"
                            )),
                            createElementVNode("text", utsMapOf("class" to "remove-image", "onClick" to fun(){
                                _ctx.removeImage(idx)
                            }
                            ), "×", 8, utsArrayOf(
                                "onClick"
                            ))
                        ))
                    }
                    ), 128),
                    if (_ctx.selectedImages.length < 6) {
                        createElementVNode("view", utsMapOf("key" to 0, "class" to "add-image", "onClick" to _ctx.chooseImages), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "add-image-text"), "+ 添加图片")
                        ), 8, utsArrayOf(
                            "onClick"
                        ))
                    } else {
                        createCommentVNode("v-if", true)
                    }
                ))
            )),
            createElementVNode("view", utsMapOf("class" to "submit-section"), utsArrayOf(
                createElementVNode("button", utsMapOf("class" to normalizeClass(utsArrayOf(
                    "submit-btn",
                    utsMapOf("submit-btn-disabled" to _ctx.isSubmitting)
                )), "disabled" to _ctx.isSubmitting, "onClick" to _ctx.submitReview), toDisplayString(if (_ctx.isSubmitting) {
                    "提交中..."
                } else {
                    "提交评价"
                }
                ), 11, utsArrayOf(
                    "disabled",
                    "onClick"
                ))
            ))
        ))
    }
    open var orderId: Number by `$data`
    open var productId: Number by `$data`
    open var productName: String by `$data`
    open var productImage: String by `$data`
    open var reviewIndex: Number by `$data`
    open var reviewTotal: Number by `$data`
    open var rating: Number by `$data`
    open var content: String by `$data`
    open var selectedImages: UTSArray<String> by `$data`
    open var isSubmitting: Boolean by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return utsMapOf("orderId" to 0, "productId" to 0, "productName" to "", "productImage" to "", "reviewIndex" to 0, "reviewTotal" to 1, "rating" to 5, "content" to "", "selectedImages" to utsArrayOf<String>(), "isSubmitting" to false)
    }
    open var goBack = ::gen_goBack_fn
    open fun gen_goBack_fn() {
        uni_navigateBack(null)
    }
    open var setRating = ::gen_setRating_fn
    open fun gen_setRating_fn(star: Number) {
        this.rating = star
    }
    open var getRatingText = ::gen_getRatingText_fn
    open fun gen_getRatingText_fn(): String {
        val texts = utsArrayOf(
            "",
            "非常差",
            "差",
            "一般",
            "好",
            "非常好"
        )
        return texts[this.rating] ?: ""
    }
    open var chooseImages = ::gen_chooseImages_fn
    open fun gen_chooseImages_fn() {
        val remain = 6 - this.selectedImages.length
        if (remain <= 0) {
            return
        }
        uni_chooseImage(ChooseImageOptions(count = remain, sizeType = utsArrayOf(
            "compressed"
        ), sourceType = utsArrayOf(
            "album",
            "camera"
        ), success = fun(res){
            val paths = res.tempFilePaths
            if (paths != null && paths.length > 0) {
                this.selectedImages = this.selectedImages.concat(paths)
            }
        }
        ))
    }
    open var removeImage = ::gen_removeImage_fn
    open fun gen_removeImage_fn(index: Number) {
        this.selectedImages.splice(index, 1)
    }
    open var previewPickedImage = ::gen_previewPickedImage_fn
    open fun gen_previewPickedImage_fn(index: Number) {
        uni_previewImage(PreviewImageOptions(urls = this.selectedImages, current = index))
    }
    open var submitReview = ::gen_submitReview_fn
    open fun gen_submitReview_fn() {
        if (this.rating == 0) {
            uni_showToast(ShowToastOptions(title = "请选择评分", icon = "none"))
            return
        }
        this.isSubmitting = true
        val imagesJson = if (this.selectedImages.length > 0) {
            JSON.stringify(this.selectedImages)
        } else {
            ""
        }
        publishReview(ReviewPublishParams(productId = this.productId, orderId = this.orderId, rating = this.rating, content = this.content, images = imagesJson)).then(fun(_res){
            this.isSubmitting = false
            uni__emit("review:submitted", let {
                object : UTSJSONObject() {
                    var orderId = it.orderId
                }
            })
            if (this.reviewIndex < this.reviewTotal - 1) {
                this.goToNextPendingReview()
            } else {
                uni_showToast(ShowToastOptions(title = "评价成功", icon = "success"))
                setTimeout(fun(){
                    uni_navigateBack(null)
                }
                , 1200)
            }
        }
        ).`catch`(fun(err){
            this.isSubmitting = false
            console.error("评价失败:", err, " at pages/order/review.uvue:152")
            uni_showToast(ShowToastOptions(title = "评价失败", icon = "none"))
        }
        )
    }
    open var goToNextPendingReview = ::gen_goToNextPendingReview_fn
    open fun gen_goToNextPendingReview_fn() {
        getOrderDetail(this.orderId).then(fun(res){
            val data = res.data as UTSJSONObject
            val arr = data.getArray("orderItems")
            if (arr == null) {
                uni_showToast(ShowToastOptions(title = "评价成功", icon = "success"))
                setTimeout(fun(){
                    uni_navigateBack(null)
                }
                , 1000)
                return
            }
            val pending: UTSArray<OrderItemVO> = utsArrayOf()
            run {
                var i: Number = 0
                while(i < arr.length){
                    val it = arr[i] as UTSJSONObject
                    if ((it.getBoolean("reviewed") ?: false) != true) {
                        pending.push(OrderItemVO(id = (it.getNumber("id") ?: 0).toInt(), productId = (it.getNumber("productId") ?: 0).toInt(), skuId = (it.getNumber("skuId") ?: 0).toInt(), productName = it.getString("productName") ?: "", skuName = it.getString("skuName") ?: "", productImage = it.getString("productImage") ?: "", price = it.getNumber("price") ?: 0, quantity = (it.getNumber("quantity") ?: 0).toInt(), totalPrice = it.getNumber("totalPrice") ?: 0, reviewed = false))
                    }
                    i++
                }
            }
            if (pending.length === 0) {
                uni_showToast(ShowToastOptions(title = "评价成功", icon = "success"))
                setTimeout(fun(){
                    uni_navigateBack(null)
                }
                , 1000)
                return
            }
            val next = pending[0]
            uni_redirectTo(RedirectToOptions(url = "/pages/order/review?orderId=" + this.orderId + "&productId=" + next.productId + "&productName=" + UTSAndroid.consoleDebugError(encodeURIComponent(next.productName), " at pages/order/review.uvue:190") + "&productImage=" + UTSAndroid.consoleDebugError(encodeURIComponent(next.productImage), " at pages/order/review.uvue:190") + "&reviewIndex=" + (this.reviewIndex + 1) + "&reviewTotal=" + this.reviewTotal))
        }
        ).`catch`(fun(_err){
            uni_showToast(ShowToastOptions(title = "评价成功", icon = "success"))
            setTimeout(fun(){
                uni_navigateBack(null)
            }
            , 1000)
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
                return utsMapOf("review-page" to padStyleMapOf(utsMapOf("backgroundColor" to "#f5f5f5", "minHeight" to "1500rpx", "paddingBottom" to "120rpx")), "nav-bar" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "height" to "88rpx", "backgroundColor" to "#ffffff", "paddingTop" to 0, "paddingRight" to "20rpx", "paddingBottom" to 0, "paddingLeft" to "20rpx", "borderBottomWidth" to "1rpx", "borderBottomStyle" to "solid", "borderBottomColor" to "#f0f0f0")), "nav-back" to padStyleMapOf(utsMapOf("width" to "80rpx", "height" to "80rpx", "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "back-icon" to padStyleMapOf(utsMapOf("fontSize" to "48rpx", "color" to "#333333")), "nav-title" to padStyleMapOf(utsMapOf("fontSize" to "32rpx", "fontWeight" to "700", "color" to "#333333")), "nav-placeholder" to padStyleMapOf(utsMapOf("width" to "80rpx")), "product-card" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "backgroundColor" to "#ffffff", "paddingTop" to "30rpx", "paddingRight" to "30rpx", "paddingBottom" to "30rpx", "paddingLeft" to "30rpx", "marginTop" to "20rpx", "marginRight" to "20rpx", "marginBottom" to "20rpx", "marginLeft" to "20rpx", "borderTopLeftRadius" to "16rpx", "borderTopRightRadius" to "16rpx", "borderBottomRightRadius" to "16rpx", "borderBottomLeftRadius" to "16rpx")), "product-image" to padStyleMapOf(utsMapOf("width" to "120rpx", "height" to "120rpx", "borderTopLeftRadius" to "12rpx", "borderTopRightRadius" to "12rpx", "borderBottomRightRadius" to "12rpx", "borderBottomLeftRadius" to "12rpx", "marginRight" to "20rpx")), "product-info" to padStyleMapOf(utsMapOf("flex" to 1)), "product-name" to padStyleMapOf(utsMapOf("fontSize" to "28rpx", "color" to "#333333", "lineHeight" to 1.4)), "rating-section" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "paddingTop" to "30rpx", "paddingRight" to "30rpx", "paddingBottom" to "30rpx", "paddingLeft" to "30rpx", "marginTop" to 0, "marginRight" to "20rpx", "marginBottom" to "20rpx", "marginLeft" to "20rpx", "borderTopLeftRadius" to "16rpx", "borderTopRightRadius" to "16rpx", "borderBottomRightRadius" to "16rpx", "borderBottomLeftRadius" to "16rpx")), "section-label" to padStyleMapOf(utsMapOf("fontSize" to "28rpx", "color" to "#333333", "fontWeight" to "bold", "marginBottom" to "20rpx")), "image-list" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "flexWrap" to "wrap")), "picked-image-wrap" to padStyleMapOf(utsMapOf("position" to "relative", "marginRight" to "16rpx", "marginBottom" to "16rpx")), "picked-image" to padStyleMapOf(utsMapOf("width" to "140rpx", "height" to "140rpx", "borderTopLeftRadius" to "12rpx", "borderTopRightRadius" to "12rpx", "borderBottomRightRadius" to "12rpx", "borderBottomLeftRadius" to "12rpx", "backgroundColor" to "#f5f5f5")), "remove-image" to padStyleMapOf(utsMapOf("position" to "absolute", "right" to "-10rpx", "top" to "-10rpx", "width" to "36rpx", "height" to "36rpx", "lineHeight" to "36rpx", "textAlign" to "center", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "backgroundColor" to "rgba(0,0,0,0.6)", "color" to "#ffffff", "fontSize" to "24rpx")), "add-image" to padStyleMapOf(utsMapOf("width" to "140rpx", "height" to "140rpx", "borderTopLeftRadius" to "12rpx", "borderTopRightRadius" to "12rpx", "borderBottomRightRadius" to "12rpx", "borderBottomLeftRadius" to "12rpx", "borderTopWidth" to "2rpx", "borderRightWidth" to "2rpx", "borderBottomWidth" to "2rpx", "borderLeftWidth" to "2rpx", "borderTopStyle" to "dashed", "borderRightStyle" to "dashed", "borderBottomStyle" to "dashed", "borderLeftStyle" to "dashed", "borderTopColor" to "#d8d8d8", "borderRightColor" to "#d8d8d8", "borderBottomColor" to "#d8d8d8", "borderLeftColor" to "#d8d8d8", "display" to "flex", "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "#fafafa")), "add-image-text" to padStyleMapOf(utsMapOf("fontSize" to "24rpx", "color" to "#999999")), "stars-row" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center")), "star" to padStyleMapOf(utsMapOf("paddingTop" to "10rpx", "paddingRight" to "10rpx", "paddingBottom" to "10rpx", "paddingLeft" to "10rpx")), "star-icon" to padStyleMapOf(utsMapOf("fontSize" to "56rpx", "color" to "#dddddd")), "star-icon-active" to padStyleMapOf(utsMapOf("color" to "#FFB800")), "rating-text" to padStyleMapOf(utsMapOf("fontSize" to "28rpx", "color" to "#FFB800", "marginLeft" to "20rpx")), "content-section" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "paddingTop" to "30rpx", "paddingRight" to "30rpx", "paddingBottom" to "30rpx", "paddingLeft" to "30rpx", "marginTop" to 0, "marginRight" to "20rpx", "marginBottom" to "20rpx", "marginLeft" to "20rpx", "borderTopLeftRadius" to "16rpx", "borderTopRightRadius" to "16rpx", "borderBottomRightRadius" to "16rpx", "borderBottomLeftRadius" to "16rpx", "position" to "relative")), "content-input" to padStyleMapOf(utsMapOf("width" to "100%", "height" to "240rpx", "fontSize" to "28rpx", "color" to "#333333", "lineHeight" to 1.6, "paddingTop" to "20rpx", "paddingRight" to "20rpx", "paddingBottom" to "20rpx", "paddingLeft" to "20rpx", "backgroundColor" to "#f8f8f8", "borderTopLeftRadius" to "12rpx", "borderTopRightRadius" to "12rpx", "borderBottomRightRadius" to "12rpx", "borderBottomLeftRadius" to "12rpx", "boxSizing" to "border-box")), "word-count" to padStyleMapOf(utsMapOf("position" to "absolute", "right" to "50rpx", "bottom" to "50rpx", "fontSize" to "24rpx", "color" to "#999999")), "submit-section" to padStyleMapOf(utsMapOf("position" to "fixed", "bottom" to 0, "left" to 0, "right" to 0, "paddingTop" to "20rpx", "paddingRight" to "30rpx", "paddingBottom" to "20rpx", "paddingLeft" to "30rpx", "backgroundColor" to "#ffffff", "boxShadow" to "0 -2rpx 10rpx rgba(0, 0, 0, 0.05)")), "submit-btn" to padStyleMapOf(utsMapOf("width" to "100%", "height" to "88rpx", "backgroundColor" to "#0066CC", "color" to "#ffffff", "fontSize" to "32rpx", "fontWeight" to "bold", "borderTopLeftRadius" to "44rpx", "borderTopRightRadius" to "44rpx", "borderBottomRightRadius" to "44rpx", "borderBottomLeftRadius" to "44rpx", "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "submit-btn-disabled" to padStyleMapOf(utsMapOf("backgroundColor" to "#cccccc")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = utsMapOf()
        var emits: Map<String, Any?> = utsMapOf()
        var props = normalizePropsOptions(utsMapOf())
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf()
        var components: Map<String, CreateVueComponent> = utsMapOf()
    }
}
