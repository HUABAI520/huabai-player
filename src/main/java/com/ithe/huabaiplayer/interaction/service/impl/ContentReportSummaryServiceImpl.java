package com.ithe.huabaiplayer.interaction.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.ithe.huabaiplayer.interaction.model.entity.ContentReportSummary;
import com.ithe.huabaiplayer.interaction.mapper.ContentReportSummaryMapper;
import com.ithe.huabaiplayer.interaction.service.ContentReportSummaryService;
import org.springframework.stereotype.Service;

/**
 * 服务层实现。
 *
 * @author L
 * @since 2025-02-27
 */
@Service
public class ContentReportSummaryServiceImpl extends ServiceImpl<ContentReportSummaryMapper, ContentReportSummary> implements ContentReportSummaryService {

    @Override
    public ContentReportSummary getById(Integer thirdType, Long thirdId) {
        return this.queryChain()
                .eq(ContentReportSummary::getThirdType, thirdType)
                .eq(ContentReportSummary::getThirdId, thirdId)
                .oneOpt()
                .orElse(null);
    }
}
