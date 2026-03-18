
	import { getAdminUsers, getAdminUserDetail, updateUserStatus } from '@/api/admin'
	import type { ResponseDataType } from '@/utils/request'

	type UserType = { __$originalPosition?: UTSSourceMapPosition<"UserType", "pages/seller/admin-users.uvue", 64, 7>;
		id: number
		username: string
		nickname: string
		phone: string
		email: string
		avatar: string
		gender: number
		status: number
		createTime: string
		updateTime: string
	}

	const __sfc__ = defineComponent({
		data() {
			return {
				searchKeyword: '',
				userList: [] as UserType[],
				pageNum: 1,
				pageSize: 10,
				loading: false,
				noMore: false
			}
		},
		onLoad() {
			this.loadUserList()
		},
		methods: {
			loadUserList() {
				if (this.loading || this.noMore) return
				
				this.loading = true
				getAdminUsers(this.pageNum, this.pageSize, this.searchKeyword).then((res : ResponseDataType) => {
					this.loading = false
					if (res.code === 200 && res.data != null) {
						const data = res.data as UTSJSONObject
						const records = data.getArray('records')
						
						if (records != null && records.length > 0) {
							const newUsers = [] as UserType[]
							for (let i = 0; i < records.length; i++) {
								const item = records[i] as UTSJSONObject
								newUsers.push({
									id: (item.getNumber('id') ?? 0).toInt(),
									username: item.getString('username') ?? '',
									nickname: item.getString('nickname') ?? '',
									phone: item.getString('phone') ?? '',
									email: item.getString('email') ?? '',
									avatar: item.getString('avatar') ?? '',
									gender: (item.getNumber('gender') ?? 0).toInt(),
									status: (item.getNumber('status') ?? 1).toInt(),
									createTime: item.getString('createTime') ?? '',
									updateTime: item.getString('updateTime') ?? ''
								} as UserType)
							}
							
							if (this.pageNum === 1) {
								this.userList = newUsers
							} else {
								this.userList = this.userList.concat(newUsers)
							}
							
							if (newUsers.length < this.pageSize) {
								this.noMore = true
							}
						} else {
							if (this.pageNum === 1) {
								this.userList = []
							}
							this.noMore = true
						}
					}
				}).catch((err : Any | null) => {
					this.loading = false
					const error = err as Error
					uni.showToast({
						title: error.message,
						icon: 'none'
					})
				})
			},
			handleSearch() {
				this.pageNum = 1
				this.noMore = false
				this.userList = []
				this.loadUserList()
			},
			loadMore() {
				if (!this.loading && !this.noMore) {
					this.pageNum++
					this.loadUserList()
				}
			},
			toggleUserStatus(user : UserType) {
				const newStatus = user.status === 1 ? 0 : 1
				const statusText = newStatus === 1 ? '启用' : '禁用'
				
				uni.showModal({
					title: '确认操作',
					content: `确定要${statusText}用户"${user.username}"吗？`,
					success: (res) => {
						if (res.confirm) {
							updateUserStatus(user.id, newStatus).then((response : ResponseDataType) => {
								if (response.code === 200) {
									uni.showToast({
										title: `${statusText}成功`,
										icon: 'success'
									})
									user.status = newStatus
								} else {
									uni.showToast({
										title: response.message,
										icon: 'none'
									})
								}
							}).catch((err : Any | null) => {
								const error = err as Error
								uni.showToast({
									title: error.message,
									icon: 'none'
								})
							})
						}
					}
				})
			},
			viewUserDetail(userId : number) {
				getAdminUserDetail(userId).then((res : ResponseDataType) => {
					if (res.code === 200 && res.data != null) {
						const user = res.data as UTSJSONObject
						const genderMap = ['未知', '男', '女']
						const gender = (user.getNumber('gender') ?? 0).toInt()
						const genderText = genderMap[gender]
						
						const content = `用户名：${user.getString('username') ?? ''}\n昵称：${user.getString('nickname') ?? '未设置'}\n手机：${user.getString('phone') ?? '未绑定'}\n邮箱：${user.getString('email') ?? '未绑定'}\n性别：${genderText}\n注册时间：${user.getString('createTime') ?? ''}`
						
						uni.showModal({
							title: '用户详情',
							content: content,
							showCancel: false
						})
					}
				}).catch((err : Any | null) => {
					const error = err as Error
					uni.showToast({
						title: error.message,
						icon: 'none'
					})
				})
			}
		}
	})

