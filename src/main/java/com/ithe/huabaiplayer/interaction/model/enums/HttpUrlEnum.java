package com.ithe.huabaiplayer.interaction.model.enums;

import lombok.Getter;

/**
 * @enum FlaskEnum
 * @Author hua bai
 * @Date 2025/2/25 10:00
 **/
@Getter
public enum HttpUrlEnum {
    FLASK_ANALYSIS_URL("http://127.0.0.1:5000/", "flask url"),
    ;
    private final String value;
    private final String name;

    HttpUrlEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }
}
