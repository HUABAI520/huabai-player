package com.ithe.huabaiplayer.interaction.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.ithe.huabaiplayer.interaction.model.entity.Collections;
import com.ithe.huabaiplayer.interaction.mapper.CollectionsMapper;
import com.ithe.huabaiplayer.interaction.service.CollectionsService;
import org.springframework.stereotype.Service;

/**
 * 收藏夹表 包含每个用户生成收藏夹 服务层实现。
 *
 * @author L
 * @since 2024-11-30
 */
@Service
public class CollectionsServiceImpl extends ServiceImpl<CollectionsMapper, Collections> implements CollectionsService {
    // 为用户新增默认收藏夹
    @Override
    public void addDefaultCollections(Long userId) {
        Collections collections = Collections.builder()
                .title("默认收藏夹")
                .userId(userId)
                .sort(0)
                .count(0)
                .isDefault(1)
                .build();
        save(collections);
    }
}
