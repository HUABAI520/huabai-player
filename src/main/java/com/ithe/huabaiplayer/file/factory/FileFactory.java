package com.ithe.huabaiplayer.file.factory;

import com.ithe.huabaiplayer.file.local.service.LocalFileService;
import com.ithe.huabaiplayer.file.minIo.service.MinIoFileService;
import com.ithe.huabaiplayer.file.service.FileStorage;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName FileFactory
 * @Author hua bai
 * @Date 2024/9/10 22:27
 **/
@Component
@RequiredArgsConstructor
@Data
public class FileFactory {
    @Value("${file.use}")
    private String use;
    private final LocalFileService localFileService;
    private final MinIoFileService minIoFileService;

    public FileStorage getFileService() {
        if ("minio".equals(use)) {
            return minIoFileService;
        }
        return localFileService;
    }
}
