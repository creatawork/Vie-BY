
import { chatWithAiStream, type AiStreamHandlers } from '@/api/ai'

type ProductCard = { __$originalPosition?: UTSSourceMapPosition<"ProductCard", "pages/service/chat.uvue", 89, 6>;
	productId : number
	productName : string
	mainImage : string
	currentPrice : number
	description : string
	detailUrl : string
}

type Message = { __$originalPosition?: UTSSourceMapPosition<"Message", "pages/service/chat.uvue", 98, 6>;
	id : string
	content : string
	isUser : boolean
	timestamp : number
	products : ProductCard[] | null
}

const __sfc__ = defineComponent({
	data() {
		return {
			messages: [] as Message[],
			inputText: '',
			scrollTop: 0,
			lastMsgId: '',
			isTyping: false,
			isStreaming: false,
			streamTask: null as any | null,
			streamMessageId: '' as string,
			sessionId: null as string | null,
			userAvatar: '/static/logo.png', // 实际应从用户信息获取
			serviceAvatar: '/static/logo.png' // 应该有个客服头像
		}
	},
	onLoad() {
		// 发送欢迎语
		this.addServiceMessage('您好！我是您的专属客服，请问有什么可以帮您？', null)
	},
	methods: {
		// 检查发送按钮是否应该禁用
		isSendDisabled() : boolean {
			const trimmed = this.inputText.trim()
			return trimmed == ''
		},
		handleBack() {
			uni.navigateBack()
		},
		formatTime(date : Date) : string {
			const hours = date.getHours().toString().padStart(2, '0')
			const minutes = date.getMinutes().toString().padStart(2, '0')
			return `${hours}:${minutes}`
		},
		scrollToBottom() {
			setTimeout(() => {
				if (this.messages.length > 0) {
					this.lastMsgId = this.messages[this.messages.length - 1].id
					// 也可以使用 scrollTop，但 scroll-into-view 更方便
				}
			}, 100)
		},
		sendMessage() {
			const text = this.inputText.trim()
			if (text == '') return

			// 添加用户消息
			this.addUserMessage(text)
			this.inputText = ''

			this.stopStreaming()

			// 调用 AI 客服回复（流式）
			this.isTyping = true
			this.isStreaming = true
			const serviceMsgId = this.addServiceStreamingMessage('')
			this.streamMessageId = serviceMsgId

			const handlers : AiStreamHandlers = {
				onChunk: (payload : any) => {
					const payloadObj = payload as UTSJSONObject
					let data = payloadObj.get('data') as UTSJSONObject | null
					if (data == null) {
						data = payloadObj
					}
					const code = payloadObj.getNumber('code') ?? 200
					if (data == null) {
						return
					}
					if (payloadObj.get('code') != null && code != 200) {
						return
					}
					const session = data.getString('sessionId')
					if (session != null && session != '') {
						this.sessionId = session
					}
					const answerChunk = data.getString('answer') ?? ''
					const products = this.parseProducts(data)
					this.appendServiceMessage(serviceMsgId, answerChunk, products)
				},
				onError: (error : any) => {
					const message = this.safeErrorMessage(error)
					uni.showToast({ title: message, icon: 'none' })
					this.finishStreamingWithFallback(serviceMsgId)
				},
				onComplete: () => {
					this.finishStreaming(serviceMsgId)
				}
			}
			this.streamTask = chatWithAiStream(text, this.sessionId, handlers)
		},
		addUserMessage(content : string) {
			const msg : Message = {
				id: 'msg-' + Date.now(),
				content: content,
				isUser: true,
				timestamp: Date.now(),
				products: null
			}
			this.messages.push(msg)
			this.scrollToBottom()
		},
		addServiceMessage(content : string, products : ProductCard[] | null) {
			const msg : Message = {
				id: 'msg-' + Date.now(),
				content: content,
				isUser: false,
				timestamp: Date.now(),
				products: products
			}
			this.messages.push(msg)
			this.scrollToBottom()
		},
		addServiceStreamingMessage(content : string) : string {
			const msg : Message = {
				id: 'msg-' + Date.now(),
				content: content,
				isUser: false,
				timestamp: Date.now(),
				products: null
			}
			this.messages.push(msg)
			this.scrollToBottom()
			return msg.id
		},
		appendServiceMessage(messageId : string, chunk : string, products : ProductCard[] | null) {
			for (let i = 0; i < this.messages.length; i++) {
				const msg = this.messages[i]
				if (msg.id == messageId) {
					msg.content = msg.content + chunk
					if (products != null && products.length > 0) {
						msg.products = this.mergeProducts(msg.products, products)
					}
					break
				}
			}
			this.scrollToBottom()
		},
		finishStreaming(messageId : string) {
			this.isTyping = false
			this.isStreaming = false
			this.streamTask = null
			this.streamMessageId = messageId
		},
		finishStreamingWithFallback(messageId : string) {
			let hasContent = false
			for (let i = 0; i < this.messages.length; i++) {
				const msg = this.messages[i]
				if (msg.id == messageId) {
					hasContent = msg.content.trim() != ''
					if (!hasContent) {
						msg.content = '客服暂时无法回答，请稍后再试。'
					}
					break
				}
			}
			this.finishStreaming(messageId)
		},
		stopStreaming() {
			const task = this.streamTask as UTSJSONObject | null
			if (task != null) {
				if (task.get('abort') != null) {
					const abortFn = task.get('abort') as () => void
					abortFn()
				}
			}
			this.isStreaming = false
			this.isTyping = false
			this.streamTask = null
		},
		parseProducts(data : UTSJSONObject) : ProductCard[] | null {
			const productsArray = data.getArray('products')
			if (productsArray == null || productsArray.length == 0) {
				return null
			}
			const products : ProductCard[] = []
			for (let i = 0; i < productsArray.length; i++) {
				const item = productsArray[i] as UTSJSONObject
				products.push({
					productId: item.getNumber('productId') ?? 0,
					productName: item.getString('productName') ?? '',
					mainImage: item.getString('mainImage') ?? '',
					currentPrice: item.getNumber('currentPrice') ?? 0,
					description: item.getString('description') ?? '',
					detailUrl: item.getString('detailUrl') ?? ''
				})
			}
			return products.length > 0 ? products : null
		},
			getProducts(message : Message) : ProductCard[] {
				return message.products ?? []
			},
			safeErrorMessage(error : any | null) : string {
				if (error == null) {
					return '客服开小差了'
				}
				const message = error.toString()
				if (message != null && message != '') {
					return message
				}
				return '客服开小差了'
			},
			getDisplayContent(message : Message) : string {
				if (message.isUser) {
					return message.content
				}
				const products = message.products
				if (products == null || products.length == 0) {
					return message.content
				}
				return ''
			},
			mergeProducts(existing : ProductCard[] | null, incoming : ProductCard[]) : ProductCard[] {
				const merged : ProductCard[] = []
				const seen : Map<number, boolean> = new Map<number, boolean>()
				if (existing != null) {
					for (let i = 0; i < existing.length; i++) {
						const item = existing[i]
						merged.push(item)
						seen.set(item.productId, true)
					}
				}
				for (let i = 0; i < incoming.length; i++) {
					const item = incoming[i]
					if (!seen.has(item.productId)) {
						merged.push(item)
						seen.set(item.productId, true)
					}
				}
				return merged
			},
			handleProductTap(product : ProductCard) {
				const detailUrl = product.detailUrl
				if (detailUrl != null && detailUrl != '' && detailUrl.startsWith('/pages/')) {
					uni.navigateTo({
						url: detailUrl,
						fail: () => {
							this.navigateToProductDetail(product.productId)
						}
					})
					return
				}
				this.navigateToProductDetail(product.productId)
			},
			navigateToProductDetail(productId : number) {
				if (productId != null && productId > 0) {
					uni.navigateTo({ url: `/pages/product/detail?id=${productId}` })
				}
			}
	}
})

