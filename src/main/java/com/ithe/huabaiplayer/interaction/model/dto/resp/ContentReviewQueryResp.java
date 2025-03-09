package com.ithe.huabaiplayer.interaction.model.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 评论/弹幕/评分 内容审核表 实体类。
 *
 * @author L
 * @since 2025-02-25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentReviewQueryResp implements Serializable {
    /**
     * 第三方类型(1-评论、2-弹幕、3-评价)
     */
    private Integer thirdType;
    /**
     * 第三方id（评论id、弹幕id、评价id)
     */
    private Long thirdId;
    /**
     * 累计举报次数
     */
    private Integer reportCount;
    /**
     * 内容
     */
    private String content;
    /**
     * 举报原因
     */
    private String reason;
    private Long publishBy;
    /**
     * 内容发表者
     */
    private UserMsg publishUser;
//    private Long reportBy;
//    /**
//     * 举报者
//     */
//    private UserMsg reportUser;
    /**
     * 审核人id
     */
    private Long reviewerId;
    /**
     * 审核人
     */
    private UserMsg reviewer;
    /**
     * 最新举报时间
     */
    private Date latestReportTime;

    /**
     * 首次举报时间
     */
    private Date firstReportTime;
    /**
     * 审核时间
     */
    private Date processedTime;
    /**
     * 0-待审核 1-举报成功，已违规 2-未违规
     */
    private Integer status;
    /**
     * 最新举报类型（1-色情低俗、2-政治敏感、3-暴恐涉政、4-垃圾广告、5-违法信息、6-其他）
     */
    private Integer latestType;

    @Data
    public static class UserMsg {
        private Long userId;
        private String username;
        private String userAvatar;
    }

}
