package com.ithe.huabaiplayer.common.aop;

import cn.hutool.json.JSONUtil;

import com.ithe.huabaiplayer.common.ErrorCode;
import com.ithe.huabaiplayer.common.annotation.AuthCheck;
import com.ithe.huabaiplayer.common.constant.UserConstant;
import com.ithe.huabaiplayer.common.exception.BusinessException;
import com.ithe.huabaiplayer.common.utils.UserContext;
import com.ithe.huabaiplayer.user.model.vo.UserVO;
import com.ithe.huabaiplayer.user.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import java.sql.SQLSyntaxErrorException;
import java.util.UUID;

/**
 * 权限校验 AOP
 *
 * @author <a href="https://github.com/lihz">ithe,itz</a>
 * @from
 */
@Aspect
@Component
@Slf4j
public class AuthInterceptor {

    @Resource
    private UserService userService;


    /**
     * 执行拦截
     *
     * @param joinPoint
     * @param authCheck
     * @return
     */
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        String mustRole = authCheck.mustRole();
        // 计时
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        if (UserConstant.LOGIN_ING.equals(mustRole)) {
            return success(joinPoint, stopWatch, request, authCheck, null);
        }
        // 如果只需要登录执行且不是获取用户详细信息
        if (UserConstant.USER_LOGIN_STATE.equals(mustRole)) {
            // 当前登录用户
            Long loginUser = userService.isLogin(request);
            if (loginUser == null) {
                return false;
            }
            UserVO userVO = new UserVO();
            userVO.setId(loginUser);
            UserContext.setUser(userVO);
            return success(joinPoint, stopWatch, request, authCheck, userVO);
        }
        // 否则需要鉴权
        UserVO loginUser = userService.getLoginUser(request);
        // 如果是admin用户 直接通过
        if (loginUser.getUserRole().equals(UserConstant.ADMIN_ROLE)) {
            // 执行原方法
            // 将该用户信息存入线程中
            UserContext.setUser(loginUser);
            return success(joinPoint, stopWatch, request, authCheck, loginUser);
        }
        // 普通用户 需要鉴权
        if (mustRole.equals(UserConstant.ADMIN_ROLE)) {
            //如果要求管理员
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        if (1 == loginUser.getUserStatus()) {
            throw new BusinessException(ErrorCode.USER_BAN_ERROR);
        }
        // 将该用户信息存入线程中
        UserContext.setUser(loginUser);
        // 通过权限校验，放行
        return success(joinPoint, stopWatch, request, authCheck, loginUser);
//        return joinPoint.proceed();
    }

    public Object success(ProceedingJoinPoint joinPoint, StopWatch stopWatch, HttpServletRequest request, AuthCheck authCheck, UserVO loginUser) throws Throwable {
        // 生成请求唯一 id
        String requestId = UUID.randomUUID().toString();
        String url = request.getRequestURI();
        String ip = request.getRemoteHost();
        // 获取请求参数
        Object[] args = joinPoint.getArgs();
//        String reqParam = "[" + StringUtils.join(args, ", ") + "]";
        String pa;
        if (args.length > 0) {
            pa = JSONUtil.toJsonStr(args[0]);
        } else {
            pa = url;
        }
        // 输出请求日志
        log.info("request start，id: {}, path: {}, ip: {}, params: {}", requestId, url,
                ip, pa);
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (BusinessException e) {
            throw new BusinessException(e.getCode(), e.getMessage());
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (SQLSyntaxErrorException e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "操作失败" + e.getMessage());
        } finally {
            UserContext.removeUser();
        }
        stopWatch.stop();
        long totalTimeMillis = stopWatch.getTotalTimeMillis();
//        recordService.record(url, totalTimeMillis, reqParam);
        log.info("request end, id: {}, cost: {}ms", requestId, totalTimeMillis);
        UserContext.removeUser();
        System.out.println();
        return result;
    }


}

