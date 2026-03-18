# 后端缺失接口需求文档

> **创建时间**: 2026-01-25  
> **优先级**: 高  
> **状态**: 待实现

---

## 📋 概述

本文档列出前端开发过程中发现的后端缺失接口，这些接口对于完整的用户体验至关重要。

---

## 🔴 高优先级接口

### 1. 获取用户收藏商品列表

**接口地址**: `GET /api/products/collections`

**权限要求**: 🔐 需要登录

**请求参数**:

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| pageNum | Integer | ❌ | 页码，默认1 |
| pageSize | Integer | ❌ | 每页数量，默认10 |

**请求示例**:

```bash
curl -X GET "http://23.247.129.117/api/products/collections?pageNum=1&pageSize=10" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例**:

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "id": 1,
        "productId": 101,
        "productName": "有机小白菜",
        "productImage": "https://example.com/product/101.jpg",
        "currentPrice": 6.50,
        "originalPrice": 8.00,
        "stock": 500,
        "salesVolume": 100,
        "status": 1,
        "collectTime": "2025-01-20 10:00:00"
      },
      {
        "id": 2,
        "productId": 102,
        "productName": "新鲜苹果",
        "productImage": "https://example.com/product/102.jpg",
        "currentPrice": 8.00,
        "originalPrice": 10.00,
        "stock": 300,
        "salesVolume": 80,
        "status": 1,
        "collectTime": "2025-01-19 15:30:00"
      }
    ],
    "total": 12,
    "size": 10,
    "current": 1,
    "pages": 2
  },
  "timestamp": 1737446400000
}
```

**数据库设计建议**:

```sql
-- 商品收藏表
CREATE TABLE product_collection (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    product_id BIGINT NOT NULL COMMENT '商品ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
    UNIQUE KEY uk_user_product (user_id, product_id),
    INDEX idx_user_id (user_id),
    INDEX idx_create_time (create_time)
) COMMENT='商品收藏表';
```

**业务逻辑**:
1. 查询当前登录用户的收藏记录
2. 关联商品表获取商品详细信息
3. 过滤已下架或删除的商品
4. 按收藏时间倒序排列
5. 支持分页查询

---

### 2. 获取用户收藏商品总数

**接口地址**: `GET /api/products/collections/count`

**权限要求**: 🔐 需要登录

**请求参数**: 无

**请求示例**:

```bash
curl -X GET "http://23.247.129.117/api/products/collections/count" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例**:

```json
{
  "code": 200,
  "message": "success",
  "data": 12,
  "timestamp": 1737446400000
}
```

**业务逻辑**:
1. 查询当前登录用户的收藏记录总数
2. 只统计有效商品（未下架、未删除）
3. 返回整数类型

---

## 🟡 中优先级接口

### 3. 批量取消收藏

**接口地址**: `DELETE /api/products/collections/batch`

**权限要求**: 🔐 需要登录

**请求参数**:

```json
{
  "collectionIds": [1, 2, 3, 4, 5]
}
```

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| collectionIds | Array<Long> | ✅ | 收藏记录ID列表 |

**请求示例**:

```bash
curl -X DELETE "http://23.247.129.117/api/products/collections/batch" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -H "Content-Type: application/json" \
  -d '{
    "collectionIds": [1, 2, 3]
  }'
```

**响应示例**:

```json
{
  "code": 200,
  "message": "批量取消收藏成功",
  "data": null,
  "timestamp": 1737446400000
}
```

**业务逻辑**:
1. 验证收藏记录是否属于当前用户
2. 批量删除收藏记录
3. 返回操作结果

---

### 4. 清空收藏夹

**接口地址**: `DELETE /api/products/collections/clear`

**权限要求**: 🔐 需要登录

**请求参数**: 无

**请求示例**:

```bash
curl -X DELETE "http://23.247.129.117/api/products/collections/clear" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**响应示例**:

```json
{
  "code": 200,
  "message": "清空收藏夹成功",
  "data": null,
  "timestamp": 1737446400000
}
```

