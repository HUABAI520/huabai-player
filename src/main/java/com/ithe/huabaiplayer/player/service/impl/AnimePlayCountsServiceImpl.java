package com.ithe.huabaiplayer.player.service.impl;

import com.ithe.huabaiplayer.player.mapper.AnimePlayCountsMapper;
import com.ithe.huabaiplayer.player.model.entity.AnimePlayCounts;
import com.ithe.huabaiplayer.player.service.AnimePlayCountsService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 服务层实现。
 *
 * @author L
 * @since 2024-10-20
 */
@Service
@RequiredArgsConstructor
public class AnimePlayCountsServiceImpl extends ServiceImpl<AnimePlayCountsMapper, AnimePlayCounts> implements AnimePlayCountsService {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String LIST_KEY = "video:play:count";

    @Override
    public void addCount(Long animeId) {
        Double score = redisTemplate.opsForZSet().score(LIST_KEY, animeId);
        if (score == null) {
            AnimePlayCounts id = this.getById(animeId);
            redisTemplate.opsForZSet().add(LIST_KEY, animeId, id == null ? 0 : id.getPlayCount());
        }
        redisTemplate.opsForZSet().incrementScore(LIST_KEY, animeId, 1);
    }

    @Override
    public Long getPlayCount(Long animeId) {
        Double score = redisTemplate.opsForZSet().score(LIST_KEY, animeId);
        if (score != null) {
            return score.longValue();
        }
        AnimePlayCounts id = this.getById(animeId);
        return id != null ? id.getPlayCount() : 0;
    }

    @Override
    public void syncPlayCounts() {
        Set<ZSetOperations.TypedTuple<Object>> tuples = redisTemplate.opsForZSet().rangeWithScores(LIST_KEY, 0, -1);
        if (tuples == null || tuples.isEmpty()) {
            return;
        }
        List<AnimePlayCounts> animePlayCounts = tuples.stream().map(tuple -> {
            Long animeId = (Long) tuple.getValue();
            Double score = tuple.getScore();
            score = score == null ? 0 : score;
            return AnimePlayCounts.builder().animeId(animeId).playCount(score.longValue()).build();
        }).toList();
        this.saveOrUpdateBatch(animePlayCounts);
        redisTemplate.delete(LIST_KEY);
    }
}
