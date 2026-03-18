# VIE 电商平台 API 接口文档（详细版）

> **更新时间**：2026-03-02
> **Base URL**：`http://154.201.70.215`
> **端口**：80（已映射到8080）

---

## 📋 目录

1. [认证说明](#认证说明)
2. [通用响应格式](#通用响应格式)
3. [用户模块](#用户模块) ⭐新增收货地址管理
4. [商品分类模块](#商品分类模块)
5. [商品模块](#商品模块)
6. [购物车模块](#购物车模块)
7. [订单模块](#订单模块)
8. [钱包模块](#钱包模块)
9. [卖家模块](#卖家模块) ⭐新增订单管理
10. [浏览历史模块](#浏览历史模块)
11. [商品评价模块](#商品评价模块) ⭐评价功能完善
12. [AI模块](#ai模块) ⭐新增智能问答
13. [管理员模块](#管理员模块)

---
## ✅ 代码对齐校验结果（Controller 全量扫描）

以下路径已与 `vie-starter/src/main/java/com/vie/starter/controller/**` 实际 `@*Mapping` 对齐：

### 用户（UserController）
- POST `/api/users/register`
- POST `/api/users/login`
- POST `/api/users/logout`
- GET `/api/users/profile`
- PUT `/api/users/profile`
- PUT `/api/users/password`
- POST `/api/users/avatar/upload`
- GET `/api/users/collections`
- GET `/api/users/collections/count`
- DELETE `/api/users/collections/clear`
- POST `/api/users/become-seller`
- GET `/api/users/seller-status`
- POST `/api/users/addresses`
- PUT `/api/users/addresses/{addressId}`
- DELETE `/api/users/addresses/{addressId}`
- GET `/api/users/addresses`
- GET `/api/users/addresses/{addressId}`
- PUT `/api/users/addresses/{addressId}/default`
- GET `/api/users/addresses/default`

### 分类 / 商品 / 评价
- GET `/api/categories/tree`
- GET `/api/categories/top`
- GET `/api/categories/children/{parentId}`
- GET `/api/categories/{categoryId}`
- POST `/api/products`
- PUT `/api/products/{productId}`
- DELETE `/api/products/{productId}`
- POST `/api/products/search`
- GET `/api/products/{productId}`
- GET `/api/products/recommended`
- GET `/api/products/new`
- GET `/api/products/hot`
- PUT `/api/products/{productId}/publish`
- PUT `/api/products/{productId}/unpublish`
- PUT `/api/products/{productId}/audit`
- POST `/api/products/{productId}/collect`
- DELETE `/api/products/{productId}/uncollect`
- GET `/api/products/{productId}/is-collected`
- POST `/api/reviews`
- GET `/api/reviews/product/{productId}`
- GET `/api/reviews/mine`
- GET `/api/reviews/order/{orderId}`
- PUT `/api/reviews/{reviewId}/reply`
- DELETE `/api/reviews/{reviewId}`
- PUT `/api/reviews/{reviewId}/status`

### 购物车 / 订单 / 钱包
- POST `/api/cart/items`
- GET `/api/cart`
- PUT `/api/cart/items/{cartItemId}`
- DELETE `/api/cart/items/{cartItemId}`
- DELETE `/api/cart/clear`
- PUT `/api/cart/items/{cartItemId}/select`
- PUT `/api/cart/select-all`
- GET `/api/cart/count`
- POST `/api/orders`
- GET `/api/orders`
- GET `/api/orders/{orderId}`
- PUT `/api/orders/{orderId}/cancel`
- PUT `/api/orders/{orderId}/receive`
- DELETE `/api/orders/{orderId}`
- PUT `/api/orders/{orderId}/pay`
- GET `/api/orders/count`
- GET `/api/orders/{orderId}/logistics`
- PUT `/api/orders/{orderId}/ship`
- PUT `/api/orders/{orderId}/deliver`
- GET `/api/wallet`
- POST `/api/wallet/recharge`
- GET `/api/wallet/transactions`

### 卖家 / 卖家账户 / 管理员
- POST `/api/seller/upload/image`
- POST `/api/seller/products`
- PUT `/api/seller/products/{productId}`
- PUT `/api/seller/products/{productId}/status`
- DELETE `/api/seller/products/{productId}`
- GET `/api/seller/products`
- GET `/api/seller/products/{productId}`
- GET `/api/seller/inventory`
- PUT `/api/seller/inventory/{skuId}`
- GET `/api/seller/statistics/sales`
- GET `/api/seller/statistics/products`
- GET `/api/seller/statistics/revenue`
- GET `/api/seller/statistics/order-overview`
- GET `/api/seller/statistics/orders/status-distribution`
- GET `/api/seller/statistics/skus/rank`
- GET `/api/seller/statistics/inventory/health`
- GET `/api/seller/orders`
- GET `/api/seller/orders/{orderId}`
- GET `/api/seller/orders/count`
- PUT `/api/seller/reviews/{reviewId}/reply`
- GET `/api/seller/reviews`
- GET `/api/seller/account`
- POST `/api/seller/account/withdraw`
- GET `/api/seller/account/transactions`
- GET `/api/admin/products`
- POST `/api/admin/wallet/recharge`
- GET `/api/admin/users`
- PUT `/api/admin/users/{userId}/status`
- GET `/api/admin/users/{userId}`
- GET `/api/admin/statistics/overview`
- GET `/api/admin/statistics/product-distribution`
- GET `/api/admin/statistics/order-distribution`
- GET `/api/admin/reviews`
- GET `/api/admin/reviews/{reviewId}`
- PUT `/api/admin/reviews/{reviewId}/status`
- DELETE `/api/admin/reviews/{reviewId}`
- GET `/api/admin/reviews/statistics`

### AI
- POST `/api/ai/chat`
- POST `/api/ai/chat/stream`

### 其它
- POST `/api/browse-history`
- GET `/api/browse-history`
- DELETE `/api/browse-history/{historyId}`
- DELETE `/api/browse-history/clear`
- POST `/api/location/reverse-geocode`

> ⚠️ 特别说明：后端不存在 `/api/seller/status`、`/api/seller/register`。请使用 `/api/users/seller-status`、`/api/users/become-seller`。

---

## 认证说明

### JWT Token 认证

所有需要登录的接口都需要在请求头中携带 Token：

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### 权限标识

- 🔓 **无需认证** - 任何人都可以访问
- 🔐 **需要登录** - 需要携带有效的 Token
- 🏪 **需要卖家** - 需要卖家身份
- 👑 **需要管理员** - 需要管理员身份

---

## 通用响应格式

### 成功响应

```json
{
  "code": 200,
  "message": "success",
  "data": { /* 具体数据 */ },
  "timestamp": 1737446400000
}
```

### 错误响应

```json
{
  "code": 400,
  "message": "参数错误",
  "data": null,
  "timestamp": 1737446400000
}
```

### 常见状态码

| 状态码 | 说明 |
|--------|------|
| 200 | 成功 |
| 400 | 请求参数错误 |
| 401 | 未登录或Token失效 |
| 403 | 权限不足 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |

---

## 用户模块

### 1. 用户注册 🔓

**接口地址：** `POST /api/users/register`

**请求示例：**

```bash
curl -X POST http://154.201.70.215/api/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "zhangsan",
    "password": "Pass123456",
    "email": "zhangsan@example.com",
    "phone": "13800138000",
    "nickname": "张三"
  }'
```

**请求参数：**

```json
{
  "username": "zhangsan",
  "password": "Pass123456",
  "email": "zhangsan@example.com",
  "phone": "13800138000",
  "nickname": "张三"
}
```

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| username | String | ✅ | 用户名，3-20位字母数字 |
| password | String | ✅ | 密码，6-20位 |
| email | String | ✅ | 邮箱地址 |
| phone | String | ✅ | 手机号码 |
| nickname | String | ❌ | 昵称 |

**响应示例：**

```json
{
  "code": 200,
  "message": "注册成功",
  "data": null,
  "timestamp": 1737446400000
}
```

---

### 2. 用户登录 🔓

**接口地址：** `POST /api/users/login`

**请求示例：**

```bash
curl -X POST http://154.201.70.215/api/users/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "zhangsan",
    "password": "Pass123456"
  }'
```

**请求参数：**

```json
{
  "username": "zhangsan",
  "password": "Pass123456"
}
```

**响应示例：**

```json
{
  "code": 200,
  "message": "登录成功",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjEsInVzZXJuYW1lIjoiemhhbmdzYW4iLCJpYXQiOjE3Mzc0NDY0MDAsImV4cCI6MTczNzUzMjgwMH0.abc123",
    "userId": 1,
    "username": "zhangsan",
    "nickname": "张三",
    "avatar": "https://example.com/avatar.jpg",
    "isSeller": false
  },
  "timestamp": 1737446400000
}
```

---

### 3. 用户登出 🔐

**接口地址：** `POST /api/users/logout`

**请求示例：**

```bash
curl -X POST http://154.201.70.215/api/users/logout \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例：**

```json
{
  "code": 200,
  "message": "登出成功",
  "data": null,
  "timestamp": 1737446400000
}
```

---

### 4. 获取个人信息 🔐

**接口地址：** `GET /api/users/profile`

**请求示例：**

```bash
curl -X GET http://154.201.70.215/api/users/profile \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "username": "zhangsan",
    "nickname": "张三",
    "email": "zhangsan@example.com",
    "phone": "13800138000",
    "avatar": "https://example.com/avatar.jpg",
    "gender": 1,
    "birthday": "1990-01-01",
    "isSeller": false,
    "createTime": "2025-01-01 10:00:00"
  },
  "timestamp": 1737446400000
}
```

---

### 5. 更新个人信息 🔐

**接口地址：** `PUT /api/users/profile`

**请求示例：**

```bash
curl -X PUT http://154.201.70.215/api/users/profile \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -H "Content-Type: application/json" \
  -d '{
    "nickname": "张三丰",
    "email": "zhangsan_new@example.com",
    "phone": "13900139000",
    "gender": 1,
    "birthday": "1990-01-01"
  }'
```

**请求参数：**

```json
{
  "nickname": "张三丰",
  "email": "zhangsan_new@example.com",
  "phone": "13900139000",
  "gender": 1,
  "birthday": "1990-01-01"
}
```

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| nickname | String | ❌ | 昵称 |
| email | String | ❌ | 邮箱 |
| phone | String | ❌ | 手机号 |
| gender | Integer | ❌ | 性别：0-未知，1-男，2-女 |
| birthday | String | ❌ | 生日，格式：YYYY-MM-DD |

**响应示例：**

```json
{
  "code": 200,
  "message": "更新成功",
  "data": null,
  "timestamp": 1737446400000
}
```

---

### 6. 修改密码 🔐

**接口地址：** `PUT /api/users/password`

**请求示例：**

```bash
curl -X PUT http://154.201.70.215/api/users/password \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -H "Content-Type: application/json" \
  -d '{
    "oldPassword": "Pass123456",
    "newPassword": "NewPass123456",
    "confirmPassword": "NewPass123456"
  }'
```

**请求参数：**

```json
{
  "oldPassword": "Pass123456",
  "newPassword": "NewPass123456",
  "confirmPassword": "NewPass123456"
}
```

**响应示例：**

```json
{
  "code": 200,
  "message": "密码修改成功，请重新登录",
  "data": null,
  "timestamp": 1737446400000
}
```

---

### 7. 开通卖家 🔐

**接口地址：** `POST /api/users/become-seller`

**请求示例：**

```bash
curl -X POST http://154.201.70.215/api/users/become-seller \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -H "Content-Type: application/json" \
  -d '{
    "shopName": "张三的小店"
  }'
```

**请求参数：**

```json
{
  "shopName": "张三的小店"
}
```

**响应示例：**

```json
{
  "code": 200,
  "message": "开通卖家成功",
  "data": null,
  "timestamp": 1737446400000
}
```

---

### 8. 查询卖家状态 🔐

**接口地址：** `GET /api/users/seller-status`

> ⚠️ 路径更正：当前后端**不存在** `GET /api/seller/status`，请使用本接口。
>
> 卖家开通接口同理：请使用 `POST /api/users/become-seller`，而不是 `POST /api/seller/register`。

**请求示例：**

```bash
curl -X GET http://154.201.70.215/api/users/seller-status \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "isSeller": true
  },
  "timestamp": 1737446400000
}
```

---

### 9. 获取用户收藏列表 🔐

**接口地址：** `GET /api/users/collections`

**请求示例：**

```bash
curl -X GET "http://154.201.70.215/api/users/collections?pageNum=1&pageSize=10&sortBy=time&sortOrder=desc" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**查询参数：**

| 参数 | 类型 | 必填 | 默认值 | 说明 |
|------|------|------|--------|------|
| pageNum | Integer | ❌ | 1 | 页码 |
| pageSize | Integer | ❌ | 10 | 每页数量 |
| sortBy | String | ❌ | time | 排序字段：time-收藏时间，price-价格 |
| sortOrder | String | ❌ | desc | 排序方式：desc-降序，asc-升序 |

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "collectionId": 1,
        "productId": 10,
        "productName": "有机小白菜",
        "mainImage": "https://picsum.photos/seed/product1/400/400",
        "currentPrice": 6.50,
        "originalPrice": 8.00,
        "stock": 100,
        "salesVolume": 0,
        "status": 1,
        "collectTime": "2026-01-25 15:30:00",
        "inStock": true
      }
    ],
    "total": 15,
    "size": 10,
    "current": 1,
    "pages": 2
  },
  "timestamp": 1737446400000
}
```

**响应字段说明：**

| 字段 | 类型 | 说明 |
|------|------|------|
| collectionId | Long | 收藏记录ID |
| productId | Long | 商品ID |
| productName | String | 商品名称 |
| mainImage | String | 商品主图URL |
| currentPrice | BigDecimal | 现价 |
| originalPrice | BigDecimal | 原价 |
| stock | Integer | 库存 |
| salesVolume | Integer | 销量 |
| status | Integer | 商品状态：0-待审核，1-上架，2-下架，3-审核不通过 |
| collectTime | String | 收藏时间 |
| inStock | Boolean | 是否有货 |

---

### 10. 获取收藏商品总数 🔐 ⭐新增

**接口地址：** `GET /api/users/collections/count`

**请求示例：**

```bash
curl -X GET http://154.201.70.215/api/users/collections/count \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": 15,
  "timestamp": 1737446400000
}
```

**响应字段说明：**
- `data`: Long类型，用户收藏的商品总数

---

### 11. 清空收藏夹 🔐 ⭐新增

**接口地址：** `DELETE /api/users/collections/clear`

**请求示例：**

```bash
curl -X DELETE http://154.201.70.215/api/users/collections/clear \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例：**

```json
{
  "code": 200,
  "message": "清空收藏夹成功",
  "data": 15,
  "timestamp": 1737446400000
}
```

**响应字段说明：**
- `data`: Integer类型，清空的收藏数量

**注意事项：**
- 此操作不可撤销，建议前端添加二次确认提示
- 使用逻辑删除，数据可恢复（需要后台操作）

---

### 12. 添加收货地址 🔐 ⭐新增

**接口地址：** `POST /api/users/addresses`

**功能说明：** 添加新的收货地址

**请求参数：**

```json
{
  "receiverName": "张三",
  "receiverPhone": "13800138000",
  "province": "北京市",
  "city": "北京市",
  "district": "朝阳区",
  "detailAddress": "xxx街道xxx号",
  "isDefault": 0
}
```

**字段说明：**

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| receiverName | String | ✅ | 收货人姓名 |
| receiverPhone | String | ✅ | 收货人电话（11位手机号） |
| province | String | ✅ | 省份 |
| city | String | ✅ | 城市 |
| district | String | ✅ | 区县 |
| detailAddress | String | ✅ | 详细地址 |
| isDefault | Integer | ❌ | 是否默认：0-否，1-是（默认：0） |

**请求示例：**

```bash
curl -X POST http://154.201.70.215/api/users/addresses \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -H "Content-Type: application/json" \
  -d '{
    "receiverName": "张三",
    "receiverPhone": "13800138000",
    "province": "北京市",
    "city": "北京市",
    "district": "朝阳区",
    "detailAddress": "xxx街道xxx号",
    "isDefault": 1
  }'
```

**响应示例：**

```json
{
  "code": 200,
  "message": "添加地址成功",
  "data": 1,
  "timestamp": 1737446400000
}
```

**响应字段说明：**
- `data`: Long类型，新创建的地址ID

**注意事项：**
- 如果是第一个地址，自动设为默认地址
- 如果设置为默认地址，会自动取消其他默认地址

---

### 13. 更新收货地址 🔐 ⭐新增

**接口地址：** `PUT /api/users/addresses/{addressId}`

**功能说明：** 更新指定的收货地址

**路径参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| addressId | Long | ✅ | 地址ID |

**请求参数：** 同添加地址

**请求示例：**

```bash
curl -X PUT http://154.201.70.215/api/users/addresses/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -H "Content-Type: application/json" \
  -d '{
    "receiverName": "李四",
    "receiverPhone": "13900139000",
    "province": "上海市",
    "city": "上海市",
    "district": "浦东新区",
    "detailAddress": "yyy路yyy号",
    "isDefault": 0
  }'
```

**响应示例：**

```json
{
  "code": 200,
  "message": "更新地址成功",
  "data": null,
  "timestamp": 1737446400000
}
```

---

### 14. 删除收货地址 🔐 ⭐新增

**接口地址：** `DELETE /api/users/addresses/{addressId}`

**功能说明：** 删除指定的收货地址

**路径参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| addressId | Long | ✅ | 地址ID |

**请求示例：**

```bash
curl -X DELETE http://154.201.70.215/api/users/addresses/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例：**

```json
{
  "code": 200,
  "message": "删除地址成功",
  "data": null,
  "timestamp": 1737446400000
}
```

**注意事项：**
- 如果删除的是默认地址，会自动将第一个地址设为默认

---

### 15. 获取地址列表 🔐 ⭐新增

**接口地址：** `GET /api/users/addresses`

**功能说明：** 获取当前用户的所有收货地址

**请求示例：**

```bash
curl -X GET http://154.201.70.215/api/users/addresses \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": 1,
      "userId": 1,
      "receiverName": "张三",
      "receiverPhone": "13800138000",
      "province": "北京市",
      "city": "北京市",
      "district": "朝阳区",
      "detailAddress": "xxx街道xxx号",
      "fullAddress": "北京市北京市朝阳区xxx街道xxx号",
      "isDefault": 1,
      "createTime": "2026-01-30 10:00:00",
      "updateTime": "2026-01-30 10:00:00"
    },
    {
      "id": 2,
      "userId": 1,
      "receiverName": "李四",
      "receiverPhone": "13900139000",
      "province": "上海市",
      "city": "上海市",
      "district": "浦东新区",
      "detailAddress": "yyy路yyy号",
      "fullAddress": "上海市上海市浦东新区yyy路yyy号",
      "isDefault": 0,
      "createTime": "2026-01-30 11:00:00",
      "updateTime": "2026-01-30 11:00:00"
    }
  ],
  "timestamp": 1737446400000
}
```

**响应字段说明：**

| 字段 | 类型 | 说明 |
|------|------|------|
| id | Long | 地址ID |
| userId | Long | 用户ID |
| receiverName | String | 收货人姓名 |
| receiverPhone | String | 收货人电话 |
| province | String | 省份 |
| city | String | 城市 |
| district | String | 区县 |
| detailAddress | String | 详细地址 |
| fullAddress | String | 完整地址（省市区+详细地址） |
| isDefault | Integer | 是否默认：0-否，1-是 |
| createTime | String | 创建时间 |
| updateTime | String | 更新时间 |

**注意事项：**
- 地址列表按默认地址优先排序
- 然后按创建时间倒序排序

---

### 16. 获取地址详情 🔐 ⭐新增

**接口地址：** `GET /api/users/addresses/{addressId}`

**功能说明：** 获取指定地址的详细信息

**路径参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| addressId | Long | ✅ | 地址ID |

**请求示例：**

```bash
curl -X GET http://154.201.70.215/api/users/addresses/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例：** 同地址列表中的单个地址对象

