package com.ithe.huabaiplayer.features.model.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 *  实体类。
 *
 * @author L
 * @since 2025-03-25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("user_features")
public class UserFeatures implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Long userId;

    private Long activeDays;

    private Double avgDailyActivities;

    private Double totalWatchDuration;

    private Double commentCount;

    private Double commentsPerHour;

    private Double collectionCount;

    private Double likeCount;

    private Integer effectivePlayCount;

    private String topType;

    @Column("top_10_types")
    private String top10Types;

    private String activePeriod;

    private Double avgSentiment;

    private String sentimentLabel;

}
