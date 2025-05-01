package com.ithe.huabaiplayer.file.local.service;

import cn.hutool.core.io.FileUtil;
import com.ithe.huabaiplayer.common.ErrorCode;
import com.ithe.huabaiplayer.common.exception.BusinessException;
import com.ithe.huabaiplayer.common.service.RedisService;
import com.ithe.huabaiplayer.file.model.entity.FileNodes;
import com.ithe.huabaiplayer.file.service.FileStorage;
import com.ithe.huabaiplayer.player.model.dto.animeVideos.FenPian;
import com.ithe.huabaiplayer.player.model.prefix.PictureProperties;
import com.ithe.huabaiplayer.player.model.prefix.PlayerProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;

import static com.ithe.huabaiplayer.common.constant.RedisKeyConstants.DONGMAN_INDEX;

/**
 * @ClassName LocalService
 * @Author hua bai
 * @Date 2024/9/10 22:25
 **/
@Service
@RequiredArgsConstructor
@Slf4j
//@ConditionalOnProperty(name = "file.use", havingValue = "local")// 条件装配Bean
public class LocalFileService implements FileStorage {
    private final RedisService redisService;
    private final PlayerProperties playerProperties;
    private final PictureProperties pictureProperties;
    public static final String FILE_SPLIT = "/";


    public static final int TWO_HOURS = 60 * 2;



