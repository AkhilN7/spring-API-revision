package com.ecom.Ecom_Application.dto;

import com.ecom.Ecom_Application.model.OrderItem;
import com.ecom.Ecom_Application.model.OrderStatus;
import com.ecom.Ecom_Application.model.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class OrderResponse {
    private Long id;
    private BigDecimal totalAmount;
    private OrderStatus orerStatus = OrderStatus.PENDING;
    private List<OrderItemDTO> items;

    @CreationTimestamp
    private LocalDateTime createAt;




    public OrderResponse(Long id, BigDecimal totalAmount, OrderStatus orerStatus, List<OrderItemDTO> list, LocalDateTime createAt) {
    }
}
