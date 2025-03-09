package com.ithe.huabaiplayer.interaction.model.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 举报详细记录表 实体类。
 *
 * @author L
 * @since 2025-02-27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("content_report_record")
public class ContentReportRecord implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 内容类型(1-评论、2-弹幕、3-评价)
     */
    private Integer thirdType;

    /**
     * 内容ID
     */
    private Long thirdId;

    /**
     * 举报用户ID
     */
    private Long reportBy;

    /**
     * 举报类型（1-色情低俗、2-政治敏感、3-暴恐涉政、4-垃圾广告、5-违法信息、6-其他）
     */
    private Integer type;

    /**
     * 举报原因
     */
    private String reason;

    /**
     * 举报时间
     */
    private Date reportTime;

    /**
     * 举报详细原因
     */
    private String reasonDetail;

}
