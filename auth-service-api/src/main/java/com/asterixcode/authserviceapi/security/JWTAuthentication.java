package com.asterixcode.authserviceapi.security;

import com.asterixcode.authserviceapi.security.dto.UserDetailsDTO;
import com.asterixcode.authserviceapi.util.JWTUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import models.requests.AuthenticationRequest;
import models.responses.AuthenticationResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Log4j2
@RequiredArgsConstructor
public class JWTAuthentication {

  private final JWTUtils jwtUtils;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse authenticate(final AuthenticationRequest request) {
    try {
      log.info("Authenticating user: {}", request.email());
      final var authResult =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(request.email(), request.password()));
      return buildAuthenticationResponse((UserDetailsDTO) authResult.getPrincipal());
    } catch (Exception e) {
      log.error("Error occurred while authenticating user: {}", request.email());
      throw new BadCredentialsException("Invalid username or password");
    }
  }

  protected AuthenticationResponse buildAuthenticationResponse(final UserDetailsDTO dto) {
    log.info("Successfully authenticated user: {}", dto.getUsername());
    final var token = jwtUtils.generateToken(dto);
    return AuthenticationResponse.builder().type("Bearer").token(token).build();
  }
}
