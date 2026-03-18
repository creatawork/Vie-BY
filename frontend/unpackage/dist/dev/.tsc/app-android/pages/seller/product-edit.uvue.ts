
import { getSellerProductDetail, createProduct, updateProduct, uploadProductImage, type ProductFormData, type SkuItem } from '@/api/seller'
import { getCategories } from '@/api/product'

// 分类类型
type CategoryType = { __$originalPosition?: UTSSourceMapPosition<"CategoryType", "pages/seller/product-edit.uvue", 202, 6>;
  id: number
  categoryName: string
  parentId: number
  level: number
}

// SKU表单类型
type SkuFormType = { __$originalPosition?: UTSSourceMapPosition<"SkuFormType", "pages/seller/product-edit.uvue", 210, 6>;
  skuName: string
  price: string
  stock: string
  specInfo: string
}

// 表单数据类型
type FormDataType = { __$originalPosition?: UTSSourceMapPosition<"FormDataType", "pages/seller/product-edit.uvue", 218, 6>;
  categoryId: number
  productName: string
  mainImage: string
  description: string
  detail: string
  originalPrice: string
  currentPrice: string
}

const __sfc__ = defineComponent({
  data() {
    return {
      isEditMode: false,
      productId: 0,
      isLoading: false,
      isSubmitting: false,
      
      // 表单数据
      formData: {
        categoryId: 0,
        productName: '',
        mainImage: '',
        description: '',
        detail: '',
        originalPrice: '',
        currentPrice: ''
      } as FormDataType,
      
      // SKU列表
      skuList: [] as SkuItem[],
      
      // 分类相关
      categories: [] as CategoryType[],
      selectedCategoryName: '',
      showCategoryModal: false,
      
      // SKU弹窗相关
      showSkuModal: false,
      editingSkuIndex: -1,
      skuForm: {
        skuName: '',
        price: '',
        stock: '',
        specInfo: ''
      } as SkuFormType
    }
  },
  onLoad(options: OnLoadOptions) {
    // 判断是新增还是编辑模式
    const id = options['id']
    if (id != null && id !== '') {
      this.isEditMode = true
      this.productId = parseInt(id)
      this.loadProductDetail()
    }
    
    // 加载分类列表
    this.loadCategories()
  },
  methods: {
    // 返回上一页
    goBack() {
      uni.navigateBack({})
    },
    
    // 加载分类列表
    loadCategories() {
      getCategories().then((res) => {
        const data = res.data as UTSJSONObject[]
        if (data != null) {
          this.categories = this.parseCategoriesFlat(data)
        }
      }).catch((err) => {
        console.error('获取分类失败:', err, " at pages/seller/product-edit.uvue:292")
      })
    },
    
    // 解析分类为扁平列表（只取叶子节点或一级分类）
    parseCategoriesFlat(data: UTSJSONObject[]): CategoryType[] {
      const result: CategoryType[] = []
      for (let i = 0; i < data.length; i++) {
        const item = data[i]
        const children = item.getArray('children')
        
        if (children != null && children.length > 0) {
          // 有子分类，递归处理
          for (let j = 0; j < children.length; j++) {
            const child = children[j] as UTSJSONObject
            const subChildren = child.getArray('children')
            
            if (subChildren != null && subChildren.length > 0) {
              // 三级分类
              for (let k = 0; k < subChildren.length; k++) {
                const subChild = subChildren[k] as UTSJSONObject
                result.push({
                  id: (subChild.getNumber('id') ?? 0).toInt(),
                  categoryName: (item.getString('categoryName') ?? '') + ' > ' + (child.getString('categoryName') ?? '') + ' > ' + (subChild.getString('categoryName') ?? ''),
                  parentId: (subChild.getNumber('parentId') ?? 0).toInt(),
                  level: 3
                } as CategoryType)
              }
            } else {
              // 二级分类
              result.push({
                id: (child.getNumber('id') ?? 0).toInt(),
                categoryName: (item.getString('categoryName') ?? '') + ' > ' + (child.getString('categoryName') ?? ''),
                parentId: (child.getNumber('parentId') ?? 0).toInt(),
                level: 2
              } as CategoryType)
            }
          }
        } else {
          // 一级分类（无子分类）
          result.push({
            id: (item.getNumber('id') ?? 0).toInt(),
            categoryName: item.getString('categoryName') ?? '',
            parentId: 0,
            level: 1
          } as CategoryType)
        }
      }
      return result
    },
    
    // 加载商品详情（编辑模式）
    loadProductDetail() {
      this.isLoading = true
      getSellerProductDetail(this.productId).then((res) => {
        const data = res.data as UTSJSONObject
        if (data != null) {
          this.formData.categoryId = (data.getNumber('categoryId') ?? 0).toInt()
          this.formData.productName = data.getString('productName') ?? ''
          this.formData.mainImage = data.getString('mainImage') ?? ''
          this.formData.description = data.getString('description') ?? ''
          this.formData.detail = data.getString('detail') ?? ''
          
          const originalPrice = data.getNumber('originalPrice')
          this.formData.originalPrice = originalPrice != null ? originalPrice.toFixed(2) : ''
          
          const currentPrice = data.getNumber('currentPrice')
          this.formData.currentPrice = currentPrice != null ? currentPrice.toFixed(2) : ''
          
          // 设置分类名称
          const categoryName = data.getString('categoryName')
          if (categoryName != null) {
            this.selectedCategoryName = categoryName
          }
          
          // 解析SKU列表
          const skuListData = data.getArray('skuList')
          if (skuListData != null) {
            this.skuList = this.parseSkuList(skuListData as UTSArray<any>)
          }
        }
        this.isLoading = false
      }).catch((err) => {
        console.error('获取商品详情失败:', err, " at pages/seller/product-edit.uvue:375")
        this.isLoading = false
        uni.showToast({ title: '获取商品详情失败', icon: 'none' })
      })
    },
    
    // 解析SKU列表
    parseSkuList(data: UTSArray<any>): SkuItem[] {
      const result: SkuItem[] = []
      for (let i = 0; i < data.length; i++) {
        const item = data[i] as UTSJSONObject
        result.push({
          skuName: item.getString('skuName') ?? '',
          price: item.getNumber('price') ?? 0,
          stock: (item.getNumber('stock') ?? 0).toInt(),
          specInfo: item.getString('specInfo')
        } as SkuItem)
      }
      return result
    },
    
    // 显示分类选择弹窗
    showCategoryPicker() {
      this.showCategoryModal = true
    },
    
    // 关闭分类选择弹窗
    closeCategoryModal() {
      this.showCategoryModal = false
    },
    
    // 选择分类
    selectCategory(category: CategoryType) {
      this.formData.categoryId = category.id
      this.selectedCategoryName = category.categoryName
      this.showCategoryModal = false
    },
    
    // 选择主图
    chooseMainImage() {
      uni.chooseImage({
        count: 1,
        sizeType: ['compressed'],
        sourceType: ['album', 'camera'],
        success: (res) => {
          const tempFilePath = res.tempFilePaths[0]
          this.uploadImage(tempFilePath)
        }
      })
    },
    
    // 上传图片
    uploadImage(filePath: string) {
      uni.showLoading({ title: '上传中...' })
      uploadProductImage(filePath).then((res) => {
        uni.hideLoading()
        const data = res.data as UTSJSONObject
        const imageUrl = data.getString('url')
        if (imageUrl != null && imageUrl !== '') {
          this.formData.mainImage = imageUrl
          uni.showToast({ title: '上传成功', icon: 'success' })
        }
      }).catch((err) => {
        uni.hideLoading()
        console.error('上传图片失败:', err, " at pages/seller/product-edit.uvue:439")
        uni.showToast({ title: '上传失败', icon: 'none' })
      })
    },
    
    // 删除主图
    deleteMainImage() {
      this.formData.mainImage = ''
    },
    
    // 显示添加SKU弹窗
    showAddSkuModal() {
      this.editingSkuIndex = -1
      this.skuForm = {
        skuName: '',
        price: '',
        stock: '',
        specInfo: ''
      } as SkuFormType
      this.showSkuModal = true
    },
    
    // 编辑SKU
    editSku(index: number) {
      this.editingSkuIndex = index
      const sku = this.skuList[index]
      this.skuForm = {
        skuName: sku.skuName,
        price: sku.price.toFixed(2),
        stock: sku.stock.toString(),
        specInfo: sku.specInfo ?? ''
      } as SkuFormType
      this.showSkuModal = true
    },
    
    // 删除SKU
    deleteSku(index: number) {
      uni.showModal({
        title: '提示',
        content: '确定要删除该规格吗？',
        success: (res) => {
          if (res.confirm) {
            this.skuList.splice(index, 1)
          }
        }
      })
    },
    
    // 关闭SKU弹窗
    closeSkuModal() {
      this.showSkuModal = false
    },
    
    // 保存SKU
    saveSku() {
      // 验证SKU表单
      if (this.skuForm.skuName.trim() === '') {
        uni.showToast({ title: '请输入规格名称', icon: 'none' })
        return
      }
      
      const price = parseFloat(this.skuForm.price)
      if (isNaN(price) || price <= 0) {
        uni.showToast({ title: '请输入有效的价格', icon: 'none' })
        return
      }
      
      const stock = parseInt(this.skuForm.stock)
      if (isNaN(stock) || stock < 0) {
        uni.showToast({ title: '请输入有效的库存', icon: 'none' })
        return
      }
      
      const skuItem: SkuItem = {
        skuName: this.skuForm.skuName.trim(),
        price: price,
        stock: stock,
        specInfo: this.skuForm.specInfo.trim() !== '' ? this.skuForm.specInfo.trim() : null
      }
      
      if (this.editingSkuIndex >= 0) {
        // 编辑模式
        this.skuList[this.editingSkuIndex] = skuItem
      } else {
        // 添加模式
        this.skuList.push(skuItem)
      }
      
      this.showSkuModal = false
    },
    
    // 表单验证
    validateForm(): boolean {
      if (this.formData.categoryId === 0) {
        uni.showToast({ title: '请选择商品分类', icon: 'none' })
        return false
      }
      
      if (this.formData.productName.trim() === '') {
        uni.showToast({ title: '请输入商品名称', icon: 'none' })
        return false
      }
      
      if (this.formData.mainImage === '') {
        uni.showToast({ title: '请上传商品主图', icon: 'none' })
        return false
      }
      
      const currentPrice = parseFloat(this.formData.currentPrice)
      if (isNaN(currentPrice) || currentPrice <= 0) {
        uni.showToast({ title: '请输入有效的商品价格', icon: 'none' })
        return false
      }
      
      if (this.skuList.length === 0) {
        uni.showToast({ title: '请至少添加一个规格', icon: 'none' })
        return false
      }
      
      return true
    },
    
    // 提交表单
    handleSubmit() {
      if (this.isSubmitting) return
      
      if (!this.validateForm()) return
      
      this.isSubmitting = true
      
      const currentPrice = parseFloat(this.formData.currentPrice)
      const originalPriceStr = this.formData.originalPrice.trim()
      const originalPrice = originalPriceStr !== '' ? parseFloat(originalPriceStr) : null
      
      const productData: ProductFormData = {
        categoryId: this.formData.categoryId,
        productName: this.formData.productName.trim(),
        mainImage: this.formData.mainImage,
        description: this.formData.description.trim() !== '' ? this.formData.description.trim() : null,
        detail: this.formData.detail.trim() !== '' ? this.formData.detail.trim() : null,
        originalPrice: originalPrice,
        currentPrice: currentPrice,
        isRecommended: null,
        isNew: null,
        isHot: null,
        images: null,
        skuList: this.skuList
      }
      
      if (this.isEditMode) {
        // 更新商品
        updateProduct(this.productId, productData).then(() => {
          this.isSubmitting = false
          uni.showToast({ title: '保存成功', icon: 'success' })
          setTimeout(() => {
            uni.navigateBack({})
          }, 1500)
        }).catch((err) => {
          this.isSubmitting = false
          console.error('更新商品失败:', err, " at pages/seller/product-edit.uvue:598")
          uni.showToast({ title: '保存失败', icon: 'none' })
        })
      } else {
        // 创建商品
        createProduct(productData).then(() => {
          this.isSubmitting = false
          uni.showToast({ title: '发布成功', icon: 'success' })
          setTimeout(() => {
            uni.navigateBack({})
          }, 1500)
        }).catch((err) => {
          this.isSubmitting = false
          console.error('创建商品失败:', err, " at pages/seller/product-edit.uvue:611")
          uni.showToast({ title: '发布失败', icon: 'none' })
        })
      }
    }
  }
})

