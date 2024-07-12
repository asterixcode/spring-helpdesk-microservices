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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository repository;
  private final UserMapper mapper;
  private final BCryptPasswordEncoder encoder;

  public UserService(UserRepository repository, UserMapper mapper, BCryptPasswordEncoder encoder) {
    this.repository = repository;
    this.mapper = mapper;
    this.encoder = encoder;
  }

  public UserResponse findById(final String id) {
    return mapper.fromEntity(findByIdOrThrow(id));
  }

  public void save(CreateUserRequest request) {
    verifyIfEmailExists(request.email(), null);
    repository.save(mapper.fromRequest(request).withPassword(encoder.encode(request.password())));
  }

  public List<UserResponse> findAll() {
    return repository.findAll().stream().map(mapper::fromEntity).toList();
  }

  public UserResponse update(String id, UpdateUserRequest request) {
    User entity = findByIdOrThrow(id);
    verifyIfEmailExists(request.email(), id);
    final var updatedEntity =
        repository.save(
            mapper
                .updateEntity(request, entity)
                .withPassword(
                    request.password() != null
                        ? encoder.encode(request.password())
                        : entity.getPassword()));
    return mapper.fromEntity(updatedEntity);
  }

  private User findByIdOrThrow(final String id) {
    return repository
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
    repository
        .findByEmail(email)
        .filter(user -> !user.getId().equals(id))
        .ifPresent(
            user -> {
              throw new DataIntegrityViolationException(
                  "Email [ %s ] already exists".formatted(email));
            });
  }
}
