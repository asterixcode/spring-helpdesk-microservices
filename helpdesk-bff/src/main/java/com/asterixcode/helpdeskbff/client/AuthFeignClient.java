package com.asterixcode.helpdeskbff.client;

import jakarta.validation.Valid;
import models.requests.AuthenticationRequest;
import models.requests.RefreshTokenRequest;
import models.responses.AuthenticationResponse;
import models.responses.RefreshTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "localhost:8765/auth-service-api", path = "/api/v1/auth")
public interface AuthFeignClient {

  @PostMapping("/login")
  ResponseEntity<AuthenticationResponse> authentication(
      @RequestBody final AuthenticationRequest request) throws Exception;

  @PostMapping("/refresh-token")
  ResponseEntity<RefreshTokenResponse> refreshToken(
      @Valid @RequestBody final RefreshTokenRequest refreshToken);
}
