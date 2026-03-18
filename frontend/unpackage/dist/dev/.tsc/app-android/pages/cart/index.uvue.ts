
import BottomNav from '@/components/BottomNav.uvue'
import { getCart, updateCartQuantity, deleteCartItem, updateCartSelected, selectAllCart, type CartItemVO, type CartVO } from '@/api/cart'

const __sfc__ = defineComponent({
  components: {
    BottomNav
  },
  data() {
    return {
      isEditing: false,
      isLoading: true,
      isRefreshing: false,
      cartData: {
        items: [] as CartItemVO[],
        totalQuantity: 0,
        selectedQuantity: 0,
        selectedAmount: 0,
        allSelected: false,
        validCount: 0,
        invalidCount: 0
      } as CartVO
    }
  },
  onLoad() {
    this.loadCart()
  },
  onShow() {
    uni.$emit('bottomNavRefresh')
    // 每次显示页面时刷新购物车
    this.loadCart()
  },
  methods: {
    /**
     * 加载购物车数据
     */
    loadCart() {
      this.isLoading = true
      getCart().then((res) => {
        console.log('=== 购物车响应 ===', " at pages/cart/index.uvue:155")
        console.log('响应code:', res.code, " at pages/cart/index.uvue:156")
        console.log('响应message:', res.message, " at pages/cart/index.uvue:157")
        console.log('响应data类型:', typeof res.data, " at pages/cart/index.uvue:158")
        console.log('购物车原始数据:', JSON.stringify(res.data), " at pages/cart/index.uvue:159")
        const data = res.data as UTSJSONObject
        this.parseCartData(data)
        console.log('解析后购物车数据:', this.cartData.items.length, '件商品', " at pages/cart/index.uvue:162")
        console.log('=================', " at pages/cart/index.uvue:163")
        this.isLoading = false
        this.isRefreshing = false
      }).catch((err) => {
        console.error('获取购物车失败:', err, " at pages/cart/index.uvue:167")
        this.isLoading = false
        this.isRefreshing = false
        uni.showToast({ title: '获取购物车失败', icon: 'none' })
      })
    },
    
    /**
     * 解析购物车数据
     */
    parseCartData(data : UTSJSONObject) {
      const itemsArr = data.getArray('items')
      const items : CartItemVO[] = []
      
      console.log('购物车items数组:', itemsArr != null ? itemsArr.length : 'null', " at pages/cart/index.uvue:181")
      
      if (itemsArr != null) {
        for (let i = 0; i < itemsArr.length; i++) {
          const item = itemsArr[i] as UTSJSONObject
          // 兼容 cartId 和 id 两种字段名
          const cartId = (item.getNumber('cartId') ?? item.getNumber('id') ?? 0).toInt()
          console.log('解析商品:', item.getString('productName'), 'cartId:', cartId, " at pages/cart/index.uvue:188")
          items.push({
          cartId: cartId,
          productId: (item.getNumber('productId') ?? 0).toInt(),
          skuId: (item.getNumber('skuId') ?? 0).toInt(),
          productName: item.getString('productName') ?? '',
          productImage: item.getString('productImage') ?? '',
          skuName: item.getString('skuName') ?? '',
          skuImage: item.getString('skuImage'),
          price: item.getNumber('price') ?? 0,
          quantity: (item.getNumber('quantity') ?? 0).toInt(),
          totalPrice: item.getNumber('totalPrice') ?? 0,
          stock: (item.getNumber('stock') ?? 0).toInt(),
          selected: item.getBoolean('selected') ?? false,
          productStatus: (item.getNumber('productStatus') ?? 0).toInt(),
          skuStatus: (item.getNumber('skuStatus') ?? 0).toInt(),
          valid: item.getBoolean('valid') ?? true
        } as CartItemVO)
        }
      }
      
      this.cartData = {
        items: items,
        totalQuantity: (data.getNumber('totalQuantity') ?? 0).toInt(),
        selectedQuantity: (data.getNumber('selectedQuantity') ?? 0).toInt(),
        selectedAmount: data.getNumber('selectedAmount') ?? 0,
        allSelected: data.getBoolean('allSelected') ?? false,
        validCount: (data.getNumber('validCount') ?? 0).toInt(),
        invalidCount: (data.getNumber('invalidCount') ?? 0).toInt()
      } as CartVO
    },
    
    /**
     * 下拉刷新
     */
    onRefresh() {
      this.isRefreshing = true
      this.loadCart()
    },
    
    toggleEditMode() {
      this.isEditing = !this.isEditing
    },
    
    /**
     * 切换单个商品选中状态
     */
    toggleSelect(item : CartItemVO) {
      const newSelected = !item.selected
      updateCartSelected(item.cartId, newSelected).then(() => {
        item.selected = newSelected
        this.recalculateCart()
      }).catch((err) => {
        console.error('更新选中状态失败:', err, " at pages/cart/index.uvue:241")
        uni.showToast({ title: '操作失败', icon: 'none' })
      })
    },
    
    /**
     * 全选/取消全选
     */
    toggleSelectAll() {
      const newSelected = !this.cartData.allSelected
      selectAllCart(newSelected).then(() => {
        this.cartData.items.forEach((item : CartItemVO) => {
          if (item.valid) {
            item.selected = newSelected
          }
        })
        this.recalculateCart()
      }).catch((err) => {
        console.error('全选操作失败:', err, " at pages/cart/index.uvue:259")
        uni.showToast({ title: '操作失败', icon: 'none' })
      })
    },
    
    /**
     * 重新计算购物车统计数据
     */
    recalculateCart() {
      let selectedQuantity = 0
      let selectedAmount = 0
      let allSelected = true
      let validCount = 0
      
      this.cartData.items.forEach((item : CartItemVO) => {
        if (item.valid) {
          validCount++
          if (item.selected) {
            selectedQuantity += item.quantity
            selectedAmount += item.totalPrice
          } else {
            allSelected = false
          }
        }
      })
      
      if (validCount === 0) {
        allSelected = false
      }
      
      this.cartData.selectedQuantity = selectedQuantity
      this.cartData.selectedAmount = selectedAmount
      this.cartData.allSelected = allSelected
      this.cartData.validCount = validCount
    },
    
    /**
     * 增加数量
     */
    increaseQuantity(item : CartItemVO) {
      if (item.quantity >= item.stock) {
        uni.showToast({ title: '库存不足', icon: 'none' })
        return
      }
      
      const newQuantity = item.quantity + 1
      updateCartQuantity(item.cartId, newQuantity).then(() => {
        item.quantity = newQuantity
        item.totalPrice = item.price * newQuantity
        this.recalculateCart()
      }).catch((err) => {
        console.error('更新数量失败:', err, " at pages/cart/index.uvue:310")
        uni.showToast({ title: '操作失败', icon: 'none' })
      })
    },
    
    /**
     * 减少数量
     */
    decreaseQuantity(item : CartItemVO) {
      if (item.quantity <= 1) {
        // 数量为1时，询问是否删除
        uni.showModal({
          title: '提示',
          content: '确认删除该商品吗？',
          success: (res) => {
            if (res.confirm) {
              this.deleteItem(item)
            }
          }
        })
        return
      }
      
      const newQuantity = item.quantity - 1
      updateCartQuantity(item.cartId, newQuantity).then(() => {
        item.quantity = newQuantity
        item.totalPrice = item.price * newQuantity
        this.recalculateCart()
      }).catch((err) => {
        console.error('更新数量失败:', err, " at pages/cart/index.uvue:339")
        uni.showToast({ title: '操作失败', icon: 'none' })
      })
    },
    
    /**
     * 删除单个商品
     */
    deleteItem(item : CartItemVO) {
      deleteCartItem(item.cartId).then(() => {
        const index = this.cartData.items.findIndex((i : CartItemVO) : boolean => i.cartId === item.cartId)
        if (index !== -1) {
          this.cartData.items.splice(index, 1)
          this.cartData.totalQuantity -= item.quantity
          this.recalculateCart()
        }
        uni.showToast({ title: '删除成功', icon: 'success' })
      }).catch((err) => {
        console.error('删除失败:', err, " at pages/cart/index.uvue:357")
        uni.showToast({ title: '删除失败', icon: 'none' })
      })
    },
    
    /**
     * 删除选中的商品
     */
    removeSelectedItems() {
      const selectedItems = this.cartData.items.filter((item : CartItemVO) : boolean => item.selected)
      if (selectedItems.length === 0) {
        return
      }
      
      uni.showModal({
        title: '提示',
        content: `确定删除选中的 ${selectedItems.length} 件商品吗？`,
        success: (res) => {
          if (res.confirm) {
            // 依次删除选中的商品
            let deleteCount = 0
            selectedItems.forEach((item : CartItemVO) => {
              deleteCartItem(item.cartId).then(() => {
                deleteCount++
                if (deleteCount === selectedItems.length) {
                  // 全部删除完成后刷新购物车
                  this.loadCart()
                  this.isEditing = false
                  uni.showToast({ title: '删除成功', icon: 'success' })
                }
              }).catch((err) => {
                console.error('删除失败:', err, " at pages/cart/index.uvue:388")
              })
            })
          }
        }
      })
    },
    
    goShopping() {
      uni.navigateBack({ delta: 99 })
    },
    
    goToDetail(productId : number) {
      uni.navigateTo({ url: `/pages/product/detail?id=${productId}` })
    },
    
    goCheckout() {
      // 获取选中的购物车ID
      const selectedCartIds = this.cartData.items
        .filter((item : CartItemVO) : boolean => item.selected && item.valid)
        .map((item : CartItemVO) : number => item.cartId)
      
      if (selectedCartIds.length === 0) {
        uni.showToast({ title: '请选择商品', icon: 'none' })
        return
      }
      
      // 跳转到结算页面
      uni.navigateTo({ url: `/pages/order/checkout?type=cart&cartIds=${selectedCartIds.join(',')}` })
    }
  }
})

