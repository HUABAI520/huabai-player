package com.ithe.huabaiplayer.common.constant;

/**
 * @interface EmailConstant
 * @Author hua bai
 * @Date 2025/2/12 11:07
 **/
public interface EmailConstant {
    String COMMON_TEMPLATE = "<html>"
            + "<body style='font-family: Arial, sans-serif;'>"
            + "<h1>${title}</h1>"
            + "<p>${message}</p>"
            + "<div style='background-color: #f0f0f0; padding: 10px; border-radius: 5px; display: inline-block;'>"
            + "<span id='verificationCode' style='font-size: 18px; font-weight: bold;'>${code}</span>"
            + "</div>"
            + "<p>${footer}</p>"
            + "<p>祝您观影愉快！</p>"
            + "<p>hua-player 团队</p>"
            + "</body>"
            + "</html>";

    String REGISTER_TEMPLATE = COMMON_TEMPLATE.replace("${title}", "欢迎注册 hua-player 动漫视频平台！")
            .replace("${message}", "感谢您注册 hua-player，请使用以下验证码完成注册：")
            .replace("${footer}", "验证码将在5分钟后失效，请尽快使用。如果您没有注册 hua-player，请忽略此邮件。");

    String LOGIN_TEMPLATE = COMMON_TEMPLATE.replace("${title}", "欢迎登录 hua-player 动漫视频平台！")
            .replace("${message}", "感谢您使用 hua-player，请使用以下验证码完成登录：")
            .replace("${footer}", "验证码将在5分钟后失效，请尽快使用。如果您没有登录 hua-player，请忽略此邮件。");

    String RESET_PASSWORD_TEMPLATE = COMMON_TEMPLATE.replace("${title}", "重置密码")
            .replace("${message}", "您好，您正在重置 hua-player 密码。请使用以下验证码：")
            .replace("${footer}", "验证码将在5分钟后失效，请尽快使用。如果您没有进行密码重置，请忽略此邮件。");
}
