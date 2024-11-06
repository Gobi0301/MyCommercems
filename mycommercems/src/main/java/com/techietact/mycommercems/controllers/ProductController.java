package com.techietact.mycommercems.controllers;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.techietact.mycommercems.dtos.ProductDto;
import com.techietact.mycommercems.exceptions.AppException;
import com.techietact.mycommercems.models.Product;
import com.techietact.mycommercems.services.ProductService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("all")
    public Page<Product> getAll(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10")
                                                 int size ) {
    	Pageable pageable = PageRequest.of(page, size);
        return productService.getAllProducts(pageable);
    }

    @PostMapping("add")
    public Product add(@RequestBody ProductDto productDto) {
        if (
                productDto.getName() == null ||
                productDto.getName().isEmpty() ||
                productDto.getDescription() == null || productDto.getDescription().isEmpty() ||
                productDto.getImgUrl() == null || productDto.getImgUrl().isEmpty() ||
                productDto.getPrice().compareTo(BigDecimal.ZERO) <= 0) {

            throw new AppException("All fields are required.", HttpStatus.BAD_REQUEST);
        }
        Product newProduct = new Product();
        newProduct.setName(productDto.getName());
        newProduct.setDescription(productDto.getDescription());
        newProduct.setPrice(productDto.getPrice());
        newProduct.setImgUrl(productDto.getImgUrl());

        return productService.add(newProduct);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable Long productId) {
        Optional<Product> productOptional = productService.getProductById(productId);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            return ResponseEntity.ok(product);
        } else {
            throw new AppException("Product not found", HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId,@RequestBody ProductDto productDto){
    	Optional<Product> productOptional = productService.getProductById(productId);
    	
    	if(productOptional.isPresent()) {
    		Product existingProduct = productOptional.get();
    		
    		  if (
    	                productDto.getName() == null ||
    	                productDto.getName().isEmpty() ||
    	                productDto.getDescription() == null || productDto.getDescription().isEmpty() ||
    	                productDto.getImgUrl() == null || productDto.getImgUrl().isEmpty() ||
    	                productDto.getPrice().compareTo(BigDecimal.ZERO) <= 0) {

    	            throw new AppException("All fields are required.", HttpStatus.BAD_REQUEST);
    	        }
    		  existingProduct.setName(productDto.getName());
    		  existingProduct.setDescription(productDto.getDescription());
    		  existingProduct.setPrice(productDto.getPrice());
    		  existingProduct.setImgUrl(productDto.getImgUrl());
    		  
    		  Product updateProduct = productService.updateProduct(existingProduct);
    		  
    	
    		  return ResponseEntity.ok(updateProduct);
    	}else {
    		throw new AppException("Product not found", HttpStatus.NOT_FOUND);
    	}
    }
    
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId){
    	 Optional<Product> productOptional = productService.getProductById(productId);

    	    if (productOptional.isPresent()) {
    	        productService.deleteProduct(productId);
    	        return ResponseEntity.ok().body("Product deleted successfully");
    	    } else {
    	        throw new AppException("Product not found", HttpStatus.NOT_FOUND);
    	    }
    }
}
