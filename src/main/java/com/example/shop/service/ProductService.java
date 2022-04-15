package com.example.shop.service;

import com.example.shop.exceptions.ResourceAlreadyExistsException;
import com.example.shop.exceptions.ResourceNotFoundException;
import com.example.shop.model.Product;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;

// interface for creating products
public interface ProductService {
    Product create(Product product) throws ResourceAlreadyExistsException;
    List<Product> getAll();
    Product getOne(long id) throws ResourceNotFoundException;
    Product remove(long id) throws ResourceNotFoundException;
    Product update(long id, Map<String, Object> data) throws ResourceNotFoundException, ConstraintViolationException, Exception;
}
