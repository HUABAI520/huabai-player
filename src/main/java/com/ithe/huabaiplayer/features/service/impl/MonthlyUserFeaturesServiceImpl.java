package com.ithe.huabaiplayer.features.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.ithe.huabaiplayer.features.model.entity.MonthlyUserFeatures;
import com.ithe.huabaiplayer.features.mapper.MonthlyUserFeaturesMapper;
import com.ithe.huabaiplayer.features.service.MonthlyUserFeaturesService;
import org.springframework.stereotype.Service;

/**
 * 每月用户特征数据 服务层实现。
 *
 * @author L
 * @since 2025-03-25
 */
@Service
public class MonthlyUserFeaturesServiceImpl extends ServiceImpl<MonthlyUserFeaturesMapper, MonthlyUserFeatures> implements MonthlyUserFeaturesService {

}
