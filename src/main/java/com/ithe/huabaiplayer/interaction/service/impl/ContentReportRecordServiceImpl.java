package com.ithe.huabaiplayer.interaction.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.ithe.huabaiplayer.interaction.model.entity.ContentReportRecord;
import com.ithe.huabaiplayer.interaction.mapper.ContentReportRecordMapper;
import com.ithe.huabaiplayer.interaction.service.ContentReportRecordService;
import org.springframework.stereotype.Service;

/**
 * 举报详细记录表 服务层实现。
 *
 * @author L
 * @since 2025-02-27
 */
@Service
public class ContentReportRecordServiceImpl extends ServiceImpl<ContentReportRecordMapper, ContentReportRecord> implements ContentReportRecordService {

    @Override
    public boolean getByUserThird(Long reportBy, Integer thirdType, Long thirdId) {
        return queryChain().eq(ContentReportRecord::getReportBy, reportBy)
                .eq(ContentReportRecord::getThirdType, thirdType)
                .eq(ContentReportRecord::getThirdId, thirdId).exists();
    }
}
