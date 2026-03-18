
import { getUserProfile, logout, updateUserProfile } from '@/api/user'

type UserInfoType = { __$originalPosition?: UTSSourceMapPosition<"UserInfoType", "pages/user/settings.uvue", 76, 6>;
	username : string
	email : string
	phone : string
	nickname : string
}

const __sfc__ = defineComponent({
	data() {
		return {
			userInfo: {
				username: 'testuser',
				email: 'user@example.com',
				phone: '13800138000',
				nickname: '测试用户'
			} as UserInfoType,
			showEditModal: false,
			editType: '',
			editTitle: '',
			editValue: '',
			editPlaceholder: ''
		}
	},
	onLoad() {
		this.loadUserInfo()
	},
	methods: {
		getEmail() : string {
			return this.userInfo.email != '' ? this.userInfo.email : '未设置'
		},
		getPhone() : string {
			return this.userInfo.phone != '' ? this.userInfo.phone : '未设置'
		},
		getNickname() : string {
			return this.userInfo.nickname != '' ? this.userInfo.nickname : '未设置'
		},
		loadUserInfo() {
			getUserProfile().then((result) => {
				if (result.code === 200) {
					const data = result.data as UTSJSONObject
					if (data != null) {
						const username = data.getString('username') ?? ''
						const email = data.getString('email') ?? ''
						const phone = data.getString('phone') ?? ''
						const nickname = data.getString('nickname') ?? ''
						
						this.userInfo = {
							username: username,
							email: email,
							phone: phone,
							nickname: nickname
						} as UserInfoType
						uni.setStorageSync('user_info', JSON.stringify(this.userInfo))
					}
				} else {
					// 降级到本地存储
					this.loadFromStorage()
				}
			}).catch((error) => {
				console.error('获取用户信息失败:', error, " at pages/user/settings.uvue:135")
				// 降级到本地存储
				this.loadFromStorage()
			})
		},
		loadFromStorage() {
			const userInfoStr = uni.getStorageSync('user_info')
			if (userInfoStr != null && userInfoStr != '') {
				try {
					const infoStr = userInfoStr as string
					const parsedInfo = UTSAndroid.consoleDebugError(JSON.parse(infoStr), " at pages/user/settings.uvue:145") as UTSJSONObject
					if (parsedInfo != null) {
						const username = parsedInfo.getString('username') ?? ''
						const email = parsedInfo.getString('email') ?? ''
						const phone = parsedInfo.getString('phone') ?? ''
						const nickname = parsedInfo.getString('nickname') ?? ''
						
						this.userInfo = {
							username: username,
							email: email,
							phone: phone,
							nickname: nickname
						} as UserInfoType
					}
				} catch (e) {
					console.error('Failed to parse user info:', e, " at pages/user/settings.uvue:160")
				}
			}
		},
		handleBack() {
			uni.navigateBack()
		},
		handleEditEmail() {
			this.editType = 'email'
			this.editTitle = '编辑邮箱'
			this.editValue = this.userInfo.email != '' ? this.userInfo.email : ''
			this.editPlaceholder = '请输入邮箱地址'
			this.showEditModal = true
		},
		handleEditNickname() {
			this.editType = 'nickname'
			this.editTitle = '编辑昵称'
			this.editValue = this.userInfo.nickname != '' ? this.userInfo.nickname : ''
			this.editPlaceholder = '请输入昵称'
			this.showEditModal = true
		},
		handleChangePassword() {
			uni.navigateTo({
				url: '/pages/user/change-password'
			})
		},
		closeEditModal() {
			this.showEditModal = false
		},
		handleConfirmEdit() {
			const trimmedValue = this.editValue.trim()
			if (trimmedValue == '') {
				uni.showToast({
					title: '内容不能为空',
					icon: 'none'
				})
				return
			}

			// 验证邮箱格式
			if (this.editType === 'email') {
				const emailRegex = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
				const isValid = emailRegex.test(this.editValue)
				if (isValid == false) {
					uni.showToast({
						title: '邮箱格式不正确',
						icon: 'none'
					})
					return
				}
			}

			let updateData : UTSJSONObject
			if (this.editType === 'email') {
				updateData = {
					email: this.editValue
				} as UTSJSONObject
			} else if (this.editType === 'nickname') {
				updateData = {
					nickname: this.editValue
				} as UTSJSONObject
			} else {
				updateData = {} as UTSJSONObject
			}

			updateUserProfile(updateData).then((result) => {
				if (result.code === 200) {
					// 更新本地数据
					if (this.editType === 'email') {
						this.userInfo.email = this.editValue
					} else if (this.editType === 'nickname') {
						this.userInfo.nickname = this.editValue
					}

					uni.setStorageSync('user_info', JSON.stringify(this.userInfo))

					this.closeEditModal()
					uni.showToast({
						title: '更新成功',
						icon: 'success'
					})
				} else {
					const msg = result.message != '' ? result.message : '更新失败'
					uni.showToast({
						title: msg,
						icon: 'error'
					})
				}
			}).catch((error) => {
				console.error('更新用户信息失败:', error, " at pages/user/settings.uvue:249")
				uni.showToast({
					title: '更新失败，请检查网络连接',
					icon: 'error'
				})
			})
		},
		handleLogout() {
			uni.showModal({
				title: '退出登录',
				content: '确定要退出登录吗？',
				confirmText: '退出',
				cancelText: '取消',
				success: (res) => {
					if (res.confirm) {
						logout().then(() => {}).catch((error) => {
							console.error('登出API调用失败:', error, " at pages/user/settings.uvue:265")
						})
						uni.removeStorageSync('auth_token')
						uni.removeStorageSync('user_info')
						uni.navigateTo({
							url: '/pages/login/login'
						})
					}
				}
			})
		}
	}
})

