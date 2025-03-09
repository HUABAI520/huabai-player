package com.ithe.huabaiplayer.interaction.model.okHttp.sensitive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @ClassName SensitiveQueryReq
 * @Author hua bai
 * @Date 2025/2/25 10:56
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensitiveQueryReq implements Serializable {
    private Integer page = 1;
    private Integer pageSize = 10;
    private String keyword;
    @Serial
    private static final long serialVersionUID = 1L;
}
