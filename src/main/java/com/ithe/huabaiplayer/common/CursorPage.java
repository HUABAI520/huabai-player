package com.ithe.huabaiplayer.common;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @ClassName CursorPage
 * @Author hua bai
 * @Date 2024/10/16 22:59
 **/
@Data
public class CursorPage<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long maxCursor;
    /**
     * 是否还有数据
     */
    private Boolean hasNext;
    private List<T> data;

    public static <T> CursorPage<T> empty() {
        CursorPage<T> objectCursorPage = new CursorPage<>();
        objectCursorPage.setHasNext(false);
        return objectCursorPage;
    }
}
