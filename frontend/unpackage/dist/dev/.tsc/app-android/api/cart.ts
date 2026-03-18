/**
 * 购物车相关 API
 */
import { get, post, put, del, type ResponseDataType } from '@/utils/request'

// 购物车商品项类型
export type CartItemVO = {
	cartId : number
	productId : number
	skuId : number
	productName : string
	productImage : string
	skuName : string
	skuImage : string | null
	price : number
	quantity : number
	totalPrice : number
	stock : number
	selected : boolean
	productStatus : number
	skuStatus : number
	valid : boolean
}

// 购物车数据类型
export type CartVO = {
	items : CartItemVO[]
	totalQuantity : number
	selectedQuantity : number
	selectedAmount : number
	allSelected : boolean
	validCount : number
	invalidCount : number
}

// 添加购物车参数
export type CartAddParams = {
	productId : number
	skuId : number
	quantity : number
}

// 更新数量参数
export type CartUpdateParams = {
	quantity : number
}

/**
 * 添加商品到购物车
 */
export function addToCart(data : CartAddParams) : Promise<ResponseDataType> {
	const jsonData = {
		productId: data.productId,
		skuId: data.skuId,
		quantity: data.quantity
	} as UTSJSONObject
	return post('/cart/items', jsonData)
}

/**
 * 获取购物车列表
 */
export function getCart() : Promise<ResponseDataType> {
	return get('/cart')
}

/**
 * 更新购物车商品数量
 */
export function updateCartQuantity(cartItemId : number, quantity : number) : Promise<ResponseDataType> {
	const jsonData = { quantity: quantity } as UTSJSONObject
	return put('/cart/items/' + cartItemId, jsonData)
}

/**
 * 删除购物车商品
 */
export function deleteCartItem(cartItemId : number) : Promise<ResponseDataType> {
	return del('/cart/items/' + cartItemId)
}

/**
 * 清空购物车
 */
export function clearCart() : Promise<ResponseDataType> {
	return del('/cart/clear')
}

/**
 * 更新商品选中状态
 */
export function updateCartSelected(cartItemId : number, selected : boolean) : Promise<ResponseDataType> {
	return put('/cart/items/' + cartItemId + '/select?selected=' + selected, null)
}

/**
 * 全选/取消全选
 */
export function selectAllCart(selected : boolean) : Promise<ResponseDataType> {
	return put('/cart/select-all?selected=' + selected, null)
}

/**
 * 获取购物车商品数量
 */
export function getCartCount() : Promise<ResponseDataType> {
	return get('/cart/count')
}
