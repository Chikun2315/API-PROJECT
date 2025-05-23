package com.authService.Config;

import com.authService.Service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Service;


@Service
public class AuthConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    String[] openUrls = {
            "/api/v1/auth/register",
            "/api/v1/auth/login",
            "/api/v1/welcome/msg",
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
        }).csrf().disable().httpBasic().disable();
        return http.build();
   }
   @Bean
   public AuthenticationManager authManager(AuthenticationConfiguration authConfig) throws Exception{
        return authConfig.getAuthenticationManager();
   }

   @Bean
   public AuthenticationProvider AauthProvider (){
       DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
       authProvider.setUserDetailsService(customUserDetailsService);
       authProvider.setPasswordEncoder(getEncode());
       return authProvider;
   }

}
