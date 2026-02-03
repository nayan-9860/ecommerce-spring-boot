package com.nayan.ecommerce.repository;

import com.nayan.ecommerce.entity.Cart;
import com.nayan.ecommerce.entity.CartItem;
import com.nayan.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem , Long> {

    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

}
