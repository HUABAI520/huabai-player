package com.ithe.huabaiplayer.interaction.model.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName MinIoProperties
 * @Author hua bai
 * @Date 2024/9/11 21:08
 **/
@Component
@ConfigurationProperties(prefix = "python")
@Data
public class HttpUrlProperties {
    private String analysis;

    private String recommendation;
}
