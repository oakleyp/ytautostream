package com.oakleyp.youtubeautostream.rest.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import static org.springframework.http.ResponseEntity.*;


import com.oakleyp.youtubeautostream.rest.service.LiveStreamNotFoundException;

@RestControllerAdvice
public class LiveStreamExceptionHandler {
    @ExceptionHandler(LiveStreamNotFoundException.class)
    public ResponseEntity<?> notFound(LiveStreamNotFoundException ex, WebRequest req) {
        Map<String, String> errors = new HashMap<>();
        errors.put("entity", "POST");
        errors.put("id", "" + ex.getId());
        errors.put("code", "not_found");
        errors.put("message", ex.getMessage());

        return status(HttpStatus.NOT_FOUND).body(errors);
    }    
}
