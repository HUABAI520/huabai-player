package com.ithe.huabaiplayer.player.service.impl;

import com.ithe.huabaiplayer.common.ErrorCode;
import com.ithe.huabaiplayer.common.exception.BusinessException;
import com.ithe.huabaiplayer.common.service.RedisService;
import com.ithe.huabaiplayer.player.mapper.HuaTypeMapper;
import com.ithe.huabaiplayer.player.model.entity.HuaAnimeType;
import com.ithe.huabaiplayer.player.model.entity.HuaType;
import com.ithe.huabaiplayer.player.service.HuaAnimeTypeService;
import com.ithe.huabaiplayer.player.service.HuaTypeService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 动漫类型表 服务层实现。
 *
 * @author L
 * @since 2024-08-28
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HuaTypeServiceImpl extends ServiceImpl<HuaTypeMapper, HuaType> implements HuaTypeService {
    private final HuaAnimeTypeService huaAnimeTypeService;
    public static final String HUA_TYPE = "'huaByType:'";
    private final RedisService redisService;

    /**
     * 将 Set<?> 安全地转换为 Set<HuaType>
     */
    @SuppressWarnings("unchecked")
    private Set<HuaType> castSet(Set<?> set) {
        return (Set<HuaType>) set;
    }

    @Override
    public Set<HuaType> getAll() {
        Set<?> setAll = redisService.getSetAll(HUA_TYPE);
        if (Objects.isNull(setAll) || setAll.isEmpty()) {
            // 如果 Redis 中没有数据，则从数据库中获取
            setAll = new HashSet<>(this.list());
            // 将数据库中的数据同步到 Redis
            redisService.setSetAll(HUA_TYPE, setAll.toArray());
            // 一天
            redisService.expire(HUA_TYPE, 60 * 24);
        }
        return castSet(setAll);
    }

    @Override
    public Set<HuaType> insert(HuaType huaType) {
        boolean exists = this.queryChain().eq(HuaType::getType, huaType.getType()).exists();
        if (exists) {
            throw new BusinessException(ErrorCode.EXIST_ERROR, "该动漫类型已存在~");
        }
        save(huaType);
        // 查询redis是否存在
        boolean b = redisService.hasKey(HUA_TYPE);
        if (!b) {
            return getAll();
        }
        redisService.setSet(HUA_TYPE, huaType);
        return getAll();
    }

    @Override
    public Set<HuaType> removeType(Integer id) {
        // 先查询有没有动漫使用了
        boolean exists = huaAnimeTypeService.queryChain().eq(HuaAnimeType::getTypeId, id).exists();
        if (exists) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "该类型下还有动漫存在~");
        }
        HuaType huaType = this.getById(id);
        boolean b = removeById(id);
        if (!b) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "该类型不存在~");
        }
        // 查询redis是否存在
        boolean have = redisService.hasKey(HUA_TYPE);
        if (!have) {
            return getAll();
        }
        redisService.deleteSet(HUA_TYPE, huaType);
        return getAll();
    }

}
