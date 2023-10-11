package com.tcc.areader.exceptions;

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
