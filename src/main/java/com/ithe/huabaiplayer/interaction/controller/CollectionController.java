package com.ithe.huabaiplayer.interaction.controller;

import com.ithe.huabaiplayer.common.BaseResponse;
import com.ithe.huabaiplayer.common.ResultUtils;
import com.ithe.huabaiplayer.common.annotation.AuthCheck;
import com.ithe.huabaiplayer.common.utils.UserContext;
import com.ithe.huabaiplayer.interaction.model.dto.req.CollectionsAddReq;
import com.ithe.huabaiplayer.interaction.model.dto.req.CollectionsUpdateReq;
import com.ithe.huabaiplayer.interaction.model.dto.resp.CollectionInResp;
import com.ithe.huabaiplayer.interaction.model.entity.Collections;
import com.ithe.huabaiplayer.interaction.service.handler.CollectionHandler;
import com.mybatisflex.core.paginate.Page;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName CollectionController
 * @Author hua bai
 * @Date 2024/11/30 20:38
 **/
@RestController
@RequestMapping("/api-player/collection")
@Slf4j
@RequiredArgsConstructor
public class CollectionController {
    private final CollectionHandler service;

    // 1. 分页查询用户拥有的收藏夹 分页大小固定20 并排序(排序 字段从小到大）
    @AuthCheck
    @GetMapping("/collections")
    public BaseResponse<Page<Collections>> collections(Integer current, Integer pageSize) {
        Long userId = UserContext.getUser().getId();
        return ResultUtils.success(service.pageCollections(current, pageSize, userId));
    }
    // 2. 分页查询收藏夹的内容 分页大小固定40 且可以根据关键词查询（暂定匹配动漫名）todo 扩展功能在所有的收藏夹搜索信息
    @AuthCheck
    @GetMapping("/collections/anime")
    public BaseResponse<Page<CollectionInResp>> collectionsAnime(Long cid, Integer current, Integer pageSize, String key) {
        Long userId = UserContext.getUser().getId();
        return ResultUtils.success(service.pageCollectionMsg(cid, current, pageSize, key, userId));
    }
    // 3. 根据用户id + 动漫id 查询是否收藏和和被哪些收藏夹id 返回收藏夹id列表 有代表收藏 且后续可以知道哪些收藏夹收录了
    @AuthCheck
    @GetMapping("/collections/anime/is")
    public BaseResponse<List<Long>> collectionsAnimeIs(Long aid) {
        Long userId = UserContext.getUser().getId();
        return ResultUtils.success(service.isCollections(userId, aid));
    }
    // 4. 收藏夹 id + 用户id + 动漫id 进行收藏
    @AuthCheck
    @GetMapping("/collections/anime/add")
    public BaseResponse<Long> collectionsAnimeAdd(Long cid, Long aid) {
        Long userId = UserContext.getUser().getId();
        return ResultUtils.success(service.addCollections(cid, userId, aid));
    }
    // 5. 通过 aid + cid 进行取消收藏
    @AuthCheck
    @DeleteMapping("/collections/anime/remove")
    public BaseResponse<Boolean> collectionsAnimeRemove(Long aId, Long cId) {
        Long userId = UserContext.getUser().getId();
        return ResultUtils.success(service.removeCollections(aId, cId, userId));
    }
    // 6. 传递用户所有的收藏夹id进行重新排序，但是默认收藏夹只能为第一个
    @AuthCheck
    @PutMapping("/collections/sort")
    public BaseResponse<Boolean> collectionsSort(@RequestBody List<Long> collectionIds) {
        Long userId = UserContext.getUser().getId();
        return ResultUtils.success(service.sortCollections(collectionIds, userId));
    }
    // 7. 传递用户id 和 收藏夹id 进行删除，但是默认收藏夹不能删除
    @AuthCheck
    @DeleteMapping("/collections/remove")
    public BaseResponse<Boolean> collectionsRemove(Long cid) {
        Long userId = UserContext.getUser().getId();
        return ResultUtils.success(service.removeCollection(cid, userId));
    }
    // 8. 传递用户id 返回 收藏夹id 进行创建 （名称必填）
    @AuthCheck
    @PostMapping("/collections/add")
    public BaseResponse<Long> collectionsAdd(@Valid @RequestBody CollectionsAddReq collectionsAddReq) {
        Long userId = UserContext.getUser().getId();
        return ResultUtils.success(service.addCollection(collectionsAddReq, userId));
    }
    // 9.  收藏夹id 进行修改信息 （名称必填）
    @AuthCheck
    @PutMapping("/collections/update")
    public BaseResponse<Boolean> collectionsUpdate(@Valid @RequestBody CollectionsUpdateReq collectionsUpdateReq) {
        Long userId = UserContext.getUser().getId();
        return ResultUtils.success(service.updateCollection(collectionsUpdateReq, userId));
    }
    // 10 查询该用户收藏夹个数
    @AuthCheck
    @GetMapping("/collections/count")
    public BaseResponse<Integer> collectionsCount() {
        Long userId = UserContext.getUser().getId();
        return ResultUtils.success(service.countCollections(userId));
    }
}
