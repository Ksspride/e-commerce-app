package com.kss.order.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PurchaseRequest {
    @NotNull(message = "Product is mandatory")
    private Integer productId;
    @Positive(message = "Quantity is mandatory")
    private double quantity;

}
