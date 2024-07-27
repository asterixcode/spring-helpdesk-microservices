package com.asterixcode.emailservice.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import lombok.experimental.UtilityClass;
import models.dto.OrderCreatedMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.context.Context;

@UtilityClass
public class EmailUtils {

  @Value("${mail.username}")
  private String from;

  public static void getMimeMessage(
      MimeMessage message, String process, OrderCreatedMessage orderDTO, String subject)
      throws MessagingException {
    MimeMessageHelper helper =
        new MimeMessageHelper(
            message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

    helper.setTo(orderDTO.getCustomer().email());
    helper.setFrom(new InternetAddress(from));
    helper.setSubject(subject);
    helper.setText(process, true);
  }

  public static Context getContextToCreatedOrder(OrderCreatedMessage orderDTO) {
    Context context = new Context();

    context.setVariable("customerName", orderDTO.getCustomer().name());
    context.setVariable("orderId", orderDTO.getOrder().id());
    context.setVariable("title", orderDTO.getOrder().title());
    context.setVariable("description", orderDTO.getOrder().description());
    context.setVariable("creationDate", orderDTO.getOrder().createdAt());
    context.setVariable("status", orderDTO.getOrder().status());
    context.setVariable("responsibleTechnician", orderDTO.getRequester().name());

    return context;
  }
}
