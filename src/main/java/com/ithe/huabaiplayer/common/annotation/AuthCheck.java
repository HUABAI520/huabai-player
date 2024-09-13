package com.ithe.huabaiplayer.common.annotation;


import com.ithe.huabaiplayer.common.constant.UserConstant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限校验
 *
 * @author <a href="https://github.com/lihz">ithe,itz</a>
 * @from
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {

    /**
     * 必须有某个角色
     *
     * @return
     */
    String mustRole() default UserConstant.USER_LOGIN_STATE;

    String mustPermission() default  "";


    String operModule() default ""; // 操作模块

    String operType() default "";  // 操作类型

    String operDesc() default "";  // 操作说明
}

