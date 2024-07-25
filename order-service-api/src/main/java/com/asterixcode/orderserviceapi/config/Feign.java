package com.asterixcode.orderserviceapi.config;

import com.asterixcode.orderserviceapi.decoder.RetrieveMessageErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Feign {

  @Bean
  public ErrorDecoder errorDecoder() {
    return new RetrieveMessageErrorDecoder();
  }
}
