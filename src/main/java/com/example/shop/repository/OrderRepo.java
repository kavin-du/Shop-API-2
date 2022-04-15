package com.example.shop.repository;

import com.example.shop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

// repository for storing the Orders in the database
public interface OrderRepo extends JpaRepository<Order, Long> {
}
