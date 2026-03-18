package com.vie.service.vo;

import lombok.Data;

import java.util.List;

/**
 * 登录返回VO
 */
@Data
public class LoginVO {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 角色编码列表
     */
    private List<String> roleCodes;

    /**
     * JWT Token
     */
    private String token;

    /**
     * Token过期时间（秒）
     */
    private Long expireTime;

    /**
     * 是否已开通卖家功能
     */
    private Boolean isSeller;
}
