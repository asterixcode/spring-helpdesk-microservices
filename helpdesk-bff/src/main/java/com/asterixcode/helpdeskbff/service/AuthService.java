package com.asterixcode.helpdeskbff.service;

import com.asterixcode.helpdeskbff.client.AuthFeignClient;
import lombok.RequiredArgsConstructor;
import models.requests.AuthenticationRequest;
import models.requests.RefreshTokenRequest;
import models.responses.AuthenticationResponse;
import models.responses.RefreshTokenResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final AuthFeignClient feignClient;

  public AuthenticationResponse authentication(AuthenticationRequest request) throws Exception {
    return feignClient.authentication(request).getBody();
  }

  public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
    return feignClient.refreshToken(request).getBody();
  }
}
