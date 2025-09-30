package com.ithe.huabaiplayer.player.controller;

import com.ithe.huabaiplayer.common.BaseResponse;
import com.ithe.huabaiplayer.common.ResultUtils;
import com.ithe.huabaiplayer.common.annotation.AuthCheck;
import com.ithe.huabaiplayer.common.utils.UserContext;
import com.ithe.huabaiplayer.player.model.dto.anime.AnimeAddReq;
import com.ithe.huabaiplayer.player.model.dto.anime.AnimeIndexResp;
import com.ithe.huabaiplayer.player.model.dto.anime.AnimeQueryReq;
import com.ithe.huabaiplayer.player.model.dto.anime.AnimeUpdateReq;
import com.ithe.huabaiplayer.player.model.dto.animeVideos.AnimeVideosReq;
import com.ithe.huabaiplayer.player.model.dto.animeVideos.AnimeVideosResp;
import com.ithe.huabaiplayer.player.service.AnimeIndexService;
import com.ithe.huabaiplayer.player.service.handler.VideoService;
import com.ithe.huabaiplayer.user.model.vo.UserVO;
import com.mybatisflex.core.paginate.Page;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.ithe.huabaiplayer.common.constant.UserConstant.ADMIN_ROLE;


/**
 * @ClassName Anime
 * @Author l
 * @Date 2024/8/28 14:57
 **/
@RestController
@RequestMapping("/api-player/anime")
@Slf4j
public class AnimeController {
    @Autowired
    private AnimeIndexService animeIndexService;

    @Autowired
    private VideoService videoService;


