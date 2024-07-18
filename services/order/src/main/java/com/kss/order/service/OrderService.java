package com.kss.order.service;

import com.kss.order.dto.OrderRequestDto;

public interface OrderService {

    Integer createOrder(OrderRequestDto request);
}
