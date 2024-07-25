package com.kss.payment.service.impl;

import com.kss.payment.entity.Payment;
import com.kss.payment.models.PaymentRequestDto;
import com.kss.payment.notification.NotificationProducer;
import com.kss.payment.notification.PaymentNotificationRequest;
import com.kss.payment.repos.PaymentRepository;
import com.kss.payment.service.PaymentService;
import com.kss.payment.utils.ObjectMapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository repository;

    @Autowired
    private NotificationProducer notificationProducer;
    @Override
    public Integer createPayment(PaymentRequestDto request) {
        Payment payment = repository.save(ObjectMapperUtils.map(request, Payment.class));
        log.info("Payment is successfully completed with amount {}", payment.getAmount());
        notificationProducer.sendNotification(new PaymentNotificationRequest(
                request.getOrderReference(),
                request.getAmount(),
                request.getPaymentMethod(),
                request.getCustomer().getFirstName(),
                request.getCustomer().getLastName(),
                request.getCustomer().getEmail()
        ));
        return payment.getId() ;
    }
}
