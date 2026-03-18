package com.vie.starter.controller;

import com.vie.service.common.Result;
import com.vie.service.dto.ReverseGeocodeDTO;
import com.vie.service.service.LocationService;
import com.vie.service.vo.ReverseGeocodeVO;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 定位控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    /**
     * 逆地理编码：将经纬度转换为详细地址
     * 🔓 公开接口（无需登录）
     */
    @PostMapping("/reverse-geocode")
    public Result<ReverseGeocodeVO> reverseGeocode(@Valid @RequestBody ReverseGeocodeDTO dto) {
        log.info("逆地理编码请求：latitude={}, longitude={}", dto.getLatitude(), dto.getLongitude());
        ReverseGeocodeVO result = locationService.reverseGeocode(dto);
        return Result.success(result);
    }
}

