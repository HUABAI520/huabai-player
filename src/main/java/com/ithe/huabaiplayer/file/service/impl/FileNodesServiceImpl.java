package com.ithe.huabaiplayer.file.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.ithe.huabaiplayer.file.model.entity.FileNodes;
import com.ithe.huabaiplayer.file.mapper.FileNodesMapper;
import com.ithe.huabaiplayer.file.service.FileNodesService;
import org.springframework.stereotype.Service;

/**
 * 文件夹/文件信息表 服务层实现。
 *
 * @author L
 * @since 2024-08-28
 */
@Service
public class FileNodesServiceImpl extends ServiceImpl<FileNodesMapper, FileNodes> implements FileNodesService {
    @Override
    public FileNodes getFile(Long fileId) {
        return this.getById(fileId);
    }
}
