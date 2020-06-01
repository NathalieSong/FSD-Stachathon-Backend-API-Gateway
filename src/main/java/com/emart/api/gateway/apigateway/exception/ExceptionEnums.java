package com.emart.api.gateway.apigateway.exception;

public enum ExceptionEnums implements ExceptionInterface {
    USERNAME_ALREADY_EXISTED(10000, "Username already existed"),
    INSUFFICIENT_AUTH(403, "No Permission for this operation"),
    INVALID_TOKEN(403, "Token is invalid");

    public Integer code;
    public String message;

    private ExceptionEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}