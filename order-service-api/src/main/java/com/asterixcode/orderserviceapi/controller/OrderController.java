package com.asterixcode.orderserviceapi.controller;

import com.asterixcode.orderserviceapi.service.OrderServiceInterface;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController implements OrderControllerInterface {

  private final OrderServiceInterface service;

  public OrderController(OrderServiceInterface service) {
    this.service = service;
  }

  @Override
  public ResponseEntity<Void> save(CreateOrderRequest createOrderRequest) {
    service.save(createOrderRequest);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @Override
  public ResponseEntity<OrderResponse> update(final Long id, UpdateOrderRequest request) {
    return ResponseEntity.ok().body(service.update(id, request));
  }
}
