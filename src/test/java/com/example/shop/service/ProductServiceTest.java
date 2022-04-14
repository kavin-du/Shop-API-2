package com.example.shop.service;

import com.example.shop.exceptions.ResourceNotFoundException;
import com.example.shop.model.Product;
import com.example.shop.repository.ProductRepo;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
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
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepo productRepo;
    @Mock
    private Validator validator;

    @Captor
    private ArgumentCaptor<Long> productIdArgumentCaptor;

    private ProductService productService;

    @BeforeEach
    void setup(){
        productService = new ProductServiceImpl(productRepo, validator);
    }

    @Test
    @DisplayName("Valid info should create a product")
    void createProduct() {
        long id = 1;
        String prodName = "my product";
        double price = 56.23;

        Product product = new Product(prodName, price);

        // returned value from the db call
        Product expectedProduct = new Product(id, prodName, price);

        // return the product when the db is called
        Mockito.when(productRepo.save(Mockito.any(Product.class))).thenReturn(expectedProduct);

        final Product[] actualResponse = new Product[1];

        // make sure no exception is thrown
        assertDoesNotThrow(() -> {
            actualResponse[0] = productService.create(product);
        });

        // check the properties of the returned object
        Assertions.assertThat(actualResponse[0].getId()).isEqualTo(expectedProduct.getId());
        Assertions.assertThat(actualResponse[0].getProductName()).isEqualTo(expectedProduct.getProductName());
        Assertions.assertThat(actualResponse[0].getPrice()).isEqualTo(expectedProduct.getPrice());
    }

    @Test
    @DisplayName("Should find a product by Id")
    void findById() {
        long id = 1;
        String prodName = "my product";
        double price = 56.23;

        // returned value from the db call
        Product expectedProduct = new Product(id, prodName, price);

        // mock the db call for id = 1
        Mockito.when(productRepo.findById(Mockito.eq(1L))).thenReturn(Optional.of(expectedProduct));

        final Product[] actualResponse = new Product[1];

        // make sure no exception is thrown
        assertDoesNotThrow(() -> {
            actualResponse[0] = productService.getOne(1L);
        });

        // verify the properties of the returned object
        Assertions.assertThat(actualResponse[0].getId()).isEqualTo(expectedProduct.getId());
        Assertions.assertThat(actualResponse[0].getProductName()).isEqualTo(expectedProduct.getProductName());
        Assertions.assertThat(actualResponse[0].getPrice()).isEqualTo(expectedProduct.getPrice());
    }

    @Test
    @DisplayName("Invalid product id should throw an Exception")
    void shouldNotFindById() {
        long id = 1;
        String prodName = "my product";
        double price = 56.23;

        // returned value from the db call
        Product expectedProduct = new Product(id, prodName, price);

        // mock the db calls
        Mockito.when(productRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(expectedProduct));
        Mockito.when(productRepo.findById(Mockito.eq(56L))).thenThrow(new IllegalArgumentException());

        // capture the exception
        assertThrows(IllegalArgumentException.class, () -> {
            Product actualResponse = productService.getOne(56L);
        });
    }

    @Test
    @DisplayName("Should remove a product")
    void removeProduct() {
        long id = 1;
        String prodName = "my product";
        double price = 56.23;

        // returned value from the database
        Product expectedProduct = new Product(id, prodName, price);

        // mock the db checking before removing the product
        Mockito.when(productRepo.findById(Mockito.eq(id))).thenReturn(Optional.of(expectedProduct));

        // make sure no exceptions are thrown
        assertDoesNotThrow(() -> {
            productService.remove(id);
        });

        // verify the db has called 1 times
        Mockito.verify(productRepo, Mockito.times(1)).deleteById(productIdArgumentCaptor.capture());

    }

    @Test
    @DisplayName("Removing non existing product throws exception")
    void removeNonExistingProduct() {
        long id = 1;
        String prodName = "my product";
        double price = 56.23;

        // returned value from the database
        Product expectedProduct = new Product(id, prodName, price);

        // mock the db checking before removing the product
        Mockito.when(productRepo.findById(Mockito.eq(id))).thenReturn(Optional.empty());

        // make sure the exception is thrown
        assertThrows(ResourceNotFoundException.class, () -> {
            productService.remove(id);
        });

        // verify the db haven't called
        Mockito.verify(productRepo, Mockito.times(0)).deleteById(productIdArgumentCaptor.capture());

    }

    @Test
    @DisplayName("Should update a product")
    void updateProduct() {
        long id = 1;
        String prodName = "my product";
        double price = 56.23;

        // returned value from the db call
        Product expectedProduct = new Product(id, prodName, price);

        // mock the db call for id = 1
        Mockito.when(productRepo.findById(Mockito.eq(1L))).thenReturn(Optional.of(expectedProduct));

        String newName = "new product";
        double newPrice = 12.56;

        // returned product when updated
        Product newProduct = new Product(id, newName, newPrice);
        Mockito.when(productRepo.save(Mockito.any(Product.class))).thenReturn(newProduct);

        // data for updating the product
        HashMap<String, Object> data = new HashMap<>();
        data.put("productName", newName);
        data.put("price", newPrice);

        final Product[] actualResponse = new Product[1];

        // make sure no exception is thrown
        assertDoesNotThrow(() -> {
            actualResponse[0] = productService.update(id, data);
        });

        // verify the properties of the returned object
        Assertions.assertThat(actualResponse[0].getId()).isEqualTo(newProduct.getId());
        Assertions.assertThat(actualResponse[0].getProductName()).isEqualTo(newProduct.getProductName());
        Assertions.assertThat(actualResponse[0].getPrice()).isEqualTo(newProduct.getPrice());
    }

    @Test
    @DisplayName("Should get all products")
    void getAllProducts() {
        long id1 = 1;
        String name1 = "my product";
        double price1 = 56.23;

        long id2 = 2;
        String name2 = "my product2";
        double price2 = 45.98;

        Product product1 = new Product(id1, name1, price1);
        Product product2 = new Product(id2, name2, price2);

        // returned value from the db call
        ArrayList<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        // mock the db call
        Mockito.when(productRepo.findAll()).thenReturn(products);

        List<Product> returnedProducts = productService.getAll();

        // verify the properties of the returned object
        Assertions.assertThat(returnedProducts.size()).isEqualTo(products.size());
        Assertions.assertThat(returnedProducts.get(0).getId()).isEqualTo(products.get(0).getId());
        Assertions.assertThat(returnedProducts.get(1).getId()).isEqualTo(products.get(1).getId());
    }

}