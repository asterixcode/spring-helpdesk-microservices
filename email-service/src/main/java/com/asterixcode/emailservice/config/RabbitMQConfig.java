package com.asterixcode.emailservice.config;

import java.util.List;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  @Bean
  public SimpleMessageConverter messageConverter() {
    SimpleMessageConverter messageConverter = new SimpleMessageConverter();
    messageConverter.setAllowedListPatterns(
        List.of("models.*", "java.util.*", "java.lang.*", "java.time.*"));
    return messageConverter;
  }
}
