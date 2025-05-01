package com.ithe.huabaiplayer.features.model.okhttp;

import lombok.Data;

import java.util.List;

/**
 * @ClassName RecommendResp
 * @Author hua bai
 * @Date 2025/3/26 19:44
 **/
@Data
public class RecommendResp {
    private Long animeId;
    private String title;
    private List<String> types;
}
