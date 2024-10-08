package com.asterixcode.userserviceapi.mapper;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.*;

import com.asterixcode.userserviceapi.entity.User;
import models.requests.CreateUserRequest;
import models.requests.UpdateUserRequest;
import models.responses.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = IGNORE,
    nullValueCheckStrategy = ALWAYS)
public interface UserMapper {
  UserResponse fromEntity(User entity);

  @Mapping(target = "id", ignore = true)
  User fromRequest(CreateUserRequest createUserRequest);

  @Mapping(target = "id", ignore = true)
  User updateEntity(UpdateUserRequest updateUserRequest, @MappingTarget User entity);
}
