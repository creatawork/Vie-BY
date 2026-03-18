
	import { syncProductVectors } from '@/api/admin'

	const __sfc__ = defineComponent({
		methods: {
			navigateTo(url : string) {
				uni.navigateTo({
					url: url,
					fail: (err) => {
						console.error('跳转失败:', err, " at pages/seller/admin-index.uvue:65")
					}
				})
			},
			handleSyncVectors() {
				uni.showModal({
					title: '同步商品向量',
					content: '确认要立即同步商品向量到向量数据库吗？',
					success: (res) => {
						if (!res.confirm) return
						uni.showLoading({ title: '同步中...' })
						syncProductVectors()
							.then((res) => {
								const rawData = res.data as any
								let count : number | null = null
								if (typeof rawData == 'number') {
									count = rawData
								} else if (rawData != null && typeof rawData == 'object') {
									const dataObj = rawData as UTSJSONObject
									count = dataObj.getNumber('data') ?? dataObj.getNumber('count')
								}
								const countText = count != null ? `，共同步 ${count} 条` : ''
								uni.showToast({ title: `同步完成${countText}`, icon: 'success' })
							})
							.catch((err) => {
								console.error('同步商品向量失败:', err, " at pages/seller/admin-index.uvue:90")
								uni.showToast({ title: '同步失败，请稍后重试', icon: 'none' })
							})
							.finally(() => {
								uni.hideLoading()
							})
					}
				})
			},
			handleLogout() {
				uni.showModal({
					title: '提示',
					content: '确定要退出登录吗？',
					success: (res) => {
						if (res.confirm) {
							uni.clearStorageSync()
							uni.reLaunch({ url: '/pages/login/login' })
						}
					}
				})
			}
		}
	})

