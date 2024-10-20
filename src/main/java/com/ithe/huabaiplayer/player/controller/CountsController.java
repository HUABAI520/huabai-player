package com.ithe.huabaiplayer.player.controller;

import com.ithe.huabaiplayer.common.BaseResponse;
import com.ithe.huabaiplayer.common.ResultUtils;
import com.ithe.huabaiplayer.player.service.AnimePlayCountsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName CountsController
 * @Author hua bai
 * @Date 2024/10/20 18:50
 **/
@RestController
@RequestMapping("/api-player/counts")
@Slf4j
@RequiredArgsConstructor
public class CountsController {
    private final AnimePlayCountsService countsService;

    @PostMapping
    public BaseResponse<Void> addCount(Long animeId) {
        countsService.addCount(animeId);
        return ResultUtils.success();
    }
}
