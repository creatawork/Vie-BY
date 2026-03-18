/**
 * 定位相关工具函数
 * 支持GPS定位和逆地理编码（通过后端接口）
 */

import { reverseGeocode as reverseGeocodeApi } from '@/api/location'

// 缓存配置
const LOCATION_CACHE_KEY = 'location_cache'
const LOCATION_CACHE_TIME = 5 * 60 * 1000
const CACHE_DISTANCE_THRESHOLD = 100

// 系统定位返回的地址信息（字段根据 uni.getLocation geocode 结果）
type SystemAddressInfo = {
	address? : string
	formattedAddress? : string
	province? : string
	city? : string
	district? : string
	street? : string
	streetNum? : string
	poiName? : string
	name? : string
}

// 简单坐标类型
export type CoordinatesType = {
	latitude : number
	longitude : number
	systemAddress : SystemAddressInfo | null
}

// 地址信息类型
export type LocationInfoType = {
	latitude : number
	longitude : number
	address : string
	province : string
	city : string
	district : string
	street : string
	streetNumber : string
	poiName : string
	formattedAddress : string
	timestamp : number
}

// 缓存数据类型
type LocationCacheType = {
	location : LocationInfoType
	timestamp : number
}

/**
 * 获取GPS定位
 */
export function getCurrentLocation() : Promise<CoordinatesType> {
	return new Promise((resolve, reject) => {
		uni.getLocation({
			type: 'gcj02',
			geocode: true,
			success: function(res : GetLocationSuccess) {
				const coords : CoordinatesType = {
					latitude: res.latitude,
					longitude: res.longitude,
					systemAddress: null
				}
				resolve(coords)
			},
			fail: function(err : GetLocationFail) {
				__f__('error','at utils/location.ts:71','获取定位失败:', err.errMsg)
				reject(err)
			}
		})
	})
}

/**
 * 计算两点间距离（米）
 */
function getDistance(lat1 : number, lng1 : number, lat2 : number, lng2 : number) : number {
	const R = 6371000
	const dLat = (lat2 - lat1) * Math.PI / 180
	const dLng = (lng2 - lng1) * Math.PI / 180
	const a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
		Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) *
		Math.sin(dLng / 2) * Math.sin(dLng / 2)
	const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
	return R * c
}

/**
 * 检查两个位置是否相近
 */
function isLocationNearby(location : LocationInfoType, lat : number, lng : number) : boolean {
	const distance = getDistance(location.latitude, location.longitude, lat, lng)
	return distance < CACHE_DISTANCE_THRESHOLD
}

/**
 * 获取缓存的定位信息
 */
function getCachedLocation() : LocationCacheType | null {
	const cacheStr = uni.getStorageSync(LOCATION_CACHE_KEY) as string
	if (cacheStr == null || cacheStr == '') {
		return null
	}

	try {
		// 在 uni-app x 中 JSON.parse 返回 UTSJSONObject，不能直接强转为自定义类型
		const cacheJson = JSON.parse(cacheStr) as UTSJSONObject

		const tsNumber = cacheJson.getNumber('timestamp')
		const locationAny = cacheJson.get('location')
		if (tsNumber == null || locationAny == null) {
			return null
		}

		const locationJson = locationAny as UTSJSONObject

		const timestamp = tsNumber.toLong()

		// 检查缓存是否过期
		if (Date.now() - timestamp > LOCATION_CACHE_TIME) {
			return null
		}

		const latitudeNum = locationJson.getNumber('latitude')
		const longitudeNum = locationJson.getNumber('longitude')

		const location : LocationInfoType = {
			latitude: (latitudeNum ?? 0).toDouble(),
			longitude: (longitudeNum ?? 0).toDouble(),
			address: locationJson.getString('address') ?? '',
			province: locationJson.getString('province') ?? '',
			city: locationJson.getString('city') ?? '',
			district: locationJson.getString('district') ?? '',
			street: locationJson.getString('street') ?? '',
			streetNumber: locationJson.getString('streetNumber') ?? '',
			poiName: locationJson.getString('poiName') ?? '',
			formattedAddress: locationJson.getString('formattedAddress') ?? '',
			timestamp: timestamp
		}

		const cache : LocationCacheType = {
			location: location,
			timestamp: timestamp
		}

		return cache
	} catch (e) {
		__f__('error','at utils/location.ts:152','解析定位缓存失败:', e.toString())
		return null
	}
}

/**
 * 缓存定位信息
 */
function cacheLocation(location : LocationInfoType) : void {
	const cache : LocationCacheType = {
		location: location,
		timestamp: Date.now()
	}
	uni.setStorageSync(LOCATION_CACHE_KEY, JSON.stringify(cache))
}

