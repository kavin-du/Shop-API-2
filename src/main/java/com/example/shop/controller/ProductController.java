package com.example.shop.controller;

import com.example.shop.exceptions.ResourceAlreadyExistsException;
import com.example.shop.exceptions.ResourceNotFoundException;
import com.example.shop.model.CustomResponse;
import com.example.shop.model.Product;
import com.example.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody Product product) {
        Product p;
        try {
            p = productService.create(product);
        } catch (ResourceAlreadyExistsException e) {
            return CustomResponse.generate(e.getMessage(), HttpStatus.CONFLICT);
        }
        return CustomResponse.generate("Product created", p, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<?> getAll() {
        return CustomResponse.generate("Successful", productService.getAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") int id) {
        Product p;
        try {
            p = productService.getOne(id);
        } catch (ResourceNotFoundException e) {
            return CustomResponse.generate(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return CustomResponse.generate("Successful", p, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> remove(@PathVariable("id") int id) {
        Product p;
        try {
            p = productService.remove(id);
        } catch (ResourceNotFoundException e) {
            return CustomResponse.generate(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return CustomResponse.generate("Product removed", p, HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody Map<String, Object> data) {
        Product p;
        try {
            p = productService.update(id, data);
        } catch (ResourceNotFoundException e) {
            return CustomResponse.generate(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ConstraintViolationException e) {
            return CustomResponse.generate(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return CustomResponse.generate(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return CustomResponse.generate("Product updated", p, HttpStatus.OK);
    }
}
