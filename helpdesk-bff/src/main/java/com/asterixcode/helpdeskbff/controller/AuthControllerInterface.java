package com.asterixcode.helpdeskbff.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import models.exceptions.StandardError;
import models.requests.AuthenticationRequest;
import models.requests.RefreshTokenRequest;
import models.responses.AuthenticationResponse;
import models.responses.RefreshTokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/auth")
public interface AuthControllerInterface {

  @Operation(summary = "Authenticate user")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "User authenticated",
            content =
                @Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AuthenticationResponse.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content =
                @Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = StandardError.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Bad Credentials",
            content =
                @Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = StandardError.class))),
        @ApiResponse(
            responseCode = "404",
            description = "User not found",
            content =
                @Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = StandardError.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @PostMapping("/login")
  ResponseEntity<AuthenticationResponse> authentication(
      @Valid @RequestBody final AuthenticationRequest request) throws Exception;

  @Operation(summary = "Refresh token")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Token refreshed",
            content =
                @Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = RefreshTokenResponse.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content =
                @Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = StandardError.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content =
                @Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = StandardError.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Username not found",
            content =
                @Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = StandardError.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content =
                @Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = StandardError.class)))
      })
  @PostMapping("/refresh-token")
  ResponseEntity<RefreshTokenResponse> refreshToken(
      @Valid @RequestBody final RefreshTokenRequest refreshToken);
}
