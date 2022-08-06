package com.oakleyp.youtubeautostream.rest.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oakleyp.youtubeautostream.service.youtube.LiveStreamService;
import com.fasterxml.jackson.annotation.JsonView;
import com.oakleyp.youtubeautostream.data.model.LiveStream;
import com.oakleyp.youtubeautostream.data.model.LiveStreamStatus;
import com.oakleyp.youtubeautostream.data.model.json.View;
import com.oakleyp.youtubeautostream.data.repository.LiveStreamRepository;
import com.oakleyp.youtubeautostream.data.repository.LiveStreamSpecifications;
import com.oakleyp.youtubeautostream.rest.service.LiveStreamNotFoundException;

import static org.springframework.http.ResponseEntity.*;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/streams")
@Slf4j
@RequiredArgsConstructor
public class LiveStreams {
    private final LiveStreamService liveStreamService;
    private final LiveStreamRepository liveStreamRepository;

    @GetMapping("")
    @JsonView(View.class)
    public ResponseEntity<Page<LiveStream>> getAllLiveStreams(
        @RequestParam(value = "q", required = false) String keyword,
        @RequestParam(value = "status", required = false) LiveStreamStatus status,
        @PageableDefault(page = 0, size = 10, sort = "createdDT", direction = Direction.DESC) Pageable page) {
            Page<LiveStream> liveStreams = liveStreamRepository.findAll(LiveStreamSpecifications.filterByKeywordAndStatus(keyword, status), page);

            return ok(liveStreams);
        }

    @PostMapping(value = "/publish/{id}")
    public ResponseEntity<LiveStream> publishLiveStream(@PathVariable("id") String id) {
        Long idl;

        try {
            idl = Long.valueOf(id);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        LiveStream liveStream = liveStreamRepository.findById(idl).orElseThrow(
            () -> new LiveStreamNotFoundException(idl)
        );

        return ok(liveStream);
    }


}
