package com.ithe.huabaiplayer.player.model.dto.videoRecord;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 *  实体类。
 *
 * @author L
 * @since 2024-09-03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoRecordAddReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 动漫id
     */
    @NotNull
    private Long animeId;
    /**
     * 视频id
     */
    @NotNull
    private Long videoId;
    /**
     * 进度 （秒)
     */
    @NotNull
    private Double seek;


}
