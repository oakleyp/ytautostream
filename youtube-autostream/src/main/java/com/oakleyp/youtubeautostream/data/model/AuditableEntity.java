package com.oakleyp.youtubeautostream.data.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.oakleyp.youtubeautostream.data.model.json.View;

import java.time.LocalDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
public class AuditableEntity extends PersistableEntity {
    public AuditableEntity() {
    }

    @CreatedDate
    @JsonView(View.Summary.class)
    protected LocalDateTime createdDT;
}
