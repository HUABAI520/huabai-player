package com.ithe.huabaiplayer.interaction.controller;

import com.ithe.huabaiplayer.common.BaseResponse;
import com.ithe.huabaiplayer.common.ResultUtils;
import com.ithe.huabaiplayer.common.annotation.AuthCheck;
import com.ithe.huabaiplayer.common.utils.UserContext;
import com.ithe.huabaiplayer.interaction.service.handler.LikeHandler;
import com.ithe.huabaiplayer.user.model.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName LikesController
 * @Author hua bai
 * @Date 2024/9/29 15:56
 **/
@Slf4j
@RestController
@RequestMapping("/api-player/likes")
@RequiredArgsConstructor
public class LikesController {
    private final LikeHandler likeHandler;

    // todo 现在先直接操作数据库 后面添加redis
    // 点赞
    @PostMapping("/like")
    @AuthCheck
    public BaseResponse<Boolean> like(Long thirdId, Integer type) {
        UserVO user = UserContext.getUser();
        return ResultUtils.success(likeHandler.likeUseCache(thirdId, type, user.getId()));
    }
    // 取消点赞
    @PostMapping("/unlike")
    @AuthCheck
    public BaseResponse<Boolean> unlike(Long thirdId, Integer type) {
        UserVO user = UserContext.getUser();
        return ResultUtils.success(likeHandler.unlikeUseCache(thirdId, type, user.getId()));
    }
}
