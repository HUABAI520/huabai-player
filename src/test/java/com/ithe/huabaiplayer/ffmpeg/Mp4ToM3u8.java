package com.ithe.huabaiplayer.ffmpeg;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Mp4ToM3u8
 * @Author hua bai
 * @Date 2024/10/11 22:13
 **/
public class Mp4ToM3u8 {
    public static void main(String[] args) {
        Loader.load(avutil.class);
        String inputFilePath = "E:\\L\\Downloads\\动漫\\神无月的巫女\\01.mp4"; // 输入文件路径
        String outputFolderPath = "E:\\L\\Downloads\\动漫\\神无月的巫女"; // 输出文件路径
        int originalWidth;
        int originalHeight;
        int videoBitrate;
        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputFilePath)) {
            grabber.start();
            originalWidth = grabber.getImageWidth();
            originalHeight = grabber.getImageHeight();
            videoBitrate = grabber.getVideoBitrate();
            grabber.stop();
        } catch (FrameGrabber.Exception e) {
            throw new RuntimeException(e);
        }
        // 视频分辨率和比特率选项 根据原视频原画质 和 低画质
        // 根据原分辨率生成 可以生成的 分辨率 和 比特率
        System.out.println("Original Width: " + originalWidth);
        System.out.println("Original Height: " + originalHeight);
        List<String> resolutions = new ArrayList<>();
        resolutions.add(originalWidth + "x" + originalHeight);
        if (originalWidth > 1920 || originalHeight > 1080) {
            resolutions.add("1920x1080");
        }
        if (originalWidth > 1280 || originalHeight > 720) {
            resolutions.add("1280x720");
        }
//        if (originalWidth > 854 || originalHeight > 480) {
//            resolutions.add("854x480");
//        }
        // 根据原比特率生成 可以生成的 比特率
        List<Integer> bitrateList = new ArrayList<>();
        for (int i = 1; i <= resolutions.size(); i++) {
            bitrateList.add(videoBitrate / i);
        }
        try {
            for (int i = 0; i < resolutions.size(); i++) {
                String outputHLSPath = outputFolderPath + "/output_" + resolutions.get(i) + ".m3u8";
                convertToHLS(inputFilePath, outputHLSPath, resolutions.get(i), bitrateList.get(i));
            }
            System.out.println("Conversion completed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void convertToHLS(String inputFilePath, String outputHLSPath, String resolution, int bitrate) throws Exception {
        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputFilePath)) {
            grabber.start();

            long lengthInTime = grabber.getLengthInTime();
            // 使用 GPU 编码，设置编码器为 h264_nvenc
            try (FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputHLSPath, grabber.getImageWidth(),
                    grabber.getImageHeight(), grabber.getAudioChannels())) {
                recorder.setFormat("hls");
                recorder.setOption("hls_time", "10");  // 每个 ts 片段的时长
                recorder.setOption("hls_list_size", "0"); // 保持所有 ts 文件在 m3u8 列表中
                recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
                recorder.setOption("c:v", "h264_nvenc");  // 使用 GPU 加速编码
                recorder.setVideoCodecName("h264_nvenc");
                recorder.setOption("vsync", "1"); // 同步帧速率
                recorder.setOption("async", "1"); // 音频同步
                recorder.setOption("preset", "fast");     // 设定编码速度
                recorder.setVideoBitrate(bitrate); // 设置比特率
                recorder.setFrameRate(grabber.getFrameRate());

                // 调整分辨率
                recorder.setImageWidth(Integer.parseInt(resolution.split("x")[0]));
                recorder.setImageHeight(Integer.parseInt(resolution.split("x")[1]));

                // 启用硬件加速
                recorder.setOption("hwaccel", "cuda");
//                recorder.setOption("hwaccel_device", "0"); // 如果有多个GPU，可以通过设备号选择

                recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);
//                recorder.setAudioBitrate(grabber.getAudioBitrate()); // 原音频比特率
                recorder.setAudioBitrate(128000); // 固定音频比特率，比如 128 kb/s
                recorder.setSampleRate(grabber.getSampleRate()); // 保持原始采样率
                recorder.setAudioChannels(grabber.getAudioChannels()); // 保持音频通道数


                recorder.start();

                long processedTime;
                int frameCount = 0;
                Frame frame;
                while ((frame = grabber.grab()) != null) {
                    if (frame.image != null) {
                        recorder.record(frame); // 记录视频帧
                    }
                    if (frame.samples != null) {
                        recorder.recordSamples(frame.samples); // 记录音频样本
                    }
                    processedTime = grabber.getTimestamp();
                    double progress = (double) processedTime / lengthInTime * 100;
                    if (frameCount % 100 == 0) {
                        System.out.printf("\r文件名: %s 进度: %.2f%%", outputHLSPath, progress);
                    }
                    frameCount++;
                }
                recorder.stop();
                recorder.release();
            }
            grabber.stop();
            grabber.release();
        }
    }
}
