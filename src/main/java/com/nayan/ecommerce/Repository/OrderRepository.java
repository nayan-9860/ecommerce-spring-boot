package com.nayan.ecommerce.repository;

import com.nayan.ecommerce.entity.Order;
import com.nayan.ecommerce.entity.User;
import com.nayan.ecommerce.entity.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    //all orders of user
    List<Order> findByUser(User user);

    //all orders of user sorted newest first
    List<Order> findByUserOrderByCreatedAtDesc(User user);

    //filter by status
    List<Order> findByStatus(OrderStatus status);
}
