package com.ithe.huabaiplayer.player.model.dto.series;

import com.ithe.huabaiplayer.player.model.vo.Seriess;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * @ClassName SeriesAddReq
 * @Author hua bai
 * @Date 2024/11/12 20:21
 **/
@Data
public class SeriesAddReq {
    /**
     * 系列名
     */
    @NotNull
    @NotBlank
    @Max(value = 50)
    private String name;
    /**
     * 系列介绍
     */
    private String intro;
    /**
     * 包含动漫id 和 展示标题
     */
    private List<Seriess> animes;
}
