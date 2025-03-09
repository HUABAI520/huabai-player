package com.ithe.huabaiplayer.interaction.model.dto.resp;

import lombok.Builder;
import lombok.Data;

/**
 * @ClassName AddCommentResp
 * @Author hua bai
 * @Date 2025/2/24 14:42
 **/
@Data
@Builder
public class AddCommentResp {
    /**
     * 响应的评论id
     */
    private Long id;
    /**
     * 情感评分
     */
    private Double sentimentScore;
}
