package com.ithe.huabaiplayer.interaction.service;

import com.ithe.huabaiplayer.interaction.model.entity.CollectionAnimeUser;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 动漫用户收藏关联表 服务层。
 *
 * @author L
 * @since 2024-11-30
 */
public interface CollectionAnimeUserService extends IService<CollectionAnimeUser> {

    // 1. 查询用户是否收藏该动漫 并获得所有收藏的收藏夹id
    List<Long> isCollecteds(Long userId, Long animeId);
    // 2. 单纯查询是否收藏
    Boolean isCollected(Long userId, Long animeId);
}
