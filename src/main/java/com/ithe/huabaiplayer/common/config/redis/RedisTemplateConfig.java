package com.ithe.huabaiplayer.common.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author L
 */
@Configuration
public class RedisTemplateConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(RedisSerializer.string()); // 设置key的序列化方式

        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer()); // 设置value的序列化方式
        redisTemplate.setHashKeySerializer(RedisSerializer.string()); // 设置 hash key 的序列化器
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer()); // 设置 hash value 的序列化器
        return redisTemplate;
    }

}
