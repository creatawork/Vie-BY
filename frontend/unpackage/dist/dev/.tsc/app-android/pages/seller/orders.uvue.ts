
import { getOrderList, type OrderVO, type OrderItemVO, type OrderQueryParams } from '@/api/order'

const __sfc__ = defineComponent({
  data() {
    return {
      currentTab: 'all',
      isLoading: false,
      isRefreshing: false,
      hasMore: true,
      pageNum: 1,
      pageSize: 10,
      orders: [] as OrderVO[]
    }
  },
  onLoad(options) {
    const opts = options as UTSJSONObject
    const status = opts.get('status')
    if (status != null) { this.currentTab = status as string }
    this.loadOrders()
  },
  methods: {
    getStatusValue() : number | null {
      // 卖家视角的状态映射
      // unshipped: 待发货(1), shipped: 待收货(2), completed: 已完成(3), cancelled: 已取消(4)
      const statusMap = new Map<string, number>([
        ['unshipped', 1], 
        ['shipped', 2], 
        ['completed', 3],
        ['cancelled', 4]
      ])
      if (this.currentTab === 'all') return null
      return statusMap.get(this.currentTab) ?? null
    },
    loadOrders() {
      this.isLoading = true
      const params : OrderQueryParams = { 
        status: this.getStatusValue(), 
        pageNum: this.pageNum, 
        pageSize: this.pageSize 
      }
      getOrderList(params).then((res) => {
        const data = res.data as UTSJSONObject
        const total = (data.getNumber('total') ?? 0).toInt()
        const orderList = this.parseOrdersFromData(data)
        if (this.pageNum === 1) { 
          this.orders = orderList 
        } else { 
          this.orders = this.orders.concat(orderList) 
        }
        this.hasMore = this.orders.length < total
        this.isLoading = false
        this.isRefreshing = false
      }).catch((err) => {
        console.error('获取订单列表失败:', err, " at pages/seller/orders.uvue:162")
        this.isLoading = false
        this.isRefreshing = false
        uni.showToast({ title: '获取订单失败', icon: 'none' })
      })
    },
    parseOrdersFromData(data : UTSJSONObject) : OrderVO[] {
      const records = data.getArray('records')
      if (records == null) return [] as OrderVO[]
      const orderList : OrderVO[] = []
      for (let i = 0; i < records.length; i++) {
        const orderObj = records[i] as UTSJSONObject
        const itemsArr = orderObj.getArray('orderItems')
        const orderItems : OrderItemVO[] = []
        if (itemsArr != null) {
          for (let j = 0; j < itemsArr.length; j++) {
            const item = itemsArr[j] as UTSJSONObject
            orderItems.push({
              id: (item.getNumber('id') ?? 0).toInt(),
              productId: (item.getNumber('productId') ?? 0).toInt(),
              skuId: (item.getNumber('skuId') ?? 0).toInt(),
              productName: item.getString('productName') ?? '',
              skuName: item.getString('skuName') ?? '',
              productImage: item.getString('productImage') ?? '',
              price: item.getNumber('price') ?? 0,
              quantity: (item.getNumber('quantity') ?? 0).toInt(),
              totalPrice: item.getNumber('totalPrice') ?? 0
            } as OrderItemVO)
          }
        }
        orderList.push({
          id: (orderObj.getNumber('id') ?? 0).toInt(),
          orderNo: orderObj.getString('orderNo') ?? '',
          totalAmount: orderObj.getNumber('totalAmount') ?? 0,
          payAmount: orderObj.getNumber('payAmount') ?? 0,
          freightAmount: orderObj.getNumber('freightAmount') ?? 0,
          status: (orderObj.getNumber('status') ?? 0).toInt(),
          statusDesc: orderObj.getString('statusDesc') ?? '',
          receiverName: orderObj.getString('receiverName') ?? '',
          receiverPhone: orderObj.getString('receiverPhone') ?? '',
          receiverAddress: orderObj.getString('receiverAddress') ?? '',
          remark: orderObj.getString('remark'),
          payTime: orderObj.getString('payTime'),
          deliverTime: orderObj.getString('deliverTime'),
          receiveTime: orderObj.getString('receiveTime'),
          cancelTime: orderObj.getString('cancelTime'),
          cancelReason: orderObj.getString('cancelReason'),
          createTime: orderObj.getString('createTime') ?? '',
          orderItems: orderItems,
          totalQuantity: (orderObj.getNumber('totalQuantity') ?? 0).toInt()
        } as OrderVO)
      }
      return orderList
    },
    switchTab(tab : string) {
      if (this.currentTab === tab) return
      this.currentTab = tab
      this.pageNum = 1
      this.orders = []
      this.loadOrders()
    },
    onRefresh() { 
      this.isRefreshing = true
      this.pageNum = 1
      this.loadOrders() 
    },
    loadMore() { 
      if (!this.hasMore || this.isLoading) return
      this.pageNum++
      this.loadOrders() 
    },
    getStatusClass(status : number) : string {
      const classMap = new Map<number, string>([
        [0, 'status-pending'], 
        [1, 'status-unshipped'], 
        [2, 'status-shipped'], 
        [3, 'status-completed'], 
        [4, 'status-cancelled'], 
        [5, 'status-closed']
      ])
      return classMap.get(status) ?? ''
    },
    formatPhone(phone : string) : string {
      if (phone.length >= 11) {
        return phone.substring(0, 3) + '****' + phone.substring(7)
      }
      return phone
    },
    goToDetail(id : number) { 
      uni.navigateTo({ url: `/pages/order/detail?id=${id}` }) 
    }
  }
})

