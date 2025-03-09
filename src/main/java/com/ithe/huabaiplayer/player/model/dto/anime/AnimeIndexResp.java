package com.ithe.huabaiplayer.player.model.dto.anime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ithe.huabaiplayer.player.model.dto.animeVideos.AnimeVideosResp;
import com.ithe.huabaiplayer.player.model.vo.Seriess;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 动漫表 实体类。
 *
 * @author L
 * @since 2024-08-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnimeIndexResp implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;
    /**
     * 动漫名
     */
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
     * 评分 一位小数
     */
    private BigDecimal score;
    /**
     * 评分次数
     */
    private Integer number;
    /**
     * 类型 0-剧场版 1-TV
     */
    private Integer type;
    /**
     * 该动漫的文件夹id
     */
    private Long folder;
    /**
     * 动漫类别
     */
    private List<String> kind;
    /**
     * 包含的视频
     */
    private List<AnimeVideosResp> videos;
    /**
     * 图片
     */
    private String image;
    /**
     * 创建时间（收集时间）
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    /**
     * 该动漫对应用户id的记录
     */
    private Long lookVideoId;
    private Long seek;
    /**
     * 用户对这个动漫的评分
     */
    private BigDecimal userScore;
    private Long ratingId;
    /**
     * 该动漫的播放量
     */
    private Long playCount;

    private List<Seriess> series;
    private String seasonTitle;

    private Integer seriesId;

    private Integer isCollect;
}
