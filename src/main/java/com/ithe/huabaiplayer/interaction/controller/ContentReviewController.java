package com.ithe.huabaiplayer.interaction.controller;

import com.ithe.huabaiplayer.common.BaseResponse;
import com.ithe.huabaiplayer.common.ResultUtils;
import com.ithe.huabaiplayer.common.annotation.AuthCheck;
import com.ithe.huabaiplayer.common.constant.UserConstant;
import com.ithe.huabaiplayer.common.utils.UserContext;
import com.ithe.huabaiplayer.interaction.model.dto.req.ReportAddReq;
import com.ithe.huabaiplayer.interaction.model.dto.resp.ContentReviewQueryResp;
import com.ithe.huabaiplayer.interaction.model.dto.resp.ReportRecordResp;
import com.ithe.huabaiplayer.interaction.service.handler.ReportHandler;
import com.mybatisflex.core.paginate.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ContentReviewController
 * @Author hua bai
 * @Date 2025/2/25 16:26
 **/
@RestController
@RequestMapping("/api-player/contentReview")
@RequiredArgsConstructor
public class ContentReviewController {

    private final ReportHandler reportHandler;

    // 举报
    @AuthCheck
    @PostMapping("/report")
    public BaseResponse<Boolean> addReport(@RequestBody ReportAddReq reportAddReq) {
        Long reportBy = UserContext.getUser().getId();
        return ResultUtils.success(reportHandler.addReport(reportAddReq, reportBy));
    }

    // 审核分页查询
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @GetMapping("/query")
    public BaseResponse<Page<ContentReviewQueryResp>> queryPage(
            Integer page, Integer pageSize, Integer status, String keyword, Integer type) {
        return ResultUtils.success(reportHandler.queryPage(page, pageSize, status, keyword, type));
    }

    // 查询一个被举报内容的的所有被举报记录
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @GetMapping("/query/record")
    public BaseResponse<Page<ReportRecordResp>> queryRecordPage(
            Integer page, Integer pageSize, Long thirdId, Integer thirdType) {
        return ResultUtils.success(reportHandler.queryRecordPage(page, pageSize, thirdId, thirdType));
    }

    // 审核
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @PostMapping("/review")
    public BaseResponse<Boolean> review(Long thirdId, Integer thirdType, Integer status, Integer accType) {
        Long reviewerId = UserContext.getUser().getId();
        return ResultUtils.success(reportHandler.review(thirdId, thirdType, status, accType,reviewerId));
    }
}
