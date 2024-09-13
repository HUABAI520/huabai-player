package com.ithe.huabaiplayer.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 存储对象
     * @param key Redis键
     * @param value 要存储的对象
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 存储对象并设置过期时间
     * @param key Redis键
     * @param value 要存储的对象
     * @param timeout 过期时间
     * @param unit 时间单位
     */
    public void setWithExpire(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 获取对象
     * @param key Redis键
     * @return 获取到的对象
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除对象
     * @param key Redis键
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 判断Key是否存在
     * @param key Redis键
     * @return 是否存在
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 设置Key的过期时间
     * @param key Redis键
     * @param timeout 过期时间
     * @param unit 时间单位
     */
    public void expire(String key, long timeout, TimeUnit unit) {
        redisTemplate.expire(key, timeout, unit);
    }
    public void expire(String key, long timeout) {
        redisTemplate.expire(key, timeout, TimeUnit.MINUTES);
    }
}
