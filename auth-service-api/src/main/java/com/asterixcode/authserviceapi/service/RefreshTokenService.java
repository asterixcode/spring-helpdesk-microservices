package com.asterixcode.authserviceapi.service;

import com.asterixcode.authserviceapi.models.RefreshToken;
import com.asterixcode.authserviceapi.repository.RefreshTokenRepository;
import com.asterixcode.authserviceapi.security.dto.UserDetailsDTO;
import com.asterixcode.authserviceapi.util.JWTUtils;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import models.exceptions.RefreshTokenExpiredException;
import models.exceptions.ResourceNotFoundException;
import models.responses.RefreshTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

  @Value("${jwt.expiration-seconds.refresh-token}")
  private long refreshTokenExpirationSeconds;

  private final RefreshTokenRepository repository;
  private final UserDetailsService userDetailsService;
  private final JWTUtils jwtUtils;

  public RefreshToken save(final String username) {
    return repository.save(
        RefreshToken.builder()
            .id(UUID.randomUUID().toString())
            .username(username)
            .createdAt(LocalDateTime.now())
            .expiresAt(LocalDateTime.now().plusSeconds(refreshTokenExpirationSeconds))
            .build());
  }

  public RefreshTokenResponse createRefreshToken(final String refreshTokenId) {
    final RefreshToken refreshToken =
        repository
            .findById(refreshTokenId)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Refresh token not found. Id: " + refreshTokenId));

    if (refreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {
      repository.delete(refreshToken);
      throw new RefreshTokenExpiredException("Refresh token expired. Id: " + refreshTokenId);
    }

    return new RefreshTokenResponse(
        jwtUtils.generateToken(
            (UserDetailsDTO) userDetailsService.loadUserByUsername(refreshToken.getUsername())));
  }
}
