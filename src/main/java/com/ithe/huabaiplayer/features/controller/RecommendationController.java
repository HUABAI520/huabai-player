package com.ithe.huabaiplayer.features.controller;

import com.ithe.huabaiplayer.common.BaseResponse;
import com.ithe.huabaiplayer.common.ResultUtils;
import com.ithe.huabaiplayer.common.annotation.AuthCheck;
import com.ithe.huabaiplayer.common.constant.UserConstant;
import com.ithe.huabaiplayer.common.utils.UserContext;
import com.ithe.huabaiplayer.features.service.handler.RecommendationHandler;
import com.ithe.huabaiplayer.player.model.dto.anime.AnimeIndexResp;
import com.mybatisflex.core.paginate.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName RecommendationController
 * @Author hua bai
 * @Date 2025/3/26 13:14
 **/
@RestController
@RequestMapping("/api-player/recommendation")
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationHandler recommendationHandler;

    // 获取每日推荐列表
    @GetMapping("/getRecommendation")
    @AuthCheck
    public BaseResponse<List<AnimeIndexResp>> getRecommendation() {
        Long userId = UserContext.getUser().getId();
        return ResultUtils.success(recommendationHandler.getRecommendation(userId));
    }

    // 获取实时推荐列表
    @GetMapping("/getRealTimeRecommendation")
    @AuthCheck
    public BaseResponse<List<AnimeIndexResp>> getRealTimeRecommendation() {
        Long userId = UserContext.getUser().getId();
        return ResultUtils.success(recommendationHandler.getRealTimeRecommendation(userId));
    }

    // 获取热度推荐列表
    @GetMapping("/getHotRecommendation")
    @AuthCheck
    public BaseResponse<Page<AnimeIndexResp>> getHotRecommendation(Integer pageNum, Integer pageSize) {
        Long userId = UserContext.getUser().getId();
        return ResultUtils.success(recommendationHandler
                .getHotRecommendation(userId, pageNum, pageSize));
    }

    // 手动刷新排行
    @GetMapping("/topAnime")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<String> topAnime() {
        recommendationHandler.topAnimeTask();
        return ResultUtils.success("刷新排行成功");
    }
}
