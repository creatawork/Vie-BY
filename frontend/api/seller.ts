import { get, post, put, del, type ResponseDataType, API_BASE_URL } from '@/utils/request'

// ==================== 类型定义 ====================

export type ProductQueryParams = {
	pageNum : number
	pageSize : number
	status : number | null
	keyword : string | null
	categoryId : number | null
}

export type ProductImage = {
	imageUrl : string
	sortOrder : number
}

export type ProductSku = {
	skuName : string
	skuCode : string
	price : number
	stock : number
	specInfo : string
}

export type ProductDTO = {
	categoryId : number
	productName : string
	productCode : string
	mainImage : string
	description : string
	detail : string
	originalPrice : number
	currentPrice : number
	stock : number
	isRecommended : number
	isNew : number
	isHot : number
	images : ProductImage[]
	skus : ProductSku[]
}

export type SkuItem = {
	skuName : string
	price : number
	stock : number
	specInfo : string | null
}

export type ProductFormData = {
	categoryId : number
	productName : string
	mainImage : string
	description : string | null
	detail : string | null
	originalPrice : number | null
	currentPrice : number
	isRecommended : number | null
	isNew : number | null
	isHot : number | null
	images : ProductImage[] | null
	skuList : SkuItem[]
}

export type InventoryQueryParams = {
	pageNum : number
	pageSize : number
	productId : number | null
	lowStock : boolean | null
}

export type AdjustData = {
	adjustType : string
	quantity : number
	remark : string | null
}

export type StatisticsParams = {
	startDate : string | null
	endDate : string | null
	timeUnit : string | null
}

export type RankingParams = {
	limit : number
	orderBy : string | null
}

export type SellerTransactionParams = {
	pageNum : number
	pageSize : number
	type : string | null
	startDate : string | null
	endDate : string | null
}

// ==================== 商品管理接口 ====================

export const UPLOAD_IMAGE_URL = API_BASE_URL + '/seller/upload/image'

export function createProduct(data : ProductFormData) : Promise<ResponseDataType> {
	return post('/seller/products', data)
}

export function updateProduct(productId : number, data : ProductFormData) : Promise<ResponseDataType> {
	return put(`/seller/products/${productId}`, data)
}

export function getSellerProductDetail(productId : number) : Promise<ResponseDataType> {
	return get(`/seller/products/${productId}`)
}

export function uploadProductImage(filePath : string) : Promise<ResponseDataType> {
	return new Promise<ResponseDataType>((resolve, reject) => {
		const token = uni.getStorageSync('auth_token') as string
		uni.uploadFile({
			url: API_BASE_URL + '/seller/upload/image',
			filePath: filePath,
			name: 'file',
			header: {
				'Authorization': 'Bearer ' + token
			} as UTSJSONObject,
			success: (uploadRes) => {
				const data = JSON.parse(uploadRes.data as string) as UTSJSONObject
				const code = (data.getNumber('code') ?? 0).toInt()
				const message = data.getString('message') ?? ''
				resolve({ code: code, message: message, data: data.get('data') } as ResponseDataType)
			},
			fail: (err) => {
				reject(new Error(err.errMsg))
			}
		})
	})
}

export function updateProductStatus(productId : number, status : number) : Promise<ResponseDataType> {
	return put(`/seller/products/${productId}/status`, { status } as UTSJSONObject)
}

export function deleteProduct(productId : number) : Promise<ResponseDataType> {
	return del(`/seller/products/${productId}`)
}

export function getSellerProducts(params : ProductQueryParams) : Promise<ResponseDataType> {
	let url = `/seller/products?pageNum=${params.pageNum}&pageSize=${params.pageSize}`
	if (params.status != null) url += `&status=${params.status}`
	if (params.keyword != null) {
		const kw = params.keyword as string
		url += `&keyword=${encodeURIComponent(kw)}`
	}
	if (params.categoryId != null) url += `&categoryId=${params.categoryId}`
	return get(url)
}

export function getSellerProduct(productId : number) : Promise<ResponseDataType> {
	return get(`/seller/products/${productId}`)
}

// ==================== 库存管理接口 ====================

export function getInventoryList(params : InventoryQueryParams) : Promise<ResponseDataType> {
	let url = `/seller/inventory?pageNum=${params.pageNum}&pageSize=${params.pageSize}`
	if (params.productId != null) url += `&productId=${params.productId}`
	if (params.lowStock == true) url += `&lowStock=true`
	return get(url)
}

