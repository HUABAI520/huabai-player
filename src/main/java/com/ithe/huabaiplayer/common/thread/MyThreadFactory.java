package com.ithe.huabaiplayer.common.thread;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

import java.util.concurrent.ThreadFactory;

/**
 * @author L
 */
@AllArgsConstructor
public class MyThreadFactory implements ThreadFactory {

    private static final MyUncaughtExceptionHandler MY_UNCAUGHT_EXCEPTION_HANDLER = new MyUncaughtExceptionHandler();

    private ThreadFactory orginThreadFactory;

    @Override
    @NotNull
    public Thread newThread(Runnable r) {
        Thread thread = orginThreadFactory.newThread(r); // 执行spring自己的线程创建逻辑
        thread.setUncaughtExceptionHandler(MY_UNCAUGHT_EXCEPTION_HANDLER); // 添加设置线程的自定义异常处理器（装饰）
        return thread;
    }
}
