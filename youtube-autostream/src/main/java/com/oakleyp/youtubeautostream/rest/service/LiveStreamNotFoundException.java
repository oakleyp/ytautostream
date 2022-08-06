package com.oakleyp.youtubeautostream.rest.service;

public class LiveStreamNotFoundException extends RuntimeException {
    private Long id;

    public LiveStreamNotFoundException(Long id) {
        super("liveStream: " + id + " was not found.");
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
