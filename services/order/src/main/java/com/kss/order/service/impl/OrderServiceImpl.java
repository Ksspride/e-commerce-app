package com.kss.order.service.impl;

import com.kss.order.customer.CustomerClient;
import com.kss.order.dto.CustomerDto;
import com.kss.order.dto.OrderLineRequest;
import com.kss.order.dto.OrderRequestDto;
import com.kss.order.dto.OrderResponseDto;
import com.kss.order.entity.Order;
import com.kss.order.kafka.OrderConfirmation;
import com.kss.order.kafka.OrderProducer;
import com.kss.order.payment.PaymentClient;
import com.kss.order.payment.PaymentRequestDto;
import com.kss.order.product.ProductClient;
import com.kss.order.product.ProductPurchaseRequestDto;
import com.kss.order.repos.OrderRepository;
import com.kss.order.service.OrderLineService;
import com.kss.order.service.OrderService;
import com.kss.order.utils.ObjectMapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CustomerClient customerClient;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private PaymentClient paymentClient;

    @Autowired
    private OrderLineService orderLineService;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProducer orderProducer;

    @Override
    public Integer createOrder(OrderRequestDto request) {
        //Check the customer -->OpenFeign
        var customer = customerClient.findCustomerById(request.getCustomerId());
        if(customer.isEmpty()){
            throw new IllegalArgumentException("Cannot create order");
        }

        //Purchase the product--> Product microservice
        var purchasedProducts = productClient.purchaseProducts(request.getProducts());

        //Persist order
        var order = orderRepository.save(ObjectMapperUtils.map(request, Order.class));

        //Persist order lines
        for(ProductPurchaseRequestDto purchaseRequest : request.getProducts()){
            orderLineService.saveOrderLine(new OrderLineRequest(null, order.getId(), purchaseRequest.productId(), purchaseRequest.quantity()));
        }

        var paymentRequest = new PaymentRequestDto(
                request.getId(),
                request.getAmount(),
                request.getPaymentMethod(),
                order.getId(),
                order.getReference(),
                customer.get()
        );
        paymentClient.createPayment(paymentRequest);

        //Start the payment process
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.getReference(),
                        request.getAmount(),
                        request.getPaymentMethod(),
                        customer.get(),
                        purchasedProducts
                )
        );

        //Send the order confirmation --> notification-ms(Kafka)
        return order.getId();
    }

    @Override
    public List<OrderResponseDto> findAllOrders() {
        List<Order> orders = orderRepository.findAll();
        log.info("Returning the list of orders");
        return ObjectMapperUtils.mapAll(orders, OrderResponseDto.class);
    }

    @Override
    public OrderResponseDto findById(Integer orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new IllegalArgumentException("Invalid order id"));
        log.info("Returning the order id: {}", order.getId());
        return ObjectMapperUtils.map(order, OrderResponseDto.class);
    }
}
