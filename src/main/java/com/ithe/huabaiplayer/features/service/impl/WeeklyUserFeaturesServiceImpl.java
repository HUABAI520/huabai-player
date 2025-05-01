package com.ithe.huabaiplayer.features.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.ithe.huabaiplayer.features.model.entity.WeeklyUserFeatures;
import com.ithe.huabaiplayer.features.mapper.WeeklyUserFeaturesMapper;
import com.ithe.huabaiplayer.features.service.WeeklyUserFeaturesService;
import org.springframework.stereotype.Service;

/**
 * 每周用户特征数据 服务层实现。
 *
 * @author L
 * @since 2025-03-25
 */
@Service
public class WeeklyUserFeaturesServiceImpl extends ServiceImpl<WeeklyUserFeaturesMapper, WeeklyUserFeatures> implements WeeklyUserFeaturesService {

}
