package com.ithe.huabaiplayer.player.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.ithe.huabaiplayer.player.model.entity.HuaAnimeType;
import com.ithe.huabaiplayer.player.mapper.HuaAnimeTypeMapper;
import com.ithe.huabaiplayer.player.service.HuaAnimeTypeService;
import org.springframework.stereotype.Service;

/**
 * 动漫-类型关联表 服务层实现。
 *
 * @author L
 * @since 2024-08-28
 */
@Service
public class HuaAnimeTypeServiceImpl extends ServiceImpl<HuaAnimeTypeMapper, HuaAnimeType> implements HuaAnimeTypeService {

}
