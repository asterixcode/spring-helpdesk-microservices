package com.asterixcode.orderserviceapi.mapper;

import com.asterixcode.orderserviceapi.entity.Order;
import models.enums.OrderStatusEnum;
import models.requests.CreateOrderRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = IGNORE,
        nullValueCheckStrategy = ALWAYS)
public interface OrderMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "status", source = "status", qualifiedByName = "mapStatus")
  Order fromRequest(CreateOrderRequest createOrderRequest);

  @Named("mapStatus")
  default OrderStatusEnum mapStatus(String orderStatus) {
    return OrderStatusEnum.toEnum(orderStatus);
  }
}
