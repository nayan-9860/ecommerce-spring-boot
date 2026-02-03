package com.nayan.ecommerce.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {

    @NotBlank(message = "product name is required")
    private String name;

    private String description;

    @NotNull(message = "price of product is required")
    @DecimalMin(value = "0.0" , inclusive = false , message = "price must be positive")
    private BigDecimal price;

    @NotNull(message = "stock quantity is required")
    @Min(value = 0 , message = "stock cannot be negative")
    private Integer StockQuantity;

}
