package com.ithe.huabaiplayer.player.model.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
@Table("anime_index")
public class AnimeIndex implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 动漫id索引
     */
    @Id(keyType = KeyType.Auto)
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

    private Integer seriesId;

    private String seasonTitle;


}
