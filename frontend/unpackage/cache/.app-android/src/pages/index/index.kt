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
import io.dcloud.uniapp.extapi.`$emit` as uni__emit
import io.dcloud.uniapp.extapi.chooseLocation as uni_chooseLocation
import io.dcloud.uniapp.extapi.getStorageSync as uni_getStorageSync
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.reLaunch as uni_reLaunch
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesIndexIndex : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onLoad(fun(_: OnLoadOptions) {
            val userInfoStr = uni_getStorageSync("user_info") as String
            if (userInfoStr != null && userInfoStr != "") {
                try {
                    val userInfo = UTSAndroid.consoleDebugError(JSON.parse(userInfoStr), " at pages/index/index.uvue:252") as UTSJSONObject
                    val roleCodes = userInfo.getAny("roleCodes")
                    if (roleCodes != null) {
                        val roles = roleCodes as UTSArray<Any>
                        if (roles.includes("ROLE_ADMIN") || roles.includes("ADMIN")) {
                            uni_reLaunch(ReLaunchOptions(url = "/pages/seller/admin-index"))
                            return
                        }
                    }
                }
                 catch (e: Throwable) {
                    console.error("解析用户信息失败:", e, " at pages/index/index.uvue:264")
                }
            }
            this.isCheckingAuth = false
            this.initLocation()
            this.loadHotProducts()
        }
        , __ins)
        onPageShow(fun() {
            uni__emit("bottomNavRefresh", null)
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        val _component_BottomNav = resolveComponent("BottomNav")
        return createElementVNode("view", utsMapOf("class" to "page"), utsArrayOf(
            if (isTrue(_ctx.isCheckingAuth)) {
                createElementVNode("view", utsMapOf("key" to 0, "class" to "auth-loading"), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "loading-bg"))
                ))
            } else {
                createElementVNode(Fragment, utsMapOf("key" to 1), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "bg-decoration")),
                    createElementVNode("view", utsMapOf("class" to "header"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "header-content"), utsArrayOf(
                            createElementVNode("view", utsMapOf("class" to "location-box"), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "location-icon"), "📍"),
                                createElementVNode("text", utsMapOf("class" to "location-text", "onClick" to _ctx.chooseLocation), toDisplayString(_ctx.currentLocation), 9, utsArrayOf(
                                    "onClick"
                                )),
                                createElementVNode("text", utsMapOf("class" to "arrow-icon"), ">")
                            )),
                            createElementVNode("view", utsMapOf("class" to "search-bar", "onClick" to _ctx.goToSearch), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "search-icon"), "🔍"),
                                createElementVNode("text", utsMapOf("class" to "search-placeholder"), "搜索新鲜果蔬、肉蛋奶...")
                            ), 8, utsArrayOf(
                                "onClick"
                            ))
                        ))
                    )),
                    createElementVNode("scroll-view", utsMapOf("class" to "content-scroll", "scroll-y" to "true", "show-scrollbar" to "false"), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "scroll-content"), utsArrayOf(
                            createElementVNode("view", utsMapOf("class" to "swiper-section"), utsArrayOf(
                                createElementVNode("swiper", utsMapOf("class" to "main-swiper", "autoplay" to true, "interval" to 4000, "circular" to true, "indicator-dots" to "true", "indicator-color" to "rgba(255,255,255,0.6)", "indicator-active-color" to "#ffffff"), utsArrayOf(
                                    createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.banners, fun(banner, index, __index, _cached): Any {
                                        return createElementVNode("swiper-item", utsMapOf("key" to index, "class" to "swiper-item", "onClick" to fun(){
                                            _ctx.goToBanner(banner)
                                        }
                                        ), utsArrayOf(
                                            createElementVNode("image", utsMapOf("class" to "swiper-image", "src" to banner.image, "mode" to "aspectFill"), null, 8, utsArrayOf(
                                                "src"
                                            ))
                                        ), 8, utsArrayOf(
                                            "onClick"
                                        ))
                                    }
                                    ), 128)
                                ))
                            )),
                            createElementVNode("view", utsMapOf("class" to "service-bar"), utsArrayOf(
                                createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.services, fun(item, index, __index, _cached): Any {
                                    return createElementVNode("view", utsMapOf("class" to "service-item", "key" to index), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to "service-icon"), "✓"),
                                        createElementVNode("text", utsMapOf("class" to "service-text"), toDisplayString(item), 1)
                                    ))
                                }
                                ), 128)
                            )),
                            createElementVNode("view", utsMapOf("class" to "category-section"), utsArrayOf(
                                createElementVNode("view", utsMapOf("class" to "category-grid"), utsArrayOf(
                                    createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.categories, fun(category, index, __index, _cached): Any {
                                        return createElementVNode("view", utsMapOf("class" to "category-item", "key" to index, "onClick" to fun(){
                                            _ctx.goToCategory(category)
                                        }
                                        ), utsArrayOf(
                                            createElementVNode("view", utsMapOf("class" to "category-icon-box", "style" to normalizeStyle(utsMapOf("backgroundColor" to category.bgColor))), utsArrayOf(
                                                createElementVNode("text", utsMapOf("class" to "category-emoji"), toDisplayString(category.icon), 1)
                                            ), 4),
                                            createElementVNode("text", utsMapOf("class" to "category-name"), toDisplayString(category.name), 1)
                                        ), 8, utsArrayOf(
                                            "onClick"
                                        ))
                                    }
                                    ), 128)
                                ))
                            )),
                            createElementVNode("view", utsMapOf("class" to "activity-section"), utsArrayOf(
                                createElementVNode("view", utsMapOf("class" to "activity-card activity-card-big", "onClick" to _ctx.goToSpecial), utsArrayOf(
                                    createElementVNode("view", utsMapOf("class" to "activity-info"), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to "activity-title"), "县域特色"),
                                        createElementVNode("text", utsMapOf("class" to "activity-desc"), "源头直采 鲜味直达"),
                                        createElementVNode("text", utsMapOf("class" to "activity-btn"), "去逛逛")
                                    )),
                                    createElementVNode("image", utsMapOf("class" to "activity-img", "src" to "https://picsum.photos/150/150?random=11", "mode" to "aspectFit"))
                                ), 8, utsArrayOf(
                                    "onClick"
                                )),
                                createElementVNode("view", utsMapOf("class" to "activity-col activity-col-spaced"), utsArrayOf(
                                    createElementVNode("view", utsMapOf("class" to "activity-card activity-card-small", "onClick" to _ctx.goToDiscount), utsArrayOf(
                                        createElementVNode("view", utsMapOf("class" to "activity-info"), utsArrayOf(
                                            createElementVNode("text", utsMapOf("class" to "activity-title activity-title-highlight"), "限时特惠"),
                                            createElementVNode("text", utsMapOf("class" to "activity-desc"), "低至5折起")
                                        )),
                                        createElementVNode("image", utsMapOf("class" to "activity-img", "src" to "https://picsum.photos/100/100?random=12", "mode" to "aspectFit"))
                                    ), 8, utsArrayOf(
                                        "onClick"
                                    )),
                                    createElementVNode("view", utsMapOf("class" to "activity-card activity-card-small", "onClick" to _ctx.goToNew), utsArrayOf(
                                        createElementVNode("view", utsMapOf("class" to "activity-info"), utsArrayOf(
                                            createElementVNode("text", utsMapOf("class" to "activity-title"), "新品尝鲜"),
                                            createElementVNode("text", utsMapOf("class" to "activity-desc"), "每日上新")
                                        )),
                                        createElementVNode("image", utsMapOf("class" to "activity-img", "src" to "https://picsum.photos/100/100?random=13", "mode" to "aspectFit"))
                                    ), 8, utsArrayOf(
                                        "onClick"
                                    ))
                                ))
                            )),
                            createElementVNode("view", utsMapOf("class" to "recommend-section"), utsArrayOf(
                                createElementVNode("view", utsMapOf("class" to "section-header"), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "section-title"), "热销推荐"),
                                    createElementVNode("view", utsMapOf("class" to "section-more", "onClick" to _ctx.goToRecommend), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to "section-more__text"), "查看更多"),
                                        createElementVNode("text", utsMapOf("class" to "section-more__arrow"), ">")
                                    ), 8, utsArrayOf(
                                        "onClick"
                                    ))
                                )),
                                createElementVNode("view", utsMapOf("class" to "product-waterfall"), utsArrayOf(
                                    createElementVNode("view", utsMapOf("class" to "waterfall-col"), utsArrayOf(
                                        createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.leftColumnProducts, fun(product, __key, __index, _cached): Any {
                                            return createElementVNode("view", utsMapOf("class" to "product-card", "key" to product.id, "onClick" to fun(){
                                                _ctx.goToProduct(product.id)
                                            }
                                            ), utsArrayOf(
                                                createElementVNode("view", utsMapOf("class" to "product-img-box"), utsArrayOf(
                                                    createElementVNode("image", utsMapOf("class" to "product-img", "src" to product.image, "mode" to "widthFix"), null, 8, utsArrayOf(
                                                        "src"
                                                    )),
                                                    if (isTrue(product.tag)) {
                                                        createElementVNode("view", utsMapOf("key" to 0, "class" to "product-tag"), utsArrayOf(
                                                            createElementVNode("text", utsMapOf("class" to "product-tag__text"), toDisplayString(product.tag), 1)
                                                        ))
                                                    } else {
                                                        createCommentVNode("v-if", true)
                                                    }
                                                )),
                                                createElementVNode("view", utsMapOf("class" to "product-info"), utsArrayOf(
                                                    createElementVNode("text", utsMapOf("class" to "product-name"), toDisplayString(product.name), 1),
                                                    createElementVNode("text", utsMapOf("class" to "product-desc"), toDisplayString(product.desc), 1),
                                                    createElementVNode("view", utsMapOf("class" to "product-bottom"), utsArrayOf(
                                                        createElementVNode("view", utsMapOf("class" to "price-box"), utsArrayOf(
                                                            createElementVNode("text", utsMapOf("class" to "currency"), "¥"),
                                                            createElementVNode("text", utsMapOf("class" to "price"), toDisplayString(product.price), 1),
                                                            if (isTrue(product.originPrice)) {
                                                                createElementVNode("text", utsMapOf("key" to 0, "class" to "origin-price"), "¥" + toDisplayString(product.originPrice), 1)
                                                            } else {
                                                                createCommentVNode("v-if", true)
                                                            }
                                                        )),
                                                        createElementVNode("view", utsMapOf("class" to "add-btn", "onClick" to withModifiers(fun(){
                                                            _ctx.addToCart(product)
                                                        }
                                                        , utsArrayOf(
                                                            "stop"
                                                        ))), utsArrayOf(
                                                            createElementVNode("text", utsMapOf("class" to "add-btn__icon"), "+")
                                                        ), 8, utsArrayOf(
                                                            "onClick"
                                                        ))
                                                    ))
                                                ))
                                            ), 8, utsArrayOf(
                                                "onClick"
                                            ))
                                        }
                                        ), 128)
                                    )),
                                    createElementVNode("view", utsMapOf("class" to "waterfall-col waterfall-col-gap"), utsArrayOf(
                                        createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.rightColumnProducts, fun(product, __key, __index, _cached): Any {
                                            return createElementVNode("view", utsMapOf("class" to "product-card", "key" to product.id, "onClick" to fun(){
                                                _ctx.goToProduct(product.id)
                                            }
                                            ), utsArrayOf(
                                                createElementVNode("view", utsMapOf("class" to "product-img-box"), utsArrayOf(
                                                    createElementVNode("image", utsMapOf("class" to "product-img", "src" to product.image, "mode" to "widthFix"), null, 8, utsArrayOf(
                                                        "src"
                                                    )),
                                                    if (isTrue(product.tag)) {
                                                        createElementVNode("view", utsMapOf("key" to 0, "class" to "product-tag"), utsArrayOf(
                                                            createElementVNode("text", utsMapOf("class" to "product-tag__text"), toDisplayString(product.tag), 1)
                                                        ))
                                                    } else {
                                                        createCommentVNode("v-if", true)
                                                    }
                                                )),
                                                createElementVNode("view", utsMapOf("class" to "product-info"), utsArrayOf(
                                                    createElementVNode("text", utsMapOf("class" to "product-name"), toDisplayString(product.name), 1),
                                                    createElementVNode("text", utsMapOf("class" to "product-desc"), toDisplayString(product.desc), 1),
                                                    createElementVNode("view", utsMapOf("class" to "product-bottom"), utsArrayOf(
                                                        createElementVNode("view", utsMapOf("class" to "price-box"), utsArrayOf(
                                                            createElementVNode("text", utsMapOf("class" to "currency"), "¥"),
                                                            createElementVNode("text", utsMapOf("class" to "price"), toDisplayString(product.price), 1),
                                                            if (isTrue(product.originPrice)) {
                                                                createElementVNode("text", utsMapOf("key" to 0, "class" to "origin-price"), "¥" + toDisplayString(product.originPrice), 1)
                                                            } else {
                                                                createCommentVNode("v-if", true)
                                                            }
                                                        )),
                                                        createElementVNode("view", utsMapOf("class" to "add-btn", "onClick" to withModifiers(fun(){
                                                            _ctx.addToCart(product)
                                                        }
                                                        , utsArrayOf(
                                                            "stop"
                                                        ))), utsArrayOf(
                                                            createElementVNode("text", utsMapOf("class" to "add-btn__icon"), "+")
                                                        ), 8, utsArrayOf(
                                                            "onClick"
                                                        ))
                                                    ))
                                                ))
                                            ), 8, utsArrayOf(
                                                "onClick"
                                            ))
                                        }
                                        ), 128)
                                    ))
                                ))
                            ))
                        ))
                    )),
                    createVNode(_component_BottomNav)
                ), 64)
            }
        ))
    }
    open var isCheckingAuth: Boolean by `$data`
    open var currentLocation: String by `$data`
    open var locationInfo: LocationInfoType? by `$data`
    open var services: UTSArray<String> by `$data`
    open var banners: UTSArray<BannerType> by `$data`
    open var categories: UTSArray<CategoryType> by `$data`
    open var hotProducts: UTSArray<HotProductType> by `$data`
    open var leftColumnProducts: UTSArray<HotProductType> by `$data`
    open var rightColumnProducts: UTSArray<HotProductType> by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return utsMapOf("isCheckingAuth" to true, "currentLocation" to "定位中...", "locationInfo" to null as LocationInfoType?, "services" to utsArrayOf(
            "源头直供",
            "坏单包赔",
            "极速达"
        ), "banners" to utsArrayOf<BannerType>(BannerType(image = "https://picsum.photos/750/350?random=1", title = "新鲜蔬菜直供", link = "/pages/product/list?category=vegetable"), BannerType(image = "https://picsum.photos/750/350?random=2", title = "优质水果精选", link = "/pages/product/list?category=fruit"), BannerType(image = "https://picsum.photos/750/350?random=3", title = "海鲜水产特惠", link = "/pages/product/list?category=seafood")), "categories" to utsArrayOf<CategoryType>(CategoryType(id = 1, name = "蔬菜豆制品", icon = "🥬", bgColor = "#e8f5e9", keyword = "蔬菜"), CategoryType(id = 2, name = "时令水果", icon = "🍎", bgColor = "#fff3e0", keyword = "水果"), CategoryType(id = 3, name = "肉禽蛋品", icon = "🥩", bgColor = "#ffebee", keyword = "肉"), CategoryType(id = 4, name = "海鲜水产", icon = "🐟", bgColor = "#e3f2fd", keyword = "海鲜"), CategoryType(id = 5, name = "乳品烘焙", icon = "🥛", bgColor = "#f3e5f5", keyword = "乳品"), CategoryType(id = 6, name = "粮油调味", icon = "🌾", bgColor = "#fff8e1", keyword = "粮油"), CategoryType(id = 7, name = "速食冷冻", icon = "🥟", bgColor = "#e0f7fa", keyword = "速食"), CategoryType(id = 8, name = "酒水饮料", icon = "🥤", bgColor = "#fce4ec", keyword = "饮料")), "hotProducts" to utsArrayOf<HotProductType>(), "leftColumnProducts" to computed<UTSArray<HotProductType>>(fun(): UTSArray<HotProductType> {
            return this.hotProducts.filter(fun(_: HotProductType, index: Number): Boolean {
                return index % 2 === 0
            }
            ) as UTSArray<HotProductType>
        }
        ), "rightColumnProducts" to computed<UTSArray<HotProductType>>(fun(): UTSArray<HotProductType> {
            return this.hotProducts.filter(fun(_: HotProductType, index: Number): Boolean {
                return index % 2 !== 0
            }
            ) as UTSArray<HotProductType>
        }
        ))
    }
    open var initLocation = ::gen_initLocation_fn
    open fun gen_initLocation_fn() {
        this.currentLocation = "定位中..."
        getFullLocationInfo().then(fun(locationInfo){
            var displayText = if (locationInfo.formattedAddress != "") {
                locationInfo.formattedAddress
            } else {
                if (locationInfo.district != "") {
                    locationInfo.district
                } else {
                    "当前位置"
                }
            }
            if (displayText.length > 12) {
                displayText = displayText.substring(0, 12) + "..."
            }
            this.currentLocation = displayText
            this.locationInfo = locationInfo
            console.log("定位成功:", locationInfo, " at pages/index/index.uvue:300")
        }
        ).`catch`(fun(error){
            console.error("定位失败:", error, " at pages/index/index.uvue:303")
            this.currentLocation = "定位失败"
            uni_showToast(ShowToastOptions(title = "定位失败，请重试", icon = "none", duration = 2000))
        }
        )
    }
    open var getCachedLocation = ::gen_getCachedLocation_fn
    open fun gen_getCachedLocation_fn(): LocationInfoType? {
        val cacheStr = uni_getStorageSync("location_cache") as String
        if (cacheStr == null || cacheStr == "") {
            return null
        }
        val cache = UTSAndroid.consoleDebugError(JSON.parse(cacheStr), " at pages/index/index.uvue:322") as LocationCacheType1
        if (Date.now() - cache.timestamp > 300000) {
            return null
        }
        return cache.location as LocationInfoType
    }
    open var chooseLocation = ::gen_chooseLocation_fn
    open fun gen_chooseLocation_fn() {
        uni_chooseLocation(ChooseLocationOptions(success = fun(res){
            this.currentLocation = "解析中..."
            reverseGeocode1(res.latitude, res.longitude).then(fun(locationInfo){
                var displayText = if (locationInfo.formattedAddress != "") {
                    locationInfo.formattedAddress
                } else {
                    if (locationInfo.district != "") {
                        locationInfo.district
                    } else {
                        "已选择位置"
                    }
                }
                if (displayText.length > 12) {
                    displayText = displayText.substring(0, 12) + "..."
                }
                this.currentLocation = displayText
                this.locationInfo = locationInfo
                uni_showToast(ShowToastOptions(title = "位置已更新", icon = "success", duration = 1500))
            }
            ).`catch`(fun(error){
                console.error("地址解析失败:", error, " at pages/index/index.uvue:354")
                this.currentLocation = if (res.name != "") {
                    res.name
                } else {
                    "已选择位置"
                }
                uni_showToast(ShowToastOptions(title = "位置已更新", icon = "success", duration = 1500))
            }
            )
        }
        , fail = fun(err){
            console.error("选择位置失败:", err, " at pages/index/index.uvue:365")
        }
        ))
    }
    open var goToSearch = ::gen_goToSearch_fn
    open fun gen_goToSearch_fn() {
        uni_navigateTo(NavigateToOptions(url = "/pages/search/index"))
    }
    open var goToBanner = ::gen_goToBanner_fn
    open fun gen_goToBanner_fn(banner: BannerType) {
        if (banner.link != null && banner.link !== "") {
            uni_navigateTo(NavigateToOptions(url = banner.link))
        }
    }
    open var goToCategory = ::gen_goToCategory_fn
    open fun gen_goToCategory_fn(category: CategoryType) {
        uni_navigateTo(NavigateToOptions(url = "/pages/product/list?keyword=" + UTSAndroid.consoleDebugError(encodeURIComponent(category.keyword), " at pages/index/index.uvue:378")))
    }
    open var goToSpecial = ::gen_goToSpecial_fn
    open fun gen_goToSpecial_fn() {
        uni_navigateTo(NavigateToOptions(url = "/pages/product/list?type=special"))
    }
    open var goToDiscount = ::gen_goToDiscount_fn
    open fun gen_goToDiscount_fn() {
        uni_navigateTo(NavigateToOptions(url = "/pages/product/list?type=discount"))
    }
    open var goToNew = ::gen_goToNew_fn
    open fun gen_goToNew_fn() {
        uni_navigateTo(NavigateToOptions(url = "/pages/product/list?type=new"))
    }
    open var goToRecommend = ::gen_goToRecommend_fn
    open fun gen_goToRecommend_fn() {
        uni_navigateTo(NavigateToOptions(url = "/pages/product/list?type=recommend"))
    }
    open var goToProduct = ::gen_goToProduct_fn
    open fun gen_goToProduct_fn(id: Number) {
        uni_navigateTo(NavigateToOptions(url = "/pages/product/detail?id=" + id))
    }
    open var loadHotProducts = ::gen_loadHotProducts_fn
    open fun gen_loadHotProducts_fn() {
        get("/products/hot?limit=6").then(fun(res){
            val data = res.data as UTSArray<Any>
            if (data != null && data.length > 0) {
                val products: UTSArray<HotProductType> = utsArrayOf()
                data.forEach(fun(item: Any){
                    val itemObj = item as UTSJSONObject
                    val isNew = itemObj.getBoolean("isNew") ?: false
                    val isHot = itemObj.getBoolean("isHot") ?: false
                    var tag = ""
                    if (isNew) {
                        tag = "新品"
                    } else if (isHot) {
                        tag = "热销"
                    }
                    products.push(HotProductType(id = (itemObj.getNumber("id") ?: 0).toInt(), name = itemObj.getString("productName") ?: "", desc = itemObj.getString("description") ?: "", price = (itemObj.getNumber("currentPrice") ?: 0).toString(10), originPrice = itemObj.getNumber("originalPrice")?.toString(), image = itemObj.getString("mainImage") ?: "", tag = tag))
                }
                )
                this.hotProducts = products
            }
        }
        ).`catch`(fun(err){
            console.error("获取热销商品失败:", err, " at pages/index/index.uvue:428")
        }
        )
    }
    open var addToCart = ::gen_addToCart_fn
    open fun gen_addToCart_fn(product: HotProductType) {
        val params = CartAddParams(productId = product.id, skuId = product.id, quantity = 1)
        uni.UNIBY001.addToCart(params).then(fun(){
            uni_showToast(ShowToastOptions(title = "已加入购物车", icon = "success"))
            uni__emit("bottomNavRefresh", null)
        }
        ).`catch`(fun(err){
            console.error("添加购物车失败:", err, " at pages/index/index.uvue:441")
            uni_showToast(ShowToastOptions(title = "添加失败", icon = "none"))
        }
        )
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
                return utsMapOf("page" to padStyleMapOf(utsMapOf("backgroundColor" to "#f5f5f5", "display" to "flex", "flexDirection" to "column", "position" to "relative", "width" to "100%", "height" to "100%")), "bg-decoration" to padStyleMapOf(utsMapOf("position" to "absolute", "top" to 0, "left" to 0, "width" to "100%", "height" to "400rpx", "backgroundImage" to "linear-gradient(180deg, #0066CC 0%, #0066CC 40%, #f7f8fa 100%)", "backgroundColor" to "rgba(0,0,0,0)", "zIndex" to 0)), "header" to padStyleMapOf(utsMapOf("position" to "fixed", "top" to 0, "left" to 0, "right" to 0, "zIndex" to 100, "paddingTop" to CSS_VAR_STATUS_BAR_HEIGHT, "backgroundColor" to "#0066CC")), "header-content" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "paddingTop" to 10, "paddingRight" to 16, "paddingBottom" to 10, "paddingLeft" to 16)), "location-box" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "maxWidth" to 100)), "location-icon" to utsMapOf(".location-box " to utsMapOf("fontSize" to 16, "marginRight" to 4)), "location-text" to utsMapOf(".location-box " to utsMapOf("fontSize" to 14, "color" to "#ffffff", "fontWeight" to "700", "overflow" to "hidden", "textOverflow" to "ellipsis", "whiteSpace" to "nowrap")), "arrow-icon" to utsMapOf(".location-box " to utsMapOf("fontSize" to 12, "color" to "#ffffff", "marginLeft" to 2)), "search-bar" to padStyleMapOf(utsMapOf("flex" to 1, "height" to 36, "backgroundColor" to "#ffffff", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "paddingTop" to 0, "paddingRight" to 12, "paddingBottom" to 0, "paddingLeft" to 12, "boxShadow" to "0 2px 8px rgba(0, 0, 0, 0.05)")), "search-icon" to utsMapOf(".search-bar " to utsMapOf("fontSize" to 16, "color" to "#999999", "marginRight" to 8)), "search-placeholder" to utsMapOf(".search-bar " to utsMapOf("fontSize" to 13, "color" to "#999999")), "content-scroll" to padStyleMapOf(utsMapOf("flex" to 1, "backgroundColor" to "rgba(0,0,0,0)", "height" to 0)), "scroll-content" to padStyleMapOf(utsMapOf("paddingTop" to CSS_VAR_STATUS_BAR_HEIGHT, "marginTop" to 56, "backgroundColor" to "#f5f5f5", "paddingBottom" to "120rpx")), "swiper-section" to padStyleMapOf(utsMapOf("paddingTop" to 10, "paddingRight" to 16, "paddingBottom" to 10, "paddingLeft" to 16, "position" to "relative", "zIndex" to 1)), "main-swiper" to padStyleMapOf(utsMapOf("height" to 150, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "overflow" to "hidden", "boxShadow" to "0 4px 12px rgba(0, 0, 0, 0.1)")), "swiper-image" to padStyleMapOf(utsMapOf("width" to "100%", "height" to "100%")), "service-bar" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "paddingTop" to 0, "paddingRight" to 24, "paddingBottom" to 12, "paddingLeft" to 24)), "service-item" to utsMapOf(".service-bar " to utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center")), "service-icon" to utsMapOf(".service-bar .service-item " to utsMapOf("fontSize" to 12, "color" to "rgba(255,255,255,0.9)", "marginRight" to 4, "backgroundColor" to "rgba(255,255,255,0.2)", "width" to 16, "height" to 16, "borderTopLeftRadius" to 999, "borderTopRightRadius" to 999, "borderBottomRightRadius" to 999, "borderBottomLeftRadius" to 999, "textAlign" to "center", "lineHeight" to "16px")), "service-text" to utsMapOf(".service-bar .service-item " to utsMapOf("fontSize" to 11, "color" to "rgba(255,255,255,0.9)")), "category-section" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 0, "borderBottomLeftRadius" to 0, "paddingTop" to 20, "paddingRight" to 0, "paddingBottom" to 20, "paddingLeft" to 0, "marginTop" to -10, "position" to "relative", "zIndex" to 2)), "category-grid" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "flexWrap" to "wrap", "paddingTop" to 0, "paddingRight" to 10, "paddingBottom" to 0, "paddingLeft" to 10)), "category-item" to padStyleMapOf(utsMapOf("width" to "25%", "display" to "flex", "flexDirection" to "column", "alignItems" to "center", "marginBottom" to 16)), "category-icon-box" to utsMapOf(".category-item " to utsMapOf("width" to 48, "height" to 48, "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "display" to "flex", "alignItems" to "center", "justifyContent" to "center", "marginBottom" to 8)), "category-emoji" to utsMapOf(".category-item .category-icon-box " to utsMapOf("fontSize" to 24)), "category-name" to utsMapOf(".category-item " to utsMapOf("fontSize" to 12, "color" to "#333333", "fontWeight" to "400")), "activity-section" to padStyleMapOf(utsMapOf("paddingTop" to 0, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "display" to "flex", "flexDirection" to "row", "backgroundColor" to "rgba(0,0,0,0)", "marginBottom" to 8)), "activity-col-spaced" to padStyleMapOf(utsMapOf("marginLeft" to 12)), "activity-card" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "position" to "relative", "overflow" to "hidden", "boxShadow" to "0 4px 16px rgba(0, 0, 0, 0.04)")), "activity-card-big" to padStyleMapOf(utsMapOf("flex" to 1, "height" to 170, "backgroundColor" to "#e6f4ff")), "activity-card-big-img" to padStyleMapOf(utsMapOf("position" to "absolute", "bottom" to 0, "right" to -10, "width" to 110, "height" to 110)), "activity-card-small" to padStyleMapOf(utsMapOf("height" to 79, "backgroundColor" to "#fff7e6", "display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between")), "activity-card-small-img" to padStyleMapOf(utsMapOf("width" to 60, "height" to 60, "marginTop" to 5)), "activity-col" to padStyleMapOf(utsMapOf("flex" to 1, "display" to "flex", "flexDirection" to "column")), "activity-card-spaced" to padStyleMapOf(utsMapOf("marginTop" to 12)), "activity-info" to padStyleMapOf(utsMapOf("position" to "relative", "zIndex" to 1, "display" to "flex", "flexDirection" to "column", "alignItems" to "flex-start")), "activity-title" to padStyleMapOf(utsMapOf("fontSize" to 16, "fontWeight" to "bold", "color" to "#333333", "marginBottom" to 4)), "activity-title-highlight" to padStyleMapOf(utsMapOf("color" to "#ff4d4f")), "activity-desc" to padStyleMapOf(utsMapOf("fontSize" to 11, "color" to "#666666", "marginBottom" to 8)), "activity-btn" to padStyleMapOf(utsMapOf("fontSize" to 10, "color" to "#ffffff", "backgroundColor" to "#ff4d4f", "paddingTop" to 2, "paddingRight" to 8, "paddingBottom" to 2, "paddingLeft" to 8, "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10)), "recommend-section" to padStyleMapOf(utsMapOf("paddingTop" to 0, "paddingRight" to 16, "paddingBottom" to 0, "paddingLeft" to 16)), "section-header" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "marginBottom" to 12)), "section-title" to padStyleMapOf(utsMapOf("fontSize" to 18, "fontWeight" to "bold", "color" to "#333333", "paddingLeft" to 10, "borderLeftWidth" to 4, "borderLeftStyle" to "solid", "borderLeftColor" to "#0066CC", "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2)), "section-more" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center")), "section-more__text" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999")), "section-more__arrow" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999", "marginLeft" to 2)), "product-waterfall" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row")), "waterfall-col" to padStyleMapOf(utsMapOf("flex" to 1, "display" to "flex", "flexDirection" to "column")), "waterfall-col-gap" to padStyleMapOf(utsMapOf("marginLeft" to 12)), "product-card-gap" to padStyleMapOf(utsMapOf("marginTop" to 12)), "product-card" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "overflow" to "hidden", "boxShadow" to "0 4px 16px rgba(0, 0, 0, 0.04)")), "product-img-box" to padStyleMapOf(utsMapOf("position" to "relative", "width" to "100%")), "product-img" to padStyleMapOf(utsMapOf("width" to "100%", "height" to "100%")), "product-tag" to padStyleMapOf(utsMapOf("position" to "absolute", "top" to 10, "left" to 10, "backgroundColor" to "#ff4d4f", "paddingTop" to 4, "paddingRight" to 8, "paddingBottom" to 4, "paddingLeft" to 8, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 0, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 0, "boxShadow" to "0 2px 4px rgba(255, 77, 79, 0.3)")), "product-tag__text" to padStyleMapOf(utsMapOf("fontSize" to 11, "fontWeight" to "bold", "color" to "#ffffff")), "product-info" to padStyleMapOf(utsMapOf("paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12)), "product-name" to padStyleMapOf(utsMapOf("fontSize" to 15, "color" to "#333333", "lineHeight" to 1.4, "marginBottom" to 6, "overflow" to "hidden", "fontWeight" to "bold")), "product-desc" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999", "marginBottom" to 12, "whiteSpace" to "nowrap", "overflow" to "hidden", "textOverflow" to "ellipsis")), "product-bottom" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "flex-end")), "price-box" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center")), "currency" to padStyleMapOf(utsMapOf("fontSize" to 12, "fontWeight" to "bold", "color" to "#ff4d4f")), "price" to padStyleMapOf(utsMapOf("fontSize" to 18, "fontWeight" to "bold", "color" to "#ff4d4f")), "origin-price" to padStyleMapOf(utsMapOf("fontSize" to 11, "color" to "#cccccc", "marginLeft" to 4, "opacity" to 0.8)), "add-btn" to padStyleMapOf(utsMapOf("width" to 24, "height" to 24, "backgroundColor" to "#0066CC", "borderTopLeftRadius" to 999, "borderTopRightRadius" to 999, "borderBottomRightRadius" to 999, "borderBottomLeftRadius" to 999, "display" to "flex", "alignItems" to "center", "justifyContent" to "center", "boxShadow" to "0 2px 6px rgba(0, 102, 204, 0.3)")), "add-btn__icon" to padStyleMapOf(utsMapOf("fontSize" to 18, "fontWeight" to "400", "color" to "#ffffff")), "auth-loading" to padStyleMapOf(utsMapOf("position" to "fixed", "top" to 0, "left" to 0, "right" to 0, "bottom" to 0, "backgroundColor" to "#0066CC", "zIndex" to 999)), "loading-bg" to padStyleMapOf(utsMapOf("width" to "100%", "height" to "100%", "backgroundColor" to "#0066CC")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = utsMapOf()
        var emits: Map<String, Any?> = utsMapOf()
        var props = normalizePropsOptions(utsMapOf())
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf()
        var components: Map<String, CreateVueComponent> = utsMapOf("BottomNav" to GenComponentsBottomNavClass)
    }
}
