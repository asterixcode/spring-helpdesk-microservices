package com.asterixcode.orderserviceapi.service;

import com.asterixcode.orderserviceapi.entity.Order;
import java.util.List;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.data.domain.Page;

public interface OrderServiceInterface {
  Order findById(final Long id);

  void save(CreateOrderRequest createOrderRequest);

  OrderResponse update(final Long id, UpdateOrderRequest request);

  void deleteById(final Long id);

  List<Order> findAll();

  Page<Order> findAllPaginated(Integer page, Integer linesPerPage, String direction, String orderBy);
}
