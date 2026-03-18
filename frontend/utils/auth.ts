/**
 * 认证相关工具函数
 */

/**
 * 检查用户是否已登录
 */
export function isLoggedIn() {
  const token = (uni as any).getStorageSync('auth_token')
  const userInfo = (uni as any).getStorageSync('user_info')
  return !!(token && userInfo)
}

/**
 * 获取当前用户信息
 */
export function getCurrentUser() {
  try {
    const userInfoStr = (uni as any).getStorageSync('user_info')
    return userInfoStr ? JSON.parse(userInfoStr) : null
  } catch (error) {
    console.error('解析用户信息失败:', error)
    return null
  }
}

/**
 * 获取认证token
 */
export function getAuthToken() {
  return (uni as any).getStorageSync('auth_token') || ''
}

/**
 * 清除登录信息
 */
export function clearAuth() {
  (uni as any).removeStorageSync('auth_token');
  (uni as any).removeStorageSync('user_info')
}

/**
 * 跳转到登录页
 */
export function redirectToLogin(message = '请先登录', showModal = true) {
  if (showModal) {
    (uni as any).showModal({
      title: '提示',
      content: message,
      showCancel: false,
      success: () => {
        (uni as any).reLaunch({
          url: '/pages/login/login'
        })
      }
    })
  } else {
    (uni as any).reLaunch({
      url: '/pages/login/login'
    })
  }
}

/**
 * 检查登录状态，未登录则跳转
 */
export function requireLogin(message = '请先登录后再访问此页面') {
  if (!isLoggedIn()) {
    redirectToLogin(message)
    return false
  }
  return true
}

// ==================== 卖家相关函数 ====================

/**
 * 检查当前用户是否已开通卖家
 * @returns boolean 是否已开通卖家
 */
export function isSeller(): boolean {
  try {
    const userInfoStr = (uni as any).getStorageSync('user_info')
    if (!userInfoStr) return false
    const userInfo = JSON.parse(userInfoStr)
    return userInfo.isSeller === true
  } catch (error) {
    console.error('读取卖家状态失败:', error)
    return false
  }
}

/**
 * 获取店铺名称
 * @returns string 店铺名称，未设置则返回空字符串
 */
export function getShopName(): string {
  try {
    const userInfoStr = (uni as any).getStorageSync('user_info')
    if (!userInfoStr) return ''
    const userInfo = JSON.parse(userInfoStr)
    return userInfo.shopName || ''
  } catch (error) {
    console.error('读取店铺名称失败:', error)
    return ''
  }
}

/**
 * 更新本地存储的卖家信息
 * @param sellerStatus 是否为卖家
 * @param shopName 店铺名称（可选）
 */
export function setSellerInfo(sellerStatus: boolean, shopName?: string): void {
  try {
    const userInfoStr = (uni as any).getStorageSync('user_info')
    let userInfo = userInfoStr ? JSON.parse(userInfoStr) : {}
    userInfo.isSeller = sellerStatus
    if (shopName !== undefined) {
      userInfo.shopName = shopName
    }
    (uni as any).setStorageSync('user_info', JSON.stringify(userInfo))
  } catch (error) {
    console.error('更新卖家信息失败:', error)
  }
}

/**
 * 获取当前身份视图
 * @returns 'buyer' | 'seller' 当前身份，默认为 'buyer'
 */
export function getCurrentRole(): 'buyer' | 'seller' {
  try {
    const role = (uni as any).getStorageSync('current_role')
    if (role === 'seller') return 'seller'
    return 'buyer'
  } catch (error) {
    console.error('读取当前身份失败:', error)
    return 'buyer'
  }
}

/**
 * 设置当前身份视图
 * @param role 身份类型 'buyer' | 'seller'
 */
export function setCurrentRole(role: 'buyer' | 'seller'): void {
  try {
    (uni as any).setStorageSync('current_role', role)
  } catch (error) {
    console.error('设置当前身份失败:', error)
  }
}
