package com.ithe.huabaiplayer.interaction.mapper;

import com.ithe.huabaiplayer.interaction.model.entity.Likes;
import com.mybatisflex.core.BaseMapper;

import java.util.List;

/**
 * 点赞表 现在包括(评论、弹幕、评价) 映射层。
 *
 * @author L
 * @since 2024-09-29
 */
public interface LikesMapper extends BaseMapper<Likes> {
    int deleteBatchSelective(List<Likes> list);
}
