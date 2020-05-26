package com.emart.api.gateway.apigateway.exception;

public class EmartException extends Exception {
    private static final long serialVersionUID = 1L;
    
    private ExceptionEnums expEnums;

    public EmartException() {
        super();
    }

    public EmartException(ExceptionEnums expEnums) {
        this.expEnums = expEnums;
    }

    public ExceptionEnums getExceptionEnums() {
        return expEnums;
    }
}