package com.ithe.huabaiplayer.features.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * @ClassName DailyUserFeatureCount
 * @Author hua bai
 * @Date 2025/3/29 21:35
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyUserFeatureCount {
    private Date activityDate;
    private Float avgDailyActivities;
}
