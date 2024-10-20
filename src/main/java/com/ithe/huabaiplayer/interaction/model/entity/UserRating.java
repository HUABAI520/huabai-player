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
import java.math.BigDecimal;
import java.util.Date;

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
@Table("user_rating")
public class UserRating implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 评分表id
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 动漫id
     */
    private Long animeId;

    /**
     * 开始/更新 评分时间
     */
    private Date ratingTime;

    /**
     * 评分 1-10
     */
    private BigDecimal score;

    /**
     * 旧的评分 默认为null
     */
    private BigDecimal oldScore;

    /**
     * 评论 - 不超过100字
     */
    private String comment;

}
