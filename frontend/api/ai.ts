/**
 * AI 智能问答接口
 */

import { API_BASE_URL } from '@/utils/request'

export type AiStreamHandlers = {
	onChunk: (payload : any) => void
	onError: (error : any) => void
	onComplete: () => void
}



/**
 * AI 智能问答（流式 SSE）
 */
export function chatWithAiStream(
	question : string,
	sessionId : string | null,
	handlers : AiStreamHandlers
) : any {
	const token = uni.getStorageSync('auth_token') as string | null
	let headers : UTSJSONObject
	if (token != null && token !== '') {
		headers = {
			'Content-Type': 'application/json',
			'Accept': 'text/event-stream',
			'Authorization': `Bearer ${token}`
		} as UTSJSONObject
	} else {
		headers = {
			'Content-Type': 'application/json',
			'Accept': 'text/event-stream'
		} as UTSJSONObject
	}

	const logTag = '[ai.stream]'
	console.log(`${logTag} start`, {
		question: question,
		sessionId: sessionId
	})
	// #ifndef APP-ANDROID || APP-IOS
	console.log(`${logTag} platform`, 'NON-APP (no xhr)')
	// #endif



	let result: any = {} as any
	// #ifdef APP-ANDROID || APP-IOS
	console.log(`${logTag} platform`, 'APP SSE via uni.request')
	const url = `${API_BASE_URL}/ai/chat/stream`
	const authHeader = headers.getString('Authorization')
	const payload = {
		question: question,
		sessionId: sessionId
	}
	console.log(`${logTag} request`, {
		url: url,
		headers: {
			'Content-Type': 'application/json; charset=utf-8',
			Accept: 'text/event-stream',
			Authorization: authHeader
		},
		payload: payload
	})

	let isCompleted = false

	const reqOpts: RequestOptions<string> = {
		url: url,
		method: 'POST',
		data: payload,
		header: {
			'Content-Type': 'application/json; charset=utf-8',
			Accept: 'text/event-stream',
			Authorization: authHeader
		},
		timeout: 0,
		success: function(res: RequestSuccess<string>) {
			if (res != null) {
				console.log(`${logTag} response`, res)
				const text = res.data != null ? res.data : ''
				if (text != null && text != '') {
					const normalized = text.split('\r\n').join('\n').split('\r').join('\n')
					const lines = normalized.split('\n')
					let dataBuffer = ''
					for (let i = 0; i < lines.length; i++) {
						const line = lines[i].trim()
						if (line == '') {
							if (dataBuffer != '') {
								const jsonText = dataBuffer.trim()
								if (jsonText != '' && jsonText != '[DONE]') {
									try {
										const payload = JSON.parse(jsonText) as any
										handlers.onChunk(payload)
									} catch (error) {
										handlers.onError(error)
									}
								}
								dataBuffer = ''
							}
							continue
						}
						if (line.startsWith('data:')) {
							const chunk = line.replace('data:', '').trim()
							dataBuffer = dataBuffer == '' ? chunk : `${dataBuffer}${chunk}`
						}
					}
					if (dataBuffer != '') {
						const jsonText = dataBuffer.trim()
						if (jsonText != '' && jsonText != '[DONE]') {
							try {
								const payload = JSON.parse(jsonText) as any
								handlers.onChunk(payload)
							} catch (error) {
								handlers.onError(error)
							}
						}
					}
				}
			}
			if (!isCompleted) {
				isCompleted = true
				handlers.onComplete()
			}
		},
		fail: function(err: RequestFail) {
			if (!isCompleted) {
				isCompleted = true
			}
			handlers.onError(err)
		}
	}

	const requestTask = uni.request<string>(reqOpts as RequestOptions<string>)

	// uni.request in UTS does not expose onChunkReceived reliably
	// fallback: parse full response in success callback when available

	result = requestTask as any
	// #endif
	return result
}
