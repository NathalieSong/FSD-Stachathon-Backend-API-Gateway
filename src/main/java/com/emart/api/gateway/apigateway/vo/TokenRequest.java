package com.emart.api.gateway.apigateway.vo;

import lombok.Data;

@Data
public class TokenRequest {
    private String token;

    public TokenRequest(String token) {
        this.token = token;
    }
}