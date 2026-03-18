
import { publishReview } from '@/api/review'
import { getOrderDetail, type OrderItemVO } from '@/api/order'

const __sfc__ = defineComponent({
	data() {
		return {
			orderId: 0,
			productId: 0,
			productName: '',
			productImage: '',
			reviewIndex: 0,
			reviewTotal: 1,
			rating: 5,
			content: '',
			selectedImages: [] as string[],
			isSubmitting: false
		}
	},
	onLoad(options : any) {
		const opts = options as UTSJSONObject
		this.orderId = parseInt(opts.getString('orderId') ?? '0')
		this.productId = parseInt(opts.getString('productId') ?? '0')
		this.reviewIndex = parseInt(opts.getString('reviewIndex') ?? '0')
		this.reviewTotal = parseInt(opts.getString('reviewTotal') ?? '1')
		const nameParam : string = opts.getString('productName') ?? ''
		const imageParam : string = opts.getString('productImage') ?? ''
		this.productName = UTSAndroid.consoleDebugError(decodeURIComponent(nameParam), " at pages/order/review.uvue:93") ?? ''
		this.productImage = UTSAndroid.consoleDebugError(decodeURIComponent(imageParam), " at pages/order/review.uvue:94") ?? ''
	},
	methods: {
		goBack() {
			uni.navigateBack()
		},
		setRating(star : number) {
			this.rating = star
		},
		getRatingText() : string {
			const texts = ['', '非常差', '差', '一般', '好', '非常好']
			return texts[this.rating] ?? ''
		},
		chooseImages() {
			const remain = 6 - this.selectedImages.length
			if (remain <= 0) return
			uni.chooseImage({
				count: remain,
				sizeType: ['compressed'],
				sourceType: ['album', 'camera'],
				success: (res) => {
					const paths = res.tempFilePaths
					if (paths != null && paths.length > 0) {
						this.selectedImages = this.selectedImages.concat(paths)
					}
				}
			})
		},
		removeImage(index : number) {
			this.selectedImages.splice(index, 1)
		},
		previewPickedImage(index : number) {
			uni.previewImage({ urls: this.selectedImages, current: index })
		},
		submitReview() {
			if (this.rating == 0) {
				uni.showToast({ title: '请选择评分', icon: 'none' })
				return
			}
			this.isSubmitting = true
			const imagesJson = this.selectedImages.length > 0 ? JSON.stringify(this.selectedImages) : ''
			publishReview({
				productId: this.productId,
				orderId: this.orderId,
				rating: this.rating,
				content: this.content,
				images: imagesJson
			}).then((_res) => {
				this.isSubmitting = false
				uni.$emit('review:submitted', { orderId: this.orderId })
				if (this.reviewIndex < this.reviewTotal - 1) {
					this.goToNextPendingReview()
				} else {
					uni.showToast({ title: '评价成功', icon: 'success' })
					setTimeout(() => { uni.navigateBack() }, 1200)
				}
			}).catch((err) => {
				this.isSubmitting = false
				console.error('评价失败:', err, " at pages/order/review.uvue:152")
				uni.showToast({ title: '评价失败', icon: 'none' })
			})
		},
		goToNextPendingReview() {
			getOrderDetail(this.orderId).then((res) => {
				const data = res.data as UTSJSONObject
				const arr = data.getArray('orderItems')
				if (arr == null) {
					uni.showToast({ title: '评价成功', icon: 'success' })
					setTimeout(() => { uni.navigateBack() }, 1000)
					return
				}
				const pending : OrderItemVO[] = []
				for (let i = 0; i < arr.length; i++) {
					const it = arr[i] as UTSJSONObject
					if ((it.getBoolean('reviewed') ?? false) != true) {
						pending.push({
							id: (it.getNumber('id') ?? 0).toInt(),
							productId: (it.getNumber('productId') ?? 0).toInt(),
							skuId: (it.getNumber('skuId') ?? 0).toInt(),
							productName: it.getString('productName') ?? '',
							skuName: it.getString('skuName') ?? '',
							productImage: it.getString('productImage') ?? '',
							price: it.getNumber('price') ?? 0,
							quantity: (it.getNumber('quantity') ?? 0).toInt(),
							totalPrice: it.getNumber('totalPrice') ?? 0,
							reviewed: false
						} as OrderItemVO)
					}
				}
				if (pending.length === 0) {
					uni.showToast({ title: '评价成功', icon: 'success' })
					setTimeout(() => { uni.navigateBack() }, 1000)
					return
				}
				const next = pending[0]
				uni.redirectTo({
					url: `/pages/order/review?orderId=${this.orderId}&productId=${next.productId}&productName=${UTSAndroid.consoleDebugError(encodeURIComponent(next.productName), " at pages/order/review.uvue:190")}&productImage=${UTSAndroid.consoleDebugError(encodeURIComponent(next.productImage), " at pages/order/review.uvue:190")}&reviewIndex=${this.reviewIndex + 1}&reviewTotal=${this.reviewTotal}`
				})
			}).catch((_err) => {
				uni.showToast({ title: '评价成功', icon: 'success' })
				setTimeout(() => { uni.navigateBack() }, 1000)
			})
		}
	}
})