export default __sfc__
function GenPagesSellerProductEditRender(this: InstanceType<typeof __sfc__>): any | null {
const _ctx = this
const _cache = this.$.renderCache
  return createElementVNode("view", utsMapOf({ class: "page" }), [
    createElementVNode("view", utsMapOf({ class: "nav-bar" }), [
      createElementVNode("view", utsMapOf({
        class: "nav-left",
        onClick: _ctx.goBack
      }), [
        createElementVNode("text", utsMapOf({ class: "nav-back" }), "←")
      ], 8 /* PROPS */, ["onClick"]),
      createElementVNode("text", utsMapOf({ class: "nav-title" }), toDisplayString(_ctx.isEditMode ? '编辑商品' : '发布商品'), 1 /* TEXT */),
      createElementVNode("view", utsMapOf({ class: "nav-right" }))
    ]),
    createElementVNode("scroll-view", utsMapOf({
      class: "form-scroll",
      "scroll-y": "true",
      "show-scrollbar": false
    }), [
      createElementVNode("view", utsMapOf({ class: "form-section" }), [
        createElementVNode("view", utsMapOf({ class: "section-title" }), [
          createElementVNode("text", utsMapOf({ class: "section-title-text" }), "基本信息")
        ]),
        createElementVNode("view", utsMapOf({
          class: "form-item",
          onClick: _ctx.showCategoryPicker
        }), [
          createElementVNode("text", utsMapOf({ class: "form-label" }), [
            createElementVNode("text", utsMapOf({ class: "required" }), "*"),
            "商品分类"
          ]),
          createElementVNode("view", utsMapOf({ class: "form-value" }), [
            createElementVNode("text", utsMapOf({
              class: normalizeClass(["form-value-text", utsMapOf({ placeholder: _ctx.selectedCategoryName === '' })])
            }), toDisplayString(_ctx.selectedCategoryName !== '' ? _ctx.selectedCategoryName : '请选择分类'), 3 /* TEXT, CLASS */),
            createElementVNode("text", utsMapOf({ class: "form-arrow" }), "›")
          ])
        ], 8 /* PROPS */, ["onClick"]),
        createElementVNode("view", utsMapOf({ class: "form-item" }), [
          createElementVNode("text", utsMapOf({ class: "form-label" }), [
            createElementVNode("text", utsMapOf({ class: "required" }), "*"),
            "商品名称"
          ]),
          createElementVNode("input", utsMapOf({
            class: "form-input",
            modelValue: _ctx.formData.productName,
            onInput: ($event: InputEvent) => {(_ctx.formData.productName) = $event.detail.value},
            placeholder: "请输入商品名称",
            maxlength: "100"
          }), null, 40 /* PROPS, NEED_HYDRATION */, ["modelValue", "onInput"])
        ]),
        createElementVNode("view", utsMapOf({ class: "form-item form-item-textarea" }), [
          createElementVNode("text", utsMapOf({ class: "form-label" }), "商品描述"),
          createElementVNode("textarea", utsMapOf({
            class: "form-textarea",
            modelValue: _ctx.formData.description,
            onInput: ($event: InputEvent) => {(_ctx.formData.description) = $event.detail.value},
            placeholder: "请输入商品描述",
            maxlength: "500"
          }), null, 40 /* PROPS, NEED_HYDRATION */, ["modelValue", "onInput"])
        ])
      ]),
      createElementVNode("view", utsMapOf({ class: "form-section" }), [
        createElementVNode("view", utsMapOf({ class: "section-title" }), [
          createElementVNode("text", utsMapOf({ class: "section-title-text" }), "商品图片")
        ]),
        createElementVNode("view", utsMapOf({ class: "form-item" }), [
          createElementVNode("text", utsMapOf({ class: "form-label" }), [
            createElementVNode("text", utsMapOf({ class: "required" }), "*"),
            "商品主图"
          ])
        ]),
        createElementVNode("view", utsMapOf({ class: "image-upload-area" }), [
          _ctx.formData.mainImage !== ''
            ? createElementVNode("view", utsMapOf({
                key: 0,
                class: "image-preview"
              }), [
                createElementVNode("image", utsMapOf({
                  class: "preview-img",
                  src: _ctx.formData.mainImage,
                  mode: "aspectFill"
                }), null, 8 /* PROPS */, ["src"]),
                createElementVNode("view", utsMapOf({
                  class: "image-delete",
                  onClick: _ctx.deleteMainImage
                }), [
                  createElementVNode("text", utsMapOf({ class: "delete-icon" }), "×")
                ], 8 /* PROPS */, ["onClick"])
              ])
            : createElementVNode("view", utsMapOf({
                key: 1,
                class: "upload-btn",
                onClick: _ctx.chooseMainImage
              }), [
                createElementVNode("text", utsMapOf({ class: "upload-icon" }), "+"),
                createElementVNode("text", utsMapOf({ class: "upload-text" }), "上传主图")
              ], 8 /* PROPS */, ["onClick"])
        ])
      ]),
      createElementVNode("view", utsMapOf({ class: "form-section" }), [
        createElementVNode("view", utsMapOf({ class: "section-title" }), [
          createElementVNode("text", utsMapOf({ class: "section-title-text" }), "价格信息")
        ]),
        createElementVNode("view", utsMapOf({ class: "form-item" }), [
          createElementVNode("text", utsMapOf({ class: "form-label" }), [
            createElementVNode("text", utsMapOf({ class: "required" }), "*"),
            "现价"
          ]),
          createElementVNode("view", utsMapOf({ class: "price-input-wrapper" }), [
            createElementVNode("text", utsMapOf({ class: "price-unit" }), "¥"),
            createElementVNode("input", utsMapOf({
              class: "price-input",
              modelValue: _ctx.formData.currentPrice,
              onInput: ($event: InputEvent) => {(_ctx.formData.currentPrice) = $event.detail.value},
              type: "digit",
              placeholder: "0.00"
            }), null, 40 /* PROPS, NEED_HYDRATION */, ["modelValue", "onInput"])
          ])
        ]),
        createElementVNode("view", utsMapOf({ class: "form-item" }), [
          createElementVNode("text", utsMapOf({ class: "form-label" }), "原价"),
          createElementVNode("view", utsMapOf({ class: "price-input-wrapper" }), [
            createElementVNode("text", utsMapOf({ class: "price-unit" }), "¥"),
            createElementVNode("input", utsMapOf({
              class: "price-input",
              modelValue: _ctx.formData.originalPrice,
              onInput: ($event: InputEvent) => {(_ctx.formData.originalPrice) = $event.detail.value},
              type: "digit",
              placeholder: "0.00"
            }), null, 40 /* PROPS, NEED_HYDRATION */, ["modelValue", "onInput"])
          ])
        ])
      ]),
      createElementVNode("view", utsMapOf({ class: "form-section" }), [
        createElementVNode("view", utsMapOf({ class: "section-title" }), [
          createElementVNode("text", utsMapOf({ class: "section-title-text" }), "规格管理"),
          createElementVNode("view", utsMapOf({
            class: "add-sku-btn",
            onClick: _ctx.showAddSkuModal
          }), [
            createElementVNode("text", utsMapOf({ class: "add-sku-text" }), "+ 添加规格")
          ], 8 /* PROPS */, ["onClick"])
        ]),
        _ctx.skuList.length > 0
          ? createElementVNode("view", utsMapOf({
              key: 0,
              class: "sku-list"
            }), [
              createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.skuList, (sku, index, __index, _cached): any => {
                return createElementVNode("view", utsMapOf({
                  class: "sku-item",
                  key: index
                }), [
                  createElementVNode("view", utsMapOf({ class: "sku-info" }), [
                    createElementVNode("text", utsMapOf({ class: "sku-name" }), toDisplayString(sku.skuName), 1 /* TEXT */),
                    createElementVNode("view", utsMapOf({ class: "sku-details" }), [
                      createElementVNode("text", utsMapOf({ class: "sku-price" }), "¥" + toDisplayString(sku.price.toFixed(2)), 1 /* TEXT */),
                      createElementVNode("text", utsMapOf({ class: "sku-stock" }), "库存: " + toDisplayString(sku.stock), 1 /* TEXT */)
                    ])
                  ]),
                  createElementVNode("view", utsMapOf({ class: "sku-actions" }), [
                    createElementVNode("view", utsMapOf({
                      class: "sku-action-btn",
                      onClick: () => {_ctx.editSku(index)}
                    }), [
                      createElementVNode("text", utsMapOf({ class: "sku-action-text" }), "编辑")
                    ], 8 /* PROPS */, ["onClick"]),
                    createElementVNode("view", utsMapOf({
                      class: "sku-action-btn sku-action-btn-delete",
                      onClick: () => {_ctx.deleteSku(index)}
                    }), [
                      createElementVNode("text", utsMapOf({ class: "sku-action-text sku-action-text-delete" }), "删除")
                    ], 8 /* PROPS */, ["onClick"])
                  ])
                ])
              }), 128 /* KEYED_FRAGMENT */)
            ])
          : createElementVNode("view", utsMapOf({
              key: 1,
              class: "empty-sku"
            }), [
              createElementVNode("text", utsMapOf({ class: "empty-sku-text" }), "暂无规格，请添加至少一个规格")
            ])
      ]),
      createElementVNode("view", utsMapOf({
        style: normalizeStyle(utsMapOf({"height":"100px"}))
      }), null, 4 /* STYLE */)
    ]),
    createElementVNode("view", utsMapOf({ class: "submit-bar" }), [
      createElementVNode("view", utsMapOf({
        class: normalizeClass(["submit-btn", utsMapOf({ disabled: _ctx.isSubmitting })]),
        onClick: _ctx.handleSubmit
      }), [
        createElementVNode("text", utsMapOf({ class: "submit-btn-text" }), toDisplayString(_ctx.isSubmitting ? '提交中...' : (_ctx.isEditMode ? '保存修改' : '发布商品')), 1 /* TEXT */)
      ], 10 /* CLASS, PROPS */, ["onClick"])
    ]),
    isTrue(_ctx.showCategoryModal)
      ? createElementVNode("view", utsMapOf({
          key: 0,
          class: "modal-mask",
          onClick: _ctx.closeCategoryModal
        }), [
          createElementVNode("view", utsMapOf({
            class: "modal-content category-modal",
            onClick: withModifiers(() => {}, ["stop"])
          }), [
            createElementVNode("view", utsMapOf({ class: "modal-header" }), [
              createElementVNode("text", utsMapOf({ class: "modal-title" }), "选择分类"),
              createElementVNode("view", utsMapOf({
                class: "modal-close",
                onClick: _ctx.closeCategoryModal
              }), [
                createElementVNode("text", utsMapOf({ class: "close-icon" }), "×")
              ], 8 /* PROPS */, ["onClick"])
            ]),
            createElementVNode("scroll-view", utsMapOf({
              class: "category-list",
              "scroll-y": "true",
              "show-scrollbar": false
            }), [
              createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.categories, (category, __key, __index, _cached): any => {
                return createElementVNode("view", utsMapOf({
                  class: "category-item",
                  key: category.id,
                  onClick: () => {_ctx.selectCategory(category)}
                }), [
                  createElementVNode("text", utsMapOf({ class: "category-name" }), toDisplayString(category.categoryName), 1 /* TEXT */),
                  _ctx.formData.categoryId === category.id
                    ? createElementVNode("text", utsMapOf({
                        key: 0,
                        class: "category-check"
                      }), "✓")
                    : createCommentVNode("v-if", true)
                ], 8 /* PROPS */, ["onClick"])
              }), 128 /* KEYED_FRAGMENT */)
            ])
          ], 8 /* PROPS */, ["onClick"])
        ], 8 /* PROPS */, ["onClick"])
      : createCommentVNode("v-if", true),
    isTrue(_ctx.showSkuModal)
      ? createElementVNode("view", utsMapOf({
          key: 1,
          class: "modal-mask",
          onClick: _ctx.closeSkuModal
        }), [
          createElementVNode("view", utsMapOf({
            class: "modal-content sku-modal",
            onClick: withModifiers(() => {}, ["stop"])
          }), [
            createElementVNode("view", utsMapOf({ class: "modal-header" }), [
              createElementVNode("text", utsMapOf({ class: "modal-title" }), toDisplayString(_ctx.editingSkuIndex >= 0 ? '编辑规格' : '添加规格'), 1 /* TEXT */),
              createElementVNode("view", utsMapOf({
                class: "modal-close",
                onClick: _ctx.closeSkuModal
              }), [
                createElementVNode("text", utsMapOf({ class: "close-icon" }), "×")
              ], 8 /* PROPS */, ["onClick"])
            ]),
            createElementVNode("view", utsMapOf({ class: "sku-form" }), [
              createElementVNode("view", utsMapOf({ class: "sku-form-item" }), [
                createElementVNode("text", utsMapOf({ class: "sku-form-label" }), [
                  createElementVNode("text", utsMapOf({ class: "required" }), "*"),
                  "规格名称"
                ]),
                createElementVNode("input", utsMapOf({
                  class: "sku-form-input",
                  modelValue: _ctx.skuForm.skuName,
                  onInput: ($event: InputEvent) => {(_ctx.skuForm.skuName) = $event.detail.value},
                  placeholder: "如：500g装"
                }), null, 40 /* PROPS, NEED_HYDRATION */, ["modelValue", "onInput"])
              ]),
              createElementVNode("view", utsMapOf({ class: "sku-form-item" }), [
                createElementVNode("text", utsMapOf({ class: "sku-form-label" }), [
                  createElementVNode("text", utsMapOf({ class: "required" }), "*"),
                  "价格"
                ]),
                createElementVNode("view", utsMapOf({ class: "price-input-wrapper" }), [
                  createElementVNode("text", utsMapOf({ class: "price-unit" }), "¥"),
                  createElementVNode("input", utsMapOf({
                    class: "sku-form-input price-input",
                    modelValue: _ctx.skuForm.price,
                    onInput: ($event: InputEvent) => {(_ctx.skuForm.price) = $event.detail.value},
                    type: "digit",
                    placeholder: "0.00"
                  }), null, 40 /* PROPS, NEED_HYDRATION */, ["modelValue", "onInput"])
                ])
              ]),
              createElementVNode("view", utsMapOf({ class: "sku-form-item" }), [
                createElementVNode("text", utsMapOf({ class: "sku-form-label" }), [
                  createElementVNode("text", utsMapOf({ class: "required" }), "*"),
                  "库存"
                ]),
                createElementVNode("input", utsMapOf({
                  class: "sku-form-input",
                  modelValue: _ctx.skuForm.stock,
                  onInput: ($event: InputEvent) => {(_ctx.skuForm.stock) = $event.detail.value},
                  type: "number",
                  placeholder: "0"
                }), null, 40 /* PROPS, NEED_HYDRATION */, ["modelValue", "onInput"])
              ]),
              createElementVNode("view", utsMapOf({ class: "sku-form-item" }), [
                createElementVNode("text", utsMapOf({ class: "sku-form-label" }), "规格信息"),
                createElementVNode("input", utsMapOf({
                  class: "sku-form-input",
                  modelValue: _ctx.skuForm.specInfo,
                  onInput: ($event: InputEvent) => {(_ctx.skuForm.specInfo) = $event.detail.value},
                  placeholder: "如：重量:500g"
                }), null, 40 /* PROPS, NEED_HYDRATION */, ["modelValue", "onInput"])
              ])
            ]),
            createElementVNode("view", utsMapOf({ class: "modal-footer" }), [
              createElementVNode("view", utsMapOf({
                class: "modal-btn modal-btn-cancel",
                onClick: _ctx.closeSkuModal
              }), [
                createElementVNode("text", utsMapOf({ class: "modal-btn-text" }), "取消")
              ], 8 /* PROPS */, ["onClick"]),
              createElementVNode("view", utsMapOf({
                class: "modal-btn modal-btn-confirm",
                onClick: _ctx.saveSku
              }), [
                createElementVNode("text", utsMapOf({ class: "modal-btn-text modal-btn-text-confirm" }), "确定")
              ], 8 /* PROPS */, ["onClick"])
            ])
          ], 8 /* PROPS */, ["onClick"])
        ], 8 /* PROPS */, ["onClick"])
      : createCommentVNode("v-if", true)
  ])
}
const GenPagesSellerProductEditStyles = [utsMapOf([["page", padStyleMapOf(utsMapOf([["backgroundColor", "#f7f8fa"], ["display", "flex"], ["flexDirection", "column"], ["flex", 1]]))], ["nav-bar", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "space-between"], ["paddingTop", CSS_VAR_STATUS_BAR_HEIGHT], ["paddingRight", 16], ["paddingBottom", 0], ["paddingLeft", 16], ["height", 44], ["backgroundColor", "#0066CC"]]))], ["nav-left", padStyleMapOf(utsMapOf([["width", 60]]))], ["nav-back", padStyleMapOf(utsMapOf([["fontSize", 24], ["color", "#ffffff"]]))], ["nav-title", padStyleMapOf(utsMapOf([["fontSize", 17], ["fontWeight", "700"], ["color", "#ffffff"]]))], ["nav-right", padStyleMapOf(utsMapOf([["width", 60]]))], ["form-scroll", padStyleMapOf(utsMapOf([["flex", 1], ["width", "100%"]]))], ["form-section", padStyleMapOf(utsMapOf([["backgroundColor", "#ffffff"], ["marginTop", 12], ["marginRight", 16], ["marginBottom", 12], ["marginLeft", 16], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12], ["paddingTop", 16], ["paddingRight", 16], ["paddingBottom", 16], ["paddingLeft", 16]]))], ["section-title", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "space-between"], ["marginBottom", 16]]))], ["section-title-text", padStyleMapOf(utsMapOf([["fontSize", 16], ["fontWeight", "700"], ["color", "#333333"]]))], ["form-item", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "space-between"], ["paddingTop", 12], ["paddingRight", 0], ["paddingBottom", 12], ["paddingLeft", 0], ["borderBottomWidth", 1], ["borderBottomStyle", "solid"], ["borderBottomColor", "#f5f5f5"]]))], ["form-item-textarea", padStyleMapOf(utsMapOf([["flexDirection", "column"], ["alignItems", "flex-start"]]))], ["form-label", padStyleMapOf(utsMapOf([["width", 80], ["fontSize", 14], ["color", "#333333"], ["flexShrink", 0]]))], ["required", padStyleMapOf(utsMapOf([["color", "#ff4d4f"], ["marginRight", 2]]))], ["form-value", padStyleMapOf(utsMapOf([["flex", 1], ["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "flex-end"]]))], ["form-value-text", padStyleMapOf(utsMapOf([["fontSize", 14], ["color", "#333333"], ["textAlign", "right"]]))], ["form-value-text-placeholder", padStyleMapOf(utsMapOf([["color", "#999999"]]))], ["form-arrow", padStyleMapOf(utsMapOf([["fontSize", 18], ["color", "#999999"], ["marginLeft", 8]]))], ["form-input", padStyleMapOf(utsMapOf([["flex", 1], ["fontSize", 14], ["color", "#333333"], ["textAlign", "right"], ["height", 40]]))], ["form-textarea", padStyleMapOf(utsMapOf([["width", "100%"], ["height", 80], ["fontSize", 14], ["color", "#333333"], ["marginTop", 8], ["paddingTop", 8], ["paddingRight", 8], ["paddingBottom", 8], ["paddingLeft", 8], ["backgroundColor", "#f9f9f9"], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8]]))], ["price-input-wrapper", padStyleMapOf(utsMapOf([["flex", 1], ["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["backgroundColor", "#f9f9f9"], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["paddingTop", 0], ["paddingRight", 10], ["paddingBottom", 0], ["paddingLeft", 10], ["height", 40], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#eeeeee"], ["borderRightColor", "#eeeeee"], ["borderBottomColor", "#eeeeee"], ["borderLeftColor", "#eeeeee"]]))], ["price-unit", padStyleMapOf(utsMapOf([["fontSize", 16], ["color", "#ff4d4f"], ["fontWeight", "700"], ["marginRight", 4]]))], ["price-input", padStyleMapOf(utsMapOf([["width", "100%"], ["height", 40], ["fontSize", 16], ["color", "#333333"], ["textAlign", "left"]]))], ["image-upload-area", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["flexWrap", "wrap"], ["marginTop", 8]]))], ["image-preview", padStyleMapOf(utsMapOf([["position", "relative"], ["width", 100], ["height", 100], ["marginRight", 12], ["marginBottom", 12]]))], ["preview-img", padStyleMapOf(utsMapOf([["width", 100], ["height", 100], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#f5f5f5"]]))], ["image-delete", padStyleMapOf(utsMapOf([["position", "absolute"], ["top", -8], ["right", -8], ["width", 20], ["height", 20], ["backgroundColor", "#ff4d4f"], ["borderTopLeftRadius", 10], ["borderTopRightRadius", 10], ["borderBottomRightRadius", 10], ["borderBottomLeftRadius", 10], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["delete-icon", padStyleMapOf(utsMapOf([["fontSize", 14], ["color", "#ffffff"]]))], ["upload-btn", padStyleMapOf(utsMapOf([["width", 100], ["height", 100], ["backgroundColor", "#f9f9f9"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "dashed"], ["borderRightStyle", "dashed"], ["borderBottomStyle", "dashed"], ["borderLeftStyle", "dashed"], ["borderTopColor", "#dddddd"], ["borderRightColor", "#dddddd"], ["borderBottomColor", "#dddddd"], ["borderLeftColor", "#dddddd"], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["display", "flex"], ["flexDirection", "column"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["upload-icon", padStyleMapOf(utsMapOf([["fontSize", 28], ["color", "#999999"]]))], ["upload-text", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "#999999"], ["marginTop", 4]]))], ["add-sku-btn", padStyleMapOf(utsMapOf([["paddingTop", 6], ["paddingRight", 12], ["paddingBottom", 6], ["paddingLeft", 12], ["backgroundColor", "#e6f7ff"], ["borderTopLeftRadius", 14], ["borderTopRightRadius", 14], ["borderBottomRightRadius", 14], ["borderBottomLeftRadius", 14]]))], ["add-sku-text", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "#0066CC"]]))], ["sku-list", padStyleMapOf(utsMapOf([["marginTop", 8]]))], ["sku-item", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "space-between"], ["paddingTop", 12], ["paddingRight", 12], ["paddingBottom", 12], ["paddingLeft", 12], ["backgroundColor", "#f9f9f9"], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["marginBottom", 8]]))], ["sku-info", padStyleMapOf(utsMapOf([["flex", 1]]))], ["sku-name", padStyleMapOf(utsMapOf([["fontSize", 14], ["fontWeight", "700"], ["color", "#333333"]]))], ["sku-details", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["marginTop", 6]]))], ["sku-price", padStyleMapOf(utsMapOf([["fontSize", 14], ["color", "#ff4d4f"], ["marginRight", 16]]))], ["sku-stock", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "#999999"]]))], ["sku-actions", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"]]))], ["sku-action-btn", padStyleMapOf(utsMapOf([["paddingTop", 4], ["paddingRight", 12], ["paddingBottom", 4], ["paddingLeft", 12], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#0066CC"], ["borderRightColor", "#0066CC"], ["borderBottomColor", "#0066CC"], ["borderLeftColor", "#0066CC"], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12], ["marginLeft", 8]]))], ["sku-action-btn-delete", padStyleMapOf(utsMapOf([["borderTopColor", "#ff4d4f"], ["borderRightColor", "#ff4d4f"], ["borderBottomColor", "#ff4d4f"], ["borderLeftColor", "#ff4d4f"]]))], ["sku-action-text", padStyleMapOf(utsMapOf([["fontSize", 12], ["color", "#0066CC"]]))], ["sku-action-text-delete", padStyleMapOf(utsMapOf([["color", "#ff4d4f"]]))], ["empty-sku", padStyleMapOf(utsMapOf([["paddingTop", 24], ["paddingRight", 0], ["paddingBottom", 24], ["paddingLeft", 0], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["empty-sku-text", padStyleMapOf(utsMapOf([["fontSize", 13], ["color", "#999999"]]))], ["submit-bar", padStyleMapOf(utsMapOf([["position", "fixed"], ["bottom", 0], ["left", 0], ["right", 0], ["paddingTop", 12], ["paddingRight", 16], ["paddingBottom", 24], ["paddingLeft", 16], ["backgroundColor", "#ffffff"], ["boxShadow", "0 -2px 8px rgba(0, 0, 0, 0.05)"]]))], ["submit-btn", padStyleMapOf(utsMapOf([["height", 48], ["backgroundColor", "#0066CC"], ["borderTopLeftRadius", 24], ["borderTopRightRadius", 24], ["borderBottomRightRadius", 24], ["borderBottomLeftRadius", 24], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["submit-btn-disabled", padStyleMapOf(utsMapOf([["backgroundColor", "#cccccc"]]))], ["submit-btn-text", padStyleMapOf(utsMapOf([["fontSize", 16], ["fontWeight", "700"], ["color", "#ffffff"]]))], ["modal-mask", padStyleMapOf(utsMapOf([["position", "fixed"], ["top", 0], ["left", 0], ["right", 0], ["bottom", 0], ["backgroundColor", "rgba(0,0,0,0.5)"], ["display", "flex"], ["alignItems", "flex-end"], ["justifyContent", "center"], ["zIndex", 1000]]))], ["modal-content", padStyleMapOf(utsMapOf([["width", "100%"], ["backgroundColor", "#ffffff"], ["borderTopLeftRadius", 16], ["borderTopRightRadius", 16], ["borderBottomRightRadius", 0], ["borderBottomLeftRadius", 0], ["maxHeight", 500]]))], ["modal-header", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "space-between"], ["paddingTop", 16], ["paddingRight", 16], ["paddingBottom", 16], ["paddingLeft", 16], ["borderBottomWidth", 1], ["borderBottomStyle", "solid"], ["borderBottomColor", "#f5f5f5"]]))], ["modal-title", padStyleMapOf(utsMapOf([["fontSize", 16], ["fontWeight", "700"], ["color", "#333333"]]))], ["modal-close", padStyleMapOf(utsMapOf([["width", 28], ["height", 28], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["close-icon", padStyleMapOf(utsMapOf([["fontSize", 24], ["color", "#999999"]]))], ["category-modal", padStyleMapOf(utsMapOf([["maxHeight", 450]]))], ["category-list", padStyleMapOf(utsMapOf([["maxHeight", 400]]))], ["category-item", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "space-between"], ["paddingTop", 14], ["paddingRight", 16], ["paddingBottom", 14], ["paddingLeft", 16], ["borderBottomWidth", 1], ["borderBottomStyle", "solid"], ["borderBottomColor", "#f5f5f5"]]))], ["category-name", padStyleMapOf(utsMapOf([["fontSize", 14], ["color", "#333333"]]))], ["category-check", padStyleMapOf(utsMapOf([["fontSize", 16], ["color", "#0066CC"]]))], ["sku-modal", padStyleMapOf(utsMapOf([["maxHeight", 600]]))], ["sku-form", padStyleMapOf(utsMapOf([["paddingTop", 16], ["paddingRight", 16], ["paddingBottom", 16], ["paddingLeft", 16]]))], ["sku-form-item", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["marginBottom", 16]]))], ["sku-form-label", padStyleMapOf(utsMapOf([["width", 80], ["fontSize", 14], ["color", "#333333"], ["flexShrink", 0]]))], ["sku-form-input", padStyleMapOf(utsMapOf([["flex", 1], ["height", 40], ["paddingTop", 0], ["paddingRight", 12], ["paddingBottom", 0], ["paddingLeft", 12], ["backgroundColor", "#f9f9f9"], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["fontSize", 14], ["color", "#333333"]]))], ["modal-footer", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["paddingTop", 16], ["paddingRight", 16], ["paddingBottom", 16], ["paddingLeft", 16], ["borderTopWidth", 1], ["borderTopStyle", "solid"], ["borderTopColor", "#f5f5f5"]]))], ["modal-btn", padStyleMapOf(utsMapOf([["flex", 1], ["height", 44], ["borderTopLeftRadius", 22], ["borderTopRightRadius", 22], ["borderBottomRightRadius", 22], ["borderBottomLeftRadius", 22], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["modal-btn-cancel", padStyleMapOf(utsMapOf([["backgroundColor", "#f5f5f5"], ["marginRight", 12]]))], ["modal-btn-confirm", padStyleMapOf(utsMapOf([["backgroundColor", "#0066CC"]]))], ["modal-btn-text", padStyleMapOf(utsMapOf([["fontSize", 15], ["color", "#666666"]]))], ["modal-btn-text-confirm", padStyleMapOf(utsMapOf([["color", "#ffffff"]]))]])]
