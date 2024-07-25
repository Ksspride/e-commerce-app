package com.kss.order.service.impl;

import com.kss.order.dto.OrderLineRequest;
import com.kss.order.dto.OrderLineResponseDto;
import com.kss.order.entity.OrderLine;
import com.kss.order.repos.OrderLineRepository;
import com.kss.order.service.OrderLineService;
import com.kss.order.utils.ObjectMapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderLineServiceImpl implements OrderLineService {

    @Autowired
    private OrderLineRepository repository;
    @Override
    public Integer saveOrderLine(OrderLineRequest orderLineRequest) {
        var orderLine = repository.save(ObjectMapperUtils.map(orderLineRequest, OrderLine.class));
        return orderLine.getId();
    }

    @Override
    public List<OrderLineResponseDto> findAllByOrderId(Integer orderId) {
        List<OrderLine> orderLines = repository.findAllByOrderId(orderId);
        log.info("Returning the order lines.");
        return ObjectMapperUtils.mapAll(orderLines, OrderLineResponseDto.class);
    }
}
