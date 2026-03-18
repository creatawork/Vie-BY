package com.vie.starter.annotation;

import java.lang.annotation.*;

/**
 * 需要登录注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequireLogin {
}
