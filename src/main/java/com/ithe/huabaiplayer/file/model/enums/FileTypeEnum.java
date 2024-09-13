package com.ithe.huabaiplayer.file.model.enums;

import lombok.Getter;

/**
 * @enum FileTypeEnum
 * @Author hua bai
 * @Date 2024/8/28 18:58
 **/
@Getter
public enum FileTypeEnum {
    FOLDER("folder", "文件夹"),
    FILE("file", "文件"),
    ;

    private final String type;
    private final String desc;

    FileTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
