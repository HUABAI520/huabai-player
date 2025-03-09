package com.ithe.huabaiplayer.interaction.model.okHttp.analyze;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @ClassName AnalyzeResponse
 * @Author hua bai
 * @Date 2025/2/21 16:54
 **/
@Data
public class AnalyzeResponse {
    private boolean allowed;
    @JsonProperty("sentiment_score")  // 映射 Python 的字段名
    private double sentimentScore;
    private String message;
    @JsonProperty("elapsed_time_ms")  // 映射 Python 的字段名
    private double elapsedTimeMs;
}
