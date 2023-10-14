package com.tcc.areader.exception;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BadRequestExceptionsDetails {
    private String title;
    private int status;
    private String message;
    private LocalDateTime timestamp;
    private String developerMessage;
}
