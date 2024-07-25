package com.asterixcode.orderserviceapi.service;

import com.asterixcode.orderserviceapi.entity.Order;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;

public interface OrderServiceInterface {
  Order findById(final Long id);

  void save(CreateOrderRequest createOrderRequest);

  OrderResponse update(final Long id, UpdateOrderRequest request);
}
