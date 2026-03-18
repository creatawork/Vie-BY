# 后端接口需求文档

本文档记录了前端开发过程中发现的缺失后端接口或需要调整的接口需求。

---

## 定位模块

### 1. 逆地理编码接口

前端需要将GPS定位的经纬度转换为详细地址（省市区、街道、小区等），用于首页显示位置信息。

**需求接口：**

*   **逆地理编码**
    *   方法: `POST`
    *   路径: `/api/location/reverse-geocode`
    *   权限: 🔓 公开接口（无需登录）
    *   描述: 将经纬度坐标转换为详细地址信息

**请求参数：**

```json
{
  "latitude": 39.908823,
  "longitude": 116.397470
}
```

**参数说明：**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| latitude | Double | ✅ | 纬度 |
| longitude | Double | ✅ | 经度 |

**响应示例：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "latitude": 39.908823,
    "longitude": 116.397470,
    "address": "北京市朝阳区xxx街道xxx小区",
    "province": "北京市",
    "city": "北京市",
    "district": "朝阳区",
    "street": "xxx街道",
    "streetNumber": "xxx号",
    "poiName": "xxx小区",
    "formattedAddress": "xxx小区"
  },
  "timestamp": 1701234567890
}
```

**响应字段说明：**

| 字段 | 类型 | 说明 |
|------|------|------|
| latitude | Double | 纬度 |
| longitude | Double | 经度 |
| address | String | 完整地址 |
| province | String | 省份 |
| city | String | 城市 |
| district | String | 区/县 |
| street | String | 街道 |
| streetNumber | String | 街道号 |
| poiName | String | POI名称（如小区名） |
| formattedAddress | String | 格式化后的显示地址（优先显示小区名，其次街道+小区，最后区+街道） |

**错误响应：**

```json
{
  "code": 400,
  "message": "参数错误：经纬度不能为空",
  "data": null,
  "timestamp": 1701234567890
}
```

**实现建议：**

1. **使用高德地图API**：
   - 接口：`https://restapi.amap.com/v3/geocode/regeo`
   - 需要在高德开放平台注册并获取API Key
   - API Key配置在后端，不要暴露在前端

2. **性能优化**：
   - 添加缓存机制（相同经纬度5分钟内不重复请求）
   - 添加接口限流（防止滥用）
   - 考虑使用Redis缓存

3. **错误处理**：
   - 高德API调用失败时返回友好错误信息
   - 记录API调用日志，便于排查问题

4. **安全考虑**：
   - API Key存储在服务端配置文件或环境变量中
   - 添加请求频率限制
   - 验证经纬度参数范围（防止无效请求）

**参考文档**：
- 高德逆地理编码API：https://lbs.amap.com/api/webservice/guide/api/georegeo