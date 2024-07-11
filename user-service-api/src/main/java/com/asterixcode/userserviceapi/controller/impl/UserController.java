package com.asterixcode.userserviceapi.controller.impl;

import com.asterixcode.userserviceapi.controller.UserControllerInterface;
import com.asterixcode.userserviceapi.entity.User;
import com.asterixcode.userserviceapi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UserControllerInterface {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @Override
  public ResponseEntity<User> findById(String id) {
    return ResponseEntity.ok().body(userService.findById(id));
  }
}
