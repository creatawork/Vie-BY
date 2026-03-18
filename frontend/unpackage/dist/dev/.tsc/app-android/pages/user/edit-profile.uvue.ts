
import { getUserProfile, updateUserProfile, uploadAvatar } from '@/api/user'

type ProfileForm = { __$originalPosition?: UTSSourceMapPosition<"ProfileForm", "pages/user/edit-profile.uvue", 135, 6>;
	nickname : string
	avatar : string
	gender : number
	phone : string
	email : string
}

type ProfileErrors = { __$originalPosition?: UTSSourceMapPosition<"ProfileErrors", "pages/user/edit-profile.uvue", 143, 6>;
	nickname : string
}

const __sfc__ = defineComponent({
	data() {
		return {
			form: {
				nickname: '',
				avatar: '',
				gender: 0,
				phone: '',
				email: ''
			} as ProfileForm,
			errors: {
				nickname: ''
			} as ProfileErrors,
			isLoading: false,
			isUploadingAvatar: false,
			formLoaded: false,
			defaultAvatar: '/static/logo.png',
			focusedField: ''
		}
	},
	onLoad() {
		this.loadUserInfo()
	},
	methods: {
		getAvatarUrl() : string {
			return this.form.avatar != '' ? this.form.avatar : this.defaultAvatar
		},
		handleFocus(field : string) {
			this.focusedField = field
		},
		handleBlur() {
			this.focusedField = ''
			this.validateNickname()
		},
		loadUserInfo() {
			getUserProfile().then((result) => {
				if (result.code === 200) {
					const userInfo = result.data as UTSJSONObject
					this.form.nickname = (userInfo.getString('nickname') ?? '') as string
					this.form.avatar = (userInfo.getString('avatar') ?? '') as string
					this.form.gender = (userInfo.getNumber('gender') ?? 0).toInt()
					this.form.phone = (userInfo.getString('phone') ?? '') as string
					this.form.email = (userInfo.getString('email') ?? '') as string
					this.formLoaded = true
				} else {
					const msg = result.message != '' ? result.message : '获取用户信息失败'
					uni.showToast({
						title: msg,
						icon: 'error'
					})
					setTimeout(() => {
						uni.navigateBack()
					}, 1000)
				}
			}).catch((error) => {
				console.error('获取用户信息失败:', error, " at pages/user/edit-profile.uvue:202")
				uni.showToast({
					title: '获取用户信息失败',
					icon: 'error'
				})
				setTimeout(() => {
					uni.navigateBack()
				}, 1000)
			})
		},
		validateNickname() {
			const nickname = this.form.nickname.trim()
			if (nickname == '') {
				this.errors.nickname = '昵称不能为空'
			} else if (nickname.length > 20) {
				this.errors.nickname = '昵称长度不能超过20个字符'
			} else {
				this.errors.nickname = ''
			}
		},
		handleUploadAvatar() {
			uni.chooseImage({
				count: 1,
				sizeType: ['original', 'compressed'],
				sourceType: ['album', 'camera'],
				success: (res) => {
					if (res.tempFilePaths != null && res.tempFilePaths.length > 0) {
						this.uploadAvatarFile(res.tempFilePaths[0])
					}
				},
				fail: (err : any) => {
					console.error('选择图片失败:', err, " at pages/user/edit-profile.uvue:233")
					uni.showToast({
						title: '选择图片失败',
						icon: 'error'
					})
				}
			})
		},
		uploadAvatarFile(filePath : string) {
			this.isUploadingAvatar = true
			uni.showLoading({ title: '上传中...' })
			uploadAvatar(filePath).then((result) => {
				if (result != null) {
					const code = (result.getNumber('code') ?? 0).toInt()
					if (code === 200) {
						const data = result.getString('data') ?? ''
						this.form.avatar = data
						uni.showToast({
							title: '头像上传成功',
							icon: 'success'
						})
					} else {
						const msg = result.getString('message') ?? '上传失败'
						uni.showToast({
							title: msg,
							icon: 'error'
						})
					}
				} else {
					uni.showToast({
						title: '上传失败',
						icon: 'error'
					})
				}
				this.isUploadingAvatar = false
				uni.hideLoading()
			}).catch((error) => {
				console.error('上传头像失败:', error, " at pages/user/edit-profile.uvue:270")
				uni.showToast({
					title: '上传失败，请检查网络连接',
					icon: 'error'
				})
				this.isUploadingAvatar = false
				uni.hideLoading()
			})
		},
		handleSubmit() {
			this.validateNickname()

			if (this.errors.nickname != '') {
				return
			}

			this.isLoading = true
			updateUserProfile({
				nickname: this.form.nickname,
				avatar: this.form.avatar,
				gender: this.form.gender
			}).then((result) => {
				if (result.code === 200) {
					// 更新本地存储
					const storedInfo = uni.getStorageSync('user_info')
					if (storedInfo != null && storedInfo != '') {
						const infoStr = storedInfo as string
						const info = UTSAndroid.consoleDebugError(JSON.parse(infoStr), " at pages/user/edit-profile.uvue:297") as UTSJSONObject
						const updatedInfo = { __$originalPosition: new UTSSourceMapPosition("updatedInfo", "pages/user/edit-profile.uvue", 298, 13), 
							id: info.getNumber('id') != null ? info.getNumber('id')!.toInt() : 0,
							username: info.getString('username') ?? '',
							nickname: this.form.nickname,
							avatar: this.form.avatar,
							gender: this.form.gender,
							roleCodes: info.getAny('roleCodes')
						} as UTSJSONObject
						uni.setStorageSync('user_info', JSON.stringify(updatedInfo))
					}

					uni.showToast({
						title: '保存成功',
						icon: 'success'
					})
					setTimeout(() => {
						uni.navigateBack()
					}, 1000)
				} else {
					const msg = result.message != '' ? result.message : '更新失败'
					uni.showToast({
						title: msg,
						icon: 'error'
					})
				}
				this.isLoading = false
			}).catch((error) => {
				console.error('更新个人信息失败:', error, " at pages/user/edit-profile.uvue:325")
				uni.showToast({
					title: '更新失败，请检查网络连接',
					icon: 'error'
				})
				this.isLoading = false
			})
		},
		handleBack() {
			uni.navigateBack()
		}
	}
})

