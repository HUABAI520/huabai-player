package com.ithe.huabaiplayer.user.controller;


import com.ithe.huabaiplayer.common.BaseResponse;
import com.ithe.huabaiplayer.common.ErrorCode;
import com.ithe.huabaiplayer.common.ResultUtils;
import com.ithe.huabaiplayer.common.annotation.AuthCheck;
import com.ithe.huabaiplayer.common.constant.OperationModule;
import com.ithe.huabaiplayer.common.constant.OperationType;
import com.ithe.huabaiplayer.common.constant.UserConstant;
import com.ithe.huabaiplayer.common.exception.BusinessException;
import com.ithe.huabaiplayer.common.utils.UserContext;
import com.ithe.huabaiplayer.user.model.dto.user.UserLoginRequest;
import com.ithe.huabaiplayer.user.model.dto.user.UserRegisterRequest;
import com.ithe.huabaiplayer.user.model.result.LoginResult;
import com.ithe.huabaiplayer.user.model.vo.UserVO;
import com.ithe.huabaiplayer.user.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.ithe.huabaiplayer.common.utils.HuaUtils.hasNonEmptyFields;


/**
 * @author L
 */
@RestController  // 适用于编写restful风格的api，返回默认为JSON类型
@RequestMapping("/api-player/user")
@Slf4j
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        long userRegister = userService.userRegister(userRegisterRequest);
        return ResultUtils.success(userRegister);
    }

    @PostMapping("/login")
    @AuthCheck(mustRole = UserConstant.LOGIN_ING, operModule = OperationModule.USER
            , operType = OperationType.LOGIN, operDesc = "用户登录")
    public BaseResponse<LoginResult> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        UserVO userVO = userService.userLogin(userAccount, userPassword, request);
        if (userVO == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "登录失败！");
        }
        LoginResult loginResult = new LoginResult();
        loginResult.setStatus("ok");
        return ResultUtils.success(loginResult);
    }

    @GetMapping("/get/login")
    public BaseResponse<UserVO> getUserLogin(HttpServletRequest request) {
        UserVO loginUser = userService.getLoginUser(request);
        return ResultUtils.success(loginUser);
    }

    /**
     * 用户退出接口
     *
     * @param request 用户请求
     * @return 是否成功退出，成功返回true，否则返回false
     */
    @PostMapping("/logout")
    @AuthCheck(operModule = OperationModule.USER
            , operType = OperationType.LOGOUT, operDesc = "用户退出登录")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        boolean userLogout = userService.userLogout(request);
        return ResultUtils.success(userLogout);
    }

    @PostMapping("/update")
    public BaseResponse<Boolean> updateUser(@RequestBody UserVO userVO, HttpServletRequest request) {
        if (userVO == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户信息为空");
        }
        // 除了id必须要有更新字段传递,防止恶意更新
        if (!hasNonEmptyFields(userVO)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "无更新字段！！！");
        }
        if (request == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        String userRole = userVO.getUserRole();
        if (userRole != null && userRole.equals(UserConstant.ADMIN_ROLE)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "只允许一个超级管理员！");
        }
        Boolean r = userService.updateUser(userVO, request);
        return ResultUtils.success(r);
    }

    @GetMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE, operModule = OperationModule.USER
            , operType = OperationType.DELETE, operDesc = "管理员删除指定用户")
    public BaseResponse<Boolean> deleteUsers(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户id错误");
        }
        // 框架自动进行逻辑删除
        boolean b = userService.removeById(id);
        return ResultUtils.success(b);
    }

    @PostMapping("/upload")
    @AuthCheck(mustRole = UserConstant.USER_DETAIL)
    public BaseResponse<String> uploadFile(@RequestPart("file") MultipartFile multipartFile) {
        UserVO user = UserContext.getUser();
        return ResultUtils.success(userService.uploadFile(multipartFile, user));
    }
}
