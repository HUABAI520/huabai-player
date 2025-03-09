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
 * 动漫用户收藏关联表 实体类。
 *
 * @author L
 * @since 2024-11-30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("collection_anime_user")
public class CollectionAnimeUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 关联id
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 收藏夹id
     */
    private Long collectionId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 被收藏的动漫id
     */
    private Long animeId;

    private Date createTime;

    private Date updateTime;

}
