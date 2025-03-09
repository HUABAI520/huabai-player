package com.ithe.huabaiplayer.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 存储对象
     *
     * @param key   Redis键
     * @param value 要存储的对象
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 存储对象并设置过期时间
     *
     * @param key     Redis键
     * @param value   要存储的对象
     * @param timeout 过期时间
     * @param unit    时间单位
     */
    public void setWithExpire(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 获取对象
     *
     * @param key Redis键
     * @return 获取到的对象
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除对象
     *
     * @param key Redis键
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 判断Key是否存在
     *
     * @param key Redis键
     * @return 是否存在
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
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

    public void expire(String key, long timeout) {
        redisTemplate.expire(key, timeout, TimeUnit.MINUTES);
    }

    /**
     * 存储列表
     */
    public void setList(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 一次性存储列表
     */
    public void setListAll(String key, Collection<Object> values) {
        redisTemplate.opsForList().rightPushAll(key, values);
    }

    /**
     * 获取列表最右边的元素
     */
    public Object rightPopList(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 获取全部列表
     */
    public List<Object> getList(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }


    /**
     * 存储Set
     */
    public void setSet(String key, Object value) {
        redisTemplate.opsForSet().add(key, value);
    }

    /**
     * 获取Set
     */
    public Set<Object> getSetAll(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * set 删除某一个
     */
    public void deleteSet(String key, Object value) {
        redisTemplate.opsForSet().remove(key, value);
    }

    /**
     * 一次性添加多个成员到Set
     */
    public Long setSetAll(String key, Object... values) {
        // 使用可变参数列表传递多个值
        return redisTemplate.opsForSet().add(key, values);
    }

    /**
     * 一个Collection类型的值列表
     */
    public Long setSetAllWithCollection(String key, Collection<Object> values) {
        // 使用集合类型的数据结构来传递多个值
        return redisTemplate.opsForSet().add(key, values.toArray());
    }
    /**
     * 查询key 和 value 是否存在
     */
    public Boolean hasKey(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }


}
