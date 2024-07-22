package com.asterixcode.authserviceapi.controller;

import com.asterixcode.authserviceapi.security.JWTAuthentication;
import com.asterixcode.authserviceapi.util.JWTUtils;
import lombok.RequiredArgsConstructor;
import models.requests.AuthenticationRequest;
import models.responses.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthControllerInterface {

  private final JWTUtils jwtUtils;
  private final AuthenticationConfiguration authenticationConfiguration;

  @Override
  public ResponseEntity<AuthenticationResponse> authentication(AuthenticationRequest request) throws Exception {
    return ResponseEntity.ok().body(
            new JWTAuthentication(jwtUtils, authenticationConfiguration.getAuthenticationManager())
                    .authenticate(request)
    );
  }
}