export default __sfc__
function GenPagesUserEditProfileRender(this: InstanceType<typeof __sfc__>): any | null {
const _ctx = this
const _cache = this.$.renderCache
  return createElementVNode("view", utsMapOf({ class: "edit-profile-container" }), [
    createElementVNode("view", utsMapOf({ class: "header" }), [
      createElementVNode("view", utsMapOf({ class: "header-inner" }), [
        createElementVNode("view", utsMapOf({
          class: "back-btn",
          onClick: _ctx.handleBack
        }), [
          createElementVNode("text", utsMapOf({ class: "back-icon" }), "←")
        ], 8 /* PROPS */, ["onClick"]),
        createElementVNode("view", utsMapOf({ class: "header-texts" }), [
          createElementVNode("text", utsMapOf({ class: "header-title" }), "编辑个人信息"),
          createElementVNode("text", utsMapOf({ class: "header-subtitle" }), "完善资料，让服务更贴近你")
        ])
      ])
    ]),
    isTrue(_ctx.isLoading && !_ctx.formLoaded)
      ? createElementVNode("view", utsMapOf({
          key: 0,
          class: "loading-box"
        }), [
          createElementVNode("text", utsMapOf({ class: "loading-text" }), "加载中...")
        ])
      : createElementVNode("view", utsMapOf({
          key: 1,
          class: "content-sheet"
        }), [
          createElementVNode("scroll-view", utsMapOf({
            class: "content",
            "scroll-y": "true",
            "show-scrollbar": false
          }), [
            createElementVNode("view", utsMapOf({ class: "form-card" }), [
              createElementVNode("view", utsMapOf({ class: "avatar-section" }), [
                createElementVNode("view", utsMapOf({
                  class: "avatar-wrapper",
                  onClick: _ctx.handleUploadAvatar
                }), [
                  createElementVNode("image", utsMapOf({
                    src: _ctx.getAvatarUrl(),
                    class: "avatar-preview",
                    mode: "aspectFill"
                  }), null, 8 /* PROPS */, ["src"]),
                  createElementVNode("view", utsMapOf({ class: "avatar-overlay" }), [
                    createElementVNode("text", utsMapOf({ class: "camera-icon" }), "📷")
                  ])
                ], 8 /* PROPS */, ["onClick"]),
                createElementVNode("text", utsMapOf({ class: "avatar-hint" }), "点击更换头像")
              ]),
              createElementVNode("view", utsMapOf({ class: "input-group" }), [
                createElementVNode("text", utsMapOf({ class: "input-label" }), "昵称"),
                createElementVNode("view", utsMapOf({ class: "flex-column flex-1" }), [
                  createElementVNode("view", utsMapOf({
                    class: normalizeClass(["input-wrapper", utsMapOf({ 'input-wrapper-focused': _ctx.focusedField === 'nickname', 'input-wrapper-error': _ctx.errors.nickname !== '' })])
                  }), [
                    createElementVNode("input", utsMapOf({
                      modelValue: _ctx.form.nickname,
                      onInput: ($event: InputEvent) => {(_ctx.form.nickname) = $event.detail.value},
                      type: "text",
                      placeholder: "请输入昵称",
                      "placeholder-class": "placeholder-text",
                      class: "input-field",
                      onFocus: () => {_ctx.handleFocus('nickname')},
                      onBlur: _ctx.handleBlur
                    }), null, 40 /* PROPS, NEED_HYDRATION */, ["modelValue", "onInput", "onFocus", "onBlur"]),
                    isTrue(_ctx.form.nickname)
                      ? createElementVNode("text", utsMapOf({
                          key: 0,
                          class: "clear-btn",
                          onClick: () => {_ctx.form.nickname = ''}
                        }), "✕", 8 /* PROPS */, ["onClick"])
                      : createCommentVNode("v-if", true)
                  ], 2 /* CLASS */),
                  isTrue(_ctx.errors.nickname)
                    ? createElementVNode("text", utsMapOf({
                        key: 0,
                        class: "error-text"
                      }), toDisplayString(_ctx.errors.nickname), 1 /* TEXT */)
                    : createCommentVNode("v-if", true)
                ])
              ]),
              createElementVNode("view", utsMapOf({ class: "input-group" }), [
                createElementVNode("text", utsMapOf({ class: "input-label" }), "性别"),
                createElementVNode("view", utsMapOf({ class: "gender-options" }), [
                  createElementVNode("view", utsMapOf({
                    class: normalizeClass(["gender-btn", utsMapOf({ 'gender-btn-active': _ctx.form.gender === 1 })]),
                    onClick: () => {_ctx.form.gender = 1}
                  }), [
                    createElementVNode("text", utsMapOf({ class: "gender-icon" }), "♂"),
                    createElementVNode("text", utsMapOf({ class: "gender-text" }), "男")
                  ], 10 /* CLASS, PROPS */, ["onClick"]),
                  createElementVNode("view", utsMapOf({
                    class: normalizeClass(["gender-btn", utsMapOf({ 'gender-btn-active': _ctx.form.gender === 2 })]),
                    onClick: () => {_ctx.form.gender = 2}
                  }), [
                    createElementVNode("text", utsMapOf({ class: "gender-icon" }), "♀"),
                    createElementVNode("text", utsMapOf({ class: "gender-text" }), "女")
                  ], 10 /* CLASS, PROPS */, ["onClick"]),
                  createElementVNode("view", utsMapOf({
                    class: normalizeClass(["gender-btn", utsMapOf({ 'gender-btn-active': _ctx.form.gender === 0 })]),
                    onClick: () => {_ctx.form.gender = 0}
                  }), [
                    createElementVNode("text", utsMapOf({ class: "gender-icon" }), "?"),
                    createElementVNode("text", utsMapOf({ class: "gender-text" }), "保密")
                  ], 10 /* CLASS, PROPS */, ["onClick"])
                ])
              ]),
              createElementVNode("view", utsMapOf({ class: "input-group" }), [
                createElementVNode("text", utsMapOf({ class: "input-label" }), "手机号"),
                createElementVNode("view", utsMapOf({ class: "input-wrapper input-wrapper-disabled" }), [
                  createElementVNode("input", utsMapOf({
                    value: _ctx.form.phone,
                    type: "text",
                    class: "input-field",
                    disabled: ""
                  }), null, 8 /* PROPS */, ["value"]),
                  createElementVNode("text", utsMapOf({ class: "lock-icon" }), "🔒")
                ])
              ]),
              createElementVNode("view", utsMapOf({ class: "input-group" }), [
                createElementVNode("text", utsMapOf({ class: "input-label" }), "邮箱"),
                createElementVNode("view", utsMapOf({ class: "input-wrapper input-wrapper-disabled" }), [
                  createElementVNode("input", utsMapOf({
                    value: _ctx.form.email,
                    type: "text",
                    class: "input-field",
                    disabled: ""
                  }), null, 8 /* PROPS */, ["value"]),
                  createElementVNode("text", utsMapOf({ class: "lock-icon" }), "🔒")
                ])
              ])
            ]),
            createElementVNode("view", utsMapOf({ class: "action-section" }), [
              createElementVNode("button", utsMapOf({
                class: "btn btn-primary submit-btn",
                "hover-class": "btn-hover",
                loading: _ctx.isLoading,
                onClick: _ctx.handleSubmit
              }), toDisplayString(_ctx.isLoading ? '保存中...' : '保存修改'), 9 /* TEXT, PROPS */, ["loading", "onClick"])
            ])
          ])
        ])
  ])
}
const GenPagesUserEditProfileStyles = [utsMapOf([["edit-profile-container", padStyleMapOf(utsMapOf([["backgroundImage", "linear-gradient(135deg, #0066CC 0%, #1890FF 100%)"], ["backgroundColor", "rgba(0,0,0,0)"], ["position", "relative"], ["overflow", "hidden"], ["display", "flex"], ["flexDirection", "column"]]))], ["header", padStyleMapOf(utsMapOf([["paddingTop", CSS_VAR_STATUS_BAR_HEIGHT], ["paddingLeft", 24], ["paddingRight", 24], ["paddingBottom", 12], ["marginTop", 8], ["position", "relative"], ["zIndex", 10]]))], ["header-inner", padStyleMapOf(utsMapOf([["height", 44], ["display", "flex"], ["alignItems", "center"]]))], ["back-btn", padStyleMapOf(utsMapOf([["width", 40], ["height", 40], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"], ["marginLeft", -12]]))], ["back-icon", padStyleMapOf(utsMapOf([["fontSize", 24], ["color", "#ffffff"], ["lineHeight", 1]]))], ["header-title", padStyleMapOf(utsMapOf([["fontSize", 18], ["fontWeight", "700"], ["color", "#ffffff"]]))], ["header-texts", padStyleMapOf(utsMapOf([["flex", 1], ["display", "flex"], ["flexDirection", "column"], ["paddingLeft", 4]]))], ["header-subtitle", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "rgba(255,255,255,0.82)"], ["marginTop", 2]]))], ["loading-box", padStyleMapOf(utsMapOf([["flex", 1], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"], ["backgroundColor", "#F5F7FA"]]))], ["loading-text", padStyleMapOf(utsMapOf([["color", "#999999"], ["fontSize", 14]]))], ["content-sheet", padStyleMapOf(utsMapOf([["flex", 1], ["backgroundColor", "#F5F7FA"], ["borderTopLeftRadius", 20], ["borderTopRightRadius", 20], ["paddingTop", 16], ["boxShadow", "0 -4px 16px rgba(0, 0, 0, 0.06)"]]))], ["content", padStyleMapOf(utsMapOf([["flex", 1], ["paddingTop", 0], ["paddingRight", 24], ["paddingBottom", 0], ["paddingLeft", 24], ["position", "relative"], ["zIndex", 1]]))], ["form-card", padStyleMapOf(utsMapOf([["backgroundColor", "#ffffff"], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12], ["paddingTop", 24], ["paddingRight", 24], ["paddingBottom", 24], ["paddingLeft", 24], ["boxShadow", "0 4px 12px rgba(0, 0, 0, 0.08)"], ["marginBottom", 24]]))], ["avatar-section", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "column"], ["alignItems", "center"], ["marginBottom", 32]]))], ["avatar-wrapper", padStyleMapOf(utsMapOf([["position", "relative"], ["width", 110], ["height", 110], ["borderTopLeftRadius", 55], ["borderTopRightRadius", 55], ["borderBottomRightRadius", 55], ["borderBottomLeftRadius", 55], ["overflow", "hidden"], ["boxShadow", "0 8px 20px rgba(0, 0, 0, 0.1)"], ["borderTopWidth", 4], ["borderRightWidth", 4], ["borderBottomWidth", 4], ["borderLeftWidth", 4], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#ffffff"], ["borderRightColor", "#ffffff"], ["borderBottomColor", "#ffffff"], ["borderLeftColor", "#ffffff"]]))], ["avatar-preview", padStyleMapOf(utsMapOf([["width", "100%"], ["height", "100%"], ["backgroundColor", "#f0f0f0"]]))], ["avatar-overlay", padStyleMapOf(utsMapOf([["position", "absolute"], ["bottom", 0], ["left", 0], ["right", 0], ["height", 35], ["backgroundColor", "rgba(0,0,0,0.6)"], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["camera-icon", padStyleMapOf(utsMapOf([["fontSize", 18], ["color", "#ffffff"], ["marginBottom", 4]]))], ["avatar-hint", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "#999999"], ["marginTop", 12], ["backgroundColor", "rgba(255,255,255,0.6)"], ["paddingTop", 4], ["paddingRight", 12], ["paddingBottom", 4], ["paddingLeft", 12], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12]]))], ["input-group", padStyleMapOf(utsMapOf([["marginBottom", 24], ["display", "flex"], ["alignItems", "center"]]))], ["input-label", padStyleMapOf(utsMapOf([["fontSize", 15], ["color", "#333333"], ["fontWeight", "400"], ["width", 70], ["marginRight", 12], ["flexShrink", 0]]))], ["input-wrapper", padStyleMapOf(utsMapOf([["flex", 1], ["backgroundColor", "#f8f9fa"], ["borderTopLeftRadius", 16], ["borderTopRightRadius", 16], ["borderBottomRightRadius", 16], ["borderBottomLeftRadius", 16], ["paddingTop", 0], ["paddingRight", 16], ["paddingBottom", 0], ["paddingLeft", 16], ["height", 56], ["display", "flex"], ["alignItems", "center"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "rgba(0,0,0,0)"], ["borderRightColor", "rgba(0,0,0,0)"], ["borderBottomColor", "rgba(0,0,0,0)"], ["borderLeftColor", "rgba(0,0,0,0)"]]))], ["input-wrapper-focused", padStyleMapOf(utsMapOf([["backgroundColor", "#ffffff"], ["borderTopColor", "#0066CC"], ["borderRightColor", "#0066CC"], ["borderBottomColor", "#0066CC"], ["borderLeftColor", "#0066CC"], ["boxShadow", "0 4px 12px rgba(0, 102, 204, 0.15)"]]))], ["input-wrapper-error", padStyleMapOf(utsMapOf([["borderTopColor", "#ff4d4f"], ["borderRightColor", "#ff4d4f"], ["borderBottomColor", "#ff4d4f"], ["borderLeftColor", "#ff4d4f"], ["backgroundColor", "#fff0f0"]]))], ["input-wrapper-disabled", padStyleMapOf(utsMapOf([["backgroundColor", "#f5f5f5"], ["opacity", 0.7], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "dashed"], ["borderRightStyle", "dashed"], ["borderBottomStyle", "dashed"], ["borderLeftStyle", "dashed"], ["borderTopColor", "#e0e0e0"], ["borderRightColor", "#e0e0e0"], ["borderBottomColor", "#e0e0e0"], ["borderLeftColor", "#e0e0e0"]]))], ["input-field", padStyleMapOf(utsMapOf([["flex", 1], ["height", 48], ["fontSize", 14], ["color", "#333333"]]))], ["placeholder-text", padStyleMapOf(utsMapOf([["color", "#CCCCCC"]]))], ["lock-icon", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "#999999"]]))], ["error-text", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "#FF4D4F"], ["marginTop", 4], ["marginLeft", 4]]))], ["flex-column", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "column"]]))], ["flex-1", padStyleMapOf(utsMapOf([["flex", 1]]))], ["clear-btn", padStyleMapOf(utsMapOf([["paddingTop", 8], ["paddingRight", 8], ["paddingBottom", 8], ["paddingLeft", 8], ["color", "#999999"], ["fontSize", 14]]))], ["gender-options", padStyleMapOf(utsMapOf([["flex", 1], ["display", "flex"], ["backgroundColor", "#f8f9fa"], ["paddingTop", 6], ["paddingRight", 6], ["paddingBottom", 6], ["paddingLeft", 6], ["borderTopLeftRadius", 16], ["borderTopRightRadius", 16], ["borderBottomRightRadius", 16], ["borderBottomLeftRadius", 16]]))], ["gender-btn", padStyleMapOf(utsMapOf([["flex", 1], ["height", 44], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "rgba(0,0,0,0)"], ["borderRightColor", "rgba(0,0,0,0)"], ["borderBottomColor", "rgba(0,0,0,0)"], ["borderLeftColor", "rgba(0,0,0,0)"], ["color", "#999999"], ["marginLeft", 12]]))], ["gender-btn-active", padStyleMapOf(utsMapOf([["backgroundColor", "#ffffff"], ["color", "#0066CC"], ["boxShadow", "0 2px 8px rgba(0, 0, 0, 0.08)"], ["fontWeight", "700"]]))], ["gender-icon", padStyleMapOf(utsMapOf([["marginRight", 6], ["fontSize", 18]]))], ["gender-text", padStyleMapOf(utsMapOf([["fontSize", 14]]))], ["action-section", padStyleMapOf(utsMapOf([["marginTop", 32], ["paddingBottom", 24]]))], ["submit-btn", padStyleMapOf(utsMapOf([["height", 56], ["borderTopLeftRadius", 28], ["borderTopRightRadius", 28], ["borderBottomRightRadius", 28], ["borderBottomLeftRadius", 28], ["fontSize", 18], ["fontWeight", "700"], ["boxShadow", "0 8px 24px rgba(0, 102, 204, 0.3)"], ["backgroundImage", "linear-gradient(135deg, #0066CC 0%, #1890FF 100%)"], ["backgroundColor", "rgba(0,0,0,0)"], ["borderTopWidth", "medium"], ["borderRightWidth", "medium"], ["borderBottomWidth", "medium"], ["borderLeftWidth", "medium"], ["borderTopStyle", "none"], ["borderRightStyle", "none"], ["borderBottomStyle", "none"], ["borderLeftStyle", "none"], ["borderTopColor", "#000000"], ["borderRightColor", "#000000"], ["borderBottomColor", "#000000"], ["borderLeftColor", "#000000"], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"], ["letterSpacing", 1]]))], ["btn-hover", padStyleMapOf(utsMapOf([["opacity", 0.9]]))]])]
