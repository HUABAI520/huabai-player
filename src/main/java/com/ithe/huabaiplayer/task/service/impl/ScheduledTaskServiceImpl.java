package com.ithe.huabaiplayer.task.service.impl;

import com.ithe.huabaiplayer.common.annotation.RedissonLock;
import com.ithe.huabaiplayer.interaction.model.entity.UserRating;
import com.ithe.huabaiplayer.interaction.service.UserRatingService;
import com.ithe.huabaiplayer.player.model.entity.AnimeIndex;
import com.ithe.huabaiplayer.player.service.AnimeIndexService;
import com.ithe.huabaiplayer.task.model.entity.Task;
import com.ithe.huabaiplayer.task.model.taskNames.TaskConstant;
import com.ithe.huabaiplayer.task.service.ScheduledTaskService;
import com.ithe.huabaiplayer.task.service.TaskService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ithe.huabaiplayer.interaction.model.entity.table.UserRatingTableDef.USER_RATING;
import static com.ithe.huabaiplayer.player.model.entity.table.AnimeIndexTableDef.ANIME_INDEX;

/**
 * @ClassName ScheduledTaskServiceImpl
 * @Author hua bai
 * @Date 2024/9/24 22:32
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduledTaskServiceImpl implements ScheduledTaskService {

    public static final int PAGE_SIZE = 10000;
    // 更新动漫表一次性最大长度
    public static final int UPDATE_SIZE = 5000;
    private final TaskService taskService;
    private final UserRatingService ratingService;
    private final AnimeIndexService animeIndexService;

    @Override
    @RedissonLock(prefixKey = "task", key = "'ratingTask_' + #s")
    public void ratingTask(String s) {
        if (s.equals(TaskConstant.ANIME_TASK)) {
            animeRatings();
        } else {
            System.out.println("未定义任务");
        }
    }

    private void animeRatings() {
        Task task = taskService.queryChain().eq(Task::getTaskKey, TaskConstant.ANIME_TASK).one();
        if (task == null) {
            task = new Task();
            task.setTaskKey(TaskConstant.ANIME_TASK);
        }
        Date time = task.getTaskTime();
        Date currentTime = new Date();

        QueryWrapper query = ratingService.query();
        // 只查询出 动漫id 评分 和可能会有的之前的评分
        query.select(USER_RATING.ANIME_ID, USER_RATING.SCORE, USER_RATING.OLD_SCORE);
        if (time != null) {
            query.ge(UserRating::getRatingTime, time);
        }
        query.le(UserRating::getRatingTime, currentTime);
        log.info("执行任务：{}", currentTime);
        Page<UserRating> page = ratingService.page(new Page<>(1, PAGE_SIZE), query);
        long totalPage = page.getTotalPage();
        List<UserRating> records = page.getRecords();
        if (totalPage == 0 || records.isEmpty()) {
            return;
        }
        List<AnimeIndex> all = new ArrayList<>();
        ratingChuli(records, all);
        for (int i = 2; i <= totalPage; i++) {
            page = ratingService.page(new Page<>(i, PAGE_SIZE), query);
            ratingChuli(page.getRecords(), all);
            // 也限制all 的长度
            if (all.size() >= UPDATE_SIZE) {
                animeIndexService.updateBatch(all);
                all.clear();
            }
        }
        if (!all.isEmpty()) {
            animeIndexService.updateBatch(all);
        }
        task.setTaskTime(currentTime);
        taskService.saveOrUpdate(task);
    }

    private void ratingChuli(List<UserRating> records, List<AnimeIndex> all) {
        // 将相同的animeId 收集为一个列表
        Map<Long, List<UserRating>> animeId2Ratings =
                records.stream().collect(Collectors.groupingBy(UserRating::getAnimeId));
        List<Long> list = animeId2Ratings.keySet().stream().toList();
        List<AnimeIndex> animes = animeIndexService.queryChain().in(AnimeIndex::getId, list)
                .select(ANIME_INDEX.ID, ANIME_INDEX.SCORE, ANIME_INDEX.NUMBER).list();
        animes.forEach(a -> {
            // 先算出当前动漫的总评分
            int number = a.getNumber() == null ? 0 : a.getNumber();
            BigDecimal s = a.getScore() == null ? new BigDecimal(0) : a.getScore();
            BigDecimal zong = s.multiply(new BigDecimal(number));
            List<UserRating> userRatings = animeId2Ratings.get(a.getId());
            for (UserRating u : userRatings) {
                BigDecimal score = u.getScore();
                BigDecimal oldScore = u.getOldScore();
                if (oldScore == null) {
                    //表示是新评分而不是更新的评分
                    number++;
                } else {
                    score = score.subtract(oldScore);
                }
                zong = zong.add(score);
            }
            // 算出新的平均分 保留一位小数
            BigDecimal newScore = zong.divide(new BigDecimal(number), 1, RoundingMode.HALF_UP);
            a.setScore(newScore);
            a.setNumber(number);
            all.add(a);
        });
    }
}
