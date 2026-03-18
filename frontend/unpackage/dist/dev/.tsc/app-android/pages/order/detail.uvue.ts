
import { getOrderDetail, cancelOrder, payOrder, confirmReceive, deleteOrder, getOrderLogistics, type OrderVO, type OrderItemVO, type LogisticsVO, type LogisticsTrack } from '@/api/order'

const __sfc__ = defineComponent({
  data() {
    return {
      orderId: 0,
      isLoading: true,
      showLogisticsModal: false,
      orderDetail: {
        id: 0,
        orderNo: '',
        totalAmount: 0,
        payAmount: 0,
        freightAmount: 0,
        status: 0,
        statusDesc: '',
        receiverName: '',
        receiverPhone: '',
        receiverAddress: '',
        remark: null,
        payTime: null,
        deliverTime: null,
        receiveTime: null,
        cancelTime: null,
        cancelReason: null,
        createTime: '',
        orderItems: [] as OrderItemVO[],
        totalQuantity: 0
      } as OrderVO,
      logistics: {
        orderId: 0,
        orderNo: '',
        logisticsNo: '',
        logisticsCompany: '',
        status: 0,
        statusDesc: '',
        delivered: false,
        deliveredDesc: '',
        shipTime: null,
        receiveTime: null,
        autoReceiveTime: null,
        tracks: [] as LogisticsTrack[]
      } as LogisticsVO
    }
  },
  computed: {
    hasActions() : boolean {
      const status = this.orderDetail.status
      return status === 0 || status === 2 || status === 3 || status === 4 || status === 5
    }
  },
  onLoad(options : any) {
    const opts = options as UTSJSONObject
    const id = opts.get('id')
    if (id != null) {
      this.orderId = parseInt(id as string)
      this.loadOrderDetail()
    }
  },
  onShow() {
    if (this.orderId > 0) {
      this.loadOrderDetail()
    }
  },
  methods: {
    loadOrderDetail() {
      this.isLoading = true
      getOrderDetail(this.orderId).then((res) => {
        const data = res.data as UTSJSONObject
        this.parseOrderDetail(data)
        this.isLoading = false
        // 如果是待收货或已完成状态，加载物流信息
        if (this.orderDetail.status === 2 || this.orderDetail.status === 3) {
          this.loadLogistics()
        }
      }).catch((err) => {
        console.error('获取订单详情失败:', err, " at pages/order/detail.uvue:249")
        this.isLoading = false
        uni.showToast({ title: '获取订单详情失败', icon: 'none' })
      })
    },
    loadLogistics() {
      getOrderLogistics(this.orderId).then((res) => {
        const data = res.data as UTSJSONObject
        this.parseLogistics(data)
      }).catch((err) => {
        console.error('获取物流信息失败:', err, " at pages/order/detail.uvue:259")
      })
    },
    parseLogistics(data : UTSJSONObject) {
      const tracksArr = data.getArray('tracks')
      const tracks : LogisticsTrack[] = []
      if (tracksArr != null) {
        for (let i = 0; i < tracksArr.length; i++) {
          const t = tracksArr[i] as UTSJSONObject
          tracks.push({
            time: t.getString('time') ?? '',
            status: t.getString('status') ?? '',
            desc: t.getString('desc') ?? ''
          } as LogisticsTrack)
        }
      }
      this.logistics = {
        orderId: (data.getNumber('orderId') ?? 0).toInt(),
        orderNo: data.getString('orderNo') ?? '',
        logisticsNo: data.getString('logisticsNo') ?? '',
        logisticsCompany: data.getString('logisticsCompany') ?? '',
        status: (data.getNumber('status') ?? 0).toInt(),
        statusDesc: data.getString('statusDesc') ?? '',
        delivered: data.getBoolean('delivered') ?? false,
        deliveredDesc: data.getString('deliveredDesc') ?? '',
        shipTime: data.getString('shipTime'),
        receiveTime: data.getString('receiveTime'),
        autoReceiveTime: data.getString('autoReceiveTime'),
        tracks: tracks
      } as LogisticsVO
    },
    parseOrderDetail(data : UTSJSONObject) {
      const itemsArr = data.getArray('orderItems')
      const orderItems : OrderItemVO[] = []
      if (itemsArr != null) {
        for (let i = 0; i < itemsArr.length; i++) {
          const item = itemsArr[i] as UTSJSONObject
          orderItems.push({
            id: (item.getNumber('id') ?? 0).toInt(),
            productId: (item.getNumber('productId') ?? 0).toInt(),
            skuId: (item.getNumber('skuId') ?? 0).toInt(),
            productName: item.getString('productName') ?? '',
            skuName: item.getString('skuName') ?? '',
            productImage: item.getString('productImage') ?? '',
            price: item.getNumber('price') ?? 0,
            quantity: (item.getNumber('quantity') ?? 0).toInt(),
            totalPrice: item.getNumber('totalPrice') ?? 0,
            reviewed: item.getBoolean('reviewed') ?? false
          } as OrderItemVO)
        }
      }
      this.orderDetail = {
        id: (data.getNumber('id') ?? 0).toInt(),
        orderNo: data.getString('orderNo') ?? '',
        totalAmount: data.getNumber('totalAmount') ?? 0,
        payAmount: data.getNumber('payAmount') ?? 0,
        freightAmount: data.getNumber('freightAmount') ?? 0,
        status: (data.getNumber('status') ?? 0).toInt(),
        statusDesc: data.getString('statusDesc') ?? '',
        receiverName: data.getString('receiverName') ?? '',
        receiverPhone: data.getString('receiverPhone') ?? '',
        receiverAddress: data.getString('receiverAddress') ?? '',
        remark: data.getString('remark'),
        payTime: data.getString('payTime'),
        deliverTime: data.getString('deliverTime'),
        receiveTime: data.getString('receiveTime'),
        cancelTime: data.getString('cancelTime'),
        cancelReason: data.getString('cancelReason'),
        createTime: data.getString('createTime') ?? '',
        orderItems: orderItems,
        totalQuantity: (data.getNumber('totalQuantity') ?? 0).toInt(),
        reviewed: data.getBoolean('reviewed') ?? false,
        reviewCount: (data.getNumber('reviewCount') ?? 0).toInt()
      } as OrderVO
    },
    formatAutoTime(timeStr : string | null) : string {
      if (timeStr == null) return ''
      if (timeStr.length >= 16) {
        return timeStr.substring(5, 16)
      }
      return timeStr
    },
    getStatusIcon(status : number) : string {
      const iconMap = new Map<number, string>([[0, '⏳'], [1, '✅'], [2, '🚚'], [3, '🎉'], [4, '❌'], [5, '⏰']])
      return iconMap.get(status) ?? '❓'
    },
    showLogisticsDetail() {
      this.showLogisticsModal = true
    },
    goToProduct(productId : number) {
      uni.navigateTo({ url: `/pages/product/detail?id=${productId}` })
    },
    handleCancelOrder() {
      uni.showModal({ title: '确认取消', content: '确定要取消这个订单吗？', success: (res) => {
        if (res.confirm) {
          cancelOrder(this.orderId, '用户取消').then(() => {
            this.orderDetail.status = 4
            this.orderDetail.statusDesc = '已取消'
            uni.showToast({ title: '订单已取消', icon: 'success' })
          }).catch((err) => { console.error('取消订单失败:', err, " at pages/order/detail.uvue:358"); uni.showToast({ title: '取消失败', icon: 'none' }) })
        }
      }})
    },
    handlePayOrder() {
      uni.navigateTo({
        url: `/pages/order/pay?orderId=${this.orderId}&orderNo=${this.orderDetail.orderNo}&amount=${this.orderDetail.payAmount}`
      })
    },
    handleConfirmReceipt() {
      uni.showModal({ title: '确认收货', content: '确认已收到商品吗？', success: (res) => {
        if (res.confirm) {
          confirmReceive(this.orderId).then(() => {
            this.orderDetail.status = 3
            this.orderDetail.statusDesc = '已完成'
            uni.showToast({ title: '确认收货成功', icon: 'success' })
          }).catch((err) => { console.error('确认收货失败:', err, " at pages/order/detail.uvue:374"); uni.showToast({ title: '操作失败', icon: 'none' }) })
        }
      }})
    },
    handleReview() {
      const items = this.orderDetail.orderItems
      if (items == null || items.length === 0) {
        uni.showToast({ title: '订单商品为空', icon: 'none' })
        return
      }
      const pending : OrderItemVO[] = []
      for (let i = 0; i < items.length; i++) {
        const it = items[i]
        if (it.reviewed != true) pending.push(it)
      }
      if (pending.length === 0) {
        uni.showToast({ title: '该订单已全部评价', icon: 'none' })
        return
      }
      const first = pending[0]
      uni.navigateTo({
        url: `/pages/order/review?orderId=${this.orderId}&productId=${first.productId}&productName=${UTSAndroid.consoleDebugError(encodeURIComponent(first.productName), " at pages/order/detail.uvue:395")}&productImage=${UTSAndroid.consoleDebugError(encodeURIComponent(first.productImage), " at pages/order/detail.uvue:395")}&reviewIndex=0&reviewTotal=${pending.length}`
      })
    },
    handleDeleteOrder() {
      uni.showModal({ title: '提示', content: '确定删除订单记录吗？', success: (res) => {
        if (res.confirm) {
          deleteOrder(this.orderId).then(() => {
            uni.showToast({ title: '删除成功', icon: 'success' })
            setTimeout(() => { uni.navigateBack() }, 1500)
          }).catch((err) => { console.error('删除订单失败:', err, " at pages/order/detail.uvue:404"); uni.showToast({ title: '删除失败', icon: 'none' }) })
        }
      }})
    }
  }
})

