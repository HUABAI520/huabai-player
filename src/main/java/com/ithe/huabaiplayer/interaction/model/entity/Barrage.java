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
 * 弹幕表 实体类。
 *
 * @author L
 * @since 2024-09-29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("barrage")
public class Barrage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 视频id
     */
    private Long videoId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 内容
     */
    private String content;

    /**
     * 弹幕发送的视频的时间
     */
    private Double timestamp;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * JSON（ {     "color": "#6666",     "postion": "top",     "size": "small" })
     */
    private String style;

    /**
     * 默认正常-1 被举报禁止-0
     */
    private Integer status;
}
