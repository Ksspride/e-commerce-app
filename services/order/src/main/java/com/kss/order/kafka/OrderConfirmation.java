package com.kss.order.kafka;

import com.kss.order.dto.CustomerDto;
import com.kss.order.enums.PaymentMethod;
import com.kss.order.product.ProductPurchaseResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderConfirmation {

    String orderReference;
    BigDecimal totalAmount;
    PaymentMethod paymentMethod;
    CustomerDto customer;
    List<ProductPurchaseResponseDto> products;
}
