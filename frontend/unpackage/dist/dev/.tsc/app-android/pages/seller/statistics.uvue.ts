
import {
  getSalesStatistics,
  getProductRanking,
  getOrderStatusDistribution,
  getSkuRanking,
  getInventoryHealth,
  type StatisticsParams,
  type RankingParams
} from '@/api/seller'
import type { ResponseDataType } from '@/utils/request'

// 销售数据类型
type SalesDataType = { __$originalPosition?: UTSSourceMapPosition<"SalesDataType", "pages/seller/statistics.uvue", 172, 6>;
  totalSales: number       // 总销售额
  totalOrders: number      // 总订单数
  totalQuantity: number    // 总销量
  avgOrderAmount: number   // 平均客单价
}

// 趋势数据类型
type TrendItemType = { __$originalPosition?: UTSSourceMapPosition<"TrendItemType", "pages/seller/statistics.uvue", 180, 6>;
  date: string
  label: string
  amount: number
  orders: number
}

// 排行数据类型
type RankingItemType = { __$originalPosition?: UTSSourceMapPosition<"RankingItemType", "pages/seller/statistics.uvue", 188, 6>;
  productId: number
  productName: string
  mainImage: string
  sales: number
  salesAmount: number
}

// 订单状态分布类型
type OrderStatusType = { __$originalPosition?: UTSSourceMapPosition<"OrderStatusType", "pages/seller/statistics.uvue", 197, 6>;
  status: number
  statusDesc: string
  count: number
  percentage: number
}

// 库存健康类型
type InventoryHealthType = { __$originalPosition?: UTSSourceMapPosition<"InventoryHealthType", "pages/seller/statistics.uvue", 205, 6>;
  totalSkuCount: number
  lowStockCount: number
  outOfStockCount: number
  healthyRate: number
}

// SKU排行类型
type SkuRankingType = { __$originalPosition?: UTSSourceMapPosition<"SkuRankingType", "pages/seller/statistics.uvue", 213, 6>;
  skuId: number
  skuName: string
  productName: string
  salesVolume: number
  salesAmount: number
}

