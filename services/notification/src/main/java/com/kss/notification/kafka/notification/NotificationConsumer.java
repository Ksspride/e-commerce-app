package com.kss.notification.kafka.notification;

import com.kss.notification.email.EmailService;
import com.kss.notification.entity.Notification;
import com.kss.notification.enums.NotificationType;
import com.kss.notification.kafka.order.OrderConfirmation;
import com.kss.notification.kafka.payment.PaymentConfirmation;
import com.kss.notification.repos.NotificationRepository;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import java.time.LocalDateTime;

@Slf4j
public class NotificationConsumer {

    @Autowired
    private NotificationRepository repository;


    @Autowired
    private EmailService emailService;


    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotification(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info("Consuming the message from payment-topic Topic:: %s", paymentConfirmation );
        repository.save(Notification.builder().type(NotificationType.PAYMENT_CONFIRMATION)
                .notificationDate(LocalDateTime.now())
                .paymentConfirmation(paymentConfirmation)
                .build());

        var customerName = paymentConfirmation.getCustomerFirstname() + " " + paymentConfirmation.getCustomerLastname();
        emailService.sentPaymentSuccessEmail(
                paymentConfirmation.getCustomerEmail(),
                customerName,
                paymentConfirmation.getAmount(),
                paymentConfirmation.getOrderReference()
        );
    }



    @KafkaListener(topics = "order-topic")
    public void consumePaymentSuccessNotification(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info("Consuming the message from order-topic Topic:: %s", orderConfirmation );
        repository.save(Notification.builder().type(NotificationType.ORDER_CONFIRMATION)
                .notificationDate(LocalDateTime.now())
                .orderConfirmation(orderConfirmation)
                .build());

        var customerName = orderConfirmation.getCustomer().getFirstName() + " " + orderConfirmation.getCustomer().getLastName();
        emailService.sentOrderSuccessEmail(
                orderConfirmation.getCustomer().getEmail(),
                customerName,
                orderConfirmation.getTotalAmount(),
                orderConfirmation.getOrderReference(),
                orderConfirmation.getProducts()
        );
    }

}
