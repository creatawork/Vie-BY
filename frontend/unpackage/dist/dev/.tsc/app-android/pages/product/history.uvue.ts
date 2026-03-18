
import { getBrowseHistory, deleteBrowseHistory, clearBrowseHistory } from '@/api/browseHistory'

type HistoryItem = { __$originalPosition?: UTSSourceMapPosition<"HistoryItem", "pages/product/history.uvue", 65, 6>;
  id : number  // 修改：后端返回的是 id，不是 historyId
  productId : number
  productName : string
  mainImage : string
  currentPrice : number
  originalPrice : number
  stock : number
  salesVolume : number
  status : number
  browseTime : string
  inStock : boolean
}

const __sfc__ = defineComponent({
  data() {
    return {
      historyList: [] as HistoryItem[],
      loading: false,
      pageNum: 1,
      pageSize: 10,
      total: 0,
      hasMore: true
    }
  },
  onLoad() {
    this.loadHistoryList()
  },
  methods: {
    // 判断是否显示原价
    shouldShowOriginalPrice(item : HistoryItem) : boolean {
      return item.originalPrice > 0 && item.originalPrice > item.currentPrice
    },
    // 加载浏览历史列表
    loadHistoryList() {
      if (this.loading || !this.hasMore) return

      this.loading = true

      getBrowseHistory(this.pageNum, this.pageSize).then((res) => {
        if (res.code == 200 && res.data != null) {
          const resData = res.data as UTSJSONObject
          const recordsArr = resData.getArray('records')
          const total = (resData.getNumber('total') ?? 0).toInt()
          const current = (resData.getNumber('current') ?? 1).toInt()
          const pages = (resData.getNumber('pages') ?? 1).toInt()

          // 解析记录数组
          const records : HistoryItem[] = []
          if (recordsArr != null) {
            for (let i = 0; i < recordsArr.length; i++) {
              const item = recordsArr[i] as UTSJSONObject
              
              // 后端返回的字段名是 historyId，不是 id
              const historyId = (item.getNumber('historyId') ?? 0).toInt()
              
              records.push({
                id: historyId,
                productId: (item.getNumber('productId') ?? 0).toInt(),
                productName: item.getString('productName') ?? '',
                mainImage: item.getString('productImage') ?? '',
                currentPrice: item.getNumber('currentPrice') ?? 0,
                originalPrice: item.getNumber('originalPrice') ?? 0,
                stock: (item.getNumber('stock') ?? 0).toInt(),
                salesVolume: (item.getNumber('salesVolume') ?? 0).toInt(),
                status: (item.getNumber('productStatus') ?? 1).toInt(),
                browseTime: item.getString('browseTime') ?? '',
                inStock: (item.getBoolean('inStock') ?? true)
              } as HistoryItem)
            }
          }

          // 追加数据
          this.historyList = this.historyList.concat(records)
          this.total = total
          this.pageNum = current

          // 判断是否还有更多
          this.hasMore = this.pageNum < pages
        } else {
          const msg = res.message != '' ? res.message : '加载失败'
          uni.showToast({
            title: msg,
            icon: 'none'
          })
        }
        this.loading = false
      }).catch((error) => {
        console.error('加载浏览历史失败:', error, " at pages/product/history.uvue:153")
        uni.showToast({
          title: '加载失败，请重试',
          icon: 'none'
        })
        this.loading = false
      })
    },

    // 加载更多
    loadMore() {
      if (!this.hasMore || this.loading) return

      this.pageNum += 1
      this.loadHistoryList()
    },

    // 跳转到商品详情
    goToDetail(productId : number) {
      uni.navigateTo({ url: `/pages/product/detail?id=${productId}` })
    },

    // 删除单条记录
    deleteItem(historyId : number) {
      console.log('准备删除浏览记录，ID:', historyId, " at pages/product/history.uvue:177")
      
      deleteBrowseHistory(historyId).then((res) => {
        console.log('删除API响应:', JSON.stringify(res), " at pages/product/history.uvue:180")
        
        if (res.code == 200) {
          // 从列表中移除
          this.historyList = this.historyList.filter((item : HistoryItem) : boolean => item.id !== historyId)
          this.total -= 1

          uni.showToast({ title: '已删除', icon: 'success' })
        } else {
          const msg = res.message != '' ? res.message : '删除失败'
          console.error('删除失败，响应:', msg, " at pages/product/history.uvue:190")
          uni.showToast({
            title: msg,
            icon: 'none'
          })
        }
      }).catch((error) => {
        console.error('删除浏览记录异常:', error, " at pages/product/history.uvue:197")
        uni.showToast({
          title: '删除失败，请重试',
          icon: 'none'
        })
      })
    },

    // 清空浏览历史
    clearHistory() {
      uni.showModal({
        title: '提示',
        content: '确定清空所有浏览记录吗？',
        success: (modalRes) => {
          if (modalRes.confirm) {
            clearBrowseHistory().then((res) => {
              if (res.code == 200) {
                this.historyList = []
                this.total = 0
                this.pageNum = 1
                this.hasMore = false

                uni.showToast({ title: '已清空', icon: 'none' })
              } else {
                const msg = res.message != '' ? res.message : '清空失败'
                uni.showToast({
                  title: msg,
                  icon: 'none'
                })
              }
            }).catch((error) => {
              console.error('清空浏览历史失败:', error, " at pages/product/history.uvue:228")
              uni.showToast({
                title: '清空失败，请重试',
                icon: 'none'
              })
            })
          }
        }
      })
    },

    // 去逛逛
    goShopping() {
      uni.switchTab({ url: '/pages/product/list' })
    },

    // 格式化时间
    formatTime(timeStr : string) : string {
      if (timeStr == '') return ''

      try {
        const time = new Date(timeStr.replace(/-/g, '/'))
        const now = new Date()
        const diff = now.getTime() - time.getTime()

        const minutes = Math.floor(diff / 60000)
        const hours = Math.floor(diff / 3600000)
        const days = Math.floor(diff / 86400000)

        if (minutes < 1) {
          return '刚刚'
        } else if (minutes < 60) {
          return `${minutes}分钟前`
        } else if (hours < 24) {
          return `${hours}小时前`
        } else if (days === 1) {
          return '昨天'
        } else if (days === 2) {
          return '前天'
        } else if (days < 7) {
          return `${days}天前`
        } else {
          // 返回日期
          const month = time.getMonth() + 1
          const date = time.getDate()
          return `${month}月${date}日`
        }
      } catch (error) {
        return timeStr
      }
    },

    // 获取库存文本
    getStockText(item : HistoryItem) : string {
      if (item.status !== 1) {
        return '商品已下架'
      }
      if (!item.inStock) {
        return '暂时缺货'
      }
      return `销量 ${item.salesVolume}`
    }
  }
})

