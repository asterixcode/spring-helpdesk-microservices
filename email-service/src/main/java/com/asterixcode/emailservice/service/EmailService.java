package com.asterixcode.emailservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import models.dto.OrderCreatedMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class EmailService {

  private final JavaMailSender mailSender;

  @Value("${mail.text-create-order-confirmation}")
  private String textCreateOrderConfirmation;

  public void sendMail(final OrderCreatedMessage order) {
    log.info("Sending email to client: {}", order.getCustomer().email());

    // Send email logic
    SimpleMailMessage message = getSimpleMailMessage(order);

    message.setSubject(message.getSubject());
    message.setTo(order.getCustomer().email());
    message.setText(message.getText());

    try {
      mailSender.send(message);
      log.info("Email sent successfully to: {}", order.getCustomer().email());
    } catch (MailException e) {
      switch (e.getClass().getSimpleName()) {
        case "MailAuthenticationException":
          log.error("Authentication error while sending email: {}", e.getMessage());
          break;
        case "MailSendException":
          log.error("Error sending email: {}", e.getMessage());
          break;
        default:
          log.error("Unexpected error while sending email: {}", e.getMessage());
          break;
      }
    }

    mailSender.send(message);
  }

  private SimpleMailMessage getSimpleMailMessage(OrderCreatedMessage order) {
    String subject = "Service order created successfully";
    String text =
        String.format(
            textCreateOrderConfirmation,
            order.getCustomer().name(),
            order.getOrder().id(),
            order.getOrder().title(),
            order.getOrder().description(),
            order.getOrder().createdAt(),
            order.getOrder().status(),
            order.getRequester().name());
    SimpleMailMessage message = new SimpleMailMessage();
    message.setSubject(subject);
    message.setTo(order.getCustomer().email());
    message.setText(text);
    return message;
  }
}
