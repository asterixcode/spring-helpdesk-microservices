package com.asterixcode.helpdeskbff.service;

import com.asterixcode.helpdeskbff.client.UserFeignClient;
import java.util.List;
import models.requests.CreateUserRequest;
import models.requests.UpdateUserRequest;
import models.responses.UserResponse;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  
  private final UserFeignClient userFeignClient;

  public UserService(UserFeignClient userFeignClient) {
    this.userFeignClient = userFeignClient;
  }

  public UserResponse findById(final String id) {
    return userFeignClient.findById(id).getBody();
  }

  public void save(CreateUserRequest request) {
    userFeignClient.save(request);
  }

  public List<UserResponse> findAll() {
    return userFeignClient.findAll().getBody();
  }

  public UserResponse update(String id, UpdateUserRequest request) {
    return userFeignClient.update(id, request).getBody();
  }
}
