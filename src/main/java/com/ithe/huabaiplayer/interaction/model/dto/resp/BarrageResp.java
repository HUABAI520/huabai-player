package com.ithe.huabaiplayer.interaction.model.dto.resp;

import com.alibaba.fastjson.JSONObject;
import com.ithe.huabaiplayer.common.utils.BeanHuaUtil;
import com.ithe.huabaiplayer.interaction.model.dto.BarStyle;
import com.ithe.huabaiplayer.interaction.model.entity.Barrage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 弹幕表 实体类。
 *
 * @author L
 * @since 2024-09-29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BarrageResp implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 视频id
     */
    private Long videoId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 内容
     */
    private String content;

    /**
     * 弹幕发送的视频的时间
     */
    private Double timestamp;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * JSON（ {     "color": "#6666",     "postion": "top",     "size": "small" })
     */
    private BarStyle style;

    /**
     * 默认正常-1 被举报禁止-0
     */
    private Integer status;


    public static BarrageResp of(Barrage b) {
        BarrageResp resp = BeanHuaUtil.copyIgnoreFields(b, BarrageResp.class, List.of("style"));
        BarStyle s = JSONObject.parseObject(b.getStyle(), BarStyle.class);
        resp.setStyle(s);
        return resp;
    }

}
