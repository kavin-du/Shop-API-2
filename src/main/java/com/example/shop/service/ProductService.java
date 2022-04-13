package com.example.shop.service;

import com.example.shop.exceptions.ResourceAlreadyExistsException;
import com.example.shop.exceptions.ResourceNotFoundException;
import com.example.shop.model.Product;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;

public interface ProductService {
    Product create(Product product) throws ResourceAlreadyExistsException;
    List<Product> getAll();
    Product getOne(int id) throws ResourceNotFoundException;
    Product remove(int id) throws ResourceNotFoundException;
    Product update(int id, Map<String, Object> data) throws ResourceNotFoundException, ConstraintViolationException, Exception;
}
