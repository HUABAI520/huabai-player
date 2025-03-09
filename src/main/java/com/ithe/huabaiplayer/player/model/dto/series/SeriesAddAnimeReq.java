package com.ithe.huabaiplayer.player.model.dto.series;

import com.ithe.huabaiplayer.player.model.vo.Seriess;
import lombok.Data;

import java.util.List;

/**
 * @ClassName SeriesAddReq
 * @Author hua bai
 * @Date 2024/11/12 20:21
 **/
@Data
public class SeriesAddAnimeReq {
    private Integer id;
    /**
     * 包含动漫id 和 展示标题
     */
    private List<Seriess> animes;
}
