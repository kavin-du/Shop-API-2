package com.example.shop.controller;

import com.example.shop.exceptions.ResourceNotFoundException;
import com.example.shop.model.CustomResponse;
import com.example.shop.model.OrderRequest;
import com.example.shop.model.Order;
import com.example.shop.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
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

@RestController
@RequestMapping("/orders")
@Tag(name = "Orders")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping() // post request
    @Operation(summary = "Create a new Order") // swagger related
    @ApiResponse( // swagger related
            responseCode = "201",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Order.class) // response type
            )
    )
    public ResponseEntity<?> create(@Valid @RequestBody OrderRequest orderRequest) {
        Order order;
        try {
            order = orderService.createOrder(orderRequest);
        } catch (ResourceNotFoundException e) {
            // return the error message as response
            return CustomResponse.generate(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        // return the created product
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping() // get request
    @Operation(summary = "Get all orders") // swagger related
    @ApiResponse( // swagger related
            responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Order.class)) // response type
            )
    )
    public ResponseEntity<?> getAll() {
        // return all the orders from the database
        return new ResponseEntity<>(orderService.getAll(), HttpStatus.OK);
    }

    @DeleteMapping("{id}") // delete request
    @Operation(summary = "Remove an order") // swagger related
    @ApiResponse( // swagger related
            responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Order.class) // response type
            )
    )
    public ResponseEntity<?> remove(@PathVariable("id") long id) {
        Order order;
        try {
            order = orderService.remove(id);
        } catch (ResourceNotFoundException e) {
            // return the error message as response
            return CustomResponse.generate(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        // return a copy of the deleted order
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("{id}") // get request
    @Operation(summary = "Get single order") // swagger related
    @ApiResponse( // swagger related
            responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Order.class) // response type
            )
    )
    public ResponseEntity<?> getOne(@PathVariable("id") long id) {
        Order order;
        try {
            order = orderService.getOne(id);
        } catch (ResourceNotFoundException e) {
            // return the error message as response
            return CustomResponse.generate(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        // return the requested order
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PatchMapping("{id}") // patch request
    @Operation(summary = "Update an order") // swagger related
    @ApiResponse( // swagger related
            responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Order.class) // response type
            )
    )
    public ResponseEntity<?> update(@PathVariable long id, @Valid @RequestBody OrderRequest data) {
        Order order;
        try {
            order = orderService.update(id, data);
        } catch (ResourceNotFoundException e) {
            // return the error message as response
            return CustomResponse.generate(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ConstraintViolationException e) {
            // return the error message as response
            return CustomResponse.generate(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // return the error message as response
            return CustomResponse.generate(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        // return the updated order
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}


