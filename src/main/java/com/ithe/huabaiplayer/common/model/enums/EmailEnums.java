package com.ithe.huabaiplayer.common.model.enums;

import com.ithe.huabaiplayer.common.constant.EmailConstant;
import lombok.Getter;

/**
 * @enum EmailEnums
 * @Author hua bai
 * @Date 2025/2/12 11:13
 **/
@Getter
public enum EmailEnums {
    REGISTER(EmailConstant.REGISTER_TEMPLATE, "欢迎注册 hua-player 动漫视频平台 验证码"),
    LOGIN(EmailConstant.LOGIN_TEMPLATE, "登录 hua-player 动漫视频平台 验证码"),
    RESET_PASSWORD(EmailConstant.RESET_PASSWORD_TEMPLATE, "重置密码 hua-player 动漫视频平台 验证码")
    ;
    private final String template;
    private final String subject;

    EmailEnums(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }
}
