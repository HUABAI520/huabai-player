package com.ithe.huabaiplayer.interaction.model.okHttp.sensitive;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SensitiveQueryResp {
    // 敏感词列表
    private List<String> data;
    // 总记录数
    private int total;
    // 当前页码
    private int page;
    // 每页大小
    @JsonProperty("page_size")  // 映射 Python 的字段名
    private int pageSize;
}