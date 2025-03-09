package com.ithe.huabaiplayer.player.service.handler;

import com.ithe.huabaiplayer.common.ErrorCode;
import com.ithe.huabaiplayer.common.exception.BusinessException;
import com.ithe.huabaiplayer.common.service.RedisService;
import com.ithe.huabaiplayer.common.utils.BeanHuaUtil;
import com.ithe.huabaiplayer.file.factory.FileFactory;
import com.ithe.huabaiplayer.file.model.entity.FileNodes;
import com.ithe.huabaiplayer.file.service.FileNodesService;
import com.ithe.huabaiplayer.file.service.FileStorage;
import com.ithe.huabaiplayer.player.model.dto.animeVideos.AnimeVideosReq;
import com.ithe.huabaiplayer.player.model.dto.animeVideos.AnimeVideosResp;
import com.ithe.huabaiplayer.player.model.dto.animeVideos.FenPian;
import com.ithe.huabaiplayer.player.model.dto.videoRecord.VideoRecordAddReq;
import com.ithe.huabaiplayer.player.model.dto.videoRecord.VideoRecordQueryReq;
import com.ithe.huabaiplayer.player.model.dto.videoRecord.VideoRecordResp;
import com.ithe.huabaiplayer.player.model.entity.AnimeIndex;
import com.ithe.huabaiplayer.player.model.entity.AnimeVideos;
import com.ithe.huabaiplayer.player.model.entity.VideoRecord;
import com.ithe.huabaiplayer.player.model.entity.VideoRecordLatest;
import com.ithe.huabaiplayer.player.model.prefix.PictureProperties;
import com.ithe.huabaiplayer.player.model.prefix.PlayerProperties;
import com.ithe.huabaiplayer.player.service.AnimeIndexService;
import com.ithe.huabaiplayer.player.service.AnimeVideosService;
import com.ithe.huabaiplayer.player.service.VideoRecordLatestService;
import com.ithe.huabaiplayer.player.service.VideoRecordService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateChain;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.ithe.huabaiplayer.common.constant.RedisKeyConstants.DONGMAN_FENPIAN;
import static com.ithe.huabaiplayer.common.constant.RedisKeyConstants.DONGMAN_INDEX;
import static com.ithe.huabaiplayer.file.model.entity.table.FileNodesTableDef.FILE_NODES;
import static com.ithe.huabaiplayer.file.model.enums.FileTypeEnum.FILE;
import static com.ithe.huabaiplayer.player.model.entity.table.AnimeIndexTableDef.ANIME_INDEX;
import static com.ithe.huabaiplayer.player.model.entity.table.AnimeVideosTableDef.ANIME_VIDEOS;
import static com.ithe.huabaiplayer.player.model.entity.table.VideoRecordLatestTableDef.VIDEO_RECORD_LATEST;
import static com.ithe.huabaiplayer.player.model.entity.table.VideoRecordTableDef.VIDEO_RECORD;


