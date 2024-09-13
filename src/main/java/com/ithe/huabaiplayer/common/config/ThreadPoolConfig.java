package com.ithe.huabaiplayer.common.config;


import com.ithe.huabaiplayer.common.thread.MyThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author L
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig implements AsyncConfigurer {
    /**
     * 项目共用线程池
     */
    public static final String HUACHAT_EXECUTOR = "huachatExecutor";
    /**
     * websocket通信线程池
     */
    public static final String WS_EXECUTOR = "websocketExecutor";

    // 重写getAsyncExecutor方法，返回一个线程池 用于处理异步任务
    @Override
    public Executor getAsyncExecutor() {
        return huachatExecutor();
    }

    @Bean(HUACHAT_EXECUTOR) // 定义一个Spring Bean，名为HUACHAT_EXECUTOR
    @Primary // 如果有多个同类型的Bean，优先选择这个Bean
    public ThreadPoolTaskExecutor huachatExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor(); // 创建一个ThreadPoolTaskExecutor对象
        executor.setWaitForTasksToCompleteOnShutdown(true); // 设置线程池关闭时等待所有任务完成 优雅关闭
        executor.setCorePoolSize(10); // 设置线程池的核心线程数为10
        executor.setMaxPoolSize(10); // 设置线程池的最大线程数为10
        executor.setQueueCapacity(200); // 设置线程池的队列容量为200
        executor.setThreadNamePrefix("huachat-executor-"); // 设置线程池中线程的名称前缀
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); // 设置线程池的拒绝策略为CallerRunsPolicy，即如果线程池已满，那么将任务交给调用线程执行
        executor.setThreadFactory(new MyThreadFactory(executor));
        executor.initialize(); // 初始化线程池
        return executor; // 返回创建的ThreadPoolTaskExecutor对象
    }
}