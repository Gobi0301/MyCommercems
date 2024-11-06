package com.techietact.mycommercems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techietact.mycommercems.models.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
