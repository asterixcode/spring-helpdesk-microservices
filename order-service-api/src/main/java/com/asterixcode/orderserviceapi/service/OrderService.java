package com.asterixcode.orderserviceapi.service;

import com.asterixcode.orderserviceapi.mapper.OrderMapper;
import com.asterixcode.orderserviceapi.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import models.requests.CreateOrderRequest;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class OrderService implements OrderServiceInterface {

  private final OrderRepository repository;
  private final OrderMapper mapper;

  @Override
  public void save(CreateOrderRequest createOrderRequest) {
    final var entity = repository.save(mapper.fromRequest(createOrderRequest));
    log.info("Order created: {}", entity);
  }
}
