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
 * 点赞表 现在包括(评论、弹幕、评价) 实体类。
 *
 * @author L
 * @since 2024-09-29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("likes")
public class Likes implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 点赞表id
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 第三方id（评论id、弹幕id、评价id)
     */
    private Long thirdId;

    /**
     * 第三方类型(1-评论、2-弹幕、3-评价)
     */
    private Integer thirdType;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 点赞时间
     */
    private Date likeTime;

}
