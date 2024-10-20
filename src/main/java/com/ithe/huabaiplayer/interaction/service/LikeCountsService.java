package com.ithe.huabaiplayer.interaction.service;

import com.mybatisflex.core.service.IService;
import com.ithe.huabaiplayer.interaction.model.entity.LikeCounts;

import java.util.List;

/**
 * 点赞表计数 现在包括(评论、弹幕、评价) 服务层。
 *
 * @author L
 * @since 2024-09-29
 */
public interface LikeCountsService extends IService<LikeCounts> {

    int updateOrInsertBatchSelective(List<LikeCounts> list);
}
