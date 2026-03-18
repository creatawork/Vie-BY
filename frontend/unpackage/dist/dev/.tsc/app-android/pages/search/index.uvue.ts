
const __sfc__ = defineComponent({
  data() {
    return {
      keyword: '',
      hotKeywords: [] as string[],
      history: [] as string[]
    }
  },
  onLoad() {
    // 读取历史记录
    const storedHistory = uni.getStorageSync('search_history') as string | null
    if (storedHistory != null && storedHistory != '') {
      try {
        // UTS 中 JSON.parse 需要处理类型转换
        // 这里简化处理，假设存储的是 JSON 字符串
        // 实际 UTS 中可能需要更严谨的类型转换
        // this.history = JSON.parse(storedHistory as string)
      } catch (e) {}
    }
  },
  methods: {
    goBack() {
      uni.navigateBack({})
    },
    clearKeyword() {
      this.keyword = ''
    },
    handleSearch() {
      const trimmedKeyword = this.keyword.trim()
      if (trimmedKeyword == '') return
      this.doSearch(this.keyword)
    },
    doSearch(key : string) {
      this.keyword = key
      this.saveHistory(key)
      // 跳转到商品列表页并传递搜索关键词
      uni.navigateTo({
        url: `/pages/product/list?keyword=${key}`
      })
    },
    saveHistory(key : string) {
      // 简单去重并添加到头部
      const index = this.history.indexOf(key)
      if (index > -1) {
        this.history.splice(index, 1)
      }
      this.history.unshift(key)
      if (this.history.length > 10) {
        this.history.pop()
      }
      uni.setStorageSync('search_history', JSON.stringify(this.history))
    },
    clearHistory() {
      this.history = []
      uni.removeStorageSync('search_history')
    }
  }
})

