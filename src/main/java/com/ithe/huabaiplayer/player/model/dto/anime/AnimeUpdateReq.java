package com.ithe.huabaiplayer.player.model.dto.anime;


import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ithe.huabaiplayer.player.model.entity.AnimeIndex;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @ClassName AnimeAddReq
 * @Author he long
 * @Date 2024/8/28 17:17
 **/
@Data
public class AnimeUpdateReq implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;
    /**
     * 动漫名
     */
    @NotBlank
    private String name;
    /**
     * 简介
     */
    private String intro;
    /**
     * 发行时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date issueTime;

    /**
     * 几月番： 1 4 7 10
     */
    private Integer month;

    /**
     * 是否是新番（未完结是：1 完结不是 0）
     */
    @NotNull
    private Integer isNew;

    /**
     * isNew = 1 时 的状态
     */
    private String status;
    /**
     * 主演
     */
    private List<String> actRole;
    /**
     * 导演
     */
    private String director;
    /**
     * 语言
     */
    private String language;
    /**
     * 类型 0-剧场版 1-TV
     */
    @NotNull
    private Integer type;


    private List<Integer> kindIds;

    public static AnimeIndex toAnimeIndex(AnimeUpdateReq animeUpdateReq) {
        String roles = JSONUtil.toJsonStr(animeUpdateReq.getActRole());
        return AnimeIndex.builder()
                .id(animeUpdateReq.getId())
                .name(animeUpdateReq.getName())
                .intro(animeUpdateReq.getIntro())
                .issueTime(animeUpdateReq.getIssueTime())
                .month(animeUpdateReq.getMonth())
                .isNew(animeUpdateReq.getIsNew())
                .status(animeUpdateReq.getStatus())
                .actRole(roles)
                .director(animeUpdateReq.getDirector())
                .language(animeUpdateReq.getLanguage())
                .type(animeUpdateReq.getType())
                .build();
    }
}
