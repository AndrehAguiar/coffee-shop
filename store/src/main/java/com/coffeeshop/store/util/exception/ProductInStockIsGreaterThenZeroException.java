package com.coffeeshop.store.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductInStockIsGreaterThenZeroException extends Exception {

    public ProductInStockIsGreaterThenZeroException(String productEan, Double inStock) {
        super(String.format("The stock have %s of the Product with EAN %s",
                inStock, productEan));
    }
}
