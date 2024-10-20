package com.ithe.huabaiplayer.interaction.controller;

import com.ithe.huabaiplayer.common.BaseResponse;
import com.ithe.huabaiplayer.common.ResultUtils;
import com.ithe.huabaiplayer.common.annotation.AuthCheck;
import com.ithe.huabaiplayer.common.utils.UserContext;
import com.ithe.huabaiplayer.interaction.model.dto.req.RatingAddReq;
import com.ithe.huabaiplayer.interaction.model.dto.req.RatingUpdateReq;
import com.ithe.huabaiplayer.interaction.model.entity.UserRating;
import com.ithe.huabaiplayer.interaction.service.UserRatingService;
import com.ithe.huabaiplayer.user.model.vo.UserVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName RatingController
 * @Author hua bai
 * @Date 2024/9/24 21:47
 **/
@Slf4j
@RestController
@RequestMapping("/api-player/rating")
@RequiredArgsConstructor
public class RatingController {
    private final UserRatingService ratingService;

    // 新增评分
    @PostMapping
    @AuthCheck
    public BaseResponse<Long> addRating(@RequestBody @Valid RatingAddReq req) {
        UserVO user = UserContext.getUser();
        return ResultUtils.success(ratingService.addRating(req, user.getId()));
    }

    // 更新评分
    @PutMapping
    @AuthCheck
    public BaseResponse<Long> updateRating(@RequestBody @Valid RatingUpdateReq req) {
        UserVO user = UserContext.getUser();
        return ResultUtils.success(ratingService.updateRating(req, user.getId()));
    }

    @GetMapping
    @AuthCheck
    public BaseResponse<UserRating> getRating(Long id) {
        UserVO user = UserContext.getUser();
        return ResultUtils.success(
                ratingService.queryChain()
                        .eq(UserRating::getAnimeId, id)
                        .eq(UserRating::getUserId, user.getId()).one()
        );
    }
}
