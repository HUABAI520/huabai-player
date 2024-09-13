package com.ithe.huabaiplayer.common.utils;


import com.ithe.huabaiplayer.user.model.vo.UserVO;

/**
 * @author L
 */
public class UserContext {
    private static final ThreadLocal<UserVO> tl = new ThreadLocal<>();

    /**
     * 保存当前登录用户信息到ThreadLocal
     *
     * @param userVO 用户信息
     */
    public static void setUser(UserVO userVO) {
        tl.set(userVO);
    }

    /**
     * 获取当前登录用户信息
     *
     * @return 用户id
     */
    public static UserVO getUser() {
        return tl.get();
    }

    /**
     * 移除当前登录用户信息
     */
    public static void removeUser() {
        tl.remove();
    }
}
