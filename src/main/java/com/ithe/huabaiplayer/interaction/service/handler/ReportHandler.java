package com.ithe.huabaiplayer.interaction.service.handler;

import com.ithe.huabaiplayer.common.ErrorCode;
import com.ithe.huabaiplayer.common.exception.BusinessException;
import com.ithe.huabaiplayer.common.utils.HuaUtils;
import com.ithe.huabaiplayer.file.factory.FileFactory;
import com.ithe.huabaiplayer.file.service.FileStorage;
import com.ithe.huabaiplayer.interaction.model.dto.req.ReportAddReq;
import com.ithe.huabaiplayer.interaction.model.dto.resp.ContentReviewQueryResp;
import com.ithe.huabaiplayer.interaction.model.dto.resp.ReportRecordResp;
import com.ithe.huabaiplayer.interaction.model.entity.ContentReportRecord;
import com.ithe.huabaiplayer.interaction.model.entity.ContentReportSummary;
import com.ithe.huabaiplayer.interaction.model.enums.InteractionEnums;
import com.ithe.huabaiplayer.interaction.service.BarrageService;
import com.ithe.huabaiplayer.interaction.service.CommentService;
import com.ithe.huabaiplayer.interaction.service.ContentReportRecordService;
import com.ithe.huabaiplayer.interaction.service.ContentReportSummaryService;
import com.ithe.huabaiplayer.interaction.service.UserRatingService;
import com.ithe.huabaiplayer.user.model.entity.User;
import com.ithe.huabaiplayer.user.service.UserService;
import com.mybatisflex.core.paginate.Page;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ithe.huabaiplayer.user.model.entity.table.UserTableDef.USER;

/**
 * @ClassName ReportHandler
 * @Author hua bai
 * @Date 2025/2/27 9:45
 **/
@Service
@RequiredArgsConstructor
public class ReportHandler {
    // 举报记录
    private final ContentReportRecordService contentReportRecordService;
    // 举报汇总
    private final ContentReportSummaryService contentReportSummaryService;
    private final BarrageService barrageService;
    private final CommentService commentService;
    private final UserRatingService userRatingService;
    private final UserService userService;
    private final FileFactory fileFactory;

    private FileStorage fileService() {
        return fileFactory.getFileService();
    }


