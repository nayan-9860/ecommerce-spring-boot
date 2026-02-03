package com.nayan.ecommerce.repository;

import com.nayan.ecommerce.entity.Cart;
import com.nayan.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart , Long> {

    Optional<Cart> findByUser(User user);
}
