package com.ithe.huabaiplayer.common.utils;

import java.util.Random;

public class VerificationCodeUtil {

    private static final String DIGITS = "0123456789";
    private static final int CODE_LENGTH = 6;

    /**
     * 生成6位随机验证码
     */
    public static String generateVerificationCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        }
        return code.toString();
    }
}