package com.asterixcode.userserviceapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.asterixcode.userserviceapi.entity.User;
import com.asterixcode.userserviceapi.mapper.UserMapper;
import com.asterixcode.userserviceapi.repository.UserRepository;
import java.util.Optional;
import models.exceptions.ResourceNotFoundException;
import models.responses.UserResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
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
    when(mapper.fromEntity(any(User.class))).thenReturn(mock(UserResponse.class));

    final var response = service.findById("1");

    assertNotNull(response);
    assertEquals(UserResponse.class, response.getClass());

    verify(repository, times(1)).findById(anyString());
    verify(mapper, times(1)).fromEntity(any(User.class));
  }

  @Test
  void whenCallFindByIdWithInvalidIdThenThrowResourceNotFoundException() {
    when(repository.findById(anyString())).thenReturn(Optional.empty());

    try{
      service.findById("1");
    } catch (Exception e) {
      assertEquals(ResourceNotFoundException.class, e.getClass());
      assertEquals("Object not found. Id: 1, Type: UserResponse", e.getMessage());
    }

    verify(repository, times(1)).findById(anyString());
    verify(mapper, never()).fromEntity(any(User.class));
  }
}
