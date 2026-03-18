
import { addToCart, type CartAddParams } from '@/api/cart'
import { getProductDetail, collectProduct, uncollectProduct, isProductCollected } from '@/api/product'
import { getProductReviews, type ReviewVO } from '@/api/review'
import { recordBrowse } from '@/api/browseHistory'
import { get } from '@/utils/request'

type ProductSku = { __$originalPosition?: UTSSourceMapPosition<"ProductSku", "pages/product/detail.uvue", 236, 6>;
  id : number
  productId : number
  skuName : string
  skuCode : string
  skuImage : string | null
  price : number
  stock : number
  salesVolume : number
  specInfo : string | null
  status : number
}

type ProductSpec = { __$originalPosition?: UTSSourceMapPosition<"ProductSpec", "pages/product/detail.uvue", 249, 6>;
  id : string
  name : string
  options : string[]
}

type Product = { __$originalPosition?: UTSSourceMapPosition<"Product", "pages/product/detail.uvue", 255, 6>;
  id : number
  name : string
  description : string
  price : number
  originalPrice : number | null
  image : string
  rating : number
  sales : number
  stock : number
  reviewCount : number
  isNew : boolean
  discount : boolean
  isFresh : boolean
  specs : Array<ProductSpec> | null
  detailImages : string[] | null
}

