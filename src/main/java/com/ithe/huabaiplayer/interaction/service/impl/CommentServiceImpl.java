package com.ithe.huabaiplayer.interaction.service.impl;

import com.ithe.huabaiplayer.common.ErrorCode;
import com.ithe.huabaiplayer.common.exception.BusinessException;
import com.ithe.huabaiplayer.common.utils.SqlUtils;
import com.ithe.huabaiplayer.common.utils.UserContext;
import com.ithe.huabaiplayer.file.factory.FileFactory;
import com.ithe.huabaiplayer.file.service.FileStorage;
import com.ithe.huabaiplayer.interaction.mapper.CommentMapper;
import com.ithe.huabaiplayer.interaction.model.dto.req.CommentAddReq;
import com.ithe.huabaiplayer.interaction.model.dto.resp.AddCommentResp;
import com.ithe.huabaiplayer.interaction.model.dto.resp.CommentResp;
import com.ithe.huabaiplayer.interaction.model.entity.Comment;
import com.ithe.huabaiplayer.interaction.model.entity.LikeCounts;
import com.ithe.huabaiplayer.interaction.model.enums.LikeTypeEnum;
import com.ithe.huabaiplayer.interaction.model.okHttp.analyze.AnalyzeResponse;
import com.ithe.huabaiplayer.interaction.service.CommentService;
import com.ithe.huabaiplayer.interaction.service.SensitiveService;
import com.ithe.huabaiplayer.interaction.service.handler.LikeService;
import com.ithe.huabaiplayer.user.model.vo.UserVO;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.ithe.huabaiplayer.interaction.model.entity.table.CommentTableDef.COMMENT;
import static com.ithe.huabaiplayer.interaction.model.entity.table.LikeCountsTableDef.LIKE_COUNTS;
import static com.ithe.huabaiplayer.user.model.entity.table.UserTableDef.USER;

