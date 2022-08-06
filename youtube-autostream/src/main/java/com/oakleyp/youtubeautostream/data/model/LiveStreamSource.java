package com.oakleyp.youtubeautostream.data.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.oakleyp.youtubeautostream.data.model.json.View;

import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "stream_sources")
@Table(name = "stream_sources")
public class LiveStreamSource extends AuditableEntity {

    @JsonView(View.Summary.class)
    @NotEmpty
    private String name;

    @JsonView(View.Summary.class)
    @NotEmpty
    private String fname;

    @JsonView(View.Summary.class)
    @NotEmpty
    private String format;
}
