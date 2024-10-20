package com.ithe.huabaiplayer.interaction.mapper;

import com.ithe.huabaiplayer.interaction.model.entity.LikeCounts;
import com.mybatisflex.core.BaseMapper;

import java.util.List;

/**
 * 点赞表计数 现在包括(评论、弹幕、评价) 映射层。
 *
 * @author L
 * @since 2024-09-29
 */
public interface LikeCountsMapper extends BaseMapper<LikeCounts> {
    int updateOrInsertBatchSelective(List<LikeCounts> list);
}
