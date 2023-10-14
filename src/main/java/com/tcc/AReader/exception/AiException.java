package com.tcc.areader.exception;

import org.apache.http.HttpResponse;

public class AiException extends RuntimeException {
    private HttpResponse response;

    public AiException(String message, HttpResponse response) {
        super(message);
        this.response = response;
    }

    public HttpResponse getResponse() {
        return response;
    }
}
