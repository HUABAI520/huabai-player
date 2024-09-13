package com.ithe.huabaiplayer.player.model.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;

import java.io.Serial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 动漫类型表 实体类。
 *
 * @author L
 * @since 2024-08-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("hua_type")
public class HuaType implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 类型id
     */
    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 类型名称
     */
    private String type;

}
