package com.ithe.huabaiplayer.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName FileTest
 * @Author hua bai
 * @Date 2024/10/10 19:00
 **/
public class FileTest {
    public static void main(String[] args) throws IOException {
        File f = new File("huabaiplayer" + File.separator + "file" + File.separator + "test.txt");
        System.out.println(f.getPath());
        System.out.println(f.getAbsolutePath());
        System.out.println(f.getCanonicalPath());
        System.out.println(File.separator);
        System.out.println(f.isFile());
        System.out.println(f.canRead());
        System.out.println(f.canWrite());
        System.out.println(f.length());
        try (OutputStream output = new FileOutputStream(f)) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 100; i++) {
                sb.append("hello world").append(i);
            }
            output.write(sb.toString().getBytes(StandardCharsets.UTF_8));
            output.flush();
        }
        try (InputStream fis = new FileInputStream(f)) {
            byte[] buffer = new byte[1000];
            int n;
            while ((n = fis.read(buffer)) != -1) { // 读取到缓冲区
                System.out.println("read " + n + " bytes.");
            }
            System.out.println(new String(buffer));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
