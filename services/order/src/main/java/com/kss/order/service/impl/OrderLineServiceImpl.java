package com.kss.order.service.impl;

import com.kss.order.dto.OrderLineRequest;
import com.kss.order.entity.OrderLine;
import com.kss.order.repos.OrderLineRepository;
import com.kss.order.service.OrderLineService;
import com.kss.order.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderLineServiceImpl implements OrderLineService {

    @Autowired
    private OrderLineRepository repository;
    @Override
    public Integer saveOrderLine(OrderLineRequest orderLineRequest) {
        var orderLine = repository.save(ObjectMapperUtils.map(orderLineRequest, OrderLine.class));
        return orderLine.getId();
    }
}
