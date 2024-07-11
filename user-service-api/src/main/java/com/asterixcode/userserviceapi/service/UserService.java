package com.asterixcode.userserviceapi.service;

import com.asterixcode.userserviceapi.entity.User;
import com.asterixcode.userserviceapi.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User findById(final String id) {
    return userRepository.findById(id).orElse(null);
  }
}
