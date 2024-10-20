package com.ithe.huabaiplayer.interaction.controller;

import com.ithe.huabaiplayer.common.BaseResponse;
import com.ithe.huabaiplayer.common.CursorPage;
import com.ithe.huabaiplayer.common.ResultUtils;
import com.ithe.huabaiplayer.common.annotation.AuthCheck;
import com.ithe.huabaiplayer.common.utils.UserContext;
import com.ithe.huabaiplayer.interaction.model.dto.req.BarrageAddReq;
import com.ithe.huabaiplayer.interaction.model.dto.resp.BarrageResp;
import com.ithe.huabaiplayer.interaction.service.BarrageService;
import com.ithe.huabaiplayer.user.model.vo.UserVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName BarrageController
 * @Author hua bai
 * @Date 2024/10/13 21:30
 **/
@Slf4j
@RestController
@RequestMapping("/api-player/barrage")
@RequiredArgsConstructor
public class BarrageController {
    private final BarrageService barrageService;

    @PostMapping
    @AuthCheck
    public BaseResponse<Boolean> addBarrage(@RequestBody @Valid BarrageAddReq add) {
        UserVO user = UserContext.getUser();
        barrageService.addBarrage(add, user.getId(), "barrage");
        return ResultUtils.success(true);
    }

    /**
     * 直接默认分页大小 1000
     * 使用游标
     * maxCursor 上次最大的id
     */
    @GetMapping
    public BaseResponse<CursorPage<BarrageResp>> getBarrages(Long videoId, Long maxCursor) {
        return ResultUtils.success(barrageService.getBarrages(videoId, maxCursor));
    }
}
