package com.asterixcode.userserviceapi.service;

import com.asterixcode.userserviceapi.mapper.UserMapper;
import com.asterixcode.userserviceapi.repository.UserRepository;
import models.exceptions.ResourceNotFoundException;
import models.responses.UserResponse;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public UserService(UserRepository userRepository, UserMapper userMapper) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
  }

  public UserResponse findById(final String id) {
    return userMapper.fromEntity(
        userRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                    "Object not found. Id: " + id + ", Type: " + UserResponse.class.getSimpleName()
            ))
    );
  }
}
