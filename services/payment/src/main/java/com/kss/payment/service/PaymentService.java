package com.kss.payment.service;

import com.kss.payment.models.PaymentRequestDto;

public interface PaymentService {
    Integer createPayment(PaymentRequestDto request);
}
