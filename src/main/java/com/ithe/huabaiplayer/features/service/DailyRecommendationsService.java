package com.ithe.huabaiplayer.features.service;

import com.ithe.huabaiplayer.player.model.dto.anime.AnimeIndexResp;
import com.mybatisflex.core.service.IService;
import com.ithe.huabaiplayer.features.model.entity.DailyRecommendations;

import java.util.List;

/**
 * 每日推荐结果表 服务层。
 *
 * @author L
 * @since 2025-03-25
 */
public interface DailyRecommendationsService extends IService<DailyRecommendations> {

    List<AnimeIndexResp> getRecommendation(Long userId);
}
