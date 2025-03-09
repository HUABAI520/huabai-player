package com.ithe.huabaiplayer.player.service;

import com.ithe.huabaiplayer.player.model.entity.HuaType;
import com.mybatisflex.core.service.IService;

import java.util.Set;

/**
 * 动漫类型表 服务层。
 *
 * @author L
 * @since 2024-08-28
 */
public interface HuaTypeService extends IService<HuaType> {

    Set<HuaType> getAll();

    Set<HuaType> insert(HuaType huaType);

    Set<HuaType> removeType(Integer id);
}
