package com.nayan.ecommerce.service;

import com.nayan.ecommerce.dto.OrderRequestDto;
import com.nayan.ecommerce.dto.OrderResponseDto;
import com.nayan.ecommerce.entity.*;
import com.nayan.ecommerce.entity.enums.OrderStatus;
import com.nayan.ecommerce.exception.BadRequestException;
import com.nayan.ecommerce.exception.ResourceNotFoundException;
import com.nayan.ecommerce.repository.*;
import com.nayan.ecommerce.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public OrderResponseDto placeOrder(OrderRequestDto requestDto) {

        Long userId = requestDto.getUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new BadRequestException("Cart is empty, cannot place order");
        }

        Order order = new Order();
        order.setUser(user);
        order.setPaymentMethod(requestDto.getPaymentMethod());
        order.setShippingAddress(String.valueOf(requestDto.getDeliveryAddress()));
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.PLACED);

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItem cartItem : cart.getItems()) {
            Product product = cartItem.getProduct();

            if(product.getStockQuantity() < cartItem.getQuantity())
            { throw new BadRequestException("insufficient stock for product : " + product.getName()); }

            product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());


            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setItemPrice(product.getPrice());

            order.getItems().add(orderItem);

            totalAmount = totalAmount.add(
                    product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()))
            );
        }

        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        // Clear cart safely using orphanRemoval
        cart.getItems().clear();

        return modelMapper.map(savedOrder, OrderResponseDto.class);
    }


    @Override
    public OrderResponseDto cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->new ResourceNotFoundException("order is not found with id :" + orderId));

        if(order.getStatus() == OrderStatus.CANCELLED){
            throw new BadRequestException("order is already cancelled");
        }

        if(order.getStatus() == OrderStatus.SHIPPED  || order.getStatus() == OrderStatus.DELIVERED){
            throw new BadRequestException("order can not be cancelled at this stage");
        }

        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
            productRepository.save(product);
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);

        return modelMapper.map(order , OrderResponseDto.class);
    }

    @Override
    public OrderResponseDto getOrderDetails(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("order is not found with orderid : " + orderId ));


        return modelMapper.map(order , OrderResponseDto.class);
    }

    @Override
    public List<OrderResponseDto> getUserOrders() {

        String email = SecurityUtils.getCurrentUserEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("user is not found with email : " + email));

        List<Order> orders = orderRepository.findByUserOrderByCreatedAtDesc(user);

        return  orders.stream()
                .map(order -> modelMapper.map(order , OrderResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponseDto> getAllOrders() {

        return orderRepository.findAll().stream()
                .map(order -> modelMapper.map(order , OrderResponseDto.class))
                .collect(Collectors.toList());
    }
}
