package com.asterixcode.emailservice.listener;

import com.asterixcode.emailservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import models.dto.OrderCreatedMessage;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class OrderListener {

  private final EmailService emailService;

  @RabbitListener(
      bindings =
          @QueueBinding(
              value = @Queue(value = "queue.orders"),
              exchange = @Exchange(value = "helpdesk", type = ExchangeTypes.TOPIC),
              key = "rk.orders.create"))
  public void listen(OrderCreatedMessage message) {
    log.info("Order created message received: {}", message);
    emailService.sendMail(message);
  }
}