const __sfc__ = defineComponent({
  name: 'ProductDetail',
  data() {
    return {
      productId: 0,
      product: {
        id: 0,
        name: '',
        description: '',
        price: 0,
        originalPrice: null,
        image: '',
        rating: 5,
        sales: 0,
        stock: 0,
        reviewCount: 0,
        isNew: false,
        discount: false,
        isFresh: false,
        specs: [] as ProductSpec[],
        detailImages: [] as string[]
      } as Product,
      productImages: [] as string[],
      currentImageIndex: 0,
      selectedSpecs: new Map<string, string>(),
      quantity: 1,
      isFavorite: false,
      skuList: [] as ProductSku[],
      selectedSkuId: 0,
      reviews: [] as ReviewVO[]
    }
  },
  onLoad(options : any) {
    // 从路由参数获取商品ID
    const opts = options as UTSJSONObject
    const queryId = opts.get('id')
    if (queryId != null) {
      const idStr = queryId as string
      const idNum = parseInt(idStr)
      this.productId = idNum > 0 ? idNum : 1
    }

    this.loadProductDetail()
    this.loadProductReviews()
    this.recordBrowseHistory()
    this.checkCollectionStatus()
    // 初始化默认规格
    this.initDefaultSpecs()
  },
  methods: {
    // 获取优惠金额
    getDiscountAmount() : string {
      const originalPrice = this.product.originalPrice
      if (originalPrice != null) {
        const discount = originalPrice - this.product.price
        return discount.toFixed(2)
      }
      return '0.00'
    },
    // 检查是否有规格
    hasSpecs() : boolean {
      const specs = this.product.specs
      return specs != null && specs.length > 0
    },
    /**
     * 记录浏览历史
     */
    recordBrowseHistory() {
      // 调用浏览历史接口
      recordBrowse(this.productId).then(() => {
        console.log('浏览历史记录成功', " at pages/product/detail.uvue:343")
      }).catch((err) => {
        console.error('记录浏览历史失败:', err, " at pages/product/detail.uvue:345")
      })
    },
    
    /**
     * 加载商品评价
     */
    loadProductReviews() {
      getProductReviews(this.productId, 1, 2).then((res) => {
        const data = res.data as UTSJSONObject
        const records = data.getArray('records')
        if (records != null && records.length > 0) {
          const reviews : ReviewVO[] = []
          for (let i = 0; i < records.length; i++) {
            const item = records[i] as UTSJSONObject
            reviews.push({
              id: (item.getNumber('id') ?? 0).toInt(),
              productId: (item.getNumber('productId') ?? 0).toInt(),
              productName: item.getString('productName') ?? '',
              productMainImage: item.getString('productMainImage') ?? '',
              userId: (item.getNumber('userId') ?? 0).toInt(),
              userNickname: item.getString('userNickname') ?? '',
              userAvatar: item.getString('userAvatar') ?? '',
              orderId: (item.getNumber('orderId') ?? 0).toInt(),
              rating: (item.getNumber('rating') ?? 5).toInt(),
              content: item.getString('content') ?? '',
              images: item.getString('images') ?? '',
              replyContent: item.getString('replyContent'),
              replyTime: item.getString('replyTime'),
              status: (item.getNumber('status') ?? 1).toInt(),
              createTime: item.getString('createTime') ?? ''
            } as ReviewVO)
          }
          this.reviews = reviews
        }
      }).catch((err) => {
        console.error('获取商品评价失败:', err, " at pages/product/detail.uvue:381")
      })
    },
    
    loadProductDetail() {
      getProductDetail(this.productId).then((res) => {
        const data = res.data as UTSJSONObject
        // 解析商品基本信息
        this.product.id = (data.getNumber('id') ?? 0).toInt()
        this.product.name = data.getString('productName') ?? ''
        this.product.description = data.getString('description') ?? ''
        this.product.price = data.getNumber('currentPrice') ?? 0
        this.product.originalPrice = data.getNumber('originalPrice')
        this.product.image = data.getString('mainImage') ?? ''
        this.product.rating = data.getNumber('avgRating') ?? 5
        this.product.sales = (data.getNumber('salesVolume') ?? 0).toInt()
        this.product.stock = (data.getNumber('stock') ?? 0).toInt()
        this.product.reviewCount = (data.getNumber('reviewCount') ?? 0).toInt()
        
        // 解析商品图片
        const imagesArr = data.getArray('images')
        const imgs : string[] = []
        
        if (imagesArr != null && imagesArr.length > 0) {
          for (let i = 0; i < imagesArr.length; i++) {
            const imgObj = imagesArr[i] as UTSJSONObject
            const imgUrl = imgObj.getString('imageUrl')
            if (imgUrl != null && imgUrl !== '') {
              imgs.push(imgUrl)
            }
          }
        }
        
        // 如果没有图片数组，使用主图
        if (imgs.length === 0) {
          const mainImage = data.getString('mainImage')
          if (mainImage != null && mainImage !== '') {
            imgs.push(mainImage)
          }
        }
        
        if (imgs.length > 0) {
          this.productImages = imgs
          this.currentImageIndex = 0  // 重置图片索引为0
          console.log('商品图片加载成功，共', imgs.length, '张', " at pages/product/detail.uvue:425")
          console.log('图片URL列表:', imgs, " at pages/product/detail.uvue:426")
        } else {
          console.log('警告：商品没有图片', " at pages/product/detail.uvue:428")
        }
        
        // 解析SKU列表并转换为UI规格展示
        const skuArr = data.getArray('skuList')
        if (skuArr != null && skuArr.length > 0) {
          const skus : ProductSku[] = []
          const specOptions : string[] = []

          for (let i = 0; i < skuArr.length; i++) {
            const skuObj = skuArr[i] as UTSJSONObject
            const skuName = skuObj.getString('skuName') ?? ''
            skus.push({
              id: (skuObj.getNumber('id') ?? 0).toInt(),
              productId: (skuObj.getNumber('productId') ?? 0).toInt(),
              skuName: skuName,
              skuCode: skuObj.getString('skuCode') ?? '',
              skuImage: skuObj.getString('skuImage'),
              price: skuObj.getNumber('price') ?? 0,
              stock: (skuObj.getNumber('stock') ?? 0).toInt(),
              salesVolume: (skuObj.getNumber('salesVolume') ?? 0).toInt(),
              specInfo: skuObj.getString('specInfo'),
              status: (skuObj.getNumber('status') ?? 1).toInt()
            } as ProductSku)

            if (skuName !== '') {
              specOptions.push(skuName)
            }
          }
          this.skuList = skus

          // 将扁平的SKU列表映射为UI需要的规格组
          if (specOptions.length > 0) {
            this.product.specs = [{
              id: 'sku',
              name: '规格',
              options: specOptions
            } as ProductSpec]

            // 默认选中第一个
            this.selectedSpecs.set('sku', specOptions[0])
            this.selectedSkuId = skus[0].id
            this.product.price = skus[0].price
            this.product.stock = skus[0].stock
          }
        }
        
        console.log('商品详情加载成功:', this.product.name, 'SKU数量:', this.skuList.length, " at pages/product/detail.uvue:475")
      }).catch((err) => {
        console.error('获取商品详情失败:', err, " at pages/product/detail.uvue:477")
      })
    },
    
    initDefaultSpecs() {
        const specs = this.product.specs
        if (specs != null) {
            specs.forEach((spec : ProductSpec) => {
                if (spec.options.length > 0) {
                    // 使用 Map 的 set 方法兼容 UTS 语法
                    this.selectedSpecs.set(spec.id, spec.options[0])
                }
            })
        }
    },

    // 检查规格是否被选中 - 用于模板中的动态类名
    isSpecSelected(specId : string, option : string) : boolean {
        return this.selectedSpecs.get(specId) === option
    },

    // 图片轮播变更
    handleImageChange(e : UniSwiperChangeEvent) {
      // swiper change 事件传递事件对象，需要从detail中获取current
      this.currentImageIndex = e.detail.current
    },

    // 预览轮播图
    previewImage(index : number) {
        uni.previewImage({
            urls: this.productImages,
            current: index
        })
    },
    
    // 预览详情图
    previewDetailImage(index : number) {
        const detailImages = this.product.detailImages
        if (detailImages != null) {
            uni.previewImage({
                urls: detailImages,
                current: index
            })
        }
    },

    // 选择规格
    selectSpec(specId : string, option : string) {
      // 使用 Map 的 set 方法兼容 UTS 语法
      this.selectedSpecs.set(specId, option)

      // 如果是单规格维度（目前对接的逻辑），根据选中的选项名称匹配 SKU
      if (specId === 'sku') {
        const selectedSku = this.skuList.find((item : ProductSku) : boolean => item.skuName === option)
        if (selectedSku != null) {
          this.selectedSkuId = selectedSku.id
          this.product.price = selectedSku.price
          this.product.stock = selectedSku.stock

          // 如果选择的数量超过了新规格的库存，重置为最大库存或1
          if (this.quantity > selectedSku.stock) {
            this.quantity = Math.max(1, selectedSku.stock)
          }
        }
      }
    },

    // 增加数量
    increaseQuantity() {
      if (this.quantity < this.product.stock) {
        this.quantity++
      }
    },

    // 减少数量
    decreaseQuantity() {
      if (this.quantity > 1) {
        this.quantity--
      }
    },
    
    /**
     * 检查商品收藏状态
     */
    checkCollectionStatus() {
      isProductCollected(this.productId).then((result) => {
        if (result.code === 200) {
          this.isFavorite = result.data as boolean
        }
      }).catch((error) => {
        console.error('检查收藏状态失败:', error, " at pages/product/detail.uvue:567")
      })
    },
    
    /**
     * 切换收藏状态
     */
    toggleFavorite() {
      const targetStatus = !this.isFavorite
      const apiCall = targetStatus ? collectProduct(this.productId) : uncollectProduct(this.productId)
      
      apiCall.then((result) => {
        if (result.code === 200) {
          this.isFavorite = targetStatus
          uni.showToast({
            title: targetStatus ? '已收藏' : '已取消收藏',
            icon: 'success'
          })
          // 触发刷新事件，更新个人中心的收藏数
          uni.$emit('refreshFavorites')
        } else {
          uni.showToast({
            title: result.message ?? '操作失败',
            icon: 'none'
          })
        }
      }).catch((error) => {
        console.error('收藏操作失败:', error, " at pages/product/detail.uvue:594")
        uni.showToast({
          title: '操作失败，请重试',
          icon: 'none'
        })
      })
    },

    // 获取当前选中的规格名称
    getSelectedSpecName() : string {
      const specs = this.product.specs
      if (specs == null || specs.length === 0) {
        return ''
      }
      const specNames : string[] = []
      specs.forEach((spec : ProductSpec) => {
        const selected = this.selectedSpecs.get(spec.id)
        if (selected != null) {
          specNames.push(selected)
        }
      })
      return specNames.join(' ')
    },
    
    // 加入购物车
    handleAddToCart() {
      // 使用选中的SKU ID，如果没有SKU则使用商品ID
      const skuId = this.selectedSkuId > 0 ? this.selectedSkuId : this.productId
      const params : CartAddParams = {
        productId: this.productId,
        skuId: skuId,
        quantity: this.quantity
      }
      console.log('添加购物车参数:', JSON.stringify(params), " at pages/product/detail.uvue:627")
      addToCart(params).then(() => {
        uni.showToast({
          title: '已添加到购物车',
          icon: 'success',
          duration: 1500
        })
        // 通知底部导航栏刷新数量
        uni.$emit('bottomNavRefresh')
      }).catch((err) => {
        console.error('添加购物车失败:', err, " at pages/product/detail.uvue:637")
        uni.showToast({
          title: '添加失败',
          icon: 'none'
        })
      })
    },

    // 立即购买
    buyNow() {
      // 构建跳转参数
      const skuId = this.selectedSkuId > 0 ? this.selectedSkuId : this.productId
      const skuName = this.getSelectedSpecName()
      const params = `type=direct&productId=${this.productId}&skuId=${skuId}&quantity=${this.quantity}&productName=${UTSAndroid.consoleDebugError(encodeURIComponent(this.product.name), " at pages/product/detail.uvue:650")}&skuName=${UTSAndroid.consoleDebugError(encodeURIComponent(skuName), " at pages/product/detail.uvue:650")}&price=${this.product.price}&productImage=${UTSAndroid.consoleDebugError(encodeURIComponent(this.product.image), " at pages/product/detail.uvue:650")}`
      uni.navigateTo({
        url: `/pages/order/checkout?${params}`
      })
    },
    
    viewAllReviews() {
      uni.navigateTo({
        url: `/pages/product/reviews?productId=${this.productId}&productName=${UTSAndroid.consoleDebugError(encodeURIComponent(this.product.name), " at pages/product/detail.uvue:658")}`
      })
    }
  }
})

