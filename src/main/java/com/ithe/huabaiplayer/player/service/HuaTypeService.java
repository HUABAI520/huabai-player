package com.ithe.huabaiplayer.player.service;

import com.mybatisflex.core.service.IService;
import com.ithe.huabaiplayer.player.model.entity.HuaType;

import java.util.List;

/**
 * 动漫类型表 服务层。
 *
 * @author L
 * @since 2024-08-28
 */
public interface HuaTypeService extends IService<HuaType> {

    List<HuaType> getAll();

    List<HuaType> insert(HuaType huaType);
}
