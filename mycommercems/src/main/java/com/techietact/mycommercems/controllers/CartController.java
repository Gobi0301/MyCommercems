package com.techietact.mycommercems.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.techietact.mycommercems.dtos.CartDto;
import com.techietact.mycommercems.exceptions.AppException;
import com.techietact.mycommercems.services.CartService;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> getCartByUserId(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();
        CartDto cartDto = cartService.getCartByUserId(userId);
        if (cartDto != null) {
            response.put("cart", cartDto);
            response.put("numberOfItemsInCart", cartService.getNumberOfItemsInCart(userId));
            return ResponseEntity.ok().body(response);
        } else {
            throw new AppException("User's cart not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{userId}/{productId}/{quantity}")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable Long userId, @PathVariable Long productId, @PathVariable int quantity) {
        CartDto cartDto = cartService.addItemToCart(userId, productId, quantity);
        return ResponseEntity.ok().body(cartDto);
    }

    @DeleteMapping("/{userId}/{productId}")
    public ResponseEntity<CartDto> removeItemFromCart(@PathVariable Long userId, @PathVariable Long productId) {
        CartDto cartDto = cartService.removeItemFromCart(userId, productId);
        return ResponseEntity.ok().body(cartDto);
    }
}
