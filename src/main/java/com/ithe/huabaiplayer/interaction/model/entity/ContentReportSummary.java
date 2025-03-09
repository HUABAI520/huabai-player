package com.ithe.huabaiplayer.interaction.model.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 *  实体类。
 *
 * @author L
 * @since 2025-02-27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("content_report_summary")
public class ContentReportSummary implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 内容类型(1-评论、2-弹幕、3-评价)
     */
    @Id
    private Integer thirdType;

    /**
     * 内容ID
     */
    @Id
    private Long thirdId;

    /**
     * 累计举报次数
     */
    private Integer reportCount;

    /**
     * 最新举报时间
     */
    private Date latestReportTime;

    /**
     * 首次举报时间
     */
    private Date firstReportTime;

    /**
     * 最新举报类型（1-色情低俗、2-政治敏感、3-暴恐涉政、4-垃圾广告、5-违法信息、6-其他）
     */
    private Integer latestType;

    /**
     * 0-待处理, 1-已违规, 2-未违规
     */
    private Integer status;

    /**
     * 最新内容快照
     */
    private String content;

    /**
     * 内容发布者
     */
    private Long publishBy;

    /**
     * 审核员ID
     */
    private Long reviewerId;

    /**
     * 审核完成时间
     */
    private Date processedTime;

}
