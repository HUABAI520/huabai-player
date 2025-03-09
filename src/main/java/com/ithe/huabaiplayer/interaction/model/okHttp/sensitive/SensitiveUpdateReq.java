package com.ithe.huabaiplayer.interaction.model.okHttp.sensitive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName SensitiveQueryReq
 * @Author hua bai
 * @Date 2025/2/25 10:56
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensitiveUpdateReq {
    private String oldWord;
    private String newWord;
}
