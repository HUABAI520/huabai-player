package com.ithe.huabaiplayer.player.service;

import com.ithe.huabaiplayer.player.model.dto.anime.AnimeAddReq;
import com.ithe.huabaiplayer.player.model.dto.anime.AnimeIndexResp;
import com.ithe.huabaiplayer.player.model.dto.anime.AnimeQueryReq;
import com.ithe.huabaiplayer.player.model.dto.anime.AnimeUpdateReq;
import com.ithe.huabaiplayer.player.model.entity.AnimeIndex;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 动漫表 服务层。
 *
 * @author L
 * @since 2024-08-28
 */
public interface AnimeIndexService extends IService<AnimeIndex> {


    Long add(AnimeAddReq animeAddReq, MultipartFile multipartFile);

    List<AnimeIndexResp> getAnimeIndexRespByIds(List<Long> animeId);

    Page<AnimeIndexResp> findAll(AnimeQueryReq animeQueryReq);

    AnimeIndexResp getAnimeById(Long id,Long userId);

    List<AnimeIndexResp> getRecommendAnime();

    Boolean update(AnimeUpdateReq animeUpdateReq);

    Boolean updateAnimePicture(Long id, MultipartFile multipartFile,Long fileId);

    String uploadVideoPicture(Long animeId, Long videoId, MultipartFile multipartFile,Long fileId);

    List<AnimeIndexResp> list(String name);

    List<AnimeIndexResp> listByIds(List<Long> ids);

    List<AnimeIndexResp> listBySeriesId(Integer sId);

    Boolean updateVideoSort(Long animeId, List<Long> videoSortReqs);

}