export default __sfc__
function GenPagesOrderReviewRender(this: InstanceType<typeof __sfc__>): any | null {
const _ctx = this
const _cache = this.$.renderCache
  return createElementVNode("view", utsMapOf({ class: "review-page" }), [
    createElementVNode("view", utsMapOf({ class: "nav-bar" }), [
      createElementVNode("view", utsMapOf({
        class: "nav-back",
        onClick: _ctx.goBack
      }), [
        createElementVNode("text", utsMapOf({ class: "back-icon" }), "‹")
      ], 8 /* PROPS */, ["onClick"]),
      createElementVNode("text", utsMapOf({ class: "nav-title" }), "发表评价"),
      createElementVNode("view", utsMapOf({ class: "nav-placeholder" }))
    ]),
    createElementVNode("view", utsMapOf({ class: "product-card" }), [
      createElementVNode("image", utsMapOf({
        class: "product-image",
        src: _ctx.productImage,
        mode: "aspectFill"
      }), null, 8 /* PROPS */, ["src"]),
      createElementVNode("view", utsMapOf({ class: "product-info" }), [
        createElementVNode("text", utsMapOf({ class: "product-name" }), toDisplayString(_ctx.productName), 1 /* TEXT */)
      ])
    ]),
    createElementVNode("view", utsMapOf({ class: "rating-section" }), [
      createElementVNode("text", utsMapOf({ class: "section-label" }), "商品评分"),
      createElementVNode("view", utsMapOf({ class: "stars-row" }), [
        createElementVNode(Fragment, null, RenderHelpers.renderList(5, (i, __key, __index, _cached): any => {
          return createElementVNode("view", utsMapOf({
            class: "star",
            key: i,
            onClick: () => {_ctx.setRating(i)}
          }), [
            createElementVNode("text", utsMapOf({
              class: normalizeClass(["star-icon", utsMapOf({ 'star-icon-active': i <= _ctx.rating })])
            }), "★", 2 /* CLASS */)
          ], 8 /* PROPS */, ["onClick"])
        }), 64 /* STABLE_FRAGMENT */),
        createElementVNode("text", utsMapOf({ class: "rating-text" }), toDisplayString(_ctx.getRatingText()), 1 /* TEXT */)
      ])
    ]),
    createElementVNode("view", utsMapOf({ class: "content-section" }), [
      createElementVNode("text", utsMapOf({ class: "section-label" }), "评价内容"),
      createElementVNode("textarea", utsMapOf({
        class: "content-input",
        modelValue: _ctx.content,
        onInput: ($event: InputEvent) => {(_ctx.content) = $event.detail.value},
        placeholder: "请分享您对商品的使用感受，帮助其他买家做出选择~",
        maxlength: 500
      }), null, 40 /* PROPS, NEED_HYDRATION */, ["modelValue", "onInput"]),
      createElementVNode("text", utsMapOf({ class: "word-count" }), toDisplayString(_ctx.content.length) + "/500", 1 /* TEXT */)
    ]),
    createElementVNode("view", utsMapOf({ class: "content-section" }), [
      createElementVNode("text", utsMapOf({ class: "section-label" }), "评价图片（选填）"),
      createElementVNode("view", utsMapOf({ class: "image-list" }), [
        createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.selectedImages, (img, idx, __index, _cached): any => {
          return createElementVNode("view", utsMapOf({
            class: "picked-image-wrap",
            key: idx
          }), [
            createElementVNode("image", utsMapOf({
              class: "picked-image",
              src: img,
              mode: "aspectFill",
              onClick: () => {_ctx.previewPickedImage(idx)}
            }), null, 8 /* PROPS */, ["src", "onClick"]),
            createElementVNode("text", utsMapOf({
              class: "remove-image",
              onClick: () => {_ctx.removeImage(idx)}
            }), "×", 8 /* PROPS */, ["onClick"])
          ])
        }), 128 /* KEYED_FRAGMENT */),
        _ctx.selectedImages.length < 6
          ? createElementVNode("view", utsMapOf({
              key: 0,
              class: "add-image",
              onClick: _ctx.chooseImages
            }), [
              createElementVNode("text", utsMapOf({ class: "add-image-text" }), "+ 添加图片")
            ], 8 /* PROPS */, ["onClick"])
          : createCommentVNode("v-if", true)
      ])
    ]),
    createElementVNode("view", utsMapOf({ class: "submit-section" }), [
      createElementVNode("button", utsMapOf({
        class: normalizeClass(["submit-btn", utsMapOf({ 'submit-btn-disabled': _ctx.isSubmitting })]),
        disabled: _ctx.isSubmitting,
        onClick: _ctx.submitReview
      }), toDisplayString(_ctx.isSubmitting ? '提交中...' : '提交评价'), 11 /* TEXT, CLASS, PROPS */, ["disabled", "onClick"])
    ])
  ])
}
const GenPagesOrderReviewStyles = [utsMapOf([["review-page", padStyleMapOf(utsMapOf([["backgroundColor", "#f5f5f5"], ["minHeight", "1500rpx"], ["paddingBottom", "120rpx"]]))], ["nav-bar", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "space-between"], ["height", "88rpx"], ["backgroundColor", "#ffffff"], ["paddingTop", 0], ["paddingRight", "20rpx"], ["paddingBottom", 0], ["paddingLeft", "20rpx"], ["borderBottomWidth", "1rpx"], ["borderBottomStyle", "solid"], ["borderBottomColor", "#f0f0f0"]]))], ["nav-back", padStyleMapOf(utsMapOf([["width", "80rpx"], ["height", "80rpx"], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["back-icon", padStyleMapOf(utsMapOf([["fontSize", "48rpx"], ["color", "#333333"]]))], ["nav-title", padStyleMapOf(utsMapOf([["fontSize", "32rpx"], ["fontWeight", "700"], ["color", "#333333"]]))], ["nav-placeholder", padStyleMapOf(utsMapOf([["width", "80rpx"]]))], ["product-card", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["backgroundColor", "#ffffff"], ["paddingTop", "30rpx"], ["paddingRight", "30rpx"], ["paddingBottom", "30rpx"], ["paddingLeft", "30rpx"], ["marginTop", "20rpx"], ["marginRight", "20rpx"], ["marginBottom", "20rpx"], ["marginLeft", "20rpx"], ["borderTopLeftRadius", "16rpx"], ["borderTopRightRadius", "16rpx"], ["borderBottomRightRadius", "16rpx"], ["borderBottomLeftRadius", "16rpx"]]))], ["product-image", padStyleMapOf(utsMapOf([["width", "120rpx"], ["height", "120rpx"], ["borderTopLeftRadius", "12rpx"], ["borderTopRightRadius", "12rpx"], ["borderBottomRightRadius", "12rpx"], ["borderBottomLeftRadius", "12rpx"], ["marginRight", "20rpx"]]))], ["product-info", padStyleMapOf(utsMapOf([["flex", 1]]))], ["product-name", padStyleMapOf(utsMapOf([["fontSize", "28rpx"], ["color", "#333333"], ["lineHeight", 1.4]]))], ["rating-section", padStyleMapOf(utsMapOf([["backgroundColor", "#ffffff"], ["paddingTop", "30rpx"], ["paddingRight", "30rpx"], ["paddingBottom", "30rpx"], ["paddingLeft", "30rpx"], ["marginTop", 0], ["marginRight", "20rpx"], ["marginBottom", "20rpx"], ["marginLeft", "20rpx"], ["borderTopLeftRadius", "16rpx"], ["borderTopRightRadius", "16rpx"], ["borderBottomRightRadius", "16rpx"], ["borderBottomLeftRadius", "16rpx"]]))], ["section-label", padStyleMapOf(utsMapOf([["fontSize", "28rpx"], ["color", "#333333"], ["fontWeight", "bold"], ["marginBottom", "20rpx"]]))], ["image-list", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["flexWrap", "wrap"]]))], ["picked-image-wrap", padStyleMapOf(utsMapOf([["position", "relative"], ["marginRight", "16rpx"], ["marginBottom", "16rpx"]]))], ["picked-image", padStyleMapOf(utsMapOf([["width", "140rpx"], ["height", "140rpx"], ["borderTopLeftRadius", "12rpx"], ["borderTopRightRadius", "12rpx"], ["borderBottomRightRadius", "12rpx"], ["borderBottomLeftRadius", "12rpx"], ["backgroundColor", "#f5f5f5"]]))], ["remove-image", padStyleMapOf(utsMapOf([["position", "absolute"], ["right", "-10rpx"], ["top", "-10rpx"], ["width", "36rpx"], ["height", "36rpx"], ["lineHeight", "36rpx"], ["textAlign", "center"], ["borderTopLeftRadius", "18rpx"], ["borderTopRightRadius", "18rpx"], ["borderBottomRightRadius", "18rpx"], ["borderBottomLeftRadius", "18rpx"], ["backgroundColor", "rgba(0,0,0,0.6)"], ["color", "#ffffff"], ["fontSize", "24rpx"]]))], ["add-image", padStyleMapOf(utsMapOf([["width", "140rpx"], ["height", "140rpx"], ["borderTopLeftRadius", "12rpx"], ["borderTopRightRadius", "12rpx"], ["borderBottomRightRadius", "12rpx"], ["borderBottomLeftRadius", "12rpx"], ["borderTopWidth", "2rpx"], ["borderRightWidth", "2rpx"], ["borderBottomWidth", "2rpx"], ["borderLeftWidth", "2rpx"], ["borderTopStyle", "dashed"], ["borderRightStyle", "dashed"], ["borderBottomStyle", "dashed"], ["borderLeftStyle", "dashed"], ["borderTopColor", "#d8d8d8"], ["borderRightColor", "#d8d8d8"], ["borderBottomColor", "#d8d8d8"], ["borderLeftColor", "#d8d8d8"], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"], ["backgroundColor", "#fafafa"]]))], ["add-image-text", padStyleMapOf(utsMapOf([["fontSize", "24rpx"], ["color", "#999999"]]))], ["stars-row", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"]]))], ["star", padStyleMapOf(utsMapOf([["paddingTop", "10rpx"], ["paddingRight", "10rpx"], ["paddingBottom", "10rpx"], ["paddingLeft", "10rpx"]]))], ["star-icon", padStyleMapOf(utsMapOf([["fontSize", "56rpx"], ["color", "#dddddd"]]))], ["star-icon-active", padStyleMapOf(utsMapOf([["color", "#FFB800"]]))], ["rating-text", padStyleMapOf(utsMapOf([["fontSize", "28rpx"], ["color", "#FFB800"], ["marginLeft", "20rpx"]]))], ["content-section", padStyleMapOf(utsMapOf([["backgroundColor", "#ffffff"], ["paddingTop", "30rpx"], ["paddingRight", "30rpx"], ["paddingBottom", "30rpx"], ["paddingLeft", "30rpx"], ["marginTop", 0], ["marginRight", "20rpx"], ["marginBottom", "20rpx"], ["marginLeft", "20rpx"], ["borderTopLeftRadius", "16rpx"], ["borderTopRightRadius", "16rpx"], ["borderBottomRightRadius", "16rpx"], ["borderBottomLeftRadius", "16rpx"], ["position", "relative"]]))], ["content-input", padStyleMapOf(utsMapOf([["width", "100%"], ["height", "240rpx"], ["fontSize", "28rpx"], ["color", "#333333"], ["lineHeight", 1.6], ["paddingTop", "20rpx"], ["paddingRight", "20rpx"], ["paddingBottom", "20rpx"], ["paddingLeft", "20rpx"], ["backgroundColor", "#f8f8f8"], ["borderTopLeftRadius", "12rpx"], ["borderTopRightRadius", "12rpx"], ["borderBottomRightRadius", "12rpx"], ["borderBottomLeftRadius", "12rpx"], ["boxSizing", "border-box"]]))], ["word-count", padStyleMapOf(utsMapOf([["position", "absolute"], ["right", "50rpx"], ["bottom", "50rpx"], ["fontSize", "24rpx"], ["color", "#999999"]]))], ["submit-section", padStyleMapOf(utsMapOf([["position", "fixed"], ["bottom", 0], ["left", 0], ["right", 0], ["paddingTop", "20rpx"], ["paddingRight", "30rpx"], ["paddingBottom", "20rpx"], ["paddingLeft", "30rpx"], ["backgroundColor", "#ffffff"], ["boxShadow", "0 -2rpx 10rpx rgba(0, 0, 0, 0.05)"]]))], ["submit-btn", padStyleMapOf(utsMapOf([["width", "100%"], ["height", "88rpx"], ["backgroundColor", "#0066CC"], ["color", "#ffffff"], ["fontSize", "32rpx"], ["fontWeight", "bold"], ["borderTopLeftRadius", "44rpx"], ["borderTopRightRadius", "44rpx"], ["borderBottomRightRadius", "44rpx"], ["borderBottomLeftRadius", "44rpx"], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["submit-btn-disabled", padStyleMapOf(utsMapOf([["backgroundColor", "#cccccc"]]))]])]
