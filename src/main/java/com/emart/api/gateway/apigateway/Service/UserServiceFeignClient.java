package com.emart.api.gateway.apigateway.Service;

import com.emart.api.gateway.apigateway.exception.EmartException;
import com.emart.api.gateway.apigateway.vo.TokenRequest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "emart-user-service")
@RequestMapping("auth")
public interface UserServiceFeignClient {
    @PostMapping("/buyer")
    public ResponseEntity<?> checkBuyerByToken(@RequestBody TokenRequest tokenReq) throws EmartException;

    @PostMapping("/seller")
    public ResponseEntity<?> checkSellerByToken(@RequestBody TokenRequest tokenReq) throws EmartException;
}