package com.ithe.huabaiplayer.interaction.service;

import com.ithe.huabaiplayer.interaction.model.dto.req.CommentAddReq;
import com.ithe.huabaiplayer.interaction.model.dto.resp.AddCommentResp;
import com.ithe.huabaiplayer.interaction.model.dto.resp.CommentResp;
import com.ithe.huabaiplayer.interaction.model.entity.Comment;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;

import java.io.IOException;

/**
 * 评论表 服务层。
 *
 * @author L
 * @since 2024-09-28
 */
public interface CommentService extends IService<Comment> {

    Page<CommentResp> queryPage(Long videoId, Long oid, Integer pageNumber, Integer pageSize,
                                String sortField, String sortOrder);

    AddCommentResp addComment(CommentAddReq add, Long id) throws IOException;

    Boolean removeAll(Long cid, Long userId);
}
