package com.ithe.huabaiplayer.file.minIo.service;

import com.ithe.huabaiplayer.common.ErrorCode;
import com.ithe.huabaiplayer.common.exception.BusinessException;
import com.ithe.huabaiplayer.common.service.RedisService;
import com.ithe.huabaiplayer.file.minIo.model.BucketEnum;
import com.ithe.huabaiplayer.file.minIo.model.MinIoProperties;
import com.ithe.huabaiplayer.file.model.entity.FileNodes;
import com.ithe.huabaiplayer.file.service.FileStorage;
import com.ithe.huabaiplayer.player.model.dto.animeVideos.FenPian;
import io.minio.ComposeSource;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import static com.ithe.huabaiplayer.common.constant.RedisKeyConstants.DONGMAN_FENPIAN;
import static com.ithe.huabaiplayer.common.constant.RedisKeyConstants.DONGMAN_INDEX;

/**
 * @ClassName MinIoFileService
 * @Author hua bai
 * @Date 2024/9/10 22:32
 **/
@Service
@RequiredArgsConstructor
@Slf4j
//@ConditionalOnProperty(name = "file.use", havingValue = "minio")// 条件装配Bean
public class MinIoFileService implements FileStorage {
    public static final int TWO_HOUR = 60 * 2;
    private final MinIoService minIoService;
    private final RedisService redisService;
    private final MinIoProperties minIoProperties;
    public static final String FILE_SPLIT = "/";
    public static final int TWO_HOURS = 60 * 2;

    public String getKey(String dirPath) {
        return DONGMAN_FENPIAN + dirPath;
    }

    @Override
    public Integer uploadFen(MultipartFile file, Long animeId, Long videoId,
                             String fileSuffix, String fileName, Integer partNumber,
                             Integer total, String fullPath, long MAX_PART_SIZE,
                             FileNodes fullNode) {
        long size = file.getSize();
        if (size > MAX_PART_SIZE) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "分片文件大小超过限制");
        }
        // 暂时的文件夹
        String dirPath = fullPath + FILE_SPLIT + fileName;
        // 放置到暂时的文件夹中的分片文件 即 // 每个分片的文件名
        String objectName = dirPath + FILE_SPLIT + "part-" + partNumber + ".tmp";
        try {
            minIoService.uploadObject(BucketEnum.HUA_VIDEO.getBucketName(), objectName, file.getInputStream(), size);
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "文件保存失败:" + e.getMessage());
        }
        String key = getKey(dirPath);
        FenPian o = redisService.get(key) == null ? null : (FenPian) redisService.get(key);
        Path path = Paths.get(dirPath);
        if (o == null) {
            if (partNumber != 1 && total != 1) {
                return -1;
            }
            o = new FenPian();
            o.setPartNumber(partNumber);
            o.setTotalParts(total);
            o.setPath(path);
            if (total != 1) {
                redisService.set(key, o);
                // 过期时间2hours
                redisService.expire(key, TWO_HOURS);
            } else {
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
                o.setPath(path);
                redisService.set(key, o);
                redisService.expire(key, TWO_HOURS);
                this.deleteFenPath(dirPath);
                return partNumber;
            }
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "分片索引错误~这次的分片索引不等于上次索引+1");
        }
        o.setPartNumber(partNumber);
        o.setTotalParts(total);
        o.setPath(path);
        if (partNumber < total) {
            redisService.set(key, o);
            redisService.expire(key, TWO_HOURS);
            return partNumber;
        }
        return null;
    }

    @Override
    public void deleteFenPath(String dirPath) {
        // 删除分片所在文件夹
        try {
            minIoService.deleteFolderFiles(BucketEnum.HUA_VIDEO.getBucketName(), dirPath);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "删除分片文件失败:" + e.getMessage());
        }
    }

    /**
     * 合并分片
     *
     * @param finalPath 最后的存储文件名称
     * @param dirPath   分片文件所在文件夹
     * @return 合并大小
     */
    @Override
    public long mergeFen(Long animeId, String finalPath, String dirPath, Long fileId) throws Exception {
        List<ComposeSource> sources = new ArrayList<>();
        FenPian fenPian = (FenPian) redisService.get(getKey(dirPath));
        Integer totalParts = fenPian.getTotalParts();
        for (int i = 1; i <= totalParts; i++) {
            String objectName = dirPath + FILE_SPLIT + "part-" + i + ".tmp";
            sources.add(ComposeSource.builder().bucket(BucketEnum.HUA_VIDEO.getBucketName()).object(objectName).build());
        }
        // 合并所有分片
        long size = minIoService.mergeChunks(BucketEnum.HUA_VIDEO.getBucketName(), finalPath, sources);
        deleteFenPath(dirPath);
        redisService.delete(getKey(dirPath));
        redisService.delete(DONGMAN_INDEX + animeId);
        return size;

    }


    @Override
    public Boolean deleteVideoFile(String file) {
        minIoService.deleteFile(BucketEnum.HUA_VIDEO.getBucketName(), file);
        return true;
    }

    @Override
    public String createPic(String filename, String fullPath, MultipartFile multipartFile, Long id) {
        String filepath = fullPath + filename;
        try {
            minIoService.uploadObject(BucketEnum.HUA_PICTURE.getBucketName(),
                    filepath, multipartFile.getInputStream(), multipartFile.getSize());
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "文件保存失败:" + e.getMessage());
        }
        return getImageUrl(filepath);
    }

    @Override
    public String getImageUrl(String image) {
        return minIoProperties.getEndpoint() + "/" + BucketEnum.HUA_PICTURE.getBucketName() + "/" + image;
    }

    @Override
    @SneakyThrows
    public String getVideoUrl(String video) {
        return minIoService.getPresignedUrlWithCache(
                BucketEnum.HUA_VIDEO.getBucketName(), video, TWO_HOUR);
    }

    @Override
    public String getVideoPath(String video) {
        return video;
    }

    @Override
    public String getOrigin(String url) {
        String[] split = url.split("://");
        if (split.length < 2) {
            return "";
        }
        String[] strings = split[1].split("/");
        if (strings.length < 3) {
            return "";
        }
        StringJoiner joiner = new StringJoiner("/");
        for (int i = 2; i < strings.length - 1; i++) {
            joiner.add(strings[i]);
        }
        return joiner.toString();
    }

    @Override
    public void deleteAvatar(String avatar) {
        minIoService.deleteFile(BucketEnum.HUA_AVATAR.getBucketName(), avatar);
    }

    @Override
    public void uploadAvatar(String avatar, MultipartFile file) throws IOException {
        minIoService.uploadObject(BucketEnum.HUA_AVATAR.getBucketName(), avatar, file.getInputStream(), file.getSize());
    }

    @Override
    public String getAvatarUrl(String avatar) {
        return minIoProperties.getEndpoint() + "/" + BucketEnum.HUA_AVATAR.getBucketName() + "/" + avatar;

    }

    @Override
    public boolean createAnimeFolder(String savePath) {
//        boolean b = minIoService.hasFolder(BucketEnum.HUA_VIDEO.getBucketName(), savePath);
//        if (b) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR, "该目录已存在~");
//        }
        try {
            minIoService.createFolder(BucketEnum.HUA_VIDEO.getBucketName(), savePath);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "创建文件夹失败~");
        }
        return true;
    }

    @Override
    public boolean updateFolderName(String path, String newName) {
        // 不支持更新文件名
        return false;
    }
}
