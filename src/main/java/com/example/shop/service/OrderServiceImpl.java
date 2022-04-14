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
    private final OrderRepo orderRepo;
    private final ProductRepo productRepo;
    private final Validator validator;

    public OrderServiceImpl(OrderRepo orderRepo, ProductRepo productRepo, Validator validator) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
        this.validator = validator;
    }

    @Override
    public Order createOrder(OrderRequest orderRequest) throws ResourceNotFoundException {
        Optional<Product> product = productRepo.findById(orderRequest.getProductId());
        if(product.isEmpty()){
            throw new ResourceNotFoundException("Product not exists");
        }
        Order order = new Order(product.get(), orderRequest.getCount());
        return orderRepo.save(order);
    }

    @Override
    public List<Order> getAll() {
        return orderRepo.findAll();
    }

    @Override
    public Order getOne(long id) throws ResourceNotFoundException {
        Optional<Order> order = orderRepo.findById(id);
        if(order.isEmpty()) {
            throw new ResourceNotFoundException("Oder does not exist");
        }
        return order.get();
    }

    @Override
    public Order remove(long id) throws ResourceNotFoundException {
        Optional<Order> order = orderRepo.findById(id);
        if(order.isEmpty()) {
            throw new ResourceNotFoundException("Order does not exist");
        }
        orderRepo.deleteById(id);
        return order.get();
    }

    @Override
    public Order update(long id, OrderRequest data) throws ResourceNotFoundException, ConstraintViolationException {
        Optional<Product> p = productRepo.findById(data.getProductId());
        if(p.isEmpty()) {
            throw new ResourceNotFoundException("Product not found");
        }

        Optional<Order> o = orderRepo.findById(id);
        if(o.isEmpty()) {
            throw new ResourceNotFoundException("Order not found");
        }

        Order order = o.get();
        order.setCount(data.getCount()); // update the count

        Set<ConstraintViolation<Order>> violations = validator.validate(order);
        if(!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        return orderRepo.save(order);
    }
}
