package com.oakleyp.youtubeautostream.rest.controller;

import javax.validation.constraints.NotEmpty;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveStreamForm {
    private static final long serialVersionUID = 1L;

    @NotEmpty
    private String test;
}
