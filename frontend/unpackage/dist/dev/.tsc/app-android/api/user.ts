/**
 * 用户相关 API 接口
 */

import { post, get, put, ResponseDataType, API_BASE_URL } from '@/utils/request'

/**
 * 用户登录
 */
export function login(username : string, password : string) : Promise<ResponseDataType> {
	return post('/users/login', {
		username: username,
		password: password
	})
}

/**
 * 用户注册
 */
export function register(username : string, email : string, phone : string, password : string, nickname : string, confirmPassword : string) : Promise<ResponseDataType> {
	return post('/users/register', {
		username: username,
		password: password,
		email: email,
		phone: phone,
		nickname: nickname,
		confirmPassword: confirmPassword
	})
}

/**
 * 用户登出
 */
export function logout() : Promise<ResponseDataType> {
	return post('/users/logout', null)
}

/**
 * 获取用户信息
 */
export function getUserProfile() : Promise<ResponseDataType> {
	return get('/users/profile')
}

/**
 * 更新用户信息
 */
export function updateUserProfile(data : any) : Promise<ResponseDataType> {
	return put('/users/profile', data)
}

/**
 * 修改密码
 */
export function changePassword(oldPassword : string, newPassword : string, confirmPassword : string) : Promise<ResponseDataType> {
	return put('/users/password', {
		oldPassword: oldPassword,
		newPassword: newPassword,
		confirmPassword: confirmPassword
	})
}

/**
 * 上传头像
 */
export function uploadAvatar(filePath : string) : Promise<UTSJSONObject> {
	return new Promise((resolve, reject) => {
		const token = uni.getStorageSync('auth_token') as string | null

		let header : UTSJSONObject
		if (token != null && token !== '') {
			header = {
				'Authorization': `Bearer ${token}`
			} as UTSJSONObject
		} else {
			header = {} as UTSJSONObject
		}

		uni.uploadFile({
			url: `${API_BASE_URL}/users/avatar/upload`,
			filePath: filePath,
			name: 'file',
			header: header,
			success: function(res : UploadFileSuccess) {
				__f__('log','at api/user.ts:85','头像上传响应状态码:', res.statusCode)
				const resData = res.data
				if (resData != null && resData != '') {
					try {
						const parsed = JSON.parse(resData) as UTSJSONObject
						__f__('log','at api/user.ts:90','头像上传响应数据:', resData)
						const code = (parsed.getNumber('code') ?? 0).toInt()
						if (code == 200) {
							resolve(parsed)
						} else {
							const msg = parsed.getString('message') ?? '上传失败'
							reject(new Error(msg))
						}
					} catch (e) {
						__f__('error','at api/user.ts:99','解析头像上传响应失败:', e)
						reject(new Error('解析响应失败'))
					}
				} else {
					reject(new Error('响应数据为空'))
				}
			},
			fail: function(err : UploadFileFail) {
				__f__('error','at api/user.ts:107','头像上传 uni.uploadFile 失败:', err.errMsg)
				reject(new Error('网络请求失败，请检查网络连接'))
			}
		})
	})
}

/**
 * 获取用户收藏列表
 */
export function getUserCollections(pageNum : number, pageSize : number) : Promise<ResponseDataType> {
	return get(`/users/collections?pageNum=${pageNum}&pageSize=${pageSize}&sortBy=time&sortOrder=desc`)
}
