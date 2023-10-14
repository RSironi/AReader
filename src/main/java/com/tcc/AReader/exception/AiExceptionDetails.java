package com.tcc.areader.exception;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AiExceptionDetails {

    private String title;
    private int status;
    private String message;
    private String classification;
    private LocalDateTime timestamp;
    private String developerMessage;
}
