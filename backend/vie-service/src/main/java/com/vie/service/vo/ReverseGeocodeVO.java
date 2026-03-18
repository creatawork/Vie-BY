package com.vie.service.vo;

import lombok.Data;

/**
 * 逆地理编码响应VO
 */
@Data
public class ReverseGeocodeVO {

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 完整地址
     */
    private String address;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区/县
     */
    private String district;

    /**
     * 街道
     */
    private String street;

    /**
     * 街道号
     */
    private String streetNumber;

    /**
     * POI名称（如小区名）
     */
    private String poiName;

    /**
     * 格式化后的显示地址（优先显示小区名，其次街道+小区，最后区+街道）
     */
    private String formattedAddress;
}

