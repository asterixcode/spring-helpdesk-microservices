package com.asterixcode.userserviceapi.service;

import com.asterixcode.userserviceapi.entity.User;
import com.asterixcode.userserviceapi.mapper.UserMapper;
import com.asterixcode.userserviceapi.repository.UserRepository;
import java.util.List;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateUserRequest;
import models.requests.UpdateUserRequest;
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
    return userMapper.fromEntity(findByIdOrThrow(id));
  }

  public void save(CreateUserRequest createUserRequest) {
    verifyIfEmailExists(createUserRequest.email(), null);
    userRepository.save(userMapper.fromRequest(createUserRequest));
  }

  public List<UserResponse> findAll() {
    return userRepository.findAll().stream().map(userMapper::fromEntity).toList();
  }

  public UserResponse update(String id, UpdateUserRequest updateUserRequest) {
    User entity = findByIdOrThrow(id);
    verifyIfEmailExists(updateUserRequest.email(), id);
    final var updatedEntity =
        userRepository.save(userMapper.updateEntity(updateUserRequest, entity));
    return userMapper.fromEntity(updatedEntity);
  }

  private User findByIdOrThrow(final String id) {
    return userRepository
        .findById(id)
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    "Object not found. Id: "
                        + id
                        + ", Type: "
                        + UserResponse.class.getSimpleName()));
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
