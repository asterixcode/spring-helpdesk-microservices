package com.asterixcode.userserviceapi.service;

import static com.asterixcode.userserviceapi.creator.Creator.generateMock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.asterixcode.userserviceapi.entity.User;
import com.asterixcode.userserviceapi.mapper.UserMapper;
import com.asterixcode.userserviceapi.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateUserRequest;
import models.requests.UpdateUserRequest;
import models.responses.UserResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("utest")
class UserServiceTest {

  @InjectMocks
  private UserService service;

  @Mock
  private UserRepository repository;

  @Mock
  private UserMapper mapper;

  @Mock
  private BCryptPasswordEncoder encoder;

  @Test
  void whenCallFindByIdWithValidIdThenReturnsUserResponse() {
    when(repository.findById(anyString())).thenReturn(Optional.of(new User()));
    when(mapper.fromEntity(any(User.class))).thenReturn(generateMock(UserResponse.class));

    final var response = service.findById("1");

    assertNotNull(response);
    assertEquals(UserResponse.class, response.getClass());

    verify(repository, times(1)).findById(anyString());
    verify(mapper, times(1)).fromEntity(any(User.class));
  }

  @Test
  void whenCallFindByIdWithInvalidIdThenThrowResourceNotFoundException() {
    when(repository.findById(anyString())).thenReturn(Optional.empty());

    try {
      service.findById("1");
    } catch (Exception e) {
      assertEquals(ResourceNotFoundException.class, e.getClass());
      assertEquals("Object not found. Id: 1, Type: UserResponse", e.getMessage());
    }

    verify(repository, times(1)).findById(anyString());
    verify(mapper, never()).fromEntity(any(User.class));
  }

  @Test
  void whenCallFindAllThenReturnListOfUserResponse() {
    when(repository.findAll()).thenReturn(List.of(new User(), new User()));
    when(mapper.fromEntity(any(User.class))).thenReturn(mock(UserResponse.class));

    final var response = service.findAll();

    assertNotNull(response);
    assertEquals(2, response.size());
    assertEquals(UserResponse.class, response.getFirst().getClass());

    verify(repository, times(1)).findAll();
    verify(mapper, times(2)).fromEntity(any(User.class));
  }

  @Test
  void whenCallSaveThenSuccess() {
    final var request = generateMock(CreateUserRequest.class);

    when(mapper.fromRequest(any())).thenReturn(new User());
    when(encoder.encode(anyString())).thenReturn("encoded");
    when(repository.save(any(User.class))).thenReturn(new User());
    when(repository.findByEmail(anyString())).thenReturn(Optional.empty());

    service.save(request);

    verify(mapper).fromRequest(request);
    verify(encoder).encode(request.password());
    verify(repository).save(any(User.class));
    verify(repository).findByEmail(request.email());
  }

  @Test
  void whenCallSaveWithInvalidEmailThenThrowDataIntegrityViolationException() {
    final var request = generateMock(CreateUserRequest.class);
    final var entity = generateMock(User.class);

    when(repository.findByEmail(anyString())).thenReturn(Optional.of(entity));

    try {
      service.save(request);
    } catch (Exception e) {
      assertEquals(DataIntegrityViolationException.class, e.getClass());
      assertEquals("Email [ " + request.email() + " ] already exists", e.getMessage());
    }

    verify(repository, times(1)).findByEmail(request.email());
    verify(mapper, never()).fromRequest(request);
    verify(encoder, never()).encode(request.password());
    verify(repository, never()).save(any(User.class));
  }

  @Test
  void whenCallUpdateWithInvalidIdThenThrowResourceNotFoundException() {
    final var request = generateMock(UpdateUserRequest.class);

    when(repository.findById(anyString())).thenReturn(Optional.empty());

    try {
      service.update("1", request);
    } catch (Exception e) {
      assertEquals(ResourceNotFoundException.class, e.getClass());
      assertEquals("Object not found. Id: 1, Type: UserResponse", e.getMessage());
    }

    verify(repository, times(1)).findById(anyString());
    verify(mapper, never()).updateEntity(any(), any());
    verify(encoder, never()).encode(request.password());
    verify(repository, never()).save(any(User.class));
  }

  @Test
  void whenCallUpdateWithInvalidEmailThenThrowDataIntegrityViolationException() {
    final var request = generateMock(UpdateUserRequest.class);
    final var entity = generateMock(User.class);

    when(repository.findById(anyString())).thenReturn(Optional.of(entity));
    when(repository.findByEmail(anyString())).thenReturn(Optional.of(entity));

    try {
      service.update("1", request);
    } catch (Exception e) {
      assertEquals(DataIntegrityViolationException.class, e.getClass());
      assertEquals("Email [ " + request.email() + " ] already exists", e.getMessage());
    }

    verify(repository, times(1)).findById(anyString());
    verify(repository, times(1)).findByEmail(request.email());
    verify(mapper, never()).updateEntity(any(), any());
    verify(encoder, never()).encode(request.password());
    verify(repository, never()).save(any(User.class));
  }

  @Test
  void whenCallUpdateWithValidParamsThenGetSuccess() {
    final var id = "1";
    final var request = generateMock(UpdateUserRequest.class);
    final var entity = generateMock(User.class).withId(id);

    when(repository.findById(anyString())).thenReturn(Optional.of(entity));
    when(repository.findByEmail(anyString())).thenReturn(Optional.of(entity));
    when(mapper.updateEntity(any(), any())).thenReturn(entity);
    when(repository.save(any(User.class))).thenReturn(entity);

    service.update(id, request);

    verify(repository).findById(anyString());
    verify(repository).findByEmail(request.email());
    verify(mapper).updateEntity(request, entity);
    verify(encoder).encode(request.password());
    verify(repository).save(any(User.class));
    verify(mapper).fromEntity(any(User.class));
  }
}
