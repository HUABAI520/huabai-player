package com.ithe.huabaiplayer.interaction.model.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @ClassName ReportAddReq
 * @Author hua bai
 * @Date 2025/2/25 16:32
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportAddReq implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 第三方类型(1-评论、2-弹幕、3-评价)
     */
    private Integer thirdType;
    /**
     * 第三方id（评论id、弹幕id、评价id)
     */
    private Long thirdId;
    /**
     * 举报类型 1-色情低俗、2-政治敏感、3-暴恐涉政、4-垃圾广告、5-违法信息、6-其他）
     */
    private Integer type;
    /**
     * 举报原因
     */
    private String reason;
    /**
     * 举报详细原因
     */
    private String reasonDetail;
}
