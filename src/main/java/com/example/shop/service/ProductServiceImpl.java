package com.example.shop.service;

import com.example.shop.exceptions.ResourceAlreadyExistsException;
import com.example.shop.exceptions.ResourceNotFoundException;
import com.example.shop.model.Product;
import com.example.shop.repository.ProductRepo;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService{
    private final Validator validator;
    private final ProductRepo productRepo;

    public ProductServiceImpl(ProductRepo productRepo, Validator validator) {
        this.productRepo = productRepo;
        this.validator = validator;
    }

    @Override
    public Product create(Product product) throws ResourceAlreadyExistsException {
        Product p = productRepo.findByProductName(product.getProductName());
        if(p != null && p.equals(product)) {
            throw new ResourceAlreadyExistsException("Product already exists");
        }
        return productRepo.save(product);
    }

    @Override
    public Product getOne(long id) throws ResourceNotFoundException {
        Optional<Product> p = productRepo.findById(id);
        if(p.isEmpty()) {
            throw new ResourceNotFoundException("Product not found");
        }
        return p.get();
    }

    @Override
    public Product remove(long id) throws ResourceNotFoundException {
        Optional<Product> p = productRepo.findById(id);
        if(p.isEmpty()) {
            throw new ResourceNotFoundException("Product not found");
        }
        productRepo.deleteById(id);
        return p.get();
    }

    @Override
    public Product update(long id, Map<String, Object> data) throws ResourceNotFoundException, ConstraintViolationException, Exception {
        Optional<Product> p = productRepo.findById(id);
        if(p.isEmpty()) {
            throw new ResourceNotFoundException("Product not found");
        }
        Product product = p.get();
        data.forEach((key, value) -> {
            // enhanced switch case
            switch (key) {
                case "productName" -> product.setProductName((String) value);
                case "price" -> product.setPrice((double) value);
            }
        });

        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        if(!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        return productRepo.save(product);
    }

    @Override
    public List<Product> getAll() {
        return productRepo.findAll();
    }
}
