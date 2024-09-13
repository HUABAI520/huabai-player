package com.ithe.huabaiplayer.common.model.enums;

import lombok.Getter;

/**
 * @author L
 */

@Getter
public enum UseStatusEnum {
    OPEN(1, "启用"),
    CLOSE(0, "禁用");;
    private final Integer status;
    private final String msg;

    UseStatusEnum(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
