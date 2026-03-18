package com.vie.service.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vie.service.config.AmapConfig;
import com.vie.service.dto.ReverseGeocodeDTO;
import com.vie.service.exception.BusinessException;
import com.vie.service.service.LocationService;
import com.vie.service.util.RedisUtil;
import com.vie.service.vo.ReverseGeocodeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * 定位服务实现类
 */
@Slf4j
@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private AmapConfig amapConfig;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 缓存键前缀
     */
    private static final String CACHE_PREFIX = "reverse_geocode:";

    /**
     * 缓存过期时间（秒）- 5分钟
     */
    private static final long CACHE_EXPIRE_TIME = 300;

    @Override
    public ReverseGeocodeVO reverseGeocode(ReverseGeocodeDTO dto) {
        // 验证API Key
        if (amapConfig.getApiKey() == null || amapConfig.getApiKey().trim().isEmpty()) {
            throw new BusinessException(500, "高德地图API Key未配置，请联系管理员");
        }

        // 生成缓存键（使用经纬度，保留4位小数）
        String cacheKey = CACHE_PREFIX + String.format("%.4f,%.4f", dto.getLatitude(), dto.getLongitude());

        // 尝试从缓存获取
        Object cached = redisUtil.get(cacheKey);
        if (cached != null) {
            log.info("从缓存获取逆地理编码结果：{}", cacheKey);
            try {
                return objectMapper.convertValue(cached, ReverseGeocodeVO.class);
            } catch (Exception e) {
                log.warn("缓存数据反序列化失败，重新请求：{}", e.getMessage());
            }
        }

        // 调用高德地图API
        ReverseGeocodeVO result = callAmapApi(dto);

        // 将结果存入缓存
        try {
            redisUtil.set(cacheKey, result, CACHE_EXPIRE_TIME);
            log.info("逆地理编码结果已缓存：{}", cacheKey);
        } catch (Exception e) {
            log.warn("缓存逆地理编码结果失败：{}", e.getMessage());
        }

        return result;
    }

    /**
     * 调用高德地图逆地理编码API
     */
    private ReverseGeocodeVO callAmapApi(ReverseGeocodeDTO dto) {
        try {
            // 构建请求URL
            String url = String.format("%s?key=%s&location=%s,%s&extensions=all",
                    amapConfig.getReverseGeocodeUrl(),
                    amapConfig.getApiKey(),
                    dto.getLongitude(), // 注意：高德API要求先经度后纬度
                    dto.getLatitude());

            log.info("调用高德地图逆地理编码API：{}", url.replace(amapConfig.getApiKey(), "***"));

            // 发送HTTP请求
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return parseAmapResponse(response.getBody(), dto);
            } else {
                throw new BusinessException(500, "高德地图API调用失败");
            }
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("调用高德地图API异常：", e);
            throw new BusinessException(500, "逆地理编码服务异常：" + e.getMessage());
        }
    }

    /**
     * 解析高德地图API响应
     */
    private ReverseGeocodeVO parseAmapResponse(String responseBody, ReverseGeocodeDTO dto) {
        try {
            JsonNode rootNode = objectMapper.readTree(responseBody);

            // 检查状态码
            String status = rootNode.path("status").asText();
            String info = rootNode.path("info").asText();

            if (!"1".equals(status)) {
                log.error("高德地图API返回错误：status={}, info={}", status, info);
                throw new BusinessException(400, "逆地理编码失败：" + info);
            }

            // 解析地址信息
            JsonNode regeocodeNode = rootNode.path("regeocode");
            if (regeocodeNode.isMissingNode()) {
                throw new BusinessException(400, "未找到地址信息");
            }

            JsonNode addressComponent = regeocodeNode.path("addressComponent");
            JsonNode pois = regeocodeNode.path("pois");

            ReverseGeocodeVO vo = new ReverseGeocodeVO();
            vo.setLatitude(dto.getLatitude());
            vo.setLongitude(dto.getLongitude());

            // 解析地址组件
            if (!addressComponent.isMissingNode()) {
                vo.setProvince(addressComponent.path("province").asText(""));
                vo.setCity(addressComponent.path("city").asText(""));
                vo.setDistrict(addressComponent.path("district").asText(""));
                vo.setStreet(addressComponent.path("street").asText(""));
                vo.setStreetNumber(addressComponent.path("streetNumber").asText(""));
            }

            // 解析完整地址
            vo.setAddress(regeocodeNode.path("formatted_address").asText(""));

            // 解析POI信息（优先取第一个POI的名称）
            if (pois.isArray() && pois.size() > 0) {
                JsonNode firstPoi = pois.get(0);
                vo.setPoiName(firstPoi.path("name").asText(""));
            }

            // 生成格式化地址
            vo.setFormattedAddress(generateFormattedAddress(vo));

            return vo;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("解析高德地图API响应异常：", e);
            throw new BusinessException(500, "解析地址信息失败：" + e.getMessage());
        }
    }

    /**
     * 生成格式化地址
     * 优先显示小区名，其次街道+小区，最后区+街道
     */
    private String generateFormattedAddress(ReverseGeocodeVO vo) {
        // 优先显示POI名称（小区名）
        if (vo.getPoiName() != null && !vo.getPoiName().trim().isEmpty()) {
            return vo.getPoiName();
        }

        // 其次显示街道+小区（如果有街道信息）
        if (vo.getStreet() != null && !vo.getStreet().trim().isEmpty()) {
            String streetInfo = vo.getStreet();
            if (vo.getStreetNumber() != null && !vo.getStreetNumber().trim().isEmpty()) {
                streetInfo += vo.getStreetNumber();
            }
            return streetInfo;
        }

        // 最后显示区+街道
        if (vo.getDistrict() != null && !vo.getDistrict().trim().isEmpty()) {
            String districtInfo = vo.getDistrict();
            if (vo.getStreet() != null && !vo.getStreet().trim().isEmpty()) {
                districtInfo += vo.getStreet();
            }
            return districtInfo;
        }

        // 如果都没有，返回完整地址
        return vo.getAddress() != null && !vo.getAddress().trim().isEmpty() 
                ? vo.getAddress() 
                : "未知地址";
    }
}

