package com.ithe.huabaiplayer.player.controller;

import com.ithe.huabaiplayer.common.BaseResponse;
import com.ithe.huabaiplayer.common.ResultUtils;
import com.ithe.huabaiplayer.common.annotation.AuthCheck;
import com.ithe.huabaiplayer.common.constant.UserConstant;
import com.ithe.huabaiplayer.player.model.entity.HuaType;
import com.ithe.huabaiplayer.player.service.HuaTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @ClassName HuaTypeController
 * @Author hua bai
 * @Date 2024/9/1 12:26
 **/
@RestController
@RequestMapping("/api-player/type")
@RequiredArgsConstructor
public class HuaTypeController {
    private final HuaTypeService huaTypeService;

    @GetMapping("/getAll")
    @AuthCheck
    public BaseResponse<Set<HuaType>> getAll() {
        return ResultUtils.success(huaTypeService.getAll());
    }

    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Set<HuaType>> add(String huaType) {
        return ResultUtils.success(huaTypeService.insert(HuaType.builder().type(huaType).build()));
    }

    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Set<HuaType>> delete(Integer id) {
        return ResultUtils.success(huaTypeService.removeType(id));
    }
        
}
