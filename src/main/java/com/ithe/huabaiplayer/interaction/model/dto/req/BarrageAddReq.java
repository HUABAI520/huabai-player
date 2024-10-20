package com.ithe.huabaiplayer.interaction.model.dto.req;

import com.alibaba.fastjson.JSON;
import com.ithe.huabaiplayer.interaction.model.dto.BarStyle;
import com.ithe.huabaiplayer.interaction.model.entity.Barrage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

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
public class BarrageAddReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 视频id
     */
    @NotNull
    private Long videoId;

    /**
     * 内容
     */
    @NotBlank
    private String content;
    /**
     * 弹幕发送的视频的时间
     */
    @NotNull
    private Double timestamp;
    /**
     * JSON（ {     "color": "#6666",     "position": 0: 滚动，1: 顶部，2: 底部 })
     */
    @NotNull
    private BarStyle style;

    public static Barrage to(BarrageAddReq req) {
        Barrage build = Barrage.builder()
                .videoId(req.getVideoId())
                .content(req.getContent())
                .timestamp(req.getTimestamp())
                .build();
        build.setStyle(JSON.toJSONString(req.getStyle()));
        return build;
    }
}
