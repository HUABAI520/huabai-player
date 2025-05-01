package com.ithe.huabaiplayer.features.service.impl;

import com.ithe.huabaiplayer.features.model.entity.UserFeatures;
import com.ithe.huabaiplayer.features.mapper.UserFeaturesMapper;
import com.ithe.huabaiplayer.features.service.UserFeaturesService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserFeaturesServiceImpl extends ServiceImpl<UserFeaturesMapper, UserFeatures> implements UserFeaturesService {

}
