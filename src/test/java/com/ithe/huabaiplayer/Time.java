package com.ithe.huabaiplayer;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.Test;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName Time
 * @Author hua bai
 * @Date 2024/10/10 19:32
 **/
public class Time {
    @Test
    public void test1(){
        Date date = new Date();
        long time = date.getTime();
        Date date1 = new Date(1574208900000L);
        System.out.println(date.toGMTString());
        System.out.println(date1.toGMTString());
        var sdf = new SimpleDateFormat("E MMM dd, yyyy");
        System.out.println(sdf.format(date));
        System.out.println(sdf.format(date1));
        System.out.println(time);
        String decoded = URLDecoder.decode("%E4%B8%AD%E6%96%87%21", StandardCharsets.UTF_8);
        System.out.println(decoded);


        Security.addProvider(new BouncyCastleProvider());
    }
}
