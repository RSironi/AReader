package com.tcc.areader.handler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcc.areader.exception.AiException;
import com.tcc.areader.exception.AiExceptionDetails;
import com.tcc.areader.exception.BadRequestException;
import com.tcc.areader.exception.BadRequestExceptionsDetails;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(AiException.class)
    public ResponseEntity<AiExceptionDetails> handlerAiException(AiException ai) throws JsonMappingException, JsonProcessingException, ParseException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(EntityUtils.toString(ai.getResponse().getEntity()));




        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(AiExceptionDetails.builder()
                        .title("API AI Exception, Check the Documentation")
                        .status(ai.getResponse().getStatusLine().getStatusCode())
                        .message(ai.getMessage())
                        .classification(jsonNode.get("classification").asText())
                        .timestamp(LocalDateTime.now())
                        .developerMessage(ai.getClass().getName())
                        .build());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionsDetails> handlerBadRequestException(BadRequestException bre) {
        return ResponseEntity.badRequest().body(BadRequestExceptionsDetails.builder()
                .title("Bad Request Exception, Check the Documentation")
                .status(400)
                .message(bre.getMessage())
                .timestamp(LocalDateTime.now())
                .developerMessage(bre.getClass().getName())
                .build());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return errors;
    }
}