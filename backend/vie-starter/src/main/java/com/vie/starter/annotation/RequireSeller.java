package com.vie.starter.annotation;

import java.lang.annotation.*;

/**
 * 需要卖家身份的注解
 * 用于标记需要开通卖家功能才能访问的接口
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequireSeller {
}