export default __sfc__
function GenPagesSellerOrdersRender(this: InstanceType<typeof __sfc__>): any | null {
const _ctx = this
const _cache = this.$.renderCache
  return createElementVNode("view", utsMapOf({ class: "order-page" }), [
    createElementVNode("view", utsMapOf({ class: "tab-header" }), [
      createElementVNode("view", utsMapOf({
        class: normalizeClass(["tab-item", utsMapOf({ active: _ctx.currentTab === 'all' })]),
        onClick: () => {_ctx.switchTab('all')}
      }), [
        createElementVNode("text", utsMapOf({
          class: normalizeClass(["tab-text", utsMapOf({ 'tab-text--active': _ctx.currentTab === 'all' })])
        }), "全部", 2 /* CLASS */),
        _ctx.currentTab === 'all'
          ? createElementVNode("view", utsMapOf({
              key: 0,
              class: "active-line"
            }))
          : createCommentVNode("v-if", true)
      ], 10 /* CLASS, PROPS */, ["onClick"]),
      createElementVNode("view", utsMapOf({
        class: normalizeClass(["tab-item", utsMapOf({ active: _ctx.currentTab === 'unshipped' })]),
        onClick: () => {_ctx.switchTab('unshipped')}
      }), [
        createElementVNode("text", utsMapOf({
          class: normalizeClass(["tab-text", utsMapOf({ 'tab-text--active': _ctx.currentTab === 'unshipped' })])
        }), "待发货", 2 /* CLASS */),
        _ctx.currentTab === 'unshipped'
          ? createElementVNode("view", utsMapOf({
              key: 0,
              class: "active-line"
            }))
          : createCommentVNode("v-if", true)
      ], 10 /* CLASS, PROPS */, ["onClick"]),
      createElementVNode("view", utsMapOf({
        class: normalizeClass(["tab-item", utsMapOf({ active: _ctx.currentTab === 'shipped' })]),
        onClick: () => {_ctx.switchTab('shipped')}
      }), [
        createElementVNode("text", utsMapOf({
          class: normalizeClass(["tab-text", utsMapOf({ 'tab-text--active': _ctx.currentTab === 'shipped' })])
        }), "待收货", 2 /* CLASS */),
        _ctx.currentTab === 'shipped'
          ? createElementVNode("view", utsMapOf({
              key: 0,
              class: "active-line"
            }))
          : createCommentVNode("v-if", true)
      ], 10 /* CLASS, PROPS */, ["onClick"]),
      createElementVNode("view", utsMapOf({
        class: normalizeClass(["tab-item", utsMapOf({ active: _ctx.currentTab === 'completed' })]),
        onClick: () => {_ctx.switchTab('completed')}
      }), [
        createElementVNode("text", utsMapOf({
          class: normalizeClass(["tab-text", utsMapOf({ 'tab-text--active': _ctx.currentTab === 'completed' })])
        }), "已完成", 2 /* CLASS */),
        _ctx.currentTab === 'completed'
          ? createElementVNode("view", utsMapOf({
              key: 0,
              class: "active-line"
            }))
          : createCommentVNode("v-if", true)
      ], 10 /* CLASS, PROPS */, ["onClick"]),
      createElementVNode("view", utsMapOf({
        class: normalizeClass(["tab-item", utsMapOf({ active: _ctx.currentTab === 'cancelled' })]),
        onClick: () => {_ctx.switchTab('cancelled')}
      }), [
        createElementVNode("text", utsMapOf({
          class: normalizeClass(["tab-text", utsMapOf({ 'tab-text--active': _ctx.currentTab === 'cancelled' })])
        }), "已取消", 2 /* CLASS */),
        _ctx.currentTab === 'cancelled'
          ? createElementVNode("view", utsMapOf({
              key: 0,
              class: "active-line"
            }))
          : createCommentVNode("v-if", true)
      ], 10 /* CLASS, PROPS */, ["onClick"])
    ]),
    createElementVNode("scroll-view", utsMapOf({
      class: "order-scroll",
      "scroll-y": "true",
      onScrolltolower: _ctx.loadMore,
      onRefresherrefresh: _ctx.onRefresh,
      "refresher-enabled": true,
      "refresher-triggered": _ctx.isRefreshing,
      "show-scrollbar": false
    }), [
      isTrue(_ctx.isLoading && _ctx.orders.length === 0)
        ? createElementVNode("view", utsMapOf({
            key: 0,
            class: "loading-state"
          }), [
            createElementVNode("text", utsMapOf({ class: "loading-text" }), "加载中...")
          ])
        : createElementVNode("view", utsMapOf({
            key: 1,
            class: "order-list"
          }), [
            createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.orders, (order, __key, __index, _cached): any => {
              return createElementVNode("view", utsMapOf({
                class: "order-card",
                key: order.id,
                onClick: () => {_ctx.goToDetail(order.id)}
              }), [
                createElementVNode("view", utsMapOf({ class: "card-header" }), [
                  createElementVNode("view", utsMapOf({ class: "order-no-info" }), [
                    createElementVNode("text", utsMapOf({ class: "order-no-label" }), "订单号："),
                    createElementVNode("text", utsMapOf({ class: "order-no" }), toDisplayString(order.orderNo), 1 /* TEXT */)
                  ]),
                  createElementVNode("text", utsMapOf({
                    class: normalizeClass(["order-status", _ctx.getStatusClass(order.status)])
                  }), toDisplayString(order.statusDesc), 3 /* TEXT, CLASS */)
                ]),
                createElementVNode("view", utsMapOf({ class: "goods-preview" }), [
                  order.orderItems.length === 1
                    ? createElementVNode("view", utsMapOf({
                        key: 0,
                        class: "single-goods"
                      }), [
                        createElementVNode("image", utsMapOf({
                          class: "goods-img",
                          src: order.orderItems[0].productImage,
                          mode: "aspectFill"
                        }), null, 8 /* PROPS */, ["src"]),
                        createElementVNode("view", utsMapOf({ class: "goods-info" }), [
                          createElementVNode("text", utsMapOf({ class: "goods-name" }), toDisplayString(order.orderItems[0].productName), 1 /* TEXT */),
                          createElementVNode("text", utsMapOf({ class: "goods-spec" }), toDisplayString(order.orderItems[0].skuName), 1 /* TEXT */)
                        ]),
                        createElementVNode("view", utsMapOf({ class: "goods-price-qty" }), [
                          createElementVNode("text", utsMapOf({ class: "price" }), "¥" + toDisplayString(order.orderItems[0].price.toFixed(2)), 1 /* TEXT */),
                          createElementVNode("text", utsMapOf({ class: "qty" }), "x" + toDisplayString(order.orderItems[0].quantity), 1 /* TEXT */)
                        ])
                      ])
                    : createElementVNode("view", utsMapOf({
                        key: 1,
                        class: "multi-goods"
                      }), [
                        createElementVNode("scroll-view", utsMapOf({
                          "scroll-x": "true",
                          class: "goods-img-scroll",
                          "show-scrollbar": false
                        }), [
                          createElementVNode("view", utsMapOf({ class: "goods-img-list" }), [
                            createElementVNode(Fragment, null, RenderHelpers.renderList(order.orderItems, (item, idx, __index, _cached): any => {
                              return createElementVNode("image", utsMapOf({
                                key: idx,
                                class: "goods-img-small",
                                src: item.productImage,
                                mode: "aspectFill"
                              }), null, 8 /* PROPS */, ["src"])
                            }), 128 /* KEYED_FRAGMENT */)
                          ])
                        ]),
                        createElementVNode("view", utsMapOf({ class: "total-qty-info" }), [
                          createElementVNode("text", utsMapOf({ class: "price" }), "¥" + toDisplayString(order.payAmount.toFixed(2)), 1 /* TEXT */),
                          createElementVNode("text", utsMapOf({ class: "qty" }), "共" + toDisplayString(order.totalQuantity) + "件", 1 /* TEXT */)
                        ])
                      ])
                ]),
                createElementVNode("view", utsMapOf({ class: "receiver-info" }), [
                  createElementVNode("text", utsMapOf({ class: "receiver-icon" }), "📍"),
                  createElementVNode("text", utsMapOf({ class: "receiver-text" }), toDisplayString(order.receiverName) + " " + toDisplayString(_ctx.formatPhone(order.receiverPhone)), 1 /* TEXT */)
                ]),
                createElementVNode("view", utsMapOf({ class: "card-footer" }), [
                  createElementVNode("view", utsMapOf({ class: "order-time" }), [
                    createElementVNode("text", utsMapOf({ class: "time-text" }), toDisplayString(order.createTime), 1 /* TEXT */)
                  ]),
                  createElementVNode("view", utsMapOf({ class: "total-info" }), [
                    createElementVNode("text", utsMapOf({ class: "label" }), "实付"),
                    createElementVNode("text", utsMapOf({ class: "amount" }), "¥" + toDisplayString(order.payAmount.toFixed(2)), 1 /* TEXT */)
                  ])
                ]),
                order.status === 1
                  ? createElementVNode("view", utsMapOf({
                      key: 0,
                      class: "btn-area"
                    }), [
                      createElementVNode("view", utsMapOf({ class: "action-btn action-btn-primary" }), [
                        createElementVNode("text", utsMapOf({ class: "btn-text btn-text-primary" }), "自动发货中")
                      ])
                    ])
                  : createCommentVNode("v-if", true)
              ], 8 /* PROPS */, ["onClick"])
            }), 128 /* KEYED_FRAGMENT */)
          ]),
      isTrue(!_ctx.isLoading && _ctx.orders.length === 0)
        ? createElementVNode("view", utsMapOf({
            key: 2,
            class: "empty-state"
          }), [
            createElementVNode("text", utsMapOf({ class: "empty-icon" }), "📋"),
            createElementVNode("text", utsMapOf({ class: "empty-text" }), "暂无相关订单")
          ])
        : createCommentVNode("v-if", true),
      _ctx.orders.length > 0
        ? createElementVNode("view", utsMapOf({
            key: 3,
            class: "load-more"
          }), [
            createElementVNode("text", utsMapOf({ class: "load-more-text" }), toDisplayString(_ctx.hasMore ? '上拉加载更多' : '没有更多了'), 1 /* TEXT */)
          ])
        : createCommentVNode("v-if", true),
      createElementVNode("view", utsMapOf({
        style: normalizeStyle(utsMapOf({"height":"40rpx"}))
      }), null, 4 /* STYLE */)
    ], 40 /* PROPS, NEED_HYDRATION */, ["onScrolltolower", "onRefresherrefresh", "refresher-triggered"])
  ])
}
const GenPagesSellerOrdersStyles = [utsMapOf([["order-page", padStyleMapOf(utsMapOf([["backgroundColor", "#f7f8fa"], ["display", "flex"], ["flexDirection", "column"], ["flex", 1]]))], ["tab-header", padStyleMapOf(utsMapOf([["backgroundColor", "#ffffff"], ["display", "flex"], ["flexDirection", "row"], ["zIndex", 100], ["boxShadow", "0 1px 2px rgba(0, 0, 0, 0.05)"]]))], ["tab-item", padStyleMapOf(utsMapOf([["flex", 1], ["height", 44], ["display", "flex"], ["flexDirection", "column"], ["alignItems", "center"], ["justifyContent", "center"], ["position", "relative"]]))], ["tab-text", padStyleMapOf(utsMapOf([["fontSize", 13], ["color", "#666666"]]))], ["tab-text--active", padStyleMapOf(utsMapOf([["color", "#0066CC"], ["fontWeight", "700"]]))], ["active-line", padStyleMapOf(utsMapOf([["position", "absolute"], ["bottom", 0], ["width", 24], ["height", 3], ["backgroundColor", "#0066CC"], ["borderTopLeftRadius", 2], ["borderTopRightRadius", 2], ["borderBottomRightRadius", 2], ["borderBottomLeftRadius", 2]]))], ["order-scroll", padStyleMapOf(utsMapOf([["flex", 1], ["width", "100%"]]))], ["loading-state", padStyleMapOf(utsMapOf([["paddingTop", 60], ["paddingRight", 0], ["paddingBottom", 60], ["paddingLeft", 0], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["loading-text", padStyleMapOf(utsMapOf([["fontSize", 14], ["color", "#999999"]]))], ["order-list", padStyleMapOf(utsMapOf([["paddingTop", 12], ["paddingRight", 16], ["paddingBottom", 12], ["paddingLeft", 16], ["display", "flex"], ["flexDirection", "column"]]))], ["order-card", padStyleMapOf(utsMapOf([["backgroundColor", "#ffffff"], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12], ["paddingTop", 16], ["paddingRight", 16], ["paddingBottom", 16], ["paddingLeft", 16], ["marginBottom", 12], ["boxShadow", "0 2px 8px rgba(0, 0, 0, 0.02)"], ["display", "flex"], ["flexDirection", "column"]]))], ["card-header", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["justifyContent", "space-between"], ["alignItems", "center"], ["marginBottom", 16]]))], ["order-no-info", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"]]))], ["order-no-label", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "#999999"]]))], ["order-no", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "#666666"]]))], ["order-status", padStyleMapOf(utsMapOf([["fontSize", 13], ["fontWeight", "400"]]))], ["status-pending", padStyleMapOf(utsMapOf([["color", "#ff9800"]]))], ["status-unshipped", padStyleMapOf(utsMapOf([["color", "#2196f3"]]))], ["status-shipped", padStyleMapOf(utsMapOf([["color", "#0066CC"]]))], ["status-completed", padStyleMapOf(utsMapOf([["color", "#4caf50"]]))], ["status-cancelled", padStyleMapOf(utsMapOf([["color", "#999999"]]))], ["status-closed", padStyleMapOf(utsMapOf([["color", "#cccccc"]]))], ["goods-preview", padStyleMapOf(utsMapOf([["marginBottom", 12], ["display", "flex"], ["flexDirection", "column"]]))], ["single-goods", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"]]))], ["goods-img", padStyleMapOf(utsMapOf([["width", 70], ["height", 70], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#f5f5f5"], ["marginRight", 12]]))], ["goods-info", padStyleMapOf(utsMapOf([["flex", 1], ["display", "flex"], ["flexDirection", "column"], ["justifyContent", "flex-start"]]))], ["goods-name", padStyleMapOf(utsMapOf([["fontSize", 14], ["color", "#333333"], ["marginBottom", 8], ["lineHeight", 1.4]]))], ["goods-spec", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "#999999"], ["backgroundColor", "#f5f5f5"], ["paddingTop", 2], ["paddingRight", 6], ["paddingBottom", 2], ["paddingLeft", 6], ["borderTopLeftRadius", 4], ["borderTopRightRadius", 4], ["borderBottomRightRadius", 4], ["borderBottomLeftRadius", 4], ["alignSelf", "flex-start"]]))], ["goods-price-qty", padStyleMapOf(utsMapOf([["textAlign", "right"], ["display", "flex"], ["flexDirection", "column"], ["alignItems", "flex-end"], ["minWidth", 60]]))], ["price", padStyleMapOf(utsMapOf([["fontSize", 14], ["color", "#333333"], ["fontWeight", "700"], ["marginBottom", 4]]))], ["qty", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "#999999"]]))], ["multi-goods", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"]]))], ["goods-img-scroll", padStyleMapOf(utsMapOf([["flex", 1], ["whiteSpace", "nowrap"], ["marginRight", 12]]))], ["goods-img-list", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"]]))], ["goods-img-small", padStyleMapOf(utsMapOf([["width", 60], ["height", 60], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#f5f5f5"], ["marginRight", 8]]))], ["total-qty-info", padStyleMapOf(utsMapOf([["textAlign", "right"], ["minWidth", 80], ["display", "flex"], ["flexDirection", "column"], ["alignItems", "flex-end"]]))], ["receiver-info", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["paddingTop", 8], ["paddingRight", 12], ["paddingBottom", 8], ["paddingLeft", 12], ["backgroundColor", "#f8f9fa"], ["borderTopLeftRadius", 6], ["borderTopRightRadius", 6], ["borderBottomRightRadius", 6], ["borderBottomLeftRadius", 6], ["marginBottom", 12]]))], ["receiver-icon", padStyleMapOf(utsMapOf([["fontSize", 14], ["marginRight", 6]]))], ["receiver-text", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "#666666"]]))], ["card-footer", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["justifyContent", "space-between"], ["alignItems", "center"], ["borderTopWidth", 1], ["borderTopStyle", "solid"], ["borderTopColor", "#f5f5f5"], ["paddingTop", 12]]))], ["order-time", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"]]))], ["time-text", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "#999999"]]))], ["total-info", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"]]))], ["label", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "#999999"], ["marginRight", 4]]))], ["amount", padStyleMapOf(utsMapOf([["fontSize", 16], ["fontWeight", "700"], ["color", "#ff6b00"]]))], ["btn-area", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["justifyContent", "flex-end"], ["marginTop", 12], ["paddingTop", 12], ["borderTopWidth", 1], ["borderTopStyle", "solid"], ["borderTopColor", "#f5f5f5"]]))], ["action-btn", padStyleMapOf(utsMapOf([["paddingTop", 0], ["paddingRight", 16], ["paddingBottom", 0], ["paddingLeft", 16], ["height", 32], ["borderTopLeftRadius", 16], ["borderTopRightRadius", 16], ["borderBottomRightRadius", 16], ["borderBottomLeftRadius", 16], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"], ["marginTop", 0], ["marginRight", 0], ["marginBottom", 0], ["marginLeft", 8]]))], ["action-btn-primary", padStyleMapOf(utsMapOf([["backgroundColor", "#ffffff"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#0066CC"], ["borderRightColor", "#0066CC"], ["borderBottomColor", "#0066CC"], ["borderLeftColor", "#0066CC"]]))], ["btn-text", padStyleMapOf(utsMapOf([["fontSize", 13]]))], ["btn-text-primary", padStyleMapOf(utsMapOf([["color", "#0066CC"]]))], ["empty-state", padStyleMapOf(utsMapOf([["paddingTop", 60], ["paddingRight", 0], ["paddingBottom", 60], ["paddingLeft", 0], ["display", "flex"], ["flexDirection", "column"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["empty-icon", padStyleMapOf(utsMapOf([["fontSize", 48], ["marginBottom", 16], ["color", "#cccccc"]]))], ["empty-text", padStyleMapOf(utsMapOf([["fontSize", 14], ["color", "#999999"]]))], ["load-more", padStyleMapOf(utsMapOf([["paddingTop", 20], ["paddingRight", 0], ["paddingBottom", 20], ["paddingLeft", 0], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["load-more-text", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "#999999"]]))]])]
