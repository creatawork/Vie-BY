package com.vie.starter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vie.service.dto.AddressCreateDTO;
import com.vie.service.dto.AddressUpdateDTO;
import com.vie.service.dto.CollectionQueryDTO;
import com.vie.service.dto.PasswordUpdateDTO;
import com.vie.service.dto.UserLoginDTO;
import com.vie.service.dto.UserRegisterDTO;
import com.vie.service.dto.UserUpdateDTO;
import com.vie.service.service.ProductCollectionService;
import com.vie.service.service.UserAddressService;
import com.vie.service.service.UserService;
import com.vie.service.util.OssUtil;
import com.vie.service.vo.AddressVO;
import com.vie.service.vo.LoginVO;
import com.vie.service.vo.ProductCollectionVO;
import com.vie.service.vo.UserVO;
import com.vie.starter.annotation.RequireLogin;
import com.vie.service.common.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private OssUtil ossUtil;

    @Autowired
    private ProductCollectionService collectionService;

    @Autowired
    private UserAddressService addressService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result<Void> register(@Valid @RequestBody UserRegisterDTO dto) {
        userService.register(dto);
        return Result.success("注册成功", null);
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<LoginVO> login(@Valid @RequestBody UserLoginDTO dto) {
        LoginVO loginVO = userService.login(dto);
        return Result.success("登录成功", loginVO);
    }

    /**
     * 用户登出
     */
    @RequireLogin
    @PostMapping("/logout")
    public Result<Void> logout(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        userService.logout(userId);
        return Result.success("登出成功", null);
    }

    /**
     * 获取个人信息
     */
    @RequireLogin
    @GetMapping("/profile")
    public Result<UserVO> getProfile(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        UserVO userVO = userService.getUserInfo(userId);
        return Result.success(userVO);
    }

    /**
     * 更新个人信息
     */
    @RequireLogin
    @PutMapping("/profile")
    public Result<Void> updateProfile(@Valid @RequestBody UserUpdateDTO dto,
                                      HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        userService.updateUserInfo(userId, dto);
        return Result.success("更新成功", null);
    }

    /**
     * 修改密码
     */
    @RequireLogin
    @PutMapping("/password")
    public Result<Void> updatePassword(@Valid @RequestBody PasswordUpdateDTO dto,
                                       HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        userService.updatePassword(userId, dto);
        return Result.success("密码修改成功，请重新登录", null);
    }

    /**
     * 上传头像
     */
    @RequireLogin
    @PostMapping("/avatar/upload")
    public Result<String> uploadAvatar(@RequestParam("file") MultipartFile file,
                                       HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");

        // 上传文件到OSS
        String avatarUrl = ossUtil.uploadFile(file, "avatar");

        // 更新用户头像信息到数据库
        userService.updateAvatar(userId, avatarUrl);

        return Result.success("头像上传成功", avatarUrl);
    }

    /**
     * 获取用户收藏列表
     */
    @RequireLogin
    @GetMapping("/collections")
    public Result<IPage<ProductCollectionVO>> getCollections(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "time") String sortBy,
            @RequestParam(defaultValue = "desc") String sortOrder,
            HttpServletRequest request) {

        Long userId = (Long) request.getAttribute("userId");

        CollectionQueryDTO queryDTO = new CollectionQueryDTO();
        queryDTO.setPageNum(pageNum);
        queryDTO.setPageSize(pageSize);
        queryDTO.setSortBy(sortBy);
        queryDTO.setSortOrder(sortOrder);

        IPage<ProductCollectionVO> collections =
                collectionService.getCollectionPage(queryDTO, userId);

        return Result.success(collections);
    }

    /**
     * 获取用户收藏商品总数
     */
    @RequireLogin
    @GetMapping("/collections/count")
    public Result<Long> getCollectionCount(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Long count = collectionService.getCollectionCount(userId);
        return Result.success(count);
    }

    /**
     * 清空用户收藏夹
     */
    @RequireLogin
    @DeleteMapping("/collections/clear")
    public Result<Integer> clearCollections(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Integer count = collectionService.clearCollections(userId);
        return Result.success("清空收藏夹成功", count);
    }

    /**
     * 开通卖家功能
     */
    @RequireLogin
    @PostMapping("/become-seller")
    public Result<Void> becomeSeller(@RequestBody(required = false) java.util.Map<String, String> body,
                                     HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String shopName = body != null ? body.get("shopName") : null;
        userService.becomeSeller(userId, shopName);
        return Result.success("开通卖家功能成功", null);
    }

    /**
     * 检查是否已开通卖家功能
     */
    @RequireLogin
    @GetMapping("/seller-status")
    public Result<java.util.Map<String, Boolean>> getSellerStatus(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        boolean isSeller = userService.isSeller(userId);
        return Result.success(java.util.Map.of("isSeller", isSeller));
    }

    // ==================== 收货地址管理 ====================

    /**
     * 添加收货地址
     */
    @RequireLogin
    @PostMapping("/addresses")
    public Result<Long> addAddress(@Valid @RequestBody AddressCreateDTO dto,
                                    HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Long addressId = addressService.addAddress(dto, userId);
        return Result.success("添加地址成功", addressId);
    }

    /**
     * 更新收货地址
     */
    @RequireLogin
    @PutMapping("/addresses/{addressId}")
    public Result<Void> updateAddress(@PathVariable Long addressId,
                                      @Valid @RequestBody AddressUpdateDTO dto,
                                      HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        addressService.updateAddress(addressId, dto, userId);
        return Result.success("更新地址成功", null);
    }

    /**
     * 删除收货地址
     */
    @RequireLogin
    @DeleteMapping("/addresses/{addressId}")
    public Result<Void> deleteAddress(@PathVariable Long addressId,
                                      HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        addressService.deleteAddress(addressId, userId);
        return Result.success("删除地址成功", null);
    }

    /**
     * 获取地址列表
     */
    @RequireLogin
    @GetMapping("/addresses")
    public Result<java.util.List<AddressVO>> getAddressList(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        java.util.List<AddressVO> addresses = addressService.getAddressList(userId);
        return Result.success(addresses);
    }

    /**
     * 获取地址详情
     */
    @RequireLogin
    @GetMapping("/addresses/{addressId}")
    public Result<AddressVO> getAddressDetail(@PathVariable Long addressId,
                                              HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        AddressVO address = addressService.getAddressDetail(addressId, userId);
        return Result.success(address);
    }

    /**
     * 设置默认地址
     */
    @RequireLogin
    @PutMapping("/addresses/{addressId}/default")
    public Result<Void> setDefaultAddress(@PathVariable Long addressId,
                                          HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        addressService.setDefaultAddress(addressId, userId);
        return Result.success("设置默认地址成功", null);
    }

    /**
     * 获取默认地址
     */
    @RequireLogin
    @GetMapping("/addresses/default")
    public Result<AddressVO> getDefaultAddress(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        AddressVO address = addressService.getDefaultAddress(userId);
        return Result.success(address);
    }
}
