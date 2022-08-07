package com.oakleyp.youtubeautostream.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oakleyp.youtubeautostream.data.model.LiveStreamSource;

@Repository
public interface LiveStreamSourceRepository extends JpaRepository<LiveStreamSource, Long> {
}
