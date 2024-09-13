package com.ithe.huabaiplayer.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author L
 */
@Service
public class RedisHashService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 存储单个Hash字段
     *
     * @param key     Redis键
     * @param hashKey Hash字段
     * @param value   存储的值
     */
    public void put(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public void put(String key, String hashKey, Object value, int durationMinutes) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 存储整个Hash
     *
     * @param key Redis键
     * @param map Hash内容
     */
    public void putAll(String key, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 获取单个Hash字段的值
     *
     * @param key     Redis键
     * @param hashKey Hash字段
     * @return 对应字段的值
     */
    public Object get(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 获取整个Hash
     *
     * @param key Redis键
     * @return Hash表的所有字段和值
     */
    public Map<Object, Object> getAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 删除单个Hash字段
     *
     * @param key     Redis键
     * @param hashKey Hash字段
     */
    public void delete(String key, String hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
    }

    /**
     * 设置Key的过期时间
     *
     * @param key     Redis键
     * @param timeout 过期时间
     * @param unit    时间单位
     */
    public void expire(String key, long timeout, TimeUnit unit) {
        redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 判断Hash字段是否存在
     *
     * @param key     Redis键
     * @param hashKey Hash字段
     * @return 是否存在
     */
    public boolean hasKey(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }
}
