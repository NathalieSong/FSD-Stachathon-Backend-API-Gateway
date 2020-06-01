package com.emart.api.gateway.apigateway.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.emart.api.gateway.apigateway.Service.UserServiceFeignClient;
import com.emart.api.gateway.apigateway.exception.EmartException;
import com.emart.api.gateway.apigateway.util.JwtTokenUtil;
import com.emart.api.gateway.apigateway.vo.JwtUser;
import com.emart.api.gateway.apigateway.vo.TokenRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private UserServiceFeignClient jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwtToken = jwtTokenUtil.getTokenFromRequest(request);
        if (jwtToken != null && !jwtToken.isEmpty()) {
            TokenRequest tokenReq = new TokenRequest(jwtToken);
            JwtUser userDetails;
            try {
                userDetails = this.jwtUserDetailsService.getUserByToken(tokenReq);
                UsernamePasswordAuthenticationToken usernamePasswordAuthToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthToken);
            } catch (EmartException e) {
                e.printStackTrace();
            }
        }
        filterChain.doFilter(request, response);
    }

}