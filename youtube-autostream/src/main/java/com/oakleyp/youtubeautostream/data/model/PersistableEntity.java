package com.oakleyp.youtubeautostream.data.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonView;
import com.oakleyp.youtubeautostream.data.model.json.View;

import lombok.Data;

@Data
@MappedSuperclass
public abstract class PersistableEntity implements Serializable {
    @Id
    @GeneratedValue
    @JsonView(View.Summary.class)
    protected Long id;

    public PersistableEntity() {
    }
}
