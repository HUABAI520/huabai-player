package com.ithe.huabaiplayer.interaction.service.impl;

import com.ithe.huabaiplayer.interaction.mapper.LikeCountsMapper;
import com.ithe.huabaiplayer.interaction.model.entity.LikeCounts;
import com.ithe.huabaiplayer.interaction.service.LikeCountsService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 点赞表计数 现在包括(评论、弹幕、评价) 服务层实现。
 *
 * @author L
 * @since 2024-09-29
 */
@Service
@RequiredArgsConstructor
public class LikeCountsServiceImpl extends ServiceImpl<LikeCountsMapper, LikeCounts> implements LikeCountsService {
    private final LikeCountsMapper mapper;
    @Override
    public int updateOrInsertBatchSelective(List<LikeCounts> list){
        return mapper.updateOrInsertBatchSelective(list);
    }
}
