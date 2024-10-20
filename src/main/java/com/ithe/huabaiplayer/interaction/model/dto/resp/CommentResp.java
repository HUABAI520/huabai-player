package com.ithe.huabaiplayer.interaction.model.dto.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
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
public class CommentResp implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
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

    //    private Long userId;
    private UserComment user;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 回复数
     */
    private Integer replyCount;
    private Integer status;
    /**
     * 该用户是否给这条评论点赞 > 0  表示已点赞 <0/null 未点赞
     */
    private Long isLike;

    @Data
    public static class UserComment {
        private Long userId;
        private String username;
        private String userAvatar;
        private String userRole;
    }

}
