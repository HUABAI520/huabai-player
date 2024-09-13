package com.ithe.huabaiplayer.player.model.vo;

import cn.hutool.json.JSONUtil;
import com.ithe.huabaiplayer.player.model.dto.anime.AnimeIndexResp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
public class AnimeIndexVo implements Serializable {

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
     * 简介
     */
    private String intro;
    /**
     * 发行时间
     */
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
    private String actRole;
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
    private String kinds;
    private String kind;

    /**
     * 图片
     */
    private String image;
    /**
     * 创建时间（收集时间）
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    private Long lookVideoId;
    private Long seek;

    public static AnimeIndexResp of(AnimeIndexVo animeIndexVo) {
        return AnimeIndexResp.builder()
                .id(animeIndexVo.getId())
                .name(animeIndexVo.getName())
                .intro(animeIndexVo.getIntro())
                .issueTime(animeIndexVo.getIssueTime())
                .month(animeIndexVo.getMonth())
                .isNew(animeIndexVo.getIsNew())
                .status(animeIndexVo.getStatus())
                .actRole(JSONUtil.toList(animeIndexVo.getActRole(), String.class))
                .director(animeIndexVo.getDirector())
                .language(animeIndexVo.getLanguage())
                .score(animeIndexVo.getScore())
                .number(animeIndexVo.getNumber())
                .type(animeIndexVo.getType())
                .folder(animeIndexVo.getFolder())
                .kind(Objects.nonNull(animeIndexVo.getKinds())
                        ? getKinds(animeIndexVo) : Collections.singletonList(animeIndexVo.getKind()))
                .image(animeIndexVo.getImage())
                .createTime(animeIndexVo.getCreateTime())
                .updateTime(animeIndexVo.getUpdateTime())
                .lookVideoId(animeIndexVo.getLookVideoId())
                .seek(animeIndexVo.getSeek())
                .build();
    }

    private static List<String> getKinds(AnimeIndexVo animeIndexVo) {
        String kinds1 = animeIndexVo.getKinds();
        // 纯字符串 通过逗号分割字符串为List
        String[] split = kinds1.split(",");
        return Arrays.stream(split).toList();
    }
}
