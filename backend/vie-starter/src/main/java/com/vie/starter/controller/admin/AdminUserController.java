package com.vie.starter.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vie.db.entity.User;
import com.vie.db.mapper.UserMapper;
import com.vie.service.common.Result;
import com.vie.service.constant.RoleConstant;
import com.vie.starter.annotation.RequireRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 管理员-用户管理
 */
@Slf4j
@RestController
@RequestMapping("/api/admin/users")
@RequireRole(RoleConstant.ADMIN_ROLE_CODE)
public class AdminUserController {

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户列表（分页）
     */
    @GetMapping
    public Result<IPage<User>> getUserList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword) {
        
        Page<User> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getIsDeleted, 0);
        
        // 关键词搜索
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.and(w -> w.like(User::getUsername, keyword)
                    .or().like(User::getNickname, keyword)
                    .or().like(User::getPhone, keyword));
        }
        
        wrapper.orderByDesc(User::getCreateTime);
        IPage<User> result = userMapper.selectPage(page, wrapper);
        
        // 隐藏密码
        result.getRecords().forEach(user -> user.setPassword(null));
        
        return Result.success(result);
    }

    /**
     * 修改用户状态
     */
    @PutMapping("/{userId}/status")
    public Result<Void> updateUserStatus(
            @PathVariable Long userId,
            @RequestParam Integer status) {
        
        User user = userMapper.selectById(userId);
        if (user == null) {
            return Result.error("用户不存在");
        }
        
        user.setStatus(status);
        userMapper.updateById(user);
        
        log.info("管理员修改用户状态：userId={}, status={}", userId, status);
        return Result.success();
    }

    /**
     * 用户详情
     */
    @GetMapping("/{userId}")
    public Result<User> getUserDetail(@PathVariable Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            return Result.error("用户不存在");
        }
        // 隐藏密码
        user.setPassword(null);
        return Result.success(user);
    }
}

