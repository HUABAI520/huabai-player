package com.ithe.huabaiplayer.player.model.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 *  实体类。
 *
 * @author L
 * @since 2024-11-12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("series")
public class Series implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 系列id
     */
    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 系列名
     */
    private String name;

    /**
     * 系列介绍
     */
    private String intro;

}
