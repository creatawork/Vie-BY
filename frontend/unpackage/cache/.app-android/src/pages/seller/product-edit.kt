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
import io.dcloud.uniapp.extapi.chooseImage as uni_chooseImage
import io.dcloud.uniapp.extapi.hideLoading as uni_hideLoading
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showModal as uni_showModal
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesSellerProductEdit : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onLoad(fun(options: OnLoadOptions) {
            val id = options["id"]
            if (id != null && id !== "") {
                this.isEditMode = true
                this.productId = parseInt(id)
                this.loadProductDetail()
            }
            this.loadCategories()
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return createElementVNode("view", utsMapOf("class" to "page"), utsArrayOf(
            createElementVNode("view", utsMapOf("class" to "nav-bar"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "nav-left", "onClick" to _ctx.goBack), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "nav-back"), "←")
                ), 8, utsArrayOf(
                    "onClick"
                )),
                createElementVNode("text", utsMapOf("class" to "nav-title"), toDisplayString(if (_ctx.isEditMode) {
                    "编辑商品"
                } else {
                    "发布商品"
                }
                ), 1),
                createElementVNode("view", utsMapOf("class" to "nav-right"))
            )),
            createElementVNode("scroll-view", utsMapOf("class" to "form-scroll", "scroll-y" to "true", "show-scrollbar" to false), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to "form-section"), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "section-title"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "section-title-text"), "基本信息")
                    )),
                    createElementVNode("view", utsMapOf("class" to "form-item", "onClick" to _ctx.showCategoryPicker), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "form-label"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "required"), "*"),
                            "商品分类"
                        )),
                        createElementVNode("view", utsMapOf("class" to "form-value"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to normalizeClass(utsArrayOf(
                                "form-value-text",
                                utsMapOf("placeholder" to (_ctx.selectedCategoryName === ""))
                            ))), toDisplayString(if (_ctx.selectedCategoryName !== "") {
                                _ctx.selectedCategoryName
                            } else {
                                "请选择分类"
                            }
                            ), 3),
                            createElementVNode("text", utsMapOf("class" to "form-arrow"), "›")
                        ))
                    ), 8, utsArrayOf(
                        "onClick"
                    )),
                    createElementVNode("view", utsMapOf("class" to "form-item"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "form-label"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "required"), "*"),
                            "商品名称"
                        )),
                        createElementVNode("input", utsMapOf("class" to "form-input", "modelValue" to _ctx.formData.productName, "onInput" to fun(`$event`: InputEvent){
                            _ctx.formData.productName = `$event`.detail.value
                        }
                        , "placeholder" to "请输入商品名称", "maxlength" to "100"), null, 40, utsArrayOf(
                            "modelValue",
                            "onInput"
                        ))
                    )),
                    createElementVNode("view", utsMapOf("class" to "form-item form-item-textarea"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "form-label"), "商品描述"),
                        createElementVNode("textarea", utsMapOf("class" to "form-textarea", "modelValue" to _ctx.formData.description, "onInput" to fun(`$event`: InputEvent){
                            _ctx.formData.description = `$event`.detail.value
                        }
                        , "placeholder" to "请输入商品描述", "maxlength" to "500"), null, 40, utsArrayOf(
                            "modelValue",
                            "onInput"
                        ))
                    ))
                )),
                createElementVNode("view", utsMapOf("class" to "form-section"), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "section-title"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "section-title-text"), "商品图片")
                    )),
                    createElementVNode("view", utsMapOf("class" to "form-item"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "form-label"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "required"), "*"),
                            "商品主图"
                        ))
                    )),
                    createElementVNode("view", utsMapOf("class" to "image-upload-area"), utsArrayOf(
                        if (_ctx.formData.mainImage !== "") {
                            createElementVNode("view", utsMapOf("key" to 0, "class" to "image-preview"), utsArrayOf(
                                createElementVNode("image", utsMapOf("class" to "preview-img", "src" to _ctx.formData.mainImage, "mode" to "aspectFill"), null, 8, utsArrayOf(
                                    "src"
                                )),
                                createElementVNode("view", utsMapOf("class" to "image-delete", "onClick" to _ctx.deleteMainImage), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "delete-icon"), "×")
                                ), 8, utsArrayOf(
                                    "onClick"
                                ))
                            ))
                        } else {
                            createElementVNode("view", utsMapOf("key" to 1, "class" to "upload-btn", "onClick" to _ctx.chooseMainImage), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "upload-icon"), "+"),
                                createElementVNode("text", utsMapOf("class" to "upload-text"), "上传主图")
                            ), 8, utsArrayOf(
                                "onClick"
                            ))
                        }
                    ))
                )),
                createElementVNode("view", utsMapOf("class" to "form-section"), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "section-title"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "section-title-text"), "价格信息")
                    )),
                    createElementVNode("view", utsMapOf("class" to "form-item"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "form-label"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "required"), "*"),
                            "现价"
                        )),
                        createElementVNode("view", utsMapOf("class" to "price-input-wrapper"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "price-unit"), "¥"),
                            createElementVNode("input", utsMapOf("class" to "price-input", "modelValue" to _ctx.formData.currentPrice, "onInput" to fun(`$event`: InputEvent){
                                _ctx.formData.currentPrice = `$event`.detail.value
                            }
                            , "type" to "digit", "placeholder" to "0.00"), null, 40, utsArrayOf(
                                "modelValue",
                                "onInput"
                            ))
                        ))
                    )),
                    createElementVNode("view", utsMapOf("class" to "form-item"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "form-label"), "原价"),
                        createElementVNode("view", utsMapOf("class" to "price-input-wrapper"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "price-unit"), "¥"),
                            createElementVNode("input", utsMapOf("class" to "price-input", "modelValue" to _ctx.formData.originalPrice, "onInput" to fun(`$event`: InputEvent){
                                _ctx.formData.originalPrice = `$event`.detail.value
                            }
                            , "type" to "digit", "placeholder" to "0.00"), null, 40, utsArrayOf(
                                "modelValue",
                                "onInput"
                            ))
                        ))
                    ))
                )),
                createElementVNode("view", utsMapOf("class" to "form-section"), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "section-title"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "section-title-text"), "规格管理"),
                        createElementVNode("view", utsMapOf("class" to "add-sku-btn", "onClick" to _ctx.showAddSkuModal), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "add-sku-text"), "+ 添加规格")
                        ), 8, utsArrayOf(
                            "onClick"
                        ))
                    )),
                    if (_ctx.skuList.length > 0) {
                        createElementVNode("view", utsMapOf("key" to 0, "class" to "sku-list"), utsArrayOf(
                            createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.skuList, fun(sku, index, __index, _cached): Any {
                                return createElementVNode("view", utsMapOf("class" to "sku-item", "key" to index), utsArrayOf(
                                    createElementVNode("view", utsMapOf("class" to "sku-info"), utsArrayOf(
                                        createElementVNode("text", utsMapOf("class" to "sku-name"), toDisplayString(sku.skuName), 1),
                                        createElementVNode("view", utsMapOf("class" to "sku-details"), utsArrayOf(
                                            createElementVNode("text", utsMapOf("class" to "sku-price"), "¥" + toDisplayString(sku.price.toFixed(2)), 1),
                                            createElementVNode("text", utsMapOf("class" to "sku-stock"), "库存: " + toDisplayString(sku.stock), 1)
                                        ))
                                    )),
                                    createElementVNode("view", utsMapOf("class" to "sku-actions"), utsArrayOf(
                                        createElementVNode("view", utsMapOf("class" to "sku-action-btn", "onClick" to fun(){
                                            _ctx.editSku(index)
                                        }), utsArrayOf(
                                            createElementVNode("text", utsMapOf("class" to "sku-action-text"), "编辑")
                                        ), 8, utsArrayOf(
                                            "onClick"
                                        )),
                                        createElementVNode("view", utsMapOf("class" to "sku-action-btn sku-action-btn-delete", "onClick" to fun(){
                                            _ctx.deleteSku(index)
                                        }), utsArrayOf(
                                            createElementVNode("text", utsMapOf("class" to "sku-action-text sku-action-text-delete"), "删除")
                                        ), 8, utsArrayOf(
                                            "onClick"
                                        ))
                                    ))
                                ))
                            }), 128)
                        ))
                    } else {
                        createElementVNode("view", utsMapOf("key" to 1, "class" to "empty-sku"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "empty-sku-text"), "暂无规格，请添加至少一个规格")
                        ))
                    }
                )),
                createElementVNode("view", utsMapOf("style" to normalizeStyle(utsMapOf("height" to "100px"))), null, 4)
            )),
            createElementVNode("view", utsMapOf("class" to "submit-bar"), utsArrayOf(
                createElementVNode("view", utsMapOf("class" to normalizeClass(utsArrayOf(
                    "submit-btn",
                    utsMapOf("disabled" to _ctx.isSubmitting)
                )), "onClick" to _ctx.handleSubmit), utsArrayOf(
                    createElementVNode("text", utsMapOf("class" to "submit-btn-text"), toDisplayString(if (_ctx.isSubmitting) {
                        "提交中..."
                    } else {
                        if (_ctx.isEditMode) {
                            "保存修改"
                        } else {
                            "发布商品"
                        }
                    }
                    ), 1)
                ), 10, utsArrayOf(
                    "onClick"
                ))
            )),
            if (isTrue(_ctx.showCategoryModal)) {
                createElementVNode("view", utsMapOf("key" to 0, "class" to "modal-mask", "onClick" to _ctx.closeCategoryModal), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "modal-content category-modal", "onClick" to withModifiers(fun(){}, utsArrayOf(
                        "stop"
                    ))), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "modal-header"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "modal-title"), "选择分类"),
                            createElementVNode("view", utsMapOf("class" to "modal-close", "onClick" to _ctx.closeCategoryModal), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "close-icon"), "×")
                            ), 8, utsArrayOf(
                                "onClick"
                            ))
                        )),
                        createElementVNode("scroll-view", utsMapOf("class" to "category-list", "scroll-y" to "true", "show-scrollbar" to false), utsArrayOf(
                            createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.categories, fun(category, __key, __index, _cached): Any {
                                return createElementVNode("view", utsMapOf("class" to "category-item", "key" to category.id, "onClick" to fun(){
                                    _ctx.selectCategory(category)
                                }), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "category-name"), toDisplayString(category.categoryName), 1),
                                    if (_ctx.formData.categoryId === category.id) {
                                        createElementVNode("text", utsMapOf("key" to 0, "class" to "category-check"), "✓")
                                    } else {
                                        createCommentVNode("v-if", true)
                                    }
                                ), 8, utsArrayOf(
                                    "onClick"
                                ))
                            }), 128)
                        ))
                    ), 8, utsArrayOf(
                        "onClick"
                    ))
                ), 8, utsArrayOf(
                    "onClick"
                ))
            } else {
                createCommentVNode("v-if", true)
            }
            ,
            if (isTrue(_ctx.showSkuModal)) {
                createElementVNode("view", utsMapOf("key" to 1, "class" to "modal-mask", "onClick" to _ctx.closeSkuModal), utsArrayOf(
                    createElementVNode("view", utsMapOf("class" to "modal-content sku-modal", "onClick" to withModifiers(fun(){}, utsArrayOf(
                        "stop"
                    ))), utsArrayOf(
                        createElementVNode("view", utsMapOf("class" to "modal-header"), utsArrayOf(
                            createElementVNode("text", utsMapOf("class" to "modal-title"), toDisplayString(if (_ctx.editingSkuIndex >= 0) {
                                "编辑规格"
                            } else {
                                "添加规格"
                            }), 1),
                            createElementVNode("view", utsMapOf("class" to "modal-close", "onClick" to _ctx.closeSkuModal), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "close-icon"), "×")
                            ), 8, utsArrayOf(
                                "onClick"
                            ))
                        )),
                        createElementVNode("view", utsMapOf("class" to "sku-form"), utsArrayOf(
                            createElementVNode("view", utsMapOf("class" to "sku-form-item"), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "sku-form-label"), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "required"), "*"),
                                    "规格名称"
                                )),
                                createElementVNode("input", utsMapOf("class" to "sku-form-input", "modelValue" to _ctx.skuForm.skuName, "onInput" to fun(`$event`: InputEvent){
                                    _ctx.skuForm.skuName = `$event`.detail.value
                                }, "placeholder" to "如：500g装"), null, 40, utsArrayOf(
                                    "modelValue",
                                    "onInput"
                                ))
                            )),
                            createElementVNode("view", utsMapOf("class" to "sku-form-item"), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "sku-form-label"), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "required"), "*"),
                                    "价格"
                                )),
                                createElementVNode("view", utsMapOf("class" to "price-input-wrapper"), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "price-unit"), "¥"),
                                    createElementVNode("input", utsMapOf("class" to "sku-form-input price-input", "modelValue" to _ctx.skuForm.price, "onInput" to fun(`$event`: InputEvent){
                                        _ctx.skuForm.price = `$event`.detail.value
                                    }, "type" to "digit", "placeholder" to "0.00"), null, 40, utsArrayOf(
                                        "modelValue",
                                        "onInput"
                                    ))
                                ))
                            )),
                            createElementVNode("view", utsMapOf("class" to "sku-form-item"), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "sku-form-label"), utsArrayOf(
                                    createElementVNode("text", utsMapOf("class" to "required"), "*"),
                                    "库存"
                                )),
                                createElementVNode("input", utsMapOf("class" to "sku-form-input", "modelValue" to _ctx.skuForm.stock, "onInput" to fun(`$event`: InputEvent){
                                    _ctx.skuForm.stock = `$event`.detail.value
                                }, "type" to "number", "placeholder" to "0"), null, 40, utsArrayOf(
                                    "modelValue",
                                    "onInput"
                                ))
                            )),
                            createElementVNode("view", utsMapOf("class" to "sku-form-item"), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "sku-form-label"), "规格信息"),
                                createElementVNode("input", utsMapOf("class" to "sku-form-input", "modelValue" to _ctx.skuForm.specInfo, "onInput" to fun(`$event`: InputEvent){
                                    _ctx.skuForm.specInfo = `$event`.detail.value
                                }, "placeholder" to "如：重量:500g"), null, 40, utsArrayOf(
                                    "modelValue",
                                    "onInput"
                                ))
                            ))
                        )),
                        createElementVNode("view", utsMapOf("class" to "modal-footer"), utsArrayOf(
                            createElementVNode("view", utsMapOf("class" to "modal-btn modal-btn-cancel", "onClick" to _ctx.closeSkuModal), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "modal-btn-text"), "取消")
                            ), 8, utsArrayOf(
                                "onClick"
                            )),
                            createElementVNode("view", utsMapOf("class" to "modal-btn modal-btn-confirm", "onClick" to _ctx.saveSku), utsArrayOf(
                                createElementVNode("text", utsMapOf("class" to "modal-btn-text modal-btn-text-confirm"), "确定")
                            ), 8, utsArrayOf(
                                "onClick"
                            ))
                        ))
                    ), 8, utsArrayOf(
                        "onClick"
                    ))
                ), 8, utsArrayOf(
                    "onClick"
                ))
            } else {
                createCommentVNode("v-if", true)
            }
        ))
    }
    open var isEditMode: Boolean by `$data`
    open var productId: Number by `$data`
    open var isLoading: Boolean by `$data`
    open var isSubmitting: Boolean by `$data`
    open var formData: FormDataType by `$data`
    open var skuList: UTSArray<SkuItem> by `$data`
    open var categories: UTSArray<CategoryType2> by `$data`
    open var selectedCategoryName: String by `$data`
    open var showCategoryModal: Boolean by `$data`
    open var showSkuModal: Boolean by `$data`
    open var editingSkuIndex: Number by `$data`
    open var skuForm: SkuFormType by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return utsMapOf("isEditMode" to false, "productId" to 0, "isLoading" to false, "isSubmitting" to false, "formData" to FormDataType(categoryId = 0, productName = "", mainImage = "", description = "", detail = "", originalPrice = "", currentPrice = ""), "skuList" to utsArrayOf<SkuItem>(), "categories" to utsArrayOf<CategoryType2>(), "selectedCategoryName" to "", "showCategoryModal" to false, "showSkuModal" to false, "editingSkuIndex" to -1, "skuForm" to SkuFormType(skuName = "", price = "", stock = "", specInfo = ""))
    }
    open var goBack = ::gen_goBack_fn
    open fun gen_goBack_fn() {
        uni_navigateBack(NavigateBackOptions())
    }
    open var loadCategories = ::gen_loadCategories_fn
    open fun gen_loadCategories_fn() {
        getCategories().then(fun(res){
            val data = res.data as UTSArray<UTSJSONObject>
            if (data != null) {
                this.categories = this.parseCategoriesFlat(data)
            }
        }
        ).`catch`(fun(err){
            console.error("获取分类失败:", err, " at pages/seller/product-edit.uvue:292")
        }
        )
    }
    open var parseCategoriesFlat = ::gen_parseCategoriesFlat_fn
    open fun gen_parseCategoriesFlat_fn(data: UTSArray<UTSJSONObject>): UTSArray<CategoryType2> {
        val result: UTSArray<CategoryType2> = utsArrayOf()
        run {
            var i: Number = 0
            while(i < data.length){
                val item = data[i]
                val children = item.getArray("children")
                if (children != null && children.length > 0) {
                    run {
                        var j: Number = 0
                        while(j < children.length){
                            val child = children[j] as UTSJSONObject
                            val subChildren = child.getArray("children")
                            if (subChildren != null && subChildren.length > 0) {
                                run {
                                    var k: Number = 0
                                    while(k < subChildren.length){
                                        val subChild = subChildren[k] as UTSJSONObject
                                        result.push(CategoryType2(id = (subChild.getNumber("id") ?: 0).toInt(), categoryName = (item.getString("categoryName") ?: "") + " > " + (child.getString("categoryName") ?: "") + " > " + (subChild.getString("categoryName") ?: ""), parentId = (subChild.getNumber("parentId") ?: 0).toInt(), level = 3))
                                        k++
                                    }
                                }
                            } else {
                                result.push(CategoryType2(id = (child.getNumber("id") ?: 0).toInt(), categoryName = (item.getString("categoryName") ?: "") + " > " + (child.getString("categoryName") ?: ""), parentId = (child.getNumber("parentId") ?: 0).toInt(), level = 2))
                            }
                            j++
                        }
                    }
                } else {
                    result.push(CategoryType2(id = (item.getNumber("id") ?: 0).toInt(), categoryName = item.getString("categoryName") ?: "", parentId = 0, level = 1))
                }
                i++
            }
        }
        return result
    }
    open var loadProductDetail = ::gen_loadProductDetail_fn
    open fun gen_loadProductDetail_fn() {
        this.isLoading = true
        getSellerProductDetail(this.productId).then(fun(res){
            val data = res.data as UTSJSONObject
            if (data != null) {
                this.formData.categoryId = (data.getNumber("categoryId") ?: 0).toInt()
                this.formData.productName = data.getString("productName") ?: ""
                this.formData.mainImage = data.getString("mainImage") ?: ""
                this.formData.description = data.getString("description") ?: ""
                this.formData.detail = data.getString("detail") ?: ""
                val originalPrice = data.getNumber("originalPrice")
                this.formData.originalPrice = if (originalPrice != null) {
                    originalPrice.toFixed(2)
                } else {
                    ""
                }
                val currentPrice = data.getNumber("currentPrice")
                this.formData.currentPrice = if (currentPrice != null) {
                    currentPrice.toFixed(2)
                } else {
                    ""
                }
                val categoryName = data.getString("categoryName")
                if (categoryName != null) {
                    this.selectedCategoryName = categoryName
                }
                val skuListData = data.getArray("skuList")
                if (skuListData != null) {
                    this.skuList = this.parseSkuList(skuListData as UTSArray<Any>)
                }
            }
            this.isLoading = false
        }
        ).`catch`(fun(err){
            console.error("获取商品详情失败:", err, " at pages/seller/product-edit.uvue:375")
            this.isLoading = false
            uni_showToast(ShowToastOptions(title = "获取商品详情失败", icon = "none"))
        }
        )
    }
    open var parseSkuList = ::gen_parseSkuList_fn
    open fun gen_parseSkuList_fn(data: UTSArray<Any>): UTSArray<SkuItem> {
        val result: UTSArray<SkuItem> = utsArrayOf()
        run {
            var i: Number = 0
            while(i < data.length){
                val item = data[i] as UTSJSONObject
                result.push(SkuItem(skuName = item.getString("skuName") ?: "", price = item.getNumber("price") ?: 0, stock = (item.getNumber("stock") ?: 0).toInt(), specInfo = item.getString("specInfo")))
                i++
            }
        }
        return result
    }
    open var showCategoryPicker = ::gen_showCategoryPicker_fn
    open fun gen_showCategoryPicker_fn() {
        this.showCategoryModal = true
    }
    open var closeCategoryModal = ::gen_closeCategoryModal_fn
    open fun gen_closeCategoryModal_fn() {
        this.showCategoryModal = false
    }
    open var selectCategory = ::gen_selectCategory_fn
    open fun gen_selectCategory_fn(category: CategoryType2) {
        this.formData.categoryId = category.id
        this.selectedCategoryName = category.categoryName
        this.showCategoryModal = false
    }
    open var chooseMainImage = ::gen_chooseMainImage_fn
    open fun gen_chooseMainImage_fn() {
        uni_chooseImage(ChooseImageOptions(count = 1, sizeType = utsArrayOf(
            "compressed"
        ), sourceType = utsArrayOf(
            "album",
            "camera"
        ), success = fun(res){
            val tempFilePath = res.tempFilePaths[0]
            this.uploadImage(tempFilePath)
        }
        ))
    }
    open var uploadImage = ::gen_uploadImage_fn
    open fun gen_uploadImage_fn(filePath: String) {
        uni_showLoading(ShowLoadingOptions(title = "上传中..."))
        uploadProductImage(filePath).then(fun(res){
            uni_hideLoading()
            val data = res.data as UTSJSONObject
            val imageUrl = data.getString("url")
            if (imageUrl != null && imageUrl !== "") {
                this.formData.mainImage = imageUrl
                uni_showToast(ShowToastOptions(title = "上传成功", icon = "success"))
            }
        }
        ).`catch`(fun(err){
            uni_hideLoading()
            console.error("上传图片失败:", err, " at pages/seller/product-edit.uvue:439")
            uni_showToast(ShowToastOptions(title = "上传失败", icon = "none"))
        }
        )
    }
    open var deleteMainImage = ::gen_deleteMainImage_fn
    open fun gen_deleteMainImage_fn() {
        this.formData.mainImage = ""
    }
    open var showAddSkuModal = ::gen_showAddSkuModal_fn
    open fun gen_showAddSkuModal_fn() {
        this.editingSkuIndex = -1
        this.skuForm = SkuFormType(skuName = "", price = "", stock = "", specInfo = "")
        this.showSkuModal = true
    }
    open var editSku = ::gen_editSku_fn
    open fun gen_editSku_fn(index: Number) {
        this.editingSkuIndex = index
        val sku = this.skuList[index]
        this.skuForm = SkuFormType(skuName = sku.skuName, price = sku.price.toFixed(2), stock = sku.stock.toString(10), specInfo = sku.specInfo ?: "")
        this.showSkuModal = true
    }
    open var deleteSku = ::gen_deleteSku_fn
    open fun gen_deleteSku_fn(index: Number) {
        uni_showModal(ShowModalOptions(title = "提示", content = "确定要删除该规格吗？", success = fun(res){
            if (res.confirm) {
                this.skuList.splice(index, 1)
            }
        }
        ))
    }
    open var closeSkuModal = ::gen_closeSkuModal_fn
    open fun gen_closeSkuModal_fn() {
        this.showSkuModal = false
    }
    open var saveSku = ::gen_saveSku_fn
    open fun gen_saveSku_fn() {
        if (this.skuForm.skuName.trim() === "") {
            uni_showToast(ShowToastOptions(title = "请输入规格名称", icon = "none"))
            return
        }
        val price = parseFloat(this.skuForm.price)
        if (isNaN(price) || price <= 0) {
            uni_showToast(ShowToastOptions(title = "请输入有效的价格", icon = "none"))
            return
        }
        val stock = parseInt(this.skuForm.stock)
        if (isNaN(stock) || stock < 0) {
            uni_showToast(ShowToastOptions(title = "请输入有效的库存", icon = "none"))
            return
        }
        val skuItem = SkuItem(skuName = this.skuForm.skuName.trim(), price = price, stock = stock, specInfo = if (this.skuForm.specInfo.trim() !== "") {
            this.skuForm.specInfo.trim()
        } else {
            null
        }
        )
        if (this.editingSkuIndex >= 0) {
            this.skuList[this.editingSkuIndex] = skuItem
        } else {
            this.skuList.push(skuItem)
        }
        this.showSkuModal = false
    }
    open var validateForm = ::gen_validateForm_fn
    open fun gen_validateForm_fn(): Boolean {
        if (this.formData.categoryId === 0) {
            uni_showToast(ShowToastOptions(title = "请选择商品分类", icon = "none"))
            return false
        }
        if (this.formData.productName.trim() === "") {
            uni_showToast(ShowToastOptions(title = "请输入商品名称", icon = "none"))
            return false
        }
        if (this.formData.mainImage === "") {
            uni_showToast(ShowToastOptions(title = "请上传商品主图", icon = "none"))
            return false
        }
        val currentPrice = parseFloat(this.formData.currentPrice)
        if (isNaN(currentPrice) || currentPrice <= 0) {
            uni_showToast(ShowToastOptions(title = "请输入有效的商品价格", icon = "none"))
            return false
        }
        if (this.skuList.length === 0) {
            uni_showToast(ShowToastOptions(title = "请至少添加一个规格", icon = "none"))
            return false
        }
        return true
    }
    open var handleSubmit = ::gen_handleSubmit_fn
    open fun gen_handleSubmit_fn() {
        if (this.isSubmitting) {
            return
        }
        if (!this.validateForm()) {
            return
        }
        this.isSubmitting = true
        val currentPrice = parseFloat(this.formData.currentPrice)
        val originalPriceStr = this.formData.originalPrice.trim()
        val originalPrice = if (originalPriceStr !== "") {
            parseFloat(originalPriceStr)
        } else {
            null
        }
        val productData = ProductFormData(categoryId = this.formData.categoryId, productName = this.formData.productName.trim(), mainImage = this.formData.mainImage, description = if (this.formData.description.trim() !== "") {
            this.formData.description.trim()
        } else {
            null
        }
        , detail = if (this.formData.detail.trim() !== "") {
            this.formData.detail.trim()
        } else {
            null
        }
        , originalPrice = originalPrice, currentPrice = currentPrice, isRecommended = null, isNew = null, isHot = null, images = null, skuList = this.skuList)
        if (this.isEditMode) {
            updateProduct(this.productId, productData).then(fun(){
                this.isSubmitting = false
                uni_showToast(ShowToastOptions(title = "保存成功", icon = "success"))
                setTimeout(fun(){
                    uni_navigateBack(NavigateBackOptions())
                }, 1500)
            }).`catch`(fun(err){
                this.isSubmitting = false
                console.error("更新商品失败:", err, " at pages/seller/product-edit.uvue:598")
                uni_showToast(ShowToastOptions(title = "保存失败", icon = "none"))
            })
        } else {
            createProduct(productData).then(fun(){
                this.isSubmitting = false
                uni_showToast(ShowToastOptions(title = "发布成功", icon = "success"))
                setTimeout(fun(){
                    uni_navigateBack(NavigateBackOptions())
                }
                , 1500)
            }
            ).`catch`(fun(err){
                this.isSubmitting = false
                console.error("创建商品失败:", err, " at pages/seller/product-edit.uvue:611")
                uni_showToast(ShowToastOptions(title = "发布失败", icon = "none"))
            }
            )
        }
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
                return utsMapOf("page" to padStyleMapOf(utsMapOf("backgroundColor" to "#f7f8fa", "display" to "flex", "flexDirection" to "column", "flex" to 1)), "nav-bar" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "paddingTop" to CSS_VAR_STATUS_BAR_HEIGHT, "paddingRight" to 16, "paddingBottom" to 0, "paddingLeft" to 16, "height" to 44, "backgroundColor" to "#0066CC")), "nav-left" to padStyleMapOf(utsMapOf("width" to 60)), "nav-back" to padStyleMapOf(utsMapOf("fontSize" to 24, "color" to "#ffffff")), "nav-title" to padStyleMapOf(utsMapOf("fontSize" to 17, "fontWeight" to "700", "color" to "#ffffff")), "nav-right" to padStyleMapOf(utsMapOf("width" to 60)), "form-scroll" to padStyleMapOf(utsMapOf("flex" to 1, "width" to "100%")), "form-section" to padStyleMapOf(utsMapOf("backgroundColor" to "#ffffff", "marginTop" to 12, "marginRight" to 16, "marginBottom" to 12, "marginLeft" to 16, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16)), "section-title" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "marginBottom" to 16)), "section-title-text" to padStyleMapOf(utsMapOf("fontSize" to 16, "fontWeight" to "700", "color" to "#333333")), "form-item" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "paddingTop" to 12, "paddingRight" to 0, "paddingBottom" to 12, "paddingLeft" to 0, "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "#f5f5f5")), "form-item-textarea" to padStyleMapOf(utsMapOf("flexDirection" to "column", "alignItems" to "flex-start")), "form-label" to padStyleMapOf(utsMapOf("width" to 80, "fontSize" to 14, "color" to "#333333", "flexShrink" to 0)), "required" to padStyleMapOf(utsMapOf("color" to "#ff4d4f", "marginRight" to 2)), "form-value" to padStyleMapOf(utsMapOf("flex" to 1, "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "flex-end")), "form-value-text" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#333333", "textAlign" to "right")), "form-value-text-placeholder" to padStyleMapOf(utsMapOf("color" to "#999999")), "form-arrow" to padStyleMapOf(utsMapOf("fontSize" to 18, "color" to "#999999", "marginLeft" to 8)), "form-input" to padStyleMapOf(utsMapOf("flex" to 1, "fontSize" to 14, "color" to "#333333", "textAlign" to "right", "height" to 40)), "form-textarea" to padStyleMapOf(utsMapOf("width" to "100%", "height" to 80, "fontSize" to 14, "color" to "#333333", "marginTop" to 8, "paddingTop" to 8, "paddingRight" to 8, "paddingBottom" to 8, "paddingLeft" to 8, "backgroundColor" to "#f9f9f9", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8)), "price-input-wrapper" to padStyleMapOf(utsMapOf("flex" to 1, "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "backgroundColor" to "#f9f9f9", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingTop" to 0, "paddingRight" to 10, "paddingBottom" to 0, "paddingLeft" to 10, "height" to 40, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#eeeeee", "borderRightColor" to "#eeeeee", "borderBottomColor" to "#eeeeee", "borderLeftColor" to "#eeeeee")), "price-unit" to padStyleMapOf(utsMapOf("fontSize" to 16, "color" to "#ff4d4f", "fontWeight" to "700", "marginRight" to 4)), "price-input" to padStyleMapOf(utsMapOf("width" to "100%", "height" to 40, "fontSize" to 16, "color" to "#333333", "textAlign" to "left")), "image-upload-area" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "flexWrap" to "wrap", "marginTop" to 8)), "image-preview" to padStyleMapOf(utsMapOf("position" to "relative", "width" to 100, "height" to 100, "marginRight" to 12, "marginBottom" to 12)), "preview-img" to padStyleMapOf(utsMapOf("width" to 100, "height" to 100, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#f5f5f5")), "image-delete" to padStyleMapOf(utsMapOf("position" to "absolute", "top" to -8, "right" to -8, "width" to 20, "height" to 20, "backgroundColor" to "#ff4d4f", "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10, "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "delete-icon" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#ffffff")), "upload-btn" to padStyleMapOf(utsMapOf("width" to 100, "height" to 100, "backgroundColor" to "#f9f9f9", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "dashed", "borderRightStyle" to "dashed", "borderBottomStyle" to "dashed", "borderLeftStyle" to "dashed", "borderTopColor" to "#dddddd", "borderRightColor" to "#dddddd", "borderBottomColor" to "#dddddd", "borderLeftColor" to "#dddddd", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "display" to "flex", "flexDirection" to "column", "alignItems" to "center", "justifyContent" to "center")), "upload-icon" to padStyleMapOf(utsMapOf("fontSize" to 28, "color" to "#999999")), "upload-text" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999", "marginTop" to 4)), "add-sku-btn" to padStyleMapOf(utsMapOf("paddingTop" to 6, "paddingRight" to 12, "paddingBottom" to 6, "paddingLeft" to 12, "backgroundColor" to "#e6f7ff", "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14)), "add-sku-text" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#0066CC")), "sku-list" to padStyleMapOf(utsMapOf("marginTop" to 8)), "sku-item" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "backgroundColor" to "#f9f9f9", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "marginBottom" to 8)), "sku-info" to padStyleMapOf(utsMapOf("flex" to 1)), "sku-name" to padStyleMapOf(utsMapOf("fontSize" to 14, "fontWeight" to "700", "color" to "#333333")), "sku-details" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "marginTop" to 6)), "sku-price" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#ff4d4f", "marginRight" to 16)), "sku-stock" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#999999")), "sku-actions" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row")), "sku-action-btn" to padStyleMapOf(utsMapOf("paddingTop" to 4, "paddingRight" to 12, "paddingBottom" to 4, "paddingLeft" to 12, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#0066CC", "borderRightColor" to "#0066CC", "borderBottomColor" to "#0066CC", "borderLeftColor" to "#0066CC", "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "marginLeft" to 8)), "sku-action-btn-delete" to padStyleMapOf(utsMapOf("borderTopColor" to "#ff4d4f", "borderRightColor" to "#ff4d4f", "borderBottomColor" to "#ff4d4f", "borderLeftColor" to "#ff4d4f")), "sku-action-text" to padStyleMapOf(utsMapOf("fontSize" to 12, "color" to "#0066CC")), "sku-action-text-delete" to padStyleMapOf(utsMapOf("color" to "#ff4d4f")), "empty-sku" to padStyleMapOf(utsMapOf("paddingTop" to 24, "paddingRight" to 0, "paddingBottom" to 24, "paddingLeft" to 0, "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "empty-sku-text" to padStyleMapOf(utsMapOf("fontSize" to 13, "color" to "#999999")), "submit-bar" to padStyleMapOf(utsMapOf("position" to "fixed", "bottom" to 0, "left" to 0, "right" to 0, "paddingTop" to 12, "paddingRight" to 16, "paddingBottom" to 24, "paddingLeft" to 16, "backgroundColor" to "#ffffff", "boxShadow" to "0 -2px 8px rgba(0, 0, 0, 0.05)")), "submit-btn" to padStyleMapOf(utsMapOf("height" to 48, "backgroundColor" to "#0066CC", "borderTopLeftRadius" to 24, "borderTopRightRadius" to 24, "borderBottomRightRadius" to 24, "borderBottomLeftRadius" to 24, "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "submit-btn-disabled" to padStyleMapOf(utsMapOf("backgroundColor" to "#cccccc")), "submit-btn-text" to padStyleMapOf(utsMapOf("fontSize" to 16, "fontWeight" to "700", "color" to "#ffffff")), "modal-mask" to padStyleMapOf(utsMapOf("position" to "fixed", "top" to 0, "left" to 0, "right" to 0, "bottom" to 0, "backgroundColor" to "rgba(0,0,0,0.5)", "display" to "flex", "alignItems" to "flex-end", "justifyContent" to "center", "zIndex" to 1000)), "modal-content" to padStyleMapOf(utsMapOf("width" to "100%", "backgroundColor" to "#ffffff", "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 0, "borderBottomLeftRadius" to 0, "maxHeight" to 500)), "modal-header" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "#f5f5f5")), "modal-title" to padStyleMapOf(utsMapOf("fontSize" to 16, "fontWeight" to "700", "color" to "#333333")), "modal-close" to padStyleMapOf(utsMapOf("width" to 28, "height" to 28, "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "close-icon" to padStyleMapOf(utsMapOf("fontSize" to 24, "color" to "#999999")), "category-modal" to padStyleMapOf(utsMapOf("maxHeight" to 450)), "category-list" to padStyleMapOf(utsMapOf("maxHeight" to 400)), "category-item" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "paddingTop" to 14, "paddingRight" to 16, "paddingBottom" to 14, "paddingLeft" to 16, "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "#f5f5f5")), "category-name" to padStyleMapOf(utsMapOf("fontSize" to 14, "color" to "#333333")), "category-check" to padStyleMapOf(utsMapOf("fontSize" to 16, "color" to "#0066CC")), "sku-modal" to padStyleMapOf(utsMapOf("maxHeight" to 600)), "sku-form" to padStyleMapOf(utsMapOf("paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16)), "sku-form-item" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "marginBottom" to 16)), "sku-form-label" to padStyleMapOf(utsMapOf("width" to 80, "fontSize" to 14, "color" to "#333333", "flexShrink" to 0)), "sku-form-input" to padStyleMapOf(utsMapOf("flex" to 1, "height" to 40, "paddingTop" to 0, "paddingRight" to 12, "paddingBottom" to 0, "paddingLeft" to 12, "backgroundColor" to "#f9f9f9", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "fontSize" to 14, "color" to "#333333")), "modal-footer" to padStyleMapOf(utsMapOf("display" to "flex", "flexDirection" to "row", "paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "borderTopWidth" to 1, "borderTopStyle" to "solid", "borderTopColor" to "#f5f5f5")), "modal-btn" to padStyleMapOf(utsMapOf("flex" to 1, "height" to 44, "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "modal-btn-cancel" to padStyleMapOf(utsMapOf("backgroundColor" to "#f5f5f5", "marginRight" to 12)), "modal-btn-confirm" to padStyleMapOf(utsMapOf("backgroundColor" to "#0066CC")), "modal-btn-text" to padStyleMapOf(utsMapOf("fontSize" to 15, "color" to "#666666")), "modal-btn-text-confirm" to padStyleMapOf(utsMapOf("color" to "#ffffff")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = utsMapOf()
        var emits: Map<String, Any?> = utsMapOf()
        var props = normalizePropsOptions(utsMapOf())
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf()
        var components: Map<String, CreateVueComponent> = utsMapOf()
    }
}
