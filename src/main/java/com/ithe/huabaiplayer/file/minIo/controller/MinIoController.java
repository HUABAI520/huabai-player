package com.ithe.huabaiplayer.file.minIo.controller;

import com.ithe.huabaiplayer.common.BaseResponse;
import com.ithe.huabaiplayer.common.ResultUtils;
import com.ithe.huabaiplayer.common.annotation.AuthCheck;
import com.ithe.huabaiplayer.file.minIo.service.MinIoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.ithe.huabaiplayer.common.constant.UserConstant.ADMIN_ROLE;

/**
 * @ClassName MinIoController
 * @Author hua bai
 * @Date 2024/9/10 21:19
 **/
@RestController
@RequestMapping("/api-player/minIo")
@Slf4j
@RequiredArgsConstructor
public class MinIoController {

    private final MinIoService minIoService;

    @PutMapping("/update/picture/miniIo")
    @AuthCheck(mustRole = ADMIN_ROLE)
    public BaseResponse<Boolean> updateMiniIoTest(
            @RequestParam String bucketName,
            @RequestPart("file") MultipartFile multipartFile,
            @RequestParam String fileName) throws IOException {
        minIoService.uploadObject(bucketName, fileName,
                multipartFile.getInputStream(), multipartFile.getSize());
        return ResultUtils.success(true);
    }

    @GetMapping("/miniIo/policy")
    @AuthCheck(mustRole = ADMIN_ROLE)
    public BaseResponse<String> getBucketPolicy(String bucketName) throws Exception {
        return ResultUtils.success(minIoService.getBucketPolicy(bucketName));
    }

    @PutMapping("/miniIo/policy")
    @AuthCheck(mustRole = ADMIN_ROLE)
    public BaseResponse<Boolean> setBucketPolicy(String bucketName, String policy) throws Exception {
        minIoService.setBucketPolicy(bucketName, policy);
        return ResultUtils.success(true);
    }

    @GetMapping("/presigned-url")
    public String getPresignedUrl(@RequestParam String bucketName, @RequestParam String objectName,
                                  @RequestParam int durationMinutes) throws Exception {
        return minIoService.getPresignedUrlWithCache(bucketName, objectName, durationMinutes);
    }

    /**
     * 创建“文件夹”
     *
     * @param bucketName 存储桶名称
     * @param folderName 文件夹名称
     */
    @PostMapping("/create-folder")
    public String createFolder(@RequestParam String bucketName, @RequestParam String folderName)
            throws Exception {
        minIoService.createFolder(bucketName, folderName);
        return "文件夹已创建：" + folderName;
    }

    /**
     * 删除指定文件
     *
     * @param bucketName 存储桶名称
     * @param objectName 文件名称
     */
    @DeleteMapping("/delete-file")
    public String deleteFile(@RequestParam String bucketName, @RequestParam String objectName)
            throws Exception {
        minIoService.deleteFile(bucketName, objectName);
        return "文件已删除：" + objectName;
    }

    /**
     * 删除“文件夹”下的所有文件
     *
     * @param bucketName 存储桶名称
     * @param folderName 文件夹名称
     */
    @DeleteMapping("/delete-folder-files")
    public String deleteFolderFiles(@RequestParam String bucketName, @RequestParam String folderName)
            throws Exception {
        minIoService.deleteFolderFiles(bucketName, folderName);
        return "文件夹下的所有文件已删除：" + folderName;
    }
}