export default __sfc__
function GenPagesProductHistoryRender(this: InstanceType<typeof __sfc__>): any | null {
const _ctx = this
const _cache = this.$.renderCache
  return createElementVNode("view", utsMapOf({ class: "history-page" }), [
    _ctx.historyList.length > 0
      ? createElementVNode("view", utsMapOf({
          key: 0,
          class: "action-bar"
        }), [
          createElementVNode("text", utsMapOf({ class: "count-text" }), "共 " + toDisplayString(_ctx.total) + " 条记录", 1 /* TEXT */),
          createElementVNode("text", utsMapOf({
            class: "clear-btn",
            onClick: _ctx.clearHistory
          }), "清空", 8 /* PROPS */, ["onClick"])
        ])
      : createCommentVNode("v-if", true),
    createElementVNode("scroll-view", utsMapOf({
      class: "history-scroll",
      "scroll-y": "true",
      "show-scrollbar": false,
      onScrolltolower: _ctx.loadMore
    }), [
      createElementVNode("view", utsMapOf({ class: "history-list" }), [
        createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.historyList, (item, __key, __index, _cached): any => {
          return createElementVNode("view", utsMapOf({
            class: "history-item",
            key: item.id,
            onClick: () => {_ctx.goToDetail(item.productId)}
          }), [
            createElementVNode("image", utsMapOf({
              class: "goods-img",
              src: item.mainImage,
              mode: "aspectFill"
            }), null, 8 /* PROPS */, ["src"]),
            createElementVNode("view", utsMapOf({ class: "goods-info" }), [
              createElementVNode("view", utsMapOf({ class: "info-top" }), [
                createElementVNode("text", utsMapOf({ class: "goods-name" }), toDisplayString(item.productName), 1 /* TEXT */),
                createElementVNode("text", utsMapOf({ class: "view-time" }), toDisplayString(_ctx.formatTime(item.browseTime)), 1 /* TEXT */)
              ]),
              createElementVNode("text", utsMapOf({ class: "goods-desc" }), toDisplayString(_ctx.getStockText(item)), 1 /* TEXT */),
              createElementVNode("view", utsMapOf({ class: "goods-bottom" }), [
                createElementVNode("view", utsMapOf({ class: "price-box" }), [
                  createElementVNode("text", utsMapOf({ class: "currency" }), "¥"),
                  createElementVNode("text", utsMapOf({ class: "price" }), toDisplayString(item.currentPrice), 1 /* TEXT */),
                  isTrue(_ctx.shouldShowOriginalPrice(item))
                    ? createElementVNode("text", utsMapOf({
                        key: 0,
                        class: "original-price"
                      }), "¥" + toDisplayString(item.originalPrice), 1 /* TEXT */)
                    : createCommentVNode("v-if", true)
                ]),
                createElementVNode("view", utsMapOf({
                  class: "action-icon",
                  onClick: withModifiers(() => {_ctx.deleteItem(item.id)}, ["stop"])
                }), [
                  createElementVNode("text", utsMapOf({ class: "delete-text" }), "✕")
                ], 8 /* PROPS */, ["onClick"])
              ])
            ])
          ], 8 /* PROPS */, ["onClick"])
        }), 128 /* KEYED_FRAGMENT */)
      ]),
      isTrue(_ctx.loading)
        ? createElementVNode("view", utsMapOf({
            key: 0,
            class: "loading-more"
          }), [
            createElementVNode("text", utsMapOf({ class: "loading-text" }), "加载中...")
          ])
        : createCommentVNode("v-if", true),
      isTrue(!_ctx.loading && _ctx.hasMore === false && _ctx.historyList.length > 0)
        ? createElementVNode("view", utsMapOf({
            key: 1,
            class: "no-more"
          }), [
            createElementVNode("text", utsMapOf({ class: "no-more-text" }), "没有更多了")
          ])
        : createCommentVNode("v-if", true),
      isTrue(!_ctx.loading && _ctx.historyList.length === 0)
        ? createElementVNode("view", utsMapOf({
            key: 2,
            class: "empty-state"
          }), [
            createElementVNode("text", utsMapOf({ class: "empty-icon" }), "🕒"),
            createElementVNode("text", utsMapOf({ class: "empty-text" }), "暂无浏览记录"),
            createElementVNode("button", utsMapOf({
              class: "go-shopping-btn",
              onClick: _ctx.goShopping
            }), "去逛逛", 8 /* PROPS */, ["onClick"])
          ])
        : createCommentVNode("v-if", true),
      createElementVNode("view", utsMapOf({
        style: normalizeStyle(utsMapOf({"height":"20px"}))
      }), null, 4 /* STYLE */)
    ], 40 /* PROPS, NEED_HYDRATION */, ["onScrolltolower"])
  ])
}
const GenPagesProductHistoryStyles = [utsMapOf([["history-page", padStyleMapOf(utsMapOf([["backgroundColor", "#F5F7FA"], ["display", "flex"], ["flexDirection", "column"]]))], ["action-bar", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["justifyContent", "space-between"], ["alignItems", "center"], ["paddingTop", 12], ["paddingRight", 16], ["paddingBottom", 12], ["paddingLeft", 16], ["backgroundColor", "#ffffff"], ["borderBottomWidth", 1], ["borderBottomStyle", "solid"], ["borderBottomColor", "#F5F5F5"]]))], ["count-text", padStyleMapOf(utsMapOf([["fontSize", 14], ["color", "#999999"]]))], ["clear-btn", padStyleMapOf(utsMapOf([["fontSize", 14], ["color", "#333333"], ["paddingTop", 4], ["paddingRight", 8], ["paddingBottom", 4], ["paddingLeft", 8]]))], ["history-scroll", padStyleMapOf(utsMapOf([["flex", 1], ["width", "100%"]]))], ["history-list", padStyleMapOf(utsMapOf([["paddingTop", 12], ["paddingRight", 16], ["paddingBottom", 12], ["paddingLeft", 16], ["display", "flex"], ["flexDirection", "column"]]))], ["history-item", padStyleMapOf(utsMapOf([["backgroundColor", "#ffffff"], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12], ["paddingTop", 12], ["paddingRight", 12], ["paddingBottom", 12], ["paddingLeft", 12], ["marginBottom", 12], ["display", "flex"], ["flexDirection", "row"], ["boxShadow", "0 1px 4px rgba(0, 0, 0, 0.02)"]]))], ["goods-img", padStyleMapOf(utsMapOf([["width", "180rpx"], ["height", "180rpx"], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#F5F7FA"], ["marginRight", "20rpx"]]))], ["goods-info", padStyleMapOf(utsMapOf([["flex", 1], ["display", "flex"], ["flexDirection", "column"], ["justifyContent", "space-between"], ["paddingTop", 2], ["paddingRight", 0], ["paddingBottom", 2], ["paddingLeft", 0]]))], ["info-top", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["justifyContent", "space-between"], ["alignItems", "flex-start"]]))], ["goods-name", padStyleMapOf(utsMapOf([["flex", 1], ["fontSize", 15], ["fontWeight", "700"], ["color", "#333333"], ["marginBottom", 4], ["lineHeight", 1.4], ["height", 42], ["overflow", "hidden"], ["marginRight", 8]]))], ["view-time", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "#999999"], ["whiteSpace", "nowrap"]]))], ["goods-desc", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "#999999"], ["marginBottom", "auto"], ["lineHeight", 1.4], ["height", 17], ["overflow", "hidden"]]))], ["goods-bottom", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["justifyContent", "space-between"], ["alignItems", "center"]]))], ["price-box", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["color", "#FF4D4F"]]))], ["currency", padStyleMapOf(utsMapOf([["fontSize", 12], ["fontWeight", "bold"]]))], ["price", padStyleMapOf(utsMapOf([["fontSize", 16], ["fontWeight", "bold"], ["marginRight", 4]]))], ["original-price", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "#999999"], ["opacity", 0.8]]))], ["action-icon", padStyleMapOf(utsMapOf([["width", 24], ["height", 24], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"], ["backgroundColor", "#F5F7FA"], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12]]))], ["delete-text", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "#999999"]]))], ["loading-more", padStyleMapOf(utsMapOf([["paddingTop", 20], ["paddingRight", 0], ["paddingBottom", 20], ["paddingLeft", 0], ["display", "flex"], ["justifyContent", "center"], ["alignItems", "center"]]))], ["loading-text", padStyleMapOf(utsMapOf([["fontSize", 14], ["color", "#999999"]]))], ["no-more", padStyleMapOf(utsMapOf([["paddingTop", 20], ["paddingRight", 0], ["paddingBottom", 20], ["paddingLeft", 0], ["display", "flex"], ["justifyContent", "center"], ["alignItems", "center"]]))], ["no-more-text", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "#C0C0C0"]]))], ["empty-state", padStyleMapOf(utsMapOf([["paddingTop", 80], ["paddingRight", 0], ["paddingBottom", 80], ["paddingLeft", 0], ["display", "flex"], ["flexDirection", "column"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["empty-icon", padStyleMapOf(utsMapOf([["fontSize", 48], ["marginBottom", 16], ["color", "#C0C0C0"]]))], ["empty-text", padStyleMapOf(utsMapOf([["fontSize", 14], ["color", "#999999"], ["marginBottom", 24]]))], ["go-shopping-btn", padStyleMapOf(utsMapOf([["backgroundColor", "#0066CC"], ["color", "#ffffff"], ["fontSize", 14], ["paddingTop", 0], ["paddingRight", 32], ["paddingBottom", 0], ["paddingLeft", 32], ["height", 40], ["lineHeight", "40px"], ["borderTopLeftRadius", 20], ["borderTopRightRadius", 20], ["borderBottomRightRadius", 20], ["borderBottomLeftRadius", 20]]))]])]
