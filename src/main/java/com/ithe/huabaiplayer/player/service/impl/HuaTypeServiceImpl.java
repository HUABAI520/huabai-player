package com.ithe.huabaiplayer.player.service.impl;

import com.ithe.huabaiplayer.player.mapper.HuaTypeMapper;
import com.ithe.huabaiplayer.player.model.entity.HuaType;
import com.ithe.huabaiplayer.player.service.HuaTypeService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ithe.huabaiplayer.common.utils.SpringContextUtils.getBean;

/**
 * 动漫类型表 服务层实现。
 *
 * @author L
 * @since 2024-08-28
 */
@Service
public class HuaTypeServiceImpl extends ServiceImpl<HuaTypeMapper, HuaType> implements HuaTypeService {

    public static final String HUA_TYPE = "'huaByType:'";

    @Override
    @Cacheable(cacheNames = "HuaType", cacheManager = "stringRedisManager", key = HUA_TYPE)
    public List<HuaType> getAll() {
        return this.list();
    }

    @Override
    public List<HuaType> insert(HuaType huaType) {
        save(huaType);
        removeCacheAll();
        // 获取HuaTypeServiceImpl 的bean
        HuaTypeService huaTypeService = getBean(HuaTypeService.class);
        return huaTypeService.getAll();
    }

    @CacheEvict(cacheNames = "HuaType", cacheManager = "stringRedisManager", key = HUA_TYPE)
    public void removeCacheAll() {
    }
}
