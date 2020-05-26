package com.emart.api.gateway.apigateway.config;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@ConfigurationProperties(prefix = "path-filter")
@Data
@Component
public class PathFilterConfig {
    private List<String> buyerPath;
    private List<String> sellerPath;
}