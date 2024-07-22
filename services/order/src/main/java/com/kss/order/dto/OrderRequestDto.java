package com.kss.order.dto;

import com.kss.order.enums.PaymentMethod;
import com.kss.order.product.ProductPurchaseRequestDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
    private Integer id;
    private String reference;
    @Positive(message = "Order amount should be positive")
    private BigDecimal amount;
    @NotNull(message = "Payment method should be precised")
    private PaymentMethod paymentMethod;
    @NotNull(message = "Customer should be present")
    @NotEmpty(message = "Customer should be present")
    @NotBlank(message = "Customer should be present")
    private String customerId;
    @NotEmpty(message = "You should at least purchase one product")
    private List<ProductPurchaseRequestDto> products;
}
