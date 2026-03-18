/**
 * @description 商品相关类型定义
 * @author 前端开发团队
 * @date 2025-11-22
 */

/**
 * 商品基本信息
 */
export interface Product {
  id: number
  name: string
  description: string
  price: number
  originalPrice?: number
  image: string
  images?: string[]
  rating: number
  sales: number
  stock: number
  reviewCount: number
  category: string
  isNew: boolean
  discount: boolean
  isFresh: boolean
  specs?: ProductSpec[]
  detailImages?: string[]
  createTime?: string
  updateTime?: string
}

/**
 * 商品规格
 */
export interface ProductSpec {
  id: string
  name: string
  options: string[]
}

/**
 * 商品分类
 */
export interface ProductCategory {
  id: string
  name: string
  icon?: string
  description?: string
  productCount?: number
}

/**
 * 商品评价
 */
export interface ProductReview {
  id: number
  productId: number
  userId: number
  username: string
  userAvatar: string
  rating: number
  content: string
  images?: string[]
  helpful: number
  unhelpful: number
  date: string
  createTime?: string
}

/**
 * 商品评价统计
 */
export interface ProductReviewStats {
  totalCount: number
  averageRating: number
  ratingDistribution: {
    5: number
    4: number
    3: number
    2: number
    1: number
  }
  helpfulCount: number
  imageCount: number
}

/**
 * 商品列表查询参数
 */
export interface ProductListParams {
  page?: number
  pageSize?: number
  category?: string
  keyword?: string
  sortBy?: 'default' | 'price-asc' | 'price-desc' | 'sales' | 'rating' | 'new'
  minPrice?: number
  maxPrice?: number
  rating?: number
}

/**
 * 商品列表响应
 */
export interface ProductListResponse {
  items: Product[]
  total: number
  page: number
  pageSize: number
  hasMore: boolean
}

/**
 * 推荐商品参数
 */
export interface RecommendedProductsParams {
  type?: 'hot' | 'new' | 'personal' | 'category'
  limit?: number
  categoryId?: string
}

/**
 * 商品收藏状态
 */
export interface ProductCollectStatus {
  productId: number
  isCollected: boolean
  collectTime?: string
}

/**
 * 商品搜索结果
 */
export interface ProductSearchResult {
  keyword: string
  items: Product[]
  total: number
  suggestedKeywords?: string[]
}

/**
 * 管理员商品列表查询参数
 */
export interface AdminProductParams {
  status?: number // 状态筛选：0-待审核，1-上架，2-下架，3-审核不通过
  categoryId?: number
  keyword?: string
  pageNum: number
  pageSize: number
}

/**
 * 商品审核参数
 */
export interface ProductAuditParams {
  status: number // 审核结果：1-通过(上架)，3-驳回
  auditRemark?: string // 审核备注/驳回原因
}