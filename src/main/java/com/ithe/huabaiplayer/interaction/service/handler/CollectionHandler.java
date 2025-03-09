package com.ithe.huabaiplayer.interaction.service.handler;

import com.ithe.huabaiplayer.common.ErrorCode;
import com.ithe.huabaiplayer.common.exception.BusinessException;
import com.ithe.huabaiplayer.file.factory.FileFactory;
import com.ithe.huabaiplayer.file.service.FileStorage;
import com.ithe.huabaiplayer.interaction.model.dto.req.CollectionsAddReq;
import com.ithe.huabaiplayer.interaction.model.dto.req.CollectionsUpdateReq;
import com.ithe.huabaiplayer.interaction.model.dto.resp.CollectionInResp;
import com.ithe.huabaiplayer.interaction.model.entity.CollectionAnimeUser;
import com.ithe.huabaiplayer.interaction.model.entity.Collections;
import com.ithe.huabaiplayer.interaction.service.CollectionAnimeUserService;
import com.ithe.huabaiplayer.interaction.service.CollectionsService;
import com.ithe.huabaiplayer.player.model.entity.AnimeIndex;
import com.ithe.huabaiplayer.player.service.AnimeIndexService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ithe.huabaiplayer.interaction.model.entity.table.CollectionAnimeUserTableDef.COLLECTION_ANIME_USER;
import static com.ithe.huabaiplayer.player.model.entity.table.AnimeIndexTableDef.ANIME_INDEX;

/**
 * @ClassName CollectionHandler
 * @Author hua bai
 * @Date 2025/1/16 16:20
 **/
@Service
@RequiredArgsConstructor
public class CollectionHandler {
    private final CollectionsService collectionsService;
    private final CollectionAnimeUserService collectionAnimeUserService;
    private final AnimeIndexService animeIndexService;
    private final FileFactory fileFactory;

    private FileStorage fileService() {
        return fileFactory.getFileService();
    }

    public Page<Collections> pageCollections(Integer current, Integer pageSize, Long userId) {
        QueryWrapper queryWrapper = collectionsService.query()
                .eq(Collections::getUserId, userId).orderBy(Collections::getSort, true);
        Page<Collections> page = collectionsService.page(new Page<>(current, pageSize), queryWrapper);
        page.getRecords().forEach(r -> {
            String image = r.getImage();
            if (StringUtils.isNotBlank(image)) {
                r.setImage(fileService().getImageUrl(image));
            }
        });
        return page;
    }

    // todo 后面单独定义一个返会对象 因为用不到这么多字段... + 收藏夹id + 收藏时间
    public Page<CollectionInResp> pageCollectionMsg(Long cid, Integer current, Integer pageSize, String key, Long userId) {
        // todo？ 校验是否是本用户的收藏夹 应该不会设计到b站的 收藏夹分享 因为没有社区功能
        QueryWrapper queryWrapper = collectionAnimeUserService.query()
                .select(ANIME_INDEX.ID, ANIME_INDEX.NAME, ANIME_INDEX.IMAGE,
                        ANIME_INDEX.ANOTHER, COLLECTION_ANIME_USER.COLLECTION_ID.as("cid"), COLLECTION_ANIME_USER.CREATE_TIME)
                .from(ANIME_INDEX)
                .leftJoin(COLLECTION_ANIME_USER).on(COLLECTION_ANIME_USER.ANIME_ID.eq(ANIME_INDEX.ID))
                .eq(CollectionAnimeUser::getCollectionId, cid)
                .like(AnimeIndex::getName, key, StringUtils.isNotBlank(key));
        Page<CollectionInResp> respPage = collectionAnimeUserService.pageAs(new Page<>(current, pageSize), queryWrapper, CollectionInResp.class);
        respPage.getRecords().forEach(r -> {
            String image = r.getImage();
            if (StringUtils.isNotBlank(image)) {
                r.setImage(fileService().getImageUrl(image));
            }
        });
        return respPage;
    }

    public List<Long> isCollections(Long userId, Long aid) {
        return collectionAnimeUserService.isCollecteds(userId, aid);
    }

