/**
 * 定位相关 API 接口
 */

import { post, ResponseDataType } from '@/utils/request'

/**
 * 逆地理编码：将经纬度转换为地址
 */
export function reverseGeocode(latitude : number, longitude : number) : Promise<ResponseDataType> {
	return post('/location/reverse-geocode', {
		latitude: latitude,
		longitude: longitude
	})
}
