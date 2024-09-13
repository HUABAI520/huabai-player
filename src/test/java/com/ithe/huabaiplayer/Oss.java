package com.ithe.huabaiplayer;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @ClassName Oss
 * @Author hua bai
 * @Date 2024/9/8 9:52
 **/
public class Oss {
    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://your-api-url.com")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // 获取响应体的字符串
            String responseBody = response.body().string();
            System.out.println(responseBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        Request request = new Request.Builder()
                .url("https://api.v2.rainyun.com/product/ros/bucket/")
                .addHeader("x-api-key", "{{apikey}}")
                .addHeader("rain-dev-token", "{{rain-dev-token}}")
                .addHeader("User-Agent", "Apifox/1.0.0 (https://apifox.com)")
                .build();
        Response response = client.newCall(request).execute();
    }
}
