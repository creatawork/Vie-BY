# 县域生鲜商品销售网站 - 前端开发指南

## 一、项目概述

### 项目信息
- **项目名称**: 县域生鲜商品销售网站系统
- **前端框架**: uni-app
- **开发语言**: Vue 3 + TypeScript/JavaScript
- **样式预处理**: SCSS/SASS
- **目标平台**: iOS、Android、H5、微信小程序等

### 项目特点
- 清新、简洁、大气的设计风格
- 采用清爽蓝 (#0066CC) 和清新绿 (#52C41A) 为主色系
- 避免紫色等暖色系颜色
- 移动端优先设计
- 响应式布局 

---

## 二、项目结构

### 推荐的目录结构

```
project-root/
├── pages/                          # 页面文件
│   ├── login/                      # 登录页
│   │   └── login.uvue
│   ├── register/                   # 注册页
│   │   └── register.uvue
│   ├── index/                      # 首页
│   │   └── index.uvue
│   ├── product/                    # 商品模块
│   │   ├── list/
│   │   │   └── list.uvue
│   │   └── detail/
│   │       └── detail.uvue
│   ├── cart/                       # 购物车模块
│   │   └── index.uvue
│   ├── order/                      # 订单模块
│   │   ├── create/
│   │   │   └── create.uvue
│   │   ├── list/
│   │   │   └── list.uvue
│   │   └── detail/
│   │       └── detail.uvue
│   ├── user/                       # 用户模块
│   │   ├── index/
│   │   │   └── index.uvue
│   │   └── profile/
│   │       └── profile.uvue
│   ├── comment/                    # 评价模块
│   │   └── index.uvue
│   └── statistics/                 # 数据统计模块
│       └── index.uvue
│
├── components/                     # 可复用组件
│   ├── Header.uvue                 # 页面头部
│   ├── Footer.uvue                 # 页面底部
│   ├── ProductCard.uvue            # 商品卡片
│   ├── OrderCard.uvue              # 订单卡片
│   ├── TabBar.uvue                 # 底部标签栏
│   ├── Loading.uvue                # 加载动画
│   ├── Empty.uvue                  # 空状态
│   ├── Toast.uvue                  # 提示框
│   ├── Modal.uvue                  # 模态框
│   ├── Button.uvue                 # 按钮
│   ├── Input.uvue                  # 输入框
│   ├── Checkbox.uvue               # 复选框
│   ├── Radio.uvue                  # 单选框
│   ├── Select.uvue                 # 下拉选择
│   ├── Slider.uvue                 # 滑块
│   ├── Stepper.uvue                # 数量选择器
│   ├── ImageUpload.uvue            # 图片上传
│   └── ...
│
├── utils/                          # 工具函数
│   ├── request.ts                  # HTTP请求封装
│   ├── storage.ts                  # 本地存储封装
│   ├── validate.ts                 # 表单验证
│   ├── format.ts                   # 数据格式化
│   ├── date.ts                     # 日期处理
│   ├── common.ts                   # 通用工具函数
│   └── constants.ts                # 常量定义
│
├── store/                          # 状态管理 (Vuex/Pinia)
│   ├── modules/
│   │   ├── user.ts                 # 用户状态
│   │   ├── product.ts              # 商品状态
│   │   ├── cart.ts                 # 购物车状态
│   │   ├── order.ts                # 订单状态
│   │   └── app.ts                  # 应用全局状态
│   └── index.ts
│
├── api/                            # API接口
│   ├── user.ts                     # 用户相关接口
│   ├── product.ts                  # 商品相关接口
│   ├── cart.ts                     # 购物车相关接口
│   ├── order.ts                    # 订单相关接口
│   ├── payment.ts                  # 支付相关接口
│   ├── comment.ts                  # 评价相关接口
│   └── statistics.ts               # 数据统计接口
│
├── static/                         # 静态资源
│   ├── images/                     # 图片
│   │   ├── logo.png
│   │   ├── icons/
│   │   └── ...
│   ├── fonts/                      # 字体文件
│   └── ...
│
├── styles/                         # 全局样式
│   ├── variables.scss              # SCSS变量
│   ├── mixins.scss                 # SCSS混入
│   ├── reset.scss                  # 样式重置
│   ├── common.scss                 # 通用样式
│   └── theme.scss                  # 主题样式
│
├── types/                          # TypeScript类型定义
│   ├── user.ts
│   ├── product.ts
│   ├── order.ts
│   ├── api.ts
│   └── ...
│
├── App.uvue                        # 应用根组件
├── main.uts                        # 应用入口
├── pages.json                      # 页面配置
├── manifest.json                   # 应用配置
├── uni.scss                        # uni-app全局样式
├── package.json                    # 项目依赖
├── tsconfig.json                   # TypeScript配置
├── REQUIREMENTS.md                 # 需求规格文档
├── DESIGN_GUIDE.md                 # 设计指南
├── DEVELOPMENT_GUIDE.md            # 开发指南
└── README.md                       # 项目说明
```

---

## 三、开发规范

### 3.1 命名规范

#### 文件命名
- **页面文件**: 使用小写 + 下划线，如 `login.uvue`、`product_list.uvue`
- **组件文件**: 使用 PascalCase，如 `ProductCard.uvue`、`OrderCard.uvue`
- **工具文件**: 使用小写 + 下划线，如 `request.ts`、`validate.ts`
- **样式文件**: 使用小写 + 下划线，如 `variables.scss`、`common.scss`

#### 变量命名
- **常量**: 使用 UPPER_SNAKE_CASE，如 `MAX_RETRY_COUNT`、`API_BASE_URL`
- **函数**: 使用 camelCase，如 `getUserInfo()`、`formatPrice()`
- **类/接口**: 使用 PascalCase，如 `UserInfo`、`ProductDetail`
- **布尔值**: 使用 is/has 前缀，如 `isLoading`、`hasError`

#### CSS类名
- **使用 BEM 命名法**: `block__element--modifier`
- 示例: `product-card__image--active`、`order-list__item--completed`

### 3.2 代码风格

#### Vue 组件结构
```vue
<template>
  <!-- 模板内容 -->
</template>

<script lang="uts">
// 导入
import { defineComponent } from 'vue'

// 导出
export default defineComponent({
  name: 'ComponentName',
  components: {
    // 子组件
  },
  props: {
    // 属性
  },
  data() {
    return {
      // 数据
    }
  },
  computed: {
    // 计算属性
  },
  watch: {
    // 监听器
  },
  methods: {
    // 方法
  },
  mounted() {
    // 生命周期钩子
  }
})
</script>

<style scoped lang="scss">
// 样式
</style>
```

#### 缩进和格式
- 使用 2 个空格缩进（不使用 Tab）
- 每行最多 100 个字符
- 使用分号结尾
- 字符串使用单引号

### 3.3 注释规范

#### 文件头注释
```javascript
/**
 * @description 页面/组件描述
 * @author 作者名
 * @date 2025-11-21
 */
```

#### 函数注释
```javascript
/**
 * 函数描述
 * @param {type} paramName - 参数描述
 * @returns {type} 返回值描述
 */
function functionName(paramName) {
  // 实现
}
```

#### 复杂逻辑注释
```javascript
// 说明为什么这样做，而不是说明做了什么
if (user.age > 18) {
  // 成年用户可以进行支付操作
  processPayment()
}
```

### 3.4 uni-app x / UTS 编译注意事项

- **HTTP 请求封装**  
  - `uni.request` 只能接受 `RequestOptions<UTSJSONObject>`；先用完整类型构建 `reqOpts`，再以 `uni.request<UTSJSONObject>(reqOpts as RequestOptions<UTSJSONObject>)` 调用，避免 `Any` 或 `UTSJSONObject` 推断错误。  
  - 提前构建 `header`、`data` 等字段，并统一断言为 `UTSJSONObject`，不要把字面量直接塞进 `uni.request`。

- **类型声明优先**  
  - `data`、`computed`、`props` 中的对象/数组必须定义 `type` 并复用，如 `type Banner = { id: string; image: string }`。  
  - `JSON.parse`、`uni.getStorageSync`、接口返回值、`Map` 取值等都要立刻转成 `UTSJSONObject` 或显式类型，严禁直接用 `any`。

- **模板布尔表达式**  
  - 模板内不要使用 `||`、`&&`、`!` 来返回字符串或数字，统一改成方法：`{{ getDeliveryCompany() }}`。  
  - `v-if` 判断复杂表达式时，先抽成方法（如 `hasSpecs()`、`shouldShowOriginalPrice()`）并确保返回 `boolean`。

- **空值与可空类型**  
  - 判断空字符串用 `value == ''`，判断空对象/数组用 `value == null`，不要使用 `!value`。  
  - 对可空对象调用方法前必须缓存局部变量并做空判断，或使用专门的 getter。

- **生命周期与路由参数**  
  - 需要读取 `options`、`route`、`params` 的逻辑放在 `onLoad`，不要放在 `mounted`。  
  - 通过 `const opts = options as UTSJSONObject` + `opts.getString('id')` 方式读取参数。

- **本地存储与 JSON**  
  - `uni.getStorageSync` 返回 `string | null`，读取后先判空再 `JSON.parse`。  
  - 更新存储数据时重新构建对象：从 `UTSJSONObject` 取值、修改，再 `JSON.stringify`；不要直接使用 ES 展开或 `Object.assign`。

- **内置 API 结果处理**  
  - `uni.chooseImage`、`uni.uploadFile` 等回调参数需转成 `UTSJSONObject`，通过 `get('tempFilePaths') as string[]` 访问。  
  - `Promise.catch` 的入参不要写类型注解 `(error: any)`，直接写 `(error) => {}`。

- **模板方法与默认值**  
  - 所有默认值（头像、昵称、支付方式等）统一封装为 `methods`，模板只调用方法。  
  - 需要展示数字/字符串的输入控件若是禁用展示，使用 `:value="number.toString()"` 而不要用 `v-model`。

- **其他限制**  
  - 不使用 `Object.values()`、`?.`、交叉类型 (`&`)，改用显式逻辑或拆分类型声明。  
  - 涉及 `Map`、`Set` 时提前声明泛型，如 `new Map<string, HistoryItem>()`，并封装所有访问操作。

---

## 四、开发工作流

### 4.1 分支管理

```
main                    # 主分支（生产环境）
├── develop             # 开发分支
│   ├── feature/login   # 功能分支
│   ├── feature/product
│   ├── bugfix/xxx      # 修复分支
│   └── ...
```

### 4.2 提交规范

使用 Conventional Commits 规范：

```
<type>(<scope>): <subject>

<body>

<footer>
```

**Type 类型**:
- `feat`: 新功能
- `fix`: 修复bug
- `docs`: 文档更新
- `style`: 代码风格调整
- `refactor`: 代码重构
- `perf`: 性能优化
- `test`: 测试相关
- `chore`: 构建/工具相关

**示例**:
```
feat(login): add remember me functionality

Add checkbox to remember user login status
Store token in localStorage for persistence

Closes #123
```

### 4.3 代码审查

- 所有代码必须通过 Code Review
- 至少需要一个审查者批准
- 检查项:
  - 代码风格是否符合规范
  - 逻辑是否正确
  - 是否有性能问题
  - 是否有安全隐患
  - 是否有重复代码

---

## 五、API 集成指南

### 5.1 请求封装

```typescript
// utils/request.ts
import { Request } from '@/types/api'

const API_BASE_URL = 'https://api.example.com'
const TIMEOUT = 10000

export function request(config: Request) {
  return new Promise((resolve, reject) => {
    uni.request({
      url: `${API_BASE_URL}${config.url}`,
      method: config.method || 'GET',
      data: config.data,
      timeout: TIMEOUT,
      success: (res) => {
        if (res.statusCode === 200) {
          resolve(res.data)
        } else {
          reject(new Error(res.data.message))
        }
      },
      fail: (err) => {
        reject(err)
      }
    })
  })
}
```

### 5.2 API 模块化

```typescript
// api/user.ts
import { request } from '@/utils/request'

export const userApi = {
  // 登录
  login(email: string, password: string) {
    return request({
      url: '/user/login',
      method: 'POST',
      data: { email, password }
    })
  },
  
  // 注册
  register(data: any) {
    return request({
      url: '/user/register',
      method: 'POST',
      data
    })
  },
  
  // 获取用户信息
  getUserInfo() {
    return request({
      url: '/user/info',
      method: 'GET'
    })
  }
}
```

### 5.3 错误处理

```typescript
// 在页面中使用
async function handleLogin() {
  try {
    const res = await userApi.login(email, password)
    // 处理成功
    uni.showToast({
      title: '登录成功',
      icon: 'success'
    })
  } catch (error) {
    // 处理错误
    uni.showToast({
      title: error.message || '登录失败',
      icon: 'error'
    })
  }
}
```

---

## 六、状态管理

### 6.1 使用 Pinia (推荐)

```typescript
// store/modules/user.ts
import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', {
  state: () => ({
    userInfo: null,
    token: '',
    isLoggedIn: false
  }),
  
  getters: {
    getUserName: (state) => state.userInfo?.name || '游客'
  },
  
  actions: {
    setUserInfo(info: any) {
      this.userInfo = info
    },
    
    setToken(token: string) {
      this.token = token
      this.isLoggedIn = !!token
    },
    
    logout() {
      this.userInfo = null
      this.token = ''
      this.isLoggedIn = false
    }
  }
})
```

### 6.2 在组件中使用

```vue
<script setup lang="ts">
import { useUserStore } from '@/store/modules/user'

const userStore = useUserStore()

// 访问状态
const userName = userStore.getUserName

// 调用 action
function logout() {
  userStore.logout()
}
</script>
```

---

## 七、本地存储

### 7.1 存储封装

```typescript
// utils/storage.ts
export const storage = {
  // 设置
  set(key: string, value: any) {
    uni.setStorage({
      key,
      data: value
    })
  },
  
  // 获取
  get(key: string) {
    try {
      const res = uni.getStorageSync(key)
      return res
    } catch (e) {
      return null
    }
  },
  
  // 删除
  remove(key: string) {
    uni.removeStorage({
      key
    })
  },
  
  // 清空
  clear() {
    uni.clearStorage()
  }
}
```

### 7.2 使用示例

```typescript
// 保存登录信息
storage.set('token', token)
storage.set('userInfo', userInfo)

// 读取登录信息
const token = storage.get('token')
const userInfo = storage.get('userInfo')

// 清除登录信息
storage.remove('token')
storage.remove('userInfo')
```

---

## 八、图片处理

### 8.1 图片压缩

```typescript
// 在上传前压缩图片
uni.compressImage({
  src: imagePath,
  quality: 80,
  success: (res) => {
    // 上传压缩后的图片
    uploadImage(res.tempFilePath)
  }
})
```

### 8.2 图片懒加载

```vue
<image 
  :src="imageUrl"
  lazy-load="true"
  mode="aspectFill"
/>
```

### 8.3 CDN 加速

```typescript
// 使用 CDN 地址替换本地路径
const getCdnUrl = (path: string) => {
  return `https://cdn.example.com${path}`
}
```

---

## 九、性能优化

### 9.1 代码分割

```typescript
// 路由懒加载
const ProductList = () => import('@/pages/product/list/list.uvue')
const ProductDetail = () => import('@/pages/product/detail/detail.uvue')
```

### 9.2 列表优化

```vue
<!-- 使用虚拟滚动处理大列表 -->
<scroll-view 
  scroll-y="true"
  @scrolltolower="loadMore"
  lower-threshold="100"
