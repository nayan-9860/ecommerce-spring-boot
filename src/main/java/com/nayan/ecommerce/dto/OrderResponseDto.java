package com.nayan.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {

    private Long orderId;
    private Long userId;

    private List<OrderItemResponseDto> items;

    private BigDecimal totalAmount;

    private String orderStatus;

    private LocalDateTime createdAt;

    private PaymentResponseDto payment;

    private AddressDto deliveryAddress;
}