export default __sfc__
function GenPagesOrderDetailRender(this: InstanceType<typeof __sfc__>): any | null {
const _ctx = this
const _cache = this.$.renderCache
  return createElementVNode("view", utsMapOf({ class: "order-detail-page" }), [
    isTrue(_ctx.isLoading)
      ? createElementVNode("view", utsMapOf({
          key: 0,
          class: "loading-state"
        }), [
          createElementVNode("text", utsMapOf({ class: "loading-text" }), "加载中...")
        ])
      : createElementVNode(Fragment, utsMapOf({ key: 1 }), [
          createElementVNode("view", utsMapOf({ class: "status-section" }), [
            createElementVNode("view", utsMapOf({ class: "status-icon" }), toDisplayString(_ctx.getStatusIcon(_ctx.orderDetail.status)), 1 /* TEXT */),
            createElementVNode("text", utsMapOf({ class: "status-text" }), toDisplayString(_ctx.orderDetail.statusDesc), 1 /* TEXT */),
            isTrue(_ctx.orderDetail.status === 2 && _ctx.logistics.autoReceiveTime != null)
              ? createElementVNode("text", utsMapOf({
                  key: 0,
                  class: "status-tip"
                }), toDisplayString(_ctx.logistics.delivered ? '已送达，等待签收' : '运输中') + " · 将于 " + toDisplayString(_ctx.formatAutoTime(_ctx.logistics.autoReceiveTime)) + " 自动确认 ", 1 /* TEXT */)
              : createCommentVNode("v-if", true)
          ]),
          isTrue((_ctx.orderDetail.status === 2 || _ctx.orderDetail.status === 3) && _ctx.logistics.logisticsNo != '')
            ? createElementVNode("view", utsMapOf({
                key: 0,
                class: "logistics-section",
                onClick: _ctx.showLogisticsDetail
              }), [
                createElementVNode("view", utsMapOf({ class: "section-header" }), [
                  createElementVNode("text", utsMapOf({ class: "section-icon" }), "🚚"),
                  createElementVNode("text", utsMapOf({ class: "section-title" }), "物流信息"),
                  createElementVNode("text", utsMapOf({ class: "view-more" }), "查看详情 ›")
                ]),
                createElementVNode("view", utsMapOf({ class: "logistics-brief" }), [
                  createElementVNode("view", utsMapOf({ class: "logistics-company" }), [
                    createElementVNode("text", utsMapOf({ class: "company-name" }), toDisplayString(_ctx.logistics.logisticsCompany), 1 /* TEXT */),
                    createElementVNode("text", utsMapOf({ class: "logistics-no" }), toDisplayString(_ctx.logistics.logisticsNo), 1 /* TEXT */)
                  ]),
                  _ctx.logistics.tracks.length > 0
                    ? createElementVNode("view", utsMapOf({
                        key: 0,
                        class: "latest-track"
                      }), [
                        createElementVNode("text", utsMapOf({ class: "track-status" }), toDisplayString(_ctx.logistics.tracks[0].status), 1 /* TEXT */),
                        createElementVNode("text", utsMapOf({ class: "track-desc" }), toDisplayString(_ctx.logistics.tracks[0].desc), 1 /* TEXT */),
                        createElementVNode("text", utsMapOf({ class: "track-time" }), toDisplayString(_ctx.logistics.tracks[0].time), 1 /* TEXT */)
                      ])
                    : createCommentVNode("v-if", true)
                ])
              ], 8 /* PROPS */, ["onClick"])
            : createCommentVNode("v-if", true),
          createElementVNode("view", utsMapOf({ class: "address-section" }), [
            createElementVNode("view", utsMapOf({ class: "section-header" }), [
              createElementVNode("text", utsMapOf({ class: "section-icon" }), "📍"),
              createElementVNode("text", utsMapOf({ class: "section-title" }), "收货信息")
            ]),
            createElementVNode("view", utsMapOf({ class: "address-info" }), [
              createElementVNode("view", utsMapOf({ class: "address-row" }), [
                createElementVNode("text", utsMapOf({ class: "receiver-name" }), toDisplayString(_ctx.orderDetail.receiverName), 1 /* TEXT */),
                createElementVNode("text", utsMapOf({ class: "receiver-phone" }), toDisplayString(_ctx.orderDetail.receiverPhone), 1 /* TEXT */)
              ]),
              createElementVNode("text", utsMapOf({ class: "receiver-address" }), toDisplayString(_ctx.orderDetail.receiverAddress), 1 /* TEXT */)
            ])
          ]),
          createElementVNode("view", utsMapOf({ class: "goods-section" }), [
            createElementVNode("view", utsMapOf({ class: "section-header" }), [
              createElementVNode("text", utsMapOf({ class: "section-icon" }), "📦"),
              createElementVNode("text", utsMapOf({ class: "section-title" }), "商品信息")
            ]),
            createElementVNode("view", utsMapOf({ class: "goods-list" }), [
              createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.orderDetail.orderItems, (goods, index, __index, _cached): any => {
                return createElementVNode("view", utsMapOf({
                  class: "goods-item",
                  key: index,
                  onClick: () => {_ctx.goToProduct(goods.productId)}
                }), [
                  createElementVNode("image", utsMapOf({
                    class: "goods-image",
                    src: goods.productImage,
                    mode: "aspectFill"
                  }), null, 8 /* PROPS */, ["src"]),
                  createElementVNode("view", utsMapOf({ class: "goods-info" }), [
                    createElementVNode("text", utsMapOf({ class: "goods-name" }), toDisplayString(goods.productName), 1 /* TEXT */),
                    isTrue(goods.skuName)
                      ? createElementVNode("text", utsMapOf({
                          key: 0,
                          class: "goods-spec"
                        }), toDisplayString(goods.skuName), 1 /* TEXT */)
                      : createCommentVNode("v-if", true),
                    createElementVNode("view", utsMapOf({ class: "goods-price-row" }), [
                      createElementVNode("text", utsMapOf({ class: "goods-price" }), "¥" + toDisplayString(goods.price.toFixed(2)), 1 /* TEXT */),
                      createElementVNode("text", utsMapOf({ class: "goods-quantity" }), "×" + toDisplayString(goods.quantity), 1 /* TEXT */)
                    ])
                  ])
                ], 8 /* PROPS */, ["onClick"])
              }), 128 /* KEYED_FRAGMENT */)
            ])
          ]),
          createElementVNode("view", utsMapOf({ class: "price-section" }), [
            createElementVNode("view", utsMapOf({ class: "section-header" }), [
              createElementVNode("text", utsMapOf({ class: "section-icon" }), "💰"),
              createElementVNode("text", utsMapOf({ class: "section-title" }), "费用明细")
            ]),
            createElementVNode("view", utsMapOf({ class: "price-list" }), [
              createElementVNode("view", utsMapOf({ class: "price-row" }), [
                createElementVNode("text", utsMapOf({ class: "price-label" }), "商品总价"),
                createElementVNode("text", utsMapOf({ class: "price-value" }), "¥" + toDisplayString(_ctx.orderDetail.totalAmount.toFixed(2)), 1 /* TEXT */)
              ]),
              createElementVNode("view", utsMapOf({ class: "price-row" }), [
                createElementVNode("text", utsMapOf({ class: "price-label" }), "运费"),
                createElementVNode("text", utsMapOf({ class: "price-value" }), "¥" + toDisplayString(_ctx.orderDetail.freightAmount.toFixed(2)), 1 /* TEXT */)
              ]),
              createElementVNode("view", utsMapOf({ class: "price-row price-row-total" }), [
                createElementVNode("text", utsMapOf({ class: "price-label price-label-total" }), "实付金额"),
                createElementVNode("text", utsMapOf({ class: "price-value price-value-total" }), "¥" + toDisplayString(_ctx.orderDetail.payAmount.toFixed(2)), 1 /* TEXT */)
              ])
            ])
          ]),
          createElementVNode("view", utsMapOf({ class: "order-section" }), [
            createElementVNode("view", utsMapOf({ class: "section-header" }), [
              createElementVNode("text", utsMapOf({ class: "section-icon" }), "📄"),
              createElementVNode("text", utsMapOf({ class: "section-title" }), "订单信息")
            ]),
            createElementVNode("view", utsMapOf({ class: "order-info" }), [
              createElementVNode("view", utsMapOf({ class: "info-row" }), [
                createElementVNode("text", utsMapOf({ class: "info-label" }), "订单编号"),
                createElementVNode("text", utsMapOf({ class: "info-value" }), toDisplayString(_ctx.orderDetail.orderNo), 1 /* TEXT */)
              ]),
              createElementVNode("view", utsMapOf({ class: "info-row" }), [
                createElementVNode("text", utsMapOf({ class: "info-label" }), "下单时间"),
                createElementVNode("text", utsMapOf({ class: "info-value" }), toDisplayString(_ctx.orderDetail.createTime), 1 /* TEXT */)
              ]),
              isTrue(_ctx.orderDetail.payTime)
                ? createElementVNode("view", utsMapOf({
                    key: 0,
                    class: "info-row"
                  }), [
                    createElementVNode("text", utsMapOf({ class: "info-label" }), "支付时间"),
                    createElementVNode("text", utsMapOf({ class: "info-value" }), toDisplayString(_ctx.orderDetail.payTime), 1 /* TEXT */)
                  ])
                : createCommentVNode("v-if", true),
              isTrue(_ctx.orderDetail.deliverTime)
                ? createElementVNode("view", utsMapOf({
                    key: 1,
                    class: "info-row"
                  }), [
                    createElementVNode("text", utsMapOf({ class: "info-label" }), "发货时间"),
                    createElementVNode("text", utsMapOf({ class: "info-value" }), toDisplayString(_ctx.orderDetail.deliverTime), 1 /* TEXT */)
                  ])
                : createCommentVNode("v-if", true),
              isTrue(_ctx.orderDetail.receiveTime)
                ? createElementVNode("view", utsMapOf({
                    key: 2,
                    class: "info-row"
                  }), [
                    createElementVNode("text", utsMapOf({ class: "info-label" }), "收货时间"),
                    createElementVNode("text", utsMapOf({ class: "info-value" }), toDisplayString(_ctx.orderDetail.receiveTime), 1 /* TEXT */)
                  ])
                : createCommentVNode("v-if", true),
              isTrue(_ctx.orderDetail.remark)
                ? createElementVNode("view", utsMapOf({
                    key: 3,
                    class: "info-row"
                  }), [
                    createElementVNode("text", utsMapOf({ class: "info-label" }), "订单备注"),
                    createElementVNode("text", utsMapOf({ class: "info-value" }), toDisplayString(_ctx.orderDetail.remark), 1 /* TEXT */)
                  ])
                : createCommentVNode("v-if", true)
            ])
          ]),
          isTrue(_ctx.hasActions)
            ? createElementVNode("view", utsMapOf({
                key: 1,
                class: "bottom-actions"
              }), [
                _ctx.orderDetail.status === 0
                  ? createElementVNode("view", utsMapOf({
                      key: 0,
                      class: "action-btn action-btn-secondary",
                      onClick: _ctx.handleCancelOrder
                    }), [
                      createElementVNode("text", utsMapOf({ class: "action-btn-text action-btn-text-secondary" }), "取消订单")
                    ], 8 /* PROPS */, ["onClick"])
                  : createCommentVNode("v-if", true),
                _ctx.orderDetail.status === 0
                  ? createElementVNode("view", utsMapOf({
                      key: 1,
                      class: "action-btn action-btn-primary",
                      onClick: _ctx.handlePayOrder
                    }), [
                      createElementVNode("text", utsMapOf({ class: "action-btn-text action-btn-text-primary" }), "立即支付")
                    ], 8 /* PROPS */, ["onClick"])
                  : createCommentVNode("v-if", true),
                _ctx.orderDetail.status === 2
                  ? createElementVNode("view", utsMapOf({
                      key: 2,
                      class: "action-btn action-btn-primary",
                      onClick: _ctx.handleConfirmReceipt
                    }), [
                      createElementVNode("text", utsMapOf({ class: "action-btn-text action-btn-text-primary" }), "确认收货")
                    ], 8 /* PROPS */, ["onClick"])
                  : createCommentVNode("v-if", true),
                isTrue(_ctx.orderDetail.status === 3 && _ctx.orderDetail.reviewed != true)
                  ? createElementVNode("view", utsMapOf({
                      key: 3,
                      class: "action-btn action-btn-secondary",
                      onClick: _ctx.handleReview
                    }), [
                      createElementVNode("text", utsMapOf({ class: "action-btn-text action-btn-text-secondary" }), "评价")
                    ], 8 /* PROPS */, ["onClick"])
                  : createCommentVNode("v-if", true),
                isTrue(_ctx.orderDetail.status === 3 || _ctx.orderDetail.status === 4 || _ctx.orderDetail.status === 5)
                  ? createElementVNode("view", utsMapOf({
                      key: 4,
                      class: "action-btn action-btn-secondary",
                      onClick: _ctx.handleDeleteOrder
                    }), [
                      createElementVNode("text", utsMapOf({ class: "action-btn-text action-btn-text-secondary" }), "删除订单")
                    ], 8 /* PROPS */, ["onClick"])
                  : createCommentVNode("v-if", true)
              ])
            : createCommentVNode("v-if", true)
        ], 64 /* STABLE_FRAGMENT */),
    isTrue(_ctx.showLogisticsModal)
      ? createElementVNode("view", utsMapOf({
          key: 2,
          class: "logistics-modal",
          onClick: () => {_ctx.showLogisticsModal = false}
        }), [
          createElementVNode("view", utsMapOf({
            class: "modal-content",
            onClick: withModifiers(() => {}, ["stop"])
          }), [
            createElementVNode("view", utsMapOf({ class: "modal-header" }), [
              createElementVNode("text", utsMapOf({ class: "modal-title" }), "物流详情"),
              createElementVNode("text", utsMapOf({
                class: "modal-close",
                onClick: () => {_ctx.showLogisticsModal = false}
              }), "×", 8 /* PROPS */, ["onClick"])
            ]),
            createElementVNode("view", utsMapOf({ class: "modal-company" }), [
              createElementVNode("text", utsMapOf({ class: "company-label" }), toDisplayString(_ctx.logistics.logisticsCompany), 1 /* TEXT */),
              createElementVNode("text", utsMapOf({ class: "company-no" }), toDisplayString(_ctx.logistics.logisticsNo), 1 /* TEXT */)
            ]),
            createElementVNode("scroll-view", utsMapOf({
              class: "tracks-scroll",
              "scroll-y": "true"
            }), [
              createElementVNode("view", utsMapOf({ class: "tracks-list" }), [
                createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.logistics.tracks, (track, index, __index, _cached): any => {
                  return createElementVNode("view", utsMapOf({
                    class: "track-item",
                    key: index
                  }), [
                    createElementVNode("view", utsMapOf({
                      class: normalizeClass(["track-dot", utsMapOf({ 'track-dot-active': index === 0 })])
                    }), null, 2 /* CLASS */),
                    index < _ctx.logistics.tracks.length - 1
                      ? createElementVNode("view", utsMapOf({
                          key: 0,
                          class: "track-line"
                        }))
                      : createCommentVNode("v-if", true),
                    createElementVNode("view", utsMapOf({ class: "track-content" }), [
                      createElementVNode("text", utsMapOf({
                        class: normalizeClass(["track-status", utsMapOf({ 'track-status-active': index === 0 })])
                      }), toDisplayString(track.status), 3 /* TEXT, CLASS */),
                      createElementVNode("text", utsMapOf({ class: "track-desc" }), toDisplayString(track.desc), 1 /* TEXT */),
                      createElementVNode("text", utsMapOf({ class: "track-time" }), toDisplayString(track.time), 1 /* TEXT */)
                    ])
                  ])
                }), 128 /* KEYED_FRAGMENT */)
              ])
            ])
          ], 8 /* PROPS */, ["onClick"])
        ], 8 /* PROPS */, ["onClick"])
      : createCommentVNode("v-if", true),
    createElementVNode("view", utsMapOf({ class: "bottom-placeholder" }))
  ])
}
const GenPagesOrderDetailStyles = [utsMapOf([["order-detail-page", padStyleMapOf(utsMapOf([["backgroundColor", "#f5f5f5"], ["paddingBottom", "140rpx"], ["minHeight", "1500rpx"]]))], ["loading-state", padStyleMapOf(utsMapOf([["paddingTop", 100], ["paddingRight", 0], ["paddingBottom", 100], ["paddingLeft", 0], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["loading-text", padStyleMapOf(utsMapOf([["fontSize", 14], ["color", "#999999"]]))], ["status-section", padStyleMapOf(utsMapOf([["backgroundColor", "#0066CC"], ["paddingTop", "60rpx"], ["paddingRight", "30rpx"], ["paddingBottom", "60rpx"], ["paddingLeft", "30rpx"], ["textAlign", "center"], ["color", "#ffffff"]]))], ["status-icon", padStyleMapOf(utsMapOf([["fontSize", "80rpx"], ["marginBottom", "20rpx"]]))], ["status-text", padStyleMapOf(utsMapOf([["fontSize", "36rpx"], ["fontWeight", "700"]]))], ["status-tip", padStyleMapOf(utsMapOf([["fontSize", "24rpx"], ["color", "rgba(255,255,255,0.8)"], ["marginTop", "16rpx"]]))], ["logistics-section", padStyleMapOf(utsMapOf([["backgroundColor", "#ffffff"], ["marginTop", "20rpx"], ["marginRight", "30rpx"], ["marginBottom", "20rpx"], ["marginLeft", "30rpx"], ["borderTopLeftRadius", "16rpx"], ["borderTopRightRadius", "16rpx"], ["borderBottomRightRadius", "16rpx"], ["borderBottomLeftRadius", "16rpx"], ["paddingTop", "30rpx"], ["paddingRight", "30rpx"], ["paddingBottom", "30rpx"], ["paddingLeft", "30rpx"], ["boxShadow", "0 4rpx 12rpx rgba(0, 0, 0, 0.05)"]]))], ["address-section", padStyleMapOf(utsMapOf([["backgroundColor", "#ffffff"], ["marginTop", "20rpx"], ["marginRight", "30rpx"], ["marginBottom", "20rpx"], ["marginLeft", "30rpx"], ["borderTopLeftRadius", "16rpx"], ["borderTopRightRadius", "16rpx"], ["borderBottomRightRadius", "16rpx"], ["borderBottomLeftRadius", "16rpx"], ["paddingTop", "30rpx"], ["paddingRight", "30rpx"], ["paddingBottom", "30rpx"], ["paddingLeft", "30rpx"], ["boxShadow", "0 4rpx 12rpx rgba(0, 0, 0, 0.05)"]]))], ["goods-section", padStyleMapOf(utsMapOf([["backgroundColor", "#ffffff"], ["marginTop", "20rpx"], ["marginRight", "30rpx"], ["marginBottom", "20rpx"], ["marginLeft", "30rpx"], ["borderTopLeftRadius", "16rpx"], ["borderTopRightRadius", "16rpx"], ["borderBottomRightRadius", "16rpx"], ["borderBottomLeftRadius", "16rpx"], ["paddingTop", "30rpx"], ["paddingRight", "30rpx"], ["paddingBottom", "30rpx"], ["paddingLeft", "30rpx"], ["boxShadow", "0 4rpx 12rpx rgba(0, 0, 0, 0.05)"]]))], ["price-section", padStyleMapOf(utsMapOf([["backgroundColor", "#ffffff"], ["marginTop", "20rpx"], ["marginRight", "30rpx"], ["marginBottom", "20rpx"], ["marginLeft", "30rpx"], ["borderTopLeftRadius", "16rpx"], ["borderTopRightRadius", "16rpx"], ["borderBottomRightRadius", "16rpx"], ["borderBottomLeftRadius", "16rpx"], ["paddingTop", "30rpx"], ["paddingRight", "30rpx"], ["paddingBottom", "30rpx"], ["paddingLeft", "30rpx"], ["boxShadow", "0 4rpx 12rpx rgba(0, 0, 0, 0.05)"]]))], ["order-section", padStyleMapOf(utsMapOf([["backgroundColor", "#ffffff"], ["marginTop", "20rpx"], ["marginRight", "30rpx"], ["marginBottom", "20rpx"], ["marginLeft", "30rpx"], ["borderTopLeftRadius", "16rpx"], ["borderTopRightRadius", "16rpx"], ["borderBottomRightRadius", "16rpx"], ["borderBottomLeftRadius", "16rpx"], ["paddingTop", "30rpx"], ["paddingRight", "30rpx"], ["paddingBottom", "30rpx"], ["paddingLeft", "30rpx"], ["boxShadow", "0 4rpx 12rpx rgba(0, 0, 0, 0.05)"]]))], ["section-header", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["marginBottom", "24rpx"]]))], ["section-icon", padStyleMapOf(utsMapOf([["fontSize", "32rpx"], ["marginRight", "12rpx"]]))], ["section-title", padStyleMapOf(utsMapOf([["fontSize", "32rpx"], ["fontWeight", "700"], ["color", "#333333"], ["flex", 1]]))], ["view-more", padStyleMapOf(utsMapOf([["fontSize", "24rpx"], ["color", "#0066CC"]]))], ["logistics-company", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["marginBottom", "16rpx"]]))], ["company-name", padStyleMapOf(utsMapOf([["fontSize", "28rpx"], ["color", "#333333"], ["fontWeight", "bold"], ["marginRight", "16rpx"]]))], ["logistics-no", padStyleMapOf(utsMapOf([["fontSize", "24rpx"], ["color", "#999999"]]))], ["latest-track", padStyleMapOf(utsMapOf([["backgroundColor", "#f8f8f8"], ["paddingTop", "20rpx"], ["paddingRight", "20rpx"], ["paddingBottom", "20rpx"], ["paddingLeft", "20rpx"], ["borderTopLeftRadius", "12rpx"], ["borderTopRightRadius", "12rpx"], ["borderBottomRightRadius", "12rpx"], ["borderBottomLeftRadius", "12rpx"]]))], ["track-status", padStyleMapOf(utsMapOf([["fontSize", "28rpx"], ["color", "#0066CC"], ["fontWeight", "bold"], ["marginBottom", "8rpx"]]))], ["track-desc", padStyleMapOf(utsMapOf([["fontSize", "26rpx"], ["color", "#666666"], ["marginBottom", "8rpx"]]))], ["track-time", padStyleMapOf(utsMapOf([["fontSize", "24rpx"], ["color", "#999999"]]))], ["address-row", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["justifyContent", "space-between"], ["marginBottom", "12rpx"]]))], ["receiver-name", padStyleMapOf(utsMapOf([["fontSize", "28rpx"], ["color", "#333333"], ["fontWeight", "700"]]))], ["receiver-phone", padStyleMapOf(utsMapOf([["fontSize", "28rpx"], ["color", "#666666"]]))], ["receiver-address", padStyleMapOf(utsMapOf([["fontSize", "26rpx"], ["color", "#666666"], ["lineHeight", 1.4]]))], ["goods-item", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["paddingTop", "20rpx"], ["paddingRight", 0], ["paddingBottom", "20rpx"], ["paddingLeft", 0], ["borderBottomWidth", "1rpx"], ["borderBottomStyle", "solid"], ["borderBottomColor", "#f0f0f0"]]))], ["goods-image", padStyleMapOf(utsMapOf([["width", "120rpx"], ["height", "120rpx"], ["borderTopLeftRadius", "12rpx"], ["borderTopRightRadius", "12rpx"], ["borderBottomRightRadius", "12rpx"], ["borderBottomLeftRadius", "12rpx"], ["marginRight", "20rpx"]]))], ["goods-info", padStyleMapOf(utsMapOf([["flex", 1]]))], ["goods-name", padStyleMapOf(utsMapOf([["fontSize", "28rpx"], ["color", "#333333"], ["marginBottom", "8rpx"]]))], ["goods-spec", padStyleMapOf(utsMapOf([["fontSize", "24rpx"], ["color", "#999999"], ["marginBottom", "12rpx"]]))], ["goods-price-row", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["justifyContent", "space-between"], ["alignItems", "center"]]))], ["goods-price", padStyleMapOf(utsMapOf([["fontSize", "28rpx"], ["color", "#FF4D4F"], ["fontWeight", "400"]]))], ["goods-quantity", padStyleMapOf(utsMapOf([["fontSize", "24rpx"], ["color", "#666666"]]))], ["price-row", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["justifyContent", "space-between"], ["alignItems", "center"], ["marginBottom", "20rpx"]]))], ["price-row-total", padStyleMapOf(utsMapOf([["paddingTop", "20rpx"], ["borderTopWidth", "1rpx"], ["borderTopStyle", "solid"], ["borderTopColor", "#f0f0f0"]]))], ["price-label-total", padStyleMapOf(utsMapOf([["fontSize", "32rpx"], ["fontWeight", "700"], ["color", "#333333"]]))], ["price-value-total", padStyleMapOf(utsMapOf([["fontSize", "32rpx"], ["fontWeight", "700"], ["color", "#333333"]]))], ["price-label", padStyleMapOf(utsMapOf([["fontSize", "28rpx"], ["color", "#666666"]]))], ["price-value", padStyleMapOf(utsMapOf([["fontSize", "28rpx"], ["color", "#333333"]]))], ["info-row", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["justifyContent", "space-between"], ["alignItems", "center"], ["marginBottom", "20rpx"]]))], ["info-label", padStyleMapOf(utsMapOf([["fontSize", "28rpx"], ["color", "#666666"]]))], ["info-value", padStyleMapOf(utsMapOf([["fontSize", "28rpx"], ["color", "#333333"], ["textAlign", "right"], ["maxWidth", "400rpx"], ["overflow", "hidden"], ["textOverflow", "ellipsis"]]))], ["bottom-actions", padStyleMapOf(utsMapOf([["position", "fixed"], ["bottom", 0], ["left", 0], ["right", 0], ["backgroundColor", "#ffffff"], ["paddingTop", "30rpx"], ["paddingRight", "30rpx"], ["paddingBottom", "30rpx"], ["paddingLeft", "30rpx"], ["boxShadow", "0 -2rpx 8rpx rgba(0, 0, 0, 0.1)"], ["display", "flex"], ["flexDirection", "row"], ["justifyContent", "flex-end"]]))], ["action-btn", padStyleMapOf(utsMapOf([["paddingTop", "20rpx"], ["paddingRight", "40rpx"], ["paddingBottom", "20rpx"], ["paddingLeft", "40rpx"], ["borderTopLeftRadius", "8rpx"], ["borderTopRightRadius", "8rpx"], ["borderBottomRightRadius", "8rpx"], ["borderBottomLeftRadius", "8rpx"], ["borderTopWidth", "1rpx"], ["borderRightWidth", "1rpx"], ["borderBottomWidth", "1rpx"], ["borderLeftWidth", "1rpx"], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "rgba(0,0,0,0)"], ["borderRightColor", "rgba(0,0,0,0)"], ["borderBottomColor", "rgba(0,0,0,0)"], ["borderLeftColor", "rgba(0,0,0,0)"], ["minWidth", "160rpx"], ["marginLeft", "20rpx"], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["action-btn-primary", padStyleMapOf(utsMapOf([["backgroundColor", "#0066CC"]]))], ["action-btn-secondary", padStyleMapOf(utsMapOf([["backgroundColor", "rgba(0,0,0,0)"], ["borderTopColor", "#e5e5e5"], ["borderRightColor", "#e5e5e5"], ["borderBottomColor", "#e5e5e5"], ["borderLeftColor", "#e5e5e5"]]))], ["action-btn-text", padStyleMapOf(utsMapOf([["fontSize", "28rpx"]]))], ["action-btn-text-primary", padStyleMapOf(utsMapOf([["color", "#ffffff"]]))], ["action-btn-text-secondary", padStyleMapOf(utsMapOf([["color", "#666666"]]))], ["bottom-placeholder", padStyleMapOf(utsMapOf([["height", "40rpx"]]))], ["logistics-modal", padStyleMapOf(utsMapOf([["position", "fixed"], ["top", 0], ["left", 0], ["right", 0], ["bottom", 0], ["backgroundColor", "rgba(0,0,0,0.5)"], ["display", "flex"], ["alignItems", "flex-end"], ["zIndex", 999]]))], ["modal-content", padStyleMapOf(utsMapOf([["width", "100%"], ["backgroundColor", "#ffffff"], ["borderTopLeftRadius", "24rpx"], ["borderTopRightRadius", "24rpx"], ["borderBottomRightRadius", 0], ["borderBottomLeftRadius", 0], ["maxHeight", "1000rpx"], ["display", "flex"], ["flexDirection", "column"]]))], ["modal-header", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "space-between"], ["paddingTop", "30rpx"], ["paddingRight", "30rpx"], ["paddingBottom", "30rpx"], ["paddingLeft", "30rpx"], ["borderBottomWidth", "1rpx"], ["borderBottomStyle", "solid"], ["borderBottomColor", "#f0f0f0"]]))], ["modal-title", padStyleMapOf(utsMapOf([["fontSize", "32rpx"], ["fontWeight", "700"], ["color", "#333333"]]))], ["modal-close", padStyleMapOf(utsMapOf([["fontSize", "48rpx"], ["color", "#999999"], ["paddingTop", 0], ["paddingRight", "10rpx"], ["paddingBottom", 0], ["paddingLeft", "10rpx"]]))], ["modal-company", padStyleMapOf(utsMapOf([["paddingTop", "20rpx"], ["paddingRight", "30rpx"], ["paddingBottom", "20rpx"], ["paddingLeft", "30rpx"], ["backgroundColor", "#f8f8f8"]]))], ["company-label", padStyleMapOf(utsMapOf([["fontSize", "28rpx"], ["color", "#333333"], ["fontWeight", "bold"]]))], ["company-no", padStyleMapOf(utsMapOf([["fontSize", "24rpx"], ["color", "#999999"], ["marginLeft", "16rpx"]]))], ["tracks-scroll", padStyleMapOf(utsMapOf([["flex", 1], ["paddingTop", "30rpx"], ["paddingRight", "30rpx"], ["paddingBottom", "30rpx"], ["paddingLeft", "30rpx"]]))], ["track-item", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["position", "relative"], ["paddingLeft", "40rpx"], ["paddingBottom", "40rpx"]]))], ["track-dot", padStyleMapOf(utsMapOf([["position", "absolute"], ["left", 0], ["top", "8rpx"], ["width", "16rpx"], ["height", "16rpx"], ["borderTopLeftRadius", "8rpx"], ["borderTopRightRadius", "8rpx"], ["borderBottomRightRadius", "8rpx"], ["borderBottomLeftRadius", "8rpx"], ["backgroundColor", "#cccccc"]]))], ["track-dot-active", padStyleMapOf(utsMapOf([["backgroundColor", "#0066CC"], ["width", "20rpx"], ["height", "20rpx"], ["left", "-2rpx"]]))], ["track-line", padStyleMapOf(utsMapOf([["position", "absolute"], ["left", "7rpx"], ["top", "28rpx"], ["width", "2rpx"], ["height", "80rpx"], ["backgroundColor", "#e0e0e0"]]))], ["track-content", padStyleMapOf(utsMapOf([["flex", 1]]))], ["track-status-active", padStyleMapOf(utsMapOf([["color", "#0066CC"], ["fontWeight", "bold"]]))]])]