export default __sfc__
function GenPagesSellerAdminUsersRender(this: InstanceType<typeof __sfc__>): any | null {
const _ctx = this
const _cache = this.$.renderCache
  return createElementVNode("view", utsMapOf({ class: "page" }), [
    createElementVNode("view", utsMapOf({ class: "search-bar" }), [
      createElementVNode("view", utsMapOf({ class: "search-wrapper" }), [
        createElementVNode("input", utsMapOf({
          modelValue: _ctx.searchKeyword,
          onInput: ($event: InputEvent) => {(_ctx.searchKeyword) = $event.detail.value},
          class: "search-input",
          placeholder: "搜索用户名/手机号",
          onConfirm: _ctx.handleSearch
        }), null, 40 /* PROPS, NEED_HYDRATION */, ["modelValue", "onInput", "onConfirm"])
      ])
    ]),
    createElementVNode("scroll-view", utsMapOf({
      class: "user-list",
      "scroll-y": "",
      onScrolltolower: _ctx.loadMore
    }), [
      createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.userList, (user, __key, __index, _cached): any => {
        return createElementVNode("view", utsMapOf({
          key: user.id,
          class: "user-card"
        }), [
          createElementVNode("view", utsMapOf({ class: "card-left" }), [
            createElementVNode("image", utsMapOf({
              src: user.avatar !== '' ? user.avatar : '/static/default-avatar.png',
              class: "user-avatar",
              mode: "aspectFill"
            }), null, 8 /* PROPS */, ["src"]),
            createElementVNode("view", utsMapOf({ class: "user-info" }), [
              createElementVNode("view", utsMapOf({ class: "info-top" }), [
                createElementVNode("text", utsMapOf({ class: "user-name" }), toDisplayString(user.username), 1 /* TEXT */),
                createElementVNode("view", utsMapOf({
                  class: normalizeClass(["status-badge", user.status === 1 ? 'badge-active' : 'badge-disabled'])
                }), [
                  createElementVNode("text", utsMapOf({
                    class: normalizeClass(["badge-text", user.status === 1 ? 'badge-active-text' : 'badge-disabled-text'])
                  }), toDisplayString(user.status === 1 ? '正常' : '禁用'), 3 /* TEXT, CLASS */)
                ], 2 /* CLASS */)
              ]),
              createElementVNode("text", utsMapOf({ class: "user-phone" }), toDisplayString(user.phone !== '' ? user.phone : '未绑定手机'), 1 /* TEXT */)
            ])
          ]),
          createElementVNode("view", utsMapOf({ class: "card-right" }), [
            createElementVNode("view", utsMapOf({
              class: "btn btn-detail",
              onClick: () => {_ctx.viewUserDetail(user.id)}
            }), [
              createElementVNode("text", utsMapOf({ class: "btn-text btn-detail-text" }), "详情")
            ], 8 /* PROPS */, ["onClick"]),
            createElementVNode("view", utsMapOf({
              class: normalizeClass(["btn", user.status === 1 ? 'btn-danger' : 'btn-success']),
              onClick: () => {_ctx.toggleUserStatus(user)}
            }), [
              createElementVNode("text", utsMapOf({
                class: normalizeClass(["btn-text", user.status === 1 ? 'btn-danger-text' : 'btn-success-text'])
              }), toDisplayString(user.status === 1 ? '禁用' : '启用'), 3 /* TEXT, CLASS */)
            ], 10 /* CLASS, PROPS */, ["onClick"])
          ])
        ])
      }), 128 /* KEYED_FRAGMENT */),
      createElementVNode("view", utsMapOf({ class: "load-tip" }), [
        isTrue(_ctx.loading)
          ? createElementVNode("text", utsMapOf({
              key: 0,
              class: "tip-text"
            }), "加载中...")
          : isTrue(_ctx.noMore)
            ? createElementVNode("text", utsMapOf({
                key: 1,
                class: "tip-text"
              }), "没有更多了")
            : createCommentVNode("v-if", true)
      ])
    ], 40 /* PROPS, NEED_HYDRATION */, ["onScrolltolower"])
  ])
}
const GenPagesSellerAdminUsersStyles = [utsMapOf([["page", padStyleMapOf(utsMapOf([["backgroundColor", "#f5f5f5"], ["display", "flex"], ["flexDirection", "column"]]))], ["search-bar", padStyleMapOf(utsMapOf([["backgroundColor", "#ffffff"], ["paddingTop", 12], ["paddingRight", 15], ["paddingBottom", 12], ["paddingLeft", 15], ["boxShadow", "0 2px 8px rgba(0, 0, 0, 0.06)"]]))], ["search-wrapper", padStyleMapOf(utsMapOf([["backgroundColor", "#f7f7f7"], ["borderTopLeftRadius", 20], ["borderTopRightRadius", 20], ["borderBottomRightRadius", 20], ["borderBottomLeftRadius", 20], ["paddingTop", 0], ["paddingRight", 15], ["paddingBottom", 0], ["paddingLeft", 15], ["height", 40], ["display", "flex"], ["alignItems", "center"]]))], ["search-input", padStyleMapOf(utsMapOf([["flex", 1], ["fontSize", 14], ["color", "#333333"]]))], ["user-list", padStyleMapOf(utsMapOf([["flex", 1], ["paddingTop", 12], ["paddingRight", 15], ["paddingBottom", 12], ["paddingLeft", 15]]))], ["user-card", padStyleMapOf(utsMapOf([["backgroundColor", "#ffffff"], ["borderTopLeftRadius", 10], ["borderTopRightRadius", 10], ["borderBottomRightRadius", 10], ["borderBottomLeftRadius", 10], ["marginBottom", 10], ["paddingTop", 12], ["paddingRight", 12], ["paddingBottom", 12], ["paddingLeft", 12], ["boxShadow", "0 2px 6px rgba(0, 0, 0, 0.06)"], ["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "space-between"]]))], ["card-left", padStyleMapOf(utsMapOf([["flex", 1], ["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"]]))], ["user-avatar", padStyleMapOf(utsMapOf([["width", 45], ["height", 45], ["borderTopLeftRadius", 22], ["borderTopRightRadius", 22], ["borderBottomRightRadius", 22], ["borderBottomLeftRadius", 22], ["marginRight", 12], ["backgroundColor", "#e0e0e0"]]))], ["user-info", padStyleMapOf(utsMapOf([["flex", 1], ["display", "flex"], ["flexDirection", "column"]]))], ["info-top", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["marginBottom", 4]]))], ["user-name", padStyleMapOf(utsMapOf([["fontSize", 16], ["fontWeight", "bold"], ["color", "#333333"], ["marginRight", 8]]))], ["status-badge", padStyleMapOf(utsMapOf([["paddingTop", 2], ["paddingRight", 8], ["paddingBottom", 2], ["paddingLeft", 8], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8]]))], ["badge-active", padStyleMapOf(utsMapOf([["backgroundColor", "#e6f7ff"]]))], ["badge-disabled", padStyleMapOf(utsMapOf([["backgroundColor", "#fff1f0"]]))], ["badge-text", padStyleMapOf(utsMapOf([["fontSize", 11]]))], ["badge-active-text", padStyleMapOf(utsMapOf([["color", "#1890ff"]]))], ["badge-disabled-text", padStyleMapOf(utsMapOf([["color", "#ff4d4f"]]))], ["user-phone", padStyleMapOf(utsMapOf([["fontSize", 13], ["color", "#999999"]]))], ["card-right", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["marginLeft", 10]]))], ["btn", padStyleMapOf(utsMapOf([["width", 55], ["height", 32], ["borderTopLeftRadius", 16], ["borderTopRightRadius", 16], ["borderBottomRightRadius", 16], ["borderBottomLeftRadius", 16], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"], ["marginLeft", 8]]))], ["btn-detail", padStyleMapOf(utsMapOf([["backgroundColor", "#f5f5f5"]]))], ["btn-detail-text", padStyleMapOf(utsMapOf([["color", "#666666"]]))], ["btn-success", padStyleMapOf(utsMapOf([["backgroundColor", "#52c41a"]]))], ["btn-success-text", padStyleMapOf(utsMapOf([["color", "#ffffff"]]))], ["btn-danger", padStyleMapOf(utsMapOf([["backgroundColor", "#ff4d4f"]]))], ["btn-danger-text", padStyleMapOf(utsMapOf([["color", "#ffffff"]]))], ["btn-text", padStyleMapOf(utsMapOf([["fontSize", 13], ["fontWeight", "bold"]]))], ["load-tip", padStyleMapOf(utsMapOf([["paddingTop", 20], ["paddingRight", 0], ["paddingBottom", 20], ["paddingLeft", 0], ["textAlign", "center"]]))], ["tip-text", padStyleMapOf(utsMapOf([["fontSize", 13], ["color", "#999999"]]))]])]
