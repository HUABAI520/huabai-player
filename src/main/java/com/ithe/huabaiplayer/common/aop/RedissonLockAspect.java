package com.ithe.huabaiplayer.common.aop;


import com.ithe.huabaiplayer.common.annotation.RedissonLock;
import com.ithe.huabaiplayer.common.service.LockService;
import com.ithe.huabaiplayer.common.utils.SpElUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author L
 */
@Component
@Aspect
@Order(0) // 确保比事务注解先执行，分布式锁在事务外（不然会导致锁无意义）
@RequiredArgsConstructor
public class RedissonLockAspect {

    private final LockService lockService;

    @Around("@annotation(redissonLock)")
    public Object around(ProceedingJoinPoint joinPoint, RedissonLock redissonLock) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String prefixKey = redissonLock.prefixKey();
        prefixKey = StringUtils.isBlank(prefixKey) ? SpElUtils.getMethodKey(method) : prefixKey;
        String key = SpElUtils.parseSpEl(method, joinPoint.getArgs(), redissonLock.key());
        int waitTime = redissonLock.waitTime();
        TimeUnit unit = redissonLock.unit();
        return lockService.executeWithLock(prefixKey + ":" + key, waitTime, unit, joinPoint::proceed);
    }
}
