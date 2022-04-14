package com.example.shop.service;

import com.example.shop.exceptions.ResourceNotFoundException;
import com.example.shop.model.Order;
import com.example.shop.model.OrderRequest;
import com.example.shop.model.Product;
import com.example.shop.repository.OrderRepo;
import com.example.shop.repository.ProductRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepo orderRepo;
    @Mock
    private ProductRepo productRepo;
    @Mock
    private Validator validator;

    @Captor
    private ArgumentCaptor<Long> orderIdArgumentCaptor;

    private OrderService orderService;

    @BeforeEach
    void setup(){
        orderService = new OrderServiceImpl(orderRepo, productRepo, validator);
    }

    @Test
    @DisplayName("Should create an order with valid info")
    void createOrder() {
        long id = 1;
        long productId = 1;
        String productName = "my product";
        double price = 45.85;
        int count = 5;

        OrderRequest order = new OrderRequest(productId, count);

        Product product = new Product(productId, productName, price);

        // returned value from the db call
        Order expectedOrder = new Order(id, product, count);

        // return the order when the db is called
        Mockito.when(productRepo.findById(Mockito.eq(productId))).thenReturn(Optional.of(product));
        Mockito.when(orderRepo.save(Mockito.any(Order.class))).thenReturn(expectedOrder);

        final Order[] actualResponse = new Order[1];

        // make sure no exception is thrown
        assertDoesNotThrow(() -> {
            actualResponse[0] = orderService.createOrder(order);
        });

        // check the properties of the returned object
        Assertions.assertThat(actualResponse[0].getId()).isEqualTo(expectedOrder.getId());
        Assertions.assertThat(actualResponse[0].getCount()).isEqualTo(expectedOrder.getCount());
        Assertions.assertThat(actualResponse[0].getProduct().getId()).isEqualTo(expectedOrder.getProduct().getId());
    }

    @Test
    @DisplayName("Should find an order by Id")
    void findById() {
        long id = 1;
        long productId = 1;
        String productName = "my product";
        double price = 45.85;
        int count = 5;

        Product product = new Product(productId, productName, price);

        // returned value from the db call
        Order expectedOrder = new Order(id, product, count);

        // mock the db call for id = 1
        Mockito.when(orderRepo.findById(Mockito.eq(1L))).thenReturn(Optional.of(expectedOrder));

        final Order[] actualResponse = new Order[1];

        // make sure no exception is thrown
        assertDoesNotThrow(() -> {
            actualResponse[0] = orderService.getOne(1L);
        });

        // verify the properties of the returned object
        Assertions.assertThat(actualResponse[0].getId()).isEqualTo(expectedOrder.getId());
        Assertions.assertThat(actualResponse[0].getCount()).isEqualTo(expectedOrder.getCount());
        Assertions.assertThat(actualResponse[0].getProduct().getId()).isEqualTo(expectedOrder.getProduct().getId());
    }

    @Test
    @DisplayName("Invalid order id should throw an Exception")
    void shouldNotFindById() {
        long id = 1;
        long productId = 1;
        String productName = "my product";
        double price = 45.85;
        int count = 5;

        Product product = new Product(productId, productName, price);

        // returned value from the db call
        Order expectedOrder = new Order(id, product, count);

        // mock the db calls
        Mockito.when(orderRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(expectedOrder));
        Mockito.when(orderRepo.findById(Mockito.eq(56L))).thenThrow(new IllegalArgumentException());

        // capture the exception
        assertThrows(IllegalArgumentException.class, () -> {
            Order actualResponse = orderService.getOne(56L);
        });
    }

    @Test
    @DisplayName("Should remove an order")
    void removeOrder() {
        long id = 1;
        long productId = 1;
        String productName = "my product";
        double price = 45.85;
        int count = 5;

        Product product = new Product(productId, productName, price);

        // returned value from the db call
        Order expectedOrder = new Order(id, product, count);

        // mock the db checking before removing the order
        Mockito.when(orderRepo.findById(Mockito.eq(id))).thenReturn(Optional.of(expectedOrder));

        // make sure no exceptions are thrown
        assertDoesNotThrow(() -> {
            orderService.remove(id);
        });

        // verify the db has called 1 times
        Mockito.verify(orderRepo, Mockito.times(1)).deleteById(orderIdArgumentCaptor.capture());

    }

    @Test
    @DisplayName("Removing non existing order throws exception")
    void removeNonExistingOrder() {
        long id = 1;

        // mock the db checking before removing the order
        Mockito.when(orderRepo.findById(Mockito.eq(id))).thenReturn(Optional.empty());

        // make sure the exception is thrown
        assertThrows(ResourceNotFoundException.class, () -> {
            orderService.remove(id);
        });

        // verify the db haven't called
        Mockito.verify(orderRepo, Mockito.times(0)).deleteById(orderIdArgumentCaptor.capture());

    }

    @Test
    @DisplayName("Should update an order")
    void updateOrder() {
        long id = 1;
        long productId = 1;
        String productName = "my product";
        double price = 45.85;
        int count = 5;

        Product product = new Product(productId, productName, price);

        // returned value from the db call
        Order expectedOrder = new Order(id, product, count);

        // mock the db call for id = 1
        Mockito.when(productRepo.findById(Mockito.eq(productId))).thenReturn(Optional.of(product));
        Mockito.when(orderRepo.findById(Mockito.eq(1L))).thenReturn(Optional.of(expectedOrder));

        int newCount = 13;

        // returned order when updated
        Order newOrder = new Order(id, product, newCount);

        // mock the db call when actual update happens
        Mockito.when(orderRepo.save(Mockito.any(Order.class))).thenReturn(newOrder);

        // data for updating the order
        OrderRequest data = new OrderRequest(id, newCount);

        final Order[] actualResponse = new Order[1];

        // make sure no exception is thrown
        assertDoesNotThrow(() -> {
            actualResponse[0] = orderService.update(id, data);
        });

        // verify the properties of the returned object
        Assertions.assertThat(actualResponse[0].getId()).isEqualTo(newOrder.getId());
        Assertions.assertThat(actualResponse[0].getCount()).isEqualTo(newOrder.getCount());
        Assertions.assertThat(actualResponse[0].getProduct().getId()).isEqualTo(newOrder.getProduct().getId());
    }

    @Test
    @DisplayName("Should get all orders")
    void getAllOrders() {
        long id1 = 1;
        long productId1 = 1;
        String name1 = "my product1";
        double price1 = 56.23;
        int count1 = 5;

        long id2 = 2;
        long productId2 = 1;
        String name2 = "my product2";
        double price2 = 45.98;
        int count2 = 5;

        Order order1 = new Order(id1, new Product(productId1, name1, price1), count1);
        Order order2 = new Order(id2, new Product(productId2, name2, price2), count2);

        // returned value from the db call
        ArrayList<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);

        // mock the db call
        Mockito.when(orderRepo.findAll()).thenReturn(orders);

        List<Order> returnedOrders = orderService.getAll();

        // verify the properties of the returned object
        Assertions.assertThat(returnedOrders.size()).isEqualTo(orders.size());
        Assertions.assertThat(returnedOrders.get(0).getId()).isEqualTo(orders.get(0).getId());
        Assertions.assertThat(returnedOrders.get(1).getId()).isEqualTo(orders.get(1).getId());
    }

}