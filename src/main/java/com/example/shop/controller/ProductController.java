package com.example.shop.controller;

import com.example.shop.exceptions.ResourceAlreadyExistsException;
import com.example.shop.exceptions.ResourceNotFoundException;
import com.example.shop.model.CustomResponse;
import com.example.shop.model.Product;
import com.example.shop.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/products")
@Tag(name = "Products")
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping()
    @Operation(summary = "Create a new Product") // swagger related
    @ApiResponse(
            responseCode = "201",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Product.class)
            )
    )
    public ResponseEntity<?> create(@Valid @RequestBody Product product) {
        Product p;
        try {
            p = productService.create(product);
        } catch (ResourceAlreadyExistsException e) {
            return CustomResponse.generate(e.getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(p, HttpStatus.CREATED);
    }

    @GetMapping()
    @Operation(summary = "Get all products")
    @ApiResponse(
            responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Product.class))
            )
    )
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Remove a product")
    @ApiResponse(
            responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Product.class)
            )
    )
    public ResponseEntity<?> remove(@PathVariable("id") long id) {
        Product p;
        try {
            p = productService.remove(id);
        } catch (ResourceNotFoundException e) {
            return CustomResponse.generate(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get single product")
    @ApiResponse(
            responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Product.class)
            )
    )
    public ResponseEntity<?> getOne(@PathVariable("id") long id) {
        Product p;
        try {
            p = productService.getOne(id);
        } catch (ResourceNotFoundException e) {
            return CustomResponse.generate(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @PatchMapping("{id}")
    @Operation(summary = "Update a product")
    @ApiResponse(
            responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Product.class)
            )
    )
    public ResponseEntity<?> update(@PathVariable long id, @Parameter(schema = @Schema(implementation = Product.class)) @RequestBody Map<String, Object> data) {
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
        return new ResponseEntity<>(p, HttpStatus.OK);
    }
}
