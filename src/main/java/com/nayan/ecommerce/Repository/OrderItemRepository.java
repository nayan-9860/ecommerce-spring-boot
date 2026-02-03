package com.nayan.ecommerce.repository;

import com.nayan.ecommerce.entity.Order;
import com.nayan.ecommerce.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem , Long> {


  List<OrderItem> findByOrder(Order order);

}
