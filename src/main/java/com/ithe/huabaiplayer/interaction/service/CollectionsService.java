package com.ithe.huabaiplayer.interaction.service;

import com.mybatisflex.core.service.IService;
import com.ithe.huabaiplayer.interaction.model.entity.Collections;

/**
 * 收藏夹表 包含每个用户生成收藏夹 服务层。
 *
 * @author L
 * @since 2024-11-30
 */
public interface CollectionsService extends IService<Collections> {

    // 为用户新增默认收藏夹
    void addDefaultCollections(Long userId);
}
