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
 * 收藏夹表 包含每个用户生成收藏夹 实体类。
 *
 * @author L
 * @since 2024-11-30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("collections")
public class Collections implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 收藏夹id
     */
    @Id(keyType = KeyType.Auto)
    private Long id;
    /**
     * 收藏夹标题
     */
    private String title;
    /**
     * 该收藏夹的用户
     */
    private Long userId;
    /**
     * 排序字段
     */
    private Integer sort;
    /**
     * 收藏的数量（系统配的默认没有限制，自己创建的不能大于999）
     */
    private Integer count;
    /**
     * 收藏夹展示的图片
     */
    private String image;
    /**
     * 是否是系统默认收藏夹 1-是 0-否
     */
    private Integer isDefault;

    private Date createTime;

    private Date updateTime;

}
