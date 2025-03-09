package com.ithe.huabaiplayer.file.controller;

import com.ithe.huabaiplayer.common.BaseResponse;
import com.ithe.huabaiplayer.common.ResultUtils;
import com.ithe.huabaiplayer.common.annotation.AuthCheck;
import com.ithe.huabaiplayer.common.constant.UserConstant;
import com.ithe.huabaiplayer.file.model.dto.resp.FileResp;
import com.ithe.huabaiplayer.file.model.entity.FileNodes;
import com.ithe.huabaiplayer.file.service.FileNodesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName FileController
 * @Author hua bai
 * @Date 2024/10/29 19:29
 * 用于获取会存储到数据库信息的文件
 **/
@RestController
@RequestMapping("/api-player/file")
@Slf4j
@RequiredArgsConstructor
public class FileController {
    private final FileNodesService fileNodesService;

    // 查询文件列表
    @GetMapping
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<List<FileResp>> list(Long id) {
        return ResultUtils.success(fileNodesService.list(id));
    }
    // 下载文件...todo 可能直接会走minIO/本地的访问接口
    // 上传文件...todo  结合之前的

    // 根据文件id查询信息
    @GetMapping("/get")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<FileNodes> getFile(Long fileId) {
        return ResultUtils.success(fileNodesService.getFile(fileId));
    }
}
