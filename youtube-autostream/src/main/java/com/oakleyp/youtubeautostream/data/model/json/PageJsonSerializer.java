package com.oakleyp.youtubeautostream.data.model.json;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.PageImpl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@JsonComponent
public class PageJsonSerializer<T> extends JsonSerializer<PageImpl<T>> {

    @Override
    public void serialize(PageImpl<T> value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        gen.writeStartObject();
        gen.writeNumberField("number", value.getNumber());
        gen.writeNumberField("numberOfElements", value.getNumberOfElements());
        gen.writeNumberField("totalElements", value.getTotalElements());
        gen.writeNumberField("totalPages", value.getTotalPages());
        gen.writeNumberField("size", value.getSize());
        gen.writeFieldName("content");
        serializers.defaultSerializeValue(value.getContent(), gen);
        gen.writeEndObject();
    }
}
