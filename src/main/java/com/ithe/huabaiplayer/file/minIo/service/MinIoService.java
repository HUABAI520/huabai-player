package com.ithe.huabaiplayer.file.minIo.service;

import com.ithe.huabaiplayer.common.service.RedisService;
import com.ithe.huabaiplayer.file.minIo.model.PreUrl;
import io.minio.ComposeObjectArgs;
import io.minio.ComposeSource;
import io.minio.GetBucketPolicyArgs;
import io.minio.GetObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.RemoveObjectsArgs;
import io.minio.Result;
import io.minio.SetBucketPolicyArgs;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.ithe.huabaiplayer.common.constant.RedisKeyConstants.MINIO_PREFIX;

@Service
@Slf4j
public class MinIoService {
    @Autowired
    private MinioClient minioClient;
    @Autowired
    private RedisService redisService;


    public String getBucketPolicy(String bucketName) throws Exception {
        return minioClient.getBucketPolicy(
                GetBucketPolicyArgs.builder().bucket(bucketName).build()
        );
    }

    /**
     * @param bucketName 请校验系统中是否存在
     */
    public void setBucketPolicy(String bucketName, String policy) throws Exception {
        minioClient.setBucketPolicy(
                SetBucketPolicyArgs.builder().bucket(bucketName).config(policy).build()
        );
    }

    /**
     * @param bucketName 请校验系统中是否存在
     */
    public void uploadObject(String bucketName, String objectName, InputStream inputStream, long size) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(inputStream, size, -1)
                            .contentType("application/octet-stream")
                            .build());
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException("Error occurred while uploading object to MinIO", e);
        }
    }

    public String getPresignedUrlWithCache(String bucketName, String objectName, int duration) throws Exception {
        String redisKey = MINIO_PREFIX + bucketName + ":" + objectName;

        // 1. 查询 Redis 缓存
        PreUrl preUrl = (PreUrl) redisService.get(redisKey);
        if (preUrl != null && preUrl.getExpireTime().after(new Date())) {
            return preUrl.getPreUrl();
        }
        // 2. 查询文件是否存在
        try {
            minioClient.statObject(
                    StatObjectArgs.builder().bucket(bucketName).object(objectName).build()
            );
        } catch (MinioException e) {
            throw new RuntimeException("文件不存在或无权访问");
        }

        // 3. 生成新的预签名 URL
        String presignedUrl = generatePresignedUrl(bucketName, objectName, duration);
        // 通过当前时间 + 过期时间 过期时间
        Date expireTime = new Date(System.currentTimeMillis() + (long) duration * 60 * 1000);
        PreUrl url = new PreUrl();
        url.setPreUrl(presignedUrl);
        url.setExpireTime(expireTime);
        // 4. 将预签名 URL 缓存到 Redis，并设置过期时间
        redisService.setWithExpire(redisKey, url, duration, TimeUnit.MINUTES);
        return presignedUrl;
    }

    private String generatePresignedUrl(String bucketName, String objectName, int durationMinutes) throws Exception {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(bucketName)
                        .object(objectName)
                        .expiry(durationMinutes, TimeUnit.MINUTES)
                        .build()
        );
    }

    public Resource downloadObject(String bucketName, String objectName) {
        try {
            InputStream inputStream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build());
            return new InputStreamResource(inputStream);
        } catch (MinioException e) {
            throw new RuntimeException("Error occurred while downloading object from MinIO", e);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建“文件夹”（在对象存储中，实际上是创建一个以 '/' 结尾的对象）
     *
     * @param bucketName 存储桶名称
     * @param folderName 文件夹名称
     */
    public void createFolder(String bucketName, String folderName) throws Exception {
        if (!folderName.endsWith("/")) {
            folderName += "/";
        }

        // 创建一个空的对象来模拟文件夹
        minioClient.putObject(
                PutObjectArgs.builder().bucket(bucketName).object(folderName).stream(
                        new ByteArrayInputStream(new byte[]{}), 0, -1
                ).build()
        );
    }

    /**
     * 删除指定文件
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     */
    @SneakyThrows
    public void deleteFile(String bucketName, String objectName) {
        minioClient.removeObject(
                RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build()
        );
    }

    public boolean hasFolder(String bucketName, String folderName) {
        // 使用 listObjects 列出指定前缀的对象
        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket(bucketName)
                        .prefix(folderName)
                        .maxKeys(1)
                        .build()
        );
        Iterator<Result<Item>> iterator = results.iterator();
        return iterator.hasNext();
    }

    /**
     * 删除指定“文件夹”下的所有文件
     *
     * @param bucketName 存储桶名称
     * @param folderName 文件夹名称（前缀）
     */
    public void deleteFolderFiles(String bucketName, String folderName) throws Exception {
        if (!folderName.endsWith("/")) {
            folderName += "/";
        }

        // 列出该文件夹下的所有文件
        Iterable<Result<Item>> objects = minioClient.listObjects(
                ListObjectsArgs.builder().bucket(bucketName).prefix(folderName).recursive(true).build()
        );

        List<DeleteObject> objectsToDelete = new ArrayList<>();
        for (Result<Item> result : objects) {
            Item item = result.get();
            objectsToDelete.add(new DeleteObject(item.objectName()));
        }

        // 批量删除文件
        if (!objectsToDelete.isEmpty()) {
            Iterable<Result<DeleteError>> results = minioClient.removeObjects(RemoveObjectsArgs.builder().bucket(bucketName).objects(objectsToDelete).build());
            // 检查是否有删除失败的文件
            for (Result<DeleteError> errorResult : results) {
                DeleteError error = errorResult.get();
                log.error("Failed to delete object: {} - {}", error.objectName(), error.message());
            }
        }
    }

    public long mergeChunks(String bucketName, String fileName, List<ComposeSource> sources) throws Exception {
        minioClient.composeObject(
                ComposeObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .sources(sources)
                        .build()
        );
        // 获取合并后文件的大小
        StatObjectResponse finalFileStat = minioClient.statObject(
                StatObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .build()
        );
        return finalFileStat.size();
    }

}