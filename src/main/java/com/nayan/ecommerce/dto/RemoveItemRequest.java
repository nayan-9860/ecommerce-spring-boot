package com.nayan.ecommerce.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RemoveItemRequest {
    @NotNull
    private Long userId;

    @NotNull
    private Long productId;
}

