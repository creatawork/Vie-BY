@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME", "UNUSED_ANONYMOUS_PARAMETER", "NAME_SHADOWING", "UNNECESSARY_NOT_NULL_ASSERTION")
package uni.UNIBY001
import io.dcloud.uniapp.*
import io.dcloud.uniapp.extapi.*
import io.dcloud.uniapp.framework.*
import io.dcloud.uniapp.runtime.*
import io.dcloud.uniapp.vue.*
import io.dcloud.uniapp.vue.shared.*
import io.dcloud.unicloud.*
import io.dcloud.uts.*
import io.dcloud.uts.Map
import io.dcloud.uts.Set
import io.dcloud.uts.UTSAndroid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import io.dcloud.uniapp.extapi.getStorageSync as uni_getStorageSync
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.removeStorageSync as uni_removeStorageSync
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
open class GenPagesSearchIndex : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onLoad(fun(_: OnLoadOptions) {
            val storedHistory = uni_getStorageSync("search_history") as String?
            if (storedHistory != null && storedHistory != "") {
            }
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return createElementVNode("view", utsMapOf("class" to "search-page"), utsArrayOf(
            createElementVNode("view", utsMapOf("class" to "header"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "header-content"), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "back-btn", "onClick" to _ctx.goBack), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "back-icon"), "←")
                    ), 8, utsArrayOf(
                        "onClick"
                    )),
                    createElementVNode("view", utsMapOf("class" to "search-input-box"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "search-icon"), "🔍"),
                        createElementVNode("input", utsMapOf("class" to "search-input", "modelValue" to _ctx.keyword, "onInput" to fun(`$event`: InputEvent){
                            _ctx.keyword = `$event`.detail.value
                        }
                        , "placeholder" to "搜索商品", "confirm-type" to "search", "focus" to true, "onConfirm" to _ctx.handleSearch), null, 40, utsArrayOf(
                            "modelValue",
                            "onInput",
                            "onConfirm"
                        )),
                        if (_ctx.keyword.length > 0) {
                            createElementVNode("view", utsMapOf("key" to 0, "class" to "clear-btn", "onClick" to _ctx.clearKeyword), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "clear-icon"), "✕")
                            ), 8, utsArrayOf(
                                "onClick"
                            ))
                        } else {
                            createCommentVNode("v-if", true)
                        }
                    )),
                    createElementVNode("text", utsMapOf("class" to "search-btn", "onClick" to _ctx.handleSearch), "搜索", 8, utsArrayOf(
                        "onClick"
                    ))
                ))
            )),
            createElementVNode("view", utsMapOf("class" to "content"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "section"), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "section-header"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "section-title"), "热门搜索")
                    )),
                    createElementVNode("view", utsMapOf("class" to "tags-container"), utsArrayOf(
                        createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.hotKeywords, fun(item, index, __index, _cached): Any {
                            return createElementVNode("text", utsMapOf("class" to "tag tag-hot", "key" to index, "onClick" to fun(){
                                _ctx.doSearch(item)
                            }
                            ), toDisplayString(item), 9, utsArrayOf(
                                "onClick"
                            ))
                        }
                        ), 128)
                    ))
                )),
                if (_ctx.history.length > 0) {
                    createElementVNode("view", utsMapOf("key" to 0, "class" to "section"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "section-header"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "section-title"), "历史搜索"),
                            createElementVNode("text", utsMapOf("class" to "clear-history", "onClick" to _ctx.clearHistory), "🗑️", 8, utsArrayOf(
                                "onClick"
                            ))
                        )),
                        createElementVNode("view", utsMapOf("class" to "tags-container"), utsArrayOf(
                            createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.history, fun(item, index, __index, _cached): Any {
                                return createElementVNode("text", utsMapOf("class" to "tag", "key" to index, "onClick" to fun(){
                                    _ctx.doSearch(item)
                                }), toDisplayString(item), 9, utsArrayOf(
                                    "onClick"
                                ))
                            }), 128)
                        ))
                    ))
                } else {
                    createCommentVNode("v-if", true)
                }
            ))
        ))
    }
    open var keyword: String by `$data`
    open var hotKeywords: UTSArray<String> by `$data`
    open var history: UTSArray<String> by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return utsMapOf("keyword" to "", "hotKeywords" to utsArrayOf<String>(), "history" to utsArrayOf<String>())
    }
    open var goBack = ::gen_goBack_fn
    open fun gen_goBack_fn() {
        uni_navigateBack(NavigateBackOptions())
    }
    open var clearKeyword = ::gen_clearKeyword_fn
    open fun gen_clearKeyword_fn() {
        this.keyword = ""
    }
    open var handleSearch = ::gen_handleSearch_fn
    open fun gen_handleSearch_fn() {
        val trimmedKeyword = this.keyword.trim()
        if (trimmedKeyword == "") {
            return
        }
        this.doSearch(this.keyword)
    }
    open var doSearch = ::gen_doSearch_fn
    open fun gen_doSearch_fn(key: String) {
        this.keyword = key
        this.saveHistory(key)
        uni_navigateTo(NavigateToOptions(url = "/pages/product/list?keyword=" + key))
    }
    open var saveHistory = ::gen_saveHistory_fn
    open fun gen_saveHistory_fn(key: String) {
        val index = this.history.indexOf(key)
        if (index > -1) {
            this.history.splice(index, 1)
        }
        this.history.unshift(key)
        if (this.history.length > 10) {
            this.history.pop()
        }
        uni_setStorageSync("search_history", JSON.stringify(this.history))
    }
    open var clearHistory = ::gen_clearHistory_fn
    open fun gen_clearHistory_fn() {
        this.history = utsArrayOf()
        uni_removeStorageSync("search_history")
    }
    companion object {
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            normalizeCssStyles(utsArrayOf(
                styles0
            ), utsArrayOf(
                GenApp.styles
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return utsMapOf("search-page" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "display" to "flex", "flexDirection" to "column")), "header" to padStyleMapOf(utsMapOf("paddingTop" to CSS_VAR_STATUS_BAR_HEIGHT, "backgroundColor" to "#ffffff", "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "#f0f0f0")), "header-content" to padStyleMapOf(utsMapOf("height" to 44, "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "paddingTop" to 0, "paddingRight" to 12, "paddingBottom" to 0, "paddingLeft" to 12)), "back-btn" to padStyleMapOf(utsMapOf("width" to 30, "height" to 30, "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "back-icon" to padStyleMapOf(utsMapOf("fontSize" to 20, "color" to "#333333")), "search-input-box" to padStyleMapOf(utsMapOf("flex" to 1, "height" to 32, "backgroundColor" to "#f5f5f5", "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "paddingTop" to 0, "paddingRight" to 10, "paddingBottom" to 0, "paddingLeft" to 10)), "search-icon" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#999999", "marginRight" to 6)), "search-input" to padStyleMapOf(utsMapOf("flex" to 1, "fontSize" to 14, "color" to "#333333", "backgroundColor" to "rgba(0,0,0,0)")), "clear-btn" to padStyleMapOf(utsMapOf("paddingTop" to 4, "paddingRight" to 4, "paddingBottom" to 4, "paddingLeft" to 4)), "clear-icon" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999")), "search-btn" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#0066CC", "fontWeight" to "400", "paddingTop" to 0, "paddingRight" to 4, "paddingBottom" to 0, "paddingLeft" to 4)), "content" to padStyleMapOf(utsMapOf("paddingTop" to 20, "paddingRight" to 16, "paddingBottom" to 20, "paddingLeft" to 16)), "section" to padStyleMapOf(utsMapOf("marginBottom" to 24)), "section-header" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "marginBottom" to 12)), "section-title" to padStyleMapOf(utsMapOf("fontSize" to 14, "fontWeight" to "bold", "color" to "#333333")), "clear-history" to padStyleMapOf(utsMapOf("fontSize" to 14)), "tags-container" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "flexWrap" to "wrap")), "tag" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#666666", "backgroundColor" to "#f5f5f5", "paddingTop" to 6, "paddingRight" to 12, "paddingBottom" to 6, "paddingLeft" to 12, "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "marginRight" to 10, "marginBottom" to 10)), "tag-hot" to padStyleMapOf(utsMapOf("color" to "#0066CC", "backgroundColor" to "rgba(0,102,204,0.05)")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = utsMapOf()
        var emits: Map<String, Any?> = utsMapOf()
        var props = normalizePropsOptions(utsMapOf())
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf()
        var components: Map<String, CreateVueComponent> = utsMapOf()
    }
}
