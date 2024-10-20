package com.ithe.huabaiplayer.interaction.service;

import com.ithe.huabaiplayer.common.CursorPage;
import com.ithe.huabaiplayer.interaction.model.dto.req.BarrageAddReq;
import com.ithe.huabaiplayer.interaction.model.dto.resp.BarrageResp;
import com.ithe.huabaiplayer.interaction.model.entity.Barrage;
import com.mybatisflex.core.service.IService;

/**
 * 弹幕表 服务层。
 *
 * @author L
 * @since 2024-09-29
 */
public interface BarrageService extends IService<Barrage> {

    void addBarrage(BarrageAddReq add, Long id);

    void addBarrage(BarrageAddReq add, Long id, String type);

    void saveByTime(Barrage message);

    CursorPage<BarrageResp> getBarrages(Long videoId, Long maxCursor);
}
