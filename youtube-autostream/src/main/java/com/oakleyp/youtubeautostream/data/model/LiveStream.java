package com.oakleyp.youtubeautostream.data.model;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonView;
import com.oakleyp.youtubeautostream.data.model.json.View;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "streams")
@Table(name = "streams")
public class LiveStream extends AuditableEntity {
    
    @JsonView(View.Summary.class)
    @NotEmpty
    private String name;

    @ElementCollection
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "streams_sources",
        joinColumns = @JoinColumn(name = "stream_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "source_id", referencedColumnName = "id")
    )
    @JsonView(View.Summary.class)
    private List<LiveStreamSource> sources;

    @JsonView(View.Summary.class)
    private String description;
    @JsonView(View.Summary.class)
    private Boolean enabled;

    @OneToOne(optional = true)
    @JoinColumn(name = "current_source_id", referencedColumnName = "id")
    @JsonView(View.Summary.class)
    private LiveStreamSource currentSource;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @JsonView(View.Summary.class)
    private LiveStreamStatus liveStreamStatus = LiveStreamStatus.STOPPED;

    @OneToOne
    @JoinColumn(name = "ffmpeg_settings_id")
    @JsonView(View.Summary.class)
    private FFmpegSettings ffmpegSettings;
}
