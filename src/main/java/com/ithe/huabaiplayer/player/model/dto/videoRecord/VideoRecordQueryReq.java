package com.ithe.huabaiplayer.player.model.dto.videoRecord;

import com.ithe.huabaiplayer.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * @ClassName VideoRecordQueryReq
 * @Author hua bai
 * @Date 2024/9/6 19:48
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class VideoRecordQueryReq extends PageRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String key;
}
