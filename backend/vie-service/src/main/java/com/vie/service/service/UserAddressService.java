package com.vie.service.service;

import com.vie.service.dto.AddressCreateDTO;
import com.vie.service.dto.AddressUpdateDTO;
import com.vie.service.vo.AddressVO;

import java.util.List;

/**
 * 用户地址服务接口
 */
public interface UserAddressService {

    /**
     * 添加收货地址
     *
     * @param dto    地址信息
     * @param userId 用户ID
     * @return 地址ID
     */
    Long addAddress(AddressCreateDTO dto, Long userId);

    /**
     * 更新收货地址
     *
     * @param addressId 地址ID
     * @param dto       地址信息
     * @param userId    用户ID
     */
    void updateAddress(Long addressId, AddressUpdateDTO dto, Long userId);

    /**
     * 删除收货地址
     *
     * @param addressId 地址ID
     * @param userId    用户ID
     */
    void deleteAddress(Long addressId, Long userId);

    /**
     * 获取地址列表
     *
     * @param userId 用户ID
     * @return 地址列表
     */
    List<AddressVO> getAddressList(Long userId);

    /**
     * 获取地址详情
     *
     * @param addressId 地址ID
     * @param userId    用户ID
     * @return 地址详情
     */
    AddressVO getAddressDetail(Long addressId, Long userId);

    /**
     * 设置默认地址
     *
     * @param addressId 地址ID
     * @param userId    用户ID
     */
    void setDefaultAddress(Long addressId, Long userId);

    /**
     * 获取默认地址
     *
     * @param userId 用户ID
     * @return 默认地址
     */
    AddressVO getDefaultAddress(Long userId);
}
