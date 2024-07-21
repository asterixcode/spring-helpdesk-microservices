package com.asterixcode.userserviceapi;

import com.asterixcode.userserviceapi.containers.MongoDBTestContainerConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = MongoDBTestContainerConfig.class)
@ActiveProfiles("it")
class UserServiceApiApplicationTests {

  @Test
  void contextLoads() {}
}