    @Override
    public Integer uploadFen(MultipartFile file, Long animeId, Long videoId,
                             String fileSuffix, String fileName, Integer partNumber,
                             Integer total, String fullPath, long MAX_PART_SIZE, FileNodes fullNode) {
        try {
            // 获取存储路径
            String path = playerProperties.getPath(fullPath);
            Path dirPath = Paths.get(path + FILE_SPLIT + fileName);
            Files.createDirectories(dirPath);
            Path partPath = dirPath.resolve("part-" + partNumber + ".tmp");

            try (InputStream in = file.getInputStream();
                 OutputStream out = new BufferedOutputStream(Files.newOutputStream(partPath, StandardOpenOption.CREATE))) {
                // 创建8MB的缓冲区
                byte[] buffer = new byte[8192 * 1024];
                int bytesRead;
                long totalBytesWritten = 0;

                while ((bytesRead = in.read(buffer)) != -1) {
                    // 检查总写入字节数，确保不超过最大分片大小
                    if (totalBytesWritten + bytesRead > MAX_PART_SIZE) {
                        // 计算剩余可以写入的字节数
                        int remainingBytes = (int) (MAX_PART_SIZE - totalBytesWritten);
                        out.write(buffer, 0, remainingBytes);
                        break;  // 分片写入完成，退出循环
                    } else {
                        out.write(buffer, 0, bytesRead);
                        totalBytesWritten += bytesRead;
                    }
                }
            }
            String key = getKey(dirPath.toString());
            FenPian o = redisService.get(key) == null ? null : (FenPian) redisService.get(key);
            if (o == null) {
                if (partNumber != 1 && total != 1) {
                    return -1;
//                    throw new BusinessException(ErrorCode.PARAMS_ERROR, "系统出错了~，找不到之前的分片~");
                }
                o = new FenPian();
                o.setPartNumber(partNumber);
                o.setTotalParts(total);
                o.setPath(dirPath);
                if (total != 1) {
                    redisService.set(key, o);
                    // 过期时间2hours
                    redisService.expire(key, TWO_HOURS);
                } else {
//                    mergeVideo(animeId, fullNode, fileName, fileSuffix, videoId);
                    return null;
                }
                return partNumber;
            }
            if (o.getTotalParts() == null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "分片总数量错误~");
            }
            if (!o.getTotalParts().equals(total)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "分片总数量错误~");
            }
            if (partNumber > total) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "分片索引错误~");
            }
            if (o.getPartNumber() + 1 != partNumber) {
                if (partNumber.equals(1)) {
                    // 表明 用户重新传递了对应路径相同名字文件 因为没用hash 不知道是否还是同一个文件 故删除之前的
                    o.setPartNumber(partNumber);
                    o.setTotalParts(total);
                    o.setPath(dirPath);
                    redisService.set(key, o);
                    redisService.expire(key, TWO_HOURS);
                    deleteFenPath(dirPath);
                    return partNumber;
                }
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "分片索引错误~这次的分片索引不等于上次索引+1");
            }
            o.setPartNumber(partNumber);
            o.setTotalParts(total);
            o.setPath(dirPath);
            if (partNumber < total) {
                redisService.set(key, o);
                redisService.expire(key, TWO_HOURS);
                return partNumber;
            }
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "保存分片" + partNumber + "失败~");
        }
        return null;
    }

    @Override
    public void deleteFenPath(String dirPath) throws IOException {
        deleteFenPath(Paths.get(dirPath));
    }

    @Override
    public long mergeFen(Long animeId, String finalPath, String dirPath, Long fileId) throws Exception {
        Path finalPaths = Paths.get(finalPath);
        Path dirPaths = Paths.get(dirPath);
        // 按顺序合并分片
        int partNumber = 1;
        long size = 0;

        try (OutputStream out = Files.newOutputStream(finalPaths, StandardOpenOption.CREATE)) {

            while (Files.exists(dirPaths.resolve("part-" + partNumber + ".tmp"))) {
                Path partPath = dirPaths.resolve("part-" + partNumber + ".tmp");
                try (InputStream in = Files.newInputStream(partPath)) {
                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                        size += bytesRead;
                    }
                }
                partNumber++;
            }
            // 计算KB
            size = size / 1024;
        }
        // 清理临时分片文件
        deleteFenPath(dirPath);
        redisService.delete(getKey(dirPaths.toString()));
        redisService.delete(DONGMAN_INDEX + animeId);
        return size;
    }

    @Override
    public Boolean deleteVideoFile(String file) {
        String filePath = playerProperties.getPath(file);
        // 删除该文件
        return FileUtil.del(filePath);
    }


    public static void deleteFenPath(Path dirPath) throws IOException {
        Files.walk(dirPath)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(file -> {
                    try {
                        boolean delete = file.delete();
                        if (!delete) {
                            // 如果文件不能被删除，可以在这里处理
                            log.error("文件删除失败: 完整路径{}/{}", dirPath, file);
                        }
                    } catch (SecurityException se) {
                        // 如果没有权限删除文件，可以在这里处理
                        log.error("没有权限删除文件: 完整路径{}/{}", dirPath, file);
                    }
                });
    }

    /**
     * @param filename 前缀需包括 /
     */
    @Override
    public String createPic(String filename, String fullPath, MultipartFile multipartFile, Long id) {
        String filepath = pictureProperties.getDongmanPath(fullPath) + filename;
        String url = fullPath + filename;
        try {
            File file = new File(filepath);

            if (!file.getParentFile().exists()) {
                FileUtil.mkParentDirs(file);
            }
            FileUtil.writeFromStream(multipartFile.getInputStream(), file, true);
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "文件保存失败:" + e.getMessage());
        }
        return getImageUrl(url);
    }

    @Override
    public String getImageUrl(String image) {
        return pictureProperties.getIpPath() + image;
    }

    @Override
    public String getVideoUrl(String video) {
        return playerProperties.getIpPath(video);
    }

    @Override
    public String getVideoPath(String video) {
        return playerProperties.getPath(video);
    }

    @Override
    public String getOrigin(String url) {
        // url 去除 pictureProperties.getIpPath()
        return url.replace(pictureProperties.getIpPath(), "");
    }

    @Override
    public void deleteAvatar(String avatar) {
        // todo
    }

    @Override
    public void deleteImage(String image) {

    }

    @Override
    public void uploadAvatar(String avatar, MultipartFile file) throws IOException {
        // todo
    }

    @Override
    public String getAvatarUrl(String avatar) {
        return "";
    }

    @Override
    public boolean createAnimeFolder(String savePath) {
        File directory = new File(savePath);
        if (!directory.exists()) {
            // 尝试创建目录
            boolean isCreated;
            try {
                isCreated = directory.mkdirs();
                if (isCreated) {
                    return true;
                } else {
                    throw new BusinessException(ErrorCode.PARAMS_ERROR, "创建文件夹失败");
                }
            } catch (Exception e) {
                // 处理安全异常，比如没有权限创建目录
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "创建文件夹失败");
            }
        } else {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "该文件夹已存在");
        }
    }

    @Override
    public boolean updateFolderName(String path, String newName) {
        Path folderPath = Paths.get(path);
        // 检查路径是否指向一个文件夹
        if (Files.isDirectory(folderPath)) {
            // 获取文件夹的父路径
            Path parentPath = folderPath.getParent();
            // 创建新的文件夹路径
            Path newFolderPath = parentPath.resolve(newName);
            try {
                // 重命名文件夹
                Files.move(folderPath, newFolderPath);
            } catch (IOException e) {
                // 处理可能发生的 I/O 异常
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "重命名失败~");
            }
        } else {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "重命名失败~指定的路径不是文件夹");
        }
        return true;
    }
}
