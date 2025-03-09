package com.ithe.huabaiplayer.common.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class RestClientConfig {

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    //    @Bean
//    public ObjectMapper objectMapper() {
//        return new ObjectMapper()
//                .registerModule(new JavaTimeModule())
//                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
//                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//    }
    @Bean(name = "pythonObjectMapper")
    public ObjectMapper pythonObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // 设置命名策略为 SNAKE_CASE
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

        // 解决 Long 类型精度丢失问题
        SimpleModule module = new SimpleModule();
        module.addSerializer(Long.class, ToStringSerializer.instance);
        module.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(module);

        // 支持 Java 8 时间类型
        objectMapper.registerModule(new JavaTimeModule());

        // 配置其他行为
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return objectMapper;
    }
}