export default __sfc__
function GenPagesSearchIndexRender(this: InstanceType<typeof __sfc__>): any | null {
const _ctx = this
const _cache = this.$.renderCache
  return createElementVNode("view", utsMapOf({ class: "search-page" }), [
    createElementVNode("view", utsMapOf({ class: "header" }), [
      createElementVNode("view", utsMapOf({ class: "header-content" }), [
        createElementVNode("view", utsMapOf({
          class: "back-btn",
          onClick: _ctx.goBack
        }), [
          createElementVNode("text", utsMapOf({ class: "back-icon" }), "←")
        ], 8 /* PROPS */, ["onClick"]),
        createElementVNode("view", utsMapOf({ class: "search-input-box" }), [
          createElementVNode("text", utsMapOf({ class: "search-icon" }), "🔍"),
          createElementVNode("input", utsMapOf({
            class: "search-input",
            modelValue: _ctx.keyword,
            onInput: ($event: InputEvent) => {(_ctx.keyword) = $event.detail.value},
            placeholder: "搜索商品",
            "confirm-type": "search",
            focus: true,
            onConfirm: _ctx.handleSearch
          }), null, 40 /* PROPS, NEED_HYDRATION */, ["modelValue", "onInput", "onConfirm"]),
          _ctx.keyword.length > 0
            ? createElementVNode("view", utsMapOf({
                key: 0,
                class: "clear-btn",
                onClick: _ctx.clearKeyword
              }), [
                createElementVNode("text", utsMapOf({ class: "clear-icon" }), "✕")
              ], 8 /* PROPS */, ["onClick"])
            : createCommentVNode("v-if", true)
        ]),
        createElementVNode("text", utsMapOf({
          class: "search-btn",
          onClick: _ctx.handleSearch
        }), "搜索", 8 /* PROPS */, ["onClick"])
      ])
    ]),
    createElementVNode("view", utsMapOf({ class: "content" }), [
      createElementVNode("view", utsMapOf({ class: "section" }), [
        createElementVNode("view", utsMapOf({ class: "section-header" }), [
          createElementVNode("text", utsMapOf({ class: "section-title" }), "热门搜索")
        ]),
        createElementVNode("view", utsMapOf({ class: "tags-container" }), [
          createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.hotKeywords, (item, index, __index, _cached): any => {
            return createElementVNode("text", utsMapOf({
              class: "tag tag-hot",
              key: index,
              onClick: () => {_ctx.doSearch(item)}
            }), toDisplayString(item), 9 /* TEXT, PROPS */, ["onClick"])
          }), 128 /* KEYED_FRAGMENT */)
        ])
      ]),
      _ctx.history.length > 0
        ? createElementVNode("view", utsMapOf({
            key: 0,
            class: "section"
          }), [
            createElementVNode("view", utsMapOf({ class: "section-header" }), [
              createElementVNode("text", utsMapOf({ class: "section-title" }), "历史搜索"),
              createElementVNode("text", utsMapOf({
                class: "clear-history",
                onClick: _ctx.clearHistory
              }), "🗑️", 8 /* PROPS */, ["onClick"])
            ]),
            createElementVNode("view", utsMapOf({ class: "tags-container" }), [
              createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.history, (item, index, __index, _cached): any => {
                return createElementVNode("text", utsMapOf({
                  class: "tag",
                  key: index,
                  onClick: () => {_ctx.doSearch(item)}
                }), toDisplayString(item), 9 /* TEXT, PROPS */, ["onClick"])
              }), 128 /* KEYED_FRAGMENT */)
            ])
          ])
        : createCommentVNode("v-if", true)
    ])
  ])
}
const GenPagesSearchIndexStyles = [utsMapOf([["search-page", padStyleMapOf(utsMapOf([["backgroundColor", "#ffffff"], ["display", "flex"], ["flexDirection", "column"]]))], ["header", padStyleMapOf(utsMapOf([["paddingTop", CSS_VAR_STATUS_BAR_HEIGHT], ["backgroundColor", "#ffffff"], ["borderBottomWidth", 1], ["borderBottomStyle", "solid"], ["borderBottomColor", "#f0f0f0"]]))], ["header-content", padStyleMapOf(utsMapOf([["height", 44], ["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["paddingTop", 0], ["paddingRight", 12], ["paddingBottom", 0], ["paddingLeft", 12]]))], ["back-btn", padStyleMapOf(utsMapOf([["width", 30], ["height", 30], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["back-icon", padStyleMapOf(utsMapOf([["fontSize", 20], ["color", "#333333"]]))], ["search-input-box", padStyleMapOf(utsMapOf([["flex", 1], ["height", 32], ["backgroundColor", "#f5f5f5"], ["borderTopLeftRadius", 16], ["borderTopRightRadius", 16], ["borderBottomRightRadius", 16], ["borderBottomLeftRadius", 16], ["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["paddingTop", 0], ["paddingRight", 10], ["paddingBottom", 0], ["paddingLeft", 10]]))], ["search-icon", padStyleMapOf(utsMapOf([["fontSize", 14], ["color", "#999999"], ["marginRight", 6]]))], ["search-input", padStyleMapOf(utsMapOf([["flex", 1], ["fontSize", 14], ["color", "#333333"], ["backgroundColor", "rgba(0,0,0,0)"]]))], ["clear-btn", padStyleMapOf(utsMapOf([["paddingTop", 4], ["paddingRight", 4], ["paddingBottom", 4], ["paddingLeft", 4]]))], ["clear-icon", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "#999999"]]))], ["search-btn", padStyleMapOf(utsMapOf([["fontSize", 14], ["color", "#0066CC"], ["fontWeight", "400"], ["paddingTop", 0], ["paddingRight", 4], ["paddingBottom", 0], ["paddingLeft", 4]]))], ["content", padStyleMapOf(utsMapOf([["paddingTop", 20], ["paddingRight", 16], ["paddingBottom", 20], ["paddingLeft", 16]]))], ["section", padStyleMapOf(utsMapOf([["marginBottom", 24]]))], ["section-header", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["justifyContent", "space-between"], ["alignItems", "center"], ["marginBottom", 12]]))], ["section-title", padStyleMapOf(utsMapOf([["fontSize", 14], ["fontWeight", "bold"], ["color", "#333333"]]))], ["clear-history", padStyleMapOf(utsMapOf([["fontSize", 14]]))], ["tags-container", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["flexWrap", "wrap"]]))], ["tag", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "#666666"], ["backgroundColor", "#f5f5f5"], ["paddingTop", 6], ["paddingRight", 12], ["paddingBottom", 6], ["paddingLeft", 12], ["borderTopLeftRadius", 14], ["borderTopRightRadius", 14], ["borderBottomRightRadius", 14], ["borderBottomLeftRadius", 14], ["marginRight", 10], ["marginBottom", 10]]))], ["tag-hot", padStyleMapOf(utsMapOf([["color", "#0066CC"], ["backgroundColor", "rgba(0,102,204,0.05)"]]))]])]
