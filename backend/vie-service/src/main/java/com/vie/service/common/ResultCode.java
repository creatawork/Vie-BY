package com.vie.service.common;

import lombok.Getter;

/**
 * 响应码枚举
 */
@Getter
public enum ResultCode {

    /**
     * 成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 失败
     */
    ERROR(500, "操作失败"),

    /**
     * 参数错误
     */
    PARAM_ERROR(400, "参数错误"),

    /**
     * 未认证
     */
    UNAUTHORIZED(401, "未认证，请先登录"),

    /**
     * 无权限
     */
    FORBIDDEN(403, "无权限访问"),

    /**
     * 资源不存在
     */
    NOT_FOUND(404, "资源不存在"),

    /**
     * 用户名已存在
     */
    USERNAME_EXISTS(1001, "用户名已存在"),

    /**
     * 手机号已存在
     */
    PHONE_EXISTS(1002, "手机号已存在"),

    /**
     * 用户不存在
     */
    USER_NOT_FOUND(1003, "用户不存在"),

    /**
     * 密码错误
     */
    PASSWORD_ERROR(1004, "密码错误"),

    /**
     * 用户已禁用
     */
    USER_DISABLED(1005, "用户已被禁用"),

    /**
     * Token无效
     */
    TOKEN_INVALID(1006, "Token无效或已过期"),

    /**
     * 两次密码不一致
     */
    PASSWORD_NOT_MATCH(1007, "两次密码不一致");

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
