package com.vie.service.service;

import com.vie.service.dto.ReverseGeocodeDTO;
import com.vie.service.vo.ReverseGeocodeVO;

/**
 * 定位服务接口
 */
public interface LocationService {

    /**
     * 逆地理编码：将经纬度转换为详细地址
     *
     * @param dto 逆地理编码请求DTO
     * @return 地址信息VO
     */
    ReverseGeocodeVO reverseGeocode(ReverseGeocodeDTO dto);
}

