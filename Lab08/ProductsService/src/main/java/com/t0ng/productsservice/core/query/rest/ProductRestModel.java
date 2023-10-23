package com.t0ng.productsservice.core.query.rest;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRestModel {
    private String productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;
}
