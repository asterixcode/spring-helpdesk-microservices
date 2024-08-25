package com.asterixcode.helpdeskbff.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

  private final JWTUtil jwtUtil;

  public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
    super(authenticationManager);
    this.jwtUtil = jwtUtil;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (authorizationHeader == null) {
      chain.doFilter(request, response);
      return;
    }

    if (authorizationHeader.startsWith("Bearer ")) {
      // Validate token
      UsernamePasswordAuthenticationToken auth = getAuthentication(request);
      // If token is valid, set the authentication in the context
      if (auth != null) SecurityContextHolder.getContext().setAuthentication(auth);
    } else {
      chain.doFilter(request, response);
      return;
    }
    chain.doFilter(request, response);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
    final String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring("Bearer ".length());
    // Validate token
    //jwtUtil.validateToken(token);

    String username = jwtUtil.getUsernameFromToken(token);
    Claims claims = jwtUtil.getAllClaimsFromToken(token);
    List<GrantedAuthority> authorities = jwtUtil.getAuthoritiesFromToken(claims);

    return username != null
        ? new UsernamePasswordAuthenticationToken(username, null, authorities)
        : null;
  }
}
