package com.ithe.huabaiplayer.common.exception;


import com.ithe.huabaiplayer.common.BaseResponse;
import com.ithe.huabaiplayer.common.ErrorCode;
import com.ithe.huabaiplayer.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author <a href="https://github.com/lihz">ithe,itz</a>
 * @from 
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessExceptionHandler(BusinessException e) {
        log.error("BusinessException", e);
        return ResultUtils.error(e.getCode(), e.getMessage());
    }

    /**
     * 拦截参数校验异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public BaseResponse<?> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuilder errMsg = new StringBuilder();
        e.getBindingResult().getFieldErrors()
                .forEach(x -> errMsg.append(x.getDefaultMessage()).append(","));
        String string = errMsg.toString();
        String substring = string.substring(0, string.length() - 1);
        return ResultUtils.error(ErrorCode.PARAMS_ERROR.getCode(), substring);
    }
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, "系统错误");
    }
}
