package com.example.shop.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OrderRequest {
    @NotNull @Min(1)
    private long productId;

    @NotNull @Min(1)
    private int count;

    public OrderRequest() {
    }

    public OrderRequest(long productId, int count) {
        this.productId = productId;
        this.count = count;
    }

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
