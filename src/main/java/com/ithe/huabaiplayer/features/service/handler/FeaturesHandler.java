package com.ithe.huabaiplayer.features.service.handler;

import com.ithe.huabaiplayer.common.ErrorCode;
import com.ithe.huabaiplayer.common.exception.BusinessException;
import com.ithe.huabaiplayer.features.model.entity.DailyUserFeatureCount;
import com.ithe.huabaiplayer.features.model.entity.DailyUserFeatures;
import com.ithe.huabaiplayer.features.model.entity.MonthlyUserFeatures;
import com.ithe.huabaiplayer.features.model.entity.UserFeatures;
import com.ithe.huabaiplayer.features.model.entity.WeeklyUserFeatures;
import com.ithe.huabaiplayer.features.service.DailyUserFeaturesService;
import com.ithe.huabaiplayer.features.service.MonthlyUserFeaturesService;
import com.ithe.huabaiplayer.features.service.UserFeaturesService;
import com.ithe.huabaiplayer.features.service.WeeklyUserFeaturesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import static com.ithe.huabaiplayer.features.model.entity.table.DailyUserFeaturesTableDef.DAILY_USER_FEATURES;

/**
 * @ClassName FeaturesHandler
 * @Author hua bai
 * @Date 2025/3/25 21:49
 **/
@Service
@RequiredArgsConstructor
public class FeaturesHandler {
    private final UserFeaturesService userFeaturesService;
    private final MonthlyUserFeaturesService monthlyUserFeaturesService;
    private final WeeklyUserFeaturesService weeklyUserFeaturesService;
    private final DailyUserFeaturesService dailyUserFeaturesService;

    public UserFeatures getAllByUserId(Long userId) {
        return userFeaturesService.getById(userId);
    }

    public WeeklyUserFeatures getWeeklyByUserId(Date weekStartDate, Long userId) {
        // 检查该周一时间是否大于现在时间 或者 是这周的周一
        // 获取当前时间的周一日期
        Date mondayDate = getThisWeekMonday();
        if (weekStartDate.after(mondayDate)) {
            return null;
        }
        return weeklyUserFeaturesService.getOneByEntityId(WeeklyUserFeatures.builder()
                .weekStartDate(weekStartDate)
                .userId(userId).build());
    }

    public MonthlyUserFeatures getMonthlyByUserId(Date monthStartDate, Long userId) {
        // 获得monthStartDate 年 和 月
        LocalDate localDate = monthStartDate.toLocalDate();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        // 获得现在时间的 年 和 月
        LocalDate now = LocalDate.now();
        int nowYear = Date.valueOf(now).toLocalDate().getYear();
        int nowMonth = Date.valueOf(now).toLocalDate().getMonthValue();
        if (year > nowYear) {
            return null;
        }
        if (year == nowYear && month >= nowMonth) {
            return null;
        }
        return monthlyUserFeaturesService.getOneByEntityId(MonthlyUserFeatures.builder()
                .monthStartDate(monthStartDate)
                .userId(userId).build());
    }

    public List<DailyUserFeatures> getBetweenFeatures(Date startDate, Date endDate, Long userId) {
        if (dateVerify(startDate, endDate)) {
            return null;
        }
        return dailyUserFeaturesService.queryChain()
                .between(DailyUserFeatures::getActivityDate, startDate, endDate)
                .eq(DailyUserFeatures::getUserId, userId)
                .list();
    }

    private static boolean dateVerify(Date startDate, Date endDate) {
        // 获取当前时间的周一日期
        Date mondayDate = getThisWeekMonday();
        // 提取年、月、日
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mondayDate);
        int nowYear = calendar.get(Calendar.YEAR);
        // Calendar.MONTH 从 0 开始，所以加 1
        int nowMonth = calendar.get(Calendar.MONTH) + 1;
        int nowDay = calendar.get(Calendar.DAY_OF_MONTH);
        if (startDate.after(endDate)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "开始时间不能大于结束时间");
        }
        int year = endDate.toLocalDate().getYear();
        int month = endDate.toLocalDate().getMonthValue();
        int day = endDate.toLocalDate().getDayOfMonth();
        if (year > nowYear) {
            return true;
        }
        if (year == nowYear && month > nowMonth) {
            return true;
        }
        if (year == nowYear && month == nowMonth && day >= nowDay) {
            return true;
        }
        return false;
    }

    public static Date getThisWeekMonday() {
        Calendar calendar = Calendar.getInstance();
        // 设置为本周的周一
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        // 设置时间为当天的 00:00:00
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return new Date(calendar.getTimeInMillis());
    }

    public List<DailyUserFeatureCount> getBetweenCount(Date startDate, Date endDate, Long userId) {
        if (dateVerify(startDate, endDate)) {
            return null;
        }
        return dailyUserFeaturesService.queryChain()
                .select(DAILY_USER_FEATURES.AVG_DAILY_ACTIVITIES, DAILY_USER_FEATURES.ACTIVITY_DATE)
                .between(DailyUserFeatures::getActivityDate, startDate, endDate)
                .eq(DailyUserFeatures::getUserId, userId)
                .list().stream().map((i)-> DailyUserFeatureCount.builder()
                        .activityDate(i.getActivityDate())
                        .avgDailyActivities(i.getAvgDailyActivities())
                        .build()).toList();
    }
}
