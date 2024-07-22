package com.kss.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineRequest {
    Integer id;
    Integer orderId;
    Integer productId;
    double quantity;
}
