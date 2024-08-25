package com.asterixcode.helpdeskbff.decoder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.InputStream;
import java.util.Map;
import models.exceptions.GenericFeignException;
import models.exceptions.InternalServerErrorException;

public class RetrieveMessageErrorDecoder implements ErrorDecoder {
  @Override
  public Exception decode(String methodKey, Response response) {
    try (InputStream bodyIs = response.body().asInputStream()) {
      ObjectMapper mapper = new ObjectMapper();
      mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

      final var error = mapper.readValue(bodyIs, Map.class);
      final var status = (Integer) error.get("status");

      return new GenericFeignException(status, error);
    } catch (Exception e) {
      throw new InternalServerErrorException("Internal Server Error. Please try again later.");
    }
  }
}
