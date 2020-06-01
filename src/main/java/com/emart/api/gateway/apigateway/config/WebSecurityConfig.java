package com.emart.api.gateway.apigateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthEntryPoint;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().logout().disable().formLogin().disable()
          .exceptionHandling().authenticationEntryPoint(jwtAuthEntryPoint)
          .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          .and().authorizeRequests()
          .antMatchers("/user/**").permitAll()
          .antMatchers("/item/cart/**").hasRole("buyer")
          .antMatchers("/item/category/**").permitAll()
          .antMatchers("/item/subCategory/**").permitAll()
          .antMatchers("/item/emartItem/getAll").permitAll()
          .antMatchers("/item/emartItem/byId").permitAll()
          .antMatchers("/item/emartItem/byText").permitAll()
          .antMatchers("/item/emartItem/byFilter").permitAll()
          .antMatchers("/item/emartItem/add").hasRole("seller")
          .antMatchers("/item/emartItem/update").hasRole("seller")
          .antMatchers("/order/discount/**").permitAll()
          .antMatchers("/order/financial/**").hasRole("buyer")
          .antMatchers("/order/purchaseHistory/**").hasRole("buyer")
          .antMatchers("/order/selling/**").hasRole("seller")
          .anyRequest().authenticated()
          .and().addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
      
      httpSecurity.headers().cacheControl();
    }
    
}