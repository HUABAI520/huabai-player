package com.ithe.huabaiplayer.player.model.prefix;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName PlayerProperties
 * @Author hua bai
 * @Date 2024/8/28 18:40
 **/
@Component
@ConfigurationProperties(prefix = "shiping")
@Data
public class PlayerProperties {
    private String uploadPath;
    private String ipPath;


    public String getPath(String path) {
        return uploadPath + path;
    }
    public String getIpPath(String path) {
        return ipPath + path;
    }
}
