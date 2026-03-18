package com.vie.service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 */
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * Token前缀
     */
    private static final String TOKEN_PREFIX = "token:";

    /**
     * 保存Token
     *
     * @param userId  用户ID
     * @param token   Token
     * @param timeout 过期时间（秒）
     */
    public void saveToken(Long userId, String token, Long timeout) {
        String key = TOKEN_PREFIX + userId;
        redisTemplate.opsForValue().set(key, token, timeout, TimeUnit.SECONDS);
    }

    /**
     * 获取Token
     *
     * @param userId 用户ID
     * @return Token
     */
    public String getToken(Long userId) {
        String key = TOKEN_PREFIX + userId;
        Object value = redisTemplate.opsForValue().get(key);
        return value != null ? value.toString() : null;
    }

    /**
     * 删除Token
     *
     * @param userId 用户ID
     */
    public void deleteToken(Long userId) {
        String key = TOKEN_PREFIX + userId;
        redisTemplate.delete(key);
    }

    /**
     * 验证Token是否存在
     *
     * @param userId 用户ID
     * @param token  Token
     * @return 是否存在
     */
    public boolean validateToken(Long userId, String token) {
        String cachedToken = getToken(userId);
        return cachedToken != null && cachedToken.equals(token);
    }

    /**
     * 设置值
     *
     * @param key   键
     * @param value 值
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置值（带过期时间）
     *
     * @param key     键
     * @param value   值
     * @param timeout 过期时间（秒）
     */
    public void set(String key, Object value, Long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 获取值
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除键
     *
     * @param key 键
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 判断键是否存在
     *
     * @param key 键
     * @return 是否存在
     */
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}
