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
import java.time.LocalDateTime;

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
@Table("video_record")
public class VideoRecord implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 动漫id
     */
    private Long animeId;

    /**
     * 视频id
     */
    private Long videoId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 进度 （秒)
     */
    private Double seek;

    private LocalDateTime updateTime;

}
