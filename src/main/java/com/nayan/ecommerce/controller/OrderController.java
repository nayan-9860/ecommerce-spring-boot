package com.nayan.ecommerce.controller;

import com.nayan.ecommerce.dto.OrderRequestDto;
import com.nayan.ecommerce.dto.OrderResponseDto;
import com.nayan.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> placeOrder(@RequestBody OrderRequestDto requestDto){

        OrderResponseDto response = orderService.placeOrder(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long orderId){
        OrderResponseDto response = orderService.getOrderDetails(orderId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getOrdersByUser(){
        List<OrderResponseDto> response = orderService.getUserOrders();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/")
    public ResponseEntity<List<OrderResponseDto> >getAllOrders(){
        List<OrderResponseDto> response = orderService.getAllOrders();
        return ResponseEntity.ok(response);
    }


    @PutMapping("/cancelOrder/{orderId}")
    public ResponseEntity<OrderResponseDto> cancelOrder(@PathVariable Long orderId){
        OrderResponseDto response = orderService.cancelOrder(orderId);
        return ResponseEntity.ok(response);
    }



}
