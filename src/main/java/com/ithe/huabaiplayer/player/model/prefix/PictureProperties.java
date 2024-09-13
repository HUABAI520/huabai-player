package com.ithe.huabaiplayer.player.model.prefix;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName PictureProperties
 * @Author hua bai
 * @Date 2024/8/29 11:29
 **/
@Component
@ConfigurationProperties(prefix = "picture")
@Data
public class PictureProperties {
    // 上传路径
    private String uploadPath;
    // 动漫路径
    private String dongman;
    // ip访问
    private String ipPath;

    public String getDongmanPath() {
        return uploadPath + dongman;
    }

    public String getDongmanPath(String fullPath) {
        return uploadPath + fullPath;
    }

    public String getDongmanUrl() {
        return ipPath + dongman;
    }

    public String getDongmanUrl(String fullPath) {
        return ipPath + fullPath;
    }
}
