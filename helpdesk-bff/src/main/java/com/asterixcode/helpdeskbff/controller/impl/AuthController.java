package com.asterixcode.helpdeskbff.controller.impl;

import com.asterixcode.helpdeskbff.controller.AuthControllerInterface;
import com.asterixcode.helpdeskbff.service.AuthService;
import lombok.RequiredArgsConstructor;
import models.requests.AuthenticationRequest;
import models.requests.RefreshTokenRequest;
import models.responses.AuthenticationResponse;
import models.responses.RefreshTokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthControllerInterface {

  private final AuthService authService;

  @Override
  public ResponseEntity<AuthenticationResponse> authentication(AuthenticationRequest request)
      throws Exception {
    return ResponseEntity.ok().body(authService.authentication(request));
  }

  @Override
  public ResponseEntity<RefreshTokenResponse> refreshToken(RefreshTokenRequest tokenRequest) {
    return ResponseEntity.ok()
        .body(authService.refreshToken(tokenRequest));
  }
}
