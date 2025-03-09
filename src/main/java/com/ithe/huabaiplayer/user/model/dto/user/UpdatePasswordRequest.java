package com.ithe.huabaiplayer.user.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author <a href="https://github.com/lihz">ithe,itz</a>
 * @from 
 */
@Data
public class UpdatePasswordRequest implements Serializable {
    private static final long serialVersionUID = 3191241716373120793L;

    private String userPassword;

    private String checkPassword;

    private String email;

    private String code;
}
