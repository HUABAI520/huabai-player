package com.ithe.huabaiplayer.task.model.entity;

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
 * 任务表 实体类。
 *
 * @author L
 * @since 2024-09-24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("task")
public class Task implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 任务key
     */
    private String taskKey;

    /**
     * 任务执行时间~
     */
    private Date taskTime;
    private Date taskOldTime;

}