>
  <view v-for="item in items" :key="item.id">
    {{ item.name }}
  </view>
</scroll-view>
```

### 9.3 缓存策略

```typescript
// 缓存 API 响应
const cache = new Map()

export async function getCachedData(key: string, fetcher: Function) {
  if (cache.has(key)) {
    return cache.get(key)
  }
  
  const data = await fetcher()
  cache.set(key, data)
  
  // 5分钟后清除缓存
  setTimeout(() => cache.delete(key), 5 * 60 * 1000)
  
  return data
}
```

---

## 十、测试指南

### 10.1 单元测试

```typescript
// 使用 Jest 或 Vitest
import { describe, it, expect } from 'vitest'
import { formatPrice } from '@/utils/format'

describe('formatPrice', () => {
  it('should format price correctly', () => {
    expect(formatPrice(100)).toBe('¥100.00')
    expect(formatPrice(99.9)).toBe('¥99.90')
  })
})
```

### 10.2 集成测试

```typescript
// 测试 API 集成
import { userApi } from '@/api/user'

describe('User API', () => {
  it('should login successfully', async () => {
    const res = await userApi.login('test@example.com', 'password')
    expect(res.token).toBeDefined()
  })
})
```

---

## 十一、调试技巧

### 11.1 控制台输出

```typescript
// 在 HBuilderX 控制台查看日志
console.log('Debug info:', data)
console.warn('Warning message')
console.error('Error message')
```

### 11.2 网络调试

```typescript
// 使用 uni.request 的 success/fail 回调调试
uni.request({
  url: 'https://api.example.com/data',
  success: (res) => {
    console.log('Response:', res)
  },
  fail: (err) => {
    console.error('Error:', err)
  }
})
```

### 11.3 真机调试

- 在 HBuilderX 中连接真机
- 使用真机预览功能
- 查看真机日志输出

---

## 十二、常见问题解答

### Q1: 如何处理网络超时？
A: 在 request 封装中设置 timeout，并实现重试机制。

### Q2: 如何优化首屏加载速度？
A: 使用代码分割、图片懒加载、缓存策略等手段。

### Q3: 如何处理跨域问题？
A: 在后端配置 CORS，或使用代理服务器。

### Q4: 如何实现离线缓存？
A: 使用 uni.setStorage 保存数据，在离线时读取缓存。

### Q5: 如何处理登录过期？
A: 在响应拦截器中检查 token 有效期，过期则重新登录。

---

## 十三、发布部署

### 13.1 构建命令

```bash
# 开发环境
npm run dev

