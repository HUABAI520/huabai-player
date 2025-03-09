package com.ithe.huabaiplayer.interaction.service.impl;

import com.ithe.huabaiplayer.interaction.mapper.CollectionAnimeUserMapper;
import com.ithe.huabaiplayer.interaction.model.entity.CollectionAnimeUser;
import com.ithe.huabaiplayer.interaction.service.CollectionAnimeUserService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ithe.huabaiplayer.interaction.model.entity.table.CollectionAnimeUserTableDef.COLLECTION_ANIME_USER;

/**
 * 动漫用户收藏关联表 服务层实现。
 *
 * @author L
 * @since 2024-11-30
 */
@Service
public class CollectionAnimeUserServiceImpl extends ServiceImpl<CollectionAnimeUserMapper, CollectionAnimeUser> implements CollectionAnimeUserService {
    // 1. 查询用户是否收藏该动漫
    @Override
    public List<Long> isCollecteds(Long userId, Long animeId) {
        return queryChain().select(COLLECTION_ANIME_USER.COLLECTION_ID)
                .where(COLLECTION_ANIME_USER.USER_ID.eq(userId))
                .and(COLLECTION_ANIME_USER.ANIME_ID.eq(animeId))
                .list()
                .stream()
                .map(CollectionAnimeUser::getCollectionId)
                .toList();
    }

    @Override
    public Boolean isCollected(Long userId, Long animeId) {
        return queryChain().where(COLLECTION_ANIME_USER.USER_ID.eq(userId))
                .and(COLLECTION_ANIME_USER.ANIME_ID.eq(animeId))
                .exists();
    }
    // 2.查询某个收藏夹下的动漫 分页 固定20大小

}
