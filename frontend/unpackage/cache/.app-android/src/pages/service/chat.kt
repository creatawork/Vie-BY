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
open class GenPagesServiceChat : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onLoad(fun(_: OnLoadOptions) {
            this.addServiceMessage("您好！我是您的专属客服，请问有什么可以帮您？", null)
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return createElementVNode("view", utsMapOf("class" to "chat-container"), utsArrayOf(
            createElementVNode("scroll-view", utsMapOf("class" to "message-list", "scroll-y" to "true", "scroll-top" to _ctx.scrollTop, "scroll-into-view" to _ctx.lastMsgId), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "time-divider"), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "time-text"), toDisplayString(_ctx.formatTime(Date())), 1)
                )),
                createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.messages, fun(msg, index, __index, _cached): Any {
                    return createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                        "message-item",
                        utsMapOf("user-msg" to msg.isUser, "service-msg" to !msg.isUser)
                    )), "key" to msg.id, "id" to msg.id), utsArrayOf(
                        createElementVNode("image", utsMapOf("class" to normalizeClass(utsArrayOf(
                            "avatar",
                            utsMapOf("user-avatar" to msg.isUser, "service-avatar" to !msg.isUser)
                        )), "src" to if (msg.isUser) {
                            _ctx.userAvatar
                        } else {
                            _ctx.serviceAvatar
                        }
                        , "mode" to "aspectFill"), null, 10, utsArrayOf(
                            "src"
                        )),
                        createElementVNode("view", utsMapOf("class" to "bubble-wrapper"), utsArrayOf(
                            if (_ctx.getDisplayContent(msg) != "") {
                                createElementVNode("view", utsMapOf("key" to 0, "class" to normalizeClass(utsArrayOf(
                                    "bubble",
                                    utsMapOf("user-bubble" to msg.isUser, "service-bubble" to !msg.isUser)
                                ))), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                                        "msg-content",
                                        utsMapOf("user-text" to msg.isUser, "service-text" to !msg.isUser)
                                    ))), toDisplayString(_ctx.getDisplayContent(msg)), 3)
                                ), 2)
                            } else {
                                createCommentVNode("v-if", true)
                            }
                            ,
                            if (isTrue(!msg.isUser && _ctx.getProducts(msg).length > 0)) {
                                createElementVNode("view", utsMapOf("key" to 1, "class" to "product-list"), utsArrayOf(
                                    createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.getProducts(msg), fun(item, pIndex, __index, _cached): Any {
                                        return createElementVNode("view", utsMapOf("class" to "product-card", "key" to (msg.id + "-" + pIndex), "onClick" to fun(){
                                            _ctx.handleProductTap(item)
                                        }), utsArrayOf(
                                            createElementVNode("image", utsMapOf("class" to "product-image", "src" to item.mainImage, "mode" to "aspectFill"), null, 8, utsArrayOf(
                                                "src"
                                            )),
                                            createElementVNode("view", utsMapOf("class" to "product-info"), utsArrayOf(
                                                createElementVNode("text", utsMapOf("class" to "product-name"), toDisplayString(item.productName), 1),
                                                createElementVNode("text", utsMapOf("class" to "product-desc"), toDisplayString(item.description), 1),
                                                createElementVNode("view", utsMapOf("class" to "product-price"), utsArrayOf(
                                                    createElementVNode("text", utsMapOf("class" to "price-label"), "¥"),
                                                    createElementVNode("text", utsMapOf("class" to "price-value"), toDisplayString(item.currentPrice), 1)
                                                ))
                                            ))
                                        ), 8, utsArrayOf(
                                            "onClick"
                                        ))
                                    }), 128)
                                ))
                            } else {
                                createCommentVNode("v-if", true)
                            }
                        ))
                    ), 10, utsArrayOf(
                        "id"
                    ))
                }
                ), 128),
                if (isTrue(_ctx.isTyping)) {
                    createElementVNode("view", utsMapOf("key" to 0, "class" to "typing-indicator"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "typing-text"), "对方正在输入...")
                    ))
                } else {
                    createCommentVNode("v-if", true)
                }
                ,
                createElementVNode("view", utsMapOf("class" to "bottom-spacer"))
            ), 8, utsArrayOf(
                "scroll-top",
                "scroll-into-view"
            )),
            createElementVNode("view", utsMapOf("class" to "input-area"), utsArrayOf(
                createElementVNode("input", utsMapOf("class" to "input-box", "type" to "text", "modelValue" to _ctx.inputText, "onInput" to fun(`$event`: InputEvent){
                    _ctx.inputText = `$event`.detail.value
                }
                , "placeholder" to "请输入您的问题...", "confirm-type" to "send", "onConfirm" to _ctx.sendMessage), null, 40, utsArrayOf(
                    "modelValue",
                    "onInput",
                    "onConfirm"
                )),
                createElementVNode("view", utsMapOf("class" to "action-group"), utsArrayOf(
                    createElementVNode("button", utsMapOf("class" to "send-btn", "disabled" to _ctx.isSendDisabled(), "onClick" to _ctx.sendMessage), " 发送 ", 8, utsArrayOf(
                        "disabled",
                        "onClick"
                    ))
                ))
            ))
        ))
    }
    open var messages: UTSArray<Message> by `$data`
    open var inputText: String by `$data`
    open var scrollTop: Number by `$data`
    open var lastMsgId: String by `$data`
    open var isTyping: Boolean by `$data`
    open var isStreaming: Boolean by `$data`
    open var streamTask: Any? by `$data`
    open var streamMessageId: String by `$data`
    open var sessionId: String? by `$data`
    open var userAvatar: String by `$data`
    open var serviceAvatar: String by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return utsMapOf("messages" to utsArrayOf<Message>(), "inputText" to "", "scrollTop" to 0, "lastMsgId" to "", "isTyping" to false, "isStreaming" to false, "streamTask" to null as Any?, "streamMessageId" to "" as String, "sessionId" to null as String?, "userAvatar" to "/static/logo.png", "serviceAvatar" to "/static/logo.png")
    }
    open var isSendDisabled = ::gen_isSendDisabled_fn
    open fun gen_isSendDisabled_fn(): Boolean {
        val trimmed = this.inputText.trim()
        return trimmed == ""
    }
    open var handleBack = ::gen_handleBack_fn
    open fun gen_handleBack_fn() {
        uni_navigateBack(null)
    }
    open var formatTime = ::gen_formatTime_fn
    open fun gen_formatTime_fn(date: Date): String {
        val hours = date.getHours().toString(10).padStart(2, "0")
        val minutes = date.getMinutes().toString(10).padStart(2, "0")
        return "" + hours + ":" + minutes
    }
    open var scrollToBottom = ::gen_scrollToBottom_fn
    open fun gen_scrollToBottom_fn() {
        setTimeout(fun(){
            if (this.messages.length > 0) {
                this.lastMsgId = this.messages[this.messages.length - 1].id
            }
        }
        , 100)
    }
    open var sendMessage = ::gen_sendMessage_fn
    open fun gen_sendMessage_fn() {
        val text = this.inputText.trim()
        if (text == "") {
            return
        }
        this.addUserMessage(text)
        this.inputText = ""
        this.stopStreaming()
        this.isTyping = true
        this.isStreaming = true
        val serviceMsgId = this.addServiceStreamingMessage("")
        this.streamMessageId = serviceMsgId
        val handlers = AiStreamHandlers(onChunk = fun(payload: Any){
            val payloadObj = payload as UTSJSONObject
            var data = payloadObj.get("data") as UTSJSONObject?
            if (data == null) {
                data = payloadObj
            }
            val code = payloadObj.getNumber("code") ?: 200
            if (data == null) {
                return
            }
            if (payloadObj.get("code") != null && code != 200) {
                return
            }
            val session = data.getString("sessionId")
            if (session != null && session != "") {
                this.sessionId = session
            }
            val answerChunk = data.getString("answer") ?: ""
            val products = this.parseProducts(data)
            this.appendServiceMessage(serviceMsgId, answerChunk, products)
        }
        , onError = fun(error: Any){
            val message = this.safeErrorMessage(error)
            uni_showToast(ShowToastOptions(title = message, icon = "none"))
            this.finishStreamingWithFallback(serviceMsgId)
        }
        , onComplete = fun(){
            this.finishStreaming(serviceMsgId)
        }
        )
        this.streamTask = chatWithAiStream(text, this.sessionId, handlers)
    }
    open var addUserMessage = ::gen_addUserMessage_fn
    open fun gen_addUserMessage_fn(content: String) {
        val msg = Message(id = "msg-" + Date.now(), content = content, isUser = true, timestamp = Date.now(), products = null)
        this.messages.push(msg)
        this.scrollToBottom()
    }
    open var addServiceMessage = ::gen_addServiceMessage_fn
    open fun gen_addServiceMessage_fn(content: String, products: UTSArray<ProductCard>?) {
        val msg = Message(id = "msg-" + Date.now(), content = content, isUser = false, timestamp = Date.now(), products = products)
        this.messages.push(msg)
        this.scrollToBottom()
    }
    open var addServiceStreamingMessage = ::gen_addServiceStreamingMessage_fn
    open fun gen_addServiceStreamingMessage_fn(content: String): String {
        val msg = Message(id = "msg-" + Date.now(), content = content, isUser = false, timestamp = Date.now(), products = null)
        this.messages.push(msg)
        this.scrollToBottom()
        return msg.id
    }
    open var appendServiceMessage = ::gen_appendServiceMessage_fn
    open fun gen_appendServiceMessage_fn(messageId: String, chunk: String, products: UTSArray<ProductCard>?) {
        run {
            var i: Number = 0
            while(i < this.messages.length){
                val msg = this.messages[i]
                if (msg.id == messageId) {
                    msg.content = msg.content + chunk
                    if (products != null && products.length > 0) {
                        msg.products = this.mergeProducts(msg.products, products)
                    }
                    break
                }
                i++
            }
        }
        this.scrollToBottom()
    }
    open var finishStreaming = ::gen_finishStreaming_fn
    open fun gen_finishStreaming_fn(messageId: String) {
        this.isTyping = false
        this.isStreaming = false
        this.streamTask = null
        this.streamMessageId = messageId
    }
    open var finishStreamingWithFallback = ::gen_finishStreamingWithFallback_fn
    open fun gen_finishStreamingWithFallback_fn(messageId: String) {
        var hasContent = false
        run {
            var i: Number = 0
            while(i < this.messages.length){
                val msg = this.messages[i]
                if (msg.id == messageId) {
                    hasContent = msg.content.trim() != ""
                    if (!hasContent) {
                        msg.content = "客服暂时无法回答，请稍后再试。"
                    }
                    break
                }
                i++
            }
        }
        this.finishStreaming(messageId)
    }
    open var stopStreaming = ::gen_stopStreaming_fn
    open fun gen_stopStreaming_fn() {
        val task = this.streamTask as UTSJSONObject?
        if (task != null) {
            if (task.get("abort") != null) {
                val abortFn = task.get("abort") as () -> Unit
                abortFn()
            }
        }
        this.isStreaming = false
        this.isTyping = false
        this.streamTask = null
    }
    open var parseProducts = ::gen_parseProducts_fn
    open fun gen_parseProducts_fn(data: UTSJSONObject): UTSArray<ProductCard>? {
        val productsArray = data.getArray("products")
        if (productsArray == null || productsArray.length == 0) {
            return null
        }
        val products: UTSArray<ProductCard> = utsArrayOf()
        run {
            var i: Number = 0
            while(i < productsArray.length){
                val item = productsArray[i] as UTSJSONObject
                products.push(ProductCard(productId = item.getNumber("productId") ?: 0, productName = item.getString("productName") ?: "", mainImage = item.getString("mainImage") ?: "", currentPrice = item.getNumber("currentPrice") ?: 0, description = item.getString("description") ?: "", detailUrl = item.getString("detailUrl") ?: ""))
                i++
            }
        }
        return if (products.length > 0) {
            products
        } else {
            null
        }
    }
    open var getProducts = ::gen_getProducts_fn
    open fun gen_getProducts_fn(message: Message): UTSArray<ProductCard> {
        return message.products ?: utsArrayOf()
    }
    open var safeErrorMessage = ::gen_safeErrorMessage_fn
    open fun gen_safeErrorMessage_fn(error: Any?): String {
        if (error == null) {
            return "客服开小差了"
        }
        val message = error.toString()
        if (message != null && message != "") {
            return message
        }
        return "客服开小差了"
    }
    open var getDisplayContent = ::gen_getDisplayContent_fn
    open fun gen_getDisplayContent_fn(message: Message): String {
        if (message.isUser) {
            return message.content
        }
        val products = message.products
        if (products == null || products.length == 0) {
            return message.content
        }
        return ""
    }
    open var mergeProducts = ::gen_mergeProducts_fn
    open fun gen_mergeProducts_fn(existing: UTSArray<ProductCard>?, incoming: UTSArray<ProductCard>): UTSArray<ProductCard> {
        val merged: UTSArray<ProductCard> = utsArrayOf()
        val seen: Map<Number, Boolean> = Map<Number, Boolean>()
        if (existing != null) {
            run {
                var i: Number = 0
                while(i < existing.length){
                    val item = existing[i]
                    merged.push(item)
                    seen.set(item.productId, true)
                    i++
                }
            }
        }
        run {
            var i: Number = 0
            while(i < incoming.length){
                val item = incoming[i]
                if (!seen.has(item.productId)) {
                    merged.push(item)
                    seen.set(item.productId, true)
                }
                i++
            }
        }
        return merged
    }
    open var handleProductTap = ::gen_handleProductTap_fn
    open fun gen_handleProductTap_fn(product: ProductCard) {
        val detailUrl = product.detailUrl
        if (detailUrl != null && detailUrl != "" && detailUrl.startsWith("/pages/")) {
            uni_navigateTo(NavigateToOptions(url = detailUrl, fail = fun(_){
                this.navigateToProductDetail(product.productId)
            }
            ))
            return
        }
        this.navigateToProductDetail(product.productId)
    }
    open var navigateToProductDetail = ::gen_navigateToProductDetail_fn
    open fun gen_navigateToProductDetail_fn(productId: Number) {
        if (productId != null && productId > 0) {
            uni_navigateTo(NavigateToOptions(url = "/pages/product/detail?id=" + productId))
        }
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
                return utsMapOf("chat-container" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "column", "backgroundColor" to "#f5f6fa", "height" to "100%")), "message-list" to padStyleMapOf(utsMapOf("flex" to 1, "paddingTop" to 12, "paddingRight" to 16, "paddingBottom" to 76, "paddingLeft" to 16)), "time-divider" to padStyleMapOf(utsMapOf("display" to "flex", "justifyContent" to "center", "marginTop" to 14, "marginRight" to 0, "marginBottom" to 14, "marginLeft" to 0)), "time-text" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#8d93a1", "backgroundColor" to "#eef1f6", "paddingTop" to 4, "paddingRight" to 10, "paddingBottom" to 4, "paddingLeft" to 10, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12)), "message-item" to padStyleMapOf(utsMapOf("display" to "flex", "marginBottom" to 18)), "service-msg" to padStyleMapOf(utsMapOf("flexDirection" to "row")), "service-avatar" to padStyleMapOf(utsMapOf("marginRight" to 10)), "service-bubble" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 4)), "service-text" to padStyleMapOf(utsMapOf("color" to "#2f3440")), "user-msg" to padStyleMapOf(utsMapOf("flexDirection" to "row-reverse")), "user-avatar" to padStyleMapOf(utsMapOf("marginLeft" to 10)), "user-bubble" to padStyleMapOf(utsMapOf("backgroundColor" to "#2563eb", "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomLeftRadius" to 14, "borderBottomRightRadius" to 4)), "user-text" to padStyleMapOf(utsMapOf("color" to "#ffffff")), "avatar" to padStyleMapOf(utsMapOf("width" to 36, "height" to 36, "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "backgroundColor" to "#e8ecf2")), "bubble-wrapper" to padStyleMapOf(utsMapOf("maxWidth" to 280)), "bubble" to padStyleMapOf(utsMapOf("paddingTop" to 12, "paddingRight" to 14, "paddingBottom" to 12, "paddingLeft" to 14, "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "boxShadow" to "0 6px 18px rgba(31, 36, 48, 0.08)")), "msg-content" to padStyleMapOf(utsMapOf("fontSize" to 15, "lineHeight" to 1.6, "color" to "#1f2430")), "product-list" to padStyleMapOf(utsMapOf("marginTop" to 12)), "product-card" to padStyleMapOf(utsMapOf("display" to "flex", "backgroundColor" to "#ffffff", "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "marginBottom" to 10, "boxShadow" to "0 10px 18px rgba(31, 36, 48, 0.08)")), "product-image" to padStyleMapOf(utsMapOf("width" to 68, "height" to 68, "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10, "backgroundColor" to "#f2f2f2", "marginRight" to 12)), "product-info" to padStyleMapOf(utsMapOf("flex" to 1, "display" to "flex", "flexDirection" to "column")), "product-name" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#1f2430", "fontWeight" to "700", "marginBottom" to 4)), "product-desc" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#8d93a1", "lineHeight" to 1.4, "marginBottom" to 8)), "product-price" to padStyleMapOf(utsMapOf("display" to "flex", "alignItems" to "center")), "price-label" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#ff5a5f")), "price-value" to padStyleMapOf(utsMapOf("fontSize" to 16, "color" to "#ff5a5f", "fontWeight" to "700")), "typing-indicator" to padStyleMapOf(utsMapOf("marginLeft" to 52, "marginBottom" to 14)), "typing-text" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#8d93a1")), "bottom-spacer" to padStyleMapOf(utsMapOf("height" to 0)), "input-area" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "paddingTop" to 12, "paddingLeft" to 16, "paddingRight" to 16, "paddingBottom" to "env(safe-area-inset-bottom)", "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "borderTopWidth" to 1, "borderTopStyle" to "solid", "borderTopColor" to "#eef0f4", "position" to "fixed", "left" to 0, "right" to 0, "bottom" to 0, "zIndex" to 10)), "input-box" to padStyleMapOf(utsMapOf("flex" to 1, "width" to 0, "height" to 42, "backgroundColor" to "#f2f4f8", "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "paddingTop" to 0, "paddingRight" to 16, "paddingBottom" to 0, "paddingLeft" to 16, "fontSize" to 14, "color" to "#1f2430")), "action-group" to padStyleMapOf(utsMapOf("display" to "flex", "alignItems" to "center", "marginLeft" to 10, "flexShrink" to 0)), "send-btn" to padStyleMapOf(utsMapOf("width" to 64, "height" to 36, "lineHeight" to "36px", "backgroundColor" to "#2563eb", "color" to "#ffffff", "fontSize" to 14, "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "paddingTop" to 0, "paddingRight" to 0, "paddingBottom" to 0, "paddingLeft" to 0, "display" to "flex", "alignItems" to "center", "justifyContent" to "center", "boxShadow" to "0 6px 16px rgba(37, 99, 235, 0.3)")), "stop-btn" to padStyleMapOf(utsMapOf("marginLeft" to 8, "width" to 64, "height" to 36, "lineHeight" to "36px", "backgroundColor" to "#ff5a5f", "color" to "#ffffff", "fontSize" to 14, "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "paddingTop" to 0, "paddingRight" to 0, "paddingBottom" to 0, "paddingLeft" to 0, "display" to "flex", "alignItems" to "center", "justifyContent" to "center", "boxShadow" to "0 6px 16px rgba(255, 90, 95, 0.3)")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = utsMapOf()
        var emits: Map<String, Any?> = utsMapOf()
        var props = normalizePropsOptions(utsMapOf())
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf()
        var components: Map<String, CreateVueComponent> = utsMapOf()
    }
}