---

### 17. 设置默认地址 🔐 ⭐新增

**接口地址：** `PUT /api/users/addresses/{addressId}/default`

**功能说明：** 将指定地址设为默认地址

**路径参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| addressId | Long | ✅ | 地址ID |

**请求示例：**

```bash
curl -X PUT http://154.201.70.215/api/users/addresses/1/default \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例：**

```json
{
  "code": 200,
  "message": "设置默认地址成功",
  "data": null,
  "timestamp": 1737446400000
}
```

**注意事项：**
- 设置新的默认地址会自动取消其他默认地址

---

### 18. 获取默认地址 🔐 ⭐新增

**接口地址：** `GET /api/users/addresses/default`

**功能说明：** 获取当前用户的默认收货地址

**请求示例：**

```bash
curl -X GET http://154.201.70.215/api/users/addresses/default \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例：** 同地址详情

**注意事项：**
- 如果没有设置默认地址，返回第一个地址
- 如果没有任何地址，返回 null

---

## 商品分类模块

### 1. 获取分类树 🔓

**接口地址：** `GET /api/categories/tree`

**请求示例：**

```bash
curl -X GET http://154.201.70.215/api/categories/tree
```

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": 1,
      "parentId": 0,
      "categoryName": "新鲜蔬菜",
      "categoryIcon": "https://example.com/icon/vegetable.png",
      "sortOrder": 1,
      "level": 1,
      "status": 1,
      "productCount": 150,
      "createTime": "2025-01-01 10:00:00",
      "children": [
        {
          "id": 11,
          "parentId": 1,
          "categoryName": "叶菜类",
          "categoryIcon": null,
          "sortOrder": 1,
          "level": 2,
          "status": 1,
          "productCount": 50,
          "createTime": "2025-01-01 10:00:00",
          "children": []
        }
      ]
    }
  ],
  "timestamp": 1737446400000
}
```

---

### 2. 获取一级分类 🔓

**接口地址：** `GET /api/categories/top`

**请求示例：**

```bash
curl -X GET http://154.201.70.215/api/categories/top
```

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": 1,
      "parentId": 0,
      "categoryName": "新鲜蔬菜",
      "categoryIcon": "https://example.com/icon/vegetable.png",
      "sortOrder": 1,
      "level": 1,
      "status": 1,
      "productCount": 150,
      "createTime": "2025-01-01 10:00:00"
    },
    {
      "id": 2,
      "parentId": 0,
      "categoryName": "新鲜水果",
      "categoryIcon": "https://example.com/icon/fruit.png",
      "sortOrder": 2,
      "level": 1,
      "status": 1,
      "productCount": 120,
      "createTime": "2025-01-01 10:00:00"
    }
  ],
  "timestamp": 1737446400000
}
```

