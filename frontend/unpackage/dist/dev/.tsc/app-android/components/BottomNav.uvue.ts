
import { getCartCount } from '@/api/cart'
import { getOrderCount } from '@/api/order'

type NavItemType = { __$originalPosition?: UTSSourceMapPosition<"NavItemType", "components/BottomNav.uvue", 23, 6>;
  name : string
  path : string
  icon : string
  activeIcon : string
  badge : boolean
  badgeCount : number
}

const __sfc__ = defineComponent({
  name: 'BottomNav',
  data() {
    return {
      currentNav: '/pages/index/index',
      refreshKey: 0,
      navList: [
        {
          name: '首页',
          path: '/pages/index/index',
          icon: '🏠',
          activeIcon: '🏡',
          badge: false,
          badgeCount: 0
        } as NavItemType,
        {
          name: '分类',
          path: '/pages/product/list',
          icon: '📂',
          activeIcon: '📁',
          badge: false,
          badgeCount: 0
        } as NavItemType,
        {
          name: '购物车',
          path: '/pages/cart/index',
          icon: '🛒',
          activeIcon: '🛍️',
          badge: true,
          badgeCount: 0
        } as NavItemType,
        {
          name: '订单',
          path: '/pages/order/list',
          icon: '📋',
          activeIcon: '📄',
          badge: true,
          badgeCount: 0
        } as NavItemType,
        {
          name: '我的',
          path: '/pages/user/profile',
          icon: '👤',
          activeIcon: '👨',
          badge: false,
          badgeCount: 0
        } as NavItemType
      ] as NavItemType[]
    }
  },
  mounted() {
    this.updateCurrentNav()
    this.loadBadgeCounts()
    // 监听页面显示事件，确保返回时刷新状态
    uni.$on('bottomNavRefresh', () => {
      this.updateCurrentNav()
      this.loadBadgeCounts()
      this.refreshKey++
    })
  },
  unmounted() {
    uni.$off('bottomNavRefresh')
  },
  methods: {
    /**
     * 加载购物车和订单数量
     */
    loadBadgeCounts() {
      // 获取购物车数量
      getCartCount().then((res) => {
        const count = (res.data as number | null) ?? 0
        this.navList[2].badgeCount = count as number
      }).catch((err) => {
        console.error('获取购物车数量失败:', err, " at components/BottomNav.uvue:105")
      })
      
      // 获取待处理订单数量（待付款 + 待收货）
      getOrderCount().then((res) => {
        const data = res.data as UTSJSONObject
        const unpaid = (data.getNumber('unpaid') ?? 0).toInt()
        const shipped = (data.getNumber('shipped') ?? 0).toInt()
        this.navList[3].badgeCount = unpaid + shipped
      }).catch((err) => {
        console.error('获取订单数量失败:', err, " at components/BottomNav.uvue:115")
      })
    },
    /**
     * 更新当前导航路径
     */
    updateCurrentNav() {
      const pages = getCurrentPages()
      if (pages.length > 0) {
        const currentPage = pages[pages.length - 1]
        this.currentNav = '/' + currentPage.route
      }
    },
    switchNav(item : NavItemType) {
      if (item.path === this.currentNav) {
        return
      }
      
      this.currentNav = item.path
      
      uni.reLaunch({
        url: item.path,
        fail: (err) => {
          console.error('切换页面失败:', err, " at components/BottomNav.uvue:138")
          uni.navigateTo({
            url: item.path,
            fail: (err2) => {
              console.error('navigateTo 也失败:', err2, " at components/BottomNav.uvue:142")
            }
          })
        }
      })
    }
  }
})

export default __sfc__
function GenComponentsBottomNavRender(this: InstanceType<typeof __sfc__>): any | null {
const _ctx = this
const _cache = this.$.renderCache
  return createElementVNode("view", utsMapOf({ class: "bottom-nav" }), [
    createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.navList, (item, index, __index, _cached): any => {
      return createElementVNode("view", utsMapOf({
        class: normalizeClass(["nav-item", utsMapOf({ 'nav-item--active': _ctx.currentNav === item.path })]),
        key: index,
        onClick: () => {_ctx.switchNav(item)}
      }), [
        createElementVNode("text", utsMapOf({ class: "nav-icon" }), toDisplayString(_ctx.currentNav === item.path ? item.activeIcon : item.icon), 1 /* TEXT */),
        createElementVNode("text", utsMapOf({ class: "nav-text" }), toDisplayString(item.name), 1 /* TEXT */),
        isTrue(item.badge && item.badgeCount > 0)
          ? createElementVNode("view", utsMapOf({
              key: 0,
              class: "nav-badge"
            }), [
              createElementVNode("text", utsMapOf({ class: "badge-text" }), toDisplayString(item.badgeCount > 99 ? '99+' : item.badgeCount), 1 /* TEXT */)
            ])
          : createCommentVNode("v-if", true)
      ], 10 /* CLASS, PROPS */, ["onClick"])
    }), 128 /* KEYED_FRAGMENT */)
  ])
}
const GenComponentsBottomNavStyles = [utsMapOf([["bottom-nav", padStyleMapOf(utsMapOf([["position", "fixed"], ["bottom", 0], ["left", 0], ["right", 0], ["display", "flex"], ["flexDirection", "row"], ["height", "120rpx"], ["backgroundColor", "#FFFFFF"], ["borderTopWidth", "1rpx"], ["borderTopStyle", "solid"], ["borderTopColor", "#E5E5E5"], ["zIndex", 9999], ["paddingBottom", "20rpx"]]))], ["nav-item", padStyleMapOf(utsMapOf([["flex", 1], ["display", "flex"], ["flexDirection", "column"], ["alignItems", "center"], ["justifyContent", "center"], ["position", "relative"], ["paddingTop", "8rpx"], ["paddingBottom", "8rpx"], ["backgroundColor", "#FFFFFF"]]))], ["nav-icon", padStyleMapOf(utsMapOf([["fontSize", "44rpx"], ["marginBottom", "4rpx"]]))], ["nav-text", utsMapOf([["", utsMapOf([["fontSize", "22rpx"], ["color", "#999999"]])], [".nav-item--active ", utsMapOf([["color", "#0066CC"], ["fontWeight", "700"]])]])], ["nav-badge", padStyleMapOf(utsMapOf([["position", "absolute"], ["top", "4rpx"], ["right", "20rpx"], ["minWidth", "32rpx"], ["height", "32rpx"], ["backgroundColor", "#FF4D4F"], ["borderTopLeftRadius", "16rpx"], ["borderTopRightRadius", "16rpx"], ["borderBottomRightRadius", "16rpx"], ["borderBottomLeftRadius", "16rpx"], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"], ["paddingLeft", "8rpx"], ["paddingRight", "8rpx"]]))], ["badge-text", padStyleMapOf(utsMapOf([["color", "#FFFFFF"], ["fontSize", "18rpx"], ["fontWeight", "700"]]))]])]
