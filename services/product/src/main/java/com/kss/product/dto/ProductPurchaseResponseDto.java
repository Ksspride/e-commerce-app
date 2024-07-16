package com.kss.product.dto;

import java.math.BigDecimal;

public record ProductPurchaseResponseDto (
        Integer productId,
        String name,
        String description,
        BigDecimal price,
double quantity
) {
        }