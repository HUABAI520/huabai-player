package com.ithe.huabaiplayer.features.service.impl;

import com.ithe.huabaiplayer.features.mapper.DailyRecommendationsMapper;
import com.ithe.huabaiplayer.features.model.entity.DailyRecommendations;
import com.ithe.huabaiplayer.features.service.DailyRecommendationsService;
import com.ithe.huabaiplayer.player.model.dto.anime.AnimeIndexResp;
import com.ithe.huabaiplayer.player.service.impl.AnimeIndexServiceImpl;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

import static com.ithe.huabaiplayer.features.model.entity.table.DailyRecommendationsTableDef.DAILY_RECOMMENDATIONS;

/**
 * 每日推荐结果表 服务层实现。
 *
 * @author L
 * @since 2025-03-25
 */
@Service
@RequiredArgsConstructor
public class DailyRecommendationsServiceImpl extends ServiceImpl<DailyRecommendationsMapper, DailyRecommendations> implements DailyRecommendationsService {

    private final AnimeIndexServiceImpl animeIndexService;

    @Override
    public List<AnimeIndexResp> getRecommendation(Long userId) {
        // 获取现在时间 只获取到日
        Date now = new Date(System.currentTimeMillis());
        List<Long> animeIdList = this.queryChain()
                .select(DAILY_RECOMMENDATIONS.ANIME_ID)
                .eq(DailyRecommendations::getUserId, userId)
                .eq(DailyRecommendations::getRecommendDate, now)
                .list()
                .stream().map(DailyRecommendations::getAnimeId)
                // 并转为Long
                .map(Integer::longValue)
                .toList();
        if (animeIdList.isEmpty()) {
            return null;
        }
        return animeIndexService.getAnimeIndexRespByIds(animeIdList);
    }
}
