package com.nayan.ecommerce.controller;


import com.nayan.ecommerce.dto.AddToCartRequest;
import com.nayan.ecommerce.dto.CartResponse;
import com.nayan.ecommerce.dto.RemoveItemRequest;
import com.nayan.ecommerce.dto.UpdateQuantityRequest;
import com.nayan.ecommerce.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Validated
public class CartController {

    private final CartService cartService;

    //add to cart
    @PostMapping("/add")
    public ResponseEntity<CartResponse> addToCart( @Valid  @RequestBody AddToCartRequest request){

        CartResponse response = cartService.addProductToCart(
                request.getUserId(),
                request.getProductId(),
                request.getQuantity() );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/update")
    public ResponseEntity<CartResponse> updateQuantity(@Valid @RequestBody UpdateQuantityRequest request){

         CartResponse response = cartService.updateQuantity(
                request.getUserId(),
                request.getProductId(),
                request.getQuantity());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<CartResponse> removeItem(@Valid @RequestBody RemoveItemRequest request){

        CartResponse response = cartService.removeProductFromCart(
                request.getUserId(),
                request.getProductId());

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CartResponse> getCart(){

       CartResponse response = cartService.getCart();
       return ResponseEntity.ok(response);
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<CartResponse> clearCart(@PathVariable Long userId){

        CartResponse response = cartService.clearCart(userId);
        return ResponseEntity.ok(response);
    }

}