/**
 * @author L
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class VideoService {

    private final AnimeVideosService animeVideosService;
    private final AnimeIndexService animeIndexService;
    private final PlayerProperties playerProperties;
    private final FileNodesService fileNodesService;
    private final RedisService redisService;
    private final PictureProperties pictureProperties;
    private final VideoRecordService videoRecordService;
    private final VideoRecordLatestService videoRecordLatestService;
    private final FileFactory fileFactory;

    private FileStorage fileService() {
        return fileFactory.getFileService();
    }


    public Long add(AnimeVideosReq animeVideosReq) {
        Long animeId = animeVideosReq.getAnimeId();
        // 校验animeId 是否存在
        AnimeIndex id = animeIndexService.getById(animeId);
        if (id == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "所在动漫不存在~");
        }
        String title = animeVideosReq.getTitle();
        Integer rank = animeVideosReq.getRank();
        AnimeVideos a = AnimeVideos.builder().animeId(animeId).title(title).rank(rank).build();
        return animeVideosService.save(a) ? a.getId() : null;
    }

    public AnimeVideosResp get(Long videoId, Long userId) {
        AnimeVideosResp one = animeVideosService.getOneAs(new QueryWrapper()
                .select(ANIME_VIDEOS.ALL_COLUMNS, FILE_NODES.ID, FILE_NODES.FULL_PATH.as("fileUrl"), VIDEO_RECORD.SEEK)
                .from(ANIME_VIDEOS)
                .leftJoin(FILE_NODES).on(ANIME_VIDEOS.FILE.eq(FILE_NODES.ID))
                .leftJoin(VIDEO_RECORD).on(VIDEO_RECORD.VIDEO_ID.eq(ANIME_VIDEOS.ID).and(VIDEO_RECORD.USER_ID.eq(userId)))
                .where(queryWrapper -> {
                    queryWrapper.eq(AnimeVideos::getId, videoId);
                }), AnimeVideosResp.class);
        String fileUrl = one.getFileUrl();
        String image = one.getImage();
        if (StringUtils.isNotBlank(image)) {
            one.setImage(fileService().getImageUrl(image));
        }
        if (StringUtils.isNotBlank(fileUrl)) {
//            one.setFileUrl(playerProperties.getIpPath(fileUrl));
            one.setFileUrl(fileService().getVideoUrl(fileUrl));
        }
        return one;
    }

    public String getKey(Path dirPath) {
        return DONGMAN_FENPIAN + dirPath.toString();
    }

    public Integer getUploadIndex(Long animeId, String fileName) {
        FileNodes fullNode = getFullPath(animeId);
        String fullPath = fullNode.getFullPath();
        if (StringUtils.isBlank(fullPath)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "所在动漫不存在~");
        }
        String path = playerProperties.getPath(fullPath);
        Path dirPath = Paths.get(path + "/" + fileName);
        String key = getKey(dirPath);
        FenPian o = redisService.get(key) == null ? null : (FenPian) redisService.get(key);
        // 因为保存时已经上传的索引+1 故直接返回 为下一次的索引
        return o == null ? 0 : o.getPartNumber();
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer uploadVideo(MultipartFile file, Long animeId,
                               Long videoId, String fileSuffix, String fileName,
                               Integer partNumber, Integer total, Integer duration) {
        FileNodes fullNode = getFullPath(animeId);
        String fullPath = fullNode.getFullPath();
        // 通常自己不会遇到这个上传了视频前端还能上传
        // todo 后面扩展多人上传且可能重复上传时做对应视频校验 （或者 加分布式锁）
        //  且加入将ip加入vaule 中 若存在key 但是ip不同则表示已经有人在上传
        if (StringUtils.isBlank(fullPath)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "所在动漫不存在~");
        }

        // 最大分片大小 (100MB)
        final long MAX_PART_SIZE = 100L * 1024 * 1024;

        Integer x = fileService().uploadFen(file, animeId, videoId, fileSuffix, fileName,
                partNumber, total, fullPath, MAX_PART_SIZE, fullNode);
        // 不为null 表示未上传全部分片
        if (x != null) {
            return x;
        }
        mergeVideo(animeId, fullNode, fileName, fileSuffix, videoId);
        // 合并成功后更新动漫视频时长
        if (duration != null && duration > 0) {
            animeVideosService.updateChain()
                    .eq(AnimeVideos::getId, videoId)
                    .set(AnimeVideos::getDuration, duration).update();
        }
        return partNumber;
    }


    public FileNodes getFullPath(Long animeId) {
        FileNodes o = (FileNodes) redisService.get(DONGMAN_INDEX + animeId);
        if (o != null) {
            return o;
        }
        o = animeIndexService.getOneAs(QueryWrapper.create()
                .select(FILE_NODES.FULL_PATH, FILE_NODES.ID)
                .from(ANIME_INDEX)
                .leftJoin(FILE_NODES).on(ANIME_INDEX.FOLDER.eq(FILE_NODES.ID))
                .where(ANIME_INDEX.ID.eq(animeId)), FileNodes.class);
        if (o == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "所在动漫不存在~");
        }
        redisService.set(DONGMAN_INDEX + animeId, o);
        redisService.expire(DONGMAN_INDEX + animeId, 5);
        return o;
    }

    @Transactional(rollbackFor = Exception.class)
    public Long mergeVideo(Long animeId, FileNodes fileNode, String fileName, String fileSuffix, Long videoId) {
        String fullPath = fileNode.getFullPath();
        Long id = fileNode.getId();
        if (StringUtils.isBlank(fullPath)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "所在动漫不存在~");
        }
//        String path = playerProperties.getPath(fullPath);

        String path = fileService().getVideoPath(fullPath);
        String dirPath = path + "/" + fileName;
        String finalPath = dirPath + fileSuffix;

//        String path = fileService().getImageUrl(fullPath);
//        String s = path + "/" + fileName;
//        Path dirPath = Paths.get(s);
//        Path finalPath = Paths.get(s + fileSuffix);
        Long fileId = saveFile(id, fullPath + "/" + fileName + fileSuffix, fileSuffix.replace(".", ""), fileName);
        if (fileId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "保存文件失败~");
        }
        boolean update = animeVideosService.updateChain()
                .set(AnimeVideos::getFile, fileId)
                .eq(AnimeVideos::getId, videoId)
                .eq(AnimeVideos::getAnimeId, animeId).update();
        if (!update) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "更新视频信息失败~可能是该视频不在对应动漫下~");
        }
        long size;
        try {
            size = fileService().mergeFen(animeId, finalPath, dirPath, fileId);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "合并分片失败~:" + e.getMessage());
        }
        fileNodesService.updateChain().set(FileNodes::getSize, size).eq(FileNodes::getId, fileId).update();
        return videoId;
    }


    public Long saveFile(Long id, String s, String replace, String fileName) {
        FileNodes build = FileNodes.builder()
                .parentId(id)
                .name(fileName)
                .fullPath(s)
                .filePath(s)
                .fileType(replace)
                .nodeType(FILE.getType())
                .build();
        return fileNodesService.save(build) ? build.getId() : null;
    }


    public Boolean deleteVideoFile(Long videoId) {
        AnimeVideos videos = animeVideosService.getById(videoId);
        Long file = videos.getFile();
        if (file == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "该视频不存在文件~");
        }
        FileNodes fileNodes = fileNodesService.getById(file);
        if (fileNodes == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "该视频文件不存在~");
        }
        boolean b = fileNodesService.removeById(file);
        if (!b) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "删除文件失败~");
        }
        boolean update = animeVideosService.updateChain()
                .set(AnimeVideos::getFile, null)
                .eq(AnimeVideos::getId, videoId).update();
        if (!update) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "更新视频信息失败~");
        }
        String fullPath = fileNodes.getFullPath();
        return fileService().deleteVideoFile(fullPath);
    }

    /**
     * 之前是查看动漫id是否存在进行更新/新增 现在改为根据动漫id+视频id进行更新/新增
     */
    public Boolean addRecord(VideoRecordAddReq videoRecordAddReq, Long userId) {
//        VideoRecord record = BeanHuaUtil.copyIgnoreFields(videoRecordAddReq, VideoRecord.class);
//        record.setUserId(userId);
//        Long animeId = videoRecordAddReq.getAnimeId();
//        VideoRecord one = videoRecordService.getOne(new QueryWrapper().eq(VideoRecord::getAnimeId, animeId)
//                .eq(VideoRecord::getUserId, userId));
//        if (one != null) {
//            return videoRecordService.updateChain()
//                    .set(VideoRecord::getSeek, videoRecordAddReq.getSeek())
//                    .set(VideoRecord::getVideoId, videoRecordAddReq.getVideoId())
//                    .eq(VideoRecord::getUserId, userId)
//                    .eq(VideoRecord::getAnimeId, animeId).update();
//        } else {
//            return videoRecordService.save(record);
//        }
        VideoRecord record = BeanHuaUtil.copyIgnoreFields(videoRecordAddReq, VideoRecord.class);
        record.setUserId(userId);
        Long animeId = videoRecordAddReq.getAnimeId();
        Long videoId = videoRecordAddReq.getVideoId();
        VideoRecord one = videoRecordService.getOne(new QueryWrapper().eq(VideoRecord::getAnimeId, animeId)
                .eq(VideoRecord::getUserId, userId).eq(VideoRecord::getVideoId, videoId));
        if (one != null) {
            return videoRecordService.updateChain()
                    .set(VideoRecord::getSeek, videoRecordAddReq.getSeek())
                    .eq(VideoRecord::getUserId, userId)
                    .eq(VideoRecord::getAnimeId, animeId)
                    .eq(VideoRecord::getVideoId, videoId)
                    .update();
        } else {
            return videoRecordService.save(record);
        }
    }

    public Page<VideoRecordResp> listRecord(VideoRecordQueryReq videoRecordQueryReq, Long userId) {
        long current = videoRecordQueryReq.getCurrent();
        long pageSize = videoRecordQueryReq.getPageSize();

        Page<VideoRecordResp> page = videoRecordLatestService.pageAs(new Page<>(current, pageSize),
                getQueryWrapper(videoRecordQueryReq, userId), VideoRecordResp.class);
        List<VideoRecordResp> records = page.getRecords();
        for (VideoRecordResp record : records) {
            String image = record.getImage();
            if (StringUtils.isNotBlank(image)) {
//                record.setImage(pictureProperties.getIpPath() + image);
                record.setImage(fileService().getImageUrl(image));
            }
        }
        return page;
    }


    public QueryWrapper getQueryWrapper(VideoRecordQueryReq videoRecordQueryReq, Long userId) {
        String key = videoRecordQueryReq.getKey();
        QueryWrapper query = new QueryWrapper()
                .select(VIDEO_RECORD_LATEST.ALL_COLUMNS, ANIME_INDEX.NAME, ANIME_VIDEOS.RANK, ANIME_VIDEOS.TITLE, ANIME_VIDEOS.IMAGE, ANIME_VIDEOS.DURATION)
                .from(VIDEO_RECORD_LATEST)
                .leftJoin(ANIME_INDEX).on(VIDEO_RECORD_LATEST.ANIME_ID.eq(ANIME_INDEX.ID))
                .leftJoin(ANIME_VIDEOS).on(VIDEO_RECORD_LATEST.VIDEO_ID.eq(ANIME_VIDEOS.ID))
                .where(VIDEO_RECORD_LATEST.USER_ID.eq(userId))
                .orderBy(VIDEO_RECORD_LATEST.UPDATE_TIME.desc());
        if (StringUtils.isNotBlank(key)) {
            query.and(q -> {
                q.like(AnimeVideos::getTitle, key).or(q2 -> {
                    q2.like(AnimeIndex::getName, key);
                });
            });

        }
        return query;
    }

    public Boolean deleteRecord(Long recordId, Long userId) {
        return UpdateChain.of(VideoRecordLatest.class).eq(VideoRecordLatest::getId, recordId).eq(VideoRecordLatest::getUserId, userId).remove();
    }

    public Boolean deleteRecord(Long userId) {
        return UpdateChain.of(VideoRecordLatest.class).eq(VideoRecordLatest::getUserId, userId).remove();

    }
}
