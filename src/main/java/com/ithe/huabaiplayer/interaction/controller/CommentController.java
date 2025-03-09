package com.ithe.huabaiplayer.interaction.controller;

import com.ithe.huabaiplayer.common.BaseResponse;
import com.ithe.huabaiplayer.common.ResultUtils;
import com.ithe.huabaiplayer.common.annotation.AuthCheck;
import com.ithe.huabaiplayer.common.utils.UserContext;
import com.ithe.huabaiplayer.interaction.model.dto.req.CommentAddReq;
import com.ithe.huabaiplayer.interaction.model.dto.resp.AddCommentResp;
import com.ithe.huabaiplayer.interaction.model.dto.resp.CommentResp;
import com.ithe.huabaiplayer.interaction.service.CommentService;
import com.ithe.huabaiplayer.user.model.vo.UserVO;
import com.mybatisflex.core.paginate.Page;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @ClassName CommentController
 * @Author hua bai
 * @Date 2024/9/28 23:12
 **/
@Slf4j
@RestController
@RequestMapping("/api-player/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    /**
     * 用户删除对应评论
     */
    @DeleteMapping
    @AuthCheck
    public BaseResponse<Boolean> deleteComment(Long cid) {
        UserVO user = UserContext.getUser();
        return ResultUtils.success(commentService.removeAll(cid, user.getId()));
    }

    @GetMapping
    @AuthCheck
    public BaseResponse<Page<CommentResp>> queryComment(Long videoId, Long oid, Integer pageNumber,
                                                        Integer pageSize, String sortField,
                                                        String sortOrder) {
        if (videoId == null) {
            return null;
        }
        return ResultUtils.success(commentService.queryPage(videoId, oid, pageNumber, pageSize, sortField, sortOrder));
    }

    @PostMapping
    @AuthCheck
    public BaseResponse<AddCommentResp> addComment(@RequestBody @Valid CommentAddReq add) throws IOException {
        UserVO user = UserContext.getUser();
        return ResultUtils.success(commentService.addComment(add, user.getId()));
    }
}
