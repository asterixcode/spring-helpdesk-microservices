package com.asterixcode.orderserviceapi.service;

import com.asterixcode.orderserviceapi.entity.Order;
import com.asterixcode.orderserviceapi.mapper.OrderMapper;
import com.asterixcode.orderserviceapi.repository.OrderRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import models.enums.OrderStatusEnum;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class OrderService implements OrderServiceInterface {

  private final OrderRepository repository;
  private final OrderMapper mapper;

  @Override
  public Order findById(Long id) {
    return repository
        .findById(id)
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    "Order not found. Id: " + id + ", Type: " + Order.class.getSimpleName()));
  }

  @Override
  public void save(CreateOrderRequest createOrderRequest) {
    final var entity = repository.save(mapper.fromRequest(createOrderRequest));
    log.info("Order created: {}", entity);
  }

  @Override
  public OrderResponse update(Long id, UpdateOrderRequest request) {
    Order entity = findById(id);
    entity = mapper.fromRequest(request, entity);

    if (entity.getStatus().equals(OrderStatusEnum.CLOSED)) {
      entity.setClosedAt(LocalDateTime.now());
    }

    final var updatedEntity = repository.save(entity);
    log.info("Order updated: {}", updatedEntity);

    return mapper.fromEntity(updatedEntity);
  }

  @Override
  public void deleteById(Long id) {
    repository.delete(findById(id));
  }

  @Override
  public List<Order> findAll() {
    return repository.findAll();
  }

  @Override
  public Page<Order> findAllPaginated(Integer page, Integer linesPerPage, String direction, String orderBy) {
    PageRequest pageRequest = PageRequest.of(
            page,
            linesPerPage,
            org.springframework.data.domain.Sort.Direction.valueOf(direction),
            orderBy);
    return repository.findAll(pageRequest);
  }
}
