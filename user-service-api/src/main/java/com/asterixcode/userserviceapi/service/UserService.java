package com.asterixcode.userserviceapi.service;

import com.asterixcode.userserviceapi.mapper.UserMapper;
import com.asterixcode.userserviceapi.repository.UserRepository;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateUserRequest;
import models.responses.UserResponse;
import org.springframework.dao.DataIntegrityViolationException;
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
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Object not found. Id: "
                            + id
                            + ", Type: "
                            + UserResponse.class.getSimpleName())));
  }

  public void save(CreateUserRequest createUserRequest) {
    verifyIfEmailExists(createUserRequest.email(), null);
    userRepository.save(userMapper.fromRequest(createUserRequest));
  }

  private void verifyIfEmailExists(final String email, final String id) {
    userRepository
        .findByEmail(email)
        .filter(user -> !user.getId().equals(id))
        .ifPresent(
            user -> {
              throw new DataIntegrityViolationException(
                  "Email [ %s ] already exists".formatted(email));
            });
  }
}
