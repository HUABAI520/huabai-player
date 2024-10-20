package com.ithe.huabaiplayer.player.service.impl;

import cn.hutool.core.io.FileUtil;
import com.github.xiaoymin.knife4j.core.util.CollectionUtils;
import com.ithe.huabaiplayer.common.ErrorCode;
import com.ithe.huabaiplayer.common.callback.UpdateCallback;
import com.ithe.huabaiplayer.common.exception.BusinessException;
import com.ithe.huabaiplayer.common.utils.BeanHuaUtil;
import com.ithe.huabaiplayer.common.utils.HuaUtils;
import com.ithe.huabaiplayer.common.utils.SqlUtils;
import com.ithe.huabaiplayer.file.factory.FileFactory;
import com.ithe.huabaiplayer.file.model.entity.FileNodes;
import com.ithe.huabaiplayer.file.service.FileNodesService;
import com.ithe.huabaiplayer.file.service.FileStorage;
import com.ithe.huabaiplayer.player.mapper.AnimeIndexMapper;
import com.ithe.huabaiplayer.player.model.dto.anime.AnimeAddReq;
import com.ithe.huabaiplayer.player.model.dto.anime.AnimeIndexResp;
import com.ithe.huabaiplayer.player.model.dto.anime.AnimeQueryReq;
import com.ithe.huabaiplayer.player.model.dto.anime.AnimeUpdateReq;
import com.ithe.huabaiplayer.player.model.dto.animeVideos.AnimeVideosResp;
import com.ithe.huabaiplayer.player.model.entity.AnimeIndex;
import com.ithe.huabaiplayer.player.model.entity.AnimePlayCounts;
import com.ithe.huabaiplayer.player.model.entity.AnimeVideos;
import com.ithe.huabaiplayer.player.model.entity.HuaAnimeType;
import com.ithe.huabaiplayer.player.model.entity.HuaType;
import com.ithe.huabaiplayer.player.model.prefix.PictureProperties;
import com.ithe.huabaiplayer.player.model.prefix.PlayerProperties;
import com.ithe.huabaiplayer.player.model.vo.AnimeIndexVo;
import com.ithe.huabaiplayer.player.service.AnimeIndexService;
import com.ithe.huabaiplayer.player.service.AnimePlayCountsService;
import com.ithe.huabaiplayer.player.service.AnimeVideosService;
import com.ithe.huabaiplayer.player.service.HuaAnimeTypeService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.util.UpdateEntity;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.ithe.huabaiplayer.common.utils.HuaUtils.reBuilderPath;
import static com.ithe.huabaiplayer.file.model.enums.FileTypeEnum.FILE;
import static com.ithe.huabaiplayer.file.model.enums.FileTypeEnum.FOLDER;
import static com.ithe.huabaiplayer.player.model.entity.table.AnimeIndexTableDef.ANIME_INDEX;
import static com.ithe.huabaiplayer.player.model.entity.table.HuaAnimeTypeTableDef.HUA_ANIME_TYPE;
import static com.ithe.huabaiplayer.player.model.entity.table.HuaTypeTableDef.HUA_TYPE;

/**
 * 动漫表 服务层实现。
 *
 * @author L
 * @since 2024-08-28
 */
@Service
@Slf4j
public class AnimeIndexServiceImpl extends ServiceImpl<AnimeIndexMapper, AnimeIndex> implements AnimeIndexService {

    public static final String FILE_SPLIT = "/";
    @Autowired
    private PlayerProperties playerProperties;
    @Autowired
    private FileNodesService fileNodesService;
    @Autowired
    private AnimeIndexMapper animeIndexMapper;
    @Autowired
    private AnimeVideosService animeVideosService;
    @Autowired
    private HuaAnimeTypeService huaAnimeTypeService;
    @Autowired
    private PictureProperties pictureProperties;
    @Autowired
    private FileFactory fileFactory;
    @Autowired
    private AnimePlayCountsService countsService;


    private FileStorage fileService() {
        return fileFactory.getFileService();
    }