export function adjustInventory(skuId : number, data : AdjustData) : Promise<ResponseDataType> {
	return put(`/seller/inventory/${skuId}`, {
		adjustType: data.adjustType,
		quantity: data.quantity,
		remark: data.remark
	} as UTSJSONObject)
}

// ==================== 销售统计接口 ====================

export function getSalesStatistics(params : StatisticsParams) : Promise<ResponseDataType> {
	let url = `/seller/statistics/sales`
	const query : string[] = []
	if (params.startDate != null) query.push(`startDate=${params.startDate}`)
	if (params.endDate != null) query.push(`endDate=${params.endDate}`)
	if (params.timeUnit != null) query.push(`timeUnit=${params.timeUnit}`)
	if (query.length > 0) url += '?' + query.join('&')
	return get(url)
}

export function getProductRanking(params : RankingParams) : Promise<ResponseDataType> {
	let url = `/seller/statistics/products?limit=${params.limit}`
	if (params.orderBy != null) url += `&orderBy=${params.orderBy}`
	return get(url)
}

export function getRevenueStatistics() : Promise<ResponseDataType> {
	return get('/seller/statistics/revenue')
}

export function getOrderOverview() : Promise<ResponseDataType> {
	return get('/seller/statistics/order-overview')
}

export function getOrderStatusDistribution(params : StatisticsParams) : Promise<ResponseDataType> {
	let url = `/seller/statistics/orders/status-distribution`
	const query : string[] = []
	if (params.startDate != null) query.push(`startDate=${params.startDate}`)
	if (params.endDate != null) query.push(`endDate=${params.endDate}`)
	if (params.timeUnit != null) query.push(`timeUnit=${params.timeUnit}`)
	if (query.length > 0) url += '?' + query.join('&')
	return get(url)
}

export function getSkuRanking(params : RankingParams) : Promise<ResponseDataType> {
	let url = `/seller/statistics/skus/rank?limit=${params.limit}`
	if (params.orderBy != null) url += `&orderBy=${params.orderBy}`
	return get(url)
}

export function getInventoryHealth() : Promise<ResponseDataType> {
	return get('/seller/statistics/inventory/health')
}

// ==================== 订单管理接口 ====================

export function getSellerOrders(pageNum : number, pageSize : number, status : number | null = null, orderNo : string | null = null, startDate : string | null = null, endDate : string | null = null) : Promise<ResponseDataType> {
	let url = `/seller/orders?pageNum=${pageNum}&pageSize=${pageSize}`
	if (status != null) url += `&status=${status}`
	if (orderNo != null) url += `&orderNo=${orderNo}`
	if (startDate != null) url += `&startDate=${startDate}`
	if (endDate != null) url += `&endDate=${endDate}`
	return get(url)
}

export function getSellerOrder(orderId : number) : Promise<ResponseDataType> {
	return get(`/seller/orders/${orderId}`)
}

export function getSellerOrderCount() : Promise<ResponseDataType> {
	return get('/seller/orders/count')
}

// ==================== 商家入驻接口 ====================

export function getSellerStatus() : Promise<ResponseDataType> {
	return get('/users/seller-status')
}

export function becomeSeller(shopName : string | null) : Promise<ResponseDataType> {
	return post('/users/become-seller', { shopName } as UTSJSONObject)
}

export function getSellerAccount() : Promise<ResponseDataType> {
	return get('/seller/account')
}

export function withdrawBalance(amount : number) : Promise<ResponseDataType> {
	return post('/seller/account/withdraw', { amount } as UTSJSONObject)
}

export function getSellerTransactions(params : SellerTransactionParams) : Promise<ResponseDataType> {
	let url = `/seller/account/transactions?pageNum=${params.pageNum}&pageSize=${params.pageSize}`
	if (params.type != null) url += `&type=${params.type}`
	if (params.startDate != null) url += `&startDate=${params.startDate}`
	if (params.endDate != null) url += `&endDate=${params.endDate}`
	return get(url)
}

// ==================== 评价管理接口 ====================

/**
 * 商家回复评价
 */
export function replyReview(reviewId : number, replyContent : string) : Promise<ResponseDataType> {
	return put(`/seller/reviews/${reviewId}/reply?replyContent=${encodeURIComponent(replyContent)}`, null)
}

/**
 * 获取商家评价列表（productId 必填）
 */
export function getSellerReviews(productId : number, pageNum : number = 1, pageSize : number = 10, rating : number | null = null) : Promise<ResponseDataType> {
	let url = `/seller/reviews?productId=${productId}&pageNum=${pageNum}&pageSize=${pageSize}`
	if (rating != null) {
		url += `&rating=${rating}`
	}
	return get(url)
}