export default __sfc__
function GenPagesProductDetailRender(this: InstanceType<typeof __sfc__>): any | null {
const _ctx = this
const _cache = this.$.renderCache
  return createElementVNode("view", utsMapOf({ class: "product-detail-page" }), [
    createElementVNode("scroll-view", utsMapOf({
      "scroll-y": "true",
      class: "product-detail__scroll"
    }), [
      createElementVNode("view", utsMapOf({ class: "product-detail__carousel" }), [
        _ctx.productImages.length === 0
          ? createElementVNode("view", utsMapOf({
              key: 0,
              class: "product-detail__loading"
            }), [
              createElementVNode("text", utsMapOf({ class: "loading-text" }), "加载中...")
            ])
          : createElementVNode("swiper", utsMapOf({
              key: 1,
              current: _ctx.currentImageIndex,
              class: "product-detail__swiper",
              circular: false,
              autoplay: false,
              interval: 4000,
              duration: 500,
              onChange: _ctx.handleImageChange
            }), [
              createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.productImages, (image, index, __index, _cached): any => {
                return createElementVNode("swiper-item", utsMapOf({
                  key: image,
                  class: "product-detail__swiper-item",
                  onClick: () => {_ctx.previewImage(index)}
                }), [
                  createElementVNode("image", utsMapOf({
                    src: image,
                    class: "product-detail__image",
                    mode: "aspectFill",
                    "show-menu-by-longpress": true
                  }), null, 8 /* PROPS */, ["src"])
                ], 8 /* PROPS */, ["onClick"])
              }), 128 /* KEYED_FRAGMENT */)
            ], 40 /* PROPS, NEED_HYDRATION */, ["current", "onChange"]),
        _ctx.productImages.length > 0
          ? createElementVNode("view", utsMapOf({
              key: 2,
              class: "product-detail__image-indicator"
            }), [
              createElementVNode("text", utsMapOf({ class: "product-detail__indicator-text" }), toDisplayString(_ctx.currentImageIndex + 1) + " / " + toDisplayString(_ctx.productImages.length), 1 /* TEXT */)
            ])
          : createCommentVNode("v-if", true)
      ]),
      createElementVNode("view", utsMapOf({ class: "product-detail__info-section" }), [
        createElementVNode("view", utsMapOf({ class: "product-detail__price-row" }), [
          createElementVNode("view", utsMapOf({ class: "product-detail__price-group" }), [
            createElementVNode("text", utsMapOf({ class: "product-detail__currency" }), "¥"),
            createElementVNode("text", utsMapOf({ class: "product-detail__current-price" }), toDisplayString(_ctx.product.price), 1 /* TEXT */),
            isTrue(_ctx.product.originalPrice)
              ? createElementVNode("text", utsMapOf({
                  key: 0,
                  class: "product-detail__original-price"
                }), " ¥" + toDisplayString(_ctx.product.originalPrice), 1 /* TEXT */)
              : createCommentVNode("v-if", true)
          ]),
          createElementVNode("text", utsMapOf({ class: "product-detail__sales" }), " 已售 " + toDisplayString(_ctx.product.sales) + " 件 ", 1 /* TEXT */)
        ]),
        isTrue(_ctx.product.isNew || _ctx.product.discount || _ctx.product.isFresh)
          ? createElementVNode("view", utsMapOf({
              key: 0,
              class: "product-detail__tags-row"
            }), [
              isTrue(_ctx.product.discount)
                ? createElementVNode("view", utsMapOf({
                    key: 0,
                    class: "product-detail__tag product-detail__tag--discount"
                  }), [
                    createElementVNode("text", utsMapOf({ class: "product-detail__tag-text product-detail__tag-text--discount" }), "省 ¥" + toDisplayString(_ctx.getDiscountAmount()), 1 /* TEXT */)
                  ])
                : createCommentVNode("v-if", true),
              isTrue(_ctx.product.isNew)
                ? createElementVNode("view", utsMapOf({
                    key: 1,
                    class: "product-detail__tag product-detail__tag--new"
                  }), [
                    createElementVNode("text", utsMapOf({ class: "product-detail__tag-text product-detail__tag-text--new" }), "新品")
                  ])
                : createCommentVNode("v-if", true),
              isTrue(_ctx.product.isFresh)
                ? createElementVNode("view", utsMapOf({
                    key: 2,
                    class: "product-detail__tag product-detail__tag--fresh"
                  }), [
                    createElementVNode("text", utsMapOf({ class: "product-detail__tag-text product-detail__tag-text--fresh" }), "新鲜")
                  ])
                : createCommentVNode("v-if", true)
            ])
          : createCommentVNode("v-if", true),
        createElementVNode("text", utsMapOf({ class: "product-detail__name" }), toDisplayString(_ctx.product.name), 1 /* TEXT */),
        isTrue(_ctx.product.description)
          ? createElementVNode("text", utsMapOf({
              key: 1,
              class: "product-detail__brief"
            }), toDisplayString(_ctx.product.description), 1 /* TEXT */)
          : createCommentVNode("v-if", true),
        createElementVNode("view", utsMapOf({ class: "product-detail__meta-row" }), [
          createElementVNode("view", utsMapOf({ class: "product-detail__rating" }), [
            createElementVNode("text", utsMapOf({ class: "rating-star" }), "⭐"),
            createElementVNode("text", utsMapOf({ class: "rating-score" }), toDisplayString(_ctx.product.rating), 1 /* TEXT */),
            createElementVNode("text", utsMapOf({ class: "rating-count" }), "(" + toDisplayString(_ctx.product.reviewCount) + "条评价)", 1 /* TEXT */)
          ]),
          createElementVNode("text", utsMapOf({
            class: normalizeClass(['product-detail__stock-status', utsMapOf({ 'product-detail__stock-status--low': _ctx.product.stock < 10 })])
          }), toDisplayString(_ctx.product.stock > 0 ? `库存: ${_ctx.product.stock}` : '暂时缺货'), 3 /* TEXT, CLASS */)
        ])
      ]),
      isTrue(_ctx.hasSpecs())
        ? createElementVNode("view", utsMapOf({
            key: 0,
            class: "product-detail__section"
          }), [
            createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.product.specs, (spec, __key, __index, _cached): any => {
              return createElementVNode("view", utsMapOf({
                key: spec.id,
                class: "product-detail__spec-item"
              }), [
                createElementVNode("text", utsMapOf({ class: "product-detail__spec-label" }), toDisplayString(spec.name), 1 /* TEXT */),
                createElementVNode("view", utsMapOf({ class: "product-detail__spec-options" }), [
                  createElementVNode(Fragment, null, RenderHelpers.renderList(spec.options, (option, __key, __index, _cached): any => {
                    return createElementVNode("view", utsMapOf({
                      key: option,
                      class: normalizeClass(['product-detail__spec-option', utsMapOf({ 'product-detail__spec-option--selected': _ctx.isSpecSelected(spec.id, option) })]),
                      onClick: () => {_ctx.selectSpec(spec.id, option)}
                    }), [
                      createElementVNode("text", utsMapOf({
                        class: normalizeClass(['product-detail__spec-text', utsMapOf({ 'product-detail__spec-text--selected': _ctx.isSpecSelected(spec.id, option) })])
                      }), toDisplayString(option), 3 /* TEXT, CLASS */)
                    ], 10 /* CLASS, PROPS */, ["onClick"])
                  }), 128 /* KEYED_FRAGMENT */)
                ])
              ])
            }), 128 /* KEYED_FRAGMENT */),
            createElementVNode("view", utsMapOf({ class: "product-detail__quantity-row" }), [
              createElementVNode("text", utsMapOf({ class: "product-detail__spec-label" }), "数量"),
              createElementVNode("view", utsMapOf({ class: "product-detail__quantity-control" }), [
                createElementVNode("view", utsMapOf({
                  class: normalizeClass(["product-detail__quantity-btn", utsMapOf({'product-detail__quantity-btn--disabled': _ctx.quantity <= 1})]),
                  onClick: _ctx.decreaseQuantity
                }), [
                  createElementVNode("text", utsMapOf({ class: "product-detail__quantity-btn-text" }), "－")
                ], 10 /* CLASS, PROPS */, ["onClick"]),
                createElementVNode("view", utsMapOf({ class: "product-detail__quantity-input" }), [
                  createElementVNode("text", utsMapOf({ class: "product-detail__quantity-input-text" }), toDisplayString(_ctx.quantity), 1 /* TEXT */)
                ]),
                createElementVNode("view", utsMapOf({
                  class: normalizeClass(["product-detail__quantity-btn", utsMapOf({'product-detail__quantity-btn--disabled': _ctx.quantity >= _ctx.product.stock})]),
                  onClick: _ctx.increaseQuantity
                }), [
                  createElementVNode("text", utsMapOf({ class: "product-detail__quantity-btn-text" }), "＋")
                ], 10 /* CLASS, PROPS */, ["onClick"])
              ])
            ])
          ])
        : createCommentVNode("v-if", true),
      createElementVNode("view", utsMapOf({ class: "product-detail__section product-detail__reviews-section" }), [
        createElementVNode("view", utsMapOf({
          class: "product-detail__section-header",
          onClick: _ctx.viewAllReviews
        }), [
          createElementVNode("view", utsMapOf({ class: "product-detail__section-title-box" }), [
            createElementVNode("text", utsMapOf({ class: "product-detail__section-title" }), "用户评价"),
            createElementVNode("text", utsMapOf({ class: "title-count" }), "(" + toDisplayString(_ctx.product.reviewCount) + ")", 1 /* TEXT */)
          ]),
          createElementVNode("view", utsMapOf({ class: "product-detail__section-more" }), [
            createElementVNode("text", utsMapOf({ class: "product-detail__section-more-text" }), "好评度 " + toDisplayString((_ctx.product.rating / 5 * 100).toFixed(0)) + "%", 1 /* TEXT */),
            createElementVNode("text", utsMapOf({ class: "arrow-right" }), ">")
          ])
        ], 8 /* PROPS */, ["onClick"]),
        _ctx.reviews.length > 0
          ? createElementVNode("view", utsMapOf({
              key: 0,
              class: "product-detail__review-list"
            }), [
              createElementVNode(Fragment, null, RenderHelpers.renderList(_ctx.reviews.slice(0, 2), (review, __key, __index, _cached): any => {
                return createElementVNode("view", utsMapOf({
                  key: review.id,
                  class: "product-detail__review-item"
                }), [
                  createElementVNode("view", utsMapOf({ class: "product-detail__review-header" }), [
                    createElementVNode("view", utsMapOf({ class: "product-detail__review-user" }), [
                      createElementVNode("image", utsMapOf({
                        src: review.userAvatar,
                        class: "product-detail__review-avatar"
                      }), null, 8 /* PROPS */, ["src"]),
                      createElementVNode("text", utsMapOf({ class: "product-detail__review-username" }), toDisplayString(review.userNickname), 1 /* TEXT */),
                      createElementVNode("view", utsMapOf({ class: "product-detail__review-rating-stars" }), [
                        createElementVNode(Fragment, null, RenderHelpers.renderList(5, (i, __key, __index, _cached): any => {
                          return createElementVNode("text", utsMapOf({
                            key: i,
                            class: normalizeClass(["star", utsMapOf({'star-active': i <= review.rating})])
                          }), "★", 2 /* CLASS */)
                        }), 64 /* STABLE_FRAGMENT */)
                      ])
                    ]),
                    createElementVNode("text", utsMapOf({ class: "product-detail__review-date" }), toDisplayString(review.createTime), 1 /* TEXT */)
                  ]),
                  createElementVNode("text", utsMapOf({ class: "product-detail__review-content" }), toDisplayString(review.content), 1 /* TEXT */),
                  isTrue(review.replyContent)
                    ? createElementVNode("view", utsMapOf({
                        key: 0,
                        class: "product-detail__review-reply"
                      }), [
                        createElementVNode("text", utsMapOf({ class: "reply-label" }), "商家回复："),
                        createElementVNode("text", utsMapOf({ class: "reply-content" }), toDisplayString(review.replyContent), 1 /* TEXT */)
                      ])
                    : createCommentVNode("v-if", true)
                ])
              }), 128 /* KEYED_FRAGMENT */)
            ])
          : createElementVNode("view", utsMapOf({
              key: 1,
              class: "product-detail__empty-review"
            }), [
              createElementVNode("text", utsMapOf({ class: "product-detail__empty-review-text" }), "暂无评价")
            ])
      ]),
      createElementVNode("view", utsMapOf({ class: "product-detail__section product-detail__detail-section" }), [
        createElementVNode("text", utsMapOf({ class: "product-detail__section-title-simple" }), "商品详情"),
        createElementVNode("text", utsMapOf({ class: "product-detail__description" }), toDisplayString(_ctx.product.description), 1 /* TEXT */)
      ]),
      createElementVNode("view", utsMapOf({ class: "product-detail__safe-area-placeholder" }))
    ]),
    createElementVNode("view", utsMapOf({ class: "product-detail__footer" }), [
      createElementVNode("view", utsMapOf({ class: "product-detail__footer-icons" }), [
        createElementVNode("view", utsMapOf({
          class: "product-detail__icon-btn",
          onClick: _ctx.toggleFavorite
        }), [
          createElementVNode("text", utsMapOf({ class: "icon-text" }), toDisplayString(_ctx.isFavorite ? '❤️' : '🤍'), 1 /* TEXT */),
          createElementVNode("text", utsMapOf({ class: "icon-label" }), "收藏")
        ], 8 /* PROPS */, ["onClick"])
      ]),
      createElementVNode("view", utsMapOf({ class: "product-detail__footer-actions" }), [
        createElementVNode("view", utsMapOf({
          class: "product-detail__action-btn product-detail__action-btn--cart",
          onClick: _ctx.handleAddToCart
        }), [
          createElementVNode("text", utsMapOf({ class: "product-detail__action-text" }), "加入购物车")
        ], 8 /* PROPS */, ["onClick"]),
        createElementVNode("view", utsMapOf({
          class: "product-detail__action-btn product-detail__action-btn--buy",
          onClick: _ctx.buyNow
        }), [
          createElementVNode("text", utsMapOf({ class: "product-detail__action-text" }), "立即购买")
        ], 8 /* PROPS */, ["onClick"])
      ])
    ])
  ])
}
const GenPagesProductDetailStyles = [utsMapOf([["product-detail-page", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "column"], ["height", "100%"], ["width", "100%"], ["backgroundColor", "#F5F7FA"]]))], ["product-detail__scroll", padStyleMapOf(utsMapOf([["flex", 1], ["width", "100%"], ["height", 0]]))], ["product-detail__carousel", padStyleMapOf(utsMapOf([["position", "relative"], ["width", "100%"], ["backgroundImage", "none"], ["backgroundColor", "#ffffff"]]))], ["product-detail__loading", padStyleMapOf(utsMapOf([["width", "100%"], ["height", "750rpx"], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"], ["backgroundColor", "#f5f5f5"]]))], ["loading-text", padStyleMapOf(utsMapOf([["fontSize", 14], ["color", "#999999"]]))], ["product-detail__swiper", padStyleMapOf(utsMapOf([["width", "100%"], ["height", "750rpx"]]))], ["product-detail__swiper-item", padStyleMapOf(utsMapOf([["width", "100%"], ["height", "100%"], ["backgroundColor", "#f5f5f5"], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["product-detail__image", padStyleMapOf(utsMapOf([["width", "100%"], ["height", "100%"], ["backgroundColor", "#ffffff"]]))], ["product-detail__image-indicator", padStyleMapOf(utsMapOf([["position", "absolute"], ["bottom", "20rpx"], ["right", "20rpx"], ["backgroundImage", "none"], ["backgroundColor", "rgba(0,0,0,0.6)"], ["paddingTop", "6rpx"], ["paddingRight", "16rpx"], ["paddingBottom", "6rpx"], ["paddingLeft", "16rpx"], ["borderTopLeftRadius", "20rpx"], ["borderTopRightRadius", "20rpx"], ["borderBottomRightRadius", "20rpx"], ["borderBottomLeftRadius", "20rpx"], ["zIndex", 5]]))], ["product-detail__indicator-text", padStyleMapOf(utsMapOf([["color", "#ffffff"], ["fontSize", "22rpx"]]))], ["product-detail__info-section", padStyleMapOf(utsMapOf([["backgroundImage", "none"], ["backgroundColor", "#ffffff"], ["paddingTop", "24rpx"], ["paddingRight", "28rpx"], ["paddingBottom", "24rpx"], ["paddingLeft", "28rpx"], ["marginTop", 0], ["marginRight", 0], ["marginBottom", "12rpx"], ["marginLeft", 0], ["display", "flex"], ["flexDirection", "column"]]))], ["product-detail__price-row", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["justifyContent", "space-between"], ["alignItems", "flex-end"], ["marginBottom", "20rpx"], ["paddingBottom", "20rpx"], ["borderBottomWidth", "1rpx"], ["borderBottomStyle", "solid"], ["borderBottomColor", "#f0f0f0"]]))], ["product-detail__price-group", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["color", "#FF4D4F"]]))], ["product-detail__currency", padStyleMapOf(utsMapOf([["fontSize", "28rpx"], ["fontWeight", "bold"], ["color", "#FF4D4F"]]))], ["product-detail__current-price", padStyleMapOf(utsMapOf([["fontSize", "48rpx"], ["fontWeight", "bold"], ["marginRight", "16rpx"], ["color", "#ff4d4f"]]))], ["product-detail__original-price", padStyleMapOf(utsMapOf([["fontSize", "26rpx"], ["color", "#999999"], ["opacity", 0.7]]))], ["product-detail__sales", padStyleMapOf(utsMapOf([["fontSize", "24rpx"], ["color", "#999999"]]))], ["product-detail__tags-row", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["flexWrap", "wrap"], ["marginBottom", "16rpx"]]))], ["product-detail__tag", padStyleMapOf(utsMapOf([["paddingTop", "6rpx"], ["paddingRight", "16rpx"], ["paddingBottom", "6rpx"], ["paddingLeft", "16rpx"], ["borderTopLeftRadius", "20rpx"], ["borderTopRightRadius", "20rpx"], ["borderBottomRightRadius", "20rpx"], ["borderBottomLeftRadius", "20rpx"], ["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "center"], ["marginRight", "8rpx"], ["marginBottom", "8rpx"]]))], ["product-detail__tag--discount", padStyleMapOf(utsMapOf([["backgroundImage", "linear-gradient(135deg, #fff0f0 0%, #ffe0e0 100%)"], ["backgroundColor", "rgba(0,0,0,0)"], ["borderTopWidth", "2rpx"], ["borderRightWidth", "2rpx"], ["borderBottomWidth", "2rpx"], ["borderLeftWidth", "2rpx"], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#ffcccc"], ["borderRightColor", "#ffcccc"], ["borderBottomColor", "#ffcccc"], ["borderLeftColor", "#ffcccc"]]))], ["product-detail__tag--new", padStyleMapOf(utsMapOf([["backgroundImage", "linear-gradient(135deg, #e6f7ff 0%, #bae0ff 100%)"], ["backgroundColor", "rgba(0,0,0,0)"], ["borderTopWidth", "2rpx"], ["borderRightWidth", "2rpx"], ["borderBottomWidth", "2rpx"], ["borderLeftWidth", "2rpx"], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#91caff"], ["borderRightColor", "#91caff"], ["borderBottomColor", "#91caff"], ["borderLeftColor", "#91caff"]]))], ["product-detail__tag--fresh", padStyleMapOf(utsMapOf([["backgroundImage", "linear-gradient(135deg, #f6ffed 0%, #d9f7be 100%)"], ["backgroundColor", "rgba(0,0,0,0)"], ["borderTopWidth", "2rpx"], ["borderRightWidth", "2rpx"], ["borderBottomWidth", "2rpx"], ["borderLeftWidth", "2rpx"], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#b7eb8f"], ["borderRightColor", "#b7eb8f"], ["borderBottomColor", "#b7eb8f"], ["borderLeftColor", "#b7eb8f"]]))], ["product-detail__tag-text", padStyleMapOf(utsMapOf([["fontSize", "20rpx"], ["fontWeight", "normal"]]))], ["product-detail__tag-text--discount", padStyleMapOf(utsMapOf([["color", "#ff4d4f"]]))], ["product-detail__tag-text--new", padStyleMapOf(utsMapOf([["color", "#0066CC"]]))], ["product-detail__tag-text--fresh", padStyleMapOf(utsMapOf([["color", "#52c41a"]]))], ["product-detail__name", padStyleMapOf(utsMapOf([["fontSize", "32rpx"], ["fontWeight", "bold"], ["color", "#1a1a1a"], ["marginBottom", "12rpx"], ["lineHeight", 1.4]]))], ["product-detail__brief", padStyleMapOf(utsMapOf([["fontSize", "24rpx"], ["color", "#999999"], ["lineHeight", 1.4], ["marginBottom", "16rpx"]]))], ["product-detail__meta-row", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["justifyContent", "space-between"], ["alignItems", "center"], ["paddingTop", "16rpx"]]))], ["product-detail__rating", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"]]))], ["rating-star", padStyleMapOf(utsMapOf([["marginRight", "8rpx"], ["fontSize", "24rpx"]]))], ["rating-score", padStyleMapOf(utsMapOf([["fontWeight", "bold"], ["color", "#333333"], ["marginRight", "8rpx"], ["fontSize", "24rpx"]]))], ["rating-count", padStyleMapOf(utsMapOf([["color", "#999999"], ["fontSize", "24rpx"]]))], ["product-detail__stock-status", padStyleMapOf(utsMapOf([["fontSize", "24rpx"], ["color", "#52C41A"]]))], ["product-detail__stock-status--low", padStyleMapOf(utsMapOf([["color", "#FF7A45"]]))], ["product-detail__section", padStyleMapOf(utsMapOf([["backgroundImage", "none"], ["backgroundColor", "#ffffff"], ["paddingTop", "24rpx"], ["paddingRight", "28rpx"], ["paddingBottom", "24rpx"], ["paddingLeft", "28rpx"], ["marginBottom", "12rpx"], ["display", "flex"], ["flexDirection", "column"]]))], ["product-detail__section-title-box", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"]]))], ["product-detail__section-title", padStyleMapOf(utsMapOf([["fontSize", "30rpx"], ["fontWeight", "bold"], ["color", "#333333"]]))], ["title-count", padStyleMapOf(utsMapOf([["fontSize", "24rpx"], ["color", "#999999"], ["fontWeight", "normal"], ["marginLeft", "8rpx"]]))], ["product-detail__section-title-simple", padStyleMapOf(utsMapOf([["fontSize", "30rpx"], ["fontWeight", "bold"], ["color", "#333333"], ["marginBottom", "24rpx"], ["position", "relative"], ["paddingLeft", "20rpx"], ["borderLeftWidth", "6rpx"], ["borderLeftStyle", "solid"], ["borderLeftColor", "#0066CC"]]))], ["product-detail__spec-item", padStyleMapOf(utsMapOf([["marginBottom", "24rpx"], ["display", "flex"], ["flexDirection", "column"]]))], ["product-detail__spec-label", padStyleMapOf(utsMapOf([["fontSize", "28rpx"], ["color", "#333333"], ["fontWeight", "bold"], ["marginBottom", "12rpx"]]))], ["product-detail__spec-options", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["flexWrap", "wrap"]]))], ["product-detail__spec-option", padStyleMapOf(utsMapOf([["paddingTop", "12rpx"], ["paddingRight", "32rpx"], ["paddingBottom", "12rpx"], ["paddingLeft", "32rpx"], ["borderTopWidth", "2rpx"], ["borderRightWidth", "2rpx"], ["borderBottomWidth", "2rpx"], ["borderLeftWidth", "2rpx"], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#e8e8e8"], ["borderRightColor", "#e8e8e8"], ["borderBottomColor", "#e8e8e8"], ["borderLeftColor", "#e8e8e8"], ["borderTopLeftRadius", "8rpx"], ["borderTopRightRadius", "8rpx"], ["borderBottomRightRadius", "8rpx"], ["borderBottomLeftRadius", "8rpx"], ["backgroundColor", "#fafafa"], ["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "center"], ["marginRight", "16rpx"], ["marginBottom", "16rpx"]]))], ["product-detail__spec-option--selected", padStyleMapOf(utsMapOf([["backgroundImage", "linear-gradient(135deg, #e6f7ff 0%, #bae0ff 100%)"], ["backgroundColor", "rgba(0,0,0,0)"], ["borderTopColor", "#0066CC"], ["borderRightColor", "#0066CC"], ["borderBottomColor", "#0066CC"], ["borderLeftColor", "#0066CC"], ["borderTopWidth", "2rpx"], ["borderRightWidth", "2rpx"], ["borderBottomWidth", "2rpx"], ["borderLeftWidth", "2rpx"], ["boxShadow", "0 4rpx 12rpx rgba(0, 102, 204, 0.2)"]]))], ["product-detail__spec-text", padStyleMapOf(utsMapOf([["fontSize", "24rpx"], ["color", "#333333"]]))], ["product-detail__spec-text--selected", padStyleMapOf(utsMapOf([["color", "#0066CC"], ["fontWeight", "400"]]))], ["product-detail__quantity-row", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["justifyContent", "space-between"], ["alignItems", "center"], ["marginTop", "8rpx"]]))], ["product-detail__quantity-control", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["backgroundImage", "none"], ["backgroundColor", "#f5f5f5"], ["borderTopLeftRadius", "8rpx"], ["borderTopRightRadius", "8rpx"], ["borderBottomRightRadius", "8rpx"], ["borderBottomLeftRadius", "8rpx"], ["paddingTop", "4rpx"], ["paddingRight", "4rpx"], ["paddingBottom", "4rpx"], ["paddingLeft", "4rpx"], ["borderTopWidth", "1rpx"], ["borderRightWidth", "1rpx"], ["borderBottomWidth", "1rpx"], ["borderLeftWidth", "1rpx"], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#e0e0e0"], ["borderRightColor", "#e0e0e0"], ["borderBottomColor", "#e0e0e0"], ["borderLeftColor", "#e0e0e0"]]))], ["product-detail__quantity-btn", padStyleMapOf(utsMapOf([["width", "56rpx"], ["height", "56rpx"], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"], ["backgroundImage", "none"], ["backgroundColor", "#ffffff"], ["borderTopLeftRadius", "6rpx"], ["borderTopRightRadius", "6rpx"], ["borderBottomRightRadius", "6rpx"], ["borderBottomLeftRadius", "6rpx"], ["marginTop", 0], ["marginRight", "2rpx"], ["marginBottom", 0], ["marginLeft", "2rpx"]]))], ["product-detail__quantity-btn--disabled", padStyleMapOf(utsMapOf([["backgroundImage", "none"], ["backgroundColor", "rgba(0,0,0,0)"], ["boxShadow", "none"], ["opacity", 0.5]]))], ["product-detail__quantity-btn-text", padStyleMapOf(utsMapOf([["fontSize", "32rpx"], ["color", "#333333"], ["fontWeight", "bold"]]))], ["product-detail__quantity-input", padStyleMapOf(utsMapOf([["width", "80rpx"], ["height", "56rpx"], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["product-detail__quantity-input-text", padStyleMapOf(utsMapOf([["fontSize", "30rpx"], ["color", "#1a1a1a"], ["fontWeight", "bold"]]))], ["product-detail__section-header", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["justifyContent", "space-between"], ["alignItems", "center"], ["marginBottom", "20rpx"]]))], ["product-detail__section-more", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"]]))], ["product-detail__section-more-text", padStyleMapOf(utsMapOf([["fontSize", "24rpx"], ["color", "#999999"]]))], ["arrow-right", padStyleMapOf(utsMapOf([["marginLeft", "4rpx"], ["fontSize", "24rpx"], ["color", "#999999"]]))], ["product-detail__review-list", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "column"]]))], ["product-detail__review-item", padStyleMapOf(utsMapOf([["borderBottomWidth", 1], ["borderBottomStyle", "solid"], ["borderBottomColor", "#F5F5F5"], ["paddingBottom", "24rpx"], ["marginBottom", "24rpx"], ["display", "flex"], ["flexDirection", "column"]]))], ["product-detail__review-header", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["justifyContent", "space-between"], ["marginBottom", "12rpx"]]))], ["product-detail__review-user", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"]]))], ["product-detail__review-avatar", padStyleMapOf(utsMapOf([["width", "48rpx"], ["height", "48rpx"], ["borderTopLeftRadius", 999], ["borderTopRightRadius", 999], ["borderBottomRightRadius", 999], ["borderBottomLeftRadius", 999], ["marginRight", "12rpx"], ["backgroundColor", "#eeeeee"]]))], ["product-detail__review-username", padStyleMapOf(utsMapOf([["fontSize", "24rpx"], ["color", "#999999"], ["marginRight", "12rpx"]]))], ["product-detail__review-rating-stars", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"]]))], ["star", padStyleMapOf(utsMapOf([["fontSize", "20rpx"], ["color", "#C0C0C0"], ["marginRight", "2rpx"]]))], ["star-active", padStyleMapOf(utsMapOf([["color", "#FF7A45"]]))], ["product-detail__review-date", padStyleMapOf(utsMapOf([["fontSize", "22rpx"], ["color", "#C0C0C0"]]))], ["product-detail__review-content", padStyleMapOf(utsMapOf([["fontSize", "26rpx"], ["color", "#333333"], ["lineHeight", 1.5]]))], ["product-detail__review-reply", padStyleMapOf(utsMapOf([["marginTop", "16rpx"], ["paddingTop", "16rpx"], ["paddingRight", "16rpx"], ["paddingBottom", "16rpx"], ["paddingLeft", "16rpx"], ["backgroundColor", "#f8f8f8"], ["borderTopLeftRadius", "8rpx"], ["borderTopRightRadius", "8rpx"], ["borderBottomRightRadius", "8rpx"], ["borderBottomLeftRadius", "8rpx"], ["display", "flex"], ["flexDirection", "column"]]))], ["reply-label", utsMapOf([[".product-detail__review-reply ", utsMapOf([["fontSize", "24rpx"], ["color", "#0066CC"], ["fontWeight", "bold"], ["marginBottom", "8rpx"]])]])], ["reply-content", utsMapOf([[".product-detail__review-reply ", utsMapOf([["fontSize", "24rpx"], ["color", "#666666"], ["lineHeight", 1.4]])]])], ["product-detail__empty-review", padStyleMapOf(utsMapOf([["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"], ["paddingTop", "40rpx"], ["paddingRight", 0], ["paddingBottom", "40rpx"], ["paddingLeft", 0], ["backgroundImage", "none"], ["backgroundColor", "#fafafa"], ["borderTopLeftRadius", "8rpx"], ["borderTopRightRadius", "8rpx"], ["borderBottomRightRadius", "8rpx"], ["borderBottomLeftRadius", "8rpx"]]))], ["product-detail__empty-review-text", padStyleMapOf(utsMapOf([["color", "#999999"], ["fontSize", "24rpx"]]))], ["product-detail__detail-section", padStyleMapOf(utsMapOf([["paddingBottom", "24rpx"]]))], ["product-detail__description", padStyleMapOf(utsMapOf([["fontSize", "26rpx"], ["color", "#333333"], ["lineHeight", 1.8], ["paddingTop", "20rpx"], ["paddingRight", 0], ["paddingBottom", "20rpx"], ["paddingLeft", 0], ["textAlign", "left"]]))], ["product-detail__safe-area-placeholder", padStyleMapOf(utsMapOf([["height", "140rpx"], ["width", "100%"]]))], ["product-detail__footer", padStyleMapOf(utsMapOf([["position", "fixed"], ["bottom", 0], ["left", 0], ["right", 0], ["backgroundImage", "none"], ["backgroundColor", "#ffffff"], ["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["paddingTop", "16rpx"], ["paddingRight", "20rpx"], ["paddingBottom", "24rpx"], ["paddingLeft", "20rpx"], ["boxShadow", "0 -2rpx 16rpx rgba(0, 0, 0, 0.06)"], ["zIndex", 1000], ["borderTopWidth", "1rpx"], ["borderTopStyle", "solid"], ["borderTopColor", "#f0f0f0"]]))], ["product-detail__footer-icons", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "row"], ["marginRight", "20rpx"]]))], ["product-detail__icon-btn", padStyleMapOf(utsMapOf([["display", "flex"], ["flexDirection", "column"], ["alignItems", "center"], ["justifyContent", "center"], ["width", "90rpx"]]))], ["icon-text", utsMapOf([[".product-detail__icon-btn ", utsMapOf([["fontSize", "36rpx"], ["marginBottom", "4rpx"]])]])], ["icon-label", utsMapOf([[".product-detail__icon-btn ", utsMapOf([["fontSize", "20rpx"], ["color", "#999999"]])]])], ["product-detail__footer-actions", padStyleMapOf(utsMapOf([["flex", 1], ["display", "flex"], ["flexDirection", "row"], ["height", "80rpx"]]))], ["product-detail__action-btn", padStyleMapOf(utsMapOf([["flex", 1], ["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "center"], ["height", "76rpx"]]))], ["product-detail__action-btn--cart", padStyleMapOf(utsMapOf([["backgroundColor", "#FF9500"], ["borderTopLeftRadius", "8rpx"], ["borderTopRightRadius", "8rpx"], ["borderBottomRightRadius", "8rpx"], ["borderBottomLeftRadius", "8rpx"], ["marginRight", "12rpx"]]))], ["product-detail__action-btn--buy", padStyleMapOf(utsMapOf([["backgroundColor", "#FF3D00"], ["borderTopLeftRadius", "8rpx"], ["borderTopRightRadius", "8rpx"], ["borderBottomRightRadius", "8rpx"], ["borderBottomLeftRadius", "8rpx"]]))], ["product-detail__action-text", padStyleMapOf(utsMapOf([["fontSize", "30rpx"], ["fontWeight", "bold"], ["letterSpacing", 1], ["color", "#ffffff"]]))]])]
