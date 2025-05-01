package com.ithe.huabaiplayer.features.model.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;

/**
 * 每周用户特征数据 实体类。
 *
 * @author L
 * @since 2025-03-25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("weekly_user_features")
public class WeeklyUserFeatures implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 开始日期（周一）
     */
    @Id
    private Date weekStartDate;

    /**
     * 用户ID
     */
    @Id
    private Long userId;

    /**
     * 总观看时长（分钟）
     */
    private Float totalWatchDuration;

    /**
     * 评论数量
     */
    private Integer commentCount;

    /**
     * 每小时平均评论数
     */
    private Float commentsPerHour;

    /**
     * 收藏数量
     */
    private Integer collectionCount;

    /**
     * 点赞数量
     */
    private Integer likeCount;

    /**
     * 有效播放次数
     */
    private Integer effectivePlayCount;

    /**
     * 活跃天数
     */
    private Integer activeDays;

    /**
     * 平均每日活动数
     */
    private Float avgDailyActivities;

    /**
     * 最喜欢的动漫类型
     */
    private String topType;

    /**
     * 主要活跃时段
     */
    private String activePeriod;

    /**
     * 情感标签（积极/消极/中性）
     */
    private String sentimentLabel;

    /**
     * 平均情感得分（0-1）
     */
    private Double avgSentiment;

}
