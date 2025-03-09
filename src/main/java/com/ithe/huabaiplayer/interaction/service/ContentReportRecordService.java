package com.ithe.huabaiplayer.interaction.service;

import com.mybatisflex.core.service.IService;
import com.ithe.huabaiplayer.interaction.model.entity.ContentReportRecord;

/**
 * 举报详细记录表 服务层。
 *
 * @author L
 * @since 2025-02-27
 */
public interface ContentReportRecordService extends IService<ContentReportRecord> {

    boolean getByUserThird(Long reportBy, Integer thirdType, Long thirdId);
}
