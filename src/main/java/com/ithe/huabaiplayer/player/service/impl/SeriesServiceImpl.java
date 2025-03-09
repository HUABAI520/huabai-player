package com.ithe.huabaiplayer.player.service.impl;

import com.ithe.huabaiplayer.common.ErrorCode;
import com.ithe.huabaiplayer.common.exception.BusinessException;
import com.ithe.huabaiplayer.common.utils.BeanHuaUtil;
import com.ithe.huabaiplayer.file.factory.FileFactory;
import com.ithe.huabaiplayer.file.service.FileStorage;
import com.ithe.huabaiplayer.player.mapper.SeriesMapper;
import com.ithe.huabaiplayer.player.model.dto.anime.AnimeIndexResp;
import com.ithe.huabaiplayer.player.model.dto.series.SeriesAddAnimeReq;
import com.ithe.huabaiplayer.player.model.dto.series.SeriesAddReq;
import com.ithe.huabaiplayer.player.model.dto.series.SeriesResp;
import com.ithe.huabaiplayer.player.model.dto.series.SeriesUpReq;
import com.ithe.huabaiplayer.player.model.entity.AnimeIndex;
import com.ithe.huabaiplayer.player.model.entity.Series;
import com.ithe.huabaiplayer.player.model.vo.Seriess;
import com.ithe.huabaiplayer.player.service.AnimeIndexService;
import com.ithe.huabaiplayer.player.service.SeriesService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 服务层实现。
 *
 * @author L
 * @since 2024-11-12
 */
@Service
@RequiredArgsConstructor
public class SeriesServiceImpl extends ServiceImpl<SeriesMapper, Series> implements SeriesService {
    private final AnimeIndexService animeIndexService;
    private final SeriesMapper seriesMapper;
    @Autowired
    private FileFactory fileFactory;

    private FileStorage fileService() {
        return fileFactory.getFileService();
    }

    @Override
    public SeriesResp get(Integer seriesId) {
        Series byId = this.getById(seriesId);
        if (byId == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "该系列不存在~");
        }
        SeriesResp seriesResp = SeriesResp.of(byId);
        List<AnimeIndexResp> list = animeIndexService.listBySeriesId(seriesId);
        seriesResp.setAnimeList(list);
        return seriesResp;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addSeries(SeriesAddReq req) {
        String name = req.getName();
        String intro = req.getIntro();
        Series series = Series.builder().intro(intro).name(name).build();
        boolean exists = this.exists(query().eq(Series::getName, name));
        if (exists) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "该系列名已存在~");
        }
        this.save(series);
        List<Seriess> animes = req.getAnimes();
        updateAnimeSeries(animes, series);
        return series.getId();
    }

    @Override
    public Boolean addAnime(SeriesAddAnimeReq req) {
        Integer id = req.getId();
        Series byId = this.getById(id);
        if (byId == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "该系列不存在~");
        }
        List<Seriess> animes = req.getAnimes();
        updateAnimeSeries(animes, byId);
        return true;
    }

    @Override
    public Boolean removeAnimeSeriesId(List<Long> animeId) {
        return animeIndexService.updateChain()
                .in(AnimeIndex::getId, animeId)
                .set(AnimeIndex::getSeriesId, null)
                .set(AnimeIndex::getSeasonTitle, null).update();
    }

    @Override
    public Boolean remove(Integer seriesId) {
        boolean b = this.removeById(seriesId);
        if (!b) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "该系列不存在~");
        }
        return animeIndexService.updateChain()
                .eq(AnimeIndex::getSeriesId, seriesId)
                .set(AnimeIndex::getSeriesId, null)
                .set(AnimeIndex::getSeasonTitle, null).update();
    }

    @Override
    public Page<SeriesResp> pageQuery(Integer pageNum, Integer pageSize, String name) {
        QueryWrapper queryWrapper = query().eq(Series::getName, name, StringUtils.isNotBlank(name));
        Page<Series> page = page(Page.of(pageNum, pageSize), queryWrapper);
        Page<SeriesResp> page1 = new Page<>();
        BeanHuaUtil.copyIgnoreFields(page, page1, List.of("records"));
        page1.setRecords(page.getRecords().stream().map(SeriesResp::of).toList());
        return page1;
    }

    @Override
    public Boolean updateById(SeriesUpReq up) {
        Series series = SeriesUpReq.to(up);
        return this.updateById(series);
    }

    private void updateAnimeSeries(List<Seriess> animes, Series series) {
        if (animes != null && !animes.isEmpty()) {
            List<AnimeIndex> list = animes.stream()
                    .map((anime) ->
                            AnimeIndex.builder()
                                    .id(anime.getId())
                                    .seriesId(series.getId())
                                    .seasonTitle(anime.getSeasonTitle())
                                    .build()).toList();
            animeIndexService.updateBatch(list);
        }
    }

    @Override
    public Page<SeriesResp> page(Integer pageNum, Integer pageSize, String keyword) {
        QueryWrapper queryWrapper = query().like(Series::getName, keyword, StringUtils.isNotBlank(keyword))
                .or(q -> {
                    q.like(AnimeIndex::getName, keyword, StringUtils.isNotBlank(keyword));
                }).or(q -> {
                    q.like(AnimeIndex::getAnother, keyword, StringUtils.isNotBlank(keyword));
                });
        Page<SeriesResp> getSeries = seriesMapper.xmlPaginate("getSeries", Page.of(pageNum, pageSize), queryWrapper);
        if (getSeries.getRecords().isEmpty()) {
            return getSeries;
        }
        getSeries.getRecords().forEach((seriesResp) -> {
            String image = seriesResp.getImage();
            if (StringUtils.isNotBlank(image)) {
                seriesResp.setImage(fileService().getImageUrl(image));
            }
        });
        return getSeries;
    }

}
