package com.vie.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.vie.db.entity.Role;
import com.vie.db.entity.SellerInfo;
import com.vie.db.entity.User;
import com.vie.db.entity.UserRole;
import com.vie.db.mapper.SellerInfoMapper;
import com.vie.db.mapper.UserMapper;
import com.vie.db.mapper.UserRoleMapper;
import com.vie.service.constant.RoleConstant;
import com.vie.service.dto.PasswordUpdateDTO;
import com.vie.service.dto.UserLoginDTO;
import com.vie.service.dto.UserRegisterDTO;
import com.vie.service.dto.UserUpdateDTO;
import com.vie.service.service.UserService;
import com.vie.service.util.JwtUtil;
import com.vie.service.util.PasswordUtil;
import com.vie.service.util.RedisUtil;
import com.vie.service.vo.LoginVO;
import com.vie.service.vo.UserVO;
import com.vie.service.common.ResultCode;
import com.vie.service.exception.BusinessException;
import com.vie.service.service.SellerAccountService;
import com.vie.service.service.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户服务实现类
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private WalletService walletService;

    @Autowired
    private SellerAccountService sellerAccountService;

    @Autowired
    private SellerInfoMapper sellerInfoMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(UserRegisterDTO dto) {
        // 1. 验证两次密码是否一致
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new BusinessException(ResultCode.PASSWORD_NOT_MATCH);
        }

        // 2. 检查用户名是否已存在
        LambdaQueryWrapper<User> usernameQuery = new LambdaQueryWrapper<>();
        usernameQuery.eq(User::getUsername, dto.getUsername());
        if (userMapper.selectCount(usernameQuery) > 0) {
            throw new BusinessException(ResultCode.USERNAME_EXISTS);
        }

        // 3. 检查手机号是否已存在
        LambdaQueryWrapper<User> phoneQuery = new LambdaQueryWrapper<>();
        phoneQuery.eq(User::getPhone, dto.getPhone());
        if (userMapper.selectCount(phoneQuery) > 0) {
            throw new BusinessException(ResultCode.PHONE_EXISTS);
        }

        // 4. 创建用户
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPhone(dto.getPhone());
        user.setEmail(dto.getEmail());
        user.setPassword(PasswordUtil.encode(dto.getPassword()));
        user.setNickname(dto.getNickname() != null ? dto.getNickname() : dto.getUsername());
        user.setStatus(1); // 正常状态
        userMapper.insert(user);

        // 5. 分配顾客角色
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(RoleConstant.CUSTOMER_ROLE_ID);
        userRoleMapper.insert(userRole);

        // 6. 为用户创建钱包
        walletService.createWallet(user.getId());

        log.info("用户注册成功：userId={}, username={}", user.getId(), user.getUsername());
    }

    /**
     * 商家注册后创建商家账户（供外部调用）
     */
    public void createSellerAccount(Long sellerId) {
        sellerAccountService.createAccount(sellerId);
        log.info("商家账户创建成功：sellerId={}", sellerId);
    }

    @Override
    public LoginVO login(UserLoginDTO dto) {
        // 1. 查询用户（支持用户名或手机号登录）
        User user = userMapper.selectUserWithRolesByUsername(dto.getUsername());
        if (user == null) {
            // 尝试用手机号查询
            LambdaQueryWrapper<User> query = new LambdaQueryWrapper<>();
            query.eq(User::getPhone, dto.getUsername());
            user = userMapper.selectOne(query);
            if (user != null) {
                // 查询用户角色
                List<Role> roles = userMapper.selectRolesByUserId(user.getId());
                user.setRoles(roles);
            }
        }

        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }

        // 2. 验证密码
        if (!PasswordUtil.matches(dto.getPassword(), user.getPassword())) {
            throw new BusinessException(ResultCode.PASSWORD_ERROR);
        }

        // 3. 检查用户状态
        if (user.getStatus() == 0) {
            throw new BusinessException(ResultCode.USER_DISABLED);
        }

        // 4. 获取角色编码列表
        List<String> roleCodes = null;
        if (user.getRoles() != null) {
            roleCodes = user.getRoles().stream()
                    .map(Role::getRoleCode)
                    .collect(Collectors.toList());
        }

        // 5. 生成Token（包含角色信息）
        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), roleCodes);

        // 6. 保存Token到Redis
        redisUtil.saveToken(user.getId(), token, jwtUtil.getExpiration());

        // 7. 检查是否已开通卖家功能
        boolean isSeller = isSeller(user.getId());

        // 8. 构建返回结果
        LoginVO loginVO = new LoginVO();
        loginVO.setUserId(user.getId());
        loginVO.setUsername(user.getUsername());
        loginVO.setNickname(user.getNickname());
        loginVO.setAvatar(user.getAvatar());
        loginVO.setToken(token);
        loginVO.setExpireTime(jwtUtil.getExpiration());
        loginVO.setRoleCodes(roleCodes);
        loginVO.setIsSeller(isSeller);

        log.info("用户登录成功：userId={}, username={}, isSeller={}", user.getId(), user.getUsername(), isSeller);
        return loginVO;
    }

    @Override
    public void logout(Long userId) {
        // 删除Redis中的Token
        redisUtil.deleteToken(userId);
        log.info("用户登出成功：userId={}", userId);
    }

    @Override
    public UserVO getUserInfo(Long userId) {
        // 查询用户信息
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }

        // 查询用户角色
        List<Role> roles = userMapper.selectRolesByUserId(userId);

        // 构建返回结果
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);

        // 设置角色编码列表
        List<String> roleCodes = roles.stream()
                .map(Role::getRoleCode)
                .collect(Collectors.toList());
        userVO.setRoleCodes(roleCodes);

        return userVO;
    }

    @Override
    public void updateUserInfo(Long userId, UserUpdateDTO dto) {
        // 查询用户
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }

        // 更新用户信息
        if (dto.getNickname() != null) {
            user.setNickname(dto.getNickname());
        }
        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }
        if (dto.getAvatar() != null) {
            user.setAvatar(dto.getAvatar());
        }
        if (dto.getGender() != null) {
            user.setGender(dto.getGender());
        }

        userMapper.updateById(user);
        log.info("用户信息更新成功：userId={}", userId);
    }

    @Override
    public void updatePassword(Long userId, PasswordUpdateDTO dto) {
        // 1. 验证两次密码是否一致
        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            throw new BusinessException(ResultCode.PASSWORD_NOT_MATCH);
        }

        // 2. 查询用户
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }

        // 3. 验证旧密码
        if (!PasswordUtil.matches(dto.getOldPassword(), user.getPassword())) {
            throw new BusinessException(ResultCode.PASSWORD_ERROR);
        }

        // 4. 更新密码
        user.setPassword(PasswordUtil.encode(dto.getNewPassword()));
        userMapper.updateById(user);

        // 5. 删除Redis中的Token，强制重新登录
        redisUtil.deleteToken(userId);

        log.info("用户密码修改成功：userId={}", userId);
    }

    @Override
    public void updateAvatar(Long userId, String avatarUrl) {
        // 查询用户
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }

        // 更新头像
        user.setAvatar(avatarUrl);
        userMapper.updateById(user);

        log.info("用户头像更新成功：userId={}, avatarUrl={}", userId, avatarUrl);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void becomeSeller(Long userId, String shopName) {
        // 1. 检查用户是否存在
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }

        // 2. 检查是否已开通卖家功能
        if (isSeller(userId)) {
            throw new BusinessException(400, "您已开通卖家功能，无需重复开通");
        }

        // 3. 创建卖家信息
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setUserId(userId);
        sellerInfo.setShopName(shopName != null ? shopName : user.getNickname() + "的小店");
        sellerInfo.setContactPhone(user.getPhone());
        sellerInfo.setAuditStatus(1); // 直接审核通过，简化流程
        sellerInfoMapper.insert(sellerInfo);

        // 4. 创建商家账户
        sellerAccountService.createAccount(userId);

        log.info("用户开通卖家功能成功：userId={}, shopName={}", userId, sellerInfo.getShopName());
    }

    @Override
    public boolean isSeller(Long userId) {
        LambdaQueryWrapper<SellerInfo> query = new LambdaQueryWrapper<>();
        query.eq(SellerInfo::getUserId, userId);
        return sellerInfoMapper.selectCount(query) > 0;
    }
}
