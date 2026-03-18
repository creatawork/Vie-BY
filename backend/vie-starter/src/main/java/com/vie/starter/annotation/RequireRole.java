package com.vie.starter.annotation;

import java.lang.annotation.*;

/**
 * 需要指定角色注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequireRole {
    /**
     * 需要的角色编码
     */
    String value();
}