---

### 3. 获取子分类 🔓

**接口地址：** `GET /api/categories/children/{parentId}`

**请求示例：**

```bash
curl -X GET http://154.201.70.215/api/categories/children/1
```

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": 11,
      "parentId": 1,
      "categoryName": "叶菜类",
      "categoryIcon": null,
      "sortOrder": 1,
      "level": 2,
      "status": 1,
      "productCount": 50,
      "createTime": "2025-01-01 10:00:00"
    },
    {
      "id": 12,
      "parentId": 1,
      "categoryName": "根茎类",
      "categoryIcon": null,
      "sortOrder": 2,
      "level": 2,
      "status": 1,
      "productCount": 40,
      "createTime": "2025-01-01 10:00:00"
    }
  ],
  "timestamp": 1737446400000
}
```

---

### 4. 获取分类详情 🔓

**接口地址：** `GET /api/categories/{categoryId}`

**请求示例：**

```bash
curl -X GET http://154.201.70.215/api/categories/1
```

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "parentId": 0,
    "categoryName": "新鲜蔬菜",
    "categoryIcon": "https://example.com/icon/vegetable.png",
    "sortOrder": 1,
    "level": 1,
    "status": 1,
    "productCount": 150,
    "createTime": "2025-01-01 10:00:00"
  },
  "timestamp": 1737446400000
}
```

---

## 商品模块

### 1. 搜索商品 🔓

**接口地址：** `POST /api/products/search`

**请求示例：**

```bash
curl -X POST http://154.201.70.215/api/products/search \
  -H "Content-Type: application/json" \
  -d '{
    "categoryId": 11,
    "keyword": "白菜",
    "minPrice": "5.00",
    "maxPrice": "20.00",
    "sortBy": "sales",
    "sortOrder": "desc",
    "pageNum": 1,
    "pageSize": 10
  }'
```

**请求参数：**

```json
{
  "categoryId": 11,
  "keyword": "白菜",
  "status": 1,
  "minPrice": "5.00",
  "maxPrice": "20.00",
  "sortBy": "sales",
  "sortOrder": "desc",
  "isRecommended": 0,
  "isNew": 0,
  "isHot": 0,
  "pageNum": 1,
  "pageSize": 10
}
```

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| categoryId | Long | ❌ | 分类ID |
| keyword | String | ❌ | 搜索关键词 |
| status | Integer | ❌ | 状态：1-上架 |
| minPrice | String | ❌ | 最低价格 |
| maxPrice | String | ❌ | 最高价格 |
| sortBy | String | ❌ | 排序字段：price-价格，sales-销量，time-时间 |
| sortOrder | String | ❌ | 排序方式：asc-升序，desc-降序 |
| isRecommended | Integer | ❌ | 是否推荐：0-否，1-是 |
| isNew | Integer | ❌ | 是否新品：0-否，1-是 |
| isHot | Integer | ❌ | 是否热卖：0-否，1-是 |
| pageNum | Integer | ❌ | 页码，默认1 |
| pageSize | Integer | ❌ | 每页数量，默认10 |

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "id": 1,
        "categoryId": 11,
        "categoryName": "叶菜类",
        "sellerId": 3,
        "productName": "有机小白菜",
        "productCode": "P001",
        "mainImage": "https://example.com/product/1.jpg",
        "description": "新鲜有机小白菜，当日采摘",
        "originalPrice": 8.00,
        "currentPrice": 6.50,
        "stock": 500,
        "salesVolume": 100,
        "viewCount": 500,
        "status": 1,
        "isRecommended": 1,
        "isNew": 1,
        "isHot": 0,
        "avgRating": 4.5,
        "reviewCount": 10,
        "createTime": "2025-01-01 10:00:00"
      }
    ],
    "total": 100,
    "size": 10,
    "current": 1,
    "pages": 10
  },
  "timestamp": 1737446400000
}
```

---

### 2. 获取商品详情 🔓

**接口地址：** `GET /api/products/{productId}`

**请求示例：**

```bash
curl -X GET http://154.201.70.215/api/products/1
```

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "categoryId": 11,
    "categoryName": "叶菜类",
    "sellerId": 3,
    "productName": "有机小白菜",
    "productCode": "P001",
    "mainImage": "https://example.com/product/1.jpg",
    "description": "新鲜有机小白菜，当日采摘",
    "detail": "<p>详细描述...</p>",
    "originalPrice": 8.00,
    "currentPrice": 6.50,
    "stock": 500,
    "salesVolume": 100,
    "viewCount": 501,
    "status": 1,
    "isRecommended": 1,
    "isNew": 1,
    "isHot": 0,
    "images": [
      {
        "id": 1,
        "productId": 1,
        "imageUrl": "https://example.com/product/1-1.jpg",
        "sortOrder": 1,
        "imageType": 1
      }
    ],
    "skuList": [
      {
        "id": 1,
        "productId": 1,
        "skuName": "小白菜 500g",
        "skuCode": "P001-S1",
        "skuImage": null,
        "price": 6.50,
        "stock": 300,
        "salesVolume": 50,
        "specInfo": "{\"重量\":\"500g\"}",
        "status": 1
      }
    ],
    "avgRating": 4.5,
    "reviewCount": 10,
    "ratingStatistics": {
      "fiveStar": 8,
      "fourStar": 1,
      "threeStar": 1,
      "twoStar": 0,
      "oneStar": 0
    },
    "createTime": "2025-01-01 10:00:00"
  },
  "timestamp": 1737446400000
}
```

---

### 3. 推荐商品 🔓

**接口地址：** `GET /api/products/recommended?limit=10`

**请求示例：**

```bash
curl -X GET "http://154.201.70.215/api/products/recommended?limit=10"
```

**查询参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| limit | Integer | ❌ | 返回数量，默认10 |

**响应示例：** 同搜索商品的 records 数组

---

### 4. 新品列表 🔓

**接口地址：** `GET /api/products/new?limit=10`

**请求示例：**

```bash
curl -X GET "http://154.201.70.215/api/products/new?limit=10"
```

**响应示例：** 同推荐商品

---

### 5. 热销商品 🔓

**接口地址：** `GET /api/products/hot?limit=10`

**请求示例：**

```bash
curl -X GET "http://154.201.70.215/api/products/hot?limit=10"
```

**响应示例：** 同推荐商品

---

### 6. 收藏商品 🔐

**接口地址：** `POST /api/products/{productId}/collect`

**请求示例：**

```bash
curl -X POST http://154.201.70.215/api/products/1/collect \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例：**

```json
{
  "code": 200,
  "message": "收藏成功",
  "data": null,
  "timestamp": 1737446400000
}
```

---

### 7. 取消收藏 🔐

**接口地址：** `DELETE /api/products/{productId}/uncollect`

**请求示例：**

```bash
curl -X DELETE http://154.201.70.215/api/products/1/uncollect \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例：**

```json
{
  "code": 200,
  "message": "取消收藏成功",
  "data": null,
  "timestamp": 1737446400000
}
```

---

### 8. 查询收藏状态 🔐

**接口地址：** `GET /api/products/{productId}/is-collected`

**请求示例：**

```bash
curl -X GET http://154.201.70.215/api/products/1/is-collected \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": true,
  "timestamp": 1737446400000
}
```

---

## 购物车模块

### 1. 添加商品到购物车 🔐

**接口地址：** `POST /api/cart/items`

**请求示例：**

```bash
curl -X POST http://154.201.70.215/api/cart/items \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -H "Content-Type: application/json" \
  -d '{
    "productId": 1,
    "skuId": 1,
    "quantity": 2
  }'
```

**请求参数：**

```json
{
  "productId": 1,
  "skuId": 1,
  "quantity": 2
}
```

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| productId | Long | ✅ | 商品ID |
| skuId | Long | ✅ | SKU ID |
| quantity | Integer | ✅ | 数量 |

**响应示例：**

```json
{
  "code": 200,
  "message": "添加成功",
  "data": null,
  "timestamp": 1737446400000
}
```

---

### 2. 获取购物车 🔐

**接口地址：** `GET /api/cart`

**请求示例：**

```bash
curl -X GET http://154.201.70.215/api/cart \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "items": [
      {
        "id": 1,
        "userId": 1,
        "productId": 1,
        "productName": "有机小白菜",
        "productImage": "https://example.com/product/1.jpg",
        "skuId": 1,
        "skuName": "小白菜 500g",
        "price": 6.50,
        "quantity": 2,
        "selected": true,
        "stock": 300,
        "status": 1,
        "createTime": "2025-01-20 10:00:00"
      }
    ],
    "totalCount": 2,
    "selectedCount": 2,
    "totalPrice": 13.00,
    "selectedPrice": 13.00
  },
  "timestamp": 1737446400000
}
```

---

### 3. 更新购物车商品数量 🔐

**接口地址：** `PUT /api/cart/items/{cartItemId}`

**请求示例：**

```bash
curl -X PUT http://154.201.70.215/api/cart/items/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -H "Content-Type: application/json" \
  -d '{
    "quantity": 3
  }'
```

**请求参数：**

```json
{
  "quantity": 3
}
```

**响应示例：**

```json
{
  "code": 200,
  "message": "更新成功",
  "data": null,
  "timestamp": 1737446400000
}
```

---

### 4. 删除购物车商品 🔐

**接口地址：** `DELETE /api/cart/items/{cartItemId}`

**请求示例：**

```bash
curl -X DELETE http://154.201.70.215/api/cart/items/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例：**

```json
{
  "code": 200,
  "message": "删除成功",
  "data": null,
  "timestamp": 1737446400000
}
```

---

### 5. 清空购物车 🔐

**接口地址：** `DELETE /api/cart/clear`

**请求示例：**

```bash
curl -X DELETE http://154.201.70.215/api/cart/clear \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例：**

```json
{
  "code": 200,
  "message": "清空成功",
  "data": null,
  "timestamp": 1737446400000
}
```

---

### 6. 选中/取消选中商品 🔐

**接口地址：** `PUT /api/cart/items/{cartItemId}/select?selected=true`

**请求示例：**

```bash
# 选中
curl -X PUT "http://154.201.70.215/api/cart/items/1/select?selected=true" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."

# 取消选中
curl -X PUT "http://154.201.70.215/api/cart/items/1/select?selected=false" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**查询参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| selected | Boolean | ✅ | true-选中，false-取消选中 |

**响应示例：**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": null,
  "timestamp": 1737446400000
}
```

---

### 7. 全选/取消全选 🔐

**接口地址：** `PUT /api/cart/select-all?selected=true`

**请求示例：**

```bash
# 全选
curl -X PUT "http://154.201.70.215/api/cart/select-all?selected=true" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."

# 取消全选
curl -X PUT "http://154.201.70.215/api/cart/select-all?selected=false" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例：**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": null,
  "timestamp": 1737446400000
}
```

---

### 8. 获取购物车商品数量 🔐

**接口地址：** `GET /api/cart/count`

**请求示例：**

```bash
curl -X GET http://154.201.70.215/api/cart/count \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": 5,
  "timestamp": 1737446400000
}
```

---

## 订单模块

### 1. 创建订单 🔐

