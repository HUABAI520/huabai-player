package com.ithe.huabaiplayer.common.service;


import com.ithe.huabaiplayer.common.ErrorCode;
import com.ithe.huabaiplayer.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


/**
 * @author L
 */
@Service
@RequiredArgsConstructor
public class LockService {
    private final RedissonClient redissonClient;

    @SneakyThrows
//    public <T> T executeWithLock(String key, int waitTime, TimeUnit timeUnit, Supplier<T> supplier) {
//        RLock lock = redissonClient.getLock(key);
//        try {
//            if (!lock.tryLock(waitTime, timeUnit)) {
//                throw new BusinessException(ErrorCode.LOCK_LIMIT);
//            }
//            return supplier.get();
//        } finally {
//            lock.unlock();
//        }
//    }
    public <T> T executeWithLock(String key, int waitTime, TimeUnit timeUnit, Supplier<T> supplier) {
        RLock lock = redissonClient.getLock(key);
        try {
            // 尝试获取锁，如果获取失败，则抛出异常
            if (!lock.tryLock(waitTime, timeUnit)) {
                throw new BusinessException(ErrorCode.LOCK_LIMIT);
            }
            // 确保当前线程持有锁
            if (lock.isHeldByCurrentThread()) {
                return supplier.get();
            }
        } catch (InterruptedException e) {
            // 处理中断异常
            Thread.currentThread().interrupt();
            throw new BusinessException(ErrorCode.LOCK_LIMIT);
        } finally {
            // 只有当当前线程持有锁时才解锁
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
        return null;
    }

    public <T> T executeWithLock(String key, Supplier<T> supplier) {
        return executeWithLock(key, -1, TimeUnit.MILLISECONDS, supplier);
    }

    public <T> T executeWithLock(String key, Runnable runnable) {
        return executeWithLock(key, () -> {
            runnable.run();
            return null;
        });
    }

    @FunctionalInterface
    public interface Supplier<T> {
        T get() throws Throwable;
    }

}
