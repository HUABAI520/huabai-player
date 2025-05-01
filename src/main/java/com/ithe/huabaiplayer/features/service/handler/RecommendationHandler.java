package com.ithe.huabaiplayer.features.service.handler;

import com.ithe.huabaiplayer.common.ErrorCode;
import com.ithe.huabaiplayer.common.exception.BusinessException;
import com.ithe.huabaiplayer.common.model.dto.RestClient.ApiResponse;
import com.ithe.huabaiplayer.common.model.dto.RestClient.TypeReference;
import com.ithe.huabaiplayer.common.service.RestClient;
import com.ithe.huabaiplayer.features.model.okhttp.RecommendResp;
import com.ithe.huabaiplayer.features.service.DailyRecommendationsService;
import com.ithe.huabaiplayer.player.model.dto.anime.AnimeIndexResp;
import com.ithe.huabaiplayer.player.service.impl.AnimeIndexServiceImpl;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ithe.huabaiplayer.common.constant.RedisKeyConstants.HOT_ANIME_KEY;
import static com.ithe.huabaiplayer.player.model.entity.table.AnimeIndexTableDef.ANIME_INDEX;
import static com.ithe.huabaiplayer.player.model.entity.table.AnimePlayCountsTableDef.ANIME_PLAY_COUNTS;

/**
 * @ClassName RecommendationHandler
 * @Author hua bai
 * @Date 2025/3/26 19:35
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendationHandler {
    private final DailyRecommendationsService dailyRecommendationsService;
    private final AnimeIndexServiceImpl animeIndexService;
    private final RestClient restClient;

    private final RedisTemplate<String, Object> redisTemplate;
    @Value("${python.recommendation}")
    private String FLASK__URL;

    public List<AnimeIndexResp> getRecommendation(Long userId) {
        return dailyRecommendationsService.getRecommendation(userId);
    }

    public List<AnimeIndexResp> getRealTimeRecommendation(Long userId) {
        ApiResponse<List<RecommendResp>> response = restClient.get(FLASK__URL + "recommend")
                .queryParam("user_id", userId)
                // 默认10条
                .queryParam("top_n", 10)
                .queryParam("allow_repeat", false)
                .execute(new TypeReference<>() {
                });
        if (!response.isSuccessful()) {
            int code = response.getStatusCode();
            log.error("获取推荐失败了~！{}", code);
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "获取推荐失败了~");
        }
        List<Long> animeIds = response.getData().stream().map(RecommendResp::getAnimeId).toList();
        if (animeIds.isEmpty()) {
            // 表示已经推荐完了~
            return null;
        }
        return animeIndexService.getAnimeIndexRespByIds(animeIds);
    }

    // 定时任务对动漫按播放量进行排序
    public void topAnimeTask() {
        // 2000 为一页进行查询,循环到查询完毕
        boolean isAll = false;
        int page = 1;
        while (!isAll) {
            QueryWrapper queryWrapper = animeIndexService.query().select(ANIME_INDEX.ID, ANIME_PLAY_COUNTS.PLAY_COUNT)
                    .from(ANIME_INDEX)
                    .leftJoin(ANIME_PLAY_COUNTS).on(ANIME_INDEX.ID.eq(ANIME_PLAY_COUNTS.ANIME_ID))
                    .orderBy(ANIME_PLAY_COUNTS.PLAY_COUNT, false);
            Page<AnimeIndexResp> animes = animeIndexService.pageAs(new Page<>(page, 2000),
                    queryWrapper, AnimeIndexResp.class);
            if (animes.getRecords().size() < 2000) {
                isAll = true;
            } else {
                page++;
            }
            // 将排序好的数据存入sorted redis 结构中
            for (AnimeIndexResp animeIndexResp : animes.getRecords()) {
                updateAnimeHotness(animeIndexResp.getId(), animeIndexResp.getPlayCount());
            }
        }

    }

    /**
     * 更新动漫热度数据
     *
     * @param animeId   动漫ID
     * @param viewCount 播放量增量
     */
    public void updateAnimeHotness(Long animeId, Long viewCount) {
        if (viewCount == null) {
            return;
        }
        redisTemplate.opsForZSet().add(HOT_ANIME_KEY, animeId.toString(), viewCount.doubleValue());
    }

    public Map<Long, Double> getAnimeHotnessScores(long start, long end) {
        Set<ZSetOperations.TypedTuple<Object>> tuples = redisTemplate.opsForZSet()
                .reverseRangeWithScores(HOT_ANIME_KEY, start, end);

        if (tuples == null) {
            return Collections.emptyMap();
        }

        return tuples.stream()
                .filter(tuple -> tuple.getValue() != null && tuple.getScore() != null)
                .collect(Collectors.toMap(
                        tuple -> Long.parseLong(tuple.getValue().toString()),
                        ZSetOperations.TypedTuple::getScore,
                        (oldVal, newVal) -> oldVal,
                        LinkedHashMap::new
                ));
    }

    public Page<AnimeIndexResp> getHotRecommendation(Long userId, Integer pageNum, Integer pageSize) {
        // redis获取总数
        Long l = redisTemplate.opsForZSet().zCard(HOT_ANIME_KEY);
        if (l == null) {
            return new Page<>(pageNum, pageSize, 0);
        }
        // 计算分页范围
        long start = (long) (pageNum - 1) * pageSize;
        long end = start + pageSize - 1;
        // 获取分页数据
        Map<Long, Double> animeIdCounts = getAnimeHotnessScores(start, end);
        List<Long> animeIds = animeIdCounts.keySet().stream().toList();
        if (animeIds.isEmpty()) {
            return new Page<>(pageNum, pageSize, l);
        }
        List<AnimeIndexResp> respByIds = animeIndexService.getAnimeIndexRespByIds(animeIds);
        respByIds.forEach(animeIndexResp -> {
            Double v = animeIdCounts.get(animeIndexResp.getId());
            long value = v.longValue();
            animeIndexResp.setPlayCount(value);
        });
        return new Page<>(respByIds, pageNum, pageSize, l);
    }

}