    @Transactional(rollbackFor = Exception.class)
    public Long addCollections(Long cid, Long userId, Long aid) {
        // 1. 判断是否是该用户的文件夹 和 不是默认的话是否超出1000限制
        Collections collections = collectionsService.getById(cid);

        boolean isMy = collections != null && userId.equals(collections.getUserId());
        if (!isMy) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "不是你的收藏夹哦~");
        }
        boolean isMax = collections.getIsDefault().equals(0) && collections.getCount() >= 1000;
        if (isMax) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "收藏夹已满哦~");
        }
        // 2. 判断是否已经被这个收藏夹收藏了
        boolean exists = collectionAnimeUserService.queryChain()
                .eq(CollectionAnimeUser::getUserId, userId)
                .eq(CollectionAnimeUser::getAnimeId, aid)
                .eq(CollectionAnimeUser::getCollectionId, cid).exists();
        if (exists) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "你已经收藏了哦~");
        }
        // 3. 查询动漫
        AnimeIndex animeIndex = animeIndexService.getById(aid);
        if (animeIndex == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "动漫不存在哦~");
        }
        CollectionAnimeUser collectionAnimeUser = CollectionAnimeUser.builder()
                .collectionId(cid)
                .animeId(aid)
                .userId(userId)
                .build();
        collectionAnimeUserService.save(collectionAnimeUser);
        collectionsService.updateChain()
                .eq(Collections::getId, cid)
                .set(Collections::getImage, animeIndex.getImage())
                .setRaw(Collections::getCount, "count+1").update();
        return collectionAnimeUser.getId();
    }


    public Boolean removeCollections(Long aId, Long cId, Long userId) {
        // 查询出最近两条的数据cId + userId
        List<CollectionAnimeUser> collectionAnimeUsers = collectionAnimeUserService.list(new QueryWrapper()
                .eq(CollectionAnimeUser::getUserId, userId)
                .eq(CollectionAnimeUser::getCollectionId, cId)
                .orderBy(CollectionAnimeUser::getId, false)
                .limit(2)
        );
        // 如果collectionAnimeUsers的数据包含了aId，则使用另外一个，否则使用第一个
        CollectionAnimeUser collectionAnimeUser = collectionAnimeUsers.stream()
                .filter(c -> !c.getAnimeId().equals(aId))
                .findFirst()
                .orElse(collectionAnimeUsers.get(0));
        String newImage = null;
        if (collectionAnimeUser != null) {
            AnimeIndex byId = animeIndexService.getById(collectionAnimeUser.getAnimeId());
            if (byId != null) {
                newImage = byId.getImage();
            }
        }
        boolean remove = collectionAnimeUserService.remove(new QueryWrapper()
                .eq(CollectionAnimeUser::getUserId, userId)
                .eq(CollectionAnimeUser::getAnimeId, aId)
                .eq(CollectionAnimeUser::getCollectionId, cId)
        );
        if (remove) {
            // 数量-1
            collectionsService.updateChain()
                    .eq(Collections::getId, cId)
                    .set(Collections::getImage, newImage)
                    .setRaw(Collections::getCount, "count-1").update();
        }
        return remove;
    }

    public Boolean removeCollection(Long cid, Long userId) {
        boolean remove = collectionsService.remove(new QueryWrapper()
                .eq(Collections::getId, cid)
                .eq(Collections::getUserId, userId)
                .eq(Collections::getIsDefault, 0)
        );
        if (remove) {
            // 删除满足条件的 收藏夹用户数据 todo 可以使用 消息队列？
            collectionAnimeUserService.remove(new QueryWrapper()
                    .eq(CollectionAnimeUser::getCollectionId, cid)
            );
        }
        return remove;
    }

    public Long addCollection(CollectionsAddReq collectionsAddReq, Long userId) {
        Integer sort = collectionsAddReq.getSort();
        long count = collectionsService.count(collectionsService.query().eq(Collections::getUserId, userId));
        if (count >= 30) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "不能超过30个收藏夹哦~");
        }
        if (sort == null || sort <= 0) {
            sort = collectionsService.queryChain()
                    .eq(Collections::getUserId, userId)
                    .limit(1)
                    .orderBy(Collections::getSort, false)
                    .one().getSort() + 1;
        }
        Collections collections = Collections.builder()
                .title(collectionsAddReq.getTitle())
                .userId(userId)
                .sort(sort)
                .build();
        collectionsService.save(collections);
        return collections.getId();
    }

    public Boolean updateCollection(CollectionsUpdateReq collectionsUpdateReq, Long userId) {
        return collectionsService.updateChain()
                .eq(Collections::getId, collectionsUpdateReq.getId())
                .eq(Collections::getUserId, userId)
                .set(Collections::getTitle, collectionsUpdateReq.getTitle())
                .update();
    }

    public Boolean sortCollections(List<Long> collectionIds, Long userId) {
        List<Collections> collections = collectionsService.queryChain().eq(Collections::getUserId, userId).list();

        if (collections.size() != collectionIds.size()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "收藏夹数量不一致");
        }

        // 检查默认收藏夹是否在第一个位置
        collections.stream()
                .filter(c -> c.getIsDefault().equals(1))
                .findFirst()
                .ifPresent(c -> {
                    if (!c.getId().equals(collectionIds.get(0))) {
                        throw new BusinessException(ErrorCode.PARAMS_ERROR, "默认收藏夹不能修改顺序！");
                    }
                });

        // 清空并重建收藏夹列表
        collections.clear();
        for (int i = 0; i < collectionIds.size(); i++) {
            collections.add(Collections.builder().id(collectionIds.get(i)).sort(i).build());
        }

        return collectionsService.updateBatch(collections);
    }

}