**接口地址：** `POST /api/orders`

**请求示例：**

```bash
curl -X POST http://154.201.70.215/api/orders \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -H "Content-Type: application/json" \
  -d '{
    "cartItemIds": [1, 2],
    "receiverName": "张三",
    "receiverPhone": "13800138000",
    "receiverAddress": "北京市朝阳区xxx街道xxx号",
    "receiverProvince": "北京市",
    "receiverCity": "北京市",
    "receiverDistrict": "朝阳区",
    "remark": "请尽快发货"
  }'
```

**请求参数：**

```json
{
  "cartItemIds": [1, 2],
  "receiverName": "张三",
  "receiverPhone": "13800138000",
  "receiverAddress": "北京市朝阳区xxx街道xxx号",
  "receiverProvince": "北京市",
  "receiverCity": "北京市",
  "receiverDistrict": "朝阳区",
  "remark": "请尽快发货"
}
```

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| cartItemIds | Array | ✅ | 购物车商品ID列表 |
| receiverName | String | ✅ | 收货人姓名 |
| receiverPhone | String | ✅ | 收货人电话 |
| receiverAddress | String | ✅ | 详细地址 |
| receiverProvince | String | ✅ | 省份 |
| receiverCity | String | ✅ | 城市 |
| receiverDistrict | String | ✅ | 区县 |
| remark | String | ❌ | 订单备注 |

**响应示例：**

```json
{
  "code": 200,
  "message": "订单创建成功",
  "data": "202501210001",
  "timestamp": 1737446400000
}
```

---

### 2. 订单列表 🔐

**接口地址：** `GET /api/orders?pageNum=1&pageSize=10&status=1`

**请求示例：**

```bash
curl -X GET "http://154.201.70.215/api/orders?pageNum=1&pageSize=10&status=1" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**查询参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| status | Integer | ❌ | 订单状态：0-待支付，1-待发货，2-待收货，3-已完成，4-已取消 |
| pageNum | Integer | ❌ | 页码，默认1 |
| pageSize | Integer | ❌ | 每页数量，默认10 |

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "id": 1,
        "orderNo": "202501210001",
        "userId": 1,
        "totalAmount": 13.00,
        "payAmount": 13.00,
        "status": 3,
        "statusDesc": "已完成",
        "receiverName": "张三",
        "receiverPhone": "13800138000",
        "receiverAddress": "北京市朝阳区xxx街道xxx号",
        "remark": "请尽快发货",
        "reviewed": true,
        "reviewCount": 1,
        "orderItems": [
          {
            "id": 1,
            "productId": 1,
            "productName": "有机小白菜",
            "productImage": "https://example.com/product/1.jpg",
            "skuId": 1,
            "skuName": "小白菜 500g",
            "price": 6.50,
            "quantity": 2,
            "totalPrice": 13.00,
            "reviewed": true
          }
        ],
        "totalQuantity": 2,
        "createTime": "2025-01-21 10:00:00",
        "payTime": "2025-01-21 10:05:00"
      }
    ],
    "total": 10,
    "size": 10,
    "current": 1,
    "pages": 1
  },
  "timestamp": 1737446400000
}
```

**⭐ 新增评价相关字段说明：**

| 字段 | 类型 | 说明 |
|------|------|------|
| reviewed | Boolean | 订单级别：是否已全部评价（所有商品均已评价） |
| reviewCount | Integer | 已评价的商品种类数 |
| orderItems[].reviewed | Boolean | 订单项级别：该商品是否已评价 |

> **注意：** `reviewed` 和 `reviewCount` 仅在订单状态为 **已完成（status=3）** 时有实际意义。其他状态下 `reviewed=false`，`reviewCount=0`。

---

### 3. 订单详情 🔐

**接口地址：** `GET /api/orders/{orderId}`

**请求示例：**

```bash
curl -X GET http://154.201.70.215/api/orders/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例：** 同订单列表中的单个订单对象，包含 `reviewed`、`reviewCount` 和每个订单项的 `reviewed` 字段

---

### 4. 取消订单 🔐

**接口地址：** `PUT /api/orders/{orderId}/cancel?reason=不想要了`

**请求示例：**

```bash
curl -X PUT "http://154.201.70.215/api/orders/1/cancel?reason=不想要了" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**查询参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| reason | String | ❌ | 取消原因 |

**响应示例：**

```json
{
  "code": 200,
  "message": "订单已取消",
  "data": null,
  "timestamp": 1737446400000
}
```

---

### 5. 确认收货 🔐

**接口地址：** `PUT /api/orders/{orderId}/receive`

**请求示例：**

```bash
curl -X PUT http://154.201.70.215/api/orders/1/receive \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例：**

```json
{
  "code": 200,
  "message": "确认收货成功",
  "data": null,
  "timestamp": 1737446400000
}
```

---

### 6. 删除订单 🔐

**接口地址：** `DELETE /api/orders/{orderId}`

**请求示例：**

```bash
curl -X DELETE http://154.201.70.215/api/orders/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例：**

```json
{
  "code": 200,
  "message": "删除成功",
  "data": null,
  "timestamp": 1737446400000
}
```

---

### 7. 模拟支付 🔐

**接口地址：** `PUT /api/orders/{orderId}/pay`

**请求示例：**

```bash
curl -X PUT http://154.201.70.215/api/orders/1/pay \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例：**

```json
{
  "code": 200,
  "message": "支付成功",
  "data": null,
  "timestamp": 1737446400000
}
```

---

### 8. 订单数量统计 🔐

**接口地址：** `GET /api/orders/count`

**请求示例：**

```bash
curl -X GET http://154.201.70.215/api/orders/count \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "unpaid": 2,
    "unshipped": 3,
    "shipped": 1,
    "completed": 10,
    "cancelled": 1
  },
  "timestamp": 1737446400000
}
```

---

### 9. 查询物流信息 🔐

**接口地址：** `GET /api/orders/{orderId}/logistics`

**请求示例：**

```bash
curl -X GET http://154.201.70.215/api/orders/1/logistics \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "orderId": 1,
    "orderNo": "202501210001",
    "logisticsCompany": "顺丰速运",
    "logisticsNo": "SF1234567890",
    "status": 2,
    "statusText": "运输中",
    "tracks": [
      {
        "time": "2025-01-21 14:00:00",
        "status": "已发货",
        "description": "您的订单已发货"
      },
      {
        "time": "2025-01-21 16:00:00",
        "status": "运输中",
        "description": "快件已到达北京转运中心"
      }
    ],
    "updateTime": "2025-01-21 16:00:00"
  },
  "timestamp": 1737446400000
}
```

---

## 钱包模块

### 1. 获取钱包信息 🔐

**接口地址：** `GET /api/wallet`

**请求示例：**

```bash
curl -X GET http://154.201.70.215/api/wallet \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "userId": 1,
    "balance": 1000.00,
    "frozenAmount": 0.00,
    "totalIncome": 5000.00,
    "totalExpense": 4000.00,
    "updateTime": "2025-01-21 10:00:00"
  },
  "timestamp": 1737446400000
}
```

---

### 2. 钱包充值 🔐

**接口地址：** `POST /api/wallet/recharge`

**请求示例：**

```bash
curl -X POST http://154.201.70.215/api/wallet/recharge \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -H "Content-Type: application/json" \
  -d '{
    "amount": 100.00,
    "paymentMethod": "alipay"
  }'
```

**请求参数：**

```json
{
  "amount": 100.00,
  "paymentMethod": "alipay"
}
```

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| amount | Decimal | ✅ | 充值金额 |
| paymentMethod | String | ✅ | 支付方式：alipay-支付宝，wechat-微信 |

**响应示例：**

```json
{
  "code": 200,
  "message": "充值成功",
  "data": null,
  "timestamp": 1737446400000
}
```

---

### 3. 交易记录 🔐

**接口地址：** `GET /api/wallet/transactions?pageNum=1&pageSize=10&type=1`

**请求示例：**

```bash
curl -X GET "http://154.201.70.215/api/wallet/transactions?pageNum=1&pageSize=10&type=1" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**查询参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| type | Integer | ❌ | 交易类型：1-充值，2-消费，3-退款，4-提现 |
| startDate | String | ❌ | 开始日期，格式：YYYY-MM-DD |
| endDate | String | ❌ | 结束日期，格式：YYYY-MM-DD |
| pageNum | Integer | ❌ | 页码，默认1 |
| pageSize | Integer | ❌ | 每页数量，默认10 |

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "id": 1,
        "userId": 1,
        "type": 1,
        "typeText": "充值",
        "amount": 100.00,
        "balance": 1000.00,
        "description": "支付宝充值",
        "relatedOrderNo": null,
        "createTime": "2025-01-21 10:00:00"
      },
      {
        "id": 2,
        "userId": 1,
        "type": 2,
        "typeText": "消费",
        "amount": -13.00,
        "balance": 987.00,
        "description": "订单支付",
        "relatedOrderNo": "202501210001",
        "createTime": "2025-01-21 10:05:00"
      }
    ],
    "total": 50,
    "size": 10,
    "current": 1,
    "pages": 5
  },
  "timestamp": 1737446400000
}
```

---

## 浏览历史模块

### 1. 记录浏览历史 🔐

**接口地址：** `POST /api/browse-history?productId=1`

**请求示例：**

```bash
curl -X POST "http://154.201.70.215/api/browse-history?productId=1" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**查询参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| productId | Long | ✅ | 商品ID |

**响应示例：**

```json
{
  "code": 200,
  "message": "记录成功",
  "data": null,
  "timestamp": 1737446400000
}
```

---

### 2. 浏览历史列表 🔐

**接口地址：** `GET /api/browse-history?pageNum=1&pageSize=10`

**请求示例：**

```bash
curl -X GET "http://154.201.70.215/api/browse-history?pageNum=1&pageSize=10" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**查询参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| pageNum | Integer | ❌ | 页码，默认1 |
| pageSize | Integer | ❌ | 每页数量，默认10 |

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "id": 1,
        "userId": 1,
        "productId": 1,
        "productName": "有机小白菜",
        "productImage": "https://example.com/product/1.jpg",
        "productPrice": 6.50,
        "productStatus": 1,
        "browseTime": "2025-01-21 10:00:00"
      },
      {
        "id": 2,
        "userId": 1,
        "productId": 2,
        "productName": "新鲜苹果",
        "productImage": "https://example.com/product/2.jpg",
        "productPrice": 8.00,
        "productStatus": 1,
        "browseTime": "2025-01-21 09:30:00"
      }
    ],
    "total": 20,
    "size": 10,
    "current": 1,
    "pages": 2
  },
  "timestamp": 1737446400000
}
```

---

### 3. 删除单条浏览历史 🔐

**接口地址：** `DELETE /api/browse-history/{historyId}`

**请求示例：**

```bash
curl -X DELETE http://154.201.70.215/api/browse-history/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**路径参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| historyId | Long | ✅ | 浏览历史记录ID |

**响应示例：**

```json
{
  "code": 200,
  "message": "删除成功",
  "data": null,
  "timestamp": 1737446400000
}
```

---

### 4. 清空浏览历史 🔐

**接口地址：** `DELETE /api/browse-history/clear`

**请求示例：**

```bash
curl -X DELETE http://154.201.70.215/api/browse-history/clear \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例：**

```json
{
  "code": 200,
  "message": "清空成功",
  "data": null,
  "timestamp": 1737446400000
}
```

