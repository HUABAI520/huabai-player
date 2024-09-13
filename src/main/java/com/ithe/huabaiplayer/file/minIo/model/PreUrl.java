package com.ithe.huabaiplayer.file.minIo.model;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName PreUrl
 * @Author hua bai
 * @Date 2024/9/10 21:38
 **/
@Data
public class PreUrl {
    private String preUrl;
    // 过期的准确时间
    private Date expireTime;
}
