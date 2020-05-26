package com.emart.api.gateway.apigateway.exception;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
    private final HttpStatus status;
    private final String message;
    private final Integer code;

    protected ErrorResponse(HttpStatus status, final String message, final Integer code) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public static ErrorResponse of(HttpStatus status, final String message, final Integer code) {
        return new ErrorResponse(status, message, code);
    }

    public Integer getStatus() {
        return status.value();
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}