package com.oakleyp.youtubeautostream.service.youtube;

import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.oakleyp.youtubeautostream.clients.ffmpeg.FFmpegClient;
import com.oakleyp.youtubeautostream.clients.youtube.YouTubeClient;
import com.oakleyp.youtubeautostream.data.model.LiveStream;
import com.oakleyp.youtubeautostream.data.model.LiveStreamStatus;
import com.oakleyp.youtubeautostream.data.repository.LiveStreamRepository;
import com.oakleyp.youtubeautostream.data.repository.LiveStreamSpecifications;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpegUtils;
import net.bramp.ffmpeg.progress.Progress;
import net.bramp.ffmpeg.progress.ProgressListener;

@Service
@RequiredArgsConstructor
@Slf4j
public class LiveStreamService {
    @Autowired
    private YouTubeClient ytClient;

    @Autowired
    private FFmpegClient ffmpegClient;

    private final LiveStreamRepository liveStreamRepository;

    @Transactional
    public Page<LiveStream> getPagedLiveStreams(String keyword, LiveStreamStatus status, Pageable page) {
        return liveStreamRepository.findAll(LiveStreamSpecifications.filterByKeywordAndStatus(keyword, status), page);
    }

    @Transactional
    public LiveStream publishLiveStream(Long liveStreamId) {
        LiveStream stream = liveStreamRepository.findById(liveStreamId).orElseThrow(
            () -> new LiveStreamNotFoundException(liveStreamId)
        );

        ffmpegClient.createStream(stream, new ProgressListener() {
            // Using the FFmpegProbeResult determine the duration of the input
            final double duration_ns = 1* TimeUnit.SECONDS.toNanos(1);

            @Override
            public void progress(Progress progress) {
                double percentage = progress.out_time_ns / duration_ns;

                // Print out interesting information about the progress
                log.debug(String.format(
                    "[%.0f%%] status:%s frame:%d time:%s ms fps:%.0f speed:%.2fx",
                    percentage * 100,
                    progress.status,
                    progress.frame,
                    FFmpegUtils.toTimecode(progress.out_time_ns, TimeUnit.NANOSECONDS),
                    progress.fps.doubleValue(),
                    progress.speed
                ));
            }
        });

        stream.setLiveStreamStatus(LiveStreamStatus.STREAMING);

        return stream;
    }
}
