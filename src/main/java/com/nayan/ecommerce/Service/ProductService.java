package com.nayan.ecommerce.service;

import com.nayan.ecommerce.dto.ProductRequestDto;
import com.nayan.ecommerce.dto.ProductResponseDto;
import com.nayan.ecommerce.entity.enums.ProductStatus;

import java.util.List;

public interface ProductService {

    ProductResponseDto createProduct(ProductRequestDto dto);

    ProductResponseDto updateProduct(Long productId, ProductRequestDto dto);

    ProductResponseDto getProduct(Long productId);

    List<ProductResponseDto> getAllProducts();

    void deleteProduct(Long productId);

    List<ProductResponseDto> searchProducts(String keyword , ProductStatus status);

}

