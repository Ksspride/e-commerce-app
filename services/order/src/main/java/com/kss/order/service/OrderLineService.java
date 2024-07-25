package com.kss.order.service;

import com.kss.order.dto.OrderLineRequest;
import com.kss.order.dto.OrderLineResponseDto;

import java.util.List;

public interface OrderLineService {
    Integer saveOrderLine(OrderLineRequest orderLineRequest);

    List<OrderLineResponseDto> findAllByOrderId(Integer orderId);
}
