package com.ithe.huabaiplayer.common.constant;

/**
 * 用户常量
 *
 * @author <a href="https://github.com/lihz">ithe,itz</a>
 * @from 
 */
public interface UserConstant {

    /**
     * 用户登录态键
     */
    String USER_LOGIN_STATE = "user_login";
    /**
     * 用户详细信息
     */
    String USER_DETAIL = "user_detail";
    /**
     * 登录中
     */
    String LOGIN_ING = "login_ing";

    //  region 权限

    /**
     * 默认角色
     */
    String DEFAULT_ROLE = "user";

    /**
     * 管理员角色
     */
    String ADMIN_ROLE = "admin";

    /**
     * 被封号
     */
    String BAN_ROLE = "ban";

    // endregion
}
