package com.oakleyp.youtubeautostream.clients.ffmpeg;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.oakleyp.youtubeautostream.data.model.LiveStream;
import com.oakleyp.youtubeautostream.data.model.LiveStreamSource;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.progress.ProgressListener;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Component
@Slf4j
public class FFmpegClient {
    @Value("${ffmpeg.bin.ffmpeg.path}")
    private String ffmpegBinPath;

    @Value("${ffmpeg.bin.ffprobe.path}")
    private String ffprobeBinPath;

    private FFmpeg ffmpeg;
    private FFprobe ffprobe;

    @PostConstruct
    public void init() throws IOException {
        log.debug("Creating client with {}, {}", ffmpegBinPath, ffprobeBinPath);

        ffmpeg = new FFmpeg(ffmpegBinPath);
        ffprobe = new FFprobe(ffprobeBinPath);
    }

    private FFmpegExecutor ffmpegExecutor() {
        return new FFmpegExecutor(ffmpeg, ffprobe);
    }

    private FFmpegBuilder builderForStream(LiveStream stream) {
        FFmpegBuilder builder = new FFmpegBuilder();
        
        stream.getSources().forEach(source -> builder.addInput(source.getFname()));
        builder.addOutput("rtmp://rtmp-server/show/stream")
            .setFormat("flv")
            .setAudioChannels(2)              // Stereo audio
            .setAudioCodec("aac")                // using the aac codec
            .setAudioSampleRate(48_000)    // at 48KHz
            .setAudioBitRate(32768)           // at 32 kbit/s

            .setVideoCodec("libx264")             // Video using x264
            .setVideoFrameRate(24, 1)       // at 24 frames per second
            .setVideoResolution(640, 480) // at 640x480 resolution
            .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
            .done();

        return builder;
    }

    public void createStream(LiveStream stream, ProgressListener pl) {
        FFmpegBuilder builder = builderForStream(stream);
        FFmpegExecutor executor = ffmpegExecutor();

        executor.createJob(builder, pl).run();
    }

    public void createStream(LiveStream stream) {
        FFmpegBuilder builder = builderForStream(stream);
        FFmpegExecutor executor = ffmpegExecutor();

        executor.createJob(builder).run();
    }
}