/**
 * 评论表 服务层实现。
 *
 * @author L
 * @since 2024-09-28
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    private final LikeService likeService;
    private final FileFactory fileFactory;
    private final SensitiveService analysisService;

    private FileStorage fileService() {
        return fileFactory.getFileService();
    }

    /**
     * @param oid 最外层的回复id
     */
    @Override
    public Page<CommentResp> queryPage(Long videoId, Long oid, Integer pageNumber,
                                       Integer pageSize, String sortField,
                                       String sortOrder) {
        UserVO user = UserContext.getUser();
//        QueryWrapper query = query()
//                .select(COMMENT.ALL_COLUMNS, LIKE_COUNTS.COUNT.as("likeCount"), LIKES.USER_ID.as("isLike"))
//                .from(COMMENT)
//                .leftJoin(LIKE_COUNTS)
//                .on(LIKE_COUNTS.THIRD_ID.eq(COMMENT.ID).and(LIKE_COUNTS.THIRD_TYPE.eq(LikeTypeEnum.COMMENT.getType())))
//                // -- todo 现在查询用户是否点赞使用连表查询 后面使用redis后使用redis查 没有再从数据库查询
//                .leftJoin(LIKES)
//                .on(LIKES.USER_ID.eq(user.getId()).and(LIKES.THIRD_ID.eq(COMMENT.ID)).and(LIKES.THIRD_TYPE.eq(LikeTypeEnum.COMMENT.getType())))
//                // --
//                .eq(Comment::getVideoId, videoId);
        QueryWrapper query = query()
                // todo 如果分成微服务 取消联表查询 先从redis 获取用户 没有的在从数据库查询
                .select(COMMENT.ALL_COLUMNS, USER.ID.as("userId"), USER.USERNAME, USER.USER_AVATAR)
                .from(COMMENT)
                .leftJoin(USER).on(USER.ID.eq(COMMENT.USER_ID))
                .eq(Comment::getVideoId, videoId);
        if (oid != null) {
            query.eq(Comment::getOriginId, oid);
        } else {
            // 这里 ParentId 或 OriginId 为null 都可以
            query.isNull(Comment::getParentId);
        }
        if (SqlUtils.validSortField(sortField)) {
            if ("count".equals(sortField)) {
                query.leftJoin(LIKE_COUNTS)
                        .on(LIKE_COUNTS.THIRD_ID.eq(COMMENT.ID).and(LIKE_COUNTS.THIRD_TYPE.eq(LikeTypeEnum.COMMENT.getType())));
            }
            Boolean order = "ASC".equals(sortOrder);
            query.orderBy(sortField, order);
        }
        Page<CommentResp> page = this.pageAs(new Page<>(pageNumber, pageSize), query, CommentResp.class);
        List<CommentResp> records = page.getRecords();
        List<Long> thirds = records.stream().map(CommentResp::getId).toList();
        // thirdId -> count
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Map<Long, Integer> collect = likeService.selectCounts(thirds, LikeTypeEnum.COMMENT).stream().collect(Collectors.toMap(LikeCounts::getThirdId, LikeCounts::getCount));
        Map<Long, Boolean> isLike = likeService.selectIsLike(user.getId(), LikeTypeEnum.COMMENT, thirds);
        records.forEach(commentResp -> {
            Long id = commentResp.getId();
            String avatar = commentResp.getUser().getUserAvatar();
            if (StringUtils.isNotBlank(avatar)) {
                commentResp.getUser().setUserAvatar(fileService().getAvatarUrl(avatar));
            }
            commentResp.setLikeCount(collect.getOrDefault(id, 0));
            commentResp.setIsLike(isLike.getOrDefault(id, false) ? 1L : 0L);
        });
        stopWatch.stop();
        log.info("查询评论点赞信息耗时:{}", stopWatch.getTotalTimeMillis());
        return page;
    }

    /**
     * todo 使用redis限制评论的频率 + 可能使用redis 收集后再更新数据库
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AddCommentResp addComment(CommentAddReq add, Long userId) throws IOException {
        Long parentId = add.getParentId();
        Long originId = add.getOriginId();
        String content = add.getContent();
        // 调用python服务 校验评论内容
        AnalyzeResponse analyzeResponse = analysisService.analyzeText(content);
        log.info("评论内容:{}", analyzeResponse);
        log.info("评论评分：{}", analyzeResponse.getSentimentScore());
        log.info("审核耗时：{}", analyzeResponse.getElapsedTimeMs());
        if (!analyzeResponse.isAllowed()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, analyzeResponse.getMessage());
        }

        Comment comment = Comment.builder()
                .parentId(parentId)
                .originId(originId)
                .content(content)
                .animeId(add.getAnimeId())
                .videoId(add.getVideoId())
                .userId(userId)
                .build();
        if (Objects.nonNull(parentId)) {
            // 最外层下的回复数量更新
            updateChain()
                    .setRaw(Comment::getReplyCount, "reply_count+1")
                    .eq(Comment::getId, originId)
                    .update();

        }
        this.save(comment);
        return AddCommentResp.builder().id(comment.getId())
                .sentimentScore(analyzeResponse.getSentimentScore()).build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeAll(Long cid, Long userId) {
        QueryWrapper queryWrapper = new QueryWrapper().eq(Comment::getId, cid).eq(Comment::getUserId, userId);
        Comment one = this.getOne(queryWrapper);
        if (one == null) {
            return false;
        }
        if (one.getOriginId() != null) {
            // 表示不是顶级评论
            // 获得所有回复
            List<Comment> list = this.list(query().eq(Comment::getParentId, one.getId()));
            list.add(one);
            this.removeByIds(list.stream().map(Comment::getId).toList());
            this.updateChain()
                    .setRaw(Comment::getReplyCount, "reply_count-" + list.size()).eq(Comment::getId, one.getOriginId())
                    .update();
            return true;
        }
        boolean remove = this.removeById(one.getId());
        if (remove) {
            this.remove(query().eq(Comment::getOriginId, cid));
        }
        // todo 是否删除点赞记录 和 点赞数？ 怎么删 异步（或消息队列？）还是同步？
        return remove;
    }
}
