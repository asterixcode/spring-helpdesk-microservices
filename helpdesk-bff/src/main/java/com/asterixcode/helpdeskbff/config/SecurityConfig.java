package com.asterixcode.helpdeskbff.config;

import com.asterixcode.helpdeskbff.security.JWTAuthorizationFilter;
import com.asterixcode.helpdeskbff.security.JWTUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  private final AuthenticationConfiguration authenticationConfiguration;
  private final JWTUtil jwtUtil;

  protected static final String[] SWAGGER_WHITELIST = {
    "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**", "/swagger-resources/**", "/webjars/**"
  };

  protected static final String[] AUTHENTICATE_WHITELIST = {
    "/api/v1/auth/login", "/api/v1/auth/refresh-token"
  };

  public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil) {
    this.authenticationConfiguration = authenticationConfiguration;
    this.jwtUtil = jwtUtil;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http.addFilterBefore(
            new JWTAuthorizationFilter(
                authenticationConfiguration.getAuthenticationManager(), jwtUtil),
            JWTAuthorizationFilter.class)
        // Disable CSRF
        .csrf(AbstractHttpConfigurer::disable)
        // Disable session management (no session will be created)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // Define public and private endpoints
        .authorizeHttpRequests(
            authorize ->
                authorize
                    .requestMatchers(SWAGGER_WHITELIST)
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, AUTHENTICATE_WHITELIST)
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .build();
  }
}
