package com.ithe.huabaiplayer.player.model.dto.anime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ithe.huabaiplayer.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @ClassName AnimeQueryReq
 * @Author hua bai
 * @Date 2024/8/28 17:19
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class AnimeQueryReq extends PageRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 动漫名
     */
    private String name;
    /**
     * 简介
     */
    private String intro;
    /**
     * 几月番： 1 4 7 10
     */
    private Integer month;
    /**
     * 是否是新番（未完结是：1 完结不是 0）
     */
    private Integer isNew;
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
     * 类型 0-剧场版 1-TV
     */
    private Integer type;
    /**
     * 动漫类别
     */
    private String kind;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endTime;


}
