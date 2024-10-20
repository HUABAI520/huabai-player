package com.ithe.huabaiplayer.interaction.service.handler;

import com.ithe.huabaiplayer.interaction.model.entity.LikeCounts;
import com.ithe.huabaiplayer.interaction.model.entity.Likes;
import com.ithe.huabaiplayer.interaction.model.enums.LikeTypeEnum;
import com.ithe.huabaiplayer.interaction.service.LikeCountsService;
import com.ithe.huabaiplayer.interaction.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName LikeHandler
 * @Author hua bai
 * @Date 2024/9/29 16:20
 **/
@Service
@RequiredArgsConstructor
public class LikeHandler {
    private final LikeCountsService likeCountsService;
    private final LikesService likesService;
    private final LikeService likeService;

    @Transactional(rollbackFor = Exception.class)
    public Boolean like(Long thirdId, Integer type, Long userId) {
        LikeTypeEnum typeEnum = LikeTypeEnum.of(type);
        if (typeEnum == null) {
            return false;
        }
        try {
            likesService.save(Likes.builder().userId(userId).thirdId(thirdId).thirdType(typeEnum.getType()).build());
        } catch (Exception e) {
            return false;
        }
        boolean b = likeCountsService.updateChain()
                .eq(LikeCounts::getThirdId, thirdId)
                .eq(LikeCounts::getThirdType, typeEnum.getType())
                .setRaw(LikeCounts::getCount, "count+1").update();
        if (!b) {
            likeCountsService.save(LikeCounts.builder().thirdId(thirdId).thirdType(typeEnum.getType()).count(1).build());
        }
        return true;
    }

    public Boolean likeUseCache(Long thirdId, Integer type, Long userId) {
        LikeTypeEnum typeEnum = LikeTypeEnum.of(type);
        return likeService.like(userId, typeEnum, thirdId);
    }

    public Boolean unlikeUseCache(Long thirdId, Integer type, Long id) {
        LikeTypeEnum typeEnum = LikeTypeEnum.of(type);
        return likeService.unlike(id, typeEnum, thirdId);
    }

    public Boolean unlike(Long thirdId, Integer type, Long id) {
        LikeTypeEnum typeEnum = LikeTypeEnum.of(type);
        if (typeEnum == null) {
            return false;
        }
        boolean remove = likesService.remove(likesService.query()
                .eq(Likes::getThirdId, thirdId)
                .eq(Likes::getThirdType, typeEnum.getType())
                .eq(Likes::getUserId, id));
        if (!remove) {
            return false;
        }
        return likeCountsService.updateChain()
                .eq(LikeCounts::getThirdId, thirdId)
                .eq(LikeCounts::getThirdType, typeEnum.getType())
                .setRaw(LikeCounts::getCount, "count-1").update();
    }
}
