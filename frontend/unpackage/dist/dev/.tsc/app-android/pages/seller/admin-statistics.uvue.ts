
	import { getAdminOverview, getProductDistribution, getOrderDistribution } from '@/api/admin'
	import type { ResponseDataType } from '@/utils/request'

	type OverviewType = { __$originalPosition?: UTSSourceMapPosition<"OverviewType", "pages/seller/admin-statistics.uvue", 204, 7>;
		totalUsers: number
		activeUsers: number
		totalProducts: number
		pendingProducts: number
		onlineProducts: number
		totalOrders: number
		completedOrders: number
		pendingOrders: number
	}

	type ProductDistType = { __$originalPosition?: UTSSourceMapPosition<"ProductDistType", "pages/seller/admin-statistics.uvue", 215, 7>;
		pending: number
		online: number
		offline: number
		rejected: number
	}

	type OrderDistType = { __$originalPosition?: UTSSourceMapPosition<"OrderDistType", "pages/seller/admin-statistics.uvue", 222, 7>;
		unpaid: number
		unshipped: number
		shipped: number
		completed: number
		cancelled: number
	}

	const __sfc__ = defineComponent({
		data() {
			return {
				overview: {
					totalUsers: 0,
					activeUsers: 0,
					totalProducts: 0,
					pendingProducts: 0,
					onlineProducts: 0,
					totalOrders: 0,
					completedOrders: 0,
					pendingOrders: 0
				} as OverviewType,
				productDist: {
					pending: 0,
					online: 0,
					offline: 0,
					rejected: 0
				} as ProductDistType,
				orderDist: {
					unpaid: 0,
					unshipped: 0,
					shipped: 0,
					completed: 0,
					cancelled: 0
				} as OrderDistType
			}
		},
		onLoad() {
			this.loadData()
		},
		methods: {
			loadData() {
				this.loadOverview()
				this.loadProductDist()
				this.loadOrderDist()
			},
			loadOverview() {
				getAdminOverview().then((res : ResponseDataType) => {
					if (res.code === 200 && res.data != null) {
						const data = res.data as UTSJSONObject
						this.overview = {
							totalUsers: (data.getNumber('totalUsers') ?? 0).toInt(),
							activeUsers: (data.getNumber('activeUsers') ?? 0).toInt(),
							totalProducts: (data.getNumber('totalProducts') ?? 0).toInt(),
							pendingProducts: (data.getNumber('pendingProducts') ?? 0).toInt(),
							onlineProducts: (data.getNumber('onlineProducts') ?? 0).toInt(),
							totalOrders: (data.getNumber('totalOrders') ?? 0).toInt(),
							completedOrders: (data.getNumber('completedOrders') ?? 0).toInt(),
							pendingOrders: (data.getNumber('pendingOrders') ?? 0).toInt()
						} as OverviewType
					}
				}).catch((err : Any | null) => {
					const error = err as Error
					uni.showToast({
						title: error.message,
						icon: 'none'
					})
				})
			},
			loadProductDist() {
				getProductDistribution().then((res : ResponseDataType) => {
					if (res.code === 200 && res.data != null) {
						const data = res.data as UTSJSONObject
						this.productDist = {
							pending: (data.getNumber('pending') ?? 0).toInt(),
							online: (data.getNumber('online') ?? 0).toInt(),
							offline: (data.getNumber('offline') ?? 0).toInt(),
							rejected: (data.getNumber('rejected') ?? 0).toInt()
						} as ProductDistType
					}
				}).catch((err : Any | null) => {
					console.error('加载商品分布失败:', err, " at pages/seller/admin-statistics.uvue:302")
				})
			},
			loadOrderDist() {
				getOrderDistribution().then((res : ResponseDataType) => {
					if (res.code === 200 && res.data != null) {
						const data = res.data as UTSJSONObject
						this.orderDist = {
							unpaid: (data.getNumber('unpaid') ?? 0).toInt(),
							unshipped: (data.getNumber('unshipped') ?? 0).toInt(),
							shipped: (data.getNumber('shipped') ?? 0).toInt(),
							completed: (data.getNumber('completed') ?? 0).toInt(),
							cancelled: (data.getNumber('cancelled') ?? 0).toInt()
						} as OrderDistType
					}
				}).catch((err : Any | null) => {
					console.error('加载订单分布失败:', err, " at pages/seller/admin-statistics.uvue:318")
				})
			},
			getTotalProducts() : number {
				return this.productDist.pending + this.productDist.online + this.productDist.offline + this.productDist.rejected
			},
			getTotalOrders() : number {
				return this.orderDist.unpaid + this.orderDist.unshipped + this.orderDist.shipped + this.orderDist.completed + this.orderDist.cancelled
			},
			getPercentage(value : number, total : number) : string {
				if (total === 0) return '0%'
				const percent = (value / total * 100).toFixed(0)
				return `${percent}%`
			}
		}
	})

