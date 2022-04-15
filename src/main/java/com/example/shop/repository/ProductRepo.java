package com.example.shop.repository;

import com.example.shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

// repository for storing the Products in the database
public interface ProductRepo extends JpaRepository<Product, Long> {
    Product findByProductName(String productName);
}