    // 新增动漫
    @PostMapping(path = "/addAPicture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @AuthCheck(mustRole = ADMIN_ROLE)
    public BaseResponse<Long> addAnimeAndPicture(@RequestPart("file") MultipartFile multipartFile,
                                                 @Valid @RequestPart("animeAddReq") AnimeAddReq animeAddReq
    ) {
        return ResultUtils.success(animeIndexService.add(animeAddReq, multipartFile));
    }

    @PostMapping("/add")
    @AuthCheck(mustRole = ADMIN_ROLE)
    public BaseResponse<Long> addAnime(@RequestBody @Valid AnimeAddReq animeAddReq) {
        return ResultUtils.success(animeIndexService.add(animeAddReq, null));
    }

    // 新增对应动漫下的视频
    @PostMapping("/video/add")
    @AuthCheck(mustRole = ADMIN_ROLE)
    public BaseResponse<Long> addVideo(
            @RequestBody @Valid AnimeVideosReq animeVideosReq) {
        return ResultUtils.success(videoService.add(animeVideosReq));
    }

    // 获得动漫视频的信息
    @PostMapping("/video/get")
//    @AuthCheck(mustRole = ADMIN_ROLE)
    @AuthCheck
    public BaseResponse<AnimeVideosResp> get(Long videoId) {
        UserVO user = UserContext.getUser();
        return ResultUtils.success(videoService.get(videoId, user.getId()));
    }

    // 上传视频分片
    @PostMapping("/video/upload/{fileName}/{partNumber}/{total}")
    @AuthCheck(mustRole = ADMIN_ROLE)
    public BaseResponse<Integer> uploadVideo(
            @RequestPart("file") MultipartFile multipartFile, Long animeId, Long videoId,
            String fileSuffix,
            @PathVariable("fileName") String fileName,
            @PathVariable("partNumber") Integer partNumber,
            @PathVariable("total") Integer total, Integer duration) throws IOException {
        // 对路径字符串做trim()处理（去除首尾空格），并校验是否包含 Windows 非法字符（如:*?"<>|）。
        fileName = fileName.trim().replaceAll("[\\\\/:*?\"<>|]", "_");
        return ResultUtils.success(videoService.uploadVideo(
                multipartFile, animeId, videoId, fileSuffix, fileName, partNumber, total, duration));
//        return ResultUtils.success(s3MultipartUploadService.uploadVideo(
//                multipartFile, animeId, videoId, fileSuffix, fileName, partNumber, total));
    }

    // 获取继续上传的索引
    @GetMapping("/video/upload/{fileName}")
    @AuthCheck(mustRole = ADMIN_ROLE)
    public BaseResponse<Integer> getUploadIndex(Long animeId, @PathVariable("fileName") String fileName) {
        return ResultUtils.success(videoService.getUploadIndex(animeId, fileName));
    }

    // 删除对应视频id有的问视频文件
    @DeleteMapping("/video/delete/file")
    @AuthCheck(mustRole = ADMIN_ROLE)
    public BaseResponse<Boolean> deleteVideo(Long videoId) {
        return ResultUtils.success(videoService.deleteVideoFile(videoId));
    }


    @PutMapping("/update/picture/{id}")
    @AuthCheck(mustRole = ADMIN_ROLE)
    public BaseResponse<Boolean> updateAnimePicture(@PathVariable("id") Long id,
                                                    Long fileId,
                                                    @RequestPart("file") MultipartFile multipartFile) {
        if (fileId == null || fileId == 0L) {
            fileId = 1L;
        }
        return ResultUtils.success(animeIndexService.updateAnimePicture(id, multipartFile, fileId));
    }

    @PutMapping("/update/picture/{animeId}/{videoId}")
    @AuthCheck(mustRole = ADMIN_ROLE)
    public BaseResponse<String> uploadVideoPicture(@PathVariable("animeId") Long animeId,
                                                   @PathVariable("videoId") Long videoId,
                                                   Long fileId,
                                                   @RequestPart("file") MultipartFile multipartFile) {
        if (fileId == null || fileId == 0L) {
            fileId = 1L;
        }
        return ResultUtils.success(animeIndexService.uploadVideoPicture(animeId, videoId, multipartFile, fileId));
    }


    // 更新
    @PutMapping("/update")
    @AuthCheck(mustRole = ADMIN_ROLE)
    public BaseResponse<Boolean> updateAnime(@RequestBody @Valid AnimeUpdateReq animeUpdateReq) {
        return ResultUtils.success(animeIndexService.update(animeUpdateReq));
    }

    // 分页查询筛选动漫
    @PostMapping("/query")
    @AuthCheck
    public BaseResponse<Page<AnimeIndexResp>> queryAnime(@RequestBody AnimeQueryReq animeQueryReq) {
        return ResultUtils.success(animeIndexService.findAll(animeQueryReq));
    }

    // 通过id获取详细信息
    @GetMapping("/get")
    @AuthCheck
    public BaseResponse<AnimeIndexResp> getAnimeById(Long id) {
        UserVO user = UserContext.getUser();
        return ResultUtils.success(animeIndexService.getAnimeById(id, user.getId()));
    }

    // 首页推荐
    @GetMapping("/recommend")
    @AuthCheck
    public BaseResponse<List<AnimeIndexResp>> getRecommendAnime() {
        return ResultUtils.success(animeIndexService.getRecommendAnime());
    }

    // 列表
    @GetMapping("/list")
    @AuthCheck
    public BaseResponse<List<AnimeIndexResp>> list(String name) {
        return ResultUtils.success(animeIndexService.list(name));
    }

    // 更新对应视频的名称
    @PutMapping("/update/video/{videoId}")
    @AuthCheck(mustRole = ADMIN_ROLE)
    public BaseResponse<Boolean> updateVideoName(@PathVariable("videoId") Long videoId,
                                                 String name) {
        return ResultUtils.success(videoService.updateVideoName(videoId, name));
    }

    // 对应动漫下视频的排序
    @PutMapping("/update/video/sort/{animeId}")
    public BaseResponse<Boolean> updateVideoSort(@PathVariable("animeId") Long animeId,
                                                 @RequestBody List<Long> videoSortReqs) {
        return ResultUtils.success(animeIndexService.updateVideoSort(animeId, videoSortReqs));
    }
    // 删除对应视频信息
    @DeleteMapping("/video/delete/{videoId}")
    public BaseResponse<Boolean> deleteVideoMsg(@PathVariable("videoId") Long videoId) {
        return ResultUtils.success(videoService.deleteVideoFileMsg(videoId));
    }
}
