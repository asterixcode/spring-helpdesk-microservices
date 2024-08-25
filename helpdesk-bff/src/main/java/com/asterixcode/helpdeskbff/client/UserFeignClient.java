package com.asterixcode.helpdeskbff.client;

import com.asterixcode.helpdeskbff.config.FeignConfig;
import jakarta.validation.Valid;
import java.util.List;
import models.requests.CreateUserRequest;
import models.requests.UpdateUserRequest;
import models.responses.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(
    name = "localhost:8765/user-service-api", // TODO: Change this to the correct URL
    path = "/api/v1/users",
    configuration = FeignConfig.class)
public interface UserFeignClient {

  @GetMapping("/{id}")
  ResponseEntity<UserResponse> findById(@PathVariable(name = "id") final String id);

  @PostMapping
  ResponseEntity<Void> save(@Valid @RequestBody CreateUserRequest createUserRequest);

  @GetMapping
  ResponseEntity<List<UserResponse>> findAll();

  @PutMapping("/{id}")
  ResponseEntity<UserResponse> update(
      @PathVariable(name = "id") final String id,
      @Valid @RequestBody UpdateUserRequest createUserRequest);
}
