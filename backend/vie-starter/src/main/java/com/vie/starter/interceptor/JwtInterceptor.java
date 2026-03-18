package com.vie.starter.interceptor;

import com.vie.service.service.UserService;
import com.vie.service.util.JwtUtil;
import com.vie.service.util.RedisUtil;
import com.vie.starter.annotation.RequireLogin;
import com.vie.starter.annotation.RequireRole;
import com.vie.starter.annotation.RequireSeller;
import com.vie.service.common.ResultCode;
import com.vie.service.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

/**
 * JWT认证拦截器
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserService userService;

    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 如果不是方法处理器，直接放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        // 检查方法或类上是否有@RequireLogin注解
        RequireLogin methodAnnotation = handlerMethod.getMethodAnnotation(RequireLogin.class);
        RequireLogin classAnnotation = handlerMethod.getBeanType().getAnnotation(RequireLogin.class);

        // 检查方法或类上是否有@RequireRole注解
        RequireRole roleMethodAnnotation = handlerMethod.getMethodAnnotation(RequireRole.class);
        RequireRole roleClassAnnotation = handlerMethod.getBeanType().getAnnotation(RequireRole.class);

        // 检查方法或类上是否有@RequireSeller注解
        RequireSeller sellerMethodAnnotation = handlerMethod.getMethodAnnotation(RequireSeller.class);
        RequireSeller sellerClassAnnotation = handlerMethod.getBeanType().getAnnotation(RequireSeller.class);

        // 如果没有任何权限注解，直接放行
        if (methodAnnotation == null && classAnnotation == null 
                && roleMethodAnnotation == null && roleClassAnnotation == null
                && sellerMethodAnnotation == null && sellerClassAnnotation == null) {
            return true;
        }

        // 获取Token
        String token = getTokenFromRequest(request);
        if (token == null) {
            throw new BusinessException(ResultCode.UNAUTHORIZED);
        }

        // 验证Token格式
        if (!jwtUtil.validateToken(token)) {
            throw new BusinessException(ResultCode.TOKEN_INVALID);
        }

        // 获取用户ID
        Long userId = jwtUtil.getUserIdFromToken(token);
        if (userId == null) {
            throw new BusinessException(ResultCode.TOKEN_INVALID);
        }

        // 验证Token是否在Redis中（防止用户登出后Token仍然有效）
        if (!redisUtil.validateToken(userId, token)) {
            throw new BusinessException(ResultCode.TOKEN_INVALID);
        }

        // 将用户ID存入请求属性中，供Controller使用
        request.setAttribute("userId", userId);
        request.setAttribute("username", jwtUtil.getUsernameFromToken(token));

        // 检查角色权限
        RequireRole requireRole = roleMethodAnnotation != null ? roleMethodAnnotation : roleClassAnnotation;
        if (requireRole != null) {
            String requiredRole = requireRole.value();
            List<String> userRoles = jwtUtil.getRolesFromToken(token);
            if (userRoles == null || !userRoles.contains(requiredRole)) {
                throw new BusinessException(403, "无权访问，需要" + requiredRole + "角色");
            }
        }

        // 检查卖家权限
        RequireSeller requireSeller = sellerMethodAnnotation != null ? sellerMethodAnnotation : sellerClassAnnotation;
        if (requireSeller != null) {
            if (!userService.isSeller(userId)) {
                throw new BusinessException(403, "请先开通卖家功能");
            }
        }

        return true;
    }

    /**
     * 从请求头中获取Token
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader(TOKEN_HEADER);
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            return header.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
