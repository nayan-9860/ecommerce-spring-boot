package com.nayan.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDto {

    private String paymentMethod;
    private String paymentStatus;
    private String transactionId;
}
