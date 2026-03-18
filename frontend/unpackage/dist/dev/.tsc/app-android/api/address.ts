/**
 * 收货地址管理 API
 * @module api/address
 */

import { get, post, put, del, type ResponseDataType } from '@/utils/request'

/**
 * 地址数据接口
 */
export interface AddressData {
	receiverName: string
	receiverPhone: string
	province: string
	city: string
	district: string
	detailAddress: string
	isDefault: number
}

/**
 * 添加收货地址
 * @param data 地址信息
 */
export function addAddress(data: any) : Promise<ResponseDataType> {
	return post('/users/addresses', data)
}

/**
 * 更新收货地址
 * @param addressId 地址ID
 * @param data 地址信息
 */
export function updateAddress(addressId: number, data: any) : Promise<ResponseDataType> {
	return put(`/users/addresses/${addressId}`, data)
}

/**
 * 删除收货地址
 * @param addressId 地址ID
 */
export function deleteAddress(addressId: number) : Promise<ResponseDataType> {
	return del(`/users/addresses/${addressId}`)
}

/**
 * 获取地址列表
 */
export function getAddressList() : Promise<ResponseDataType> {
	return get('/users/addresses')
}

/**
 * 获取地址详情
 * @param addressId 地址ID
 */
export function getAddressDetail(addressId: number) : Promise<ResponseDataType> {
	return get(`/users/addresses/${addressId}`)
}

/**
 * 设置默认地址
 * @param addressId 地址ID
 */
export function setDefaultAddress(addressId: number) : Promise<ResponseDataType> {
	return put(`/users/addresses/${addressId}/default`, null)
}

/**
 * 获取默认地址
 */
export function getDefaultAddress() : Promise<ResponseDataType> {
	return get('/users/addresses/default')
}