export default __sfc__
function GenPagesSellerAdminStatisticsRender(this: InstanceType<typeof __sfc__>): any | null {
const _ctx = this
const _cache = this.$.renderCache
  return createElementVNode("scroll-view", utsMapOf({
    class: "page",
    "scroll-y": ""
  }), [
    createElementVNode("view", utsMapOf({ class: "top-cards" }), [
      createElementVNode("view", utsMapOf({ class: "data-card card-1" }), [
        createElementVNode("view", utsMapOf({ class: "card-header" }), [
          createElementVNode("text", utsMapOf({ class: "card-title" }), "用户"),
          createElementVNode("text", utsMapOf({ class: "card-icon" }), "👥")
        ]),
        createElementVNode("text", utsMapOf({ class: "card-number" }), toDisplayString(_ctx.overview.totalUsers), 1 /* TEXT */),
        createElementVNode("view", utsMapOf({ class: "card-footer" }), [
          createElementVNode("text", utsMapOf({ class: "footer-label" }), "活跃用户"),
          createElementVNode("text", utsMapOf({ class: "footer-value" }), toDisplayString(_ctx.overview.activeUsers), 1 /* TEXT */)
        ])
      ]),
      createElementVNode("view", utsMapOf({ class: "data-card card-2" }), [
        createElementVNode("view", utsMapOf({ class: "card-header" }), [
          createElementVNode("text", utsMapOf({ class: "card-title" }), "商品"),
          createElementVNode("text", utsMapOf({ class: "card-icon" }), "🛍️")
        ]),
        createElementVNode("text", utsMapOf({ class: "card-number" }), toDisplayString(_ctx.overview.totalProducts), 1 /* TEXT */),
        createElementVNode("view", utsMapOf({ class: "card-footer" }), [
          createElementVNode("text", utsMapOf({ class: "footer-label" }), "在售"),
          createElementVNode("text", utsMapOf({ class: "footer-value" }), toDisplayString(_ctx.overview.onlineProducts), 1 /* TEXT */)
        ])
      ]),
      createElementVNode("view", utsMapOf({ class: "data-card card-3" }), [
        createElementVNode("view", utsMapOf({ class: "card-header" }), [
          createElementVNode("text", utsMapOf({ class: "card-title" }), "订单"),
          createElementVNode("text", utsMapOf({ class: "card-icon" }), "📦")
        ]),
        createElementVNode("text", utsMapOf({ class: "card-number" }), toDisplayString(_ctx.overview.totalOrders), 1 /* TEXT */),
        createElementVNode("view", utsMapOf({ class: "card-footer" }), [
          createElementVNode("text", utsMapOf({ class: "footer-label" }), "已完成"),
          createElementVNode("text", utsMapOf({ class: "footer-value" }), toDisplayString(_ctx.overview.completedOrders), 1 /* TEXT */)
        ])
      ]),
      createElementVNode("view", utsMapOf({ class: "data-card card-4" }), [
        createElementVNode("view", utsMapOf({ class: "card-header" }), [
          createElementVNode("text", utsMapOf({ class: "card-title" }), "待审核"),
          createElementVNode("text", utsMapOf({ class: "card-icon" }), "⚠️")
        ]),
        createElementVNode("text", utsMapOf({ class: "card-number" }), toDisplayString(_ctx.overview.pendingProducts), 1 /* TEXT */),
        createElementVNode("view", utsMapOf({ class: "card-footer" }), [
          createElementVNode("text", utsMapOf({ class: "footer-label" }), "需处理"),
          createElementVNode("text", utsMapOf({ class: "footer-value" }), toDisplayString(_ctx.overview.pendingProducts), 1 /* TEXT */)
        ])
      ])
    ]),
    createElementVNode("view", utsMapOf({ class: "analysis-section" }), [
      createElementVNode("view", utsMapOf({ class: "section-title-bar" }), [
        createElementVNode("text", utsMapOf({ class: "section-title" }), "商品分析"),
        createElementVNode("text", utsMapOf({ class: "section-total" }), "总计 " + toDisplayString(_ctx.getTotalProducts()), 1 /* TEXT */)
      ]),
      createElementVNode("view", utsMapOf({ class: "analysis-card" }), [
        createElementVNode("view", utsMapOf({ class: "analysis-item" }), [
          createElementVNode("view", utsMapOf({ class: "item-left" }), [
            createElementVNode("view", utsMapOf({ class: "item-indicator ind-orange" })),
            createElementVNode("text", utsMapOf({ class: "item-name" }), "待审核")
          ]),
          createElementVNode("view", utsMapOf({ class: "item-right" }), [
            createElementVNode("text", utsMapOf({ class: "item-number" }), toDisplayString(_ctx.productDist.pending), 1 /* TEXT */),
            createElementVNode("text", utsMapOf({ class: "item-percent" }), toDisplayString(_ctx.getPercentage(_ctx.productDist.pending, _ctx.getTotalProducts())), 1 /* TEXT */)
          ])
        ]),
        createElementVNode("view", utsMapOf({ class: "progress-track" }), [
          createElementVNode("view", utsMapOf({
            class: "progress-bar bar-orange",
            style: normalizeStyle(utsMapOf({width: _ctx.getPercentage(_ctx.productDist.pending, _ctx.getTotalProducts())}))
          }), null, 4 /* STYLE */)
        ]),
        createElementVNode("view", utsMapOf({ class: "analysis-item" }), [
          createElementVNode("view", utsMapOf({ class: "item-left" }), [
            createElementVNode("view", utsMapOf({ class: "item-indicator ind-green" })),
            createElementVNode("text", utsMapOf({ class: "item-name" }), "已上架")
          ]),
          createElementVNode("view", utsMapOf({ class: "item-right" }), [
            createElementVNode("text", utsMapOf({ class: "item-number" }), toDisplayString(_ctx.productDist.online), 1 /* TEXT */),
            createElementVNode("text", utsMapOf({ class: "item-percent" }), toDisplayString(_ctx.getPercentage(_ctx.productDist.online, _ctx.getTotalProducts())), 1 /* TEXT */)
          ])
        ]),
        createElementVNode("view", utsMapOf({ class: "progress-track" }), [
          createElementVNode("view", utsMapOf({
            class: "progress-bar bar-green",
            style: normalizeStyle(utsMapOf({width: _ctx.getPercentage(_ctx.productDist.online, _ctx.getTotalProducts())}))
          }), null, 4 /* STYLE */)
        ]),
        createElementVNode("view", utsMapOf({ class: "analysis-item" }), [
          createElementVNode("view", utsMapOf({ class: "item-left" }), [
            createElementVNode("view", utsMapOf({ class: "item-indicator ind-gray" })),
            createElementVNode("text", utsMapOf({ class: "item-name" }), "已下架")
          ]),
          createElementVNode("view", utsMapOf({ class: "item-right" }), [
            createElementVNode("text", utsMapOf({ class: "item-number" }), toDisplayString(_ctx.productDist.offline), 1 /* TEXT */),
            createElementVNode("text", utsMapOf({ class: "item-percent" }), toDisplayString(_ctx.getPercentage(_ctx.productDist.offline, _ctx.getTotalProducts())), 1 /* TEXT */)
          ])
        ]),
        createElementVNode("view", utsMapOf({ class: "progress-track" }), [
          createElementVNode("view", utsMapOf({
            class: "progress-bar bar-gray",
            style: normalizeStyle(utsMapOf({width: _ctx.getPercentage(_ctx.productDist.offline, _ctx.getTotalProducts())}))
          }), null, 4 /* STYLE */)
        ]),
        createElementVNode("view", utsMapOf({ class: "analysis-item" }), [
          createElementVNode("view", utsMapOf({ class: "item-left" }), [
            createElementVNode("view", utsMapOf({ class: "item-indicator ind-red" })),
            createElementVNode("text", utsMapOf({ class: "item-name" }), "审核不通过")
          ]),
          createElementVNode("view", utsMapOf({ class: "item-right" }), [
            createElementVNode("text", utsMapOf({ class: "item-number" }), toDisplayString(_ctx.productDist.rejected), 1 /* TEXT */),
            createElementVNode("text", utsMapOf({ class: "item-percent" }), toDisplayString(_ctx.getPercentage(_ctx.productDist.rejected, _ctx.getTotalProducts())), 1 /* TEXT */)
          ])
        ]),
        createElementVNode("view", utsMapOf({ class: "progress-track" }), [
          createElementVNode("view", utsMapOf({
            class: "progress-bar bar-red",
            style: normalizeStyle(utsMapOf({width: _ctx.getPercentage(_ctx.productDist.rejected, _ctx.getTotalProducts())}))
          }), null, 4 /* STYLE */)
        ])
      ])
    ]),
    createElementVNode("view", utsMapOf({ class: "analysis-section" }), [
      createElementVNode("view", utsMapOf({ class: "section-title-bar" }), [
        createElementVNode("text", utsMapOf({ class: "section-title" }), "订单分析"),
        createElementVNode("text", utsMapOf({ class: "section-total" }), "总计 " + toDisplayString(_ctx.getTotalOrders()), 1 /* TEXT */)
      ]),
      createElementVNode("view", utsMapOf({ class: "analysis-card" }), [
        createElementVNode("view", utsMapOf({ class: "analysis-item" }), [
          createElementVNode("view", utsMapOf({ class: "item-left" }), [
            createElementVNode("view", utsMapOf({ class: "item-indicator ind-orange" })),
            createElementVNode("text", utsMapOf({ class: "item-name" }), "待支付")
          ]),
          createElementVNode("view", utsMapOf({ class: "item-right" }), [
            createElementVNode("text", utsMapOf({ class: "item-number" }), toDisplayString(_ctx.orderDist.unpaid), 1 /* TEXT */),
            createElementVNode("text", utsMapOf({ class: "item-percent" }), toDisplayString(_ctx.getPercentage(_ctx.orderDist.unpaid, _ctx.getTotalOrders())), 1 /* TEXT */)
          ])
        ]),
        createElementVNode("view", utsMapOf({ class: "progress-track" }), [
          createElementVNode("view", utsMapOf({
            class: "progress-bar bar-orange",
            style: normalizeStyle(utsMapOf({width: _ctx.getPercentage(_ctx.orderDist.unpaid, _ctx.getTotalOrders())}))
          }), null, 4 /* STYLE */)
        ]),
        createElementVNode("view", utsMapOf({ class: "analysis-item" }), [
          createElementVNode("view", utsMapOf({ class: "item-left" }), [
            createElementVNode("view", utsMapOf({ class: "item-indicator ind-blue" })),
            createElementVNode("text", utsMapOf({ class: "item-name" }), "待发货")
          ]),
          createElementVNode("view", utsMapOf({ class: "item-right" }), [
            createElementVNode("text", utsMapOf({ class: "item-number" }), toDisplayString(_ctx.orderDist.unshipped), 1 /* TEXT */),
            createElementVNode("text", utsMapOf({ class: "item-percent" }), toDisplayString(_ctx.getPercentage(_ctx.orderDist.unshipped, _ctx.getTotalOrders())), 1 /* TEXT */)
          ])
        ]),
        createElementVNode("view", utsMapOf({ class: "progress-track" }), [
          createElementVNode("view", utsMapOf({
            class: "progress-bar bar-blue",
            style: normalizeStyle(utsMapOf({width: _ctx.getPercentage(_ctx.orderDist.unshipped, _ctx.getTotalOrders())}))
          }), null, 4 /* STYLE */)
        ]),
        createElementVNode("view", utsMapOf({ class: "analysis-item" }), [
          createElementVNode("view", utsMapOf({ class: "item-left" }), [
            createElementVNode("view", utsMapOf({ class: "item-indicator ind-purple" })),
            createElementVNode("text", utsMapOf({ class: "item-name" }), "待收货")
          ]),
          createElementVNode("view", utsMapOf({ class: "item-right" }), [
            createElementVNode("text", utsMapOf({ class: "item-number" }), toDisplayString(_ctx.orderDist.shipped), 1 /* TEXT */),
            createElementVNode("text", utsMapOf({ class: "item-percent" }), toDisplayString(_ctx.getPercentage(_ctx.orderDist.shipped, _ctx.getTotalOrders())), 1 /* TEXT */)
          ])
        ]),
        createElementVNode("view", utsMapOf({ class: "progress-track" }), [
          createElementVNode("view", utsMapOf({
            class: "progress-bar bar-purple",
            style: normalizeStyle(utsMapOf({width: _ctx.getPercentage(_ctx.orderDist.shipped, _ctx.getTotalOrders())}))
          }), null, 4 /* STYLE */)
        ]),
        createElementVNode("view", utsMapOf({ class: "analysis-item" }), [
          createElementVNode("view", utsMapOf({ class: "item-left" }), [
            createElementVNode("view", utsMapOf({ class: "item-indicator ind-green" })),
            createElementVNode("text", utsMapOf({ class: "item-name" }), "已完成")
          ]),
          createElementVNode("view", utsMapOf({ class: "item-right" }), [
            createElementVNode("text", utsMapOf({ class: "item-number" }), toDisplayString(_ctx.orderDist.completed), 1 /* TEXT */),
            createElementVNode("text", utsMapOf({ class: "item-percent" }), toDisplayString(_ctx.getPercentage(_ctx.orderDist.completed, _ctx.getTotalOrders())), 1 /* TEXT */)
          ])
        ]),
        createElementVNode("view", utsMapOf({ class: "progress-track" }), [
          createElementVNode("view", utsMapOf({
            class: "progress-bar bar-green",
            style: normalizeStyle(utsMapOf({width: _ctx.getPercentage(_ctx.orderDist.completed, _ctx.getTotalOrders())}))
          }), null, 4 /* STYLE */)
        ]),
        createElementVNode("view", utsMapOf({ class: "analysis-item" }), [
          createElementVNode("view", utsMapOf({ class: "item-left" }), [
            createElementVNode("view", utsMapOf({ class: "item-indicator ind-gray" })),
            createElementVNode("text", utsMapOf({ class: "item-name" }), "已取消")
          ]),
          createElementVNode("view", utsMapOf({ class: "item-right" }), [
            createElementVNode("text", utsMapOf({ class: "item-number" }), toDisplayString(_ctx.orderDist.cancelled), 1 /* TEXT */),
            createElementVNode("text", utsMapOf({ class: "item-percent" }), toDisplayString(_ctx.getPercentage(_ctx.orderDist.cancelled, _ctx.getTotalOrders())), 1 /* TEXT */)
          ])
        ]),
        createElementVNode("view", utsMapOf({ class: "progress-track" }), [
          createElementVNode("view", utsMapOf({
            class: "progress-bar bar-gray",
            style: normalizeStyle(utsMapOf({width: _ctx.getPercentage(_ctx.orderDist.cancelled, _ctx.getTotalOrders())}))
          }), null, 4 /* STYLE */)
        ])
      ])
    ])
  ])
}
const GenPagesSellerAdminStatisticsStyles = [utsMapOf([["page", padStyleMapOf(utsMapOf([["backgroundColor", "#f7f8fa"], ["paddingTop", 15], ["paddingRight", 15], ["paddingBottom", 15], ["paddingLeft", 15]]))], ["top-cards", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["justifyContent", "space-between"], ["marginBottom", 20]]))], ["data-card", padStyleMapOf(utsMapOf([["width", "23%"], ["backgroundColor", "#ffffff"], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12], ["paddingTop", 12], ["paddingRight", 12], ["paddingBottom", 12], ["paddingLeft", 12], ["boxShadow", "0 2px 8px rgba(0, 0, 0, 0.06)"]]))], ["card-1", padStyleMapOf(utsMapOf([["borderTopWidth", 3], ["borderTopStyle", "solid"], ["borderTopColor", "#3b82f6"]]))], ["card-2", padStyleMapOf(utsMapOf([["borderTopWidth", 3], ["borderTopStyle", "solid"], ["borderTopColor", "#10b981"]]))], ["card-3", padStyleMapOf(utsMapOf([["borderTopWidth", 3], ["borderTopStyle", "solid"], ["borderTopColor", "#f59e0b"]]))], ["card-4", padStyleMapOf(utsMapOf([["borderTopWidth", 3], ["borderTopStyle", "solid"], ["borderTopColor", "#ef4444"]]))], ["card-header", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["justifyContent", "space-between"], ["alignItems", "center"], ["marginBottom", 8]]))], ["card-title", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "#6b7280"]]))], ["card-icon", padStyleMapOf(utsMapOf([["fontSize", 18]]))], ["card-number", padStyleMapOf(utsMapOf([["fontSize", 24], ["fontWeight", "bold"], ["color", "#111827"], ["display", "flex"], ["marginBottom", 6]]))], ["card-footer", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "column"], ["paddingTop", 6], ["borderTopWidth", 1], ["borderTopStyle", "solid"], ["borderTopColor", "#f3f4f6"]]))], ["footer-label", padStyleMapOf(utsMapOf([["fontSize", 11], ["color", "#9ca3af"], ["marginBottom", 2]]))], ["footer-value", padStyleMapOf(utsMapOf([["fontSize", 13], ["fontWeight", "bold"], ["color", "#6b7280"]]))], ["analysis-section", padStyleMapOf(utsMapOf([["marginBottom", 20]]))], ["section-title-bar", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["justifyContent", "space-between"], ["alignItems", "center"], ["marginBottom", 12]]))], ["section-title", padStyleMapOf(utsMapOf([["fontSize", 18], ["fontWeight", "bold"], ["color", "#111827"]]))], ["section-total", padStyleMapOf(utsMapOf([["fontSize", 14], ["color", "#6b7280"]]))], ["analysis-card", padStyleMapOf(utsMapOf([["backgroundColor", "#ffffff"], ["borderTopLeftRadius", 16], ["borderTopRightRadius", 16], ["borderBottomRightRadius", 16], ["borderBottomLeftRadius", 16], ["paddingTop", 16], ["paddingRight", 16], ["paddingBottom", 16], ["paddingLeft", 16], ["boxShadow", "0 2px 12px rgba(0, 0, 0, 0.06)"]]))], ["analysis-item", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["justifyContent", "space-between"], ["alignItems", "center"], ["marginBottom", 8]]))], ["item-left", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"]]))], ["item-indicator", padStyleMapOf(utsMapOf([["width", 12], ["height", 12], ["borderTopLeftRadius", 6], ["borderTopRightRadius", 6], ["borderBottomRightRadius", 6], ["borderBottomLeftRadius", 6], ["marginRight", 10]]))], ["ind-blue", padStyleMapOf(utsMapOf([["backgroundColor", "#3b82f6"]]))], ["ind-green", padStyleMapOf(utsMapOf([["backgroundColor", "#10b981"]]))], ["ind-orange", padStyleMapOf(utsMapOf([["backgroundColor", "#f59e0b"]]))], ["ind-red", padStyleMapOf(utsMapOf([["backgroundColor", "#ef4444"]]))], ["ind-gray", padStyleMapOf(utsMapOf([["backgroundColor", "#9ca3af"]]))], ["ind-purple", padStyleMapOf(utsMapOf([["backgroundColor", "#8b5cf6"]]))], ["item-name", padStyleMapOf(utsMapOf([["fontSize", 15], ["color", "#374151"]]))], ["item-right", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"]]))], ["item-number", padStyleMapOf(utsMapOf([["fontSize", 16], ["fontWeight", "bold"], ["color", "#111827"], ["marginRight", 8]]))], ["item-percent", padStyleMapOf(utsMapOf([["fontSize", 13], ["color", "#9ca3af"], ["minWidth", 40], ["textAlign", "right"]]))], ["progress-track", padStyleMapOf(utsMapOf([["height", 6], ["backgroundColor", "#f3f4f6"], ["borderTopLeftRadius", 3], ["borderTopRightRadius", 3], ["borderBottomRightRadius", 3], ["borderBottomLeftRadius", 3], ["marginBottom", 16], ["overflow", "hidden"]]))], ["progress-bar", padStyleMapOf(utsMapOf([["height", "100%"], ["borderTopLeftRadius", 3], ["borderTopRightRadius", 3], ["borderBottomRightRadius", 3], ["borderBottomLeftRadius", 3]]))], ["bar-blue", padStyleMapOf(utsMapOf([["backgroundColor", "#3b82f6"]]))], ["bar-green", padStyleMapOf(utsMapOf([["backgroundColor", "#10b981"]]))], ["bar-orange", padStyleMapOf(utsMapOf([["backgroundColor", "#f59e0b"]]))], ["bar-red", padStyleMapOf(utsMapOf([["backgroundColor", "#ef4444"]]))], ["bar-gray", padStyleMapOf(utsMapOf([["backgroundColor", "#9ca3af"]]))], ["bar-purple", padStyleMapOf(utsMapOf([["backgroundColor", "#8b5cf6"]]))]])]
