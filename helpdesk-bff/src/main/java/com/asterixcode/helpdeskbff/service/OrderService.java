package com.asterixcode.helpdeskbff.service;

import com.asterixcode.helpdeskbff.client.OrderFeignClient;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderFeignClient orderFeignClient;

  public OrderResponse findById(Long id) {
    return orderFeignClient.findById(id).getBody();
  }

  public void save(CreateOrderRequest createOrderRequest) {
    orderFeignClient.save(createOrderRequest);
  }

  public OrderResponse update(Long id, UpdateOrderRequest request) {
    return orderFeignClient.update(id, request).getBody();
  }

  public void deleteById(Long id) {
    orderFeignClient.deleteById(id);
  }

  public List<OrderResponse> findAll() {
    return orderFeignClient.findAll().getBody();
  }

  public Page<OrderResponse> findAllPaginated(
      Integer page, Integer linesPerPage, String direction, String orderBy) {
    return orderFeignClient.findAllPaginated(page, linesPerPage, direction, orderBy).getBody();
  }
}