    @Override
    public Page<AnimeIndexResp> findAll(AnimeQueryReq animeQueryReq) {
        long pageSize = animeQueryReq.getPageSize();
        long pageNum = animeQueryReq.getCurrent();

        Page<AnimeIndexVo> animeIndexVoPage = this.pageAs(new Page<>(pageNum, pageSize),
                getQueryWrapper(animeQueryReq), AnimeIndexVo.class);
        Page<AnimeIndexResp> page = new Page<>();
        BeanHuaUtil.copyIgnoreFields(animeIndexVoPage, page, List.of("records"));
        List<AnimeIndexVo> records = animeIndexVoPage.getRecords();
        // 根据id去重
        // 如果需要保持原有顺序，可以使用以下方式：
        List<AnimeIndexResp> deduplicatedRecordsWithOrder = records.stream()
                .map(r -> {
                    String image = r.getImage();
                    if (StringUtils.isNotBlank(image)) {
//                        r.setImage(pictureProperties.getIpPath() + image);
                        r.setImage(fileService().getImageUrl(image));
                    }
                    return AnimeIndexVo.of(r);
                }).toList();
        page.setRecords(deduplicatedRecordsWithOrder);
        return page;
    }

    @Override
    public AnimeIndexResp getAnimeById(Long id, Long userId) {
        AnimeIndexResp resp = getAnimeIndexResp(id, userId);
        Long playCount = countsService.getPlayCount(resp.getId());
        resp.setPlayCount(playCount);
        return resp;
    }

    @NotNull
    // todo 添加缓存
    public AnimeIndexResp getAnimeIndexResp(Long id, Long userId) {
        AnimeIndexVo animeIndexVoById = animeIndexMapper.getAnimeIndexVoById(id, userId);
        String image = animeIndexVoById.getImage();
        if (StringUtils.isNotBlank(image)) {
            animeIndexVoById.setImage(fileService().getImageUrl(image));
        }
        List<AnimeVideos> list = animeVideosService.list(query().eq(AnimeVideos::getAnimeId, id));
        // list 按照 rank 升序 次按照 crateTime 升序
        List<AnimeVideosResp> list1 = list.stream().map((l) -> {
                    String lImage = l.getImage();
                    if (StringUtils.isNotBlank(lImage)) {
                        l.setImage(fileService().getImageUrl(lImage));
                    }
                    return AnimeVideosResp.of(l);
                })
                .sorted(Comparator.comparingInt(AnimeVideosResp::getRank)
                        .thenComparing(AnimeVideosResp::getCrateTime)).toList();
        AnimeIndexResp resp = AnimeIndexVo.of(animeIndexVoById);
        resp.setVideos(list1);
        return resp;
    }

    @Override
    public List<AnimeIndexResp> getRecommendAnime() {
        // 随机获取 限制25个
        List<AnimeIndexVo> animeIndices = animeIndexMapper.selectRandomly(25);
        return animeIndices.stream().map(AnimeIndexVo::of).toList();
    }


