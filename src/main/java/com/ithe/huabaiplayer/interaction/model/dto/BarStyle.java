package com.ithe.huabaiplayer.interaction.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @ClassName BarStyle
 * @Author hua bai
 * @Date 2024/10/13 21:33
 **/
@Data
public class BarStyle implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String color;
    /**
     * 0: 滚动，1: 顶部，2: 底部
     */
    @Min(0)
    @Max(2)
    @NotNull
    private Integer position;
}
