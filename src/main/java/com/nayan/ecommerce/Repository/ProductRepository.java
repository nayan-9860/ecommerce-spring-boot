package com.nayan.ecommerce.repository;

import com.nayan.ecommerce.entity.Product;
import com.nayan.ecommerce.entity.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product , Long> {

    List<Product> findByStatus(ProductStatus status);

    List<Product> findByNameContainingIgnoreCaseAndStatus(String keyword, ProductStatus status);

    List<Product> findByNameContainingIgnoreCase(String keyword);

}
