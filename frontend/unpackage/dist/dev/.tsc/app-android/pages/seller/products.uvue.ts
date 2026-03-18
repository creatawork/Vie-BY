
import { getSellerProducts, updateProductStatus, deleteProduct, type ProductQueryParams } from '@/api/seller'

// 商品类型定义
type SellerProductType = { __$originalPosition?: UTSSourceMapPosition<"SellerProductType", "pages/seller/products.uvue", 131, 6>;
  id: number
  productName: string
  mainImage: string
  currentPrice: number
  originalPrice: number | null
  totalStock: number
  sales: number
  status: number  // 0-待审核 1-已上架 2-已下架 3-审核不通过
  categoryId: number
  categoryName: string | null
}

const __sfc__ = defineComponent({
  data() {
    return {
      currentStatus: null as number | null,
      searchKeyword: '',
      isLoading: false,
      isRefreshing: false,
      hasMore: true,
      pageNum: 1,
      pageSize: 10,
      products: [] as SellerProductType[]
    }
  },
  onLoad() {
    this.loadProducts()
  },
  onShow() {
    // 页面显示时刷新数据
    if (this.products.length > 0) {
      this.pageNum = 1
      this.loadProducts()
    }
  },
  methods: {
    // 加载商品列表
    loadProducts() {
      this.isLoading = true
      const params: ProductQueryParams = {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        status: this.currentStatus,
        keyword: this.searchKeyword !== '' ? this.searchKeyword : null,
        categoryId: null
      }
      
      getSellerProducts(params).then((res) => {
        const data = res.data as UTSJSONObject
        const total = (data.getNumber('total') ?? 0).toInt()
        const productList = this.parseProductsFromData(data)
        
        if (this.pageNum === 1) {
          this.products = productList
        } else {
          this.products = this.products.concat(productList)
        }
        
        this.hasMore = this.products.length < total
        this.isLoading = false
        this.isRefreshing = false
      }).catch((err) => {
        console.error('获取商品列表失败:', err, " at pages/seller/products.uvue:194")
        this.isLoading = false
        this.isRefreshing = false
        uni.showToast({ title: '获取商品失败', icon: 'none' })
      })
    },
    
    // 解析商品数据
    parseProductsFromData(data: UTSJSONObject): SellerProductType[] {
      const records = data.getArray('records')
      if (records == null) return [] as SellerProductType[]
      
      const productList: SellerProductType[] = []
      for (let i = 0; i < records.length; i++) {
        const item = records[i] as UTSJSONObject
        productList.push({
          id: (item.getNumber('id') ?? 0).toInt(),
          productName: item.getString('productName') ?? '',
          mainImage: item.getString('mainImage') ?? '',
          currentPrice: item.getNumber('currentPrice') ?? 0,
          originalPrice: item.getNumber('originalPrice'),
          totalStock: (item.getNumber('stock') ?? item.getNumber('totalStock') ?? 0).toInt(),
          sales: (item.getNumber('sales') ?? item.getNumber('salesVolume') ?? 0).toInt(),
          status: (item.getNumber('status') ?? 0).toInt(),
          categoryId: (item.getNumber('categoryId') ?? 0).toInt(),
          categoryName: item.getString('categoryName')
        } as SellerProductType)
      }
      return productList
    },
    
    // 切换状态筛选
    switchStatus(status: number | null) {
      if (this.currentStatus === status) return
      this.currentStatus = status
      this.pageNum = 1
      this.products = []
      this.loadProducts()
    },
    
    // 搜索
    handleSearch() {
      this.pageNum = 1
      this.products = []
      this.loadProducts()
    },
    
    // 下拉刷新
    onRefresh() {
      this.isRefreshing = true
      this.pageNum = 1
      this.loadProducts()
    },
    
    // 加载更多
    loadMore() {
      if (!this.hasMore || this.isLoading) return
      this.pageNum++
      this.loadProducts()
    },
    
    // 获取状态样式类
    getStatusClass(status: number): string {
      const classMap = new Map<number, string>([
        [0, 'status-tag-pending'],
        [1, 'status-tag-online'],
        [2, 'status-tag-offline'],
        [3, 'status-tag-rejected']
      ])
      return classMap.get(status) ?? ''
    },
    
    getStatusTextClass(status: number): string {
      const classMap = new Map<number, string>([
        [0, 'status-text-pending'],
        [1, 'status-text-online'],
        [2, 'status-text-offline'],
        [3, 'status-text-rejected']
      ])
      return classMap.get(status) ?? ''
    },
    
    // 获取状态文本
    getStatusText(status: number): string {
      const textMap = new Map<number, string>([
        [0, '待审核'],
        [1, '已上架'],
        [2, '已下架'],
        [3, '审核不通过']
      ])
      return textMap.get(status) ?? '未知'
    },
    
    // 跳转到编辑页面
    goToEdit(productId: number) {
      uni.navigateTo({ url: `/pages/seller/product-edit?id=${productId}` })
    },
    
    // 跳转到发布页面
    goToPublish() {
      uni.navigateTo({ url: '/pages/seller/product-edit' })
    },

    // 跳转到评价管理
    goToReviews(product: SellerProductType) {
      uni.navigateTo({
        url: `/pages/seller/reviews?productId=${product.id}&productName=${product.productName}`
      })
    },
    
    // 下架商品
    handleOffShelf(product: SellerProductType) {
      uni.showModal({
        title: '提示',
        content: '确定要下架该商品吗？',
        success: (res) => {
          if (res.confirm) {
            updateProductStatus(product.id, 2).then(() => {
              product.status = 2
              uni.showToast({ title: '下架成功', icon: 'success' })
            }).catch((err) => {
              console.error('下架失败:', err, " at pages/seller/products.uvue:315")
              uni.showToast({ title: '下架失败', icon: 'none' })
            })
          }
        }
      })
    },
    
    // 申请上架商品
    handleOnShelf(product: SellerProductType) {
      uni.showModal({
        title: '提示',
        content: '确定要重新申请上架该商品吗？提交后需经管理员审核。',
        success: (res) => {
          if (res.confirm) {
            uni.showLoading({ title: '提交中...' })
            updateProductStatus(product.id, 1).then(() => {
              uni.hideLoading()
              product.status = 0 // 变为待审核状态
              uni.showToast({ title: '申请已提交', icon: 'success' })
            }).catch((err) => {
              uni.hideLoading()
              console.error('申请上架失败:', err, " at pages/seller/products.uvue:337")
              uni.showToast({ title: '提交失败', icon: 'none' })
            })
          }
        }
      })
    },
    
    // 删除商品
    handleDelete(product: SellerProductType) {
      uni.showModal({
        title: '提示',
        content: '确定要删除该商品吗？删除后不可恢复。',
        success: (res) => {
          if (res.confirm) {
            deleteProduct(product.id).then(() => {
              const index = this.products.findIndex((p: SellerProductType): boolean => p.id === product.id)
              if (index !== -1) {
                this.products.splice(index, 1)
              }
              uni.showToast({ title: '删除成功', icon: 'success' })
            }).catch((err) => {
              console.error('删除失败:', err, " at pages/seller/products.uvue:359")
              uni.showToast({ title: '删除失败', icon: 'none' })
            })
          }
        }
      })
    }
  }
})

