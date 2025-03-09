package com.ithe.huabaiplayer.file.minIo.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName MinIoBucket
 * @Author hua bai
 * @Date 2024/10/28 22:34
 **/
@Data
public class MinIoBucket implements Serializable {
    private String bucketName;
    /**
     * 文件大小 - b
     */
    private Long size;
    /**
     * 文件数量
     */
    private Integer objectCount;
}
