package com.asterixcode.userserviceapi.controller;


import models.responses.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/users")
public interface UserControllerInterface {

  @GetMapping("/{id}")
  ResponseEntity<UserResponse> findById(@PathVariable(name = "id") final String id);
}