---

## 商品评价模块

### 1. 发布评价 🔐

**接口地址：** `POST /api/reviews`

**功能说明：** 用户对已完成订单中的商品发布评价。系统会校验：订单归属当前用户、订单状态为已完成（status=3）、订单中包含该商品、同一订单同一商品不可重复评价。

**请求示例：**

```bash
curl -X POST http://154.201.70.215/api/reviews \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -H "Content-Type: application/json" \
  -d '{
    "productId": 1,
    "orderId": 1,
    "rating": 5,
    "content": "商品很新鲜，包装也很好，会回购！",
    "images": "[\"https://example.com/review/1.jpg\",\"https://example.com/review/2.jpg\"]"
  }'
```

**请求参数：**

```json
{
  "productId": 1,
  "orderId": 1,
  "rating": 5,
  "content": "商品很新鲜，包装也很好，会回购！",
  "images": "[\"https://example.com/review/1.jpg\",\"https://example.com/review/2.jpg\"]"
}
```

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| productId | Long | ✅ | 商品ID |
| orderId | Long | ✅ | 订单ID |
| rating | Integer | ✅ | 评分：1-5星 |
| content | String | ❌ | 评价内容 |
| images | String | ❌ | 评价图片，支持两种格式：JSON数组字符串（推荐，如`["url1","url2"]`）或逗号分隔URL（如`url1,url2`）；空值会按无图处理 |

**响应示例：**

```json
{
  "code": 200,
  "message": "评价发布成功",
  "data": null,
  "timestamp": 1737446400000
}
```

**业务校验规则：**

| 校验项 | 错误码 | 说明 |
|--------|--------|------|
| 商品不存在 | 400 | 商品ID无效 |
| 订单不存在 | 400 | 订单ID无效或已删除 |
| 无权评价 | 403 | 订单不属于当前用户 |
| 订单未完成 | 400 | 只有已完成（status=3）的订单才能评价 |
| 商品不在订单中 | 400 | 该订单中不包含此商品 |
| 重复评价 | 400 | 同一订单同一商品不可重复评价 |
| images格式错误 | 400 | `images` 若传 JSON 格式，必须是合法 JSON 数组（如 `["url1","url2"]`） |

---

### 2. 商品评价列表 🔓

**接口地址：** `GET /api/reviews/product/{productId}`

**请求示例：**

```bash
curl -X GET "http://154.201.70.215/api/reviews/product/1?rating=5&pageNum=1&pageSize=10"
```

**路径参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| productId | Long | ✅ | 商品ID |

**查询参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| rating | Integer | ❌ | 评分筛选：1-5星 |
| pageNum | Integer | ❌ | 页码，默认1 |
| pageSize | Integer | ❌ | 每页数量，默认10 |

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "id": 1,
        "productId": 1,
        "productName": "有机小白菜",
        "productMainImage": "https://example.com/product/1.jpg",
        "userId": 2,
        "userNickname": "李四",
        "userAvatar": "https://example.com/avatar/2.jpg",
        "orderId": 1,
        "rating": 5,
        "content": "商品很新鲜，包装也很好，会回购！",
        "images": "[\"https://example.com/review/1.jpg\"]",
        "replyContent": "感谢您的支持！",
        "replyTime": "2025-01-21 11:00:00",
        "status": 1,
        "createTime": "2025-01-21 10:30:00"
      }
    ],
    "total": 10,
    "size": 10,
    "current": 1,
    "pages": 1
  },
  "timestamp": 1737446400000
}
```

---

### 3. 我的评价列表 🔐 ⭐新增

**接口地址：** `GET /api/reviews/mine`

**功能说明：** 查询当前登录用户发布的所有评价，支持分页

**请求示例：**

```bash
curl -X GET "http://154.201.70.215/api/reviews/mine?pageNum=1&pageSize=10" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**查询参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| pageNum | Integer | ❌ | 页码，默认1 |
| pageSize | Integer | ❌ | 每页数量，默认10 |

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "id": 1,
        "productId": 1,
        "productName": "有机小白菜",
        "productMainImage": "https://example.com/product/1.jpg",
        "userId": 2,
        "userNickname": "李四",
        "userAvatar": "https://example.com/avatar/2.jpg",
        "orderId": 1,
        "rating": 5,
        "content": "商品很新鲜，包装也很好，会回购！",
        "images": "[\"https://example.com/review/1.jpg\"]",
        "replyContent": "感谢您的支持！",
        "replyTime": "2025-01-21 11:00:00",
        "status": 1,
        "createTime": "2025-01-21 10:30:00"
      }
    ],
    "total": 5,
    "size": 10,
    "current": 1,
    "pages": 1
  },
  "timestamp": 1737446400000
}
```

---

### 4. 查询订单评价状态 🔐 ⭐新增

**接口地址：** `GET /api/reviews/order/{orderId}`

**功能说明：** 查询指定订单中哪些商品已经评价，返回已评价的商品ID列表。用于前端判断是否显示"去评价"按钮。

**请求示例：**

```bash
curl -X GET http://154.201.70.215/api/reviews/order/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**路径参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| orderId | Long | ✅ | 订单ID |

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "reviewedProductIds": [1, 3]
  },
  "timestamp": 1737446400000
}
```

**响应字段说明：**
- `reviewedProductIds`: 该订单中已评价的商品ID数组。前端可对比订单明细中的productId，判断每个商品是否已评价。

---

### 5. 商家回复评价 🔐🏪

**接口地址：** `PUT /api/reviews/{reviewId}/reply?replyContent=感谢支持`

**功能说明：** 商家回复用户的评价。系统会校验：评价对应的商品属于当前商家、评价未被回复过（不可重复回复）。

**请求示例：**

```bash
curl -X PUT "http://154.201.70.215/api/reviews/1/reply?replyContent=感谢您的支持！" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**路径参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| reviewId | Long | ✅ | 评价ID |

**查询参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| replyContent | String | ✅ | 回复内容 |

**响应示例：**

```json
{
  "code": 200,
  "message": "回复成功",
  "data": null,
  "timestamp": 1737446400000
}
```

**注意事项：**
- 商家只能回复自己商品的评价，否则返回 403
- 已回复的评价不可重复回复，否则返回 400

---

### 6. 删除评价 🔐👑

**接口地址：** `DELETE /api/reviews/{reviewId}`

**功能说明：** 管理员删除违规评价（逻辑删除）

**请求示例：**

```bash
curl -X DELETE http://154.201.70.215/api/reviews/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**路径参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| reviewId | Long | ✅ | 评价ID |

**响应示例：**

```json
{
  "code": 200,
  "message": "评价删除成功",
  "data": null,
  "timestamp": 1737446400000
}
```

---

### 7. 隐藏/显示评价 🔐👑

**接口地址：** `PUT /api/reviews/{reviewId}/status`

**功能说明：** 管理员隐藏或显示评价

**请求示例：**

```bash
# 隐藏评价
curl -X PUT "http://154.201.70.215/api/reviews/1/status?status=0" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."

# 显示评价
curl -X PUT "http://154.201.70.215/api/reviews/1/status?status=1" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**路径参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| reviewId | Long | ✅ | 评价ID |

**查询参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| status | Integer | ✅ | 状态：0-隐藏，1-显示 |

**响应示例：**

```json
{
  "code": 200,
  "message": "状态更新成功",
  "data": null,
  "timestamp": 1737446400000
}
```

---

## 卖家模块

### 1. 上传商品图片 🔐🏪

**接口地址：** `POST /api/seller/upload/image`

**请求示例：**

```bash
curl -X POST http://154.201.70.215/api/seller/upload/image \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -F "file=@/path/to/image.jpg"
```

**请求参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| file | File | ✅ | 图片文件（支持jpg、png、gif） |

**响应示例：**

```json
{
  "code": 200,
  "message": "上传成功",
  "data": {
    "url": "https://example.com/upload/product/20250121/abc123.jpg",
    "fileName": "abc123.jpg",
    "fileSize": 102400
  },
  "timestamp": 1737446400000
}
```

---

### 2. 创建商品 🔐🏪

**接口地址：** `POST /api/seller/products`

**请求示例：**

```bash
curl -X POST http://154.201.70.215/api/seller/products \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -H "Content-Type: application/json" \
  -d '{
    "categoryId": 11,
    "productName": "有机小白菜",
    "productCode": "P001",
    "mainImage": "https://example.com/product/1.jpg",
    "description": "新鲜有机小白菜，当日采摘",
    "detail": "<p>详细描述...</p>",
    "originalPrice": 8.00,
    "currentPrice": 6.50,
    "stock": 500,
    "isRecommended": 1,
    "isNew": 1,
    "isHot": 0,
    "images": [
      {"imageUrl": "https://example.com/product/1-1.jpg", "sortOrder": 1},
      {"imageUrl": "https://example.com/product/1-2.jpg", "sortOrder": 2}
    ],
    "skus": [
      {
        "skuName": "小白菜 500g",
        "skuCode": "P001-S1",
        "price": 6.50,
        "stock": 300,
        "specInfo": "{\"重量\":\"500g\"}"
      }
    ]
  }'
```

**请求参数：**

```json
{
  "categoryId": 11,
  "productName": "有机小白菜",
  "productCode": "P001",
  "mainImage": "https://example.com/product/1.jpg",
  "description": "新鲜有机小白菜，当日采摘",
  "detail": "<p>详细描述...</p>",
  "originalPrice": 8.00,
  "currentPrice": 6.50,
  "stock": 500,
  "isRecommended": 1,
  "isNew": 1,
  "isHot": 0,
  "images": [
    {"imageUrl": "https://example.com/product/1-1.jpg", "sortOrder": 1}
  ],
  "skus": [
    {
      "skuName": "小白菜 500g",
      "skuCode": "P001-S1",
      "price": 6.50,
      "stock": 300,
      "specInfo": "{\"重量\":\"500g\"}"
    }
  ]
}
```

**响应示例：**

```json
{
  "code": 200,
  "message": "商品创建成功",
  "data": {
    "productId": 1
  },
  "timestamp": 1737446400000
}
```

---

### 3. 更新商品 🔐🏪

**接口地址：** `PUT /api/seller/products/{productId}`

**请求示例：**

```bash
curl -X PUT http://154.201.70.215/api/seller/products/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -H "Content-Type: application/json" \
  -d '{
    "productName": "有机小白菜（更新）",
    "currentPrice": 7.00,
    "stock": 600
  }'
```

**响应示例：**

```json
{
  "code": 200,
  "message": "更新成功",
  "data": null,
  "timestamp": 1737446400000
}
```

---

### 4. 上下架商品 🔐🏪

**接口地址：** `PUT /api/seller/products/{productId}/status`

**功能说明：** 商家操作商品状态。

- `status=2`：直接下架（商品状态变为 **2-下架**）
- `status=1`：**申请上架**（商品状态会被置为 **0-待审核**，需要管理员审核通过后才会变为 **1-上架中**）

```bash
# 申请上架（提交后进入待审核状态 0）
curl -X PUT http://154.201.70.215/api/seller/products/1/status \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -H "Content-Type: application/json" \
  -d '{"status": 1}'

# 直接下架
curl -X PUT http://154.201.70.215/api/seller/products/1/status \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -H "Content-Type: application/json" \
  -d '{"status": 2}'
```

**请求参数：**

