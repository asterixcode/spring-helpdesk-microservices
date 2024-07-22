package com.asterixcode.authserviceapi.util;

import com.asterixcode.authserviceapi.security.dto.UserDetailsDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtils {

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private Long expiration;

  public String generateToken(final UserDetailsDTO dto) {
    return Jwts.builder()
            .claim("id", dto.getId())
            .claim("name", dto.getName())
            .claim("authorities", dto.getAuthorities())
            .setSubject(dto.getUsername())
            .signWith(SignatureAlgorithm.HS512, secret.getBytes())
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .compact();
  }
}
