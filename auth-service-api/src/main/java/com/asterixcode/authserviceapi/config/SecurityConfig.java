package com.asterixcode.authserviceapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            authorizeHttp -> {
              authorizeHttp.requestMatchers("/swagger-ui/**").permitAll();
              authorizeHttp.requestMatchers("/swagger-ui.html").permitAll();
              authorizeHttp.requestMatchers("/v3/api-docs/**").permitAll();
              authorizeHttp.requestMatchers("/api/v1/auth/**").permitAll();
              authorizeHttp.anyRequest().authenticated();
            })
        .sessionManagement(AbstractHttpConfigurer::disable)
        .build();
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
