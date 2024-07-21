package com.asterixcode.userserviceapi.controller.impl;

import static com.asterixcode.userserviceapi.creator.Creator.generateMock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.asterixcode.userserviceapi.containers.MongoDBTestContainerConfig;
import com.asterixcode.userserviceapi.entity.User;
import com.asterixcode.userserviceapi.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = MongoDBTestContainerConfig.class)
@ActiveProfiles("it")
@AutoConfigureMockMvc
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

  @Test
  void testFindAllWithSuccess() throws Exception {
    final var entity1 = generateMock(User.class);
    final var entity2 = generateMock(User.class);

    userRepository.saveAll(List.of(entity1, entity2));

    mockMvc
        .perform(get("/api/v1/users"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$.[0]").isNotEmpty())
        .andExpect(jsonPath("$.[1]").isNotEmpty())
        .andExpect(jsonPath("$.[0].id").value(entity1.getId()))
        .andExpect(jsonPath("$.[0].name").value(entity1.getName()))
        .andExpect(jsonPath("$.[0].email").value(entity1.getEmail()))
        .andExpect(jsonPath("$.[0].password").value(entity1.getPassword()))
        .andExpect(jsonPath("$.[0].profiles").isArray())
        .andExpect(jsonPath("$.[1].id").value(entity2.getId()))
        .andExpect(jsonPath("$.[1].name").value(entity2.getName()))
        .andExpect(jsonPath("$.[1].email").value(entity2.getEmail()))
        .andExpect(jsonPath("$.[1].password").value(entity2.getPassword()))
        .andExpect(jsonPath("$.[1].profiles").isArray());

    userRepository.deleteAll(List.of(entity1, entity2));
  }

  @Test
  void testSaveUserWithSuccess() throws Exception {
    final var validEmail = "ahi213uashe12@mail.com";
    final var request = generateMock(User.class).withEmail(validEmail);

    mockMvc
        .perform(
            post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(request)))
        .andExpect(status().isCreated());

    userRepository.deleteByEmail(validEmail);
  }

  private String toJson(final Object object) {
    try {
      return new ObjectMapper().writeValueAsString(object);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
