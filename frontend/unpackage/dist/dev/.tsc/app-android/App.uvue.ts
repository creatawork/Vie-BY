
	let firstBackTime = 0
	const __sfc__ = defineApp({
		onLaunch: function () {
			console.log('App Launch', " at App.uvue:5")
		},
		onShow: function () {
			console.log('App Show', " at App.uvue:8")
		},
		onHide: function () {
			console.log('App Hide', " at App.uvue:11")
		},

		onLastPageBackPress: function () {
			console.log('App LastPageBackPress', " at App.uvue:15")
			if (firstBackTime == 0) {
				uni.showToast({
					title: '再按一次退出应用',
					position: 'bottom',
				})
				firstBackTime = Date.now()
				setTimeout(() => {
					firstBackTime = 0
				}, 2000)
			} else if (Date.now() - firstBackTime < 2000) {
				firstBackTime = Date.now()
				uni.exit()
			}
		},

		onExit: function () {
			console.log('App Exit', " at App.uvue:32")
		},
	})

export default __sfc__
const GenAppStyles = [utsMapOf([["app-root-page", padStyleMapOf(utsMapOf([["backgroundColor", "#F5F7FA"], ["fontFamily", "-apple-system, BlinkMacSystemFont, Helvetica Neue, Helvetica, Segoe UI, Arial, Roboto, PingFang SC, miui, Hiragino Sans GB, Microsoft Yahei, sans-serif"], ["color", "#333333"], ["fontSize", 14], ["lineHeight", 1.5]]))], ["uni-row", padStyleMapOf(utsMapOf([["flexDirection", "row"], ["display", "flex"]]))], ["uni-column", padStyleMapOf(utsMapOf([["flexDirection", "column"], ["display", "flex"]]))], ["flex-row", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"]]))], ["flex-col", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "column"]]))], ["justify-between", padStyleMapOf(utsMapOf([["justifyContent", "space-between"]]))], ["justify-center", padStyleMapOf(utsMapOf([["justifyContent", "center"]]))], ["justify-end", padStyleMapOf(utsMapOf([["justifyContent", "flex-end"]]))], ["align-center", padStyleMapOf(utsMapOf([["alignItems", "center"]]))], ["card", padStyleMapOf(utsMapOf([["backgroundColor", "#ffffff"], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12], ["paddingTop", 16], ["paddingRight", 16], ["paddingBottom", 16], ["paddingLeft", 16], ["marginBottom", 16], ["boxShadow", "0 2px 8px rgba(0, 0, 0, 0.05)"]]))], ["btn", utsMapOf([["", utsMapOf([["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"], ["height", 44], ["borderTopLeftRadius", 22], ["borderTopRightRadius", 22], ["borderBottomRightRadius", 22], ["borderBottomLeftRadius", 22], ["fontSize", 16], ["fontWeight", "700"], ["transitionDuration", "0.3s"]])], [".btn-primary", utsMapOf([["backgroundColor", "#0066CC"], ["color", "#ffffff"], ["boxShadow", "0 4px 12px rgba(0, 102, 204, 0.3)"]])], [".btn-secondary", utsMapOf([["backgroundColor", "#52C41A"], ["color", "#ffffff"], ["boxShadow", "0 4px 12px rgba(82, 196, 26, 0.3)"]])], [".btn-outline", utsMapOf([["backgroundColor", "rgba(0,0,0,0)"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#0066CC"], ["borderRightColor", "#0066CC"], ["borderBottomColor", "#0066CC"], ["borderLeftColor", "#0066CC"], ["color", "#0066CC"]])]])], ["input-group", padStyleMapOf(utsMapOf([["marginBottom", 24]]))], ["input-label", utsMapOf([[".input-group ", utsMapOf([["fontSize", 14], ["color", "#333333"], ["marginBottom", 8], ["fontWeight", "400"]])]])], ["input-box", utsMapOf([[".input-group ", utsMapOf([["height", 48], ["backgroundColor", "#F5F7FA"], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["paddingTop", 0], ["paddingRight", 16], ["paddingBottom", 0], ["paddingLeft", 16], ["fontSize", 14], ["color", "#333333"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "rgba(0,0,0,0)"], ["borderRightColor", "rgba(0,0,0,0)"], ["borderBottomColor", "rgba(0,0,0,0)"], ["borderLeftColor", "rgba(0,0,0,0)"], ["transitionDuration", "0.3s"], ["backgroundColor:focus", "#ffffff"], ["borderTopColor:focus", "#0066CC"], ["borderRightColor:focus", "#0066CC"], ["borderBottomColor:focus", "#0066CC"], ["borderLeftColor:focus", "#0066CC"], ["boxShadow:focus", "0 0 0 2px rgba(0, 102, 204, 0.1)"]])]])], ["page-container", padStyleMapOf(utsMapOf([["flex", 1], ["backgroundColor", "#F5F7FA"], ["paddingTop", 16], ["paddingRight", 16], ["paddingBottom", 16], ["paddingLeft", 16], ["boxSizing", "border-box"]]))], ["text-ellipsis", padStyleMapOf(utsMapOf([["overflow", "hidden"], ["textOverflow", "ellipsis"], ["whiteSpace", "nowrap"]]))], ["text-ellipsis-2", padStyleMapOf(utsMapOf([["overflow", "hidden"], ["textOverflow", "ellipsis"], ["whiteSpace", "normal"], ["lineHeight", 1.4], ["maxHeight", 40]]))], ["@TRANSITION", utsMapOf([["btn", utsMapOf([["duration", "0.3s"]])], ["input-box", utsMapOf([["duration", "0.3s"]])]])]])]
