package com.ithe.huabaiplayer.file.minIo.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName MinIoFile
 * @Author hua bai
 * @Date 2024/10/28 22:27
 **/
@Data
public class MinIoFile implements Serializable {
    private String path;

    private String fileName;
    /**
     * 1-文件
     * 0-文件夹
     */
    private Integer file;
    /**
     * 文件大小 - b
     * 1 时设置
     */
    private Long size;
}