export default __sfc__
function GenPagesSellerProductsRender(this: InstanceType<typeof __sfc__>): any | null {
const _ctx = this
const _cache = this.$.renderCache
  return createElementVNode("view", utsMapOf({ class: "page" }), [
    createElementVNode("view", utsMapOf({ class: "filter-header" }), [
      createElementVNode("scroll-view", utsMapOf({
        class: "status-scroll",
        "scroll-x": "true",
        "show-scrollbar": false
      }), [
        createElementVNode("view", utsMapOf({ class: "status-tabs" }), [
          createElementVNode("view", utsMapOf({
            class: normalizeClass(["tab-item", utsMapOf({ active: _ctx.currentStatus === null })]),
            onClick: () => {_ctx.switchStatus(null)}
          }), [
            createElementVNode("text", utsMapOf({
              class: normalizeClass(["tab-text", utsMapOf({ 'tab-text--active': _ctx.currentStatus === null })])
            }), "全部", 2 /* CLASS */),
            _ctx.currentStatus === null
              ? createElementVNode("view", utsMapOf({
                  key: 0,
                  class: "active-line"
                }))
              : createCommentVNode("v-if", true)
          ], 10 /* CLASS, PROPS */, ["onClick"]),
          createElementVNode("view", utsMapOf({
            class: normalizeClass(["tab-item", utsMapOf({ active: _ctx.currentStatus === 0 })]),
            onClick: () => {_ctx.switchStatus(0)}
          }), [
            createElementVNode("text", utsMapOf({
              class: normalizeClass(["tab-text", utsMapOf({ 'tab-text--active': _ctx.currentStatus === 0 })])
            }), "待审核", 2 /* CLASS */),
            _ctx.currentStatus === 0
              ? createElementVNode("view", utsMapOf({
                  key: 0,
                  class: "active-line"
                }))
              : createCommentVNode("v-if", true)
          ], 10 /* CLASS, PROPS */, ["onClick"]),
          createElementVNode("view", utsMapOf({
            class: normalizeClass(["tab-item", utsMapOf({ active: _ctx.currentStatus === 1 })]),
            onClick: () => {_ctx.switchStatus(1)}
          }), [
            createElementVNode("text", utsMapOf({
              class: normalizeClass(["tab-text", utsMapOf({ 'tab-text--active': _ctx.currentStatus === 1 })])
            }), "已上架", 2 /* CLASS */),
            _ctx.currentStatus === 1
              ? createElementVNode("view", utsMapOf({
                  key: 0,
                  class: "active-line"
                }))
              : createCommentVNode("v-if", true)
          ], 10 /* CLASS, PROPS */, ["onClick"]),
          createElementVNode("view", utsMapOf({
            class: normalizeClass(["tab-item", utsMapOf({ active: _ctx.currentStatus === 2 })]),
            onClick: () => {_ctx.switchStatus(2)}
          }), [
            createElementVNode("text", utsMapOf({
              class: normalizeClass(["tab-text", utsMapOf({ 'tab-text--active': _ctx.currentStatus === 2 })])
            }), "已下架", 2 /* CLASS */),
            _ctx.currentStatus === 2
              ? createElementVNode("view", utsMapOf({
                  key: 0,
                  class: "active-line"
                }))
              : createCommentVNode("v-if", true)
          ], 10 /* CLASS, PROPS */, ["onClick"]),
          createElementVNode("view", utsMapOf({
            class: normalizeClass(["tab-item", utsMapOf({ active: _ctx.currentStatus === 3 })]),
            onClick: () => {_ctx.switchStatus(3)}
          }), [
            createElementVNode("text", utsMapOf({
              class: normalizeClass(["tab-text", utsMapOf({ 'tab-text--active': _ctx.currentStatus === 3 })])
            }), "审核不通过", 2 /* CLASS */),
            _ctx.currentStatus === 3
              ? createElementVNode("view", utsMapOf({
                  key: 0,
                  class: "active-line"
                }))
              : createCommentVNode("v-if", true)
          ], 10 /* CLASS, PROPS */, ["onClick"])
        ])
      ]),
      createElementVNode("view", utsMapOf({ class: "search-bar" }), [
        createElementVNode("text", utsMapOf({ class: "search-icon" }), "🔍"),
        createElementVNode("input", utsMapOf({
          class: "search-input",
          modelValue: _ctx.searchKeyword,
          onInput: ($event: InputEvent) => {(_ctx.searchKeyword) = $event.detail.value},
          placeholder: "搜索商品名称",
          onConfirm: _ctx.handleSearch
        }), null, 40 /* PROPS, NEED_HYDRATION */, ["modelValue", "onInput", "onConfirm"]),
        _ctx.searchKeyword !== ''
          ? createElementVNode("view", utsMapOf({
              key: 0,
              class: "search-btn",
              onClick: _ctx.handleSearch
            }), [
              createElementVNode("text", utsMapOf({ class: "search-btn-text" }), "搜索")
            ], 8 /* PROPS */, ["onClick"])
          : createCommentVNode("v-if", true)
      ])
    ]),
    createElementVNode("scroll-view", utsMapOf({
      class: "product-scroll",
      "scroll-y": "true",
      onScrolltolower: _ctx.loadMore,
      onRefresherrefresh: _ctx.onRefresh,
      "refresher-enabled": true,
      "refresher-triggered": _ctx.isRefreshing,
      "show-scrollbar": false
    }), [
      isTrue(_ctx.isLoading && _ctx.products.length === 0)
        ? createElementVNode("view", utsMapOf({
            key: 0,
            class: "loading-state"
          }), [
            createElementVNode("text", utsMapOf({ class: "loading-text" }), "加载中...")
          ])
        : createElementVNode("view", utsMapOf({
            key: 1,
            class: "product-list"
          }), [
            createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.products, (product, __key, __index, _cached): any => {
              return createElementVNode("view", utsMapOf({
                class: "product-card",
                key: product.id,
                onClick: () => {_ctx.goToEdit(product.id)}
              }), [
                createElementVNode("image", utsMapOf({
                  class: "product-img",
                  src: product.mainImage,
                  mode: "aspectFill"
                }), null, 8 /* PROPS */, ["src"]),
                createElementVNode("view", utsMapOf({ class: "product-info" }), [
                  createElementVNode("view", utsMapOf({ class: "info-top" }), [
                    createElementVNode("text", utsMapOf({ class: "product-name" }), toDisplayString(product.productName), 1 /* TEXT */),
                    createElementVNode("view", utsMapOf({
                      class: normalizeClass(["status-tag", _ctx.getStatusClass(product.status)])
                    }), [
                      createElementVNode("text", utsMapOf({
                        class: normalizeClass(["status-text", _ctx.getStatusTextClass(product.status)])
                      }), toDisplayString(_ctx.getStatusText(product.status)), 3 /* TEXT, CLASS */)
                    ], 2 /* CLASS */)
                  ]),
                  createElementVNode("view", utsMapOf({ class: "info-middle" }), [
                    createElementVNode("view", utsMapOf({ class: "price-row" }), [
                      createElementVNode("text", utsMapOf({ class: "currency" }), "¥"),
                      createElementVNode("text", utsMapOf({ class: "price" }), toDisplayString(product.currentPrice.toFixed(2)), 1 /* TEXT */),
                      isTrue(product.originalPrice != null && (product.originalPrice as number) > 0)
                        ? createElementVNode("text", utsMapOf({
                            key: 0,
                            class: "original-price"
                          }), "¥" + toDisplayString((product.originalPrice as number).toFixed(2)), 1 /* TEXT */)
                        : createCommentVNode("v-if", true)
                    ]),
                    createElementVNode("view", utsMapOf({ class: "stock-row" }), [
                      createElementVNode("text", utsMapOf({ class: "stock-label" }), "库存:"),
                      createElementVNode("text", utsMapOf({
                        class: normalizeClass(["stock-value", utsMapOf({ 'stock-value-low': product.totalStock < 10 })])
                      }), toDisplayString(product.totalStock), 3 /* TEXT, CLASS */)
                    ])
                  ]),
                  createElementVNode("view", utsMapOf({ class: "info-bottom" }), [
                    createElementVNode("text", utsMapOf({ class: "sales-text" }), "销量: " + toDisplayString(product.sales), 1 /* TEXT */),
                    createElementVNode("view", utsMapOf({ class: "action-btns" }), [
                      product.status === 1
                        ? createElementVNode("view", utsMapOf({
                            key: 0,
                            class: "action-btn",
                            onClick: withModifiers(() => {_ctx.handleOffShelf(product)}, ["stop"])
                          }), [
                            createElementVNode("text", utsMapOf({ class: "action-btn-text" }), "下架")
                          ], 8 /* PROPS */, ["onClick"])
                        : createCommentVNode("v-if", true),
                      isTrue(product.status === 2 || product.status === 3)
                        ? createElementVNode("view", utsMapOf({
                            key: 1,
                            class: "action-btn",
                            onClick: withModifiers(() => {_ctx.handleOnShelf(product)}, ["stop"])
                          }), [
                            createElementVNode("text", utsMapOf({ class: "action-btn-text" }), "申请上架")
                          ], 8 /* PROPS */, ["onClick"])
                        : createCommentVNode("v-if", true),
                      createElementVNode("view", utsMapOf({
                        class: "action-btn",
                        onClick: withModifiers(() => {_ctx.goToReviews(product)}, ["stop"])
                      }), [
                        createElementVNode("text", utsMapOf({ class: "action-btn-text" }), "评价")
                      ], 8 /* PROPS */, ["onClick"]),
                      createElementVNode("view", utsMapOf({
                        class: "action-btn action-btn-delete",
                        onClick: withModifiers(() => {_ctx.handleDelete(product)}, ["stop"])
                      }), [
                        createElementVNode("text", utsMapOf({ class: "action-btn-text action-btn-text-delete" }), "删除")
                      ], 8 /* PROPS */, ["onClick"])
                    ])
                  ])
                ])
              ], 8 /* PROPS */, ["onClick"])
            }), 128 /* KEYED_FRAGMENT */)
          ]),
      isTrue(!_ctx.isLoading && _ctx.products.length === 0)
        ? createElementVNode("view", utsMapOf({
            key: 2,
            class: "empty-state"
          }), [
            createElementVNode("text", utsMapOf({ class: "empty-icon" }), "📦"),
            createElementVNode("text", utsMapOf({ class: "empty-text" }), "暂无商品"),
            createElementVNode("view", utsMapOf({
              class: "empty-btn",
              onClick: _ctx.goToPublish
            }), [
              createElementVNode("text", utsMapOf({ class: "empty-btn-text" }), "去发布商品")
            ], 8 /* PROPS */, ["onClick"])
          ])
        : createCommentVNode("v-if", true),
      _ctx.products.length > 0
        ? createElementVNode("view", utsMapOf({
            key: 3,
            class: "load-more"
          }), [
            createElementVNode("text", utsMapOf({ class: "load-more-text" }), toDisplayString(_ctx.hasMore ? '上拉加载更多' : '没有更多了'), 1 /* TEXT */)
          ])
        : createCommentVNode("v-if", true),
      createElementVNode("view", utsMapOf({
        style: normalizeStyle(utsMapOf({"height":"80px"}))
      }), null, 4 /* STYLE */)
    ], 40 /* PROPS, NEED_HYDRATION */, ["onScrolltolower", "onRefresherrefresh", "refresher-triggered"]),
    createElementVNode("view", utsMapOf({ class: "publish-btn-wrapper" }), [
      createElementVNode("view", utsMapOf({
        class: "publish-btn",
        onClick: _ctx.goToPublish
      }), [
        createElementVNode("text", utsMapOf({ class: "publish-icon" }), "+"),
        createElementVNode("text", utsMapOf({ class: "publish-text" }), "发布商品")
      ], 8 /* PROPS */, ["onClick"])
    ])
  ])
}
const GenPagesSellerProductsStyles = [utsMapOf([["page", padStyleMapOf(utsMapOf([["backgroundColor", "#f7f8fa"], ["display", "flex"], ["flexDirection", "column"], ["flex", 1]]))], ["filter-header", padStyleMapOf(utsMapOf([["backgroundColor", "#ffffff"], ["paddingTop", CSS_VAR_STATUS_BAR_HEIGHT], ["boxShadow", "0 1px 4px rgba(0, 0, 0, 0.05)"], ["zIndex", 100]]))], ["status-scroll", padStyleMapOf(utsMapOf([["whiteSpace", "nowrap"], ["borderBottomWidth", 1], ["borderBottomStyle", "solid"], ["borderBottomColor", "#f5f5f5"]]))], ["status-tabs", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["paddingTop", 0], ["paddingRight", 8], ["paddingBottom", 0], ["paddingLeft", 8]]))], ["tab-item", padStyleMapOf(utsMapOf([["position", "relative"], ["paddingTop", 12], ["paddingRight", 16], ["paddingBottom", 12], ["paddingLeft", 16], ["display", "flex"], ["flexDirection", "column"], ["alignItems", "center"]]))], ["tab-text", padStyleMapOf(utsMapOf([["fontSize", 14], ["color", "#666666"]]))], ["tab-text--active", padStyleMapOf(utsMapOf([["color", "#0066CC"], ["fontWeight", "700"]]))], ["active-line", padStyleMapOf(utsMapOf([["position", "absolute"], ["bottom", 4], ["width", 20], ["height", 3], ["backgroundColor", "#0066CC"], ["borderTopLeftRadius", 2], ["borderTopRightRadius", 2], ["borderBottomRightRadius", 2], ["borderBottomLeftRadius", 2]]))], ["search-bar", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["marginTop", 10], ["marginRight", 16], ["marginBottom", 10], ["marginLeft", 16], ["paddingTop", 0], ["paddingRight", 12], ["paddingBottom", 0], ["paddingLeft", 12], ["height", 36], ["backgroundColor", "#f5f5f5"], ["borderTopLeftRadius", 18], ["borderTopRightRadius", 18], ["borderBottomRightRadius", 18], ["borderBottomLeftRadius", 18]]))], ["search-icon", padStyleMapOf(utsMapOf([["fontSize", 14], ["color", "#999999"], ["marginRight", 8]]))], ["search-input", padStyleMapOf(utsMapOf([["flex", 1], ["fontSize", 14], ["color", "#333333"]]))], ["search-btn", padStyleMapOf(utsMapOf([["paddingTop", 4], ["paddingRight", 12], ["paddingBottom", 4], ["paddingLeft", 12], ["backgroundColor", "#0066CC"], ["borderTopLeftRadius", 14], ["borderTopRightRadius", 14], ["borderBottomRightRadius", 14], ["borderBottomLeftRadius", 14], ["marginLeft", 8]]))], ["search-btn-text", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "#ffffff"]]))], ["product-scroll", padStyleMapOf(utsMapOf([["flex", 1], ["width", "100%"]]))], ["loading-state", padStyleMapOf(utsMapOf([["paddingTop", 60], ["paddingRight", 0], ["paddingBottom", 60], ["paddingLeft", 0], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["loading-text", padStyleMapOf(utsMapOf([["fontSize", 14], ["color", "#999999"]]))], ["product-list", padStyleMapOf(utsMapOf([["paddingTop", 12], ["paddingRight", 16], ["paddingBottom", 12], ["paddingLeft", 16], ["display", "flex"], ["flexDirection", "column"]]))], ["product-card", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["backgroundColor", "#ffffff"], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12], ["paddingTop", 12], ["paddingRight", 12], ["paddingBottom", 12], ["paddingLeft", 12], ["marginBottom", 12], ["boxShadow", "0 2px 8px rgba(0, 0, 0, 0.02)"]]))], ["product-img", padStyleMapOf(utsMapOf([["width", 90], ["height", 90], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#f5f5f5"], ["flexShrink", 0]]))], ["product-info", padStyleMapOf(utsMapOf([["flex", 1], ["marginLeft", 12], ["display", "flex"], ["flexDirection", "column"], ["justifyContent", "space-between"]]))], ["info-top", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["justifyContent", "space-between"], ["alignItems", "flex-start"]]))], ["product-name", padStyleMapOf(utsMapOf([["flex", 1], ["fontSize", 14], ["fontWeight", "700"], ["color", "#333333"], ["lineHeight", 1.4], ["marginRight", 8], ["overflow", "hidden"], ["textOverflow", "ellipsis"]]))], ["status-tag", padStyleMapOf(utsMapOf([["paddingTop", 2], ["paddingRight", 8], ["paddingBottom", 2], ["paddingLeft", 8], ["borderTopLeftRadius", 4], ["borderTopRightRadius", 4], ["borderBottomRightRadius", 4], ["borderBottomLeftRadius", 4], ["flexShrink", 0]]))], ["status-tag-pending", padStyleMapOf(utsMapOf([["backgroundColor", "#fff7e6"]]))], ["status-tag-online", padStyleMapOf(utsMapOf([["backgroundColor", "#e6f7ff"]]))], ["status-tag-offline", padStyleMapOf(utsMapOf([["backgroundColor", "#f5f5f5"]]))], ["status-tag-rejected", padStyleMapOf(utsMapOf([["backgroundColor", "#fff1f0"]]))], ["status-text", padStyleMapOf(utsMapOf([["fontSize", 11]]))], ["status-text-pending", padStyleMapOf(utsMapOf([["color", "#fa8c16"]]))], ["status-text-online", padStyleMapOf(utsMapOf([["color", "#1890ff"]]))], ["status-text-offline", padStyleMapOf(utsMapOf([["color", "#999999"]]))], ["status-text-rejected", padStyleMapOf(utsMapOf([["color", "#ff4d4f"]]))], ["info-middle", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["justifyContent", "space-between"], ["alignItems", "center"], ["marginTop", 8], ["marginRight", 0], ["marginBottom", 8], ["marginLeft", 0]]))], ["price-row", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "flex-end"]]))], ["currency", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "#ff4d4f"], ["fontWeight", "700"]]))], ["price", padStyleMapOf(utsMapOf([["fontSize", 16], ["color", "#ff4d4f"], ["fontWeight", "700"]]))], ["original-price", padStyleMapOf(utsMapOf([["fontSize", 11], ["color", "#999999"], ["marginLeft", 6]]))], ["stock-row", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"]]))], ["stock-label", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "#999999"]]))], ["stock-value", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "#333333"], ["marginLeft", 4]]))], ["stock-value-low", padStyleMapOf(utsMapOf([["color", "#ff4d4f"]]))], ["info-bottom", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["justifyContent", "space-between"], ["alignItems", "center"]]))], ["sales-text", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "#999999"]]))], ["action-btns", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"]]))], ["action-btn", padStyleMapOf(utsMapOf([["paddingTop", 4], ["paddingRight", 12], ["paddingBottom", 4], ["paddingLeft", 12], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#0066CC"], ["borderRightColor", "#0066CC"], ["borderBottomColor", "#0066CC"], ["borderLeftColor", "#0066CC"], ["borderTopLeftRadius", 14], ["borderTopRightRadius", 14], ["borderBottomRightRadius", 14], ["borderBottomLeftRadius", 14], ["marginLeft", 8]]))], ["action-btn-delete", padStyleMapOf(utsMapOf([["borderTopColor", "#ff4d4f"], ["borderRightColor", "#ff4d4f"], ["borderBottomColor", "#ff4d4f"], ["borderLeftColor", "#ff4d4f"]]))], ["action-btn-text", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "#0066CC"]]))], ["action-btn-text-delete", padStyleMapOf(utsMapOf([["color", "#ff4d4f"]]))], ["empty-state", padStyleMapOf(utsMapOf([["paddingTop", 80], ["paddingRight", 0], ["paddingBottom", 80], ["paddingLeft", 0], ["display", "flex"], ["flexDirection", "column"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["empty-icon", padStyleMapOf(utsMapOf([["fontSize", 48], ["marginBottom", 16], ["color", "#cccccc"]]))], ["empty-text", padStyleMapOf(utsMapOf([["fontSize", 14], ["color", "#999999"], ["marginBottom", 20]]))], ["empty-btn", padStyleMapOf(utsMapOf([["paddingTop", 10], ["paddingRight", 24], ["paddingBottom", 10], ["paddingLeft", 24], ["backgroundColor", "#0066CC"], ["borderTopLeftRadius", 20], ["borderTopRightRadius", 20], ["borderBottomRightRadius", 20], ["borderBottomLeftRadius", 20]]))], ["empty-btn-text", padStyleMapOf(utsMapOf([["fontSize", 14], ["color", "#ffffff"]]))], ["load-more", padStyleMapOf(utsMapOf([["paddingTop", 20], ["paddingRight", 0], ["paddingBottom", 20], ["paddingLeft", 0], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["load-more-text", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "#999999"]]))], ["publish-btn-wrapper", padStyleMapOf(utsMapOf([["position", "fixed"], ["bottom", 20], ["left", 0], ["right", 0], ["display", "flex"], ["justifyContent", "center"], ["zIndex", 100]]))], ["publish-btn", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "center"], ["paddingTop", 12], ["paddingRight", 32], ["paddingBottom", 12], ["paddingLeft", 32], ["backgroundColor", "#0066CC"], ["borderTopLeftRadius", 24], ["borderTopRightRadius", 24], ["borderBottomRightRadius", 24], ["borderBottomLeftRadius", 24], ["boxShadow", "0 4px 12px rgba(0, 102, 204, 0.3)"]]))], ["publish-icon", padStyleMapOf(utsMapOf([["fontSize", 18], ["color", "#ffffff"], ["marginRight", 6], ["fontWeight", "700"]]))], ["publish-text", padStyleMapOf(utsMapOf([["fontSize", 15], ["color", "#ffffff"], ["fontWeight", "700"]]))]])]
