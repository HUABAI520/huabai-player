package com.ithe.huabaiplayer.interaction.service.impl;

import com.ithe.huabaiplayer.interaction.mapper.LikesMapper;
import com.ithe.huabaiplayer.interaction.model.entity.Likes;
import com.ithe.huabaiplayer.interaction.service.LikesService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 点赞表 现在包括(评论、弹幕、评价) 服务层实现。
 *
 * @author L
 * @since 2024-09-29
 */
@Service
@RequiredArgsConstructor
public class LikesServiceImpl extends ServiceImpl<LikesMapper, Likes> implements LikesService {
    private final LikesMapper likesMapper;

    @Override
    public int deleteBatchSelective(List<Likes> likes) {
        return likesMapper.deleteBatchSelective(likes);
    }
}
