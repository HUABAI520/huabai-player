package com.ithe.huabaiplayer.interaction.service.impl;

import com.ithe.huabaiplayer.common.ErrorCode;
import com.ithe.huabaiplayer.common.exception.BusinessException;
import com.ithe.huabaiplayer.player.model.entity.AnimeIndex;
import com.ithe.huabaiplayer.player.service.AnimeIndexService;
import com.ithe.huabaiplayer.interaction.mapper.UserRatingMapper;
import com.ithe.huabaiplayer.interaction.model.dto.req.RatingAddReq;
import com.ithe.huabaiplayer.interaction.model.dto.req.RatingUpdateReq;
import com.ithe.huabaiplayer.interaction.model.entity.UserRating;
import com.ithe.huabaiplayer.interaction.service.UserRatingService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 用户评分表 服务层实现。
 *
 * @author L
 * @since 2024-09-24
 */
@Service
@RequiredArgsConstructor
public class UserRatingServiceImpl extends ServiceImpl<UserRatingMapper, UserRating> implements UserRatingService {

    private final AnimeIndexService animeIndexService;

    @Override
    public Long addRating(RatingAddReq req, Long id) {
        Long animeId = req.getAnimeId();
        BigDecimal score = req.getScore();
        String comment = req.getComment();
        boolean exists = animeIndexService.exists(query().eq(AnimeIndex::getId, animeId));
        if (!exists) {
            return -1L;
        }
        boolean existsed = this.queryChain()
                .eq(UserRating::getAnimeId, animeId)
                .eq(UserRating::getUserId, id).exists();
        if (existsed) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "已评分!");
        }
        UserRating userRating = UserRating.builder()
                .userId(id)
                .animeId(animeId)
                .score(score)
                .comment(comment)
                .build();
        save(userRating);
        return userRating.getId();
    }

    @Override
    public Long updateRating(RatingUpdateReq req, Long userId) {
        Long id = req.getId();
        Long animeId = req.getAnimeId();
        BigDecimal score = req.getScore();
        BigDecimal oldScore = req.getOldScore();
        String comment = req.getComment();
        UserRating userRating = UserRating.builder()
                .oldScore(oldScore)
                .score(score)
                .comment(comment)
                .build();
        this.update(userRating, query()
                .eq(UserRating::getUserId, userId)
                .eq(UserRating::getId, id)
                .eq(UserRating::getAnimeId, animeId));
        return id;
    }
}