    public QueryWrapper getQueryWrapper(AnimeQueryReq animeQueryReq) {
        String name = animeQueryReq.getName();
        Integer month = animeQueryReq.getMonth();
        Integer isNew = animeQueryReq.getIsNew();
        String actRole = animeQueryReq.getActRole();
        String director = animeQueryReq.getDirector();
        String language = animeQueryReq.getLanguage();
        Integer type = animeQueryReq.getType();
        String kind = animeQueryReq.getKind();
        Date startTime = animeQueryReq.getStartTime();
        Date endTime = animeQueryReq.getEndTime();
        String sortField = animeQueryReq.getSortField();
        String sortOrder = animeQueryReq.getSortOrder();
        QueryWrapper queryWrapper = query()
                .select(ANIME_INDEX.ALL_COLUMNS)
                .from(ANIME_INDEX);
        if (StringUtils.isNotBlank(kind)) {
            queryWrapper.leftJoin(HUA_ANIME_TYPE).on(ANIME_INDEX.ID.eq(HUA_ANIME_TYPE.ANIME_ID))
                    .leftJoin(HUA_TYPE).on(HUA_ANIME_TYPE.TYPE_ID.eq(HUA_TYPE.ID));
        }
        queryWrapper.like(AnimeIndex::getName, name, StringUtils.isNotBlank(name))
                .eq(AnimeIndex::getMonth, month, month != null)
                .eq(AnimeIndex::getIsNew, isNew, isNew != null)
                .like(AnimeIndex::getActRole, actRole, StringUtils.isNotBlank(actRole))
                .like(AnimeIndex::getDirector, director, StringUtils.isNotBlank(director))
                .like(AnimeIndex::getLanguage, language, StringUtils.isNotBlank(language))
                .eq(AnimeIndex::getType, type, type != null)
                .eq(HuaType::getType, kind, StringUtils.isNotBlank(kind))
                .between(AnimeIndex::getIssueTime, startTime, endTime, startTime != null && endTime != null);
        if (SqlUtils.validSortField(sortField)) {
            Boolean order = "ASC".equals(sortOrder);
            queryWrapper.orderBy(sortField, order);
        }
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(AnimeAddReq animeAddReq, MultipartFile multipartFile) {
        String fileSuffix = null;
        if (Objects.nonNull(multipartFile)) {
            fileSuffix = HuaUtils.validPictureFile(multipartFile);
        }
        String name = animeAddReq.getName();
        // 去掉前后空格
        name = name.trim();
        boolean exists = this.exists(query().eq(AnimeIndex::getName, name));
        if (exists) {
            throw new BusinessException(ErrorCode.EXIST_ERROR, "该动漫名已存在~");
        }
        animeAddReq.setName(name);
        Long fileId = animeAddReq.getFileId();
        fileId = Objects.isNull(fileId) ? 1L : fileId;
        // 存入数据库的路径
        String path = getPath(fileId);
        path += "/" + name;
        FileNodes fileNodes = FileNodes.builder()
                .parentId(fileId)
                .name(name)
                .fullPath(path)
                .nodeType(FOLDER.getType())
                .build();
        // 保存的路径
//        String savePath = playerProperties.getPath(path);
        String savePath = fileService().getVideoPath(path);
        AnimeIndex animeIndex = AnimeAddReq.toAnimeIndex(animeAddReq);
        List<Integer> kindIds = animeAddReq.getKindIds();
        // 在path下创建用name 命名的文件夹
        return executeAddAnime(animeIndex, fileNodes, savePath, kindIds, fileSuffix, multipartFile);
    }

    private String getPath(Long fileId) {
        String path;
        if (Objects.isNull(fileId)) {
            path = getDbPath(1L);
        } else {
            path = getDbPath(fileId);
        }
        return path;
    }

    private String getDbPath(Long fileId) {
        String path;
        FileNodes byId = fileNodesService.getById(fileId);
        if (Objects.isNull(byId)) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "文件不存在");
        }
        String nodeType = byId.getNodeType();
        if (nodeType.equals(FILE.getType())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请选择文件夹");
        }
        path = byId.getFullPath();
        return path;
    }


    @Transactional(rollbackFor = Exception.class)
    public Long executeAddAnime(AnimeIndex animeIndex, FileNodes fileNodes, String savePath,
                                List<Integer> kindIds, String fileSuffix, MultipartFile multipartFile) {

        fileNodesService.save(fileNodes);
        // 保存成功后获取到id
        Long fId = fileNodes.getId();
        animeIndex.setFolder(fId);
        boolean save = this.save(animeIndex);
        if (save) {
            countsService.save(AnimePlayCounts.builder().animeId(animeIndex.getId()).build());
            Long id = animeIndex.getId();
            // 视频图片
            if (StringUtils.isNotBlank(fileSuffix)) {
                // 已废弃
//                createPicture(fileSuffix, multipartFile, id);
            }
            if (CollectionUtils.isNotEmpty(kindIds)) {
                List<HuaAnimeType> huaAnimeTypes = kindIds.stream().map(kindId -> HuaAnimeType.builder()
                        .animeId(id)
                        .typeId(kindId)
                        .build()).toList();
                huaAnimeTypeService.saveBatch(huaAnimeTypes);
            }
            // 创建 savePath 文件夹 创建失败报错 存在报错
            try {
                boolean isCreated = fileService().createAnimeFolder(savePath);
                if (!isCreated) {
                    throw new BusinessException(ErrorCode.PARAMS_ERROR, "创建文件夹失败~");
                }
                return id;
            } catch (Exception e) {
                log.error("创建文件夹失败~", e);
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "创建文件夹失败~ : " + e.getMessage());
            }
        }
        return null;
    }


    /**
     * 更新图片
     *
     * @param s        -- 文件名 (/.../..)
     * @param fileId   所在文件夹下的id
     * @param callback 回调函数
     */
    @Transactional(rollbackFor = Exception.class)
    public String updatePicture(String s, Long id, MultipartFile multipartFile, Long fileId, UpdateCallback callback) {
        FileNodes file = fileNodesService.getFile(fileId);
        String fullPath = file.getFullPath();
        String url = fullPath + s;
        boolean b = callback.update(url);
        if (!b) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新失败,该动漫/视频不存在~");
        }
        return fileService().createPic(s, fullPath, multipartFile, id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void createPicture(String fileSuffix, MultipartFile multipartFile, Long id) {
        String filename = id + "." + fileSuffix;
        String s = FILE_SPLIT + id + FILE_SPLIT + filename;
        String filepath = pictureProperties.getDongmanPath() + s;
        String url = pictureProperties.getDongman() + s;
//        String url = pictureProperties.getDongmanUrl() + s;
        setPicture(filepath, url, multipartFile, id);
    }


    @Transactional(rollbackFor = Exception.class)
    public String createPictureJi(String fileSuffix, MultipartFile multipartFile, Long animeId, Long videoId) {
        String filename = animeId + "_" + videoId + "." + fileSuffix;
        String s = FILE_SPLIT + animeId + FILE_SPLIT + filename;
        String filepath = pictureProperties.getDongmanPath() + s;
        String url = pictureProperties.getDongman() + s;
//        String url = pictureProperties.getDongmanUrl() + s;
        setPictureJi(filepath, url, multipartFile, videoId);
        return pictureProperties.getIpPath() + url;
    }

    @Transactional(rollbackFor = Exception.class)
    public void setPictureJi(String filepath, String url, MultipartFile multipartFile, Long videoId) {
        if (multipartFile.isEmpty()) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "文件为空");
        }
        try {
            File file = new File(filepath);
            boolean b = animeVideosService.updateChain().set(AnimeVideos::getImage, url)
                    .where(queryWrapper -> {
                        queryWrapper.eq(AnimeVideos::getId, videoId);
                    }).update();
            if (!b) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新失败,该动漫视频不存在~");
            }
            if (!file.getParentFile().exists()) {
                FileUtil.mkParentDirs(file);
            }
            FileUtil.writeFromStream(multipartFile.getInputStream(), file, true);
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "文件保存失败:" + e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void setPicture(String filepath, String url, MultipartFile multipartFile, Long id) {

        // 保存文件到本地
        if (multipartFile.isEmpty()) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "文件为空");
        }
        try {
            File file = new File(filepath);
            AnimeIndex animeIndex = UpdateEntity.of(AnimeIndex.class, id);
            animeIndex.setImage(url);
            boolean b = this.updateById(animeIndex);
            if (!b) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新失败,该动漫不存在~");
            }
            if (!file.getParentFile().exists()) {
                FileUtil.mkParentDirs(file);
            }
            FileUtil.writeFromStream(multipartFile.getInputStream(), file, true);
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "文件保存失败:" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean update(AnimeUpdateReq animeUpdateReq) {
        Long id = animeUpdateReq.getId();
        String name = animeUpdateReq.getName();
        List<Integer> kindIds = animeUpdateReq.getKindIds();
        AnimeIndex animeIndex = AnimeUpdateReq.toAnimeIndex(animeUpdateReq);
        if (Objects.nonNull(name)) {
            String nameNew = name.trim();
            AnimeIndex old = this.getById(id);
            if (!nameNew.equals(old.getName())) {
                if (exists(query().eq(AnimeIndex::getName, nameNew))) {
                    throw new BusinessException(ErrorCode.EXIST_ERROR, "该动漫名已存在~");
                } else if ("minio".equals(fileFactory.getUse())) {
                    executeUpdate(animeIndex, kindIds, id);
                    return true;
                } else {
                    Long folder = old.getFolder();
                    FileNodes byId = fileNodesService.getById(folder);
                    String path = byId.getFullPath();
                    String pathNew = reBuilderPath(path, nameNew);
                    // 更新成功
                    FileNodes fileNodes = FileNodes.builder()
                            .id(folder)
                            .name(nameNew)
                            .fullPath(pathNew)
                            .nodeType(FOLDER.getType())
                            .parentId(byId.getParentId())
                            .build();
                    return executeUpdate(animeIndex, fileNodes, path, nameNew, kindIds);
                }
            }
        }
        executeUpdate(animeIndex, kindIds, id);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateAnimePicture(Long id, MultipartFile multipartFile, Long fileId) {
        String fileSuffix = HuaUtils.validPictureFile(multipartFile);
//        createPicture(fileSuffix, multipartFile, id);
        String filename = id + "." + fileSuffix;
        String s = FILE_SPLIT + id + FILE_SPLIT + filename;
        updatePicture(s, id, multipartFile, fileId, url -> {
            AnimeIndex animeIndex = UpdateEntity.of(AnimeIndex.class, id);
            animeIndex.setImage(url);
            return this.updateById(animeIndex);
        });
//       createPicture(fileSuffix, multipartFile, id);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String uploadVideoPicture(Long animeId, Long videoId, MultipartFile multipartFile, Long fileId) {
        String fileSuffix = HuaUtils.validPictureFile(multipartFile);
        String filename = animeId + "_" + videoId + "." + fileSuffix;
        String s = FILE_SPLIT + animeId + FILE_SPLIT + filename;
        return updatePicture(s, videoId, multipartFile, fileId, url ->
                animeVideosService.updateChain().set(AnimeVideos::getImage, url)
                        .where(queryWrapper -> {
                            queryWrapper.eq(AnimeVideos::getId, videoId);
                        }).update());
//        return createPictureJi(fileSuffix, multipartFile, animeId, videoId);
    }


    @Transactional(rollbackFor = Exception.class)
    public void executeUpdate(AnimeIndex animeIndex, List<Integer> kindIds, Long id) {
        boolean update = this.updateById(animeIndex);
        if (!update) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "更新失败~");
        }
        updateKinds(kindIds, id);
    }


    @Transactional(rollbackFor = Exception.class)
    public Boolean executeUpdate(AnimeIndex animeIndex, FileNodes fileNodes,
                                 String path, String nameNew, List<Integer> kindIds) {
        boolean update = this.updateById(animeIndex);
        if (!update) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "更新失败~");
        }
        boolean b = fileNodesService.updateById(fileNodes);
        if (!b) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "更新失败~");
        }
        updateKinds(kindIds, animeIndex.getId());
        // 保存的路径
        String savePath = playerProperties.getPath(path);
        // 更新对应文件夹的名字
        // 将路径字符串转换为 Path 对象
        fileService().updateFolderName(savePath, nameNew);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateKinds(List<Integer> kindIds, Long id) {
        if (CollectionUtils.isNotEmpty(kindIds)) {
            List<HuaAnimeType> huaAnimeTypes = kindIds.stream().map(kindId -> HuaAnimeType.builder()
                    .animeId(id)
                    .typeId(kindId)
                    .build()).toList();
            huaAnimeTypeService.remove(query().eq(HuaAnimeType::getAnimeId, id));
            huaAnimeTypeService.saveBatch(huaAnimeTypes);
        }
    }

    @Override
    public List<AnimeIndexResp> list(String name) {
        return listAs(query()
                        .select(ANIME_INDEX.ID, ANIME_INDEX.NAME)
                        .from(ANIME_INDEX)
                        .like(AnimeIndex::getName, name, StringUtils.isNotBlank(name))
                        .limit(10).orderBy(AnimeIndex::getIssueTime, false),
                AnimeIndexResp.class);
    }
}
