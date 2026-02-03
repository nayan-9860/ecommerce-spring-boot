package com.nayan.ecommerce.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CartItemResponse {

    private Long productId;
    private String productName;
    private double price;
    private int quantity;
    private double totalPrice;


}

