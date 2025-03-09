package com.ithe.huabaiplayer.player.model.dto.anime;


import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ithe.huabaiplayer.player.model.entity.AnimeIndex;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @ClassName AnimeAddReq
 * @Author he long
 * @Date 2024/8/28 17:17
 **/
@Data
public class AnimeAddReq implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 动漫名
     */
    @NotBlank
    @NotNull
    private String name;
    /**
     * 别称
     */
    private String another;
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
    /**
     * 可以指定创建到对应文件路径下 默认在主路径
     */
    private Long fileId;

    private List<Integer> kindIds;

    /**
     * 评分 一位小数
     */
    private BigDecimal score;

    /**
     * 评分次数
     */
    private Integer number;

    public static AnimeIndex toAnimeIndex(AnimeAddReq animeAddReq) {
        String roles = JSONUtil.toJsonStr(animeAddReq.getActRole());
        return AnimeIndex.builder()
                .name(animeAddReq.getName())
                .another(animeAddReq.getAnother())
                .intro(animeAddReq.getIntro())
                .issueTime(animeAddReq.getIssueTime())
                .month(animeAddReq.getMonth())
                .isNew(animeAddReq.getIsNew())
                .status(animeAddReq.getStatus())
                .actRole(roles)
                .director(animeAddReq.getDirector())
                .language(animeAddReq.getLanguage())
                .type(animeAddReq.getType())
                .score(animeAddReq.getScore())
                .number(animeAddReq.getNumber())
                .build();
    }
}
