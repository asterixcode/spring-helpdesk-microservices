package com.asterixcode.orderserviceapi.controller;

import com.asterixcode.orderserviceapi.mapper.OrderMapper;
import com.asterixcode.orderserviceapi.service.OrderServiceInterface;
import java.util.List;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController implements OrderControllerInterface {

  private final OrderServiceInterface service;
  private final OrderMapper mapper;

  public OrderController(OrderServiceInterface service, OrderMapper mapper) {
    this.service = service;
    this.mapper = mapper;
  }

  @Override
  public ResponseEntity<OrderResponse> findById(Long id) {
    return ResponseEntity.ok().body(mapper.fromEntity(service.findById(id)));
  }

  @Override
  public ResponseEntity<List<OrderResponse>> findAll() {
    return ResponseEntity.ok().body(mapper.fromCollectionEntity(service.findAll()));
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

  @Override
  public ResponseEntity<Void> deleteById(Long id) {
    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<Page<OrderResponse>> findAllPaginated(
      Integer page, Integer linesPerPage, String direction, String orderBy) {
    return ResponseEntity.ok()
        .body(
            service
                .findAllPaginated(page, linesPerPage, direction, orderBy)
                .map(mapper::fromEntity));
  }
}
