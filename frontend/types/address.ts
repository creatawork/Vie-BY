/**
 * 收货地址相关类型定义
 * @module types/address
 */

/**
 * 收货地址信息
 */
export type Address = {
	id: number
	userId: number
	receiverName: string
	receiverPhone: string
	province: string
	city: string
	district: string
	detailAddress: string
	fullAddress: string
	isDefault: number
	createTime: string
	updateTime: string
}

/**
 * 添加/更新地址请求参数
 */
export type AddressForm = {
	receiverName: string
	receiverPhone: string
	province: string
	city: string
	district: string
	detailAddress: string
	isDefault?: number
}
