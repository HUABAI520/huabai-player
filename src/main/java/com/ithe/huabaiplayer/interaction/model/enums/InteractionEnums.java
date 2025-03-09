package com.ithe.huabaiplayer.interaction.model.enums;

import lombok.Getter;

/**
 * @enum InteractionEnums
 * @Author hua bai
 * @Date 2025/2/25 16:26
 **/
@Getter
public enum InteractionEnums {
    // 这里可以添加使用工厂/策略模式 但是我这个就为了审核这些功能，所以就直接写死了
    BARRAGE(2, "弹幕", "content"),
    COMMENT(1, "评论", "content"),
    USER_RATING(3, "用户评分", "comment"),
    ;
    private final Integer code;
    private final String desc;
    // 内容的字段名
    private final String value;

    InteractionEnums(Integer code, String desc, String value) {
        this.code = code;
        this.desc = desc;
        this.value = value;
    }
    // 根据code 查询这个枚举
    public static InteractionEnums getByCode(Integer code) {
        for (InteractionEnums value : InteractionEnums.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
