package com.ithe.huabaiplayer.player.model.dto.series;

import com.ithe.huabaiplayer.player.model.entity.Series;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @ClassName SeriesAddReq
 * @Author hua bai
 * @Date 2024/11/12 20:21
 **/
@Data
public class SeriesUpReq {
    @NotNull
    private Integer id;
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

    public static Series to(SeriesUpReq req) {
        return Series.builder()
                .id(req.getId())
                .name(req.getName())
                .intro(req.getIntro())
                .build();
    }
}
