package com.ithe.huabaiplayer.player.service;

import com.ithe.huabaiplayer.player.model.entity.AnimePlayCounts;
import com.mybatisflex.core.service.IService;

/**
 *  服务层。
 *
 * @author L
 * @since 2024-10-20
 */
public interface AnimePlayCountsService extends IService<AnimePlayCounts> {

    void addCount(Long animeId);


    Long getPlayCount(Long animeId);

    void syncPlayCounts();
}
