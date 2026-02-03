package com.nayan.ecommerce.controller;

import com.nayan.ecommerce.dto.ProductRequestDto;
import com.nayan.ecommerce.dto.ProductResponseDto;
import com.nayan.ecommerce.entity.enums.ProductStatus;
import com.nayan.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@Validated
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@Valid  @RequestBody ProductRequestDto requestDto){

        ProductResponseDto response = productService.createProduct(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long productId ,
                                                           @Valid @RequestBody ProductRequestDto requestDto){

        ProductResponseDto response = productService.updateProduct(productId , requestDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long productId){

        ProductResponseDto response = productService.getProduct(productId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(){

        List<ProductResponseDto> response = productService.getAllProducts();
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId){

        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDto>> searchProduct(@RequestParam String keyword ,
                                                                        @RequestParam(required = false)ProductStatus status){

        List<ProductResponseDto> response = productService.searchProducts(keyword , status);
        return ResponseEntity.ok(response);
    }
}
