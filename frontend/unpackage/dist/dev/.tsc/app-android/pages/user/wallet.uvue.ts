
import { getWallet, rechargeWallet, getTransactions, type WalletVO, type TransactionVO } from '@/api/wallet'

const __sfc__ = defineComponent({
	data() {
		return {
			isLoading: false,
			showBalance: true,
			currentFilter: 'all',
			walletInfo: {
				id: 0,
				userId: 0,
				balance: 0,
				frozenAmount: 0,
				totalRecharge: 0,
				totalConsume: 0
			} as WalletVO,
			records: [] as TransactionVO[],
			pageNum: 1,
			pageSize: 10,
			hasMore: true,
			showRechargeModal: false,
			selectedAmount: 100,
			customAmount: ''
		}
	},
	onLoad() {
		this.loadWalletInfo()
		this.loadTransactions()
	},
	onShow() {
		this.loadWalletInfo()
	},
	methods: {
		loadWalletInfo() {
			getWallet().then((res) => {
				const data = res.data as UTSJSONObject
				this.walletInfo = {
					id: (data.getNumber('id') ?? 0).toInt(),
					userId: (data.getNumber('userId') ?? 0).toInt(),
					balance: data.getNumber('balance') ?? 0,
					frozenAmount: data.getNumber('frozenAmount') ?? 0,
					totalRecharge: data.getNumber('totalRecharge') ?? 0,
					totalConsume: data.getNumber('totalConsume') ?? 0
				} as WalletVO
			}).catch((err) => {
				console.error('获取钱包信息失败:', err, " at pages/user/wallet.uvue:183")
			})
		},
		loadTransactions() {
			if (this.isLoading) return
			this.isLoading = true
			let typeParam : string | null = null
			if (this.currentFilter == 'in') {
				typeParam = 'RECHARGE'
			} else if (this.currentFilter == 'out') {
				typeParam = 'PAYMENT'
			}
			getTransactions({
				type: typeParam,
				startDate: null,
				endDate: null,
				pageNum: this.pageNum,
				pageSize: this.pageSize
			}).then((res) => {
				const data = res.data as UTSJSONObject
				const recordsArr = data.getArray('records')
				const total = (data.getNumber('total') ?? 0).toInt()
				if (recordsArr != null) {
					const newRecords : TransactionVO[] = []
					for (let i = 0; i < recordsArr.length; i++) {
						const item = recordsArr[i] as UTSJSONObject
						newRecords.push({
							id: (item.getNumber('id') ?? 0).toInt(),
							transactionNo: item.getString('transactionNo') ?? '',
							type: item.getString('type') ?? '',
							typeDesc: item.getString('typeDesc') ?? '',
							amount: item.getNumber('amount') ?? 0,
							balanceAfter: item.getNumber('balanceAfter') ?? 0,
							relatedOrderNo: item.getString('relatedOrderNo'),
							description: item.getString('description') ?? '',
							createTime: item.getString('createTime') ?? ''
						} as TransactionVO)
					}
					if (this.pageNum == 1) {
						this.records = newRecords
					} else {
						this.records = this.records.concat(newRecords)
					}
					this.hasMore = this.records.length < total
				}
				this.isLoading = false
			}).catch((err) => {
				console.error('获取交易记录失败:', err, " at pages/user/wallet.uvue:230")
				this.isLoading = false
			})
		},
		loadMore() {
			if (!this.hasMore || this.isLoading) return
			this.pageNum++
			this.loadTransactions()
		},
		changeFilter(filter : string) {
			if (this.currentFilter == filter) return
			this.currentFilter = filter
			this.pageNum = 1
			this.records = [] as TransactionVO[]
			this.hasMore = true
			this.loadTransactions()
		},
		formatMoney(num : number) : string {
			return Math.abs(num).toFixed(2)
		},
		formatTime(timeStr : string) : string {
			if (timeStr.length >= 16) {
				return timeStr.substring(5, 16)
			}
			return timeStr
		},
		goBack() {
			uni.navigateBack()
		},
		toggleEye() {
			this.showBalance = !this.showBalance
		},
		showHelp() {
			uni.showModal({
				title: '余额说明',
				content: '账户余额可用于支付订单。充值金额不可提现，退款金额可原路返回。',
				showCancel: false
			})
		},
		handleRecharge() {
			this.showRechargeModal = true
			this.selectedAmount = 100
			this.customAmount = ''
		},
		closeRechargeModal() {
			this.showRechargeModal = false
		},
		selectAmount(amount : number) {
			this.selectedAmount = amount
			this.customAmount = ''
		},
		onCustomInput() {
			if (this.customAmount != '') {
				this.selectedAmount = 0
			}
		},
		confirmRecharge() {
			let amount = this.selectedAmount
			if (this.customAmount != '') {
				amount = parseFloat(this.customAmount)
				if (isNaN(amount) || amount <= 0) {
					uni.showToast({ title: '请输入有效金额', icon: 'none' })
					return
				}
			}
			if (amount <= 0) {
				uni.showToast({ title: '请选择充值金额', icon: 'none' })
				return
			}
			uni.showLoading({ title: '充值中...' })
			rechargeWallet(amount).then((_res) => {
				uni.hideLoading()
				uni.showToast({ title: '充值成功', icon: 'success' })
				this.closeRechargeModal()
				this.loadWalletInfo()
				this.pageNum = 1
				this.records = [] as TransactionVO[]
				this.loadTransactions()
			}).catch((err) => {
				uni.hideLoading()
				console.error('充值失败:', err, " at pages/user/wallet.uvue:310")
				uni.showToast({ title: '充值失败', icon: 'none' })
			})
		},
		handleWithdraw() {
			uni.showToast({ title: '提现功能暂未开放', icon: 'none' })
		},
		getIcon(type : string) : string {
			if (type == 'RECHARGE' || type == 'ADMIN_RECHARGE') return '💰'
			if (type == 'PAYMENT') return '🛒'
			if (type == 'REFUND') return '↩️'
			return '📝'
		},
		getIconClass(type : string) : string {
			if (type == 'RECHARGE' || type == 'ADMIN_RECHARGE') return 'item-icon-recharge'
			if (type == 'PAYMENT') return 'item-icon-pay'
			if (type == 'REFUND') return 'item-icon-refund'
			return ''
		}
	}
})

