package com.asterixcode.userserviceapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import models.exceptions.StandardError;
import models.responses.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "User Controller", description = "Controller responsible for user operations")
@RequestMapping("/api/v1/users")
public interface UserControllerInterface {

  @Operation(summary = "Find user by id", description = "Returns a single user")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(
            responseCode = "404",
            description = "User not found",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandardError.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandardError.class)))
      })
  @GetMapping("/{id}")
  ResponseEntity<UserResponse> findById(
      @Parameter(
              description = "Id of the user to be obtained. Cannot be empty.",
              required = true,
              example = "66903ead0437ce1fc5704bec")
          @PathVariable(name = "id")
          final String id);
}