# 生产环境
npm run build

# 预发布环境
npm run build:staging
```

### 13.2 版本管理

遵循 Semantic Versioning (语义化版本):
- 主版本号: 不兼容的 API 修改
- 次版本号: 向下兼容的功能新增
- 修订号: 向下兼容的 bug 修复

示例: `1.2.3`

### 13.3 发布流程

1. 更新版本号
2. 更新 CHANGELOG
3. 提交代码到 main 分支
4. 创建 Release 标签
5. 构建应用包
6. 上传到应用商店

---

## 十四、参考资源

### 官方文档
- [uni-app 官方文档](https://uniapp.dcloud.io/)
- [Vue 3 官方文档](https://v3.vuejs.org/)
- [Pinia 官方文档](https://pinia.vuejs.org/)

### 工具和库
- [HBuilderX](https://www.dcloud.io/hbuilderx.html) - 开发工具
- [uni-ui](https://uniapp.dcloud.io/component/uniui/uni-ui.html) - UI 组件库
- [ECharts](https://echarts.apache.org/) - 数据可视化

### 最佳实践
- [Vue 风格指南](https://v3.vuejs.org/style-guide/)
- [JavaScript 代码规范](https://google.github.io/styleguide/jsguide.html)
- [TypeScript 最佳实践](https://www.typescriptlang.org/docs/handbook/)

---

**开发指南版本**: 1.0  
**最后更新**: 2025年11月21日  
**维护人**: 前端开发团队
