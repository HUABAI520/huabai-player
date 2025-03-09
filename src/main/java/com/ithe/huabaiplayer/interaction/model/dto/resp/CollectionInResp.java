package com.ithe.huabaiplayer.interaction.model.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName CollectionInResp
 * @Author hua bai
 * @Date 2025/1/30 11:28
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollectionInResp {
    /**
     * 收藏夹id
     */
    private Long cid;
    /**
     * 收藏时间
     */
    private Date createTime;
    /**
     * 动漫id
     */
    private Long id;
    /**
     * 动漫名
     */
    private String name;
    /**
     * 动漫别称
     */
    private String another;
    /**
     * 图片
     */
    private String image;
}
