package com.vie.service.service;

import com.vie.service.dto.PasswordUpdateDTO;
import com.vie.service.dto.UserLoginDTO;
import com.vie.service.dto.UserRegisterDTO;
import com.vie.service.dto.UserUpdateDTO;
import com.vie.service.vo.LoginVO;
import com.vie.service.vo.UserVO;

/**
 * 用户服务接口
 */
public interface UserService {

    /**
     * 用户注册
     *
     * @param dto 注册信息
     */
    void register(UserRegisterDTO dto);

    /**
     * 用户登录
     *
     * @param dto 登录信息
     * @return 登录结果（包含Token）
     */
    LoginVO login(UserLoginDTO dto);

    /**
     * 用户登出
     *
     * @param userId 用户ID
     */
    void logout(Long userId);

    /**
     * 获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    UserVO getUserInfo(Long userId);

    /**
     * 更新用户信息
     *
     * @param userId 用户ID
     * @param dto    更新信息
     */
    void updateUserInfo(Long userId, UserUpdateDTO dto);

    /**
     * 修改密码
     *
     * @param userId 用户ID
     * @param dto    密码信息
     */
    void updatePassword(Long userId, PasswordUpdateDTO dto);

    /**
     * 更新用户头像
     *
     * @param userId 用户ID
     * @param avatarUrl 头像URL
     */
    void updateAvatar(Long userId, String avatarUrl);

    /**
     * 开通卖家功能
     *
     * @param userId 用户ID
     * @param shopName 店铺名称（可选）
     */
    void becomeSeller(Long userId, String shopName);

    /**
     * 检查用户是否已开通卖家功能
     *
     * @param userId 用户ID
     * @return 是否已开通
     */
    boolean isSeller(Long userId);
}
