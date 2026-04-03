package com.ecom.Ecom_Application.controller;

import com.ecom.Ecom_Application.dto.OrderResponse;
import com.ecom.Ecom_Application.service.OrderService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private  OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestHeader("X-User-Id") String userId){
        Optional<OrderResponse> order = orderService.createOrder(Long.valueOf(userId));
        return new ResponseEntity<>(order.get(), HttpStatus.CREATED);
    }
}
