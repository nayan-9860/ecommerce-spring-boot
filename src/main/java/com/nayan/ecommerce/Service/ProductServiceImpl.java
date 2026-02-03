package com.nayan.ecommerce.service;

import com.nayan.ecommerce.dto.ProductRequestDto;
import com.nayan.ecommerce.dto.ProductResponseDto;
import com.nayan.ecommerce.entity.Product;
import com.nayan.ecommerce.entity.enums.ProductStatus;
import com.nayan.ecommerce.exception.BadRequestException;
import com.nayan.ecommerce.exception.ResourceNotFoundException;
import com.nayan.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;
    //Creating product

    @Override
    public ProductResponseDto createProduct(ProductRequestDto dto) {

        if(dto.getPrice().compareTo(BigDecimal.ZERO) <= 0  || dto.getStockQuantity() < 0 ){
            throw new BadRequestException("invalid price or stock quantity");
        }

        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());

        productRepository.save(product);

        return modelMapper.map(product , ProductResponseDto.class);
    }

    @Override
    public ProductResponseDto updateProduct(Long productId, ProductRequestDto dto) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product is not found with id : " + productId));

        if (dto.getPrice().compareTo(BigDecimal.ZERO) <= 0 || dto.getStockQuantity() < 0) {
            throw new BadRequestException("Invalid price or stock quantity");
        }

        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setStockQuantity(dto.getStockQuantity());

        Product updatedProduct = productRepository.save(product);
        return modelMapper.map(updatedProduct , ProductResponseDto.class);
    }

    @Override
    public ProductResponseDto getProduct(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product is not found : " + productId));


        return modelMapper.map(product , ProductResponseDto.class);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {

        return productRepository.findAll().stream()
                .map(product -> modelMapper.map(product , ProductResponseDto.class))
                .collect(Collectors.toList());

    }

    @Override
    public void deleteProduct(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product is not found"));

        productRepository.delete(product);
    }

    @Override
    public List<ProductResponseDto> searchProducts(String keyword , ProductStatus status) {

        List<Product> product;

        if (status != null) {
            product = productRepository
                    .findByNameContainingIgnoreCaseAndStatus(keyword, status);
        } else {
            product = productRepository
                    .findByNameContainingIgnoreCase(keyword);
        }


        return product.stream()
                .map(p -> modelMapper.map(p , ProductResponseDto.class ))
                .collect(Collectors.toList());
    }

}
