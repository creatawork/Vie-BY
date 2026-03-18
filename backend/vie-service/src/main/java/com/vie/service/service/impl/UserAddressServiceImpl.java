package com.vie.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.vie.db.entity.UserAddress;
import com.vie.db.mapper.UserAddressMapper;
import com.vie.service.dto.AddressCreateDTO;
import com.vie.service.dto.AddressUpdateDTO;
import com.vie.service.exception.BusinessException;
import com.vie.service.service.UserAddressService;
import com.vie.service.vo.AddressVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户地址服务实现类
 */
@Slf4j
@Service
public class UserAddressServiceImpl implements UserAddressService {

    @Autowired
    private UserAddressMapper addressMapper;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addAddress(AddressCreateDTO dto, Long userId) {
        log.info("添加收货地址，userId: {}, dto: {}", userId, dto);

        // 如果设置为默认地址，先取消其他默认地址
        if (dto.getIsDefault() != null && dto.getIsDefault() == 1) {
            cancelOtherDefaultAddress(userId);
        }

        // 如果是第一个地址，自动设为默认
        long count = addressMapper.selectCount(
                new LambdaQueryWrapper<UserAddress>()
                        .eq(UserAddress::getUserId, userId)
        );
        if (count == 0) {
            dto.setIsDefault(1);
        }

        // 创建地址
        UserAddress address = new UserAddress();
        BeanUtils.copyProperties(dto, address);
        address.setUserId(userId);
        if (address.getIsDefault() == null) {
            address.setIsDefault(0);
        }

        addressMapper.insert(address);
        log.info("添加收货地址成功，addressId: {}", address.getId());

        return address.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAddress(Long addressId, AddressUpdateDTO dto, Long userId) {
        log.info("更新收货地址，addressId: {}, userId: {}, dto: {}", addressId, userId, dto);

        // 验证地址是否存在且属于当前用户
        UserAddress address = getAndValidateAddress(addressId, userId);

        // 如果设置为默认地址，先取消其他默认地址
        if (dto.getIsDefault() != null && dto.getIsDefault() == 1 && address.getIsDefault() != 1) {
            cancelOtherDefaultAddress(userId);
        }

        // 更新地址
        BeanUtils.copyProperties(dto, address);
        address.setId(addressId);
        if (address.getIsDefault() == null) {
            address.setIsDefault(0);
        }

        addressMapper.updateById(address);
        log.info("更新收货地址成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAddress(Long addressId, Long userId) {
        log.info("删除收货地址，addressId: {}, userId: {}", addressId, userId);

        // 验证地址是否存在且属于当前用户
        UserAddress address = getAndValidateAddress(addressId, userId);

        // 删除地址
        addressMapper.deleteById(addressId);

        // 如果删除的是默认地址，将第一个地址设为默认
        if (address.getIsDefault() == 1) {
            UserAddress firstAddress = addressMapper.selectOne(
                    new LambdaQueryWrapper<UserAddress>()
                            .eq(UserAddress::getUserId, userId)
                            .orderByAsc(UserAddress::getCreateTime)
                            .last("LIMIT 1")
            );
            if (firstAddress != null) {
                firstAddress.setIsDefault(1);
                addressMapper.updateById(firstAddress);
            }
        }

        log.info("删除收货地址成功");
    }

    @Override
    public List<AddressVO> getAddressList(Long userId) {
        log.info("获取地址列表，userId: {}", userId);

        List<UserAddress> addresses = addressMapper.selectList(
                new LambdaQueryWrapper<UserAddress>()
                        .eq(UserAddress::getUserId, userId)
                        .orderByDesc(UserAddress::getIsDefault)
                        .orderByDesc(UserAddress::getCreateTime)
        );

        return addresses.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public AddressVO getAddressDetail(Long addressId, Long userId) {
        log.info("获取地址详情，addressId: {}, userId: {}", addressId, userId);

        UserAddress address = getAndValidateAddress(addressId, userId);
        return convertToVO(address);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setDefaultAddress(Long addressId, Long userId) {
        log.info("设置默认地址，addressId: {}, userId: {}", addressId, userId);

        // 验证地址是否存在且属于当前用户
        getAndValidateAddress(addressId, userId);

        // 取消其他默认地址
        cancelOtherDefaultAddress(userId);

        // 设置为默认地址
        UserAddress address = new UserAddress();
        address.setId(addressId);
        address.setIsDefault(1);
        addressMapper.updateById(address);

        log.info("设置默认地址成功");
    }

    @Override
    public AddressVO getDefaultAddress(Long userId) {
        log.info("获取默认地址，userId: {}", userId);

        UserAddress address = addressMapper.selectOne(
                new LambdaQueryWrapper<UserAddress>()
                        .eq(UserAddress::getUserId, userId)
                        .eq(UserAddress::getIsDefault, 1)
        );

        if (address == null) {
            // 如果没有默认地址，返回第一个地址
            address = addressMapper.selectOne(
                    new LambdaQueryWrapper<UserAddress>()
                            .eq(UserAddress::getUserId, userId)
                            .orderByAsc(UserAddress::getCreateTime)
                            .last("LIMIT 1")
            );
        }

        return address == null ? null : convertToVO(address);
    }

    /**
     * 获取并验证地址
     */
    private UserAddress getAndValidateAddress(Long addressId, Long userId) {
        UserAddress address = addressMapper.selectById(addressId);
        if (address == null) {
            throw new BusinessException("地址不存在");
        }
        if (!address.getUserId().equals(userId)) {
            throw new BusinessException("无权操作此地址");
        }
        return address;
    }

    /**
     * 取消其他默认地址
     */
    private void cancelOtherDefaultAddress(Long userId) {
        addressMapper.update(null,
                new LambdaUpdateWrapper<UserAddress>()
                        .eq(UserAddress::getUserId, userId)
                        .eq(UserAddress::getIsDefault, 1)
                        .set(UserAddress::getIsDefault, 0)
        );
    }

    /**
     * 转换为VO
     */
    private AddressVO convertToVO(UserAddress address) {
        AddressVO vo = new AddressVO();
        BeanUtils.copyProperties(address, vo);

        // 拼接完整地址
        vo.setFullAddress(address.getProvince() + address.getCity() + 
                         address.getDistrict() + address.getDetailAddress());

        // 格式化时间
        if (address.getCreateTime() != null) {
            vo.setCreateTime(formatDateTime(address.getCreateTime()));
        }
        if (address.getUpdateTime() != null) {
            vo.setUpdateTime(formatDateTime(address.getUpdateTime()));
        }

        return vo;
    }

    /**
     * 格式化日期时间
     */
    private String formatDateTime(LocalDateTime dateTime) {
        return dateTime == null ? null : dateTime.format(DATE_TIME_FORMATTER);
    }
}
