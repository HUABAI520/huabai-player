package com.ithe.huabaiplayer.player.controller;

import com.ithe.huabaiplayer.common.BaseResponse;
import com.ithe.huabaiplayer.common.ResultUtils;
import com.ithe.huabaiplayer.common.annotation.AuthCheck;
import com.ithe.huabaiplayer.common.utils.UserContext;
import com.ithe.huabaiplayer.player.model.dto.videoRecord.VideoRecordAddReq;
import com.ithe.huabaiplayer.player.model.dto.videoRecord.VideoRecordQueryReq;
import com.ithe.huabaiplayer.player.model.dto.videoRecord.VideoRecordResp;
import com.ithe.huabaiplayer.player.service.handler.VideoService;
import com.ithe.huabaiplayer.user.model.vo.UserVO;
import com.mybatisflex.core.paginate.Page;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName VideoRecordController
 * @Author hua bai
 * @Date 2024/9/3 22:30
 **/
@RestController
@RequestMapping("/api-player/video/record")
@Slf4j
@RequiredArgsConstructor
public class VideoRecordController {
    private final VideoService videoService;

    @PostMapping("/noMsg/addOrUp")/*noMsg表示防抖后不提示*/
    @AuthCheck
    public BaseResponse<Boolean> addOrUp(@RequestBody @Valid VideoRecordAddReq videoRecordAddReq) {
        UserVO user = UserContext.getUser();
        return ResultUtils.success(videoService.addRecord(videoRecordAddReq, user.getId()));
    }

    @PostMapping("/list")
    @AuthCheck
    public BaseResponse<Page<VideoRecordResp>> listRecord(@RequestBody VideoRecordQueryReq videoRecordQueryReq) {
        UserVO user = UserContext.getUser();
        return ResultUtils.success(videoService.listRecord(videoRecordQueryReq, user.getId()));
    }

    // 删除就删最新观看记录的表 全部记录就不删除
    @DeleteMapping("/{id}")
    @AuthCheck
    public BaseResponse<Boolean> deleteRecordById(@PathVariable("id") Long id) {
        UserVO user = UserContext.getUser();
        return ResultUtils.success(videoService.deleteRecord(id, user.getId()));
    }
    // 删除就删最新观看记录的表 全部记录就不删除
    @DeleteMapping("/all")
    @AuthCheck
    public BaseResponse<Boolean> deleteRecordAll() {
        UserVO user = UserContext.getUser();
        return ResultUtils.success(videoService.deleteRecord(user.getId()));
    }
}
