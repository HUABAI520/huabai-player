package com.ithe.huabaiplayer.file.minIo.model;

import lombok.Getter;

/**
 * @enum BucketEnum
 * @Author hua bai
 * @Date 2024/9/10 23:54
 **/
@Getter
public enum BucketEnum {
    HUA_VIDEO("hua-video", "视频桶"),
    HUA_PICTURE("hua-picture", "图片桶"),
    HUA_AVATAR("hua-avatar", "头像桶"),
    ;

    private final String bucketName;
    private final String desc;

    BucketEnum(String bucketName, String desc) {
        this.bucketName = bucketName;
        this.desc = desc;
    }
}
