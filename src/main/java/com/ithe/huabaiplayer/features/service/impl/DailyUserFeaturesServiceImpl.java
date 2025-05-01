package com.ithe.huabaiplayer.features.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.ithe.huabaiplayer.features.model.entity.DailyUserFeatures;
import com.ithe.huabaiplayer.features.mapper.DailyUserFeaturesMapper;
import com.ithe.huabaiplayer.features.service.DailyUserFeaturesService;
import org.springframework.stereotype.Service;

/**
 * 每日用户特征数据 服务层实现。
 *
 * @author L
 * @since 2025-03-25
 */
@Service
public class DailyUserFeaturesServiceImpl extends ServiceImpl<DailyUserFeaturesMapper, DailyUserFeatures> implements DailyUserFeaturesService {

}