export default __sfc__
function GenPagesSellerAdminIndexRender(this: InstanceType<typeof __sfc__>): any | null {
const _ctx = this
const _cache = this.$.renderCache
  return createElementVNode("view", utsMapOf({ class: "page" }), [
    createElementVNode("view", utsMapOf({ class: "header" }), [
      createElementVNode("text", utsMapOf({ class: "title" }), "管理工作台"),
      createElementVNode("text", utsMapOf({ class: "subtitle" }), "欢迎回来，管理员")
    ]),
    createElementVNode("view", utsMapOf({ class: "grid-container" }), [
      createElementVNode("view", utsMapOf({
        class: "grid-item",
        onClick: () => {_ctx.navigateTo('/pages/seller/product-audit')}
      }), [
        createElementVNode("view", utsMapOf({ class: "icon-box icon-box-audit" }), [
          createElementVNode("text", utsMapOf({ class: "icon" }), "📋")
        ]),
        createElementVNode("text", utsMapOf({ class: "item-name" }), "商品审核"),
        createElementVNode("text", utsMapOf({ class: "item-desc" }), "处理商家申请")
      ], 8 /* PROPS */, ["onClick"]),
      createElementVNode("view", utsMapOf({
        class: "grid-item",
        onClick: () => {_ctx.navigateTo('/pages/seller/admin-users')}
      }), [
        createElementVNode("view", utsMapOf({ class: "icon-box icon-box-users" }), [
          createElementVNode("text", utsMapOf({ class: "icon" }), "👥")
        ]),
        createElementVNode("text", utsMapOf({ class: "item-name" }), "用户管理"),
        createElementVNode("text", utsMapOf({ class: "item-desc" }), "管理平台用户")
      ], 8 /* PROPS */, ["onClick"]),
      createElementVNode("view", utsMapOf({
        class: "grid-item",
        onClick: () => {_ctx.navigateTo('/pages/seller/admin-statistics')}
      }), [
        createElementVNode("view", utsMapOf({ class: "icon-box icon-box-stats" }), [
          createElementVNode("text", utsMapOf({ class: "icon" }), "📊")
        ]),
        createElementVNode("text", utsMapOf({ class: "item-name" }), "数据统计"),
        createElementVNode("text", utsMapOf({ class: "item-desc" }), "查看运营报表")
      ], 8 /* PROPS */, ["onClick"]),
      createElementVNode("view", utsMapOf({
        class: "grid-item",
        onClick: () => {_ctx.navigateTo('/pages/admin/reviews')}
      }), [
        createElementVNode("view", utsMapOf({ class: "icon-box icon-box-reviews" }), [
          createElementVNode("text", utsMapOf({ class: "icon" }), "💬")
        ]),
        createElementVNode("text", utsMapOf({ class: "item-name" }), "评价管理"),
        createElementVNode("text", utsMapOf({ class: "item-desc" }), "全平台评价管理")
      ], 8 /* PROPS */, ["onClick"]),
      createElementVNode("view", utsMapOf({
        class: "grid-item grid-item-full",
        onClick: _ctx.handleSyncVectors
      }), [
        createElementVNode("view", utsMapOf({ class: "icon-box icon-box-sync" }), [
          createElementVNode("text", utsMapOf({ class: "icon" }), "🧠")
        ]),
        createElementVNode("text", utsMapOf({ class: "item-name" }), "同步商品向量"),
        createElementVNode("text", utsMapOf({ class: "item-desc" }), "手动触发向量数据库同步")
      ], 8 /* PROPS */, ["onClick"])
    ]),
    createElementVNode("view", utsMapOf({ class: "logout-section" }), [
      createElementVNode("button", utsMapOf({
        class: "logout-btn",
        onClick: _ctx.handleLogout
      }), "退出登录", 8 /* PROPS */, ["onClick"])
    ])
  ])
}
const GenPagesSellerAdminIndexStyles = [utsMapOf([["page", padStyleMapOf(utsMapOf([["backgroundColor", "#f7f8fa"], ["flex", 1], ["paddingTop", 20], ["paddingRight", 20], ["paddingBottom", 20], ["paddingLeft", 20]]))], ["header", padStyleMapOf(utsMapOf([["marginTop", 40], ["marginBottom", 30], ["paddingTop", 0], ["paddingRight", 10], ["paddingBottom", 0], ["paddingLeft", 10]]))], ["title", padStyleMapOf(utsMapOf([["fontSize", 24], ["fontWeight", "700"], ["color", "#333333"]]))], ["subtitle", padStyleMapOf(utsMapOf([["fontSize", 14], ["color", "#999999"], ["marginTop", 8]]))], ["grid-container", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["flexWrap", "wrap"], ["justifyContent", "space-between"]]))], ["grid-item", padStyleMapOf(utsMapOf([["width", "46%"], ["backgroundColor", "#ffffff"], ["borderTopLeftRadius", 16], ["borderTopRightRadius", 16], ["borderBottomRightRadius", 16], ["borderBottomLeftRadius", 16], ["paddingTop", 20], ["paddingRight", 20], ["paddingBottom", 20], ["paddingLeft", 20], ["marginBottom", 20], ["display", "flex"], ["flexDirection", "column"], ["alignItems", "center"], ["boxShadow", "0 4px 12px rgba(0, 0, 0, 0.05)"]]))], ["icon-box", padStyleMapOf(utsMapOf([["width", 50], ["height", 50], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"], ["marginBottom", 12]]))], ["icon-box-audit", padStyleMapOf(utsMapOf([["backgroundColor", "#e6f7ff"]]))], ["icon-box-users", padStyleMapOf(utsMapOf([["backgroundColor", "#f6ffed"]]))], ["icon-box-stats", padStyleMapOf(utsMapOf([["backgroundColor", "#fff7e6"]]))], ["icon-box-reviews", padStyleMapOf(utsMapOf([["backgroundColor", "#fff0f6"]]))], ["icon-box-sync", padStyleMapOf(utsMapOf([["backgroundColor", "#f0f5ff"]]))], ["grid-item-full", padStyleMapOf(utsMapOf([["width", "100%"]]))], ["icon", padStyleMapOf(utsMapOf([["fontSize", 24]]))], ["item-name", padStyleMapOf(utsMapOf([["fontSize", 16], ["fontWeight", "700"], ["color", "#333333"]]))], ["item-desc", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "#999999"], ["marginTop", 4]]))], ["logout-section", padStyleMapOf(utsMapOf([["marginTop", 40], ["paddingTop", 0], ["paddingRight", 10], ["paddingBottom", 0], ["paddingLeft", 10]]))], ["logout-btn", padStyleMapOf(utsMapOf([["backgroundColor", "#ffffff"], ["color", "#ff4d4f"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#ff4d4f"], ["borderRightColor", "#ff4d4f"], ["borderBottomColor", "#ff4d4f"], ["borderLeftColor", "#ff4d4f"], ["borderTopLeftRadius", 24], ["borderTopRightRadius", 24], ["borderBottomRightRadius", 24], ["borderBottomLeftRadius", 24], ["fontSize", 16]]))]])]