const __sfc__ = defineComponent({
  data() {
    return {
      isLoading: false,
      timeUnit: 'DAY' as string,  // 'DAY' | 'WEEK' | 'MONTH'

      // 销售汇总数据
      salesData: {
        totalSales: 0,
        totalOrders: 0,
        totalQuantity: 0,
        avgOrderAmount: 0
      } as SalesDataType,

      // 趋势数据
      trendData: [] as TrendItemType[],
      maxTrendAmount: 0,

      // 排行数据
      rankingList: [] as RankingItemType[],

      // 订单分布
      orderDistribution: [] as OrderStatusType[],

      // 库存健康
      inventoryHealth: {
        totalSkuCount: 0,
        lowStockCount: 0,
        outOfStockCount: 0,
        healthyRate: 0
      } as InventoryHealthType,

      // SKU排行
      skuRankingList: [] as SkuRankingType[]
    }
  },
  onLoad() {
    this.loadAllData()
  },
  methods: {
    // 加载所有数据
    loadAllData() {
      this.isLoading = true

      // 并行加载所有统计数据
      Promise.all([
        this.loadSalesStatistics(),
        this.loadProductRanking(),
        this.loadOrderStatusDistribution(),
        this.loadSkuRanking(),
        this.loadInventoryHealth()
      ]).then(() => {
        this.isLoading = false
      }).catch((err) => {
        console.error('加载数据失败:', err, " at pages/seller/statistics.uvue:275")
        this.isLoading = false
        uni.showToast({ title: '加载数据失败', icon: 'none' })
      })
    },

    // 加载销售统计数据
    loadSalesStatistics(): Promise<void> {
      return new Promise((resolve, reject) => {
        const params: StatisticsParams = {
          startDate: null,
          endDate: null,
          timeUnit: this.timeUnit
        }

        getSalesStatistics(params).then((res) => {
          const root = res.data as UTSJSONObject | null
          if (root == null) {
            resolve()
            return
          }

          const summary = root.get('summary') as UTSJSONObject | null
          if (summary != null) {
            this.salesData = {
              totalSales: summary.getNumber('totalSales') ?? 0,
              totalOrders: (summary.getNumber('totalOrders') ?? 0).toInt(),
              totalQuantity: (summary.getNumber('totalQuantity') ?? 0).toInt(),
              avgOrderAmount: summary.getNumber('avgOrderAmount') ?? 0
            } as SalesDataType
          }

          const trend = root.getArray('trend')
          this.parseTrendData(trend as any[] | null)

          resolve()
        }).catch((err) => {
          console.error('获取销售统计失败:', err, " at pages/seller/statistics.uvue:312")
          reject(err)
        })
      })
    },

    // 解析趋势数据
    parseTrendData(trend: any[] | null) {
      if (trend == null) {
        this.trendData = []
        this.maxTrendAmount = 0
        return
      }

      const items: TrendItemType[] = []
      let maxAmount = 0

      for (let i = 0; i < trend.length; i++) {
        const item = trend[i] as UTSJSONObject
        const amount = item.getNumber('sales') ?? 0

        if (amount > maxAmount) {
          maxAmount = amount
        }

        const dateStr = item.getString('date') ?? ''
        items.push({
          date: dateStr,
          label: this.formatDateLabel(dateStr),
          amount: amount,
          orders: (item.getNumber('orders') ?? 0).toInt()
        } as TrendItemType)
      }

      this.trendData = items
      this.maxTrendAmount = maxAmount
    },

    // 格式化日期标签
    formatDateLabel(dateStr: string): string {
      if (dateStr === '' || dateStr.length < 5) return dateStr

      // 根据时间粒度格式化
      if (this.timeUnit === 'DAY') {
        // 显示 MM-DD
        if (dateStr.length >= 10) {
          return dateStr.substring(5, 10)
        }
        return dateStr
      } else if (this.timeUnit === 'WEEK') {
        // 显示周数
        return dateStr
      } else {
        // 显示月份
        if (dateStr.length >= 7) {
          return dateStr.substring(5, 7) + '月'
        }
        return dateStr
      }
    },

    // 加载商品排行数据
    loadProductRanking(): Promise<void> {
      return new Promise((resolve, reject) => {
        const params: RankingParams = {
          limit: 10,
          orderBy: 'sales'
        }

        getProductRanking(params).then((res) => {
          this.parseRankingData(res.data)
          resolve()
        }).catch((err) => {
          console.error('获取商品排行失败:', err, " at pages/seller/statistics.uvue:385")
          reject(err)
        })
      })
    },

    // 解析排行数据
    parseRankingData(data: any | null) {
      const list = data as any[] | null
      if (list == null) {
        this.rankingList = []
        return
      }

      const items: RankingItemType[] = []
      for (let i = 0; i < list.length; i++) {
        const item = list[i] as UTSJSONObject
        items.push({
          productId: (item.getNumber('productId') ?? 0).toInt(),
          productName: item.getString('productName') ?? '',
          mainImage: item.getString('mainImage') ?? '',
          sales: (item.getNumber('salesVolume') ?? 0).toInt(),
          salesAmount: item.getNumber('salesAmount') ?? 0
        } as RankingItemType)
      }
      this.rankingList = items
    },

    // 加载订单状态分布
    loadOrderStatusDistribution(): Promise<void> {
      return new Promise((resolve, reject) => {
        getOrderStatusDistribution({ startDate: null, endDate: null, timeUnit: null } as StatisticsParams).then((res : ResponseDataType) => {
          const root = res.data as UTSJSONObject | null
          if (root != null) {
            const list = root.getArray('distribution')
            if (list != null) {
              const items: OrderStatusType[] = []
              for (let i = 0; i < list.length; i++) {
                const item = list[i] as UTSJSONObject
                items.push({
                  status: (item.getNumber('status') ?? 0).toInt(),
                  statusDesc: item.getString('statusDesc') ?? '',
                  count: (item.getNumber('count') ?? 0).toInt(),
                  percentage: item.getNumber('percentage') ?? 0
                } as OrderStatusType)
              }
              this.orderDistribution = items
            }
          }
          resolve()
        }).catch((err: any | null) => {
          console.error('获取订单分布失败:', err, " at pages/seller/statistics.uvue:436")
          reject(err)
        })
      })
    },
    goInventoryLowStock() {
      uni.navigateTo({
        url: '/pages/seller/inventory?lowStock=true'
      })
    },

    // 跳转到库存管理（缺货）
    goInventoryOutOfStock() {
      uni.navigateTo({
        url: '/pages/seller/inventory?outOfStock=true'
      })
    },

    // 加载 SKU 排行
    loadSkuRanking(): Promise<void> {
      return new Promise((resolve, reject) => {
        getSkuRanking({ limit: 5, orderBy: 'sales' } as RankingParams).then((res : ResponseDataType) => {
          const list = res.data as any[] | null
          if (list != null) {
            const items: SkuRankingType[] = []
            for (let i = 0; i < list.length; i++) {
              const item = list[i] as UTSJSONObject
              items.push({
                skuId: (item.getNumber('skuId') ?? 0).toInt(),
                skuName: item.getString('skuName') ?? '',
                productName: item.getString('productName') ?? '',
                salesVolume: (item.getNumber('salesVolume') ?? 0).toInt(),
                salesAmount: item.getNumber('salesAmount') ?? 0
              } as SkuRankingType)
            }
            this.skuRankingList = items
          }
          resolve()
        }).catch((err) => {
          console.error('获取SKU排行失败:', err, " at pages/seller/statistics.uvue:475")
          reject(err)
        })
      })
    },

    // 加载库存健康
    loadInventoryHealth(): Promise<void> {
      return new Promise((resolve, reject) => {
        getInventoryHealth().then((res) => {
          const data = res.data as UTSJSONObject | null
          if (data != null) {
            this.inventoryHealth = {
              totalSkuCount: (data.getNumber('totalSkuCount') ?? 0).toInt(),
              lowStockCount: (data.getNumber('lowStockCount') ?? 0).toInt(),
              outOfStockCount: (data.getNumber('outOfStockCount') ?? 0).toInt(),
              healthyRate: data.getNumber('healthyRate') ?? 0
            } as InventoryHealthType
          }
          resolve()
        }).catch((err) => {
          console.error('获取库存健康失败:', err, " at pages/seller/statistics.uvue:496")
          reject(err)
        })
      })
    },

    // 切换时间粒度
    switchTimeUnit(unit: string) {
      if (this.timeUnit === unit) return
      this.timeUnit = unit
      this.loadSalesStatistics()
    },

    // 格式化金额
    formatMoney(amount: number): string {
      if (amount >= 10000) {
        return (amount / 10000).toFixed(2) + '万'
      }
      return amount.toFixed(2)
    },

    // 计算柱状图高度
    getBarHeight(amount: number): number {
      if (this.maxTrendAmount <= 0) return 10
      const maxHeight = 100
      const minHeight = 10
      const ratio = amount / this.maxTrendAmount
      return Math.max(minHeight, ratio * maxHeight)
    },

    // 获取排名样式类
    getRankClass(index: number): string {
      if (index === 0) return 'rank-badge-first'
      if (index === 1) return 'rank-badge-second'
      if (index === 2) return 'rank-badge-third'
      return ''
    }
  }
})

