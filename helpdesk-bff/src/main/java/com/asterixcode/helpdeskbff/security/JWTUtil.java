package com.asterixcode.helpdeskbff.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import java.util.LinkedHashMap;
import java.util.List;
import models.exceptions.JWTCustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil {

  @Value("${jwt.secret}")
  private String secret;

  public Claims getAllClaimsFromToken(final String token) {
    try {
      return Jwts.parserBuilder()
          .setSigningKey(secret.getBytes())
          .build()
          .parseClaimsJws(token)
          .getBody();
    } catch (ExpiredJwtException
        | UnsupportedJwtException
        | MalformedJwtException
        | SignatureException
        | IllegalArgumentException ex) {
      throw new JWTCustomException(ex.getMessage());
    }
  }

  public String getUsernameFromToken(String token) {
    Claims claims = getAllClaimsFromToken(token);
    return claims.getSubject() != null ? claims.getSubject() : null;
    // subject = 'sub' field in the jwt token payload, which contains the email
  }

  public List<GrantedAuthority> getAuthoritiesFromToken(Claims claims) {
    if (claims.get("authorities") != null) {
      var authorities = (List<LinkedHashMap<String, String>>) claims.get("authorities");

      return authorities.stream()
          .map(
              // foreach authority key fields, get the value of the 'authority' field
              authority -> (GrantedAuthority) () -> authority.get("authority"))
          .toList();
    }
    throw new JWTCustomException("No authorities found in token");
  }
}
