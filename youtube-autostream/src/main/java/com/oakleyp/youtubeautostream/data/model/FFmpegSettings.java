package com.oakleyp.youtubeautostream.data.model;

import javax.persistence.Entity;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonView;
import com.oakleyp.youtubeautostream.data.model.json.View;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ffmpeg_settings")
@Table(name = "ffmpeg_settings")
public class FFmpegSettings extends AuditableEntity {
    @JsonView(View.Summary.class)
    @NotEmpty
    private Integer audioChannels;

    @JsonView(View.Summary.class)
    @NotEmpty
    private String audioCodec;

    @JsonView(View.Summary.class)
    @NotEmpty
    private Integer audioSampleRate;

    @JsonView(View.Summary.class)
    @NotEmpty
    private Integer audioBitRate;

    @JsonView(View.Summary.class)
    @NotEmpty
    private String videoCodec;

    @JsonView(View.Summary.class)
    @NotEmpty
    private Integer videoFPS;

    @JsonView(View.Summary.class)
    @NotEmpty
    private Integer videoResWidth;

    @JsonView(View.Summary.class)
    @NotEmpty
    private Integer videoResHeight;

    @JsonView(View.Summary.class)
    @OneToOne(mappedBy = "ffmpegSettings")
    private LiveStream liveStream;
}
