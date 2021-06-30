package com.coffeeshop.store.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductInStockIsLowerThanZero extends Exception {

    public ProductInStockIsLowerThanZero(Long productId, Double inStock) {
        super(String.format("Product with EAN: %s, to decrement informed fail, the minimum stock capacity: %s",
                productId, inStock));
    }

}
