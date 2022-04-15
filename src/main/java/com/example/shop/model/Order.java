package com.example.shop.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    @NotNull
    private Product product;

    @Min(1)
    @NotNull
    private int count;

    // no args constructor
    public Order() {
    }

    // all args constructor
    public Order(long id, Product product, int count) {
        this.id = id;
        this.product = product;
        this.count = count;
    }

    public Order(Product product, int count) {
        this.product = product;
        this.count = count;
    }

    // getters and setters
    public long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
