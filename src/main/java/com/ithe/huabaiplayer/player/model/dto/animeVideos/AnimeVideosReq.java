package com.ithe.huabaiplayer.player.model.dto.animeVideos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 实体类。
 *
 * @author L
 * @since 2024-08-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnimeVideosReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 动漫id
     */
    @NotNull
    private Long animeId;

    /**
     * 视频标题
     */
    @NotNull
    @NotBlank
    private String title;
    /**
     * 排序 > 0
     */
    @Min(1)
    @NotNull
    private Integer rank;
}
