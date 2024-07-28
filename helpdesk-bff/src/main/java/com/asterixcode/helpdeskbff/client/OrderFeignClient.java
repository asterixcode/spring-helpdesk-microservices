package com.asterixcode.helpdeskbff.client;

import jakarta.validation.Valid;
import java.util.List;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "order-service-api", path = "/api/v1/orders")
public interface OrderFeignClient {

  @GetMapping("/{id}")
  ResponseEntity<OrderResponse> findById(@PathVariable(name = "id") final Long id);

  @GetMapping
  ResponseEntity<List<OrderResponse>> findAll();

  @PostMapping
  ResponseEntity<Void> save(@Valid @RequestBody final CreateOrderRequest request);

  @PutMapping("/{id}")
  ResponseEntity<OrderResponse> update(
      @PathVariable(name = "id") Long id, final UpdateOrderRequest request);

  @DeleteMapping("/{id}")
  ResponseEntity<Void> deleteById(@PathVariable(name = "id") final Long id);

  @GetMapping("/paginated")
  ResponseEntity<Page<OrderResponse>> findAllPaginated(
      @RequestParam(name = "page", defaultValue = "0") final Integer page,
      @RequestParam(name = "linesPerPage", defaultValue = "12") final Integer linesPerPage,
      @RequestParam(name = "direction", defaultValue = "ASC") final String direction,
      @RequestParam(name = "orderBy", defaultValue = "id") final String orderBy);
}
