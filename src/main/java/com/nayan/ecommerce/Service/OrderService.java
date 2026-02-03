package com.nayan.ecommerce.service;

import com.nayan.ecommerce.dto.OrderRequestDto;
import com.nayan.ecommerce.dto.OrderResponseDto;

import java.util.List;

public interface OrderService {

    OrderResponseDto placeOrder(OrderRequestDto requestDto);

    OrderResponseDto cancelOrder(Long orderId);

    OrderResponseDto getOrderDetails(Long orderId);

    List<OrderResponseDto> getUserOrders();

    List<OrderResponseDto> getAllOrders();
}
