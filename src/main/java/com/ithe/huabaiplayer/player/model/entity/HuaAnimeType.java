package com.ithe.huabaiplayer.player.model.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;

import java.io.Serial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 动漫-类型关联表 实体类。
 *
 * @author L
 * @since 2024-08-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("hua_anime_type")
public class HuaAnimeType implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 动漫id
     */
    @Id
    private Long animeId;

    /**
     * 类型id
     */
    @Id
    private Integer typeId;

}
