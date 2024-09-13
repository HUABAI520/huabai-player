package com.ithe.huabaiplayer.common.config.redis;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redisson 配置
 *
 * @author L
 */
@Configuration
@ConfigurationProperties(prefix = "spring.data.redis")
@Data
public class RedissonConfig {

    /**
     * redis地址
     */
    private String host;

    /**
     * redis端口
     */
    private String port;

    private String password;

    @Bean
    public RedissonClient redissonClient() {
        // 创建配置
        Config config = new Config();
        String redisAddress = String.format("redis://%s:%s", host, port);
        // 设置redis地址和数据库为 6
        config.useSingleServer().setAddress(redisAddress).setDatabase(6);
        if (StringUtils.isNotBlank(password)) {
            config.useSingleServer().setPassword(password);
        }
        // 创建实例
        return Redisson.create(config);
    }
}
