package com.key.oa.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author 孙强
 * 用于快捷地操作redis数据库
 */
@Component
public class RedisUtil {
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Boolean expire(String key, long time) {
        return redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    public Long getTime(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    public void setKeyValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteValue(String key) {
        redisTemplate.delete(key);
    }
}
