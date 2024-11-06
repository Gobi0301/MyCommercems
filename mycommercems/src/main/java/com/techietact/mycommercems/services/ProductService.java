package com.techietact.mycommercems.services;

import lombok.RequiredArgsConstructor;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.techietact.mycommercems.exceptions.AppException;
import com.techietact.mycommercems.models.Product;
import com.techietact.mycommercems.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }

    public Product add(Product product) {
        return productRepository.save(product);
    }
    
    public Product updateProduct(Product product) {
    	 Optional<Product> existingProduct = productRepository.findById(product.getId());
         if (existingProduct.isPresent()) {
             return productRepository.save(product);
         } else {
             throw new AppException("Product not found", HttpStatus.NOT_FOUND);
         }
    }
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
