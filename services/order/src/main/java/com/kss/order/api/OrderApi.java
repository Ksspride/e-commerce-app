package com.kss.order.api;


import com.kss.order.dto.OrderRequestDto;
import com.kss.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderApi {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Integer> createOrder(@RequestBody @Valid OrderRequestDto request){
        return ResponseEntity.ok().body(orderService.createOrder(request));
    }
}