export default __sfc__
function GenPagesSellerStatisticsRender(this: InstanceType<typeof __sfc__>): any | null {
const _ctx = this
const _cache = this.$.renderCache
  return createElementVNode("view", utsMapOf({ class: "page" }), [
    createElementVNode("view", utsMapOf({ class: "summary-section" }), [
      createElementVNode("view", utsMapOf({ class: "summary-header" }), [
        createElementVNode("text", utsMapOf({ class: "summary-title" }), "数据概览"),
        createElementVNode("view", utsMapOf({ class: "time-selector" }), [
          createElementVNode("view", utsMapOf({
            class: normalizeClass(["time-option", utsMapOf({ 'time-option-active': _ctx.timeUnit === 'DAY' })]),
            onClick: () => {_ctx.switchTimeUnit('DAY')}
          }), [
            createElementVNode("text", utsMapOf({
              class: normalizeClass(["time-text", utsMapOf({ 'time-text-active': _ctx.timeUnit === 'DAY' })])
            }), "日", 2 /* CLASS */)
          ], 10 /* CLASS, PROPS */, ["onClick"]),
          createElementVNode("view", utsMapOf({
            class: normalizeClass(["time-option", utsMapOf({ 'time-option-active': _ctx.timeUnit === 'WEEK' })]),
            onClick: () => {_ctx.switchTimeUnit('WEEK')}
          }), [
            createElementVNode("text", utsMapOf({
              class: normalizeClass(["time-text", utsMapOf({ 'time-text-active': _ctx.timeUnit === 'WEEK' })])
            }), "周", 2 /* CLASS */)
          ], 10 /* CLASS, PROPS */, ["onClick"]),
          createElementVNode("view", utsMapOf({
            class: normalizeClass(["time-option", utsMapOf({ 'time-option-active': _ctx.timeUnit === 'MONTH' })]),
            onClick: () => {_ctx.switchTimeUnit('MONTH')}
          }), [
            createElementVNode("text", utsMapOf({
              class: normalizeClass(["time-text", utsMapOf({ 'time-text-active': _ctx.timeUnit === 'MONTH' })])
            }), "月", 2 /* CLASS */)
          ], 10 /* CLASS, PROPS */, ["onClick"])
        ])
      ]),
      createElementVNode("view", utsMapOf({ class: "summary-cards" }), [
        createElementVNode("view", utsMapOf({ class: "summary-card" }), [
          createElementVNode("text", utsMapOf({ class: "card-value" }), "¥" + toDisplayString(_ctx.formatMoney(_ctx.salesData.totalSales)), 1 /* TEXT */),
          createElementVNode("text", utsMapOf({ class: "card-label" }), "总销售额")
        ]),
        createElementVNode("view", utsMapOf({ class: "summary-card" }), [
          createElementVNode("text", utsMapOf({ class: "card-value" }), toDisplayString(_ctx.salesData.totalOrders), 1 /* TEXT */),
          createElementVNode("text", utsMapOf({ class: "card-label" }), "订单数")
        ]),
        createElementVNode("view", utsMapOf({ class: "summary-card" }), [
          createElementVNode("text", utsMapOf({ class: "card-value" }), toDisplayString(_ctx.salesData.totalQuantity), 1 /* TEXT */),
          createElementVNode("text", utsMapOf({ class: "card-label" }), "销量")
        ]),
        createElementVNode("view", utsMapOf({ class: "summary-card" }), [
          createElementVNode("text", utsMapOf({ class: "card-value" }), "¥" + toDisplayString(_ctx.formatMoney(_ctx.salesData.avgOrderAmount)), 1 /* TEXT */),
          createElementVNode("text", utsMapOf({ class: "card-label" }), "客单价")
        ])
      ])
    ]),
    createElementVNode("view", utsMapOf({ class: "chart-section" }), [
      createElementVNode("view", utsMapOf({ class: "section-header" }), [
        createElementVNode("text", utsMapOf({ class: "section-title" }), "销售趋势")
      ]),
      createElementVNode("view", utsMapOf({ class: "chart-container" }), [
        _ctx.trendData.length > 0
          ? createElementVNode("view", utsMapOf({
              key: 0,
              class: "chart-wrapper"
            }), [
              createElementVNode("view", utsMapOf({ class: "chart-bars" }), [
                createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.trendData, (item, index, __index, _cached): any => {
                  return createElementVNode("view", utsMapOf({
                    class: "chart-bar-item",
                    key: index
                  }), [
                    createElementVNode("view", utsMapOf({ class: "bar-wrapper" }), [
                      createElementVNode("view", utsMapOf({
                        class: "bar",
                        style: normalizeStyle(utsMapOf({ height: _ctx.getBarHeight(item.amount) + 'px' }))
                      }), null, 4 /* STYLE */)
                    ]),
                    createElementVNode("text", utsMapOf({ class: "bar-label" }), toDisplayString(item.label), 1 /* TEXT */)
                  ])
                }), 128 /* KEYED_FRAGMENT */)
              ]),
              createElementVNode("view", utsMapOf({ class: "chart-legend" }), [
                createElementVNode("text", utsMapOf({ class: "legend-text" }), "单位: 元")
              ])
            ])
          : createElementVNode("view", utsMapOf({
              key: 1,
              class: "chart-empty"
            }), [
              createElementVNode("text", utsMapOf({ class: "empty-text" }), "暂无趋势数据")
            ])
      ])
    ]),
    createElementVNode("view", utsMapOf({ class: "ranking-section" }), [
      createElementVNode("view", utsMapOf({ class: "section-header" }), [
        createElementVNode("text", utsMapOf({ class: "section-title" }), "商品销量排行 TOP10")
      ]),
      _ctx.rankingList.length > 0
        ? createElementVNode("view", utsMapOf({
            key: 0,
            class: "ranking-list"
          }), [
            createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.rankingList, (item, index, __index, _cached): any => {
              return createElementVNode("view", utsMapOf({
                class: "ranking-item",
                key: item.productId
              }), [
                createElementVNode("view", utsMapOf({
                  class: normalizeClass(["rank-badge", _ctx.getRankClass(index)])
                }), [
                  createElementVNode("text", utsMapOf({
                    class: normalizeClass(["rank-num", utsMapOf({ 'rank-num-top': index < 3 })])
                  }), toDisplayString(index + 1), 3 /* TEXT, CLASS */)
                ], 2 /* CLASS */),
                createElementVNode("image", utsMapOf({
                  class: "product-img",
                  src: item.mainImage,
                  mode: "aspectFill"
                }), null, 8 /* PROPS */, ["src"]),
                createElementVNode("view", utsMapOf({ class: "product-info" }), [
                  createElementVNode("text", utsMapOf({ class: "product-name" }), toDisplayString(item.productName), 1 /* TEXT */),
                  createElementVNode("view", utsMapOf({ class: "product-stats" }), [
                    createElementVNode("text", utsMapOf({ class: "stat-item" }), "销量: " + toDisplayString(item.sales), 1 /* TEXT */),
                    createElementVNode("text", utsMapOf({ class: "stat-item" }), "销售额: ¥" + toDisplayString(_ctx.formatMoney(item.salesAmount)), 1 /* TEXT */)
                  ])
                ])
              ])
            }), 128 /* KEYED_FRAGMENT */)
          ])
        : createElementVNode("view", utsMapOf({
            key: 1,
            class: "ranking-empty"
          }), [
            createElementVNode("text", utsMapOf({ class: "empty-icon" }), "📊"),
            createElementVNode("text", utsMapOf({ class: "empty-text" }), "暂无排行数据")
          ])
    ]),
    createElementVNode("view", utsMapOf({ class: "insight-grid" }), [
      createElementVNode("view", utsMapOf({
        class: "insight-card",
        onClick: _ctx.goInventoryLowStock
      }), [
        createElementVNode("text", utsMapOf({ class: "insight-value" }), toDisplayString(_ctx.inventoryHealth.lowStockCount), 1 /* TEXT */),
        createElementVNode("text", utsMapOf({ class: "insight-label" }), "低库存 SKU")
      ], 8 /* PROPS */, ["onClick"]),
      createElementVNode("view", utsMapOf({
        class: "insight-card",
        onClick: _ctx.goInventoryOutOfStock
      }), [
        createElementVNode("text", utsMapOf({ class: "insight-value insight-value-warn" }), toDisplayString(_ctx.inventoryHealth.outOfStockCount), 1 /* TEXT */),
        createElementVNode("text", utsMapOf({ class: "insight-label" }), "缺货 SKU")
      ], 8 /* PROPS */, ["onClick"]),
      createElementVNode("view", utsMapOf({ class: "insight-card" }), [
        createElementVNode("text", utsMapOf({ class: "insight-value" }), toDisplayString(_ctx.inventoryHealth.healthyRate.toFixed(1)) + "%", 1 /* TEXT */),
        createElementVNode("text", utsMapOf({ class: "insight-label" }), "库存健康率")
      ])
    ]),
    createElementVNode("view", utsMapOf({ class: "dist-section" }), [
      createElementVNode("view", utsMapOf({ class: "section-header" }), [
        createElementVNode("text", utsMapOf({ class: "section-title" }), "订单状态分布")
      ]),
      _ctx.orderDistribution.length > 0
        ? createElementVNode("view", utsMapOf({
            key: 0,
            class: "dist-list"
          }), [
            createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.orderDistribution, (item, index, __index, _cached): any => {
              return createElementVNode("view", utsMapOf({
                class: "dist-item",
                key: index
              }), [
                createElementVNode("view", utsMapOf({ class: "dist-row" }), [
                  createElementVNode("text", utsMapOf({ class: "dist-name" }), toDisplayString(item.statusDesc), 1 /* TEXT */),
                  createElementVNode("text", utsMapOf({ class: "dist-meta" }), toDisplayString(item.count) + " · " + toDisplayString(item.percentage.toFixed(1)) + "%", 1 /* TEXT */)
                ]),
                createElementVNode("view", utsMapOf({ class: "dist-bar" }), [
                  createElementVNode("view", utsMapOf({
                    class: "dist-bar-fill",
                    style: normalizeStyle(utsMapOf({ width: item.percentage + '%' }))
                  }), null, 4 /* STYLE */)
                ])
              ])
            }), 128 /* KEYED_FRAGMENT */)
          ])
        : createElementVNode("view", utsMapOf({
            key: 1,
            class: "chart-empty"
          }), [
            createElementVNode("text", utsMapOf({ class: "empty-text" }), "暂无订单分布数据")
          ])
    ]),
    createElementVNode("view", utsMapOf({ class: "ranking-section" }), [
      createElementVNode("view", utsMapOf({ class: "section-header" }), [
        createElementVNode("text", utsMapOf({ class: "section-title" }), "SKU 销量排行 TOP5")
      ]),
      _ctx.skuRankingList.length > 0
        ? createElementVNode("view", utsMapOf({
            key: 0,
            class: "sku-list"
          }), [
            createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.skuRankingList, (item, index, __index, _cached): any => {
              return createElementVNode("view", utsMapOf({
                class: "sku-item",
                key: item.skuId
              }), [
                createElementVNode("view", utsMapOf({ class: "sku-rank" }), [
                  createElementVNode("text", utsMapOf({ class: "sku-rank-num" }), toDisplayString(index + 1), 1 /* TEXT */)
                ]),
                createElementVNode("view", utsMapOf({ class: "sku-info" }), [
                  createElementVNode("text", utsMapOf({ class: "sku-name" }), toDisplayString(item.productName) + " · " + toDisplayString(item.skuName), 1 /* TEXT */),
                  createElementVNode("text", utsMapOf({ class: "sku-sub" }), "销量 " + toDisplayString(item.salesVolume) + " · ¥" + toDisplayString(_ctx.formatMoney(item.salesAmount)), 1 /* TEXT */)
                ])
              ])
            }), 128 /* KEYED_FRAGMENT */)
          ])
        : createElementVNode("view", utsMapOf({
            key: 1,
            class: "ranking-empty"
          }), [
            createElementVNode("text", utsMapOf({ class: "empty-text" }), "暂无 SKU 排行数据")
          ])
    ]),
    isTrue(_ctx.isLoading)
      ? createElementVNode("view", utsMapOf({
          key: 0,
          class: "loading-mask"
        }), [
          createElementVNode("text", utsMapOf({ class: "loading-text" }), "加载中...")
        ])
      : createCommentVNode("v-if", true)
  ])
}
const GenPagesSellerStatisticsStyles = [utsMapOf([["page", padStyleMapOf(utsMapOf([["backgroundColor", "#f7f8fa"], ["display", "flex"], ["flexDirection", "column"], ["flex", 1], ["paddingBottom", 20]]))], ["summary-section", padStyleMapOf(utsMapOf([["backgroundColor", "#0066CC"], ["paddingTop", 66], ["paddingRight", 16], ["paddingBottom", 16], ["paddingLeft", 16]]))], ["summary-header", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["justifyContent", "space-between"], ["alignItems", "center"], ["marginBottom", 16]]))], ["summary-title", padStyleMapOf(utsMapOf([["fontSize", 16], ["fontWeight", "700"], ["color", "#ffffff"]]))], ["time-selector", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["backgroundColor", "rgba(255,255,255,0.2)"], ["borderTopLeftRadius", 16], ["borderTopRightRadius", 16], ["borderBottomRightRadius", 16], ["borderBottomLeftRadius", 16], ["paddingTop", 2], ["paddingRight", 2], ["paddingBottom", 2], ["paddingLeft", 2]]))], ["time-option", padStyleMapOf(utsMapOf([["paddingTop", 6], ["paddingRight", 14], ["paddingBottom", 6], ["paddingLeft", 14], ["borderTopLeftRadius", 14], ["borderTopRightRadius", 14], ["borderBottomRightRadius", 14], ["borderBottomLeftRadius", 14]]))], ["time-option-active", padStyleMapOf(utsMapOf([["backgroundColor", "#ffffff"]]))], ["time-text", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "rgba(255,255,255,0.8)"]]))], ["time-text-active", padStyleMapOf(utsMapOf([["color", "#0066CC"], ["fontWeight", "700"]]))], ["summary-cards", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["flexWrap", "wrap"], ["marginTop", -6], ["marginRight", -6], ["marginBottom", -6], ["marginLeft", -6]]))], ["summary-card", padStyleMapOf(utsMapOf([["width", "46%"], ["paddingTop", 16], ["paddingRight", 16], ["paddingBottom", 16], ["paddingLeft", 16], ["boxSizing", "border-box"], ["backgroundColor", "rgba(255,255,255,0.15)"], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12], ["display", "flex"], ["flexDirection", "column"], ["alignItems", "center"], ["marginTop", 6], ["marginRight", 6], ["marginBottom", 6], ["marginLeft", 6]]))], ["card-value", padStyleMapOf(utsMapOf([["fontSize", 20], ["fontWeight", "700"], ["color", "#ffffff"]]))], ["card-label", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "rgba(255,255,255,0.8)"], ["marginTop", 6]]))], ["chart-section", padStyleMapOf(utsMapOf([["backgroundColor", "#ffffff"], ["marginTop", 12], ["marginRight", 16], ["marginBottom", 12], ["marginLeft", 16], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12], ["paddingTop", 16], ["paddingRight", 16], ["paddingBottom", 16], ["paddingLeft", 16], ["boxShadow", "0 2px 8px rgba(0, 0, 0, 0.02)"]]))], ["section-header", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["justifyContent", "space-between"], ["alignItems", "center"], ["marginBottom", 16]]))], ["section-title", padStyleMapOf(utsMapOf([["fontSize", 15], ["fontWeight", "700"], ["color", "#333333"]]))], ["chart-container", padStyleMapOf(utsMapOf([["minHeight", 160]]))], ["chart-wrapper", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "column"]]))], ["chart-bars", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["justifyContent", "space-around"], ["alignItems", "flex-end"], ["height", 120], ["paddingTop", 0], ["paddingRight", 8], ["paddingBottom", 0], ["paddingLeft", 8]]))], ["chart-bar-item", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "column"], ["alignItems", "center"], ["flex", 1], ["maxWidth", 40]]))], ["bar-wrapper", padStyleMapOf(utsMapOf([["height", 100], ["display", "flex"], ["flexDirection", "column"], ["justifyContent", "flex-end"], ["alignItems", "center"]]))], ["bar", padStyleMapOf(utsMapOf([["width", 20], ["backgroundImage", "linear-gradient(180deg, #0066CC 0%, #4d94db 100%)"], ["backgroundColor", "rgba(0,0,0,0)"], ["borderTopLeftRadius", 4], ["borderTopRightRadius", 4], ["borderBottomRightRadius", 0], ["borderBottomLeftRadius", 0], ["minHeight", 4]]))], ["bar-label", padStyleMapOf(utsMapOf([["fontSize", 10], ["color", "#999999"], ["marginTop", 6], ["textAlign", "center"]]))], ["chart-legend", padStyleMapOf(utsMapOf([["display", "flex"], ["justifyContent", "flex-end"], ["marginTop", 12]]))], ["legend-text", padStyleMapOf(utsMapOf([["fontSize", 11], ["color", "#999999"]]))], ["chart-empty", padStyleMapOf(utsMapOf([["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"], ["height", 120]]))], ["ranking-section", padStyleMapOf(utsMapOf([["backgroundColor", "#ffffff"], ["marginTop", 0], ["marginRight", 16], ["marginBottom", 12], ["marginLeft", 16], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12], ["paddingTop", 16], ["paddingRight", 16], ["paddingBottom", 16], ["paddingLeft", 16], ["boxShadow", "0 2px 8px rgba(0, 0, 0, 0.02)"]]))], ["ranking-list", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "column"]]))], ["ranking-item", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["paddingTop", 12], ["paddingRight", 0], ["paddingBottom", 12], ["paddingLeft", 0], ["borderBottomWidth", 1], ["borderBottomStyle", "solid"], ["borderBottomColor", "#f5f5f5"]]))], ["rank-badge", padStyleMapOf(utsMapOf([["width", 24], ["height", 24], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12], ["backgroundColor", "#f5f5f5"], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"], ["flexShrink", 0]]))], ["rank-badge-first", padStyleMapOf(utsMapOf([["backgroundColor", "#ffd700"]]))], ["rank-badge-second", padStyleMapOf(utsMapOf([["backgroundColor", "#c0c0c0"]]))], ["rank-badge-third", padStyleMapOf(utsMapOf([["backgroundColor", "#cd7f32"]]))], ["rank-num", padStyleMapOf(utsMapOf([["fontSize", 12], ["fontWeight", "700"], ["color", "#666666"]]))], ["rank-num-top", padStyleMapOf(utsMapOf([["color", "#ffffff"]]))], ["product-img", padStyleMapOf(utsMapOf([["width", 50], ["height", 50], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#f5f5f5"], ["marginLeft", 12], ["flexShrink", 0]]))], ["product-info", padStyleMapOf(utsMapOf([["flex", 1], ["marginLeft", 12], ["display", "flex"], ["flexDirection", "column"]]))], ["product-name", padStyleMapOf(utsMapOf([["fontSize", 14], ["fontWeight", "bold"], ["color", "#333333"], ["lineHeight", 1.4], ["overflow", "hidden"], ["textOverflow", "ellipsis"]]))], ["product-stats", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["marginTop", 6]]))], ["stat-item", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "#999999"], ["marginRight", 16]]))], ["ranking-empty", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "column"], ["alignItems", "center"], ["justifyContent", "center"], ["paddingTop", 40], ["paddingRight", 0], ["paddingBottom", 40], ["paddingLeft", 0]]))], ["empty-icon", padStyleMapOf(utsMapOf([["fontSize", 40], ["marginBottom", 12]]))], ["empty-text", padStyleMapOf(utsMapOf([["fontSize", 14], ["color", "#999999"]]))], ["loading-mask", padStyleMapOf(utsMapOf([["position", "fixed"], ["top", 0], ["left", 0], ["right", 0], ["bottom", 0], ["backgroundColor", "rgba(255,255,255,0.8)"], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"], ["zIndex", 1000]]))], ["loading-text", padStyleMapOf(utsMapOf([["fontSize", 14], ["color", "#666666"]]))]])]
