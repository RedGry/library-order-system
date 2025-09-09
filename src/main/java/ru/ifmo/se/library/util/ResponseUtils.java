package ru.ifmo.se.library.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.ifmo.se.library.web.exception.model.ErrorResponse;

import java.time.Instant;

@Component
public class ResponseUtils {
    public ResponseEntity<ErrorResponse> buildErrorResponseWithMessage(HttpStatus status, String message, String path){
        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorResponse.builder()
                        .message(message)
                        .timestamp(Instant.now().toString())
                        .path(path)
                        .build()
                );
    }
}
