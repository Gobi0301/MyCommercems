package com.techietact.mycommercems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techietact.mycommercems.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
