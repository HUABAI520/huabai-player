package com.ithe.huabaiplayer.player.model.dto.series;

import com.ithe.huabaiplayer.player.model.dto.anime.AnimeIndexResp;
import com.ithe.huabaiplayer.player.model.entity.Series;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName Seriess
 * @Author hua bai
 * @Date 2024/11/12 20:00
 * 系列中含有的信息
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeriesResp {
    private Integer id;

    /**
     * 系列名
     */
    private String name;

    /**
     * 系列介绍
     */
    private String intro;

    private String image;


    private List<AnimeIndexResp> animeList;

    public static SeriesResp of(Series series) {
        return SeriesResp.builder()
                .id(series.getId())
                .name(series.getName())
                .intro(series.getIntro())
                .build();
    }
}
