package com.ithe.huabaiplayer.player.controller;


import com.ithe.huabaiplayer.file.model.entity.FileNodes;
import com.ithe.huabaiplayer.player.handler.NonStaticResourceHttpRequestHandler;
import com.ithe.huabaiplayer.player.model.prefix.PlayerProperties;
import com.ithe.huabaiplayer.player.service.AnimeVideosService;
import com.mybatisflex.core.query.QueryWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import static com.ithe.huabaiplayer.file.model.entity.table.FileNodesTableDef.FILE_NODES;
import static com.ithe.huabaiplayer.player.model.entity.table.AnimeVideosTableDef.ANIME_VIDEOS;


/**
 * @author L
 */
@RestController
@RequestMapping("/api-player/play")
@Slf4j
public class PlayController {

    @Autowired
    private NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;
    @Autowired
    private AnimeVideosService animeVideosService;
    @Autowired
    private PlayerProperties playerProperties;


    @GetMapping("/video")
    public void video(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {

            String path = "E:/shandou/download/哔哩哔哩视频/为了消灭鬼舞辻无惨.mp4";
            File file = new File(path);
            if (file.exists()) {
                request.setAttribute(NonStaticResourceHttpRequestHandler.ATTR_FILE, path);
                nonStaticResourceHttpRequestHandler.handleRequest(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            }
        } catch (Exception e) {

        }
    }

    private FileNodes getFullPath(Long videoId) {
        return animeVideosService.getOneAs(QueryWrapper.create()
                .select(FILE_NODES.FULL_PATH, FILE_NODES.ID)
                .from(ANIME_VIDEOS)
                .leftJoin(FILE_NODES).on(ANIME_VIDEOS.FILE.eq(FILE_NODES.ID))
                .where(ANIME_VIDEOS.ID.eq(videoId)), FileNodes.class);
    }

    @GetMapping("/video2/**")
    public void video2(HttpServletRequest request, HttpServletResponse response) {
        String fullPath = request.getRequestURI();
        String path = fullPath.substring(fullPath.indexOf("/video2") + "/video2".length());
        path = URLDecoder.decode(path, StandardCharsets.UTF_8);
        String filePath = playerProperties.getPath(path);
        File videoFile = new File(filePath);

        if (!videoFile.exists()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        try (RandomAccessFile randomAccessFile = new RandomAccessFile(videoFile, "r")) {
            // 自定义的分片大小，例如 30MB
            int chunkSize = 20 * 1024 * 1024;

            // 获取文件的总长度
            long videoLength = randomAccessFile.length();

            // 获取 Range 请求头信息
            String rangeHeader = request.getHeader("Range");
            long start = 0;
            long end = chunkSize - 1;

            if (rangeHeader != null && rangeHeader.startsWith("bytes=")) {
                // 解析 Range 请求头中的开始和结束位置
                String[] ranges = rangeHeader.substring(6).split("-");
                start = Long.parseLong(ranges[0]);
                if (ranges.length > 1) {
                    end = Long.parseLong(ranges[1]);
                } else {
                    end = Math.min(start + chunkSize - 1, videoLength - 1);
                }
            }

            // 设置响应头
            long contentLength = end - start + 1;
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
            response.setContentType("video/mp4");
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Content-Range", "bytes " + start + "-" + end + "/" + videoLength);
            response.setHeader("Content-Length", String.valueOf(contentLength));

            // 将视频数据写入响应输出流
            byte[] buffer = new byte[chunkSize];
            randomAccessFile.seek(start);

            try (OutputStream outputStream = response.getOutputStream()) {
                int bytesRead;
                while ((bytesRead = randomAccessFile.read(buffer)) != -1 && contentLength > 0) {
                    log.info("bytesRead:{}", bytesRead);
                    outputStream.write(buffer, 0, Math.min(bytesRead, (int) contentLength));
                    contentLength -= bytesRead;
                }
            }
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
