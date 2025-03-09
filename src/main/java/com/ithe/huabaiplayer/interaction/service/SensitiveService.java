package com.ithe.huabaiplayer.interaction.service;


import com.ithe.huabaiplayer.common.ErrorCode;
import com.ithe.huabaiplayer.common.exception.BusinessException;
import com.ithe.huabaiplayer.common.model.dto.RestClient.ApiResponse;
import com.ithe.huabaiplayer.common.service.RestClient;
import com.ithe.huabaiplayer.interaction.model.enums.HttpUrlEnum;
import com.ithe.huabaiplayer.interaction.model.okHttp.analyze.AnalyzeRequest;
import com.ithe.huabaiplayer.interaction.model.okHttp.analyze.AnalyzeResponse;
import com.ithe.huabaiplayer.interaction.model.okHttp.sensitive.SensitiveQueryReq;
import com.ithe.huabaiplayer.interaction.model.okHttp.sensitive.SensitiveQueryResp;
import com.ithe.huabaiplayer.interaction.model.okHttp.sensitive.SensitiveUpdateReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author L
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SensitiveService {

    private final RestClient restClient;
    private final String FLASK__URL = HttpUrlEnum.FLASK_ANALYSIS_URL.getValue();

    public AnalyzeResponse analyzeText(String text) {
        // 构造请求体
        AnalyzeRequest requestBody = new AnalyzeRequest(text);
        // 使用封装的 RestClient 工具发送请求
        ApiResponse<AnalyzeResponse> response = restClient.post(FLASK__URL + "analyze")
                // 设置 JSON 请求体
                .jsonBody(requestBody)
                // 设置请求头
                .header("Content-Type", "application/json")
                // 执行请求并指定响应类型
                .execute(AnalyzeResponse.class);
        // 检查响应是否成功
        if (!response.isSuccessful()) {
            log.error("Failed to analyze text: {}", response.getStatusCode());
            throw new RuntimeException("Failed to analyze text: " + response.getStatusCode());
        }

        // 返回解析后的响应数据
        return response.getData();
    }

    /**
     * 添加敏感词
     *
     * @param word 敏感词
     * @return 添加结果
     */
    public String addSensitiveWord(String word) {
        if (word == null || word.trim().isEmpty()) {
            throw new IllegalArgumentException("Word cannot be null or empty");
        }
        // 构造请求体
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("word", word);
        // 发送请求
        ApiResponse<Map> response = restClient.post(FLASK__URL + "add-sensitive-word")
                // 设置 JSON 请求体
                .jsonBody(requestBody)
                // 设置请求头
                .header("Content-Type", "application/json")
                // 执行请求并解析响应为 Map
                .execute(Map.class);

        // 检查响应状态
        if (!response.isSuccessful()) {
            Object error = response.getData().get("error");
            log.error("Failed to Add sensitive word: {},报错原因：{}", response.getStatusCode(), error);
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "添加敏感词失败了！：" + error);
        }
        // 返回结果
        return (String) response.getData().get("message");
    }

    /**
     * 分页查询敏感词
     *
     * @return 分页查询结果
     */
    public SensitiveQueryResp getSensitiveWords(SensitiveQueryReq request) {


        // 发送请求
        ApiResponse<SensitiveQueryResp> response = restClient.get(FLASK__URL + "sensitive-words")
                // 添加查询参数
                .queryParam("page", request.getPage())
                .queryParam("page_size", request.getPageSize())
                .queryParam("keyword", request.getKeyword())
                // 执行请求并解析响应
                .execute(SensitiveQueryResp.class);

        // 检查响应状态
        if (!response.isSuccessful()) {
            throw new RuntimeException("Failed to query sensitive words: " + response.getStatusCode());
        }

        // 返回响应数据
        return response.getData();
    }

    public String updateSensitiveWord(SensitiveUpdateReq sensitiveUpdateReq) {
        ApiResponse<Map> response = restClient.post(FLASK__URL + "update-sensitive-word")
                .jsonBody(sensitiveUpdateReq)
                .header("Content-Type", "application/json")
                .execute(Map.class);
        // 检查响应状态
        if (!response.isSuccessful()) {
            Object error = response.getData().get("error");
            log.error("Failed to update sensitive word: {},报错原因：{}", response.getStatusCode(), error);
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "修改敏感词失败了！：" + error);
        }
        return (String) response.getData().get("message");
    }
}