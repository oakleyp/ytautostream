package com.oakleyp.youtubeautostream.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.oakleyp.youtubeautostream.data.model.LiveStream;

@Repository
public interface LiveStreamRepository extends JpaRepository<LiveStream, Long>, JpaSpecificationExecutor<LiveStream> {
}
