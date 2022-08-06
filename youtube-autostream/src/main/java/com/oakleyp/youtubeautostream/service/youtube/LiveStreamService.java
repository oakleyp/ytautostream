package com.oakleyp.youtubeautostream.service.youtube;

import org.springframework.beans.factory.annotation.Autowired;

import com.oakleyp.youtubeautostream.clients.youtube.YouTubeClient;

public class LiveStreamService {
    @Autowired
    private YouTubeClient ytClient;
}
