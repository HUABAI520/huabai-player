package com.ithe.huabaiplayer.interaction.model.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

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
public class CommentAddReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 动漫Id
     */
    @NotNull
    private Long animeId;
    /**
     * 视频id
     */
    @NotNull
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
    @NotNull
    @NotBlank
    @Size(max = 200, message = "评论内容长度不能超过200")
    private String content;
}
