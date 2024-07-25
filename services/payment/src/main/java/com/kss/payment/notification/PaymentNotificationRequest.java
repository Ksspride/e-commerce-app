package com.kss.payment.notification;

import com.kss.payment.enums.PaymentMethod;

import java.math.BigDecimal;

public record PaymentNotificationRequest(
        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerFirstname,
        String customerLastname,
        String customerEmail
) {
}

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class PaymentNotificationRequest {
//    private String orderReference;
//    private BigDecimal amount;
//    private PaymentMethod paymentMethod;
//    private String customerFirstName;
//    private String customerLastName;
//    private String customerEmail;
//
//}
