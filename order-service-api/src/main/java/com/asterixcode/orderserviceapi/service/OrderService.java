package com.asterixcode.orderserviceapi.service;

import com.asterixcode.orderserviceapi.client.UserServiceFeignClient;
import com.asterixcode.orderserviceapi.entity.Order;
import com.asterixcode.orderserviceapi.mapper.OrderMapper;
import com.asterixcode.orderserviceapi.repository.OrderRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import models.dto.OrderCreatedMessage;
import models.enums.OrderStatusEnum;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import models.responses.UserResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class OrderService implements OrderServiceInterface {

  private final OrderRepository repository;
  private final OrderMapper mapper;
  private final UserServiceFeignClient userServiceFeignClient;
  private final RabbitTemplate rabbitTemplate;

  @Override
  @Cacheable(value = "orders", key = "#id")
  public Order findById(Long id) {
    return repository
        .findById(id)
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    "Order not found. Id: " + id + ", Type: " + Order.class.getSimpleName()));
  }

  @Override
  @CacheEvict(value = "orders", allEntries = true)
  public void save(CreateOrderRequest createOrderRequest) {
    final var requester = validateUserId(createOrderRequest.requesterId());
    final var customer = validateUserId(createOrderRequest.customerId());
    final var entity = repository.save(mapper.fromRequest(createOrderRequest));

    log.info("Order created: {}", entity);

    rabbitTemplate.convertAndSend(
        "helpdesk",
        "rk.orders.create",
        new OrderCreatedMessage(mapper.fromEntity(entity), customer, requester));
  }

  @Override
  @CacheEvict(value = "orders", allEntries = true)
  public OrderResponse update(Long id, UpdateOrderRequest request) {
    validateUser(request);

    Order entity = findById(id);
    entity = mapper.fromRequest(request, entity);

    if (entity.getStatus().equals(OrderStatusEnum.CLOSED)) {
      entity.setClosedAt(LocalDateTime.now());
    }

    final var updatedEntity = repository.save(entity);
    log.info("Order updated: {}", updatedEntity);

    return mapper.fromEntity(updatedEntity);
  }

  private void validateUser(UpdateOrderRequest request) {
    if (request.requesterId() != null) validateUserId(request.requesterId());
    if (request.customerId() != null) validateUserId(request.customerId());
  }

  @Override
  @CacheEvict(value = "orders", allEntries = true)
  public void deleteById(Long id) {
    repository.delete(findById(id));
  }

  @Override
  @Cacheable(value = "orders")
  public List<Order> findAll() {
    return repository.findAll();
  }

  @Override
  @Cacheable(
      value = "orders",
      key = "#page + '-' + #linesPerPage + '-' + #direction + '-' + #orderBy")
  public Page<Order> findAllPaginated(
      Integer page, Integer linesPerPage, String direction, String orderBy) {
    PageRequest pageRequest =
        PageRequest.of(
            page,
            linesPerPage,
            org.springframework.data.domain.Sort.Direction.valueOf(direction),
            orderBy);
    return repository.findAll(pageRequest);
  }

  public UserResponse validateUserId(final String id) {
    return userServiceFeignClient.findById(id).getBody();
  }
}