export default __sfc__
function GenPagesUserWalletRender(this: InstanceType<typeof __sfc__>): any | null {
const _ctx = this
const _cache = this.$.renderCache
  return createElementVNode("view", utsMapOf({ class: "page" }), [
    createElementVNode("view", utsMapOf({ class: "header-bg" }), [
      createElementVNode("view", utsMapOf({ class: "status-bar" })),
      createElementVNode("view", utsMapOf({ class: "nav-bar" }), [
        createElementVNode("view", utsMapOf({
          class: "nav-back",
          onClick: _ctx.goBack
        }), [
          createElementVNode("text", utsMapOf({ class: "back-arrow" }), "‹")
        ], 8 /* PROPS */, ["onClick"]),
        createElementVNode("text", utsMapOf({ class: "nav-title" }), "我的钱包"),
        createElementVNode("view", utsMapOf({
          class: "nav-action",
          onClick: _ctx.showHelp
        }), [
          createElementVNode("text", utsMapOf({ class: "nav-action-text" }), "说明")
        ], 8 /* PROPS */, ["onClick"])
      ]),
      createElementVNode("view", utsMapOf({ class: "balance-box" }), [
        createElementVNode("view", utsMapOf({ class: "balance-header" }), [
          createElementVNode("text", utsMapOf({ class: "balance-label" }), "账户余额(元)"),
          createElementVNode("view", utsMapOf({
            class: "eye-btn",
            onClick: _ctx.toggleEye
          }), [
            createElementVNode("text", utsMapOf({ class: "eye-text" }), toDisplayString(_ctx.showBalance ? '隐藏' : '显示'), 1 /* TEXT */)
          ], 8 /* PROPS */, ["onClick"])
        ]),
        createElementVNode("view", utsMapOf({ class: "balance-amount" }), [
          createElementVNode("text", utsMapOf({ class: "amount-symbol" }), "¥"),
          createElementVNode("text", utsMapOf({ class: "amount-num" }), toDisplayString(_ctx.showBalance ? _ctx.formatMoney(_ctx.walletInfo.balance) : '****'), 1 /* TEXT */)
        ]),
        createElementVNode("view", utsMapOf({ class: "balance-actions" }), [
          createElementVNode("view", utsMapOf({
            class: "action-btn-primary",
            onClick: _ctx.handleRecharge
          }), [
            createElementVNode("text", utsMapOf({ class: "action-btn-primary-text" }), "充值")
          ], 8 /* PROPS */, ["onClick"]),
          createElementVNode("view", utsMapOf({
            class: "action-btn-outline",
            onClick: _ctx.handleWithdraw
          }), [
            createElementVNode("text", utsMapOf({ class: "action-btn-outline-text" }), "提现")
          ], 8 /* PROPS */, ["onClick"])
        ])
      ])
    ]),
    createElementVNode("scroll-view", utsMapOf({
      class: "main-scroll",
      "scroll-y": "true",
      "show-scrollbar": false,
      onScrolltolower: _ctx.loadMore
    }), [
      createElementVNode("view", utsMapOf({ class: "stats-card" }), [
        createElementVNode("view", utsMapOf({ class: "stats-item" }), [
          createElementVNode("text", utsMapOf({ class: "stats-num" }), toDisplayString(_ctx.formatMoney(_ctx.walletInfo.frozenAmount)), 1 /* TEXT */),
          createElementVNode("text", utsMapOf({ class: "stats-name" }), "冻结金额")
        ]),
        createElementVNode("view", utsMapOf({ class: "stats-divider" })),
        createElementVNode("view", utsMapOf({ class: "stats-item" }), [
          createElementVNode("text", utsMapOf({ class: "stats-num stats-num-green" }), toDisplayString(_ctx.formatMoney(_ctx.walletInfo.totalRecharge)), 1 /* TEXT */),
          createElementVNode("text", utsMapOf({ class: "stats-name" }), "累计充值")
        ]),
        createElementVNode("view", utsMapOf({ class: "stats-divider" })),
        createElementVNode("view", utsMapOf({ class: "stats-item" }), [
          createElementVNode("text", utsMapOf({ class: "stats-num stats-num-orange" }), toDisplayString(_ctx.formatMoney(_ctx.walletInfo.totalConsume)), 1 /* TEXT */),
          createElementVNode("text", utsMapOf({ class: "stats-name" }), "累计消费")
        ])
      ]),
      createElementVNode("view", utsMapOf({ class: "record-card" }), [
        createElementVNode("view", utsMapOf({ class: "record-header" }), [
          createElementVNode("text", utsMapOf({ class: "record-title" }), "交易记录"),
          createElementVNode("view", utsMapOf({ class: "filter-tabs" }), [
            createElementVNode("text", utsMapOf({
              class: normalizeClass(["filter-tab", utsMapOf({ 'filter-tab-active': _ctx.currentFilter == 'all' })]),
              onClick: () => {_ctx.changeFilter('all')}
            }), "全部", 10 /* CLASS, PROPS */, ["onClick"]),
            createElementVNode("text", utsMapOf({
              class: normalizeClass(["filter-tab", utsMapOf({ 'filter-tab-active': _ctx.currentFilter == 'in' })]),
              onClick: () => {_ctx.changeFilter('in')}
            }), "收入", 10 /* CLASS, PROPS */, ["onClick"]),
            createElementVNode("text", utsMapOf({
              class: normalizeClass(["filter-tab", utsMapOf({ 'filter-tab-active': _ctx.currentFilter == 'out' })]),
              onClick: () => {_ctx.changeFilter('out')}
            }), "支出", 10 /* CLASS, PROPS */, ["onClick"])
          ])
        ]),
        isTrue(_ctx.isLoading && _ctx.records.length == 0)
          ? createElementVNode("view", utsMapOf({
              key: 0,
              class: "loading-box"
            }), [
              createElementVNode("text", utsMapOf({ class: "loading-text" }), "加载中...")
            ])
          : createElementVNode("view", utsMapOf({
              key: 1,
              class: "record-list"
            }), [
              createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.records, (item, index, __index, _cached): any => {
                return createElementVNode("view", utsMapOf({
                  class: "record-item",
                  key: index
                }), [
                  createElementVNode("view", utsMapOf({
                    class: normalizeClass(["item-icon", _ctx.getIconClass(item.type)])
                  }), [
                    createElementVNode("text", utsMapOf({ class: "icon-text" }), toDisplayString(_ctx.getIcon(item.type)), 1 /* TEXT */)
                  ], 2 /* CLASS */),
                  createElementVNode("view", utsMapOf({ class: "item-info" }), [
                    createElementVNode("text", utsMapOf({ class: "item-title" }), toDisplayString(item.typeDesc), 1 /* TEXT */),
                    isTrue(item.description)
                      ? createElementVNode("text", utsMapOf({
                          key: 0,
                          class: "item-desc"
                        }), toDisplayString(item.description), 1 /* TEXT */)
                      : createCommentVNode("v-if", true),
                    createElementVNode("text", utsMapOf({ class: "item-time" }), toDisplayString(_ctx.formatTime(item.createTime)), 1 /* TEXT */)
                  ]),
                  createElementVNode("text", utsMapOf({
                    class: normalizeClass(["item-amount", utsMapOf({ 'item-amount-income': item.amount > 0 })])
                  }), toDisplayString(item.amount > 0 ? '+' : '') + toDisplayString(_ctx.formatMoney(item.amount)), 3 /* TEXT, CLASS */)
                ])
              }), 128 /* KEYED_FRAGMENT */)
            ]),
        _ctx.records.length > 0
          ? createElementVNode("view", utsMapOf({
              key: 2,
              class: "list-footer"
            }), [
              isTrue(_ctx.isLoading)
                ? createElementVNode("text", utsMapOf({
                    key: 0,
                    class: "footer-text"
                  }), "加载中...")
                : isTrue(!_ctx.hasMore)
                  ? createElementVNode("text", utsMapOf({
                      key: 1,
                      class: "footer-text"
                    }), "— 没有更多了 —")
                  : createCommentVNode("v-if", true)
            ])
          : createCommentVNode("v-if", true),
        isTrue(!_ctx.isLoading && _ctx.records.length == 0)
          ? createElementVNode("view", utsMapOf({
              key: 3,
              class: "empty-box"
            }), [
              createElementVNode("text", utsMapOf({ class: "empty-icon" }), "📝"),
              createElementVNode("text", utsMapOf({ class: "empty-text" }), "暂无记录")
            ])
          : createCommentVNode("v-if", true)
      ])
    ], 40 /* PROPS, NEED_HYDRATION */, ["onScrolltolower"]),
    isTrue(_ctx.showRechargeModal)
      ? createElementVNode("view", utsMapOf({
          key: 0,
          class: "modal-mask",
          onClick: _ctx.closeRechargeModal
        }), [
          createElementVNode("view", utsMapOf({
            class: "modal-content",
            onClick: withModifiers(() => {}, ["stop"])
          }), [
            createElementVNode("text", utsMapOf({ class: "modal-title" }), "充值金额"),
            createElementVNode("view", utsMapOf({ class: "amount-options" }), [
              createElementVNode("view", utsMapOf({
                class: normalizeClass(["amount-option", utsMapOf({ 'amount-option-active': _ctx.selectedAmount == 50 })]),
                onClick: () => {_ctx.selectAmount(50)}
              }), [
                createElementVNode("text", utsMapOf({ class: "option-text" }), "50元")
              ], 10 /* CLASS, PROPS */, ["onClick"]),
              createElementVNode("view", utsMapOf({
                class: normalizeClass(["amount-option", utsMapOf({ 'amount-option-active': _ctx.selectedAmount == 100 })]),
                onClick: () => {_ctx.selectAmount(100)}
              }), [
                createElementVNode("text", utsMapOf({ class: "option-text" }), "100元")
              ], 10 /* CLASS, PROPS */, ["onClick"]),
              createElementVNode("view", utsMapOf({
                class: normalizeClass(["amount-option", utsMapOf({ 'amount-option-active': _ctx.selectedAmount == 200 })]),
                onClick: () => {_ctx.selectAmount(200)}
              }), [
                createElementVNode("text", utsMapOf({ class: "option-text" }), "200元")
              ], 10 /* CLASS, PROPS */, ["onClick"]),
              createElementVNode("view", utsMapOf({
                class: normalizeClass(["amount-option", utsMapOf({ 'amount-option-active': _ctx.selectedAmount == 500 })]),
                onClick: () => {_ctx.selectAmount(500)}
              }), [
                createElementVNode("text", utsMapOf({ class: "option-text" }), "500元")
              ], 10 /* CLASS, PROPS */, ["onClick"])
            ]),
            createElementVNode("view", utsMapOf({ class: "custom-input-box" }), [
              createElementVNode("text", utsMapOf({ class: "input-label" }), "自定义金额"),
              createElementVNode("input", utsMapOf({
                class: "custom-input",
                type: "digit",
                placeholder: "请输入金额",
                modelValue: _ctx.customAmount,
                onInput: ($event: InputEvent) => {(_ctx.customAmount) = $event.detail.value},
                onBlur: _ctx.onCustomInput
              }), null, 40 /* PROPS, NEED_HYDRATION */, ["modelValue", "onInput", "onBlur"])
            ]),
            createElementVNode("view", utsMapOf({ class: "modal-actions" }), [
              createElementVNode("view", utsMapOf({
                class: "modal-btn modal-btn-cancel",
                onClick: _ctx.closeRechargeModal
              }), [
                createElementVNode("text", utsMapOf({ class: "btn-text" }), "取消")
              ], 8 /* PROPS */, ["onClick"]),
              createElementVNode("view", utsMapOf({
                class: "modal-btn modal-btn-confirm",
                onClick: _ctx.confirmRecharge
              }), [
                createElementVNode("text", utsMapOf({ class: "btn-text-white" }), "确认充值")
              ], 8 /* PROPS */, ["onClick"])
            ])
          ], 8 /* PROPS */, ["onClick"])
        ], 8 /* PROPS */, ["onClick"])
      : createCommentVNode("v-if", true)
  ])
}
const GenPagesUserWalletStyles = [utsMapOf([["page", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "column"], ["backgroundColor", "#F5F7FA"], ["height", "100%"]]))], ["header-bg", padStyleMapOf(utsMapOf([["backgroundColor", "#0066CC"], ["paddingBottom", "80rpx"]]))], ["status-bar", padStyleMapOf(utsMapOf([["height", CSS_VAR_STATUS_BAR_HEIGHT]]))], ["nav-bar", padStyleMapOf(utsMapOf([["height", "88rpx"], ["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "space-between"], ["paddingTop", 0], ["paddingRight", "24rpx"], ["paddingBottom", 0], ["paddingLeft", "24rpx"]]))], ["nav-back", padStyleMapOf(utsMapOf([["width", "80rpx"], ["height", "80rpx"], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["back-arrow", padStyleMapOf(utsMapOf([["fontSize", "56rpx"], ["color", "#FFFFFF"], ["fontWeight", "400"]]))], ["nav-title", padStyleMapOf(utsMapOf([["fontSize", "34rpx"], ["fontWeight", "700"], ["color", "#FFFFFF"]]))], ["nav-action", padStyleMapOf(utsMapOf([["paddingTop", "8rpx"], ["paddingRight", "20rpx"], ["paddingBottom", "8rpx"], ["paddingLeft", "20rpx"], ["borderTopWidth", "2rpx"], ["borderRightWidth", "2rpx"], ["borderBottomWidth", "2rpx"], ["borderLeftWidth", "2rpx"], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "rgba(255,255,255,0.6)"], ["borderRightColor", "rgba(255,255,255,0.6)"], ["borderBottomColor", "rgba(255,255,255,0.6)"], ["borderLeftColor", "rgba(255,255,255,0.6)"], ["borderTopLeftRadius", "24rpx"], ["borderTopRightRadius", "24rpx"], ["borderBottomRightRadius", "24rpx"], ["borderBottomLeftRadius", "24rpx"]]))], ["nav-action-text", padStyleMapOf(utsMapOf([["fontSize", "24rpx"], ["color", "#FFFFFF"]]))], ["balance-box", padStyleMapOf(utsMapOf([["paddingTop", "32rpx"], ["paddingRight", "32rpx"], ["paddingBottom", "32rpx"], ["paddingLeft", "32rpx"]]))], ["balance-header", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["justifyContent", "space-between"], ["alignItems", "center"], ["marginBottom", "16rpx"]]))], ["balance-label", padStyleMapOf(utsMapOf([["fontSize", "26rpx"], ["color", "rgba(255,255,255,0.8)"]]))], ["eye-btn", padStyleMapOf(utsMapOf([["paddingTop", "8rpx"], ["paddingRight", "16rpx"], ["paddingBottom", "8rpx"], ["paddingLeft", "16rpx"]]))], ["eye-text", padStyleMapOf(utsMapOf([["fontSize", "24rpx"], ["color", "rgba(255,255,255,0.8)"]]))], ["balance-amount", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "flex-end"], ["marginBottom", "40rpx"]]))], ["amount-symbol", padStyleMapOf(utsMapOf([["fontSize", "36rpx"], ["color", "#FFFFFF"], ["fontWeight", "700"], ["marginRight", "8rpx"], ["marginBottom", "12rpx"]]))], ["amount-num", padStyleMapOf(utsMapOf([["fontSize", "72rpx"], ["color", "#FFFFFF"], ["fontWeight", "700"]]))], ["balance-actions", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"]]))], ["action-btn-primary", padStyleMapOf(utsMapOf([["flex", 1], ["height", "80rpx"], ["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", "40rpx"], ["borderTopRightRadius", "40rpx"], ["borderBottomRightRadius", "40rpx"], ["borderBottomLeftRadius", "40rpx"], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"], ["marginRight", "24rpx"]]))], ["action-btn-primary-text", padStyleMapOf(utsMapOf([["fontSize", "28rpx"], ["color", "#0066CC"], ["fontWeight", "700"]]))], ["action-btn-outline", padStyleMapOf(utsMapOf([["flex", 1], ["height", "80rpx"], ["backgroundColor", "rgba(0,0,0,0)"], ["borderTopWidth", "2rpx"], ["borderRightWidth", "2rpx"], ["borderBottomWidth", "2rpx"], ["borderLeftWidth", "2rpx"], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "rgba(255,255,255,0.6)"], ["borderRightColor", "rgba(255,255,255,0.6)"], ["borderBottomColor", "rgba(255,255,255,0.6)"], ["borderLeftColor", "rgba(255,255,255,0.6)"], ["borderTopLeftRadius", "40rpx"], ["borderTopRightRadius", "40rpx"], ["borderBottomRightRadius", "40rpx"], ["borderBottomLeftRadius", "40rpx"], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["action-btn-outline-text", padStyleMapOf(utsMapOf([["fontSize", "28rpx"], ["color", "#FFFFFF"], ["fontWeight", "700"]]))], ["main-scroll", padStyleMapOf(utsMapOf([["flex", 1], ["marginTop", "-48rpx"]]))], ["stats-card", padStyleMapOf(utsMapOf([["marginTop", 0], ["marginRight", "32rpx"], ["marginBottom", "24rpx"], ["marginLeft", "32rpx"], ["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", "24rpx"], ["borderTopRightRadius", "24rpx"], ["borderBottomRightRadius", "24rpx"], ["borderBottomLeftRadius", "24rpx"], ["paddingTop", "32rpx"], ["paddingRight", "32rpx"], ["paddingBottom", "32rpx"], ["paddingLeft", "32rpx"], ["display", "flex"], ["flexDirection", "row"], ["justifyContent", "space-around"]]))], ["stats-item", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "column"], ["alignItems", "center"]]))], ["stats-num", padStyleMapOf(utsMapOf([["fontSize", "36rpx"], ["fontWeight", "700"], ["color", "#1A1A1A"], ["marginBottom", "8rpx"]]))], ["stats-num-green", padStyleMapOf(utsMapOf([["color", "#52C41A"]]))], ["stats-num-orange", padStyleMapOf(utsMapOf([["color", "#FF7A45"]]))], ["stats-name", padStyleMapOf(utsMapOf([["fontSize", "24rpx"], ["color", "#999999"]]))], ["stats-divider", padStyleMapOf(utsMapOf([["width", "2rpx"], ["height", "60rpx"], ["backgroundColor", "#F0F0F0"]]))], ["record-card", padStyleMapOf(utsMapOf([["marginTop", 0], ["marginRight", "32rpx"], ["marginBottom", "32rpx"], ["marginLeft", "32rpx"], ["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", "24rpx"], ["borderTopRightRadius", "24rpx"], ["borderBottomRightRadius", "24rpx"], ["borderBottomLeftRadius", "24rpx"], ["paddingTop", "32rpx"], ["paddingRight", "32rpx"], ["paddingBottom", "32rpx"], ["paddingLeft", "32rpx"]]))], ["record-header", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["justifyContent", "space-between"], ["alignItems", "center"], ["marginBottom", "24rpx"]]))], ["record-title", padStyleMapOf(utsMapOf([["fontSize", "32rpx"], ["fontWeight", "700"], ["color", "#1A1A1A"]]))], ["filter-tabs", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["backgroundColor", "#F5F7FA"], ["borderTopLeftRadius", "8rpx"], ["borderTopRightRadius", "8rpx"], ["borderBottomRightRadius", "8rpx"], ["borderBottomLeftRadius", "8rpx"], ["paddingTop", "4rpx"], ["paddingRight", "4rpx"], ["paddingBottom", "4rpx"], ["paddingLeft", "4rpx"]]))], ["filter-tab", padStyleMapOf(utsMapOf([["fontSize", "24rpx"], ["color", "#666666"], ["paddingTop", "8rpx"], ["paddingRight", "20rpx"], ["paddingBottom", "8rpx"], ["paddingLeft", "20rpx"], ["borderTopLeftRadius", "6rpx"], ["borderTopRightRadius", "6rpx"], ["borderBottomRightRadius", "6rpx"], ["borderBottomLeftRadius", "6rpx"]]))], ["filter-tab-active", padStyleMapOf(utsMapOf([["backgroundColor", "#0066CC"], ["color", "#FFFFFF"]]))], ["loading-box", padStyleMapOf(utsMapOf([["paddingTop", "60rpx"], ["paddingRight", 0], ["paddingBottom", "60rpx"], ["paddingLeft", 0], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["loading-text", padStyleMapOf(utsMapOf([["fontSize", "28rpx"], ["color", "#999999"]]))], ["record-list", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "column"]]))], ["record-item", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["paddingTop", "24rpx"], ["paddingRight", 0], ["paddingBottom", "24rpx"], ["paddingLeft", 0], ["borderBottomWidth", "1rpx"], ["borderBottomStyle", "solid"], ["borderBottomColor", "#F5F5F5"]]))], ["item-icon", padStyleMapOf(utsMapOf([["width", "80rpx"], ["height", "80rpx"], ["borderTopLeftRadius", "20rpx"], ["borderTopRightRadius", "20rpx"], ["borderBottomRightRadius", "20rpx"], ["borderBottomLeftRadius", "20rpx"], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"], ["marginRight", "24rpx"], ["backgroundColor", "#F5F7FA"]]))], ["item-icon-recharge", padStyleMapOf(utsMapOf([["backgroundColor", "#E6F7E6"]]))], ["item-icon-pay", padStyleMapOf(utsMapOf([["backgroundColor", "#FFF2E8"]]))], ["item-icon-refund", padStyleMapOf(utsMapOf([["backgroundColor", "#E8F4FF"]]))], ["icon-text", padStyleMapOf(utsMapOf([["fontSize", "36rpx"]]))], ["item-info", padStyleMapOf(utsMapOf([["flex", 1], ["display", "flex"], ["flexDirection", "column"]]))], ["item-title", padStyleMapOf(utsMapOf([["fontSize", "28rpx"], ["color", "#1A1A1A"], ["marginBottom", "4rpx"]]))], ["item-desc", padStyleMapOf(utsMapOf([["fontSize", "24rpx"], ["color", "#666666"], ["marginBottom", "4rpx"]]))], ["item-time", padStyleMapOf(utsMapOf([["fontSize", "24rpx"], ["color", "#999999"]]))], ["item-amount", padStyleMapOf(utsMapOf([["fontSize", "32rpx"], ["fontWeight", "700"], ["color", "#1A1A1A"]]))], ["item-amount-income", padStyleMapOf(utsMapOf([["color", "#52C41A"]]))], ["list-footer", padStyleMapOf(utsMapOf([["paddingTop", "32rpx"], ["paddingRight", 0], ["paddingBottom", "32rpx"], ["paddingLeft", 0], ["display", "flex"], ["justifyContent", "center"]]))], ["footer-text", padStyleMapOf(utsMapOf([["fontSize", "24rpx"], ["color", "#CCCCCC"]]))], ["empty-box", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "column"], ["alignItems", "center"], ["paddingTop", "80rpx"], ["paddingRight", 0], ["paddingBottom", "80rpx"], ["paddingLeft", 0]]))], ["empty-icon", padStyleMapOf(utsMapOf([["fontSize", "80rpx"], ["marginBottom", "16rpx"]]))], ["empty-text", padStyleMapOf(utsMapOf([["fontSize", "28rpx"], ["color", "#999999"]]))], ["modal-mask", padStyleMapOf(utsMapOf([["position", "fixed"], ["top", 0], ["left", 0], ["right", 0], ["bottom", 0], ["backgroundColor", "rgba(0,0,0,0.5)"], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"], ["zIndex", 999]]))], ["modal-content", padStyleMapOf(utsMapOf([["width", "600rpx"], ["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", "24rpx"], ["borderTopRightRadius", "24rpx"], ["borderBottomRightRadius", "24rpx"], ["borderBottomLeftRadius", "24rpx"], ["paddingTop", "40rpx"], ["paddingRight", "40rpx"], ["paddingBottom", "40rpx"], ["paddingLeft", "40rpx"]]))], ["modal-title", padStyleMapOf(utsMapOf([["fontSize", "36rpx"], ["fontWeight", "700"], ["color", "#1A1A1A"], ["textAlign", "center"], ["marginBottom", "40rpx"]]))], ["amount-options", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["flexWrap", "wrap"], ["justifyContent", "space-between"], ["marginBottom", "32rpx"]]))], ["amount-option", padStyleMapOf(utsMapOf([["width", "48%"], ["height", "80rpx"], ["backgroundColor", "#F5F7FA"], ["borderTopLeftRadius", "12rpx"], ["borderTopRightRadius", "12rpx"], ["borderBottomRightRadius", "12rpx"], ["borderBottomLeftRadius", "12rpx"], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"], ["marginBottom", "16rpx"], ["borderTopWidth", "2rpx"], ["borderRightWidth", "2rpx"], ["borderBottomWidth", "2rpx"], ["borderLeftWidth", "2rpx"], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "rgba(0,0,0,0)"], ["borderRightColor", "rgba(0,0,0,0)"], ["borderBottomColor", "rgba(0,0,0,0)"], ["borderLeftColor", "rgba(0,0,0,0)"]]))], ["amount-option-active", padStyleMapOf(utsMapOf([["backgroundColor", "#E6F0FF"], ["borderTopColor", "#0066CC"], ["borderRightColor", "#0066CC"], ["borderBottomColor", "#0066CC"], ["borderLeftColor", "#0066CC"]]))], ["option-text", padStyleMapOf(utsMapOf([["fontSize", "28rpx"], ["color", "#333333"]]))], ["option-text-active", padStyleMapOf(utsMapOf([["color", "#0066CC"], ["fontWeight", "700"]]))], ["custom-input-box", padStyleMapOf(utsMapOf([["marginBottom", "40rpx"]]))], ["input-label", padStyleMapOf(utsMapOf([["fontSize", "26rpx"], ["color", "#666666"], ["marginBottom", "12rpx"]]))], ["custom-input", padStyleMapOf(utsMapOf([["height", "80rpx"], ["backgroundColor", "#F5F7FA"], ["borderTopLeftRadius", "12rpx"], ["borderTopRightRadius", "12rpx"], ["borderBottomRightRadius", "12rpx"], ["borderBottomLeftRadius", "12rpx"], ["paddingTop", 0], ["paddingRight", "24rpx"], ["paddingBottom", 0], ["paddingLeft", "24rpx"], ["fontSize", "28rpx"]]))], ["modal-actions", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["justifyContent", "space-between"]]))], ["modal-btn", padStyleMapOf(utsMapOf([["flex", 1], ["height", "88rpx"], ["borderTopLeftRadius", "44rpx"], ["borderTopRightRadius", "44rpx"], ["borderBottomRightRadius", "44rpx"], ["borderBottomLeftRadius", "44rpx"], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["modal-btn-cancel", padStyleMapOf(utsMapOf([["backgroundColor", "#F5F7FA"], ["marginRight", "24rpx"]]))], ["modal-btn-confirm", padStyleMapOf(utsMapOf([["backgroundColor", "#0066CC"]]))], ["btn-text", padStyleMapOf(utsMapOf([["fontSize", "30rpx"], ["color", "#666666"]]))], ["btn-text-white", padStyleMapOf(utsMapOf([["fontSize", "30rpx"], ["color", "#FFFFFF"], ["fontWeight", "700"]]))]])]
