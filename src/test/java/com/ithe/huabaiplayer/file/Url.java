package com.ithe.huabaiplayer.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName Url
 * @Author hua bai
 * @Date 2024/10/15 16:03
 **/
public class Url {
    public static void main(String[] args) throws IOException {

        URL url = new URL("http://8.137.78.53");

        /* 字节流 */
        InputStream is = url.openStream();

        /* 字符流 */
        InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);

        /* 提供缓存功能 */
        BufferedReader br = new BufferedReader(isr);

        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }

        br.close();
    }
}
