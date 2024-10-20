package com.ithe.huabaiplayer.interaction.model.dto.req;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户评分表 实体类。
 *
 * @author L
 * @since 2024-09-24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingAddReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 动漫id
     */
    @NotNull
    private Long animeId;
    /**
     * 评分 1-10
     */
    @NotNull
    @Min(1)
    @Max(10)
    private BigDecimal score;
    /**
     * 评论 - 不超过100字
     */
    @Size(max = 100)
    private String comment;

}
