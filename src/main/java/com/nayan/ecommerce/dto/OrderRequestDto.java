package com.nayan.ecommerce.dto;

import com.nayan.ecommerce.entity.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {


    private Long userId;

    private List<OrderItemRequestDto> items;

    private PaymentMethod paymentMethod;

    private AddressDto deliveryAddress;

}
