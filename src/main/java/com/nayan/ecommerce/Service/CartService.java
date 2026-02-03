package com.nayan.ecommerce.service;

import com.nayan.ecommerce.dto.CartResponse;
import com.nayan.ecommerce.entity.Cart;
import com.nayan.ecommerce.entity.CartItem;
import com.nayan.ecommerce.entity.Product;
import com.nayan.ecommerce.entity.User;
import com.nayan.ecommerce.entity.enums.ProductStatus;
import com.nayan.ecommerce.exception.BadRequestException;
import com.nayan.ecommerce.exception.ResourceNotFoundException;
import com.nayan.ecommerce.repository.CartItemRepository;
import com.nayan.ecommerce.repository.CartRepository;
import com.nayan.ecommerce.repository.ProductRepository;
import com.nayan.ecommerce.repository.UserRepository;
import com.nayan.ecommerce.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;


@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    private final ModelMapper modelMapper;

    public CartResponse getCart(){

        String email = SecurityUtils.getCurrentUserEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("user is not found with email : " + email));

         Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("cart is not found "));

        return modelMapper.map(cart , CartResponse.class);
    }

    public CartResponse addProductToCart(Long userId , Long productId , int quantity){

        if(quantity <= 0 ){
            throw new BadRequestException("Quantity must be greater than zero");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user is not found with id : " + userId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product was not found "));

        if(product.getStatus() != ProductStatus.ACTIVE){
            throw new BadRequestException("product is not available");
        }

        Cart cart = cartRepository.findByUser(user)
                .orElseGet( () -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    newCart.setItems(new ArrayList<>());
                    return cartRepository.save(newCart);

                });

        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product)
                .orElse(null);

        if(cartItem == null){
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cart.getItems().add(cartItem);
        }else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        if(cartItem.getQuantity() > product.getStockQuantity()){
            throw new BadRequestException("Insufficient stock");
        }

        cartRepository.save(cart);
        return modelMapper.map(cart , CartResponse.class);
    }


    public CartResponse removeProductFromCart(Long userId , Long productId){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user is not found with id: " + userId));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("cart is not found "));

        cart.getItems().removeIf(
                item -> item.getProduct().getId().equals(productId)
        );

        cartRepository.save(cart);
        return modelMapper.map(cart , CartResponse.class);
    }


    public CartResponse updateQuantity(Long userId , Long productId ,int quantity) {

        if (quantity <= 0) {
            throw new BadRequestException("Quantity must be greater than zero");
        }


        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user is not found with id : " + userId));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("cart is not found"));

        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("product not in a cart "));

        if (quantity > cartItem.getProduct().getStockQuantity()) {
            throw new BadRequestException("insufficient stock");
        }
        cartItem.setQuantity(quantity);
        cartRepository.save(cart);
        return  modelMapper.map(cart , CartResponse.class);

    }


    public CartResponse clearCart(Long userId){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user is not found with id : " + userId));

        Cart cart = cartRepository.findByUser(user)
                        .orElseThrow(()-> new ResourceNotFoundException("cart is not found"));


        cart.getItems().clear();
        cartRepository.save(cart);
        return modelMapper.map(cart , CartResponse.class);
    }

}
