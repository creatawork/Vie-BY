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
import io.dcloud.uniapp.extapi.connectSocket as uni_connectSocket
import io.dcloud.uniapp.extapi.exit as uni_exit
import io.dcloud.uniapp.extapi.getLocation as uni_getLocation
import io.dcloud.uniapp.extapi.getStorageSync as uni_getStorageSync
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.removeStorageSync as uni_removeStorageSync
import io.dcloud.uniapp.extapi.request as uni_request
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showToast as uni_showToast
import io.dcloud.uniapp.extapi.uploadFile as uni_uploadFile
val runBlock1 = run {
    __uniConfig.getAppStyles = fun(): Map<String, Map<String, Map<String, Any>>> {
        return GenApp.styles
    }
}
fun initRuntimeSocket(hosts: String, port: String, id: String): UTSPromise<SocketTask?> {
    if (hosts == "" || port == "" || id == "") {
        return UTSPromise.resolve(null)
    }
    return hosts.split(",").reduce<UTSPromise<SocketTask?>>(fun(promise: UTSPromise<SocketTask?>, host: String): UTSPromise<SocketTask?> {
        return promise.then(fun(socket): UTSPromise<SocketTask?> {
            if (socket != null) {
                return UTSPromise.resolve(socket)
            }
            return tryConnectSocket(host, port, id)
        }
        )
    }
    , UTSPromise.resolve(null))
}
val SOCKET_TIMEOUT: Number = 500
fun tryConnectSocket(host: String, port: String, id: String): UTSPromise<SocketTask?> {
    return UTSPromise(fun(resolve, reject){
        val socket = uni_connectSocket(ConnectSocketOptions(url = "ws://" + host + ":" + port + "/" + id, fail = fun(_) {
            resolve(null)
        }
        ))
        val timer = setTimeout(fun(){
            socket.close(CloseSocketOptions(code = 1006, reason = "connect timeout"))
            resolve(null)
        }
        , SOCKET_TIMEOUT)
        socket.onOpen(fun(e){
            clearTimeout(timer)
            resolve(socket)
        }
        )
        socket.onClose(fun(e){
            clearTimeout(timer)
            resolve(null)
        }
        )
        socket.onError(fun(e){
            clearTimeout(timer)
            resolve(null)
        }
        )
    }
    )
}
fun initRuntimeSocketService(): UTSPromise<Boolean> {
    val hosts: String = "192.168.86.1,192.168.199.1,10.4.23.54,127.0.0.1"
    val port: String = "8090"
    val id: String = "app-android_nJ1cO-"
    if (hosts == "" || port == "" || id == "") {
        return UTSPromise.resolve(false)
    }
    var socketTask: SocketTask? = null
    __registerWebViewUniConsole(fun(): String {
        return "!function(){\"use strict\";\"function\"==typeof SuppressedError&&SuppressedError;var e=[\"log\",\"warn\",\"error\",\"info\",\"debug\"],n=e.reduce((function(e,n){return e[n]=console[n].bind(console),e}),{}),t=null,r=new Set,o={};function i(e){if(null!=t){var n=e.map((function(e){if(\"string\"==typeof e)return e;var n=e&&\"promise\"in e&&\"reason\"in e,t=n?\"UnhandledPromiseRejection: \":\"\";if(n&&(e=e.reason),e instanceof Error&&e.stack)return e.message&&!e.stack.includes(e.message)?\"\".concat(t).concat(e.message,\"\\n\").concat(e.stack):\"\".concat(t).concat(e.stack);if(\"object\"==typeof e&&null!==e)try{return t+JSON.stringify(e)}catch(e){return t+String(e)}return t+String(e)})).filter(Boolean);n.length>0&&t(JSON.stringify(Object.assign({type:\"error\",data:n},o)))}else e.forEach((function(e){r.add(e)}))}function a(e,n){try{return{type:e,args:u(n)}}catch(e){}return{type:e,args:[]}}function u(e){return e.map((function(e){return c(e)}))}function c(e,n){if(void 0===n&&(n=0),n>=7)return{type:\"object\",value:\"[Maximum depth reached]\"};switch(typeof e){case\"string\":return{type:\"string\",value:e};case\"number\":return function(e){return{type:\"number\",value:String(e)}}(e);case\"boolean\":return function(e){return{type:\"boolean\",value:String(e)}}(e);case\"object\":try{return function(e,n){if(null===e)return{type:\"null\"};if(function(e){return e.\$&&s(e.\$)}(e))return function(e,n){return{type:\"object\",className:\"ComponentPublicInstance\",value:{properties:Object.entries(e.\$.type).map((function(e){return f(e[0],e[1],n+1)}))}}}(e,n);if(s(e))return function(e,n){return{type:\"object\",className:\"ComponentInternalInstance\",value:{properties:Object.entries(e.type).map((function(e){return f(e[0],e[1],n+1)}))}}}(e,n);if(function(e){return e.style&&null!=e.tagName&&null!=e.nodeName}(e))return function(e,n){return{type:\"object\",value:{properties:Object.entries(e).filter((function(e){var n=e[0];return[\"id\",\"tagName\",\"nodeName\",\"dataset\",\"offsetTop\",\"offsetLeft\",\"style\"].includes(n)})).map((function(e){return f(e[0],e[1],n+1)}))}}}(e,n);if(function(e){return\"function\"==typeof e.getPropertyValue&&\"function\"==typeof e.setProperty&&e.\$styles}(e))return function(e,n){return{type:\"object\",value:{properties:Object.entries(e.\$styles).map((function(e){return f(e[0],e[1],n+1)}))}}}(e,n);if(Array.isArray(e))return{type:\"object\",subType:\"array\",value:{properties:e.map((function(e,t){return function(e,n,t){var r=c(e,t);return r.name=\"\".concat(n),r}(e,t,n+1)}))}};if(e instanceof Set)return{type:\"object\",subType:\"set\",className:\"Set\",description:\"Set(\".concat(e.size,\")\"),value:{entries:Array.from(e).map((function(e){return function(e,n){return{value:c(e,n)}}(e,n+1)}))}};if(e instanceof Map)return{type:\"object\",subType:\"map\",className:\"Map\",description:\"Map(\".concat(e.size,\")\"),value:{entries:Array.from(e.entries()).map((function(e){return function(e,n){return{key:c(e[0],n),value:c(e[1],n)}}(e,n+1)}))}};if(e instanceof Promise)return{type:\"object\",subType:\"promise\",value:{properties:[]}};if(e instanceof RegExp)return{type:\"object\",subType:\"regexp\",value:String(e),className:\"Regexp\"};if(e instanceof Date)return{type:\"object\",subType:\"date\",value:String(e),className:\"Date\"};if(e instanceof Error)return{type:\"object\",subType:\"error\",value:e.message||String(e),className:e.name||\"Error\"};var t=void 0,r=e.constructor;r&&r.get\$UTSMetadata\$&&(t=r.get\$UTSMetadata\$().name);var o=Object.entries(e);(function(e){return e.modifier&&e.modifier._attribute&&e.nodeContent})(e)&&(o=o.filter((function(e){var n=e[0];return\"modifier\"!==n&&\"nodeContent\"!==n})));return{type:\"object\",className:t,value:{properties:o.map((function(e){return f(e[0],e[1],n+1)}))}}}(e,n)}catch(e){return{type:\"object\",value:{properties:[]}}}case\"undefined\":return{type:\"undefined\"};case\"function\":return function(e){return{type:\"function\",value:\"function \".concat(e.name,\"() {}\")}}(e);case\"symbol\":return function(e){return{type:\"symbol\",value:e.description}}(e);case\"bigint\":return function(e){return{type:\"bigint\",value:String(e)}}(e)}}function s(e){return e.type&&null!=e.uid&&e.appContext}function f(e,n,t){var r=c(n,t);return r.name=e,r}var l=null,p=[],y={},g=\"---BEGIN:EXCEPTION---\",d=\"---END:EXCEPTION---\";function v(e){null!=l?l(JSON.stringify(Object.assign({type:\"console\",data:e},y))):p.push.apply(p,e)}var m=/^\\s*at\\s+[\\w/./-]+:\\d+\$/;function b(){function t(e){return function(){for(var t=[],r=0;r<arguments.length;r++)t[r]=arguments[r];var o=function(e,n,t){if(t||2===arguments.length)for(var r,o=0,i=n.length;o<i;o++)!r&&o in n||(r||(r=Array.prototype.slice.call(n,0,o)),r[o]=n[o]);return e.concat(r||Array.prototype.slice.call(n))}([],t,!0);if(o.length){var u=o[o.length-1];\"string\"==typeof u&&m.test(u)&&o.pop()}if(n[e].apply(n,o),\"error\"===e&&1===t.length){var c=t[0];if(\"string\"==typeof c&&c.startsWith(g)){var s=g.length,f=c.length-d.length;return void i([c.slice(s,f)])}if(c instanceof Error)return void i([c])}v([a(e,t)])}}return function(){var e=console.log,n=Symbol();try{console.log=n}catch(e){return!1}var t=console.log===n;return console.log=e,t}()?(e.forEach((function(e){console[e]=t(e)})),function(){e.forEach((function(e){console[e]=n[e]}))}):function(){}}function _(e){var n={type:\"WEB_INVOKE_APPSERVICE\",args:{data:{name:\"console\",arg:e}}};return window.__uniapp_x_postMessageToService?window.__uniapp_x_postMessageToService(n):window.__uniapp_x_.postMessageToService(JSON.stringify(n))}!function(){if(!window.__UNI_CONSOLE_WEBVIEW__){window.__UNI_CONSOLE_WEBVIEW__=!0;var e=\"[web-view]\".concat(window.__UNI_PAGE_ROUTE__?\"[\".concat(window.__UNI_PAGE_ROUTE__,\"]\"):\"\");b(),function(e,n){if(void 0===n&&(n={}),l=e,Object.assign(y,n),null!=e&&p.length>0){var t=p.slice();p.length=0,v(t)}}((function(e){_(e)}),{channel:e}),function(e,n){if(void 0===n&&(n={}),t=e,Object.assign(o,n),null!=e&&r.size>0){var a=Array.from(r);r.clear(),i(a)}}((function(e){_(e)}),{channel:e}),window.addEventListener(\"error\",(function(e){i([e.error])})),window.addEventListener(\"unhandledrejection\",(function(e){i([e])}))}}()}();"
    }
    , fun(data: String){
        socketTask?.send(SendSocketMessageOptions(data = data))
    }
    )
    return UTSPromise.resolve().then(fun(): UTSPromise<Boolean> {
        return initRuntimeSocket(hosts, port, id).then(fun(socket): Boolean {
            if (socket == null) {
                return false
            }
            socketTask = socket
            return true
        }
        )
    }
    ).`catch`(fun(): Boolean {
        return false
    }
    )
}
val runBlock2 = run {
    initRuntimeSocketService()
}
var firstBackTime: Number = 0
open class GenApp : BaseApp {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {
        onLaunch(fun(_: OnLaunchOptions) {
            console.log("App Launch", " at App.uvue:5")
        }
        , __ins)
        onAppShow(fun(_: OnShowOptions) {
            console.log("App Show", " at App.uvue:8")
        }
        , __ins)
        onAppHide(fun() {
            console.log("App Hide", " at App.uvue:11")
        }
        , __ins)
        onLastPageBackPress(fun() {
            console.log("App LastPageBackPress", " at App.uvue:15")
            if (firstBackTime == 0) {
                uni_showToast(ShowToastOptions(title = "再按一次退出应用", position = "bottom"))
                firstBackTime = Date.now()
                setTimeout(fun(){
                    firstBackTime = 0
                }, 2000)
            } else if (Date.now() - firstBackTime < 2000) {
                firstBackTime = Date.now()
                uni_exit(null)
            }
        }
        , __ins)
        onExit(fun() {
            console.log("App Exit", " at App.uvue:32")
        }
        , __ins)
    }
    companion object {
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            normalizeCssStyles(utsArrayOf(
                styles0
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return utsMapOf("app-root-page" to padStyleMapOf(utsMapOf("backgroundColor" to "#F5F7FA", "fontFamily" to "-apple-system, BlinkMacSystemFont, Helvetica Neue, Helvetica, Segoe UI, Arial, Roboto, PingFang SC, miui, Hiragino Sans GB, Microsoft Yahei, sans-serif", "color" to "#333333", "fontSize" to 14, "lineHeight" to 1.5)), "uni-row" to padStyleMapOf(utsMapOf("flexDirection" to "row", "display" to "flex")), "uni-column" to padStyleMapOf(utsMapOf("flexDirection" to "column", "display" to "flex")), "flex-row" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center")), "flex-col" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "column")), "justify-between" to padStyleMapOf(utsMapOf("justifyContent" to "space-between")), "justify-center" to padStyleMapOf(utsMapOf("justifyContent" to "center")), "justify-end" to padStyleMapOf(utsMapOf("justifyContent" to "flex-end")), "align-center" to padStyleMapOf(utsMapOf("alignItems" to "center")), "card" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "marginBottom" to 16, "boxShadow" to "0 2px 8px rgba(0, 0, 0, 0.05)")), "btn" to utsMapOf("" to utsMapOf("display" to "flex", "alignItems" to "center", "justifyContent" to "center", "height" to 44, "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "fontSize" to 16, "fontWeight" to "700", "transitionDuration" to "0.3s"), ".btn-primary" to utsMapOf("backgroundColor" to "#0066CC", "color" to "#ffffff", "boxShadow" to "0 4px 12px rgba(0, 102, 204, 0.3)"), ".btn-secondary" to utsMapOf("backgroundColor" to "#52C41A", "color" to "#ffffff", "boxShadow" to "0 4px 12px rgba(82, 196, 26, 0.3)"), ".btn-outline" to utsMapOf("backgroundColor" to "rgba(0,0,0,0)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#0066CC", "borderRightColor" to "#0066CC", "borderBottomColor" to "#0066CC", "borderLeftColor" to "#0066CC", "color" to "#0066CC")), "input-group" to padStyleMapOf(utsMapOf("marginBottom" to 24)), "input-label" to utsMapOf(".input-group " to utsMapOf("fontSize" to 14, "color" to "#333333", "marginBottom" to 8, "fontWeight" to "400")), "input-box" to utsMapOf(".input-group " to utsMapOf("height" to 48, "backgroundColor" to "#F5F7FA", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingTop" to 0, "paddingRight" to 16, "paddingBottom" to 0, "paddingLeft" to 16, "fontSize" to 14, "color" to "#333333", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "rgba(0,0,0,0)", "borderRightColor" to "rgba(0,0,0,0)", "borderBottomColor" to "rgba(0,0,0,0)", "borderLeftColor" to "rgba(0,0,0,0)", "transitionDuration" to "0.3s", "backgroundColor:focus" to "#ffffff", "borderTopColor:focus" to "#0066CC", "borderRightColor:focus" to "#0066CC", "borderBottomColor:focus" to "#0066CC", "borderLeftColor:focus" to "#0066CC", "boxShadow:focus" to "0 0 0 2px rgba(0, 102, 204, 0.1)")), "page-container" to padStyleMapOf(utsMapOf("flex" to 1, "backgroundColor" to "#F5F7FA", "paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "boxSizing" to "border-box")), "text-ellipsis" to padStyleMapOf(utsMapOf("overflow" to "hidden", "textOverflow" to "ellipsis", "whiteSpace" to "nowrap")), "text-ellipsis-2" to padStyleMapOf(utsMapOf("overflow" to "hidden", "textOverflow" to "ellipsis", "whiteSpace" to "normal", "lineHeight" to 1.4, "maxHeight" to 40)), "@TRANSITION" to utsMapOf("btn" to utsMapOf("duration" to "0.3s"), "input-box" to utsMapOf("duration" to "0.3s")))
            }
    }
}
val GenAppClass = CreateVueAppComponent(GenApp::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "app", name = "", inheritAttrs = true, inject = Map(), props = Map(), propsNeedCastKeys = utsArrayOf(), emits = Map(), components = Map(), styles = GenApp.styles)
}
, fun(instance): GenApp {
    return GenApp(instance)
}
)
val API_BASE_URL = "http://23.247.129.117/api"
val REQUEST_TIMEOUT: Number = 20000
open class ResponseDataType (
    @JsonNotNull
    open var code: Number,
    @JsonNotNull
    open var message: String,
    open var data: Any? = null,
    open var timestamp: Number? = null,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ResponseDataType", "utils/request.uts", 9, 13)
    }
}
fun request(url: String, method: String, requestData: Any?): UTSPromise<ResponseDataType> {
    return UTSPromise(fun(resolve, reject){
        val token = uni_getStorageSync("auth_token") as String?
        var finalHeader: UTSJSONObject
        if (token != null && token !== "") {
            finalHeader = object : UTSJSONObject() {
                var `Content-Type` = "application/json"
                var Authorization = "Bearer " + token
            }
        } else {
            finalHeader = object : UTSJSONObject() {
                var `Content-Type` = "application/json"
            }
        }
        console.log("HTTP 请求:", "" + API_BASE_URL + url, " at utils/request.ts:39")
        console.log("请求方法:", method, " at utils/request.ts:40")
        if (requestData != null) {
            console.log("请求数据:", JSON.stringify(requestData), " at utils/request.ts:42")
        }
        if (token != null && token !== "") {
            console.log("Token:", token.substring(0, 20) + "...", " at utils/request.ts:45")
        } else {
            console.log("Token: 无", " at utils/request.ts:47")
        }
        val reqOpts = RequestOptions(url = "" + API_BASE_URL + url, method = method, data = requestData as UTSJSONObject?, header = finalHeader, timeout = REQUEST_TIMEOUT, success = fun(res: RequestSuccess<UTSJSONObject>) {
            val resObj = res.data as UTSJSONObject
            val code = (resObj.getNumber("code") ?: 0).toInt()
            val message = resObj.getString("message") ?: ""
            val resData = resObj.getAny("data")
            val timestamp = resObj.getNumber("timestamp")
            val result = ResponseDataType(code = code, message = message, data = resData, timestamp = if (timestamp != null) {
                timestamp.toInt()
            } else {
                null
            }
            )
            console.log("HTTP 响应:", code, message, " at utils/request.ts:71")
            if (code == 401) {
                uni_removeStorageSync("auth_token")
                uni_removeStorageSync("user_info")
                uni_navigateTo(NavigateToOptions(url = "/pages/login/login"))
                reject(UTSError("认证过期，请重新登录"))
                return
            }
            if (code == 403) {
                reject(UTSError("您没有权限执行此操作"))
                return
            }
            if (code != 200) {
                val errMsg = if (message != "") {
                    message
                } else {
                    "请求失败"
                }
                reject(UTSError(errMsg))
                return
            }
            resolve(result)
        }
        , fail = fun(err: RequestFail) {
            console.error("请求失败:", err.errMsg, " at utils/request.ts:100")
            reject(UTSError("网络请求失败，请检查网络连接"))
        }
        )
        uni_request<UTSJSONObject>(reqOpts as RequestOptions<UTSJSONObject>)
    }
    )
}
fun get(url: String): UTSPromise<ResponseDataType> {
    return request(url, "GET", null)
}
fun post(url: String, data: Any?): UTSPromise<ResponseDataType> {
    return request(url, "POST", data)
}
fun put(url: String, data: Any?): UTSPromise<ResponseDataType> {
    return request(url, "PUT", data)
}
fun del(url: String): UTSPromise<ResponseDataType> {
    return request(url, "DELETE", null)
}
fun reverseGeocode(latitude: Number, longitude: Number): UTSPromise<ResponseDataType> {
    return post("/location/reverse-geocode", UTSJSONObject(Map<String, Any?>(utsArrayOf(
        utsArrayOf(
            "latitude",
            latitude
        ),
        utsArrayOf(
            "longitude",
            longitude
        )
    ))))
}
val LOCATION_CACHE_KEY = "location_cache"
val LOCATION_CACHE_TIME: Number = 300000
val CACHE_DISTANCE_THRESHOLD: Number = 100
open class SystemAddressInfo (
    open var address: String? = null,
    open var formattedAddress: String? = null,
    open var province: String? = null,
    open var city: String? = null,
    open var district: String? = null,
    open var street: String? = null,
    open var streetNum: String? = null,
    open var poiName: String? = null,
    open var name: String? = null,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("SystemAddressInfo", "utils/location.uts", 11, 6)
    }
}
open class CoordinatesType (
    @JsonNotNull
    open var latitude: Number,
    @JsonNotNull
    open var longitude: Number,
    open var systemAddress: SystemAddressInfo? = null,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("CoordinatesType", "utils/location.uts", 23, 13)
    }
}
open class LocationInfoType (
    @JsonNotNull
    open var latitude: Number,
    @JsonNotNull
    open var longitude: Number,
    @JsonNotNull
    open var address: String,
    @JsonNotNull
    open var province: String,
    @JsonNotNull
    open var city: String,
    @JsonNotNull
    open var district: String,
    @JsonNotNull
    open var street: String,
    @JsonNotNull
    open var streetNumber: String,
    @JsonNotNull
    open var poiName: String,
    @JsonNotNull
    open var formattedAddress: String,
    @JsonNotNull
    open var timestamp: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("LocationInfoType", "utils/location.uts", 29, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return LocationInfoTypeReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class LocationInfoTypeReactiveObject : LocationInfoType, IUTSReactive<LocationInfoType> {
    override var __v_raw: LocationInfoType
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: LocationInfoType, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(latitude = __v_raw.latitude, longitude = __v_raw.longitude, address = __v_raw.address, province = __v_raw.province, city = __v_raw.city, district = __v_raw.district, street = __v_raw.street, streetNumber = __v_raw.streetNumber, poiName = __v_raw.poiName, formattedAddress = __v_raw.formattedAddress, timestamp = __v_raw.timestamp) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): LocationInfoTypeReactiveObject {
        return LocationInfoTypeReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var latitude: Number
        get() {
            return trackReactiveGet(__v_raw, "latitude", __v_raw.latitude, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("latitude")) {
                return
            }
            val oldValue = __v_raw.latitude
            __v_raw.latitude = value
            triggerReactiveSet(__v_raw, "latitude", oldValue, value)
        }
    override var longitude: Number
        get() {
            return trackReactiveGet(__v_raw, "longitude", __v_raw.longitude, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("longitude")) {
                return
            }
            val oldValue = __v_raw.longitude
            __v_raw.longitude = value
            triggerReactiveSet(__v_raw, "longitude", oldValue, value)
        }
    override var address: String
        get() {
            return trackReactiveGet(__v_raw, "address", __v_raw.address, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("address")) {
                return
            }
            val oldValue = __v_raw.address
            __v_raw.address = value
            triggerReactiveSet(__v_raw, "address", oldValue, value)
        }
    override var province: String
        get() {
            return trackReactiveGet(__v_raw, "province", __v_raw.province, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("province")) {
                return
            }
            val oldValue = __v_raw.province
            __v_raw.province = value
            triggerReactiveSet(__v_raw, "province", oldValue, value)
        }
    override var city: String
        get() {
            return trackReactiveGet(__v_raw, "city", __v_raw.city, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("city")) {
                return
            }
            val oldValue = __v_raw.city
            __v_raw.city = value
            triggerReactiveSet(__v_raw, "city", oldValue, value)
        }
    override var district: String
        get() {
            return trackReactiveGet(__v_raw, "district", __v_raw.district, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("district")) {
                return
            }
            val oldValue = __v_raw.district
            __v_raw.district = value
            triggerReactiveSet(__v_raw, "district", oldValue, value)
        }
    override var street: String
        get() {
            return trackReactiveGet(__v_raw, "street", __v_raw.street, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("street")) {
                return
            }
            val oldValue = __v_raw.street
            __v_raw.street = value
            triggerReactiveSet(__v_raw, "street", oldValue, value)
        }
    override var streetNumber: String
        get() {
            return trackReactiveGet(__v_raw, "streetNumber", __v_raw.streetNumber, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("streetNumber")) {
                return
            }
            val oldValue = __v_raw.streetNumber
            __v_raw.streetNumber = value
            triggerReactiveSet(__v_raw, "streetNumber", oldValue, value)
        }
    override var poiName: String
        get() {
            return trackReactiveGet(__v_raw, "poiName", __v_raw.poiName, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("poiName")) {
                return
            }
            val oldValue = __v_raw.poiName
            __v_raw.poiName = value
            triggerReactiveSet(__v_raw, "poiName", oldValue, value)
        }
    override var formattedAddress: String
        get() {
            return trackReactiveGet(__v_raw, "formattedAddress", __v_raw.formattedAddress, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("formattedAddress")) {
                return
            }
            val oldValue = __v_raw.formattedAddress
            __v_raw.formattedAddress = value
            triggerReactiveSet(__v_raw, "formattedAddress", oldValue, value)
        }
    override var timestamp: Number
        get() {
            return trackReactiveGet(__v_raw, "timestamp", __v_raw.timestamp, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("timestamp")) {
                return
            }
            val oldValue = __v_raw.timestamp
            __v_raw.timestamp = value
            triggerReactiveSet(__v_raw, "timestamp", oldValue, value)
        }
}
open class LocationCacheType (
    @JsonNotNull
    open var location: LocationInfoType,
    @JsonNotNull
    open var timestamp: Number,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("LocationCacheType", "utils/location.uts", 43, 6)
    }
}
fun getCurrentLocation(): UTSPromise<CoordinatesType> {
    return UTSPromise(fun(resolve, reject){
        uni_getLocation(GetLocationOptions(type = "gcj02", geocode = true, success = fun(res: GetLocationSuccess) {
            val coords = CoordinatesType(latitude = res.latitude, longitude = res.longitude, systemAddress = null)
            resolve(coords)
        }
        , fail = fun(err: GetLocationFail) {
            console.error("获取定位失败:", err.errMsg, " at utils/location.ts:71")
            reject(err)
        }
        ))
    }
    )
}
fun getDistance(lat1: Number, lng1: Number, lat2: Number, lng2: Number): Number {
    val R: Number = 6371000
    val dLat = (lat2 - lat1) * Math.PI / 180
    val dLng = (lng2 - lng1) * Math.PI / 180
    val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) * Math.sin(dLng / 2) * Math.sin(dLng / 2)
    val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
    return R * c
}
fun isLocationNearby(location: LocationInfoType, lat: Number, lng: Number): Boolean {
    val distance = getDistance(location.latitude, location.longitude, lat, lng)
    return distance < CACHE_DISTANCE_THRESHOLD
}
fun getCachedLocation(): LocationCacheType? {
    val cacheStr = uni_getStorageSync(LOCATION_CACHE_KEY) as String
    if (cacheStr == null || cacheStr == "") {
        return null
    }
    try {
        val cacheJson = UTSAndroid.consoleDebugError(JSON.parse(cacheStr), " at utils/location.uts:100") as UTSJSONObject
        val tsNumber = cacheJson.getNumber("timestamp")
        val locationAny = cacheJson.get("location")
        if (tsNumber == null || locationAny == null) {
            return null
        }
        val locationJson = locationAny as UTSJSONObject
        val timestamp = tsNumber.toLong()
        if (Date.now() - timestamp > LOCATION_CACHE_TIME) {
            return null
        }
        val latitudeNum = locationJson.getNumber("latitude")
        val longitudeNum = locationJson.getNumber("longitude")
        val location = LocationInfoType(latitude = (latitudeNum ?: 0).toDouble(), longitude = (longitudeNum ?: 0).toDouble(), address = locationJson.getString("address") ?: "", province = locationJson.getString("province") ?: "", city = locationJson.getString("city") ?: "", district = locationJson.getString("district") ?: "", street = locationJson.getString("street") ?: "", streetNumber = locationJson.getString("streetNumber") ?: "", poiName = locationJson.getString("poiName") ?: "", formattedAddress = locationJson.getString("formattedAddress") ?: "", timestamp = timestamp)
        val cache = LocationCacheType(location = location, timestamp = timestamp)
        return cache
    }
     catch (e: Throwable) {
        console.error("解析定位缓存失败:", e.toString(), " at utils/location.ts:152")
        return null
    }
}
fun cacheLocation(location: LocationInfoType): Unit {
    val cache = LocationCacheType(location = location, timestamp = Date.now())
    uni_setStorageSync(LOCATION_CACHE_KEY, JSON.stringify(cache))
}
fun formatAddress(district: String, street: String, poiName: String): String {
    if (poiName != "") {
        if (poiName.indexOf("小区") >= 0 || poiName.indexOf("社区") >= 0 || poiName.indexOf("花园") >= 0) {
            return poiName
        }
        if (street != "") {
            return "" + street + " " + poiName
        }
        return poiName
    }
    if (street != "") {
        if (district != "") {
            return "" + district + " " + street
        }
        return street
    }
    if (district != "") {
        return district
    }
    return "当前位置"
}
fun reverseGeocode1(latitude: Number, longitude: Number): UTSPromise<LocationInfoType> {
    return UTSPromise(fun(resolve, reject){
        val cached = getCachedLocation()
        if (cached != null && isLocationNearby(cached.location, latitude, longitude)) {
            console.log("使用缓存的地址信息", " at utils/location.ts:204")
            resolve(cached.location)
            return
        }
        reverseGeocode(latitude, longitude).then(fun(response){
            if (response.code == 200 && response.data != null) {
                val data = response.data as UTSJSONObject
                val address = data.getString("address") ?: ""
                val province = data.getString("province") ?: ""
                val city = data.getString("city") ?: ""
                val district = data.getString("district") ?: ""
                val street = data.getString("street") ?: ""
                val streetNumber = data.getString("streetNumber") ?: ""
                val poiName = data.getString("poiName") ?: ""
                val formattedAddr = data.getString("formattedAddress") ?: formatAddress(district, street, poiName)
                val locationInfo = LocationInfoType(latitude = (data.getNumber("latitude") ?: latitude).toDouble(), longitude = (data.getNumber("longitude") ?: longitude).toDouble(), address = address, province = province, city = city, district = district, street = street, streetNumber = streetNumber, poiName = poiName, formattedAddress = formattedAddr, timestamp = Date.now().toLong())
                cacheLocation(locationInfo)
                resolve(locationInfo)
            } else {
                val msg = if (response.message != "") {
                    response.message
                } else {
                    "地址解析失败"
                }
                reject(UTSError(msg))
            }
        }
        ).`catch`(fun(error){
            console.error("逆地理编码失败:", error, " at utils/location.ts:246")
            reject(UTSError("网络请求失败"))
        }
        )
    }
    )
}
fun getFullLocationInfo(): UTSPromise<LocationInfoType> {
    return UTSPromise(fun(resolve, reject){
        getCurrentLocation().then(fun(coords){
            reverseGeocode1(coords.latitude, coords.longitude).then(fun(locationInfo){
                resolve(locationInfo)
            }
            ).`catch`(fun(error){
                console.warn("逆地理编码失败，尝试使用系统返回的地址信息:", error.toString(), " at utils/location.ts:261")
                val fallbackLocation = buildLocationInfoFromSystem(coords)
                if (fallbackLocation != null) {
                    cacheLocation(fallbackLocation)
                    resolve(fallbackLocation)
                    return
                }
                reject(error)
            }
            )
        }
        ).`catch`(fun(error){
            console.error("获取定位信息失败:", error, " at utils/location.ts:271")
            reject(error)
        }
        )
    }
    )
}
fun buildLocationInfoFromSystem(coords: CoordinatesType): LocationInfoType? {
    val addressInfo = coords.systemAddress
    if (addressInfo == null) {
        return null
    }
    val district = addressInfo.district ?: ""
    val street = addressInfo.street ?: ""
    val poiName = addressInfo.poiName ?: addressInfo.name ?: ""
    val formattedAddr = addressInfo.formattedAddress ?: addressInfo.address ?: formatAddress(district, street, poiName)
    val fallbackLocation = LocationInfoType(latitude = coords.latitude, longitude = coords.longitude, address = addressInfo.address ?: formattedAddr, province = addressInfo.province ?: "", city = addressInfo.city ?: "", district = district, street = street, streetNumber = addressInfo.streetNum ?: "", poiName = poiName, formattedAddress = formattedAddr, timestamp = Date.now().toLong())
    return fallbackLocation
}
open class CartItemVO (
    @JsonNotNull
    open var cartId: Number,
    @JsonNotNull
    open var productId: Number,
    @JsonNotNull
    open var skuId: Number,
    @JsonNotNull
    open var productName: String,
    @JsonNotNull
    open var productImage: String,
    @JsonNotNull
    open var skuName: String,
    open var skuImage: String? = null,
    @JsonNotNull
    open var price: Number,
    @JsonNotNull
    open var quantity: Number,
    @JsonNotNull
    open var totalPrice: Number,
    @JsonNotNull
    open var stock: Number,
    @JsonNotNull
    open var selected: Boolean = false,
    @JsonNotNull
    open var productStatus: Number,
    @JsonNotNull
    open var skuStatus: Number,
    @JsonNotNull
    open var valid: Boolean = false,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("CartItemVO", "api/cart.uts", 6, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return CartItemVOReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class CartItemVOReactiveObject : CartItemVO, IUTSReactive<CartItemVO> {
    override var __v_raw: CartItemVO
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: CartItemVO, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(cartId = __v_raw.cartId, productId = __v_raw.productId, skuId = __v_raw.skuId, productName = __v_raw.productName, productImage = __v_raw.productImage, skuName = __v_raw.skuName, skuImage = __v_raw.skuImage, price = __v_raw.price, quantity = __v_raw.quantity, totalPrice = __v_raw.totalPrice, stock = __v_raw.stock, selected = __v_raw.selected, productStatus = __v_raw.productStatus, skuStatus = __v_raw.skuStatus, valid = __v_raw.valid) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): CartItemVOReactiveObject {
        return CartItemVOReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var cartId: Number
        get() {
            return trackReactiveGet(__v_raw, "cartId", __v_raw.cartId, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("cartId")) {
                return
            }
            val oldValue = __v_raw.cartId
            __v_raw.cartId = value
            triggerReactiveSet(__v_raw, "cartId", oldValue, value)
        }
    override var productId: Number
        get() {
            return trackReactiveGet(__v_raw, "productId", __v_raw.productId, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("productId")) {
                return
            }
            val oldValue = __v_raw.productId
            __v_raw.productId = value
            triggerReactiveSet(__v_raw, "productId", oldValue, value)
        }
    override var skuId: Number
        get() {
            return trackReactiveGet(__v_raw, "skuId", __v_raw.skuId, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("skuId")) {
                return
            }
            val oldValue = __v_raw.skuId
            __v_raw.skuId = value
            triggerReactiveSet(__v_raw, "skuId", oldValue, value)
        }
    override var productName: String
        get() {
            return trackReactiveGet(__v_raw, "productName", __v_raw.productName, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("productName")) {
                return
            }
            val oldValue = __v_raw.productName
            __v_raw.productName = value
            triggerReactiveSet(__v_raw, "productName", oldValue, value)
        }
    override var productImage: String
        get() {
            return trackReactiveGet(__v_raw, "productImage", __v_raw.productImage, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("productImage")) {
                return
            }
            val oldValue = __v_raw.productImage
            __v_raw.productImage = value
            triggerReactiveSet(__v_raw, "productImage", oldValue, value)
        }
    override var skuName: String
        get() {
            return trackReactiveGet(__v_raw, "skuName", __v_raw.skuName, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("skuName")) {
                return
            }
            val oldValue = __v_raw.skuName
            __v_raw.skuName = value
            triggerReactiveSet(__v_raw, "skuName", oldValue, value)
        }
    override var skuImage: String?
        get() {
            return trackReactiveGet(__v_raw, "skuImage", __v_raw.skuImage, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("skuImage")) {
                return
            }
            val oldValue = __v_raw.skuImage
            __v_raw.skuImage = value
            triggerReactiveSet(__v_raw, "skuImage", oldValue, value)
        }
    override var price: Number
        get() {
            return trackReactiveGet(__v_raw, "price", __v_raw.price, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("price")) {
                return
            }
            val oldValue = __v_raw.price
            __v_raw.price = value
            triggerReactiveSet(__v_raw, "price", oldValue, value)
        }
    override var quantity: Number
        get() {
            return trackReactiveGet(__v_raw, "quantity", __v_raw.quantity, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("quantity")) {
                return
            }
            val oldValue = __v_raw.quantity
            __v_raw.quantity = value
            triggerReactiveSet(__v_raw, "quantity", oldValue, value)
        }
    override var totalPrice: Number
        get() {
            return trackReactiveGet(__v_raw, "totalPrice", __v_raw.totalPrice, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("totalPrice")) {
                return
            }
            val oldValue = __v_raw.totalPrice
            __v_raw.totalPrice = value
            triggerReactiveSet(__v_raw, "totalPrice", oldValue, value)
        }
    override var stock: Number
        get() {
            return trackReactiveGet(__v_raw, "stock", __v_raw.stock, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("stock")) {
                return
            }
            val oldValue = __v_raw.stock
            __v_raw.stock = value
            triggerReactiveSet(__v_raw, "stock", oldValue, value)
        }
    override var selected: Boolean
        get() {
            return trackReactiveGet(__v_raw, "selected", __v_raw.selected, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("selected")) {
                return
            }
            val oldValue = __v_raw.selected
            __v_raw.selected = value
            triggerReactiveSet(__v_raw, "selected", oldValue, value)
        }
    override var productStatus: Number
        get() {
            return trackReactiveGet(__v_raw, "productStatus", __v_raw.productStatus, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("productStatus")) {
                return
            }
            val oldValue = __v_raw.productStatus
            __v_raw.productStatus = value
            triggerReactiveSet(__v_raw, "productStatus", oldValue, value)
        }
    override var skuStatus: Number
        get() {
            return trackReactiveGet(__v_raw, "skuStatus", __v_raw.skuStatus, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("skuStatus")) {
                return
            }
            val oldValue = __v_raw.skuStatus
            __v_raw.skuStatus = value
            triggerReactiveSet(__v_raw, "skuStatus", oldValue, value)
        }
    override var valid: Boolean
        get() {
            return trackReactiveGet(__v_raw, "valid", __v_raw.valid, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("valid")) {
                return
            }
            val oldValue = __v_raw.valid
            __v_raw.valid = value
            triggerReactiveSet(__v_raw, "valid", oldValue, value)
        }
}
open class CartVO (
    @JsonNotNull
    open var items: UTSArray<CartItemVO>,
    @JsonNotNull
    open var totalQuantity: Number,
    @JsonNotNull
    open var selectedQuantity: Number,
    @JsonNotNull
    open var selectedAmount: Number,
    @JsonNotNull
    open var allSelected: Boolean = false,
    @JsonNotNull
    open var validCount: Number,
    @JsonNotNull
    open var invalidCount: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("CartVO", "api/cart.uts", 24, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return CartVOReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class CartVOReactiveObject : CartVO, IUTSReactive<CartVO> {
    override var __v_raw: CartVO
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: CartVO, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(items = __v_raw.items, totalQuantity = __v_raw.totalQuantity, selectedQuantity = __v_raw.selectedQuantity, selectedAmount = __v_raw.selectedAmount, allSelected = __v_raw.allSelected, validCount = __v_raw.validCount, invalidCount = __v_raw.invalidCount) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): CartVOReactiveObject {
        return CartVOReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var items: UTSArray<CartItemVO>
        get() {
            return trackReactiveGet(__v_raw, "items", __v_raw.items, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("items")) {
                return
            }
            val oldValue = __v_raw.items
            __v_raw.items = value
            triggerReactiveSet(__v_raw, "items", oldValue, value)
        }
    override var totalQuantity: Number
        get() {
            return trackReactiveGet(__v_raw, "totalQuantity", __v_raw.totalQuantity, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("totalQuantity")) {
                return
            }
            val oldValue = __v_raw.totalQuantity
            __v_raw.totalQuantity = value
            triggerReactiveSet(__v_raw, "totalQuantity", oldValue, value)
        }
    override var selectedQuantity: Number
        get() {
            return trackReactiveGet(__v_raw, "selectedQuantity", __v_raw.selectedQuantity, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("selectedQuantity")) {
                return
            }
            val oldValue = __v_raw.selectedQuantity
            __v_raw.selectedQuantity = value
            triggerReactiveSet(__v_raw, "selectedQuantity", oldValue, value)
        }
    override var selectedAmount: Number
        get() {
            return trackReactiveGet(__v_raw, "selectedAmount", __v_raw.selectedAmount, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("selectedAmount")) {
                return
            }
            val oldValue = __v_raw.selectedAmount
            __v_raw.selectedAmount = value
            triggerReactiveSet(__v_raw, "selectedAmount", oldValue, value)
        }
    override var allSelected: Boolean
        get() {
            return trackReactiveGet(__v_raw, "allSelected", __v_raw.allSelected, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("allSelected")) {
                return
            }
            val oldValue = __v_raw.allSelected
            __v_raw.allSelected = value
            triggerReactiveSet(__v_raw, "allSelected", oldValue, value)
        }
    override var validCount: Number
        get() {
            return trackReactiveGet(__v_raw, "validCount", __v_raw.validCount, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("validCount")) {
                return
            }
            val oldValue = __v_raw.validCount
            __v_raw.validCount = value
            triggerReactiveSet(__v_raw, "validCount", oldValue, value)
        }
    override var invalidCount: Number
        get() {
            return trackReactiveGet(__v_raw, "invalidCount", __v_raw.invalidCount, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("invalidCount")) {
                return
            }
            val oldValue = __v_raw.invalidCount
            __v_raw.invalidCount = value
            triggerReactiveSet(__v_raw, "invalidCount", oldValue, value)
        }
}
open class CartAddParams (
    @JsonNotNull
    open var productId: Number,
    @JsonNotNull
    open var skuId: Number,
    @JsonNotNull
    open var quantity: Number,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("CartAddParams", "api/cart.uts", 34, 13)
    }
}
fun addToCart(data: CartAddParams): UTSPromise<ResponseDataType> {
    val jsonData: UTSJSONObject = object : UTSJSONObject(UTSSourceMapPosition("jsonData", "api/cart.uts", 47, 11)) {
        var productId = data.productId
        var skuId = data.skuId
        var quantity = data.quantity
    }
    return post("/cart/items", jsonData)
}
fun getCart(): UTSPromise<ResponseDataType> {
    return get("/cart")
}
fun updateCartQuantity(cartItemId: Number, quantity: Number): UTSPromise<ResponseDataType> {
    val jsonData: UTSJSONObject = UTSJSONObject(Map<String, Any?>(utsArrayOf(
        utsArrayOf(
            "__\$originalPosition",
            UTSSourceMapPosition("jsonData", "api/cart.uts", 64, 11)
        ),
        utsArrayOf(
            "quantity",
            quantity
        )
    )))
    return put("/cart/items/" + cartItemId, jsonData)
}
fun deleteCartItem(cartItemId: Number): UTSPromise<ResponseDataType> {
    return del("/cart/items/" + cartItemId)
}
fun updateCartSelected(cartItemId: Number, selected: Boolean): UTSPromise<ResponseDataType> {
    return put("/cart/items/" + cartItemId + "/select?selected=" + selected, null)
}
fun selectAllCart(selected: Boolean): UTSPromise<ResponseDataType> {
    return put("/cart/select-all?selected=" + selected, null)
}
fun getCartCount(): UTSPromise<ResponseDataType> {
    return get("/cart/count")
}
open class OrderItemVO (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var productId: Number,
    @JsonNotNull
    open var skuId: Number,
    @JsonNotNull
    open var productName: String,
    @JsonNotNull
    open var skuName: String,
    @JsonNotNull
    open var productImage: String,
    @JsonNotNull
    open var price: Number,
    @JsonNotNull
    open var quantity: Number,
    @JsonNotNull
    open var totalPrice: Number,
    open var reviewed: Boolean? = null,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("OrderItemVO", "api/order.uts", 6, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return OrderItemVOReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class OrderItemVOReactiveObject : OrderItemVO, IUTSReactive<OrderItemVO> {
    override var __v_raw: OrderItemVO
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: OrderItemVO, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, productId = __v_raw.productId, skuId = __v_raw.skuId, productName = __v_raw.productName, skuName = __v_raw.skuName, productImage = __v_raw.productImage, price = __v_raw.price, quantity = __v_raw.quantity, totalPrice = __v_raw.totalPrice, reviewed = __v_raw.reviewed) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): OrderItemVOReactiveObject {
        return OrderItemVOReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return trackReactiveGet(__v_raw, "id", __v_raw.id, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            triggerReactiveSet(__v_raw, "id", oldValue, value)
        }
    override var productId: Number
        get() {
            return trackReactiveGet(__v_raw, "productId", __v_raw.productId, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("productId")) {
                return
            }
            val oldValue = __v_raw.productId
            __v_raw.productId = value
            triggerReactiveSet(__v_raw, "productId", oldValue, value)
        }
    override var skuId: Number
        get() {
            return trackReactiveGet(__v_raw, "skuId", __v_raw.skuId, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("skuId")) {
                return
            }
            val oldValue = __v_raw.skuId
            __v_raw.skuId = value
            triggerReactiveSet(__v_raw, "skuId", oldValue, value)
        }
    override var productName: String
        get() {
            return trackReactiveGet(__v_raw, "productName", __v_raw.productName, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("productName")) {
                return
            }
            val oldValue = __v_raw.productName
            __v_raw.productName = value
            triggerReactiveSet(__v_raw, "productName", oldValue, value)
        }
    override var skuName: String
        get() {
            return trackReactiveGet(__v_raw, "skuName", __v_raw.skuName, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("skuName")) {
                return
            }
            val oldValue = __v_raw.skuName
            __v_raw.skuName = value
            triggerReactiveSet(__v_raw, "skuName", oldValue, value)
        }
    override var productImage: String
        get() {
            return trackReactiveGet(__v_raw, "productImage", __v_raw.productImage, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("productImage")) {
                return
            }
            val oldValue = __v_raw.productImage
            __v_raw.productImage = value
            triggerReactiveSet(__v_raw, "productImage", oldValue, value)
        }
    override var price: Number
        get() {
            return trackReactiveGet(__v_raw, "price", __v_raw.price, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("price")) {
                return
            }
            val oldValue = __v_raw.price
            __v_raw.price = value
            triggerReactiveSet(__v_raw, "price", oldValue, value)
        }
    override var quantity: Number
        get() {
            return trackReactiveGet(__v_raw, "quantity", __v_raw.quantity, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("quantity")) {
                return
            }
            val oldValue = __v_raw.quantity
            __v_raw.quantity = value
            triggerReactiveSet(__v_raw, "quantity", oldValue, value)
        }
    override var totalPrice: Number
        get() {
            return trackReactiveGet(__v_raw, "totalPrice", __v_raw.totalPrice, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("totalPrice")) {
                return
            }
            val oldValue = __v_raw.totalPrice
            __v_raw.totalPrice = value
            triggerReactiveSet(__v_raw, "totalPrice", oldValue, value)
        }
    override var reviewed: Boolean?
        get() {
            return trackReactiveGet(__v_raw, "reviewed", __v_raw.reviewed, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("reviewed")) {
                return
            }
            val oldValue = __v_raw.reviewed
            __v_raw.reviewed = value
            triggerReactiveSet(__v_raw, "reviewed", oldValue, value)
        }
}
open class OrderVO (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var orderNo: String,
    @JsonNotNull
    open var totalAmount: Number,
    @JsonNotNull
    open var payAmount: Number,
    @JsonNotNull
    open var freightAmount: Number,
    @JsonNotNull
    open var status: Number,
    @JsonNotNull
    open var statusDesc: String,
    @JsonNotNull
    open var receiverName: String,
    @JsonNotNull
    open var receiverPhone: String,
    @JsonNotNull
    open var receiverAddress: String,
    open var remark: String? = null,
    open var payTime: String? = null,
    open var deliverTime: String? = null,
    open var receiveTime: String? = null,
    open var cancelTime: String? = null,
    open var cancelReason: String? = null,
    @JsonNotNull
    open var createTime: String,
    @JsonNotNull
    open var orderItems: UTSArray<OrderItemVO>,
    @JsonNotNull
    open var totalQuantity: Number,
    open var reviewed: Boolean? = null,
    open var reviewCount: Number? = null,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("OrderVO", "api/order.uts", 19, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return OrderVOReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class OrderVOReactiveObject : OrderVO, IUTSReactive<OrderVO> {
    override var __v_raw: OrderVO
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: OrderVO, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, orderNo = __v_raw.orderNo, totalAmount = __v_raw.totalAmount, payAmount = __v_raw.payAmount, freightAmount = __v_raw.freightAmount, status = __v_raw.status, statusDesc = __v_raw.statusDesc, receiverName = __v_raw.receiverName, receiverPhone = __v_raw.receiverPhone, receiverAddress = __v_raw.receiverAddress, remark = __v_raw.remark, payTime = __v_raw.payTime, deliverTime = __v_raw.deliverTime, receiveTime = __v_raw.receiveTime, cancelTime = __v_raw.cancelTime, cancelReason = __v_raw.cancelReason, createTime = __v_raw.createTime, orderItems = __v_raw.orderItems, totalQuantity = __v_raw.totalQuantity, reviewed = __v_raw.reviewed, reviewCount = __v_raw.reviewCount) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): OrderVOReactiveObject {
        return OrderVOReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return trackReactiveGet(__v_raw, "id", __v_raw.id, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            triggerReactiveSet(__v_raw, "id", oldValue, value)
        }
    override var orderNo: String
        get() {
            return trackReactiveGet(__v_raw, "orderNo", __v_raw.orderNo, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("orderNo")) {
                return
            }
            val oldValue = __v_raw.orderNo
            __v_raw.orderNo = value
            triggerReactiveSet(__v_raw, "orderNo", oldValue, value)
        }
    override var totalAmount: Number
        get() {
            return trackReactiveGet(__v_raw, "totalAmount", __v_raw.totalAmount, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("totalAmount")) {
                return
            }
            val oldValue = __v_raw.totalAmount
            __v_raw.totalAmount = value
            triggerReactiveSet(__v_raw, "totalAmount", oldValue, value)
        }
    override var payAmount: Number
        get() {
            return trackReactiveGet(__v_raw, "payAmount", __v_raw.payAmount, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("payAmount")) {
                return
            }
            val oldValue = __v_raw.payAmount
            __v_raw.payAmount = value
            triggerReactiveSet(__v_raw, "payAmount", oldValue, value)
        }
    override var freightAmount: Number
        get() {
            return trackReactiveGet(__v_raw, "freightAmount", __v_raw.freightAmount, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("freightAmount")) {
                return
            }
            val oldValue = __v_raw.freightAmount
            __v_raw.freightAmount = value
            triggerReactiveSet(__v_raw, "freightAmount", oldValue, value)
        }
    override var status: Number
        get() {
            return trackReactiveGet(__v_raw, "status", __v_raw.status, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("status")) {
                return
            }
            val oldValue = __v_raw.status
            __v_raw.status = value
            triggerReactiveSet(__v_raw, "status", oldValue, value)
        }
    override var statusDesc: String
        get() {
            return trackReactiveGet(__v_raw, "statusDesc", __v_raw.statusDesc, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("statusDesc")) {
                return
            }
            val oldValue = __v_raw.statusDesc
            __v_raw.statusDesc = value
            triggerReactiveSet(__v_raw, "statusDesc", oldValue, value)
        }
    override var receiverName: String
        get() {
            return trackReactiveGet(__v_raw, "receiverName", __v_raw.receiverName, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("receiverName")) {
                return
            }
            val oldValue = __v_raw.receiverName
            __v_raw.receiverName = value
            triggerReactiveSet(__v_raw, "receiverName", oldValue, value)
        }
    override var receiverPhone: String
        get() {
            return trackReactiveGet(__v_raw, "receiverPhone", __v_raw.receiverPhone, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("receiverPhone")) {
                return
            }
            val oldValue = __v_raw.receiverPhone
            __v_raw.receiverPhone = value
            triggerReactiveSet(__v_raw, "receiverPhone", oldValue, value)
        }
    override var receiverAddress: String
        get() {
            return trackReactiveGet(__v_raw, "receiverAddress", __v_raw.receiverAddress, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("receiverAddress")) {
                return
            }
            val oldValue = __v_raw.receiverAddress
            __v_raw.receiverAddress = value
            triggerReactiveSet(__v_raw, "receiverAddress", oldValue, value)
        }
    override var remark: String?
        get() {
            return trackReactiveGet(__v_raw, "remark", __v_raw.remark, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("remark")) {
                return
            }
            val oldValue = __v_raw.remark
            __v_raw.remark = value
            triggerReactiveSet(__v_raw, "remark", oldValue, value)
        }
    override var payTime: String?
        get() {
            return trackReactiveGet(__v_raw, "payTime", __v_raw.payTime, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("payTime")) {
                return
            }
            val oldValue = __v_raw.payTime
            __v_raw.payTime = value
            triggerReactiveSet(__v_raw, "payTime", oldValue, value)
        }
    override var deliverTime: String?
        get() {
            return trackReactiveGet(__v_raw, "deliverTime", __v_raw.deliverTime, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("deliverTime")) {
                return
            }
            val oldValue = __v_raw.deliverTime
            __v_raw.deliverTime = value
            triggerReactiveSet(__v_raw, "deliverTime", oldValue, value)
        }
    override var receiveTime: String?
        get() {
            return trackReactiveGet(__v_raw, "receiveTime", __v_raw.receiveTime, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("receiveTime")) {
                return
            }
            val oldValue = __v_raw.receiveTime
            __v_raw.receiveTime = value
            triggerReactiveSet(__v_raw, "receiveTime", oldValue, value)
        }
    override var cancelTime: String?
        get() {
            return trackReactiveGet(__v_raw, "cancelTime", __v_raw.cancelTime, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("cancelTime")) {
                return
            }
            val oldValue = __v_raw.cancelTime
            __v_raw.cancelTime = value
            triggerReactiveSet(__v_raw, "cancelTime", oldValue, value)
        }
    override var cancelReason: String?
        get() {
            return trackReactiveGet(__v_raw, "cancelReason", __v_raw.cancelReason, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("cancelReason")) {
                return
            }
            val oldValue = __v_raw.cancelReason
            __v_raw.cancelReason = value
            triggerReactiveSet(__v_raw, "cancelReason", oldValue, value)
        }
    override var createTime: String
        get() {
            return trackReactiveGet(__v_raw, "createTime", __v_raw.createTime, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("createTime")) {
                return
            }
            val oldValue = __v_raw.createTime
            __v_raw.createTime = value
            triggerReactiveSet(__v_raw, "createTime", oldValue, value)
        }
    override var orderItems: UTSArray<OrderItemVO>
        get() {
            return trackReactiveGet(__v_raw, "orderItems", __v_raw.orderItems, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("orderItems")) {
                return
            }
            val oldValue = __v_raw.orderItems
            __v_raw.orderItems = value
            triggerReactiveSet(__v_raw, "orderItems", oldValue, value)
        }
    override var totalQuantity: Number
        get() {
            return trackReactiveGet(__v_raw, "totalQuantity", __v_raw.totalQuantity, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("totalQuantity")) {
                return
            }
            val oldValue = __v_raw.totalQuantity
            __v_raw.totalQuantity = value
            triggerReactiveSet(__v_raw, "totalQuantity", oldValue, value)
        }
    override var reviewed: Boolean?
        get() {
            return trackReactiveGet(__v_raw, "reviewed", __v_raw.reviewed, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("reviewed")) {
                return
            }
            val oldValue = __v_raw.reviewed
            __v_raw.reviewed = value
            triggerReactiveSet(__v_raw, "reviewed", oldValue, value)
        }
    override var reviewCount: Number?
        get() {
            return trackReactiveGet(__v_raw, "reviewCount", __v_raw.reviewCount, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("reviewCount")) {
                return
            }
            val oldValue = __v_raw.reviewCount
            __v_raw.reviewCount = value
            triggerReactiveSet(__v_raw, "reviewCount", oldValue, value)
        }
}
open class DirectBuyItem (
    @JsonNotNull
    open var productId: Number,
    @JsonNotNull
    open var skuId: Number,
    @JsonNotNull
    open var quantity: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("DirectBuyItem", "api/order.uts", 51, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return DirectBuyItemReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class DirectBuyItemReactiveObject : DirectBuyItem, IUTSReactive<DirectBuyItem> {
    override var __v_raw: DirectBuyItem
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: DirectBuyItem, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(productId = __v_raw.productId, skuId = __v_raw.skuId, quantity = __v_raw.quantity) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): DirectBuyItemReactiveObject {
        return DirectBuyItemReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var productId: Number
        get() {
            return trackReactiveGet(__v_raw, "productId", __v_raw.productId, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("productId")) {
                return
            }
            val oldValue = __v_raw.productId
            __v_raw.productId = value
            triggerReactiveSet(__v_raw, "productId", oldValue, value)
        }
    override var skuId: Number
        get() {
            return trackReactiveGet(__v_raw, "skuId", __v_raw.skuId, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("skuId")) {
                return
            }
            val oldValue = __v_raw.skuId
            __v_raw.skuId = value
            triggerReactiveSet(__v_raw, "skuId", oldValue, value)
        }
    override var quantity: Number
        get() {
            return trackReactiveGet(__v_raw, "quantity", __v_raw.quantity, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("quantity")) {
                return
            }
            val oldValue = __v_raw.quantity
            __v_raw.quantity = value
            triggerReactiveSet(__v_raw, "quantity", oldValue, value)
        }
}
open class OrderCreateParams (
    open var cartItemIds: UTSArray<Number>? = null,
    open var directBuyItem: DirectBuyItem? = null,
    @JsonNotNull
    open var receiverName: String,
    @JsonNotNull
    open var receiverPhone: String,
    @JsonNotNull
    open var receiverAddress: String,
    @JsonNotNull
    open var receiverProvince: String,
    @JsonNotNull
    open var receiverCity: String,
    @JsonNotNull
    open var receiverDistrict: String,
    open var remark: String? = null,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("OrderCreateParams", "api/order.uts", 57, 13)
    }
}
open class OrderQueryParams (
    open var status: Number? = null,
    @JsonNotNull
    open var pageNum: Number,
    @JsonNotNull
    open var pageSize: Number,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("OrderQueryParams", "api/order.uts", 69, 13)
    }
}
fun createOrder(data: OrderCreateParams): UTSPromise<ResponseDataType> {
    val jsonData: UTSJSONObject = object : UTSJSONObject(UTSSourceMapPosition("jsonData", "api/order.uts", 78, 11)) {
        var cartItemIds = data.cartItemIds
        var directBuyItem = if (data.directBuyItem != null) {
            object : UTSJSONObject() {
                var productId = data.directBuyItem!!.productId
                var skuId = data.directBuyItem!!.skuId
                var quantity = data.directBuyItem!!.quantity
            }
        } else {
            null
        }
        var receiverName = data.receiverName
        var receiverPhone = data.receiverPhone
        var receiverAddress = data.receiverAddress
        var receiverProvince = data.receiverProvince
        var receiverCity = data.receiverCity
        var receiverDistrict = data.receiverDistrict
        var remark = data.remark
    }
    return post("/orders", jsonData)
}
fun getOrderList(params: OrderQueryParams): UTSPromise<ResponseDataType> {
    var url = "/orders?pageNum=" + params.pageNum + "&pageSize=" + params.pageSize
    if (params.status != null) {
        url += "&status=" + params.status
    }
    return get(url)
}
fun getOrderDetail(orderId: Number): UTSPromise<ResponseDataType> {
    return get("/orders/" + orderId)
}
fun cancelOrder(orderId: Number, reason: String?): UTSPromise<ResponseDataType> {
    var url = "/orders/" + orderId + "/cancel"
    if (reason != null && reason !== "") {
        url += "?reason=" + UTSAndroid.consoleDebugError(encodeURIComponent(reason), " at api/order.uts:117")
    }
    return put(url, null)
}
fun confirmReceive(orderId: Number): UTSPromise<ResponseDataType> {
    return put("/orders/" + orderId + "/receive", null)
}
fun deleteOrder(orderId: Number): UTSPromise<ResponseDataType> {
    return del("/orders/" + orderId)
}
fun payOrder(orderId: Number): UTSPromise<ResponseDataType> {
    return put("/orders/" + orderId + "/pay", null)
}
fun getOrderCount(): UTSPromise<ResponseDataType> {
    return get("/orders/count")
}
open class LogisticsTrack (
    @JsonNotNull
    open var time: String,
    @JsonNotNull
    open var status: String,
    @JsonNotNull
    open var desc: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("LogisticsTrack", "api/order.uts", 146, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return LogisticsTrackReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class LogisticsTrackReactiveObject : LogisticsTrack, IUTSReactive<LogisticsTrack> {
    override var __v_raw: LogisticsTrack
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: LogisticsTrack, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(time = __v_raw.time, status = __v_raw.status, desc = __v_raw.desc) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): LogisticsTrackReactiveObject {
        return LogisticsTrackReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var time: String
        get() {
            return trackReactiveGet(__v_raw, "time", __v_raw.time, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("time")) {
                return
            }
            val oldValue = __v_raw.time
            __v_raw.time = value
            triggerReactiveSet(__v_raw, "time", oldValue, value)
        }
    override var status: String
        get() {
            return trackReactiveGet(__v_raw, "status", __v_raw.status, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("status")) {
                return
            }
            val oldValue = __v_raw.status
            __v_raw.status = value
            triggerReactiveSet(__v_raw, "status", oldValue, value)
        }
    override var desc: String
        get() {
            return trackReactiveGet(__v_raw, "desc", __v_raw.desc, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("desc")) {
                return
            }
            val oldValue = __v_raw.desc
            __v_raw.desc = value
            triggerReactiveSet(__v_raw, "desc", oldValue, value)
        }
}
open class LogisticsVO (
    @JsonNotNull
    open var orderId: Number,
    @JsonNotNull
    open var orderNo: String,
    @JsonNotNull
    open var logisticsNo: String,
    @JsonNotNull
    open var logisticsCompany: String,
    @JsonNotNull
    open var status: Number,
    @JsonNotNull
    open var statusDesc: String,
    @JsonNotNull
    open var delivered: Boolean = false,
    @JsonNotNull
    open var deliveredDesc: String,
    open var shipTime: String? = null,
    open var receiveTime: String? = null,
    open var autoReceiveTime: String? = null,
    @JsonNotNull
    open var tracks: UTSArray<LogisticsTrack>,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("LogisticsVO", "api/order.uts", 152, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return LogisticsVOReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class LogisticsVOReactiveObject : LogisticsVO, IUTSReactive<LogisticsVO> {
    override var __v_raw: LogisticsVO
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: LogisticsVO, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(orderId = __v_raw.orderId, orderNo = __v_raw.orderNo, logisticsNo = __v_raw.logisticsNo, logisticsCompany = __v_raw.logisticsCompany, status = __v_raw.status, statusDesc = __v_raw.statusDesc, delivered = __v_raw.delivered, deliveredDesc = __v_raw.deliveredDesc, shipTime = __v_raw.shipTime, receiveTime = __v_raw.receiveTime, autoReceiveTime = __v_raw.autoReceiveTime, tracks = __v_raw.tracks) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): LogisticsVOReactiveObject {
        return LogisticsVOReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var orderId: Number
        get() {
            return trackReactiveGet(__v_raw, "orderId", __v_raw.orderId, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("orderId")) {
                return
            }
            val oldValue = __v_raw.orderId
            __v_raw.orderId = value
            triggerReactiveSet(__v_raw, "orderId", oldValue, value)
        }
    override var orderNo: String
        get() {
            return trackReactiveGet(__v_raw, "orderNo", __v_raw.orderNo, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("orderNo")) {
                return
            }
            val oldValue = __v_raw.orderNo
            __v_raw.orderNo = value
            triggerReactiveSet(__v_raw, "orderNo", oldValue, value)
        }
    override var logisticsNo: String
        get() {
            return trackReactiveGet(__v_raw, "logisticsNo", __v_raw.logisticsNo, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("logisticsNo")) {
                return
            }
            val oldValue = __v_raw.logisticsNo
            __v_raw.logisticsNo = value
            triggerReactiveSet(__v_raw, "logisticsNo", oldValue, value)
        }
    override var logisticsCompany: String
        get() {
            return trackReactiveGet(__v_raw, "logisticsCompany", __v_raw.logisticsCompany, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("logisticsCompany")) {
                return
            }
            val oldValue = __v_raw.logisticsCompany
            __v_raw.logisticsCompany = value
            triggerReactiveSet(__v_raw, "logisticsCompany", oldValue, value)
        }
    override var status: Number
        get() {
            return trackReactiveGet(__v_raw, "status", __v_raw.status, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("status")) {
                return
            }
            val oldValue = __v_raw.status
            __v_raw.status = value
            triggerReactiveSet(__v_raw, "status", oldValue, value)
        }
    override var statusDesc: String
        get() {
            return trackReactiveGet(__v_raw, "statusDesc", __v_raw.statusDesc, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("statusDesc")) {
                return
            }
            val oldValue = __v_raw.statusDesc
            __v_raw.statusDesc = value
            triggerReactiveSet(__v_raw, "statusDesc", oldValue, value)
        }
    override var delivered: Boolean
        get() {
            return trackReactiveGet(__v_raw, "delivered", __v_raw.delivered, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("delivered")) {
                return
            }
            val oldValue = __v_raw.delivered
            __v_raw.delivered = value
            triggerReactiveSet(__v_raw, "delivered", oldValue, value)
        }
    override var deliveredDesc: String
        get() {
            return trackReactiveGet(__v_raw, "deliveredDesc", __v_raw.deliveredDesc, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("deliveredDesc")) {
                return
            }
            val oldValue = __v_raw.deliveredDesc
            __v_raw.deliveredDesc = value
            triggerReactiveSet(__v_raw, "deliveredDesc", oldValue, value)
        }
    override var shipTime: String?
        get() {
            return trackReactiveGet(__v_raw, "shipTime", __v_raw.shipTime, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("shipTime")) {
                return
            }
            val oldValue = __v_raw.shipTime
            __v_raw.shipTime = value
            triggerReactiveSet(__v_raw, "shipTime", oldValue, value)
        }
    override var receiveTime: String?
        get() {
            return trackReactiveGet(__v_raw, "receiveTime", __v_raw.receiveTime, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("receiveTime")) {
                return
            }
            val oldValue = __v_raw.receiveTime
            __v_raw.receiveTime = value
            triggerReactiveSet(__v_raw, "receiveTime", oldValue, value)
        }
    override var autoReceiveTime: String?
        get() {
            return trackReactiveGet(__v_raw, "autoReceiveTime", __v_raw.autoReceiveTime, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("autoReceiveTime")) {
                return
            }
            val oldValue = __v_raw.autoReceiveTime
            __v_raw.autoReceiveTime = value
            triggerReactiveSet(__v_raw, "autoReceiveTime", oldValue, value)
        }
    override var tracks: UTSArray<LogisticsTrack>
        get() {
            return trackReactiveGet(__v_raw, "tracks", __v_raw.tracks, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("tracks")) {
                return
            }
            val oldValue = __v_raw.tracks
            __v_raw.tracks = value
            triggerReactiveSet(__v_raw, "tracks", oldValue, value)
        }
}
fun getOrderLogistics(orderId: Number): UTSPromise<ResponseDataType> {
    return get("/orders/" + orderId + "/logistics")
}
open class NavItemType (
    @JsonNotNull
    open var name: String,
    @JsonNotNull
    open var path: String,
    @JsonNotNull
    open var icon: String,
    @JsonNotNull
    open var activeIcon: String,
    @JsonNotNull
    open var badge: Boolean = false,
    @JsonNotNull
    open var badgeCount: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("NavItemType", "components/BottomNav.uvue", 23, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return NavItemTypeReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class NavItemTypeReactiveObject : NavItemType, IUTSReactive<NavItemType> {
    override var __v_raw: NavItemType
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: NavItemType, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(name = __v_raw.name, path = __v_raw.path, icon = __v_raw.icon, activeIcon = __v_raw.activeIcon, badge = __v_raw.badge, badgeCount = __v_raw.badgeCount) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): NavItemTypeReactiveObject {
        return NavItemTypeReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var name: String
        get() {
            return trackReactiveGet(__v_raw, "name", __v_raw.name, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("name")) {
                return
            }
            val oldValue = __v_raw.name
            __v_raw.name = value
            triggerReactiveSet(__v_raw, "name", oldValue, value)
        }
    override var path: String
        get() {
            return trackReactiveGet(__v_raw, "path", __v_raw.path, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("path")) {
                return
            }
            val oldValue = __v_raw.path
            __v_raw.path = value
            triggerReactiveSet(__v_raw, "path", oldValue, value)
        }
    override var icon: String
        get() {
            return trackReactiveGet(__v_raw, "icon", __v_raw.icon, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("icon")) {
                return
            }
            val oldValue = __v_raw.icon
            __v_raw.icon = value
            triggerReactiveSet(__v_raw, "icon", oldValue, value)
        }
    override var activeIcon: String
        get() {
            return trackReactiveGet(__v_raw, "activeIcon", __v_raw.activeIcon, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("activeIcon")) {
                return
            }
            val oldValue = __v_raw.activeIcon
            __v_raw.activeIcon = value
            triggerReactiveSet(__v_raw, "activeIcon", oldValue, value)
        }
    override var badge: Boolean
        get() {
            return trackReactiveGet(__v_raw, "badge", __v_raw.badge, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("badge")) {
                return
            }
            val oldValue = __v_raw.badge
            __v_raw.badge = value
            triggerReactiveSet(__v_raw, "badge", oldValue, value)
        }
    override var badgeCount: Number
        get() {
            return trackReactiveGet(__v_raw, "badgeCount", __v_raw.badgeCount, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("badgeCount")) {
                return
            }
            val oldValue = __v_raw.badgeCount
            __v_raw.badgeCount = value
            triggerReactiveSet(__v_raw, "badgeCount", oldValue, value)
        }
}
val GenComponentsBottomNavClass = CreateVueComponent(GenComponentsBottomNav::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "component", name = GenComponentsBottomNav.name, inheritAttrs = GenComponentsBottomNav.inheritAttrs, inject = GenComponentsBottomNav.inject, props = GenComponentsBottomNav.props, propsNeedCastKeys = GenComponentsBottomNav.propsNeedCastKeys, emits = GenComponentsBottomNav.emits, components = GenComponentsBottomNav.components, styles = GenComponentsBottomNav.styles)
}
, fun(instance, renderer): GenComponentsBottomNav {
    return GenComponentsBottomNav(instance)
}
)
open class BannerType (
    @JsonNotNull
    open var image: String,
    @JsonNotNull
    open var title: String,
    @JsonNotNull
    open var link: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("BannerType", "pages/index/index.uvue", 169, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return BannerTypeReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class BannerTypeReactiveObject : BannerType, IUTSReactive<BannerType> {
    override var __v_raw: BannerType
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: BannerType, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(image = __v_raw.image, title = __v_raw.title, link = __v_raw.link) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): BannerTypeReactiveObject {
        return BannerTypeReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var image: String
        get() {
            return trackReactiveGet(__v_raw, "image", __v_raw.image, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("image")) {
                return
            }
            val oldValue = __v_raw.image
            __v_raw.image = value
            triggerReactiveSet(__v_raw, "image", oldValue, value)
        }
    override var title: String
        get() {
            return trackReactiveGet(__v_raw, "title", __v_raw.title, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("title")) {
                return
            }
            val oldValue = __v_raw.title
            __v_raw.title = value
            triggerReactiveSet(__v_raw, "title", oldValue, value)
        }
    override var link: String
        get() {
            return trackReactiveGet(__v_raw, "link", __v_raw.link, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("link")) {
                return
            }
            val oldValue = __v_raw.link
            __v_raw.link = value
            triggerReactiveSet(__v_raw, "link", oldValue, value)
        }
}
open class CategoryType (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var name: String,
    @JsonNotNull
    open var icon: String,
    @JsonNotNull
    open var bgColor: String,
    @JsonNotNull
    open var keyword: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("CategoryType", "pages/index/index.uvue", 175, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return CategoryTypeReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class CategoryTypeReactiveObject : CategoryType, IUTSReactive<CategoryType> {
    override var __v_raw: CategoryType
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: CategoryType, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, name = __v_raw.name, icon = __v_raw.icon, bgColor = __v_raw.bgColor, keyword = __v_raw.keyword) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): CategoryTypeReactiveObject {
        return CategoryTypeReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return trackReactiveGet(__v_raw, "id", __v_raw.id, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            triggerReactiveSet(__v_raw, "id", oldValue, value)
        }
    override var name: String
        get() {
            return trackReactiveGet(__v_raw, "name", __v_raw.name, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("name")) {
                return
            }
            val oldValue = __v_raw.name
            __v_raw.name = value
            triggerReactiveSet(__v_raw, "name", oldValue, value)
        }
    override var icon: String
        get() {
            return trackReactiveGet(__v_raw, "icon", __v_raw.icon, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("icon")) {
                return
            }
            val oldValue = __v_raw.icon
            __v_raw.icon = value
            triggerReactiveSet(__v_raw, "icon", oldValue, value)
        }
    override var bgColor: String
        get() {
            return trackReactiveGet(__v_raw, "bgColor", __v_raw.bgColor, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("bgColor")) {
                return
            }
            val oldValue = __v_raw.bgColor
            __v_raw.bgColor = value
            triggerReactiveSet(__v_raw, "bgColor", oldValue, value)
        }
    override var keyword: String
        get() {
            return trackReactiveGet(__v_raw, "keyword", __v_raw.keyword, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("keyword")) {
                return
            }
            val oldValue = __v_raw.keyword
            __v_raw.keyword = value
            triggerReactiveSet(__v_raw, "keyword", oldValue, value)
        }
}
open class HotProductType (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var name: String,
    @JsonNotNull
    open var desc: String,
    @JsonNotNull
    open var price: String,
    open var originPrice: String? = null,
    @JsonNotNull
    open var image: String,
    @JsonNotNull
    open var tag: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("HotProductType", "pages/index/index.uvue", 183, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return HotProductTypeReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class HotProductTypeReactiveObject : HotProductType, IUTSReactive<HotProductType> {
    override var __v_raw: HotProductType
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: HotProductType, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, name = __v_raw.name, desc = __v_raw.desc, price = __v_raw.price, originPrice = __v_raw.originPrice, image = __v_raw.image, tag = __v_raw.tag) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): HotProductTypeReactiveObject {
        return HotProductTypeReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return trackReactiveGet(__v_raw, "id", __v_raw.id, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            triggerReactiveSet(__v_raw, "id", oldValue, value)
        }
    override var name: String
        get() {
            return trackReactiveGet(__v_raw, "name", __v_raw.name, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("name")) {
                return
            }
            val oldValue = __v_raw.name
            __v_raw.name = value
            triggerReactiveSet(__v_raw, "name", oldValue, value)
        }
    override var desc: String
        get() {
            return trackReactiveGet(__v_raw, "desc", __v_raw.desc, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("desc")) {
                return
            }
            val oldValue = __v_raw.desc
            __v_raw.desc = value
            triggerReactiveSet(__v_raw, "desc", oldValue, value)
        }
    override var price: String
        get() {
            return trackReactiveGet(__v_raw, "price", __v_raw.price, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("price")) {
                return
            }
            val oldValue = __v_raw.price
            __v_raw.price = value
            triggerReactiveSet(__v_raw, "price", oldValue, value)
        }
    override var originPrice: String?
        get() {
            return trackReactiveGet(__v_raw, "originPrice", __v_raw.originPrice, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("originPrice")) {
                return
            }
            val oldValue = __v_raw.originPrice
            __v_raw.originPrice = value
            triggerReactiveSet(__v_raw, "originPrice", oldValue, value)
        }
    override var image: String
        get() {
            return trackReactiveGet(__v_raw, "image", __v_raw.image, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("image")) {
                return
            }
            val oldValue = __v_raw.image
            __v_raw.image = value
            triggerReactiveSet(__v_raw, "image", oldValue, value)
        }
    override var tag: String
        get() {
            return trackReactiveGet(__v_raw, "tag", __v_raw.tag, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("tag")) {
                return
            }
            val oldValue = __v_raw.tag
            __v_raw.tag = value
            triggerReactiveSet(__v_raw, "tag", oldValue, value)
        }
}
open class LocationCacheType1 (
    @JsonNotNull
    open var timestamp: Number,
    @JsonNotNull
    open var location: LocationInfoType,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("LocationCacheType", "pages/index/index.uvue", 193, 6)
    }
}
val GenPagesIndexIndexClass = CreateVueComponent(GenPagesIndexIndex::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesIndexIndex.inheritAttrs, inject = GenPagesIndexIndex.inject, props = GenPagesIndexIndex.props, propsNeedCastKeys = GenPagesIndexIndex.propsNeedCastKeys, emits = GenPagesIndexIndex.emits, components = GenPagesIndexIndex.components, styles = GenPagesIndexIndex.styles)
}
, fun(instance, renderer): GenPagesIndexIndex {
    return GenPagesIndexIndex(instance, renderer)
}
)
val GenPagesSearchIndexClass = CreateVueComponent(GenPagesSearchIndex::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesSearchIndex.inheritAttrs, inject = GenPagesSearchIndex.inject, props = GenPagesSearchIndex.props, propsNeedCastKeys = GenPagesSearchIndex.propsNeedCastKeys, emits = GenPagesSearchIndex.emits, components = GenPagesSearchIndex.components, styles = GenPagesSearchIndex.styles)
}
, fun(instance, renderer): GenPagesSearchIndex {
    return GenPagesSearchIndex(instance, renderer)
}
)
fun login(username: String, password: String): UTSPromise<ResponseDataType> {
    return post("/users/login", UTSJSONObject(Map<String, Any?>(utsArrayOf(
        utsArrayOf(
            "username",
            username
        ),
        utsArrayOf(
            "password",
            password
        )
    ))))
}
fun register(username: String, email: String, phone: String, password: String, nickname: String, confirmPassword: String): UTSPromise<ResponseDataType> {
    return post("/users/register", UTSJSONObject(Map<String, Any?>(utsArrayOf(
        utsArrayOf(
            "username",
            username
        ),
        utsArrayOf(
            "password",
            password
        ),
        utsArrayOf(
            "email",
            email
        ),
        utsArrayOf(
            "phone",
            phone
        ),
        utsArrayOf(
            "nickname",
            nickname
        ),
        utsArrayOf(
            "confirmPassword",
            confirmPassword
        )
    ))))
}
fun logout(): UTSPromise<ResponseDataType> {
    return post("/users/logout", null)
}
fun getUserProfile(): UTSPromise<ResponseDataType> {
    return get("/users/profile")
}
fun updateUserProfile(data: Any): UTSPromise<ResponseDataType> {
    return put("/users/profile", data)
}
fun changePassword(oldPassword: String, newPassword: String, confirmPassword: String): UTSPromise<ResponseDataType> {
    return put("/users/password", UTSJSONObject(Map<String, Any?>(utsArrayOf(
        utsArrayOf(
            "oldPassword",
            oldPassword
        ),
        utsArrayOf(
            "newPassword",
            newPassword
        ),
        utsArrayOf(
            "confirmPassword",
            confirmPassword
        )
    ))))
}
fun uploadAvatar(filePath: String): UTSPromise<UTSJSONObject> {
    return UTSPromise(fun(resolve, reject){
        val token = uni_getStorageSync("auth_token") as String?
        var header: UTSJSONObject
        if (token != null && token !== "") {
            header = object : UTSJSONObject() {
                var Authorization = "Bearer " + token
            }
        } else {
            header = UTSJSONObject()
        }
        uni_uploadFile(UploadFileOptions(url = "" + API_BASE_URL + "/users/avatar/upload", filePath = filePath, name = "file", header = header, success = fun(res: UploadFileSuccess) {
            console.log("头像上传响应状态码:", res.statusCode, " at api/user.ts:85")
            val resData = res.data
            if (resData != null && resData != "") {
                try {
                    val parsed = UTSAndroid.consoleDebugError(JSON.parse(resData), " at api/user.uts:80") as UTSJSONObject
                    console.log("头像上传响应数据:", resData, " at api/user.ts:90")
                    val code = (parsed.getNumber("code") ?: 0).toInt()
                    if (code == 200) {
                        resolve(parsed)
                    } else {
                        val msg = parsed.getString("message") ?: "上传失败"
                        reject(UTSError(msg))
                    }
                } catch (e: Throwable) {
                    console.error("解析头像上传响应失败:", e, " at api/user.ts:99")
                    reject(UTSError("解析响应失败"))
                }
            } else {
                reject(UTSError("响应数据为空"))
            }
        }
        , fail = fun(err: UploadFileFail) {
            console.error("头像上传 uni.uploadFile 失败:", err.errMsg, " at api/user.ts:107")
            reject(UTSError("网络请求失败，请检查网络连接"))
        }
        ))
    }
    )
}
open class LoginForm (
    @JsonNotNull
    open var username: String,
    @JsonNotNull
    open var password: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("LoginForm", "pages/login/login.uvue", 76, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return LoginFormReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class LoginFormReactiveObject : LoginForm, IUTSReactive<LoginForm> {
    override var __v_raw: LoginForm
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: LoginForm, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(username = __v_raw.username, password = __v_raw.password) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): LoginFormReactiveObject {
        return LoginFormReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var username: String
        get() {
            return trackReactiveGet(__v_raw, "username", __v_raw.username, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("username")) {
                return
            }
            val oldValue = __v_raw.username
            __v_raw.username = value
            triggerReactiveSet(__v_raw, "username", oldValue, value)
        }
    override var password: String
        get() {
            return trackReactiveGet(__v_raw, "password", __v_raw.password, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("password")) {
                return
            }
            val oldValue = __v_raw.password
            __v_raw.password = value
            triggerReactiveSet(__v_raw, "password", oldValue, value)
        }
}
open class LoginErrors (
    @JsonNotNull
    open var username: String,
    @JsonNotNull
    open var password: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("LoginErrors", "pages/login/login.uvue", 81, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return LoginErrorsReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class LoginErrorsReactiveObject : LoginErrors, IUTSReactive<LoginErrors> {
    override var __v_raw: LoginErrors
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: LoginErrors, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(username = __v_raw.username, password = __v_raw.password) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): LoginErrorsReactiveObject {
        return LoginErrorsReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var username: String
        get() {
            return trackReactiveGet(__v_raw, "username", __v_raw.username, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("username")) {
                return
            }
            val oldValue = __v_raw.username
            __v_raw.username = value
            triggerReactiveSet(__v_raw, "username", oldValue, value)
        }
    override var password: String
        get() {
            return trackReactiveGet(__v_raw, "password", __v_raw.password, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("password")) {
                return
            }
            val oldValue = __v_raw.password
            __v_raw.password = value
            triggerReactiveSet(__v_raw, "password", oldValue, value)
        }
}
val GenPagesLoginLoginClass = CreateVueComponent(GenPagesLoginLogin::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesLoginLogin.inheritAttrs, inject = GenPagesLoginLogin.inject, props = GenPagesLoginLogin.props, propsNeedCastKeys = GenPagesLoginLogin.propsNeedCastKeys, emits = GenPagesLoginLogin.emits, components = GenPagesLoginLogin.components, styles = GenPagesLoginLogin.styles)
}
, fun(instance, renderer): GenPagesLoginLogin {
    return GenPagesLoginLogin(instance, renderer)
}
)
open class RegisterForm (
    @JsonNotNull
    open var username: String,
    @JsonNotNull
    open var email: String,
    @JsonNotNull
    open var phone: String,
    @JsonNotNull
    open var password: String,
    @JsonNotNull
    open var confirmPassword: String,
    @JsonNotNull
    open var agreeTerms: Boolean = false,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("RegisterForm", "pages/register/register.uvue", 141, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return RegisterFormReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class RegisterFormReactiveObject : RegisterForm, IUTSReactive<RegisterForm> {
    override var __v_raw: RegisterForm
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: RegisterForm, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(username = __v_raw.username, email = __v_raw.email, phone = __v_raw.phone, password = __v_raw.password, confirmPassword = __v_raw.confirmPassword, agreeTerms = __v_raw.agreeTerms) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): RegisterFormReactiveObject {
        return RegisterFormReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var username: String
        get() {
            return trackReactiveGet(__v_raw, "username", __v_raw.username, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("username")) {
                return
            }
            val oldValue = __v_raw.username
            __v_raw.username = value
            triggerReactiveSet(__v_raw, "username", oldValue, value)
        }
    override var email: String
        get() {
            return trackReactiveGet(__v_raw, "email", __v_raw.email, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("email")) {
                return
            }
            val oldValue = __v_raw.email
            __v_raw.email = value
            triggerReactiveSet(__v_raw, "email", oldValue, value)
        }
    override var phone: String
        get() {
            return trackReactiveGet(__v_raw, "phone", __v_raw.phone, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("phone")) {
                return
            }
            val oldValue = __v_raw.phone
            __v_raw.phone = value
            triggerReactiveSet(__v_raw, "phone", oldValue, value)
        }
    override var password: String
        get() {
            return trackReactiveGet(__v_raw, "password", __v_raw.password, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("password")) {
                return
            }
            val oldValue = __v_raw.password
            __v_raw.password = value
            triggerReactiveSet(__v_raw, "password", oldValue, value)
        }
    override var confirmPassword: String
        get() {
            return trackReactiveGet(__v_raw, "confirmPassword", __v_raw.confirmPassword, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("confirmPassword")) {
                return
            }
            val oldValue = __v_raw.confirmPassword
            __v_raw.confirmPassword = value
            triggerReactiveSet(__v_raw, "confirmPassword", oldValue, value)
        }
    override var agreeTerms: Boolean
        get() {
            return trackReactiveGet(__v_raw, "agreeTerms", __v_raw.agreeTerms, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("agreeTerms")) {
                return
            }
            val oldValue = __v_raw.agreeTerms
            __v_raw.agreeTerms = value
            triggerReactiveSet(__v_raw, "agreeTerms", oldValue, value)
        }
}
open class RegisterErrors (
    @JsonNotNull
    open var username: String,
    @JsonNotNull
    open var email: String,
    @JsonNotNull
    open var phone: String,
    @JsonNotNull
    open var password: String,
    @JsonNotNull
    open var confirmPassword: String,
    @JsonNotNull
    open var agreement: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("RegisterErrors", "pages/register/register.uvue", 150, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return RegisterErrorsReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class RegisterErrorsReactiveObject : RegisterErrors, IUTSReactive<RegisterErrors> {
    override var __v_raw: RegisterErrors
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: RegisterErrors, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(username = __v_raw.username, email = __v_raw.email, phone = __v_raw.phone, password = __v_raw.password, confirmPassword = __v_raw.confirmPassword, agreement = __v_raw.agreement) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): RegisterErrorsReactiveObject {
        return RegisterErrorsReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var username: String
        get() {
            return trackReactiveGet(__v_raw, "username", __v_raw.username, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("username")) {
                return
            }
            val oldValue = __v_raw.username
            __v_raw.username = value
            triggerReactiveSet(__v_raw, "username", oldValue, value)
        }
    override var email: String
        get() {
            return trackReactiveGet(__v_raw, "email", __v_raw.email, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("email")) {
                return
            }
            val oldValue = __v_raw.email
            __v_raw.email = value
            triggerReactiveSet(__v_raw, "email", oldValue, value)
        }
    override var phone: String
        get() {
            return trackReactiveGet(__v_raw, "phone", __v_raw.phone, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("phone")) {
                return
            }
            val oldValue = __v_raw.phone
            __v_raw.phone = value
            triggerReactiveSet(__v_raw, "phone", oldValue, value)
        }
    override var password: String
        get() {
            return trackReactiveGet(__v_raw, "password", __v_raw.password, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("password")) {
                return
            }
            val oldValue = __v_raw.password
            __v_raw.password = value
            triggerReactiveSet(__v_raw, "password", oldValue, value)
        }
    override var confirmPassword: String
        get() {
            return trackReactiveGet(__v_raw, "confirmPassword", __v_raw.confirmPassword, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("confirmPassword")) {
                return
            }
            val oldValue = __v_raw.confirmPassword
            __v_raw.confirmPassword = value
            triggerReactiveSet(__v_raw, "confirmPassword", oldValue, value)
        }
    override var agreement: String
        get() {
            return trackReactiveGet(__v_raw, "agreement", __v_raw.agreement, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("agreement")) {
                return
            }
            val oldValue = __v_raw.agreement
            __v_raw.agreement = value
            triggerReactiveSet(__v_raw, "agreement", oldValue, value)
        }
}
val GenPagesRegisterRegisterClass = CreateVueComponent(GenPagesRegisterRegister::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesRegisterRegister.inheritAttrs, inject = GenPagesRegisterRegister.inject, props = GenPagesRegisterRegister.props, propsNeedCastKeys = GenPagesRegisterRegister.propsNeedCastKeys, emits = GenPagesRegisterRegister.emits, components = GenPagesRegisterRegister.components, styles = GenPagesRegisterRegister.styles)
}
, fun(instance, renderer): GenPagesRegisterRegister {
    return GenPagesRegisterRegister(instance, renderer)
}
)
fun getProductDetail(productId: Number): UTSPromise<ResponseDataType> {
    return get("/products/" + productId)
}
fun getCategories(): UTSPromise<ResponseDataType> {
    return get("/categories/tree")
}
fun collectProduct(productId: Number): UTSPromise<ResponseDataType> {
    return post("/products/" + productId + "/collect", null)
}
fun uncollectProduct(productId: Number): UTSPromise<ResponseDataType> {
    return del("/products/" + productId + "/uncollect")
}
fun isProductCollected(productId: Number): UTSPromise<ResponseDataType> {
    return get("/products/" + productId + "/is-collected")
}
fun getCollectionList(pageNum: Number, pageSize: Number, sortBy: String?, sortOrder: String?): UTSPromise<ResponseDataType> {
    var url = "/users/collections?pageNum=" + pageNum + "&pageSize=" + pageSize
    if (sortBy != null && sortBy != "") {
        url += "&sortBy=" + sortBy
    }
    if (sortOrder != null && sortOrder != "") {
        url += "&sortOrder=" + sortOrder
    }
    return get(url)
}
fun getCollectionCount(): UTSPromise<ResponseDataType> {
    return get("/users/collections/count")
}
fun clearCollections(): UTSPromise<ResponseDataType> {
    return del("/users/collections/clear")
}
fun getAdminProducts(pageNum: Number, pageSize: Number, status: Number?): UTSPromise<ResponseDataType> {
    var url = "/admin/products?pageNum=" + pageNum + "&pageSize=" + pageSize
    if (status != null) {
        url += "&status=" + status
    }
    return get(url)
}
fun auditProduct(productId: Number, status: Number, auditRemark: String?): UTSPromise<ResponseDataType> {
    val data: UTSJSONObject = UTSJSONObject(Map<String, Any?>(utsArrayOf(
        utsArrayOf(
            "__\$originalPosition",
            UTSSourceMapPosition("data", "api/product.uts", 152, 11)
        ),
        utsArrayOf(
            "status",
            status
        ),
        utsArrayOf(
            "auditRemark",
            auditRemark
        )
    )))
    return put("/products/" + productId + "/audit", data)
}
open class CategoryType1 (
    @JsonNotNull
    open var id: String,
    @JsonNotNull
    open var name: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("CategoryType", "pages/product/list.uvue", 108, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return CategoryType1ReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class CategoryType1ReactiveObject : CategoryType1, IUTSReactive<CategoryType1> {
    override var __v_raw: CategoryType1
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: CategoryType1, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, name = __v_raw.name) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): CategoryType1ReactiveObject {
        return CategoryType1ReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: String
        get() {
            return trackReactiveGet(__v_raw, "id", __v_raw.id, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            triggerReactiveSet(__v_raw, "id", oldValue, value)
        }
    override var name: String
        get() {
            return trackReactiveGet(__v_raw, "name", __v_raw.name, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("name")) {
                return
            }
            val oldValue = __v_raw.name
            __v_raw.name = value
            triggerReactiveSet(__v_raw, "name", oldValue, value)
        }
}
open class ProductType (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var name: String,
    @JsonNotNull
    open var description: String,
    @JsonNotNull
    open var image: String,
    @JsonNotNull
    open var price: Number,
    open var originalPrice: Number? = null,
    @JsonNotNull
    open var sales: Number,
    @JsonNotNull
    open var category: String,
    open var tags: UTSArray<String>? = null,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ProductType", "pages/product/list.uvue", 113, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ProductTypeReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class ProductTypeReactiveObject : ProductType, IUTSReactive<ProductType> {
    override var __v_raw: ProductType
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ProductType, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, name = __v_raw.name, description = __v_raw.description, image = __v_raw.image, price = __v_raw.price, originalPrice = __v_raw.originalPrice, sales = __v_raw.sales, category = __v_raw.category, tags = __v_raw.tags) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ProductTypeReactiveObject {
        return ProductTypeReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return trackReactiveGet(__v_raw, "id", __v_raw.id, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            triggerReactiveSet(__v_raw, "id", oldValue, value)
        }
    override var name: String
        get() {
            return trackReactiveGet(__v_raw, "name", __v_raw.name, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("name")) {
                return
            }
            val oldValue = __v_raw.name
            __v_raw.name = value
            triggerReactiveSet(__v_raw, "name", oldValue, value)
        }
    override var description: String
        get() {
            return trackReactiveGet(__v_raw, "description", __v_raw.description, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("description")) {
                return
            }
            val oldValue = __v_raw.description
            __v_raw.description = value
            triggerReactiveSet(__v_raw, "description", oldValue, value)
        }
    override var image: String
        get() {
            return trackReactiveGet(__v_raw, "image", __v_raw.image, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("image")) {
                return
            }
            val oldValue = __v_raw.image
            __v_raw.image = value
            triggerReactiveSet(__v_raw, "image", oldValue, value)
        }
    override var price: Number
        get() {
            return trackReactiveGet(__v_raw, "price", __v_raw.price, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("price")) {
                return
            }
            val oldValue = __v_raw.price
            __v_raw.price = value
            triggerReactiveSet(__v_raw, "price", oldValue, value)
        }
    override var originalPrice: Number?
        get() {
            return trackReactiveGet(__v_raw, "originalPrice", __v_raw.originalPrice, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("originalPrice")) {
                return
            }
            val oldValue = __v_raw.originalPrice
            __v_raw.originalPrice = value
            triggerReactiveSet(__v_raw, "originalPrice", oldValue, value)
        }
    override var sales: Number
        get() {
            return trackReactiveGet(__v_raw, "sales", __v_raw.sales, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("sales")) {
                return
            }
            val oldValue = __v_raw.sales
            __v_raw.sales = value
            triggerReactiveSet(__v_raw, "sales", oldValue, value)
        }
    override var category: String
        get() {
            return trackReactiveGet(__v_raw, "category", __v_raw.category, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("category")) {
                return
            }
            val oldValue = __v_raw.category
            __v_raw.category = value
            triggerReactiveSet(__v_raw, "category", oldValue, value)
        }
    override var tags: UTSArray<String>?
        get() {
            return trackReactiveGet(__v_raw, "tags", __v_raw.tags, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("tags")) {
                return
            }
            val oldValue = __v_raw.tags
            __v_raw.tags = value
            triggerReactiveSet(__v_raw, "tags", oldValue, value)
        }
}
val GenPagesProductListClass = CreateVueComponent(GenPagesProductList::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesProductList.inheritAttrs, inject = GenPagesProductList.inject, props = GenPagesProductList.props, propsNeedCastKeys = GenPagesProductList.propsNeedCastKeys, emits = GenPagesProductList.emits, components = GenPagesProductList.components, styles = GenPagesProductList.styles)
}
, fun(instance, renderer): GenPagesProductList {
    return GenPagesProductList(instance, renderer)
}
)
open class ReviewVO (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var productId: Number,
    @JsonNotNull
    open var productName: String,
    @JsonNotNull
    open var productMainImage: String,
    @JsonNotNull
    open var userId: Number,
    @JsonNotNull
    open var userNickname: String,
    @JsonNotNull
    open var userAvatar: String,
    @JsonNotNull
    open var orderId: Number,
    @JsonNotNull
    open var rating: Number,
    @JsonNotNull
    open var content: String,
    @JsonNotNull
    open var images: String,
    open var replyContent: String? = null,
    open var replyTime: String? = null,
    @JsonNotNull
    open var status: Number,
    @JsonNotNull
    open var createTime: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ReviewVO", "api/review.uts", 5, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ReviewVOReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class ReviewVOReactiveObject : ReviewVO, IUTSReactive<ReviewVO> {
    override var __v_raw: ReviewVO
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ReviewVO, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, productId = __v_raw.productId, productName = __v_raw.productName, productMainImage = __v_raw.productMainImage, userId = __v_raw.userId, userNickname = __v_raw.userNickname, userAvatar = __v_raw.userAvatar, orderId = __v_raw.orderId, rating = __v_raw.rating, content = __v_raw.content, images = __v_raw.images, replyContent = __v_raw.replyContent, replyTime = __v_raw.replyTime, status = __v_raw.status, createTime = __v_raw.createTime) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ReviewVOReactiveObject {
        return ReviewVOReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return trackReactiveGet(__v_raw, "id", __v_raw.id, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            triggerReactiveSet(__v_raw, "id", oldValue, value)
        }
    override var productId: Number
        get() {
            return trackReactiveGet(__v_raw, "productId", __v_raw.productId, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("productId")) {
                return
            }
            val oldValue = __v_raw.productId
            __v_raw.productId = value
            triggerReactiveSet(__v_raw, "productId", oldValue, value)
        }
    override var productName: String
        get() {
            return trackReactiveGet(__v_raw, "productName", __v_raw.productName, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("productName")) {
                return
            }
            val oldValue = __v_raw.productName
            __v_raw.productName = value
            triggerReactiveSet(__v_raw, "productName", oldValue, value)
        }
    override var productMainImage: String
        get() {
            return trackReactiveGet(__v_raw, "productMainImage", __v_raw.productMainImage, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("productMainImage")) {
                return
            }
            val oldValue = __v_raw.productMainImage
            __v_raw.productMainImage = value
            triggerReactiveSet(__v_raw, "productMainImage", oldValue, value)
        }
    override var userId: Number
        get() {
            return trackReactiveGet(__v_raw, "userId", __v_raw.userId, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("userId")) {
                return
            }
            val oldValue = __v_raw.userId
            __v_raw.userId = value
            triggerReactiveSet(__v_raw, "userId", oldValue, value)
        }
    override var userNickname: String
        get() {
            return trackReactiveGet(__v_raw, "userNickname", __v_raw.userNickname, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("userNickname")) {
                return
            }
            val oldValue = __v_raw.userNickname
            __v_raw.userNickname = value
            triggerReactiveSet(__v_raw, "userNickname", oldValue, value)
        }
    override var userAvatar: String
        get() {
            return trackReactiveGet(__v_raw, "userAvatar", __v_raw.userAvatar, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("userAvatar")) {
                return
            }
            val oldValue = __v_raw.userAvatar
            __v_raw.userAvatar = value
            triggerReactiveSet(__v_raw, "userAvatar", oldValue, value)
        }
    override var orderId: Number
        get() {
            return trackReactiveGet(__v_raw, "orderId", __v_raw.orderId, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("orderId")) {
                return
            }
            val oldValue = __v_raw.orderId
            __v_raw.orderId = value
            triggerReactiveSet(__v_raw, "orderId", oldValue, value)
        }
    override var rating: Number
        get() {
            return trackReactiveGet(__v_raw, "rating", __v_raw.rating, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("rating")) {
                return
            }
            val oldValue = __v_raw.rating
            __v_raw.rating = value
            triggerReactiveSet(__v_raw, "rating", oldValue, value)
        }
    override var content: String
        get() {
            return trackReactiveGet(__v_raw, "content", __v_raw.content, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("content")) {
                return
            }
            val oldValue = __v_raw.content
            __v_raw.content = value
            triggerReactiveSet(__v_raw, "content", oldValue, value)
        }
    override var images: String
        get() {
            return trackReactiveGet(__v_raw, "images", __v_raw.images, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("images")) {
                return
            }
            val oldValue = __v_raw.images
            __v_raw.images = value
            triggerReactiveSet(__v_raw, "images", oldValue, value)
        }
    override var replyContent: String?
        get() {
            return trackReactiveGet(__v_raw, "replyContent", __v_raw.replyContent, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("replyContent")) {
                return
            }
            val oldValue = __v_raw.replyContent
            __v_raw.replyContent = value
            triggerReactiveSet(__v_raw, "replyContent", oldValue, value)
        }
    override var replyTime: String?
        get() {
            return trackReactiveGet(__v_raw, "replyTime", __v_raw.replyTime, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("replyTime")) {
                return
            }
            val oldValue = __v_raw.replyTime
            __v_raw.replyTime = value
            triggerReactiveSet(__v_raw, "replyTime", oldValue, value)
        }
    override var status: Number
        get() {
            return trackReactiveGet(__v_raw, "status", __v_raw.status, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("status")) {
                return
            }
            val oldValue = __v_raw.status
            __v_raw.status = value
            triggerReactiveSet(__v_raw, "status", oldValue, value)
        }
    override var createTime: String
        get() {
            return trackReactiveGet(__v_raw, "createTime", __v_raw.createTime, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("createTime")) {
                return
            }
            val oldValue = __v_raw.createTime
            __v_raw.createTime = value
            triggerReactiveSet(__v_raw, "createTime", oldValue, value)
        }
}
open class ReviewPublishParams (
    @JsonNotNull
    open var productId: Number,
    @JsonNotNull
    open var orderId: Number,
    @JsonNotNull
    open var rating: Number,
    @JsonNotNull
    open var content: String,
    open var images: String? = null,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ReviewPublishParams", "api/review.uts", 22, 13)
    }
}
fun publishReview(data: ReviewPublishParams): UTSPromise<ResponseDataType> {
    val jsonData: UTSJSONObject = object : UTSJSONObject(UTSSourceMapPosition("jsonData", "api/review.uts", 33, 11)) {
        var productId = data.productId
        var orderId = data.orderId
        var rating = data.rating
        var content = data.content
        var images = if (data.images != null) {
            data.images
        } else {
            null
        }
    }
    return post("/reviews", jsonData)
}
fun getProductReviews(productId: Number, pageNum: Number = 1, pageSize: Number = 10, rating: Number? = null): UTSPromise<ResponseDataType> {
    var url = "/reviews/product/" + productId + "?pageNum=" + pageNum + "&pageSize=" + pageSize
    if (rating != null) {
        url += "&rating=" + rating
    }
    return get(url)
}
fun getMyReviews(pageNum: Number = 1, pageSize: Number = 10): UTSPromise<ResponseDataType> {
    return get("/reviews/mine?pageNum=" + pageNum + "&pageSize=" + pageSize)
}
fun recordBrowse(productId: Number): UTSPromise<ResponseDataType> {
    return post("/browse-history?productId=" + productId, null)
}
fun getBrowseHistory(pageNum: Number, pageSize: Number): UTSPromise<ResponseDataType> {
    return get("/browse-history?pageNum=" + pageNum + "&pageSize=" + pageSize)
}
fun deleteBrowseHistory(historyId: Number): UTSPromise<ResponseDataType> {
    return del("/browse-history/" + historyId)
}
fun clearBrowseHistory(): UTSPromise<ResponseDataType> {
    return del("/browse-history/clear")
}
open class ProductSku (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var productId: Number,
    @JsonNotNull
    open var skuName: String,
    @JsonNotNull
    open var skuCode: String,
    open var skuImage: String? = null,
    @JsonNotNull
    open var price: Number,
    @JsonNotNull
    open var stock: Number,
    @JsonNotNull
    open var salesVolume: Number,
    open var specInfo: String? = null,
    @JsonNotNull
    open var status: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ProductSku", "pages/product/detail.uvue", 236, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ProductSkuReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class ProductSkuReactiveObject : ProductSku, IUTSReactive<ProductSku> {
    override var __v_raw: ProductSku
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ProductSku, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, productId = __v_raw.productId, skuName = __v_raw.skuName, skuCode = __v_raw.skuCode, skuImage = __v_raw.skuImage, price = __v_raw.price, stock = __v_raw.stock, salesVolume = __v_raw.salesVolume, specInfo = __v_raw.specInfo, status = __v_raw.status) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ProductSkuReactiveObject {
        return ProductSkuReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return trackReactiveGet(__v_raw, "id", __v_raw.id, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            triggerReactiveSet(__v_raw, "id", oldValue, value)
        }
    override var productId: Number
        get() {
            return trackReactiveGet(__v_raw, "productId", __v_raw.productId, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("productId")) {
                return
            }
            val oldValue = __v_raw.productId
            __v_raw.productId = value
            triggerReactiveSet(__v_raw, "productId", oldValue, value)
        }
    override var skuName: String
        get() {
            return trackReactiveGet(__v_raw, "skuName", __v_raw.skuName, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("skuName")) {
                return
            }
            val oldValue = __v_raw.skuName
            __v_raw.skuName = value
            triggerReactiveSet(__v_raw, "skuName", oldValue, value)
        }
    override var skuCode: String
        get() {
            return trackReactiveGet(__v_raw, "skuCode", __v_raw.skuCode, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("skuCode")) {
                return
            }
            val oldValue = __v_raw.skuCode
            __v_raw.skuCode = value
            triggerReactiveSet(__v_raw, "skuCode", oldValue, value)
        }
    override var skuImage: String?
        get() {
            return trackReactiveGet(__v_raw, "skuImage", __v_raw.skuImage, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("skuImage")) {
                return
            }
            val oldValue = __v_raw.skuImage
            __v_raw.skuImage = value
            triggerReactiveSet(__v_raw, "skuImage", oldValue, value)
        }
    override var price: Number
        get() {
            return trackReactiveGet(__v_raw, "price", __v_raw.price, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("price")) {
                return
            }
            val oldValue = __v_raw.price
            __v_raw.price = value
            triggerReactiveSet(__v_raw, "price", oldValue, value)
        }
    override var stock: Number
        get() {
            return trackReactiveGet(__v_raw, "stock", __v_raw.stock, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("stock")) {
                return
            }
            val oldValue = __v_raw.stock
            __v_raw.stock = value
            triggerReactiveSet(__v_raw, "stock", oldValue, value)
        }
    override var salesVolume: Number
        get() {
            return trackReactiveGet(__v_raw, "salesVolume", __v_raw.salesVolume, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("salesVolume")) {
                return
            }
            val oldValue = __v_raw.salesVolume
            __v_raw.salesVolume = value
            triggerReactiveSet(__v_raw, "salesVolume", oldValue, value)
        }
    override var specInfo: String?
        get() {
            return trackReactiveGet(__v_raw, "specInfo", __v_raw.specInfo, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("specInfo")) {
                return
            }
            val oldValue = __v_raw.specInfo
            __v_raw.specInfo = value
            triggerReactiveSet(__v_raw, "specInfo", oldValue, value)
        }
    override var status: Number
        get() {
            return trackReactiveGet(__v_raw, "status", __v_raw.status, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("status")) {
                return
            }
            val oldValue = __v_raw.status
            __v_raw.status = value
            triggerReactiveSet(__v_raw, "status", oldValue, value)
        }
}
open class ProductSpec (
    @JsonNotNull
    open var id: String,
    @JsonNotNull
    open var name: String,
    @JsonNotNull
    open var options: UTSArray<String>,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ProductSpec", "pages/product/detail.uvue", 249, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ProductSpecReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class ProductSpecReactiveObject : ProductSpec, IUTSReactive<ProductSpec> {
    override var __v_raw: ProductSpec
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ProductSpec, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, name = __v_raw.name, options = __v_raw.options) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ProductSpecReactiveObject {
        return ProductSpecReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: String
        get() {
            return trackReactiveGet(__v_raw, "id", __v_raw.id, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            triggerReactiveSet(__v_raw, "id", oldValue, value)
        }
    override var name: String
        get() {
            return trackReactiveGet(__v_raw, "name", __v_raw.name, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("name")) {
                return
            }
            val oldValue = __v_raw.name
            __v_raw.name = value
            triggerReactiveSet(__v_raw, "name", oldValue, value)
        }
    override var options: UTSArray<String>
        get() {
            return trackReactiveGet(__v_raw, "options", __v_raw.options, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("options")) {
                return
            }
            val oldValue = __v_raw.options
            __v_raw.options = value
            triggerReactiveSet(__v_raw, "options", oldValue, value)
        }
}
open class Product (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var name: String,
    @JsonNotNull
    open var description: String,
    @JsonNotNull
    open var price: Number,
    open var originalPrice: Number? = null,
    @JsonNotNull
    open var image: String,
    @JsonNotNull
    open var rating: Number,
    @JsonNotNull
    open var sales: Number,
    @JsonNotNull
    open var stock: Number,
    @JsonNotNull
    open var reviewCount: Number,
    @JsonNotNull
    open var isNew: Boolean = false,
    @JsonNotNull
    open var discount: Boolean = false,
    @JsonNotNull
    open var isFresh: Boolean = false,
    open var specs: UTSArray<ProductSpec>? = null,
    open var detailImages: UTSArray<String>? = null,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("Product", "pages/product/detail.uvue", 255, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ProductReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class ProductReactiveObject : Product, IUTSReactive<Product> {
    override var __v_raw: Product
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: Product, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, name = __v_raw.name, description = __v_raw.description, price = __v_raw.price, originalPrice = __v_raw.originalPrice, image = __v_raw.image, rating = __v_raw.rating, sales = __v_raw.sales, stock = __v_raw.stock, reviewCount = __v_raw.reviewCount, isNew = __v_raw.isNew, discount = __v_raw.discount, isFresh = __v_raw.isFresh, specs = __v_raw.specs, detailImages = __v_raw.detailImages) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ProductReactiveObject {
        return ProductReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return trackReactiveGet(__v_raw, "id", __v_raw.id, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            triggerReactiveSet(__v_raw, "id", oldValue, value)
        }
    override var name: String
        get() {
            return trackReactiveGet(__v_raw, "name", __v_raw.name, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("name")) {
                return
            }
            val oldValue = __v_raw.name
            __v_raw.name = value
            triggerReactiveSet(__v_raw, "name", oldValue, value)
        }
    override var description: String
        get() {
            return trackReactiveGet(__v_raw, "description", __v_raw.description, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("description")) {
                return
            }
            val oldValue = __v_raw.description
            __v_raw.description = value
            triggerReactiveSet(__v_raw, "description", oldValue, value)
        }
    override var price: Number
        get() {
            return trackReactiveGet(__v_raw, "price", __v_raw.price, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("price")) {
                return
            }
            val oldValue = __v_raw.price
            __v_raw.price = value
            triggerReactiveSet(__v_raw, "price", oldValue, value)
        }
    override var originalPrice: Number?
        get() {
            return trackReactiveGet(__v_raw, "originalPrice", __v_raw.originalPrice, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("originalPrice")) {
                return
            }
            val oldValue = __v_raw.originalPrice
            __v_raw.originalPrice = value
            triggerReactiveSet(__v_raw, "originalPrice", oldValue, value)
        }
    override var image: String
        get() {
            return trackReactiveGet(__v_raw, "image", __v_raw.image, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("image")) {
                return
            }
            val oldValue = __v_raw.image
            __v_raw.image = value
            triggerReactiveSet(__v_raw, "image", oldValue, value)
        }
    override var rating: Number
        get() {
            return trackReactiveGet(__v_raw, "rating", __v_raw.rating, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("rating")) {
                return
            }
            val oldValue = __v_raw.rating
            __v_raw.rating = value
            triggerReactiveSet(__v_raw, "rating", oldValue, value)
        }
    override var sales: Number
        get() {
            return trackReactiveGet(__v_raw, "sales", __v_raw.sales, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("sales")) {
                return
            }
            val oldValue = __v_raw.sales
            __v_raw.sales = value
            triggerReactiveSet(__v_raw, "sales", oldValue, value)
        }
    override var stock: Number
        get() {
            return trackReactiveGet(__v_raw, "stock", __v_raw.stock, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("stock")) {
                return
            }
            val oldValue = __v_raw.stock
            __v_raw.stock = value
            triggerReactiveSet(__v_raw, "stock", oldValue, value)
        }
    override var reviewCount: Number
        get() {
            return trackReactiveGet(__v_raw, "reviewCount", __v_raw.reviewCount, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("reviewCount")) {
                return
            }
            val oldValue = __v_raw.reviewCount
            __v_raw.reviewCount = value
            triggerReactiveSet(__v_raw, "reviewCount", oldValue, value)
        }
    override var isNew: Boolean
        get() {
            return trackReactiveGet(__v_raw, "isNew", __v_raw.isNew, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("isNew")) {
                return
            }
            val oldValue = __v_raw.isNew
            __v_raw.isNew = value
            triggerReactiveSet(__v_raw, "isNew", oldValue, value)
        }
    override var discount: Boolean
        get() {
            return trackReactiveGet(__v_raw, "discount", __v_raw.discount, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("discount")) {
                return
            }
            val oldValue = __v_raw.discount
            __v_raw.discount = value
            triggerReactiveSet(__v_raw, "discount", oldValue, value)
        }
    override var isFresh: Boolean
        get() {
            return trackReactiveGet(__v_raw, "isFresh", __v_raw.isFresh, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("isFresh")) {
                return
            }
            val oldValue = __v_raw.isFresh
            __v_raw.isFresh = value
            triggerReactiveSet(__v_raw, "isFresh", oldValue, value)
        }
    override var specs: UTSArray<ProductSpec>?
        get() {
            return trackReactiveGet(__v_raw, "specs", __v_raw.specs, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("specs")) {
                return
            }
            val oldValue = __v_raw.specs
            __v_raw.specs = value
            triggerReactiveSet(__v_raw, "specs", oldValue, value)
        }
    override var detailImages: UTSArray<String>?
        get() {
            return trackReactiveGet(__v_raw, "detailImages", __v_raw.detailImages, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("detailImages")) {
                return
            }
            val oldValue = __v_raw.detailImages
            __v_raw.detailImages = value
            triggerReactiveSet(__v_raw, "detailImages", oldValue, value)
        }
}
val GenPagesProductDetailClass = CreateVueComponent(GenPagesProductDetail::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = GenPagesProductDetail.name, inheritAttrs = GenPagesProductDetail.inheritAttrs, inject = GenPagesProductDetail.inject, props = GenPagesProductDetail.props, propsNeedCastKeys = GenPagesProductDetail.propsNeedCastKeys, emits = GenPagesProductDetail.emits, components = GenPagesProductDetail.components, styles = GenPagesProductDetail.styles)
}
, fun(instance, renderer): GenPagesProductDetail {
    return GenPagesProductDetail(instance, renderer)
}
)
val GenPagesProductReviewsClass = CreateVueComponent(GenPagesProductReviews::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesProductReviews.inheritAttrs, inject = GenPagesProductReviews.inject, props = GenPagesProductReviews.props, propsNeedCastKeys = GenPagesProductReviews.propsNeedCastKeys, emits = GenPagesProductReviews.emits, components = GenPagesProductReviews.components, styles = GenPagesProductReviews.styles)
}
, fun(instance, renderer): GenPagesProductReviews {
    return GenPagesProductReviews(instance, renderer)
}
)
val GenPagesCartIndexClass = CreateVueComponent(GenPagesCartIndex::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesCartIndex.inheritAttrs, inject = GenPagesCartIndex.inject, props = GenPagesCartIndex.props, propsNeedCastKeys = GenPagesCartIndex.propsNeedCastKeys, emits = GenPagesCartIndex.emits, components = GenPagesCartIndex.components, styles = GenPagesCartIndex.styles)
}
, fun(instance, renderer): GenPagesCartIndex {
    return GenPagesCartIndex(instance, renderer)
}
)
val GenPagesOrderListClass = CreateVueComponent(GenPagesOrderList::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesOrderList.inheritAttrs, inject = GenPagesOrderList.inject, props = GenPagesOrderList.props, propsNeedCastKeys = GenPagesOrderList.propsNeedCastKeys, emits = GenPagesOrderList.emits, components = GenPagesOrderList.components, styles = GenPagesOrderList.styles)
}
, fun(instance, renderer): GenPagesOrderList {
    return GenPagesOrderList(instance, renderer)
}
)
val GenPagesOrderDetailClass = CreateVueComponent(GenPagesOrderDetail::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesOrderDetail.inheritAttrs, inject = GenPagesOrderDetail.inject, props = GenPagesOrderDetail.props, propsNeedCastKeys = GenPagesOrderDetail.propsNeedCastKeys, emits = GenPagesOrderDetail.emits, components = GenPagesOrderDetail.components, styles = GenPagesOrderDetail.styles)
}
, fun(instance, renderer): GenPagesOrderDetail {
    return GenPagesOrderDetail(instance, renderer)
}
)
fun addAddress(data: Any): UTSPromise<ResponseDataType> {
    return post("/users/addresses", data)
}
fun updateAddress(addressId: Number, data: Any): UTSPromise<ResponseDataType> {
    return put("/users/addresses/" + addressId, data)
}
fun deleteAddress(addressId: Number): UTSPromise<ResponseDataType> {
    return del("/users/addresses/" + addressId)
}
fun getAddressList(): UTSPromise<ResponseDataType> {
    return get("/users/addresses")
}
fun getAddressDetail(addressId: Number): UTSPromise<ResponseDataType> {
    return get("/users/addresses/" + addressId)
}
fun setDefaultAddress(addressId: Number): UTSPromise<ResponseDataType> {
    return put("/users/addresses/" + addressId + "/default", null)
}
fun getDefaultAddress(): UTSPromise<ResponseDataType> {
    return get("/users/addresses/default")
}
open class AddressType (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var receiverName: String,
    @JsonNotNull
    open var receiverPhone: String,
    @JsonNotNull
    open var province: String,
    @JsonNotNull
    open var city: String,
    @JsonNotNull
    open var district: String,
    @JsonNotNull
    open var detailAddress: String,
    @JsonNotNull
    open var fullAddress: String,
    @JsonNotNull
    open var isDefault: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("AddressType", "pages/order/checkout.uvue", 101, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return AddressTypeReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class AddressTypeReactiveObject : AddressType, IUTSReactive<AddressType> {
    override var __v_raw: AddressType
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: AddressType, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, receiverName = __v_raw.receiverName, receiverPhone = __v_raw.receiverPhone, province = __v_raw.province, city = __v_raw.city, district = __v_raw.district, detailAddress = __v_raw.detailAddress, fullAddress = __v_raw.fullAddress, isDefault = __v_raw.isDefault) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): AddressTypeReactiveObject {
        return AddressTypeReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return trackReactiveGet(__v_raw, "id", __v_raw.id, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            triggerReactiveSet(__v_raw, "id", oldValue, value)
        }
    override var receiverName: String
        get() {
            return trackReactiveGet(__v_raw, "receiverName", __v_raw.receiverName, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("receiverName")) {
                return
            }
            val oldValue = __v_raw.receiverName
            __v_raw.receiverName = value
            triggerReactiveSet(__v_raw, "receiverName", oldValue, value)
        }
    override var receiverPhone: String
        get() {
            return trackReactiveGet(__v_raw, "receiverPhone", __v_raw.receiverPhone, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("receiverPhone")) {
                return
            }
            val oldValue = __v_raw.receiverPhone
            __v_raw.receiverPhone = value
            triggerReactiveSet(__v_raw, "receiverPhone", oldValue, value)
        }
    override var province: String
        get() {
            return trackReactiveGet(__v_raw, "province", __v_raw.province, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("province")) {
                return
            }
            val oldValue = __v_raw.province
            __v_raw.province = value
            triggerReactiveSet(__v_raw, "province", oldValue, value)
        }
    override var city: String
        get() {
            return trackReactiveGet(__v_raw, "city", __v_raw.city, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("city")) {
                return
            }
            val oldValue = __v_raw.city
            __v_raw.city = value
            triggerReactiveSet(__v_raw, "city", oldValue, value)
        }
    override var district: String
        get() {
            return trackReactiveGet(__v_raw, "district", __v_raw.district, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("district")) {
                return
            }
            val oldValue = __v_raw.district
            __v_raw.district = value
            triggerReactiveSet(__v_raw, "district", oldValue, value)
        }
    override var detailAddress: String
        get() {
            return trackReactiveGet(__v_raw, "detailAddress", __v_raw.detailAddress, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("detailAddress")) {
                return
            }
            val oldValue = __v_raw.detailAddress
            __v_raw.detailAddress = value
            triggerReactiveSet(__v_raw, "detailAddress", oldValue, value)
        }
    override var fullAddress: String
        get() {
            return trackReactiveGet(__v_raw, "fullAddress", __v_raw.fullAddress, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("fullAddress")) {
                return
            }
            val oldValue = __v_raw.fullAddress
            __v_raw.fullAddress = value
            triggerReactiveSet(__v_raw, "fullAddress", oldValue, value)
        }
    override var isDefault: Number
        get() {
            return trackReactiveGet(__v_raw, "isDefault", __v_raw.isDefault, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("isDefault")) {
                return
            }
            val oldValue = __v_raw.isDefault
            __v_raw.isDefault = value
            triggerReactiveSet(__v_raw, "isDefault", oldValue, value)
        }
}
open class OrderGoodsType (
    @JsonNotNull
    open var cartId: Number,
    @JsonNotNull
    open var productId: Number,
    @JsonNotNull
    open var skuId: Number,
    @JsonNotNull
    open var productName: String,
    @JsonNotNull
    open var productImage: String,
    @JsonNotNull
    open var skuName: String,
    @JsonNotNull
    open var price: Number,
    @JsonNotNull
    open var quantity: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("OrderGoodsType", "pages/order/checkout.uvue", 113, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return OrderGoodsTypeReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class OrderGoodsTypeReactiveObject : OrderGoodsType, IUTSReactive<OrderGoodsType> {
    override var __v_raw: OrderGoodsType
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: OrderGoodsType, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(cartId = __v_raw.cartId, productId = __v_raw.productId, skuId = __v_raw.skuId, productName = __v_raw.productName, productImage = __v_raw.productImage, skuName = __v_raw.skuName, price = __v_raw.price, quantity = __v_raw.quantity) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): OrderGoodsTypeReactiveObject {
        return OrderGoodsTypeReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var cartId: Number
        get() {
            return trackReactiveGet(__v_raw, "cartId", __v_raw.cartId, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("cartId")) {
                return
            }
            val oldValue = __v_raw.cartId
            __v_raw.cartId = value
            triggerReactiveSet(__v_raw, "cartId", oldValue, value)
        }
    override var productId: Number
        get() {
            return trackReactiveGet(__v_raw, "productId", __v_raw.productId, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("productId")) {
                return
            }
            val oldValue = __v_raw.productId
            __v_raw.productId = value
            triggerReactiveSet(__v_raw, "productId", oldValue, value)
        }
    override var skuId: Number
        get() {
            return trackReactiveGet(__v_raw, "skuId", __v_raw.skuId, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("skuId")) {
                return
            }
            val oldValue = __v_raw.skuId
            __v_raw.skuId = value
            triggerReactiveSet(__v_raw, "skuId", oldValue, value)
        }
    override var productName: String
        get() {
            return trackReactiveGet(__v_raw, "productName", __v_raw.productName, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("productName")) {
                return
            }
            val oldValue = __v_raw.productName
            __v_raw.productName = value
            triggerReactiveSet(__v_raw, "productName", oldValue, value)
        }
    override var productImage: String
        get() {
            return trackReactiveGet(__v_raw, "productImage", __v_raw.productImage, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("productImage")) {
                return
            }
            val oldValue = __v_raw.productImage
            __v_raw.productImage = value
            triggerReactiveSet(__v_raw, "productImage", oldValue, value)
        }
    override var skuName: String
        get() {
            return trackReactiveGet(__v_raw, "skuName", __v_raw.skuName, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("skuName")) {
                return
            }
            val oldValue = __v_raw.skuName
            __v_raw.skuName = value
            triggerReactiveSet(__v_raw, "skuName", oldValue, value)
        }
    override var price: Number
        get() {
            return trackReactiveGet(__v_raw, "price", __v_raw.price, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("price")) {
                return
            }
            val oldValue = __v_raw.price
            __v_raw.price = value
            triggerReactiveSet(__v_raw, "price", oldValue, value)
        }
    override var quantity: Number
        get() {
            return trackReactiveGet(__v_raw, "quantity", __v_raw.quantity, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("quantity")) {
                return
            }
            val oldValue = __v_raw.quantity
            __v_raw.quantity = value
            triggerReactiveSet(__v_raw, "quantity", oldValue, value)
        }
}
val GenPagesOrderCheckoutClass = CreateVueComponent(GenPagesOrderCheckout::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesOrderCheckout.inheritAttrs, inject = GenPagesOrderCheckout.inject, props = GenPagesOrderCheckout.props, propsNeedCastKeys = GenPagesOrderCheckout.propsNeedCastKeys, emits = GenPagesOrderCheckout.emits, components = GenPagesOrderCheckout.components, styles = GenPagesOrderCheckout.styles)
}
, fun(instance, renderer): GenPagesOrderCheckout {
    return GenPagesOrderCheckout(instance, renderer)
}
)
val GenPagesOrderReviewClass = CreateVueComponent(GenPagesOrderReview::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesOrderReview.inheritAttrs, inject = GenPagesOrderReview.inject, props = GenPagesOrderReview.props, propsNeedCastKeys = GenPagesOrderReview.propsNeedCastKeys, emits = GenPagesOrderReview.emits, components = GenPagesOrderReview.components, styles = GenPagesOrderReview.styles)
}
, fun(instance, renderer): GenPagesOrderReview {
    return GenPagesOrderReview(instance, renderer)
}
)
open class WalletVO (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var userId: Number,
    @JsonNotNull
    open var balance: Number,
    @JsonNotNull
    open var frozenAmount: Number,
    @JsonNotNull
    open var totalRecharge: Number,
    @JsonNotNull
    open var totalConsume: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("WalletVO", "api/wallet.uts", 6, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return WalletVOReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class WalletVOReactiveObject : WalletVO, IUTSReactive<WalletVO> {
    override var __v_raw: WalletVO
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: WalletVO, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, userId = __v_raw.userId, balance = __v_raw.balance, frozenAmount = __v_raw.frozenAmount, totalRecharge = __v_raw.totalRecharge, totalConsume = __v_raw.totalConsume) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): WalletVOReactiveObject {
        return WalletVOReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return trackReactiveGet(__v_raw, "id", __v_raw.id, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            triggerReactiveSet(__v_raw, "id", oldValue, value)
        }
    override var userId: Number
        get() {
            return trackReactiveGet(__v_raw, "userId", __v_raw.userId, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("userId")) {
                return
            }
            val oldValue = __v_raw.userId
            __v_raw.userId = value
            triggerReactiveSet(__v_raw, "userId", oldValue, value)
        }
    override var balance: Number
        get() {
            return trackReactiveGet(__v_raw, "balance", __v_raw.balance, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("balance")) {
                return
            }
            val oldValue = __v_raw.balance
            __v_raw.balance = value
            triggerReactiveSet(__v_raw, "balance", oldValue, value)
        }
    override var frozenAmount: Number
        get() {
            return trackReactiveGet(__v_raw, "frozenAmount", __v_raw.frozenAmount, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("frozenAmount")) {
                return
            }
            val oldValue = __v_raw.frozenAmount
            __v_raw.frozenAmount = value
            triggerReactiveSet(__v_raw, "frozenAmount", oldValue, value)
        }
    override var totalRecharge: Number
        get() {
            return trackReactiveGet(__v_raw, "totalRecharge", __v_raw.totalRecharge, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("totalRecharge")) {
                return
            }
            val oldValue = __v_raw.totalRecharge
            __v_raw.totalRecharge = value
            triggerReactiveSet(__v_raw, "totalRecharge", oldValue, value)
        }
    override var totalConsume: Number
        get() {
            return trackReactiveGet(__v_raw, "totalConsume", __v_raw.totalConsume, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("totalConsume")) {
                return
            }
            val oldValue = __v_raw.totalConsume
            __v_raw.totalConsume = value
            triggerReactiveSet(__v_raw, "totalConsume", oldValue, value)
        }
}
open class TransactionVO (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var transactionNo: String,
    @JsonNotNull
    open var type: String,
    @JsonNotNull
    open var typeDesc: String,
    @JsonNotNull
    open var amount: Number,
    @JsonNotNull
    open var balanceAfter: Number,
    open var relatedOrderNo: String? = null,
    @JsonNotNull
    open var description: String,
    @JsonNotNull
    open var createTime: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("TransactionVO", "api/wallet.uts", 15, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return TransactionVOReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class TransactionVOReactiveObject : TransactionVO, IUTSReactive<TransactionVO> {
    override var __v_raw: TransactionVO
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: TransactionVO, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, transactionNo = __v_raw.transactionNo, type = __v_raw.type, typeDesc = __v_raw.typeDesc, amount = __v_raw.amount, balanceAfter = __v_raw.balanceAfter, relatedOrderNo = __v_raw.relatedOrderNo, description = __v_raw.description, createTime = __v_raw.createTime) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): TransactionVOReactiveObject {
        return TransactionVOReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return trackReactiveGet(__v_raw, "id", __v_raw.id, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            triggerReactiveSet(__v_raw, "id", oldValue, value)
        }
    override var transactionNo: String
        get() {
            return trackReactiveGet(__v_raw, "transactionNo", __v_raw.transactionNo, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("transactionNo")) {
                return
            }
            val oldValue = __v_raw.transactionNo
            __v_raw.transactionNo = value
            triggerReactiveSet(__v_raw, "transactionNo", oldValue, value)
        }
    override var type: String
        get() {
            return trackReactiveGet(__v_raw, "type", __v_raw.type, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("type")) {
                return
            }
            val oldValue = __v_raw.type
            __v_raw.type = value
            triggerReactiveSet(__v_raw, "type", oldValue, value)
        }
    override var typeDesc: String
        get() {
            return trackReactiveGet(__v_raw, "typeDesc", __v_raw.typeDesc, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("typeDesc")) {
                return
            }
            val oldValue = __v_raw.typeDesc
            __v_raw.typeDesc = value
            triggerReactiveSet(__v_raw, "typeDesc", oldValue, value)
        }
    override var amount: Number
        get() {
            return trackReactiveGet(__v_raw, "amount", __v_raw.amount, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("amount")) {
                return
            }
            val oldValue = __v_raw.amount
            __v_raw.amount = value
            triggerReactiveSet(__v_raw, "amount", oldValue, value)
        }
    override var balanceAfter: Number
        get() {
            return trackReactiveGet(__v_raw, "balanceAfter", __v_raw.balanceAfter, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("balanceAfter")) {
                return
            }
            val oldValue = __v_raw.balanceAfter
            __v_raw.balanceAfter = value
            triggerReactiveSet(__v_raw, "balanceAfter", oldValue, value)
        }
    override var relatedOrderNo: String?
        get() {
            return trackReactiveGet(__v_raw, "relatedOrderNo", __v_raw.relatedOrderNo, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("relatedOrderNo")) {
                return
            }
            val oldValue = __v_raw.relatedOrderNo
            __v_raw.relatedOrderNo = value
            triggerReactiveSet(__v_raw, "relatedOrderNo", oldValue, value)
        }
    override var description: String
        get() {
            return trackReactiveGet(__v_raw, "description", __v_raw.description, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("description")) {
                return
            }
            val oldValue = __v_raw.description
            __v_raw.description = value
            triggerReactiveSet(__v_raw, "description", oldValue, value)
        }
    override var createTime: String
        get() {
            return trackReactiveGet(__v_raw, "createTime", __v_raw.createTime, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("createTime")) {
                return
            }
            val oldValue = __v_raw.createTime
            __v_raw.createTime = value
            triggerReactiveSet(__v_raw, "createTime", oldValue, value)
        }
}
open class TransactionQueryParams (
    open var type: String? = null,
    open var startDate: String? = null,
    open var endDate: String? = null,
    @JsonNotNull
    open var pageNum: Number,
    @JsonNotNull
    open var pageSize: Number,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("TransactionQueryParams", "api/wallet.uts", 27, 13)
    }
}
fun getWallet(): UTSPromise<ResponseDataType> {
    return get("/wallet")
}
fun rechargeWallet(amount: Number): UTSPromise<ResponseDataType> {
    return post("/wallet/recharge", UTSJSONObject(Map<String, Any?>(utsArrayOf(
        utsArrayOf(
            "amount",
            amount
        )
    ))))
}
fun getTransactions(params: TransactionQueryParams): UTSPromise<ResponseDataType> {
    var url = "/wallet/transactions?pageNum=" + params.pageNum + "&pageSize=" + params.pageSize
    if (params.type != null && params.type != "") {
        url += "&type=" + params.type
    }
    if (params.startDate != null && params.startDate != "") {
        url += "&startDate=" + params.startDate
    }
    if (params.endDate != null && params.endDate != "") {
        url += "&endDate=" + params.endDate
    }
    return get(url)
}
val GenPagesOrderPayClass = CreateVueComponent(GenPagesOrderPay::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesOrderPay.inheritAttrs, inject = GenPagesOrderPay.inject, props = GenPagesOrderPay.props, propsNeedCastKeys = GenPagesOrderPay.propsNeedCastKeys, emits = GenPagesOrderPay.emits, components = GenPagesOrderPay.components, styles = GenPagesOrderPay.styles)
}
, fun(instance, renderer): GenPagesOrderPay {
    return GenPagesOrderPay(instance, renderer)
}
)
open class CollectionItem (
    @JsonNotNull
    open var collectionId: Number,
    @JsonNotNull
    open var productId: Number,
    @JsonNotNull
    open var productName: String,
    @JsonNotNull
    open var mainImage: String,
    @JsonNotNull
    open var currentPrice: Number,
    @JsonNotNull
    open var originalPrice: Number,
    @JsonNotNull
    open var stock: Number,
    @JsonNotNull
    open var salesVolume: Number,
    @JsonNotNull
    open var status: Number,
    @JsonNotNull
    open var collectTime: String,
    @JsonNotNull
    open var inStock: Boolean = false,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("CollectionItem", "pages/product/favorites.uvue", 37, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return CollectionItemReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class CollectionItemReactiveObject : CollectionItem, IUTSReactive<CollectionItem> {
    override var __v_raw: CollectionItem
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: CollectionItem, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(collectionId = __v_raw.collectionId, productId = __v_raw.productId, productName = __v_raw.productName, mainImage = __v_raw.mainImage, currentPrice = __v_raw.currentPrice, originalPrice = __v_raw.originalPrice, stock = __v_raw.stock, salesVolume = __v_raw.salesVolume, status = __v_raw.status, collectTime = __v_raw.collectTime, inStock = __v_raw.inStock) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): CollectionItemReactiveObject {
        return CollectionItemReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var collectionId: Number
        get() {
            return trackReactiveGet(__v_raw, "collectionId", __v_raw.collectionId, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("collectionId")) {
                return
            }
            val oldValue = __v_raw.collectionId
            __v_raw.collectionId = value
            triggerReactiveSet(__v_raw, "collectionId", oldValue, value)
        }
    override var productId: Number
        get() {
            return trackReactiveGet(__v_raw, "productId", __v_raw.productId, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("productId")) {
                return
            }
            val oldValue = __v_raw.productId
            __v_raw.productId = value
            triggerReactiveSet(__v_raw, "productId", oldValue, value)
        }
    override var productName: String
        get() {
            return trackReactiveGet(__v_raw, "productName", __v_raw.productName, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("productName")) {
                return
            }
            val oldValue = __v_raw.productName
            __v_raw.productName = value
            triggerReactiveSet(__v_raw, "productName", oldValue, value)
        }
    override var mainImage: String
        get() {
            return trackReactiveGet(__v_raw, "mainImage", __v_raw.mainImage, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("mainImage")) {
                return
            }
            val oldValue = __v_raw.mainImage
            __v_raw.mainImage = value
            triggerReactiveSet(__v_raw, "mainImage", oldValue, value)
        }
    override var currentPrice: Number
        get() {
            return trackReactiveGet(__v_raw, "currentPrice", __v_raw.currentPrice, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("currentPrice")) {
                return
            }
            val oldValue = __v_raw.currentPrice
            __v_raw.currentPrice = value
            triggerReactiveSet(__v_raw, "currentPrice", oldValue, value)
        }
    override var originalPrice: Number
        get() {
            return trackReactiveGet(__v_raw, "originalPrice", __v_raw.originalPrice, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("originalPrice")) {
                return
            }
            val oldValue = __v_raw.originalPrice
            __v_raw.originalPrice = value
            triggerReactiveSet(__v_raw, "originalPrice", oldValue, value)
        }
    override var stock: Number
        get() {
            return trackReactiveGet(__v_raw, "stock", __v_raw.stock, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("stock")) {
                return
            }
            val oldValue = __v_raw.stock
            __v_raw.stock = value
            triggerReactiveSet(__v_raw, "stock", oldValue, value)
        }
    override var salesVolume: Number
        get() {
            return trackReactiveGet(__v_raw, "salesVolume", __v_raw.salesVolume, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("salesVolume")) {
                return
            }
            val oldValue = __v_raw.salesVolume
            __v_raw.salesVolume = value
            triggerReactiveSet(__v_raw, "salesVolume", oldValue, value)
        }
    override var status: Number
        get() {
            return trackReactiveGet(__v_raw, "status", __v_raw.status, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("status")) {
                return
            }
            val oldValue = __v_raw.status
            __v_raw.status = value
            triggerReactiveSet(__v_raw, "status", oldValue, value)
        }
    override var collectTime: String
        get() {
            return trackReactiveGet(__v_raw, "collectTime", __v_raw.collectTime, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("collectTime")) {
                return
            }
            val oldValue = __v_raw.collectTime
            __v_raw.collectTime = value
            triggerReactiveSet(__v_raw, "collectTime", oldValue, value)
        }
    override var inStock: Boolean
        get() {
            return trackReactiveGet(__v_raw, "inStock", __v_raw.inStock, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("inStock")) {
                return
            }
            val oldValue = __v_raw.inStock
            __v_raw.inStock = value
            triggerReactiveSet(__v_raw, "inStock", oldValue, value)
        }
}
val GenPagesProductFavoritesClass = CreateVueComponent(GenPagesProductFavorites::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesProductFavorites.inheritAttrs, inject = GenPagesProductFavorites.inject, props = GenPagesProductFavorites.props, propsNeedCastKeys = GenPagesProductFavorites.propsNeedCastKeys, emits = GenPagesProductFavorites.emits, components = GenPagesProductFavorites.components, styles = GenPagesProductFavorites.styles)
}
, fun(instance, renderer): GenPagesProductFavorites {
    return GenPagesProductFavorites(instance, renderer)
}
)
open class HistoryItem (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var productId: Number,
    @JsonNotNull
    open var productName: String,
    @JsonNotNull
    open var mainImage: String,
    @JsonNotNull
    open var currentPrice: Number,
    @JsonNotNull
    open var originalPrice: Number,
    @JsonNotNull
    open var stock: Number,
    @JsonNotNull
    open var salesVolume: Number,
    @JsonNotNull
    open var status: Number,
    @JsonNotNull
    open var browseTime: String,
    @JsonNotNull
    open var inStock: Boolean = false,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("HistoryItem", "pages/product/history.uvue", 65, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return HistoryItemReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class HistoryItemReactiveObject : HistoryItem, IUTSReactive<HistoryItem> {
    override var __v_raw: HistoryItem
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: HistoryItem, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, productId = __v_raw.productId, productName = __v_raw.productName, mainImage = __v_raw.mainImage, currentPrice = __v_raw.currentPrice, originalPrice = __v_raw.originalPrice, stock = __v_raw.stock, salesVolume = __v_raw.salesVolume, status = __v_raw.status, browseTime = __v_raw.browseTime, inStock = __v_raw.inStock) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): HistoryItemReactiveObject {
        return HistoryItemReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return trackReactiveGet(__v_raw, "id", __v_raw.id, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            triggerReactiveSet(__v_raw, "id", oldValue, value)
        }
    override var productId: Number
        get() {
            return trackReactiveGet(__v_raw, "productId", __v_raw.productId, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("productId")) {
                return
            }
            val oldValue = __v_raw.productId
            __v_raw.productId = value
            triggerReactiveSet(__v_raw, "productId", oldValue, value)
        }
    override var productName: String
        get() {
            return trackReactiveGet(__v_raw, "productName", __v_raw.productName, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("productName")) {
                return
            }
            val oldValue = __v_raw.productName
            __v_raw.productName = value
            triggerReactiveSet(__v_raw, "productName", oldValue, value)
        }
    override var mainImage: String
        get() {
            return trackReactiveGet(__v_raw, "mainImage", __v_raw.mainImage, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("mainImage")) {
                return
            }
            val oldValue = __v_raw.mainImage
            __v_raw.mainImage = value
            triggerReactiveSet(__v_raw, "mainImage", oldValue, value)
        }
    override var currentPrice: Number
        get() {
            return trackReactiveGet(__v_raw, "currentPrice", __v_raw.currentPrice, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("currentPrice")) {
                return
            }
            val oldValue = __v_raw.currentPrice
            __v_raw.currentPrice = value
            triggerReactiveSet(__v_raw, "currentPrice", oldValue, value)
        }
    override var originalPrice: Number
        get() {
            return trackReactiveGet(__v_raw, "originalPrice", __v_raw.originalPrice, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("originalPrice")) {
                return
            }
            val oldValue = __v_raw.originalPrice
            __v_raw.originalPrice = value
            triggerReactiveSet(__v_raw, "originalPrice", oldValue, value)
        }
    override var stock: Number
        get() {
            return trackReactiveGet(__v_raw, "stock", __v_raw.stock, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("stock")) {
                return
            }
            val oldValue = __v_raw.stock
            __v_raw.stock = value
            triggerReactiveSet(__v_raw, "stock", oldValue, value)
        }
    override var salesVolume: Number
        get() {
            return trackReactiveGet(__v_raw, "salesVolume", __v_raw.salesVolume, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("salesVolume")) {
                return
            }
            val oldValue = __v_raw.salesVolume
            __v_raw.salesVolume = value
            triggerReactiveSet(__v_raw, "salesVolume", oldValue, value)
        }
    override var status: Number
        get() {
            return trackReactiveGet(__v_raw, "status", __v_raw.status, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("status")) {
                return
            }
            val oldValue = __v_raw.status
            __v_raw.status = value
            triggerReactiveSet(__v_raw, "status", oldValue, value)
        }
    override var browseTime: String
        get() {
            return trackReactiveGet(__v_raw, "browseTime", __v_raw.browseTime, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("browseTime")) {
                return
            }
            val oldValue = __v_raw.browseTime
            __v_raw.browseTime = value
            triggerReactiveSet(__v_raw, "browseTime", oldValue, value)
        }
    override var inStock: Boolean
        get() {
            return trackReactiveGet(__v_raw, "inStock", __v_raw.inStock, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("inStock")) {
                return
            }
            val oldValue = __v_raw.inStock
            __v_raw.inStock = value
            triggerReactiveSet(__v_raw, "inStock", oldValue, value)
        }
}
val GenPagesProductHistoryClass = CreateVueComponent(GenPagesProductHistory::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesProductHistory.inheritAttrs, inject = GenPagesProductHistory.inject, props = GenPagesProductHistory.props, propsNeedCastKeys = GenPagesProductHistory.propsNeedCastKeys, emits = GenPagesProductHistory.emits, components = GenPagesProductHistory.components, styles = GenPagesProductHistory.styles)
}
, fun(instance, renderer): GenPagesProductHistory {
    return GenPagesProductHistory(instance, renderer)
}
)
open class ProductQueryParams (
    @JsonNotNull
    open var pageNum: Number,
    @JsonNotNull
    open var pageSize: Number,
    open var status: Number? = null,
    open var keyword: String? = null,
    open var categoryId: Number? = null,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ProductQueryParams", "api/seller.uts", 3, 13)
    }
}
open class ProductImage (
    @JsonNotNull
    open var imageUrl: String,
    @JsonNotNull
    open var sortOrder: Number,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ProductImage", "api/seller.uts", 10, 13)
    }
}
open class SkuItem (
    @JsonNotNull
    open var skuName: String,
    @JsonNotNull
    open var price: Number,
    @JsonNotNull
    open var stock: Number,
    open var specInfo: String? = null,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("SkuItem", "api/seller.uts", 37, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return SkuItemReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class SkuItemReactiveObject : SkuItem, IUTSReactive<SkuItem> {
    override var __v_raw: SkuItem
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: SkuItem, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(skuName = __v_raw.skuName, price = __v_raw.price, stock = __v_raw.stock, specInfo = __v_raw.specInfo) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): SkuItemReactiveObject {
        return SkuItemReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var skuName: String
        get() {
            return trackReactiveGet(__v_raw, "skuName", __v_raw.skuName, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("skuName")) {
                return
            }
            val oldValue = __v_raw.skuName
            __v_raw.skuName = value
            triggerReactiveSet(__v_raw, "skuName", oldValue, value)
        }
    override var price: Number
        get() {
            return trackReactiveGet(__v_raw, "price", __v_raw.price, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("price")) {
                return
            }
            val oldValue = __v_raw.price
            __v_raw.price = value
            triggerReactiveSet(__v_raw, "price", oldValue, value)
        }
    override var stock: Number
        get() {
            return trackReactiveGet(__v_raw, "stock", __v_raw.stock, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("stock")) {
                return
            }
            val oldValue = __v_raw.stock
            __v_raw.stock = value
            triggerReactiveSet(__v_raw, "stock", oldValue, value)
        }
    override var specInfo: String?
        get() {
            return trackReactiveGet(__v_raw, "specInfo", __v_raw.specInfo, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("specInfo")) {
                return
            }
            val oldValue = __v_raw.specInfo
            __v_raw.specInfo = value
            triggerReactiveSet(__v_raw, "specInfo", oldValue, value)
        }
}
open class ProductFormData (
    @JsonNotNull
    open var categoryId: Number,
    @JsonNotNull
    open var productName: String,
    @JsonNotNull
    open var mainImage: String,
    open var description: String? = null,
    open var detail: String? = null,
    open var originalPrice: Number? = null,
    @JsonNotNull
    open var currentPrice: Number,
    open var isRecommended: Number? = null,
    open var isNew: Number? = null,
    open var isHot: Number? = null,
    open var images: UTSArray<ProductImage>? = null,
    @JsonNotNull
    open var skuList: UTSArray<SkuItem>,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ProductFormData", "api/seller.uts", 43, 13)
    }
}
open class InventoryQueryParams (
    @JsonNotNull
    open var pageNum: Number,
    @JsonNotNull
    open var pageSize: Number,
    open var productId: Number? = null,
    open var lowStock: Boolean? = null,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("InventoryQueryParams", "api/seller.uts", 57, 13)
    }
}
open class AdjustData (
    @JsonNotNull
    open var adjustType: String,
    @JsonNotNull
    open var quantity: Number,
    open var remark: String? = null,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("AdjustData", "api/seller.uts", 63, 13)
    }
}
open class StatisticsParams (
    open var startDate: String? = null,
    open var endDate: String? = null,
    open var timeUnit: String? = null,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("StatisticsParams", "api/seller.uts", 68, 13)
    }
}
open class RankingParams (
    @JsonNotNull
    open var limit: Number,
    open var orderBy: String? = null,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("RankingParams", "api/seller.uts", 73, 13)
    }
}
open class SellerTransactionParams (
    @JsonNotNull
    open var pageNum: Number,
    @JsonNotNull
    open var pageSize: Number,
    open var type: String? = null,
    open var startDate: String? = null,
    open var endDate: String? = null,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("SellerTransactionParams", "api/seller.uts", 77, 13)
    }
}
fun createProduct(data: ProductFormData): UTSPromise<ResponseDataType> {
    return post("/seller/products", data)
}
fun updateProduct(productId: Number, data: ProductFormData): UTSPromise<ResponseDataType> {
    return put("/seller/products/" + productId, data)
}
fun getSellerProductDetail(productId: Number): UTSPromise<ResponseDataType> {
    return get("/seller/products/" + productId)
}
fun uploadProductImage(filePath: String): UTSPromise<ResponseDataType> {
    return UTSPromise<ResponseDataType>(fun(resolve, reject){
        val token = uni_getStorageSync("auth_token") as String
        uni_uploadFile(UploadFileOptions(url = API_BASE_URL + "/seller/upload/image", filePath = filePath, name = "file", header = object : UTSJSONObject() {
            var Authorization = "Bearer " + token
        }, success = fun(uploadRes){
            val data = UTSAndroid.consoleDebugError(JSON.parse(uploadRes.data as String), " at api/seller.uts:106") as UTSJSONObject
            val code = (data.getNumber("code") ?: 0).toInt()
            val message = data.getString("message") ?: ""
            resolve(ResponseDataType(code = code, message = message, data = data.get("data")))
        }
        , fail = fun(err){
            reject(UTSError(err.errMsg))
        }
        ))
    }
    )
}
fun updateProductStatus(productId: Number, status: Number): UTSPromise<ResponseDataType> {
    return put("/seller/products/" + productId + "/status", UTSJSONObject(Map<String, Any?>(utsArrayOf(
        utsArrayOf(
            "status",
            status
        )
    ))))
}
fun deleteProduct(productId: Number): UTSPromise<ResponseDataType> {
    return del("/seller/products/" + productId)
}
fun getSellerProducts(params: ProductQueryParams): UTSPromise<ResponseDataType> {
    var url = "/seller/products?pageNum=" + params.pageNum + "&pageSize=" + params.pageSize
    if (params.status != null) {
        url += "&status=" + params.status
    }
    if (params.keyword != null) {
        val kw = params.keyword as String
        url += "&keyword=" + UTSAndroid.consoleDebugError(encodeURIComponent(kw), " at api/seller.uts:129")
    }
    if (params.categoryId != null) {
        url += "&categoryId=" + params.categoryId
    }
    return get(url)
}
fun getInventoryList(params: InventoryQueryParams): UTSPromise<ResponseDataType> {
    var url = "/seller/inventory?pageNum=" + params.pageNum + "&pageSize=" + params.pageSize
    if (params.productId != null) {
        url += "&productId=" + params.productId
    }
    if (params.lowStock == true) {
        url += "&lowStock=true"
    }
    return get(url)
}
fun adjustInventory(skuId: Number, data: AdjustData): UTSPromise<ResponseDataType> {
    return put("/seller/inventory/" + skuId, object : UTSJSONObject() {
        var adjustType = data.adjustType
        var quantity = data.quantity
        var remark = data.remark
    })
}
fun getSalesStatistics(params: StatisticsParams): UTSPromise<ResponseDataType> {
    var url = "/seller/statistics/sales"
    val query: UTSArray<String> = utsArrayOf()
    if (params.startDate != null) {
        query.push("startDate=" + params.startDate)
    }
    if (params.endDate != null) {
        query.push("endDate=" + params.endDate)
    }
    if (params.timeUnit != null) {
        query.push("timeUnit=" + params.timeUnit)
    }
    if (query.length > 0) {
        url += "?" + query.join("&")
    }
    return get(url)
}
fun getProductRanking(params: RankingParams): UTSPromise<ResponseDataType> {
    var url = "/seller/statistics/products?limit=" + params.limit
    if (params.orderBy != null) {
        url += "&orderBy=" + params.orderBy
    }
    return get(url)
}
fun getRevenueStatistics(): UTSPromise<ResponseDataType> {
    return get("/seller/statistics/revenue")
}
fun getOrderOverview(): UTSPromise<ResponseDataType> {
    return get("/seller/statistics/order-overview")
}
fun getOrderStatusDistribution(params: StatisticsParams): UTSPromise<ResponseDataType> {
    var url = "/seller/statistics/orders/status-distribution"
    val query: UTSArray<String> = utsArrayOf()
    if (params.startDate != null) {
        query.push("startDate=" + params.startDate)
    }
    if (params.endDate != null) {
        query.push("endDate=" + params.endDate)
    }
    if (params.timeUnit != null) {
        query.push("timeUnit=" + params.timeUnit)
    }
    if (query.length > 0) {
        url += "?" + query.join("&")
    }
    return get(url)
}
fun getSkuRanking(params: RankingParams): UTSPromise<ResponseDataType> {
    var url = "/seller/statistics/skus/rank?limit=" + params.limit
    if (params.orderBy != null) {
        url += "&orderBy=" + params.orderBy
    }
    return get(url)
}
fun getInventoryHealth(): UTSPromise<ResponseDataType> {
    return get("/seller/statistics/inventory/health")
}
fun getSellerStatus(): UTSPromise<ResponseDataType> {
    return get("/users/seller-status")
}
fun becomeSeller(shopName: String?): UTSPromise<ResponseDataType> {
    return post("/users/become-seller", UTSJSONObject(Map<String, Any?>(utsArrayOf(
        utsArrayOf(
            "shopName",
            shopName
        )
    ))))
}
fun getSellerAccount(): UTSPromise<ResponseDataType> {
    return get("/seller/account")
}
fun withdrawBalance(amount: Number): UTSPromise<ResponseDataType> {
    return post("/seller/account/withdraw", UTSJSONObject(Map<String, Any?>(utsArrayOf(
        utsArrayOf(
            "amount",
            amount
        )
    ))))
}
fun getSellerTransactions(params: SellerTransactionParams): UTSPromise<ResponseDataType> {
    var url = "/seller/account/transactions?pageNum=" + params.pageNum + "&pageSize=" + params.pageSize
    if (params.type != null) {
        url += "&type=" + params.type
    }
    if (params.startDate != null) {
        url += "&startDate=" + params.startDate
    }
    if (params.endDate != null) {
        url += "&endDate=" + params.endDate
    }
    return get(url)
}
fun replyReview(reviewId: Number, replyContent: String): UTSPromise<ResponseDataType> {
    return put("/seller/reviews/" + reviewId + "/reply?replyContent=" + UTSAndroid.consoleDebugError(encodeURIComponent(replyContent), " at api/seller.uts:249"), null)
}
fun getSellerReviews(productId: Number, pageNum: Number = 1, pageSize: Number = 10, rating: Number? = null): UTSPromise<ResponseDataType> {
    var url = "/seller/reviews?productId=" + productId + "&pageNum=" + pageNum + "&pageSize=" + pageSize
    if (rating != null) {
        url += "&rating=" + rating
    }
    return get(url)
}
open class UserInfoType (
    @JsonNotNull
    open var id: Any,
    @JsonNotNull
    open var avatar: String,
    @JsonNotNull
    open var username: String,
    @JsonNotNull
    open var email: String,
    @JsonNotNull
    open var phone: String,
    @JsonNotNull
    open var nickname: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("UserInfoType", "pages/user/profile.uvue", 302, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return UserInfoTypeReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class UserInfoTypeReactiveObject : UserInfoType, IUTSReactive<UserInfoType> {
    override var __v_raw: UserInfoType
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: UserInfoType, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, avatar = __v_raw.avatar, username = __v_raw.username, email = __v_raw.email, phone = __v_raw.phone, nickname = __v_raw.nickname) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UserInfoTypeReactiveObject {
        return UserInfoTypeReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Any
        get() {
            return trackReactiveGet(__v_raw, "id", __v_raw.id, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            triggerReactiveSet(__v_raw, "id", oldValue, value)
        }
    override var avatar: String
        get() {
            return trackReactiveGet(__v_raw, "avatar", __v_raw.avatar, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("avatar")) {
                return
            }
            val oldValue = __v_raw.avatar
            __v_raw.avatar = value
            triggerReactiveSet(__v_raw, "avatar", oldValue, value)
        }
    override var username: String
        get() {
            return trackReactiveGet(__v_raw, "username", __v_raw.username, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("username")) {
                return
            }
            val oldValue = __v_raw.username
            __v_raw.username = value
            triggerReactiveSet(__v_raw, "username", oldValue, value)
        }
    override var email: String
        get() {
            return trackReactiveGet(__v_raw, "email", __v_raw.email, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("email")) {
                return
            }
            val oldValue = __v_raw.email
            __v_raw.email = value
            triggerReactiveSet(__v_raw, "email", oldValue, value)
        }
    override var phone: String
        get() {
            return trackReactiveGet(__v_raw, "phone", __v_raw.phone, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("phone")) {
                return
            }
            val oldValue = __v_raw.phone
            __v_raw.phone = value
            triggerReactiveSet(__v_raw, "phone", oldValue, value)
        }
    override var nickname: String
        get() {
            return trackReactiveGet(__v_raw, "nickname", __v_raw.nickname, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("nickname")) {
                return
            }
            val oldValue = __v_raw.nickname
            __v_raw.nickname = value
            triggerReactiveSet(__v_raw, "nickname", oldValue, value)
        }
}
open class StatsType (
    @JsonNotNull
    open var orders: Number,
    @JsonNotNull
    open var pending: Number,
    @JsonNotNull
    open var shipped: Number,
    @JsonNotNull
    open var favorites: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("StatsType", "pages/user/profile.uvue", 311, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return StatsTypeReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class StatsTypeReactiveObject : StatsType, IUTSReactive<StatsType> {
    override var __v_raw: StatsType
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: StatsType, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(orders = __v_raw.orders, pending = __v_raw.pending, shipped = __v_raw.shipped, favorites = __v_raw.favorites) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): StatsTypeReactiveObject {
        return StatsTypeReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var orders: Number
        get() {
            return trackReactiveGet(__v_raw, "orders", __v_raw.orders, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("orders")) {
                return
            }
            val oldValue = __v_raw.orders
            __v_raw.orders = value
            triggerReactiveSet(__v_raw, "orders", oldValue, value)
        }
    override var pending: Number
        get() {
            return trackReactiveGet(__v_raw, "pending", __v_raw.pending, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("pending")) {
                return
            }
            val oldValue = __v_raw.pending
            __v_raw.pending = value
            triggerReactiveSet(__v_raw, "pending", oldValue, value)
        }
    override var shipped: Number
        get() {
            return trackReactiveGet(__v_raw, "shipped", __v_raw.shipped, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("shipped")) {
                return
            }
            val oldValue = __v_raw.shipped
            __v_raw.shipped = value
            triggerReactiveSet(__v_raw, "shipped", oldValue, value)
        }
    override var favorites: Number
        get() {
            return trackReactiveGet(__v_raw, "favorites", __v_raw.favorites, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("favorites")) {
                return
            }
            val oldValue = __v_raw.favorites
            __v_raw.favorites = value
            triggerReactiveSet(__v_raw, "favorites", oldValue, value)
        }
}
open class SellerStatsType (
    @JsonNotNull
    open var todaySales: Number,
    @JsonNotNull
    open var pendingShip: Number,
    @JsonNotNull
    open var pendingSettle: Number,
    @JsonNotNull
    open var productCount: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("SellerStatsType", "pages/user/profile.uvue", 319, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return SellerStatsTypeReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class SellerStatsTypeReactiveObject : SellerStatsType, IUTSReactive<SellerStatsType> {
    override var __v_raw: SellerStatsType
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: SellerStatsType, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(todaySales = __v_raw.todaySales, pendingShip = __v_raw.pendingShip, pendingSettle = __v_raw.pendingSettle, productCount = __v_raw.productCount) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): SellerStatsTypeReactiveObject {
        return SellerStatsTypeReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var todaySales: Number
        get() {
            return trackReactiveGet(__v_raw, "todaySales", __v_raw.todaySales, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("todaySales")) {
                return
            }
            val oldValue = __v_raw.todaySales
            __v_raw.todaySales = value
            triggerReactiveSet(__v_raw, "todaySales", oldValue, value)
        }
    override var pendingShip: Number
        get() {
            return trackReactiveGet(__v_raw, "pendingShip", __v_raw.pendingShip, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("pendingShip")) {
                return
            }
            val oldValue = __v_raw.pendingShip
            __v_raw.pendingShip = value
            triggerReactiveSet(__v_raw, "pendingShip", oldValue, value)
        }
    override var pendingSettle: Number
        get() {
            return trackReactiveGet(__v_raw, "pendingSettle", __v_raw.pendingSettle, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("pendingSettle")) {
                return
            }
            val oldValue = __v_raw.pendingSettle
            __v_raw.pendingSettle = value
            triggerReactiveSet(__v_raw, "pendingSettle", oldValue, value)
        }
    override var productCount: Number
        get() {
            return trackReactiveGet(__v_raw, "productCount", __v_raw.productCount, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("productCount")) {
                return
            }
            val oldValue = __v_raw.productCount
            __v_raw.productCount = value
            triggerReactiveSet(__v_raw, "productCount", oldValue, value)
        }
}
val GenPagesUserProfileClass = CreateVueComponent(GenPagesUserProfile::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesUserProfile.inheritAttrs, inject = GenPagesUserProfile.inject, props = GenPagesUserProfile.props, propsNeedCastKeys = GenPagesUserProfile.propsNeedCastKeys, emits = GenPagesUserProfile.emits, components = GenPagesUserProfile.components, styles = GenPagesUserProfile.styles)
}
, fun(instance, renderer): GenPagesUserProfile {
    return GenPagesUserProfile(instance, renderer)
}
)
open class ProfileForm (
    @JsonNotNull
    open var nickname: String,
    @JsonNotNull
    open var avatar: String,
    @JsonNotNull
    open var gender: Number,
    @JsonNotNull
    open var phone: String,
    @JsonNotNull
    open var email: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ProfileForm", "pages/user/edit-profile.uvue", 135, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ProfileFormReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class ProfileFormReactiveObject : ProfileForm, IUTSReactive<ProfileForm> {
    override var __v_raw: ProfileForm
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ProfileForm, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(nickname = __v_raw.nickname, avatar = __v_raw.avatar, gender = __v_raw.gender, phone = __v_raw.phone, email = __v_raw.email) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ProfileFormReactiveObject {
        return ProfileFormReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var nickname: String
        get() {
            return trackReactiveGet(__v_raw, "nickname", __v_raw.nickname, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("nickname")) {
                return
            }
            val oldValue = __v_raw.nickname
            __v_raw.nickname = value
            triggerReactiveSet(__v_raw, "nickname", oldValue, value)
        }
    override var avatar: String
        get() {
            return trackReactiveGet(__v_raw, "avatar", __v_raw.avatar, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("avatar")) {
                return
            }
            val oldValue = __v_raw.avatar
            __v_raw.avatar = value
            triggerReactiveSet(__v_raw, "avatar", oldValue, value)
        }
    override var gender: Number
        get() {
            return trackReactiveGet(__v_raw, "gender", __v_raw.gender, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("gender")) {
                return
            }
            val oldValue = __v_raw.gender
            __v_raw.gender = value
            triggerReactiveSet(__v_raw, "gender", oldValue, value)
        }
    override var phone: String
        get() {
            return trackReactiveGet(__v_raw, "phone", __v_raw.phone, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("phone")) {
                return
            }
            val oldValue = __v_raw.phone
            __v_raw.phone = value
            triggerReactiveSet(__v_raw, "phone", oldValue, value)
        }
    override var email: String
        get() {
            return trackReactiveGet(__v_raw, "email", __v_raw.email, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("email")) {
                return
            }
            val oldValue = __v_raw.email
            __v_raw.email = value
            triggerReactiveSet(__v_raw, "email", oldValue, value)
        }
}
open class ProfileErrors (
    @JsonNotNull
    open var nickname: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ProfileErrors", "pages/user/edit-profile.uvue", 143, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ProfileErrorsReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class ProfileErrorsReactiveObject : ProfileErrors, IUTSReactive<ProfileErrors> {
    override var __v_raw: ProfileErrors
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ProfileErrors, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(nickname = __v_raw.nickname) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ProfileErrorsReactiveObject {
        return ProfileErrorsReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var nickname: String
        get() {
            return trackReactiveGet(__v_raw, "nickname", __v_raw.nickname, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("nickname")) {
                return
            }
            val oldValue = __v_raw.nickname
            __v_raw.nickname = value
            triggerReactiveSet(__v_raw, "nickname", oldValue, value)
        }
}
val GenPagesUserEditProfileClass = CreateVueComponent(GenPagesUserEditProfile::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesUserEditProfile.inheritAttrs, inject = GenPagesUserEditProfile.inject, props = GenPagesUserEditProfile.props, propsNeedCastKeys = GenPagesUserEditProfile.propsNeedCastKeys, emits = GenPagesUserEditProfile.emits, components = GenPagesUserEditProfile.components, styles = GenPagesUserEditProfile.styles)
}
, fun(instance, renderer): GenPagesUserEditProfile {
    return GenPagesUserEditProfile(instance, renderer)
}
)
open class PasswordForm (
    @JsonNotNull
    open var oldPassword: String,
    @JsonNotNull
    open var newPassword: String,
    @JsonNotNull
    open var confirmPassword: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PasswordForm", "pages/user/change-password.uvue", 109, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return PasswordFormReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class PasswordFormReactiveObject : PasswordForm, IUTSReactive<PasswordForm> {
    override var __v_raw: PasswordForm
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: PasswordForm, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(oldPassword = __v_raw.oldPassword, newPassword = __v_raw.newPassword, confirmPassword = __v_raw.confirmPassword) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): PasswordFormReactiveObject {
        return PasswordFormReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var oldPassword: String
        get() {
            return trackReactiveGet(__v_raw, "oldPassword", __v_raw.oldPassword, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("oldPassword")) {
                return
            }
            val oldValue = __v_raw.oldPassword
            __v_raw.oldPassword = value
            triggerReactiveSet(__v_raw, "oldPassword", oldValue, value)
        }
    override var newPassword: String
        get() {
            return trackReactiveGet(__v_raw, "newPassword", __v_raw.newPassword, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("newPassword")) {
                return
            }
            val oldValue = __v_raw.newPassword
            __v_raw.newPassword = value
            triggerReactiveSet(__v_raw, "newPassword", oldValue, value)
        }
    override var confirmPassword: String
        get() {
            return trackReactiveGet(__v_raw, "confirmPassword", __v_raw.confirmPassword, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("confirmPassword")) {
                return
            }
            val oldValue = __v_raw.confirmPassword
            __v_raw.confirmPassword = value
            triggerReactiveSet(__v_raw, "confirmPassword", oldValue, value)
        }
}
open class PasswordErrors (
    @JsonNotNull
    open var oldPassword: String,
    @JsonNotNull
    open var newPassword: String,
    @JsonNotNull
    open var confirmPassword: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PasswordErrors", "pages/user/change-password.uvue", 115, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return PasswordErrorsReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class PasswordErrorsReactiveObject : PasswordErrors, IUTSReactive<PasswordErrors> {
    override var __v_raw: PasswordErrors
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: PasswordErrors, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(oldPassword = __v_raw.oldPassword, newPassword = __v_raw.newPassword, confirmPassword = __v_raw.confirmPassword) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): PasswordErrorsReactiveObject {
        return PasswordErrorsReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var oldPassword: String
        get() {
            return trackReactiveGet(__v_raw, "oldPassword", __v_raw.oldPassword, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("oldPassword")) {
                return
            }
            val oldValue = __v_raw.oldPassword
            __v_raw.oldPassword = value
            triggerReactiveSet(__v_raw, "oldPassword", oldValue, value)
        }
    override var newPassword: String
        get() {
            return trackReactiveGet(__v_raw, "newPassword", __v_raw.newPassword, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("newPassword")) {
                return
            }
            val oldValue = __v_raw.newPassword
            __v_raw.newPassword = value
            triggerReactiveSet(__v_raw, "newPassword", oldValue, value)
        }
    override var confirmPassword: String
        get() {
            return trackReactiveGet(__v_raw, "confirmPassword", __v_raw.confirmPassword, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("confirmPassword")) {
                return
            }
            val oldValue = __v_raw.confirmPassword
            __v_raw.confirmPassword = value
            triggerReactiveSet(__v_raw, "confirmPassword", oldValue, value)
        }
}
val GenPagesUserChangePasswordClass = CreateVueComponent(GenPagesUserChangePassword::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesUserChangePassword.inheritAttrs, inject = GenPagesUserChangePassword.inject, props = GenPagesUserChangePassword.props, propsNeedCastKeys = GenPagesUserChangePassword.propsNeedCastKeys, emits = GenPagesUserChangePassword.emits, components = GenPagesUserChangePassword.components, styles = GenPagesUserChangePassword.styles)
}
, fun(instance, renderer): GenPagesUserChangePassword {
    return GenPagesUserChangePassword(instance, renderer)
}
)
open class PCAItem (
    @JsonNotNull
    open var name: String,
    @JsonNotNull
    open var code: String,
    @JsonNotNull
    open var children: UTSArray<PCAItem>,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PCAItem", "utils/pca-data.uts", 4, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return PCAItemReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class PCAItemReactiveObject : PCAItem, IUTSReactive<PCAItem> {
    override var __v_raw: PCAItem
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: PCAItem, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(name = __v_raw.name, code = __v_raw.code, children = __v_raw.children) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): PCAItemReactiveObject {
        return PCAItemReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var name: String
        get() {
            return trackReactiveGet(__v_raw, "name", __v_raw.name, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("name")) {
                return
            }
            val oldValue = __v_raw.name
            __v_raw.name = value
            triggerReactiveSet(__v_raw, "name", oldValue, value)
        }
    override var code: String
        get() {
            return trackReactiveGet(__v_raw, "code", __v_raw.code, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("code")) {
                return
            }
            val oldValue = __v_raw.code
            __v_raw.code = value
            triggerReactiveSet(__v_raw, "code", oldValue, value)
        }
    override var children: UTSArray<PCAItem>
        get() {
            return trackReactiveGet(__v_raw, "children", __v_raw.children, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("children")) {
                return
            }
            val oldValue = __v_raw.children
            __v_raw.children = value
            triggerReactiveSet(__v_raw, "children", oldValue, value)
        }
}
open class AddressForm (
    @JsonNotNull
    open var receiverName: String,
    @JsonNotNull
    open var receiverPhone: String,
    @JsonNotNull
    open var province: String,
    @JsonNotNull
    open var city: String,
    @JsonNotNull
    open var district: String,
    @JsonNotNull
    open var detailAddress: String,
    @JsonNotNull
    open var isDefault: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("AddressForm", "pages/user/address-edit.uvue", 79, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return AddressFormReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class AddressFormReactiveObject : AddressForm, IUTSReactive<AddressForm> {
    override var __v_raw: AddressForm
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: AddressForm, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(receiverName = __v_raw.receiverName, receiverPhone = __v_raw.receiverPhone, province = __v_raw.province, city = __v_raw.city, district = __v_raw.district, detailAddress = __v_raw.detailAddress, isDefault = __v_raw.isDefault) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): AddressFormReactiveObject {
        return AddressFormReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var receiverName: String
        get() {
            return trackReactiveGet(__v_raw, "receiverName", __v_raw.receiverName, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("receiverName")) {
                return
            }
            val oldValue = __v_raw.receiverName
            __v_raw.receiverName = value
            triggerReactiveSet(__v_raw, "receiverName", oldValue, value)
        }
    override var receiverPhone: String
        get() {
            return trackReactiveGet(__v_raw, "receiverPhone", __v_raw.receiverPhone, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("receiverPhone")) {
                return
            }
            val oldValue = __v_raw.receiverPhone
            __v_raw.receiverPhone = value
            triggerReactiveSet(__v_raw, "receiverPhone", oldValue, value)
        }
    override var province: String
        get() {
            return trackReactiveGet(__v_raw, "province", __v_raw.province, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("province")) {
                return
            }
            val oldValue = __v_raw.province
            __v_raw.province = value
            triggerReactiveSet(__v_raw, "province", oldValue, value)
        }
    override var city: String
        get() {
            return trackReactiveGet(__v_raw, "city", __v_raw.city, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("city")) {
                return
            }
            val oldValue = __v_raw.city
            __v_raw.city = value
            triggerReactiveSet(__v_raw, "city", oldValue, value)
        }
    override var district: String
        get() {
            return trackReactiveGet(__v_raw, "district", __v_raw.district, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("district")) {
                return
            }
            val oldValue = __v_raw.district
            __v_raw.district = value
            triggerReactiveSet(__v_raw, "district", oldValue, value)
        }
    override var detailAddress: String
        get() {
            return trackReactiveGet(__v_raw, "detailAddress", __v_raw.detailAddress, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("detailAddress")) {
                return
            }
            val oldValue = __v_raw.detailAddress
            __v_raw.detailAddress = value
            triggerReactiveSet(__v_raw, "detailAddress", oldValue, value)
        }
    override var isDefault: Number
        get() {
            return trackReactiveGet(__v_raw, "isDefault", __v_raw.isDefault, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("isDefault")) {
                return
            }
            val oldValue = __v_raw.isDefault
            __v_raw.isDefault = value
            triggerReactiveSet(__v_raw, "isDefault", oldValue, value)
        }
}
val GenPagesUserAddressEditClass = CreateVueComponent(GenPagesUserAddressEdit::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesUserAddressEdit.inheritAttrs, inject = GenPagesUserAddressEdit.inject, props = GenPagesUserAddressEdit.props, propsNeedCastKeys = GenPagesUserAddressEdit.propsNeedCastKeys, emits = GenPagesUserAddressEdit.emits, components = GenPagesUserAddressEdit.components, styles = GenPagesUserAddressEdit.styles)
}
, fun(instance, renderer): GenPagesUserAddressEdit {
    return GenPagesUserAddressEdit(instance, renderer)
}
)
open class UserInfoType1 (
    @JsonNotNull
    open var username: String,
    @JsonNotNull
    open var email: String,
    @JsonNotNull
    open var phone: String,
    @JsonNotNull
    open var nickname: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("UserInfoType", "pages/user/settings.uvue", 76, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return UserInfoType1ReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class UserInfoType1ReactiveObject : UserInfoType1, IUTSReactive<UserInfoType1> {
    override var __v_raw: UserInfoType1
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: UserInfoType1, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(username = __v_raw.username, email = __v_raw.email, phone = __v_raw.phone, nickname = __v_raw.nickname) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UserInfoType1ReactiveObject {
        return UserInfoType1ReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var username: String
        get() {
            return trackReactiveGet(__v_raw, "username", __v_raw.username, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("username")) {
                return
            }
            val oldValue = __v_raw.username
            __v_raw.username = value
            triggerReactiveSet(__v_raw, "username", oldValue, value)
        }
    override var email: String
        get() {
            return trackReactiveGet(__v_raw, "email", __v_raw.email, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("email")) {
                return
            }
            val oldValue = __v_raw.email
            __v_raw.email = value
            triggerReactiveSet(__v_raw, "email", oldValue, value)
        }
    override var phone: String
        get() {
            return trackReactiveGet(__v_raw, "phone", __v_raw.phone, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("phone")) {
                return
            }
            val oldValue = __v_raw.phone
            __v_raw.phone = value
            triggerReactiveSet(__v_raw, "phone", oldValue, value)
        }
    override var nickname: String
        get() {
            return trackReactiveGet(__v_raw, "nickname", __v_raw.nickname, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("nickname")) {
                return
            }
            val oldValue = __v_raw.nickname
            __v_raw.nickname = value
            triggerReactiveSet(__v_raw, "nickname", oldValue, value)
        }
}
val GenPagesUserSettingsClass = CreateVueComponent(GenPagesUserSettings::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesUserSettings.inheritAttrs, inject = GenPagesUserSettings.inject, props = GenPagesUserSettings.props, propsNeedCastKeys = GenPagesUserSettings.propsNeedCastKeys, emits = GenPagesUserSettings.emits, components = GenPagesUserSettings.components, styles = GenPagesUserSettings.styles)
}
, fun(instance, renderer): GenPagesUserSettings {
    return GenPagesUserSettings(instance, renderer)
}
)
open class AddressItem (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var receiverName: String,
    @JsonNotNull
    open var receiverPhone: String,
    @JsonNotNull
    open var province: String,
    @JsonNotNull
    open var city: String,
    @JsonNotNull
    open var district: String,
    @JsonNotNull
    open var detailAddress: String,
    @JsonNotNull
    open var fullAddress: String,
    @JsonNotNull
    open var isDefault: Number,
    @JsonNotNull
    open var createTime: String,
    @JsonNotNull
    open var updateTime: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("AddressItem", "pages/user/address-list.uvue", 86, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return AddressItemReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class AddressItemReactiveObject : AddressItem, IUTSReactive<AddressItem> {
    override var __v_raw: AddressItem
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: AddressItem, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, receiverName = __v_raw.receiverName, receiverPhone = __v_raw.receiverPhone, province = __v_raw.province, city = __v_raw.city, district = __v_raw.district, detailAddress = __v_raw.detailAddress, fullAddress = __v_raw.fullAddress, isDefault = __v_raw.isDefault, createTime = __v_raw.createTime, updateTime = __v_raw.updateTime) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): AddressItemReactiveObject {
        return AddressItemReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return trackReactiveGet(__v_raw, "id", __v_raw.id, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            triggerReactiveSet(__v_raw, "id", oldValue, value)
        }
    override var receiverName: String
        get() {
            return trackReactiveGet(__v_raw, "receiverName", __v_raw.receiverName, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("receiverName")) {
                return
            }
            val oldValue = __v_raw.receiverName
            __v_raw.receiverName = value
            triggerReactiveSet(__v_raw, "receiverName", oldValue, value)
        }
    override var receiverPhone: String
        get() {
            return trackReactiveGet(__v_raw, "receiverPhone", __v_raw.receiverPhone, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("receiverPhone")) {
                return
            }
            val oldValue = __v_raw.receiverPhone
            __v_raw.receiverPhone = value
            triggerReactiveSet(__v_raw, "receiverPhone", oldValue, value)
        }
    override var province: String
        get() {
            return trackReactiveGet(__v_raw, "province", __v_raw.province, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("province")) {
                return
            }
            val oldValue = __v_raw.province
            __v_raw.province = value
            triggerReactiveSet(__v_raw, "province", oldValue, value)
        }
    override var city: String
        get() {
            return trackReactiveGet(__v_raw, "city", __v_raw.city, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("city")) {
                return
            }
            val oldValue = __v_raw.city
            __v_raw.city = value
            triggerReactiveSet(__v_raw, "city", oldValue, value)
        }
    override var district: String
        get() {
            return trackReactiveGet(__v_raw, "district", __v_raw.district, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("district")) {
                return
            }
            val oldValue = __v_raw.district
            __v_raw.district = value
            triggerReactiveSet(__v_raw, "district", oldValue, value)
        }
    override var detailAddress: String
        get() {
            return trackReactiveGet(__v_raw, "detailAddress", __v_raw.detailAddress, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("detailAddress")) {
                return
            }
            val oldValue = __v_raw.detailAddress
            __v_raw.detailAddress = value
            triggerReactiveSet(__v_raw, "detailAddress", oldValue, value)
        }
    override var fullAddress: String
        get() {
            return trackReactiveGet(__v_raw, "fullAddress", __v_raw.fullAddress, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("fullAddress")) {
                return
            }
            val oldValue = __v_raw.fullAddress
            __v_raw.fullAddress = value
            triggerReactiveSet(__v_raw, "fullAddress", oldValue, value)
        }
    override var isDefault: Number
        get() {
            return trackReactiveGet(__v_raw, "isDefault", __v_raw.isDefault, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("isDefault")) {
                return
            }
            val oldValue = __v_raw.isDefault
            __v_raw.isDefault = value
            triggerReactiveSet(__v_raw, "isDefault", oldValue, value)
        }
    override var createTime: String
        get() {
            return trackReactiveGet(__v_raw, "createTime", __v_raw.createTime, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("createTime")) {
                return
            }
            val oldValue = __v_raw.createTime
            __v_raw.createTime = value
            triggerReactiveSet(__v_raw, "createTime", oldValue, value)
        }
    override var updateTime: String
        get() {
            return trackReactiveGet(__v_raw, "updateTime", __v_raw.updateTime, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("updateTime")) {
                return
            }
            val oldValue = __v_raw.updateTime
            __v_raw.updateTime = value
            triggerReactiveSet(__v_raw, "updateTime", oldValue, value)
        }
}
val GenPagesUserAddressListClass = CreateVueComponent(GenPagesUserAddressList::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesUserAddressList.inheritAttrs, inject = GenPagesUserAddressList.inject, props = GenPagesUserAddressList.props, propsNeedCastKeys = GenPagesUserAddressList.propsNeedCastKeys, emits = GenPagesUserAddressList.emits, components = GenPagesUserAddressList.components, styles = GenPagesUserAddressList.styles)
}
, fun(instance, renderer): GenPagesUserAddressList {
    return GenPagesUserAddressList(instance, renderer)
}
)
val GenPagesUserReviewsClass = CreateVueComponent(GenPagesUserReviews::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesUserReviews.inheritAttrs, inject = GenPagesUserReviews.inject, props = GenPagesUserReviews.props, propsNeedCastKeys = GenPagesUserReviews.propsNeedCastKeys, emits = GenPagesUserReviews.emits, components = GenPagesUserReviews.components, styles = GenPagesUserReviews.styles)
}
, fun(instance, renderer): GenPagesUserReviews {
    return GenPagesUserReviews(instance, renderer)
}
)
val GenPagesUserWalletClass = CreateVueComponent(GenPagesUserWallet::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesUserWallet.inheritAttrs, inject = GenPagesUserWallet.inject, props = GenPagesUserWallet.props, propsNeedCastKeys = GenPagesUserWallet.propsNeedCastKeys, emits = GenPagesUserWallet.emits, components = GenPagesUserWallet.components, styles = GenPagesUserWallet.styles)
}
, fun(instance, renderer): GenPagesUserWallet {
    return GenPagesUserWallet(instance, renderer)
}
)
open class AiStreamHandlers (
    open var onChunk: (payload: Any) -> Unit,
    open var onError: (error: Any) -> Unit,
    open var onComplete: () -> Unit,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("AiStreamHandlers", "api/ai.uts", 5, 13)
    }
}
fun chatWithAiStream(question: String, sessionId: String?, handlers: AiStreamHandlers): Any {
    val token = uni_getStorageSync("auth_token") as String?
    var headers: UTSJSONObject
    if (token != null && token !== "") {
        headers = object : UTSJSONObject() {
            var `Content-Type` = "application/json"
            var Accept = "text/event-stream"
            var Authorization = "Bearer " + token
        }
    } else {
        headers = object : UTSJSONObject() {
            var `Content-Type` = "application/json"
            var Accept = "text/event-stream"
        }
    }
    val logTag = "[ai.stream]"
    console.log("" + logTag + " start", UTSJSONObject(Map<String, Any?>(utsArrayOf(
        utsArrayOf(
            "question",
            question
        ),
        utsArrayOf(
            "sessionId",
            sessionId
        )
    ))), " at api/ai.ts:39")
    var result: Any = UTSJSONObject() as Any
    console.log("" + logTag + " platform", "APP SSE via uni.request", " at api/ai.ts:51")
    val url = "" + API_BASE_URL + "/ai/chat/stream"
    val authHeader = headers.getString("Authorization")
    val payload: UTSJSONObject = UTSJSONObject(Map<String, Any?>(utsArrayOf(
        utsArrayOf(
            "__\$originalPosition",
            UTSSourceMapPosition("payload", "api/ai.uts", 38, 11)
        ),
        utsArrayOf(
            "question",
            question
        ),
        utsArrayOf(
            "sessionId",
            sessionId
        )
    )))
    console.log("" + logTag + " request", UTSJSONObject(Map<String, Any?>(utsArrayOf(
        utsArrayOf(
            "url",
            url
        ),
        utsArrayOf(
            "headers",
            object : UTSJSONObject() {
                var `Content-Type` = "application/json; charset=utf-8"
                var Accept = "text/event-stream"
                var Authorization = authHeader
            }
        ),
        utsArrayOf(
            "payload",
            payload
        )
    ))), " at api/ai.ts:58")
    var isCompleted = false
    val reqOpts = RequestOptions(url = url, method = "POST", data = payload, header = object : UTSJSONObject() {
        var `Content-Type` = "application/json; charset=utf-8"
        var Accept = "text/event-stream"
        var Authorization = authHeader
    }, timeout = 0, success = fun(res: RequestSuccess<String>) {
        if (res != null) {
            console.log("" + logTag + " response", res, " at api/ai.ts:82")
            val text = if (res.data != null) {
                res.data
            } else {
                ""
            }
            if (text != null && text != "") {
                val normalized = text.split("\r\n").join("\n").split("\r").join("\n")
                val lines = normalized.split("\n")
                var dataBuffer = ""
                run {
                    var i: Number = 0
                    while(i < lines.length){
                        val line = lines[i].trim()
                        if (line == "") {
                            if (dataBuffer != "") {
                                val jsonText = dataBuffer.trim()
                                if (jsonText != "" && jsonText != "[DONE]") {
                                    try {
                                        val payload = UTSAndroid.consoleDebugError(JSON.parse(jsonText), " at api/ai.uts:77") as Any
                                        handlers.onChunk(payload)
                                    }
                                     catch (error: Throwable) {
                                        handlers.onError(error)
                                    }
                                }
                                dataBuffer = ""
                            }
                            i++
                            continue
                        }
                        if (line.startsWith("data:")) {
                            val chunk = line.replace("data:", "").trim()
                            dataBuffer = if (dataBuffer == "") {
                                chunk
                            } else {
                                "" + dataBuffer + chunk
                            }
                        }
                        i++
                    }
                }
                if (dataBuffer != "") {
                    val jsonText = dataBuffer.trim()
                    if (jsonText != "" && jsonText != "[DONE]") {
                        try {
                            val payload = UTSAndroid.consoleDebugError(JSON.parse(jsonText), " at api/ai.uts:97") as Any
                            handlers.onChunk(payload)
                        }
                         catch (error: Throwable) {
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
    }
    , fail = fun(err: RequestFail) {
        if (!isCompleted) {
            isCompleted = true
        }
        handlers.onError(err)
    }
    )
    val requestTask = uni_request<String>(reqOpts as RequestOptions<String>)
    result = requestTask as Any
    return result
}
open class ProductCard (
    @JsonNotNull
    open var productId: Number,
    @JsonNotNull
    open var productName: String,
    @JsonNotNull
    open var mainImage: String,
    @JsonNotNull
    open var currentPrice: Number,
    @JsonNotNull
    open var description: String,
    @JsonNotNull
    open var detailUrl: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ProductCard", "pages/service/chat.uvue", 89, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ProductCardReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class ProductCardReactiveObject : ProductCard, IUTSReactive<ProductCard> {
    override var __v_raw: ProductCard
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ProductCard, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(productId = __v_raw.productId, productName = __v_raw.productName, mainImage = __v_raw.mainImage, currentPrice = __v_raw.currentPrice, description = __v_raw.description, detailUrl = __v_raw.detailUrl) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ProductCardReactiveObject {
        return ProductCardReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var productId: Number
        get() {
            return trackReactiveGet(__v_raw, "productId", __v_raw.productId, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("productId")) {
                return
            }
            val oldValue = __v_raw.productId
            __v_raw.productId = value
            triggerReactiveSet(__v_raw, "productId", oldValue, value)
        }
    override var productName: String
        get() {
            return trackReactiveGet(__v_raw, "productName", __v_raw.productName, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("productName")) {
                return
            }
            val oldValue = __v_raw.productName
            __v_raw.productName = value
            triggerReactiveSet(__v_raw, "productName", oldValue, value)
        }
    override var mainImage: String
        get() {
            return trackReactiveGet(__v_raw, "mainImage", __v_raw.mainImage, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("mainImage")) {
                return
            }
            val oldValue = __v_raw.mainImage
            __v_raw.mainImage = value
            triggerReactiveSet(__v_raw, "mainImage", oldValue, value)
        }
    override var currentPrice: Number
        get() {
            return trackReactiveGet(__v_raw, "currentPrice", __v_raw.currentPrice, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("currentPrice")) {
                return
            }
            val oldValue = __v_raw.currentPrice
            __v_raw.currentPrice = value
            triggerReactiveSet(__v_raw, "currentPrice", oldValue, value)
        }
    override var description: String
        get() {
            return trackReactiveGet(__v_raw, "description", __v_raw.description, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("description")) {
                return
            }
            val oldValue = __v_raw.description
            __v_raw.description = value
            triggerReactiveSet(__v_raw, "description", oldValue, value)
        }
    override var detailUrl: String
        get() {
            return trackReactiveGet(__v_raw, "detailUrl", __v_raw.detailUrl, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("detailUrl")) {
                return
            }
            val oldValue = __v_raw.detailUrl
            __v_raw.detailUrl = value
            triggerReactiveSet(__v_raw, "detailUrl", oldValue, value)
        }
}
open class Message (
    @JsonNotNull
    open var id: String,
    @JsonNotNull
    open var content: String,
    @JsonNotNull
    open var isUser: Boolean = false,
    @JsonNotNull
    open var timestamp: Number,
    open var products: UTSArray<ProductCard>? = null,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("Message", "pages/service/chat.uvue", 98, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return MessageReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class MessageReactiveObject : Message, IUTSReactive<Message> {
    override var __v_raw: Message
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: Message, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, content = __v_raw.content, isUser = __v_raw.isUser, timestamp = __v_raw.timestamp, products = __v_raw.products) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): MessageReactiveObject {
        return MessageReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: String
        get() {
            return trackReactiveGet(__v_raw, "id", __v_raw.id, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            triggerReactiveSet(__v_raw, "id", oldValue, value)
        }
    override var content: String
        get() {
            return trackReactiveGet(__v_raw, "content", __v_raw.content, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("content")) {
                return
            }
            val oldValue = __v_raw.content
            __v_raw.content = value
            triggerReactiveSet(__v_raw, "content", oldValue, value)
        }
    override var isUser: Boolean
        get() {
            return trackReactiveGet(__v_raw, "isUser", __v_raw.isUser, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("isUser")) {
                return
            }
            val oldValue = __v_raw.isUser
            __v_raw.isUser = value
            triggerReactiveSet(__v_raw, "isUser", oldValue, value)
        }
    override var timestamp: Number
        get() {
            return trackReactiveGet(__v_raw, "timestamp", __v_raw.timestamp, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("timestamp")) {
                return
            }
            val oldValue = __v_raw.timestamp
            __v_raw.timestamp = value
            triggerReactiveSet(__v_raw, "timestamp", oldValue, value)
        }
    override var products: UTSArray<ProductCard>?
        get() {
            return trackReactiveGet(__v_raw, "products", __v_raw.products, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("products")) {
                return
            }
            val oldValue = __v_raw.products
            __v_raw.products = value
            triggerReactiveSet(__v_raw, "products", oldValue, value)
        }
}
val GenPagesServiceChatClass = CreateVueComponent(GenPagesServiceChat::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesServiceChat.inheritAttrs, inject = GenPagesServiceChat.inject, props = GenPagesServiceChat.props, propsNeedCastKeys = GenPagesServiceChat.propsNeedCastKeys, emits = GenPagesServiceChat.emits, components = GenPagesServiceChat.components, styles = GenPagesServiceChat.styles)
}
, fun(instance, renderer): GenPagesServiceChat {
    return GenPagesServiceChat(instance, renderer)
}
)
open class SellerProductType (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var productName: String,
    @JsonNotNull
    open var mainImage: String,
    @JsonNotNull
    open var currentPrice: Number,
    open var originalPrice: Number? = null,
    @JsonNotNull
    open var totalStock: Number,
    @JsonNotNull
    open var sales: Number,
    @JsonNotNull
    open var status: Number,
    @JsonNotNull
    open var categoryId: Number,
    open var categoryName: String? = null,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("SellerProductType", "pages/seller/products.uvue", 131, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return SellerProductTypeReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class SellerProductTypeReactiveObject : SellerProductType, IUTSReactive<SellerProductType> {
    override var __v_raw: SellerProductType
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: SellerProductType, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, productName = __v_raw.productName, mainImage = __v_raw.mainImage, currentPrice = __v_raw.currentPrice, originalPrice = __v_raw.originalPrice, totalStock = __v_raw.totalStock, sales = __v_raw.sales, status = __v_raw.status, categoryId = __v_raw.categoryId, categoryName = __v_raw.categoryName) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): SellerProductTypeReactiveObject {
        return SellerProductTypeReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return trackReactiveGet(__v_raw, "id", __v_raw.id, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            triggerReactiveSet(__v_raw, "id", oldValue, value)
        }
    override var productName: String
        get() {
            return trackReactiveGet(__v_raw, "productName", __v_raw.productName, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("productName")) {
                return
            }
            val oldValue = __v_raw.productName
            __v_raw.productName = value
            triggerReactiveSet(__v_raw, "productName", oldValue, value)
        }
    override var mainImage: String
        get() {
            return trackReactiveGet(__v_raw, "mainImage", __v_raw.mainImage, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("mainImage")) {
                return
            }
            val oldValue = __v_raw.mainImage
            __v_raw.mainImage = value
            triggerReactiveSet(__v_raw, "mainImage", oldValue, value)
        }
    override var currentPrice: Number
        get() {
            return trackReactiveGet(__v_raw, "currentPrice", __v_raw.currentPrice, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("currentPrice")) {
                return
            }
            val oldValue = __v_raw.currentPrice
            __v_raw.currentPrice = value
            triggerReactiveSet(__v_raw, "currentPrice", oldValue, value)
        }
    override var originalPrice: Number?
        get() {
            return trackReactiveGet(__v_raw, "originalPrice", __v_raw.originalPrice, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("originalPrice")) {
                return
            }
            val oldValue = __v_raw.originalPrice
            __v_raw.originalPrice = value
            triggerReactiveSet(__v_raw, "originalPrice", oldValue, value)
        }
    override var totalStock: Number
        get() {
            return trackReactiveGet(__v_raw, "totalStock", __v_raw.totalStock, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("totalStock")) {
                return
            }
            val oldValue = __v_raw.totalStock
            __v_raw.totalStock = value
            triggerReactiveSet(__v_raw, "totalStock", oldValue, value)
        }
    override var sales: Number
        get() {
            return trackReactiveGet(__v_raw, "sales", __v_raw.sales, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("sales")) {
                return
            }
            val oldValue = __v_raw.sales
            __v_raw.sales = value
            triggerReactiveSet(__v_raw, "sales", oldValue, value)
        }
    override var status: Number
        get() {
            return trackReactiveGet(__v_raw, "status", __v_raw.status, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("status")) {
                return
            }
            val oldValue = __v_raw.status
            __v_raw.status = value
            triggerReactiveSet(__v_raw, "status", oldValue, value)
        }
    override var categoryId: Number
        get() {
            return trackReactiveGet(__v_raw, "categoryId", __v_raw.categoryId, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("categoryId")) {
                return
            }
            val oldValue = __v_raw.categoryId
            __v_raw.categoryId = value
            triggerReactiveSet(__v_raw, "categoryId", oldValue, value)
        }
    override var categoryName: String?
        get() {
            return trackReactiveGet(__v_raw, "categoryName", __v_raw.categoryName, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("categoryName")) {
                return
            }
            val oldValue = __v_raw.categoryName
            __v_raw.categoryName = value
            triggerReactiveSet(__v_raw, "categoryName", oldValue, value)
        }
}
val GenPagesSellerProductsClass = CreateVueComponent(GenPagesSellerProducts::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesSellerProducts.inheritAttrs, inject = GenPagesSellerProducts.inject, props = GenPagesSellerProducts.props, propsNeedCastKeys = GenPagesSellerProducts.propsNeedCastKeys, emits = GenPagesSellerProducts.emits, components = GenPagesSellerProducts.components, styles = GenPagesSellerProducts.styles)
}
, fun(instance, renderer): GenPagesSellerProducts {
    return GenPagesSellerProducts(instance, renderer)
}
)
open class CategoryType2 (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var categoryName: String,
    @JsonNotNull
    open var parentId: Number,
    @JsonNotNull
    open var level: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("CategoryType", "pages/seller/product-edit.uvue", 202, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return CategoryType2ReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class CategoryType2ReactiveObject : CategoryType2, IUTSReactive<CategoryType2> {
    override var __v_raw: CategoryType2
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: CategoryType2, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, categoryName = __v_raw.categoryName, parentId = __v_raw.parentId, level = __v_raw.level) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): CategoryType2ReactiveObject {
        return CategoryType2ReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return trackReactiveGet(__v_raw, "id", __v_raw.id, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            triggerReactiveSet(__v_raw, "id", oldValue, value)
        }
    override var categoryName: String
        get() {
            return trackReactiveGet(__v_raw, "categoryName", __v_raw.categoryName, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("categoryName")) {
                return
            }
            val oldValue = __v_raw.categoryName
            __v_raw.categoryName = value
            triggerReactiveSet(__v_raw, "categoryName", oldValue, value)
        }
    override var parentId: Number
        get() {
            return trackReactiveGet(__v_raw, "parentId", __v_raw.parentId, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("parentId")) {
                return
            }
            val oldValue = __v_raw.parentId
            __v_raw.parentId = value
            triggerReactiveSet(__v_raw, "parentId", oldValue, value)
        }
    override var level: Number
        get() {
            return trackReactiveGet(__v_raw, "level", __v_raw.level, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("level")) {
                return
            }
            val oldValue = __v_raw.level
            __v_raw.level = value
            triggerReactiveSet(__v_raw, "level", oldValue, value)
        }
}
open class SkuFormType (
    @JsonNotNull
    open var skuName: String,
    @JsonNotNull
    open var price: String,
    @JsonNotNull
    open var stock: String,
    @JsonNotNull
    open var specInfo: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("SkuFormType", "pages/seller/product-edit.uvue", 210, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return SkuFormTypeReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class SkuFormTypeReactiveObject : SkuFormType, IUTSReactive<SkuFormType> {
    override var __v_raw: SkuFormType
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: SkuFormType, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(skuName = __v_raw.skuName, price = __v_raw.price, stock = __v_raw.stock, specInfo = __v_raw.specInfo) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): SkuFormTypeReactiveObject {
        return SkuFormTypeReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var skuName: String
        get() {
            return trackReactiveGet(__v_raw, "skuName", __v_raw.skuName, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("skuName")) {
                return
            }
            val oldValue = __v_raw.skuName
            __v_raw.skuName = value
            triggerReactiveSet(__v_raw, "skuName", oldValue, value)
        }
    override var price: String
        get() {
            return trackReactiveGet(__v_raw, "price", __v_raw.price, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("price")) {
                return
            }
            val oldValue = __v_raw.price
            __v_raw.price = value
            triggerReactiveSet(__v_raw, "price", oldValue, value)
        }
    override var stock: String
        get() {
            return trackReactiveGet(__v_raw, "stock", __v_raw.stock, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("stock")) {
                return
            }
            val oldValue = __v_raw.stock
            __v_raw.stock = value
            triggerReactiveSet(__v_raw, "stock", oldValue, value)
        }
    override var specInfo: String
        get() {
            return trackReactiveGet(__v_raw, "specInfo", __v_raw.specInfo, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("specInfo")) {
                return
            }
            val oldValue = __v_raw.specInfo
            __v_raw.specInfo = value
            triggerReactiveSet(__v_raw, "specInfo", oldValue, value)
        }
}
open class FormDataType (
    @JsonNotNull
    open var categoryId: Number,
    @JsonNotNull
    open var productName: String,
    @JsonNotNull
    open var mainImage: String,
    @JsonNotNull
    open var description: String,
    @JsonNotNull
    open var detail: String,
    @JsonNotNull
    open var originalPrice: String,
    @JsonNotNull
    open var currentPrice: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("FormDataType", "pages/seller/product-edit.uvue", 218, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return FormDataTypeReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class FormDataTypeReactiveObject : FormDataType, IUTSReactive<FormDataType> {
    override var __v_raw: FormDataType
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: FormDataType, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(categoryId = __v_raw.categoryId, productName = __v_raw.productName, mainImage = __v_raw.mainImage, description = __v_raw.description, detail = __v_raw.detail, originalPrice = __v_raw.originalPrice, currentPrice = __v_raw.currentPrice) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): FormDataTypeReactiveObject {
        return FormDataTypeReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var categoryId: Number
        get() {
            return trackReactiveGet(__v_raw, "categoryId", __v_raw.categoryId, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("categoryId")) {
                return
            }
            val oldValue = __v_raw.categoryId
            __v_raw.categoryId = value
            triggerReactiveSet(__v_raw, "categoryId", oldValue, value)
        }
    override var productName: String
        get() {
            return trackReactiveGet(__v_raw, "productName", __v_raw.productName, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("productName")) {
                return
            }
            val oldValue = __v_raw.productName
            __v_raw.productName = value
            triggerReactiveSet(__v_raw, "productName", oldValue, value)
        }
    override var mainImage: String
        get() {
            return trackReactiveGet(__v_raw, "mainImage", __v_raw.mainImage, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("mainImage")) {
                return
            }
            val oldValue = __v_raw.mainImage
            __v_raw.mainImage = value
            triggerReactiveSet(__v_raw, "mainImage", oldValue, value)
        }
    override var description: String
        get() {
            return trackReactiveGet(__v_raw, "description", __v_raw.description, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("description")) {
                return
            }
            val oldValue = __v_raw.description
            __v_raw.description = value
            triggerReactiveSet(__v_raw, "description", oldValue, value)
        }
    override var detail: String
        get() {
            return trackReactiveGet(__v_raw, "detail", __v_raw.detail, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("detail")) {
                return
            }
            val oldValue = __v_raw.detail
            __v_raw.detail = value
            triggerReactiveSet(__v_raw, "detail", oldValue, value)
        }
    override var originalPrice: String
        get() {
            return trackReactiveGet(__v_raw, "originalPrice", __v_raw.originalPrice, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("originalPrice")) {
                return
            }
            val oldValue = __v_raw.originalPrice
            __v_raw.originalPrice = value
            triggerReactiveSet(__v_raw, "originalPrice", oldValue, value)
        }
    override var currentPrice: String
        get() {
            return trackReactiveGet(__v_raw, "currentPrice", __v_raw.currentPrice, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("currentPrice")) {
                return
            }
            val oldValue = __v_raw.currentPrice
            __v_raw.currentPrice = value
            triggerReactiveSet(__v_raw, "currentPrice", oldValue, value)
        }
}
val GenPagesSellerProductEditClass = CreateVueComponent(GenPagesSellerProductEdit::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesSellerProductEdit.inheritAttrs, inject = GenPagesSellerProductEdit.inject, props = GenPagesSellerProductEdit.props, propsNeedCastKeys = GenPagesSellerProductEdit.propsNeedCastKeys, emits = GenPagesSellerProductEdit.emits, components = GenPagesSellerProductEdit.components, styles = GenPagesSellerProductEdit.styles)
}
, fun(instance, renderer): GenPagesSellerProductEdit {
    return GenPagesSellerProductEdit(instance, renderer)
}
)
open class InventoryItemType (
    @JsonNotNull
    open var skuId: Number,
    @JsonNotNull
    open var productId: Number,
    @JsonNotNull
    open var productName: String,
    @JsonNotNull
    open var mainImage: String,
    @JsonNotNull
    open var skuName: String,
    @JsonNotNull
    open var price: Number,
    @JsonNotNull
    open var stock: Number,
    @JsonNotNull
    open var sales: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("InventoryItemType", "pages/seller/inventory.uvue", 168, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return InventoryItemTypeReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class InventoryItemTypeReactiveObject : InventoryItemType, IUTSReactive<InventoryItemType> {
    override var __v_raw: InventoryItemType
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: InventoryItemType, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(skuId = __v_raw.skuId, productId = __v_raw.productId, productName = __v_raw.productName, mainImage = __v_raw.mainImage, skuName = __v_raw.skuName, price = __v_raw.price, stock = __v_raw.stock, sales = __v_raw.sales) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): InventoryItemTypeReactiveObject {
        return InventoryItemTypeReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var skuId: Number
        get() {
            return trackReactiveGet(__v_raw, "skuId", __v_raw.skuId, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("skuId")) {
                return
            }
            val oldValue = __v_raw.skuId
            __v_raw.skuId = value
            triggerReactiveSet(__v_raw, "skuId", oldValue, value)
        }
    override var productId: Number
        get() {
            return trackReactiveGet(__v_raw, "productId", __v_raw.productId, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("productId")) {
                return
            }
            val oldValue = __v_raw.productId
            __v_raw.productId = value
            triggerReactiveSet(__v_raw, "productId", oldValue, value)
        }
    override var productName: String
        get() {
            return trackReactiveGet(__v_raw, "productName", __v_raw.productName, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("productName")) {
                return
            }
            val oldValue = __v_raw.productName
            __v_raw.productName = value
            triggerReactiveSet(__v_raw, "productName", oldValue, value)
        }
    override var mainImage: String
        get() {
            return trackReactiveGet(__v_raw, "mainImage", __v_raw.mainImage, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("mainImage")) {
                return
            }
            val oldValue = __v_raw.mainImage
            __v_raw.mainImage = value
            triggerReactiveSet(__v_raw, "mainImage", oldValue, value)
        }
    override var skuName: String
        get() {
            return trackReactiveGet(__v_raw, "skuName", __v_raw.skuName, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("skuName")) {
                return
            }
            val oldValue = __v_raw.skuName
            __v_raw.skuName = value
            triggerReactiveSet(__v_raw, "skuName", oldValue, value)
        }
    override var price: Number
        get() {
            return trackReactiveGet(__v_raw, "price", __v_raw.price, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("price")) {
                return
            }
            val oldValue = __v_raw.price
            __v_raw.price = value
            triggerReactiveSet(__v_raw, "price", oldValue, value)
        }
    override var stock: Number
        get() {
            return trackReactiveGet(__v_raw, "stock", __v_raw.stock, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("stock")) {
                return
            }
            val oldValue = __v_raw.stock
            __v_raw.stock = value
            triggerReactiveSet(__v_raw, "stock", oldValue, value)
        }
    override var sales: Number
        get() {
            return trackReactiveGet(__v_raw, "sales", __v_raw.sales, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("sales")) {
                return
            }
            val oldValue = __v_raw.sales
            __v_raw.sales = value
            triggerReactiveSet(__v_raw, "sales", oldValue, value)
        }
}
val GenPagesSellerInventoryClass = CreateVueComponent(GenPagesSellerInventory::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesSellerInventory.inheritAttrs, inject = GenPagesSellerInventory.inject, props = GenPagesSellerInventory.props, propsNeedCastKeys = GenPagesSellerInventory.propsNeedCastKeys, emits = GenPagesSellerInventory.emits, components = GenPagesSellerInventory.components, styles = GenPagesSellerInventory.styles)
}
, fun(instance, renderer): GenPagesSellerInventory {
    return GenPagesSellerInventory(instance, renderer)
}
)
val GenPagesSellerOrdersClass = CreateVueComponent(GenPagesSellerOrders::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesSellerOrders.inheritAttrs, inject = GenPagesSellerOrders.inject, props = GenPagesSellerOrders.props, propsNeedCastKeys = GenPagesSellerOrders.propsNeedCastKeys, emits = GenPagesSellerOrders.emits, components = GenPagesSellerOrders.components, styles = GenPagesSellerOrders.styles)
}
, fun(instance, renderer): GenPagesSellerOrders {
    return GenPagesSellerOrders(instance, renderer)
}
)
open class SalesDataType (
    @JsonNotNull
    open var totalSales: Number,
    @JsonNotNull
    open var totalOrders: Number,
    @JsonNotNull
    open var totalQuantity: Number,
    @JsonNotNull
    open var avgOrderAmount: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("SalesDataType", "pages/seller/statistics.uvue", 172, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return SalesDataTypeReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class SalesDataTypeReactiveObject : SalesDataType, IUTSReactive<SalesDataType> {
    override var __v_raw: SalesDataType
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: SalesDataType, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(totalSales = __v_raw.totalSales, totalOrders = __v_raw.totalOrders, totalQuantity = __v_raw.totalQuantity, avgOrderAmount = __v_raw.avgOrderAmount) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): SalesDataTypeReactiveObject {
        return SalesDataTypeReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var totalSales: Number
        get() {
            return trackReactiveGet(__v_raw, "totalSales", __v_raw.totalSales, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("totalSales")) {
                return
            }
            val oldValue = __v_raw.totalSales
            __v_raw.totalSales = value
            triggerReactiveSet(__v_raw, "totalSales", oldValue, value)
        }
    override var totalOrders: Number
        get() {
            return trackReactiveGet(__v_raw, "totalOrders", __v_raw.totalOrders, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("totalOrders")) {
                return
            }
            val oldValue = __v_raw.totalOrders
            __v_raw.totalOrders = value
            triggerReactiveSet(__v_raw, "totalOrders", oldValue, value)
        }
    override var totalQuantity: Number
        get() {
            return trackReactiveGet(__v_raw, "totalQuantity", __v_raw.totalQuantity, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("totalQuantity")) {
                return
            }
            val oldValue = __v_raw.totalQuantity
            __v_raw.totalQuantity = value
            triggerReactiveSet(__v_raw, "totalQuantity", oldValue, value)
        }
    override var avgOrderAmount: Number
        get() {
            return trackReactiveGet(__v_raw, "avgOrderAmount", __v_raw.avgOrderAmount, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("avgOrderAmount")) {
                return
            }
            val oldValue = __v_raw.avgOrderAmount
            __v_raw.avgOrderAmount = value
            triggerReactiveSet(__v_raw, "avgOrderAmount", oldValue, value)
        }
}
open class TrendItemType (
    @JsonNotNull
    open var date: String,
    @JsonNotNull
    open var label: String,
    @JsonNotNull
    open var amount: Number,
    @JsonNotNull
    open var orders: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("TrendItemType", "pages/seller/statistics.uvue", 180, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return TrendItemTypeReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class TrendItemTypeReactiveObject : TrendItemType, IUTSReactive<TrendItemType> {
    override var __v_raw: TrendItemType
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: TrendItemType, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(date = __v_raw.date, label = __v_raw.label, amount = __v_raw.amount, orders = __v_raw.orders) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): TrendItemTypeReactiveObject {
        return TrendItemTypeReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var date: String
        get() {
            return trackReactiveGet(__v_raw, "date", __v_raw.date, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("date")) {
                return
            }
            val oldValue = __v_raw.date
            __v_raw.date = value
            triggerReactiveSet(__v_raw, "date", oldValue, value)
        }
    override var label: String
        get() {
            return trackReactiveGet(__v_raw, "label", __v_raw.label, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("label")) {
                return
            }
            val oldValue = __v_raw.label
            __v_raw.label = value
            triggerReactiveSet(__v_raw, "label", oldValue, value)
        }
    override var amount: Number
        get() {
            return trackReactiveGet(__v_raw, "amount", __v_raw.amount, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("amount")) {
                return
            }
            val oldValue = __v_raw.amount
            __v_raw.amount = value
            triggerReactiveSet(__v_raw, "amount", oldValue, value)
        }
    override var orders: Number
        get() {
            return trackReactiveGet(__v_raw, "orders", __v_raw.orders, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("orders")) {
                return
            }
            val oldValue = __v_raw.orders
            __v_raw.orders = value
            triggerReactiveSet(__v_raw, "orders", oldValue, value)
        }
}
open class RankingItemType (
    @JsonNotNull
    open var productId: Number,
    @JsonNotNull
    open var productName: String,
    @JsonNotNull
    open var mainImage: String,
    @JsonNotNull
    open var sales: Number,
    @JsonNotNull
    open var salesAmount: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("RankingItemType", "pages/seller/statistics.uvue", 188, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return RankingItemTypeReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class RankingItemTypeReactiveObject : RankingItemType, IUTSReactive<RankingItemType> {
    override var __v_raw: RankingItemType
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: RankingItemType, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(productId = __v_raw.productId, productName = __v_raw.productName, mainImage = __v_raw.mainImage, sales = __v_raw.sales, salesAmount = __v_raw.salesAmount) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): RankingItemTypeReactiveObject {
        return RankingItemTypeReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var productId: Number
        get() {
            return trackReactiveGet(__v_raw, "productId", __v_raw.productId, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("productId")) {
                return
            }
            val oldValue = __v_raw.productId
            __v_raw.productId = value
            triggerReactiveSet(__v_raw, "productId", oldValue, value)
        }
    override var productName: String
        get() {
            return trackReactiveGet(__v_raw, "productName", __v_raw.productName, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("productName")) {
                return
            }
            val oldValue = __v_raw.productName
            __v_raw.productName = value
            triggerReactiveSet(__v_raw, "productName", oldValue, value)
        }
    override var mainImage: String
        get() {
            return trackReactiveGet(__v_raw, "mainImage", __v_raw.mainImage, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("mainImage")) {
                return
            }
            val oldValue = __v_raw.mainImage
            __v_raw.mainImage = value
            triggerReactiveSet(__v_raw, "mainImage", oldValue, value)
        }
    override var sales: Number
        get() {
            return trackReactiveGet(__v_raw, "sales", __v_raw.sales, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("sales")) {
                return
            }
            val oldValue = __v_raw.sales
            __v_raw.sales = value
            triggerReactiveSet(__v_raw, "sales", oldValue, value)
        }
    override var salesAmount: Number
        get() {
            return trackReactiveGet(__v_raw, "salesAmount", __v_raw.salesAmount, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("salesAmount")) {
                return
            }
            val oldValue = __v_raw.salesAmount
            __v_raw.salesAmount = value
            triggerReactiveSet(__v_raw, "salesAmount", oldValue, value)
        }
}
open class OrderStatusType (
    @JsonNotNull
    open var status: Number,
    @JsonNotNull
    open var statusDesc: String,
    @JsonNotNull
    open var count: Number,
    @JsonNotNull
    open var percentage: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("OrderStatusType", "pages/seller/statistics.uvue", 197, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return OrderStatusTypeReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class OrderStatusTypeReactiveObject : OrderStatusType, IUTSReactive<OrderStatusType> {
    override var __v_raw: OrderStatusType
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: OrderStatusType, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(status = __v_raw.status, statusDesc = __v_raw.statusDesc, count = __v_raw.count, percentage = __v_raw.percentage) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): OrderStatusTypeReactiveObject {
        return OrderStatusTypeReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var status: Number
        get() {
            return trackReactiveGet(__v_raw, "status", __v_raw.status, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("status")) {
                return
            }
            val oldValue = __v_raw.status
            __v_raw.status = value
            triggerReactiveSet(__v_raw, "status", oldValue, value)
        }
    override var statusDesc: String
        get() {
            return trackReactiveGet(__v_raw, "statusDesc", __v_raw.statusDesc, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("statusDesc")) {
                return
            }
            val oldValue = __v_raw.statusDesc
            __v_raw.statusDesc = value
            triggerReactiveSet(__v_raw, "statusDesc", oldValue, value)
        }
    override var count: Number
        get() {
            return trackReactiveGet(__v_raw, "count", __v_raw.count, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("count")) {
                return
            }
            val oldValue = __v_raw.count
            __v_raw.count = value
            triggerReactiveSet(__v_raw, "count", oldValue, value)
        }
    override var percentage: Number
        get() {
            return trackReactiveGet(__v_raw, "percentage", __v_raw.percentage, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("percentage")) {
                return
            }
            val oldValue = __v_raw.percentage
            __v_raw.percentage = value
            triggerReactiveSet(__v_raw, "percentage", oldValue, value)
        }
}
open class InventoryHealthType (
    @JsonNotNull
    open var totalSkuCount: Number,
    @JsonNotNull
    open var lowStockCount: Number,
    @JsonNotNull
    open var outOfStockCount: Number,
    @JsonNotNull
    open var healthyRate: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("InventoryHealthType", "pages/seller/statistics.uvue", 205, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return InventoryHealthTypeReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class InventoryHealthTypeReactiveObject : InventoryHealthType, IUTSReactive<InventoryHealthType> {
    override var __v_raw: InventoryHealthType
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: InventoryHealthType, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(totalSkuCount = __v_raw.totalSkuCount, lowStockCount = __v_raw.lowStockCount, outOfStockCount = __v_raw.outOfStockCount, healthyRate = __v_raw.healthyRate) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): InventoryHealthTypeReactiveObject {
        return InventoryHealthTypeReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var totalSkuCount: Number
        get() {
            return trackReactiveGet(__v_raw, "totalSkuCount", __v_raw.totalSkuCount, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("totalSkuCount")) {
                return
            }
            val oldValue = __v_raw.totalSkuCount
            __v_raw.totalSkuCount = value
            triggerReactiveSet(__v_raw, "totalSkuCount", oldValue, value)
        }
    override var lowStockCount: Number
        get() {
            return trackReactiveGet(__v_raw, "lowStockCount", __v_raw.lowStockCount, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("lowStockCount")) {
                return
            }
            val oldValue = __v_raw.lowStockCount
            __v_raw.lowStockCount = value
            triggerReactiveSet(__v_raw, "lowStockCount", oldValue, value)
        }
    override var outOfStockCount: Number
        get() {
            return trackReactiveGet(__v_raw, "outOfStockCount", __v_raw.outOfStockCount, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("outOfStockCount")) {
                return
            }
            val oldValue = __v_raw.outOfStockCount
            __v_raw.outOfStockCount = value
            triggerReactiveSet(__v_raw, "outOfStockCount", oldValue, value)
        }
    override var healthyRate: Number
        get() {
            return trackReactiveGet(__v_raw, "healthyRate", __v_raw.healthyRate, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("healthyRate")) {
                return
            }
            val oldValue = __v_raw.healthyRate
            __v_raw.healthyRate = value
            triggerReactiveSet(__v_raw, "healthyRate", oldValue, value)
        }
}
open class SkuRankingType (
    @JsonNotNull
    open var skuId: Number,
    @JsonNotNull
    open var skuName: String,
    @JsonNotNull
    open var productName: String,
    @JsonNotNull
    open var salesVolume: Number,
    @JsonNotNull
    open var salesAmount: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("SkuRankingType", "pages/seller/statistics.uvue", 213, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return SkuRankingTypeReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class SkuRankingTypeReactiveObject : SkuRankingType, IUTSReactive<SkuRankingType> {
    override var __v_raw: SkuRankingType
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: SkuRankingType, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(skuId = __v_raw.skuId, skuName = __v_raw.skuName, productName = __v_raw.productName, salesVolume = __v_raw.salesVolume, salesAmount = __v_raw.salesAmount) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): SkuRankingTypeReactiveObject {
        return SkuRankingTypeReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var skuId: Number
        get() {
            return trackReactiveGet(__v_raw, "skuId", __v_raw.skuId, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("skuId")) {
                return
            }
            val oldValue = __v_raw.skuId
            __v_raw.skuId = value
            triggerReactiveSet(__v_raw, "skuId", oldValue, value)
        }
    override var skuName: String
        get() {
            return trackReactiveGet(__v_raw, "skuName", __v_raw.skuName, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("skuName")) {
                return
            }
            val oldValue = __v_raw.skuName
            __v_raw.skuName = value
            triggerReactiveSet(__v_raw, "skuName", oldValue, value)
        }
    override var productName: String
        get() {
            return trackReactiveGet(__v_raw, "productName", __v_raw.productName, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("productName")) {
                return
            }
            val oldValue = __v_raw.productName
            __v_raw.productName = value
            triggerReactiveSet(__v_raw, "productName", oldValue, value)
        }
    override var salesVolume: Number
        get() {
            return trackReactiveGet(__v_raw, "salesVolume", __v_raw.salesVolume, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("salesVolume")) {
                return
            }
            val oldValue = __v_raw.salesVolume
            __v_raw.salesVolume = value
            triggerReactiveSet(__v_raw, "salesVolume", oldValue, value)
        }
    override var salesAmount: Number
        get() {
            return trackReactiveGet(__v_raw, "salesAmount", __v_raw.salesAmount, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("salesAmount")) {
                return
            }
            val oldValue = __v_raw.salesAmount
            __v_raw.salesAmount = value
            triggerReactiveSet(__v_raw, "salesAmount", oldValue, value)
        }
}
val GenPagesSellerStatisticsClass = CreateVueComponent(GenPagesSellerStatistics::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesSellerStatistics.inheritAttrs, inject = GenPagesSellerStatistics.inject, props = GenPagesSellerStatistics.props, propsNeedCastKeys = GenPagesSellerStatistics.propsNeedCastKeys, emits = GenPagesSellerStatistics.emits, components = GenPagesSellerStatistics.components, styles = GenPagesSellerStatistics.styles)
}
, fun(instance, renderer): GenPagesSellerStatistics {
    return GenPagesSellerStatistics(instance, renderer)
}
)
open class AccountInfoType (
    @JsonNotNull
    open var availableBalance: Number,
    @JsonNotNull
    open var frozenAmount: Number,
    @JsonNotNull
    open var totalIncome: Number,
    @JsonNotNull
    open var totalWithdraw: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("AccountInfoType", "pages/seller/account.uvue", 138, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return AccountInfoTypeReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class AccountInfoTypeReactiveObject : AccountInfoType, IUTSReactive<AccountInfoType> {
    override var __v_raw: AccountInfoType
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: AccountInfoType, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(availableBalance = __v_raw.availableBalance, frozenAmount = __v_raw.frozenAmount, totalIncome = __v_raw.totalIncome, totalWithdraw = __v_raw.totalWithdraw) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): AccountInfoTypeReactiveObject {
        return AccountInfoTypeReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var availableBalance: Number
        get() {
            return trackReactiveGet(__v_raw, "availableBalance", __v_raw.availableBalance, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("availableBalance")) {
                return
            }
            val oldValue = __v_raw.availableBalance
            __v_raw.availableBalance = value
            triggerReactiveSet(__v_raw, "availableBalance", oldValue, value)
        }
    override var frozenAmount: Number
        get() {
            return trackReactiveGet(__v_raw, "frozenAmount", __v_raw.frozenAmount, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("frozenAmount")) {
                return
            }
            val oldValue = __v_raw.frozenAmount
            __v_raw.frozenAmount = value
            triggerReactiveSet(__v_raw, "frozenAmount", oldValue, value)
        }
    override var totalIncome: Number
        get() {
            return trackReactiveGet(__v_raw, "totalIncome", __v_raw.totalIncome, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("totalIncome")) {
                return
            }
            val oldValue = __v_raw.totalIncome
            __v_raw.totalIncome = value
            triggerReactiveSet(__v_raw, "totalIncome", oldValue, value)
        }
    override var totalWithdraw: Number
        get() {
            return trackReactiveGet(__v_raw, "totalWithdraw", __v_raw.totalWithdraw, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("totalWithdraw")) {
                return
            }
            val oldValue = __v_raw.totalWithdraw
            __v_raw.totalWithdraw = value
            triggerReactiveSet(__v_raw, "totalWithdraw", oldValue, value)
        }
}
open class TransactionItemType (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var transactionNo: String,
    @JsonNotNull
    open var type: String,
    @JsonNotNull
    open var typeDesc: String,
    @JsonNotNull
    open var amount: Number,
    @JsonNotNull
    open var balanceAfter: Number,
    @JsonNotNull
    open var relatedOrderNo: String,
    @JsonNotNull
    open var description: String,
    @JsonNotNull
    open var createTime: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("TransactionItemType", "pages/seller/account.uvue", 146, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return TransactionItemTypeReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class TransactionItemTypeReactiveObject : TransactionItemType, IUTSReactive<TransactionItemType> {
    override var __v_raw: TransactionItemType
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: TransactionItemType, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, transactionNo = __v_raw.transactionNo, type = __v_raw.type, typeDesc = __v_raw.typeDesc, amount = __v_raw.amount, balanceAfter = __v_raw.balanceAfter, relatedOrderNo = __v_raw.relatedOrderNo, description = __v_raw.description, createTime = __v_raw.createTime) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): TransactionItemTypeReactiveObject {
        return TransactionItemTypeReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return trackReactiveGet(__v_raw, "id", __v_raw.id, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            triggerReactiveSet(__v_raw, "id", oldValue, value)
        }
    override var transactionNo: String
        get() {
            return trackReactiveGet(__v_raw, "transactionNo", __v_raw.transactionNo, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("transactionNo")) {
                return
            }
            val oldValue = __v_raw.transactionNo
            __v_raw.transactionNo = value
            triggerReactiveSet(__v_raw, "transactionNo", oldValue, value)
        }
    override var type: String
        get() {
            return trackReactiveGet(__v_raw, "type", __v_raw.type, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("type")) {
                return
            }
            val oldValue = __v_raw.type
            __v_raw.type = value
            triggerReactiveSet(__v_raw, "type", oldValue, value)
        }
    override var typeDesc: String
        get() {
            return trackReactiveGet(__v_raw, "typeDesc", __v_raw.typeDesc, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("typeDesc")) {
                return
            }
            val oldValue = __v_raw.typeDesc
            __v_raw.typeDesc = value
            triggerReactiveSet(__v_raw, "typeDesc", oldValue, value)
        }
    override var amount: Number
        get() {
            return trackReactiveGet(__v_raw, "amount", __v_raw.amount, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("amount")) {
                return
            }
            val oldValue = __v_raw.amount
            __v_raw.amount = value
            triggerReactiveSet(__v_raw, "amount", oldValue, value)
        }
    override var balanceAfter: Number
        get() {
            return trackReactiveGet(__v_raw, "balanceAfter", __v_raw.balanceAfter, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("balanceAfter")) {
                return
            }
            val oldValue = __v_raw.balanceAfter
            __v_raw.balanceAfter = value
            triggerReactiveSet(__v_raw, "balanceAfter", oldValue, value)
        }
    override var relatedOrderNo: String
        get() {
            return trackReactiveGet(__v_raw, "relatedOrderNo", __v_raw.relatedOrderNo, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("relatedOrderNo")) {
                return
            }
            val oldValue = __v_raw.relatedOrderNo
            __v_raw.relatedOrderNo = value
            triggerReactiveSet(__v_raw, "relatedOrderNo", oldValue, value)
        }
    override var description: String
        get() {
            return trackReactiveGet(__v_raw, "description", __v_raw.description, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("description")) {
                return
            }
            val oldValue = __v_raw.description
            __v_raw.description = value
            triggerReactiveSet(__v_raw, "description", oldValue, value)
        }
    override var createTime: String
        get() {
            return trackReactiveGet(__v_raw, "createTime", __v_raw.createTime, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("createTime")) {
                return
            }
            val oldValue = __v_raw.createTime
            __v_raw.createTime = value
            triggerReactiveSet(__v_raw, "createTime", oldValue, value)
        }
}
val GenPagesSellerAccountClass = CreateVueComponent(GenPagesSellerAccount::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesSellerAccount.inheritAttrs, inject = GenPagesSellerAccount.inject, props = GenPagesSellerAccount.props, propsNeedCastKeys = GenPagesSellerAccount.propsNeedCastKeys, emits = GenPagesSellerAccount.emits, components = GenPagesSellerAccount.components, styles = GenPagesSellerAccount.styles)
}
, fun(instance, renderer): GenPagesSellerAccount {
    return GenPagesSellerAccount(instance, renderer)
}
)
val GenPagesSellerReviewsClass = CreateVueComponent(GenPagesSellerReviews::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesSellerReviews.inheritAttrs, inject = GenPagesSellerReviews.inject, props = GenPagesSellerReviews.props, propsNeedCastKeys = GenPagesSellerReviews.propsNeedCastKeys, emits = GenPagesSellerReviews.emits, components = GenPagesSellerReviews.components, styles = GenPagesSellerReviews.styles)
}
, fun(instance, renderer): GenPagesSellerReviews {
    return GenPagesSellerReviews(instance, renderer)
}
)
fun getAdminReviews(pageNum: Number, pageSize: Number, keyword: String, status: Number = -1, rating: Number = 0): UTSPromise<ResponseDataType> {
    var url = "/admin/reviews?pageNum=" + pageNum + "&pageSize=" + pageSize
    if (keyword != "") {
        url += "&keyword=" + UTSAndroid.consoleDebugError(encodeURIComponent(keyword), " at api/admin.uts:8")
    }
    if (status == 0 || status == 1) {
        url += "&status=" + status
    }
    if (rating >= 1 && rating <= 5) {
        url += "&rating=" + rating
    }
    return get(url)
}
fun getAdminReviewStatistics(): UTSPromise<ResponseDataType> {
    return get("/admin/reviews/statistics")
}
fun deleteReview(reviewId: Number): UTSPromise<ResponseDataType> {
    return del("/admin/reviews/" + reviewId)
}
fun updateReviewStatus(reviewId: Number, status: Number): UTSPromise<ResponseDataType> {
    return put("/admin/reviews/" + reviewId + "/status?status=" + status, null)
}
fun getAdminUsers(pageNum: Number, pageSize: Number, keyword: String): UTSPromise<ResponseDataType> {
    var url = "/admin/users?pageNum=" + pageNum + "&pageSize=" + pageSize
    if (keyword != "") {
        url += "&keyword=" + keyword
    }
    return get(url)
}
fun getAdminUserDetail(userId: Number): UTSPromise<ResponseDataType> {
    return get("/admin/users/" + userId)
}
fun updateUserStatus(userId: Number, status: Number): UTSPromise<ResponseDataType> {
    return put("/admin/users/" + userId + "/status?status=" + status, null)
}
fun getAdminOverview(): UTSPromise<ResponseDataType> {
    return get("/admin/statistics/overview")
}
fun getProductDistribution(): UTSPromise<ResponseDataType> {
    return get("/admin/statistics/product-distribution")
}
fun getOrderDistribution(): UTSPromise<ResponseDataType> {
    return get("/admin/statistics/order-distribution")
}
fun syncProductVectors(): UTSPromise<ResponseDataType> {
    return post("/admin/products/sync-vectors", null)
}
val GenPagesAdminReviewsClass = CreateVueComponent(GenPagesAdminReviews::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesAdminReviews.inheritAttrs, inject = GenPagesAdminReviews.inject, props = GenPagesAdminReviews.props, propsNeedCastKeys = GenPagesAdminReviews.propsNeedCastKeys, emits = GenPagesAdminReviews.emits, components = GenPagesAdminReviews.components, styles = GenPagesAdminReviews.styles)
}
, fun(instance, renderer): GenPagesAdminReviews {
    return GenPagesAdminReviews(instance, renderer)
}
)
val GenPagesSellerAdminIndexClass = CreateVueComponent(GenPagesSellerAdminIndex::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesSellerAdminIndex.inheritAttrs, inject = GenPagesSellerAdminIndex.inject, props = GenPagesSellerAdminIndex.props, propsNeedCastKeys = GenPagesSellerAdminIndex.propsNeedCastKeys, emits = GenPagesSellerAdminIndex.emits, components = GenPagesSellerAdminIndex.components, styles = GenPagesSellerAdminIndex.styles)
}
, fun(instance, renderer): GenPagesSellerAdminIndex {
    return GenPagesSellerAdminIndex(instance, renderer)
}
)
open class AdminProductType (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var productName: String,
    @JsonNotNull
    open var productCode: String,
    @JsonNotNull
    open var mainImage: String,
    @JsonNotNull
    open var currentPrice: Number,
    @JsonNotNull
    open var stock: Number,
    @JsonNotNull
    open var salesVolume: Number,
    @JsonNotNull
    open var status: Number,
    @JsonNotNull
    open var createTime: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("AdminProductType", "pages/seller/product-audit.uvue", 85, 7)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return AdminProductTypeReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class AdminProductTypeReactiveObject : AdminProductType, IUTSReactive<AdminProductType> {
    override var __v_raw: AdminProductType
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: AdminProductType, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, productName = __v_raw.productName, productCode = __v_raw.productCode, mainImage = __v_raw.mainImage, currentPrice = __v_raw.currentPrice, stock = __v_raw.stock, salesVolume = __v_raw.salesVolume, status = __v_raw.status, createTime = __v_raw.createTime) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): AdminProductTypeReactiveObject {
        return AdminProductTypeReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return trackReactiveGet(__v_raw, "id", __v_raw.id, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            triggerReactiveSet(__v_raw, "id", oldValue, value)
        }
    override var productName: String
        get() {
            return trackReactiveGet(__v_raw, "productName", __v_raw.productName, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("productName")) {
                return
            }
            val oldValue = __v_raw.productName
            __v_raw.productName = value
            triggerReactiveSet(__v_raw, "productName", oldValue, value)
        }
    override var productCode: String
        get() {
            return trackReactiveGet(__v_raw, "productCode", __v_raw.productCode, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("productCode")) {
                return
            }
            val oldValue = __v_raw.productCode
            __v_raw.productCode = value
            triggerReactiveSet(__v_raw, "productCode", oldValue, value)
        }
    override var mainImage: String
        get() {
            return trackReactiveGet(__v_raw, "mainImage", __v_raw.mainImage, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("mainImage")) {
                return
            }
            val oldValue = __v_raw.mainImage
            __v_raw.mainImage = value
            triggerReactiveSet(__v_raw, "mainImage", oldValue, value)
        }
    override var currentPrice: Number
        get() {
            return trackReactiveGet(__v_raw, "currentPrice", __v_raw.currentPrice, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("currentPrice")) {
                return
            }
            val oldValue = __v_raw.currentPrice
            __v_raw.currentPrice = value
            triggerReactiveSet(__v_raw, "currentPrice", oldValue, value)
        }
    override var stock: Number
        get() {
            return trackReactiveGet(__v_raw, "stock", __v_raw.stock, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("stock")) {
                return
            }
            val oldValue = __v_raw.stock
            __v_raw.stock = value
            triggerReactiveSet(__v_raw, "stock", oldValue, value)
        }
    override var salesVolume: Number
        get() {
            return trackReactiveGet(__v_raw, "salesVolume", __v_raw.salesVolume, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("salesVolume")) {
                return
            }
            val oldValue = __v_raw.salesVolume
            __v_raw.salesVolume = value
            triggerReactiveSet(__v_raw, "salesVolume", oldValue, value)
        }
    override var status: Number
        get() {
            return trackReactiveGet(__v_raw, "status", __v_raw.status, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("status")) {
                return
            }
            val oldValue = __v_raw.status
            __v_raw.status = value
            triggerReactiveSet(__v_raw, "status", oldValue, value)
        }
    override var createTime: String
        get() {
            return trackReactiveGet(__v_raw, "createTime", __v_raw.createTime, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("createTime")) {
                return
            }
            val oldValue = __v_raw.createTime
            __v_raw.createTime = value
            triggerReactiveSet(__v_raw, "createTime", oldValue, value)
        }
}
val GenPagesSellerProductAuditClass = CreateVueComponent(GenPagesSellerProductAudit::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesSellerProductAudit.inheritAttrs, inject = GenPagesSellerProductAudit.inject, props = GenPagesSellerProductAudit.props, propsNeedCastKeys = GenPagesSellerProductAudit.propsNeedCastKeys, emits = GenPagesSellerProductAudit.emits, components = GenPagesSellerProductAudit.components, styles = GenPagesSellerProductAudit.styles)
}
, fun(instance, renderer): GenPagesSellerProductAudit {
    return GenPagesSellerProductAudit(instance, renderer)
}
)
open class UserType (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var username: String,
    @JsonNotNull
    open var nickname: String,
    @JsonNotNull
    open var phone: String,
    @JsonNotNull
    open var email: String,
    @JsonNotNull
    open var avatar: String,
    @JsonNotNull
    open var gender: Number,
    @JsonNotNull
    open var status: Number,
    @JsonNotNull
    open var createTime: String,
    @JsonNotNull
    open var updateTime: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("UserType", "pages/seller/admin-users.uvue", 64, 7)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return UserTypeReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class UserTypeReactiveObject : UserType, IUTSReactive<UserType> {
    override var __v_raw: UserType
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: UserType, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, username = __v_raw.username, nickname = __v_raw.nickname, phone = __v_raw.phone, email = __v_raw.email, avatar = __v_raw.avatar, gender = __v_raw.gender, status = __v_raw.status, createTime = __v_raw.createTime, updateTime = __v_raw.updateTime) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UserTypeReactiveObject {
        return UserTypeReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return trackReactiveGet(__v_raw, "id", __v_raw.id, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            triggerReactiveSet(__v_raw, "id", oldValue, value)
        }
    override var username: String
        get() {
            return trackReactiveGet(__v_raw, "username", __v_raw.username, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("username")) {
                return
            }
            val oldValue = __v_raw.username
            __v_raw.username = value
            triggerReactiveSet(__v_raw, "username", oldValue, value)
        }
    override var nickname: String
        get() {
            return trackReactiveGet(__v_raw, "nickname", __v_raw.nickname, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("nickname")) {
                return
            }
            val oldValue = __v_raw.nickname
            __v_raw.nickname = value
            triggerReactiveSet(__v_raw, "nickname", oldValue, value)
        }
    override var phone: String
        get() {
            return trackReactiveGet(__v_raw, "phone", __v_raw.phone, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("phone")) {
                return
            }
            val oldValue = __v_raw.phone
            __v_raw.phone = value
            triggerReactiveSet(__v_raw, "phone", oldValue, value)
        }
    override var email: String
        get() {
            return trackReactiveGet(__v_raw, "email", __v_raw.email, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("email")) {
                return
            }
            val oldValue = __v_raw.email
            __v_raw.email = value
            triggerReactiveSet(__v_raw, "email", oldValue, value)
        }
    override var avatar: String
        get() {
            return trackReactiveGet(__v_raw, "avatar", __v_raw.avatar, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("avatar")) {
                return
            }
            val oldValue = __v_raw.avatar
            __v_raw.avatar = value
            triggerReactiveSet(__v_raw, "avatar", oldValue, value)
        }
    override var gender: Number
        get() {
            return trackReactiveGet(__v_raw, "gender", __v_raw.gender, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("gender")) {
                return
            }
            val oldValue = __v_raw.gender
            __v_raw.gender = value
            triggerReactiveSet(__v_raw, "gender", oldValue, value)
        }
    override var status: Number
        get() {
            return trackReactiveGet(__v_raw, "status", __v_raw.status, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("status")) {
                return
            }
            val oldValue = __v_raw.status
            __v_raw.status = value
            triggerReactiveSet(__v_raw, "status", oldValue, value)
        }
    override var createTime: String
        get() {
            return trackReactiveGet(__v_raw, "createTime", __v_raw.createTime, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("createTime")) {
                return
            }
            val oldValue = __v_raw.createTime
            __v_raw.createTime = value
            triggerReactiveSet(__v_raw, "createTime", oldValue, value)
        }
    override var updateTime: String
        get() {
            return trackReactiveGet(__v_raw, "updateTime", __v_raw.updateTime, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("updateTime")) {
                return
            }
            val oldValue = __v_raw.updateTime
            __v_raw.updateTime = value
            triggerReactiveSet(__v_raw, "updateTime", oldValue, value)
        }
}
val GenPagesSellerAdminUsersClass = CreateVueComponent(GenPagesSellerAdminUsers::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesSellerAdminUsers.inheritAttrs, inject = GenPagesSellerAdminUsers.inject, props = GenPagesSellerAdminUsers.props, propsNeedCastKeys = GenPagesSellerAdminUsers.propsNeedCastKeys, emits = GenPagesSellerAdminUsers.emits, components = GenPagesSellerAdminUsers.components, styles = GenPagesSellerAdminUsers.styles)
}
, fun(instance, renderer): GenPagesSellerAdminUsers {
    return GenPagesSellerAdminUsers(instance, renderer)
}
)
open class OverviewType (
    @JsonNotNull
    open var totalUsers: Number,
    @JsonNotNull
    open var activeUsers: Number,
    @JsonNotNull
    open var totalProducts: Number,
    @JsonNotNull
    open var pendingProducts: Number,
    @JsonNotNull
    open var onlineProducts: Number,
    @JsonNotNull
    open var totalOrders: Number,
    @JsonNotNull
    open var completedOrders: Number,
    @JsonNotNull
    open var pendingOrders: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("OverviewType", "pages/seller/admin-statistics.uvue", 204, 7)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return OverviewTypeReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class OverviewTypeReactiveObject : OverviewType, IUTSReactive<OverviewType> {
    override var __v_raw: OverviewType
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: OverviewType, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(totalUsers = __v_raw.totalUsers, activeUsers = __v_raw.activeUsers, totalProducts = __v_raw.totalProducts, pendingProducts = __v_raw.pendingProducts, onlineProducts = __v_raw.onlineProducts, totalOrders = __v_raw.totalOrders, completedOrders = __v_raw.completedOrders, pendingOrders = __v_raw.pendingOrders) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): OverviewTypeReactiveObject {
        return OverviewTypeReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var totalUsers: Number
        get() {
            return trackReactiveGet(__v_raw, "totalUsers", __v_raw.totalUsers, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("totalUsers")) {
                return
            }
            val oldValue = __v_raw.totalUsers
            __v_raw.totalUsers = value
            triggerReactiveSet(__v_raw, "totalUsers", oldValue, value)
        }
    override var activeUsers: Number
        get() {
            return trackReactiveGet(__v_raw, "activeUsers", __v_raw.activeUsers, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("activeUsers")) {
                return
            }
            val oldValue = __v_raw.activeUsers
            __v_raw.activeUsers = value
            triggerReactiveSet(__v_raw, "activeUsers", oldValue, value)
        }
    override var totalProducts: Number
        get() {
            return trackReactiveGet(__v_raw, "totalProducts", __v_raw.totalProducts, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("totalProducts")) {
                return
            }
            val oldValue = __v_raw.totalProducts
            __v_raw.totalProducts = value
            triggerReactiveSet(__v_raw, "totalProducts", oldValue, value)
        }
    override var pendingProducts: Number
        get() {
            return trackReactiveGet(__v_raw, "pendingProducts", __v_raw.pendingProducts, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("pendingProducts")) {
                return
            }
            val oldValue = __v_raw.pendingProducts
            __v_raw.pendingProducts = value
            triggerReactiveSet(__v_raw, "pendingProducts", oldValue, value)
        }
    override var onlineProducts: Number
        get() {
            return trackReactiveGet(__v_raw, "onlineProducts", __v_raw.onlineProducts, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("onlineProducts")) {
                return
            }
            val oldValue = __v_raw.onlineProducts
            __v_raw.onlineProducts = value
            triggerReactiveSet(__v_raw, "onlineProducts", oldValue, value)
        }
    override var totalOrders: Number
        get() {
            return trackReactiveGet(__v_raw, "totalOrders", __v_raw.totalOrders, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("totalOrders")) {
                return
            }
            val oldValue = __v_raw.totalOrders
            __v_raw.totalOrders = value
            triggerReactiveSet(__v_raw, "totalOrders", oldValue, value)
        }
    override var completedOrders: Number
        get() {
            return trackReactiveGet(__v_raw, "completedOrders", __v_raw.completedOrders, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("completedOrders")) {
                return
            }
            val oldValue = __v_raw.completedOrders
            __v_raw.completedOrders = value
            triggerReactiveSet(__v_raw, "completedOrders", oldValue, value)
        }
    override var pendingOrders: Number
        get() {
            return trackReactiveGet(__v_raw, "pendingOrders", __v_raw.pendingOrders, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("pendingOrders")) {
                return
            }
            val oldValue = __v_raw.pendingOrders
            __v_raw.pendingOrders = value
            triggerReactiveSet(__v_raw, "pendingOrders", oldValue, value)
        }
}
open class ProductDistType (
    @JsonNotNull
    open var pending: Number,
    @JsonNotNull
    open var online: Number,
    @JsonNotNull
    open var offline: Number,
    @JsonNotNull
    open var rejected: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ProductDistType", "pages/seller/admin-statistics.uvue", 215, 7)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ProductDistTypeReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class ProductDistTypeReactiveObject : ProductDistType, IUTSReactive<ProductDistType> {
    override var __v_raw: ProductDistType
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ProductDistType, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(pending = __v_raw.pending, online = __v_raw.online, offline = __v_raw.offline, rejected = __v_raw.rejected) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ProductDistTypeReactiveObject {
        return ProductDistTypeReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var pending: Number
        get() {
            return trackReactiveGet(__v_raw, "pending", __v_raw.pending, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("pending")) {
                return
            }
            val oldValue = __v_raw.pending
            __v_raw.pending = value
            triggerReactiveSet(__v_raw, "pending", oldValue, value)
        }
    override var online: Number
        get() {
            return trackReactiveGet(__v_raw, "online", __v_raw.online, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("online")) {
                return
            }
            val oldValue = __v_raw.online
            __v_raw.online = value
            triggerReactiveSet(__v_raw, "online", oldValue, value)
        }
    override var offline: Number
        get() {
            return trackReactiveGet(__v_raw, "offline", __v_raw.offline, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("offline")) {
                return
            }
            val oldValue = __v_raw.offline
            __v_raw.offline = value
            triggerReactiveSet(__v_raw, "offline", oldValue, value)
        }
    override var rejected: Number
        get() {
            return trackReactiveGet(__v_raw, "rejected", __v_raw.rejected, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("rejected")) {
                return
            }
            val oldValue = __v_raw.rejected
            __v_raw.rejected = value
            triggerReactiveSet(__v_raw, "rejected", oldValue, value)
        }
}
open class OrderDistType (
    @JsonNotNull
    open var unpaid: Number,
    @JsonNotNull
    open var unshipped: Number,
    @JsonNotNull
    open var shipped: Number,
    @JsonNotNull
    open var completed: Number,
    @JsonNotNull
    open var cancelled: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("OrderDistType", "pages/seller/admin-statistics.uvue", 222, 7)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return OrderDistTypeReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class OrderDistTypeReactiveObject : OrderDistType, IUTSReactive<OrderDistType> {
    override var __v_raw: OrderDistType
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: OrderDistType, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(unpaid = __v_raw.unpaid, unshipped = __v_raw.unshipped, shipped = __v_raw.shipped, completed = __v_raw.completed, cancelled = __v_raw.cancelled) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): OrderDistTypeReactiveObject {
        return OrderDistTypeReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var unpaid: Number
        get() {
            return trackReactiveGet(__v_raw, "unpaid", __v_raw.unpaid, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("unpaid")) {
                return
            }
            val oldValue = __v_raw.unpaid
            __v_raw.unpaid = value
            triggerReactiveSet(__v_raw, "unpaid", oldValue, value)
        }
    override var unshipped: Number
        get() {
            return trackReactiveGet(__v_raw, "unshipped", __v_raw.unshipped, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("unshipped")) {
                return
            }
            val oldValue = __v_raw.unshipped
            __v_raw.unshipped = value
            triggerReactiveSet(__v_raw, "unshipped", oldValue, value)
        }
    override var shipped: Number
        get() {
            return trackReactiveGet(__v_raw, "shipped", __v_raw.shipped, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("shipped")) {
                return
            }
            val oldValue = __v_raw.shipped
            __v_raw.shipped = value
            triggerReactiveSet(__v_raw, "shipped", oldValue, value)
        }
    override var completed: Number
        get() {
            return trackReactiveGet(__v_raw, "completed", __v_raw.completed, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("completed")) {
                return
            }
            val oldValue = __v_raw.completed
            __v_raw.completed = value
            triggerReactiveSet(__v_raw, "completed", oldValue, value)
        }
    override var cancelled: Number
        get() {
            return trackReactiveGet(__v_raw, "cancelled", __v_raw.cancelled, this.__v_isReadonly, this.__v_isShallow)
        }
        set(value) {
            if (!this.__v_canSet("cancelled")) {
                return
            }
            val oldValue = __v_raw.cancelled
            __v_raw.cancelled = value
            triggerReactiveSet(__v_raw, "cancelled", oldValue, value)
        }
}
val GenPagesSellerAdminStatisticsClass = CreateVueComponent(GenPagesSellerAdminStatistics::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesSellerAdminStatistics.inheritAttrs, inject = GenPagesSellerAdminStatistics.inject, props = GenPagesSellerAdminStatistics.props, propsNeedCastKeys = GenPagesSellerAdminStatistics.propsNeedCastKeys, emits = GenPagesSellerAdminStatistics.emits, components = GenPagesSellerAdminStatistics.components, styles = GenPagesSellerAdminStatistics.styles)
}
, fun(instance, renderer): GenPagesSellerAdminStatistics {
    return GenPagesSellerAdminStatistics(instance, renderer)
}
)
fun createApp(): UTSJSONObject {
    val app = createSSRApp(GenAppClass)
    return UTSJSONObject(Map<String, Any?>(utsArrayOf(
        utsArrayOf(
            "app",
            app
        )
    )))
}
fun main(app: IApp) {
    definePageRoutes()
    defineAppConfig()
    (createApp()["app"] as VueApp).mount(app, GenUniApp())
}
open class UniAppConfig : io.dcloud.uniapp.appframe.AppConfig {
    override var name: String = "by"
    override var appid: String = "__UNI__BY001"
    override var versionName: String = "1.0.0"
    override var versionCode: String = "100"
    override var uniCompilerVersion: String = "4.66"
    constructor() : super() {}
}
fun definePageRoutes() {
    __uniRoutes.push(UniPageRoute(path = "pages/index/index", component = GenPagesIndexIndexClass, meta = UniPageMeta(isQuit = true), style = utsMapOf("navigationBarTitleText" to "", "navigationStyle" to "custom")))
    __uniRoutes.push(UniPageRoute(path = "pages/search/index", component = GenPagesSearchIndexClass, meta = UniPageMeta(isQuit = false), style = utsMapOf("navigationBarTitleText" to "搜索", "navigationStyle" to "custom")))
    __uniRoutes.push(UniPageRoute(path = "pages/login/login", component = GenPagesLoginLoginClass, meta = UniPageMeta(isQuit = false), style = utsMapOf("navigationBarTitleText" to "", "navigationStyle" to "custom")))
    __uniRoutes.push(UniPageRoute(path = "pages/register/register", component = GenPagesRegisterRegisterClass, meta = UniPageMeta(isQuit = false), style = utsMapOf("navigationBarTitleText" to "", "navigationStyle" to "custom")))
    __uniRoutes.push(UniPageRoute(path = "pages/product/list", component = GenPagesProductListClass, meta = UniPageMeta(isQuit = false), style = utsMapOf("navigationBarTitleText" to "商品分类", "navigationBarBackgroundColor" to "#0066CC", "navigationBarTextStyle" to "white")))
    __uniRoutes.push(UniPageRoute(path = "pages/product/detail", component = GenPagesProductDetailClass, meta = UniPageMeta(isQuit = false), style = utsMapOf("navigationBarTitleText" to "商品详情", "navigationBarBackgroundColor" to "#0066CC", "navigationBarTextStyle" to "white")))
    __uniRoutes.push(UniPageRoute(path = "pages/product/reviews", component = GenPagesProductReviewsClass, meta = UniPageMeta(isQuit = false), style = utsMapOf("navigationBarTitleText" to "商品评价", "navigationBarBackgroundColor" to "#0066CC", "navigationBarTextStyle" to "white")))
    __uniRoutes.push(UniPageRoute(path = "pages/cart/index", component = GenPagesCartIndexClass, meta = UniPageMeta(isQuit = false), style = utsMapOf("navigationBarTitleText" to "购物车", "navigationBarBackgroundColor" to "#0066CC", "navigationBarTextStyle" to "white")))
    __uniRoutes.push(UniPageRoute(path = "pages/order/list", component = GenPagesOrderListClass, meta = UniPageMeta(isQuit = false), style = utsMapOf("navigationBarTitleText" to "我的订单", "navigationBarBackgroundColor" to "#0066CC", "navigationBarTextStyle" to "white")))
    __uniRoutes.push(UniPageRoute(path = "pages/order/detail", component = GenPagesOrderDetailClass, meta = UniPageMeta(isQuit = false), style = utsMapOf("navigationBarTitleText" to "订单详情", "navigationBarBackgroundColor" to "#0066CC", "navigationBarTextStyle" to "white")))
    __uniRoutes.push(UniPageRoute(path = "pages/order/checkout", component = GenPagesOrderCheckoutClass, meta = UniPageMeta(isQuit = false), style = utsMapOf("navigationBarTitleText" to "确认订单", "navigationStyle" to "custom")))
    __uniRoutes.push(UniPageRoute(path = "pages/order/review", component = GenPagesOrderReviewClass, meta = UniPageMeta(isQuit = false), style = utsMapOf("navigationBarTitleText" to "发表评价", "navigationStyle" to "custom")))
    __uniRoutes.push(UniPageRoute(path = "pages/order/pay", component = GenPagesOrderPayClass, meta = UniPageMeta(isQuit = false), style = utsMapOf("navigationBarTitleText" to "订单支付", "navigationStyle" to "custom")))
    __uniRoutes.push(UniPageRoute(path = "pages/product/favorites", component = GenPagesProductFavoritesClass, meta = UniPageMeta(isQuit = false), style = utsMapOf("navigationBarTitleText" to "我的收藏", "navigationBarBackgroundColor" to "#0066CC", "navigationBarTextStyle" to "white")))
    __uniRoutes.push(UniPageRoute(path = "pages/product/history", component = GenPagesProductHistoryClass, meta = UniPageMeta(isQuit = false), style = utsMapOf("navigationBarTitleText" to "浏览历史", "navigationBarBackgroundColor" to "#0066CC", "navigationBarTextStyle" to "white")))
    __uniRoutes.push(UniPageRoute(path = "pages/user/profile", component = GenPagesUserProfileClass, meta = UniPageMeta(isQuit = false), style = utsMapOf("navigationBarTitleText" to "", "navigationStyle" to "custom")))
    __uniRoutes.push(UniPageRoute(path = "pages/user/edit-profile", component = GenPagesUserEditProfileClass, meta = UniPageMeta(isQuit = false), style = utsMapOf("navigationBarTitleText" to "编辑资料", "navigationBarBackgroundColor" to "#0066CC", "navigationBarTextStyle" to "white")))
    __uniRoutes.push(UniPageRoute(path = "pages/user/change-password", component = GenPagesUserChangePasswordClass, meta = UniPageMeta(isQuit = false), style = utsMapOf("navigationBarTitleText" to "修改密码", "navigationBarBackgroundColor" to "#0066CC", "navigationBarTextStyle" to "white")))
    __uniRoutes.push(UniPageRoute(path = "pages/user/address-edit", component = GenPagesUserAddressEditClass, meta = UniPageMeta(isQuit = false), style = utsMapOf("navigationBarTitleText" to "编辑地址", "navigationBarBackgroundColor" to "#0066CC", "navigationBarTextStyle" to "white")))
    __uniRoutes.push(UniPageRoute(path = "pages/user/settings", component = GenPagesUserSettingsClass, meta = UniPageMeta(isQuit = false), style = utsMapOf("navigationBarTitleText" to "设置", "navigationBarBackgroundColor" to "#0066CC", "navigationBarTextStyle" to "white")))
    __uniRoutes.push(UniPageRoute(path = "pages/user/address-list", component = GenPagesUserAddressListClass, meta = UniPageMeta(isQuit = false), style = utsMapOf("navigationBarTitleText" to "地址管理", "navigationStyle" to "custom")))
    __uniRoutes.push(UniPageRoute(path = "pages/user/reviews", component = GenPagesUserReviewsClass, meta = UniPageMeta(isQuit = false), style = utsMapOf("navigationBarTitleText" to "我的评价", "navigationStyle" to "custom")))
    __uniRoutes.push(UniPageRoute(path = "pages/user/wallet", component = GenPagesUserWalletClass, meta = UniPageMeta(isQuit = false), style = utsMapOf("navigationBarTitleText" to "我的钱包", "navigationStyle" to "custom")))
    __uniRoutes.push(UniPageRoute(path = "pages/service/chat", component = GenPagesServiceChatClass, meta = UniPageMeta(isQuit = false), style = utsMapOf("navigationBarTitleText" to "在线客服")))
    __uniRoutes.push(UniPageRoute(path = "pages/seller/products", component = GenPagesSellerProductsClass, meta = UniPageMeta(isQuit = false), style = utsMapOf("navigationBarTitleText" to "商品管理", "navigationBarBackgroundColor" to "#0066CC", "navigationBarTextStyle" to "white")))
    __uniRoutes.push(UniPageRoute(path = "pages/seller/product-edit", component = GenPagesSellerProductEditClass, meta = UniPageMeta(isQuit = false), style = utsMapOf("navigationBarTitleText" to "编辑商品", "navigationBarBackgroundColor" to "#0066CC", "navigationBarTextStyle" to "white")))
    __uniRoutes.push(UniPageRoute(path = "pages/seller/inventory", component = GenPagesSellerInventoryClass, meta = UniPageMeta(isQuit = false), style = utsMapOf("navigationBarTitleText" to "库存管理", "navigationBarBackgroundColor" to "#0066CC", "navigationBarTextStyle" to "white")))
    __uniRoutes.push(UniPageRoute(path = "pages/seller/orders", component = GenPagesSellerOrdersClass, meta = UniPageMeta(isQuit = false), style = utsMapOf("navigationBarTitleText" to "订单管理", "navigationBarBackgroundColor" to "#0066CC", "navigationBarTextStyle" to "white")))
    __uniRoutes.push(UniPageRoute(path = "pages/seller/statistics", component = GenPagesSellerStatisticsClass, meta = UniPageMeta(isQuit = false), style = utsMapOf("navigationBarTitleText" to "销售统计", "navigationBarBackgroundColor" to "#0066CC", "navigationBarTextStyle" to "white")))
    __uniRoutes.push(UniPageRoute(path = "pages/seller/account", component = GenPagesSellerAccountClass, meta = UniPageMeta(isQuit = false), style = utsMapOf("navigationBarTitleText" to "商家账户", "navigationBarBackgroundColor" to "#0066CC", "navigationBarTextStyle" to "white")))
    __uniRoutes.push(UniPageRoute(path = "pages/seller/reviews", component = GenPagesSellerReviewsClass, meta = UniPageMeta(isQuit = false), style = utsMapOf("navigationBarTitleText" to "评价管理", "navigationBarBackgroundColor" to "#0066CC", "navigationBarTextStyle" to "white")))
    __uniRoutes.push(UniPageRoute(path = "pages/admin/reviews", component = GenPagesAdminReviewsClass, meta = UniPageMeta(isQuit = false), style = utsMapOf("navigationBarTitleText" to "评价管理", "navigationBarBackgroundColor" to "#0066CC", "navigationBarTextStyle" to "white")))
    __uniRoutes.push(UniPageRoute(path = "pages/seller/admin-index", component = GenPagesSellerAdminIndexClass, meta = UniPageMeta(isQuit = false), style = utsMapOf("navigationBarTitleText" to "管理工作台", "navigationBarBackgroundColor" to "#0066CC", "navigationBarTextStyle" to "white")))
    __uniRoutes.push(UniPageRoute(path = "pages/seller/product-audit", component = GenPagesSellerProductAuditClass, meta = UniPageMeta(isQuit = false), style = utsMapOf("navigationBarTitleText" to "商品审核", "navigationBarBackgroundColor" to "#0066CC", "navigationBarTextStyle" to "white")))
    __uniRoutes.push(UniPageRoute(path = "pages/seller/admin-users", component = GenPagesSellerAdminUsersClass, meta = UniPageMeta(isQuit = false), style = utsMapOf("navigationBarTitleText" to "用户管理", "navigationBarBackgroundColor" to "#0066CC", "navigationBarTextStyle" to "white")))
    __uniRoutes.push(UniPageRoute(path = "pages/seller/admin-statistics", component = GenPagesSellerAdminStatisticsClass, meta = UniPageMeta(isQuit = false), style = utsMapOf("navigationBarTitleText" to "数据统计", "navigationBarBackgroundColor" to "#0066CC", "navigationBarTextStyle" to "white")))
}
val __uniLaunchPage: Map<String, Any?> = utsMapOf("url" to "pages/index/index", "style" to utsMapOf("navigationBarTitleText" to "", "navigationStyle" to "custom"))
fun defineAppConfig() {
    __uniConfig.entryPagePath = "/pages/index/index"
    __uniConfig.globalStyle = utsMapOf("navigationBarTextStyle" to "white", "navigationBarTitleText" to "鲜农优选", "navigationBarBackgroundColor" to "#0066CC", "backgroundColor" to "#f5f5f5")
    __uniConfig.getTabBarConfig = fun(): Map<String, Any>? {
        return null
    }
    __uniConfig.tabBar = __uniConfig.getTabBarConfig()
    __uniConfig.conditionUrl = ""
    __uniConfig.uniIdRouter = utsMapOf()
    __uniConfig.ready = true
}
open class GenUniApp : UniAppImpl() {
    open val vm: GenApp?
        get() {
            return getAppVm() as GenApp?
        }
    open val `$vm`: GenApp?
        get() {
            return getAppVm() as GenApp?
        }
}
fun getApp(): GenUniApp {
    return getUniApp() as GenUniApp
}
