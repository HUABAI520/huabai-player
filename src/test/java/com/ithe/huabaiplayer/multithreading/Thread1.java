package com.ithe.huabaiplayer.multithreading;

import com.ithe.huabaiplayer.common.thread.MyThreadFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName Thread1
 * @Author hua bai
 * @Date 2024/10/7 20:35
 **/
public class Thread1 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Counter counter = new Counter();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor(); // 创建一个ThreadPoolTaskExecutor对象
        executor.setWaitForTasksToCompleteOnShutdown(true); // 设置线程池关闭时等待所有任务完成 优雅关闭
        executor.setCorePoolSize(10); // 设置线程池的核心线程数为10
        executor.setMaxPoolSize(10); // 设置线程池的最大线程数为10
        executor.setQueueCapacity(200); // 设置线程池的队列容量为200
        executor.setThreadNamePrefix("huachat-executor-"); // 设置线程池中线程的名称前缀
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); // 设置线程池的拒绝策略为CallerRunsPolicy，即如果线程池已满，那么将任务交给调用线程执行
        executor.setThreadFactory(new MyThreadFactory(executor));
        executor.initialize(); // 初始化线程池

        for (int i = 0; i < 10; i++) {
            Future<Integer> submit = executor.submit(() -> {
                System.out.println(Thread.currentThread().getName() + "新线程启动");
                for (int j = 0; j < 12000000; j++) {
                    counter.add(1);
                }
                System.out.println(Thread.currentThread().getName() + "新线程结束");
                return 1;
            });
            System.out.println("获得返回值："+ submit.get());
            extracted(counter);
            counter.clear();
        }
        executor.shutdown();
        // 等待所有任务完成
        System.out.println(Thread.currentThread().getName() + "线程结束");
    }

    private static void extracted(Counter counter) throws InterruptedException {
        long start = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + "线程启动");
        for (int i = 0; i < 10000000; i++) {
            counter.add(1);
        }
        System.out.println(Thread.currentThread().getName() + "结果："+ counter.get());
        System.out.println("最终结果：" + counter.get());
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start) + "ms");
    }
}
