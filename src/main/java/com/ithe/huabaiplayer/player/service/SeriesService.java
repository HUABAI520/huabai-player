package com.ithe.huabaiplayer.player.service;

import com.ithe.huabaiplayer.player.model.dto.series.SeriesAddAnimeReq;
import com.ithe.huabaiplayer.player.model.dto.series.SeriesAddReq;
import com.ithe.huabaiplayer.player.model.dto.series.SeriesResp;
import com.ithe.huabaiplayer.player.model.dto.series.SeriesUpReq;
import com.ithe.huabaiplayer.player.model.entity.Series;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 服务层。
 *
 * @author L
 * @since 2024-11-12
 */
public interface SeriesService extends IService<Series> {
    SeriesResp get(Integer seriesId);

    Integer addSeries(SeriesAddReq req);

    Boolean addAnime(SeriesAddAnimeReq req);

    Boolean removeAnimeSeriesId(List<Long> animeId);

    Boolean remove(Integer seriesId);


    Page<SeriesResp> pageQuery(Integer pageNum, Integer pageSize,String name);

    Boolean updateById(SeriesUpReq up);

    Page<SeriesResp> page(Integer pageNum, Integer pageSize, String keyword);
}