/**
 * 格式化地址显示
 */
function formatAddress(district : string, street : string, poiName : string) : string {
	if (poiName != '') {
		if (poiName.indexOf('小区') >= 0 || poiName.indexOf('社区') >= 0 || poiName.indexOf('花园') >= 0) {
			return poiName
		}
		if (street != '') {
			return `${street} ${poiName}`
		}
		return poiName
	}

	if (street != '') {
		if (district != '') {
			return `${district} ${street}`
		}
		return street
	}

	if (district != '') {
		return district
	}

	return '当前位置'
}

/**
 * 逆地理编码：将经纬度转换为地址
 */
export function reverseGeocode(latitude : number, longitude : number) : Promise<LocationInfoType> {
	return new Promise((resolve, reject) => {
		// 检查缓存
		const cached = getCachedLocation()
		if (cached != null && isLocationNearby(cached.location, latitude, longitude)) {
			__f__('log','at utils/location.ts:204','使用缓存的地址信息')
			resolve(cached.location)
			return
		}

		// 调用后端接口
		reverseGeocodeApi(latitude, longitude).then((response) => {
			if (response.code == 200 && response.data != null) {
				const data = response.data as UTSJSONObject

				const address = data.getString('address') ?? ''
				const province = data.getString('province') ?? ''
				const city = data.getString('city') ?? ''
				const district = data.getString('district') ?? ''
				const street = data.getString('street') ?? ''
				const streetNumber = data.getString('streetNumber') ?? ''
				const poiName = data.getString('poiName') ?? ''
				const formattedAddr = data.getString('formattedAddress') ?? formatAddress(district, street, poiName)

				const locationInfo : LocationInfoType = {
					latitude: (data.getNumber('latitude') ?? latitude).toDouble(),
					longitude: (data.getNumber('longitude') ?? longitude).toDouble(),
					address: address,
					province: province,
					city: city,
					district: district,
					street: street,
					streetNumber: streetNumber,
					poiName: poiName,
					formattedAddress: formattedAddr,
					timestamp: Date.now().toLong()
				}

				// 缓存地址信息
				cacheLocation(locationInfo)

				resolve(locationInfo)
			} else {
				const msg = response.message != '' ? response.message : '地址解析失败'
				reject(new Error(msg))
			}
		}).catch((error) => {
			__f__('error','at utils/location.ts:246','逆地理编码失败:', error)
			reject(new Error('网络请求失败'))
		})
	})
}

/**
 * 获取完整定位信息（GPS + 地址）
 */
export function getFullLocationInfo() : Promise<LocationInfoType> {
	return new Promise((resolve, reject) => {
		getCurrentLocation().then((coords) => {
			reverseGeocode(coords.latitude, coords.longitude).then((locationInfo) => {
				resolve(locationInfo)
			}).catch((error) => {
				__f__('warn','at utils/location.ts:261','逆地理编码失败，尝试使用系统返回的地址信息:', error.toString())
				const fallbackLocation = buildLocationInfoFromSystem(coords)
				if (fallbackLocation != null) {
					cacheLocation(fallbackLocation)
					resolve(fallbackLocation)
					return
				}
				reject(error)
			})
		}).catch((error) => {
			__f__('error','at utils/location.ts:271','获取定位信息失败:', error)
			reject(error)
		})
	})
}

/**
 * 清除定位缓存
 */
export function clearLocationCache() : void {
	uni.removeStorageSync(LOCATION_CACHE_KEY)
}

/**
 * 根据系统 geocode 结果构造地址信息，作为后备方案
 */
function buildLocationInfoFromSystem(coords : CoordinatesType) : LocationInfoType | null {
	const addressInfo = coords.systemAddress
	if (addressInfo == null) {
		return null
	}

	const district = addressInfo.district ?? ''
	const street = addressInfo.street ?? ''
	const poiName = addressInfo.poiName ?? addressInfo.name ?? ''
	const formattedAddr = addressInfo.formattedAddress ?? addressInfo.address ?? formatAddress(district, street, poiName)

	const fallbackLocation : LocationInfoType = {
		latitude: coords.latitude,
		longitude: coords.longitude,
		address: addressInfo.address ?? formattedAddr,
		province: addressInfo.province ?? '',
		city: addressInfo.city ?? '',
		district: district,
		street: street,
		streetNumber: addressInfo.streetNum ?? '',
		poiName: poiName,
		formattedAddress: formattedAddr,
		timestamp: Date.now().toLong()
	}

	return fallbackLocation
}
