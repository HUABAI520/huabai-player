package com.ithe.huabaiplayer.file.model.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * 文件夹/文件信息表 实体类。
 *
 * @author L
 * @since 2024-08-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileResp implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 上级节点ID，根文件夹为NULL
     */
    private Long parentId;
    /**
     * 名称
     */
    private String name;
    /**
     * 节点类型：文件夹或文件
     */
    private String nodeType;
    /**
     * 完整路径
     */
    private String fullPath;
    /**
     * 大小，对于文件夹默认为0
     */
    private BigInteger size;
    /**
     * 文件存储路径，对于文件不为空
     */
    private String filePath;
    /**
     * 文件类型，仅文件类型时不为空
     */
    private String fileType;
    /**
     * 创建时间
     */
    private Date createdAt;
    /**
     * 更新时间
     */
    private Date updatedAt;

}
