package com.ithe.huabaiplayer.zhujie;

import com.ithe.huabaiplayer.fanshe.Fen;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

/**
 * @ClassName ReportUse
 * @Author hua bai
 * @Date 2024/10/9 18:46
 **/
public class ReportUse {
    @Test
    public void test() throws NoSuchMethodException {
        Fen fen = new Fen();
        Class<? extends Fen> aClass = fen.getClass();
        Method instance = aClass.getDeclaredMethod("getInstance", int.class, String.class);
        Report annotation = instance.getAnnotation(Report.class);
        System.out.println(annotation.level());
        Number value = 1;
    }
}
