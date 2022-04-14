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
import java.util.Map;

@RestController
@RequestMapping("/orders")
@Tag(name = "Orders")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping()
    @Operation(summary = "Create a new Order") // swagger related
    @ApiResponse(
            responseCode = "201",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Order.class)
            )
    )
    public ResponseEntity<?> create(@Valid @RequestBody OrderRequest orderRequest) {
        Order order;
        try {
            order = orderService.createOrder(orderRequest);
        } catch (ResourceNotFoundException e) {
            return CustomResponse.generate(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping()
    @Operation(summary = "Get all orders")
    @ApiResponse(
            responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Order.class))
            )
    )
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(orderService.getAll(), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Remove an order")
    @ApiResponse(
            responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Order.class)
            )
    )
    public ResponseEntity<?> remove(@PathVariable("id") long id) {
        Order order;
        try {
            order = orderService.remove(id);
        } catch (ResourceNotFoundException e) {
            return CustomResponse.generate(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get single order")
    @ApiResponse(
            responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Order.class)
            )
    )
    public ResponseEntity<?> getOne(@PathVariable("id") long id) {
        Order order;
        try {
            order = orderService.getOne(id);
        } catch (ResourceNotFoundException e) {
            return CustomResponse.generate(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PatchMapping("{id}")
    @Operation(summary = "Update an order")
    @ApiResponse(
            responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Order.class)
            )
    )
    public ResponseEntity<?> update(@PathVariable long id, @Valid @RequestBody OrderRequest data) {
        Order order;
        try {
            order = orderService.update(id, data);
        } catch (ResourceNotFoundException e) {
            return CustomResponse.generate(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ConstraintViolationException e) {
            return CustomResponse.generate(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return CustomResponse.generate(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}


