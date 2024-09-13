package com.ithe.huabaiplayer.player.model.dto.animeVideos;

import lombok.Data;

import java.nio.file.Path;

/**
 * @ClassName FenPian
 * @Author hua bai
 * @Date 2024/8/30 11:23
 **/
@Data
public class FenPian {
    private Path path;
    private Integer partNumber;
    private Integer totalParts;
    private String key;
}
