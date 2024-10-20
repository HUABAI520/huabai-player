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
 * 评论表 实体类。
 *
 * @author L
 * @since 2024-09-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("comment")
public class Comment implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
//    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private Long id;

    /**
     * 动漫Id
     */
    private Long animeId;

    /**
     * 视频id
     */
    private Long videoId;

    /**
     * 父id
     */
    private Long parentId;
    /**
     * 最外层的父id
     */
    private Long originId;
    /**
     * 评论内容
     */
    private String content;

    private Long userId;

    private Date createTime;

    private Date updateTime;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 回复数
     */
    private Integer replyCount;
    private Integer status;

}
