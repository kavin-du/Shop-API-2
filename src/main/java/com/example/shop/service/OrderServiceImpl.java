package com.example.shop.service;

import com.example.shop.exceptions.ResourceNotFoundException;
import com.example.shop.model.OrderRequest;
import com.example.shop.model.Order;
import com.example.shop.model.Product;
import com.example.shop.repository.OrderRepo;
import com.example.shop.repository.ProductRepo;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService{
    private final OrderRepo orderRepo; // instance of the order repository
    private final ProductRepo productRepo; // instance of the product repository
    private final Validator validator; // instance of the validator

    // all args constructor
    public OrderServiceImpl(OrderRepo orderRepo, ProductRepo productRepo, Validator validator) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
        this.validator = validator;
    }

    @Override
    public Order createOrder(OrderRequest orderRequest) throws ResourceNotFoundException {
        // find if the order already exists
        Optional<Product> product = productRepo.findById(orderRequest.getProductId());
        if(product.isEmpty()){
            throw new ResourceNotFoundException("Product not exists");
        }
        // create a new order
        Order order = new Order(product.get(), orderRequest.getCount());
        // return the saved order
        return orderRepo.save(order);
    }

    @Override
    public List<Order> getAll() {
        // fetch all orders from the database
        return orderRepo.findAll();
    }

    @Override
    public Order getOne(long id) throws ResourceNotFoundException {
        // find if the order already exists
        Optional<Order> order = orderRepo.findById(id);
        if(order.isEmpty()) {
            throw new ResourceNotFoundException("Oder does not exist");
        }
        // return the requested order
        return order.get();
    }

    @Override
    public Order remove(long id) throws ResourceNotFoundException {
        // find if the order already exists
        Optional<Order> order = orderRepo.findById(id);
        if(order.isEmpty()) {
            throw new ResourceNotFoundException("Order does not exist");
        }
        // delete the order
        orderRepo.deleteById(id);
        return order.get(); // return a copy of the deleted order
    }

    @Override
    public Order update(long id, OrderRequest data) throws ResourceNotFoundException, ConstraintViolationException {
        // find if the product already exists
        Optional<Product> p = productRepo.findById(data.getProductId());
        if(p.isEmpty()) {
            throw new ResourceNotFoundException("Product not found");
        }

        // find if the order already exists
        Optional<Order> o = orderRepo.findById(id);
        if(o.isEmpty()) {
            throw new ResourceNotFoundException("Order not found");
        }

        Order order = o.get();
        order.setCount(data.getCount()); // update the count

        // check if there are any invalid fields
        Set<ConstraintViolation<Order>> violations = validator.validate(order);
        if(!violations.isEmpty()) {
            // if there are any violations, throw exception
            throw new ConstraintViolationException(violations);
        }

        // save the updated order and return a copy
        return orderRepo.save(order);
    }
}
