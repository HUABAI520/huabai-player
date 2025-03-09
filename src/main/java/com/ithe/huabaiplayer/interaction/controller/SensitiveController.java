package com.ithe.huabaiplayer.interaction.controller;

import com.ithe.huabaiplayer.common.BaseResponse;
import com.ithe.huabaiplayer.common.ResultUtils;
import com.ithe.huabaiplayer.common.annotation.AuthCheck;
import com.ithe.huabaiplayer.interaction.model.okHttp.sensitive.SensitiveQueryReq;
import com.ithe.huabaiplayer.interaction.model.okHttp.sensitive.SensitiveQueryResp;
import com.ithe.huabaiplayer.interaction.model.okHttp.sensitive.SensitiveUpdateReq;
import com.ithe.huabaiplayer.interaction.service.SensitiveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ithe.huabaiplayer.common.constant.UserConstant.ADMIN_ROLE;

/**
 * @ClassName SensitiveController
 * @Author hua bai
 * @Date 2025/2/25 10:42
 **/
@RestController
@Slf4j
@RequestMapping("/api-player/sensitive")
@RequiredArgsConstructor
public class SensitiveController {
    private final SensitiveService sensitiveService;

    @PostMapping("/add")
    @AuthCheck(mustRole = ADMIN_ROLE)
    public BaseResponse<String> addSensitiveWord(String word) {
        return ResultUtils.success(sensitiveService.addSensitiveWord(word));
    }

    @PostMapping("query")
    public BaseResponse<SensitiveQueryResp> getSensitiveWords(@RequestBody SensitiveQueryReq sensitiveQueryReq) {
        return ResultUtils.success(sensitiveService.getSensitiveWords(sensitiveQueryReq));
    }

    @PutMapping("/update")
    @AuthCheck(mustRole = ADMIN_ROLE)
    public BaseResponse<String> updateSensitiveWord(@RequestBody SensitiveUpdateReq sensitiveUpdateReq) {
        return ResultUtils.success(sensitiveService.updateSensitiveWord(sensitiveUpdateReq));
    }
}
