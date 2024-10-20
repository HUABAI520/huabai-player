package com.ithe.huabaiplayer.interaction.service;

import com.ithe.huabaiplayer.interaction.model.dto.req.RatingAddReq;
import com.ithe.huabaiplayer.interaction.model.dto.req.RatingUpdateReq;
import com.ithe.huabaiplayer.interaction.model.entity.UserRating;
import com.mybatisflex.core.service.IService;

/**
 * 用户评分表 服务层。
 *
 * @author L
 * @since 2024-09-24
 */
public interface UserRatingService extends IService<UserRating> {

    Long addRating(RatingAddReq req, Long id);

    Long updateRating(RatingUpdateReq req, Long id);
}
