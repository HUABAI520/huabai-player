package com.ithe.huabaiplayer.player.model.dto.videoRecord;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 实体类。
 *
 * @author L
 * @since 2024-09-03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoRecordResp implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Long userId;
    /**
     * 动漫id
     */
    private Long animeId;
    /**
     * 动漫名
     */
    private String name;
    /**
     * 视频id
     */
    private Long videoId;
    /**
     * 视频集数
     */
    private Integer rank;
    /**
     * 视频标题
     */
    private String title;
    /**
     * 观看时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    /**
     * 进度
     */
    private Double seek;
    /**
     * 对应视频的图片
     */
    private String image;

    private Long id;

}