**业务逻辑**:
1. 删除当前用户的所有收藏记录
2. 返回操作结果

---

## 📊 前端使用场景

### 收藏列表页面

**页面路径**: `pages/user/favorites.uvue` (待创建)

**功能需求**:
1. 展示用户收藏的商品列表
2. 支持分页加载
3. 支持单个取消收藏
4. 支持批量取消收藏
5. 支持清空收藏夹
6. 点击商品跳转到商品详情页

**API调用**:
```typescript
import { getCollectionList, getCollectionCount } from '@/api/product'

// 获取收藏列表
getCollectionList(1, 10).then(result => {
  if (result.code === 200) {
    const data = result.data as UTSJSONObject
    // 处理数据
  }
})

// 获取收藏总数
getCollectionCount().then(result => {
  if (result.code === 200) {
    const count = result.data as number
    // 更新收藏夹角标
  }
})
```

### 个人中心页面

**页面路径**: `pages/user/profile.uvue` (已存在)

**功能需求**:
1. 显示收藏商品总数
2. 点击跳转到收藏列表页

**API调用**:
```typescript
import { getCollectionCount } from '@/api/product'

// 在 loadOrderStats 方法中添加
loadCollectionCount() {
  getCollectionCount().then((result) => {
    if (result.code === 200) {
      this.stats.favorites = result.data as number
    }
  }).catch((error) => {
    console.error('获取收藏数量失败:', error)
  })
}
```

---

## 🔧 实现优先级

| 接口 | 优先级 | 原因 | 预计工作量 |
|------|--------|------|-----------|
| 获取收藏列表 | 🔴 高 | 收藏夹页面核心功能 | 2-3小时 |
| 获取收藏总数 | 🔴 高 | 个人中心统计数据 | 1小时 |
| 批量取消收藏 | 🟡 中 | 提升用户体验 | 1-2小时 |
| 清空收藏夹 | 🟡 中 | 便捷操作 | 0.5小时 |

**总计**: 约 4.5-6.5 小时

---

## 📝 技术要点

### 1. 数据库索引优化

```sql
-- 用户ID索引（查询用户收藏）
CREATE INDEX idx_user_id ON product_collection(user_id);

-- 商品ID索引（查询商品被收藏情况）
CREATE INDEX idx_product_id ON product_collection(product_id);

-- 收藏时间索引（按时间排序）
CREATE INDEX idx_create_time ON product_collection(create_time);

-- 联合唯一索引（防止重复收藏）
CREATE UNIQUE INDEX uk_user_product ON product_collection(user_id, product_id);
```

### 2. 缓存策略

```java
// 收藏总数缓存
String cacheKey = "user:collection:count:" + userId;
Integer count = redisUtil.get(cacheKey);
if (count == null) {
    count = collectionMapper.countByUserId(userId);
    redisUtil.set(cacheKey, count, 300); // 缓存5分钟
}
```

### 3. 性能优化

1. **分页查询**: 使用 MyBatis-Plus 的 Page 对象
2. **关联查询**: 使用 LEFT JOIN 一次性获取商品信息
3. **缓存**: 收藏总数使用 Redis 缓存
4. **索引**: 在 user_id 和 product_id 上建立索引

---

## 🎯 验收标准

### 功能验收

- [ ] 获取收藏列表接口正常返回数据
- [ ] 获取收藏总数接口正常返回数据
- [ ] 批量取消收藏功能正常
- [ ] 清空收藏夹功能正常
- [ ] 分页功能正常
- [ ] 数据过滤正确（只显示有效商品）

### 性能验收

- [ ] 接口响应时间 < 200ms
- [ ] 支持并发请求
- [ ] 缓存命中率 > 80%

### 安全验收

- [ ] 用户只能操作自己的收藏
- [ ] 防止SQL注入
- [ ] 防止重复收藏

---

## 📞 联系方式

如有疑问，请联系前端开发团队。

**文档版本**: v1.0  
**最后更新**: 2026-01-25
