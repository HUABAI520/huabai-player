package com.ithe.huabaiplayer.interaction.service;

import com.mybatisflex.core.service.IService;
import com.ithe.huabaiplayer.interaction.model.entity.ContentReportSummary;

/**
 *  服务层。
 *
 * @author L
 * @since 2025-02-27
 */
public interface ContentReportSummaryService extends IService<ContentReportSummary> {

    ContentReportSummary getById(Integer thirdType, Long thirdId);
}
