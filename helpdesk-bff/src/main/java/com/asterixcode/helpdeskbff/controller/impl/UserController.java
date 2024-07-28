package com.asterixcode.helpdeskbff.controller.impl;

import static org.springframework.http.HttpStatus.*;

import com.asterixcode.helpdeskbff.controller.UserControllerInterface;
import com.asterixcode.helpdeskbff.service.UserService;
import java.util.List;
import models.requests.CreateUserRequest;
import models.requests.UpdateUserRequest;
import models.responses.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UserControllerInterface {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @Override
  public ResponseEntity<UserResponse> findById(String id) {
    return ResponseEntity.ok().body(userService.findById(id));
  }

  @Override
  public ResponseEntity<Void> save(CreateUserRequest createUserRequest) {
    userService.save(createUserRequest);
    return ResponseEntity.status(CREATED.value()).build();
  }

  @Override
  public ResponseEntity<List<UserResponse>> findAll() {
    return ResponseEntity.ok().body(userService.findAll());
  }

  @Override
  public ResponseEntity<UserResponse> update(String id, UpdateUserRequest createUserRequest) {
    return ResponseEntity.ok().body(userService.update(id, createUserRequest));
  }
}
