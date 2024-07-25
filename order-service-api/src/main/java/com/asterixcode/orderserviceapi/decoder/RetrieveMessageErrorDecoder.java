package com.asterixcode.orderserviceapi.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.InputStream;
import java.util.Map;
import models.exceptions.GenericFeignException;

public class RetrieveMessageErrorDecoder implements ErrorDecoder {
  @Override
  public Exception decode(String s, Response response) {
    try (InputStream bodyIs = response.body().asInputStream()) {
      ObjectMapper mapper = new ObjectMapper();
      final var error = mapper.readValue(bodyIs, Map.class);
      final var status = (Integer) error.get("status");
      return new GenericFeignException(status, error);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
