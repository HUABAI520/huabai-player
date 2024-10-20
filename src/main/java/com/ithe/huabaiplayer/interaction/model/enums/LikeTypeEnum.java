package com.ithe.huabaiplayer.interaction.model.enums;

import lombok.Getter;

/**
 * @enum LikeTypeEnum
 * @Author hua bai
 * @Date 2024/9/29 12:17
 **/
@Getter
public enum LikeTypeEnum {
    COMMENT(1, "评论"),
    BARRAGE(2, "弹幕"),
    RATING(3, "评分"),
    ;
    private final Integer type;
    private final String desc;

    LikeTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static LikeTypeEnum of(Integer type) {
        if (type == null) {
            return null;
        }
        // 找到与type相同的LikeTypeEnum.type
        for (LikeTypeEnum value : LikeTypeEnum.values()) {
            if (value.getType().equals(type)) {
                return value;
            }
        }
        return null;
    }
}
