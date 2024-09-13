package com.ithe.huabaiplayer.common.exception;


import com.ithe.huabaiplayer.common.ErrorCode;

/**
 * 抛异常工具类
 *
 * @author <a href="https://github.com/lihz">ithe,itz</a>
 * @from 
 */
public class ThrowUtils {

    /**
     * 条件成立则抛异常
     *
     * @param condition
     * @param runtimeException
     */
    public static void throwIf(boolean condition, RuntimeException runtimeException) {
        if (condition) {
            throw runtimeException;
        }
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition
     * @param errorCode
     */
    public static void throwIf(boolean condition, ErrorCode errorCode) {
        throwIf(condition, new BusinessException(errorCode));
    }

    public static void throwIf(String condition, ErrorCode errorCode) {
        boolean f = condition == null;
        throwIf(f, new BusinessException(errorCode));
    }
    /**
     * 条件成立则抛异常
     *
     * @param condition
     * @param errorCode
     * @param message
     */
    public static void throwIf(boolean condition, ErrorCode errorCode, String message) {
        throwIf(condition, new BusinessException(errorCode, message));
    }
}
