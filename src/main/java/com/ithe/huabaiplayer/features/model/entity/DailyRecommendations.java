package com.ithe.huabaiplayer.features.model.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.sql.Date;

import java.io.Serial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 每日推荐结果表 实体类。
 *
 * @author L
 * @since 2025-03-25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("daily_recommendations")
public class DailyRecommendations implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Id
    private Long userId;

    /**
     * 动漫ID
     */
    @Id
    private Integer animeId;

    /**
     * 动漫标题
     */
    private String title;

    /**
     * 推荐日期
     */
    @Id
    private Date recommendDate;

}
