package com.ithe.huabaiplayer.ffmpeg;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;

import java.io.File;

/**
 * @ClassName Ts2Mp4
 * @Author hua bai
 * @Date 2024/10/11 12:16
 **/
public class Ts2Mp4 {
    public static void main(String[] args) {
        Loader.load(avutil.class);

        String inputFilePath = "E:\\L\\Downloads\\动漫\\神无月的巫女\\DL(1).ts"; // 输入文件路径
        String outputFilePath = "E:\\L\\Downloads\\动漫\\神无月的巫女"; // 输出文件路径
        // 获取到该文件夹的所有.ts文件
        File[] tsFiles = new File(inputFilePath).getParentFile().listFiles(file -> file.getName().endsWith(".ts"));
        assert tsFiles != null;
        int count = tsFiles.length;
        int i = 1;
        for (File tsFile : tsFiles) {
            String name = tsFile.getName();
            if (name.endsWith(".ts")) {
                String outputFileName = name.replace(".ts", ".mp4");
                String outputFilePath1 = outputFilePath + "\\" + outputFileName;
                extracted(tsFile.getAbsolutePath(), outputFilePath1, count, i, name);
                i++;
            }
        }
    }

    private static void extracted(String inputFilePath, String outputFilePath, int count, int i, String name) {
        try {
            // 创建 FFmpegFrameGrabber 实例来读取输入文件
            FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(inputFilePath);
            frameGrabber.setFormat("mpegts"); // 指定输入格式为 .ts
            frameGrabber.start();
            // 获取视频总时长（以微秒为单位）
            long totalDuration = frameGrabber.getLengthInTime();
            // 创建 FFmpegFrameRecorder 实例
            FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputFilePath, frameGrabber.getImageWidth(), frameGrabber.getImageHeight(), frameGrabber.getAudioChannels());
            recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264); // 设置视频编码器
            recorder.setFormat("mp4"); // 设置输出格式
            recorder.setFrameRate(frameGrabber.getFrameRate()); // 设置帧率
            recorder.setSampleRate(frameGrabber.getSampleRate()); // 设置音频采样率
            recorder.setOption("preset", "fast"); // 设置编码速度优先
            recorder.setVideoCodecName("h264_nvenc"); // 使用 NVIDIA 的 NVENC 编码器
            recorder.setOption("hwaccel", "cuda"); // 使用 CUDA 硬件加速
            recorder.start();

            // 读取帧并写入输出文件
            Frame frame;
            long processedTime = 0;
            int frameCount = 0;
            while ((frame = frameGrabber.grab()) != null) {
                if (frame.image != null) {
                    recorder.record(frame); // 记录视频帧
                }
                if (frame.samples != null) {
                    recorder.recordSamples(frame.samples); // 记录音频样本
                }
                // 计算进度
                processedTime = frameGrabber.getTimestamp();
                double progress = (double) processedTime / totalDuration * 100;

                // 每处理一定数量的帧输出进度（比如每处理100帧显示一次）
                if (frameCount % 100 == 0) {
                    System.out.printf("\r第 %d / 共%d 文件名: %s 进度: %.2f%%", i, count, name, progress);
                }
                frameCount++;
            }

            // 停止录制和读取
            recorder.stop();
            recorder.release();
            frameGrabber.stop();
            frameGrabber.release();

            System.out.println("转换完成！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
