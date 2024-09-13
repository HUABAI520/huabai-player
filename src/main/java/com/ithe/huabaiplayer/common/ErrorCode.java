package com.ithe.huabaiplayer.common;

import lombok.Getter;

/**
 * 自定义错误码
 *
 * @author <a href="https://github.com/lihz">ithe,itz</a>
 * @from
 */
@Getter
public enum ErrorCode {

    SUCCESS(0, "ok"),
    PARAMS_ERROR(40000, "请求参数错误"),
    NOT_LOGIN_ERROR(40100, "请先登录哟~"),
    NO_AUTH_ERROR(40101, "无权限"),
    NO_AUTH_SET(40102, "该权限未设置，请联系开发人员设置！"),
    NO_AREA_ERROR(40505, "没有该仓库的权限哟~"),
    USER_BAN_ERROR(40110, "用户被禁用"),
    NOT_FOUND_ERROR(40400, "请求数据不存在"),
    FORBIDDEN_ERROR(40300, "禁止访问"),
    SYSTEM_ERROR(50000, "系统内部异常"),
    OPERATION_ERROR(50001, "操作失败"),
    PURCHASE_ERROR(51111, "采购订单拥有单据~"),
    // 已存在
    EXIST_ERROR(50002, "数据已存在"),
    LOCK_LIMIT(20000, "不用频繁请求~宝宝会累的~"),
    ;

    /**
     * 状态码
     */
    private final int code;

    /**
     * 信息
     */
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }


}
