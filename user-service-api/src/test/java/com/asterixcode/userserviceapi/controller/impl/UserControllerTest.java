package com.asterixcode.userserviceapi.controller.impl;

import static com.asterixcode.userserviceapi.creator.Creator.generateMock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.asterixcode.userserviceapi.containers.MongoDBTestContainerConfig;
import com.asterixcode.userserviceapi.entity.User;
import com.asterixcode.userserviceapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration(classes = MongoDBTestContainerConfig.class)
@ActiveProfiles("it")
class UserControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private UserRepository userRepository;

  @Test
  void testFindByWithSuccess() throws Exception {
    final var entity = generateMock(User.class);
    final var userId = userRepository.save(entity).getId();

    mockMvc
        .perform(get("/api/v1/users/{id}", userId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(userId))
        .andExpect(jsonPath("$.name").value(entity.getName()))
        .andExpect(jsonPath("$.email").value(entity.getEmail()))
        .andExpect(jsonPath("$.password").value(entity.getPassword()))
        .andExpect(jsonPath("$.profiles").isArray());

    userRepository.deleteById(userId);
  }

  @Test
  void testFindByWithNoFoundException() throws Exception {
    mockMvc
        .perform(get("/api/v1/users/{id}", "123"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message").value("Object not found. Id: 123, Type: UserResponse"))
        .andExpect(jsonPath("$.error").value(HttpStatus.NOT_FOUND.getReasonPhrase()))
        .andExpect(jsonPath("$.path").value("/api/v1/users/123"))
        .andExpect(jsonPath("$.status").value(404))
        .andExpect(jsonPath("$.timestamp").isNotEmpty());
  }
}