```json
{
  "status": 1
}
```

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| status | Integer | ✅ | 状态：1-申请上架（进入待审核），2-直接下架 |

**响应示例：**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": null,
  "timestamp": 1737446400000
}
```

---

### 5. 删除商品 🔐🏪
### 5. 获取商品列表 (管理员) 🔐🛡️ ⭐新增

**接口地址：** `GET /api/admin/products?status=0&pageNum=1&pageSize=10`

**功能说明：** 管理员分页获取商品列表，默认筛选待审核商品。

**请求示例：**

```bash
curl -X GET "http://154.201.70.215/api/admin/products?status=0&pageNum=1&pageSize=10" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**请求参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| status | Integer | ❌ | 状态筛选：0-待审核（默认），1-上架，2-下架，3-审核不通过 |
| categoryId | Long | ❌ | 分类ID |
| keyword | String | ❌ | 搜索关键词 |
| pageNum | Integer | ❌ | 页码，默认1 |
| pageSize | Integer | ❌ | 每页数量，默认10 |

**响应示例：**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "records": [
      {
        "id": 1,
        "productName": "有机小白菜",
        "productCode": "P001",
        "mainImage": "https://example.com/product.jpg",
        "currentPrice": 6.50,
        "stock": 500,
        "salesVolume": 100,
        "status": 0,
        "createTime": "2026-01-30 10:00:00"
      }
    ],
    "total": 1,
    "size": 10,
    "current": 1
  }
}
```

---

### 6. 商品审核 (管理员) 🔐🛡️ ⭐新增

**接口地址：** `PUT /api/products/{productId}/audit`

**功能说明：** 管理员审核商品。通过后商品自动上架（status=1），驳回则记录原因。

**请求示例：**

```bash
curl -X PUT http://154.201.70.215/api/products/1/audit \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -H "Content-Type: application/json" \
  -d '{
    "status": 1,
    "auditRemark": "符合上架标准"
  }'
```

**请求参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| status | Integer | ✅ | 审核结果：1-通过(上架)，3-驳回 |
| auditRemark | String | ❌ | 审核备注/驳回原因 |

**响应示例：**

```json
{
  "code": 200,
  "message": "审核成功",
  "data": null
}
```

---

### 6. 删除商品 🔐🏪

**接口地址：** `DELETE /api/seller/products/{productId}`

**请求示例：**

```bash
curl -X DELETE http://154.201.70.215/api/seller/products/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例：**

```json
{
  "code": 200,
  "message": "商品删除成功",
  "data": null
}
```

---

### 6. 商品列表 🔐🏪

**接口地址：** `GET /api/seller/products?pageNum=1&pageSize=10&status=1&keyword=白菜`

**请求示例：**

```bash
curl -X GET "http://154.201.70.215/api/seller/products?pageNum=1&pageSize=10&status=1&keyword=白菜" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**查询参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| status | Integer | ❌ | 状态：0-待审核，1-上架，2-下架，3-审核不通过 |
| keyword | String | ❌ | 搜索关键词 |
| categoryId | Long | ❌ | 分类ID |
| pageNum | Integer | ❌ | 页码，默认1 |
| pageSize | Integer | ❌ | 每页数量，默认10 |

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "id": 1,
        "categoryId": 11,
        "categoryName": "叶菜类",
        "productName": "有机小白菜",
        "productCode": "P001",
        "mainImage": "https://example.com/product/1.jpg",
        "currentPrice": 6.50,
        "stock": 500,
        "salesVolume": 100,
        "status": 1,
        "statusText": "上架",
        "createTime": "2025-01-01 10:00:00"
      }
    ],
    "total": 50,
    "size": 10,
    "current": 1,
    "pages": 5
  },
  "timestamp": 1737446400000
}
```

---

### 7. 商品详情 🔐🏪

**接口地址：** `GET /api/seller/products/{productId}`

**请求示例：**

```bash
curl -X GET http://154.201.70.215/api/seller/products/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例：** 同商品搜索接口的详细信息

---

### 8. 库存列表 🔐🏪

**接口地址：** `GET /api/seller/inventory?pageNum=1&pageSize=10&lowStock=true`

**请求示例：**

```bash
curl -X GET "http://154.201.70.215/api/seller/inventory?pageNum=1&pageSize=10&lowStock=true" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**查询参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| productId | Long | ❌ | 商品ID |
| lowStock | Boolean | ❌ | 是否只显示低库存 |
| pageNum | Integer | ❌ | 页码，默认1 |
| pageSize | Integer | ❌ | 每页数量，默认10 |

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "skuId": 1,
        "productId": 1,
        "productName": "有机小白菜",
        "skuName": "小白菜 500g",
        "skuCode": "P001-S1",
        "stock": 50,
        "salesVolume": 250,
        "status": 1,
        "isLowStock": true,
        "updateTime": "2025-01-21 10:00:00"
      }
    ],
    "total": 10,
    "size": 10,
    "current": 1,
    "pages": 1
  },
  "timestamp": 1737446400000
}
```

---

### 9. 调整库存 🔐🏪

**接口地址：** `PUT /api/seller/inventory/{skuId}`

**请求示例：**

```bash
curl -X PUT http://154.201.70.215/api/seller/inventory/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -H "Content-Type: application/json" \
  -d '{
    "adjustType": 1,
    "adjustQuantity": 100,
    "remark": "补货"
  }'
```

**请求参数：**

```json
{
  "adjustType": 1,
  "adjustQuantity": 100,
  "remark": "补货"
}
```

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| adjustType | Integer | ✅ | 调整类型：1-增加，2-减少 |
| adjustQuantity | Integer | ✅ | 调整数量 |
| remark | String | ❌ | 备注 |

**响应示例：**

```json
{
  "code": 200,
  "message": "库存调整成功",
  "data": {
    "skuId": 1,
    "beforeStock": 50,
    "afterStock": 150,
    "adjustQuantity": 100
  },
  "timestamp": 1737446400000
}
```

---

### 10. 销售统计 🔐🏪

**接口地址：** `GET /api/seller/statistics/sales?startDate=2025-01-01&endDate=2025-01-31&timeUnit=day`

**请求示例：**

```bash
curl -X GET "http://154.201.70.215/api/seller/statistics/sales?startDate=2025-01-01&endDate=2025-01-31&timeUnit=day" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**查询参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| startDate | String | ❌ | 开始日期，格式：YYYY-MM-DD |
| endDate | String | ❌ | 结束日期，格式：YYYY-MM-DD |
| timeUnit | String | ❌ | 时间单位：day-天，week-周，month-月 |

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "totalSales": 15000.00,
    "totalOrders": 150,
    "totalProducts": 500,
    "chartData": [
      {
        "date": "2025-01-01",
        "sales": 500.00,
        "orders": 5,
        "products": 15
      },
      {
        "date": "2025-01-02",
        "sales": 600.00,
        "orders": 6,
        "products": 18
      }
    ]
  },
  "timestamp": 1737446400000
}
```

---

### 11. 商品排行 🔐🏪

**接口地址：** `GET /api/seller/statistics/products?limit=10&orderBy=sales`

**请求示例：**

```bash
curl -X GET "http://154.201.70.215/api/seller/statistics/products?limit=10&orderBy=sales" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**查询参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| limit | Integer | ❌ | 返回数量，默认10 |
| orderBy | String | ❌ | 排序字段：sales-销量，revenue-收入 |
| startDate | String | ❌ | 开始日期 |
| endDate | String | ❌ | 结束日期 |

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "productId": 1,
      "productName": "有机小白菜",
      "productImage": "https://example.com/product/1.jpg",
      "salesVolume": 100,
      "revenue": 650.00,
      "rank": 1
    }
  ],
  "timestamp": 1737446400000
}
```

---

### 12. 收益统计 🔐🏪

**接口地址：** `GET /api/seller/statistics/revenue`

**请求示例：**

```bash
curl -X GET http://154.201.70.215/api/seller/statistics/revenue \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "totalRevenue": 50000.00,
    "availableBalance": 30000.00,
    "frozenAmount": 5000.00,
    "withdrawnAmount": 15000.00,
    "todayRevenue": 500.00,
    "monthRevenue": 8000.00
  },
  "timestamp": 1737446400000
}
```

---
### 12. 商家订单概览统计 🔐🏪 ⭐新增

**接口地址：** `GET /api/seller/statistics/order-overview`

**功能说明：** 获取商家当前的核心订单统计指标（累计口径，排除已取消订单）。主要用于商家模式首页展示。

**请求示例：**

```bash
curl -X GET http://154.201.70.215/api/seller/statistics/order-overview \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "totalCount": 120,
    "waitShipCount": 15,
    "finishedCount": 85
  },
  "timestamp": 1737446400000
}
```

**响应字段说明：**

| 字段 | 类型 | 说明 |
| :--- | :--- | :--- |
| totalCount | Integer | 订单总数（已排除状态为“已取消”的订单） |
| waitShipCount | Integer | 待发货订单数（状态为“待发货”） |
| finishedCount | Integer | 已完成订单数（状态为“已完成”） |

---

### 12. 获取商家订单列表 🔐🏪 ⭐新增

**接口地址：** `GET /api/seller/orders`

**功能说明：** 商家查看自己的订单列表，支持多条件筛选

**请求参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| pageNum | Integer | ❌ | 页码（默认：1） |
| pageSize | Integer | ❌ | 每页数量（默认：10） |
| status | Integer | ❌ | 订单状态：0-待支付，1-待发货，2-待收货，3-已完成，4-已取消 |
| orderNo | String | ❌ | 订单号搜索 |
| startDate | String | ❌ | 开始日期（格式：yyyy-MM-dd） |
| endDate | String | ❌ | 结束日期（格式：yyyy-MM-dd） |
| keyword | String | ❌ | 关键词搜索（买家昵称/手机号） |

**请求示例：**

```bash
# 查询待发货订单
curl -X GET "http://154.201.70.215/api/seller/orders?status=1&pageNum=1&pageSize=20" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."

# 按订单号搜索
curl -X GET "http://154.201.70.215/api/seller/orders?orderNo=202601300001" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."

# 按日期范围查询
curl -X GET "http://154.201.70.215/api/seller/orders?startDate=2026-01-01&endDate=2026-01-31" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "id": 1,
        "orderNo": "202601300001",
        "buyerId": 2,
        "buyerNickname": "张三",
        "buyerPhone": "13800138000",
        "totalAmount": 100.00,
        "payAmount": 100.00,
        "status": 1,
        "statusText": "待发货",
        "receiverName": "李四",
        "receiverPhone": "13900139000",
        "receiverAddress": "北京市朝阳区xxx街道xxx号",
        "remark": "请尽快发货",
        "payTime": "2026-01-30 10:00:00",
        "deliverTime": null,
        "createTime": "2026-01-30 09:55:00",
        "itemCount": 2,
        "totalQuantity": 5,
        "logisticsNo": null,
        "logisticsCompany": null,
        "items": [
          {
            "id": 1,
            "orderId": 1,
            "orderNo": "202601300001",
            "productId": 10,
            "skuId": 15,
            "productName": "有机小白菜",
            "skuName": "小白菜 500g",
            "productImage": "https://example.com/product.jpg",
            "price": 6.50,
            "quantity": 2,
            "totalPrice": 13.00
          }
        ]
      }
    ],
    "total": 50,
    "size": 10,
    "current": 1,
    "pages": 5
  },
  "timestamp": 1737446400000
}
```

---

### 13. 获取商家订单详情 🔐🏪 ⭐新增

**接口地址：** `GET /api/seller/orders/{orderId}`

**功能说明：** 查看指定订单的完整信息

**路径参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| orderId | Long | ✅ | 订单ID |

**请求示例：**

```bash
curl -X GET http://154.201.70.215/api/seller/orders/123 \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例：** 同订单列表中的单个订单对象，包含完整的订单明细

