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
    private final Validator validator; // validator for validating products
    private final ProductRepo productRepo; // instance of product repository

    // all args constructor
    public ProductServiceImpl(ProductRepo productRepo, Validator validator) {
        this.productRepo = productRepo;
        this.validator = validator;
    }

    @Override
    public Product create(Product product) throws ResourceAlreadyExistsException {
        // check if the product already exists
        Product p = productRepo.findByProductName(product.getProductName());
        if(p != null && p.equals(product)) { // throw the exception
            throw new ResourceAlreadyExistsException("Product already exists");
        }
        // return the saved product
        return productRepo.save(product);
    }

    @Override
    public Product getOne(long id) throws ResourceNotFoundException {
        // find if the product exists
        Optional<Product> p = productRepo.findById(id);
        if(p.isEmpty()) {
            throw new ResourceNotFoundException("Product not found");
        }
        // return the product
        return p.get();
    }

    @Override
    public Product remove(long id) throws ResourceNotFoundException {
        // check if the product exists before remove
        Optional<Product> p = productRepo.findById(id);
        if(p.isEmpty()) {
            throw new ResourceNotFoundException("Product not found");
        }
        // remove the product
        productRepo.deleteById(id);
        return p.get(); // return a copy
    }

    @Override
    public Product update(long id, Map<String, Object> data) throws ResourceNotFoundException, ConstraintViolationException, Exception {
        // check if the product exists
        Optional<Product> p = productRepo.findById(id);
        if(p.isEmpty()) {
            throw new ResourceNotFoundException("Product not found");
        }
        Product product = p.get(); // get the product
        data.forEach((key, value) -> {
            // enhanced switch case
            switch (key) { // update each value of the product
                case "productName" -> product.setProductName((String) value);
                case "price" -> product.setPrice((double) value);
            }
        });

        // check if the updated product is valid
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        if(!violations.isEmpty()) { // if there are invalid items, throw exception
            throw new ConstraintViolationException(violations);
        }

        // save and return the updated product
        return productRepo.save(product);
    }

    @Override
    public List<Product> getAll() {
        // get all the products from the database
        return productRepo.findAll();
    }
}
