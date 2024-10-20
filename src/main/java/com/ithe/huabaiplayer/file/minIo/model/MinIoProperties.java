package com.ithe.huabaiplayer.file.minIo.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName MinIoProperties
 * @Author hua bai
 * @Date 2024/9/11 21:08
 **/
@Component
@ConfigurationProperties(prefix = "minio")
@Data
public class MinIoProperties {
    private String endpoint;

    private String accessKey;

    private String secretKey;

}
