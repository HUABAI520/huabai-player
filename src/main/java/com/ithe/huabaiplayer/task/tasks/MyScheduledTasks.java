package com.ithe.huabaiplayer.task.tasks;

import com.ithe.huabaiplayer.interaction.service.handler.LikeService;
import com.ithe.huabaiplayer.player.service.AnimePlayCountsService;
import com.ithe.huabaiplayer.task.model.taskNames.TaskConstant;
import com.ithe.huabaiplayer.task.service.ScheduledTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * @author L
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class MyScheduledTasks {
    private final ScheduledTaskService service;
    private final LikeService likeService;
    private final AnimePlayCountsService countsService;


    @Scheduled(cron = "0 7 0 * * ?")
    public void ratingTask() {
        prepareTask(service::ratingTask, TaskConstant.ANIME_TASK);
    }

    // 每两分钟执行
    @Scheduled(cron = "0 */2 * * * ?")
    public void likeTask() {
        prepareTask(likeService::likeCoTask, TaskConstant.LIKE_COMMENT_TASK);
    }
    @Scheduled(cron = "0 */5 * * * ?")
    public void likeTask2() {
        prepareTask(countsService::syncPlayCounts, TaskConstant.PLAY_COUNTS_TASK);
    }
    @Scheduled(cron = "0 */4 * * * ?")
    public void likeTask3() {
        prepareTask(likeService::likeRaTask, TaskConstant.LIKE_RATING_TASK);
    }


    /**
     * 准备并执行指定的定时任务
     *
     * @param consumer 接受字符串类型的消费者接口，用于处理任务名称
     * @param taskName 待执行的定时任务名称
     */
    public void prepareTask(Consumer<String> consumer, String taskName) {
        // 记录任务开始执行的时间
        long startTime = System.nanoTime();
        // 记录任务开始执行的日志
        log.info("开始执行定时任务：{}", taskName);
        // 执行任务，通过消费者接口处理任务名称
        consumer.accept(taskName);
        // 记录任务结束的时间
        long endTime = System.nanoTime();
        // 计算任务执行的时间
        long duration = endTime - startTime;
        // 记录任务结束执行的日志
        log.info("结束执行定时任务：{}", taskName);
        // 记录任务执行的时间，包括纳秒和毫秒单位
        log.info("程序运行时间： {}ns 或者说 {}ms", duration, duration / (1000000));
    }

    public void prepareTask(Runnable runnable, String taskName) {
        prepareTask(s -> runnable.run(), taskName);
    }
}