export default __sfc__
function GenPagesServiceChatRender(this: InstanceType<typeof __sfc__>): any | null {
const _ctx = this
const _cache = this.$.renderCache
  return createElementVNode("view", utsMapOf({ class: "chat-container" }), [
    createElementVNode("scroll-view", utsMapOf({
      class: "message-list",
      "scroll-y": "true",
      "scroll-top": _ctx.scrollTop,
      "scroll-into-view": _ctx.lastMsgId
    }), [
      createElementVNode("view", utsMapOf({ class: "time-divider" }), [
        createElementVNode("text", utsMapOf({ class: "time-text" }), toDisplayString(_ctx.formatTime(new Date())), 1 /* TEXT */)
      ]),
      createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.messages, (msg, index, __index, _cached): any => {
        return createElementVNode("view", utsMapOf({
          class: normalizeClass(["message-item", utsMapOf({ 'user-msg': msg.isUser, 'service-msg': !msg.isUser })]),
          key: msg.id,
          id: msg.id
        }), [
          createElementVNode("image", utsMapOf({
            class: normalizeClass(["avatar", utsMapOf({ 'user-avatar': msg.isUser, 'service-avatar': !msg.isUser })]),
            src: msg.isUser ? _ctx.userAvatar : _ctx.serviceAvatar,
            mode: "aspectFill"
          }), null, 10 /* CLASS, PROPS */, ["src"]),
          createElementVNode("view", utsMapOf({ class: "bubble-wrapper" }), [
            _ctx.getDisplayContent(msg) != ''
              ? createElementVNode("view", utsMapOf({
                  key: 0,
                  class: normalizeClass(["bubble", utsMapOf({ 'user-bubble': msg.isUser, 'service-bubble': !msg.isUser })])
                }), [
                  createElementVNode("text", utsMapOf({
                    class: normalizeClass(["msg-content", utsMapOf({ 'user-text': msg.isUser, 'service-text': !msg.isUser })])
                  }), toDisplayString(_ctx.getDisplayContent(msg)), 3 /* TEXT, CLASS */)
                ], 2 /* CLASS */)
              : createCommentVNode("v-if", true),
            isTrue(!msg.isUser && _ctx.getProducts(msg).length > 0)
              ? createElementVNode("view", utsMapOf({
                  key: 1,
                  class: "product-list"
                }), [
                  createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.getProducts(msg), (item, pIndex, __index, _cached): any => {
                    return createElementVNode("view", utsMapOf({
                      class: "product-card",
                      key: msg.id + '-' + pIndex,
                      onClick: () => {_ctx.handleProductTap(item)}
                    }), [
                      createElementVNode("image", utsMapOf({
                        class: "product-image",
                        src: item.mainImage,
                        mode: "aspectFill"
                      }), null, 8 /* PROPS */, ["src"]),
                      createElementVNode("view", utsMapOf({ class: "product-info" }), [
                        createElementVNode("text", utsMapOf({ class: "product-name" }), toDisplayString(item.productName), 1 /* TEXT */),
                        createElementVNode("text", utsMapOf({ class: "product-desc" }), toDisplayString(item.description), 1 /* TEXT */),
                        createElementVNode("view", utsMapOf({ class: "product-price" }), [
                          createElementVNode("text", utsMapOf({ class: "price-label" }), "¥"),
                          createElementVNode("text", utsMapOf({ class: "price-value" }), toDisplayString(item.currentPrice), 1 /* TEXT */)
                        ])
                      ])
                    ], 8 /* PROPS */, ["onClick"])
                  }), 128 /* KEYED_FRAGMENT */)
                ])
              : createCommentVNode("v-if", true)
          ])
        ], 10 /* CLASS, PROPS */, ["id"])
      }), 128 /* KEYED_FRAGMENT */),
      isTrue(_ctx.isTyping)
        ? createElementVNode("view", utsMapOf({
            key: 0,
            class: "typing-indicator"
          }), [
            createElementVNode("text", utsMapOf({ class: "typing-text" }), "对方正在输入...")
          ])
        : createCommentVNode("v-if", true),
      createElementVNode("view", utsMapOf({ class: "bottom-spacer" }))
    ], 8 /* PROPS */, ["scroll-top", "scroll-into-view"]),
    createElementVNode("view", utsMapOf({ class: "input-area" }), [
      createElementVNode("input", utsMapOf({
        class: "input-box",
        type: "text",
        modelValue: _ctx.inputText,
        onInput: ($event: InputEvent) => {(_ctx.inputText) = $event.detail.value},
        placeholder: "请输入您的问题...",
        "confirm-type": "send",
        onConfirm: _ctx.sendMessage
      }), null, 40 /* PROPS, NEED_HYDRATION */, ["modelValue", "onInput", "onConfirm"]),
      createElementVNode("view", utsMapOf({ class: "action-group" }), [
        createElementVNode("button", utsMapOf({
          class: "send-btn",
          disabled: _ctx.isSendDisabled(),
          onClick: _ctx.sendMessage
        }), " 发送 ", 8 /* PROPS */, ["disabled", "onClick"])
      ])
    ])
  ])
}
const GenPagesServiceChatStyles = [utsMapOf([["chat-container", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "column"], ["backgroundColor", "#f5f6fa"], ["height", "100%"]]))], ["message-list", padStyleMapOf(utsMapOf([["flex", 1], ["paddingTop", 12], ["paddingRight", 16], ["paddingBottom", 76], ["paddingLeft", 16]]))], ["time-divider", padStyleMapOf(utsMapOf([["display", "flex"], ["justifyContent", "center"], ["marginTop", 14], ["marginRight", 0], ["marginBottom", 14], ["marginLeft", 0]]))], ["time-text", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "#8d93a1"], ["backgroundColor", "#eef1f6"], ["paddingTop", 4], ["paddingRight", 10], ["paddingBottom", 4], ["paddingLeft", 10], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12]]))], ["message-item", padStyleMapOf(utsMapOf([["display", "flex"], ["marginBottom", 18]]))], ["service-msg", padStyleMapOf(utsMapOf([["flexDirection", "row"]]))], ["service-avatar", padStyleMapOf(utsMapOf([["marginRight", 10]]))], ["service-bubble", padStyleMapOf(utsMapOf([["backgroundColor", "#ffffff"], ["borderTopLeftRadius", 14], ["borderTopRightRadius", 14], ["borderBottomRightRadius", 14], ["borderBottomLeftRadius", 4]]))], ["service-text", padStyleMapOf(utsMapOf([["color", "#2f3440"]]))], ["user-msg", padStyleMapOf(utsMapOf([["flexDirection", "row-reverse"]]))], ["user-avatar", padStyleMapOf(utsMapOf([["marginLeft", 10]]))], ["user-bubble", padStyleMapOf(utsMapOf([["backgroundColor", "#2563eb"], ["borderTopLeftRadius", 14], ["borderTopRightRadius", 14], ["borderBottomLeftRadius", 14], ["borderBottomRightRadius", 4]]))], ["user-text", padStyleMapOf(utsMapOf([["color", "#ffffff"]]))], ["avatar", padStyleMapOf(utsMapOf([["width", 36], ["height", 36], ["borderTopLeftRadius", 18], ["borderTopRightRadius", 18], ["borderBottomRightRadius", 18], ["borderBottomLeftRadius", 18], ["backgroundColor", "#e8ecf2"]]))], ["bubble-wrapper", padStyleMapOf(utsMapOf([["maxWidth", 280]]))], ["bubble", padStyleMapOf(utsMapOf([["paddingTop", 12], ["paddingRight", 14], ["paddingBottom", 12], ["paddingLeft", 14], ["borderTopLeftRadius", 14], ["borderTopRightRadius", 14], ["borderBottomRightRadius", 14], ["borderBottomLeftRadius", 14], ["boxShadow", "0 6px 18px rgba(31, 36, 48, 0.08)"]]))], ["msg-content", padStyleMapOf(utsMapOf([["fontSize", 15], ["lineHeight", 1.6], ["color", "#1f2430"]]))], ["product-list", padStyleMapOf(utsMapOf([["marginTop", 12]]))], ["product-card", padStyleMapOf(utsMapOf([["display", "flex"], ["backgroundColor", "#ffffff"], ["borderTopLeftRadius", 14], ["borderTopRightRadius", 14], ["borderBottomRightRadius", 14], ["borderBottomLeftRadius", 14], ["paddingTop", 12], ["paddingRight", 12], ["paddingBottom", 12], ["paddingLeft", 12], ["marginBottom", 10], ["boxShadow", "0 10px 18px rgba(31, 36, 48, 0.08)"]]))], ["product-image", padStyleMapOf(utsMapOf([["width", 68], ["height", 68], ["borderTopLeftRadius", 10], ["borderTopRightRadius", 10], ["borderBottomRightRadius", 10], ["borderBottomLeftRadius", 10], ["backgroundColor", "#f2f2f2"], ["marginRight", 12]]))], ["product-info", padStyleMapOf(utsMapOf([["flex", 1], ["display", "flex"], ["flexDirection", "column"]]))], ["product-name", padStyleMapOf(utsMapOf([["fontSize", 14], ["color", "#1f2430"], ["fontWeight", "700"], ["marginBottom", 4]]))], ["product-desc", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "#8d93a1"], ["lineHeight", 1.4], ["marginBottom", 8]]))], ["product-price", padStyleMapOf(utsMapOf([["display", "flex"], ["alignItems", "center"]]))], ["price-label", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "#ff5a5f"]]))], ["price-value", padStyleMapOf(utsMapOf([["fontSize", 16], ["color", "#ff5a5f"], ["fontWeight", "700"]]))], ["typing-indicator", padStyleMapOf(utsMapOf([["marginLeft", 52], ["marginBottom", 14]]))], ["typing-text", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "#8d93a1"]]))], ["bottom-spacer", padStyleMapOf(utsMapOf([["height", 0]]))], ["input-area", padStyleMapOf(utsMapOf([["backgroundColor", "#ffffff"], ["paddingTop", 12], ["paddingLeft", 16], ["paddingRight", 16], ["paddingBottom", "env(safe-area-inset-bottom)"], ["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["borderTopWidth", 1], ["borderTopStyle", "solid"], ["borderTopColor", "#eef0f4"], ["position", "fixed"], ["left", 0], ["right", 0], ["bottom", 0], ["zIndex", 10]]))], ["input-box", padStyleMapOf(utsMapOf([["flex", 1], ["width", 0], ["height", 42], ["backgroundColor", "#f2f4f8"], ["borderTopLeftRadius", 22], ["borderTopRightRadius", 22], ["borderBottomRightRadius", 22], ["borderBottomLeftRadius", 22], ["paddingTop", 0], ["paddingRight", 16], ["paddingBottom", 0], ["paddingLeft", 16], ["fontSize", 14], ["color", "#1f2430"]]))], ["action-group", padStyleMapOf(utsMapOf([["display", "flex"], ["alignItems", "center"], ["marginLeft", 10], ["flexShrink", 0]]))], ["send-btn", padStyleMapOf(utsMapOf([["width", 64], ["height", 36], ["lineHeight", "36px"], ["backgroundColor", "#2563eb"], ["color", "#ffffff"], ["fontSize", 14], ["borderTopLeftRadius", 18], ["borderTopRightRadius", 18], ["borderBottomRightRadius", 18], ["borderBottomLeftRadius", 18], ["paddingTop", 0], ["paddingRight", 0], ["paddingBottom", 0], ["paddingLeft", 0], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"], ["boxShadow", "0 6px 16px rgba(37, 99, 235, 0.3)"]]))], ["stop-btn", padStyleMapOf(utsMapOf([["marginLeft", 8], ["width", 64], ["height", 36], ["lineHeight", "36px"], ["backgroundColor", "#ff5a5f"], ["color", "#ffffff"], ["fontSize", 14], ["borderTopLeftRadius", 18], ["borderTopRightRadius", 18], ["borderBottomRightRadius", 18], ["borderBottomLeftRadius", 18], ["paddingTop", 0], ["paddingRight", 0], ["paddingBottom", 0], ["paddingLeft", 0], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"], ["boxShadow", "0 6px 16px rgba(255, 90, 95, 0.3)"]]))]])]