export default __sfc__
function GenPagesUserSettingsRender(this: InstanceType<typeof __sfc__>): any | null {
const _ctx = this
const _cache = this.$.renderCache
  return createElementVNode("view", utsMapOf({ class: "settings-container" }), [
    createElementVNode("view", utsMapOf({ class: "header" }), [
      createElementVNode("text", utsMapOf({
        class: "back-icon",
        onClick: _ctx.handleBack
      }), "‹", 8 /* PROPS */, ["onClick"]),
      createElementVNode("text", utsMapOf({ class: "header-title" }), "设置")
    ]),
    createElementVNode("view", utsMapOf({ class: "content" }), [
      createElementVNode("view", utsMapOf({ class: "section" }), [
        createElementVNode("text", utsMapOf({ class: "section-title" }), "基本信息"),
        createElementVNode("view", utsMapOf({ class: "setting-item" }), [
          createElementVNode("text", utsMapOf({ class: "setting-label" }), "用户名"),
          createElementVNode("text", utsMapOf({ class: "setting-value" }), toDisplayString(_ctx.userInfo.username), 1 /* TEXT */)
        ]),
        createElementVNode("view", utsMapOf({
          class: "setting-item",
          onClick: _ctx.handleEditEmail
        }), [
          createElementVNode("text", utsMapOf({ class: "setting-label" }), "邮箱"),
          createElementVNode("text", utsMapOf({ class: "setting-value" }), toDisplayString(_ctx.getEmail()), 1 /* TEXT */),
          createElementVNode("text", utsMapOf({ class: "setting-arrow" }), "›")
        ], 8 /* PROPS */, ["onClick"]),
        createElementVNode("view", utsMapOf({ class: "setting-item" }), [
          createElementVNode("text", utsMapOf({ class: "setting-label" }), "手机号"),
          createElementVNode("text", utsMapOf({ class: "setting-value" }), toDisplayString(_ctx.getPhone()), 1 /* TEXT */)
        ]),
        createElementVNode("view", utsMapOf({
          class: "setting-item",
          onClick: _ctx.handleEditNickname
        }), [
          createElementVNode("text", utsMapOf({ class: "setting-label" }), "昵称"),
          createElementVNode("text", utsMapOf({ class: "setting-value" }), toDisplayString(_ctx.getNickname()), 1 /* TEXT */),
          createElementVNode("text", utsMapOf({ class: "setting-arrow" }), "›")
        ], 8 /* PROPS */, ["onClick"])
      ]),
      createElementVNode("view", utsMapOf({ class: "section" }), [
        createElementVNode("text", utsMapOf({ class: "section-title" }), "安全设置"),
        createElementVNode("view", utsMapOf({
          class: "setting-item",
          onClick: _ctx.handleChangePassword
        }), [
          createElementVNode("text", utsMapOf({ class: "setting-label" }), "修改密码"),
          createElementVNode("text", utsMapOf({ class: "setting-arrow" }), "›")
        ], 8 /* PROPS */, ["onClick"])
      ]),
      createElementVNode("view", utsMapOf({ class: "logout-section" }), [
        createElementVNode("button", utsMapOf({
          class: "logout-btn",
          onClick: _ctx.handleLogout
        }), "退出登录", 8 /* PROPS */, ["onClick"])
      ])
    ]),
    isTrue(_ctx.showEditModal)
      ? createElementVNode("view", utsMapOf({
          key: 0,
          class: "modal-overlay",
          onClick: _ctx.closeEditModal
        }), [
          createElementVNode("view", utsMapOf({
            class: "modal-content",
            onClick: withModifiers(() => {}, ["stop"])
          }), [
            createElementVNode("text", utsMapOf({ class: "modal-title" }), toDisplayString(_ctx.editTitle), 1 /* TEXT */),
            createElementVNode("input", utsMapOf({
              modelValue: _ctx.editValue,
              onInput: ($event: InputEvent) => {(_ctx.editValue) = $event.detail.value},
              class: "modal-input",
              placeholder: _ctx.editPlaceholder,
              onConfirm: _ctx.handleConfirmEdit
            }), null, 40 /* PROPS, NEED_HYDRATION */, ["modelValue", "onInput", "placeholder", "onConfirm"]),
            createElementVNode("view", utsMapOf({ class: "modal-actions" }), [
              createElementVNode("button", utsMapOf({
                class: "modal-btn cancel-btn",
                onClick: _ctx.closeEditModal
              }), "取消", 8 /* PROPS */, ["onClick"]),
              createElementVNode("button", utsMapOf({
                class: "modal-btn confirm-btn",
                onClick: _ctx.handleConfirmEdit
              }), "确定", 8 /* PROPS */, ["onClick"])
            ])
          ])
        ], 8 /* PROPS */, ["onClick"])
      : createCommentVNode("v-if", true)
  ])
}
const GenPagesUserSettingsStyles = [utsMapOf([["settings-container", padStyleMapOf(utsMapOf([["flex", 1], ["backgroundColor", "#f8f8f8"], ["display", "flex"], ["flexDirection", "column"]]))], ["header", padStyleMapOf(utsMapOf([["display", "flex"], ["justifyContent", "center"], ["alignItems", "center"], ["paddingTop", 10], ["paddingRight", 16], ["paddingBottom", 10], ["paddingLeft", 16], ["backgroundImage", "linear-gradient(135deg, #0066CC 0%, #1890FF 100%)"], ["backgroundColor", "rgba(0,0,0,0)"], ["color", "#ffffff"], ["flexShrink", 0], ["position", "relative"]]))], ["back-icon", padStyleMapOf(utsMapOf([["fontSize", 28], ["color", "#ffffff"], ["position", "absolute"], ["left", 16], ["top", "50%"], ["width", 44], ["height", 44], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"], ["zIndex", 10]]))], ["header-title", padStyleMapOf(utsMapOf([["fontSize", 16], ["fontWeight", "700"], ["color", "#ffffff"]]))], ["content", padStyleMapOf(utsMapOf([["flex", 1], ["paddingTop", 8], ["paddingRight", 0], ["paddingBottom", 8], ["paddingLeft", 0]]))], ["section", padStyleMapOf(utsMapOf([["backgroundColor", "#ffffff"], ["marginBottom", 8], ["overflow", "hidden"]]))], ["section-title", padStyleMapOf(utsMapOf([["fontSize", 12], ["fontWeight", "700"], ["color", "#999999"], ["paddingTop", 10], ["paddingRight", 16], ["paddingBottom", 10], ["paddingLeft", 16], ["backgroundColor", "#f8f8f8"]]))], ["setting-item", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["justifyContent", "space-between"], ["alignItems", "center"], ["paddingTop", 12], ["paddingRight", 16], ["paddingBottom", 12], ["paddingLeft", 16], ["borderBottomWidth", 1], ["borderBottomStyle", "solid"], ["borderBottomColor", "#f0f0f0"]]))], ["setting-label", padStyleMapOf(utsMapOf([["fontSize", 14], ["color", "#333333"], ["fontWeight", "400"], ["flexShrink", 0], ["width", 60]]))], ["setting-value", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "#999999"], ["flex", 1], ["textAlign", "right"], ["marginRight", 8]]))], ["setting-arrow", padStyleMapOf(utsMapOf([["fontSize", 18], ["color", "#cccccc"], ["flexShrink", 0]]))], ["logout-section", padStyleMapOf(utsMapOf([["paddingTop", 16], ["paddingRight", 16], ["paddingBottom", 16], ["paddingLeft", 16], ["flexShrink", 0]]))], ["logout-btn", padStyleMapOf(utsMapOf([["width", "100%"], ["height", 44], ["backgroundColor", "#ffffff"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#e74c3c"], ["borderRightColor", "#e74c3c"], ["borderBottomColor", "#e74c3c"], ["borderLeftColor", "#e74c3c"], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["color", "#e74c3c"], ["fontSize", 15], ["fontWeight", "bold"], ["display", "flex"], ["justifyContent", "center"], ["alignItems", "center"]]))], ["modal-overlay", padStyleMapOf(utsMapOf([["position", "fixed"], ["top", 0], ["left", 0], ["right", 0], ["bottom", 0], ["backgroundColor", "rgba(0,0,0,0.5)"], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"], ["zIndex", 1000]]))], ["modal-content", padStyleMapOf(utsMapOf([["backgroundColor", "#ffffff"], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12], ["paddingTop", 20], ["paddingRight", 20], ["paddingBottom", 20], ["paddingLeft", 20], ["width", "80%"], ["maxWidth", 300]]))], ["modal-title", padStyleMapOf(utsMapOf([["fontSize", 16], ["fontWeight", "700"], ["color", "#333333"], ["marginBottom", 12], ["display", "flex"]]))], ["modal-input", padStyleMapOf(utsMapOf([["width", "100%"], ["height", 40], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#e0e0e0"], ["borderRightColor", "#e0e0e0"], ["borderBottomColor", "#e0e0e0"], ["borderLeftColor", "#e0e0e0"], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["paddingTop", 0], ["paddingRight", 12], ["paddingBottom", 0], ["paddingLeft", 12], ["fontSize", 14], ["color", "#333333"], ["marginBottom", 16], ["boxSizing", "border-box"]]))], ["modal-actions", padStyleMapOf(utsMapOf([["display", "flex"], ["marginRight", 12]]))], ["modal-btn", padStyleMapOf(utsMapOf([["flex", 1], ["height", 40], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["fontSize", 14], ["fontWeight", "400"], ["borderTopWidth", "medium"], ["borderRightWidth", "medium"], ["borderBottomWidth", "medium"], ["borderLeftWidth", "medium"], ["borderTopStyle", "none"], ["borderRightStyle", "none"], ["borderBottomStyle", "none"], ["borderLeftStyle", "none"], ["borderTopColor", "#000000"], ["borderRightColor", "#000000"], ["borderBottomColor", "#000000"], ["borderLeftColor", "#000000"]]))], ["cancel-btn", padStyleMapOf(utsMapOf([["backgroundColor", "#f0f0f0"], ["color", "#333333"]]))], ["confirm-btn", padStyleMapOf(utsMapOf([["backgroundImage", "linear-gradient(135deg, #0066CC 0%, #1890FF 100%)"], ["backgroundColor", "rgba(0,0,0,0)"], ["color", "#ffffff"]]))]])]
