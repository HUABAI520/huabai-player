package com.ithe.huabaiplayer.player.model.vo;

import lombok.Data;

/**
 * @ClassName Seriess
 * @Author hua bai
 * @Date 2024/11/12 20:00
 * 系列中含有的信息
 **/
@Data
public class Seriess {
    /**
     * 动漫id
     */
    private Long id;
    /**
     * 动漫名
     */
    private String name;
    /**
     * 标签页展示的名称
     */
    private String seasonTitle;
}
