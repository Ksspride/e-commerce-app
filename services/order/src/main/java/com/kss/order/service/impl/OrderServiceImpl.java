package com.kss.order.service.impl;

import com.kss.order.dto.OrderRequestDto;
import com.kss.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Override
    public Integer createOrder(OrderRequestDto request) {
        //Check the customer -->OpenFeign

        //Purchase the product--> Product microservice

        //Persist order

        //Persist order lines

        //Start the payment process

        //Send the order confirmation --> notification-ms(Kafka)
        return null;
    }
}
