package com.ithe.huabaiplayer.file.minIo.config;

import com.ithe.huabaiplayer.file.minIo.model.MinIoProperties;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author L
 */
@Configuration
public class MinIoConfig {
    @Autowired
    private MinIoProperties minIoProperties;


    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(minIoProperties.getEndpoint())
                .credentials(minIoProperties.getAccessKey(), minIoProperties.getSecretKey())
                .build();
    }
}