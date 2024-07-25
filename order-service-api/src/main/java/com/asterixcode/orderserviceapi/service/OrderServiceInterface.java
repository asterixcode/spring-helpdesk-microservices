package com.asterixcode.orderserviceapi.service;

import models.requests.CreateOrderRequest;

public interface OrderServiceInterface {
  void save(CreateOrderRequest createOrderRequest);
}
