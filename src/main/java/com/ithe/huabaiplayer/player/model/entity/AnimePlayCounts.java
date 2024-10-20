package com.ithe.huabaiplayer.player.model.entity;

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
 * @since 2024-10-20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("anime_play_counts")
public class AnimePlayCounts implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 动漫表的动漫id
     */
    @Id
    private Long animeId;

    private Long playCount;

    private Date updatedAt;

}
