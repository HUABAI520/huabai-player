package com.ithe.huabaiplayer.player.model.entity;

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
 * 实体类。
 *
 * @author L
 * @since 2024-08-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("anime_videos")
public class AnimeVideos implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 动漫拥有视频的id
     */
    @Id(keyType = KeyType.Auto)
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
     * 创建时间
     */
    private Date crateTime;

    /**
     * 图片url
     */
    private String image;

    /**
     * 该视频总时长
     */
    private Integer duration;

}
