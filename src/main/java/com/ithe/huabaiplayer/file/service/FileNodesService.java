package com.ithe.huabaiplayer.file.service;

import com.ithe.huabaiplayer.file.model.dto.resp.FileResp;
import com.mybatisflex.core.service.IService;
import com.ithe.huabaiplayer.file.model.entity.FileNodes;

import java.util.List;

/**
 * 文件夹/文件信息表 服务层。
 *
 * @author L
 * @since 2024-08-28
 */
public interface FileNodesService extends IService<FileNodes> {

    FileNodes getFile(Long fileId);

    List<FileResp> list(Long id);
}
