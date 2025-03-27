package com.techietact.mycommercems.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.techietact.mycommercems.dtos.CartDto;
import com.techietact.mycommercems.dtos.CartItemDto;
import com.techietact.mycommercems.models.Cart;
import com.techietact.mycommercems.models.CartItem;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    @Mapping(target = "id", source = "cart.id")
    @Mapping(target = "cartItems", source = "cartItems")
    CartDto cartToCartDto(Cart cart, BigDecimal totalPrice, List<CartItemDto> cartItems);

    @Mapping(target = "subTotal", expression = "java(cartItem.getSubTotal())")
    CartItemDto cartItemToCartItemDto(CartItem cartItem);
}