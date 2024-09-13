package com.ithe.huabaiplayer.player.controller;

import com.ithe.huabaiplayer.player.model.prefix.PictureProperties;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import static com.ithe.huabaiplayer.common.utils.HuaUtils.validPictureName;

/**
 * @ClassName PictureController
 * @Author hua bai
 * @Date 2024/8/29 12:13
 **/
@RestController
@RequestMapping("/picture")
public class PictureController {
    public static final String SPLIT = "/";
    @Autowired
    private PictureProperties pictureProperties;

    @GetMapping("/{kind}/{id}/{name}")
    public void getPictureUrl(@PathVariable String kind,
                              @PathVariable Long id,
                              @PathVariable String name,
                              HttpServletResponse httpServletResponse) {
        String fileSuffix = validPictureName(name);
        String filePath = pictureProperties.getUploadPath() + SPLIT + kind + SPLIT + id + SPLIT + name;
        // 读取文件并响应
        File file = new File(filePath);
        if (file.exists()) {
            // 获取文件后缀判断是否是图片
            httpServletResponse.setContentType("image/" + fileSuffix);
            httpServletResponse.setHeader("Content-Disposition", "attachment; filename=" + name);
            try (InputStream inputStream = Files.newInputStream(file.toPath())) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    httpServletResponse.getOutputStream().write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
