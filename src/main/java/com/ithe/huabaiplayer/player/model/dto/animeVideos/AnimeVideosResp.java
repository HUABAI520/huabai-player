package com.ithe.huabaiplayer.player.model.dto.animeVideos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ithe.huabaiplayer.player.model.entity.AnimeVideos;
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
 * @since 2024-08-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnimeVideosResp implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 动漫拥有视频的id
     */
    private Long id;

    /**
     * 动漫id
     */
    private Long animeId;

    /**
     * 视频标题
     */
    private String title;

    /**
     * 排序
     */
    private Integer rank;

    /**
     * 文件id
     */
    private Long file;
    /**
     * 文件访问链接
     */
    private String fileUrl;
    /**
     * 图片url
     */
    private String image;
    /**
     * 该视频总时长
     */
    private Integer duration;
    /**
     * seek
     */
    private Double seek;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date crateTime;


    public static AnimeVideosResp of(AnimeVideos animeVideos) {
        return AnimeVideosResp.builder()
                .id(animeVideos.getId())
                .animeId(animeVideos.getAnimeId())
                .title(animeVideos.getTitle())
                .rank(animeVideos.getRank())
                .crateTime(animeVideos.getCrateTime())
                .file(animeVideos.getFile())
                .image(animeVideos.getImage())
                .duration(animeVideos.getDuration())
                .build();
    }

}
