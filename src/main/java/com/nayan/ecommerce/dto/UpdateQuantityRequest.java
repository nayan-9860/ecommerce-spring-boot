package com.nayan.ecommerce.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateQuantityRequest {

    @NotNull(message = "userId is required")
    private Long userId;

    @NotNull
    private Long productId;

    @Min(value = 1 , message = "quantity must be at least 1")
    private int quantity;
}
