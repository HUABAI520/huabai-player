//package com.ithe.huabaiplayer.player.service.handler;
//
//import com.amazonaws.AmazonClientException;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
//import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
//import com.amazonaws.services.s3.model.InitiateMultipartUploadResult;
//import com.amazonaws.services.s3.model.ListPartsRequest;
//import com.amazonaws.services.s3.model.PartETag;
//import com.amazonaws.services.s3.model.PartListing;
//import com.amazonaws.services.s3.model.PartSummary;
//import com.amazonaws.services.s3.model.UploadPartRequest;
//import com.ithe.huabaiplayer.common.ErrorCode;
//import com.ithe.huabaiplayer.common.exception.BusinessException;
//import com.ithe.huabaiplayer.common.service.RedisService;
//import com.ithe.huabaiplayer.player.model.dto.animeVideos.FenPian;
//import com.ithe.huabaiplayer.player.model.entity.AnimeVideos;
//import com.ithe.huabaiplayer.file.model.entity.FileNodes;
//import com.ithe.huabaiplayer.player.service.AnimeVideosService;
//import lombok.RequiredArgsConstructor;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.ithe.huabaiplayer.common.constant.RedisKeyConstants.DONGMAN_INDEX;
//
//@Service
//@RequiredArgsConstructor
//public class S3MultipartUploadService {
//
//    private final AmazonS3 s3Client;
//    private final RedisService redisService;
//    private final VideoService videoService;
//    private final AnimeVideosService animeVideosService;
//    private static final long MAX_PART_SIZE = 200L * 1024 * 1024; // 200MB 分片大小
//    private static final String UPLOAD_PREFIX = "upload:s3:";
//    public static final int TWO_HOURS = 60 * 2;
//
//    public String getKey(String fullPath, String fileName) {
//        return fullPath + "/" + fileName;
//    }
//
//    public Integer uploadVideo(MultipartFile file, Long animeId,
//                               Long videoId, String fileSuffix, String fileName,
//                               Integer partNumber, Integer total) {
//        FileNodes fullNode = videoService.getFullPath(animeId);
//        String fullPath = fullNode.getFullPath();
//
//        if (StringUtils.isBlank(fullPath)) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR, "所在动漫不存在~");
//        }
//
//        // 最大分片大小 (200MB)
//        final long MAX_PART_SIZE = 200L * 1024 * 1024;
//
//        try {
//            // 使用 S3 上传
//            String key = getKey(fullPath, fileName);
//            String uploadId = redisService.get(key + "-uploadId") == null ? null : redisService.get(key + "-uploadId").toString();
//
//            // 如果是第一次上传，需要初始化多部分上传
//            if (uploadId == null) {
//                uploadId = initMultipartUpload(key);
//                redisService.set(key + "-uploadId", uploadId);
//                redisService.expire(key + "-uploadId", TWO_HOURS);
//            }
//
//            // 上传分片
//            uploadPart(file, key, partNumber, uploadId);
//
//            FenPian o = redisService.get(key) == null ? null : (FenPian) redisService.get(key);
//            if (o == null) {
//                if (partNumber != 1 && total != 1) {
//                    return -1;
//                }
//                o = new FenPian();
//                o.setPartNumber(partNumber);
//                o.setTotalParts(total);
//                o.setKey(key);
//                if (total != 1) {
//                    redisService.set(key, o);
//                    redisService.expire(key, TWO_HOURS);
//                } else {
//                    completeMultipartUpload(animeId, fullNode, fileName, fileSuffix, videoId, uploadId, key, total);
//                }
//                return partNumber;
//            }
//
//            if (o.getTotalParts() == null || !o.getTotalParts().equals(total) || partNumber > total) {
//                throw new BusinessException(ErrorCode.PARAMS_ERROR, "分片索引或总数量错误~");
//            }
//
//            if (o.getPartNumber() + 1 != partNumber) {
//                if (partNumber.equals(1)) {
//                    o.setPartNumber(partNumber);
//                    o.setTotalParts(total);
//                    redisService.set(key, o);
//                    redisService.expire(key, TWO_HOURS);
//                    return partNumber;
//                }
//                throw new BusinessException(ErrorCode.PARAMS_ERROR, "分片索引错误~");
//            }
//
//            o.setPartNumber(partNumber);
//            if (partNumber < total) {
//                redisService.set(key, o);
//                redisService.expire(key, TWO_HOURS);
//                return partNumber;
//            }
//            completeMultipartUpload(animeId, fullNode, fileName, fileSuffix, videoId, uploadId, key, total);
//        } catch (IOException e) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR, "保存分片" + partNumber + "失败~");
//        }
//
//        return partNumber;
//    }
//
//    public void completeMultipartUpload(Long animeId, FileNodes fileNode, String fileName,
//                                        String fileSuffix, Long videoId, String uploadId,
//                                        String key, int totalParts) {
//        try {
//            // 合并分片
//            List<PartETag> partETags = listParts(uploadId, key, totalParts);
//
//            CompleteMultipartUploadRequest completeRequest = new CompleteMultipartUploadRequest()
//                    .withBucketName("huaplayer")
//                    .withKey(key)
//                    .withUploadId(uploadId)
//                    .withPartETags(partETags);
//
//            s3Client.completeMultipartUpload(completeRequest);
//
//            Long fileId = videoService.saveFile(fileNode.getId(), fileNode.getFullPath() + "/" + fileName + fileSuffix,
//                    fileSuffix.replace(".", ""), fileName);
//            if (fileId == null) {
//                throw new BusinessException(ErrorCode.PARAMS_ERROR, "保存文件失败~");
//            }
//
//            boolean update = animeVideosService.updateChain()
//                    .set(AnimeVideos::getFile, fileId)
//                    .eq(AnimeVideos::getId, videoId)
//                    .eq(AnimeVideos::getAnimeId, animeId).update();
//            if (!update) {
//                throw new BusinessException(ErrorCode.PARAMS_ERROR, "更新视频信息失败~可能是该视频不在对应动漫下~");
//            }
//
//            // 清理缓存
//            redisService.delete(key);
//            redisService.delete(key + "-uploadId");
//            redisService.delete(DONGMAN_INDEX + animeId);
//        } catch (AmazonClientException e) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR, "合并分片失败~");
//        }
//    }
//
//    private String initMultipartUpload(String key) {
//        InitiateMultipartUploadRequest initRequest = new InitiateMultipartUploadRequest("huaplayer", key);
//        InitiateMultipartUploadResult initResponse = s3Client.initiateMultipartUpload(initRequest);
//        return initResponse.getUploadId();
//    }
//
//    private void uploadPart(MultipartFile file, String key, int partNumber, String uploadId) throws IOException {
//        UploadPartRequest uploadRequest = new UploadPartRequest()
//                .withBucketName("huaplayer")
//                .withKey(key)
//                .withUploadId(uploadId)
//                .withPartNumber(partNumber)
//                .withInputStream(file.getInputStream())
//                .withPartSize(file.getSize());
//
//        s3Client.uploadPart(uploadRequest);
//    }
//
//    private List<PartETag> listParts(String uploadId, String key, int totalParts) {
//        ListPartsRequest listPartsRequest = new ListPartsRequest("huaplayer", key, uploadId);
//        PartListing partListing = s3Client.listParts(listPartsRequest);
//
//        List<PartETag> partETags = new ArrayList<>();
//        for (PartSummary partSummary : partListing.getParts()) {
//            partETags.add(new PartETag(partSummary.getPartNumber(), partSummary.getETag()));
//        }
//        return partETags;
//    }
//
//
//}
