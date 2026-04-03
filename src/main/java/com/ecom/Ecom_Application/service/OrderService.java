package com.ecom.Ecom_Application.service;

import com.ecom.Ecom_Application.dto.OrderItemDTO;
import com.ecom.Ecom_Application.dto.OrderResponse;
import com.ecom.Ecom_Application.model.*;
import com.ecom.Ecom_Application.repository.OrderRepository;
import com.ecom.Ecom_Application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartService cartService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

//    public OrderService(CartService cartService, UserRepository userRepository, OrderRepository orderRepository) {
//        this.cartService = cartService;
//        this.userRepository = userRepository;
//        this.orderRepository = orderRepository;
//    }

    @Transactional
    public Optional<OrderResponse> createOrder(Long userId) {

        // 1. Validate user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Get cart items
        List<CartItem> cartItems = cartService.findItems(String.valueOf(userId));

        if (cartItems.isEmpty()) {
            return Optional.empty();
        }

        // 3. Calculate total
        BigDecimal totalAmount = cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 4. Create Order
        Order order = new Order();
        order.setUser(user);
        order.setOrerStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalAmount);

        // 5. Convert CartItems → OrderItems
        List<OrderItem> orderItems = cartItems.stream()
                .map(item -> new OrderItem(
                        null,
                        item.getProduct(),
                        item.getQuantity(),
                        item.getPrice(),
                        order
                ))
                .toList();

        order.setItems(orderItems);

        // 6. Save Order
        Order savedOrder = orderRepository.save(order);

        // 7. Clear cart
        cartService.clearCart(String.valueOf(userId));

        // 8. Return response
        return Optional.of(mapToOrderResponse(savedOrder));
    }

    // ------------------ MAPPER ------------------

    private OrderResponse mapToOrderResponse(Order order) {

        List<OrderItemDTO> items = order.getItems().stream()
                .map(item -> new OrderItemDTO(
                        item.getId(),
                        item.getProduct().getId(),
                        item.getQuantity(),
                        item.getPrice(),
                        item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())) // subtotal
                ))
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getTotalAmount(),
                order.getOrerStatus(),
                items,
                order.getCreateAt()
        );
    }
}