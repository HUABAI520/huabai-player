package com.ithe.huabaiplayer.features.controller;

import com.ithe.huabaiplayer.common.BaseResponse;
import com.ithe.huabaiplayer.common.ResultUtils;
import com.ithe.huabaiplayer.common.annotation.AuthCheck;
import com.ithe.huabaiplayer.common.utils.UserContext;
import com.ithe.huabaiplayer.features.model.entity.DailyUserFeatureCount;
import com.ithe.huabaiplayer.features.model.entity.DailyUserFeatures;
import com.ithe.huabaiplayer.features.model.entity.MonthlyUserFeatures;
import com.ithe.huabaiplayer.features.model.entity.UserFeatures;
import com.ithe.huabaiplayer.features.model.entity.WeeklyUserFeatures;
import com.ithe.huabaiplayer.features.service.handler.FeaturesHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

/**
 * @ClassName FeaturesController
 * @Author hua bai
 * @Date 2025/3/25 21:47
 **/
@RestController
@RequestMapping("/api-player/features")
@RequiredArgsConstructor
public class FeaturesController {
    private final FeaturesHandler featuresHandler;

    // 查询该用户全部数据分析的特征
    @GetMapping("/getAll")
    @AuthCheck
    public BaseResponse<UserFeatures> getAllFeatures() {
        Long userId = UserContext.getUser().getId();
        return ResultUtils.success(featuresHandler.getAllByUserId(userId));
    }

    // 传递某周的周一的日期
    @GetMapping("/getWeekly")
    @AuthCheck
    public BaseResponse<WeeklyUserFeatures> getWeeklyFeatures(Date weekStartDate) {
        Long userId = UserContext.getUser().getId();
        return ResultUtils.success(featuresHandler.getWeeklyByUserId(weekStartDate, userId));
    }

    // 传递该月1日的日期
    @GetMapping("/getMonthly")
    @AuthCheck
    public BaseResponse<MonthlyUserFeatures> getMonthlyFeatures(Date monthStartDate) {
        Long userId = UserContext.getUser().getId();
        return ResultUtils.success(featuresHandler.getMonthlyByUserId(monthStartDate, userId));
    }

    // 传递时间区间 不能大于等于本周的周一日期
    @GetMapping("/getBetween")
    @AuthCheck
    public BaseResponse<List<DailyUserFeatures>> getBetweenFeatures(Date startDate, Date endDate) {
        Long userId = UserContext.getUser().getId();
        return ResultUtils.success(featuresHandler.getBetweenFeatures(startDate, endDate, userId));
    }

    // 传递时间区间 只获取该区间之内的每天的活动次数
    @GetMapping("/getBetweenCount")
    @AuthCheck
    public BaseResponse<List<DailyUserFeatureCount>> getBetweenCount(Date startDate, Date endDate) {
        Long userId = UserContext.getUser().getId();
        return ResultUtils.success(featuresHandler.getBetweenCount(startDate, endDate, userId));
    }
}
