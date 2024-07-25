package com.asterixcode.orderserviceapi.mapper;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import com.asterixcode.orderserviceapi.entity.Order;
import java.util.List;
import models.enums.OrderStatusEnum;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = IGNORE,
    nullValueCheckStrategy = ALWAYS)
public interface OrderMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "closedAt", ignore = true)
  @Mapping(target = "status", source = "status", qualifiedByName = "mapStatus")
  Order fromRequest(CreateOrderRequest createOrderRequest);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "closedAt", ignore = true)
  @Mapping(target = "status", source = "request.status", qualifiedByName = "mapStatus")
  Order fromRequest(UpdateOrderRequest request, @MappingTarget Order entity);

  OrderResponse fromEntity(Order updatedEntity);

  List<OrderResponse> fromCollectionEntity(List<Order> orders);

  @Named("mapStatus")
  default OrderStatusEnum mapStatus(String orderStatus) {
    return OrderStatusEnum.toEnum(orderStatus);
  }
}