---

### 14. 获取商家订单统计 🔐🏪 ⭐新增

**接口地址：** `GET /api/seller/orders/count`

**功能说明：** 统计各状态订单数量

**请求示例：**

```bash
curl -X GET http://154.201.70.215/api/seller/orders/count \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "unpaid": 5,
    "unshipped": 12,
    "shipped": 8,
    "completed": 156,
    "cancelled": 3,
    "total": 184
  },
  "timestamp": 1737446400000
}
```

**字段说明：**

| 字段 | 说明 |
|------|------|
| unpaid | 待支付订单数 |
| unshipped | 待发货订单数 |
| shipped | 待收货订单数（已发货） |
| completed | 已完成订单数 |
| cancelled | 已取消订单数 |
| total | 订单总数 |

---

### 15. 商家回复评价 🔐🏪 ⭐新增

**接口地址：** `PUT /api/seller/reviews/{reviewId}/reply`

**功能说明：** 商家回复用户对自己商品的评价。系统会校验评价对应的商品归属当前商家，且不可重复回复。

**请求示例：**

```bash
curl -X PUT "http://154.201.70.215/api/seller/reviews/1/reply?replyContent=感谢您的支持！" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**路径参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| reviewId | Long | ✅ | 评价ID |

**查询参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| replyContent | String | ✅ | 回复内容 |

**响应示例：**

```json
{
  "code": 200,
  "message": "回复成功",
  "data": null,
  "timestamp": 1737446400000
}
```

**注意事项：**
- 商家只能回复自己商品的评价，否则返回 403
- 已回复的评价不可重复回复，否则返回 400

---

### 16. 查看商品评价列表 🔐🏪 ⭐新增

**接口地址：** `GET /api/seller/reviews`

**功能说明：** 商家查看指定商品的评价列表，支持按评分筛选和分页

**请求示例：**

```bash
curl -X GET "http://154.201.70.215/api/seller/reviews?productId=1&pageNum=1&pageSize=10" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**查询参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| productId | Long | ✅ | 商品ID |
| rating | Integer | ❌ | 评分筛选：1-5星 |
| pageNum | Integer | ❌ | 页码，默认1 |
| pageSize | Integer | ❌ | 每页数量，默认10 |

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "id": 1,
        "productId": 1,
        "productName": "有机小白菜",
        "productMainImage": "https://example.com/product/1.jpg",
        "userId": 2,
        "userNickname": "李四",
        "userAvatar": "https://example.com/avatar/2.jpg",
        "orderId": 1,
        "rating": 5,
        "content": "商品很新鲜，包装也很好！",
        "images": "[\"https://example.com/review/1.jpg\"]",
        "replyContent": null,
        "replyTime": null,
        "status": 1,
        "createTime": "2025-01-21 10:30:00"
      }
    ],
    "total": 10,
    "size": 10,
    "current": 1,
    "pages": 1
  },
  "timestamp": 1737446400000
}
```

**参数校验与错误说明：**
- `productId` 为必填参数
- 若未传 `productId`，接口返回：`400`，消息：`缺少必要参数：productId`

**错误响应示例（缺少 productId）：**


```json
{
  "code": 400,
  "message": "缺少必要参数：productId",
  "data": null,
  "timestamp": 1737446400000
}
```


---
## AI模块

### 1. AI 智能问答（非流式）🔐

**接口地址：** `POST /api/ai/chat`

**功能说明：** 基于电商知识库与商品推荐的智能问答。支持多轮会话。

**请求参数：**

```json
{
  "question": "有适合宝宝的有机蔬菜吗？",
  "sessionId": "optional-session-id"
}
```

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| question | String | ✅ | 用户问题 |
| sessionId | String | ❌ | 会话ID（多轮对话使用） |

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "answer": "推荐有机小白菜、有机胡萝卜等产品。",
    "sessionId": "optional-session-id",
    "products": [
      {
        "productId": 1,
        "productName": "有机小白菜",
        "mainImage": "https://example.com/product/1.jpg",
        "currentPrice": 6.50,
        "description": "新鲜有机小白菜，当日采摘",
        "detailUrl": "/pages/product/1"
      }
    ]
  },
  "timestamp": 1737446400000
}
```

**响应字段说明：**

| 字段 | 类型 | 说明 |
|------|------|------|
| answer | String | AI回答内容 |
| sessionId | String | 会话ID |
| products | Array | 推荐商品卡片列表 |
| products[].productId | Long | 商品ID |
| products[].productName | String | 商品名称 |
| products[].mainImage | String | 商品主图 |
| products[].currentPrice | BigDecimal | 当前价格 |
| products[].description | String | 商品描述 |
| products[].detailUrl | String | 商品详情页链接 |

---

### 2. AI 智能问答（流式 SSE）🔐 ⭐新增

**接口地址：** `POST /api/ai/chat/stream`

**协议说明：**
- 响应类型：`text/event-stream`（SSE）
- 服务端会不断推送事件，前端边接收边渲染
- 每个事件的 `data` 是一个**统一 Result 包装**，其中 `data` 为 `AiChatResponse`（与非流式一致）

**请求头：**

```http
Accept: text/event-stream
Content-Type: application/json
Authorization: Bearer <token>
```

**请求参数：**（与非流式一致）

```json
{
  "question": "有适合宝宝的有机蔬菜吗？",
  "sessionId": "optional-session-id"
}
```

**响应示例：**（示意，真实返回为持续推送的事件流）

```text
event: message
data: {"code":200,"message":"success","data":{"answer":"推荐","sessionId":"...","products":[...]},"timestamp":1737446400000}

event: message
data: {"code":200,"message":"success","data":{"answer":"推荐有机小白菜","sessionId":"...","products":[...]},"timestamp":1737446400001}

```

**注意事项：**
- 流式接口会多次推送 `answer` 的“增量内容/片段”（后端当前实现是每次推送一个 chunk 的 content）。前端应将其**追加**到已有文本，而不是覆盖（除非你选择按“全量”显示）。
- 建议前端在请求发起时禁用代理缓冲（如 Nginx 的 `proxy_buffering off`），否则可能看起来“不流”。

---

#### 前端对接建议（uni-app 安卓端）

> 说明：uni-app 在 App-Plus（安卓）端默认的 `uni.request` **不支持 SSE 流式增量读取**，建议使用 `plus.net.XMLHttpRequest` 进行流式处理。

1) **App-Plus 端推荐：`plus.net.XMLHttpRequest`（可读取增量响应）**

```js
// #ifdef APP-PLUS
function chatStreamAppPlus(question, token, onChunk) {
  const xhr = new plus.net.XMLHttpRequest();
  let lastSize = 0;

  xhr.onreadystatechange = function () {
    if (xhr.readyState === 3 || xhr.readyState === 4) {
      const text = xhr.responseText || '';
      const newText = text.slice(lastSize);
      lastSize = text.length;

      // SSE 以空行分隔事件
      const events = newText.split('\n\n');
      for (const evt of events) {
        const dataLine = evt.split('\n').find(l => l.startsWith('data: '));
        if (!dataLine) continue;

        const payload = JSON.parse(dataLine.slice(6));
        if (payload.code !== 200) {
          console.error(payload.message || 'stream error');
          return;
        }
        const chunk = payload.data?.answer || '';
        onChunk && onChunk(chunk); // 追加渲染
      }
    }
  };

  xhr.open('POST', '/api/ai/chat/stream');
  xhr.setRequestHeader('Content-Type', 'application/json');
  xhr.setRequestHeader('Accept', 'text/event-stream');
  xhr.setRequestHeader('Authorization', `Bearer ${token}`);
  xhr.send(JSON.stringify({ question }));

  return xhr; // 用于取消
}
// #endif
```

2) **取消/停止生成**
- 返回的 `xhr` 可直接 `xhr.abort()` 终止连接：

```js
const xhr = chatStreamAppPlus('你好', token, chunk => { /* 追加显示 */ });
// 用户点击停止
xhr.abort();
```

3) **渲染策略**
- 后端推送的是“chunk 片段”，前端建议将 `chunk` 追加到已有 `answer` 文本，形成打字机效果。

4) **错误处理**
- 服务端异常时会推送 `Result.error(message)`；前端需要识别 `code != 200` 并提示。

---



## 管理员模块

### 1. 用户列表 🔐👑

**接口地址：** `GET /api/admin/users`

**功能说明：** 管理员查看所有用户列表，支持分页和搜索

**请求示例：**

```bash
# 获取用户列表
curl -X GET "http://154.201.70.215/api/admin/users?pageNum=1&pageSize=10" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."

# 搜索用户
curl -X GET "http://154.201.70.215/api/admin/users?keyword=test" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**请求参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| pageNum | Integer | ❌ | 页码，默认1 |
| pageSize | Integer | ❌ | 每页数量，默认10 |
| keyword | String | ❌ | 搜索关键词（用户名/昵称/手机号） |

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "id": 2,
        "username": "test_user1",
        "nickname": "测试用户1",
        "phone": "13900000001",
        "email": null,
        "avatar": null,
        "gender": 1,
        "status": 1,
        "createTime": "2026-02-25 10:00:00",
        "updateTime": "2026-02-25 10:00:00"
      }
    ],
    "total": 3,
    "size": 10,
    "current": 1,
    "pages": 1
  },
  "timestamp": 1737446400000
}
```

---
### 1.1 手动同步商品向量 🔐👑

**接口地址：** `POST /api/admin/products/sync-vectors`

**功能说明：** 管理员手动触发商品向量全量同步（仅同步上架且未删除的商品）。

**请求示例：**

```bash
curl -X POST http://154.201.70.215/api/admin/products/sync-vectors \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**请求参数：** 无

**响应示例：**

```json
{
  "code": 200,
  "message": "商品向量同步完成",
  "data": 120,
  "timestamp": 1737446400000
}
```

---



### 2. 用户详情 🔐👑

**接口地址：** `GET /api/admin/users/{userId}`

**功能说明：** 查看指定用户的详细信息

**请求示例：**

```bash
curl -X GET http://154.201.70.215/api/admin/users/2 \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 2,
    "username": "test_user1",
    "nickname": "测试用户1",
    "phone": "13900000001",
    "email": null,
    "avatar": null,
    "gender": 1,
    "status": 1,
    "createTime": "2026-02-25 10:00:00",
    "updateTime": "2026-02-25 10:00:00"
  },
  "timestamp": 1737446400000
}
```

---

### 3. 修改用户状态 🔐👑

**接口地址：** `PUT /api/admin/users/{userId}/status`

**功能说明：** 启用或禁用用户账号

**请求示例：**

```bash
# 禁用用户
curl -X PUT "http://154.201.70.215/api/admin/users/2/status?status=0" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."

# 启用用户
curl -X PUT "http://154.201.70.215/api/admin/users/2/status?status=1" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**请求参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| status | Integer | ✅ | 状态：0-禁用，1-启用 |

**响应示例：**

