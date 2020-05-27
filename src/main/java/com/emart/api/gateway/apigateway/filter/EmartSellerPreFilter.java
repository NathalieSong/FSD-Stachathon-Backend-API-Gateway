package com.emart.api.gateway.apigateway.filter;

import javax.servlet.http.HttpServletResponse;

import com.emart.api.gateway.apigateway.Service.UserServiceFeignClient;
import com.emart.api.gateway.apigateway.config.PathFilterConfig;
import com.emart.api.gateway.apigateway.exception.EmartException;
import com.emart.api.gateway.apigateway.exception.ErrorResponse;
import com.emart.api.gateway.apigateway.exception.ExceptionEnums;
import com.emart.api.gateway.apigateway.util.JwtTokenUtil;
import com.emart.api.gateway.apigateway.util.PathUtil;
import com.emart.api.gateway.apigateway.vo.TokenRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class EmartSellerPreFilter extends ZuulFilter {
    @Autowired
    private PathFilterConfig pathFilterConfig;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserServiceFeignClient userClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        String path = ctx.getRequest().getRequestURI();
        return pathFilterConfig.getSellerPath().stream()
          .anyMatch(pattern -> PathUtil.isPathMatch(pattern, path));
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        TokenRequest tokenReq = new TokenRequest(
            jwtTokenUtil.getTokenFromRequest(ctx.getRequest())
        );
        try {
            userClient.checkSellerByToken(tokenReq);
        } catch (EmartException ee) {
            ctx.setSendZuulResponse(false);
            responseError(ctx, ExceptionEnums.INSUFFICIENT_AUTH);
        }
        return null;
    }

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    private void responseError(RequestContext ctx, ExceptionEnums eEnums) {
        HttpServletResponse response = ctx.getResponse();
        ctx.setResponseBody(toJsonString(
            ErrorResponse.of(HttpStatus.FORBIDDEN, eEnums.getMessage(), eEnums.getCode())
        ));
        response.setContentType("application/json");
    }

    private String toJsonString(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}