package com.example.shop.service;

import com.example.shop.exceptions.ResourceNotFoundException;
import com.example.shop.model.OrderRequest;
import com.example.shop.model.Order;

import javax.validation.ConstraintViolationException;
import java.util.List;

public interface OrderService {
    Order createOrder(OrderRequest order) throws ResourceNotFoundException;
    List<Order> getAll();
    Order getOne(long id) throws ResourceNotFoundException;
    Order remove(long id) throws ResourceNotFoundException;
    Order update(long id, OrderRequest data) throws ResourceNotFoundException, ConstraintViolationException;
}

