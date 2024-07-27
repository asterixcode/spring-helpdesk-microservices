package com.asterixcode.emailservice.service;

import com.asterixcode.emailservice.models.enums.OperationEnum;
import com.asterixcode.emailservice.util.EmailUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import models.dto.OrderCreatedMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Log4j2
@Service
@RequiredArgsConstructor
public class EmailService {

  private final JavaMailSender mailSender;
  private final TemplateEngine templateEngine;

  public void sendHtmlMail(final OrderCreatedMessage orderDTO, OperationEnum operation)
      throws MessagingException {
    MimeMessage message = mailSender.createMimeMessage();

    String process = getContext(orderDTO, operation);

    EmailUtils.getMimeMessage(message, process, orderDTO, "Order created successfully");

    mailSender.send(message);
  }

  private String getContext(OrderCreatedMessage orderDTO, OperationEnum operation) {
    Context context = new Context();

    return switch (operation) {
      case ORDER_CREATED -> {
        log.info("Sending email for order created");
        context = EmailUtils.getContextToCreatedOrder(orderDTO);
        yield templateEngine.process("email/order-created", context);
      }
      case ORDER_UPDATED -> {
        log.info("Sending email for order updated");
        // context = EmailUtils.getContextToUpdatedOrder(orderDTO);
        yield templateEngine.process("email/order-updated", context);
      }
      case ORDER_DELETED -> {
        log.info("Sending email for order deleted");
        // context = EmailUtils.getContextToDeletedOrder(orderDTO);
        yield templateEngine.process("email/order-deleted", context);
      }
    };
  }
}
