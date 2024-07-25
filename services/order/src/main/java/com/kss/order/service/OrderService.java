package com.kss.order.service;

import com.kss.order.dto.OrderRequestDto;
import com.kss.order.dto.OrderResponseDto;

import java.util.List;

public interface OrderService {

    Integer createOrder(OrderRequestDto request);

    List<OrderResponseDto> findAllOrders();

    OrderResponseDto findById(Integer orderId);
}
