package models.enums;

import java.util.Arrays;

public enum OrderStatusEnum {
  OPEN("Open"),
  IN_PROGRESS("In Progress"),
  CLOSED("Closed"),
  CANCELLED("Cancelled");

  private final String description;

  OrderStatusEnum(String description) {
    this.description = description;
  }

  public static OrderStatusEnum toEnum(final String description) {
    return Arrays.stream(OrderStatusEnum.values())
        .filter(orderStatusEnum -> orderStatusEnum.getDescription().equals(description))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Invalid description " + description));
  }

  public String getDescription() {
    return this.description;
  }
}
