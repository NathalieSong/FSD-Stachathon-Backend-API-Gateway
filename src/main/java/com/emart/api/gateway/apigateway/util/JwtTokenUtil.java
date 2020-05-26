package com.emart.api.gateway.apigateway.util;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = 1L;

    public String getTokenFromRequest(HttpServletRequest request) {
        final String reqTokenHeader = request.getHeader("Authorization");
        if (reqTokenHeader != null && reqTokenHeader.startsWith("Bearer ")) {
            return reqTokenHeader.substring(7);
        }
        return null;
    }
}