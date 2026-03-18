/**
 * 订单相关 API
 */
import { get, post, put, del, type ResponseDataType } from '@/utils/request'

// 订单商品项类型
export type OrderItemVO = {
	id : number
	productId : number
	skuId : number
	productName : string
	skuName : string
	productImage : string
	price : number
	quantity : number
	totalPrice : number
	reviewed? : boolean
}

// 订单类型
export type OrderVO = {
	id : number
	orderNo : string
	totalAmount : number
	payAmount : number
	freightAmount : number
	status : number
	statusDesc : string
	receiverName : string
	receiverPhone : string
	receiverAddress : string
	remark : string | null
	payTime : string | null
	deliverTime : string | null
	receiveTime : string | null
	cancelTime : string | null
	cancelReason : string | null
	createTime : string
	orderItems : OrderItemVO[]
	totalQuantity : number
	reviewed? : boolean
	reviewCount? : number
}

// 订单数量统计类型
export type OrderCountVO = {
	unpaid : number
	unshipped : number
	shipped : number
	completed : number
	total : number
}

// 直接购买商品参数
export type DirectBuyItem = {
	productId : number
	skuId : number
	quantity : number
}

// 创建订单参数
export type OrderCreateParams = {
	cartItemIds : number[] | null
	directBuyItem : DirectBuyItem | null
	receiverName : string
	receiverPhone : string
	receiverAddress : string
	receiverProvince : string
	receiverCity : string
	receiverDistrict : string
	remark : string | null
}

// 订单查询参数
export type OrderQueryParams = {
	status : number | null
	pageNum : number
	pageSize : number
}

/**
 * 创建订单
 */
export function createOrder(data : OrderCreateParams) : Promise<ResponseDataType> {
	const jsonData = {
		cartItemIds: data.cartItemIds,
		directBuyItem: data.directBuyItem != null ? {
			productId: data.directBuyItem!.productId,
			skuId: data.directBuyItem!.skuId,
			quantity: data.directBuyItem!.quantity
		} as UTSJSONObject : null,
		receiverName: data.receiverName,
		receiverPhone: data.receiverPhone,
		receiverAddress: data.receiverAddress,
		receiverProvince: data.receiverProvince,
		receiverCity: data.receiverCity,
		receiverDistrict: data.receiverDistrict,
		remark: data.remark
	} as UTSJSONObject
	return post('/orders', jsonData)
}

/**
 * 分页查询订单列表
 */
export function getOrderList(params : OrderQueryParams) : Promise<ResponseDataType> {
	let url = '/orders?pageNum=' + params.pageNum + '&pageSize=' + params.pageSize
	if (params.status != null) {
		url += '&status=' + params.status
	}
	return get(url)
}

/**
 * 获取订单详情
 */
export function getOrderDetail(orderId : number) : Promise<ResponseDataType> {
	return get('/orders/' + orderId)
}

/**
 * 取消订单
 */
export function cancelOrder(orderId : number, reason : string | null) : Promise<ResponseDataType> {
	let url = '/orders/' + orderId + '/cancel'
	if (reason != null && reason !== '') {
		url += '?reason=' + encodeURIComponent(reason)
	}
	return put(url, null)
}

/**
 * 确认收货
 */
export function confirmReceive(orderId : number) : Promise<ResponseDataType> {
	return put('/orders/' + orderId + '/receive', null)
}

/**
 * 删除订单
 */
export function deleteOrder(orderId : number) : Promise<ResponseDataType> {
	return del('/orders/' + orderId)
}

/**
 * 模拟支付
 */
export function payOrder(orderId : number) : Promise<ResponseDataType> {
	return put('/orders/' + orderId + '/pay', null)
}

/**
 * 获取各状态订单数量
 */
export function getOrderCount() : Promise<ResponseDataType> {
	return get('/orders/count')
}

// 物流轨迹类型
export type LogisticsTrack = {
	time : string
	status : string
	desc : string
}

// 物流信息类型
export type LogisticsVO = {
	orderId : number
	orderNo : string
	logisticsNo : string
	logisticsCompany : string
	status : number
	statusDesc : string
	delivered : boolean
	deliveredDesc : string
	shipTime : string | null
	receiveTime : string | null
	autoReceiveTime : string | null
	tracks : LogisticsTrack[]
}

/**
 * 查询物流信息
 */
export function getOrderLogistics(orderId : number) : Promise<ResponseDataType> {
	return get('/orders/' + orderId + '/logistics')
}
