package com.authService.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Service;


@Service
public class AuthConfig {

    String[] openUrls = {
            "/api/v1/auth/register",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/webjars/**"
    };
    @Bean
   public PasswordEncoder getEncode(){
       return new BCryptPasswordEncoder();
   }
    @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        http.authorizeHttpRequests(req->{
          req.requestMatchers(openUrls).permitAll().anyRequest().authenticated();
        }).csrf().disable();
        return http.build();
   }

}
