package com.ithe.huabaiplayer.file.service;

import com.ithe.huabaiplayer.file.model.entity.FileNodes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @interface FileStorage
 * @Author hua bai
 * @Date 2024/9/10 22:31
 **/
public interface FileStorage {
    Integer uploadFen(MultipartFile file, Long animeId, Long videoId,
                      String fileSuffix, String fileName, Integer partNumber,
                      Integer total, String fullPath, long MAX_PART_SIZE, FileNodes fullNode);

    void deleteFenPath(String dirPath) throws IOException;

    long mergeFen(Long animeId, String finalPath, String dirPath, Long fileId) throws Exception;

    Boolean deleteVideoFile(String file);

    // filename -- 文件所在的目录和文件名 //fullPath 最上层的文件夹路径 fullPath +  filename 等于文件地址
    String createPic(String filename, String fullPath, MultipartFile multipartFile, Long id);

    String getImageUrl(String image);

    String getVideoUrl(String video);

    String getVideoPath(String video);

    boolean createAnimeFolder(String savePath);

    /**
     * @param path 原名字的完整路径
     */
    boolean updateFolderName(String path, String newName);
}
