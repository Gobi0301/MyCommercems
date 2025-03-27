package com.techietact.mycommercems.controllers;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.techietact.mycommercems.dtos.CartDto;
import com.techietact.mycommercems.dtos.OrderDto;
import com.techietact.mycommercems.dtos.PaymentDto;
import com.techietact.mycommercems.models.Order;
import com.techietact.mycommercems.services.CartService;
import com.techietact.mycommercems.services.OrderService;
import com.techietact.mycommercems.services.PaymentService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;
    private final PaymentService paymentService;

    @GetMapping("/{userId}")
    public List<OrderDto> getOrdersByUserId(@PathVariable Long userId, Authentication authentication) {
        return orderService.getOrdersByUserId(userId, authentication);
    }

    @PostMapping("/{userId}/checkout")
    public ResponseEntity<PaymentDto> checkout(@PathVariable Long userId, Authentication authentication) throws StripeException {
        CartDto cart = cartService.getCartByUserId(userId);
        BigDecimal totalPrice = cart.getTotalPrice();
        PaymentIntent paymentIntent = paymentService.createPaymentIntent(totalPrice);

        Order createdOrder = orderService.createOrderFromCart(cart, userId, authentication);

        cartService.clearCart(userId);

        PaymentDto paymentDto = new PaymentDto(paymentIntent.getClientSecret(), totalPrice, "usd", createdOrder.getId());

        return ResponseEntity.ok().body(paymentDto);
    }
}
