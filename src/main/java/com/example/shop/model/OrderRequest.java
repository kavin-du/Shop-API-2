package com.example.shop.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

// request type for when creating an order
public class OrderRequest {
    @NotNull @Min(1)
    private long productId;

    @NotNull @Min(1)
    private int count;

    // no args constructor
    public OrderRequest() {
    }

    // some args constructor
    public OrderRequest(long productId, int count) {
        this.productId = productId;
        this.count = count;
    }

    // getters and setters
    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
