package com.ithe.huabaiplayer.interaction.model.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 收藏夹表 包含每个用户生成收藏夹 实体类。
 *
 * @author L
 * @since 2024-11-30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollectionsAddReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 收藏夹标题
     */
    @NotBlank(message = "收藏夹标题不能为空")
    private String title;
    /**
     * 排序字段
     */
    private Integer sort;

}
