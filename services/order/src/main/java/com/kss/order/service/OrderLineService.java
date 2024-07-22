package com.kss.order.service;

import com.kss.order.dto.OrderLineRequest;

public interface OrderLineService {
    Integer saveOrderLine(OrderLineRequest orderLineRequest);
}