```json
{
  "code": 200,
  "message": "状态修改成功",
  "data": null,
  "timestamp": 1737446400000
}
```

---

### 4. 平台概览统计 🔐👑

**接口地址：** `GET /api/admin/statistics/overview`

**功能说明：** 获取平台整体运营数据概览

**请求示例：**

```bash
curl -X GET http://154.201.70.215/api/admin/statistics/overview \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "totalUsers": 150,
    "activeUsers": 145,
    "totalProducts": 320,
    "pendingProducts": 5,
    "onlineProducts": 280,
    "totalOrders": 1250,
    "completedOrders": 980,
    "pendingOrders": 45
  },
  "timestamp": 1737446400000
}
```

**响应字段说明：**

| 字段 | 类型 | 说明 |
|------|------|------|
| totalUsers | Long | 用户总数 |
| activeUsers | Long | 正常用户数（状态=1） |
| totalProducts | Long | 商品总数 |
| pendingProducts | Long | 待审核商品数 |
| onlineProducts | Long | 上架商品数 |
| totalOrders | Long | 订单总数 |
| completedOrders | Long | 已完成订单数 |
| pendingOrders | Long | 待处理订单数（待支付+待发货） |

---

### 5. 商品状态分布 🔐👑

**接口地址：** `GET /api/admin/statistics/product-distribution`

**功能说明：** 统计各状态商品的数量分布

**请求示例：**

```bash
curl -X GET http://154.201.70.215/api/admin/statistics/product-distribution \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "pending": 5,
    "online": 280,
    "offline": 30,
    "rejected": 5
  },
  "timestamp": 1737446400000
}
```

**响应字段说明：**

| 字段 | 说明 |
|------|------|
| pending | 待审核商品数 |
| online | 已上架商品数 |
| offline | 已下架商品数 |
| rejected | 审核不通过商品数 |

---

### 6. 订单状态分布 🔐👑

**接口地址：** `GET /api/admin/statistics/order-distribution`

**功能说明：** 统计各状态订单的数量分布

**请求示例：**

```bash
curl -X GET http://154.201.70.215/api/admin/statistics/order-distribution \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "unpaid": 25,
    "unshipped": 20,
    "shipped": 35,
    "completed": 980,
    "cancelled": 190
  },
  "timestamp": 1737446400000
}
```

**响应字段说明：**

| 字段 | 说明 |
|------|------|
| unpaid | 待支付订单数 |
| unshipped | 待发货订单数 |
| shipped | 待收货订单数 |
| completed | 已完成订单数 |
| cancelled | 已取消订单数 |

---

### 7. 评价列表（管理员） 🔐👑 ⭐新增

**接口地址：** `GET /api/admin/reviews`

**功能说明：** 管理员分页查询评价列表，支持按关键词、状态、评分、商品ID、用户ID筛选。

**请求示例：**

```bash
curl -X GET "http://154.201.70.215/api/admin/reviews?keyword=新鲜&status=1&rating=5&pageNum=1&pageSize=10" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**请求参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| keyword | String | ❌ | 关键词（评价内容/用户名/昵称） |
| status | Integer | ❌ | 状态：0-隐藏，1-显示 |
| rating | Integer | ❌ | 评分：1~5 |
| productId | Long | ❌ | 商品ID |
| userId | Long | ❌ | 用户ID |
| pageNum | Integer | ❌ | 页码，默认1 |
| pageSize | Integer | ❌ | 每页数量，默认10 |

---

### 8. 评价详情（管理员） 🔐👑 ⭐新增

**接口地址：** `GET /api/admin/reviews/{reviewId}`

**功能说明：** 管理员查看指定评价详情。

**请求示例：**

```bash
curl -X GET http://154.201.70.215/api/admin/reviews/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

---

### 9. 修改评价状态（管理员） 🔐👑 ⭐新增

**接口地址：** `PUT /api/admin/reviews/{reviewId}/status`

**功能说明：** 管理员隐藏或显示评价。

**请求示例：**

```bash
# 隐藏评价
curl -X PUT "http://154.201.70.215/api/admin/reviews/1/status?status=0" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."

# 显示评价
curl -X PUT "http://154.201.70.215/api/admin/reviews/1/status?status=1" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**请求参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| status | Integer | ✅ | 0-隐藏，1-显示 |

---

### 10. 删除评价（管理员） 🔐👑 ⭐新增

**接口地址：** `DELETE /api/admin/reviews/{reviewId}`

**功能说明：** 管理员删除违规评价。

**请求示例：**

```bash
curl -X DELETE http://154.201.70.215/api/admin/reviews/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

---

### 11. 评价统计概览（管理员） 🔐👑 ⭐新增

**接口地址：** `GET /api/admin/reviews/statistics`

**功能说明：** 管理员查看评价统计（总数、显示数、隐藏数）。

**请求示例：**

```bash
curl -X GET http://154.201.70.215/api/admin/reviews/statistics \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "total": 120,
    "visible": 110,
    "hidden": 10
  },
  "timestamp": 1737446400000
}
```

---

### 12. 商品审核 🔐👑

**接口地址：** `PUT /api/products/{productId}/audit`

**功能说明：** 管理员审核商品（已实现）

**请求示例：**

```bash
# 审核通过
curl -X PUT http://154.201.70.215/api/products/1/audit \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -H "Content-Type: application/json" \
  -d '{
    "status": 1,
    "auditRemark": "商品符合要求，审核通过"
  }'

# 审核不通过
curl -X PUT http://154.201.70.215/api/products/2/audit \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -H "Content-Type: application/json" \
  -d '{
    "status": 3,
    "auditRemark": "商品描述不清晰，请修改后重新提交"
  }'
```

**请求参数：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| status | Integer | ✅ | 审核结果：1-通过（上架），3-不通过 |
| auditRemark | String | ❌ | 审核备注 |

**响应示例：**

```json
{
  "code": 200,
  "message": "审核成功",
  "data": null,
  "timestamp": 1737446400000
}
```

---

## 附录

### 数据字典

#### 订单状态

| 值 | 说明 |
|----|------|
| 0 | 待支付 |
| 1 | 待发货 |
| 2 | 待收货 |
| 3 | 已完成 |
| 4 | 已取消 |

#### 商品状态

| 值 | 说明 |
|----|------|
| 0 | 待审核 |
| 1 | 上架 |
| 2 | 下架 |
| 3 | 审核不通过 |

#### 性别

| 值 | 说明 |
|----|------|
| 0 | 未知 |
| 1 | 男 |
| 2 | 女 |

---

## 常见问题

### 1. Token过期怎么办？

Token默认有效期为24小时，过期后需要重新登录获取新的Token。

### 2. 如何测试接口？

可以使用Postman、curl或其他HTTP客户端工具进行测试。建议先注册账号并登录获取Token。

### 3. 图片上传有大小限制吗？

是的，单个图片最大5MB。

### 4. 分页参数的默认值是多少？

pageNum默认为1，pageSize默认为10。

---

## 📝 更新日志

### 2026-03-04
- ⭐ **新增管理员评价管理接口（5个）**
  - `GET /api/admin/reviews` - 评价列表（分页+筛选）
  - `GET /api/admin/reviews/{reviewId}` - 评价详情
  - `PUT /api/admin/reviews/{reviewId}/status` - 隐藏/显示评价
  - `DELETE /api/admin/reviews/{reviewId}` - 删除评价
  - `GET /api/admin/reviews/statistics` - 评价统计（total/visible/hidden）
- 📝 同步更新接口总览与管理员模块章节

### 2026-03-02
- 🛠️ **评价发布接口参数规则更新**：`POST /api/reviews`
  - `images` 字段支持两种输入：
    - JSON数组字符串（推荐）：`["url1","url2"]`
    - 逗号分隔URL字符串：`url1,url2`
  - `images` 为空（`null` / 空串）按无图处理
  - 新增 `images` 格式校验说明：若传 JSON，必须是合法 JSON 数组，否则返回 400
- 🛠️ **商家评价管理接口错误说明补充**：`GET /api/seller/reviews`
  - 明确 `productId` 为必填参数
  - 缺少 `productId` 时返回：`400`，消息：`缺少必要参数：productId`


### 2026-02-27
- ⭐ **评价功能完善**：商品评价模块全面升级（新增4个接口，优化3个接口）
  - `GET /api/reviews/mine` - 我的评价列表（⭐新增）
  - `GET /api/reviews/order/{orderId}` - 查询订单评价状态（⭐新增）
  - `PUT /api/seller/reviews/{reviewId}/reply` - 商家回复评价（⭐新增，卖家模块）
  - `GET /api/seller/reviews` - 商家查看商品评价（⭐新增，卖家模块）
  - `DELETE /api/reviews/{reviewId}` - 删除评价（✅已加管理员权限校验）
  - `PUT /api/reviews/{reviewId}/status` - 隐藏/显示评价（✅已加管理员权限校验）
  - `POST /api/reviews` - 发布评价（✅已加完整业务校验：订单已完成+商品在订单中+防重复评价）
- ⭐ **订单VO新增评价字段**
  - `OrderVO` 新增 `reviewed`（是否全部评价）、`reviewCount`（已评价商品数）
  - `OrderItemVO` 新增 `reviewed`（该商品是否已评价）
- 📝 更新订单列表/详情响应示例

### 2026-02-25
- ✅ **新增模块**：管理员后台（7个接口）
  - `GET /api/admin/users` - 用户列表
  - `GET /api/admin/users/{userId}` - 用户详情
  - `PUT /api/admin/users/{userId}/status` - 修改用户状态
  - `GET /api/admin/statistics/overview` - 平台概览统计
  - `GET /api/admin/statistics/product-distribution` - 商品状态分布
  - `GET /api/admin/statistics/order-distribution` - 订单状态分布
  - `PUT /api/products/{productId}/audit` - 商品审核（已有）
- 📝 完善管理员权限说明
- 🎓 适配毕业设计需求，简化实现

### 2026-01-30
- ✅ **新增模块**：收货地址管理（7个接口）
  - `POST /api/users/addresses` - 添加收货地址
  - `PUT /api/users/addresses/{addressId}` - 更新收货地址
  - `DELETE /api/users/addresses/{addressId}` - 删除收货地址
  - `GET /api/users/addresses` - 获取地址列表
  - `GET /api/users/addresses/{addressId}` - 获取地址详情
  - `PUT /api/users/addresses/{addressId}/default` - 设置默认地址
  - `GET /api/users/addresses/default` - 获取默认地址
- ✅ **新增模块**：商家订单管理（3个接口）
  - `GET /api/seller/orders` - 获取商家订单列表
  - `GET /api/seller/orders/{orderId}` - 获取商家订单详情
  - `GET /api/seller/orders/count` - 获取商家订单统计
- 🎉 **重要里程碑**：核心功能全部完成，项目达到MVP标准

### 2026-01-25
- ✅ **新增接口**：`GET /api/users/collections` - 获取用户收藏列表
- ✅ **新增接口**：`GET /api/users/collections/count` - 获取收藏商品总数
- ✅ **新增接口**：`DELETE /api/users/collections/clear` - 清空收藏夹
- 📝 完善收藏功能相关接口文档

### 2026-01-21
- 📄 初始版本发布
- ✅ 完成用户、商品、订单、购物车等核心模块接口文档

---

**文档更新日期：** 2026-02-27
**联系方式：** 如有问题请联系开发团队
**管理员账号：** admin / 123456