export default __sfc__
function GenPagesCartIndexRender(this: InstanceType<typeof __sfc__>): any | null {
const _ctx = this
const _cache = this.$.renderCache
const _component_checkbox = resolveComponent("checkbox")
const _component_BottomNav = resolveComponent("BottomNav")

  return createElementVNode("view", utsMapOf({ class: "cart-page" }), [
    createElementVNode("view", utsMapOf({ class: "header" }), [
      createElementVNode("view", utsMapOf({ class: "header-left" }), [
        createElementVNode("text", utsMapOf({ class: "page-title" }), "购物车"),
        createElementVNode("text", utsMapOf({ class: "cart-count" }), "(" + toDisplayString(_ctx.cartData.totalQuantity) + ")", 1 /* TEXT */)
      ]),
      createElementVNode("text", utsMapOf({
        class: "edit-btn",
        onClick: _ctx.toggleEditMode
      }), toDisplayString(_ctx.isEditing ? '完成' : '编辑'), 9 /* TEXT, PROPS */, ["onClick"])
    ]),
    isTrue(_ctx.isLoading)
      ? createElementVNode("view", utsMapOf({
          key: 0,
          class: "loading-state"
        }), [
          createElementVNode("text", utsMapOf({ class: "loading-text" }), "加载中...")
        ])
      : _ctx.cartData.items.length === 0
        ? createElementVNode("view", utsMapOf({
            key: 1,
            class: "empty-cart"
          }), [
            createElementVNode("text", utsMapOf({ class: "empty-icon" }), "🛒"),
            createElementVNode("text", utsMapOf({ class: "empty-title" }), "购物车空空如也"),
            createElementVNode("text", utsMapOf({ class: "empty-desc" }), "赶紧去挑选心仪的商品吧"),
            createElementVNode("button", utsMapOf({
              class: "go-shopping-btn",
              onClick: _ctx.goShopping
            }), "去逛逛", 8 /* PROPS */, ["onClick"])
          ])
        : createElementVNode("scroll-view", utsMapOf({
            key: 2,
            class: "cart-scroll",
            "scroll-y": "true",
            onRefresherrefresh: _ctx.onRefresh,
            "refresher-enabled": true,
            "refresher-triggered": _ctx.isRefreshing
          }), [
            createElementVNode("view", utsMapOf({ class: "cart-list" }), [
              createElementVNode("view", utsMapOf({ class: "shop-group" }), [
                createElementVNode("view", utsMapOf({ class: "shop-header" }), [
                  createVNode(_component_checkbox, utsMapOf({
                    class: "shop-checkbox",
                    checked: _ctx.cartData.allSelected,
                    onClick: _ctx.toggleSelectAll,
                    color: "#0066CC"
                  }), null, 8 /* PROPS */, ["checked", "onClick"]),
                  createElementVNode("text", utsMapOf({ class: "shop-icon" }), "🏪"),
                  createElementVNode("text", utsMapOf({ class: "shop-name" }), "鲜农优选直营店")
                ]),
                createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.cartData.items, (item, index, __index, _cached): any => {
                  return createElementVNode("view", utsMapOf({
                    class: "cart-item",
                    key: item.cartId
                  }), [
                    createElementVNode("view", utsMapOf({
                      class: "item-checkbox-wrapper",
                      onClick: () => {_ctx.toggleSelect(item)}
                    }), [
                      createVNode(_component_checkbox, utsMapOf({
                        checked: item.selected,
                        color: "#0066CC",
                        onClick: withModifiers(() => {_ctx.toggleSelect(item)}, ["stop"])
                      }), null, 8 /* PROPS */, ["checked", "onClick"])
                    ], 8 /* PROPS */, ["onClick"]),
                    createElementVNode("view", utsMapOf({
                      class: "item-content",
                      onClick: () => {_ctx.goToDetail(item.productId)}
                    }), [
                      createElementVNode("image", utsMapOf({
                        class: "item-image",
                        src: item.productImage,
                        mode: "aspectFill"
                      }), null, 8 /* PROPS */, ["src"]),
                      createElementVNode("view", utsMapOf({ class: "item-info" }), [
                        createElementVNode("view", utsMapOf({ class: "info-top" }), [
                          createElementVNode("text", utsMapOf({ class: "item-name" }), toDisplayString(item.productName), 1 /* TEXT */),
                          isTrue(item.skuName)
                            ? createElementVNode("view", utsMapOf({
                                key: 0,
                                class: "item-spec-box"
                              }), [
                                createElementVNode("text", utsMapOf({ class: "item-spec" }), toDisplayString(item.skuName), 1 /* TEXT */)
                              ])
                            : createCommentVNode("v-if", true),
                          isTrue(!item.valid)
                            ? createElementVNode("view", utsMapOf({
                                key: 1,
                                class: "invalid-tag"
                              }), [
                                createElementVNode("text", utsMapOf({ class: "invalid-text" }), toDisplayString(item.productStatus !== 1 ? '已下架' : '库存不足'), 1 /* TEXT */)
                              ])
                            : createCommentVNode("v-if", true)
                        ]),
                        createElementVNode("view", utsMapOf({ class: "info-bottom" }), [
                          createElementVNode("view", utsMapOf({ class: "price-box" }), [
                            createElementVNode("text", utsMapOf({ class: "currency" }), "¥"),
                            createElementVNode("text", utsMapOf({ class: "price" }), toDisplayString(item.price.toFixed(2)), 1 /* TEXT */)
                          ]),
                          createElementVNode("view", utsMapOf({ class: "quantity-control" }), [
                            createElementVNode("view", utsMapOf({
                              class: normalizeClass(["qty-btn qty-btn-minus", utsMapOf({ 'qty-btn-disabled': item.quantity <= 1 })]),
                              onClick: withModifiers(() => {_ctx.decreaseQuantity(item)}, ["stop"])
                            }), [
                              createElementVNode("text", utsMapOf({ class: "qty-btn-text" }), "-")
                            ], 10 /* CLASS, PROPS */, ["onClick"]),
                            createElementVNode("view", utsMapOf({ class: "qty-input" }), [
                              createElementVNode("text", utsMapOf({ class: "qty-text" }), toDisplayString(item.quantity), 1 /* TEXT */)
                            ]),
                            createElementVNode("view", utsMapOf({
                              class: normalizeClass(["qty-btn qty-btn-plus", utsMapOf({ 'qty-btn-disabled': item.quantity >= item.stock })]),
                              onClick: withModifiers(() => {_ctx.increaseQuantity(item)}, ["stop"])
                            }), [
                              createElementVNode("text", utsMapOf({ class: "qty-btn-text" }), "+")
                            ], 10 /* CLASS, PROPS */, ["onClick"])
                          ])
                        ])
                      ])
                    ], 8 /* PROPS */, ["onClick"])
                  ])
                }), 128 /* KEYED_FRAGMENT */)
              ])
            ]),
            createElementVNode("view", utsMapOf({
              style: normalizeStyle(utsMapOf({"height":"140rpx"}))
            }), null, 4 /* STYLE */)
          ], 40 /* PROPS, NEED_HYDRATION */, ["onRefresherrefresh", "refresher-triggered"]),
    _ctx.cartData.items.length > 0
      ? createElementVNode("view", utsMapOf({
          key: 3,
          class: "bottom-bar"
        }), [
          createElementVNode("view", utsMapOf({
            class: "select-all",
            onClick: _ctx.toggleSelectAll
          }), [
            createVNode(_component_checkbox, utsMapOf({
              checked: _ctx.cartData.allSelected,
              color: "#0066CC",
              onClick: withModifiers(_ctx.toggleSelectAll, ["stop"])
            }), null, 8 /* PROPS */, ["checked", "onClick"]),
            createElementVNode("text", utsMapOf({ class: "select-all-text" }), "全选")
          ], 8 /* PROPS */, ["onClick"]),
          createElementVNode("view", utsMapOf({ class: "action-area" }), [
            isTrue(!_ctx.isEditing)
              ? createElementVNode(Fragment, utsMapOf({ key: 0 }), [
                  createElementVNode("view", utsMapOf({ class: "price-info" }), [
                    createElementVNode("view", utsMapOf({ class: "total-row" }), [
                      createElementVNode("text", utsMapOf({ class: "total-label" }), "合计:"),
                      createElementVNode("text", utsMapOf({ class: "total-price" }), "¥" + toDisplayString(_ctx.cartData.selectedAmount.toFixed(2)), 1 /* TEXT */)
                    ])
                  ]),
                  createElementVNode("button", utsMapOf({
                    class: "submit-btn submit-btn-checkout",
                    disabled: _ctx.cartData.selectedQuantity === 0,
                    onClick: _ctx.goCheckout
                  }), " 结算(" + toDisplayString(_ctx.cartData.selectedQuantity) + ") ", 9 /* TEXT, PROPS */, ["disabled", "onClick"])
                ], 64 /* STABLE_FRAGMENT */)
              : createElementVNode("button", utsMapOf({
                  key: 1,
                  class: "submit-btn submit-btn-delete",
                  disabled: _ctx.cartData.selectedQuantity === 0,
                  onClick: _ctx.removeSelectedItems
                }), " 删除(" + toDisplayString(_ctx.cartData.selectedQuantity) + ") ", 9 /* TEXT, PROPS */, ["disabled", "onClick"])
          ])
        ])
      : createCommentVNode("v-if", true),
    createVNode(_component_BottomNav)
  ])
}
const GenPagesCartIndexStyles = [utsMapOf([["cart-page", padStyleMapOf(utsMapOf([["backgroundColor", "#f7f8fa"], ["flex", 1], ["display", "flex"], ["flexDirection", "column"]]))], ["header", padStyleMapOf(utsMapOf([["backgroundColor", "#ffffff"], ["paddingTop", CSS_VAR_STATUS_BAR_HEIGHT], ["paddingRight", 16], ["paddingBottom", 12], ["paddingLeft", 16], ["display", "flex"], ["flexDirection", "row"], ["justifyContent", "space-between"], ["alignItems", "center"], ["zIndex", 100]]))], ["header-left", utsMapOf([[".header ", utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"]])]])], ["page-title", utsMapOf([[".header .header-left ", utsMapOf([["fontSize", 18], ["fontWeight", "700"], ["color", "#333333"]])]])], ["cart-count", utsMapOf([[".header .header-left ", utsMapOf([["fontSize", 14], ["color", "#999999"], ["marginLeft", 4]])]])], ["edit-btn", utsMapOf([[".header ", utsMapOf([["fontSize", 14], ["color", "#0066CC"]])]])], ["loading-state", padStyleMapOf(utsMapOf([["flex", 1], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["loading-text", padStyleMapOf(utsMapOf([["fontSize", 14], ["color", "#999999"]]))], ["empty-cart", padStyleMapOf(utsMapOf([["flex", 1], ["display", "flex"], ["flexDirection", "column"], ["alignItems", "center"], ["justifyContent", "center"], ["paddingBottom", 100]]))], ["empty-icon", padStyleMapOf(utsMapOf([["fontSize", 60], ["marginBottom", 20]]))], ["empty-title", padStyleMapOf(utsMapOf([["fontSize", 16], ["color", "#333333"], ["marginBottom", 8]]))], ["empty-desc", padStyleMapOf(utsMapOf([["fontSize", 13], ["color", "#999999"], ["marginBottom", 24]]))], ["go-shopping-btn", padStyleMapOf(utsMapOf([["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#0066CC"], ["borderRightColor", "#0066CC"], ["borderBottomColor", "#0066CC"], ["borderLeftColor", "#0066CC"], ["color", "#0066CC"], ["backgroundColor", "rgba(0,0,0,0)"], ["borderTopLeftRadius", 20], ["borderTopRightRadius", 20], ["borderBottomRightRadius", 20], ["borderBottomLeftRadius", 20], ["paddingTop", 0], ["paddingRight", 30], ["paddingBottom", 0], ["paddingLeft", 30], ["fontSize", 14], ["height", 36], ["lineHeight", "36px"]]))], ["cart-scroll", padStyleMapOf(utsMapOf([["flex", 1]]))], ["cart-list", padStyleMapOf(utsMapOf([["paddingTop", 12], ["paddingRight", 16], ["paddingBottom", 12], ["paddingLeft", 16]]))], ["shop-group", padStyleMapOf(utsMapOf([["backgroundColor", "#ffffff"], ["borderTopLeftRadius", 16], ["borderTopRightRadius", 16], ["borderBottomRightRadius", 16], ["borderBottomLeftRadius", 16], ["paddingTop", 0], ["paddingRight", 16], ["paddingBottom", 0], ["paddingLeft", 16], ["marginBottom", 12], ["boxShadow", "0 4px 16px rgba(0, 0, 0, 0.04)"]]))], ["shop-header", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["paddingTop", 12], ["paddingRight", 0], ["paddingBottom", 12], ["paddingLeft", 0], ["borderBottomWidth", 1], ["borderBottomStyle", "solid"], ["borderBottomColor", "#f5f5f5"]]))], ["shop-checkbox", padStyleMapOf(utsMapOf([["marginRight", 8]]))], ["shop-icon", padStyleMapOf(utsMapOf([["fontSize", 16], ["marginRight", 6]]))], ["shop-name", padStyleMapOf(utsMapOf([["fontSize", 14], ["fontWeight", "700"], ["color", "#333333"]]))], ["cart-item", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["paddingTop", 16], ["paddingRight", 0], ["paddingBottom", 16], ["paddingLeft", 0], ["borderBottomWidth", 1], ["borderBottomStyle", "solid"], ["borderBottomColor", "#f5f5f5"]]))], ["item-checkbox-wrapper", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["marginRight", 8]]))], ["item-content", padStyleMapOf(utsMapOf([["flex", 1], ["display", "flex"], ["flexDirection", "row"]]))], ["item-image", padStyleMapOf(utsMapOf([["width", 90], ["height", 90], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#f5f5f5"], ["marginRight", 12]]))], ["item-info", padStyleMapOf(utsMapOf([["flex", 1], ["display", "flex"], ["flexDirection", "column"], ["justifyContent", "space-between"]]))], ["info-top", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "column"]]))], ["item-name", padStyleMapOf(utsMapOf([["fontSize", 14], ["color", "#333333"], ["lineHeight", 1.4], ["marginBottom", 6]]))], ["item-spec-box", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["backgroundColor", "#f5f5f5"], ["paddingTop", 2], ["paddingRight", 6], ["paddingBottom", 2], ["paddingLeft", 6], ["borderTopLeftRadius", 4], ["borderTopRightRadius", 4], ["borderBottomRightRadius", 4], ["borderBottomLeftRadius", 4], ["alignSelf", "flex-start"], ["marginBottom", 6]]))], ["item-spec", padStyleMapOf(utsMapOf([["fontSize", 11], ["color", "#666666"]]))], ["invalid-tag", padStyleMapOf(utsMapOf([["backgroundColor", "#ffebee"], ["paddingTop", 2], ["paddingRight", 6], ["paddingBottom", 2], ["paddingLeft", 6], ["borderTopLeftRadius", 4], ["borderTopRightRadius", 4], ["borderBottomRightRadius", 4], ["borderBottomLeftRadius", 4], ["alignSelf", "flex-start"]]))], ["invalid-text", padStyleMapOf(utsMapOf([["fontSize", 11], ["color", "#ff4d4f"]]))], ["info-bottom", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["justifyContent", "space-between"], ["alignItems", "flex-end"]]))], ["price-box", padStyleMapOf(utsMapOf([["color", "#ff4d4f"], ["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"]]))], ["currency", padStyleMapOf(utsMapOf([["fontSize", 12], ["fontWeight", "700"]]))], ["price", padStyleMapOf(utsMapOf([["fontSize", 16], ["fontWeight", "700"]]))], ["quantity-control", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#eeeeee"], ["borderRightColor", "#eeeeee"], ["borderBottomColor", "#eeeeee"], ["borderLeftColor", "#eeeeee"], ["borderTopLeftRadius", 4], ["borderTopRightRadius", 4], ["borderBottomRightRadius", 4], ["borderBottomLeftRadius", 4]]))], ["qty-btn", padStyleMapOf(utsMapOf([["width", 28], ["height", 28], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"], ["backgroundColor", "#ffffff"]]))], ["qty-btn-minus", padStyleMapOf(utsMapOf([["borderRightWidth", 1], ["borderRightStyle", "solid"], ["borderRightColor", "#eeeeee"]]))], ["qty-btn-plus", padStyleMapOf(utsMapOf([["borderLeftWidth", 1], ["borderLeftStyle", "solid"], ["borderLeftColor", "#eeeeee"]]))], ["qty-btn-disabled", padStyleMapOf(utsMapOf([["backgroundColor", "#f5f5f5"]]))], ["qty-btn-text", padStyleMapOf(utsMapOf([["fontSize", 16], ["color", "#333333"]]))], ["qty-input", padStyleMapOf(utsMapOf([["width", 36], ["height", 28], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["qty-text", padStyleMapOf(utsMapOf([["fontSize", 14], ["color", "#333333"]]))], ["bottom-bar", padStyleMapOf(utsMapOf([["position", "fixed"], ["bottom", "120rpx"], ["left", 0], ["right", 0], ["backgroundColor", "#ffffff"], ["height", 50], ["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "space-between"], ["paddingTop", 0], ["paddingRight", 16], ["paddingBottom", 0], ["paddingLeft", 16], ["boxShadow", "0 -1px 8px rgba(0, 0, 0, 0.05)"], ["zIndex", 99]]))], ["select-all", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"]]))], ["select-all-text", padStyleMapOf(utsMapOf([["fontSize", 14], ["color", "#666666"], ["marginLeft", 8]]))], ["action-area", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"]]))], ["price-info", padStyleMapOf(utsMapOf([["textAlign", "right"], ["marginRight", 12]]))], ["total-row", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "flex-end"]]))], ["total-label", padStyleMapOf(utsMapOf([["fontSize", 14], ["color", "#333333"]]))], ["total-price", padStyleMapOf(utsMapOf([["fontSize", 18], ["fontWeight", "700"], ["color", "#ff4d4f"], ["marginLeft", 4]]))], ["submit-btn", padStyleMapOf(utsMapOf([["borderTopLeftRadius", 20], ["borderTopRightRadius", 20], ["borderBottomRightRadius", 20], ["borderBottomLeftRadius", 20], ["fontSize", 14], ["color", "#ffffff"], ["paddingTop", 0], ["paddingRight", 24], ["paddingBottom", 0], ["paddingLeft", 24], ["height", 36], ["lineHeight", "36px"], ["borderTopWidth", "medium"], ["borderRightWidth", "medium"], ["borderBottomWidth", "medium"], ["borderLeftWidth", "medium"], ["borderTopStyle", "none"], ["borderRightStyle", "none"], ["borderBottomStyle", "none"], ["borderLeftStyle", "none"], ["borderTopColor", "#000000"], ["borderRightColor", "#000000"], ["borderBottomColor", "#000000"], ["borderLeftColor", "#000000"]]))], ["submit-btn-checkout", padStyleMapOf(utsMapOf([["backgroundColor", "#0066CC"]]))], ["submit-btn-delete", padStyleMapOf(utsMapOf([["backgroundColor", "#ffffff"], ["color", "#ff4d4f"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#ff4d4f"], ["borderRightColor", "#ff4d4f"], ["borderBottomColor", "#ff4d4f"], ["borderLeftColor", "#ff4d4f"]]))]])]
