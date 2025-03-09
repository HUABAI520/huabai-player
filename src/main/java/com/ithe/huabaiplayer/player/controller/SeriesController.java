package com.ithe.huabaiplayer.player.controller;

import com.ithe.huabaiplayer.common.BaseResponse;
import com.ithe.huabaiplayer.common.ErrorCode;
import com.ithe.huabaiplayer.common.ResultUtils;
import com.ithe.huabaiplayer.common.exception.BusinessException;
import com.ithe.huabaiplayer.player.model.dto.series.SeriesAddAnimeReq;
import com.ithe.huabaiplayer.player.model.dto.series.SeriesAddReq;
import com.ithe.huabaiplayer.player.model.dto.series.SeriesResp;
import com.ithe.huabaiplayer.player.model.dto.series.SeriesUpReq;
import com.ithe.huabaiplayer.player.model.entity.AnimeIndex;
import com.ithe.huabaiplayer.player.model.entity.Series;
import com.ithe.huabaiplayer.player.service.AnimeIndexService;
import com.ithe.huabaiplayer.player.service.SeriesService;
import com.mybatisflex.core.paginate.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName SeriesController
 * @Author hua bai
 * @Date 2024/11/12 20:17
 **/
@RestController
@RequestMapping("/api-player/series")
@Slf4j
@RequiredArgsConstructor
public class SeriesController {
    private final SeriesService service;
    private final AnimeIndexService animeIndexService;

    /**
     * todo 分页实现 并思考是否需要通过 关联查询实现 模糊查询动漫名和动漫别名查询到该系列
     */
    @GetMapping("/page")
    public BaseResponse<Page<SeriesResp>> pageQuery(Integer pageNum, Integer pageSize, String name) {
//        return ResultUtils.success();
        return ResultUtils.success(service.page(pageNum, pageSize, name));
    }

    @GetMapping("/list")
    public BaseResponse<List<SeriesResp>> listQuery(String name) {
        if (StringUtils.isBlank(name)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请输入查询条件");
        }
        return ResultUtils.success(service.listAs(service.query().like(Series::getName, name), SeriesResp.class));
    }

    @GetMapping
    public BaseResponse<SeriesResp> getOne(Integer seriesId) {
        return ResultUtils.success(service.get(seriesId));
    }

    /**
     * 若传入的 动漫已有系列id 之前的会被覆盖
     */
    @PostMapping("/addSeries")
    public BaseResponse<Integer> addSeries(@RequestBody SeriesAddReq req) {
        return ResultUtils.success(service.addSeries(req));
    }

    /**
     * 若传入的 动漫已有系列id 之前的会被覆盖
     */
    @PostMapping("/addAnime")
    public BaseResponse<Boolean> addAnime(@RequestBody SeriesAddAnimeReq req) {
        return ResultUtils.success(service.addAnime(req));
    }

    /**
     * 将动漫的系列id清除
     */
    @DeleteMapping("/deleteAnime")
    public BaseResponse<Boolean> deleteAnime(@RequestBody List<Long> animeId) {
        return ResultUtils.success(service.removeAnimeSeriesId(animeId));
    }

    /**
     * 删除该系列
     */
    @DeleteMapping("/deleteSeries")
    public BaseResponse<Boolean> deleteSeries(Integer seriesId) {
        return ResultUtils.success(service.remove(seriesId));
    }

    /**
     * 只更新序列信息
     */
    @PutMapping
    public BaseResponse<Boolean> updateSeries(@RequestBody SeriesUpReq up) {
        return ResultUtils.success(service.updateById(up));
    }

    /**
     * 更新对应序列下对应动漫的标题
     */
    @PutMapping("/updateAnime")
    public BaseResponse<Boolean> updateAnime(Long animeId, String sessionTitle) {
        return ResultUtils.success(animeIndexService.updateChain()
                .eq(AnimeIndex::getId, animeId)
                .set(AnimeIndex::getSeasonTitle, sessionTitle).update());
    }
}
