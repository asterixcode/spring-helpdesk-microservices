package com.asterixcode.orderserviceapi.config;

import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class CustomizeApplicationLoggingInterceptor implements HandlerInterceptor {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(CustomizeApplicationLoggingInterceptor.class);
  private static final String REQUEST_ID = "requestId";

  @Override
  public boolean preHandle(
      @Nonnull HttpServletRequest request,
      @Nonnull HttpServletResponse response,
      @Nonnull Object handler)
      throws Exception {
    String requestId = UUID.randomUUID().toString().substring(0, 6);
    MDC.put(REQUEST_ID, requestId);
    LOGGER.info("Received request with ID: {}", requestId);
    return true;
  }

  @Override
  public void afterCompletion(
      @Nonnull HttpServletRequest request,
      @Nonnull HttpServletResponse response,
      @Nonnull Object handler,
      Exception ex)
      throws Exception {
    String requestId = MDC.get(REQUEST_ID);
    LOGGER.info("Request with ID {} completed", requestId);
    MDC.remove(REQUEST_ID);
  }
}
