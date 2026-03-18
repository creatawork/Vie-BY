/**
 * 钱包相关 API
 */
import { get, post, type ResponseDataType } from '@/utils/request'

// 钱包信息类型
export type WalletVO = {
	id : number
	userId : number
	balance : number
	frozenAmount : number
	totalRecharge : number
	totalConsume : number
}

// 交易记录类型
export type TransactionVO = {
	id : number
	transactionNo : string
	type : string
	typeDesc : string
	amount : number
	balanceAfter : number
	relatedOrderNo : string | null
	description : string
	createTime : string
}

// 交易记录查询参数
export type TransactionQueryParams = {
	type : string | null
	startDate : string | null
	endDate : string | null
	pageNum : number
	pageSize : number
}

/**
 * 获取钱包信息
 */
export function getWallet() : Promise<ResponseDataType> {
	return get('/wallet')
}

/**
 * 用户充值
 */
export function rechargeWallet(amount : number) : Promise<ResponseDataType> {
	return post('/wallet/recharge', { amount: amount } as UTSJSONObject)
}

/**
 * 查询交易记录
 */
export function getTransactions(params : TransactionQueryParams) : Promise<ResponseDataType> {
	let url = `/wallet/transactions?pageNum=${params.pageNum}&pageSize=${params.pageSize}`
	if (params.type != null && params.type != '') {
		url += `&type=${params.type}`
	}
	if (params.startDate != null && params.startDate != '') {
		url += `&startDate=${params.startDate}`
	}
	if (params.endDate != null && params.endDate != '') {
		url += `&endDate=${params.endDate}`
	}
	return get(url)
}
