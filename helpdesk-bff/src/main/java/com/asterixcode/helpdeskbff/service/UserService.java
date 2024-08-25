package com.asterixcode.helpdeskbff.service;

import com.asterixcode.helpdeskbff.client.UserFeignClient;
import java.util.List;
import models.requests.CreateUserRequest;
import models.requests.UpdateUserRequest;
import models.responses.UserResponse;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  
  private final UserFeignClient userFeignClient;

  public UserService(UserFeignClient userFeignClient) {
    this.userFeignClient = userFeignClient;
  }

  @Cacheable(value = "users", key = "#id")
  public UserResponse findById(final String id) {
    return userFeignClient.findById(id).getBody();
  }

  @CacheEvict(value = "users", allEntries = true)
  public void save(CreateUserRequest request) {
    userFeignClient.save(request);
  }

  @Cacheable(value = "users")
  public List<UserResponse> findAll() {
    return userFeignClient.findAll().getBody();
  }

  @CacheEvict(value = "users", allEntries = true)
  public UserResponse update(String id, UpdateUserRequest request) {
    return userFeignClient.update(id, request).getBody();
  }
}