    @Transactional(rollbackFor = Exception.class)
    public Boolean addReport(ReportAddReq reportAddReq, Long reportBy) {
        Integer thirdType = reportAddReq.getThirdType();
        Long thirdId = reportAddReq.getThirdId();
        String reason = reportAddReq.getReason();
        Integer type = reportAddReq.getType();
        String reasonDetail = reportAddReq.getReasonDetail();
        InteractionEnums enums = InteractionEnums.getByCode(thirdType);
        if (Objects.isNull(enums)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请举报正确的类型！");
        }
        // 先查询用户是否已经举报了同一个
        boolean isHave = contentReportRecordService.getByUserThird(reportBy, thirdType, thirdId);
        if (isHave) {
            throw new BusinessException(ErrorCode.EXIST_ERROR, "您已经举报过该内容了!~");
        }
        Object data = getData(enums, thirdId);
        if (Objects.isNull(data)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "举报的内容不存在~");
        }
        ContentReportRecord reportRecord = ContentReportRecord.builder()
                .thirdType(thirdType)
                .thirdId(thirdId)
                .reportBy(reportBy)
                .type(type)
                .reason(reason)
                .reasonDetail(reasonDetail)
                .build();
        if (!contentReportRecordService.save(reportRecord)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "举报失败！");
        }
        ContentReportSummary have = contentReportSummaryService.getById(thirdType, thirdId);
        if (Objects.isNull(have)) {
            ContentReportSummary newContentReport = ContentReportSummary.builder()
                    .thirdType(thirdType)
                    .thirdId(thirdId)
                    .firstReportTime(new Date())
                    .latestReportTime(new Date())
                    .latestType(type)
                    .content(HuaUtils.getField(data, enums.getValue(), String.class))
                    .publishBy(HuaUtils.getField(data, "userId", Long.class))
                    .build();
            contentReportSummaryService.save(newContentReport);
            return true;
        } else {
            Integer status = have.getStatus();
            if (status.equals(0)) {
                have.setReportCount(have.getReportCount() + 1);
                have.setLatestType(type);
                have.setLatestReportTime(new Date());
                return contentReportSummaryService.updateById(have);
            } else if (status.equals(1)) {
                return true;
            } else {
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "该内容已被审核通过不为违规内容！！");
            }
        }
    }


    public Page<ContentReviewQueryResp> queryPage(Integer page, Integer pageSize, Integer status, String keyword, Integer type) {
        Page<ContentReviewQueryResp> respPage = contentReportSummaryService.queryChain()
                .eq(ContentReportSummary::getStatus, status)
                .like(ContentReportSummary::getContent, keyword, StringUtils.isNotBlank(keyword))
                .eq(ContentReportSummary::getLatestType, type, Objects.nonNull(type))
                .orderBy(ContentReportSummary::getReportCount, false)
                .pageAs(new Page<>(page, pageSize), ContentReviewQueryResp.class);
        if (respPage.getRecords().isEmpty()) {
            return respPage;
        }
        Set<Long> userIds = respPage.getRecords().stream()
                .flatMap(review -> Stream.of(review.getPublishBy(), review.getReviewerId()))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        if (userIds.isEmpty()) {
            return respPage;
        }
        Map<Long, ContentReviewQueryResp.UserMsg> userMap = getLongUserMsgMap(userIds);
        respPage.getRecords().forEach(review -> {
            Long publishBy = review.getPublishBy();
            Long reportBy = review.getReviewerId();
            if (Objects.nonNull(publishBy) && userMap.containsKey(publishBy)) {

                review.setPublishUser(userMap.get(publishBy));

            }
            if (Objects.nonNull(reportBy) && userMap.containsKey(reportBy)) {
                review.setReviewer(userMap.get(reportBy));
            }
        });
        return respPage;
    }

    @NotNull
    private Map<Long, ContentReviewQueryResp.UserMsg> getLongUserMsgMap(Set<Long> userIds) {
        return userService.queryChain()
                .select(USER.ID.as("userId"), USER.USER_AVATAR, USER.USERNAME)
                .from(USER)
                .in(User::getId, userIds)
                .listAs(ContentReviewQueryResp.UserMsg.class)
                .stream()
                .collect(Collectors.toMap(ContentReviewQueryResp.UserMsg::getUserId, userMsg -> {
                    String userAvatar = userMsg.getUserAvatar();
                    if (StringUtils.isNotBlank(userAvatar)) {
                        userMsg.setUserAvatar(fileService().getAvatarUrl(userAvatar));
                    }
                    return userMsg;
                }));
    }

    public Page<ReportRecordResp> queryRecordPage(Integer page, Integer pageSize, Long thirdId, Integer thirdType) {
        Page<ReportRecordResp> reportRecordRespPage = contentReportRecordService.queryChain()
                .eq(ContentReportRecord::getThirdId, thirdId)
                .eq(ContentReportRecord::getThirdType, thirdType)
                .pageAs(new Page<>(page, pageSize), ReportRecordResp.class);
        if (reportRecordRespPage.getRecords().isEmpty()) {
            return reportRecordRespPage;
        }
        Set<Long> userIds = reportRecordRespPage.getRecords().stream()
                .map(ReportRecordResp::getReportBy)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        if (userIds.isEmpty()) {
            return reportRecordRespPage;
        }
        Map<Long, ContentReviewQueryResp.UserMsg> userMap = getLongUserMsgMap(userIds);
        reportRecordRespPage.getRecords().forEach(reportRecordResp -> {
            Long reportBy = reportRecordResp.getReportBy();
            if (Objects.nonNull(reportBy) && userMap.containsKey(reportBy)) {
                reportRecordResp.setReportUser(userMap.get(reportBy));
            }
        });
        return reportRecordRespPage;
    }

    /**
     * @param accType 最后确认类型
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean review(
            Long thirdId, Integer thirdType, Integer status, Integer accType, Long reviewerId) {
        if (Objects.isNull(status) || Objects.isNull(accType) || status.equals(0)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误！");
        }
        InteractionEnums enums = InteractionEnums.getByCode(thirdType);
        if (Objects.isNull(enums)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请举报正确的类型！");
        }
        boolean update = contentReportSummaryService.updateChain()
                .set(ContentReportSummary::getStatus, status)
                .set(ContentReportSummary::getProcessedTime, new Date())
                .set(ContentReportSummary::getReviewerId, reviewerId)
                .set(ContentReportSummary::getLatestType, accType)
                .eq(ContentReportSummary::getThirdId, thirdId)
                .eq(ContentReportSummary::getThirdType, thirdType)
                .eq(ContentReportSummary::getStatus, 0)
                .update();
        if (!update) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "已经被处理了 请不要重复处理~！");
        }
        if (status.equals(1)) {
            // 违规了需要删除
            delete(enums, thirdId);
            //todo 后续看情况实现 通知举报人 和被举报的 举报成功
            return true;
        }//todo 后续看情况实现 通知举报人 和被举报的 没有违规
        return true;
    }

    private void delete(InteractionEnums enums, Long thirdId) {
        switch (enums) {
            case BARRAGE -> barrageService.removeById(thirdId);
            case COMMENT -> commentService.removeById(thirdId);
            case USER_RATING -> userRatingService.removeById(thirdId);
        }
    }

    private Object getData(InteractionEnums enums, Long thirdId) {
        return switch (enums) {
            case BARRAGE -> barrageService.getById(thirdId);
            case COMMENT -> commentService.getById(thirdId);
            case USER_RATING -> userRatingService.getById(thirdId);
        };
    }
}
