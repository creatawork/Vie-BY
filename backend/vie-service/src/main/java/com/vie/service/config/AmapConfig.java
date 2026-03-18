package com.vie.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 高德地图配置
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "amap")
public class AmapConfig {

    /**
     * 高德地图API Key
     */
    private String apiKey;

    /**
     * 逆地理编码API地址
     */
    private String reverseGeocodeUrl = "https://restapi.amap.com/v3/geocode/regeo";
}

